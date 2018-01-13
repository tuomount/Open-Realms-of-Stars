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
      case MAKE_WAR: return createMakeWarLine(race);
      case DECLINE: return createDeclineLine(race);
      case DECLINE_ANGER: return createDeclineAngerLine(race);
      case DECLINE_WAR: return createDeclineWarLine(race);
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
      case HUMAN: return new SpeechLine(type,
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
          "Your fleet has enter on my sector."
          + " Move " + fleetName + " it away, please!");
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
          "Good day! Let's get know each others, " + raceName + ".");
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
      default: return null;
    }
  }

  /**
   * Create Decline War SpeechLine according the race
   * @param race SpaceRace
   * @return SpeechLine or null if creating line fails
   */
  private static SpeechLine createDeclineWarLine(final SpaceRace race) {
    SpeechType type = SpeechType.DECLINE_WAR;
    switch (race) {
      case CENTAURS: return new SpeechLine(type,
          "This is your last insult! Prepare to be stomped!");
      case GREYANS: return new SpeechLine(type,
          "Your offer insulted us final time! Prepare to be anhilated!");
      case HUMAN: return new SpeechLine(type,
          "This was your last insult! Die!");
      case MECHIONS: return new SpeechLine(type,
          "Initiating war protocol!");
      case MOTHOIDS: return new SpeechLine(type,
          "We offer you this and you accept it?");
      case SPORKS: return new SpeechLine(type,
          "You either take this with good or bad!");
      case TEUTHIDAES: return new SpeechLine(type,
          "You will pay for your insults!");
      case SCAURIANS: return new SpeechLine(type,
          "I will take last credits from your cold body!");
      case HOMARIANS: return new SpeechLine(type,
          "Prepare to be sliced!");
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
      case TEUTHIDAES: return new SpeechLine(type,
          "Prepare to die!");
      case SCAURIANS: return new SpeechLine(type,
          "Time to die!");
      case HOMARIANS: return new SpeechLine(type,
          "Time to get sliced and boiled!");
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
      case TEUTHIDAES: return new SpeechLine(type,
          "Excellent!");
      case SCAURIANS: return new SpeechLine(type,
          "Agreed!");
      case HOMARIANS: return new SpeechLine(type,
          "Let's shake chelae for this!");
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
      default: return null;
    }
  }

}
