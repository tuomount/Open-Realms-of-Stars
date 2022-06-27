package org.openRealmOfStars.player.diplomacy;

import java.util.ArrayList;

import org.openRealmOfStars.AI.Mission.Mission;
import org.openRealmOfStars.AI.Mission.MissionPhase;
import org.openRealmOfStars.AI.Mission.MissionType;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.WinningStrategy;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.artifact.Artifact;
import org.openRealmOfStars.player.diplomacy.negotiation.NegotiationList;
import org.openRealmOfStars.player.diplomacy.negotiation.NegotiationOffer;
import org.openRealmOfStars.player.diplomacy.negotiation.NegotiationType;
import org.openRealmOfStars.player.diplomacy.speeches.SpeechType;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.fleet.FleetVisibility;
import org.openRealmOfStars.player.leader.Job;
import org.openRealmOfStars.player.leader.Perk;
import org.openRealmOfStars.player.leader.stats.StatType;
import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.player.tech.TechList;
import org.openRealmOfStars.player.tech.TechType;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.StarMapUtilities;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.vote.Vote;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2017-2022 Tuomo Untinen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see http://www.gnu.org/licenses/
 *
 * Diplomatic Trade between two players. This class is able to
 * tell players if trade is good for them or not. Also this
 * class is able to generate trade for AI player.
 *
 */
public class DiplomaticTrade {

  /**
   * Starmap object containing all player infos and map infos.
   */
  private StarMap starMap;

  /**
   * Index for First player.
   */
  private int first;

  /**
   * Index for second player.
   */
  private int second;

  /**
   * First player is getting in offer.
   */
  private NegotiationList firstOffer;

  /**
   * Second player is getting in offer.
   */
  private NegotiationList secondOffer;

  /**
   * Tech List for first player. This list contains techs which first player
   * is missing.
   */
  private ArrayList<Tech> techListForFirst;

  /**
   * Tech List for second player. This list contains techs which second player
   * is missing.
   */
  private ArrayList<Tech> techListForSecond;

  /**
   * Fleet list for first player. This list contain fleets which are actually
   * first player's fleets.
   */
  private ArrayList<Fleet> fleetListForFirst;

  /**
   * Fleet list for second player. This list contain fleets which are actually
   * second player's fleet.
   */
  private ArrayList<Fleet> fleetListForSecond;

  /**
   * Planet list for first player. This list contain planets which are actually
   * first player's.
   */
  private ArrayList<Planet> planetListForFirst;

  /**
   * Planet list for second player. This list contain planets which are actually
   * second player's.
   */
  private ArrayList<Planet> planetListForSecond;

  /**
   * Flag for indicating diplomacy with pirates.
   */
  private boolean diplomacyWithPirates = false;

  /**
   * Difference between two parties which is considered as
   * insult;
   */
  public static final int DECLINE_INSULT = 10000;

  /**
   * Equal trade
   */
  public static final int TRADE = 0;
  /**
   * Buy trade
   */
  public static final int BUY = 1;
  /**
   * Sell trade
   */
  public static final int SELL = 2;
  /**
   * Power of multiple border crossing.
   */
  private static final int BORDER_CROSSING_POWER = 20;
  /**
   * Constructor for Diplomatic trade.
   * @param map Starmap
   * @param index1 First player index
   * @param index2 Second player index
   * @throws IllegalArgumentException if player indexes are out of bounds.
   */
  public DiplomaticTrade(final StarMap map, final int index1,
      final int index2) throws IllegalArgumentException {
    starMap = map;
    first = index1;
    second = index2;
    diplomacyWithPirates = false;
    PlayerInfo pirate = starMap.getPlayerList().getSpacePiratePlayer();
    int pirateIndex = starMap.getPlayerList().getIndex(pirate);
    PlayerInfo monster = starMap.getPlayerList().getSpaceMonsterPlayer();
    int monsterIndex = starMap.getPlayerList().getIndex(monster);
    int max = starMap.getPlayerList().getCurrentMaxPlayers();
    if (pirate != null
        && first == pirateIndex || second == pirateIndex) {
      diplomacyWithPirates = true;
    }
    if (monster != null
        && first == monsterIndex || second == monsterIndex) {
      diplomacyWithPirates = true;
    }
    if (!diplomacyWithPirates) {
      if (first >= max || first < 0) {
        throw new IllegalArgumentException("First player index out of bounds("
            + first + ")!");
      }
      if (second >= max || second < 0) {
        throw new IllegalArgumentException("Second player index out of bounds("
           + second + ")!");
      }
    }
  }

  /**
   * Gets the speechtype by current offer
   * @return SpeechType
   */
  public SpeechType getSpeechTypeByOffer() {
    if (firstOffer == null && secondOffer == null) {
      return SpeechType.NOTHING_TO_TRADE;
    }
    if (firstOffer == null) {
      return SpeechType.NOTHING_TO_TRADE;
    }
    if (firstOffer.getSize() == 0
        && (secondOffer == null || secondOffer.getSize() == 0)) {
      return SpeechType.NOTHING_TO_TRADE;
    }
    if (firstOffer.getEmbargoOffer() != null) {
      return SpeechType.TRADE_EMBARGO;
    }
    if (firstOffer.isPeaceInOffer()) {
      return SpeechType.PEACE_OFFER;
    }
    if (firstOffer.isWarInOffer()) {
      return SpeechType.MAKE_WAR;
    }
    if (firstOffer.isTypeInOffer(NegotiationType.ALLIANCE)) {
      return SpeechType.ALLIANCE;
    }
    if (firstOffer.isTypeInOffer(NegotiationType.SPY_TRADE)) {
      return SpeechType.OFFER_SPY_TRADE;
    }
    if (firstOffer.isTypeInOffer(NegotiationType.TRADE_ALLIANCE)) {
      return SpeechType.TRADE_ALLIANCE;
    }
    if (firstOffer.isTypeInOffer(NegotiationType.DEFENSIVE_PACT)) {
      return SpeechType.DEFESIVE_PACT;
    }
    if (firstOffer.getSize() > 0
        && (secondOffer == null || secondOffer.getSize() == 0)) {
      return SpeechType.DEMAND;
    }
    if (firstOffer.getSize() == 0
        && secondOffer != null
        && secondOffer.isTypeInOffer(NegotiationType.RECALL_FLEET)) {
      return SpeechType.ASK_MOVE_FLEET;
    }
    return SpeechType.TRADE;
  }

  /**
   * Get Best tech for trading
   * @param techList Tech list search for best tech
   * @param attitude AI attitude
   * @return Index for best tech
   */
  private static int getBestTech(final ArrayList<Tech> techList,
      final Attitude attitude) {
    int bestIndex = 0;
    for (int i = 0; i < techList.size(); i++) {
      Tech tech = techList.get(i);
      Tech currentBest = techList.get(bestIndex);
      if (tech.getLevel() > currentBest.getLevel()) {
        bestIndex = i;
      }
      if (tech.getLevel() == currentBest.getLevel()) {
        switch (attitude) {
          case AGGRESSIVE:
          case MILITARISTIC:
          case BACKSTABBING: {
            if (tech.getType() == TechType.Combat
                || tech.getType() == TechType.Hulls) {
              bestIndex = i;
            }
            break;
          }
          case EXPANSIONIST: {
            if (tech.getType() == TechType.Propulsion
                || tech.getType() == TechType.Hulls) {
              bestIndex = i;
            }
            break;
          }
          case MERCHANTICAL: {
            if (tech.getType() == TechType.Hulls) {
              bestIndex = i;
            }
            break;
          }
          case SCIENTIFIC: {
            if (tech.getType() == TechType.Electrics
                || tech.getType() == TechType.Improvements) {
              bestIndex = i;
            }
            break;
          }
          case LOGICAL: {
            if (tech.getType() == TechType.Defense) {
              bestIndex = i;
            }
            break;
          }
          case DIPLOMATIC:
          case PEACEFUL: {
            if (tech.getType() == TechType.Improvements
                || tech.getType() == TechType.Defense) {
              bestIndex = i;
            }
            break;
          }
          default: {
            break;
          }
        }
      }
    }
    return bestIndex;
  }

  /**
   * Minimum value of five. This method will take absolute
   * value and make sure that it is at least five.
   * @param value Value making sure that its minimum is 5.
   * @return Five or bigger number.
   */
  public static int  minFive(final int value) {
    int result = Math.abs(value);
    if (result < 5) {
      result = 5;
    }
    return result;
  }

  /**
   * Create promise vote yes offer. Requires that there is important vote.
   * @param agree PlayerInfo who makes the promise
   * @return NegotiationOffer or null
   */
  private NegotiationOffer createPromiseYes(final PlayerInfo agree) {
    Vote vote = starMap.getVotes().getNextImportantVote();
    if (vote != null) {
      int value = StarMapUtilities.getVotingSupport(agree, vote, starMap);
        if (value < 0) {
          value = minFive(value);
          value = value * 2;
          return new NegotiationOffer(NegotiationType.PROMISE_VOTE_YES,
              Integer.valueOf(value));
        } else if (value > 0) {
          value = minFive(value);
          return new NegotiationOffer(NegotiationType.PROMISE_VOTE_YES,
              Integer.valueOf(value));
        }
    }
    return null;
  }

  /**
   * Create promise vote no offer. Requires that there is important vote.
   * @param agree PlayerInfo who makes the promise
   * @return NegotiationOffer or null
   */
  private NegotiationOffer createPromiseNo(final PlayerInfo agree) {
    Vote vote = starMap.getVotes().getNextImportantVote();
    if (vote != null) {
      int value = StarMapUtilities.getVotingSupport(agree, vote, starMap);
        if (value < 0) {
          value = minFive(value);
          return new NegotiationOffer(NegotiationType.PROMISE_VOTE_NO,
              Integer.valueOf(value));
        } else if (value > 0) {
          value = minFive(value);
          value = value * 2;
          return new NegotiationOffer(NegotiationType.PROMISE_VOTE_NO,
              Integer.valueOf(value));
        }
    }
    return null;
  }

  /**
   * Create vote promise offer. Requires that there is important vote.
   * @param agree PlayerInfo who makes the promise
   * @return NegotiationOffer or null
   */
  private NegotiationOffer createBestVotePromise(final PlayerInfo agree) {
    NegotiationOffer voteNo = createPromiseNo(agree);
    NegotiationOffer voteYes = createPromiseYes(agree);
    if (voteNo != null && voteYes != null) {
      if (voteNo.getOfferValue(agree.getRace()) < voteYes.getOfferValue(
          agree.getRace())) {
        return voteNo;
      }
      return voteYes;
    }
    if (voteYes != null) {
      return voteYes;
    }
    if (voteNo != null) {
      return voteNo;
    }
    return null;
  }

  /**
   * Create vote demand offer. Requires that there is important vote.
   * @param demander who is demanding the vote
   * @param promiser who is promising to vote
   * @return NegotiationOffer or null
   */
  private NegotiationOffer createVoteDemand(final PlayerInfo demander,
      final PlayerInfo promiser) {
    Vote vote = starMap.getVotes().getNextImportantVote();
    if (vote != null) {
      int value = StarMapUtilities.getVotingSupport(demander, vote, starMap);
      if (value > 0) {
        return createPromiseYes(promiser);
      } else if (value < 0) {
        return createPromiseNo(promiser);
      } else {
        return null;
      }
    }
    return null;
  }

  /**
   * Generate Space pirate protection offer.
   */
  protected void generateProtectionOffer() {
    PlayerInfo offerMaker = starMap.getPlayerByIndex(first);
    PlayerInfo agree = starMap.getPlayerByIndex(second);
    if (offerMaker.getRace() == SpaceRace.SPACE_PIRATE) {
      if (techListForFirst.size() > 0) {
        firstOffer = new NegotiationList();
        int index = getBestTech(techListForFirst, offerMaker.getAiAttitude());
        firstOffer.add(new NegotiationOffer(NegotiationType.TECH,
            techListForFirst.get(index)));
        int value = firstOffer.getOfferValue(offerMaker.getRace());
        if (value < 15) {
          int credit = 15 - value;
          if (credit > agree.getTotalCredits()) {
            credit = agree.getTotalCredits();
          }
          firstOffer.add(new NegotiationOffer(NegotiationType.CREDIT, credit));
        }
        secondOffer = new NegotiationList();
        secondOffer.add(new NegotiationOffer(NegotiationType.ASK_PROTECTION,
            null));
      } else {
        int credit = 15;
        if (credit > agree.getTotalCredits()) {
          credit = agree.getTotalCredits();
        }
        firstOffer = new NegotiationList();
        firstOffer.add(new NegotiationOffer(NegotiationType.CREDIT, credit));
        secondOffer = new NegotiationList();
        secondOffer.add(new NegotiationOffer(NegotiationType.ASK_PROTECTION,
            null));
      }
    } else {
      if (techListForSecond.size() > 0) {
        secondOffer = new NegotiationList();
        int index = getBestTech(techListForSecond, agree.getAiAttitude());
        secondOffer.add(new NegotiationOffer(NegotiationType.TECH,
            techListForSecond.get(index)));
        int value = secondOffer.getOfferValue(agree.getRace());
        if (value < 15) {
          int credit = 15 - value;
          if (credit > offerMaker.getTotalCredits()) {
            credit = offerMaker.getTotalCredits();
          }
          secondOffer.add(new NegotiationOffer(NegotiationType.CREDIT, credit));
        }
        firstOffer = new NegotiationList();
        firstOffer.add(new NegotiationOffer(NegotiationType.ASK_PROTECTION,
            null));
      } else {
        int credit = 15;
        if (credit > offerMaker.getTotalCredits()) {
          credit = offerMaker.getTotalCredits();
        }
        secondOffer = new NegotiationList();
        secondOffer.add(new NegotiationOffer(NegotiationType.CREDIT, credit));
        firstOffer = new NegotiationList();
        firstOffer.add(new NegotiationOffer(NegotiationType.ASK_PROTECTION,
            null));
      }
    }
  }

