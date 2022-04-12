package org.openRealmOfStars.gui.icons;

import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.util.ArrayList;
import java.util.HashMap;

import org.openRealmOfStars.utilities.IOUtilities;

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
 * Class for handling Icons
 *
 */

public final class Icons {

  /**
   * Just hiding the constructor
   */
  private Icons() {
    // Nothing to do
  }
  /**
   * Mine icon
   */
  public static final String ICON_MINE = "Mine";
  /**
   * Minus icon
   */
  public static final String ICON_MINUS = "Minus";
  /**
   * Minus pressed icon
   */
  public static final String ICON_MINUS_PRESSED = "Minus_Pressed";
  /**
   * Plus icon
   */
  public static final String ICON_PLUS = "Plus";
  /**
   * Plus pressed icon
   */
  public static final String ICON_PLUS_PRESSED = "Plus_Pressed";
  /**
   * Factory icon
   */
  public static final String ICON_FACTORY = "Factory";
  /**
   * Farm icon
   */
  public static final String ICON_FARM = "Farm";
  /**
   * Research icon
   */
  public static final String ICON_RESEARCH = "Research";
  /**
   * Culture icon
   */
  public static final String ICON_CULTURE = "Culture";
  /**
   * People icon
   */
  public static final String ICON_PEOPLE = "People";
  /**
   * No More People icon
   */
  public static final String ICON_NO_MORE_PEOPLE = "NoMorePeople";
  /**
   * Death aka skull icon
   */
  public static final String ICON_DEATH = "Death";
  /**
   * Credit or money icon
   */
  public static final String ICON_CREDIT = "Credit";
  /**
   * Mined metal icon
   */
  public static final String ICON_METAL = "Metal";
  /**
   * Metal ore icon
   */
  public static final String ICON_METAL_ORE = "MetalOre";
  /**
   * Maintenance icon aka credit goes away
   */
  public static final String ICON_MAINTENANCE = "Maintenance";
  /**
   * Tax icon aka credit goes in
   */
  public static final String ICON_TAX = "Tax";
  /**
   * Combat tech icon aka big gun
   */
  public static final String ICON_COMBAT_TECH = "CombatTech";
  /**
   * Defense tech icon aka bunker
   */
  public static final String ICON_DEFENSE_TECH = "DefenseTech";
  /**
   * Hull tech icon aka space ship
   */
  public static final String ICON_HULL_TECH = "HullTech";
  /**
   * Improvement tech icon aka construction tower
   */
  public static final String ICON_IMPROVEMENT_TECH = "ImprovementTech";
  /**
   * Propulsion tech icon aka futuristic engine core
   */
  public static final String ICON_PROPULSION_TECH = "PropulsionTech";
  /**
   * Electronics tech icon aka old computer
   */
  public static final String ICON_ELECTRONICS_TECH = "ElectronicsTech";
  /**
   * Arrow up icon
   */
  public static final String ICON_ARROWUP = "ArrowUp";
  /**
   * Arrow up icon pressed
   */
  public static final String ICON_ARROWUP_PRESSED = "ArrowUp_Pressed";
  /**
   * Arrow up icon disabled
   */
  public static final String ICON_ARROWUP_DISABLED = "ArrowUp_Disabled";
  /**
   * Empty icon so nothing to draw
   */
  public static final String ICON_EMPTY = "Empty";
  /**
   * Scroll bar up arrow
   */
  public static final String ICON_SCROLL_UP = "ScrollUp";
  /**
   * Scroll bar down arrow
   */
  public static final String ICON_SCROLL_DOWN = "ScrollDown";
  /**
   * Scroll bar left arrow
   */
  public static final String ICON_SCROLL_LEFT = "ScrollLeft";
  /**
   * Scroll bar right arrow
   */
  public static final String ICON_SCROLL_RIGHT = "ScrollRight";
  /**
   * Scroll bar up arrow pressed
   */
  public static final String ICON_SCROLL_UP_PRESSED = "ScrollUpPressed";
  /**
   * Scroll bar down arrow pressed
   */
  public static final String ICON_SCROLL_DOWN_PRESSED = "ScrollDownPressed";
  /**
   * Scroll bar left arrow pressed
   */
  public static final String ICON_SCROLL_LEFT_PRESSED = "ScrollLeftPressed";
  /**
   * Scroll bar right arrow pressed
   */
  public static final String ICON_SCROLL_RIGHT_PRESSED = "ScrollRightPressed";
  /**
   * Missile icon
   */
  public static final String ICON_MISSILE = "Missile";
  /**
   * Scanner icon
   */
  public static final String ICON_SCANNER = "Scanner";
  /**
   * Armor icon
   */
  public static final String ICON_ARMOR = "Armor";
  /**
   * Force field icon
   */
  public static final String ICON_SHIELD = "Shield";
  /**
   * Cloaked ship icon
   */
  public static final String ICON_CLOACKING_DEVICE = "CloackingDevice";
  /**
   * Troops icon
   */
  public static final String ICON_TROOPS = "Troops";
  /**
   * Laser gun icon
   */
  public static final String ICON_LASERGUN = "LaserGun";
  /**
   * Bomb icon
   */
  public static final String ICON_BOMB = "Bomb";
  /**
   * Mushroom cloud icon
   */
  public static final String ICON_NUKE = "Nuke";
  /**
   * Planetary turret icon
   */
  public static final String ICON_PLANETARY_TURRET = "PlanetaryTurret";
  /**
   * Satellite icon
   */
  public static final String ICON_STARBASE = "StarBase";
  /**
   * Radar beam icon
   */
  public static final String ICON_LR_SCANNER = "LRScanner";
  /**
   * Circuit board icon
   */
  public static final String ICON_CIRCUIT_BOARD = "CircuitBoard";
  /**
   * Radiation icon
   */
  public static final String ICON_RADIATION = "Radiation";
  /**
   * Power core icon
   */
  public static final String ICON_POWERSOURCE = "PowerSource";
  /**
   * Arrow down icon
   */
  public static final String ICON_ARROWDOWN = "ArrowDown";
  /**
   * Green tick for OK icon
   */
  public static final String ICON_OK = "IconOk";
  /**
   * Circle with line crossing it icon
   */
  public static final String ICON_DELETE = "IconDelete";
  /**
   * Battery icon
   */
  public static final String ICON_BATTERY = "IconBattery";

