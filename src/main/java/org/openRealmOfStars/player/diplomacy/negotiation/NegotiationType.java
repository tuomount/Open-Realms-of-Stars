package org.openRealmOfStars.player.diplomacy.negotiation;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017-2019,2022  Tuomo Untinen
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
* Negotiation type
*
*/
public enum NegotiationType {
  /**
   * Credit offering
   */
  CREDIT,
  /**
   * Planet offering
   */
  PLANET,
  /**
   * Fleet offering
   */
  FLEET,
  /**
   * Tech offering
   */
  TECH,
  /**
   * Peace offering
   */
  PEACE,
  /**
   * Trade alliance offering
   */
  TRADE_ALLIANCE,
  /**
   * Alliance offering
   */
  ALLIANCE,
  /**
   * Map offering
   */
  MAP,
  /**
   * Captured diplomat offering
   */
  DIPLOMAT,
  /**
   * War offering or threat
   */
  WAR,
  /**
   * Recall fleet due border crossing
   */
  RECALL_FLEET,
  /**
   * Defensive pact offering
   */
  DEFENSIVE_PACT,
  /**
   * Spy trade offering
   */
  SPY_TRADE,
  /**
   * Trade embargo offering
   */
  TRADE_EMBARGO,
  /**
   * Map containing only realm's planets
   */
  MAP_PLANETS,
  /**
   * Promise to vote yes in next important voting.
   */
  PROMISE_VOTE_YES,
  /**
   * Promise to vote no in next important voting.
   */
  PROMISE_VOTE_NO,
  /**
   * Discovered ancient artifact.
   */
  DISCOVERED_ARTIFACT,
  /**
   * Ask protection from space pirates.
   */
  ASK_PROTECTION;

}