  /**
   * Generate Tech trade between two players.
   * @param tradeType Choices are TRADE, BUY and SELL
   */
  protected void generateTechTrade(final int tradeType) {
    PlayerInfo offerMaker = starMap.getPlayerByIndex(first);
    PlayerInfo agree = starMap.getPlayerByIndex(second);
    if (techListForFirst.size() > 0
        && (tradeType == BUY || tradeType == TRADE)) {
      firstOffer = new NegotiationList();
      int index = getBestTech(techListForFirst, offerMaker.getAiAttitude());
      firstOffer.add(new NegotiationOffer(NegotiationType.TECH,
          techListForFirst.get(index)));
      int i = 0;
      secondOffer = new NegotiationList();
      do {
        if (tradeType == TRADE && techListForSecond.size() > i) {
          secondOffer.add(new NegotiationOffer(NegotiationType.TECH,
              techListForSecond.get(i)));
        } else {
          NegotiationOffer voteOffer = createBestVotePromise(offerMaker);
          int value = firstOffer.getOfferValue(offerMaker.getRace());
          if (voteOffer != null && voteOffer.getOfferValue(
              offerMaker.getRace()) >= value && !isDiplomacyWithPirates()) {
            secondOffer.add(voteOffer);
            value = value - voteOffer.getOfferValue(offerMaker.getRace());
          }
          if (value > 0) {
            int maxAmount = offerMaker.getArtifactLists()
                .getDiscoveredArtifacts().length;
            if (maxAmount > 0) {
              int amount = value / 5;
              if (amount == 0) {
                amount = 1;
              }
              secondOffer.add(new NegotiationOffer(
                  NegotiationType.DISCOVERED_ARTIFACT,
                  Integer.valueOf(amount)));
              value = value - amount * 5;
            }
          }
          if (offerMaker.getTotalCredits() < value) {
            value = offerMaker.getTotalCredits();
          }
          if (value > 0) {
            secondOffer.add(new NegotiationOffer(NegotiationType.CREDIT,
                Integer.valueOf(value)));
          }
          break;
        }
        i++;
      } while (firstOffer.getOfferValue(offerMaker.getRace())
          > secondOffer.getOfferValue(offerMaker.getRace()));
    }
    if (tradeType == SELL && techListForSecond.size() > 0) {
      int index = getBestTech(techListForSecond, agree.getAiAttitude());
      int value = techListForSecond.get(index).getLevel() * 2;
      if (agree.getTotalCredits() < value) {
        value = agree.getTotalCredits();
      }
      firstOffer = new NegotiationList();
      firstOffer.add(new NegotiationOffer(NegotiationType.CREDIT,
          Integer.valueOf(value)));
      secondOffer = new NegotiationList();
      secondOffer.add(new NegotiationOffer(NegotiationType.TECH,
          techListForSecond.get(index)));
    }
    if (tradeType == SELL && techListForSecond.size() == 0
        && offerMaker.getArtifactLists().hasDiscoveredArtifacts()) {
      int value = 5;
      if (agree.getTotalCredits() < value) {
        value = agree.getTotalCredits();
      }
      firstOffer = new NegotiationList();
      firstOffer.add(new NegotiationOffer(NegotiationType.CREDIT,
          Integer.valueOf(value)));
      secondOffer = new NegotiationList();
      secondOffer.add(new NegotiationOffer(NegotiationType.DISCOVERED_ARTIFACT,
          Integer.valueOf(1)));
    }
  }
  /**
   * Generate credit gift
   * @param giver player who is giving the gift
   */
  protected void generateGift(final PlayerInfo giver) {
    firstOffer = new NegotiationList();
    int credit = giver.getTotalCredits() / 2;
    if (credit > 15) {
      credit = 15;
    }
    if (credit > 0) {
      secondOffer = new NegotiationList();
      secondOffer.add(new NegotiationOffer(NegotiationType.CREDIT,
          Integer.valueOf(credit)));
    } else {
      int artifacts = giver.getArtifactLists().getDiscoveredArtifacts().length;
      if (artifacts > 0) {
        secondOffer = new NegotiationList();
        secondOffer.add(new NegotiationOffer(
            NegotiationType.DISCOVERED_ARTIFACT, Integer.valueOf(1)));
      }
    }
  }
  /**
   * Generate tech demand or artifact demand or money demand
   * @param agreePlayer Who is need to agree with trade
   * @param demander Realm who is making the demand
   */
  protected void generateTechDemand(final PlayerInfo agreePlayer,
      final PlayerInfo demander) {
    if (techListForFirst.size() > 0) {
      firstOffer = new NegotiationList();
      int index = getBestTech(techListForFirst, demander.getAiAttitude());
      firstOffer.add(new NegotiationOffer(NegotiationType.TECH,
          techListForFirst.get(index)));
    } else if (agreePlayer.getArtifactLists().hasDiscoveredArtifacts()) {
      int amount = agreePlayer.getArtifactLists()
          .getDiscoveredArtifacts().length;
      if (amount > 9) {
        amount = 3;
      }
      if (amount > 5) {
        amount = 2;
      }
      firstOffer = new NegotiationList();
      firstOffer.add(new NegotiationOffer(NegotiationType.DISCOVERED_ARTIFACT,
          Integer.valueOf(amount)));
    } else {
      int credit = agreePlayer.getTotalCredits();
      if (credit > 10) {
        credit = 10;
      }
      firstOffer = new NegotiationList();
      firstOffer.add(new NegotiationOffer(NegotiationType.CREDIT,
          Integer.valueOf(credit)));
    }
    secondOffer = new NegotiationList();
  }
  /**
   * Generate Map trade between two players.
   * @param tradeType Choices are TRADE, BUY and SELL
   * @param fullMap Full map trade if true, otherwise just the planet sectors.
   */
  protected void generateMapTrade(final int tradeType, final boolean fullMap) {
    PlayerInfo offerMaker = starMap.getPlayerByIndex(first);
    PlayerInfo agree = starMap.getPlayerByIndex(second);
    NegotiationType mapType = NegotiationType.MAP;
    if (!fullMap) {
      mapType = NegotiationType.MAP_PLANETS;
    }
    if (tradeType == BUY) {
      NegotiationOffer offer = new NegotiationOffer(mapType, null);
      int value = calculateMapValue(offerMaker, agree, fullMap);
      offer.setMapValue(value);
      value = offer.getMapValue();
      firstOffer = new NegotiationList();
      secondOffer = new NegotiationList();
      NegotiationOffer voteOffer = createBestVotePromise(offerMaker);
      if (voteOffer != null && !isDiplomacyWithPirates()) {
        secondOffer.add(voteOffer);
        value = value - voteOffer.getOfferValue(offerMaker.getRace());
      }
      if (offerMaker.getTotalCredits() < value) {
        value = offerMaker.getTotalCredits();
      }
      firstOffer.add(offer);
      if (value > 0) {
        secondOffer.add(new NegotiationOffer(NegotiationType.CREDIT,
            Integer.valueOf(value)));
      }
    } else if (tradeType == TRADE) {
      firstOffer = new NegotiationList();
      NegotiationOffer offer = new NegotiationOffer(mapType, null);
      offer.setMapValue(calculateMapValue(offerMaker, agree, fullMap));
      firstOffer.add(offer);
      secondOffer = new NegotiationList();
      offer = new NegotiationOffer(mapType, null);
      offer.setMapValue(calculateMapValue(agree, offerMaker, fullMap));
      secondOffer.add(offer);
    } else {
      int value = calculateMapValue(agree, offerMaker, fullMap);
      NegotiationOffer offer = new NegotiationOffer(mapType, null);
      offer.setMapValue(value);
      value = offer.getMapValue();
      if (agree.getTotalCredits() < value) {
        value = agree.getTotalCredits();
      }
      firstOffer = new NegotiationList();
      firstOffer.add(new NegotiationOffer(NegotiationType.CREDIT,
          Integer.valueOf(value)));
      secondOffer = new NegotiationList();
      secondOffer.add(offer);
    }
  }

  /**
   * Generate equal trade between two players.
   * @param type Negotiation type, can be ALLIANCE, TRADE_ALLIANCE, WAR,
   *              PEACE or DEFENSIVE_PACT
   */
  public void generateEqualTrade(final NegotiationType type) {
    if (type == NegotiationType.ALLIANCE
        || type == NegotiationType.TRADE_ALLIANCE
        || type == NegotiationType.WAR
        || type == NegotiationType.PEACE
        || type == NegotiationType.DEFENSIVE_PACT) {
      firstOffer = new NegotiationList();
      firstOffer.add(new NegotiationOffer(type, null));
      secondOffer = new NegotiationList();
      secondOffer.add(new NegotiationOffer(type, null));
    }
  }

  /**
   * Generate fleet sell offer. Generates new offers for selling fleet.
   * @param seller Realm who is selling
   * @param buyer Realm who is buying
   * @param fleets All sellable fleets for seller
   * @return True if offer was made
   */
  public boolean generateFleetSellOffer(final PlayerInfo seller,
      final PlayerInfo buyer, final ArrayList<Fleet> fleets) {
    if (fleetListForFirst == null || fleetListForSecond == null) {
      generateFleetList();
    }
    Fleet fleet = getTradeableFleet(seller, fleetListForFirst);
    if (fleet != null
        && seller.getFleets().getTotalFleetCapacity()
        > starMap.getTotalFleetCapacity(seller)) {
      int originalValue = fleet.getMilitaryValue() / 2;
      int credits = buyer.getTotalCredits();
      int value = originalValue;
      if (value > credits) {
        value = credits;
      }
      if (value > 0 && value > originalValue / 2) {
        firstOffer = new NegotiationList();
        firstOffer.add(new NegotiationOffer(NegotiationType.CREDIT,
            Integer.valueOf(value)));
        secondOffer = new NegotiationList();
        secondOffer.add(new NegotiationOffer(NegotiationType.FLEET,
            fleet));
        return true;
      }
    }
    return false;
  }
  /**
   * Generate equal trade between two players.
   * @param realm Realm who to cause trade embargo
   */
  public void generateTradeEmbargoOffer(final PlayerInfo realm) {
    firstOffer = new NegotiationList();
    firstOffer.add(new NegotiationOffer(NegotiationType.TRADE_EMBARGO,
        realm));
    secondOffer = new NegotiationList();
    secondOffer.add(new NegotiationOffer(NegotiationType.TRADE_EMBARGO,
        realm));
  }

  /**
   * Generate Recall fleet offer. Second player should
   * recall his or her fleet.
   * @param fleet Fleet which to recall
   */
  public void generateRecallFleetOffer(final Fleet fleet) {
    firstOffer = new NegotiationList();
    secondOffer = new NegotiationList();
    secondOffer.add(new NegotiationOffer(NegotiationType.RECALL_FLEET, fleet));
  }

  /**
   * Get best fleet to trade. Fleet has biggest obsolete value
   * and then minimum military value.
   * @param info Realm who owns the fleets
   * @param fleets ArrayList of fleets
   * @return Fleet or null
   */
  public Fleet getTradeableFleet(final PlayerInfo info,
      final ArrayList<Fleet> fleets) {
    Fleet bestFleetForTrade = null;
    int bestObsoleteValue = 0;
    for (Fleet fleet : fleets) {
      if (bestFleetForTrade != null) {
        int value = fleet.calculateFleetObsoleteValue(info);
        if (fleet.getMilitaryValue() > 0 && value > bestObsoleteValue) {
          bestFleetForTrade = fleet;
          bestObsoleteValue = value;
        } else if (fleet.getMilitaryValue()
            < bestFleetForTrade.getMilitaryValue()
            && value == bestObsoleteValue) {
          bestFleetForTrade = fleet;
          bestObsoleteValue = value;
        }
      } else {
        int value = fleet.calculateFleetObsoleteValue(info);
        if (fleet.getMilitaryValue() > 0 && value > 0) {
          bestFleetForTrade = fleet;
          bestObsoleteValue = value;
        }
      }
    }
    return bestFleetForTrade;
  }

  /**
   * Calculate planet value for demander.
   * @param demander PlayerInfo
   * @param planet PLanet to calculate
   * @return Planet value.
   */
  private static int calculatePlanetValue(final PlayerInfo demander,
      final Planet planet) {
    int result = 0;
    result = result + planet.getAmountMetalInGround() / 1000;
    result = result + planet.getGroundSize() - 7;
    result = result + planet.getTotalPopulation() / 3;
    if (planet.getHomeWorldIndex() != -1) {
      result = result + 3;
    }
    if (planet.getRadiationLevel() > demander.getRace().getMaxRad()) {
      result = 0;
    }
    return result;
  }

