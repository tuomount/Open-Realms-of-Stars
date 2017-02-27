package org.openRealmOfStars.starMap.newsCorp;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017  Tuomo Untinen
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
* News Corporation Data Model
*
*/
public class DataModel {

  /**
   * Actual data for Data model
   */
  private int[] data;

  /**
   * Used data size
   */
  private int size;

  /**
   * Constructor for Data Model
   */
  public DataModel() {
    data = new int[0];
    size = -1;
  }

  /**
   * Add new value to Data model
   * @param value Value to Add
   */
  public void addData(final int value) {
    size++;
    if (size >= data.length) {
      int[] temp = new int[data.length + 1];
      System.arraycopy(data, 0, temp, 0, data.length);
      data = temp;
    }
    data[size] = value;
  }

  /**
   * Get the actual Data model
   * @return Data model as interger array
   */
  public int[] getData() {
    return data;
  }

  /**
   * Get the data model size as integer
   * @return Data model size
   */
  public int getSize() {
    return size + 1;
  }
}