  /**
   * Checkbox icon
   */
  public static final String ICON_CHECKBOX = "IconCheckBox";
  /**
   * Checkbox tick icon
   */
  public static final String ICON_CHECKBOX_TICK = "IconCheckBoxTick";
  /**
   * Spy goggles icon
   */
  public static final String ICON_SPY_GOGGLES = "IconSpyGoggles";

  /**
   * Music Icon
   */
  public static final String ICON_MUSIC = "IconMusic";

  /**
   * Sound icon
   */
  public static final String ICON_SOUND = "IconSound";

  /**
   * Very happy face
   */
  public static final String ICON_VERY_HAPPY = "IconVeryHappy";

  /**
   * Happy face
   */
  public static final String ICON_HAPPY = "IconHappy";

  /**
   * Okay face
   */
  public static final String ICON_OKAY = "IconOkayFace";

  /**
   * Sad face
   */
  public static final String ICON_SAD = "IconSadFace";
  /**
   * Very sad face
   */
  public static final String ICON_VERY_SAD = "IconVerySadFace";
  /**
   * Planet icon
   */
  public static final String ICON_PLANET = "IconPlanet";
  /**
   * Stats icon
   */
  public static final String ICON_STATS = "IconStats";
  /**
   * Closed icon
   */
  public static final String ICON_CLOSED = "IconClosed";
  /**
   * News icon
   */
  public static final String ICON_NEWS = "IconNews";
  /**
   * Antenna icon
   */
  public static final String ICON_ANTENNA = "IconAntenna";
  /**
   * Map icon
   */
  public static final String ICON_MAP = "IconMap";
  /**
   * Elder Checkbox icon
   */
  public static final String ICON_ELDER_BOX = "IconElder";
  /**
   * Anceint Checkbox tick icon
   */
  public static final String ICON_ELDER_BOX_TICK = "IconElderTick";
  /**
   * Tutorial icon
   */
  public static final String ICON_TUTORIAL = "IconTutorial";
  /**
   * Arrow right icon
   */
  public static final String ICON_ARROW_RIGHT = "IconArrowRight";
  /**
   * Node expanded icon
   */
  public static final String ICON_EXPANDED = "IconExpanded";
  /**
   * Node collabsed icon
   */
  public static final String ICON_COLLAPSED = "IconCollabsed";
  /**
   * AirLock open icon
   */
  public static final String ICON_AIRLOCK_OPEN = "IconAirLockOpen";
  /**
   * Leader too young icon
   */
  public static final String ICON_TOO_YOUNG = "IconTooYoung";
  /**
   * Leaders icon
   */
  public static final String ICON_LEADERS = "IconLeaders";
  /**
   * Governor leader
   */
  public static final String ICON_GOVERNOR = "IconGovernor";
  /**
   * Commander leader
   */
  public static final String ICON_COMMANDER = "IconCommander";
  /**
   * Ruler leader
   */
  public static final String ICON_RULER = "IconRuler";
  /**
   * Tractor beam
   */
  public static final String ICON_TRACTOR_BEAM = "IconTractorBeam";
  /**
   * Solar armor
   */
  public static final String ICON_SOLAR_ARMOR = "IconSolarArmor";
  /**
   * Organic armor
   */
  public static final String ICON_ORGANIC_ARMOR = "IconOrganicArmor";
  /**
   * Plasma cannon
   */
  public static final String ICON_PLASMA_CANNON = "IconPlasmaCannon";
  /**
   * Ion cannon
   */
  public static final String ICON_ION_CANNON = "IconIonCannon";
  /**
   * Distortion shield
   */
  public static final String ICON_DISTORTION_SHIELD = "IconDistortionShield";
  /**
   * Photon torpedo
   */
  public static final String ICON_PHOTON_TORPEDO = "IconPhotonTorpedo";
  /**
   * Prison icon
   */
  public static final String ICON_PRISON = "IconPrison";
  /**
   * Orbital elevator
   */
  public static final String ICON_ORBITAL_ELEVATOR = "IconOrbitalElevator";
  /**
   * Space fin
   */
  public static final String ICON_SPACE_FIN = "IconSpaceFin";
  /**
   * Heart
   */
  public static final String ICON_HEART = "IconHeart";
  /**
   * Mouth
   */
  public static final String ICON_MOUTH = "IconMount";
  /**
   * Tentacle
   */
  public static final String ICON_TENTACLE = "IconTentacle";
  /**
   * Multicannon
   */
  public static final String ICON_MULTI_CANNON = "IconMultiCannon";
  /**
   * Spinosaurus
   */
  public static final String ICON_SPINOSAURUS = "IconSpinosaurus";

