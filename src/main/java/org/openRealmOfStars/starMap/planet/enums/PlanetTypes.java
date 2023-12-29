package org.openRealmOfStars.starMap.planet.enums;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2018-2022 Tuomo Untinen
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

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.openRealmOfStars.gui.util.GuiStatics;
import org.openRealmOfStars.mapTiles.TileNames;
import org.openRealmOfStars.mapTiles.Tiles;
import org.openRealmOfStars.player.race.SpaceRace;
import org.openRealmOfStars.starMap.newsCorp.ImageInstruction;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
*
* Planet types aka world type matched to images
*
*/
public enum PlanetTypes {
  /**
   * Barren world for first image. Planet looks plain rocky planet
   */
  BARRENWORLD1(Tiles.getTileByName(TileNames.ROCK1).getIndex(),
      GuiStatics.BIG_PLANET_ROCK1, WorldType.BARRENWORLD,
      ImageInstruction.PLANET_ROCK1, false, false),
  /**
   * First water world images
   */
  WATERWORLD1(Tiles.getTileByName(TileNames.WATERWORLD1).getIndex(),
      GuiStatics.BIG_PLANET_WATERWORLD1, WorldType.WATERWORLD,
      ImageInstruction.PLANET_WATERWORLD1, false,  false),
  /**
   * Second water world images
   */
  WATERWORLD2(Tiles.getTileByName(TileNames.WATERWORLD2).getIndex(),
      GuiStatics.BIG_PLANET_WATERWORLD2, WorldType.WATERWORLD,
      ImageInstruction.PLANET_WATERWORLD2, false, false),
  /**
   * First volcanic world images
   */
  VOLCANICWORLD1(Tiles.getTileByName(TileNames.VOLCANICPLANET1).getIndex(),
      GuiStatics.BIG_PLANET_VOLCANICPLANET1, WorldType.VOLCANICWORLD,
      ImageInstruction.PLANET_VOLCANICWORLD1, false, false),
  /**
   * Gas giant 1
   */
  GASGIANT1(Tiles.getTileByName(TileNames.GAS_GIANT_1_NE).getIndex(),
      GuiStatics.BIG_GASWORLD1, WorldType.GASWORLD,
      ImageInstruction.PLANET_GASGIANT1, true, false),
  /**
   * Second volcanic world images
   */
  VOLCANICWORLD2(Tiles.getTileByName(TileNames.VOLCANICPLANET2).getIndex(),
      GuiStatics.BIG_PLANET_VOLCANICPLANET2, WorldType.VOLCANICWORLD,
      ImageInstruction.PLANET_VOLCANICWORLD2, false, false),
  /**
   * Third water world images
   */
  WATERWORLD3(Tiles.getTileByName(TileNames.WATERWORLD3).getIndex(),
      GuiStatics.BIG_PLANET_WATERWORLD3, WorldType.WATERWORLD,
      ImageInstruction.PLANET_WATERWORLD3, false, false),
  /**
   * Fourth water world images
   */
  WATERWORLD4(Tiles.getTileByName(TileNames.WATERWORLD4).getIndex(),
      GuiStatics.BIG_PLANET_WATERWORLD4, WorldType.WATERWORLD,
      ImageInstruction.PLANET_WATERWORLD4, false, false),
  /**
   * Gas giant 2
   */
  GASGIANT2(Tiles.getTileByName(TileNames.GAS_GIANT_2_NE).getIndex(),
      GuiStatics.BIG_GASWORLD2, WorldType.GASWORLD,
      ImageInstruction.PLANET_GASGIANT2, true, false),
  /**
   * First ice world images
   */
  ICEWORLD1(Tiles.getTileByName(TileNames.ICEWORLD1).getIndex(),
      GuiStatics.BIG_PLANET_ICEWORLD1, WorldType.ICEWORLD,
      ImageInstruction.PLANET_ICEWORLD1, false, false),
  /**
   * Second ice world images
   */
  ICEWORLD2(Tiles.getTileByName(TileNames.ICEWORLD2).getIndex(),
      GuiStatics.BIG_PLANET_ICEWORLD2, WorldType.ICEWORLD,
      ImageInstruction.PLANET_ICEWORLD2, false, false),
  /**
   * Third volcanic world images
   */
  VOLCANICWORLD3(Tiles.getTileByName(TileNames.VOLCANICPLANET3).getIndex(),
      GuiStatics.BIG_PLANET_VOLCANICPLANET3, WorldType.VOLCANICWORLD,
      ImageInstruction.PLANET_VOLCANICWORLD3, false, false),
  /**
   * First swamp world images
   */
  SWAMPWORLD1(Tiles.getTileByName(TileNames.SWAMPWORLD1).getIndex(),
      GuiStatics.BIG_PLANET_SWAMPWORLD1, WorldType.SWAMPWORLD,
      ImageInstruction.PLANET_SWAMPWORLD1, false, false),
  /**
   * Gas giant 3
   */
  GASGIANT3(Tiles.getTileByName(TileNames.GAS_GIANT_3_NE).getIndex(),
      GuiStatics.BIG_GASWORLD3, WorldType.GASWORLD,
      ImageInstruction.PLANET_GASGIANT3, true, false),
  /**
   * Third ice world images
   */
  ICEWORLD3(Tiles.getTileByName(TileNames.ICEWORLD3).getIndex(),
      GuiStatics.BIG_PLANET_ICEWORLD3, WorldType.ICEWORLD,
      ImageInstruction.PLANET_ICEWORLD3, false, false),
  /**
   * Fourth volcanic world images
   */
  VOLCANICWORLD4(Tiles.getTileByName(TileNames.VOLCANICPLANET4).getIndex(),
      GuiStatics.BIG_PLANET_VOLCANICPLANET4, WorldType.VOLCANICWORLD,
      ImageInstruction.PLANET_VOLCANICWORLD4, false, false),
  /**
   * second swamp world images
   */
  SWAMPWORLD2(Tiles.getTileByName(TileNames.SWAMPWORLD2).getIndex(),
      GuiStatics.BIG_PLANET_SWAMPWORLD2, WorldType.SWAMPWORLD,
      ImageInstruction.PLANET_SWAMPWORLD2, false, false),
  /**
   * First desert world images
   */
  DESERTWORLD1(Tiles.getTileByName(TileNames.DESERTWORLD1).getIndex(),
      GuiStatics.BIG_PLANET_DESERTWORLD1, WorldType.DESERTWORLD,
      ImageInstruction.PLANET_DESERTWORLD1, false, false),
  /**
   * Fifth water world images
   */
  WATERWORLD5(Tiles.getTileByName(TileNames.WATERWORLD5).getIndex(),
      GuiStatics.BIG_PLANET_WATERWORLD5, WorldType.WATERWORLD,
      ImageInstruction.PLANET_WATERWORLD5, false, false),
  /**
   * Sixth water world images
   */
  WATERWORLD6(Tiles.getTileByName(TileNames.WATERWORLD6).getIndex(),
      GuiStatics.BIG_PLANET_WATERWORLD6, WorldType.WATERWORLD,
      ImageInstruction.PLANET_WATERWORLD6, false, false),
  /**
   * Seventh water world images
   */
  WATERWORLD7(Tiles.getTileByName(TileNames.WATERWORLD7).getIndex(),
      GuiStatics.BIG_PLANET_WATERWORLD7, WorldType.WATERWORLD,
      ImageInstruction.PLANET_WATERWORLD7, false, false),
  /**
   * Fourth ice world images
   */
  ICEWORLD4(Tiles.getTileByName(TileNames.ICEWORLD4).getIndex(),
      GuiStatics.BIG_PLANET_ICEWORLD4, WorldType.ICEWORLD,
      ImageInstruction.PLANET_ICEWORLD4, false, false),
  /**
   * Eigth water world images
   */
  WATERWORLD8(Tiles.getTileByName(TileNames.WATERWORLD8).getIndex(),
      GuiStatics.BIG_PLANET_WATERWORLD8, WorldType.WATERWORLD,
      ImageInstruction.PLANET_WATERWORLD8, false, false),
  /**
   * Second desert world images
   */
  DESERTWORLD2(Tiles.getTileByName(TileNames.DESERTWORLD2).getIndex(),
      GuiStatics.BIG_PLANET_DESERTWORLD2, WorldType.DESERTWORLD,
      ImageInstruction.PLANET_DESERTWORLD2, false, false),
  /**
   * Nineth water world images
   */
  WATERWORLD9(Tiles.getTileByName(TileNames.WATERWORLD9).getIndex(),
      GuiStatics.BIG_PLANET_WATERWORLD9, WorldType.WATERWORLD,
      ImageInstruction.PLANET_WATERWORLD9, false, false),
  /**
   * Fifth volcanic world images
   */
  VOLCANICWORLD5(Tiles.getTileByName(TileNames.VOLCANICPLANET5).getIndex(),
      GuiStatics.BIG_PLANET_VOLCANICPLANET5, WorldType.VOLCANICWORLD,
      ImageInstruction.PLANET_VOLCANICWORLD5, false, false),
  /**
   * Sixth volcanic world images
   */
  VOLCANICWORLD6(Tiles.getTileByName(TileNames.VOLCANICPLANET6).getIndex(),
      GuiStatics.BIG_PLANET_VOLCANICPLANET6, WorldType.VOLCANICWORLD,
      ImageInstruction.PLANET_VOLCANICWORLD6, false, false),
  /**
   * Third desert world images
   */
  DESERTWORLD3(Tiles.getTileByName(TileNames.DESERTWORLD3).getIndex(),
      GuiStatics.BIG_PLANET_DESERTWORLD3, WorldType.DESERTWORLD,
      ImageInstruction.PLANET_DESERTWORLD3, false, false),
  /**
   * Third swamp world images
   */
  SWAMPWORLD3(Tiles.getTileByName(TileNames.SWAMPWORLD3).getIndex(),
      GuiStatics.BIG_PLANET_SWAMPWORLD3, WorldType.SWAMPWORLD,
      ImageInstruction.PLANET_SWAMPWORLD3, false, false),
  /**
   * First artificial world images
   */
  ARTIFICIALWORLD1(Tiles.getTileByName(TileNames.ARTIFICIALWORLD1).getIndex(),
      GuiStatics.BIG_PLANET_ARTIFICIALPLANET1, WorldType.ARTIFICALWORLD,
      ImageInstruction.PLANET_ARTIFICIALWORLD1, false, false),
  /**
   * Planet Earth
   */
  PLANET_EARTH(Tiles.getTileByName(TileNames.PLANET_EARTH).getIndex(),
      GuiStatics.BIG_PLANET_EARTH, WorldType.WATERWORLD,
      ImageInstruction.PLANET_EARTH, false, true),
  /**
   * Planet Mars
   */
  PLANET_MARS(Tiles.getTileByName(TileNames.PLANET_MARS).getIndex(),
      GuiStatics.BIG_PLANET_MARS, WorldType.DESERTWORLD,
      ImageInstruction.PLANET_MARS, false, true),
  /**
   * Planet Jupiter
   */
  PLANET_JUPITER(Tiles.getTileByName(TileNames.PLANET_JUPITER).getIndex(),
      GuiStatics.BIG_PLANET_JUPITER, WorldType.GASWORLD,
      ImageInstruction.PLANET_JUPITER, true, true),
  /**
   * Planet Saturn
   */
  PLANET_SATURN(Tiles.getTileByName(TileNames.PLANET_SATURN).getIndex(),
      GuiStatics.BIG_PLANET_SATURN, WorldType.GASWORLD,
      ImageInstruction.PLANET_SATURN, true, true),
  /**
   * Ice Giant1
   */
  ICEGIANT1(Tiles.getTileByName(TileNames.ICEGIANT1).getIndex(),
      GuiStatics.BIG_PLANET_ICEGIANT1, WorldType.ICEGIANTWORLD,
      ImageInstruction.PLANET_ICEGIANT1, true, false),
  /**
   * Ice Giant2
   */
  ICEGIANT2(Tiles.getTileByName(TileNames.ICEGIANT2).getIndex(),
      GuiStatics.BIG_PLANET_ICEGIANT2, WorldType.ICEGIANTWORLD,
      ImageInstruction.PLANET_ICEGIANT2, true, false);




