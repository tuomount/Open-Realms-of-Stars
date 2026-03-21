package org.openRealmOfStars.player.message;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2026 Tuomo Untinen
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
 */

/**
 * Sub type message types.
 */
public enum SmType {
  /**
   * Use when no other sub type does not fit.
   */
  GENERIC,
  /**
   * Message when assimilation happens,
   */
  ASSIMILATION,
  /**
   * Someone dies, probably leader
   */
  DEATH,
  /**
   * Starport destruction
   */
  STARPORT_DESTRUCTION,
  /**
   * ESPIONAGE
   */
  ESPIONAGE,
  /**
   * TRADE_STOP
   */
  TRADE_STOP,
  /**
   * TUTORIAL
   */
  TUTORIAL,
  /**
   * Evasion
   */
  EVASION,
  /**
   * Encounter
   */
  ENCOUNTER,
  /**
   * Voting
   */
  VOTE,
  /**
   * Paid to win, saved from death
   */
  PAID_TO_WIN,
  /**
   * Freed from jail due corruption
   */
  FREE_JAIL,
  /**
   * New ruler
   */
  NEW_RULER,
  /**
   * Corruption
   */
  CORRUPTION,
  /**
   * Waiting for command
   */
  WAITING,
  /**
   * Levelling up
   */
  LEVELUP,
  /**
   * Obstacle encountered
   */
  OBSTACLE,
  /**
   * Credits, warn about credits
   */
  CREDITS,
  /**
   * War fatigue
   */
  WAR_FATIGUE,
  /**
   * Promise
   */
  PROMISE,
  /**
   * Something related to artifact.
   */
  ARTIFACT,
  /**
   * Something related to monsters.
   */
  MONSTER,
  /**
   * Combat
   */
  COMBAT,
  /**
   * Recruit
   */
  RECRUIT,
  /**
   * Jail
   */
  JAIL,
  /**
   * Bad event happened.
   */
  BAD_EVENT,
  /**
   * Good event happened.
   */
  GOOD_EVENT,
  /**
   * Population growth.
   */
  POP_GROW,
  /**
   * Building built.
   */
  BUILT_BUILDING,
  /**
   * Culture related
   */
  CULTURE,
  /**
   * Cannot build something
   */
  CANNOT_BUILD,
  /**
   * Building related
   */
  BUILDING,
  /**
   * Ship related, usually building of ship
   */
  SHIP,
  /**
   * Happy population
   */
  HAPPY,
  /**
   * Angry Population
   */
  ANGRY,
  /**
   * Story related
   */
  STORY,
  /**
   * Anomaly found
   */
  ANOMALY,
  /**
   * Trade
   */
  TRADE;




  /**
   * Get Message type index
   * @return int
   */
  public int getIndex() {
    switch (this) {
    case GENERIC:
      return 0;
    case ASSIMILATION:
      return 1;
    case DEATH:
      return 2;
    case STARPORT_DESTRUCTION:
      return 3;
    case ESPIONAGE:
      return 4;
    case TRADE_STOP:
      return 5;
    case TUTORIAL:
      return 6;
    case EVASION:
      return 7;
    case ENCOUNTER:
      return 8;
    case VOTE:
      return 9;
    case PAID_TO_WIN:
      return 10;
    case FREE_JAIL:
      return 11;
    case NEW_RULER:
      return 12;
    case CORRUPTION:
      return 13;
    case WAITING:
      return 14;
    case LEVELUP:
      return 15;
    case OBSTACLE:
      return 16;
    case CREDITS:
      return 17;
    case WAR_FATIGUE:
      return 18;
    case PROMISE:
      return 19;
    case ARTIFACT:
      return 20;
    case MONSTER:
      return 21;
    case COMBAT:
      return 22;
    case RECRUIT:
      return 23;
    case JAIL:
      return 24;
    case BAD_EVENT:
      return 25;
    case GOOD_EVENT:
      return 26;
    case POP_GROW:
      return 27;
    case CULTURE:
      return 28;
    case CANNOT_BUILD:
      return 29;
    case BUILDING:
      return 30;
    case SHIP:
      return 31;
    case HAPPY:
      return 32;
    case ANGRY:
      return 33;
    case STORY:
      return 34;
    case ANOMALY:
      return 35;
    case TRADE:
      return 36;
    default:
      return 0;
    }
  }

