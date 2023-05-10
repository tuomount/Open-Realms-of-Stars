package org.openRealmOfStars.gui.labels;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.ListRenderers.ProductionListRenderer;
import org.openRealmOfStars.gui.borders.SimpleBorder;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.infopanel.EmptyInfoPanel;
import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.mapTiles.Tile;
import org.openRealmOfStars.mapTiles.Tiles;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.construction.Construction;
/**
*
* Open Realm of Stars game project
* Copyright (C) 2018,2020,2023 Tuomo Untinen
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
* Planet info panel with production chooser
*
*/
public class PlanetInfoLabel extends EmptyInfoPanel {

  /**
  *
  */
  private static final long serialVersionUID = 1L;

  /**
   * Planet attribute
   */
  private Planet planet;

  /**
   * Selected construction
   */
  private JComboBox<Construction> constructionSelect;
  /**
   * Estimate how long it takes to build construction.
   */
  private SpaceLabel buildingEstimate;
  /**
   * Constructor for planet info label.
   * @param target Planet which information is shown
   * @param listener ActionListener
   */
  public PlanetInfoLabel(final Planet target, final ActionListener listener) {
    planet = target;
    this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    if (planet == null) {
      this.add(Box.createRigidArea(new Dimension(400, 25)));
      return;
    }
    int tileIndex = planet.getPlanetType().getTileIndex();
    Tile planetTile = Tiles.getTileByIndex(tileIndex);
    BufferedImage image = new BufferedImage(Tile.MAX_WIDTH, Tile.MAX_HEIGHT,
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
    int textWidth = 150 - GuiStatics.getTextWidth(GuiStatics.getFontCubellan(),
        planet.getName());
    if (textWidth > 0) {
      this.add(Box.createRigidArea(new Dimension(textWidth, 25)));
    }
    IconLabel icon = new IconLabel(null, Icons.getIconByName(Icons.ICON_PEOPLE),
        String.valueOf(planet.getTotalPopulation()));
    icon.setToolTipText("Planet's current population number.");
    addIcon(icon);
    icon = new IconLabel(null, Icons.getIconByName(Icons.ICON_FARM),
        String.valueOf(planet.getTotalProduction(Planet.PRODUCTION_FOOD)));
    icon.setToolTipText("Planet's total food per star year.");
    int metalProd = planet.getTotalProduction(Planet.PRODUCTION_METAL);
    if (planet.getPlanetPlayerInfo() != null
        && planet.getPlanetPlayerInfo().getRace().isLithovorian()) {
      metalProd = metalProd - planet.getTotalPopulation() / 2;
    }
    icon = new IconLabel(null, Icons.getIconByName(Icons.ICON_MINE),
        String.valueOf(metalProd));
    icon.setToolTipText("Planet's total metal per star year.");
    addIcon(icon);
    icon = new IconLabel(null, Icons.getIconByName(Icons.ICON_FACTORY),
        String.valueOf(planet.getTotalProduction(
        Planet.PRODUCTION_PRODUCTION)));
    icon.setToolTipText("Planet's total production per star year.");
    addIcon(icon);
    icon = new IconLabel(null, Icons.getIconByName(Icons.ICON_RESEARCH),
        String.valueOf(planet.getTotalProduction(Planet.PRODUCTION_RESEARCH)));
    icon.setToolTipText("Planet's research points per star year.");
    addIcon(icon);
    icon = new IconLabel(null, Icons.getIconByName(Icons.ICON_CULTURE),
        String.valueOf(planet.getTotalProduction(Planet.PRODUCTION_CULTURE)));
    icon.setToolTipText("Planet's total culture production per star year.");
    addIcon(icon);
    icon = new IconLabel(null, Icons.getIconByName(Icons.ICON_CREDIT),
        String.valueOf(planet.getTotalProduction(Planet.PRODUCTION_CREDITS)));
    icon.setToolTipText("<html>Planet's total credits production per star"
        + " year.<br> Negative value means that planet upkeep cost credits."
        + "<br>Positive value means that that planet produces credits."
        + "</html>");
    addIcon(icon);
    int happyValue = planet.calculateHappiness();
    icon = new IconLabel(null, Icons.getHappyFace(happyValue),
        String.valueOf(happyValue));
    if (planet.getPlanetPlayerInfo().getGovernment().isImmuneToHappiness()) {
      icon.setToolTipText("Population is so heavily united that they do "
          + "not feel unhappy or happy.");
    } else {
      icon.setToolTipText("<html>Planet happiness value. Positive value means"
          + " that population is happy.<br> Negative value means that"
          + " population is unhappy.</html>");
    }
    addIcon(icon);
    if (planet.getGovernor() != null) {
      icon = new IconLabel(null, Icons.getIconByName(Icons.ICON_LEADERS), "");
      icon.setToolTipText("Planet has governor "
          + planet.getGovernor().getName() + ".");
    } else {
      icon = new IconLabel(null, Icons.getIconByName(Icons.ICON_GREYED_LEADERS),
          "");
      icon.setToolTipText("Planet does not have governor.");
    }
    addIcon(icon);
    constructionSelect = new JComboBox<>(this.planet.getProductionList());
    constructionSelect.setActionCommand(planet.getName() + "|"
        + GameCommands.COMMAND_PRODUCTION_LIST);
    constructionSelect.setBackground(GuiStatics.COLOR_COOL_SPACE_BLUE_DARK);
    constructionSelect.setForeground(GuiStatics.getCoolSpaceColor());
    constructionSelect.setBorder(new SimpleBorder());
    constructionSelect.setFont(GuiStatics.getFontCubellan());
    constructionSelect.setRenderer(new ProductionListRenderer());
    constructionSelect.setToolTipText("Current project to be build on the"
        + " planet.");
    constructionSelect.setMaximumSize(new Dimension(Integer.MAX_VALUE,
        GuiStatics.TEXT_FIELD_HEIGHT));
    if (planet.getUnderConstruction() != null) {
      Construction[] list = this.planet.getProductionList();
      for (int i = 0; i < list.length; i++) {
        if (list[i].getName().equals(planet.getUnderConstruction().getName())) {
          constructionSelect.setSelectedIndex(i);
          break;
        }
      }
    }
    constructionSelect.addActionListener(listener);
    constructionSelect.setEditable(false);
    this.add(constructionSelect);
    this.add(Box.createRigidArea(new Dimension(5, 25)));
    buildingEstimate = new SpaceLabel("1000 star years");
    buildingEstimate.setText(planet.getProductionTimeAsString(
        (Construction) constructionSelect.getSelectedItem()));
    this.add(buildingEstimate);
    SpaceButton btn = new SpaceButton("View", planet.getName() + "|"
        + GameCommands.COMMAND_VIEW_PLANET);
    btn.addActionListener(listener);
    this.add(btn);
  }

  /**
   * Add IconLabel with possible padding.
   * @param icon Icon Label to add
   */
  private void addIcon(final IconLabel icon) {
    this.add(icon);
    int textWidth = GuiStatics.getTextWidth(GuiStatics.getFontCubellan(), "88")
        - GuiStatics.getTextWidth(GuiStatics.getFontCubellan(), icon.getText());
    if (textWidth > 0) {
      this.add(Box.createRigidArea(new Dimension(textWidth, 25)));
    }
  }

  /**
   * Get selected construction
   * @return Construction or null
   */
  public Construction getSelectedConstruction() {
    if (constructionSelect != null) {
      return (Construction) constructionSelect.getSelectedItem();
    }
    return null;
  }

  /**
   * Update time estimation for construction
   */
  public void updateTimeEstimate() {
    buildingEstimate.setText(planet.getProductionTimeAsString(
        (Construction) constructionSelect.getSelectedItem()));
  }
}
