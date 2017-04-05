package org.openRealmOfStars.gui.icons;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import org.openRealmOfStars.utilities.IOUtilities;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016,2017  Tuomo Untinen
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
  }

}