  /**
   * Return Message Type by index
   * @param index The message type index
   * @return SubMessage Type
   */
  public static SmType getTypeByIndex(final int index) {
    switch (index) {
    case 0:
      return GENERIC;
    case 1:
      return ASSIMILATION;
    case 2:
      return DEATH;
    case 3:
      return STARPORT_DESTRUCTION;
    case 4:
      return ESPIONAGE;
    case 5:
      return TRADE_STOP;
    case 6:
      return TUTORIAL;
    case 7:
      return EVASION;
    case 8:
      return ENCOUNTER;
    case 9:
      return VOTE;
    case 10:
      return PAID_TO_WIN;
    case 11:
      return FREE_JAIL;
    case 12:
      return NEW_RULER;
    case 13:
      return CORRUPTION;
    case 14:
      return WAITING;
    case 15:
      return LEVELUP;
    case 16:
      return OBSTACLE;
    case 17:
      return CREDITS;
    case 18:
      return WAR_FATIGUE;
    case 19:
      return PROMISE;
    case 20:
      return ARTIFACT;
    case 21:
      return MONSTER;
    case 22:
      return COMBAT;
    case 23:
      return RECRUIT;
    case 24:
      return JAIL;
    case 25:
      return BAD_EVENT;
    case 26:
      return GOOD_EVENT;
    case 27:
      return POP_GROW;
    case 28:
      return CULTURE;
    case 29:
      return CANNOT_BUILD;
    case 30:
      return BUILDING;
    case 31:
      return SHIP;
    case 32:
      return HAPPY;
    case 33:
      return ANGRY;
    case 34:
      return STORY;
    case 35:
      return ANOMALY;
    case 36:
      return TRADE;
    default:
      return GENERIC;
    }
  }

  @Override
  public String toString() {
    switch (this) {
    case GENERIC:
      return "Generic";
    case ASSIMILATION:
      return "Assimilation";
    case DEATH:
      return "Death";
    case STARPORT_DESTRUCTION:
      return "Starport destruction";
    case ESPIONAGE:
      return "Espionage";
    case TRADE_STOP:
      return "Trade stop";
    case TUTORIAL:
      return "Tutorial";
    case EVASION:
      return "Evasion";
    case ENCOUNTER:
      return "Encounter";
    case VOTE:
      return "Vote";
    case PAID_TO_WIN:
      return "Paid, didn't die";
    case FREE_JAIL:
      return "Get out of jail free";
    case NEW_RULER:
      return "New ruler";
    case CORRUPTION:
      return "Corruption";
    case WAITING:
      return "Waiting for command";
    case LEVELUP:
      return "Level up";
    case OBSTACLE:
      return "Obstacle";
    case CREDITS:
      return "Credits";
    case WAR_FATIGUE:
      return "War fatigue";
    case PROMISE:
      return "Promise";
    case ARTIFACT:
      return "Artifact";
    case MONSTER:
      return "Monster";
    case COMBAT:
      return "Combat";
    case RECRUIT:
      return "Recruit";
    case JAIL:
      return "Jail";
    case BAD_EVENT:
      return "Bad event";
    case GOOD_EVENT:
      return "Good event";
    case POP_GROW:
      return "Population growth";
    case CULTURE:
      return "Culture";
    case CANNOT_BUILD:
      return "Cannot build";
    case BUILDING:
      return "Building";
    case SHIP:
      return "Ship";
    case HAPPY:
      return "Happy";
    case ANGRY:
      return "Angry";
    case STORY:
      return "Story";
    case ANOMALY:
      return "Anomaly";
    case TRADE:
      return "Trade";
    default:
      return "Error - Unknown";
    }

  }

}