  /**
   * List of Icon
   */
  private static ArrayList<Icon16x16> listOfIcons;
  /**
   * Hash map with Icon name to Icon
   */
  private static HashMap<String, Icon16x16> hashOfIcons;

  /**
   * Get Icon with index. Initializes icons if they are uninitialized
   * @param index for icon
   * @return Icon Always returns a tile, If not found icon then first index is
   * returned.
   */
  public static Icon16x16 getIconByIndex(final int index) {
    if (listOfIcons == null) {
      initIcons();
    }
    if (index > 0 && index < listOfIcons.size()) {
      return listOfIcons.get(index);
    }
    return listOfIcons.get(0);
  }

  /**
   * Get icon by name. Initializes icons if they are uninitialized.
   * @param name For search the icon
   * @return Always returns an icon if not found tile then first index is
   * returned.
   */
  public static Icon16x16 getIconByName(final String name) {
    if (hashOfIcons == null) {
      initIcons();
    }
    Icon16x16 icon = hashOfIcons.get(name);
    if (icon == null) {
      return getIconByIndex(0);
    }
    return icon;
  }

  /**
   * Get happy face icon by happiness.
   * @param happyValue Happiness value
   * @return Always returns an happy face icon.
   */
  public static Icon16x16 getHappyFace(final int happyValue) {
    if (hashOfIcons == null) {
      initIcons();
    }
    if (happyValue > 3) {
      return hashOfIcons.get(ICON_VERY_HAPPY);
    }
    if (happyValue > 1) {
      return hashOfIcons.get(ICON_HAPPY);
    }
    if (happyValue > -2) {
      return hashOfIcons.get(ICON_OKAY);
    }
    if (happyValue > -4) {
      return hashOfIcons.get(ICON_SAD);
    }
    return hashOfIcons.get(ICON_VERY_SAD);
  }

