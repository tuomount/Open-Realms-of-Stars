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

import org.openRealmOfStars.gui.util.GraphRoutines;
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
  private BufferedImage[] largeShipImages;
  /**
   * larger than normal size ship images
   */
  private BufferedImage[] largerShipImages;
  /**
   * Normal size ship images
   */
  private BufferedImage[] normalShipImages;
  /**
   * smaller than normal size ship images
   */
  private BufferedImage[] smallerShipImages;
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
   * Index for devourer
   */
  public static final int DEVOURER = 24;
  /**
   * Must be one bigger than last ship
   */
  public static final int NUMBER_OF_IMAGES = 25;


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
      case 24: return "Devourer";
    }
  }

  /**
   * Get Ship type.
   * @param type Type as string
   * @return type as int.
   */
  public static int getShipType(final String type) {
    switch (type) {
      default:
      case "Scout": return 0;
      case "Colony": return 1;
      case "Destroyer": return 2;
      case "Probe": return 3;
      case "Small freighter": return 4;
      case "Small starbase": return 5;
      case "Corvette": return 6;
      case "Medium starbase": return 7;
      case "Medium freighter": return 8;
      case "Cruiser": return 9;
      case "Battleship": return 10;
      case "Privateer": return 11;
      case "Large privateer": return 12;
      case "Large freighter": return 13;
      case "Large starbase": return 14;
      case "Battle cruiser": return 15;
      case "Massive freighter": return 16;
      case "Massive starbase": return 17;
      case "Capital ship": return 18;
      case "Artificial planet": return 19;
      case "Space worm": return 20;
      case "Space kraken": return 21;
      case "Large space kraken": return 22;
      case "Spore": return 23;
      case "Devourer": return 24;
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
    largeShipImages = new BufferedImage[number];
    largerShipImages = new BufferedImage[number];
    normalShipImages = new BufferedImage[number];
    smallerShipImages = new BufferedImage[number];
    smallShipImages = new BufferedImage[number];
    // Scout
    largeShipImages[SCOUT] = image64x64(image, 0, 0);
    largerShipImages[SCOUT] = GraphRoutines.scaleTile64to48(
        largeShipImages[SCOUT], false);
    normalShipImages[SCOUT] = scaleTo32x32(largeShipImages[SCOUT]);
    smallerShipImages[SCOUT] = GraphRoutines.scaleTile32to24(
        normalShipImages[SCOUT], false);
    smallShipImages[SCOUT] = scaleTo16x16(largeShipImages[SCOUT]);
    // Colony
    largeShipImages[COLONY] = image64x64(image, 1, 0);
    largerShipImages[COLONY] = GraphRoutines.scaleTile64to48(
        largeShipImages[COLONY], false);
    normalShipImages[COLONY] = scaleTo32x32(largeShipImages[COLONY]);
    smallerShipImages[COLONY] = GraphRoutines.scaleTile32to24(
        normalShipImages[COLONY], false);
    smallShipImages[COLONY] = scaleTo16x16(largeShipImages[COLONY]);
    // Destroyer
    largeShipImages[DESTROYER] = image64x64(image, 2, 0);
    largerShipImages[DESTROYER] = GraphRoutines.scaleTile64to48(
        largeShipImages[DESTROYER], false);
    normalShipImages[DESTROYER] = scaleTo32x32(largeShipImages[DESTROYER]);
    smallerShipImages[DESTROYER] = GraphRoutines.scaleTile32to24(
        normalShipImages[DESTROYER], false);
    smallShipImages[DESTROYER] = scaleTo16x16(largeShipImages[DESTROYER]);
    // Probe
    largeShipImages[PROBE] = image64x64(image, 3, 0);
    largerShipImages[PROBE] = GraphRoutines.scaleTile64to48(
        largeShipImages[PROBE], false);
    normalShipImages[PROBE] = scaleTo32x32(largeShipImages[PROBE]);
    smallerShipImages[PROBE] = GraphRoutines.scaleTile32to24(
        normalShipImages[PROBE], false);
    smallShipImages[PROBE] = scaleTo16x16(largeShipImages[PROBE]);
    // Small freighter
    largeShipImages[SMALL_FREIGHTER] = image64x64(image, 4, 0);
    largerShipImages[SMALL_FREIGHTER] = GraphRoutines.scaleTile64to48(
        largeShipImages[SMALL_FREIGHTER], false);
    normalShipImages[SMALL_FREIGHTER] = scaleTo32x32(
        largeShipImages[SMALL_FREIGHTER]);
    smallerShipImages[SMALL_FREIGHTER] = GraphRoutines.scaleTile32to24(
        normalShipImages[SMALL_FREIGHTER], false);
    smallShipImages[SMALL_FREIGHTER] = scaleTo16x16(
        largeShipImages[SMALL_FREIGHTER]);
    // Small starbase
    largeShipImages[SMALL_STARBASE] = image64x64(image, 0, 1);
    largerShipImages[SMALL_STARBASE] = GraphRoutines.scaleTile64to48(
        largeShipImages[SMALL_STARBASE], false);
    normalShipImages[SMALL_STARBASE] = scaleTo32x32(
        largeShipImages[SMALL_STARBASE]);
    smallerShipImages[SMALL_STARBASE] = GraphRoutines.scaleTile32to24(
        normalShipImages[SMALL_STARBASE], false);
    smallShipImages[SMALL_STARBASE] = scaleTo16x16(
        largeShipImages[SMALL_STARBASE]);
    // Corvette
    largeShipImages[CORVETTE] = image64x64(image, 1, 1);
    largerShipImages[CORVETTE] = GraphRoutines.scaleTile64to48(
        largeShipImages[CORVETTE], false);
    normalShipImages[CORVETTE] = scaleTo32x32(largeShipImages[CORVETTE]);
    smallerShipImages[CORVETTE] = GraphRoutines.scaleTile32to24(
        normalShipImages[CORVETTE], false);
    smallShipImages[CORVETTE] = scaleTo16x16(largeShipImages[CORVETTE]);
    // Medium starbase
    largeShipImages[MEDIUM_STARBASE] = image64x64(image, 2, 1);
    largerShipImages[MEDIUM_STARBASE] = GraphRoutines.scaleTile64to48(
        largeShipImages[MEDIUM_STARBASE], false);
    normalShipImages[MEDIUM_STARBASE] = scaleTo32x32(
        largeShipImages[MEDIUM_STARBASE]);
    smallerShipImages[MEDIUM_STARBASE] = GraphRoutines.scaleTile32to24(
        normalShipImages[MEDIUM_STARBASE], false);
    smallShipImages[MEDIUM_STARBASE] = scaleTo16x16(
        largeShipImages[MEDIUM_STARBASE]);
    // Medium freighter
    largeShipImages[MEDIUM_FREIGHTER] = image64x64(image, 3, 1);
    largerShipImages[MEDIUM_FREIGHTER] = GraphRoutines.scaleTile64to48(
        largeShipImages[MEDIUM_FREIGHTER], false);
    normalShipImages[MEDIUM_FREIGHTER] = scaleTo32x32(
        largeShipImages[MEDIUM_FREIGHTER]);
    smallerShipImages[MEDIUM_FREIGHTER] = GraphRoutines.scaleTile32to24(
        normalShipImages[MEDIUM_FREIGHTER], false);
    smallShipImages[MEDIUM_FREIGHTER] = scaleTo16x16(
        largeShipImages[MEDIUM_FREIGHTER]);
    // Cruiser
    largeShipImages[CRUISER] = image64x64(image, 4, 1);
    largerShipImages[CRUISER] = GraphRoutines.scaleTile64to48(
        largeShipImages[CRUISER], false);
    normalShipImages[CRUISER] = scaleTo32x32(largeShipImages[CRUISER]);
    smallerShipImages[CRUISER] = GraphRoutines.scaleTile32to24(
        normalShipImages[CRUISER], false);
    smallShipImages[CRUISER] = scaleTo16x16(largeShipImages[CRUISER]);
    // Battleship
    largeShipImages[BATTLESHIP] = image64x64(image, 0, 2);
    largerShipImages[BATTLESHIP] = GraphRoutines.scaleTile64to48(
        largeShipImages[BATTLESHIP], false);
    normalShipImages[BATTLESHIP] = scaleTo32x32(largeShipImages[BATTLESHIP]);
    smallerShipImages[BATTLESHIP] = GraphRoutines.scaleTile32to24(
        normalShipImages[BATTLESHIP], false);
    smallShipImages[BATTLESHIP] = scaleTo16x16(largeShipImages[BATTLESHIP]);
    // Privateer
    largeShipImages[PRIVATEER] = SharedShipImages.getShipImage(
        SharedShipImages.PRIVATEER);
    largerShipImages[PRIVATEER] = SharedShipImages.getLargerShipImage(
        SharedShipImages.PRIVATEER);
    normalShipImages[PRIVATEER] = SharedShipImages.getNormalShipImage(
        SharedShipImages.PRIVATEER);
    smallerShipImages[PRIVATEER] = SharedShipImages.getSmallerShipImage(
        SharedShipImages.PRIVATEER);
    smallShipImages[PRIVATEER] = SharedShipImages.getSmallShipImage(
        SharedShipImages.PRIVATEER);
    // Privateer large
    largeShipImages[PRIVATEER_LARGE] = SharedShipImages.getShipImage(
        SharedShipImages.PRIVATEER_LARGE);
    largerShipImages[PRIVATEER_LARGE] = SharedShipImages.getLargerShipImage(
        SharedShipImages.PRIVATEER_LARGE);
    normalShipImages[PRIVATEER_LARGE] = SharedShipImages.getNormalShipImage(
        SharedShipImages.PRIVATEER_LARGE);
    smallerShipImages[PRIVATEER_LARGE] = SharedShipImages.getSmallerShipImage(
        SharedShipImages.PRIVATEER_LARGE);
    smallShipImages[PRIVATEER_LARGE] = SharedShipImages.getSmallShipImage(
        SharedShipImages.PRIVATEER_LARGE);
    // Large freighter
    largeShipImages[LARGE_FREIGHTER] = image64x64(image, 3, 2);
    largerShipImages[LARGE_FREIGHTER] = GraphRoutines.scaleTile64to48(
        largeShipImages[LARGE_FREIGHTER], false);
    normalShipImages[LARGE_FREIGHTER] = scaleTo32x32(
        largeShipImages[LARGE_FREIGHTER]);
    smallerShipImages[LARGE_FREIGHTER] = GraphRoutines.scaleTile32to24(
        normalShipImages[LARGE_FREIGHTER], false);
    smallShipImages[LARGE_FREIGHTER] = scaleTo16x16(
        largeShipImages[LARGE_FREIGHTER]);
    // Large starbase
    largeShipImages[LARGE_STARBASE] = image64x64(image, 4, 2);
    largerShipImages[LARGE_STARBASE] = GraphRoutines.scaleTile64to48(
        largeShipImages[LARGE_STARBASE], false);
    normalShipImages[LARGE_STARBASE] = scaleTo32x32(
        largeShipImages[LARGE_STARBASE]);
    smallerShipImages[LARGE_STARBASE] = GraphRoutines.scaleTile32to24(
        normalShipImages[LARGE_STARBASE], false);
    smallShipImages[LARGE_STARBASE] = scaleTo16x16(
        largeShipImages[LARGE_STARBASE]);
    // Battlecruiser
    largeShipImages[BATTLECRUISER] = image64x64(image, 0, 3);
    largerShipImages[BATTLECRUISER] = GraphRoutines.scaleTile64to48(
        largeShipImages[BATTLECRUISER], false);
    normalShipImages[BATTLECRUISER] = scaleTo32x32(
        largeShipImages[BATTLECRUISER]);
    smallerShipImages[BATTLECRUISER] = GraphRoutines.scaleTile32to24(
        normalShipImages[BATTLECRUISER], false);
    smallShipImages[BATTLECRUISER] = scaleTo16x16(
        largeShipImages[BATTLECRUISER]);
    // Massive freighter
    largeShipImages[MASSIVE_FREIGHTER] = image64x64(image, 1, 3);
    largerShipImages[MASSIVE_FREIGHTER] = GraphRoutines.scaleTile64to48(
        largeShipImages[MASSIVE_FREIGHTER], false);
    normalShipImages[MASSIVE_FREIGHTER] = scaleTo32x32(
        largeShipImages[MASSIVE_FREIGHTER]);
    smallerShipImages[MASSIVE_FREIGHTER] = GraphRoutines.scaleTile32to24(
        normalShipImages[MASSIVE_FREIGHTER], false);
    smallShipImages[MASSIVE_FREIGHTER] = scaleTo16x16(
        largeShipImages[MASSIVE_FREIGHTER]);
    // Massive starbase
    largeShipImages[MASSIVE_STARBASE] = image64x64(image, 2, 3);
    largerShipImages[MASSIVE_STARBASE] = GraphRoutines.scaleTile64to48(
        largeShipImages[MASSIVE_STARBASE], false);
    normalShipImages[MASSIVE_STARBASE] = scaleTo32x32(
        largeShipImages[MASSIVE_STARBASE]);
    smallerShipImages[MASSIVE_STARBASE] = GraphRoutines.scaleTile32to24(
        normalShipImages[MASSIVE_STARBASE], false);
    smallShipImages[MASSIVE_STARBASE] = scaleTo16x16(
        largeShipImages[MASSIVE_STARBASE]);
    // Capitalship
    largeShipImages[CAPITAL_SHIP] = image64x64(image, 3, 3);
    largerShipImages[CAPITAL_SHIP] = GraphRoutines.scaleTile64to48(
        largeShipImages[CAPITAL_SHIP], false);
    normalShipImages[CAPITAL_SHIP] = scaleTo32x32(
        largeShipImages[CAPITAL_SHIP]);
    smallerShipImages[CAPITAL_SHIP] = GraphRoutines.scaleTile32to24(
        normalShipImages[CAPITAL_SHIP], false);
    smallShipImages[CAPITAL_SHIP] = scaleTo16x16(largeShipImages[CAPITAL_SHIP]);
    // Artificial planet
    largeShipImages[ARTIFICIAL_PLANET] = SharedShipImages.getShipImage(
        SharedShipImages.ARTIFICIAL_PLANET);
    largerShipImages[ARTIFICIAL_PLANET] = GraphRoutines.scaleTile64to48(
        largeShipImages[ARTIFICIAL_PLANET], false);
    normalShipImages[ARTIFICIAL_PLANET] = SharedShipImages.getNormalShipImage(
        SharedShipImages.ARTIFICIAL_PLANET);
    smallShipImages[ARTIFICIAL_PLANET] = SharedShipImages.getSmallShipImage(
        SharedShipImages.ARTIFICIAL_PLANET);
    // Space worm
    largeShipImages[SPACE_WORM] = SharedShipImages.getMonsterShipImage(
        SharedShipImages.SPACE_WORM);
    largerShipImages[SPACE_WORM] = SharedShipImages.getLargerMonsterShipImage(
        SharedShipImages.SPACE_WORM);
    normalShipImages[SPACE_WORM] = SharedShipImages.getNormalMonsterShipImage(
        SharedShipImages.SPACE_WORM);
    smallShipImages[SPACE_WORM] = SharedShipImages.getSmallMonsterShipImage(
        SharedShipImages.SPACE_WORM);
    smallerShipImages[SPACE_WORM] = SharedShipImages.getSmallerMonsterShipImage(
        SharedShipImages.SPACE_WORM);
    // Space kraken
    largeShipImages[SPACE_KRAKEN] = SharedShipImages.getMonsterShipImage(
        SharedShipImages.SPACE_KRAKEN);
    largerShipImages[SPACE_KRAKEN] = SharedShipImages.getLargerMonsterShipImage(
        SharedShipImages.SPACE_KRAKEN);
    normalShipImages[SPACE_KRAKEN] = SharedShipImages.getNormalMonsterShipImage(
        SharedShipImages.SPACE_KRAKEN);
    smallerShipImages[SPACE_KRAKEN] =
        SharedShipImages.getSmallerMonsterShipImage(
            SharedShipImages.SPACE_KRAKEN);
    smallShipImages[SPACE_KRAKEN] = SharedShipImages.getSmallMonsterShipImage(
        SharedShipImages.SPACE_KRAKEN);
    // Large space kraken
    largeShipImages[LARGE_SPACE_KRAKEN] = SharedShipImages.getMonsterShipImage(
        SharedShipImages.LARGE_SPACE_KRAKEN);
    largerShipImages[LARGE_SPACE_KRAKEN] =
        SharedShipImages.getLargerMonsterShipImage(
            SharedShipImages.LARGE_SPACE_KRAKEN);
    normalShipImages[LARGE_SPACE_KRAKEN] =
        SharedShipImages.getNormalMonsterShipImage(
            SharedShipImages.LARGE_SPACE_KRAKEN);
    smallerShipImages[LARGE_SPACE_KRAKEN] =
        SharedShipImages.getSmallerMonsterShipImage(
            SharedShipImages.LARGE_SPACE_KRAKEN);
    smallShipImages[LARGE_SPACE_KRAKEN] =
        SharedShipImages.getSmallMonsterShipImage(
            SharedShipImages.LARGE_SPACE_KRAKEN);
    // Spore
    largeShipImages[SPORE] = SharedShipImages.getShipImage(
        SharedShipImages.SPORE);
    largerShipImages[SPORE] = SharedShipImages.getLargerShipImage(
        SharedShipImages.SPORE);
    normalShipImages[SPORE] = SharedShipImages.getNormalShipImage(
        SharedShipImages.SPORE);
    smallShipImages[SPORE] = SharedShipImages.getSmallShipImage(
        SharedShipImages.SPORE);
    smallerShipImages[SPORE] = SharedShipImages.getSmallerShipImage(
        SharedShipImages.SPORE);
    // Devourer
    largeShipImages[DEVOURER] = SharedShipImages.getMonsterShipImage(
        SharedShipImages.DEVOURER);
    largerShipImages[DEVOURER] = SharedShipImages.getLargerMonsterShipImage(
        SharedShipImages.DEVOURER);
    normalShipImages[DEVOURER] = SharedShipImages.getNormalMonsterShipImage(
        SharedShipImages.DEVOURER);
    smallerShipImages[DEVOURER] = SharedShipImages.getSmallerMonsterShipImage(
        SharedShipImages.DEVOURER);
    smallShipImages[DEVOURER] = SharedShipImages.getSmallMonsterShipImage(
        SharedShipImages.DEVOURER);
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
    if (zoomLevel == Tile.ZOOM_IN2) {
      return getShipImage(index);
    }
    if (zoomLevel == Tile.ZOOM_NORMAL) {
      return getNormalShipImage(index);
    }
    if (zoomLevel == Tile.ZOOM_OUT2) {
      return getSmallShipImage(index);
    }
    return largeShipImages[0];
  }

  /**
   * Get ship image by index. If index is out of bounds scout image is returned.
   * @param index The ship image index
   * @return BufferedImage
   */
  public BufferedImage getShipImage(final int index) {
    if (index >= 0 && index < largeShipImages.length) {
      return largeShipImages[index];
    }
    return largeShipImages[0];
  }

  /**
   * Get larger ship image by index.
   * If index is out of bounds scout image is returned.
   * @param index The ship image index
   * @return BufferedImage
   */
  public BufferedImage getLargerShipImage(final int index) {
    if (index >= 0 && index < largerShipImages.length) {
      return largerShipImages[index];
    }
    return largerShipImages[0];
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
   * Get smaller ship image by index. If index is out of bounds scout image is
   * returned.
   * @param index The ship image index
   * @return BufferedImage
   */
  public BufferedImage getSmallerShipImage(final int index) {
    if (index >= 0 && index < smallerShipImages.length) {
      return smallerShipImages[index];
    }
    return smallerShipImages[0];
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
