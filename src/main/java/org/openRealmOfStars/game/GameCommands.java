package org.openRealmOfStars.game;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016-2020 Tuomo Untinen
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
 * Game commands
 *
 */
public final class GameCommands {

  /**
   * Hiding the default constructor.
   */
  private GameCommands() {
    // Nothing to do here
  }

  /**
   * Animation timer command
   */
  public static final String COMMAND_ANIMATION_TIMER = "AnimationTimerCommand";

  /**
   * Music timer command
   */
  public static final String COMMAND_MUSIC_TIMER = "MusicTimerCommand";

  /**
   * OK command
   */
  public static final String COMMAND_OK = "OK";

  /**
   * Apply command
   */
  public static final String COMMAND_APPLY = "Apply";

  /**
   * Resize command
   */
  public static final String COMMAND_RESIZE = "Resize";

  /**
   * Options View command
   */
  public static final String COMMAND_OPTIONS_VIEW = "OptionsView";

  /**
   * Music volume up
   */
  public static final String COMMAND_MUSIC_VOLUME_UP = "OptionsMusicVolumeUp";

  /**
   * Music volume Down
   */
  public static final String COMMAND_MUSIC_VOLUME_DN = "OptionsMusicVolumeDn";

  /**
   * Music volume
   */
  public static final String COMMAND_MUSIC_VOLUME = "OptionsMusicVolume";

  /**
   * Sound volume up
   */
  public static final String COMMAND_SOUND_VOLUME_UP = "OptionsSoundVolumeUp";

  /**
   * Sound volume Down
   */
  public static final String COMMAND_SOUND_VOLUME_DN = "OptionsSoundVolumeDn";

  /**
   * Sound volume
   */
  public static final String COMMAND_SOUND_VOLUME = "OptionsSoundVolume";

  /**
   * Ambient lights intense up
   */
  public static final String COMMAND_LIGHTS_UP = "OptionsLightsUp";

  /**
   * Ambient lights intense down
   */
  public static final String COMMAND_LIGHTS_DN = "OptionsLightsDn";

  /**
   * Ambient lights intense
   */
  public static final String COMMAND_LIGHTS_INTENSE = "OptionsLightsIntense";

  /**
   * Ambient lights setup
   */
  public static final String COMMAND_SETUP_LIGHTS = "SetupLights";

  /**
   * Ambient lights connect
   */
  public static final String COMMAND_BRIDGE_CONNECT = "SetupBridge";
  /**
   * Light test
   */
  public static final String COMMAND_LIGHT_TEST = "TestLight";

  /**
   * End Turn command
   */
  public static final String COMMAND_END_TURN = "EndTurn";

  /**
   * End battle round
   */
  public static final String COMMAND_END_BATTLE_ROUND = "EndBattleRound";

  /**
   * Auto combat command
   */
  public static final String COMMAND_AUTO_COMBAT = "AutoCombat";

  /**
   * Auto use all weapons
   */
  public static final String COMMAND_USE_ALL_WEAPONS = "UseAllWeapons";

  /**
   * Defend sector command
   */
  public static final String COMMAND_DEFEND_SECTOR = "DefendCommand";
  /**
   * Fix fleet command
   */
  public static final String COMMAND_FIX_FLEET = "FixFleetCommand";
  /**
   * Filename command
   */
  public static final String COMMAND_FILE_OVERWRITE = "FileOverwriteCommand";
  /**
   * Trade fleet command
   */
  public static final String COMMAND_TRADE_FLEET = "TradeFleetCommand";
  /**
   * Route fleet command
   */
  public static final String COMMAND_ROUTE_FLEET = "RouteFleetCommand";
  /**
   * Deploy starbase command
   */
  public static final String COMMAND_DEPLOY_STARBASE = "DeployStarbaseCommand";
  /**
   * View planet command
   */
  public static final String COMMAND_VIEW_PLANET = "ViewPlanetCommand";

  /**
   * Change espionage mission.
   */
  public static final String COMMAND_CHANGE_MISSION = "ChangeEspionageMission";

  /**
   * View fleet command
   */
  public static final String COMMAND_VIEW_FLEET = "ViewFleet";

  /**
   * Upgrade selected command
   */
  public static final String COMMAND_UPGRADE_SELECTED = "UpgradeSelected";

  /**
   * Upgrade command
   */
  public static final String COMMAND_UPGRADE = "Upgrade";