  /**
   * Get the number of planet types available
   * @return Number of planet types available
   */
  public static int numberOfPlanetTypes() {
    return PlanetTypes.values().length;
  }

  /**
   * Get random planet type.
   * @param gasGiant True for Gas giant, false for normal planets
   * @return PlanetTypes
   */
  public static PlanetTypes getRandomPlanetType(final boolean gasGiant) {
    if (gasGiant) {
      return getRandomPlanetType(true, false, false);
    }
    return getRandomPlanetType(false, true, false);
  }
  /**
   * Get random planet type
   * @param gasGiant Include gas giants
   * @param normalPlanet Include normal planets
   * @param artificalPlanets Include articial planets
   * @return Random planet type
   */
  public static PlanetTypes getRandomPlanetType(final boolean gasGiant,
      final boolean normalPlanet, final boolean artificalPlanets) {
    ArrayList<PlanetTypes> list = new ArrayList<>();
    for (PlanetTypes type : PlanetTypes.values()) {
      if (type.isNamedPlanet() && !artificalPlanets) {
        continue;
      }
      if (type.worldType == WorldType.ARTIFICALWORLD && artificalPlanets) {
        list.add(type);
      } else if (type.isGasGiant() && gasGiant) {
        list.add(type);
      } else if (normalPlanet && type.worldType != WorldType.ARTIFICALWORLD
          && !type.isGasGiant()) {
        list.add(type);
      }
    }
    int index = DiceGenerator.getRandom(0, list.size() - 1);
    return list.get(index);
  }

