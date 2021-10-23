package org.openRealmOfStars.player.diplomacy;

import java.awt.Color;
import java.util.ArrayList;

import org.openRealmOfStars.game.States.DiplomacyView;
import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.diplomacy.negotiation.NegotiationType;
import org.openRealmOfStars.player.espionage.Espionage;
import org.openRealmOfStars.player.espionage.EspionageList;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.StarMapUtilities;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017-2021 Tuomo Untinen
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
   * Limit when realm has casus belli.
   */
  public static final int CASUS_BELLI_LIMIT = 20;

  /**
   * String for Trade Alliance.
   */
  public static final String TRADE_ALLIANCE = "Trade alliance";

  /**
   * String for Peace.
   */
  public static final String PEACE = "Peace";

  /**
   * No diplomatic relation yet.
   */
  public static final String NO_RELATION = "";
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
   * String for Trade embargo
   */
  public static final String TRADE_EMBARGO = "Trade embargo";

  /**
   * Relation int for WAR
   */
  public static final int RELATION_WAR = -2;
  /**
   * Relation int for TRADE_WAR
   */
  public static final int RELATION_TRADE_WAR = -1;
  /**
   * Relation int for no relation
   */
  public static final int RELATION_NO_RELATION = 0;
  /**
   * Relation int for peace
   */
  public static final int RELATION_PEACE = 1;
  /**
   * Relation int for trade alliance
   */
  public static final int RELATION_TRADE_ALLIANCE = 2;
  /**
   * Relation int for defensive pact
   */
  public static final int RELATION_DEFENSIVE_PACT = 3;
  /**
   * Relation int for alliance
   */
  public static final int RELATION_ALLIANCE = 4;

  /**
   * Diplomacy Bonus list for each player
   */
  private DiplomacyBonusList[] diplomacyList;

  /**
   * Constructor for Diplomacy for one player
   * @param maxPlayers Maximum number of players when game is created
   * @param playerIndex Which player is creating the list
   * @param boardPlayerIndex Which player is board player
   */
  public Diplomacy(final int maxPlayers, final int playerIndex,
      final int boardPlayerIndex) {
    diplomacyList = new DiplomacyBonusList[maxPlayers];
    for (int i = 0; i < maxPlayers; i++) {
      if (i != playerIndex) {
        diplomacyList[i] = new DiplomacyBonusList(i);
        if (boardPlayerIndex != -1 && boardPlayerIndex == i) {
          diplomacyList[i].addBonus(DiplomacyBonusType.BOARD_PLAYER,
              SpaceRace.SPACE_PIRATE);
        }
      }
    }
  }

  /**
   * Constructor for Diplomacy for one player
   * @param maxPlayers Maximum number of players when game is created
   * @param playerIndex Which player is creating the list
   */
  public Diplomacy(final int maxPlayers, final int playerIndex) {
    this(maxPlayers, playerIndex, -1);
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
        && diplomacyList[index] != null
        && !diplomacyList[index].isBonusType(
            DiplomacyBonusType.BOARD_PLAYER)) {
      return diplomacyList[index].isBonusType(DiplomacyBonusType.IN_WAR);
    }
    return false;
  }

  /**
   * Is certain player(index) with player who is asking lost?
   * @param index Player index
   * @return True if realm has lost
   */
  public boolean isLost(final int index) {
    if (index > -1 && index < diplomacyList.length
        && diplomacyList[index] != null
        && !diplomacyList[index].isBonusType(
            DiplomacyBonusType.BOARD_PLAYER)) {
      return diplomacyList[index].isBonusType(DiplomacyBonusType.REALM_LOST);
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
   * Is certain player(index) with player who is asking in trade embargo?
   * @param index Player index
   * @return True if trade embargo is between two players
   */
  public boolean isTradeEmbargo(final int index) {
    if (index > -1 && index < diplomacyList.length
        && diplomacyList[index] != null) {
      return diplomacyList[index].isBonusType(
          DiplomacyBonusType.EMBARGO);
    }
    return false;
  }

  /**
   * Is realm in alliance with someone
   * @return True if has alliance with some
   */
  public boolean hasAlliance() {
    if (getAllianceIndex() != -1) {
      return true;
    }
    return false;
  }

  /**
   * Is realm in alliance with someone
   * @return Index or -1 if not alliance
   */
  public int getAllianceIndex() {
    for (int i = 0; i < diplomacyList.length; i++) {
      if (diplomacyList[i] != null
          && diplomacyList[i].isBonusType(DiplomacyBonusType.IN_ALLIANCE)) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Lowest value for neutral
   */
  private static final int LOW_NEUTRAL = -10;

  /**
   * High value for neutral
   */
  private static final int HIGH_NEUTRAL = 10;

  /**
   * Low value for dislike
   */
  private static final int LOW_DISLIKE = -20;

  /**
   * High value for like
   */
  private static final int HIGH_LIKE = 20;

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
   * Does not check board players.
   * @return Least liked player index
   */
  public int getLeastLiking() {
    int index = -1;
    int likingForIndex = VERY_HIGH_LIKE;
    for (int i = 0; i < diplomacyList.length; i++) {
      if (diplomacyList[i] != null
          && !diplomacyList[i].isBonusType(DiplomacyBonusType.BOARD_PLAYER)) {
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
   * Get Least liked realm with low espionage
   * @param espionage Realm which is doing the comparison
   * @return least liked realm index
   */
  public int getLeastLikingWithLowEspionage(final Espionage espionage) {
    int index = -1;
    int likingForIndex = VERY_HIGH_LIKE;
    int bonus = 10;
    for (int i = 0; i < diplomacyList.length; i++) {
      if (diplomacyList[i] != null) {
        int liking = getLiking(i);
        EspionageList espionageList = espionage.getByIndex(i);
        int espionageValue = 10;
        if (espionageList != null) {
          espionageValue = espionageList.getTotalBonus();
        }
        if (liking < likingForIndex && espionageValue <= 8) {
          bonus = espionageValue;
          index = i;
          likingForIndex = liking;
        }
        if (liking == likingForIndex && espionageValue < bonus) {
          bonus = espionageValue;
          index = i;
          likingForIndex = liking;
        }
        if (index == -1) {
          bonus = espionageValue;
          index = i;
          likingForIndex = liking;
        }
      }
    }
    return index;
  }
  /**
   * Get liking value as a String.
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
   * Get liking value as a Color.
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
   * Has realm casus belli against another? If realm is already war against
   * another that is not casus belli.
   * @param playerIndex Against whom casus belli?
   * @return True if casus belli.
   */
  public boolean hasCasusBelli(final int playerIndex) {
    DiplomacyBonusList list = getDiplomacyList(playerIndex);
    if (list != null && list.getCasusBelliScore() > CASUS_BELLI_LIMIT
        && !isWar(playerIndex)) {
      return true;
    }
    return false;
  }

  /**
   * Get casus belli reason as a string.
   * @param playerIndex Against whom casus belli and reason?
   * @return Casus belli reason
   */
  public String getCasusBelliReason(final int playerIndex) {
    DiplomacyBonusList list = getDiplomacyList(playerIndex);
    if (list != null && list.getCasusBelliScore() > CASUS_BELLI_LIMIT) {
      return list.getMostCassusBelli();
    }
    return "no casus belli";
  }
  /**
   * Get diplomatic relations between two players
   * @param playerIndex Player index to check
   * @return String choices: "", "War", "Trade alliance", "Alliance", "Peace",
   *                         "Defensive pact", "Trade embargo"
   */
  public String getDiplomaticRelation(final int playerIndex) {
    int value = getDiplomaticRelations(playerIndex);
    if (value == RELATION_PEACE) {
      return PEACE;
    }
    if (value == RELATION_TRADE_ALLIANCE) {
      return TRADE_ALLIANCE;
    }
    if (value == RELATION_DEFENSIVE_PACT) {
      return DEFENSIVE_PACT;
    }
    if (value == RELATION_ALLIANCE) {
      return ALLIANCE;
    }
    if (value == RELATION_TRADE_WAR) {
      return TRADE_EMBARGO;
    }
    if (value == RELATION_WAR) {
      return WAR;
    }
    return NO_RELATION;
  }

  /**
   * Get diplomatic relations between two players
   * @param playerIndex Player index to check
   * @return Relation as int. -2 is war, 4 is alliance.
   */
  public int getDiplomaticRelations(final int playerIndex) {
    int result = RELATION_NO_RELATION;
    if (isPeace(playerIndex)) {
      result = RELATION_PEACE;
    }
    if (isTradeAlliance(playerIndex)) {
      result = RELATION_TRADE_ALLIANCE;
    }
    if (isDefensivePact(playerIndex)) {
      result = RELATION_DEFENSIVE_PACT;
    }
    if (isAlliance(playerIndex)) {
      result = RELATION_ALLIANCE;
    }
    if (isTradeEmbargo(playerIndex)) {
      result = RELATION_TRADE_WAR;
    }
    if (isWar(playerIndex)) {
      result = RELATION_WAR;
    }
    if (getDiplomacyList(playerIndex) != null
        && getDiplomacyList(playerIndex).isBonusType(
        DiplomacyBonusType.BOARD_PLAYER)) {
      // Always war with board player
      result = RELATION_WAR;
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
   * Has this diplomacy with some one defensive pact?
   * @return True if defensive pact is with some one, otherwise false.
   */
  public int getNumberOfWar() {
    int result = 0;
    for (int i = 0; i < diplomacyList.length; i++) {
      if (isWar(i) && !isLost(i)) {
        result++;
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
    int count = countBorderCrossing(index);
    if (count > 2) {
        return true;
    }
    return false;
  }

  /**
   * Count number of border crossing
   * @param index Player index
   * @return Number of border crossing.
   */
  public int countBorderCrossing(final int index) {
    int count = 0;
    if (index > -1 && index < diplomacyList.length
        && diplomacyList[index] != null) {
      for (int i = 0; i < diplomacyList[index].getListSize(); i++) {
        DiplomacyBonus bonus = diplomacyList[index].get(i);
        if (bonus.getType() == DiplomacyBonusType.BORDER_CROSSED
            || bonus.getType() == DiplomacyBonusType.ESPIONAGE_BORDER_CROSS) {
          count++;
        }
      }
    }
    return count;
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
        PlayerInfo defender = starMap.getPlayerByIndex(i);
        StarMapUtilities.addWarDeclatingReputation(starMap, attacker,
            defender);
        trade.doTrades();
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
      // There is no diplomacy for privateer fleets
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
