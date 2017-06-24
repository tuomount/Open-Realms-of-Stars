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
      case MAKE_WAR: return createMakeWarLine(race);
      case DECLINE: return createDeclineLine(race);
      case DECLINE_ANGER: return createDeclineAngerLine(race);
      case DECLINE_WAR: return createDeclineWarLine(race);
      case DEMAND: return createDemandLine(race);
      case NEUTRAL_GREET: return createNeutralGreetLine(race);
      case DISLIKE_GREET: return createDislikeGreetLine(race);
      //TODO: Add another greet lines too
      case HATE_GREET: return createNeutralGreetLine(race);
      case LIKE_GREET: return createNeutralGreetLine(race);
      case FRIENDS_GREET: return createNeutralGreetLine(race);
      default: return null;
    }
  }

  /**
   * Create Neutral Greet SpeechLine according the race
   * @param race SpaceRace
   * @return SpeechLine or null if creating line fails
   */
  private static SpeechLine createNeutralGreetLine(final SpaceRace race) {
    SpeechType type = SpeechType.NEUTRAL_GREET;
    switch (race) {
      case CENTAURS: return new SpeechLine(type,
          "Don't be affaird of our big size.");
      case GREYANS: return new SpeechLine(type,
          "Interesting! Another sentient race!");
      case HUMAN: return new SpeechLine(type,
          "Good day! Let's get know each others!");
      case MECHIONS: return new SpeechLine(type,
          "Initiating neutral encounter protocol...");
      case MOTHOIDS: return new SpeechLine(type,
          "We come in peace!");
      case SPORKS: return new SpeechLine(type,
          "Peace or war, it's up to you!");
      default: return null;
    }
  }

  /**
   * Create Dislike Greet SpeechLine according the race
   * @param race SpaceRace
   * @return SpeechLine or null if creating line fails
   */
  private static SpeechLine createDislikeGreetLine(final SpaceRace race) {
    SpeechType type = SpeechType.DISLIKE_GREET;
    switch (race) {
      case CENTAURS: return new SpeechLine(type,
          "Hmm, it's you. We have size on our side!");
      case GREYANS: return new SpeechLine(type,
          "Meeting sentient being is interesting but are you a sentient?");
      case HUMAN: return new SpeechLine(type,
          "Think carefully what you do next...");
      case MECHIONS: return new SpeechLine(type,
          "Dislike encounter...");
      case MOTHOIDS: return new SpeechLine(type,
          "We are watching you carefully!");
      case SPORKS: return new SpeechLine(type,
          "Oh, it's you. Crew, be ready to rise shields!");
      default: return null;
    }
  }

  /**
   * Create Demand SpeechLine according the race
   * @param race SpaceRace
   * @return SpeechLine or null if creating line fails
   */
  private static SpeechLine createDemandLine(final SpaceRace race) {
    SpeechType type = SpeechType.DEMAND;
    switch (race) {
      case CENTAURS: return new SpeechLine(type,
          "You accept this offer or be stomped!");
      case GREYANS: return new SpeechLine(type,
          "Accept this offer or be anhilated!");
      case HUMAN: return new SpeechLine(type,
          "This is take it or leave deal!");
      case MECHIONS: return new SpeechLine(type,
          "Negative! Initializing war protocol!");
      case MOTHOIDS: return new SpeechLine(type,
          "We are now being insulted! We will kill all your kind!");
      case SPORKS: return new SpeechLine(type,
          "What is this? You want some war! You just got one!");
      default: return null;
    }
  }

  /**
   * Create Decline War SpeechLine according the race
   * @param race SpaceRace
   * @return SpeechLine or null if creating line fails
   */
  private static SpeechLine createDeclineWarLine(final SpaceRace race) {
    SpeechType type = SpeechType.DEMAND;
    switch (race) {
      case CENTAURS: return new SpeechLine(type,
          "This is your last insult! Prepare to be stomped!");
      case GREYANS: return new SpeechLine(type,
          "Your offer insulted us final time! Prepare to be anhilated!");
      case HUMAN: return new SpeechLine(type,
          "This was your last insult! Die!");
      case MECHIONS: return new SpeechLine(type,
          "Trade OR war protocol!");
      case MOTHOIDS: return new SpeechLine(type,
          "We offer you this and you accept it?");
      case SPORKS: return new SpeechLine(type,
          "You either take this with good or bad!");
      default: return null;
    }
  }

  /**
   * Create Decline Anger SpeechLine according the race
   * @param race SpaceRace
   * @return SpeechLine or null if creating line fails
   */
  private static SpeechLine createDeclineAngerLine(final SpaceRace race) {
    SpeechType type = SpeechType.DECLINE_ANGER;
    switch (race) {
      case CENTAURS: return new SpeechLine(type,
          "Take your offer away!");
      case GREYANS: return new SpeechLine(type,
          "Your offer insults us!");
      case HUMAN: return new SpeechLine(type,
          "I'll take this as an insult!");
      case MECHIONS: return new SpeechLine(type,
          "Negative!");
      case MOTHOIDS: return new SpeechLine(type,
          "We feel insulted about this!");
      case SPORKS: return new SpeechLine(type,
          "You call this a deal? Go away!");
      default: return null;
    }
  }

  /**
   * Create Decline SpeechLine according the race
   * @param race SpaceRace
   * @return SpeechLine or null if creating line fails
   */
  private static SpeechLine createDeclineLine(final SpaceRace race) {
    SpeechType type = SpeechType.DECLINE;
    switch (race) {
      case CENTAURS: return new SpeechLine(type,
          "I cannot accept your offer.");
      case GREYANS: return new SpeechLine(type,
          "This offer is not good enough.");
      case HUMAN: return new SpeechLine(type,
          "I am sorry, I cannot accept this.");
      case MECHIONS: return new SpeechLine(type,
          "Negative!");
      case MOTHOIDS: return new SpeechLine(type,
          "We cannot accept this!");
      case SPORKS: return new SpeechLine(type,
          "No deal!");
      default: return null;
    }
  }

  /**
   * Create Make War SpeechLine according the race
   * @param race SpaceRace
   * @return SpeechLine or null if creating line fails
   */
  private static SpeechLine createMakeWarLine(final SpaceRace race) {
    SpeechType type = SpeechType.MAKE_WAR;
    switch (race) {
      case CENTAURS: return new SpeechLine(type,
          "You will be stomped and crushed!");
      case GREYANS: return new SpeechLine(type,
          "Prepare to be anhilated!");
      case HUMAN: return new SpeechLine(type,
          "This means war!");
      case MECHIONS: return new SpeechLine(type,
          "Initializing war protocol!");
      case MOTHOIDS: return new SpeechLine(type,
          "We will kill all your kind!");
      case SPORKS: return new SpeechLine(type,
          "Shields up! Ready to fire!");
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