  /**
   * Get best tradeable planet.
   * @param demander Realm who demands planet.
   *        If null owner is trying to offer planet.
   * @param planets ArrayList of planet
   * @return Planet or null
   */
  public Planet getTradeablePlanet(final PlayerInfo demander,
      final ArrayList<Planet> planets) {
    Planet bestPlanet = null;
    int value = 0;
    PlayerInfo info = demander;
    boolean biggest = true;
    if (planets.isEmpty()
        || planets.get(0).getPlanetPlayerInfo() == null) {
      return null;
    }
    if (demander == null) {
      info = planets.get(0).getPlanetPlayerInfo();
      biggest = false;
      value = 9999;
    }
    for (Planet planet : planets) {
      if (biggest) {
        if (calculatePlanetValue(info, planet) > value) {
          bestPlanet = planet;
          value = calculatePlanetValue(info, bestPlanet);
        }
      } else {
        if (calculatePlanetValue(info, planet) < value) {
          bestPlanet = planet;
          value = calculatePlanetValue(info, bestPlanet);
        }
      }
    }
    return bestPlanet;
  }
  /**
   * Generate peace offer depending on military power difference
   * and war fatigue
   */
  public void generatePeaceOffer() {
    PlayerInfo info = starMap.getPlayerByIndex(first);
    PlayerInfo info2 = starMap.getPlayerByIndex(second);
    Attitude attitude = info.getAiAttitude();
    if (fleetListForFirst == null || fleetListForSecond == null) {
      generateFleetList();
    }
    if (planetListForFirst == null || planetListForSecond == null) {
      generatePlanetList();
    }
    int lowCredit = 0;
    int mediumCredit = 0;
    int highCredit = 0;
    int credit = 0;
    NegotiationOffer lowDemand = null;
    NegotiationOffer mediumDemand = null;
    NegotiationOffer highDemand = null;
    NegotiationOffer lowGift = null;
    NegotiationOffer mediumGift = null;
    NegotiationOffer highGift = null;
    int powerDiff = 30;
    lowDemand = createVoteDemand(info, info2);
    if (lowDemand == null && techListForFirst.size() > 0) {
      int index = getBestTech(techListForFirst, attitude);
      lowDemand = new NegotiationOffer(NegotiationType.TECH,
          techListForFirst.get(index));
    }
    if (lowDemand == null
        && info2.getArtifactLists().hasDiscoveredArtifacts()) {
      lowDemand = new NegotiationOffer(NegotiationType.DISCOVERED_ARTIFACT,
          Integer.valueOf(1));
    }
    Fleet fleet = getTradeableFleet(info2, fleetListForSecond);
    if (fleet != null) {
      mediumDemand = new NegotiationOffer(NegotiationType.FLEET, fleet);
    }
    Planet planet = getTradeablePlanet(info, planetListForSecond);
    if (planet != null) {
      highDemand = new NegotiationOffer(NegotiationType.PLANET, planet);
    }
    planet = getTradeablePlanet(null, planetListForFirst);
    if (planet != null) {
      highGift = new NegotiationOffer(NegotiationType.PLANET,
          planet);
    }
    fleet = getTradeableFleet(info, fleetListForFirst);
    if (fleet != null) {
      mediumGift = new NegotiationOffer(NegotiationType.FLEET, fleet);
    }
    lowGift = createVoteDemand(info2, info);
    if (lowGift == null && techListForSecond.size() > 0) {
      int index = getBestTech(techListForSecond, attitude);
      lowGift = new NegotiationOffer(NegotiationType.TECH,
        techListForSecond.get(index));
    }
    switch (attitude) {
      case AGGRESSIVE: {
        lowCredit = 8;
        mediumCredit = 10;
        highCredit = 12;
        powerDiff = -30;
        break;
      }
      case MILITARISTIC: {
        lowCredit = 6;
        mediumCredit = 8;
        highCredit = 10;
        powerDiff = -20;
        break;
      }
      case BACKSTABBING: {
        lowCredit = 6;
        mediumCredit = 8;
        highCredit = 10;
        powerDiff = -10;
        break;
      }
      case DIPLOMATIC:
      case PEACEFUL: {
        lowCredit = 4;
        mediumCredit = 6;
        highCredit = 8;
        powerDiff = 30;
        break;
      }
      case MERCHANTICAL: {
        lowCredit = 7;
        mediumCredit = 9;
        highCredit = 11;
        powerDiff = 20;
        break;
      }
      case EXPANSIONIST: {
        lowCredit = 5;
        mediumCredit = 7;
        highCredit = 9;
        powerDiff = 10;
        break;
      }
      case LOGICAL:
      case SCIENTIFIC:
      default: {
        lowCredit = 5;
        mediumCredit = 7;
        highCredit = 9;
        powerDiff = 0;
        break;
      }
    }
    int power = starMap.getMilitaryDifference(first,
        second);
    int fatigue = info.getTotalWarFatigue();
    generateEqualTrade(NegotiationType.PEACE);
    if (power > 150 + powerDiff) {
      if (fatigue > -3) {
        if (lowDemand != null) {
          firstOffer.add(lowDemand);
        } else {
          credit = credit + lowCredit;
        }
      }
      if (fatigue > -2) {
        if (mediumDemand != null) {
          firstOffer.add(mediumDemand);
        } else {
          credit = credit + mediumCredit;
        }
      }
      if (fatigue == 0) {
        if (highDemand != null) {
          firstOffer.add(highDemand);
        } else {
          credit = credit + highCredit;
        }
      }
      if (credit > 0) {
        if (credit > info2.getTotalCredits()) {
          credit = info2.getTotalCredits();
        }
        firstOffer.add(new NegotiationOffer(NegotiationType.CREDIT,
            Integer.valueOf(credit)));
      }
    } else if (power > 50 + powerDiff) {
      if (fatigue > -2 && techListForFirst.size() > 0) {
        if (lowDemand != null) {
          firstOffer.add(lowDemand);
        } else {
          credit = credit + lowCredit;
        }
      }
      if (fatigue == 0) {
        if (mediumDemand != null) {
          firstOffer.add(mediumDemand);
        } else {
          credit = credit + mediumCredit;
        }
      }
      if (credit > 0) {
        if (credit > info2.getTotalCredits()) {
          credit = info2.getTotalCredits();
        }
        firstOffer.add(new NegotiationOffer(NegotiationType.CREDIT,
            Integer.valueOf(credit)));
      }
    } else if (power > -50 + powerDiff) {
      generateEqualTrade(NegotiationType.PEACE);
    }  else if (power < -150 + powerDiff) {
      if (fatigue < -3) {
        if (highGift != null) {
          secondOffer.add(highGift);
        } else {
          credit = credit + highCredit;
        }
      }
      if (fatigue == -2) {
        if (mediumGift != null) {
          secondOffer.add(mediumGift);
        } else {
          credit = credit + mediumCredit;
        }
      }
      if (fatigue == -1 && techListForSecond.size() > 0) {
        if (lowGift != null) {
          secondOffer.add(lowGift);
        } else {
          credit = credit + lowCredit;
        }
      }
      if (credit > 0) {
        if (credit > info.getTotalCredits()) {
          credit = info.getTotalCredits();
        }
        secondOffer.add(new NegotiationOffer(NegotiationType.CREDIT,
            Integer.valueOf(credit)));
      }
    } else {
      if (fatigue <= -2) {
        if (mediumGift != null) {
          secondOffer.add(mediumGift);
        } else {
          credit = credit + mediumCredit;
        }
      }
      if (fatigue == -1 && techListForSecond.size() > 0) {
        if (lowGift != null) {
          secondOffer.add(lowGift);
        } else {
          credit = credit + lowCredit;
        }
      }
      if (credit > 0) {
        if (credit > info.getTotalCredits()) {
          credit = info.getTotalCredits();
        }
        secondOffer.add(new NegotiationOffer(NegotiationType.CREDIT,
            Integer.valueOf(credit)));
      }
    }
  }
  /**
   * Generate first offer depending on AI's attitude.
   */
  public void generateFirstOffer() {
    PlayerInfo info = starMap.getPlayerByIndex(first);
    Attitude attitude = info.getAiAttitude();
    boolean hate = false;
    boolean casusBelli = info.getDiplomacy().hasCasusBelli(second);
    if (info.getDiplomacy().getLikingAsString(second).contains("Hate")) {
      hate = true;
    }
    switch (attitude) {
      case AGGRESSIVE: {
        int power = starMap.getMilitaryDifference(first,
            second);
        if (power > 30 || hate) {
          generateEqualTrade(NegotiationType.WAR);
        } else {
          generateEqualTrade(NegotiationType.PEACE);
        }
        break;
      }
      case MILITARISTIC: {
        int power = starMap.getMilitaryDifference(first,
            second);
        if (power > 60 || hate) {
          generateEqualTrade(NegotiationType.WAR);
        } else {
          generateEqualTrade(NegotiationType.PEACE);
        }
        break;
      }
      case EXPANSIONIST: {
        if (hate) {
          generateEqualTrade(NegotiationType.WAR);
        } else {
          generateEqualTrade(NegotiationType.PEACE);
          PlayerInfo info2 = starMap.getPlayerByIndex(second);
          NegotiationOffer offer = new NegotiationOffer(NegotiationType.MAP,
              null);
          offer.setMapValue(calculateMapValue(info, info2, true));
          firstOffer.add(offer);
          offer = new NegotiationOffer(NegotiationType.MAP, null);
          offer.setMapValue(calculateMapValue(info2, info, true));
          secondOffer.add(offer);
        }
        break;
      }
      case MERCHANTICAL: {
        if (hate && casusBelli) {
          generateEqualTrade(NegotiationType.WAR);
        } else {
          generateEqualTrade(NegotiationType.PEACE);
          PlayerInfo info2 = starMap.getPlayerByIndex(second);
          NegotiationOffer offer = new NegotiationOffer(NegotiationType.MAP,
              null);
          offer.setMapValue(calculateMapValue(info, info2, true));
          firstOffer.add(offer);
          offer = new NegotiationOffer(NegotiationType.MAP, null);
          offer.setMapValue(calculateMapValue(info2, info, true));
          secondOffer.add(offer);
        }
        break;
      }
      case BACKSTABBING: {
        if (hate || casusBelli) {
          generateEqualTrade(NegotiationType.WAR);
        } else {
          generateEqualTrade(NegotiationType.PEACE);
        }
        break;
      }
      case LOGICAL:
      case SCIENTIFIC: {
        if (hate && casusBelli) {
          generateEqualTrade(NegotiationType.WAR);
        } else {
          generateEqualTrade(NegotiationType.PEACE);
        }
        break;
      }
      case DIPLOMATIC:
      case PEACEFUL:
      default: {
        generateEqualTrade(NegotiationType.PEACE);
        break;
      }
    }
  }

  /**
   * Check if trade embargo is about to be possible
   * @return Realm index with embargo is possible, -1 if not.
   */
  private int getPossibleTradeEmbargo() {
    PlayerInfo info = starMap.getPlayerByIndex(first);
    PlayerInfo info2 = starMap.getPlayerByIndex(second);
    int leastLiked = info.getDiplomacy().getLeastLiking();
    if (leastLiked != second
        && info.getDiplomacy().getDiplomacyList(leastLiked) != null
        && info.getDiplomacy().getDiplomacyList(leastLiked)
           .getNumberOfMeetings() > 0
        && !info2.getDiplomacy().isTradeEmbargo(leastLiked)) {
      return leastLiked;
    }
    return -1;
  }
  /**
   * Offer by aggressive attitude.
   * This makes war quite easily and can make demands.
   */
  public void generateAggressiveAttitudeOffer() {
    PlayerInfo info = starMap.getPlayerByIndex(first);
    PlayerInfo agree = starMap.getPlayerByIndex(second);
    int power = starMap.getMilitaryDifference(first,
        second);
    boolean casusBelli = info.getDiplomacy().hasCasusBelli(second);
    if (power > 0 && casusBelli) {
      power = power + 20;
    }
    if (info.getDiplomacy().countBorderCrossing(second) > 6) {
      power = power + BORDER_CROSSING_POWER * info.getAiDifficulty().getIndex();
    }
    if (power > 40 && info.getDiplomacy().getDiplomaticRelation(second)
        .isEmpty()) {
      generateEqualTrade(NegotiationType.WAR);
      return;
    }
    if (power > 100 && info.getDiplomacy().getDiplomaticRelation(second)
        .equals(Diplomacy.TRADE_ALLIANCE)) {
      generateEqualTrade(NegotiationType.WAR);
      return;
    }
    if (power > 200) {
      generateEqualTrade(NegotiationType.WAR);
      return;
    }
    if (info.getDiplomacy().getLiking(second) == Diplomacy.LIKE) {
      if (DiceGenerator.getRandom(100) < 10
          && info.getDiplomacy().getDiplomaticRelation(second)
          .equals(Diplomacy.PEACE)) {
        generateEqualTrade(NegotiationType.TRADE_ALLIANCE);
        return;
      }
    } else if (info.getDiplomacy().getLiking(second) == Diplomacy.FRIENDS) {
      int value = DiceGenerator.getRandom(100);
      if (value < 10) {
        if (info.getDiplomacy().getDiplomaticRelation(second)
            .equals(Diplomacy.DEFENSIVE_PACT)
            && !info.getDiplomacy().hasAlliance()
            && !agree.getDiplomacy().hasAlliance()) {
          generateEqualTrade(NegotiationType.ALLIANCE);
          return;
        }
        if (info.getDiplomacy().getDiplomaticRelation(second)
            .equals(Diplomacy.TRADE_ALLIANCE)) {
          generateEqualTrade(NegotiationType.DEFENSIVE_PACT);
          return;
        }
        if (info.getDiplomacy().getDiplomaticRelation(second)
            .equals(Diplomacy.PEACE)) {
          generateEqualTrade(NegotiationType.TRADE_ALLIANCE);
          return;
        }
      }
      if (value < 25 && info.getDiplomacy().getDiplomaticRelation(second)
            .equals(Diplomacy.TRADE_ALLIANCE)
            && !info.getDiplomacy().getDiplomaticRelation(second)
            .equals(Diplomacy.ALLIANCE)
            && info.getEspionage().isSpyTradePossible()) {
        generateEqualTrade(NegotiationType.SPY_TRADE);
      }
    }
    int value = DiceGenerator.getRandom(100);
    int embargoIndex = getPossibleTradeEmbargo();
    if (value < 25) {
      if (info.getDiplomacy().isDefensivePact(second)
        || info.getDiplomacy().isAlliance(second)) {
        generateMapTrade(TRADE, true);
      } else {
        generateMapTrade(TRADE, false);
      }
    } else if (value < 50) {
      if (!generateFleetSellOffer(info, agree, fleetListForFirst)) {
        generateTechTrade(TRADE);
      }
    } else {
      if (embargoIndex != -1 && DiceGenerator.getRandom(100) < 50) {
        PlayerInfo embagoed = starMap.getPlayerByIndex(embargoIndex);
        generateTradeEmbargoOffer(embagoed);
      } else {
        generateTechDemand(agree, info);
      }
    }
  }

