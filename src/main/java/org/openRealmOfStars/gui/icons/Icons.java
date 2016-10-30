package org.openRealmOfStars.gui.icons;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import org.openRealmOfStars.utilities.IOUtilities;


/**
 * 
 * Open Realm of Stars game project
 * Copyright (C) 2016  Tuomo Untinen
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

public class Icons {

  public static final String ICON_MINE = "Mine";
  public static final String ICON_MINUS = "Minus";
  public static final String ICON_MINUS_PRESSED = "Minus_Pressed";
  public static final String ICON_PLUS = "Plus";
  public static final String ICON_PLUS_PRESSED = "Plus_Pressed";
  public static final String ICON_FACTORY = "Factory";
  public static final String ICON_FARM = "Farm";
  public static final String ICON_RESEARCH = "Research";
  public static final String ICON_CULTURE = "Culture";
  public static final String ICON_PEOPLE = "People";
  public static final String ICON_DEATH = "Death";
  public static final String ICON_CREDIT = "Credit";
  public static final String ICON_METAL = "Metal";
  public static final String ICON_METAL_ORE = "MetalOre";
  public static final String ICON_MAINTENANCE = "Maintenance";
  public static final String ICON_TAX = "Tax";
  public static final String ICON_COMBAT_TECH = "CombatTech";
  public static final String ICON_DEFENSE_TECH = "DefenseTech";
  public static final String ICON_HULL_TECH = "HullTech";
  public static final String ICON_IMPROVEMENT_TECH = "ImprovementTech";
  public static final String ICON_PROPULSION_TECH = "PropulsionTech";
  public static final String ICON_ELECTRONICS_TECH = "ElectronicsTech";
  public static final String ICON_ARROWUP = "ArrowUp";
  public static final String ICON_ARROWUP_PRESSED = "ArrowUp_Pressed";
  public static final String ICON_ARROWUP_DISABLED = "ArrowUp_Disabled";
  public static final String ICON_EMPTY = "Empty";
  public static final String ICON_SCROLL_UP = "ScrollUp";
  public static final String ICON_SCROLL_DOWN = "ScrollDown";
  public static final String ICON_SCROLL_LEFT = "ScrollLeft";
  public static final String ICON_SCROLL_RIGHT = "ScrollRight";
  public static final String ICON_SCROLL_UP_PRESSED = "ScrollUpPressed";
  public static final String ICON_SCROLL_DOWN_PRESSED = "ScrollDownPressed";
  public static final String ICON_SCROLL_LEFT_PRESSED = "ScrollLeftPressed";
  public static final String ICON_SCROLL_RIGHT_PRESSED = "ScrollRightPressed";
  public static final String ICON_MISSILE = "Missile";
  public static final String ICON_SCANNER = "Scanner";
  public static final String ICON_ARMOR = "Armor";
  public static final String ICON_SHIELD = "Shield";
  public static final String ICON_CLOACKING_DEVICE = "CloackingDevice";
  public static final String ICON_TROOPS = "Troops";
  public static final String ICON_LASERGUN = "LaserGun";
  public static final String ICON_BOMB = "Bomb";
  public static final String ICON_NUKE = "Nuke";
  public static final String ICON_PLANETARY_TURRET = "PlanetaryTurret";
  public static final String ICON_STARBASE = "StarBase";
  public static final String ICON_LR_SCANNER = "LRScanner";
  public static final String ICON_CIRCUIT_BOARD = "CircuitBoard";
  public static final String ICON_RADIATION = "Radiation";
  public static final String ICON_POWERSOURCE = "PowerSource";
  public static final String ICON_ARROWDOWN = "ArrowDown";
  public static final String ICON_OK = "IconOk";
  public static final String ICON_DELETE = "IconDelete";
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
  public static Icon16x16 getIconByIndex(int index) {
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
  public static Icon16x16 getIconByName(String name) {
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
   * @param Icon Icon to add
   */
  public static void addIcon(Icon16x16 Icon) {
    listOfIcons.add(Icon);
    Icon.setIndex(listOfIcons.size()-1);
    hashOfIcons.put(Icon.getName(), Icon);
  }
  
  /**
   * Init Icons
   */
  private static void initIcons() {
    BufferedImage image = IOUtilities.loadImage(Icons.class.getResource(
        "/resources/images/icons.png"));
    listOfIcons = new ArrayList<>();
    hashOfIcons = new HashMap<>();
    Icon16x16 icon = new Icon16x16(image, 0, 0,ICON_MINE);
    addIcon(icon);
    icon = new Icon16x16(image, 1, 0,ICON_MINUS);
    addIcon(icon);
    icon = new Icon16x16(image, 1, 1,ICON_MINUS_PRESSED);
    addIcon(icon);
    icon = new Icon16x16(image, 2, 0,ICON_PLUS);
    addIcon(icon);
    icon = new Icon16x16(image, 2, 1,ICON_PLUS_PRESSED);
    addIcon(icon);
    icon = new Icon16x16(image, 3, 0,ICON_FACTORY);
    addIcon(icon);
    icon = new Icon16x16(image, 4, 0,ICON_FARM);
    addIcon(icon);
    icon = new Icon16x16(image, 5, 0,ICON_RESEARCH);
    addIcon(icon);
    icon = new Icon16x16(image, 6, 0,ICON_CULTURE);
    addIcon(icon);
    icon = new Icon16x16(image, 7, 0,ICON_PEOPLE);
    addIcon(icon);
    icon = new Icon16x16(image, 7, 1,ICON_DEATH);
    addIcon(icon);
    icon = new Icon16x16(image, 0, 1,ICON_CREDIT);
    addIcon(icon);
    icon = new Icon16x16(image, 3, 1,ICON_METAL);
    addIcon(icon);
    icon = new Icon16x16(image, 4, 1,ICON_METAL_ORE);
    addIcon(icon);
    icon = new Icon16x16(image, 5, 1,ICON_MAINTENANCE);
    addIcon(icon);
    icon = new Icon16x16(image, 6, 1,ICON_TAX);
    addIcon(icon);
    icon = new Icon16x16(image, 0, 2,ICON_COMBAT_TECH);
    addIcon(icon);
    icon = new Icon16x16(image, 1, 2,ICON_DEFENSE_TECH);
    addIcon(icon);
    icon = new Icon16x16(image, 2, 2,ICON_HULL_TECH);
    addIcon(icon);
    icon = new Icon16x16(image, 3, 2,ICON_IMPROVEMENT_TECH);
    addIcon(icon);
    icon = new Icon16x16(image, 4, 2,ICON_PROPULSION_TECH);
    addIcon(icon);
    icon = new Icon16x16(image, 5, 2,ICON_ELECTRONICS_TECH);
    addIcon(icon);
    icon = new Icon16x16(image, 6, 2,ICON_ARROWUP);
    addIcon(icon);
    icon = new Icon16x16(image, 7, 2,ICON_ARROWUP_PRESSED);
    addIcon(icon);
    icon = new Icon16x16(image, 0, 3,ICON_ARROWUP_DISABLED);
    addIcon(icon);
    icon = new Icon16x16(image, 1, 3,ICON_EMPTY);
    addIcon(icon);
    icon = new Icon16x16(image, 2, 3,ICON_SCROLL_UP);
    addIcon(icon);
    icon = new Icon16x16(image, 3, 3,ICON_SCROLL_DOWN);
    addIcon(icon);
    icon = new Icon16x16(image, 4, 3,ICON_SCROLL_LEFT);
    addIcon(icon);
    icon = new Icon16x16(image, 5, 3,ICON_SCROLL_RIGHT);
    addIcon(icon);
    icon = new Icon16x16(image, 2, 4,ICON_SCROLL_UP_PRESSED);
    addIcon(icon);
    icon = new Icon16x16(image, 3, 4,ICON_SCROLL_DOWN_PRESSED);
    addIcon(icon);
    icon = new Icon16x16(image, 4, 4,ICON_SCROLL_LEFT_PRESSED);
    addIcon(icon);
    icon = new Icon16x16(image, 5, 4,ICON_SCROLL_RIGHT_PRESSED);
    addIcon(icon);
    icon = new Icon16x16(image, 6, 3,ICON_MISSILE);
    addIcon(icon);
    icon = new Icon16x16(image, 7, 3,ICON_SCANNER);
    addIcon(icon);
    icon = new Icon16x16(image, 0, 4,ICON_ARMOR);
    addIcon(icon);
    icon = new Icon16x16(image, 1, 4,ICON_SHIELD);
    addIcon(icon);
    icon = new Icon16x16(image, 6, 4,ICON_CLOACKING_DEVICE);
    addIcon(icon);
    icon = new Icon16x16(image, 7, 4,ICON_TROOPS);
    addIcon(icon);
    icon = new Icon16x16(image, 0, 5,ICON_LASERGUN);
    addIcon(icon);
    icon = new Icon16x16(image, 1, 5,ICON_BOMB);
    addIcon(icon);
    icon = new Icon16x16(image, 2, 5,ICON_NUKE);
    addIcon(icon);
    icon = new Icon16x16(image, 3, 5,ICON_PLANETARY_TURRET);
    addIcon(icon);
    icon = new Icon16x16(image, 4, 5,ICON_STARBASE);
    addIcon(icon);
    icon = new Icon16x16(image, 5, 5,ICON_LR_SCANNER);
    addIcon(icon);
    icon = new Icon16x16(image, 6, 5,ICON_CIRCUIT_BOARD);
    addIcon(icon);
    icon = new Icon16x16(image, 7, 5,ICON_RADIATION);
    addIcon(icon);
    icon = new Icon16x16(image, 0, 6,ICON_POWERSOURCE);
    addIcon(icon);
    icon = new Icon16x16(image, 1, 6,ICON_ARROWDOWN);
    addIcon(icon);
    icon = new Icon16x16(image, 2, 6,ICON_OK);
    addIcon(icon);
    icon = new Icon16x16(image, 3, 6,ICON_DELETE);
    addIcon(icon);
    icon = new Icon16x16(image, 4, 6,ICON_BATTERY);
    addIcon(icon);
  }
  

}
