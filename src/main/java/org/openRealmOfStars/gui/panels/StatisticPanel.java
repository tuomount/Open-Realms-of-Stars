package org.openRealmOfStars.gui.panels;

import java.awt.Color;

import javax.swing.JPanel;

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
   * Largest value on Y axel on data
   */
  private int largestY = 0;

  /**
   * Construct Statistics Panel.
   */
  public StatisticPanel() {
    super();
    this.setBackground(Color.black);
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
    largestY = 0;
    int sizeI = 0;
    for (int p = 0; p < data.length; p++) {
      if (p == 0) {
        sizeI = data[p].length;
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

}
