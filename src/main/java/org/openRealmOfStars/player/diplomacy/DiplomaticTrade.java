package org.openRealmOfStars.player.diplomacy;

import java.util.ArrayList;

import org.openRealmOfStars.AI.Mission.Mission;
import org.openRealmOfStars.AI.Mission.MissionPhase;
import org.openRealmOfStars.AI.Mission.MissionType;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.diplomacy.negotiation.NegotiationList;
import org.openRealmOfStars.player.diplomacy.negotiation.NegotiationOffer;
import org.openRealmOfStars.player.diplomacy.negotiation.NegotiationType;
import org.openRealmOfStars.player.diplomacy.speeches.SpeechType;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.player.tech.TechList;
import org.openRealmOfStars.player.tech.TechType;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2017 Tuomo Untinen
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
    int max = starMap.getPlayerList().getCurrentMaxPlayers();
    if (first >= max || first < 0) {
      throw new IllegalArgumentException("First player index out of bounds!");
    }
    if (second >= max || second < 0) {
      throw new IllegalArgumentException("Second player index out of bounds!");
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
    if (firstOffer.isPeaceInOffer()) {
      return SpeechType.PEACE_OFFER;
    }
    if (firstOffer.isWarInOffer()) {
      return SpeechType.MAKE_WAR;
    }
    if (firstOffer.isTypeInOffer(NegotiationType.ALLIANCE)) {
      return SpeechType.ALLIANCE;
    }
    if (firstOffer.isTypeInOffer(NegotiationType.TRADE_ALLIANCE)) {
      return SpeechType.TRADE_ALLIANCE;
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
   * Generate Tech trade between two players.
   * @param tradeType Choices are TRADE, BUY and SELL
   */
  protected void generateTechTrade(final int tradeType) {
    PlayerInfo offerMaker = starMap.getPlayerByIndex(first);
    PlayerInfo agree = starMap.getPlayerByIndex(second);
    if (techListForFirst.size() > 0
        && (tradeType == BUY || tradeType == TRADE)) {
      firstOffer = new NegotiationList();
      firstOffer.add(new NegotiationOffer(NegotiationType.TECH,
          techListForFirst.get(0)));
      int i = 0;
      secondOffer = new NegotiationList();
      do {
        if (tradeType == TRADE && techListForSecond.size() > i) {
          secondOffer.add(new NegotiationOffer(NegotiationType.TECH,
              techListForSecond.get(i)));
        } else {
          int value = firstOffer.getOfferValue(offerMaker.getRace());
          if (offerMaker.getTotalCredits() < value) {
            value = offerMaker.getTotalCredits();
          }
          secondOffer.add(new NegotiationOffer(NegotiationType.CREDIT,
              new Integer(value)));
          break;
        }
        i++;
      } while (firstOffer.getOfferValue(offerMaker.getRace())
          < secondOffer.getOfferValue(offerMaker.getRace()));
    }
    if (tradeType == SELL && techListForSecond.size() > 0) {
      int value = techListForSecond.get(0).getLevel() * 2;
      if (agree.getTotalCredits() < value) {
        value = agree.getTotalCredits();
      }
      firstOffer = new NegotiationList();
      firstOffer.add(new NegotiationOffer(NegotiationType.CREDIT,
          new Integer(value)));
      secondOffer = new NegotiationList();
      secondOffer.add(new NegotiationOffer(NegotiationType.TECH,
          techListForSecond.get(0)));
    }
  }
  /**
   * Generate credit gift
   * @param giver player who is giving the gift
   */
  protected void generateGift(final PlayerInfo giver) {
    firstOffer = new NegotiationList();
    int credit = giver.getTotalCredits();
    if (credit > 15) {
      credit = 15;
    }
    secondOffer = new NegotiationList();
    secondOffer.add(new NegotiationOffer(NegotiationType.CREDIT,
          new Integer(credit)));
  }
  /**
   * Generate tech demand or money demand
   * @param agreePlayer Who is need to agree with trade
   */
  protected void generateTechDemand(final PlayerInfo agreePlayer) {
    if (techListForFirst.size() > 0) {
      firstOffer = new NegotiationList();
      firstOffer.add(new NegotiationOffer(NegotiationType.TECH,
          techListForFirst.get(0)));
    } else {
      int credit = agreePlayer.getTotalCredits();
      if (credit > 10) {
        credit = 10;
      }
      firstOffer = new NegotiationList();
      firstOffer.add(new NegotiationOffer(NegotiationType.CREDIT,
            new Integer(credit)));
    }
    secondOffer = new NegotiationList();
  }
  /**
   * Generate Map trade between two players.
   * @param tradeType Choices are TRADE, BUY and SELL
   */
  protected void generateMapTrade(final int tradeType) {
    PlayerInfo offerMaker = starMap.getPlayerByIndex(first);
    PlayerInfo agree = starMap.getPlayerByIndex(second);
    if (tradeType == BUY) {
      int value = 15;
      if (offerMaker.getTotalCredits() < value) {
        value = offerMaker.getTotalCredits();
      }
      firstOffer = new NegotiationList();
      firstOffer.add(new NegotiationOffer(NegotiationType.MAP, null));
      secondOffer = new NegotiationList();
      secondOffer.add(new NegotiationOffer(NegotiationType.CREDIT,
          new Integer(value)));
    } else if (tradeType == TRADE) {
      firstOffer = new NegotiationList();
      firstOffer.add(new NegotiationOffer(NegotiationType.MAP, null));
      secondOffer = new NegotiationList();
      secondOffer.add(new NegotiationOffer(NegotiationType.MAP, null));
    } else {
      int value = 15;
      if (agree.getTotalCredits() < value) {
        value = agree.getTotalCredits();
      }
      firstOffer = new NegotiationList();
      firstOffer.add(new NegotiationOffer(NegotiationType.CREDIT,
          new Integer(value)));
      secondOffer = new NegotiationList();
      secondOffer.add(new NegotiationOffer(NegotiationType.MAP, null));
    }
  }

  /**
   * Generate equal trade between two players.
   * @param type Negotiation type, can be ALLIANCE, TRADE_ALLIANCE, WAR or PEACE
   */
  public void generateEqualTrade(final NegotiationType type) {
    if (type == NegotiationType.ALLIANCE
        || type == NegotiationType.TRADE_ALLIANCE
        || type == NegotiationType.WAR
        || type == NegotiationType.PEACE) {
      firstOffer = new NegotiationList();
      firstOffer.add(new NegotiationOffer(type, null));
      secondOffer = new NegotiationList();
      secondOffer.add(new NegotiationOffer(type, null));
    }
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
   * Generate first offer depending on AI's attitude.
   */
  public void generateFirstOffer() {
    PlayerInfo info = starMap.getPlayerByIndex(first);
    Attitude attitude = info.getAiAttitude();
    switch (attitude) {
      case AGGRESSIVE: {
        int power = starMap.getNewsCorpData().getMilitaryDifference(first,
            second);
        if (power > 30) {
          generateEqualTrade(NegotiationType.WAR);
        } else {
          generateEqualTrade(NegotiationType.PEACE);
        }
        break;
      }
      case MILITARISTIC: {
        int power = starMap.getNewsCorpData().getMilitaryDifference(first,
            second);
        if (power > 60) {
          generateEqualTrade(NegotiationType.WAR);
        } else {
          generateEqualTrade(NegotiationType.PEACE);
        }
        break;
      }
      case EXPANSIONIST:
      case MERCHANTICAL: {
        generateEqualTrade(NegotiationType.PEACE);
        firstOffer.add(new NegotiationOffer(NegotiationType.MAP, null));
        secondOffer.add(new NegotiationOffer(NegotiationType.MAP, null));
        break;
      }
      case BACKSTABBING:
      case DIPLOMATIC:
      case LOGICAL:
      case PEACEFUL:
      case SCIENTIFIC:
      default: {
        generateEqualTrade(NegotiationType.PEACE);
        break;
      }
    }
  }

  /**
   * Offer by aggressive attitude.
   * This makes war quite easily and can make demands.
   */
  public void generateAggressiveAttitudeOffer() {
    PlayerInfo info = starMap.getPlayerByIndex(first);
    PlayerInfo agree = starMap.getPlayerByIndex(second);
    int power = starMap.getNewsCorpData().getMilitaryDifference(first,
        second);
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
            .equals(Diplomacy.TRADE_ALLIANCE)) {
          generateEqualTrade(NegotiationType.ALLIANCE);
          return;
        }
        if (info.getDiplomacy().getDiplomaticRelation(second)
            .equals(Diplomacy.PEACE)) {
          generateEqualTrade(NegotiationType.TRADE_ALLIANCE);
          return;
        }
      }
    }
    int value = DiceGenerator.getRandom(100);
    if (value < 25) {
      generateMapTrade(TRADE);
    } else if (value < 50) {
      generateTechTrade(TRADE);
    } else {
      generateTechDemand(agree);
    }
  }

  /**
   * Offer by backstabbing attitude.
   * This makes war and demands now and then...
   */
  public void generateBackstabbingAttitudeOffer() {
    PlayerInfo info = starMap.getPlayerByIndex(first);
    PlayerInfo agree = starMap.getPlayerByIndex(second);
    int power = starMap.getNewsCorpData().getMilitaryDifference(first,
        second);
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
            .equals(Diplomacy.TRADE_ALLIANCE)) {
          generateEqualTrade(NegotiationType.ALLIANCE);
          return;
        }
        if (info.getDiplomacy().getDiplomaticRelation(second)
          .equals(Diplomacy.PEACE)) {
          generateEqualTrade(NegotiationType.TRADE_ALLIANCE);
          return;
        }
      }
    }
    if (info.getDiplomacy().getDiplomaticRelation(second)
        .equals(Diplomacy.TRADE_ALLIANCE)
        || info.getDiplomacy().getDiplomaticRelation(second)
        .equals(Diplomacy.ALLIANCE)) {
      int value = DiceGenerator.getRandom(100);
      if (value < 5) {
        generateEqualTrade(NegotiationType.WAR);
      } else if (value < 10) {
        generateTechDemand(agree);
      }
    }
    int value = DiceGenerator.getRandom(100);
    if (value < 40) {
      generateMapTrade(TRADE);
    } else {
      generateTechTrade(TRADE);
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
    int power = starMap.getNewsCorpData().getMilitaryDifference(first,
        second);
    if (power > 80 && info.getDiplomacy().getDiplomaticRelation(second)
        .isEmpty()) {
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
            .equals(Diplomacy.TRADE_ALLIANCE)) {
          generateEqualTrade(NegotiationType.ALLIANCE);
          return;
        }
        if (info.getDiplomacy().getDiplomaticRelation(second)
          .equals(Diplomacy.PEACE)) {
          generateEqualTrade(NegotiationType.TRADE_ALLIANCE);
          return;
        }
      }
    }
    int value = DiceGenerator.getRandom(100);
    if (value < 45) {
      generateMapTrade(TRADE);
    } else if (value < 90) {
      generateTechTrade(TRADE);
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
    int power = starMap.getNewsCorpData().getMilitaryDifference(first,
        second);
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
            .equals(Diplomacy.TRADE_ALLIANCE)) {
          generateEqualTrade(NegotiationType.ALLIANCE);
          return;
        }
        if (info.getDiplomacy().getDiplomaticRelation(second)
          .equals(Diplomacy.PEACE)) {
          generateEqualTrade(NegotiationType.TRADE_ALLIANCE);
          return;
        }
      }
    }
    int value = DiceGenerator.getRandom(100);
    if (value < 40) {
      generateMapTrade(TRADE);
    } else if (value < 80) {
      generateMapTrade(BUY);
    } else {
      generateTechTrade(TRADE);
    }
  }

  /**
   * Offer by scientific attitude.
   * Tries to focus trading or buying the tech
   */
  public void generateScientificAttitudeOffer() {
    PlayerInfo info = starMap.getPlayerByIndex(first);
    int power = starMap.getNewsCorpData().getMilitaryDifference(first,
        second);
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
            .equals(Diplomacy.TRADE_ALLIANCE)) {
          generateEqualTrade(NegotiationType.ALLIANCE);
          return;
        }
        if (info.getDiplomacy().getDiplomaticRelation(second)
          .equals(Diplomacy.PEACE)) {
          generateEqualTrade(NegotiationType.TRADE_ALLIANCE);
          return;
        }
      }
    }
    int value = DiceGenerator.getRandom(100);
    if (value < 40) {
      generateTechTrade(TRADE);
    } else if (value < 80) {
      generateTechTrade(BUY);
    } else {
      generateMapTrade(TRADE);
    }
  }

  /**
   * Offer by merchantical attitude.
   * This tries to buy and sell offerings
   */
  public void generateMerchanticalAttitudeOffer() {
    PlayerInfo info = starMap.getPlayerByIndex(first);
    int power = starMap.getNewsCorpData().getMilitaryDifference(first,
        second);
    if (power > 100 && info.getDiplomacy().getDiplomaticRelation(second)
        .isEmpty()) {
      generateEqualTrade(NegotiationType.WAR);
      return;
    }
    if (power > 220 && info.getDiplomacy().getDiplomaticRelation(second)
        .equals(Diplomacy.TRADE_ALLIANCE)) {
      generateEqualTrade(NegotiationType.WAR);
      return;
    }
    if (power > 440) {
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
            .equals(Diplomacy.TRADE_ALLIANCE)) {
          generateEqualTrade(NegotiationType.ALLIANCE);
          return;
        }
        if (info.getDiplomacy().getDiplomaticRelation(second)
          .equals(Diplomacy.PEACE)) {
          generateEqualTrade(NegotiationType.TRADE_ALLIANCE);
          return;
        }
      }
    }
    int value = DiceGenerator.getRandom(5);
    switch (value) {
      case 0:
      default: {
        generateMapTrade(TRADE); break;
      }
      case 1: {
        generateMapTrade(BUY); break;
      }
      case 2: {
        generateMapTrade(SELL); break;
      }
      case 3: {
        generateTechTrade(TRADE); break;
      }
      case 4: {
        generateTechTrade(BUY); break;
      }
      case 5: {
        generateTechTrade(SELL); break;
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
    int power = starMap.getNewsCorpData().getMilitaryDifference(first,
        second);
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
            .equals(Diplomacy.TRADE_ALLIANCE)) {
          generateEqualTrade(NegotiationType.ALLIANCE);
          return;
        }
        if (info.getDiplomacy().getDiplomaticRelation(second)
          .equals(Diplomacy.PEACE)) {
          generateEqualTrade(NegotiationType.TRADE_ALLIANCE);
          return;
        }
      }
    }
    int value = DiceGenerator.getRandom(100);
    if (value < 34) {
      generateMapTrade(TRADE);
    } else if (value < 67) {
      generateTechTrade(TRADE);
    } else {
      if (!info.getDiplomacy().getDiplomaticRelation(second)
          .equals(Diplomacy.ALLIANCE)) {
        generateTechDemand(agree);
      } else {
        generateMapTrade(BUY);
      }
    }
  }
  /**
   * Very basic offer by logical attitude.
   * This makes war only if power difference goes too big.
   */
  public void generateLogicalAttitudeOffer() {
    PlayerInfo info = starMap.getPlayerByIndex(first);
    int power = starMap.getNewsCorpData().getMilitaryDifference(first,
        second);
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
    if (power > 400) {
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
            .equals(Diplomacy.TRADE_ALLIANCE)) {
          generateEqualTrade(NegotiationType.ALLIANCE);
          return;
        }
        if (info.getDiplomacy().getDiplomaticRelation(second)
          .equals(Diplomacy.PEACE)) {
          generateEqualTrade(NegotiationType.TRADE_ALLIANCE);
          return;
        }
      }
    }
    int value = DiceGenerator.getRandom(100);
    if (value < 50) {
      generateMapTrade(TRADE);
    } else {
      generateTechTrade(TRADE);
    }
  }
  /**
   * Offer by peaceful attitude.
   * This makes war only if power difference goes too big.
   */
  public void generatePeacefulAttitudeOffer() {
    PlayerInfo info = starMap.getPlayerByIndex(first);
    int power = starMap.getNewsCorpData().getMilitaryDifference(first,
        second);
    if (power > 200 && info.getDiplomacy().getDiplomaticRelation(second)
        .isEmpty()) {
      generateEqualTrade(NegotiationType.WAR);
      return;
    }
    if (power > 400 && info.getDiplomacy().getDiplomaticRelation(second)
        .equals(Diplomacy.TRADE_ALLIANCE)) {
      generateEqualTrade(NegotiationType.WAR);
      return;
    }
    if (power > 600) {
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
            .equals(Diplomacy.TRADE_ALLIANCE)) {
          generateEqualTrade(NegotiationType.ALLIANCE);
          return;
        }
        if (info.getDiplomacy().getDiplomaticRelation(second)
          .equals(Diplomacy.PEACE)) {
          generateEqualTrade(NegotiationType.TRADE_ALLIANCE);
          return;
        }
      }
    }
    int value = DiceGenerator.getRandom(100);
    if (value < 50) {
      generateMapTrade(TRADE);
    } else {
      generateTechTrade(TRADE);
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
    if (offerPlayer.getDiplomacy().getDiplomacyList(second)
        .getNumberOfMeetings() == 0) {
      generateFirstOffer();
    } else {
      Attitude attitude = offerPlayer.getAiAttitude();
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
    PlayerInfo info = starMap.getPlayerByIndex(second);
    boolean isWar = info.getDiplomacy().isWar(first);
    if ((firstOffer.isPlanetInOffer() || firstOffer.isFleetInOffer())
        && (!isWar || !firstOffer.isPeaceInOffer())) {
      // AI should never give up planet or fleet unless
      // is peace is being offered
      return DECLINE_INSULT;
    }
    int difference = firstValue - secondValue;
    // Maybe good diplomatic relations help to get trade through
    int bonus = info.getDiplomacy().getDiplomacyList(first)
        .getDiplomacyBonus();
    if (bonus > 20) {
      bonus = 20;
    }
    difference = difference - bonus;
    if (info.getDiplomacy().isWar(first)
        || getSpeechTypeByOffer() == SpeechType.DEMAND
        || getSpeechTypeByOffer() == SpeechType.ASK_MOVE_FLEET
        && secondOffer.getByIndex(0).getFleet().getMilitaryValue() > 0) {
      Attitude attitude = info.getAiAttitude();
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
      int militaryDifference = starMap.getNewsCorpData().getMilitaryDifference(
          first, second);
      if (militaryDifference > 0) {
        difference = difference - militaryDifference / divider;
      } else {
        difference = difference - militaryDifference / ownDivider;
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
   * @return Chance for war. Number between 0-100.
   */
  public static int getWarChanceForDecline(final SpeechType type,
      final Attitude attitude, final int liking) {
    int warChance = 0;
    if (type == SpeechType.DEMAND) {
      warChance = 50;
      switch (attitude) {
        case AGGRESSIVE: {
          warChance = warChance + 25;
          break;
        }
        case BACKSTABBING:
        case MILITARISTIC: {
          warChance = warChance + 15;
          break;
        }
        case DIPLOMATIC: {
          warChance = warChance - 5;
          break;
        }
        case EXPANSIONIST: {
          warChance = warChance + 5;
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
          break;
        }
        case BACKSTABBING:
        case MILITARISTIC: {
          warChance = warChance + 5;
          break;
        }
        case DIPLOMATIC: {
          warChance = warChance - 5;
          break;
        }
        case EXPANSIONIST: {
          warChance = warChance + 3;
          break;
        }
        case LOGICAL:
        case SCIENTIFIC:
        default: {
          // No chance for warChance
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
   */
  private void doMapTrade(final PlayerInfo mapReceiver,
      final PlayerInfo mapGiver) {
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
    for (int i = 0; i < offerList.getSize(); i++) {
      NegotiationOffer offer = offerList.getByIndex(i);
      totalValue = totalValue + offer.getOfferValue(info.getRace());
      switch (offer.getNegotiationType()) {
      case CREDIT: {
        info.setTotalCredits(info.getTotalCredits() + offer.getCreditValue());
        giver.setTotalCredits(giver.getTotalCredits() - offer.getCreditValue());
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
              info.getMissions().add(mission);
            }

          }
        }
        break;
      }
      case TECH: {
        info.getTechList().addTech(offer.getTech());
        break;
      }
      case FLEET: {
        info.getFleets().add(offer.getFleet());
        int index = giver.getFleets().getIndexByName(offer.getFleet()
            .getName());
        giver.getFleets().remove(index);
        break;
      }
      case PLANET: {
        Planet planet = starMap.getPlanetByName(offer.getPlanet().getName());
        int index = starMap.getPlayerList().getIndex(info);
        planet.setPlanetOwner(index, info);
        break;
      }
      case MAP: {
        doMapTrade(info, giver);
        break;
      }
      case DIPLOMAT: {
        //TODO Make map trade
        break;
      }
      case TRADE_ALLIANCE: {
        int index = starMap.getPlayerList().getIndex(giver);
        info.getDiplomacy().getDiplomacyList(index).addBonus(
            DiplomacyBonusType.IN_TRADE_ALLIANCE, info.getRace());
        break;
      }
      case ALLIANCE: {
        int index = starMap.getPlayerList().getIndex(giver);
        info.getDiplomacy().getDiplomacyList(index).addBonus(
            DiplomacyBonusType.IN_ALLIANCE, info.getRace());
        break;
      }
      case PEACE: {
        int index = starMap.getPlayerList().getIndex(giver);
        info.getDiplomacy().getDiplomacyList(index).addBonus(
            DiplomacyBonusType.LONG_PEACE, info.getRace());
        break;
      }
      case WAR: {
        int index = starMap.getPlayerList().getIndex(giver);
        info.getDiplomacy().getDiplomacyList(index).addBonus(
            DiplomacyBonusType.IN_WAR, info.getRace());
        break;
      }
      default:
        //DO nothing
        break;
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
   * Generate Fleet list containg all fleets from both players.
   */
  private void generateFleetList() {
    fleetListForFirst = new ArrayList<>();
    fleetListForSecond = new ArrayList<>();
    PlayerInfo info = starMap.getPlayerByIndex(first);
    int size = info.getFleets().getNumberOfFleets();
    for (int i = 0; i < size; i++) {
      Fleet fleet = info.getFleets().getByIndex(i);
      fleetListForFirst.add(fleet);
    }
    info = starMap.getPlayerByIndex(second);
    size = info.getFleets().getNumberOfFleets();
    for (int i = 0; i < size; i++) {
      Fleet fleet = info.getFleets().getByIndex(i);
      fleetListForSecond.add(fleet);
    }
  }
  /**
   * Generate Tech list containing techs whichs are tradeable.
   */
  private void generateTechList() {
    techListForFirst = new ArrayList<>();
    techListForSecond = new ArrayList<>();
    for (int type = 0; type < 6; type++) {
      for (int lvl = 1; lvl < 11; lvl++) {
        PlayerInfo info = starMap.getPlayerByIndex(first);
        if (!info.getTechList()
            .isTechListForLevelFull(TechType.getTypeByIndex(type), lvl)) {
              Tech[] tradeTechs = starMap.getPlayerByIndex(second)
                  .getTechList().getListForTypeAndLevel(TechType
                      .getTypeByIndex(type), lvl);
              Tech[] ownTechs = starMap.getPlayerByIndex(first)
                  .getTechList().getListForTypeAndLevel(TechType
                      .getTypeByIndex(type), lvl);
              Tech[] techs = TechList.getTechDifference(tradeTechs, ownTechs);
              for (Tech tech : techs) {
                techListForFirst.add(tech);
              }
        }
        info = starMap.getPlayerByIndex(second);
        if (!info.getTechList()
            .isTechListForLevelFull(TechType.getTypeByIndex(type), lvl)) {
              Tech[] tradeTechs = starMap.getPlayerByIndex(first)
                  .getTechList().getListForTypeAndLevel(TechType
                      .getTypeByIndex(type), lvl);
              Tech[] ownTechs = starMap.getPlayerByIndex(second)
                  .getTechList().getListForTypeAndLevel(TechType
                      .getTypeByIndex(type), lvl);
              Tech[] techs = TechList.getTechDifference(tradeTechs, ownTechs);
              for (Tech tech : techs) {
                techListForSecond.add(tech);
              }
            }
      }
    }
  }

  /**
   * Generate Planet list containing planets whichs are tradeable.
   */
  private void generatePlanetList() {
    planetListForFirst = new ArrayList<>();
    planetListForSecond = new ArrayList<>();
    for (Planet planet : starMap.getPlanetList()) {
      if (planet.getPlanetOwnerIndex() == first) {
        planetListForFirst.add(planet);
      }
      if (planet.getPlanetOwnerIndex() == second) {
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

}
