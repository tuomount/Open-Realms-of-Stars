package org.openRealmOfStars.player.ship;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016-2025 Tuomo Untinen
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
import java.awt.image.RasterFormatException;

import org.openRealmOfStars.gui.util.GuiStatics;
import org.openRealmOfStars.mapTiles.Tile;
import org.openRealmOfStars.utilities.FileIo.Folders;
import org.openRealmOfStars.utilities.FileIo.IOUtilities;

/**
 * Ship image for 64x64 images read from single file
 */
public class ShipImage {

  /**
   * Ship image width
   */
  public static final int MAX_WIDTH = 64;
  /**
   * Ship image height
   */
  public static final int MAX_HEIGHT = 64;

  /**
   * MAX size ship images
   */
  private BufferedImage[] shipImages;
  /**
   * Normal size ship images
   */
  private BufferedImage[] normalShipImages;
  /**
   * Min size ship images
   */
  private BufferedImage[] smallShipImages;

  /** Ship image id. */
  private String id;
  /** Flag for custom image path */
  private boolean customImage = false;
  /**
   * Index for Scout
   */
  public static final int SCOUT = 0;
  /**
   * Index for Colony
   */
  public static final int COLONY = 1;
  /**
   * Index for Destroyer
   */
  public static final int DESTROYER = 2;
  /**
   * Index for probe
   */
  public static final int PROBE = 3;
  /**
   * Index for small freighter
   */
  public static final int SMALL_FREIGHTER = 4;
  /**
   * Index for small starbase
   */
  public static final int SMALL_STARBASE = 5;
  /**
   * Index for corvette
   */
  public static final int CORVETTE = 6;
  /**
   * Index for medium starbase
   */
  public static final int MEDIUM_STARBASE = 7;
  /**
   * Index for medium freighter
   */
  public static final int MEDIUM_FREIGHTER = 8;
  /**
   * Index for cruiser
   */
  public static final int CRUISER = 9;
  /**
   * Index for battleship
   */
  public static final int BATTLESHIP = 10;
  /**
   * Index for privateer
   */
  public static final int PRIVATEER = 11;
  /**
   * Index for large privateer
   */
  public static final int PRIVATEER_LARGE = 12;
  /**
   * Index for large freighter
   */
  public static final int LARGE_FREIGHTER = 13;
  /**
   * Index for large starbase
   */
  public static final int LARGE_STARBASE = 14;
  /**
   * Index for battle cruiser
   */
  public static final int BATTLECRUISER = 15;
  /**
   * Index for massive freighter
   */
  public static final int MASSIVE_FREIGHTER = 16;
  /**
   * Index for massive starbase
   */
  public static final int MASSIVE_STARBASE = 17;
  /**
   * Index for capital ship
   */
  public static final int CAPITAL_SHIP = 18;
  /**
   * Index for artificial planet
   */
  public static final int ARTIFICIAL_PLANET = 19;

  /**
   * Index for space worm
   */
  public static final int SPACE_WORM = 20;
  /**
   * Index for space kraken
   */
  public static final int SPACE_KRAKEN = 21;
  /**
   * Index for large space kraken
   */
  public static final int LARGE_SPACE_KRAKEN = 22;
  /**
   * Index for spore
   */
  public static final int SPORE = 23;
  /**
   * Must be one bigger than last ship
   */
  public static final int NUMBER_OF_IMAGES = 24;


  /**
   * Initialize ship images
   * @param fileToRead Needs to be inside JAR file
   * @param id ShipImage Id.
   * @param custom Flag for custom
   */
  public ShipImage(final String fileToRead,
      final String id, final boolean custom) {
    this.id = id;
    this.customImage = custom;
    loadImages(fileToRead);
  }