  /**
   * View Upgrade command
   */
  public static final String COMMAND_VIEW_UPGRADE = "ViewUpgrade";

  /**
   * View Star map
   */
  public static final String COMMAND_VIEW_STARMAP = "ViewStarMap";

  /**
   * View Voting view
   */
  public static final String COMMAND_VIEW_VOTING = "ViewVote";
  /**
   * View minimap command
   */
  public static final String COMMAND_VIEW_MINIMAP = "ViewMiniMap";
  /**
   * View help command
   */
  public static final String COMMAND_VIEW_HELP = "ViewHelp";

  /**
   * View Research view
   */
  public static final String COMMAND_VIEW_RESEARCH = "ViewResearch";

  /**
   * Minus mine
   */
  public static final String COMMAND_MINUS_MINE = "MinusMine";
  /**
   * Plus mine
   */
  public static final String COMMAND_PLUS_MINE = "PlusMine";

  /**
   * Minus farm
   */
  public static final String COMMAND_MINUS_FARM = "MinusFarm";
  /**
   * Plus farm
   */
  public static final String COMMAND_PLUS_FARM = "PlusFarm";

  /**
   * Minus production
   */
  public static final String COMMAND_MINUS_PRODUCTION = "MinusProduction";
  /**
   * Plus production
   */
  public static final String COMMAND_PLUS_PRODUCTION = "PlusProduction";

  /**
   * Minus Research
   */
  public static final String COMMAND_MINUS_RESEARCH = "MinusResearch";
  /**
   * Plus Research
   */
  public static final String COMMAND_PLUS_RESEARCH = "PlusResearch";
  /**
   * Minus Tax
   */
  public static final String COMMAND_MINUS_TAX = "MinusTax";
  /**
   * Plus Tax
   */
  public static final String COMMAND_PLUS_TAX = "PlusTax";

  /**
   * Minus Fake military
   */
  public static final String COMMAND_MINUS_MILITARY = "MinusMilitary";
  /**
   * Plus Fake military
   */
  public static final String COMMAND_PLUS_MILITARY = "PlusMilitary";

  /**
   * Fake military
   */
  public static final String COMMAND_FAKE_MILITARY = "FakeMilitary";

  /**
   * Minus Human credit in diplomacy view
   */
  public static final String COMMAND_MINUS_HUMAN_CREDIT = "MinusCreditHuman";
  /**
   * Plus  Human credit in diplomacy view
   */
  public static final String COMMAND_PLUS_HUMAN_CREDIT = "PlusCreditHuman";
  /**
   * Minus AI credit in diplomacy view
   */
  public static final String COMMAND_MINUS_AI_CREDIT = "MinusCreditAi";
  /**
   * Plus AI credit in diplomacy view
   */
  public static final String COMMAND_PLUS_AI_CREDIT = "PlusCreditAi";

  /**
   * Minus Combat research
   */
  public static final String COMMAND_MINUS_COMBAT_RESEARCH = "MinusCombatRese";
  /**
   * Plus Combat research
   */
  public static final String COMMAND_PLUS_COMBAT_RESEARCH = "PlusCombatRese";
  /**
   * Slider Combat research
   */
  public static final String COMMAND_SLIDER_COMBAT_RESEARCH =
      "SliderCombatRese";
  /**
   * Minus Defense research
   */
  public static final String COMMAND_MINUS_DEFENSE_RESEARCH =
      "MinusDefenseRese";
  /**
   * Plus Defense research
   */
  public static final String COMMAND_PLUS_DEFENSE_RESEARCH = "PlusDefenseRese";
  /**
   * Slider Defense research
   */
  public static final String COMMAND_SLIDER_DEFENSE_RESEARCH =
      "SliderDefenseRese";
  /**
   * Minus Hull research
   */
  public static final String COMMAND_MINUS_HULL_RESEARCH = "MinusHullRese";
  /**
   * Plus Hull research
   */
  public static final String COMMAND_PLUS_HULL_RESEARCH = "PlusHullRese";
  /**
   * Slider Hull research
   */
  public static final String COMMAND_SLIDER_HULL_RESEARCH = "SliderHullRese";
  /**
   * Minus Improvement research
   */
  public static final String COMMAND_MINUS_IMPROVEMENT_RESEARCH =
      "MinusImprovementRese";
  /**
   * Plus Improvement research
   */
  public static final String COMMAND_PLUS_IMPROVEMENT_RESEARCH =
      "PlusImprovementRese";
  /**
   * Slider Improvement research
   */
  public static final String COMMAND_SLIDER_IMPROVEMENT_RESEARCH =
      "SliderImprovementRese";
  /**
   * Minus Propulsion research
   */
  public static final String COMMAND_MINUS_PROPULSION_RESEARCH =
      "MinusPropulsionRese";
  /**
   * Plus Propulsion research
   */
  public static final String COMMAND_PLUS_PROPULSION_RESEARCH =
      "PlusPropulsionRese";
  /**
   * Slider Propulsion research
   */
  public static final String COMMAND_SLIDER_PROPULSION_RESEARCH =
      "SliderPropulsionRese";
  /**
   * Minus Electronics research
   */
  public static final String COMMAND_MINUS_ELECTRONICS_RESEARCH =
      "MinusElectronicsRese";
  /**
   * Plus Electronics research
   */
  public static final String COMMAND_PLUS_ELECTRONICS_RESEARCH =
      "PlusElectronicsRese";
  /**
   * Slider Electronics research
   */
  public static final String COMMAND_SLIDER_ELECTRONICS_RESEARCH =
      "SliderElectronicsRese";

