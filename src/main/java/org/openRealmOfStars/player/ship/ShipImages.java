package org.openRealmOfStars.player.ship;

import org.openRealmOfStars.player.SpaceRace.SpaceRace;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016-2018,2021-2022  Tuomo Untinen
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
 * Static ship images for different races
 *
 */

public final class ShipImages {

  /**
   * Human ships
   */
  private static ShipImage humans;

  /**
   * Mechion ships
   */
  private static ShipImage mechions;

  /**
   * Sporks ships
   */
  private static ShipImage sporks;

  /**
   * Greyans ships
   */
  private static ShipImage greyans;

  /**
   * Centaurs ships
   */
  private static ShipImage centaurs;

  /**
   * Mothoids ships
   */
  private static ShipImage mothoids;

  /**
   * Teuthidaes ships
   */
  private static ShipImage teuthidaes;

  /**
   * Scaurian ships
   */
  private static ShipImage scaurians;

  /**
   * Homarian ships
   */
  private static ShipImage homarians;

  /**
   * Space pirate ships
   */
  private static ShipImage spacePirate;

  /**
   * Chiraloid ships
   */
  private static ShipImage chiraloids;

  /**
   * Reborgian ships
   */
  private static ShipImage reborgians;

  /**
   * Lithorian ships
   */
  private static ShipImage lithorians;

  /**
   * Alteirian ships
   */
  private static ShipImage alteirians;

  /**
   * Smaugirian ships
   */
  private static ShipImage smaugirians;

  /**
   * Synthdroids ships
   */
  private static ShipImage synthdroids;

  /**
   * Hiding the constructor
   */
  private ShipImages() {
    // Nothing to do
  }

  /**
   * Init all images
   */
  private static void initImages() {
    humans = new ShipImage("humanships.png");
    mechions = new ShipImage("mechionships.png");
    centaurs = new ShipImage("centaurships.png");
    sporks = new ShipImage("sporkships.png");
    greyans = new ShipImage("greyanships.png");
    mothoids = new ShipImage("mothoidships.png");
    teuthidaes = new ShipImage("teuthidaeships.png");
    scaurians = new ShipImage("scaurianships.png");
    homarians = new ShipImage("homarianships.png");
    spacePirate = new ShipImage("privateerships.png", true);
    chiraloids = new ShipImage("chiraloidships.png");
    reborgians = new ShipImage("reborgianships.png");
    lithorians = new ShipImage("lithorianships.png");
    alteirians = new ShipImage("alteirianships.png");
    smaugirians = new ShipImage("smaugirianships.png");
    synthdroids = new ShipImage("synthdroidships.png");
  }

  /**
   * Get Human ship images
   * @return Human ship images
   */
  public static ShipImage humans() {
    if (humans == null) {
      initImages();
    }
    return humans;
  }

  /**
   * Get Mechions ship images
   * @return Mechions ship images
   */
  public static ShipImage mechions() {
    if (mechions == null) {
      initImages();
    }
    return mechions;
  }

  /**
   * Get Sporks ship images
   * @return Sporks ship images
   */
  public static ShipImage sporks() {
    if (sporks == null) {
      initImages();
    }
    return sporks;
  }

  /**
   * Get Greyans ship images
   * @return Greyans ship images
   */
  public static ShipImage greyans() {
    if (greyans == null) {
      initImages();
    }
    return greyans;
  }

  /**
   * Get Centaurs ship images
   * @return Centaurs ship images
   */
  public static ShipImage centaurs() {
    if (centaurs == null) {
      initImages();
    }
    return centaurs;
  }

  /**
   * Get Mothoids ship images
   * @return Mothoids ship images
   */
  public static ShipImage mothoids() {
    if (mothoids == null) {
      initImages();
    }
    return mothoids;
  }

  /**
   * Get Teuthidaes ship images
   * @return Teuthidaes ship images
   */
  public static ShipImage teuthidaes() {
    if (teuthidaes == null) {
      initImages();
    }
    return teuthidaes;
  }

  /**
   * Get Scaurians ship images
   * @return Scaurians ship images
   */
  public static ShipImage scaurians() {
    if (scaurians == null) {
      initImages();
    }
    return scaurians;
  }

  /**
   * Get homarians ship images
   * @return homarians ship images
   */
  public static ShipImage homarians() {
    if (homarians == null) {
      initImages();
    }
    return homarians;
  }

  /**
   * Get Space pirate ship images
   * @return Space pirate ship images
   */
  public static ShipImage spacePirates() {
    if (spacePirate == null) {
      initImages();
    }
    return spacePirate;
  }

  /**
   * Get Chiraloid ship images
   * @return chiraloid ship images
   */
  public static ShipImage chiraloids() {
    if (chiraloids == null) {
      initImages();
    }
    return chiraloids;
  }

  /**
   * Get Reborgian ship images
   * @return Reborgian ship images
   */
  public static ShipImage reborgians() {
    if (reborgians == null) {
      initImages();
    }
    return reborgians;
  }

  /**
   * Get Lithorian ship images
   * @return Lithorian ship images
   */
  public static ShipImage lithorians() {
    if (lithorians == null) {
      initImages();
    }
    return lithorians;
  }

  /**
   * Get Alteirian ship images
   * @return Alteirian ship images
   */
  public static ShipImage alteirians() {
    if (alteirians == null) {
      initImages();
    }
    return alteirians;
  }

  /**
   * Get Smaugirian ship images
   * @return Smaugirian ship images
   */
  public static ShipImage smaugirians() {
    if (smaugirians == null) {
      initImages();
    }
    return smaugirians;
  }

  /**
   * Get Smaugirian ship images
   * @return Smaugirian ship images
   */
  public static ShipImage synthdroids() {
    if (synthdroids == null) {
      initImages();
    }
    return synthdroids;
  }

  /**
   * Get ship images for certain race
   * @param race Space race which images to get
   * @return ShipImage
   */
  public static ShipImage getByRace(final SpaceRace race) {
    switch (race) {
    case HUMAN:
      return humans();
    case MECHIONS:
      return mechions();
    case GREYANS:
      return greyans();
    case CENTAURS:
      return centaurs();
    case SPORKS:
      return sporks();
    case MOTHOIDS:
      return mothoids();
    case TEUTHIDAES:
      return teuthidaes();
    case SCAURIANS:
      return scaurians();
    case HOMARIANS:
      return homarians();
    case SPACE_MONSTERS:
    case SPACE_PIRATE:
      return spacePirates();
    case CHIRALOIDS:
      return chiraloids();
    case REBORGIANS:
      return reborgians();
    case LITHORIANS:
      return lithorians();
    case ALTEIRIANS:
      return alteirians();
    case SMAUGIRIANS:
      return smaugirians();
    case SYNTHDROIDS:
      return synthdroids();
    default:
      return humans();
    }
  }

}