  /**
   * Offer by backstabbing attitude.
   * This makes war and demands now and then...
   */
  public void generateBackstabbingAttitudeOffer() {
    PlayerInfo info = starMap.getPlayerByIndex(first);
    PlayerInfo agree = starMap.getPlayerByIndex(second);
    int power = starMap.getMilitaryDifference(first,
        second);
    boolean casusBelli = info.getDiplomacy().hasCasusBelli(second);
    if (power > 0 && casusBelli) {
      power = power + 20;
    }
    if (info.getDiplomacy().countBorderCrossing(second) > 6) {
      power = power + BORDER_CROSSING_POWER * info.getAiDifficulty().getIndex();
    }
    if (power > 80 && info.getDiplomacy().getDiplomaticRelation(second)
        .isEmpty()) {
      generateEqualTrade(NegotiationType.WAR);
      return;
    }
    if (power > 200 && info.getDiplomacy().getDiplomaticRelation(second)
        .equals(Diplomacy.TRADE_ALLIANCE)) {
      generateEqualTrade(NegotiationType.WAR);
      return;
    }
    if (power > 300) {
      generateEqualTrade(NegotiationType.WAR);
      return;
    }
    if (info.getDiplomacy().getLiking(second) == Diplomacy.LIKE) {
      if (DiceGenerator.getRandom(100) < 25
          && info.getDiplomacy().getDiplomaticRelation(second)
          .equals(Diplomacy.PEACE)) {
        generateEqualTrade(NegotiationType.TRADE_ALLIANCE);
        return;
      }
    } else if (info.getDiplomacy().getLiking(second) == Diplomacy.FRIENDS) {
      int value = DiceGenerator.getRandom(100);
      if (value < 25) {
        if (info.getDiplomacy().getDiplomaticRelation(second)
            .equals(Diplomacy.DEFENSIVE_PACT)
            && !info.getDiplomacy().hasAlliance()
            && !agree.getDiplomacy().hasAlliance()) {
          generateEqualTrade(NegotiationType.ALLIANCE);
          return;
        }
        if (info.getDiplomacy().getDiplomaticRelation(second)
            .equals(Diplomacy.TRADE_ALLIANCE)) {
          generateEqualTrade(NegotiationType.DEFENSIVE_PACT);
          return;
        }
        if (info.getDiplomacy().getDiplomaticRelation(second)
          .equals(Diplomacy.PEACE)) {
          generateEqualTrade(NegotiationType.TRADE_ALLIANCE);
          return;
        }
      }
      if (value < 30 && info.getDiplomacy().getDiplomaticRelation(second)
          .equals(Diplomacy.TRADE_ALLIANCE)
          && !info.getDiplomacy().getDiplomaticRelation(second)
          .equals(Diplomacy.ALLIANCE)
          && info.getEspionage().isSpyTradePossible()) {
        generateEqualTrade(NegotiationType.SPY_TRADE);
      }
    }
    if (info.getDiplomacy().getDiplomaticRelation(second)
        .equals(Diplomacy.TRADE_ALLIANCE)
        || info.getDiplomacy().getDiplomaticRelation(second)
        .equals(Diplomacy.DEFENSIVE_PACT)
        || info.getDiplomacy().getDiplomaticRelation(second)
        .equals(Diplomacy.ALLIANCE)) {
      int value = DiceGenerator.getRandom(100);
      if (value < 5) {
        generateEqualTrade(NegotiationType.WAR);
      } else if (value < 10) {
        generateTechDemand(agree, info);
      }
    }
    int value = DiceGenerator.getRandom(100);
    int embargoIndex = getPossibleTradeEmbargo();
    if (value < 30) {
      if (info.getDiplomacy().isDefensivePact(second)
          || info.getDiplomacy().isAlliance(second)) {
          generateMapTrade(TRADE, true);
        } else {
          generateMapTrade(TRADE, false);
        }
    } else if (value < 80) {
      if (!generateFleetSellOffer(info, agree, fleetListForFirst)) {
        generateTechTrade(TRADE);
      }
    } else {
      if (embargoIndex != -1 && DiceGenerator.getRandom(100) < 50) {
        PlayerInfo embagoed = starMap.getPlayerByIndex(embargoIndex);
        generateTradeEmbargoOffer(embagoed);
      } else {
        generateTechDemand(agree, info);
      }
    }
  }
  /**
   * Generate diplomatic trade offer between two players
   */

  /**
   * Offer by diplomatic attitude.
   * This makes war only which it does not like. Might
   * give a gift too sometimes.
   */
  public void generateDiplomaticAttitudeOffer() {
    PlayerInfo info = starMap.getPlayerByIndex(first);
    PlayerInfo agree = starMap.getPlayerByIndex(second);
    int power = starMap.getMilitaryDifference(first,
        second);
    boolean casusBelli = info.getDiplomacy().hasCasusBelli(second);
    if (info.getDiplomacy().countBorderCrossing(second) > 6) {
      power = power + BORDER_CROSSING_POWER * info.getAiDifficulty().getIndex();
    }
    if (power > 80 && info.getDiplomacy().getDiplomaticRelation(second)
        .isEmpty() && casusBelli) {
      generateEqualTrade(NegotiationType.WAR);
      return;
    }
    if (info.getDiplomacy().getLiking(second) == Diplomacy.LIKE) {
      if (DiceGenerator.getRandom(100) < 50
          && info.getDiplomacy().getDiplomaticRelation(second)
          .equals(Diplomacy.PEACE)) {
        generateEqualTrade(NegotiationType.TRADE_ALLIANCE);
        return;
      }
    } else if (info.getDiplomacy().getLiking(second) == Diplomacy.FRIENDS) {
      int value = DiceGenerator.getRandom(100);
      if (value < 50) {
        if (info.getDiplomacy().getDiplomaticRelation(second)
            .equals(Diplomacy.DEFENSIVE_PACT)
            && !info.getDiplomacy().hasAlliance()
            && !agree.getDiplomacy().hasAlliance()) {
          generateEqualTrade(NegotiationType.ALLIANCE);
          return;
        }
        if (info.getDiplomacy().getDiplomaticRelation(second)
            .equals(Diplomacy.TRADE_ALLIANCE)) {
          generateEqualTrade(NegotiationType.DEFENSIVE_PACT);
          return;
        }
        if (info.getDiplomacy().getDiplomaticRelation(second)
          .equals(Diplomacy.PEACE)) {
          generateEqualTrade(NegotiationType.TRADE_ALLIANCE);
          return;
        }
      }
      if (value < 60 && info.getDiplomacy().getDiplomaticRelation(second)
          .equals(Diplomacy.TRADE_ALLIANCE)
          && !info.getDiplomacy().getDiplomaticRelation(second)
          .equals(Diplomacy.ALLIANCE)
          && info.getEspionage().isSpyTradePossible()) {
        generateEqualTrade(NegotiationType.SPY_TRADE);
      }
    }
    int value = DiceGenerator.getRandom(100);
    int embargoIndex = getPossibleTradeEmbargo();
    if (value < 45) {
      if (embargoIndex != -1 && DiceGenerator.getRandom(100) < 50) {
        PlayerInfo embagoed = starMap.getPlayerByIndex(embargoIndex);
        generateTradeEmbargoOffer(embagoed);
      } else {
          if (info.getDiplomacy().isDefensivePact(second)
            || info.getDiplomacy().isAlliance(second)) {
            generateMapTrade(TRADE, true);
          } else {
            generateMapTrade(TRADE, false);
          }
      }
    } else if (value < 90) {
      if (!generateFleetSellOffer(info, agree, fleetListForFirst)) {
        generateTechTrade(TRADE);
      }
    } else {
      generateGift(info);
    }
  }

  /**
   * Offer by expansionist attitude.
   * Tries to focus trading or buying the map
   */
  public void generateExpansionistAttitudeOffer() {
    PlayerInfo info = starMap.getPlayerByIndex(first);
    PlayerInfo agree = starMap.getPlayerByIndex(second);
    int power = starMap.getMilitaryDifference(first,
        second);
    boolean casusBelli = info.getDiplomacy().hasCasusBelli(second);
    if (power > 0 && casusBelli) {
      power = power + 10;
    }
    if (info.getDiplomacy().countBorderCrossing(second) > 6) {
      power = power + BORDER_CROSSING_POWER * info.getAiDifficulty().getIndex();
    }
    if (power > 60 && info.getDiplomacy().getDiplomaticRelation(second)
        .isEmpty()) {
      generateEqualTrade(NegotiationType.WAR);
      return;
    }
    if (power > 150 && info.getDiplomacy().getDiplomaticRelation(second)
        .equals(Diplomacy.TRADE_ALLIANCE)) {
      generateEqualTrade(NegotiationType.WAR);
      return;
    }
    if (power > 300) {
      generateEqualTrade(NegotiationType.WAR);
      return;
    }
    if (info.getDiplomacy().getLiking(second) == Diplomacy.LIKE) {
      if (DiceGenerator.getRandom(100) < 25
          && info.getDiplomacy().getDiplomaticRelation(second)
          .equals(Diplomacy.PEACE)) {
        generateEqualTrade(NegotiationType.TRADE_ALLIANCE);
        return;
      }
    } else if (info.getDiplomacy().getLiking(second) == Diplomacy.FRIENDS) {
      int value = DiceGenerator.getRandom(100);
      if (value < 25) {
        if (info.getDiplomacy().getDiplomaticRelation(second)
            .equals(Diplomacy.DEFENSIVE_PACT)
            && !info.getDiplomacy().hasAlliance()
            && !agree.getDiplomacy().hasAlliance()) {
          generateEqualTrade(NegotiationType.ALLIANCE);
          return;
        }
        if (info.getDiplomacy().getDiplomaticRelation(second)
            .equals(Diplomacy.TRADE_ALLIANCE)) {
          generateEqualTrade(NegotiationType.DEFENSIVE_PACT);
          return;
        }
        if (info.getDiplomacy().getDiplomaticRelation(second)
          .equals(Diplomacy.PEACE)) {
          generateEqualTrade(NegotiationType.TRADE_ALLIANCE);
          return;
        }
      }
      if (value < 25 && info.getDiplomacy().getDiplomaticRelation(second)
          .equals(Diplomacy.TRADE_ALLIANCE)
          && !info.getDiplomacy().getDiplomaticRelation(second)
          .equals(Diplomacy.ALLIANCE)
          && info.getEspionage().isSpyTradePossible()) {
        generateEqualTrade(NegotiationType.SPY_TRADE);
      }
    }
    int value = DiceGenerator.getRandom(100);
    int embargoIndex = getPossibleTradeEmbargo();
    if (value < 30) {
      if (info.getDiplomacy().isDefensivePact(second)
          || info.getDiplomacy().isAlliance(second)) {
          generateMapTrade(TRADE, true);
        } else {
          generateMapTrade(TRADE, false);
        }
    } else if (value < 60) {
      generateMapTrade(BUY, true);
    } else {
      if (embargoIndex != -1 && DiceGenerator.getRandom(100) < 50) {
        PlayerInfo embagoed = starMap.getPlayerByIndex(embargoIndex);
        generateTradeEmbargoOffer(embagoed);
      } else {
        if (!generateFleetSellOffer(info, agree, fleetListForFirst)) {
          generateTechTrade(TRADE);
        }
      }
    }
  }