  /**
   * Command for showing combat tech info
   */
  public static final String COMMAND_COMBAT_INFO =
      "CombatInfo";

  /**
   * Command for showing defense tech info
   */
  public static final String COMMAND_DEFENSE_INFO =
      "DefenseInfo";

  /**
   * Command for showing hull tech info
   */
  public static final String COMMAND_HULL_INFO =
      "HullInfo";

  /**
   * Command for showing improvement tech info
   */
  public static final String COMMAND_IMPROVEMENT_INFO =
      "ImprovementInfo";

  /**
   * Command for showing propulsion tech info
   */
  public static final String COMMAND_PROPULSION_INFO =
      "PropulsionInfo";

  /**
   * Command for showing electronics tech info
   */
  public static final String COMMAND_ELECTRONICS_INFO =
      "ElectronicsInfo";

  /**
   * Upgrade combat
   */
  public static final String COMMAND_UPGRADE_COMBAT = "UpgradeCombat";
  /**
   * Upgrade defense
   */
  public static final String COMMAND_UPGRADE_DEFENSE = "UpgradeDefense";
  /**
   * Upgrade hull
   */
  public static final String COMMAND_UPGRADE_HULL = "UpgradeHull";
  /**
   * Upgrade improvement
   */
  public static final String COMMAND_UPGRADE_IMPROVEMENT =
      "UpgradeImprovement";
  /**
   * Upgrade propulsion
   */
  public static final String COMMAND_UPGRADE_PROPULSION =
      "UpgradePropulsion";
  /**
   * Upgrade electronics
   */
  public static final String COMMAND_UPGRADE_ELECTRONICS =
      "UpgradeElectronics";

  /**
   * Production list changed in planet
   */
  public static final String COMMAND_PRODUCTION_LIST = "ProductionList";

  /**
   * Command for showing planet list
   */
  public static final String COMMAND_SHOW_PLANET_LIST = "ShowPlanetList";
  /**
   * Demolish/recycle building
   */
  public static final String COMMAND_DEMOLISH_BUILDING = "DemolishBuilding";

  /**
   * New Game command
   */
  public static final String COMMAND_NEW_GAME = "NewGame";

  /**
   * Continue Game
   */
  public static final String COMMAND_CONTINUE_GAME = "ContinueGame";

  /**
   * Show credits command
   */
  public static final String COMMAND_CREDITS = "Credits";

  /**
   * Quit Game command
   */
  public static final String COMMAND_QUIT_GAME = "QuitGame";

  /**
   * Something has changed in Galaxy Setup
   */
  public static final String COMMAND_GALAXY_SETUP = "GalaxySetup";

  /**
   * Realm color has changed in setup
   */
  public static final String COMMAND_COLOR_SETUP = "ColorSetup";

  /**
   * Government has changed in Galaxy Setup
   */
  public static final String COMMAND_GOVERNMENT_SETUP = "GovernmentSetup";

  /**
   * Difficulty has changed in Galaxy Setup
   */
  public static final String COMMAND_DIFFICULT_SETUP = "DifficultSetup";

  /**
   * Random name button action
   */
  public static final String COMMAND_RANDOM_NAME = "RandomName";

