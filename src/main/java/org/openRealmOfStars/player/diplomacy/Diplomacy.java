package org.openRealmOfStars.player.diplomacy;

import java.awt.Color;
import java.util.ArrayList;

import org.openRealmOfStars.game.States.DiplomacyView;
import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.diplomacy.negotiation.NegotiationType;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.StarMapUtilities;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017, 2018  Tuomo Untinen
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
*
* Diplomacy for player and handling of it.
* Diplomacy creates Diplomacy Bonus lists for all players
* except the one who is creating the Diplomacy.
*
*/
public class Diplomacy {

  /**
   * How much player likes another one: Neutral
   */
  public static final int NEUTRAL = 0;

  /**
   * How much player likes another one: Like
   */
  public static final int LIKE = 1;

  /**
   * How much player likes another one: Friends
   */
  public static final int FRIENDS = 2;

  /**
   * How much player likes another one: Dislike
   */
  public static final int DISLIKE = -1;

  /**
   * How much player likes another one: Hate
   */
  public static final int HATE = -2;

  /**
   * String for Trade Alliance.
   */
  public static final String TRADE_ALLIANCE = "Trade alliance";

  /**
   * String for Peace.
   */
  public static final String PEACE = "Peace";

  /**
   * String for Alliance.
   */
  public static final String ALLIANCE = "Alliance";

  /**
   * String for Defensive pact.
   */
  public static final String DEFENSIVE_PACT = "Defensive pact";

  /**
   * String for War.
   */
  public static final String WAR = "War";

  /**
   * Diplomacy Bonus list for each player
   */
  private DiplomacyBonusList[] diplomacyList;

  /**
   * Constructor for Diplomacy for one player
   * @param maxPlayers Maximum number of players when game is created
   * @param playerIndex Which player is creating the list
   */
  public Diplomacy(final int maxPlayers, final int playerIndex) {
    diplomacyList = new DiplomacyBonusList[maxPlayers];
    for (int i = 0; i < maxPlayers; i++) {
      if (i != playerIndex) {
        diplomacyList[i] = new DiplomacyBonusList(i);
      }
    }
  }

  /**
   * Constructs Diplomacy without initializing the list
   * @param maxPlayers Number of players when game is created
   */
  public Diplomacy(final int maxPlayers) {
    diplomacyList = new DiplomacyBonusList[maxPlayers];
  }

  /**
   * Set Diplmacy List
   * @param list List to set into diplomacy
   * @param index Index where list is placed
   */
  public void setList(final DiplomacyBonusList list, final int index) {
    if (index > -1 && index < diplomacyList.length) {
      diplomacyList[index] = list;
    }
  }
  /**
   * Get diplomacy size
   * @return Diplomacy size
   */
  public int getDiplomacySize() {
    return diplomacyList.length;
  }

  /**
   * Get Player index by which diplomacy list is null.
   * There should be only one null if real game. JUnits
   * can have more than just one.
   * @return Player index
   */
  public int getPlayerIndex() {
    for (int i = 0; i < diplomacyList.length; i++) {
      if (diplomacyList[i] == null) {
        return i;
      }
    }
    throw new IllegalStateException("Player should not have diplomacy with"
        + " him/herself!");
  }
  /**
   * Get diplomacy list for player index. This can return
   * null if player index is same as who's diplomacy object this is
   * being called or index is out of bounds.
   * @param index Player index.
   * @return Diplomacy list or null
   */
  public DiplomacyBonusList getDiplomacyList(final int index) {
    if (index > -1 && index < diplomacyList.length) {
      return diplomacyList[index];
    }
    return null;
  }

  /**
   * Is certain player(index) with player who is asking in war?
   * @param index Player index
   * @return True if war is between two players
   */
  public boolean isWar(final int index) {
    if (index > -1 && index < diplomacyList.length
        && diplomacyList[index] != null) {
      return diplomacyList[index].isBonusType(DiplomacyBonusType.IN_WAR);
    }
    return false;
  }

  /**
   * Is certain player(index) with player who is asking in peace?
   * @param index Player index
   * @return True if peace is between two players
   */
  public boolean isPeace(final int index) {
    if (index > -1 && index < diplomacyList.length
        && diplomacyList[index] != null) {
      return diplomacyList[index].isBonusType(DiplomacyBonusType.LONG_PEACE);
    }
    return false;
  }

  /**
   * Is certain player(index) with player who is asking in spy trade?
   * @param index Player index
   * @return True if spy trade is between two players
   */
  public boolean isSpyTrade(final int index) {
    if (index > -1 && index < diplomacyList.length
        && diplomacyList[index] != null) {
      return diplomacyList[index].isBonusType(DiplomacyBonusType.SPY_TRADE);
    }
    return false;
  }

  /**
   * Is certain player(index) with player who is asking in spy trade?
   * @param index Player index
   * @return number of turns spy trade is lasting
   */
  public int getSpyTradeLasting(final int index) {
    if (index > -1 && index < diplomacyList.length
        && diplomacyList[index] != null) {
      return diplomacyList[index].getBonusTypeLasting(
          DiplomacyBonusType.SPY_TRADE);
    }
    return 0;
  }