  /**
   * Offer for space pirate.
   * Tries to trade/buy tech or buy maps or sell ships
   */
  public void generatePirateOffer() {
    PlayerInfo info = starMap.getPlayerByIndex(first);
    PlayerInfo agree = starMap.getPlayerByIndex(second);
    int value = DiceGenerator.getRandom(100);
    if (value < 30) {
      if (!generateFleetSellOffer(info, agree, fleetListForFirst)) {
        generateTechTrade(TRADE);
      }
    } else if (value < 50) {
      generateProtectionOffer();
    } else if (value < 70) {
      generateTechTrade(BUY);
    } else if (value < 80) {
      generateTechTrade(SELL);
    } else {
      if (DiceGenerator.getRandom(1) == 0) {
        generateMapTrade(TRADE, true);
      } else {
        generateMapTrade(BUY, true);
      }
    }
  }
  /**
   * Offer by scientific attitude.
   * Tries to focus trading or buying the tech
   */
  public void generateScientificAttitudeOffer() {
    PlayerInfo info = starMap.getPlayerByIndex(first);
    PlayerInfo agree = starMap.getPlayerByIndex(second);
    int power = starMap.getMilitaryDifference(first,
        second);
    boolean casusBelli = info.getDiplomacy().hasCasusBelli(second);
    if (power > 0 && casusBelli) {
      power = power + 20;
    }
    if (info.getDiplomacy().countBorderCrossing(second) > 6) {
      power = power + BORDER_CROSSING_POWER * info.getAiDifficulty().getIndex();
    }
    if (power > 100 && info.getDiplomacy().getDiplomaticRelation(second)
        .isEmpty()) {
      generateEqualTrade(NegotiationType.WAR);
      return;
    }
    if (power > 200 && info.getDiplomacy().getDiplomaticRelation(second)
        .equals(Diplomacy.TRADE_ALLIANCE)) {
      generateEqualTrade(NegotiationType.WAR);
      return;
    }
    if (power > 400) {
      generateEqualTrade(NegotiationType.WAR);
      return;
    }
    if (info.getDiplomacy().getLiking(second) == Diplomacy.LIKE) {
      if (DiceGenerator.getRandom(100) < 40
          && info.getDiplomacy().getDiplomaticRelation(second)
          .equals(Diplomacy.PEACE)) {
        generateEqualTrade(NegotiationType.TRADE_ALLIANCE);
        return;
      }
    } else if (info.getDiplomacy().getLiking(second) == Diplomacy.FRIENDS) {
      int value = DiceGenerator.getRandom(100);
      if (value < 40) {
        if (info.getDiplomacy().getDiplomaticRelation(second)
            .equals(Diplomacy.DEFENSIVE_PACT)
            && !info.getDiplomacy().hasAlliance()
            && !agree.getDiplomacy().hasAlliance()) {
          generateEqualTrade(NegotiationType.ALLIANCE);
          return;
        }
        if (info.getDiplomacy().getDiplomaticRelation(second)
            .equals(Diplomacy.TRADE_ALLIANCE)) {
          generateEqualTrade(NegotiationType.DEFENSIVE_PACT);
          return;
        }
        if (info.getDiplomacy().getDiplomaticRelation(second)
          .equals(Diplomacy.PEACE)) {
          generateEqualTrade(NegotiationType.TRADE_ALLIANCE);
          return;
        }
      }
      if (value < 50 && info.getDiplomacy().getDiplomaticRelation(second)
          .equals(Diplomacy.TRADE_ALLIANCE)
          && !info.getDiplomacy().getDiplomaticRelation(second)
          .equals(Diplomacy.ALLIANCE)
          && info.getEspionage().isSpyTradePossible()) {
        generateEqualTrade(NegotiationType.SPY_TRADE);
      }
    }
    int value = DiceGenerator.getRandom(100);
    int embargoIndex = getPossibleTradeEmbargo();
    if (value < 40) {
      if (!generateFleetSellOffer(info, agree, fleetListForFirst)) {
        generateTechTrade(TRADE);
      }
    } else if (value < 80) {
      generateTechTrade(BUY);
    } else {
      if (embargoIndex != -1 && DiceGenerator.getRandom(100) < 50) {
        PlayerInfo embagoed = starMap.getPlayerByIndex(embargoIndex);
        generateTradeEmbargoOffer(embagoed);
      } else {
          if (info.getDiplomacy().isDefensivePact(second)
            || info.getDiplomacy().isAlliance(second)) {
            generateMapTrade(TRADE, true);
          } else {
            generateMapTrade(TRADE, false);
          }
      }
    }
  }