  /**
   * Cancel command
   */
  public static final String COMMAND_CANCEL = "Cancel";

  /**
   * Next command
   */
  public static final String COMMAND_NEXT = "Next";

  /**
   * Prev Message command
   */
  public static final String COMMAND_PREV_MSG = "PrevMessage";

  /**
   * Next Message command
   */
  public static final String COMMAND_NEXT_MSG = "NextMessage";

  /**
   * Prev planet/ship command
   */
  public static final String COMMAND_PREV_TARGET = "PrevPlanet/Ship";

  /**
   * Next planet/ship command
   */
  public static final String COMMAND_NEXT_TARGET = "NextPlanet/Ship";

  /**
   * Focus Message command
   */
  public static final String COMMAND_FOCUS_MSG = "FocusMessage";

  /**
   * View ships stats and designs
   */
  public static final String COMMAND_SHIPS = "ViewShips";

  /**
   * View player stats
   */
  public static final String COMMAND_VIEW_STATS = "ViewStats";

  /**
   * View leader view
   */
  public static final String COMMAND_VIEW_LEADERS = "ViewLeaders";
  /**
   * Recruit leader
   */
  public static final String COMMAND_RECRUIT_LEADER = "RecruitLeader";
  /**
   * Assign leader
   */
  public static final String COMMAND_ASSIGN_LEADER = "AssignLeader";
  /**
   * View espionage mission view
   */
  public static final String COMMAND_ESPIONAGE_MISSIONS = "EspionageMissions";
  /**
   * Execute mission.
   */
  public static final String COMMAND_EXECUTE_MISSION = "ExecuteMission";
  /**
   * Enable auto exploring
   */
  public static final String COMMAND_AUTO_EXPLORE = "AutoExplore";

  /**
   * Start combat for no reason. This is more like debug purposes
   */
  public static final String COMMAND_BATTLE = "ViewBattle";

  /**
   * Show espionage view
   */
  public static final String COMMAND_SPY = "ViewEspionage";

  /**
   * View news
   */
  public static final String COMMAND_NEWS = "ViewNews";

  /**
   * Ship Design is done and ready
   */
  public static final String COMMAND_SHIPDESIGN_DONE = "ShipDesignIsReady";

  /**
   * Copy ship design and start design screen
   */
  public static final String COMMAND_COPY_SHIP = "CopyShipDesign";

  /**
   * Obsolete ship design
   */
  public static final String COMMAND_OBSOLETE_SHIP = "ObsoleteShipDesign";

  /**
   * Delete ship design
   */
  public static final String COMMAND_DELETE_SHIP = "DeleteShipDesign";

  /**
   * Start ship design with new ship
   */
  public static final String COMMAND_SHIPDESIGN = "ShipDesign";

  /**
   * Ship Hull Selected from the list
   */
  public static final String COMMAND_SHIPDESIGN_HULLSELECTED =
      "ShipDesignHullSelected";

  /**
   * Change Hull button pressed
   */
  public static final String COMMAND_SHIPDESIGN_CHANGEHULL = "ChangeHull";

  /**
   * Ship Component selected from the list
   */
  public static final String COMMAND_SHIPDESIGN_COMPONENTSELECTED =
      "ShipComponentSelected";

  /**
   * Ship Component filtered
   */
  public static final String COMMAND_SHIPDESIGN_COMPONENTFILTERED =
      "ShipComponentFiltered";

  /**
   * Ship Component added
   */
  public static final String COMMAND_SHIPDESIGN_COMPONENTADDED =
      "ShipComponentAdded";
  /**
   * Ship Component Removed
   */
  public static final String COMMAND_SHIPDESIGN_COMPONENTREMOVED =
      "ShipComponentRemoved";
  /**
   * Ship Component set higher priority
   */
  public static final String COMMAND_SHIPDESIGN_COMPONENT_PRIORITYHI =
      "ShipComponentPriorityHi";
  /**
   * Ship Component set lower priority
   */
  public static final String COMMAND_SHIPDESIGN_COMPONENT_PRIORITYLO =
      "ShipComponentPriorityLo";

  /**
   * Colonize command
   */
  public static final String COMMAND_COLONIZE = "ColonizeCommand";

  /**
   * Conquest command
   */
  public static final String COMMAND_CONQUEST = "ConquestCommand";

