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
  
  /**
   * List of Icon
   */
  private static ArrayList<Icon> listOfIcons;
  /**
   * Hash map with Icon name to Icon
   */
  private static HashMap<String, Icon> hashOfIcons;
  
  /**
   * Get Icon with index. Initializes icons if they are uninitialized
   * @param index for icon
   * @return Icon Always returns a tile, If not found icon then first index is 
   * returned.
   */
  public static Icon getIconByIndex(int index) {
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
  public static Icon getIconByName(String name) {
    if (hashOfIcons == null) {
      initIcons();
    }
    Icon icon = hashOfIcons.get(name);
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
  public static void addIcon(Icon Icon) {
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
    Icon icon = new Icon(image, 0, 0,ICON_MINE);
    addIcon(icon);
    icon = new Icon(image, 1, 0,ICON_MINUS);
    addIcon(icon);
    icon = new Icon(image, 1, 1,ICON_MINUS_PRESSED);
    addIcon(icon);
    icon = new Icon(image, 2, 0,ICON_PLUS);
    addIcon(icon);
    icon = new Icon(image, 2, 1,ICON_PLUS_PRESSED);
    addIcon(icon);
    icon = new Icon(image, 3, 0,ICON_FACTORY);
    addIcon(icon);
    icon = new Icon(image, 4, 0,ICON_FARM);
    addIcon(icon);
    icon = new Icon(image, 5, 0,ICON_RESEARCH);
    addIcon(icon);
    icon = new Icon(image, 6, 0,ICON_CULTURE);
    addIcon(icon);
    icon = new Icon(image, 7, 0,ICON_PEOPLE);
    addIcon(icon);
  }
  

}
