package org.openRealmOfStars.player.diplomacy.speeches;

import org.openRealmOfStars.player.SpaceRace.SpaceRace;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017  Tuomo Untinen
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
* Speech factory for generating speeches
*
*/
public final class SpeechFactory {

  /**
   * Just hiding the Constructor.
   */
  private SpeechFactory() {
    // Nothing to do
  }

  /**
   * Create SpeechLine according the type and race
   * @param type SpeechType
   * @param race SpaceRace
   * @return SpeechLine or null if creating line fails
   */
  public static SpeechLine createLine(final SpeechType type,
      final SpaceRace race) {
    switch (type) {
      case AGREE: return createAgreeLine(race);
      case TRADE: return createTradeLine(race);
      case ALLIANCE: return createAllianceLine(race);
      case TRADE_ALLIANCE: return createTradeAllianceLine(race);
      default: return null;
    }
  }

  /**
   * Create Trade Alliance SpeechLine according the race
   * @param race SpaceRace
   * @return SpeechLine or null if creating line fails
   */
  private static SpeechLine createTradeAllianceLine(final SpaceRace race) {
    SpeechType type = SpeechType.TRADE_ALLIANCE;
    switch (race) {
      case CENTAURS: return new SpeechLine(type,
          "We should allow trade between our people. What do you say?");
      case GREYANS: return new SpeechLine(type,
          "We should start trade alliance. What do you think about it?");
      case HUMAN: return new SpeechLine(type,
          "Let's build trade alliance together! What do you say?");
      case MECHIONS: return new SpeechLine(type,
          "Trade alliance?");
      case MOTHOIDS: return new SpeechLine(type,
          "We offer you trade alliance! We wait your answer...");
      case SPORKS: return new SpeechLine(type,
          "Let's start non military alliance! What do you say?");
      default: return null;
    }
  }

  /**
   * Create Alliance SpeechLine according the race
   * @param race SpaceRace
   * @return SpeechLine or null if creating line fails
   */
  private static SpeechLine createAllianceLine(final SpaceRace race) {
    SpeechType type = SpeechType.ALLIANCE;
    switch (race) {
      case CENTAURS: return new SpeechLine(type,
          "We should join our forces. What do you say?");
      case GREYANS: return new SpeechLine(type,
          "We should join our venture. What do you think about it?");
      case HUMAN: return new SpeechLine(type,
          "Let's build alliance together! What do you say?");
      case MECHIONS: return new SpeechLine(type,
          "Mutual alliance?");
      case MOTHOIDS: return new SpeechLine(type,
          "Join your mind with ours! We wait your answer...");
      case SPORKS: return new SpeechLine(type,
          "Join my alliance! What do you say?");
      default: return null;
    }
  }

  /**
   * Create Agree SpeechLine according the race
   * @param race SpaceRace
   * @return SpeechLine or null if creating line fails
   */
  private static SpeechLine createAgreeLine(final SpaceRace race) {
    SpeechType type = SpeechType.AGREE;
    switch (race) {
      case CENTAURS: return new SpeechLine(type,
          "Very well, agreed!");
      case GREYANS: return new SpeechLine(type,
          "Fantastic, agreed!");
      case HUMAN: return new SpeechLine(type,
          "Glad to make business with you!");
      case MECHIONS: return new SpeechLine(type,
          "Acknowledge!");
      case MOTHOIDS: return new SpeechLine(type,
          "We accept your offer!");
      case SPORKS: return new SpeechLine(type,
          "Deal!");
      default: return null;
    }
  }

  /**
   * Create Trade SpeechLine according the race
   * @param race SpaceRace
   * @return SpeechLine or null if creating line fails
   */
  private static SpeechLine createTradeLine(final SpaceRace race) {
    SpeechType type = SpeechType.TRADE;
    switch (race) {
      case CENTAURS: return new SpeechLine(type,
          "What do you think about this?");
      case GREYANS: return new SpeechLine(type,
          "Are interested about this?");
      case HUMAN: return new SpeechLine(type,
          "How about this?");
      case MECHIONS: return new SpeechLine(type,
          "Trade?");
      case MOTHOIDS: return new SpeechLine(type,
          "We offer your kind this!");
      case SPORKS: return new SpeechLine(type,
          "What you say about this?");
      default: return null;
    }
  }

}
