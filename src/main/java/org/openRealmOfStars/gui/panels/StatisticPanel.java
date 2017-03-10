package org.openRealmOfStars.gui.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import org.openRealmOfStars.gui.GuiStatics;

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
 * Statistic panel can show statistical information
 *
 */
public class StatisticPanel extends JPanel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * Data for drawing stats for panel.
   * First array is for players. Second array is actual data to show.
   */
  private int[][] data;

  /**
   * Names for Y data
   */
  private String[] yDataNames;

  /**
   * Largest value on Y axel on data
   */
  private int largestY = 0;

  /**
   * Largest value on X axel on data
   */
  private int largestX = 0;

  /**
   * How many turn is between each X coordinate value. Default is 10.
   */
  private int turnDistance = 10;

  /**
   * Construct Statistics Panel.
   */
  public StatisticPanel() {
    super();
    this.setBackground(Color.black);
  }

  /**
   * Player colors to match culture color shown on map. Notice that
   * culture color on map has alpha channel these do not so they
   * are not identical match.
   */
  public static final Color[] PLAYER_COLORS = {new Color(24, 0, 255),
      new Color(0, 255, 18), new Color(255, 255, 255), new Color(255, 162, 0),
      new Color(11, 255, 241), new Color(233, 44, 255), new Color(188, 0, 0),
      new Color(0, 71, 121)};

  /**
   * Set Y data names which should be equal to player names
   * @param nameList Player name list
   */
  public void setYDataNames(final String[] nameList) {
    yDataNames = nameList;
  }

  /**
   * Get the widest Y data name AKA player name
   * @return Largest player name width in pixels.
   */
  public int getWidestDataName() {
    int largest = 0;
    if (yDataNames != null) {
      for (String name : yDataNames) {
        int value = GuiStatics.getTextWidth(GuiStatics.getFontCubellanSC(),
            name);
        if (value > largest) {
          largest = value;
        }
      }
    }
    return largest;
  }

  /**
   * Set Data for panel.
   * First array is for players. Second array is actual data to show.
   * @param dataModel for statistics panel
   * @throws IllegalArgumentException if data is invalid or null
   */
  public void setData(final int[][] dataModel) throws IllegalArgumentException {
    if (dataModel == null) {
      throw new IllegalArgumentException("Data cannot be null!");
    }
    data = dataModel;
    largestY = 1;
    largestX = 1;
    int sizeI = 0;
    for (int p = 0; p < data.length; p++) {
      if (p == 0) {
        sizeI = data[p].length;
        largestX = sizeI;
      } else {
        if (sizeI != data[p].length) {
          throw new IllegalArgumentException("Data arrays are not equal size");
        }
      }
      for (int i = 0; i < sizeI; i++) {
        if (data[p][i] > largestY) {
          largestY = data[p][i];
        }
      }
    }
  }

  /**
   * This should be used only for JUnits. Returns
   * largest Y value on data
   * @return Largest Y value on data
   */
  public int getLargestY() {
    return largestY;
  }

  /**
   * This should be used only for JUnits. Returns
   * largest X value on data
   * @return Largest X value on data
   */
  public int getLargestX() {
    return largestX;
  }

  /**
   * Set what is the value between two x coordinate value.
   * X is always number of turns
   * @param turnDist Turn distance between two X values
   */
  public void setTurnDistance(final int turnDist) {
    if (turnDist > 0) {
      turnDistance = turnDist;
    }
  }

  /**
   * This should be used only for JUnits. Returns
   * turn distance which is value between to X values
   * @return Turn distance between to X values
   */
  public int getTurnDistance() {
    return turnDistance;
  }

  /**
   * Stat grid density, how long distance is about
   * to draw new lines
   */
  private static final int GRID_DENSITY = 30;
  @Override
  public void paint(final Graphics arg0) {
    int offsetX = 10;
    int offsetY = 30;
    int topOffsetY = 10;
    int rightOffsetX = 10;
    int textWidth = GuiStatics.getTextWidth(GuiStatics.getFontCubellanSC(),
        String.valueOf(largestY));
    textWidth = textWidth + 5;
    offsetX = offsetX + textWidth + getWidestDataName() + 5;
    int drawWidth = this.getWidth() - offsetX - rightOffsetX;
    int drawHeigth = this.getHeight() - offsetY - topOffsetY;

    double scaleY = drawHeigth / largestY;
    double scaleX = drawWidth / largestX;
    Graphics2D g2d = (Graphics2D) arg0;
    g2d.setColor(Color.black);
    g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

    g2d.setFont(GuiStatics.getFontCubellanSC());
    int textHeight = GuiStatics.getTextHeight(GuiStatics.getFontCubellanSC(),
        "0");

    //Draw the grid
    int amount = 1;
    int mult = 1;
    if (GRID_DENSITY / scaleX < 1) {
      amount = largestX;
    } else {
      mult = (int) Math.round(GRID_DENSITY / scaleX);
      amount = (int) Math.round(drawWidth / (GRID_DENSITY));
    }
    for (int i = 0; i <= amount; i++) {
      if (i > 0) {
        g2d.setColor(GuiStatics.COLOR_COOL_SPACE_BLUE_DARK);
        g2d.drawLine((int) Math.round(offsetX + i * scaleX * mult),
            this.getHeight() - offsetY,
            (int) Math.round(offsetX + i * scaleX * mult), topOffsetY);
      }
      g2d.setColor(GuiStatics.COLOR_GREEN_TEXT);
      g2d.drawString(String.valueOf(i * turnDistance),
          (int) Math.round(offsetX + i * scaleX * mult),
          this.getHeight() - offsetY + textHeight);
    }
    amount = 1;
    if (GRID_DENSITY / scaleY < 1) {
      amount = largestY;
    } else {
      mult = (int) Math.round(GRID_DENSITY / scaleY);
      amount = (int) Math.round(drawHeigth / (GRID_DENSITY));
    }
    for (int i = 0; i <= amount; i++) {
      if (i > 0) {
        g2d.setColor(GuiStatics.COLOR_COOL_SPACE_BLUE_DARK);
        g2d.drawLine(offsetX,
            (int) Math.round(this.getHeight() - offsetY - i * scaleY * mult),
            offsetX + drawWidth,
            (int) Math.round(this.getHeight() - offsetY - i * scaleY * mult));
      }
      g2d.setColor(GuiStatics.COLOR_GREEN_TEXT);
      g2d.drawString(String.valueOf(i * mult), offsetX - textWidth,
          (int) Math.round(this.getHeight() - offsetY - i * scaleY
              * mult));
    }

    //Draw the axis
    g2d.setColor(GuiStatics.COLOR_SPACE_GREY_BLUE);
    g2d.drawLine(offsetX, this.getHeight() - offsetY, offsetX + drawWidth,
        this.getHeight() - offsetY);
    g2d.drawLine(offsetX, this.getHeight() - offsetY, offsetX, topOffsetY);

    //Draw the data
    for (int p = 0; p < data.length; p++) {
      g2d.setColor(PLAYER_COLORS[p]);
      if (yDataNames != null && yDataNames.length == data.length) {
        int nameHeight = GuiStatics.getTextHeight(
            GuiStatics.getFontCubellanSC(), yDataNames[p]);
        int nameWidth = GuiStatics.getTextWidth(
            GuiStatics.getFontCubellanSC(), yDataNames[p]);
        g2d.drawString(yDataNames[p], offsetX / 2 - nameWidth / 2 - 10,
            this.getHeight() / 2 - 4 * nameHeight + p * nameHeight);
      }
      if (data[p].length == 1) {
        g2d.drawLine(drawWidth / 2 + offsetX + p * 5,
            this.getHeight() - offsetY, drawWidth / 2 + offsetX + p * 5,
            this.getHeight() - offsetY - (int) Math.round(
                data[p][0] * scaleY));
      } else {
        for (int i = 0; i < largestX - 1; i++) {
          g2d.drawLine(offsetX + (int) Math.round(i * scaleX),
              this.getHeight() - offsetY - (int) Math.round(
                  data[p][i] * scaleY),
               offsetX + (int) Math.round((i + 1) * scaleX),
              this.getHeight() - offsetY - (int) Math.round(
                  data[p][i + 1] * scaleY));
        }
      }
    }
  }

}