  /**
   * Get Maximum count of icons
   * @return Maximum count of icons
   */
  public static int getMaxIcons() {
    if (listOfIcons == null) {
      initIcons();
    }
    return listOfIcons.size();
  }

  /**
   * Add new Icon to Icons list and map
   * @param icon Icon to add
   */
  public static void addIcon(final Icon16x16 icon) {
    listOfIcons.add(icon);
    icon.setIndex(listOfIcons.size() - 1);
    hashOfIcons.put(icon.getName(), icon);
  }

  /**
   * Load big icon from file
   * @param imageFile File name where to load
   * @param x X coordinate in image
   * @param y Y coordiante in image
   * @param width icon width
   * @param height icon height
   * @return BufferedImage as icon
   * @throws RasterFormatException If loading icon fails
   */
  public static BufferedImage loadBigIcon(final String imageFile, final int x,
      final int y, final int width, final int height)
          throws RasterFormatException {
    BufferedImage image = IOUtilities
        .loadImage(Icons.class.getResource(imageFile));
    if (x >= 0 && y >= 0 && x + width < image.getWidth() + 1
        && y + height < image.getHeight() + 1) {
      return image.getSubimage(x, y, width, height);
    }
    throw new RasterFormatException("Icon is outside of image.");
  }
  /**
   * Init Icons
   */
  private static void initIcons() {
    BufferedImage image = IOUtilities
        .loadImage(Icons.class.getResource("/resources/images/icons.png"));
    listOfIcons = new ArrayList<>();
    hashOfIcons = new HashMap<>();
    Icon16x16 icon = new Icon16x16(image, 0, 0, ICON_MINE);
    addIcon(icon);
    icon = new Icon16x16(image, 1, 0, ICON_MINUS);
    addIcon(icon);
    icon = new Icon16x16(image, 1, 1, ICON_MINUS_PRESSED);
    addIcon(icon);
    icon = new Icon16x16(image, 2, 0, ICON_PLUS);
    addIcon(icon);
    icon = new Icon16x16(image, 2, 1, ICON_PLUS_PRESSED);
    addIcon(icon);
    icon = new Icon16x16(image, 3, 0, ICON_FACTORY);
    addIcon(icon);
    icon = new Icon16x16(image, 4, 0, ICON_FARM);
    addIcon(icon);
    icon = new Icon16x16(image, 5, 0, ICON_RESEARCH);
    addIcon(icon);
    icon = new Icon16x16(image, 6, 0, ICON_CULTURE);
    addIcon(icon);
    icon = new Icon16x16(image, 7, 0, ICON_PEOPLE);
    addIcon(icon);
    icon = new Icon16x16(image, 7, 1, ICON_DEATH);
    addIcon(icon);
    icon = new Icon16x16(image, 0, 1, ICON_CREDIT);
    addIcon(icon);
    icon = new Icon16x16(image, 3, 1, ICON_METAL);
    addIcon(icon);
    icon = new Icon16x16(image, 4, 1, ICON_METAL_ORE);
    addIcon(icon);
    icon = new Icon16x16(image, 5, 1, ICON_MAINTENANCE);
    addIcon(icon);
    icon = new Icon16x16(image, 6, 1, ICON_TAX);
    addIcon(icon);
    icon = new Icon16x16(image, 0, 2, ICON_COMBAT_TECH);
    addIcon(icon);
    icon = new Icon16x16(image, 1, 2, ICON_DEFENSE_TECH);
    addIcon(icon);
    icon = new Icon16x16(image, 2, 2, ICON_HULL_TECH);
    addIcon(icon);
    icon = new Icon16x16(image, 3, 2, ICON_IMPROVEMENT_TECH);
    addIcon(icon);
    icon = new Icon16x16(image, 4, 2, ICON_PROPULSION_TECH);
    addIcon(icon);
    icon = new Icon16x16(image, 5, 2, ICON_ELECTRONICS_TECH);
    addIcon(icon);
    icon = new Icon16x16(image, 6, 2, ICON_ARROWUP);
    addIcon(icon);
    icon = new Icon16x16(image, 7, 2, ICON_ARROWUP_PRESSED);
    addIcon(icon);
    icon = new Icon16x16(image, 0, 3, ICON_ARROWUP_DISABLED);
    addIcon(icon);
    icon = new Icon16x16(image, 1, 3, ICON_EMPTY);
    addIcon(icon);
    icon = new Icon16x16(image, 2, 3, ICON_SCROLL_UP);
    addIcon(icon);
    icon = new Icon16x16(image, 3, 3, ICON_SCROLL_DOWN);
    addIcon(icon);
    icon = new Icon16x16(image, 4, 3, ICON_SCROLL_LEFT);
    addIcon(icon);
    icon = new Icon16x16(image, 5, 3, ICON_SCROLL_RIGHT);
    addIcon(icon);
    icon = new Icon16x16(image, 2, 4, ICON_SCROLL_UP_PRESSED);
    addIcon(icon);
    icon = new Icon16x16(image, 3, 4, ICON_SCROLL_DOWN_PRESSED);
    addIcon(icon);
    icon = new Icon16x16(image, 4, 4, ICON_SCROLL_LEFT_PRESSED);
    addIcon(icon);
    icon = new Icon16x16(image, 5, 4, ICON_SCROLL_RIGHT_PRESSED);
    addIcon(icon);
    icon = new Icon16x16(image, 6, 3, ICON_MISSILE);
    addIcon(icon);
    icon = new Icon16x16(image, 7, 3, ICON_SCANNER);
    addIcon(icon);
    icon = new Icon16x16(image, 0, 4, ICON_ARMOR);
    addIcon(icon);
    icon = new Icon16x16(image, 1, 4, ICON_SHIELD);
    addIcon(icon);
    icon = new Icon16x16(image, 6, 4, ICON_CLOACKING_DEVICE);
    addIcon(icon);
    icon = new Icon16x16(image, 7, 4, ICON_TROOPS);
    addIcon(icon);
    icon = new Icon16x16(image, 0, 5, ICON_LASERGUN);
    addIcon(icon);
    icon = new Icon16x16(image, 1, 5, ICON_BOMB);
    addIcon(icon);
    icon = new Icon16x16(image, 2, 5, ICON_NUKE);
    addIcon(icon);
    icon = new Icon16x16(image, 3, 5, ICON_PLANETARY_TURRET);
    addIcon(icon);
    icon = new Icon16x16(image, 4, 5, ICON_STARBASE);
    addIcon(icon);
    icon = new Icon16x16(image, 5, 5, ICON_LR_SCANNER);
    addIcon(icon);
    icon = new Icon16x16(image, 6, 5, ICON_CIRCUIT_BOARD);
    addIcon(icon);
    icon = new Icon16x16(image, 7, 5, ICON_RADIATION);
    addIcon(icon);
    icon = new Icon16x16(image, 0, 6, ICON_POWERSOURCE);
    addIcon(icon);
    icon = new Icon16x16(image, 1, 6, ICON_ARROWDOWN);
    addIcon(icon);
    icon = new Icon16x16(image, 2, 6, ICON_OK);
    addIcon(icon);
    icon = new Icon16x16(image, 3, 6, ICON_DELETE);
    addIcon(icon);
    icon = new Icon16x16(image, 4, 6, ICON_BATTERY);
    addIcon(icon);
    icon = new Icon16x16(image, 5, 6, ICON_NO_MORE_PEOPLE);
    addIcon(icon);
    icon = new Icon16x16(image, 6, 6, ICON_CHECKBOX);
    addIcon(icon);
    icon = new Icon16x16(image, 7, 6, ICON_CHECKBOX_TICK);
    addIcon(icon);
    icon = new Icon16x16(image, 0, 7, ICON_SPY_GOGGLES);
    addIcon(icon);
    icon = new Icon16x16(image, 1, 7, ICON_MUSIC);
    addIcon(icon);
    icon = new Icon16x16(image, 2, 7, ICON_SOUND);
    addIcon(icon);
    icon = new Icon16x16(image, 3, 7, ICON_VERY_HAPPY);
    addIcon(icon);
    icon = new Icon16x16(image, 4, 7, ICON_HAPPY);
    addIcon(icon);
    icon = new Icon16x16(image, 5, 7, ICON_OKAY);
    addIcon(icon);
    icon = new Icon16x16(image, 6, 7, ICON_SAD);
    addIcon(icon);
    icon = new Icon16x16(image, 7, 7, ICON_VERY_SAD);
    addIcon(icon);
    image = IOUtilities.loadImage(Icons.class.getResource(
        "/resources/images/icons2.png"));
    icon = new Icon16x16(image, 0, 0, ICON_PLANET);
    addIcon(icon);
    icon = new Icon16x16(image, 1, 0, ICON_STATS);
    addIcon(icon);
    icon = new Icon16x16(image, 2, 0, ICON_CLOSED);
    addIcon(icon);
    icon = new Icon16x16(image, 3, 0, ICON_NEWS);
    addIcon(icon);
    icon = new Icon16x16(image, 4, 0, ICON_ANTENNA);
    addIcon(icon);
    icon = new Icon16x16(image, 5, 0, ICON_MAP);
    addIcon(icon);
    icon = new Icon16x16(image, 6, 0, ICON_ELDER_BOX_TICK);
    addIcon(icon);
    icon = new Icon16x16(image, 7, 0, ICON_ELDER_BOX);
    addIcon(icon);
    icon = new Icon16x16(image, 0, 1, ICON_TUTORIAL);
    addIcon(icon);
    icon = new Icon16x16(image, 1, 1, ICON_ARROW_RIGHT);
    addIcon(icon);
    icon = new Icon16x16(image, 2, 1, ICON_EXPANDED);
    addIcon(icon);
    icon = new Icon16x16(image, 3, 1, ICON_COLLAPSED);
    addIcon(icon);
    icon = new Icon16x16(image, 4, 1, ICON_AIRLOCK_OPEN);
    addIcon(icon);
    icon = new Icon16x16(image, 5, 1, ICON_TOO_YOUNG);
    addIcon(icon);
    icon = new Icon16x16(image, 6, 1, ICON_GOVERNOR);
    addIcon(icon);
    icon = new Icon16x16(image, 7, 1, ICON_COMMANDER);
    addIcon(icon);
    icon = new Icon16x16(image, 0, 2, ICON_LEADERS);
    addIcon(icon);
    icon = new Icon16x16(image, 1, 2, ICON_RULER);
    addIcon(icon);
    icon = new Icon16x16(image, 2, 2, ICON_SOLAR_ARMOR);
    addIcon(icon);
    icon = new Icon16x16(image, 3, 2, ICON_TRACTOR_BEAM);
    addIcon(icon);
    icon = new Icon16x16(image, 4, 2, ICON_ORGANIC_ARMOR);
    addIcon(icon);
    icon = new Icon16x16(image, 5, 2, ICON_PLASMA_CANNON);
    addIcon(icon);
    icon = new Icon16x16(image, 6, 2, ICON_ION_CANNON);
    addIcon(icon);
    icon = new Icon16x16(image, 7, 2, ICON_DISTORTION_SHIELD);
    addIcon(icon);
    icon = new Icon16x16(image, 0, 3, ICON_PHOTON_TORPEDO);
    addIcon(icon);
    icon = new Icon16x16(image, 1, 3, ICON_PRISON);
    addIcon(icon);
    icon = new Icon16x16(image, 2, 3, ICON_ORBITAL_ELEVATOR);
    addIcon(icon);
    icon = new Icon16x16(image, 3, 3, ICON_SPACE_FIN);
    addIcon(icon);
    icon = new Icon16x16(image, 4, 3, ICON_HEART);
    addIcon(icon);
    icon = new Icon16x16(image, 5, 3, ICON_MOUTH);
    addIcon(icon);
    icon = new Icon16x16(image, 6, 3, ICON_TENTACLE);
    addIcon(icon);
    icon = new Icon16x16(image, 7, 3, ICON_MULTI_CANNON);
    addIcon(icon);
    icon = new Icon16x16(image, 0, 4, ICON_SPINOSAURUS);
    addIcon(icon);
  }

}