  /**
   * Get Highest random start planet type.
   * @param race Space race for best starting planet
   * @return Planet type
   */
  public static PlanetTypes getRandomStartPlanetType(final SpaceRace race) {
    ArrayList<PlanetTypes> list = new ArrayList<>();
    int highestValue = 0;
    for (WorldType worldType : WorldType.values()) {
      if (worldType != WorldType.ARTIFICALWORLD
          && race.getWorldTypeBaseValue(worldType) > highestValue) {
          highestValue = race.getWorldTypeBaseValue(worldType);
      }
    }
    for (PlanetTypes type : PlanetTypes.values()) {
      if (type.isNamedPlanet()) {
        continue;
      }
      if (type.worldType != WorldType.ARTIFICALWORLD && !type.isGasGiant()) {
        int value = race.getWorldTypeBaseValue(type.getWorldType());
        if (value == highestValue) {
          list.add(type);
        }
      }
    }
    if (list.size() == 0) {
      // This should not really happen.
      return null;
    }
    int index = DiceGenerator.getRandom(0, list.size() - 1);
    return list.get(index);
  }
  /**
   * Tile index
   */
  private int tile;

  /**
   * Gas giant or not. True for gas giants
   */
  private boolean gasGiant;
  /**
   * Big image for show full size planet
   */
  private BufferedImage image;