  /**
   * Offer by merchantical attitude.
   * This tries to buy and sell offerings
   */
  public void generateMerchanticalAttitudeOffer() {
    PlayerInfo info = starMap.getPlayerByIndex(first);
    PlayerInfo agree = starMap.getPlayerByIndex(second);
    int power = starMap.getMilitaryDifference(first,
        second);
    boolean casusBelli = info.getDiplomacy().hasCasusBelli(second);
    if (power > 0 && casusBelli) {
      power = power + 10;
    }
    if (info.getDiplomacy().countBorderCrossing(second) > 6) {
      power = power + BORDER_CROSSING_POWER * info.getAiDifficulty().getIndex();
    }
    if (power > 100 && info.getDiplomacy().getDiplomaticRelation(second)
        .isEmpty()) {
      generateEqualTrade(NegotiationType.WAR);
      return;
    }
    if (power > 220 && info.getDiplomacy().getDiplomaticRelation(second)
        .equals(Diplomacy.TRADE_ALLIANCE) && casusBelli) {
      generateEqualTrade(NegotiationType.WAR);
      return;
    }
    if (power > 440 && casusBelli) {
      generateEqualTrade(NegotiationType.WAR);
      return;
    }
    if (info.getDiplomacy().getLiking(second) == Diplomacy.LIKE) {
      if (DiceGenerator.getRandom(100) < 35
          && info.getDiplomacy().getDiplomaticRelation(second)
          .equals(Diplomacy.PEACE)) {
        generateEqualTrade(NegotiationType.TRADE_ALLIANCE);
        return;
      }
    } else if (info.getDiplomacy().getLiking(second) == Diplomacy.FRIENDS) {
      int value = DiceGenerator.getRandom(100);
      if (value < 35) {
        if (info.getDiplomacy().getDiplomaticRelation(second)
            .equals(Diplomacy.DEFENSIVE_PACT)
            && !info.getDiplomacy().hasAlliance()
            && !agree.getDiplomacy().hasAlliance()) {
          generateEqualTrade(NegotiationType.ALLIANCE);
          return;
        }
        if (info.getDiplomacy().getDiplomaticRelation(second)
            .equals(Diplomacy.TRADE_ALLIANCE)) {
          generateEqualTrade(NegotiationType.DEFENSIVE_PACT);
          return;
        }
        if (info.getDiplomacy().getDiplomaticRelation(second)
          .equals(Diplomacy.PEACE)) {
          generateEqualTrade(NegotiationType.TRADE_ALLIANCE);
          return;
        }
      }
      if (value < 55 && info.getDiplomacy().getDiplomaticRelation(second)
          .equals(Diplomacy.TRADE_ALLIANCE)
          && !info.getDiplomacy().getDiplomaticRelation(second)
          .equals(Diplomacy.ALLIANCE)
          && info.getEspionage().isSpyTradePossible()) {
        generateEqualTrade(NegotiationType.SPY_TRADE);
      }
    }
    int value = DiceGenerator.getRandom(6);
    int embargoIndex = getPossibleTradeEmbargo();
    switch (value) {
      case 0:
      default: {
        if (info.getDiplomacy().isDefensivePact(second)
            || info.getDiplomacy().isAlliance(second)) {
            generateMapTrade(TRADE, true);
          } else {
            generateMapTrade(TRADE, false);
          }
        break;
      }
      case 1: {
        generateMapTrade(BUY, true); break;
      }
      case 2: {
        if (!generateFleetSellOffer(info, agree, fleetListForFirst)) {
          generateMapTrade(SELL, true);
        }
        break;
      }
      case 3: {
        generateTechTrade(TRADE); break;
      }
      case 4: {
        generateTechTrade(BUY); break;
      }
      case 5: {
        if (!generateFleetSellOffer(info, agree, fleetListForFirst)) {
          generateTechTrade(SELL);
        }
        break;
      }
      case 6: {
        if (embargoIndex != -1 && DiceGenerator.getRandom(100) < 50) {
          PlayerInfo embagoed = starMap.getPlayerByIndex(embargoIndex);
          generateTradeEmbargoOffer(embagoed);
        } else {
          if (!generateFleetSellOffer(info, agree, fleetListForFirst)) {
            generateTechTrade(SELL);
          }
        }
        break;
      }
    }
  }
  /**
   * Offer by Militaristic attitude.
   * This makes war quite easily and can make demands if
   * does not like another player
   */
  public void generateMilitaristicAttitudeOffer() {
    PlayerInfo info = starMap.getPlayerByIndex(first);
    PlayerInfo agree = starMap.getPlayerByIndex(second);
    int power = starMap.getMilitaryDifference(first,
        second);
    boolean casusBelli = info.getDiplomacy().hasCasusBelli(second);
    if (power > 0 && casusBelli) {
      power = power + 20;
    }
    if (info.getDiplomacy().countBorderCrossing(second) > 6) {
      power = power + BORDER_CROSSING_POWER * info.getAiDifficulty().getIndex();
    }
    if (power > 50 && info.getDiplomacy().getDiplomaticRelation(second)
        .isEmpty()) {
      generateEqualTrade(NegotiationType.WAR);
      return;
    }
    if (power > 120 && info.getDiplomacy().getDiplomaticRelation(second)
        .equals(Diplomacy.TRADE_ALLIANCE)) {
      generateEqualTrade(NegotiationType.WAR);
      return;
    }
    if (power > 220) {
      generateEqualTrade(NegotiationType.WAR);
      return;
    }
    if (info.getDiplomacy().getLiking(second) == Diplomacy.LIKE) {
      if (DiceGenerator.getRandom(100) < 25
          && info.getDiplomacy().getDiplomaticRelation(second)
          .equals(Diplomacy.PEACE)) {
        generateEqualTrade(NegotiationType.TRADE_ALLIANCE);
        return;
      }
    } else if (info.getDiplomacy().getLiking(second) == Diplomacy.FRIENDS) {
      int value = DiceGenerator.getRandom(100);
      if (value < 25) {
        if (info.getDiplomacy().getDiplomaticRelation(second)
            .equals(Diplomacy.DEFENSIVE_PACT)
            && !info.getDiplomacy().hasAlliance()
            && !agree.getDiplomacy().hasAlliance()) {
          generateEqualTrade(NegotiationType.ALLIANCE);
          return;
        }
        if (info.getDiplomacy().getDiplomaticRelation(second)
            .equals(Diplomacy.TRADE_ALLIANCE)) {
          generateEqualTrade(NegotiationType.DEFENSIVE_PACT);
          return;
        }
        if (info.getDiplomacy().getDiplomaticRelation(second)
          .equals(Diplomacy.PEACE)) {
          generateEqualTrade(NegotiationType.TRADE_ALLIANCE);
          return;
        }
      }
      if (value < 35 && info.getDiplomacy().getDiplomaticRelation(second)
          .equals(Diplomacy.TRADE_ALLIANCE)
          && !info.getDiplomacy().getDiplomaticRelation(second)
          .equals(Diplomacy.ALLIANCE)
          && info.getEspionage().isSpyTradePossible()) {
        generateEqualTrade(NegotiationType.SPY_TRADE);
      }
    }
    int value = DiceGenerator.getRandom(100);
    int embargoIndex = getPossibleTradeEmbargo();
    if (value < 34) {
      if (embargoIndex != -1 && DiceGenerator.getRandom(100) < 50) {
        PlayerInfo embagoed = starMap.getPlayerByIndex(embargoIndex);
        generateTradeEmbargoOffer(embagoed);
      } else {
          if (info.getDiplomacy().isDefensivePact(second)
            || info.getDiplomacy().isAlliance(second)) {
            generateMapTrade(TRADE, true);
          } else {
            generateMapTrade(TRADE, false);
          }
      }
    } else if (value < 67) {
      if (!generateFleetSellOffer(info, agree, fleetListForFirst)) {
        generateTechTrade(TRADE);
      }
    } else {
      if (!info.getDiplomacy().getDiplomaticRelation(second)
          .equals(Diplomacy.DEFENSIVE_PACT)) {
        generateTechDemand(agree, info);
      } else {
        generateMapTrade(BUY, true);
      }
    }
  }
  /**
   * Very basic offer by logical attitude.
   * This makes war only if power difference goes too big.
   */
  public void generateLogicalAttitudeOffer() {
    PlayerInfo info = starMap.getPlayerByIndex(first);
    PlayerInfo agree = starMap.getPlayerByIndex(second);
    int power = starMap.getMilitaryDifference(first,
        second);
    boolean casusBelli = info.getDiplomacy().hasCasusBelli(second);
    if (power > 80 && info.getDiplomacy().getDiplomaticRelation(second)
        .isEmpty() && casusBelli) {
      generateEqualTrade(NegotiationType.WAR);
      return;
    }
    if (info.getDiplomacy().countBorderCrossing(second) > 6) {
      power = power + BORDER_CROSSING_POWER * info.getAiDifficulty().getIndex();
    }
    if (power > 200 && info.getDiplomacy().getDiplomaticRelation(second)
        .equals(Diplomacy.TRADE_ALLIANCE) && casusBelli) {
      generateEqualTrade(NegotiationType.WAR);
      return;
    }
    if (power > 400 && casusBelli) {
      generateEqualTrade(NegotiationType.WAR);
      return;
    }
    if (info.getDiplomacy().getLiking(second) == Diplomacy.LIKE) {
      if (DiceGenerator.getRandom(100) < 25
          && info.getDiplomacy().getDiplomaticRelation(second)
          .equals(Diplomacy.PEACE)) {
        generateEqualTrade(NegotiationType.TRADE_ALLIANCE);
        return;
      }
    } else if (info.getDiplomacy().getLiking(second) == Diplomacy.FRIENDS) {
      int value = DiceGenerator.getRandom(100);
      if (value < 25) {
        if (info.getDiplomacy().getDiplomaticRelation(second)
            .equals(Diplomacy.DEFENSIVE_PACT)
            && !info.getDiplomacy().hasAlliance()
            && !agree.getDiplomacy().hasAlliance()) {
          generateEqualTrade(NegotiationType.ALLIANCE);
          return;
        }
        if (info.getDiplomacy().getDiplomaticRelation(second)
            .equals(Diplomacy.TRADE_ALLIANCE)) {
          generateEqualTrade(NegotiationType.DEFENSIVE_PACT);
          return;
        }
        if (info.getDiplomacy().getDiplomaticRelation(second)
          .equals(Diplomacy.PEACE)) {
          generateEqualTrade(NegotiationType.TRADE_ALLIANCE);
          return;
        }
      }
      if (value < 35 && info.getDiplomacy().getDiplomaticRelation(second)
          .equals(Diplomacy.TRADE_ALLIANCE)
          && !info.getDiplomacy().getDiplomaticRelation(second)
          .equals(Diplomacy.ALLIANCE)
          && info.getEspionage().isSpyTradePossible()) {
        generateEqualTrade(NegotiationType.SPY_TRADE);
      }
    }
    int value = DiceGenerator.getRandom(100);
    int embargoIndex = getPossibleTradeEmbargo();
    if (value < 50) {
      if (info.getDiplomacy().isDefensivePact(second)
          || info.getDiplomacy().isAlliance(second)) {
          generateMapTrade(TRADE, true);
        } else {
          generateMapTrade(TRADE, false);
        }
    } else {
      if (embargoIndex != -1 && DiceGenerator.getRandom(100) < 50) {
        PlayerInfo embagoed = starMap.getPlayerByIndex(embargoIndex);
        generateTradeEmbargoOffer(embagoed);
      } else {
        if (!generateFleetSellOffer(info, agree, fleetListForFirst)) {
          generateTechTrade(TRADE);
        }
      }
    }
  }
  /**
   * Offer by peaceful attitude.
   * This makes war only if power difference goes too big.
   */
  public void generatePeacefulAttitudeOffer() {
    PlayerInfo info = starMap.getPlayerByIndex(first);
    PlayerInfo agree = starMap.getPlayerByIndex(second);
    int power = starMap.getMilitaryDifference(first,
        second);
    boolean casusBelli = info.getDiplomacy().hasCasusBelli(second);
    if (power > 0 && casusBelli) {
      power = power + 20;
    }
    if (info.getDiplomacy().countBorderCrossing(second) > 6) {
      power = power + BORDER_CROSSING_POWER * info.getAiDifficulty().getIndex();
    }
    if (power > 200 && info.getDiplomacy().getDiplomaticRelation(second)
        .isEmpty() && casusBelli) {
      generateEqualTrade(NegotiationType.WAR);
      return;
    }
    if (power > 400 && info.getDiplomacy().getDiplomaticRelation(second)
        .equals(Diplomacy.TRADE_ALLIANCE) && casusBelli) {
      generateEqualTrade(NegotiationType.WAR);
      return;
    }
    if (power > 600 && casusBelli) {
      generateEqualTrade(NegotiationType.WAR);
      return;
    }
    if (info.getDiplomacy().getLiking(second) == Diplomacy.LIKE) {
      if (DiceGenerator.getRandom(100) < 50
          && info.getDiplomacy().getDiplomaticRelation(second)
          .equals(Diplomacy.PEACE)) {
        generateEqualTrade(NegotiationType.TRADE_ALLIANCE);
        return;
      }
    } else if (info.getDiplomacy().getLiking(second) == Diplomacy.FRIENDS) {
      int value = DiceGenerator.getRandom(100);
      if (value < 50) {
        if (info.getDiplomacy().getDiplomaticRelation(second)
            .equals(Diplomacy.DEFENSIVE_PACT)
            && !info.getDiplomacy().hasAlliance()
            && !agree.getDiplomacy().hasAlliance()) {
          generateEqualTrade(NegotiationType.ALLIANCE);
          return;
        }
        if (info.getDiplomacy().getDiplomaticRelation(second)
            .equals(Diplomacy.TRADE_ALLIANCE)) {
          generateEqualTrade(NegotiationType.DEFENSIVE_PACT);
          return;
        }
        if (info.getDiplomacy().getDiplomaticRelation(second)
          .equals(Diplomacy.PEACE)) {
          generateEqualTrade(NegotiationType.TRADE_ALLIANCE);
          return;
        }
      }
      if (value < 60 && info.getDiplomacy().getDiplomaticRelation(second)
          .equals(Diplomacy.TRADE_ALLIANCE)
          && !info.getDiplomacy().getDiplomaticRelation(second)
          .equals(Diplomacy.ALLIANCE)
          && info.getEspionage().isSpyTradePossible()) {
        generateEqualTrade(NegotiationType.SPY_TRADE);
      }
    }
    int value = DiceGenerator.getRandom(100);
    int embargoIndex = getPossibleTradeEmbargo();
    if (value < 50) {
      if (info.getDiplomacy().isDefensivePact(second)
          || info.getDiplomacy().isAlliance(second)) {
        generateMapTrade(TRADE, true);
      } else {
        generateMapTrade(TRADE, false);
      }
    } else {
      if (embargoIndex != -1 && DiceGenerator.getRandom(100) < 50) {
        PlayerInfo embagoed = starMap.getPlayerByIndex(embargoIndex);
        generateTradeEmbargoOffer(embagoed);
      } else {
        if (!generateFleetSellOffer(info, agree, fleetListForFirst)) {
          generateTechTrade(TRADE);
        }
      }
    }
  }
  /**
   * Generate diplomatic trade offer between two players
   */
  public void generateOffer() {
    PlayerInfo offerPlayer = starMap.getPlayerByIndex(first);
    if (techListForFirst == null || techListForSecond == null) {
      generateTechList();
    }
    if (isDiplomacyWithPirates()) {
      generatePirateOffer();
    } else if (offerPlayer.getDiplomacy().getDiplomacyList(second)
        .getNumberOfMeetings() == 0
        || offerPlayer.getDiplomacy().getDiplomaticRelation(second)
            .equals(Diplomacy.NO_RELATION)) {
      generateFirstOffer();
    } else if (offerPlayer.getDiplomacy().getDiplomaticRelation(second)
        .equals(Diplomacy.WAR)) {
      generatePeaceOffer();
    } else {
      Attitude attitude = offerPlayer.getAiAttitude();
      WinningStrategy  strategy = offerPlayer.getStrategy();
      if (strategy == WinningStrategy.CULTURAL) {
        if (attitude == Attitude.AGGRESSIVE
            || attitude == Attitude.MILITARISTIC) {
          // Cleans diplomatic behaviour so it tries to not to make war.
          attitude = Attitude.DIPLOMATIC;
        }
        if (attitude == Attitude.BACKSTABBING) {
          // Cleans diplomatic behaviour so it tries to not to make war.
          attitude = Attitude.PEACEFUL;
        }
      }
      if (strategy == WinningStrategy.DIPLOMATIC) {
        if (attitude == Attitude.AGGRESSIVE
            || attitude == Attitude.MILITARISTIC) {
          // Cleans diplomatic behaviour so it tries to not to make war.
          attitude = Attitude.DIPLOMATIC;
        }
        if (attitude == Attitude.BACKSTABBING) {
          // Cleans diplomatic behaviour so it tries to not to make war.
          attitude = Attitude.PEACEFUL;
        }
      }
      if (strategy == WinningStrategy.CONQUER) {
        if (attitude == Attitude.PEACEFUL) {
          // Cleans diplomatic behaviour so it tries to  make war.
          attitude = Attitude.MILITARISTIC;
        }
        if (attitude == Attitude.DIPLOMATIC) {
          // Cleans diplomatic behaviour so it tries to  make war.
          attitude = Attitude.AGGRESSIVE;
        }
      }
      if (strategy == WinningStrategy.SCIENCE
          && attitude == Attitude.BACKSTABBING) {
        // Cleans diplomatic behaviour so it tries trade more science.
        attitude = Attitude.SCIENTIFIC;
      }
      switch (attitude) {
        case AGGRESSIVE: {
          generateAggressiveAttitudeOffer();
          break;
        }
        case BACKSTABBING: {
          generateBackstabbingAttitudeOffer();
          break;
        }
        case DIPLOMATIC: {
          generateDiplomaticAttitudeOffer();
          break;
        }
        case EXPANSIONIST: {
          generateExpansionistAttitudeOffer();
          break;
        }
        case LOGICAL: {
          generateLogicalAttitudeOffer();
          break;
        }
        case MERCHANTICAL: {
          generateMerchanticalAttitudeOffer();
          break;
        }
        case MILITARISTIC: {
          generateMilitaristicAttitudeOffer();
          break;
        }
        case PEACEFUL: {
          generatePeacefulAttitudeOffer();
          break;
        }
        case SCIENTIFIC: {
          generateScientificAttitudeOffer();
          break;
        }
        default: {
          generateLogicalAttitudeOffer();
          break;
        }
      }
    }
  }

