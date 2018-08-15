package org.openRealmOfStars.mapTiles.anomaly;

import java.awt.image.BufferedImage;

import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.mapTiles.Tile;
import org.openRealmOfStars.mapTiles.TileNames;
import org.openRealmOfStars.mapTiles.Tiles;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2018  Tuomo Untinen
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
* Space anomaly
*
*/
public class SpaceAnomaly {
  /**
   * Space anomaly type
   */
  private AnomalyType type;
  /**
   * Anomaly value for example credits
   */
  private int value;

  /**
   * Textual description what found or happened
   */
  private String text;

  /**
   * Image of Space anomaly
   */
  private BufferedImage image;

  /**
   * Constructor for Space anomaly
   * @param type AnomalyType
   * @param value Space anomaly value
   */
  public SpaceAnomaly(final AnomalyType type, final int value) {
    this.type = type;
    this.value = value;
    this.text = null;
    this.image = null;
  }

  /**
   * Get Space anomaly type
   * @return AnomalyType
   */
  public AnomalyType getType() {
    return type;
  }

  /**
   * Get Space anomaly value
   * @return value
   */
  public int getValue() {
    return value;
  }

  /**
   * Get textual description of space anomaly.
   * @return Textual description
   */
  public String getText() {
    return text;
  }

  /**
   * Set textual description of space anomaly
   * @param text To set
   */
  public void setText(final String text) {
    this.text = text;
  }

  /**
   * Set image of space anomaly
   * @param image to set
   */
  public void setImage(final BufferedImage image) {
    this.image = image;
  }

  /**
   * Get image of space anomaly
   * @return BufferedImage
   */
  public BufferedImage getImage() {
    return image;
  }

  /**
   * Create Space anomaly and handle space anomaly tile
   * @param map StarMap
   * @param info Player who found the anomaly
   * @param fleet Fleet which found anomaly
   * @return SpaceAnomaly or null if coordinates are illegal.
   */
  public static SpaceAnomaly createAnomalyEvent(final StarMap map,
      final PlayerInfo info, final Fleet fleet) {
    SpaceAnomaly result = null;
    Tile tile = map.getTile(fleet.getX(), fleet.getY());
    Tile empty = Tiles.getTileByName(TileNames.EMPTY);
    if (tile != null) {
      switch (tile.getName()) {
        case TileNames.SPACE_ANOMALY_CREDITS: {
          result = new SpaceAnomaly(AnomalyType.CREDIT,
              DiceGenerator.getRandom(2, 10));
          result.setText("There was hidden cache of credits hidden in"
              + " asteroids. Cache contained " + result.getValue()
              + " credits.");
          result.setImage(GuiStatics.IMAGE_ASTEROIDS);
          map.setTile(fleet.getX(), fleet.getY(), empty);
          break;
        }
        case TileNames.SPACE_ANOMALY_MAP: {
          result = new SpaceAnomaly(AnomalyType.MAP, 7);
          result.setText("There was floating very old probe. Probe had"
              + " explored near by space. Explored data was added your"
              + " realm's exploration data.");
          //FIXME with image
          result.setImage(null);
          map.setTile(fleet.getX(), fleet.getY(), empty);
          for (int x = -3; x < 4; x++) {
            for (int y = -3; y < 4; y++) {
              if (map.isValidCoordinate(fleet.getX() + x, fleet.getY() + y)) {
                info.setSectorVisibility(fleet.getX() + x, fleet.getY() + y,
                    PlayerInfo.VISIBLE);
              }
            }
          }
          break;
        }
        default: {
          break;
        }
      }
    }
    return result;
  }
}
