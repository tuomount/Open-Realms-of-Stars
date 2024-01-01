package org.openRealmOfStars.gui.labels;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2023-2024 Tuomo Untinen
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

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.BoxLayout;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.infopanel.EmptyInfoPanel;
import org.openRealmOfStars.gui.util.GuiFonts;
import org.openRealmOfStars.gui.util.GuiStatics;
import org.openRealmOfStars.mapTiles.Tile;
import org.openRealmOfStars.mapTiles.Tiles;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.starMap.planet.Planet;

/**
*
* Planet info panel for uncolozized
*
*/
public class UncolonizedPlanetInfoLabel extends EmptyInfoPanel {

  /**
  *
  */
  private static final long serialVersionUID = 1L;

  /**
   * Planet attribute
   */
  private Planet planet;

  /**
   * Constructor for planet info label.
   * @param target Planet which information is shown
   * @param info PlayerInfo
   * @param listener ActionListener
   */
  public UncolonizedPlanetInfoLabel(final Planet target, final PlayerInfo info,
      final ActionListener listener) {
    planet = target;
    this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    if (planet == null) {
      this.add(Box.createRigidArea(new Dimension(400, 25)));
      return;
    }
    int tileIndex = planet.getPlanetType().getTileIndex();
    Tile planetTile = Tiles.getTileByIndex(tileIndex);
    BufferedImage image = new BufferedImage(Tile.getMaxWidth(Tile.ZOOM_NORMAL),
        Tile.getMaxHeight(Tile.ZOOM_NORMAL),
        BufferedImage.TYPE_4BYTE_ABGR);
    planetTile.draw(image.createGraphics(), 0, 0);
    ImageLabel planetImage = new ImageLabel(image, false);
    this.add(planetImage);
    SpaceLabel planetLabel = new SpaceLabel(planet.getName());
    this.add(planetLabel);
    if (planet.getHomeWorldIndex() != -1) {
      this.add(new IconLabel(null, Icons.getIconByName(Icons.ICON_CULTURE),
          ""));
    } else {
      this.add(Box.createRigidArea(new Dimension(26, 25)));
    }
    int textWidth = 150 - GuiStatics.getTextWidth(GuiFonts.getFontCubellan(),
        planet.getName());
    if (textWidth > 0) {
      this.add(Box.createRigidArea(new Dimension(textWidth, 25)));
    }
    IconLabel icon = new IconLabel(null,
        Icons.getIconByName(Icons.ICON_LEADERS),
        planet.getSizeAsString());
    icon.setToolTipText("Planet's total ground size");
    addIcon(icon);
    icon = new IconLabel(null, Icons.getIconByName(
        Icons.ICON_RADIATION), String.valueOf(planet.getRadiationLevel()));
    icon.setToolTipText("Planet's current radiation level");
    addIcon(icon);
    int suitable = info.getPlanetSuitabilityValue(planet);
    String iconName = Icons.ICON_OKAY;
    if (suitable >= 150) {
      iconName = Icons.ICON_VERY_HAPPY;
    } else if (suitable >= 125) {
      iconName = Icons.ICON_HAPPY;
    } else if (suitable >= 100) {
      iconName = Icons.ICON_OKAY;
    } else if (suitable >= 75) {
      iconName = Icons.ICON_SAD;
    } else if (suitable >= 50) {
      iconName = Icons.ICON_VERY_SAD;
    }
    icon = new IconLabel(null, Icons.getIconByName(iconName),
        "Suitable:" + suitable + "% ");
    icon.setToolTipText("<html>How suitable planet is for the realm.<br>"
        + "How many percent of maximum population can live there.</html>");
    addIcon(icon);
    icon = new IconLabel(null, Icons.getIconByName(Icons.ICON_PLANET),
        planet.getPlanetType().getTypeAsString());
    icon.setToolTipText("Planet's type");
    addIcon(icon);
    int dist = (int) info.getCenterRealm().calculateDistance(
        planet.getCoordinate());
    icon = new IconLabel(null, Icons.getIconByName(Icons.ICON_PROPULSION_TECH),
        "Distance: " + dist);
    icon.setToolTipText("Distance from realm's center in sectors.");
    addIcon(icon);
    SpaceButton btn = new SpaceButton("View", planet.getName() + "|"
        + GameCommands.COMMAND_VIEW_PLANET_ON_MAP);
    btn.addActionListener(listener);
    this.add(btn);
  }

  /**
   * Add IconLabel with possible padding.
   * @param icon Icon Label to add
   */
  private void addIcon(final IconLabel icon) {
    this.add(icon);
    int textWidth = GuiStatics.getTextWidth(GuiFonts.getFontCubellan(), "88")
        - GuiStatics.getTextWidth(GuiFonts.getFontCubellan(), icon.getText());
    if (textWidth > 0) {
      this.add(Box.createRigidArea(new Dimension(textWidth, 25)));
    }
  }

}
