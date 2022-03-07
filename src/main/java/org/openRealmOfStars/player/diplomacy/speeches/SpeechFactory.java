package org.openRealmOfStars.player.diplomacy.speeches;

import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017,2018  Tuomo Untinen
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
   * String for suggesting trade embargo.
   */
  public static final String TRADE_EMBARGO_SUGGESTION =
      "Suggest trade embargo (more)";
  /**
   * Create SpeechLine according the type and race
   * @param type SpeechType
   * @param race SpaceRace
   * @param dynamicContent Dynamic content used in lines,
   *        for example fleet name to move
   * @return SpeechLine or null if creating line fails
   */
  public static SpeechLine createLine(final SpeechType type,
      final SpaceRace race, final String dynamicContent) {
    switch (type) {
      case AGREE: return createAgreeLine(race);
      case TRADE: return createTradeLine(race);
      case ALLIANCE: return createAllianceLine(race);
      case TRADE_ALLIANCE: return createTradeAllianceLine(race);
      case MAKE_WAR: return createMakeWarLine(race, dynamicContent);
      case DECLINE: return createDeclineLine(race);
      case DECLINE_ANGER: return createDeclineAngerLine(race);
      case DECLINE_WAR: return createDeclineWarLine(race, dynamicContent);
      case DEMAND: return createDemandLine(race);
      case NEUTRAL_GREET: return createNeutralGreetLine(race, dynamicContent);
      case DISLIKE_GREET: return createDislikeGreetLine(race);
      case HATE_GREET: return createHateGreetLine(race);
      case LIKE_GREET: return createLikeGreetLine(race);
      case FRIENDS_GREET: return createFriendsGreetLine(race);
      case PEACE_OFFER: return createPeaceOfferLine(race);
      case INSULT_RESPOND: return createInsultRespondLine(race);
      case OFFER_REJECTED: return createOfferRejectedLine(race);
      case OFFER_ACCEPTED: return createOfferAcceptedLine(race);
      case ASK_MOVE_FLEET: return createAskMoveFleetLine(race, dynamicContent);
      case MOVE_FLEET: return createMoveFleetLine(race);
      case NOTHING_TO_TRADE: return createNothingToTradeLine(race);
      case DEFESIVE_PACT: return createDefensivePactLine(race);
      case ASK_MOVE_SPY: return createAskMoveSpyFleetLine(race, dynamicContent);
      case OFFER_SPY_TRADE: return createSpyTradeLine(race);
      case TRADE_EMBARGO: return createEmbargoLine(race, dynamicContent);
      case BORDER_WARS: return createMakeBorderWarLine(race, dynamicContent);
      default: return null;
    }
  }

  /**
   * Create Ask move fleet SpeechLine according the race
   * @param race SpaceRace
   * @param fleetName Fleet which should be moved.
   * @return SpeechLine or null if creating line fails
   */
  private static SpeechLine createAskMoveFleetLine(final SpaceRace race,
      final String fleetName) {
    SpeechType type = SpeechType.ASK_MOVE_FLEET;
    switch (race) {
      case CENTAURS: return new SpeechLine(type,
          "Hey, your fleet called " + fleetName + " is in our sector!");
      case GREYANS: return new SpeechLine(type,
          "Your fleet " + fleetName + " is on my sector!");
      case HUMAN:
      case SPACE_MONSTERS:
      case SPACE_PIRATE:
        return new SpeechLine(type,
          "Your fleet " + fleetName
          + " has crossed my sector. Please move it away!");
      case MECHIONS: return new SpeechLine(type,
          "Border crossed. Fleet " + fleetName + ". Move it!");
      case MOTHOIDS: return new SpeechLine(type,
          "We have noticed your fleet " + fleetName
          + " in our sector! Move it away immediately!");
      case SPORKS: return new SpeechLine(type,
          "Your ship has crossed our borders. Move fleet "
          + fleetName + " now away!");
      case TEUTHIDAES: return new SpeechLine(type,
          "My scanners has detected your fleet "
          + fleetName + " in our space. Move it away!");
      case SCAURIANS: return new SpeechLine(type,
          "Your fleet has crossed my borders."
          + " Move " + fleetName + " away now!");
      case HOMARIANS: return new SpeechLine(type,
          "Your fleet has entered on my sector."
          + " Move " + fleetName + " it away, please!");
      case CHIRALOIDS: return new SpeechLine(type,
          "I have detected your fleet on my sector."
          + " Pull your " + fleetName + " away, now!");
      case REBORGIANS: return new SpeechLine(type,
          "Your fleet " + fleetName
          + " is in our sector! Pull it away immediately!");
      case LITHORIANS: return new SpeechLine(type,
          "Your fleet " + fleetName
          + " has entered into my sector. Remove it!");
      case ALTEIRIANS: return new SpeechLine(type,
          "Your pod " + fleetName
          + " has detected in my sector. Move it!");
      case SMAUGIRIANS: return new SpeechLine(type,
          "I have spotted your fleet " + fleetName
          + " in my sector. Fleet must be moved!");
      case SYNTHDROIDS: return new SpeechLine(type,
          "Detected border crossing. Fleet " + fleetName + ". Move it!");
      default: return null;
    }
  }

  /**
   * Create Ask move spy fleet SpeechLine according the race
   * @param race SpaceRace
   * @param fleetName Spy Fleet which should be moved.
   * @return SpeechLine or null if creating line fails
   */
  private static SpeechLine createAskMoveSpyFleetLine(final SpaceRace race,
      final String fleetName) {
    SpeechType type = SpeechType.ASK_MOVE_SPY;
    switch (race) {
      case CENTAURS: return new SpeechLine(type,
          "Hey, your SPY FLEET called " + fleetName + " is in our sector!");
      case GREYANS: return new SpeechLine(type,
          "Your espionage fleet " + fleetName + " is on my sector!");
      case HUMAN:
        return new SpeechLine(type,
          "Your espionage fleet " + fleetName
          + " has crossed my sector. Please move it away!");
      case MECHIONS: return new SpeechLine(type,
          "Border crossed. Spy detected! Fleet " + fleetName + ". Move it!");
      case MOTHOIDS: return new SpeechLine(type,
          "We have noticed your spy fleet " + fleetName
          + " in our sector! Move it away immediately!");
      case SPORKS: return new SpeechLine(type,
          "Your spy ship has crossed our borders. Move fleet "
          + fleetName + " now away!");
      case TEUTHIDAES: return new SpeechLine(type,
          "My scanners has detected your espionage fleet "
          + fleetName + " in our space. Move it away!");
      case SCAURIANS: return new SpeechLine(type,
          "Your espionage fleet has crossed my borders."
          + " Move " + fleetName + " away now!");
      case HOMARIANS: return new SpeechLine(type,
          "Your spy fleet has enter on my sector."
          + " Move " + fleetName + " it away, please!");
      case SPACE_MONSTERS:
      case SPACE_PIRATE:
        return new SpeechLine(type,
          "Yarr! Your espionage fleet " + fleetName
          + " has crossed my sector. Tow it away!");
      case CHIRALOIDS: return new SpeechLine(type,
          "I have detected your spy fleet on my sector."
          + " Pull your " + fleetName + " away, now!");
      case REBORGIANS: return new SpeechLine(type,
          "We have detected your spy fleet "
          + fleetName + " in our sector. Pull it away!");
      case LITHORIANS: return new SpeechLine(type,
          "Your spy " + fleetName
          + " has entered into my sector. Remove it! NOW!");
      case ALTEIRIANS: return new SpeechLine(type,
          "Your spy pod " + fleetName
          + " has detected in my sector. Move it!");
      case SMAUGIRIANS: return new SpeechLine(type,
          "I have spotted your spy fleet " + fleetName
          + " in my sector. Spy must be moved!");
      case SYNTHDROIDS: return new SpeechLine(type,
          "Detected spy and border crossing. Fleet " + fleetName
              + ". Move it!");

      default: return null;
    }
  }

  /**
   * Create Nothing to trade SpeechLine according the race
   * @param race SpaceRace
   * @return SpeechLine or null if creating line fails
   */
  private static SpeechLine createNothingToTradeLine(final SpaceRace race) {
    SpeechType type = SpeechType.NOTHING_TO_TRADE;
    switch (race) {
      case CENTAURS: return new SpeechLine(type,
          "Sorry, you got nothing to trade with me.");
      case GREYANS: return new SpeechLine(type,
          "My mistake, you had nothing to trade.");
      case HUMAN: return new SpeechLine(type,
          "Sorry about this call. I thought you would have something to"
          + " trade.");
      case MECHIONS: return new SpeechLine(type,
          "Nothing to trade!");
      case MOTHOIDS: return new SpeechLine(type,
          "We found out that you had nothing we want.");
      case SPORKS: return new SpeechLine(type,
          "Hmm, You got zero tradable things...");
      case TEUTHIDAES: return new SpeechLine(type,
          "Let's talk later when you have something to trade.");
      case SCAURIANS: return new SpeechLine(type,
          "You should find something to trade! "
          + "I have excellent deals just for you.");
      case HOMARIANS: return new SpeechLine(type,
          "I am sorry, but I couldn't find anything to trade with you.");
      case SPACE_MONSTERS:
      case SPACE_PIRATE: return new SpeechLine(type,
          "Yarr, you got no loot!");
      case CHIRALOIDS: return new SpeechLine(type,
          "Hmm, oh you didn't have anything to trade...");
      case REBORGIANS: return new SpeechLine(type,
          "We see you have nothing to trade with us, except yourself!");
      case LITHORIANS: return new SpeechLine(type,
          "You don't seem to have even pebble to trade.");
      case ALTEIRIANS: return new SpeechLine(type,
          "We don't feel you have anything to trade.");
      case SMAUGIRIANS: return new SpeechLine(type,
          "You don't have anything we would like to trade...");
      case SYNTHDROIDS: return new SpeechLine(type,
          "No trade detected!");
      default: return null;
    }
  }

  /**
   * Create Move fleet SpeechLine according the race
   * @param race SpaceRace
   * @return SpeechLine or null if creating line fails
   */
  private static SpeechLine createMoveFleetLine(final SpaceRace race) {
    SpeechType type = SpeechType.MOVE_FLEET;
    switch (race) {
      case CENTAURS: return new SpeechLine(type,
          "I call my fleet back!");
      case GREYANS: return new SpeechLine(type,
          "My mistake, calling my fleet back!");
      case HUMAN: return new SpeechLine(type,
          "Sorry about the fleet. I'll call it back.");
      case MECHIONS: return new SpeechLine(type,
          "Fleet return to base!");
      case MOTHOIDS: return new SpeechLine(type,
          "We'll call the fleet back!");
      case SPORKS: return new SpeechLine(type,
          "Fine I'll call the fleet back!");
      case TEUTHIDAES: return new SpeechLine(type,
          "I'll call the sp..am..ship back!");
      case SCAURIANS: return new SpeechLine(type,
          "I will call the fleet back!");
      case HOMARIANS: return new SpeechLine(type,
          "I am calling the fleet back!");
      case SPACE_MONSTERS:
      case SPACE_PIRATE: return new SpeechLine(type,
          "Yarr, I am towing my sails back!");
      case CHIRALOIDS: return new SpeechLine(type,
          "I am pulling the fleet back!");
      case REBORGIANS: return new SpeechLine(type,
          "We will move the fleet!");
      case LITHORIANS: return new SpeechLine(type,
          "I am moving the fleet from your sector.");
      case ALTEIRIANS: return new SpeechLine(type,
          "I'll be moving our pod.");
      case SMAUGIRIANS: return new SpeechLine(type,
          "I'll move my fleet.");
      case SYNTHDROIDS: return new SpeechLine(type,
          "Handshake for fleet moving out!");
      default: return null;
    }
  }

  /**
   * Create Neutral Greet SpeechLine according the race
   * @param race SpaceRace
   * @param raceName Race name which they are meeting, not plural
   * @return SpeechLine or null if creating line fails
   */
  private static SpeechLine createNeutralGreetLine(final SpaceRace race,
      final String raceName) {
    SpeechType type = SpeechType.NEUTRAL_GREET;
    switch (race) {
      case CENTAURS: return new SpeechLine(type,
          "Don't be affaird of our big size, " +  raceName + ".");
      case GREYANS: return new SpeechLine(type,
          "Interesting! " + raceName + " is sentient race!");
      case HUMAN: return new SpeechLine(type,
          "Good day! Let's get to know each others, " + raceName + ".");
      case MECHIONS: return new SpeechLine(type,
          "Initiating neutral encounter protocol with " + raceName + ".");
      case MOTHOIDS: return new SpeechLine(type,
          "We come in peace, " + raceName + "!");
      case SPORKS: return new SpeechLine(type,
          "Peace or war, it's up to you, " + raceName + "!");
      case TEUTHIDAES: return new SpeechLine(type,
          "We appear in peace to you..." +  raceName + "!");
      case SCAURIANS: return new SpeechLine(type,
          "Let's do some trading " +  raceName + "!");
      case HOMARIANS: return new SpeechLine(type,
          "Let's do excellent deals today " +  raceName + "!");
      case SPACE_MONSTERS:
      case SPACE_PIRATE: return new SpeechLine(type,
          "Yarr, Let's look some loot " +  raceName + "!");
      case CHIRALOIDS: return new SpeechLine(type,
          "Nice to meet with you " +  raceName + "!");
      case REBORGIANS: return new SpeechLine(type,
          "We haven't decide if you will be synthesized " + raceName + "!");
      case LITHORIANS: return new SpeechLine(type,
          "Let's cool down and discuss, " + raceName + ".");
      case ALTEIRIANS: return new SpeechLine(type,
          "Let us feel for this moment, " + raceName + ".");
      case SMAUGIRIANS: return new SpeechLine(type,
          "This could be interesting, " + raceName + ".");
      case SYNTHDROIDS: return new SpeechLine(type,
          "Starting neutral encounter protocol with " + raceName + ".");
      default: return null;
    }
  }

  /**
   * Create Friends Greet SpeechLine according the race
   * @param race SpaceRace
   * @return SpeechLine or null if creating line fails
   */
  private static SpeechLine createFriendsGreetLine(final SpaceRace race) {
    SpeechType type = SpeechType.FRIENDS_GREET;
    switch (race) {
      case CENTAURS: return new SpeechLine(type,
          "My friend let's do big trades today!");
      case GREYANS: return new SpeechLine(type,
          "Most interesting to meet with you my friend!");
      case HUMAN: return new SpeechLine(type,
          "Good day, friend! Let's sit down and talk a bit!");
      case MECHIONS: return new SpeechLine(type,
          "Peer encountered!");
      case MOTHOIDS: return new SpeechLine(type,
          "Our friend! We are ready to talk with you!");
      case SPORKS: return new SpeechLine(type,
          "Ah my trustful friend. Let's talk!");
      case TEUTHIDAES: return new SpeechLine(type,
          "My friend, let's shake tentacles!");
      case SCAURIANS: return new SpeechLine(type,
          "My friend, I have my pockets full of credits! Let's do business.");
      case HOMARIANS: return new SpeechLine(type,
          "My fellow, let's shake chelae!");
      case SPACE_MONSTERS:
      case SPACE_PIRATE: return new SpeechLine(type,
          "Matey, let's split some loot!");
      case CHIRALOIDS: return new SpeechLine(type,
          "My fellow, let's talk a while...");
      case REBORGIANS: return new SpeechLine(type,
          "Our thrall! Speak, we listen.");
      case LITHORIANS: return new SpeechLine(type,
          "Greetings, my fellow friend!");
      case ALTEIRIANS: return new SpeechLine(type,
          "Ah my friend!");
      case SMAUGIRIANS: return new SpeechLine(type,
          "Ah good old pal. Let's sit down and talk.");
      case SYNTHDROIDS: return new SpeechLine(type,
          "Starting peer encounter!");
      default: return null;
    }
  }

  /**
   * Create Like Greet SpeechLine according the race
   * @param race SpaceRace
   * @return SpeechLine or null if creating line fails
   */
  private static SpeechLine createLikeGreetLine(final SpaceRace race) {
    SpeechType type = SpeechType.LIKE_GREET;
    switch (race) {
      case CENTAURS: return new SpeechLine(type,
          "Nice to meet you again!");
      case GREYANS: return new SpeechLine(type,
          "Interesting! Another sentient race that I like!");
      case HUMAN: return new SpeechLine(type,
          "Good day! I'll hope we can make business today.");
      case MECHIONS: return new SpeechLine(type,
          "Initiating liking encounter...");
      case MOTHOIDS: return new SpeechLine(type,
          "We like to meet with you!");
      case SPORKS: return new SpeechLine(type,
          "Ah the one I like to do business.");
      case TEUTHIDAES: return new SpeechLine(type,
          "Nice to appear to you...");
      case SCAURIANS: return new SpeechLine(type,
          "Fellow trade companion, nice to meet again!");
      case HOMARIANS: return new SpeechLine(type,
          "Nice to meet you again, my fellow!");
      case SPACE_MONSTERS:
      case SPACE_PIRATE: return new SpeechLine(type,
          "Yarr!");
      case CHIRALOIDS: return new SpeechLine(type,
          "Nice to see you, again!");
      case REBORGIANS: return new SpeechLine(type,
          "We might not synthesize you, for now.");
      case LITHORIANS: return new SpeechLine(type,
          "Nice to see warm person in cool space.");
      case ALTEIRIANS: return new SpeechLine(type,
          "It is nice to see you again!");
      case SMAUGIRIANS: return new SpeechLine(type,
          "Well met, my pal!");
      case SYNTHDROIDS: return new SpeechLine(type,
          "Starting liking encounter...");
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
      case TEUTHIDAES: return new SpeechLine(type,
          "Hmm, it's you mud dirt...");
      case SCAURIANS: return new SpeechLine(type,
          "Hmph, the one with no credits...");
      case HOMARIANS: return new SpeechLine(type,
          "Just be quick and tell your thing...");
      case SPACE_MONSTERS:
      case SPACE_PIRATE: return new SpeechLine(type,
          "Hmph, yarr...");
      case CHIRALOIDS: return new SpeechLine(type,
          "Okay, I'll listen but be quick!");
      case REBORGIANS: return new SpeechLine(type,
          "YES?!");
      case LITHORIANS: return new SpeechLine(type,
          "Ah, something to put into smelting pot and eat up...");
      case ALTEIRIANS: return new SpeechLine(type,
          "Hmm, do I smell foul gases here?");
      case SMAUGIRIANS: return new SpeechLine(type,
          "Are you ready to make proposal?");
      case SYNTHDROIDS: return new SpeechLine(type,
          "Preparing for negative meeting...");
      default: return null;
    }
  }

  /**
   * Create Hate Greet SpeechLine according the race
   * @param race SpaceRace
   * @return SpeechLine or null if creating line fails
   */
  private static SpeechLine createHateGreetLine(final SpaceRace race) {
    SpeechType type = SpeechType.HATE_GREET;
    switch (race) {
      case CENTAURS: return new SpeechLine(type,
          "Be careful what you say or you'll be stomped!");
      case GREYANS: return new SpeechLine(type,
          "Ah non-sentient spieces has learn how to speak...");
      case HUMAN: return new SpeechLine(type,
          "This meeting is something we both hate, but let's try it anyway!");
      case MECHIONS: return new SpeechLine(type,
          "Hate encounter...");
      case MOTHOIDS: return new SpeechLine(type,
          "We hate meetings with you but let's see your offer!");
      case SPORKS: return new SpeechLine(type,
          "I am glad to see you when my targeting system is locked on you!");
      case TEUTHIDAES: return new SpeechLine(type,
          "Ah my nemesis...");
      case SCAURIANS: return new SpeechLine(type,
          "This is going to be a fight, but...");
      case HOMARIANS: return new SpeechLine(type,
          "I really hate these meeting with you...");
      case SPACE_MONSTERS:
      case SPACE_PIRATE: return new SpeechLine(type,
          "Yarr, I'll be needing sum rum after this!");
      case CHIRALOIDS: return new SpeechLine(type,
          "Watch your tongue or I'll rip it off!");
      case REBORGIANS: return new SpeechLine(type,
          "Synthesis material, speak now!");
      case LITHORIANS: return new SpeechLine(type,
          "Be quick or I will explode...");
      case ALTEIRIANS: return new SpeechLine(type,
          "Urgh, I can feel and smell the foul gases...");
      case SMAUGIRIANS: return new SpeechLine(type,
          "Uh, oh. This could end really bad or...");
      case SYNTHDROIDS: return new SpeechLine(type,
          "Starting hate encounter...");
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
          "This is take it or leave it deal!");
      case MECHIONS: return new SpeechLine(type,
          "Accept this OR initialize war!");
      case MOTHOIDS: return new SpeechLine(type,
          "We demand you to accept this!");
      case SPORKS: return new SpeechLine(type,
          "You take this deal or it war!");
      case TEUTHIDAES: return new SpeechLine(type,
          "Please take this deal or suffer!");
      case SCAURIANS: return new SpeechLine(type,
          "You better accept this deal or suffer!");
      case HOMARIANS: return new SpeechLine(type,
          "Accept this deal or prepare to be boiled!");
      case SPACE_MONSTERS:
      case SPACE_PIRATE: return new SpeechLine(type,
          "Yarr, Accept this or slice your throat!");
      case CHIRALOIDS: return new SpeechLine(type,
          "Agree or burn in nuclear fire!");
      case REBORGIANS: return new SpeechLine(type,
          "You will accept this or we will synthesize your species!");
      case LITHORIANS: return new SpeechLine(type,
          "Accept this or I will eat you like a pebble stone!");
      case ALTEIRIANS: return new SpeechLine(type,
          "Accept this offer or you will feel our wrath!");
      case SMAUGIRIANS: return new SpeechLine(type,
          "Quick, accept this or you will suffer!");
      case SYNTHDROIDS: return new SpeechLine(type,
          "Handshake this OR start war protocol!");
      default: return null;
    }
  }

  /**
   * Create Decline War SpeechLine according the race
   * @param race SpaceRace
   * @param casusBelli Casus belli indicator
   * @return SpeechLine or null if creating line fails
   */
  private static SpeechLine createDeclineWarLine(final SpaceRace race,
      final String casusBelli) {
    SpeechType type = SpeechType.DECLINE_WAR;
    String extra = "";
    if (casusBelli != null) {
      extra = casusBelli;
    }
    switch (race) {
      case CENTAURS: return new SpeechLine(type,
          "This is your last insult! Prepare to be stomped!" + extra);
      case GREYANS: return new SpeechLine(type,
          "Your offer insulted us final time! Prepare to be anhilated!"
      + extra);
      case HUMAN: return new SpeechLine(type,
          "This was your last insult! Die!" + extra);
      case MECHIONS: return new SpeechLine(type,
          "Initiating war protocol!" + extra);
      case MOTHOIDS: return new SpeechLine(type,
          "We offer you this and you accept it?" + extra);
      case SPORKS: return new SpeechLine(type,
          "You either take this with good or bad!" + extra);
      case TEUTHIDAES: return new SpeechLine(type,
          "You will pay for your insults!" + extra);
      case SCAURIANS: return new SpeechLine(type,
          "I will take last credits from your cold body!" + extra);
      case HOMARIANS: return new SpeechLine(type,
          "Prepare to be sliced!" + extra);
      case SPACE_MONSTERS:
      case SPACE_PIRATE: return new SpeechLine(type,
          "Yarr, Prepate to be boarded and destroyed!" + extra);
      case CHIRALOIDS: return new SpeechLine(type,
          "I say no and I will nuke you now!" + extra);
      case REBORGIANS: return new SpeechLine(type,
          "We will not accept but your species is good synthesize"
          + " material for us!" + extra);
      case LITHORIANS: return new SpeechLine(type,
          "This offer is so bad, I'll explode and prepare yourself"
          + " for war!" + extra);
      case ALTEIRIANS: return new SpeechLine(type,
          "Your offer is full gas and you will feel our wrath!" + extra);
      case SMAUGIRIANS: return new SpeechLine(type,
          "You shall pay for your nonsense!" + extra);
      case SYNTHDROIDS: return new SpeechLine(type,
          "Starting war protocol!" + extra);
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
      case MECHIONS: {
        // Mechions do not know how to decline with anger
        type = SpeechType.DECLINE;
        return new SpeechLine(type, "Negative!");
      }
      case MOTHOIDS: return new SpeechLine(type,
          "We feel insulted about this!");
      case SPORKS: return new SpeechLine(type,
          "You call this a deal? Go away!");
      case TEUTHIDAES: return new SpeechLine(type,
          "Your offer repulses me.");
      case SCAURIANS: return new SpeechLine(type,
          "Your offer insults me!");
      case HOMARIANS: return new SpeechLine(type,
          "Your offer stinks ancient mold!");
      case SPACE_MONSTERS:
      case SPACE_PIRATE: return new SpeechLine(type,
          "Yarr, My socks smell better than your offer!");
      case CHIRALOIDS: return new SpeechLine(type,
          "Your offer is mad!");
      case REBORGIANS: return new SpeechLine(type,
          "We will not accept this!");
      case LITHORIANS: return new SpeechLine(type,
          "This offer makes me blazing hot and I getting close to explode!");
      case ALTEIRIANS: return new SpeechLine(type,
          "This offer is full of ether and gases!");
      case SMAUGIRIANS: return new SpeechLine(type,
          "What kind offer is this? There is not enough for me!");
      case SYNTHDROIDS: {
        // Synthdroids do not know how to decline with anger
        type = SpeechType.DECLINE;
        return new SpeechLine(type, "Cannot comply!");
      }
      default: return null;
    }
  }

  /**
   * Create respond for insult SpeechLine according the race
   * @param race SpaceRace
   * @return SpeechLine or null if creating line fails
   */
  private static SpeechLine createInsultRespondLine(final SpaceRace race) {
    SpeechType type = SpeechType.INSULT_RESPOND;
    switch (race) {
      case CENTAURS: return new SpeechLine(type,
          "You are rude, we should stomp on you!");
      case GREYANS: return new SpeechLine(type,
          "You do not seem sentient at all.");
      case HUMAN: return new SpeechLine(type,
          "Hmm, childish insulting...");
      case MECHIONS: return new SpeechLine(type,
          "Core dumped?");
      case MOTHOIDS: return new SpeechLine(type,
          "You are acting like a larvae!");
      case SPORKS: return new SpeechLine(type,
          "You are insulting me? How dare you!");
      case TEUTHIDAES: return new SpeechLine(type,
          "You are getting on my nerves!");
      case SCAURIANS: return new SpeechLine(type,
          "Insults do not help here!");
      case HOMARIANS: return new SpeechLine(type,
          "You are making fool of yourself with your insults!");
      case SPACE_MONSTERS:
      case SPACE_PIRATE: return new SpeechLine(type,
          "Yarr, You start getting my nerve!");
      case CHIRALOIDS: return new SpeechLine(type,
          "You seem quiet mad... Are you?");
      case REBORGIANS: return new SpeechLine(type,
          "Adding cybergenetic implants might help you!");
      case LITHORIANS: return new SpeechLine(type,
          "You seem like a good fuel for furnace...");
      case ALTEIRIANS: return new SpeechLine(type,
          "You are full of gases and you seem to leak it...");
      case SMAUGIRIANS: return new SpeechLine(type,
          "You are offer is full of junk, like yourself!");
      case SYNTHDROIDS: return new SpeechLine(type,
          "Guru mediatation?");
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
      case TEUTHIDAES: return new SpeechLine(type,
          "Offer does not please me.");
      case SCAURIANS: return new SpeechLine(type,
          "Your offer isn't good enough!");
      case HOMARIANS: return new SpeechLine(type,
          "I have to turn down this offer.");
      case SPACE_MONSTERS:
      case SPACE_PIRATE: return new SpeechLine(type,
          "Yarr, I cannot accept this!");
      case CHIRALOIDS: return new SpeechLine(type,
          "No, no, and no thanks!");
      case REBORGIANS: return new SpeechLine(type,
          "We will not accept this!");
      case LITHORIANS: return new SpeechLine(type,
          "I'll have to cool down this offer.");
      case ALTEIRIANS: return new SpeechLine(type,
          "I cannot accept your offer.");
      case SMAUGIRIANS: return new SpeechLine(type,
          "Your offer is not high enough.");
      case SYNTHDROIDS: return new SpeechLine(type,
          "Handshake failure!");
      default: return null;
    }
  }

  /**
   * Create Offer rejected SpeechLine according the race
   * @param race SpaceRace
   * @return SpeechLine or null if creating line fails
   */
  private static SpeechLine createOfferRejectedLine(final SpaceRace race) {
    SpeechType type = SpeechType.OFFER_REJECTED;
    switch (race) {
      case CENTAURS: return new SpeechLine(type,
          "So maybe next time then...");
      case GREYANS: return new SpeechLine(type,
          "Hmm, do you have counter offer?");
      case HUMAN: return new SpeechLine(type,
          "I am waiting your counter offer then.");
      case MECHIONS: return new SpeechLine(type,
          "Acknowledge! Offer?");
      case MOTHOIDS: return new SpeechLine(type,
          "We are waiting your counter offer...");
      case SPORKS: return new SpeechLine(type,
          "So, do you have an offer?");
      case TEUTHIDAES: return new SpeechLine(type,
          "I understand. Please do you have counter offer?");
      case SCAURIANS: return new SpeechLine(type,
          "Do you have counter offer?");
      case HOMARIANS: return new SpeechLine(type,
          "Any counter offers then?");
      case SPACE_MONSTERS:
      case SPACE_PIRATE: return new SpeechLine(type,
          "Yarr, I'll wait your counter offer then...");
      case CHIRALOIDS: return new SpeechLine(type,
          "What do you propose then?");
      case REBORGIANS: return new SpeechLine(type,
          "Our offer was not good enough, what do you offer then?");
      case LITHORIANS: return new SpeechLine(type,
          "Suggest your counter offer then...");
      case ALTEIRIANS: return new SpeechLine(type,
          "So, what is your offer then?");
      case SMAUGIRIANS: return new SpeechLine(type,
          "I am waiting your offer then...");
      case SYNTHDROIDS: return new SpeechLine(type,
          "Handshake failre! Restart offer?");
      default: return null;
    }
  }

  /**
   * Create Make War SpeechLine according the race
   * @param race SpaceRace
   * @param casusBelli Casus belli indicator
   * @return SpeechLine or null if creating line fails
   */
  private static SpeechLine createMakeWarLine(final SpaceRace race,
      final String casusBelli) {
    SpeechType type = SpeechType.MAKE_WAR;
    String extra = "";
    if (casusBelli != null) {
      extra = casusBelli;
    }
    switch (race) {
      case CENTAURS: return new SpeechLine(type,
          "You will be stomped and crushed!" + extra);
      case GREYANS: return new SpeechLine(type,
          "Prepare to be anhilated!" + extra);
      case HUMAN: return new SpeechLine(type,
          "This means war!" + extra);
      case MECHIONS: return new SpeechLine(type,
          "Initializing war protocol!" + extra);
      case MOTHOIDS: return new SpeechLine(type,
          "We will kill all your kind!" + extra);
      case SPORKS: return new SpeechLine(type,
          "Shields up! Ready to fire!" + extra);
      case TEUTHIDAES: return new SpeechLine(type,
          "Prepare to die!" + extra);
      case SCAURIANS: return new SpeechLine(type,
          "Time to die!" + extra);
      case HOMARIANS: return new SpeechLine(type,
          "Time to get sliced and boiled!" + extra);
      case SPACE_MONSTERS:
      case SPACE_PIRATE: return new SpeechLine(type,
          "Yarr, Time to die!" + extra);
      case CHIRALOIDS: return new SpeechLine(type,
          "Prepare to be nuked!" + extra);
      case REBORGIANS: return new SpeechLine(type,
          "We will start synthesize with your species!" + extra);
      case LITHORIANS: return new SpeechLine(type,
          "Prepare to encounter hot blazing war from us!" + extra);
      case ALTEIRIANS: return new SpeechLine(type,
          "Prepare to to feel wrath from us!" + extra);
      case SMAUGIRIANS: return new SpeechLine(type,
          "Get ready for fight!" + extra);
      case SYNTHDROIDS: return new SpeechLine(type,
          "Starting war protocol!" + extra);
      default: return null;
    }
  }

  /**
   * Create Make War SpeechLine according the race
   * @param race SpaceRace
   * @param fleetName which was last fleet crossing the border
   * @return SpeechLine or null if creating line fails
   */
  private static SpeechLine createMakeBorderWarLine(final SpaceRace race,
      final String fleetName) {
    SpeechType type = SpeechType.BORDER_WARS;
    switch (race) {
      case CENTAURS: return new SpeechLine(type,
          "Your " + fleetName + " will be stomped and crushed along with you!");
      case GREYANS: return new SpeechLine(type,
          fleetName + " prepare to be anhilated!");
      case HUMAN: return new SpeechLine(type,
          "Your fleet " + fleetName + " has crossed our borders!"
              + " This means war!");
      case MECHIONS: return new SpeechLine(type,
          "Fleet " + fleetName + " detected again! Initializing war protocol!");
      case MOTHOIDS: return new SpeechLine(type,
          "We have detected " + fleetName + "! We will kill all your kind!");
      case SPORKS: return new SpeechLine(type,
          "Intruder " + fleetName + "! Shields up! Ready to fire!");
      case TEUTHIDAES: return new SpeechLine(type,
          "Fleet " + fleetName + " located. Prepare to die!");
      case SCAURIANS: return new SpeechLine(type,
          "Fleet " + fleetName + " has entered our sector! Time to die!");
      case HOMARIANS: return new SpeechLine(type,
          "Fleet " + fleetName + "has been seen."
              + " Time to get sliced and boiled!");
      case SPACE_MONSTERS:
      case SPACE_PIRATE: return new SpeechLine(type,
          "Yarr, scurvy fleet " + fleetName + " spotted. Time to die!");
      case CHIRALOIDS: return new SpeechLine(type,
          "Your fleet " + fleetName + " has betrayed us."
              + " Prepare to be nuked!");
      case REBORGIANS: return new SpeechLine(type,
          "Our scanners have detected " + fleetName + " in our sectors."
              + "We will start synthesize with your species!");
      case LITHORIANS: return new SpeechLine(type,
          "Your puny " + fleetName + " has been detected."
              + " Prepare to encounter hot blazing war from us!");
      case ALTEIRIANS: return new SpeechLine(type,
          "Your puny pod " + fleetName + " has been detected."
              + " Prepare to feel the wrath from us!");
      case SMAUGIRIANS: return new SpeechLine(type,
          "Your puny fleet " + fleetName + " is seen."
              + " Prepare to fight with me!");
      case SYNTHDROIDS: return new SpeechLine(type,
          "Detected fleet " + fleetName + " again! Starting war protocol!");
      default: return null;
    }
  }

  /**
   * Create Espionage Trade SpeechLine according the race
   * @param race SpaceRace
   * @return SpeechLine or null if creating line fails
   */
  private static SpeechLine createSpyTradeLine(final SpaceRace race) {
    SpeechType type = SpeechType.OFFER_SPY_TRADE;
    switch (race) {
      case CENTAURS: return new SpeechLine(type,
          "Let's change information for 20 turns?");
      case GREYANS: return new SpeechLine(type,
          "Let's trade espionage information for 20 turns?");
      case HUMAN: return new SpeechLine(type,
          "How about some intelligence trades for 20 turns?");
      case MECHIONS: return new SpeechLine(type,
          "Espionage information? 20 turns?");
      case MOTHOIDS: return new SpeechLine(type,
          "Join your spies with our spies for 20 turns?");
      case SPORKS: return new SpeechLine(type,
          "How about share spy information for 20 turns?");
      case TEUTHIDAES: return new SpeechLine(type,
          "Are you interested in intellingece trading for 20 turns?");
      case SCAURIANS: return new SpeechLine(type,
          "Let's build together awesome spy network for 20 turns?");
      case HOMARIANS: return new SpeechLine(type,
          "Do you have spies? We should work together for 20 turns?");
      case SPACE_MONSTERS:
      case SPACE_PIRATE: return new SpeechLine(type,
          "Yarr, Are you interested in espionage trade for 20 turns?");
      case CHIRALOIDS: return new SpeechLine(type,
          "Are you willing to trade intel for 20 turns?");
      case REBORGIANS: return new SpeechLine(type,
          "We are offering you our spy information for 20 turns. Interested?");
      case LITHORIANS: return new SpeechLine(type,
          "I offer you our spy information for 20 turns. Interested?");
      case ALTEIRIANS: return new SpeechLine(type,
          "Are you interested joining to my spy network for 20 turns?");
      case SMAUGIRIANS: return new SpeechLine(type,
          "I have good intel for you 20 turns. Interested?");
      case SYNTHDROIDS: return new SpeechLine(type,
          "Handshake for espionage information? 20 turns?");
      default: return null;
    }
  }

  /**
   * Create Trade embargo SpeechLine according the race
   * @param race SpaceRace
   * @param empire Realm against who to make trade embargo.
   * @return SpeechLine or null if creating line fails
   */
  private static SpeechLine createEmbargoLine(final SpaceRace race,
      final String empire) {
    SpeechType type = SpeechType.TRADE_EMBARGO;
    switch (race) {
      case CENTAURS: return new SpeechLine(type,
          "Let's do trade embargo for 20 turns for " + empire + "?");
      case GREYANS: return new SpeechLine(type,
          "Let's make embargo for 20 turns against " + empire + "?");
      case HUMAN: return new SpeechLine(type,
          "How about trade embargo for 20 turns against " + empire + "?");
      case MECHIONS: return new SpeechLine(type,
          "Trade embargo? 20 turns? " + empire + "?");
      case MOTHOIDS: return new SpeechLine(type,
          "We should turn our backs for 20 turns against " + empire + ".");
      case SPORKS: return new SpeechLine(type,
          "How about embargo for 20 turns for " + empire + "?");
      case TEUTHIDAES: return new SpeechLine(type,
          "Are you interested in embargo for 20 against " + empire + "?");
      case SCAURIANS: return new SpeechLine(type,
          "Let's build embargo of trade for 20 turns for " + empire + "?");
      case HOMARIANS: return new SpeechLine(type,
          "We should work together embargo for 20 turns against " + empire
              + "?");
      case SPACE_MONSTERS:
      case SPACE_PIRATE: return new SpeechLine(type,
          "Yarr, Are you interested in trade emparbo for 20 turns against "
              + empire + "?");
      case CHIRALOIDS: return new SpeechLine(type,
          "Are you willing make trade embargo for 20 turns against "
              + empire + "?");
      case REBORGIANS: return new SpeechLine(type,
          "We ask you to make trade embargo for 20 turns against "
              + empire + "?");
      case LITHORIANS: return new SpeechLine(type,
          "Are you will to do trade embargo for 20 turns against "
              + empire + "?");
      case ALTEIRIANS: return new SpeechLine(type,
          "Will you accept trade embargo for 20 turns against "
              + empire + "?");
      case SMAUGIRIANS: return new SpeechLine(type,
          "Let's put "
              + empire + " to trade embargo for 20 turns?");
      case SYNTHDROIDS: return new SpeechLine(type,
          "Handshake for trade embargo? 20 turns? " + empire + "?");
      default: return null;
    }
  }

  /**
   * Create line for suggesting trade embargo, this is only
   * for human UI.
   * @return Speechline for suggesting trade embargo
   */
  public static SpeechLine createEmbargoSuggestion() {
    SpeechType type = SpeechType.TRADE_EMBARGO;
    return new SpeechLine(type, TRADE_EMBARGO_SUGGESTION);
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
          "We should start trading between!");
      case GREYANS: return new SpeechLine(type,
          "We should start trade alliance!");
      case HUMAN: return new SpeechLine(type,
          "Let's build trade alliance together!");
      case MECHIONS: return new SpeechLine(type,
          "Trade alliance?");
      case MOTHOIDS: return new SpeechLine(type,
          "We offer you trade alliance!");
      case SPORKS: return new SpeechLine(type,
          "Let's start non military alliance!");
      case TEUTHIDAES: return new SpeechLine(type,
          "Let's start a trade union!");
      case SCAURIANS: return new SpeechLine(type,
          "Let's build magnificent trade alliance!");
      case HOMARIANS: return new SpeechLine(type,
          "Are you insterested in trade alliance?");
      case SPACE_MONSTERS:
      case SPACE_PIRATE: return new SpeechLine(type,
          "Yarr, Are you interested in trade alliance?");
      case CHIRALOIDS: return new SpeechLine(type,
          "Let's form a trade alliance!");
      case REBORGIANS: return new SpeechLine(type,
          "Your species has trading value for us, trade alliance?");
      case LITHORIANS: return new SpeechLine(type,
          "Let's start trading together, what is your answer?");
      case ALTEIRIANS: return new SpeechLine(type,
          "Let's join to mutual trading, what say you?");
      case SMAUGIRIANS: return new SpeechLine(type,
          "How about if we start to trade?");
      case SYNTHDROIDS: return new SpeechLine(type,
          "Handshake for trade alliance?");
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
          "We should join our venture. ");
      case HUMAN: return new SpeechLine(type,
          "Let's build alliance together!");
      case MECHIONS: return new SpeechLine(type,
          "Mutual alliance?");
      case MOTHOIDS: return new SpeechLine(type,
          "Join your mind with ours!");
      case SPORKS: return new SpeechLine(type,
          "Join my alliance! What do you say?");
      case TEUTHIDAES: return new SpeechLine(type,
          "Join my side to alliance!");
      case SCAURIANS: return new SpeechLine(type,
          "Let's build awesome alliance!");
      case HOMARIANS: return new SpeechLine(type,
          "Are you insterested in alliance?");
      case SPACE_MONSTERS:
      case SPACE_PIRATE: return new SpeechLine(type,
          "Yarr, Are you interested in alliance?");
      case CHIRALOIDS:
        return new SpeechLine(type,
          "Are you ready to join my alliance?");
      case REBORGIANS: return new SpeechLine(type,
          "Be our thrall or be our future synthesize material."
          + " Join to our alliance!");
      case LITHORIANS: return new SpeechLine(type,
          "Let's stand together like rocks and defend ourselves!");
      case ALTEIRIANS: return new SpeechLine(type,
          "We should join side by side to rule the galaxy!");
      case SMAUGIRIANS: return new SpeechLine(type,
          "We should join to same alliance, what do you think?");
      case SYNTHDROIDS: return new SpeechLine(type,
          "Handshake for alliance?");
      default: return null;
    }
  }

  /**
   * Create Defensive SpeechLine according the race
   * @param race SpaceRace
   * @return SpeechLine or null if creating line fails
   */
  private static SpeechLine createDefensivePactLine(final SpaceRace race) {
    SpeechType type = SpeechType.DEFESIVE_PACT;
    switch (race) {
      case CENTAURS: return new SpeechLine(type,
          "We should defend each others. What do you say?");
      case GREYANS: return new SpeechLine(type,
          "We should join our defending forces? ");
      case HUMAN: return new SpeechLine(type,
          "Let's make defensive pact together!");
      case MECHIONS: return new SpeechLine(type,
          "Defend together?");
      case MOTHOIDS: return new SpeechLine(type,
          "Join your drones with ours to defend!");
      case SPORKS: return new SpeechLine(type,
          "Join my military defense group!");
      case TEUTHIDAES: return new SpeechLine(type,
          "Join my side defense organization!");
      case SCAURIANS: return new SpeechLine(type,
          "Let's build awesome defending power!");
      case HOMARIANS: return new SpeechLine(type,
          "Are you insterested in defensive pact?");
      case SPACE_MONSTERS:
      case SPACE_PIRATE: return new SpeechLine(type,
          "Yarr, Are you interested in defensive pact?");
      case CHIRALOIDS:
        return new SpeechLine(type,
          "Are you ready to make defensive pact?");
      case REBORGIANS:
        return new SpeechLine(type,
          "Are ready to be our battle thrall?");
      case LITHORIANS:
        return new SpeechLine(type,
          "Let's form guarding pact, what say you?");
      case ALTEIRIANS:
        return new SpeechLine(type,
          "We should join side by side to defend ourselves!");
      case SMAUGIRIANS:
        return new SpeechLine(type,
          "I am ready to defend you. Are you ready for defensive pact?");
      case SYNTHDROIDS: return new SpeechLine(type,
          "Handshake for defending together?");
      default: return null;
    }
  }

  /**
   * Create Agree SpeechLine according the race
   * @param race SpaceRace
   * @return SpeechLine or null if creating line fails
   */
  public static SpeechLine createAgreeWithWarLine(final SpaceRace race) {
    SpeechType type = SpeechType.AGREE;
    switch (race) {
      case CENTAURS: return new SpeechLine(type,
          "Very well, prepare to be stomped!");
      case GREYANS: return new SpeechLine(type,
          "Prepare to be anhilated!");
      case HUMAN: return new SpeechLine(type,
          "This means war then!");
      case MECHIONS: return new SpeechLine(type,
          "Initiating war protocol!");
      case MOTHOIDS: return new SpeechLine(type,
          "We accept your war offer and kill all your kinds!");
      case SPORKS: return new SpeechLine(type,
          "WAR! SHIELDS UP AND FIRE!");
      case TEUTHIDAES: return new SpeechLine(type,
          "Prepare to be dead!");
      case SCAURIANS: return new SpeechLine(type,
          "Agreed! Time to die!");
      case HOMARIANS: return new SpeechLine(type,
          "Let the war begin!");
      case SPACE_PIRATE: return new SpeechLine(type,
          "Yarr, I agree! Now we fight!");
      case CHIRALOIDS:
        return new SpeechLine(type,
          "I will nuke you!");
      case REBORGIANS:
        return new SpeechLine(type,
          "We will synthetize you!");
      case LITHORIANS:
        return new SpeechLine(type,
          "We will crush you like a pebble stone!");
      case ALTEIRIANS:
        return new SpeechLine(type,
          "Prepare feel the wrath of us!");
      case SMAUGIRIANS:
        return new SpeechLine(type,
          "We will fight now!");
      case SYNTHDROIDS: return new SpeechLine(type,
          "Starting war protocols!");
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
      case HUMAN:
        return new SpeechLine(type,
          "Glad to make business with you!");
      case MECHIONS: return new SpeechLine(type,
          "Acknowledge!");
      case MOTHOIDS: return new SpeechLine(type,
          "We accept your offer!");
      case SPORKS: return new SpeechLine(type,
          "Deal!");
      case TEUTHIDAES: return new SpeechLine(type,
          "Excellent!");
      case SCAURIANS: return new SpeechLine(type,
          "Agreed!");
      case HOMARIANS: return new SpeechLine(type,
          "Let's shake chelae for this!");
      case SPACE_MONSTERS:
      case SPACE_PIRATE: return new SpeechLine(type,
          "Yarr, I agreed!");
      case CHIRALOIDS:
        return new SpeechLine(type,
          "I am willing to agree!");
      case REBORGIANS:
        return new SpeechLine(type,
          "We will agree with your offer!");
      case LITHORIANS:
        return new SpeechLine(type,
          "This deal makes me warm inside.");
      case ALTEIRIANS:
        return new SpeechLine(type,
          "Most excellent deal!");
      case SMAUGIRIANS:
        return new SpeechLine(type,
          "I agree with this!");
      case SYNTHDROIDS: return new SpeechLine(type,
          "Handshake for agreement!");
      default: return null;
    }
  }

  /**
   * Create Offer accepted SpeechLine according the race
   * @param race SpaceRace
   * @return SpeechLine or null if creating line fails
   */
  private static SpeechLine createOfferAcceptedLine(final SpaceRace race) {
    SpeechType type = SpeechType.OFFER_ACCEPTED;
    switch (race) {
      case CENTAURS: {
        switch (DiceGenerator.getRandom(1)) {
          default:
          case 0: return new SpeechLine(type,
              "This is a begin of friendship!");
          case 1: return new SpeechLine(type,
              "Magnificent trade!");
        }
      }
      case GREYANS: return new SpeechLine(type,
          "Fantastic!");
      case HUMAN: return new SpeechLine(type,
          "Glad to make business with you!");
      case MECHIONS: return new SpeechLine(type,
          "Acknowledge!");
      case MOTHOIDS: return new SpeechLine(type,
          "We are happy to make trade with you!");
      case SPORKS: return new SpeechLine(type,
          "Happy to make business with you!");
      case TEUTHIDAES: return new SpeechLine(type,
          "Excellent!");
      case SCAURIANS: {
        switch (DiceGenerator.getRandom(1)) {
          default:
          case 0: return new SpeechLine(type,
              "Agreed and happy to make business with you!");
          case 1: return new SpeechLine(type,
              "I am happy to make business with you!");
        }
      }
      case HOMARIANS: return new SpeechLine(type,
          "I glad to shake chelae with you!");
      case SPACE_MONSTERS:
      case SPACE_PIRATE: return new SpeechLine(type,
          "Yarr, Most excellent deal!");
      case CHIRALOIDS: return new SpeechLine(type,
          "Excellent. This was great deal!");
      case REBORGIANS: return new SpeechLine(type,
          "We accept your deal!");
      case LITHORIANS: return new SpeechLine(type,
          "I accept your offer with warm!");
      case ALTEIRIANS: return new SpeechLine(type,
          "I feel your offer. This is good!");
      case SMAUGIRIANS: return new SpeechLine(type,
          "This is good deal for both of us!");
      case SYNTHDROIDS: return new SpeechLine(type,
          "Handshake done!");
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
      case TEUTHIDAES: return new SpeechLine(type,
          "This is our fair trade?");
      case SCAURIANS: return new SpeechLine(type,
          "What do you say about my offer?");
      case HOMARIANS: return new SpeechLine(type,
          "What do you think about my offer?");
      case SPACE_MONSTERS:
      case SPACE_PIRATE: return new SpeechLine(type,
          "Yarr, What say you about my loot?");
      case CHIRALOIDS: return new SpeechLine(type,
          "What say you about my trade offer?");
      case REBORGIANS: return new SpeechLine(type,
          "Here is our offer!");
      case LITHORIANS: return new SpeechLine(type,
          "This is my offer!");
      case ALTEIRIANS: return new SpeechLine(type,
          "I am offering this one, are you feeling it?");
      case SMAUGIRIANS: return new SpeechLine(type,
          "I am offering you this. Interested?");
      case SYNTHDROIDS: return new SpeechLine(type,
          "Handshake?");
      default: return null;
    }
  }

  /**
   * Create Peace offer SpeechLine according the race
   * @param race SpaceRace
   * @return SpeechLine or null if creating line fails
   */
  private static SpeechLine createPeaceOfferLine(final SpaceRace race) {
    SpeechType type = SpeechType.PEACE_OFFER;
    switch (race) {
      case CENTAURS: return new SpeechLine(type,
          "Are ready to make a peace?");
      case GREYANS: return new SpeechLine(type,
          "Are interested about peace?");
      case HUMAN: return new SpeechLine(type,
          "How about peace?");
      case MECHIONS: return new SpeechLine(type,
          "Peace?");
      case MOTHOIDS: return new SpeechLine(type,
          "We offer your kind peace!");
      case SPORKS: return new SpeechLine(type,
          "What you say about peace?");
      case TEUTHIDAES: return new SpeechLine(type,
          "Are you ready for peace?");
      case SCAURIANS: return new SpeechLine(type,
          "Let's sign a peace contract?");
      case HOMARIANS: return new SpeechLine(type,
          "Interested to sign peace with me?");
      case SPACE_MONSTERS:
      case SPACE_PIRATE: return new SpeechLine(type,
          "Care to sign peace?");
      case CHIRALOIDS: return new SpeechLine(type,
          "Do you want peace or nuke?");
      case REBORGIANS: return new SpeechLine(type,
          "We offer you mutual coexistance.");
      case LITHORIANS: return new SpeechLine(type,
          "There is room for both of us.");
      case ALTEIRIANS: return new SpeechLine(type,
          "Let's join together with peace...");
      case SMAUGIRIANS: return new SpeechLine(type,
          "How about peace contract?");
      case SYNTHDROIDS: return new SpeechLine(type,
          "Handshake for peace?");
      default: return null;
    }
  }

}