  /**
   * Get name for ship type.
   * @param index Image type
   * @return Ship type name
   */
  public static String getShipType(final int index) {
    switch (index) {
      default:
      case 0: return "Scout";
      case 1: return "Colony";
      case 2: return "Destroyer";
      case 3: return "Probe";
      case 4: return "Small freighter";
      case 5: return "Small starbase";
      case 6: return "Corvette";
      case 7: return "Medium starbase";
      case 8: return "Medium freighter";
      case 9: return "Cruiser";
      case 10: return "Battleship";
      case 11: return "Privateer";
      case 12: return "Large privateer";
      case 13: return "Large freighter";
      case 14: return "Large starbase";
      case 15: return "Battle cruiser";
      case 16: return "Massive freighter";
      case 17: return "Massive starbase";
      case 18: return "Capital ship";
      case 19: return "Artificial planet";
      case 20: return "Space worm";
      case 21: return "Space kraken";
      case 22: return "Large space kraken";
      case 23: return "Spore";
    }
  }
  /**
   * Initialize ship images.
   * @param fileToRead Needs to be inside JAR file
   */
  private void loadImages(final String fileToRead) {
    BufferedImage image = null;
    if (isCustomImage()) {
      image = IOUtilities.loadImage(Folders.getCustomSpaceShipImage()
          + "/" + fileToRead);
    } else {
      image = IOUtilities.loadImage("/resources/images/" + fileToRead);
    }
    int number = NUMBER_OF_IMAGES;
    shipImages = new BufferedImage[number];
    normalShipImages = new BufferedImage[number];
    smallShipImages = new BufferedImage[number];
    shipImages[SCOUT] = image64x64(image, 0, 0);
    normalShipImages[SCOUT] = scaleTo32x32(shipImages[SCOUT]);
    smallShipImages[SCOUT] = scaleTo16x16(shipImages[SCOUT]);
    shipImages[COLONY] = image64x64(image, 1, 0);
    normalShipImages[COLONY] = scaleTo32x32(shipImages[COLONY]);
    smallShipImages[COLONY] = scaleTo16x16(shipImages[COLONY]);
    shipImages[DESTROYER] = image64x64(image, 2, 0);
    normalShipImages[DESTROYER] = scaleTo32x32(shipImages[DESTROYER]);
    smallShipImages[DESTROYER] = scaleTo16x16(shipImages[DESTROYER]);
    shipImages[PROBE] = image64x64(image, 3, 0);
    normalShipImages[PROBE] = scaleTo32x32(shipImages[PROBE]);
    smallShipImages[PROBE] = scaleTo16x16(shipImages[PROBE]);
    shipImages[SMALL_FREIGHTER] = image64x64(image, 4, 0);
    normalShipImages[SMALL_FREIGHTER] = scaleTo32x32(
        shipImages[SMALL_FREIGHTER]);
    smallShipImages[SMALL_FREIGHTER] = scaleTo16x16(
        shipImages[SMALL_FREIGHTER]);
    shipImages[SMALL_STARBASE] = image64x64(image, 0, 1);
    normalShipImages[SMALL_STARBASE] = scaleTo32x32(shipImages[SMALL_STARBASE]);
    smallShipImages[SMALL_STARBASE] = scaleTo16x16(shipImages[SMALL_STARBASE]);
    shipImages[CORVETTE] = image64x64(image, 1, 1);
    normalShipImages[CORVETTE] = scaleTo32x32(shipImages[CORVETTE]);
    smallShipImages[CORVETTE] = scaleTo16x16(shipImages[CORVETTE]);
    shipImages[MEDIUM_STARBASE] = image64x64(image, 2, 1);
    normalShipImages[MEDIUM_STARBASE] = scaleTo32x32(
        shipImages[MEDIUM_STARBASE]);
    smallShipImages[MEDIUM_STARBASE] = scaleTo16x16(
        shipImages[MEDIUM_STARBASE]);
    shipImages[MEDIUM_FREIGHTER] = image64x64(image, 3, 1);
    normalShipImages[MEDIUM_FREIGHTER] = scaleTo32x32(
        shipImages[MEDIUM_FREIGHTER]);
    smallShipImages[MEDIUM_FREIGHTER] = scaleTo16x16(
        shipImages[MEDIUM_FREIGHTER]);
    shipImages[CRUISER] = image64x64(image, 4, 1);
    normalShipImages[CRUISER] = scaleTo32x32(shipImages[CRUISER]);
    smallShipImages[CRUISER] = scaleTo16x16(shipImages[CRUISER]);
    shipImages[BATTLESHIP] = image64x64(image, 0, 2);
    normalShipImages[BATTLESHIP] = scaleTo32x32(shipImages[BATTLESHIP]);
    smallShipImages[BATTLESHIP] = scaleTo16x16(shipImages[BATTLESHIP]);
    shipImages[PRIVATEER] = SharedShipImages.getShipImage(
        SharedShipImages.PRIVATEER);
    normalShipImages[PRIVATEER] = SharedShipImages.getNormalShipImage(
        SharedShipImages.PRIVATEER);
    smallShipImages[PRIVATEER] = SharedShipImages.getSmallShipImage(
        SharedShipImages.PRIVATEER);
    shipImages[PRIVATEER_LARGE] = SharedShipImages.getShipImage(
        SharedShipImages.PRIVATEER_LARGE);
    normalShipImages[PRIVATEER_LARGE] = SharedShipImages.getNormalShipImage(
        SharedShipImages.PRIVATEER_LARGE);
    smallShipImages[PRIVATEER_LARGE] = SharedShipImages.getSmallShipImage(
        SharedShipImages.PRIVATEER_LARGE);
    shipImages[LARGE_FREIGHTER] = image64x64(image, 3, 2);
    normalShipImages[LARGE_FREIGHTER] = scaleTo32x32(
        shipImages[LARGE_FREIGHTER]);
    smallShipImages[LARGE_FREIGHTER] = scaleTo16x16(
        shipImages[LARGE_FREIGHTER]);
    shipImages[LARGE_STARBASE] = image64x64(image, 4, 2);
    normalShipImages[LARGE_STARBASE] = scaleTo32x32(shipImages[LARGE_STARBASE]);
    smallShipImages[LARGE_STARBASE] = scaleTo16x16(shipImages[LARGE_STARBASE]);
    shipImages[BATTLECRUISER] = image64x64(image, 0, 3);
    normalShipImages[BATTLECRUISER] = scaleTo32x32(shipImages[BATTLECRUISER]);
    smallShipImages[BATTLECRUISER] = scaleTo16x16(shipImages[BATTLECRUISER]);
    shipImages[MASSIVE_FREIGHTER] = image64x64(image, 1, 3);
    normalShipImages[MASSIVE_FREIGHTER] = scaleTo32x32(
        shipImages[MASSIVE_FREIGHTER]);
    smallShipImages[MASSIVE_FREIGHTER] = scaleTo16x16(
        shipImages[MASSIVE_FREIGHTER]);
    shipImages[MASSIVE_STARBASE] = image64x64(image, 2, 3);
    normalShipImages[MASSIVE_STARBASE] = scaleTo32x32(
        shipImages[MASSIVE_STARBASE]);
    smallShipImages[MASSIVE_STARBASE] = scaleTo16x16(
        shipImages[MASSIVE_STARBASE]);
    shipImages[CAPITAL_SHIP] = image64x64(image, 3, 3);
    normalShipImages[CAPITAL_SHIP] = scaleTo32x32(shipImages[CAPITAL_SHIP]);
    smallShipImages[CAPITAL_SHIP] = scaleTo16x16(shipImages[CAPITAL_SHIP]);
    shipImages[ARTIFICIAL_PLANET] = SharedShipImages.getShipImage(
        SharedShipImages.ARTIFICIAL_PLANET);
    normalShipImages[ARTIFICIAL_PLANET] = SharedShipImages.getNormalShipImage(
        SharedShipImages.ARTIFICIAL_PLANET);
    smallShipImages[ARTIFICIAL_PLANET] = SharedShipImages.getSmallShipImage(
        SharedShipImages.ARTIFICIAL_PLANET);
    shipImages[SPACE_WORM] = SharedShipImages.getMonsterShipImage(
        SharedShipImages.SPACE_WORM);
    normalShipImages[SPACE_WORM] = SharedShipImages.getNormalMonsterShipImage(
        SharedShipImages.SPACE_WORM);
    smallShipImages[SPACE_WORM] = SharedShipImages.getSmallMonsterShipImage(
        SharedShipImages.SPACE_WORM);
    shipImages[SPACE_KRAKEN] = SharedShipImages.getMonsterShipImage(
        SharedShipImages.SPACE_KRAKEN);
    normalShipImages[SPACE_KRAKEN] = SharedShipImages.getNormalMonsterShipImage(
        SharedShipImages.SPACE_KRAKEN);
    smallShipImages[SPACE_KRAKEN] = SharedShipImages.getSmallMonsterShipImage(
        SharedShipImages.SPACE_KRAKEN);
    shipImages[LARGE_SPACE_KRAKEN] = SharedShipImages.getMonsterShipImage(
        SharedShipImages.LARGE_SPACE_KRAKEN);
    normalShipImages[LARGE_SPACE_KRAKEN] =
        SharedShipImages.getNormalMonsterShipImage(
            SharedShipImages.LARGE_SPACE_KRAKEN);
    smallShipImages[LARGE_SPACE_KRAKEN] =
        SharedShipImages.getSmallMonsterShipImage(
            SharedShipImages.LARGE_SPACE_KRAKEN);
    shipImages[SPORE] = SharedShipImages.getShipImage(SharedShipImages.SPORE);
    normalShipImages[SPORE] = SharedShipImages.getNormalShipImage(
        SharedShipImages.SPORE);
    smallShipImages[SPORE] = SharedShipImages.getSmallShipImage(
        SharedShipImages.SPORE);
  }
  /**
   * Capture 64x64 image from bigger one
   * @param image Image where to capture
   * @param x Offset for X coordinate
   * @param y Offset for X coordinate
   * @return 64x64 pixels buffered image
   * @throws RasterFormatException If trying to capture outside of image.
   */
  public static BufferedImage image64x64(final BufferedImage image,
      final int x, final int y) throws RasterFormatException {

    if (x >= 0 && y >= 0 && x * MAX_WIDTH < image.getHeight()
        && y * MAX_HEIGHT < image.getHeight()) {
      return image.getSubimage(x * MAX_WIDTH, y * MAX_HEIGHT, MAX_WIDTH,
          MAX_HEIGHT);
    }
    throw new RasterFormatException("Icon is outside of image.");
  }

