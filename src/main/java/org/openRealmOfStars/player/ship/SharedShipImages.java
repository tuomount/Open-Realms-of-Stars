package org.openRealmOfStars.player.ship;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2025 Tuomo Untinen
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

import org.openRealmOfStars.utilities.FileIo.IOUtilities;

/**
 * Shared ship images for 64x64 images read from single file
 */
public final class SharedShipImages {

  /**
   * MAX size ship images
   */
  private static BufferedImage[] shipImages;
  /**
   * Normal size ship images
   */
  private static BufferedImage[] normalShipImages;
  /**
   * Min size ship images
   */
  private static BufferedImage[] smallShipImages;

  /**
   * MAX size ship images
   */
  private static BufferedImage[] monsterShipImages;
  /**
   * Normal size ship images
   */
  private static BufferedImage[] normalMonsterShipImages;
  /**
   * Min size ship images
   */
  private static BufferedImage[] smallMonsterShipImages;

  /**
   * Index for privateer
   */
  public static final int PRIVATEER = 0;
  /**
   * Index for large privateer
   */
  public static final int PRIVATEER_LARGE = 1;
  /**
   * Index for artificial planet
   */
  public static final int ARTIFICIAL_PLANET = 2;
  /**
   * Index for spore ship.
   */
  public static final int SPORE = 3;
  /**
   * Index for space worm
   */
  public static final int SPACE_WORM = 0;
  /**
   * Index for space kraken
   */
  public static final int SPACE_KRAKEN = 1;
  /**
   * Index for large space kraken
   */
  public static final int LARGE_SPACE_KRAKEN = 2;
  /**
   * Index for large space devourer
   */
  public static final int DEVOURER = 3;
  /**
   * Must be one bigger than last ship
   */
  public static final int NUMBER_OF_IMAGES = 4;

  /**
   * Number of monster images.
   */
  private static final int NUMBER_OF_MONSTER_IMAGES = 4;

  /**
   * Hidden constructor.
   */
  private SharedShipImages() {
    // Nothing to do.
  }

  /**
   * Initialize ship images.
   */
  private static void loadImages() {
    BufferedImage image = null;
    image = IOUtilities.loadImage("/resources/images/shared_ship_models.png");
    int number = NUMBER_OF_IMAGES;
    shipImages = new BufferedImage[number];
    normalShipImages = new BufferedImage[number];
    smallShipImages = new BufferedImage[number];
    shipImages[PRIVATEER] = ShipImage.image64x64(image, 0, 0);
    normalShipImages[PRIVATEER] = ShipImage.scaleTo32x32(shipImages[PRIVATEER]);
    smallShipImages[PRIVATEER] = ShipImage.scaleTo16x16(shipImages[PRIVATEER]);
    shipImages[PRIVATEER_LARGE] = ShipImage.image64x64(image, 1, 0);
    normalShipImages[PRIVATEER_LARGE] = ShipImage.scaleTo32x32(
        shipImages[PRIVATEER_LARGE]);
    smallShipImages[PRIVATEER_LARGE] = ShipImage.scaleTo16x16(
        shipImages[PRIVATEER_LARGE]);
    shipImages[ARTIFICIAL_PLANET] = ShipImage.image64x64(image, 2, 0);
    normalShipImages[ARTIFICIAL_PLANET] = ShipImage.scaleTo32x32(
        shipImages[ARTIFICIAL_PLANET]);
    smallShipImages[ARTIFICIAL_PLANET] = ShipImage.scaleTo16x16(
        shipImages[ARTIFICIAL_PLANET]);
    shipImages[SPORE] = ShipImage.image64x64(image, 3, 0);
    normalShipImages[SPORE] = ShipImage.scaleTo32x32(shipImages[SPORE]);
    smallShipImages[SPORE] = ShipImage.scaleTo16x16(shipImages[SPORE]);

    image = IOUtilities.loadImage("/resources/images/space_monsters_ships.png");
    number = NUMBER_OF_MONSTER_IMAGES;
    monsterShipImages = new BufferedImage[number];
    normalMonsterShipImages = new BufferedImage[number];
    smallMonsterShipImages = new BufferedImage[number];

    monsterShipImages[SPACE_WORM] = ShipImage.image64x64(image, 0, 0);
    normalMonsterShipImages[SPACE_WORM] = ShipImage.scaleTo32x32(
        monsterShipImages[SPACE_WORM]);
    smallMonsterShipImages[SPACE_WORM] = ShipImage.scaleTo16x16(
        monsterShipImages[SPACE_WORM]);
    monsterShipImages[SPACE_KRAKEN] = ShipImage.image64x64(image, 1, 0);
    normalMonsterShipImages[SPACE_KRAKEN] = ShipImage.scaleTo32x32(
        monsterShipImages[SPACE_KRAKEN]);
    smallMonsterShipImages[SPACE_KRAKEN] = ShipImage.scaleTo16x16(
        monsterShipImages[SPACE_KRAKEN]);
    monsterShipImages[LARGE_SPACE_KRAKEN] = ShipImage.image64x64(image, 2, 0);
    normalMonsterShipImages[LARGE_SPACE_KRAKEN] = ShipImage.scaleTo32x32(
        monsterShipImages[LARGE_SPACE_KRAKEN]);
    smallMonsterShipImages[LARGE_SPACE_KRAKEN] = ShipImage.scaleTo16x16(
        monsterShipImages[LARGE_SPACE_KRAKEN]);
    monsterShipImages[DEVOURER] = ShipImage.image64x64(image, 3, 0);
    normalMonsterShipImages[DEVOURER] = ShipImage.scaleTo32x32(
        monsterShipImages[DEVOURER]);
    smallMonsterShipImages[DEVOURER] = ShipImage.scaleTo16x16(
        monsterShipImages[DEVOURER]);
  }