  /**
   * Abort conquest
   */
  public static final String COMMAND_ABORT_CONQUEST = "AbortConquestCommand";

  /**
   * Launch ground attack command
   */
  public static final String COMMAND_LAUNCH_GROUND_ATTACK =
      "LaunchGroundAttackCommand";

  /**
   * Split the fleet
   */
  public static final String COMMAND_SPLIT_THE_FLEET = "SplitTheFleet";

  /**
   * Hail the fleet/planet
   */
  public static final String COMMAND_HAIL_FLEET_PLANET = "HailFleetPlanet";

  /**
   * Start trade mission
   */
  public static final String COMMAND_START_TRADE_MISSION = "StartTradeMission";

  /**
   * Stop trade mission
   */
  public static final String COMMAND_STOP_TRADE_MISSION = "StopTradeMission";

  /**
   * Split the fleet
   */
  public static final String COMMAND_DESTROY_THE_SHIP = "DestroyTheShip";

  /**
   * Merge fleets
   */
  public static final String COMMAND_MERGE_FLEETS = "MergeFleets";

  /**
   * Switch to fleet
   */
  public static final String COMMAND_SWITCH_FLEETS = "SwitchFleets";

  /**
   * Colonist plus command
   */
  public static final String COMMAND_COLONIST_PLUS = "ColonistPlus";
  /**
   * Colonist minus command
   */
  public static final String COMMAND_COLONIST_MINUS = "ColonistMinus";

  /**
   * Metal cargo plus command
   */
  public static final String COMMAND_METAL_CARGO_PLUS = "MetalCargoPlus";
  /**
   * Metal cargo minus command
   */
  public static final String COMMAND_METAL_CARGO_MINUS = "MetalCargoMinus";

  /**
   * Command for using components in battle
   */
  public static final String COMMAND_COMPONENT_USE = "UseComponent";

  /**
   * Command for rushing with credits
   */
  public static final String COMMAND_RUSH_WITH_CREDITS = "RushCredits";

  /**
   * Command for rushing with population
   */
  public static final String COMMAND_RUSH_WITH_POPULATION = "RushPopulation";

  /**
   * Command for showing history information
   */
  public static final String COMMAND_SHOW_HISTORY = "ShowHistory";

  /**
   * Command for end showing history information
   */
  public static final String COMMAND_DONE_HISTORY = "DoneHistory";

  /**
   * Command for previous vote
   */
  public static final String COMMAND_PREV_VOTE = "PrevVote";
  /**
   * Command for next vote
   */
  public static final String COMMAND_NEXT_VOTE = "NextVote";
  /**
   * Command for vote yes
   */
  public static final String COMMAND_VOTE_YES = "VoteYes";
  /**
   * Command for vote no
   */
  public static final String COMMAND_VOTE_NO = "VoteNo";

  /**
   * Command for previous year
   */
  public static final String COMMAND_PREV_YEAR = "PrevYear";

  /**
   * Command for next year
   */
  public static final String COMMAND_NEXT_YEAR = "NextYear";

  /**
   * Command for previous event
   */
  public static final String COMMAND_PREV_EVENT = "PrevEvent";

  /**
   * Command for next event
   */
  public static final String COMMAND_NEXT_EVENT = "NextEvent";

  /**
   * Command for realm view
   */
  public static final String COMMAND_REALM_VIEW = "RealmView";

  /**
   * Human full map trade
   */
  public static final String COMMAND_HUMAN_FULL_MAP = "HumanFullMap";
  /**
   * Human planet map trade
   */
  public static final String COMMAND_HUMAN_PLANET_MAP = "HumanPlanetMap";
  /**
   * AI full map trade
   */
  public static final String COMMAND_AI_FULL_MAP = "AiFullMap";
  /**
   * AI planet map trade
   */
  public static final String COMMAND_AI_PLANET_MAP = "AiPlanetMap";
  /**
   * Human Promise Vote Yes
   */
  public static final String COMMAND_HUMAN_VOTE_YES = "HumanVoteYes";
  /**
   * Human Promise Vote No
   */
  public static final String COMMAND_HUMAN_VOTE_NO = "HumanVoteNo";
  /**
   * AI Promise Vote Yes
   */
  public static final String COMMAND_AI_VOTE_YES = "AiVoteYes";
  /**
   * AI Promise Vote No
   */
  public static final String COMMAND_AI_VOTE_NO = "AiVoteNo";

}