  /**
   * Simply calls handleForTurn for each diplomacyBonusList.
   * Should be called after end of each turn.
   */
  public void updateDiplomacyLastingForTurn() {
    for (DiplomacyBonusList list : diplomacyList) {
      if (list != null) {
        list.handleForTurn();
      }
    }
  }
  /**
   * Is certain player(index) with player who is asking in trade alliance?
   * @param index Player index
   * @return True if trade alliance is between two players
   */
  public boolean isTradeAlliance(final int index) {
    if (index > -1 && index < diplomacyList.length
        && diplomacyList[index] != null) {
      return diplomacyList[index].isBonusType(
          DiplomacyBonusType.IN_TRADE_ALLIANCE);
    }
    return false;
  }

  /**
   * Lowest value for neutral
   */
  private static final int LOW_NEUTRAL = -5;

  /**
   * High value for neutral
   */
  private static final int HIGH_NEUTRAL = 5;

  /**
   * Low value for dislike
   */
  private static final int LOW_DISLIKE = -15;

  /**
   * High value for like
   */
  private static final int HIGH_LIKE = 15;

  /**
   * Very High value for looking least liking
   */
  private static final int VERY_HIGH_LIKE = 9999;

  /**
   * Get numeric value how much player likes another player.
   * There are five choices: HATE, DISLIKE, NEUTRAL, LIKE and FRIENDS
   * @param playerIndex Whom to check
   * @return liking value
   */
  public int getLiking(final int playerIndex) {
    int result = NEUTRAL;
    if (playerIndex > -1 && playerIndex < diplomacyList.length
        && diplomacyList[playerIndex] != null) {
      int value = diplomacyList[playerIndex].getDiplomacyBonus();
      if (value < LOW_NEUTRAL) {
        result = DISLIKE;
      }
      if (value < LOW_DISLIKE) {
        result = HATE;
      }
      if (value > HIGH_NEUTRAL) {
        result = LIKE;
      }
      if (value > HIGH_LIKE) {
        result = FRIENDS;
      }
    }
    return result;
  }

  /**
   * Get least liked player index.
   * @return Least liked player index
   */
  public int getLeastLiking() {
    int index = -1;
    int likingForIndex = VERY_HIGH_LIKE;
    for (int i = 0; i < diplomacyList.length; i++) {
      if (diplomacyList[i] != null) {
        int liking = getLiking(i);
        if (liking < likingForIndex) {
          index = i;
          likingForIndex = liking;
        }
        if (index == -1) {
          index = i;
          likingForIndex = liking;
        }
      }
    }
    return index;
  }

  /**
   * Get likess value as a String.
   * @param playerIndex whom to check
   * @return Likeness value as a string
   */
  public String getLikingAsString(final int playerIndex) {
    switch (getLiking(playerIndex)) {
    case NEUTRAL: return "Neutral";
    case DISLIKE: return "Dislike";
    case HATE: return "Hate";
    case LIKE: return "Like";
    case FRIENDS: return "Friends";
    default:
      return "Unknown";
    }
  }
  /**
   * Get likess value as a Color.
   * @param playerIndex whom to check
   * @return Likeness value as a Color
   */
  public Color getLikingAsColor(final int playerIndex) {
    switch (getLiking(playerIndex)) {
    case NEUTRAL: return GuiStatics.COLOR_DAMAGE_HALF;
    case DISLIKE: return GuiStatics.COLOR_DAMAGE_MUCH;
    case HATE: return GuiStatics.COLOR_DESTROYED;
    case LIKE: return GuiStatics.COLOR_DAMAGE_LITTLE;
    case FRIENDS: return GuiStatics.COLOR_GREEN_TEXT;
    default:
      return GuiStatics.COLOR_GREY_160;
    }
  }

  /**
   * Get diplomatic relations between two players
   * @param playerIndex PLayer index to check
   * @return String choices: "", "War", "Trade alliance", "Alliance", "Peace",
   *                         "Defensive pact
   */
  public String getDiplomaticRelation(final int playerIndex) {
    String result = "";
    if (isPeace(playerIndex)) {
      result = PEACE;
    }
    if (isTradeAlliance(playerIndex)) {
      result = TRADE_ALLIANCE;
    }
    if (isDefensivePact(playerIndex)) {
      result = DEFENSIVE_PACT;
    }
    if (isAlliance(playerIndex)) {
      result = ALLIANCE;
    }
    if (isWar(playerIndex)) {
      result = WAR;
    }
    return result;
  }
  /**
   * Is certain player(index) with player who is asking in alliance?
   * @param index Player index
   * @return True if alliance is between two players
   */
  public boolean isAlliance(final int index) {
    if (index > -1 && index < diplomacyList.length
        && diplomacyList[index] != null) {
      return diplomacyList[index].isBonusType(
          DiplomacyBonusType.IN_ALLIANCE);
    }
    return false;
  }
  /**
   * Is certain player(index) with player who is asking in defensive pact?
   * @param index Player index
   * @return True if defensive is between two players
   */
  public boolean isDefensivePact(final int index) {
    if (index > -1 && index < diplomacyList.length
        && diplomacyList[index] != null) {
      return diplomacyList[index].isBonusType(
          DiplomacyBonusType.IN_DEFENSIVE_PACT);
    }
    return false;
  }

