package org.openRealmOfStars.player;

import java.awt.Color;

import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.mapTiles.TileNames;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2021,2022 Tuomo Untinen
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
* Player Color enum
*
*/
public enum PlayerColor {

  /**
   * Blue
   */
  BLUE(0, "Blue"),
  /**
   * Green
   */
  GREEN(1, "Green"),
  /**
   * White
   */
  WHITE(2, "White"),
  /**
   * Orange
   */
  ORANGE(3, "Orange"),
  /**
   * Cyan
   */
  CYAN(4, "Cyan"),
  /**
   * Pink
   */
  PINK(5, "Pink"),
  /**
   * Red
   */
  RED(6, "Red"),
  /**
   * Yellow
   */
  YELLOW(7, "Yellow"),
  /**
   * Black
   */
  BLACK(8, "Black"),
  /**
   * Purple
   */
  PURPLE(9, "Purple"),
  /**
   * Brown
   */
  BROWN(10, "Brown"),
  /**
   * Lime
   */
  LIME(11, "Lime"),
  /**
   * Chestnut
   */
  CHESTNUT(12, "Chestnut"),
  /**
   * Rose
   */
  ROSE(13, "Rose"),
  /**
   * Banana
   */
  BANANA(14, "Banana"),
  /**
   * Gray
   */
  GRAY(15, "Gray"),
  /**
   * Tan
   */
  TAN(16, "Tan"),
  /**
   * Coral
   */
  CORAL(17, "Coral"),
  /**
   * Olive
   */
  OLIVE(18, "Olive"),
  /**
   * Sky
   */
  SKY(19, "Sky");
  /**
   * Player Color constructor
   * @param index Color index
   * @param name Color name
   */
  PlayerColor(final int index, final String name) {
    this.index = index;
    this.name = name;
  }

  /**
   * Get Player color by index.
   * @param index Color index
   * @return PlayerColor
   */
  public static PlayerColor getByIndex(final int index) {
    if (index >= 0 && index < PlayerColor.values().length) {
      return PlayerColor.values()[index];
    }
    return PlayerColor.BLACK;

  }
  /**
   * Get Culture tile name for player color
   * @return Tile name
   */
  public String getCultureTile() {
    switch (this) {
    case BLUE: return TileNames.PLAYER_BLUE;
    case GREEN: return TileNames.PLAYER_GREEN;
    case WHITE: return TileNames.PLAYER_WHITE;
    case ORANGE: return TileNames.PLAYER_ORANGE;
    case CYAN: return TileNames.PLAYER_CYAN;
    case PINK: return TileNames.PLAYER_PINK;
    case RED: return TileNames.PLAYER_RED;
    case YELLOW: return TileNames.PLAYER_YELLOW;
    default:
    case BLACK: return TileNames.PLAYER_BLACK;
    case PURPLE: return TileNames.PLAYER_PURPLE;
    case BROWN: return TileNames.PLAYER_BROWN;
    case LIME: return TileNames.PLAYER_LIME;
    case CHESTNUT: return TileNames.PLAYER_CHESTNUT;
    case ROSE: return TileNames.PLAYER_ROSE;
    case BANANA: return TileNames.PLAYER_BANANA;
    case GRAY: return TileNames.PLAYER_GRAY;
    case TAN: return TileNames.PLAYER_TAN;
    case CORAL: return TileNames.PLAYER_CORAL;
    case OLIVE: return TileNames.PLAYER_OLIVE;
    case SKY: return TileNames.PLAYER_SKY;
   }
  }
  /**
   * Get ship tile name for player color
   * @return Tile name
   */
  public String getShipTile() {
    switch (this) {
    case BLUE: return TileNames.PLAYER_SHIP_BLUE;
    case GREEN: return TileNames.PLAYER_SHIP_GREEN;
    case WHITE: return TileNames.PLAYER_SHIP_WHITE;
    case ORANGE: return TileNames.PLAYER_SHIP_ORANGE;
    case CYAN: return TileNames.PLAYER_SHIP_CYAN;
    case PINK: return TileNames.PLAYER_SHIP_PINK;
    case RED: return TileNames.PLAYER_SHIP_RED;
    case YELLOW: return TileNames.PLAYER_SHIP_YELLOW;
    default:
    case BLACK: return TileNames.PLAYER_SHIP_BLACK;
    case PURPLE: return TileNames.PLAYER_SHIP_PURPLE;
    case BROWN: return TileNames.PLAYER_SHIP_BROWN;
    case LIME: return TileNames.PLAYER_SHIP_LIME;
    case CHESTNUT: return TileNames.PLAYER_SHIP_CHESTNUT;
    case ROSE: return TileNames.PLAYER_SHIP_ROSE;
    case BANANA: return TileNames.PLAYER_SHIP_BANANA;
    case GRAY: return TileNames.PLAYER_SHIP_GRAY;
    case TAN: return TileNames.PLAYER_SHIP_TAN;
    case CORAL: return TileNames.PLAYER_SHIP_CORAL;
    case OLIVE: return TileNames.PLAYER_SHIP_OLIVE;
    case SKY: return TileNames.PLAYER_SHIP_SKY;
   }
  }
  /**
   * Get main color as a color
   * @return Color
   */
  public Color getColor() {
    switch (this) {
    case BLUE: return GuiStatics.PLAYER_BLUE;
    case GREEN: return GuiStatics.PLAYER_GREEN;
    case WHITE: return GuiStatics.PLAYER_WHITE;
    case ORANGE: return GuiStatics.PLAYER_ORANGE;
    case CYAN: return GuiStatics.PLAYER_CYAN;
    case PINK: return GuiStatics.PLAYER_PINK;
    case RED: return GuiStatics.PLAYER_RED;
    case YELLOW: return GuiStatics.PLAYER_YELLOW;
    default:
    case BLACK: return GuiStatics.PLAYER_BLACK;
    case PURPLE: return GuiStatics.PLAYER_PURPLE;
    case BROWN: return GuiStatics.PLAYER_BROWN;
    case LIME: return GuiStatics.PLAYER_LIME;
    case CHESTNUT: return GuiStatics.PLAYER_CHESTNUT;
    case ROSE: return GuiStatics.PLAYER_ROSE;
    case BANANA: return GuiStatics.PLAYER_BANANA;
    case GRAY: return GuiStatics.PLAYER_GRAY;
    case TAN: return GuiStatics.PLAYER_TAN;
    case CORAL: return GuiStatics.PLAYER_CORAL;
    case OLIVE: return GuiStatics.PLAYER_OLIVE;
    case SKY: return GuiStatics.PLAYER_SKY;
   }
  }
  /**
   * Get Color index.
   * @return Color index.
   */
  public int getIndex() {
    return index;
  }

  /**
   * Return color name.
   * @return Color name
   */
  public String getName() {
    return name;
  }
  /**
   * Color index.
   */
  private int index;
  /**
   * Color name;
   */
  private String name;
}
