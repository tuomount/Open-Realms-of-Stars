package org.openRealmOfStars.mapTiles;

import java.util.ArrayList;

import org.openRealmOfStars.utilities.DiceGenerator;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016-2022 Tuomo Untinen
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
   * Blue Star North West
   */
  public static final String BLUE_STAR_NW = "BLUE_STAR_NW";
  /**
   * Blue Star North
   */
  public static final String BLUE_STAR_N = "BLUE_STAR_N";
  /**
   * Blue Star North East
   */
  public static final String BLUE_STAR_NE = "BLUE_STAR_NE";
  /**
   * Blue Star East
   */
  public static final String BLUE_STAR_E = "BLUE_STAR_E";
  /**
   * Blue Star South East
   */
  public static final String BLUE_STAR_SE = "BLUE_STAR_SE";
  /**
   * Blue Star South
   */
  public static final String BLUE_STAR_S = "BLUE_STAR_S";
  /**
   * Blue Star South West
   */
  public static final String BLUE_STAR_SW = "BLUE_STAR_SW";
  /**
   * Blue Star West
   */
  public static final String BLUE_STAR_W = "BLUE_STAR_W";
  /**
   * Blue Star Center
   */
  public static final String BLUE_STAR_C = "BLUE_STAR_C";

  /**
   * Star North West
   */
  public static final String STAR_NW = "STAR_NW";
  /**
   * Star North
   */
  public static final String STAR_N = "STAR_N";
  /**
   * Star North East
   */
  public static final String STAR_NE = "STAR_NE";
  /**
   * Star East
   */
  public static final String STAR_E = "STAR_E";
  /**
   * Star South East
   */
  public static final String STAR_SE = "STAR_SE";
  /**
   * Star South
   */
  public static final String STAR_S = "STAR_S";
  /**
   * Star South West
   */
  public static final String STAR_SW = "STAR_SW";
  /**
   * Star West
   */
  public static final String STAR_W = "STAR_W";
  /**
   * Star Center
   */
  public static final String STAR_C = "STAR_C";

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
   * Jupiter North West
   */
  public static final String JUPITER_NW = "JUPITER_NW";
  /**
   * Jupiter North East
   */
  public static final String JUPITER_NE = "JUPITER_NE";
  /**
   * Jupiter South East
   */
  public static final String JUPITER_SE = "JUPITER_SE";
  /**
   * Jupiter South West
   */
  public static final String JUPITER_SW = "JUPITER_SW";

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
   * Culture tile for player BLUE (Overlay)
   */
  public static final String PLAYER_BLUE = "Player_BLUE";
  /**
   * Culture tile for player GREEN (Overlay)
   */
  public static final String PLAYER_GREEN = "Player_GREEN";
  /**
   * Culture tile for player WHITE (Overlay)
   */
  public static final String PLAYER_WHITE = "Player_WHITE";
  /**
   * Culture tile for player ORANGE (Overlay)
   */
  public static final String PLAYER_ORANGE = "Player_ORANGE";
  /**
   * Culture tile for player CYAN (Overlay)
   */
  public static final String PLAYER_CYAN = "Player_CYAN";
  /**
   * Culture tile for player PINK (Overlay)
   */
  public static final String PLAYER_PINK = "Player_PINK";
  /**
   * Culture tile for player RED (Overlay)
   */
  public static final String PLAYER_RED = "Player_RED";
  /**
   * Culture tile for player YELLOW (Overlay)
   */
  public static final String PLAYER_YELLOW = "Player_YELLOW";
  /**
   * Culture tile for player BLACK (Overlay)
   */
  public static final String PLAYER_BLACK = "Player_BLACK";
  /**
   * Culture tile for player PURPLE (Overlay)
   */
  public static final String PLAYER_PURPLE = "Player_PURPLE";
  /**
   * Culture tile for player BROWN (Overlay)
   */
  public static final String PLAYER_BROWN = "Player_BROWN";
  /**
   * Culture tile for player LIME (Overlay)
   */
  public static final String PLAYER_LIME = "Player_LIME";
  /**
   * Culture tile for player CHESTNUT (Overlay)
   */
  public static final String PLAYER_CHESTNUT = "Player_CHESTNUT";
  /**
   * Culture tile for player ROSE (Overlay)
   */
  public static final String PLAYER_ROSE = "Player_ROSE";
  /**
   * Culture tile for player BANANA (Overlay)
   */
  public static final String PLAYER_BANANA = "Player_BANANA";
  /**
   * Culture tile for player GRAY (Overlay)
   */
  public static final String PLAYER_GRAY = "Player_GRAY";
  /**
   * Culture tile for player TAN (Overlay)
   */
  public static final String PLAYER_TAN = "Player_TAN";
  /**
   * Culture tile for player CORAL (Overlay)
   */
  public static final String PLAYER_CORAL = "Player_CORAL";
  /**
   * Culture tile for player OLIVE (Overlay)
   */
  public static final String PLAYER_OLIVE = "Player_OLIVE";
  /**
   * Culture tile for player SKY (Overlay)
   */
  public static final String PLAYER_SKY = "Player_SKY";

  /**
   * Ship color tile for player BLUE (Overlay)
   */
  public static final String PLAYER_SHIP_BLUE = "Player_Ship_BLUE";
  /**
   * Ship color tile for player GREEN (Overlay)
   */
  public static final String PLAYER_SHIP_GREEN = "Player_Ship_GREEN";
  /**
   * Ship color tile for player WHITE (Overlay)
   */
  public static final String PLAYER_SHIP_WHITE = "Player_Ship_WHITE";
  /**
   * Ship color tile for player ORANGE (Overlay)
   */
  public static final String PLAYER_SHIP_ORANGE = "Player_Ship_ORANGE";
  /**
   * Ship color tile for player CYAN (Overlay)
   */
  public static final String PLAYER_SHIP_CYAN = "Player_Ship_CYAN";
  /**
   * Ship color tile for player PINK (Overlay)
   */
  public static final String PLAYER_SHIP_PINK = "Player_Ship_PINK";
  /**
   * Ship color tile for player RED (Overlay)
   */
  public static final String PLAYER_SHIP_RED = "Player_Ship_RED";
  /**
   * Ship color tile for player YELLOW (Overlay)
   */
  public static final String PLAYER_SHIP_YELLOW = "Player_Ship_YELLOW";
  /**
   * Ship color tile for player BLACK (Overlay)
   */
  public static final String PLAYER_SHIP_BLACK = "Player_Ship_BLACK";
  /**
   * Ship color tile for player PURPLE (Overlay)
   */
  public static final String PLAYER_SHIP_PURPLE = "Player_Ship_PURPLE";
  /**
   * Ship color tile for player BROWN (Overlay)
   */
  public static final String PLAYER_SHIP_BROWN = "Player_Ship_BROWN";
  /**
   * Ship color tile for player LIME(Overlay)
   */
  public static final String PLAYER_SHIP_LIME = "Player_Ship_LIME";
  /**
   * Ship color tile for player CHESTNUT(Overlay)
   */
  public static final String PLAYER_SHIP_CHESTNUT = "Player_Ship_CHESTNUT";
  /**
   * Ship color tile for player ROSE(Overlay)
   */
  public static final String PLAYER_SHIP_ROSE = "Player_Ship_ROSE";
  /**
   * Ship color tile for player BANANA(Overlay)
   */
  public static final String PLAYER_SHIP_BANANA = "Player_Ship_BANANA";
  /**
   * Ship color tile for player GRAY(Overlay)
   */
  public static final String PLAYER_SHIP_GRAY = "Player_Ship_GRAY";
  /**
   * Ship color tile for player TAN(Overlay)
   */
  public static final String PLAYER_SHIP_TAN = "Player_Ship_TAN";
  /**
   * Ship color tile for player CORAL(Overlay)
   */
  public static final String PLAYER_SHIP_CORAL = "Player_Ship_CORAL";
  /**
   * Ship color tile for player OLIVE(Overlay)
   */
  public static final String PLAYER_SHIP_OLIVE = "Player_Ship_OLIVE";
  /**
   * Ship color tile for player SKY(Overlay)
   */
  public static final String PLAYER_SHIP_SKY = "Player_Ship_SKY";
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
   * Planet Earth
   */
  public static final String PLANET_EARTH = "PLANET_EARTH";
  /**
   * Planet Mars
   */
  public static final String PLANET_MARS = "PLANET_MARS";
  /**
   * Planet Jupiter
   */
  public static final String PLANET_JUPITER = "PLANET_JUPITER";

  /**
   * List of non harmful space anomalies
   */
  public static final String[] NON_HARMFUL_SPACE_ANOMALIES = {
      SPACE_ANOMALY_CREDITS, SPACE_ANOMALY_TECH, SPACE_ANOMALY_DSA,
      SPACE_ANOMALY_MAP, SPACE_ANOMALY_SHIP, SPACE_ANOMALY_MECHION,
      SPACE_ANOMALY_RARE_TECH};

  /**
   * List of harmful space anomalies which are not related to space pirates or
   * monsters.
   */
  public static final String[] HARMFUL_SPACE_ANOMALIES = {
      SPACE_ANOMALY, SPACE_ANOMALY_TIME_WARP};

  /**
   * List of harmful space anomalies which are related to space pirates.
   */
  public static final String[] HARMFUL_SPACE_PIRATES = {
      SPACE_ANOMALY_LAIR, SPACE_ANOMALY_PIRATE
  };
  /**
   * List of harmful space anomalies which are related to space monsters.
   */
  public static final String[] HARMFUL_SPACE_MONSTERS = {
      SPACE_ANOMALY_MONSTER};

  /**
   * Get random space anomaly
   * @param harmful Is harmful anomalies allowed
   * @param pirate Is board pirate player present
   * @param monster Is board monster player present
   * @return Space anomaly
   */
  public static String getRandomSpaceAnomaly(final boolean harmful,
      final boolean pirate, final boolean monster) {
    ArrayList<String> list = new ArrayList<>();
    for (String str : NON_HARMFUL_SPACE_ANOMALIES) {
      list.add(str);
    }
    if (harmful) {
      for (String str : HARMFUL_SPACE_ANOMALIES) {
        list.add(str);
      }
      if (pirate) {
        for (String str : HARMFUL_SPACE_PIRATES) {
          list.add(str);
        }
      }
      if (monster) {
        for (String str : HARMFUL_SPACE_MONSTERS) {
          list.add(str);
        }
      }
    }
    int value = DiceGenerator.getRandom(list.size() - 1);
    //int value = list.size() - 1;
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
