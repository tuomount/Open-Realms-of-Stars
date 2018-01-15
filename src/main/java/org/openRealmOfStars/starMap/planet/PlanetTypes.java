package org.openRealmOfStars.starMap.planet;

import java.awt.image.BufferedImage;

import org.openRealmOfStars.gui.GuiStatics;
import org.openRealmOfStars.mapTiles.TileNames;
import org.openRealmOfStars.mapTiles.Tiles;
import org.openRealmOfStars.starMap.newsCorp.ImageInstruction;

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
      ImageInstruction.PLANET_ROCK1),
  /**
   * First water world images
   */
  WATERWORLD1(Tiles.getTileByName(TileNames.WATERWORLD1).getIndex(),
      GuiStatics.BIG_PLANET_WATERWORLD1, WorldType.WATERWORLD,
      ImageInstruction.PLANET_WATERWORLD1),
  /**
   * Second water world images
   */
  WATERWORLD2(Tiles.getTileByName(TileNames.WATERWORLD2).getIndex(),
      GuiStatics.BIG_PLANET_WATERWORLD2, WorldType.WATERWORLD,
      ImageInstruction.PLANET_WATERWORLD2),
  /**
   * First iron world images
   */
  IRONWORLD1(Tiles.getTileByName(TileNames.IRONPLANET1).getIndex(),
      GuiStatics.BIG_PLANET_IRONPLANET1, WorldType.IRONWORLD,
      ImageInstruction.PLANET_IRONWORLD1),
  /**
   * Second iron world images
   */
  IRONWORLD2(Tiles.getTileByName(TileNames.IRONPLANET2).getIndex(),
      GuiStatics.BIG_PLANET_IRONPLANET2, WorldType.IRONWORLD,
      ImageInstruction.PLANET_IRONWORLD2),
  /**
   * Third water world images
   */
  WATERWORLD3(Tiles.getTileByName(TileNames.WATERWORLD3).getIndex(),
      GuiStatics.BIG_PLANET_WATERWORLD3, WorldType.WATERWORLD,
      ImageInstruction.PLANET_WATERWORLD3),
  /**
   * Fourth water world images
   */
  WATERWORLD4(Tiles.getTileByName(TileNames.WATERWORLD4).getIndex(),
      GuiStatics.BIG_PLANET_WATERWORLD4, WorldType.WATERWORLD,
      ImageInstruction.PLANET_WATERWORLD4),
  /**
   * First ice world images
   */
  ICEWORLD1(Tiles.getTileByName(TileNames.ICEWORLD1).getIndex(),
      GuiStatics.BIG_PLANET_ICEWORLD1, WorldType.ICEWORLD,
      ImageInstruction.PLANET_ICEWORLD1),
  /**
   * Second ice world images
   */
  ICEWORLD2(Tiles.getTileByName(TileNames.ICEWORLD2).getIndex(),
      GuiStatics.BIG_PLANET_ICEWORLD2, WorldType.ICEWORLD,
      ImageInstruction.PLANET_ICEWORLD2),
  /**
   * Third iron world images
   */
  IRONWORLD3(Tiles.getTileByName(TileNames.IRONPLANET3).getIndex(),
      GuiStatics.BIG_PLANET_IRONPLANET3, WorldType.IRONWORLD,
      ImageInstruction.PLANET_IRONWORLD3),
  /**
   * First carbon world images
   */
  CARBONWORLD1(Tiles.getTileByName(TileNames.CARBONWORLD1).getIndex(),
      GuiStatics.BIG_PLANET_CARBONWORLD1, WorldType.CARBONWORLD,
      ImageInstruction.PLANET_CARBONWORLD1);


  /**
   * Get the number of planet types available
   * @return Number of planet types available
   */
  public static int numberOfPlanetTypes() {
    return PlanetTypes.values().length;
  }
  /**
   * Tile index
   */
  private int tile;

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
   */
  PlanetTypes(final int tileIndex, final BufferedImage bigImage,
      final WorldType type, final String imageInstruction) {
    tile = tileIndex;
    image = bigImage;
    worldType = type;
    instructions = imageInstruction;
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
}
