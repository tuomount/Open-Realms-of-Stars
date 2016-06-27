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
   * Get icon by name. Initializes icons if they are unitialized.
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
  }
  

}
