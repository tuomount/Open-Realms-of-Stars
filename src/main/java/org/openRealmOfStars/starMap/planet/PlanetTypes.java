package org.openRealmOfStars.starMap.planet;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.openRealmOfStars.gui.GuiStatics;
import org.openRealmOfStars.mapTiles.TileNames;
import org.openRealmOfStars.mapTiles.Tiles;
import org.openRealmOfStars.starMap.newsCorp.ImageInstruction;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2018  Tuomo Untinen
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
* Planet types aka world type matched to images
*
*/
public enum PlanetTypes {
  /**
   * Silicon world for first image. Planet looks plain rocky planet
   */
  SILICONWORLD1(Tiles.getTileByName(TileNames.ROCK1).getIndex(),
      GuiStatics.BIG_PLANET_ROCK1, WorldType.SILICONWORLD,
      ImageInstruction.PLANET_ROCK1, false),
  /**
   * First water world images
   */
  WATERWORLD1(Tiles.getTileByName(TileNames.WATERWORLD1).getIndex(),
      GuiStatics.BIG_PLANET_WATERWORLD1, WorldType.WATERWORLD,
      ImageInstruction.PLANET_WATERWORLD1, false),
  /**
   * Second water world images
   */
  WATERWORLD2(Tiles.getTileByName(TileNames.WATERWORLD2).getIndex(),
      GuiStatics.BIG_PLANET_WATERWORLD2, WorldType.WATERWORLD,
      ImageInstruction.PLANET_WATERWORLD2, false),
  /**
   * First iron world images
   */
  IRONWORLD1(Tiles.getTileByName(TileNames.IRONPLANET1).getIndex(),
      GuiStatics.BIG_PLANET_IRONPLANET1, WorldType.IRONWORLD,
      ImageInstruction.PLANET_IRONWORLD1, false),
  /**
   * Gas giant 1
   */
  GASGIANT1(Tiles.getTileByName(TileNames.GAS_GIANT_1_NE).getIndex(),
      GuiStatics.BIG_GASWORLD1, WorldType.GASWORLD,
      ImageInstruction.PLANET_GASGIANT1, true),
  /**
   * Second iron world images
   */
  IRONWORLD2(Tiles.getTileByName(TileNames.IRONPLANET2).getIndex(),
      GuiStatics.BIG_PLANET_IRONPLANET2, WorldType.IRONWORLD,
      ImageInstruction.PLANET_IRONWORLD2, false),
  /**
   * Third water world images
   */
  WATERWORLD3(Tiles.getTileByName(TileNames.WATERWORLD3).getIndex(),
      GuiStatics.BIG_PLANET_WATERWORLD3, WorldType.WATERWORLD,
      ImageInstruction.PLANET_WATERWORLD3, false),
  /**
   * Fourth water world images
   */
  WATERWORLD4(Tiles.getTileByName(TileNames.WATERWORLD4).getIndex(),
      GuiStatics.BIG_PLANET_WATERWORLD4, WorldType.WATERWORLD,
      ImageInstruction.PLANET_WATERWORLD4, false),
  /**
   * Gas giant 2
   */
  GASGIANT2(Tiles.getTileByName(TileNames.GAS_GIANT_2_NE).getIndex(),
      GuiStatics.BIG_GASWORLD2, WorldType.GASWORLD,
      ImageInstruction.PLANET_GASGIANT2, true),
  /**
   * First ice world images
   */
  ICEWORLD1(Tiles.getTileByName(TileNames.ICEWORLD1).getIndex(),
      GuiStatics.BIG_PLANET_ICEWORLD1, WorldType.ICEWORLD,
      ImageInstruction.PLANET_ICEWORLD1, false),
  /**
   * Second ice world images
   */
  ICEWORLD2(Tiles.getTileByName(TileNames.ICEWORLD2).getIndex(),
      GuiStatics.BIG_PLANET_ICEWORLD2, WorldType.ICEWORLD,
      ImageInstruction.PLANET_ICEWORLD2, false),
  /**
   * Third iron world images
   */
  IRONWORLD3(Tiles.getTileByName(TileNames.IRONPLANET3).getIndex(),
      GuiStatics.BIG_PLANET_IRONPLANET3, WorldType.IRONWORLD,
      ImageInstruction.PLANET_IRONWORLD3, false),
  /**
   * First carbon world images
   */
  CARBONWORLD1(Tiles.getTileByName(TileNames.CARBONWORLD1).getIndex(),
      GuiStatics.BIG_PLANET_CARBONWORLD1, WorldType.CARBONWORLD,
      ImageInstruction.PLANET_CARBONWORLD1, false);


  /**
   * Get the number of planet types available
   * @return Number of planet types available
   */
  public static int numberOfPlanetTypes() {
    return PlanetTypes.values().length;
  }

  /**
   * Get random planet type.
   * @param gasGiant True for Gas giant
   * @return PlanetTypes
   */
  public static PlanetTypes getRandomPlanetType(final boolean gasGiant) {
    ArrayList<PlanetTypes> list = new ArrayList<>();
    for (PlanetTypes type : PlanetTypes.values()) {
      if (type.gasGiant == gasGiant) {
        list.add(type);
      }
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
   * Constructor for planet type enum
   * @param tileIndex TileIndex number
   * @param bigImage Buffered Image for full size planet
   * @param type WorldType
   * @param imageInstruction Image instructions for news
   * @param isGasGiant True for gas giants
   */
  PlanetTypes(final int tileIndex, final BufferedImage bigImage,
      final WorldType type, final String imageInstruction,
      final boolean isGasGiant) {
    tile = tileIndex;
    image = bigImage;
    worldType = type;
    instructions = imageInstruction;
    gasGiant = isGasGiant;
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
      case SILICONWORLD1: return 0;
      case WATERWORLD1: return 1;
      case WATERWORLD2: return 2;
      case IRONWORLD1: return 3;
      case IRONWORLD2: return 4;
      case WATERWORLD3: return 5;
      case WATERWORLD4: return 6;
      case ICEWORLD1: return 7;
      case ICEWORLD2: return 8;
      case IRONWORLD3: return 9;
      case CARBONWORLD1: return 10;
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
        default:
          throw new IllegalArgumentException("No planet type available "
              + "for this index!!");
      }
    }
    switch (index) {
      case  0: return SILICONWORLD1;
      case  1: return WATERWORLD1;
      case  2: return WATERWORLD2;
      case  3: return IRONWORLD1;
      case  4: return IRONWORLD2;
      case  5: return WATERWORLD3;
      case  6: return WATERWORLD4;
      case  7: return ICEWORLD1;
      case  8: return ICEWORLD2;
      case  9: return IRONWORLD3;
      case 10: return CARBONWORLD1;
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
      case GASGIANT2: return "Gas giant";
      case SILICONWORLD1: return "Silicon world";
      case WATERWORLD1:
      case WATERWORLD2:
      case WATERWORLD3:
      case WATERWORLD4: return "Water world";
      case IRONWORLD1:
      case IRONWORLD2:
      case IRONWORLD3: return "Iron world";
      case ICEWORLD1: return "Ice world";
      case ICEWORLD2: return "Ice world";
      case CARBONWORLD1: return "Carbon world";
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
}