  /**
   * Has this diplomacy with some one defensive pact?
   * @return True if defensive pact is with some one, otherwise false.
   */
  public boolean hasDefensivePact() {
    boolean result = false;
    for (int i = 0; i < diplomacyList.length; i++) {
      if (isDefensivePact(i)) {
        result = true;
      }
    }
    return result;
  }

  /**
   * Has certain player(index) multiple border crossing?
   * @param index Player index
   * @return True if there are multiple(more than 2) border crossing.
   */
  public boolean isMultipleBorderCrossing(final int index) {
    if (index > -1 && index < diplomacyList.length
        && diplomacyList[index] != null) {
      int count = 0;
      for (int i = 0; i < diplomacyList[index].getListSize(); i++) {
        DiplomacyBonus bonus = diplomacyList[index].get(i);
        if (bonus.getType() == DiplomacyBonusType.BORDER_CROSSED
            || bonus.getType() == DiplomacyBonusType.ESPIONAGE_BORDER_CROSS) {
          count++;
        }
      }
      if (count > 2) {
        return true;
      }
    }
    return false;
  }

  /**
   * Generate relation text
   * @param humanIndex Human relation index
   * @return Relation text about two realms
   */
  public String generateRelationText(final int humanIndex) {
    String text = getLikingAsString(humanIndex);
    String relation = getDiplomaticRelation(humanIndex);
    if (!relation.isEmpty()) {
      text = text + " " + relation;
    }
    return text;
  }

  /**
   * Activate defensive pact.
   * @param starMap Starmap for fetching empire names and playerinfos
   * @param attacker Player who is attacking
   * @return List of empire names being activated or null if no defensive pact
   *         available.
   */
  public String[] activateDefensivePact(final StarMap starMap,
      final PlayerInfo attacker) {
    ArrayList<String> defesiveGroupMember = new ArrayList<>();
    int attackerIndex = starMap.getPlayerList().getIndex(attacker);
    for (int i = 0; i < diplomacyList.length; i++) {
      if (isDefensivePact(i) && i != attackerIndex) {
        DiplomaticTrade trade = new DiplomaticTrade(starMap, attackerIndex, i);
        trade.generateEqualTrade(NegotiationType.WAR);
        StarMapUtilities.addWarDeclatingRepuation(starMap, attacker);
        trade.doTrades();
        PlayerInfo defender = starMap.getPlayerByIndex(i);
        defesiveGroupMember.add(defender.getEmpireName());
      }
    }
    if (defesiveGroupMember.isEmpty()) {
      return null;
    }
    return defesiveGroupMember.toArray(new String[defesiveGroupMember.size()]);
  }

  /**
   * Get Border crossing type
   * @param info PlayerInfo who owns the sector
   * @param fleet Fleet crossing the border
   * @param playerIndex Player index who owns the fleet
   * @return DiplomacyView.AI_BORDER_CROSS,
   *         DiplomacyView.AI_REGULAR,
   *         DiplomacyView.AI_ESPIONAGE,
   *         DiplomacyView.HUMAN_BORDER_CROSS,
   *         DiplomacyView.HUMAN_REGULAR,
   *         DiplomacyView.HUMAN_ESPIONAGE
   */
  public static int getBorderCrossingType(final PlayerInfo info,
      final Fleet fleet, final int playerIndex) {
    int military = fleet.getMilitaryValue();
    int type = DiplomacyView.AI_BORDER_CROSS;
    if (info.getDiplomacy().isTradeAlliance(playerIndex) && military == 0) {
      type = DiplomacyView.AI_REGULAR;
    }
    if (info.getDiplomacy().isDefensivePact(playerIndex) && military >= 0) {
      type = DiplomacyView.AI_REGULAR;
    }
    if (info.getDiplomacy().isAlliance(playerIndex) && military >= 0) {
      type = DiplomacyView.AI_REGULAR;
    }
    if (info.getSectorCloakDetection(fleet.getX(), fleet.getY())
        >= fleet.getFleetCloackingValue() + Ship.ESPIONAGE_HIDE
        && fleet.getEspionageBonus() > 0) {
      type = DiplomacyView.AI_ESPIONAGE;
    }
    if (info.getDiplomacy().isWar(playerIndex)) {
      type = DiplomacyView.AI_REGULAR;
    }
    if (fleet.isPrivateerFleet()) {
      // TODO there should be different diplomacy for privateering ships
      type = DiplomacyView.AI_REGULAR;
    }
    if (info.isHuman()) {
      if (type == DiplomacyView.AI_REGULAR) {
        type = DiplomacyView.HUMAN_REGULAR;
      }
      if (type == DiplomacyView.AI_ESPIONAGE) {
        type = DiplomacyView.HUMAN_ESPIONAGE;
      }
      if (type == DiplomacyView.AI_BORDER_CROSS) {
        type = DiplomacyView.HUMAN_BORDER_CROSS;
      }
    }
    return type;
  }

}