  /**
   * World type
   */
  private WorldType worldType;

  /**
   * Image instructions for news
   */
  private String instructions;

  /**
   * Planet is known named planet.
   * For example Earth would be planet like this.
   */
  private boolean namedPlanet;
  /**
   * Constructor for planet type enum
   * @param tileIndex TileIndex number
   * @param bigImage Buffered Image for full size planet
   * @param type WorldType
   * @param imageInstruction Image instructions for news
   * @param isGasGiant True for gas giants
   * @param namedPlanet True for named planets
   */
  PlanetTypes(final int tileIndex, final BufferedImage bigImage,
      final WorldType type, final String imageInstruction,
      final boolean isGasGiant, final boolean namedPlanet) {
    tile = tileIndex;
    image = bigImage;
    worldType = type;
    instructions = imageInstruction;
    gasGiant = isGasGiant;
    this.namedPlanet = namedPlanet;
  }

  /**
   * Get Tile index
   * @return Tile index
   */
  public int getTileIndex() {
    return tile;
  }

  /**
   * Get Full size planet image
   * @return Buffered Image
   */
  public BufferedImage getImage() {
    return image;
  }

  /**
   * Get world type for planet.
   * @return World Type
   */
  public WorldType getWorldType() {
    return worldType;
  }

  /**
   * Get image instructions for news corporation
   * @return Image instructions as string
   */
  public String getImageInstructions() {
    return instructions;
  }

  /**
   * Get planet type to index
   * @return Planet type index
   */
  public int getPlanetTypeIndex() {
    switch (this) {
      case GASGIANT1: return 0;
      case GASGIANT2: return 1;
      case BARRENWORLD1: return 0;
      case WATERWORLD1: return 1;
      case WATERWORLD2: return 2;
      case VOLCANICWORLD1: return 3;
      case VOLCANICWORLD2: return 4;
      case WATERWORLD3: return 5;
      case WATERWORLD4: return 6;
      case ICEWORLD1: return 7;
      case ICEWORLD2: return 8;
      case VOLCANICWORLD3: return 9;
      case SWAMPWORLD1: return 10;
      case GASGIANT3: return 2;
      case ICEWORLD3: return 11;
      case VOLCANICWORLD4: return 12;
      case SWAMPWORLD2: return 13;
      case DESERTWORLD1: return 14;
      case WATERWORLD5: return 15;
      case WATERWORLD6: return 16;
      case WATERWORLD7: return 17;
      case ICEWORLD4: return 18;
      case WATERWORLD8: return 19;
      case DESERTWORLD2: return 20;
      case WATERWORLD9: return 21;
      case VOLCANICWORLD5: return 22;
      case VOLCANICWORLD6: return 23;
      case DESERTWORLD3: return 24;
      case SWAMPWORLD3: return 25;
      case ARTIFICIALWORLD1: return 26;
      case PLANET_EARTH: return 27;
      case PLANET_MARS: return 28;
      case PLANET_JUPITER: return 29;
      case PLANET_SATURN: return 30;
      case ICEGIANT1: return 31;
      case ICEGIANT2: return 32;
      default:
        throw new IllegalArgumentException("Unknown planet index!!");
    }
  }

