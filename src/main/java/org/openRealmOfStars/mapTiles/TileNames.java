package org.openRealmOfStars.mapTiles;

import java.util.ArrayList;

import org.openRealmOfStars.utilities.DiceGenerator;

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
 * Class for naming individual tiles
 *
 */

public final class TileNames {

  /**
   * Hiding the constructor
   */
  private TileNames() {
    //Nothing to do
  }
  /**
   * Name for empty tile which is not drawn
   */
  public static final String EMPTY = "EMPTY";

  /**
   * Sun North West
   */
  public static final String SUN_NW = "SUN_NW";
  /**
   * Sun North
   */
  public static final String SUN_N = "SUN_N";
  /**
   * Sun North East
   */
  public static final String SUN_NE = "SUN_NE";
  /**
   * Sun East
   */
  public static final String SUN_E = "SUN_E";
  /**
   * Sun South East
   */
  public static final String SUN_SE = "SUN_SE";
  /**
   * Sun South
   */
  public static final String SUN_S = "SUN_S";
  /**
   * Sun South West
   */
  public static final String SUN_SW = "SUN_SW";
  /**
   * Sun West
   */
  public static final String SUN_W = "SUN_W";
  /**
   * Sun Center
   */
  public static final String SUN_C = "SUN_C";

  /**
   * Gas Giant 1 North West
   */
  public static final String GAS_GIANT_1_NW = "GAS_GIANT_1_NW";
  /**
   * Gas Giant 1 North East
   */
  public static final String GAS_GIANT_1_NE = "GAS_GIANT_1_NE";
  /**
   * Gas Giant 1 South East
   */
  public static final String GAS_GIANT_1_SE = "GAS_GIANT_1_SE";
  /**
   * Gas Giant 1 South West
   */
  public static final String GAS_GIANT_1_SW = "GAS_GIANT_1_SW";

  /**
   * Gas Giant 2 North West
   */
  public static final String GAS_GIANT_2_NW = "GAS_GIANT_2_NW";
  /**
   * Gas Giant 2 North East
   */
  public static final String GAS_GIANT_2_NE = "GAS_GIANT_2_NE";
  /**
   * Gas Giant 2 South East
   */
  public static final String GAS_GIANT_2_SE = "GAS_GIANT_2_SE";
  /**
   * Gas Giant 2 South West
   */
  public static final String GAS_GIANT_2_SW = "GAS_GIANT_2_SW";

  /**
   * Gas Giant 3 North West
   */
  public static final String GAS_GIANT_3_NW = "GAS_GIANT_3_NW";
  /**
   * Gas Giant 3 North East
   */
  public static final String GAS_GIANT_3_NE = "GAS_GIANT_3_NE";
  /**
   * Gas Giant 3 South East
   */
  public static final String GAS_GIANT_3_SE = "GAS_GIANT_3_SE";
  /**
   * Gas Giant 3 South West
   */
  public static final String GAS_GIANT_3_SW = "GAS_GIANT_3_SW";

  /**
   * Planet 1 Rock world 1
   */
  public static final String ROCK1 = "ROCK1";
  /**
   * Planet 2 Water world 1
   */
  public static final String WATERWORLD1 = "WATERWORLD1";
  /**
   * Planet 3 Water world 2
   */
  public static final String WATERWORLD2 = "WATERWORLD2";
  /**
   * Planet 4 Iron world 1
   */
  public static final String IRONPLANET1 = "IRONPLANET1";
  /**
   * Planet 5 Iron world 2
   */
  public static final String IRONPLANET2 = "IRONPLANET2";

  /**
   * Planet 6 Water world 3
   */
  public static final String WATERWORLD3 = "WATERWORLD3";

  /**
   * Planet 7 Water world 4
   */
  public static final String WATERWORLD4 = "WATERWORLD4";

  /**
   * Planet 8 Ice world 1
   */
  public static final String ICEWORLD1 = "ICEWORLD1";

  /**
   * Planet 9 Ice world 2
   */
  public static final String ICEWORLD2 = "ICEWORLD2";