  /**
   * Get ship image(64x64) by index. If index is out of bounds scout privateer
   * is returned.
   * @param index The ship image index
   * @return BufferedImage
   */
  public static BufferedImage getShipImage(final int index) {
    if (shipImages == null) {
      loadImages();
    }
    if (index >= 0 && index < shipImages.length) {
      return shipImages[index];
    }
    return shipImages[0];
  }

  /**
   * Get normal ship image(32x32) by index. If index is out of bounds privateer
   * image is returned.
   * @param index The ship image index
   * @return BufferedImage
   */
  public static BufferedImage getNormalShipImage(final int index) {
    if (normalShipImages == null) {
      loadImages();
    }
    if (index >= 0 && index < normalShipImages.length) {
      return normalShipImages[index];
    }
    return normalShipImages[0];
  }

  /**
   * Get small ship image(16x16) by index. If index is out of bounds privateer
   * image is returned.
   * @param index The ship image index
   * @return BufferedImage
   */
  public static BufferedImage getSmallShipImage(final int index) {
    if (smallShipImages == null) {
      loadImages();
    }
    if (index >= 0 && index < smallShipImages.length) {
      return smallShipImages[index];
    }
    return smallShipImages[0];
  }

  /**
   * Get ship image by index. If index is out of bounds space worm
   * image is returned.
   * @param index The ship image index
   * @return BufferedImage
   */
  public static BufferedImage getMonsterShipImage(final int index) {
    if (monsterShipImages == null) {
      loadImages();
    }
    if (index >= 0 && index < monsterShipImages.length) {
      return monsterShipImages[index];
    }
    return monsterShipImages[0];
  }

  /**
   * Get normal ship image by index. If index is out of bounds space worm
   * image is returned.
   * @param index The ship image index
   * @return BufferedImage
   */
  public static BufferedImage getNormalMonsterShipImage(final int index) {
    if (normalMonsterShipImages == null) {
      loadImages();
    }
    if (index >= 0 && index < normalMonsterShipImages.length) {
      return normalMonsterShipImages[index];
    }
    return normalMonsterShipImages[0];
  }

  /**
   * Get small ship image by index. If index is out of bounds space worm
   * image is returned.
   * @param index The ship image index
   * @return BufferedImage
   */
  public static BufferedImage getSmallMonsterShipImage(final int index) {
    if (smallMonsterShipImages == null) {
      loadImages();
    }
    if (index >= 0 && index < smallMonsterShipImages.length) {
      return smallMonsterShipImages[index];
    }
    return smallMonsterShipImages[0];
  }


}