  /**
   * Get planet type to index. There are two list:
   * One for regular planet and one for gas giants.
   * @param index World Type index
   * @param gasGiant Set True for gas giant.
   * @return Planet type index
   */
  public static PlanetTypes getPlanetType(final int index,
      final boolean gasGiant) {
    if (gasGiant) {
      switch (index) {
        case  0: return GASGIANT1;
        case  1: return GASGIANT2;
        case  2: return GASGIANT3;
        case  29: return PLANET_JUPITER;
        case  30: return PLANET_SATURN;
        case  31: return ICEGIANT1;
        case  32: return ICEGIANT2;
        default:
          throw new IllegalArgumentException("No planet type available "
              + "for this index!!");
      }
    }
    switch (index) {
      case  0: return BARRENWORLD1;
      case  1: return WATERWORLD1;
      case  2: return WATERWORLD2;
      case  3: return VOLCANICWORLD1;
      case  4: return VOLCANICWORLD2;
      case  5: return WATERWORLD3;
      case  6: return WATERWORLD4;
      case  7: return ICEWORLD1;
      case  8: return ICEWORLD2;
      case  9: return VOLCANICWORLD3;
      case 10: return SWAMPWORLD1;
      case 11: return ICEWORLD3;
      case 12: return VOLCANICWORLD4;
      case 13: return SWAMPWORLD2;
      case 14: return DESERTWORLD1;
      case 15: return WATERWORLD5;
      case 16: return WATERWORLD6;
      case 17: return WATERWORLD7;
      case 18: return ICEWORLD4;
      case 19: return WATERWORLD8;
      case 20: return DESERTWORLD2;
      case 21: return WATERWORLD9;
      case 22: return VOLCANICWORLD5;
      case 23: return VOLCANICWORLD6;
      case 24: return DESERTWORLD3;
      case 25: return SWAMPWORLD3;
      case 26: return ARTIFICIALWORLD1;
      case 27: return PLANET_EARTH;
      case 28: return PLANET_MARS;
      default:
        throw new IllegalArgumentException("No planet type available "
            + "for this index!!");
    }
  }

  /**
   * Get planet type as String. This combines all same types
   * into one. So all water worlds are now water worlds.
   * @return Planet type as string
   */
  public String getTypeAsString() {
    switch (this) {
      case GASGIANT1:
      case GASGIANT2:
      case GASGIANT3: return "Gas giant";
      case BARRENWORLD1: return "Barren world";
      case WATERWORLD1:
      case WATERWORLD2:
      case WATERWORLD3:
      case WATERWORLD4:
      case WATERWORLD5:
      case WATERWORLD6:
      case WATERWORLD7:
      case WATERWORLD8:
      case WATERWORLD9: return "Water world";
      case VOLCANICWORLD1:
      case VOLCANICWORLD2:
      case VOLCANICWORLD3:
      case VOLCANICWORLD4:
      case VOLCANICWORLD5:
      case VOLCANICWORLD6: return "Volcanic world";
      case ICEWORLD1: return "Ice world";
      case ICEWORLD2:
      case ICEWORLD3:
      case ICEWORLD4: return "Ice world";
      case SWAMPWORLD1:
      case SWAMPWORLD2:
      case SWAMPWORLD3: return "Swamp world";
      case DESERTWORLD1:
      case DESERTWORLD2:
      case DESERTWORLD3: return "Desert world";
      case ARTIFICIALWORLD1: return "Artificial world";
      case PLANET_EARTH: return "Water world";
      case PLANET_MARS: return "Iron world";
      case PLANET_JUPITER: return "Gas giant";
      case PLANET_SATURN: return "Gas giant";
      case ICEGIANT1: return "Ice giant";
      case ICEGIANT2: return "Ice giant";
      default:
        throw new IllegalArgumentException("Unknown planet type!!");
    }
  }
  /**
   * Verify that image instruction is valid for planet
   * @param instruction Image Instruction
   * @return True if valid
   */
  public static boolean verifyInstruction(final String instruction) {
    for (PlanetTypes type : PlanetTypes.values()) {
      if (type.getImageInstructions().equals(instruction)) {
        return true;
      }
    }
    return false;
  }
  /**
   * Is planet type gas giant
   * @return True if gasgiant
   */
  public boolean isGasGiant() {
    return this.gasGiant;
  }

  /**
   * Is planet something which is well known and named?
   * @return True if named planet.
   */
  public boolean isNamedPlanet() {
    return this.namedPlanet;
  }
}