  /**
   * Planet 10 iron world 3
   */
  public static final String IRONPLANET3 = "IRONPLANET3";
  /**
   * Planet 11 carbon world 1
   */
  public static final String CARBONWORLD1 = "CARBONWORLD1";
  /**
   * Planet 12 Ice world 3
   */
  public static final String ICEWORLD3 = "ICEWORLD3";
  /**
   * Planet 13 Iron world 4
   */
  public static final String IRONPLANET4 = "IRONPLANET4";
  /**
   * Planet 14 carbon world 2
   */
  public static final String CARBONWORLD2 = "CARBONWORLD2";
  /**
   * Planet 15 desert world 1
   */
  public static final String DESERTWORLD1 = "DESERTWORLD1";
  /**
   * Planet 16 water world 5
   */
  public static final String WATERWORLD5 = "WATERWORLD5";
  /**
   * Planet 17 water world 6
   */
  public static final String WATERWORLD6 = "WATERWORLD6";
  /**
   * Planet 18 water world 7
   */
  public static final String WATERWORLD7 = "WATERWORLD7";
  /**
   * Planet 19 Ice world 4
   */
  public static final String ICEWORLD4 = "ICEWORLD4";
  /**
   * Planet 20 water world 8
   */
  public static final String WATERWORLD8 = "WATERWORLD8";
  /**
   * Planet 21 desert world 2
   */
  public static final String DESERTWORLD2 = "DESERTWORLD2";
  /**
   * Planet 22 water world 9
   */
  public static final String WATERWORLD9 = "WATERWORLD9";
  /**
   * Planet 23 Iron world 5
   */
  public static final String IRONPLANET5 = "IRONPLANET5";
  /**
   * Planet 24 Iron world 6
   */
  public static final String IRONPLANET6 = "IRONPLANET6";
  /**
   * Planet 25 desert world 3
   */
  public static final String DESERTWORLD3 = "DESERTWORLD3";
  /**
   * Planet 26 carbon world 3
   */
  public static final String CARBONWORLD3 = "CARBONWORLD3";
  /**
   * Planet 27 artificial planet 1
   */
  public static final String ARTIFICIALWORLD1 = "ARTIFICIALWORLD1";
  /**
   * Deep space anchor 1, place where starbase can be deploy
   */
  public static final String DEEP_SPACE_ANCHOR1 = "DEEPSPACEANCHOR1";
  /**
   * Deep space anchor 2, place where starbase can be deploy
   */
  public static final String DEEP_SPACE_ANCHOR2 = "DEEPSPACEANCHOR2";

  /**
   * Fog of war tile (Overlay)
   */
  public static final String FOG_OF_WAR = "FOG_OF_WAR";
  /**
   * Uncharted tile (Overlay)
   */
  public static final String UNCHARTED = "UNCHARTED";

  /**
   * Culture tile for player 0 (Overlay)
   */
  public static final String PLAYER_0 = "Player_0";
  /**
   * Culture tile for player 1 (Overlay)
   */
  public static final String PLAYER_1 = "Player_1";
  /**
   * Culture tile for player 2 (Overlay)
   */
  public static final String PLAYER_2 = "Player_2";
  /**
   * Culture tile for player 3 (Overlay)
   */
  public static final String PLAYER_3 = "Player_3";
  /**
   * Culture tile for player 4 (Overlay)
   */
  public static final String PLAYER_4 = "Player_4";
  /**
   * Culture tile for player 5 (Overlay)
   */
  public static final String PLAYER_5 = "Player_5";
  /**
   * Culture tile for player 6 (Overlay)
   */
  public static final String PLAYER_6 = "Player_6";
  /**
   * Culture tile for player 7 (Overlay)
   */
  public static final String PLAYER_7 = "Player_7";
  /**
   * Culture tile for player 8 (Overlay)
   */
  public static final String PLAYER_8 = "Player_8";

  /**
   * Ship color tile for player 0 (Overlay)
   */
  public static final String PLAYER_SHIP_0 = "Player_Ship_0";
  /**
   * Ship color tile for player 1 (Overlay)
   */
  public static final String PLAYER_SHIP_1 = "Player_Ship_1";
  /**
   * Ship color tile for player 2 (Overlay)
   */
  public static final String PLAYER_SHIP_2 = "Player_Ship_2";
  /**
   * Ship color tile for player 3 (Overlay)
   */
  public static final String PLAYER_SHIP_3 = "Player_Ship_3";
  /**
   * Ship color tile for player 4 (Overlay)
   */
  public static final String PLAYER_SHIP_4 = "Player_Ship_4";
  /**
   * Ship color tile for player 5 (Overlay)
   */
  public static final String PLAYER_SHIP_5 = "Player_Ship_5";
  /**
   * Ship color tile for player 6 (Overlay)
   */
  public static final String PLAYER_SHIP_6 = "Player_Ship_6";
  /**
   * Ship color tile for player 7 (Overlay)
   */
  public static final String PLAYER_SHIP_7 = "Player_Ship_7";
  /**
   * Ship color tile for player 8 (Overlay)
   */
  public static final String PLAYER_SHIP_8 = "Player_Ship_8";
  /**
   * Space anomaly tile: Credits
   */
  public static final String SPACE_ANOMALY_CREDITS = "SpaceAnomalyCredits";
  /**
   * Space anomaly tile: New tech
   */
  public static final String SPACE_ANOMALY_TECH = "SpaceAnomalyTech";
  /**
   * Space anomaly tile: Ship
   */
  public static final String SPACE_ANOMALY_SHIP = "SpaceAnomalyShip";
  /**
   * Space anomaly tile: Map
   */
  public static final String SPACE_ANOMALY_MAP = "SpaceAnomalyMap";
  /**
   * Space anomaly tile: DSA aka Deep Space Anchor
   */
  public static final String SPACE_ANOMALY_DSA = "SpaceAnomalyDSA";
  /**
   * Space anomaly tile: SpaceAnomaly
   */
  public static final String SPACE_ANOMALY = "SpaceAnomaly";
  /**
   * Space anomaly tile: Pirate Lair
   */
  public static final String SPACE_ANOMALY_LAIR = "SpaceAnomalyLair";
  /**
   * Space anomaly tile: Pirate
   */
  public static final String SPACE_ANOMALY_PIRATE = "SpaceAnomalyPirate";
  /**
   * Space anomaly tile: Monster
   */
  public static final String SPACE_ANOMALY_MONSTER = "SpaceAnomalyMonster";
  /**
   * Space anomaly tile: Ancient Mechion
   */
  public static final String SPACE_ANOMALY_MECHION = "SpaceAnomalyMechion";
  /**
   * Space anomaly tile: Time Warp
   */
  public static final String SPACE_ANOMALY_TIME_WARP = "SpaceAnomalyTimeWarp";
  /**
   * Space anomaly tile: Rare tech
   */
  public static final String SPACE_ANOMALY_RARE_TECH = "SpaceAnomalyRareTech";
  /**
   * Worm hole tile 1
   */
  public static final String WORM_HOLE1 = "WormHole1";
  /**
   * Worm hole tile 2
   */
  public static final String WORM_HOLE2 = "WormHole2";

