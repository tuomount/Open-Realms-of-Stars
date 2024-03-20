package org.openRealmOfStars.game;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016-2023 Tuomo Untinen
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
 *
 * Game states in enum
 *
 */
public enum GameState {
  /**
   * Main Menu state where Main menu is shown
   */
  MAIN_MENU,
  /**
   * State where universum is being created. This is so called pseudo state
   * it does not have actual screen.
   */
  NEW_GAME,
  /**
   * Star Map view where Star map of the game is shown
   */
  STARMAP,
  /**
   * Planet View is state where planet information is shown and player
   * can handling planet constructions.
   */
  PLANETVIEW,
  /**
   * State where fleet is being shown. Fleet can be either in space or
   * orbiting a planet
   */
  FLEETVIEW,
  /**
   * Research view is state where player can select which techs to focus
   * and view techs which have already researched.
   */
  RESEARCHVIEW,
  /**
   * View ships is state where player can browse through his or her
   * ships.
   */
  VIEWSHIPS,
  /**
   * Ship Design is state where player can design totally new ships.
   */
  SHIPDESIGN,
  /**
   * Combat state is when two fleets encounter and battle begins.
   */
  COMBAT,
  /**
   * Credits state is state which shows license information and people
   * who has contributed to this game.
   */
  CREDITS,
  /**
   * AI Turn is state where ai is doing it's own turn and random planet
   * is being shown to player.
   */
  AITURN,
  /**
   * State which is shown when enemy's planet is being bombed.
   */
  PLANETBOMBINGVIEW,
  /**
   * Galaxy creation is state where basic settings for galaxy is being
   * configured.
   */
  GALAXY_CREATION,
  /**
   * Player setup is state where all the players are being configured.
   */
  PLAYER_SETUP,
  /**
   * State where player can select which saved game is about to be loaded.
   */
  LOAD_GAME,
  /**
   * View player stats info
   */
  VIEWSTATS,
  /**
   * Diplomacy view
   */
  DIPLOMACY_VIEW,
  /**
   * News corp view which will tell highlights of previous turn
   */
  NEWS_CORP_VIEW,
  /**
   * Espionage view show spy information about other reals
   * and add possibility to pay GNBC to create fake news
   */
  ESPIONAGE_VIEW,
  /**
   * History view showing historical game events
   */
  HISTORY_VIEW,
  /**
   * Options view to configure sound/music volume and resolution.
   */
  OPTIONS_VIEW,
  /**
   * State where trade fleet is being shown.
   * Fleet needs to be orbiting own planet.
   */
  FLEET_TRADE_VIEW,
  /**
   * Realm View
   */
  REALM_VIEW,
  /**
   * Planet list view containing all the planets in single list.
   */
  PLANET_LIST_VIEW,
  /**
   * Voting view
   */
  VOTE_VIEW,
  /**
   * View where save game file name is defined when starting new game.
   */
  SAVE_GAME_NAME_VIEW,
  /**
   * View where all tutorial/help text is being shown.
   */
  HELP_VIEW,
  /**
   * Show all the leaders for realm.
   */
  LEADER_VIEW,
  /**
   * Espionage mission view.
   */
  ESPIONAGE_MISSIONS_VIEW,
  /**
   * View for setuping ambient lights for the game.
   */
  SETUP_AMBIENT_LIGHTS,
  /**
   * View for ship upgrade.
   */
  SHIP_UPGRADE_VIEW,
  /**
   * Text screen on top of planet screen
   */
  TEXT_SCREEN_VIEW,
  /**
   * Text screen when game ends.
   */
  GAME_END_VIEW,
  /**
   * View where human player can select next galactic voting.
   */
  VOTING_SELECTION_VIEW,
  /**
   * View Change Log.
   */
  CHANGE_LOG,
  /**
   * Story view for viewing background story of the realm.
   */
  STORY_VIEW,
  /**
   * Story view for viewing full story of the realm when game ends.
   */
  END_STORY_VIEW,
  /** View for configuring individual realm */
  REALM_SETUP_VIEW,
  /** View for configuring all AI realms */
  AI_REALM_SETUP_VIEW;
  
}