  /**
   * Get value for embargo
   * @param info Realm valueing the embargo
   * @param offer Embargo offer
   * @return Value for embargo
   */
  public int getValueForEmbargo(final PlayerInfo info,
      final NegotiationOffer offer) {
    int value = 0;
    PlayerInfo embargoedRealm = offer.getRealm();
    int embargoed = starMap.getPlayerList().getIndex(embargoedRealm);
    int liking = info.getDiplomacy().getLiking(embargoed);
    if (liking == Diplomacy.NEUTRAL) {
      value = 0;
    }
    if (liking == Diplomacy.DISLIKE) {
      value = 2;
    }
    if (liking == Diplomacy.HATE) {
      value = 4;
    }
    if (liking == Diplomacy.LIKE) {
      value = -5;
    }
    if (liking == Diplomacy.FRIENDS) {
      value = -10;
    }
    String relation = info.getDiplomacy().getDiplomaticRelation(embargoed);
    if (Diplomacy.TRADE_ALLIANCE.equals(relation)) {
      value = value - 1;
    }
    if (Diplomacy.DEFENSIVE_PACT.equals(relation)) {
      value = value - 10;
    }
    if (Diplomacy.ALLIANCE.equals(relation)) {
      value = value - 50;
    }
    if (Diplomacy.WAR.equals(relation)) {
      value = value + 5;
    }
    return value;
  }
  /**
   * Is offer good for both. This assumes that first one is making the offer
   * and is okay with it. So it only checks that second one likes it.
   * Difference is integer value. Positive means that deal is good for first
   * player but bad for second player. Negative means that deal is good for
   * second but bad for first player. Main idea is that first player has
   * made the offer so that player is okay already with offer
   * @return Difference value negative means that both parties agree it.
   */
  public int getOfferDifferenceForBoth() {
    int firstValue = 0;
    int secondValue = 0;
    boolean secondValueZero = false;
    boolean militaryThreat = false;
    if (firstOffer == null) {
      // Just creating empty list
      firstOffer = new NegotiationList();
    }
    if (secondOffer == null) {
      // Just creating empty list
      secondOffer = new NegotiationList();
    }
    firstValue = firstOffer.getOfferValue(starMap.getPlayerByIndex(first)
        .getRace());
    secondValue = secondOffer.getOfferValue(starMap.getPlayerByIndex(second)
        .getRace());
    PlayerInfo info1 = starMap.getPlayerByIndex(first);
    PlayerInfo info2 = starMap.getPlayerByIndex(second);
    NegotiationOffer offer = firstOffer.getEmbargoOffer();
    if (offer != null && Math.abs(firstValue - secondValue) < 10) {
      firstValue = firstValue + getValueForEmbargo(info1, offer);
      offer = secondOffer.getEmbargoOffer();
      if (offer != null) {
        secondValue = secondValue + getValueForEmbargo(info2, offer);
      }
    }
    boolean isWar = info2.getDiplomacy().isWar(first);
    if ((firstOffer.isPlanetInOffer() || firstOffer.isFleetInOffer())
        && (!isWar || !firstOffer.isPeaceInOffer())) {
      // AI should never give up planet or fleet unless
      // is peace is being offered
      return DECLINE_INSULT;
    }
    if (secondValue == 0 && firstValue > 0) {
      secondValueZero = true;
    }
    int difference = firstValue - secondValue;
    // Maybe good diplomatic relations help to get trade through
    int bonus = info2.getDiplomacy().getDiplomacyList(first)
        .getDiplomacyBonus() / 2;
    if (bonus > 10) {
      bonus = 10;
    }
    if (info1.getRuler() != null
        && info1.getRuler().hasPerk(Perk.CHARISMATIC)) {
      bonus = bonus + 1;
    }
    if (info1.getRuler() != null
        && info1.getRuler().hasPerk(Perk.SKILLFUL)) {
      bonus = bonus + 1;
    }
    if (info1.getRuler() != null
        && info1.getRuler().hasPerk(Perk.DIPLOMATIC)) {
      bonus = bonus + 1;
    }
    if (info1.getRuler() != null
        && info1.getRuler().hasPerk(Perk.CRUEL)) {
      bonus = bonus - 2;
    }
    if (info1.getRuler() != null
        && info1.getRuler().hasPerk(Perk.PEACEFUL)) {
      bonus = bonus + 1;
    }
    if (info1.getRuler() != null
        && info1.getRuler().hasPerk(Perk.REPULSIVE)) {
      bonus = bonus - 1;
    }
    if (info1.getRuler() != null
        && info1.getRuler().hasPerk(Perk.INCOMPETENT)) {
      bonus = bonus - 1;
    }
    if (info1.getRuler() != null
        && info1.getRuler().hasPerk(Perk.MAD)) {
      bonus = bonus - 2;
    }
    if (!isDiplomacyWithPirates()) {
      difference = difference - bonus;
    } else {
      // There is always war between pirates
      return difference;
    }
    if (info2.getDiplomacy().isWar(first)
        || getSpeechTypeByOffer() == SpeechType.DEMAND
        || getSpeechTypeByOffer() == SpeechType.ASK_MOVE_FLEET
        || getSpeechTypeByOffer() == SpeechType.ASK_MOVE_SPY
        && secondOffer.getByIndex(0).getFleet().getMilitaryValue() > 0) {
      militaryThreat = true;
      Attitude attitude = info2.getAiAttitude();
      int divider = 4;
      int ownDivider = 4;
      switch (attitude) {
        case AGGRESSIVE:
        case MILITARISTIC: {
          divider = 6;
          ownDivider = 2;
          break;
        }
        case BACKSTABBING:
        case EXPANSIONIST: {
          divider = 5;
          ownDivider = 3;
          break;
        }
        case LOGICAL:
        case DIPLOMATIC:
        case SCIENTIFIC:
        default: {
          divider = 4;
          ownDivider = 4;
          break;
        }
        case MERCHANTICAL:
        case PEACEFUL: {
          divider = 3;
          ownDivider = 5;
          break;
        }
      }
      int militaryDifference = starMap.getMilitaryDifference(first, second);
      if (militaryDifference > 0) {
        if (isWar) {
          if (firstOffer.isPeaceInOffer()) {
            difference = difference - militaryDifference / divider;
          }
        } else {
          difference = difference - militaryDifference / divider;
        }
      } else {
        difference = difference - militaryDifference / ownDivider;
      }
    }
    if (!militaryThreat && secondValueZero && difference <= 0) {
      Attitude attitude = info2.getAiAttitude();
      switch (attitude) {
        case AGGRESSIVE:
        case MILITARISTIC:
        case BACKSTABBING:
        case LOGICAL:
        default: {
          difference = 1;
          break;
        }
        case EXPANSIONIST:
        case SCIENTIFIC: {
          if (DiceGenerator.getRandom(3) > 0) {
            difference = 1;
          }
          break;
        }
        case MERCHANTICAL: {
          if (DiceGenerator.getRandom(2) > 0) {
            difference = 1;
          }
          break;
        }
        case DIPLOMATIC:
        case PEACEFUL: {
          if (DiceGenerator.getRandom(1) == 0) {
            difference = 1;
          }
          break;
        }
      }
    }
    return difference;
  }
  /**
   * Is offer good for both. This assumes that first one is making the offer
   * and is okay with it. So it only checks that second one likes it.
   * @return true if offer is good for both players.
   */
  public boolean isOfferGoodForBoth() {
    int difference = getOfferDifferenceForBoth();
    if (difference <= 0) {
      return true;
    }
    return false;
  }

  /**
   * Get war chance for getting decline for offer
   * @param type What was the original SpeechType
   * @param attitude AI's attitude who's offer was declined
   * @param liking See fixed values from Diplomacy class.
   *        There are five choices: HATE, DISLIKE, NEUTRAL, LIKE and FRIENDS
   * @param casusBelli True if asking has casus belli against decliner.
   * @return Chance for war. Number between 0-100.
   */
  public static int getWarChanceForDecline(final SpeechType type,
      final Attitude attitude, final int liking, final boolean casusBelli) {
    int warChance = 0;
    if (type == SpeechType.MAKE_WAR) {
      warChance = 100;
    } else if (type == SpeechType.DEMAND) {
      warChance = 50;
      switch (attitude) {
        case AGGRESSIVE: {
          warChance = warChance + 25;
          if (casusBelli) {
            warChance = warChance + 10;
          }
          break;
        }
        case BACKSTABBING:
        case MILITARISTIC: {
          warChance = warChance + 15;
          if (casusBelli) {
            warChance = warChance + 10;
          }
          break;
        }
        case DIPLOMATIC: {
          warChance = warChance - 5;
          break;
        }
        case EXPANSIONIST: {
          warChance = warChance + 5;
          if (casusBelli) {
            warChance = warChance + 2;
          }
          break;
        }
        case LOGICAL:
        case SCIENTIFIC:
        default: {
          // No chance for warChance
          break;
        }
        case MERCHANTICAL: {
          warChance = warChance - 10;
          break;
        }
        case PEACEFUL: {
          warChance = warChance - 25;
          break;
        }
      }
    } else {
      warChance = 0;
      switch (attitude) {
        case AGGRESSIVE: {
          warChance = warChance + 10;
          if (casusBelli) {
            warChance = warChance + 5;
          }
          break;
        }
        case BACKSTABBING:
        case MILITARISTIC: {
          warChance = warChance + 5;
          if (casusBelli) {
            warChance = warChance + 5;
          }
          break;
        }
        case DIPLOMATIC: {
          warChance = warChance - 5;
          break;
        }
        case EXPANSIONIST: {
          warChance = warChance + 3;
          if (casusBelli) {
            warChance = warChance + 2;
          }
          break;
        }
        case LOGICAL:
        case SCIENTIFIC:
        default: {
          // No change for warChance
          break;
        }
        case MERCHANTICAL: {
          warChance = warChance - 3;
          break;
        }
        case PEACEFUL: {
          warChance = warChance - 10;
          break;
        }
      }
    }
    if (liking == Diplomacy.HATE) {
      warChance = warChance + 20;
    }
    if (liking == Diplomacy.DISLIKE) {
      warChance = warChance + 10;
    }
    if (liking == Diplomacy.LIKE) {
      warChance = warChance - 10;
    }
    if (liking == Diplomacy.FRIENDS) {
      warChance = warChance - 20;
    }
    if (casusBelli) {
      warChance = warChance + 10;
    }
    if (warChance > 100) {
      warChance = 100;
    }
    if (warChance < 0) {
      warChance = 0;
    }
    return warChance;
  }

  /**
   * Change map for one player
   * @param mapReceiver Player who is receiving the map
   * @param mapGiver Player who is giving the map
   * @param fullMap True if full map otherwise just planets
   */
  private void doMapTrade(final PlayerInfo mapReceiver,
      final PlayerInfo mapGiver, final boolean fullMap) {
    if (fullMap) {
      for (int y = 0; y < starMap.getMaxY(); y++) {
        for (int x = 0; x < starMap.getMaxX(); x++) {
          Coordinate coord = new Coordinate(x, y);
          byte visibility = mapGiver.getSectorVisibility(coord);
          if (visibility == PlayerInfo.FOG_OF_WAR
              || visibility == PlayerInfo.VISIBLE) {
            byte origVisiblity = mapReceiver.getSectorVisibility(coord);
            if (origVisiblity == PlayerInfo.UNCHARTED) {
              mapReceiver.setSectorVisibility(x, y, PlayerInfo.FOG_OF_WAR);
            }
         }
        }
      }
    } else {
      for (Planet planet : starMap.getPlanetList()) {
        if (planet.getPlanetPlayerInfo() == mapGiver) {
          byte origVisiblity = mapReceiver.getSectorVisibility(
              planet.getCoordinate());
          if (origVisiblity == PlayerInfo.UNCHARTED) {
            mapReceiver.setSectorVisibility(planet.getX(), planet.getY(),
                PlayerInfo.FOG_OF_WAR);
          }
        }
      }
    }
  }

  /**
   * Calculate map trade value. This will calculate how many
   * sectors are unknown to receiver
   * @param mapReceiver Receiver realm
   * @param mapGiver Giver realm
   * @param fullMap True for full map value, false for planets only
   * @return Value of map.
   */
  private int calculateMapValue(final PlayerInfo mapReceiver,
      final PlayerInfo mapGiver, final boolean fullMap) {
    return calculateMapValue(starMap, mapReceiver, mapGiver, fullMap);
  }

  /**
   * Calculate map trade value. This will calculate how many
   * sectors are unknown to receiver
   * @param starMap StarMap
   * @param mapReceiver Receiver realm
   * @param mapGiver Giver realm
   * @param fullMap True for full map value, false for planets only
   * @return Value of map.
   */
  public static int calculateMapValue(final StarMap starMap,
      final PlayerInfo mapReceiver, final PlayerInfo mapGiver,
      final boolean fullMap) {
    int value = 0;
    if (fullMap) {
      for (int y = 0; y < starMap.getMaxY(); y++) {
        for (int x = 0; x < starMap.getMaxX(); x++) {
          Coordinate coord = new Coordinate(x, y);
          byte visibility = mapGiver.getSectorVisibility(coord);
          if (visibility == PlayerInfo.FOG_OF_WAR
              || visibility == PlayerInfo.VISIBLE) {
            byte origVisiblity = mapReceiver.getSectorVisibility(coord);
            if (origVisiblity == PlayerInfo.UNCHARTED) {
              value = value + 1;
            }
         }
        }
      }
    } else {
      for (Planet planet : starMap.getPlanetList()) {
        if (planet.getPlanetPlayerInfo() == mapGiver) {
          byte origVisiblity = mapReceiver.getSectorVisibility(
              planet.getCoordinate());
          if (origVisiblity == PlayerInfo.UNCHARTED) {
            value = value + 1;
          }
        }
      }
    }
    return value;
  }