  /**
   * Black North West
   */
  public static final String BLACKHOLE_NW = "BLACKHOLE_NW";
  /**
   * Blackhole North
   */
  public static final String BLACKHOLE_N = "BLACKHOLE_N";
  /**
   * Blackhole North East
   */
  public static final String BLACKHOLE_NE = "BLACKHOLE_NE";
  /**
   * Blackhole East
   */
  public static final String BLACKHOLE_E = "BLACKHOLE_E";
  /**
   * Blackhole South East
   */
  public static final String BLACKHOLE_SE = "BLACKHOLE_SE";
  /**
   * Blackhole South
   */
  public static final String BLACKHOLE_S = "BLACKHOLE_S";
  /**
   * Blackhole South West
   */
  public static final String BLACKHOLE_SW = "BLACKHOLE_SW";
  /**
   * Blackhole West
   */
  public static final String BLACKHOLE_W = "BLACKHOLE_W";
  /**
   * Blackhole Center
   */
  public static final String BLACKHOLE_C = "BLACKHOLE_C";

  /**
   * List of non harmful space anomalies
   */
  public static final String[] NON_HARMULFUL_SPACE_ANOMALIES = {
      SPACE_ANOMALY_CREDITS, SPACE_ANOMALY_TECH, SPACE_ANOMALY_DSA,
      SPACE_ANOMALY_MAP, SPACE_ANOMALY_SHIP, SPACE_ANOMALY_MECHION,
      SPACE_ANOMALY_RARE_TECH};

  /**
   * List of harmful space anomalies
   */
  public static final String[] HARMULFUL_SPACE_ANOMALIES = {
      SPACE_ANOMALY_LAIR, SPACE_ANOMALY, SPACE_ANOMALY_PIRATE,
      SPACE_ANOMALY_TIME_WARP};

  /**
   * Get random space anomaly
   * @param harmful Is harmful anomalies allowed
   * @param board Is board player present
   * @return Space anomaly
   */
  public static String getRandomSpaceAnomaly(final boolean harmful,
      final boolean board) {
    ArrayList<String> list = new ArrayList<>();
    for (String str : NON_HARMULFUL_SPACE_ANOMALIES) {
      list.add(str);
    }
    if (harmful) {
      for (String str : HARMULFUL_SPACE_ANOMALIES) {
        if (!board) {
          if (str.equals(SPACE_ANOMALY)) {
            list.add(str);
          }
        } else {
          list.add(str);
        }
      }
    }
    int value = DiceGenerator.getRandom(list.size() - 1);
    return list.get(value);
  }
  /**
   * Description about deep space anchor
   */
  public static final String DEEP_SPACE_ANCHOR_DESCRIPTION = "Deep space anchor"
      + " is special place in space and time where big space constructions can"
      + " be safely build. This ideal place for Deep Space Stations.";

  /**
   * Description about star
   */
  public static final String STAR_DESCRIPTION = "Stars are massive stellar "
      + "objects which radiants energy into outer space. Energy is genereated"
      + " from thermonuclear fusion of hydrogen and helium in "
      + "core of the star.";
  /**
   * Description about blackhole
   */
  public static final String BLACKHOLE_DESCRIPTION = "Super massive black holes"
      + " have mass that is in order of one hundred thousands to billion times"
      + " of star. There is super massive black hole in center of each galaxy.";
  /**
   * Description about space anomaly
   */
  public static final String SPACE_ANOMALY_DESCRIPTION = "Space anomalies are"
      + " unknown objects floating in space. Space anomaly can be explored to"
      + " reveal it's secrets.";
  /**
   * Description about space anomaly
   */
  public static final String WORM_HOLE_DESCRIPTION = "Worm hole is a tunnel"
      + " between two locations in spacetime continuum. Since space is"
      + " constantly moving also other end of tunnel is moving.";

}