  /**
   * Get ship image by index. If index is out of bounds scout image is returned.
   * This will return zoomed level
   * @param index The ship image index
   * @param zoomLevel Zoomlevel for fetching image
   * @return BufferedImage
   */
  public BufferedImage getShipZoomedImage(final int index,
      final int zoomLevel) {
    if (zoomLevel == Tile.ZOOM_IN) {
      return getShipImage(index);
    }
    if (zoomLevel == Tile.ZOOM_NORMAL) {
      return getNormalShipImage(index);
    }
    if (zoomLevel == Tile.ZOOM_OUT1) {
      return getSmallShipImage(index);
    }
    return shipImages[0];
  }

  /**
   * Get ship image by index. If index is out of bounds scout image is returned.
   * @param index The ship image index
   * @return BufferedImage
   */
  public BufferedImage getShipImage(final int index) {
    if (index >= 0 && index < shipImages.length) {
      return shipImages[index];
    }
    return shipImages[0];
  }

  /**
   * Get normal ship image by index. If index is out of bounds scout image is
   * returned.
   * @param index The ship image index
   * @return BufferedImage
   */
  public BufferedImage getNormalShipImage(final int index) {
    if (index >= 0 && index < normalShipImages.length) {
      return normalShipImages[index];
    }
    return normalShipImages[0];
  }

  /**
   * Get small ship image by index. If index is out of bounds scout image is
   * returned.
   * @param index The ship image index
   * @return BufferedImage
   */
  public BufferedImage getSmallShipImage(final int index) {
    if (index >= 0 && index < smallShipImages.length) {
      return smallShipImages[index];
    }
    return smallShipImages[0];
  }

  /**
   * Get ShipImage id.
   * @return ShipImage id
   */
  public String getId() {
    return id;
  }
  /**
   * Is Custom image which should be loaded outside of JAR.
   * @return the customImage
   */
  public boolean isCustomImage() {
    return customImage;
  }

  /**
   * Scale 64x64 image to 32x32 image
   * @param source Buffered image to scale
   * @return Scaled buffered image
   */
  public static BufferedImage scaleTo32x32(final BufferedImage source) {
    return GuiStatics.scaleToHalf(source);
  }

  /**
   * Scale 64x64 image to 16x16 image
   * @param source Buffered image to scale
   * @return Scaled buffered image
   */
  public static BufferedImage scaleTo16x16(final BufferedImage source) {
    return GuiStatics.scaleToHalf(GuiStatics.scaleToHalf(source));
  }

}