  /**
   * Do trading for one player
   * @param offerList Trade goods aka offering list
   * @param info Player who is getting the stuff
   * @param giver Player who is giving the stuff
   * @return Total value of trade
   */
  private int doTrade(final NegotiationList offerList, final PlayerInfo info,
      final PlayerInfo giver) {
    int totalValue = 0;
    boolean countAsDiplomaticTrade = false;
    boolean countAsWar = false;
    for (int i = 0; i < offerList.getSize(); i++) {
      NegotiationOffer offer = offerList.getByIndex(i);
      totalValue = totalValue + offer.getOfferValue(info.getRace());
      switch (offer.getNegotiationType()) {
      case CREDIT: {
        info.setTotalCredits(info.getTotalCredits() + offer.getCreditValue());
        giver.setTotalCredits(giver.getTotalCredits() - offer.getCreditValue());
        countAsDiplomaticTrade = true;
        break;
      }
      case RECALL_FLEET: {
        if (!info.isHuman()) {
          Mission mission = info.getMissions().getMissionForFleet(
              offer.getFleet().getName());
          if (mission != null) {
            mission.setType(MissionType.MOVE);
            Planet planet = starMap.getClosestHomePort(info,
                offer.getFleet().getCoordinate());
            if (planet != null) {
              mission.setTargetPlanet(planet.getName());
              mission.setTarget(planet.getCoordinate());
            }
            mission.setPhase(MissionPhase.PLANNING);
          } else {
            Planet planet = starMap.getClosestHomePort(info,
                offer.getFleet().getCoordinate());
            if (planet != null) {
              mission = new Mission(MissionType.MOVE, MissionPhase.PLANNING,
                  planet.getCoordinate());
              mission.setFleetName(offer.getFleet().getName());
              info.getMissions().add(mission);
            }

          }
        }
        break;
      }
      case TECH: {
        info.getTechList().addTech(offer.getTech());
        countAsDiplomaticTrade = true;
        break;
      }
      case FLEET: {
        if (offer.getFleet().getCommander() != null) {
          offer.getFleet().getCommander().setJob(Job.UNASSIGNED);
          offer.getFleet().setCommander(null);
        }
        info.getFleets().add(offer.getFleet());
        int index = giver.getFleets().getIndexByName(offer.getFleet()
            .getName());
        giver.getFleets().remove(index);
        giver.getMissions().deleteMissionForFleet(offer.getFleet().getName());
        countAsDiplomaticTrade = true;
        break;
      }
      case PLANET: {
        Planet planet = starMap.getPlanetByName(offer.getPlanet().getName());
        int index = starMap.getPlayerList().getIndex(info);
        if (planet.getGovernor() != null) {
          planet.getGovernor().setJob(Job.UNASSIGNED);
          planet.setGovernor(null);
        }
        planet.setPlanetOwner(index, info);
        if (info.getRace() == SpaceRace.ALTEIRIANS) {
          planet.colonizeWithOrbital();
        }
        countAsDiplomaticTrade = true;
        break;
      }
      case MAP: {
        doMapTrade(info, giver, true);
        countAsDiplomaticTrade = true;
        break;
      }
      case DIPLOMAT: {
        //There are no diplomats in the game
        break;
      }
      case TRADE_ALLIANCE: {
        int index = starMap.getPlayerList().getIndex(giver);
        info.getDiplomacy().getDiplomacyList(index).addBonus(
            DiplomacyBonusType.IN_TRADE_ALLIANCE, info.getRace());
        countAsDiplomaticTrade = true;
        break;
      }
      case ALLIANCE: {
        int index = starMap.getPlayerList().getIndex(giver);
        info.getDiplomacy().getDiplomacyList(index).addBonus(
            DiplomacyBonusType.IN_ALLIANCE, info.getRace());
        countAsDiplomaticTrade = true;
        break;
      }
      case PEACE: {
        int index = starMap.getPlayerList().getIndex(giver);
        info.getDiplomacy().getDiplomacyList(index).addBonus(
            DiplomacyBonusType.LONG_PEACE, info.getRace());
        countAsDiplomaticTrade = true;
        break;
      }
      case WAR: {
        int index = starMap.getPlayerList().getIndex(giver);
        info.getDiplomacy().getDiplomacyList(index).addBonus(
            DiplomacyBonusType.IN_WAR, info.getRace());
        countAsWar = true;
        break;
      }
      case DEFENSIVE_PACT: {
        int index = starMap.getPlayerList().getIndex(giver);
        info.getDiplomacy().getDiplomacyList(index).addBonus(
            DiplomacyBonusType.IN_DEFENSIVE_PACT, info.getRace());
        countAsDiplomaticTrade = true;
        break;
      }
      case SPY_TRADE: {
        int index = starMap.getPlayerList().getIndex(giver);
        info.getDiplomacy().getDiplomacyList(index).addBonus(
            DiplomacyBonusType.SPY_TRADE, info.getRace());
        countAsDiplomaticTrade = true;
        break;
      }
      case TRADE_EMBARGO: {
        int index = starMap.getPlayerList().getIndex(giver);
        info.getDiplomacy().getDiplomacyList(index).addBonus(
            DiplomacyBonusType.LIKED_EMBARGO, info.getRace());
        countAsDiplomaticTrade = true;
        break;
      }
      case MAP_PLANETS: {
        doMapTrade(info, giver, false);
        countAsDiplomaticTrade = true;
        break;
      }
      case PROMISE_VOTE_NO: {
        int index = starMap.getPlayerList().getIndex(giver);
        info.getDiplomacy().getDiplomacyList(index).addBonus(
            DiplomacyBonusType.PROMISED_VOTE_NO, info.getRace());
        countAsDiplomaticTrade = true;
        break;
      }
      case PROMISE_VOTE_YES: {
        int index = starMap.getPlayerList().getIndex(giver);
        info.getDiplomacy().getDiplomacyList(index).addBonus(
            DiplomacyBonusType.PROMISED_VOTE_YES, info.getRace());
        countAsDiplomaticTrade = true;
        break;
      }
      case DISCOVERED_ARTIFACT: {
        int amount = offer.getDiscoveredArtifacts();
        for (int k = 0; k < amount; k++) {
          Artifact artifact = giver.getArtifactLists()
              .takeRandomDiscoveredArtifact();
          if (artifact != null) {
            info.getArtifactLists().addDiscoveredArtifact(artifact);
            countAsDiplomaticTrade = true;
          }
        }
        break;
      }
      case ASK_PROTECTION: {
        int index = starMap.getPlayerList().getIndex(giver);
        info.getDiplomacy().getDiplomacyList(index).addBonus(
            DiplomacyBonusType.PROMISED_PROTECTION, info.getRace());
        break;
      }
      default:
        //DO nothing
        break;
      }
    }
    if (countAsWar) {
      if (giver.getRuler() != null) {
        giver.getRuler().getStats().addOne(StatType.WAR_DECLARATIONS);
      }
    } else if (countAsDiplomaticTrade) {
      if (giver.getRuler() != null) {
        giver.getRuler().getStats().addOne(StatType.DIPLOMATIC_TRADE);
      }
      if (info.getRuler() != null) {
        info.getRuler().getStats().addOne(StatType.DIPLOMATIC_TRADE);
      }
    }
    return totalValue;
  }

  /**
   * Do actual trades between two players
   */
  public void doTrades() {
    PlayerInfo info = starMap.getPlayerByIndex(first);
    PlayerInfo giver = starMap.getPlayerByIndex(second);
    int value1 = doTrade(firstOffer, info, giver);
    info = starMap.getPlayerByIndex(second);
    giver = starMap.getPlayerByIndex(first);
    int value2 = doTrade(secondOffer, info, giver);
    if (Math.abs(value1 - value2) < 5
        && getSpeechTypeByOffer() == SpeechType.TRADE) {
      info = starMap.getPlayerByIndex(second);
      info.getDiplomacy().getDiplomacyList(first).addBonus(
          DiplomacyBonusType.DIPLOMATIC_TRADE, info.getRace());
      info = starMap.getPlayerByIndex(first);
      info.getDiplomacy().getDiplomacyList(second).addBonus(
          DiplomacyBonusType.DIPLOMATIC_TRADE, info.getRace());
    } else if (value1 == 0 && value2 > 0) {
      info = starMap.getPlayerByIndex(second);
      info.getDiplomacy().getDiplomacyList(first).addBonus(
          DiplomacyBonusType.GIVEN_VALUABLE_FREE, info.getRace());
    } else if (value1 > 0 && value2 == 0) {
      info = starMap.getPlayerByIndex(first);
      info.getDiplomacy().getDiplomacyList(second).addBonus(
          DiplomacyBonusType.GIVEN_VALUABLE_FREE, info.getRace());
    }
  }
  /**
   * Generate Fleet list containing all fleets from both players.
   */
  private void generateFleetList() {
    fleetListForFirst = new ArrayList<>();
    fleetListForSecond = new ArrayList<>();
    PlayerInfo info = starMap.getPlayerByIndex(first);
    PlayerInfo viewer = starMap.getPlayerByIndex(second);
    int size = info.getFleets().getNumberOfFleets();
    for (int i = 0; i < size; i++) {
      Fleet fleet = info.getFleets().getByIndex(i);
      FleetVisibility visiblity = new FleetVisibility(viewer, fleet, first);
      if (visiblity.isFleetVisible()) {
        if (fleet.isPrivateerFleet() && visiblity.isRecognized()) {
          fleetListForFirst.add(fleet);
        } else if (!fleet.isPrivateerFleet()) {
          fleetListForFirst.add(fleet);
        }
      }
    }
    info = starMap.getPlayerByIndex(second);
    viewer = starMap.getPlayerByIndex(first);
    size = info.getFleets().getNumberOfFleets();
    for (int i = 0; i < size; i++) {
      Fleet fleet = info.getFleets().getByIndex(i);
      FleetVisibility visiblity = new FleetVisibility(viewer, fleet, second);
      if (visiblity.isFleetVisible()) {
        if (fleet.isPrivateerFleet() && visiblity.isRecognized()) {
          fleetListForSecond.add(fleet);
        }  else if (!fleet.isPrivateerFleet()) {
          fleetListForSecond.add(fleet);
        }
      }
    }
  }
  /**
   * Generate Tech list containing techs whichs are tradeable.
   */
  private void generateTechList() {
    techListForFirst = new ArrayList<>();
    techListForSecond = new ArrayList<>();
    for (int type = 0; type < 6; type++) {
      Tech[] tradeTechs = starMap.getPlayerByIndex(second)
          .getTechList().getListForType(TechType
              .getTypeByIndex(type));
      Tech[] ownTechs = starMap.getPlayerByIndex(first)
          .getTechList().getListForType(TechType
              .getTypeByIndex(type));
      Tech[] techs = TechList.getTechDifference(tradeTechs, ownTechs);
      for (Tech tech : techs) {
        techListForFirst.add(tech);
      }
      tradeTechs = starMap.getPlayerByIndex(first)
          .getTechList().getListForType(TechType
              .getTypeByIndex(type));
      ownTechs = starMap.getPlayerByIndex(second)
          .getTechList().getListForType(TechType
              .getTypeByIndex(type));
      techs = TechList.getTechDifference(tradeTechs, ownTechs);
      for (Tech tech : techs) {
        techListForSecond.add(tech);
      }
    }
  }

  /**
   * Generate Planet list containing planets whichs are tradeable.
   */
  private void generatePlanetList() {
    planetListForFirst = new ArrayList<>();
    planetListForSecond = new ArrayList<>();
    PlayerInfo infoFirst = starMap.getPlayerByIndex(first);
    PlayerInfo infoSecond = starMap.getPlayerByIndex(second);
    for (Planet planet : starMap.getPlanetList()) {
      if (planet.getPlanetOwnerIndex() == first
          && infoSecond.getSectorVisibility(planet.getCoordinate())
          > PlayerInfo.UNCHARTED) {
        planetListForFirst.add(planet);
      }
      if (planet.getPlanetOwnerIndex() == second
          && infoFirst.getSectorVisibility(planet.getCoordinate())
          > PlayerInfo.UNCHARTED) {
        planetListForSecond.add(planet);
      }
    }
  }

  /**
   * Get tradeable tech list for player according the tech known
   * by the second player.
   * @return Array of Tech. Can be empty.
   */
  public Tech[] getTradeableTechListForFirst() {
    if (techListForFirst == null) {
      generateTechList();
    }
    return techListForFirst.toArray(new Tech[techListForFirst.size()]);
  }

  /**
   * Get tradeable tech list for player according the tech known
   * by the first player.
   * @return Array of Tech. Can be empty.
   */
  public Tech[] getTradeableTechListForSecond() {
    if (techListForSecond == null) {
      generateTechList();
    }
    return techListForSecond.toArray(new Tech[techListForSecond.size()]);
  }

  /**
   * Get tradeable fleet list which first player has for trade.
   * @return Array of Fleet. Can be empty.
   */
  public Fleet[] getTradeableFleetListForFirst() {
    if (fleetListForFirst == null) {
      generateFleetList();
    }
    return fleetListForFirst.toArray(new Fleet[fleetListForFirst.size()]);
  }

  /**
   * Get tradeable fleet list which second player has for trade.
   * @return Array of Fleet. Can be empty.
   */
  public Fleet[] getTradeableFleetListForSecond() {
    if (fleetListForSecond == null) {
      generateFleetList();
    }
    return fleetListForSecond.toArray(new Fleet[fleetListForSecond.size()]);
  }

  /**
   * Get tradeable planet list which first player has for trade.
   * @return Array of Planet. Can be empty.
   */
  public Planet[] getTradeablePlanetListForFirst() {
    if (planetListForFirst == null) {
      generatePlanetList();
    }
    return planetListForFirst.toArray(new Planet[planetListForFirst.size()]);
  }

  /**
   * Get tradeable planet list which second player has for trade.
   * @return Array of Planet. Can be empty.
   */
  public Planet[] getTradeablePlanetListForSecond() {
    if (planetListForSecond == null) {
      generatePlanetList();
    }
    return planetListForSecond.toArray(new Planet[planetListForSecond.size()]);
  }

  /**
   * Get First Player index
   * @return First player index
   */
  public int getFirstIndex() {
    return first;
  }

  /**
   * Get Second Player index
   * @return Second player index
   */
  public int getSecondIndex() {
    return second;
  }

  /**
   * Set offer list what First player would get.
   * @param offer NegotiationList for first player
   */
  public void setFirstOffer(final NegotiationList offer) {
    firstOffer = offer;
  }

  /**
   * Get offer list what First player would get.
   * @return NegotiationList for first player
   */
  public NegotiationList getFirstOffer() {
    return firstOffer;
  }

  /**
   * Set offer list what Second player would get.
   * @param offer NegotiationList for first player
   */
  public void setSecondOffer(final NegotiationList offer) {
    secondOffer = offer;
  }

  /**
   * Get offer list what Second player would get.
   * @return NegotiationList for second player
   */
  public NegotiationList getSecondOffer() {
    return secondOffer;
  }

  /**
   * Increase meeting numbers for both parties.
   */
  public void updateMeetingNumbers() {
    PlayerInfo firstInfo = starMap.getPlayerByIndex(first);
    PlayerInfo secondInfo = starMap.getPlayerByIndex(second);
    DiplomacyBonusList diplomacyList = secondInfo.getDiplomacy()
        .getDiplomacyList(first);
    diplomacyList.setNumberOfMeetings(diplomacyList.getNumberOfMeetings() + 1);
    diplomacyList = firstInfo.getDiplomacy().getDiplomacyList(second);
    diplomacyList.setNumberOfMeetings(diplomacyList.getNumberOfMeetings() + 1);
  }

  /**
   * Check flag for diplomacy with pirates.
   * @return True if having diplomacy with pirates.
   */
  public boolean isDiplomacyWithPirates() {
    return diplomacyWithPirates;
  }
}
