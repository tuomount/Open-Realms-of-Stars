package org.openRealmOfStars.game.tutorial;

import java.util.ArrayList;

import org.openRealmOfStars.utilities.TextUtilities;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2019  Tuomo Untinen
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
* Tutorial list for help lines. This list cannot remove elements only add.
* If same index is added twice then old is replaced.
*
*/
public class TutorialList {

  /**
   * Tutorial list.
   */
  private ArrayList<HelpLine> list;

  /**
   * Constructor for empty tutorial list.
   */
  public TutorialList() {
    list = new ArrayList<>();
  }

  /**
   * Adds new tutorial line to list and sorts the list by index.
   * @param line HelpLine to add.
   */
  public void add(final HelpLine line) {
    if (list.size() == 0) {
      list.add(line);
    } else {
      boolean added = false;
      for (int i = 0; i < list.size(); i++) {
        HelpLine lineTmp = list.get(i);
        if (lineTmp.getIndex() > line.getIndex()) {
          list.add(i, line);
          added = true;
          break;
        }
        if (lineTmp.getIndex() == line.getIndex()) {
          list.remove(i);
          list.add(i, line);
          added = true;
          break;
        }
      }
      if (!added) {
        list.add(line);
      }
    }
  }

  /**
   * Get all categories in array.
   * @return Array of categories
   */
  public String[] getCategories() {
    ArrayList<String> categories = new ArrayList<>();
    for (HelpLine line : list) {
      String category = line.getCategory();
      boolean found = false;
      for (String temp : categories) {
        if (category.equals(temp)) {
          found = true;
        }
      }
      if (!found) {
        categories.add(category);
      }
    }
    return categories.toArray(new String[categories.size()]);
  }

  /**
   * Get help line topics for certain category
   * @param category Category which to search.
   * @return Array of help lines for category
   */
  public HelpLine[] getTopics(final String category) {
    ArrayList<HelpLine> lines = new ArrayList<>();
    for (HelpLine line : list) {
      if (line.getCategory().equals(category)) {
        lines.add(line);
      }
    }
    return lines.toArray(new HelpLine[lines.size()]);
  }
  /**
   * Get HelpLine by list index. This method should be used
   * in for loops where each list index is fetched.
   * @param index List index
   * @return Help Line or null
   */
  public HelpLine get(final int index) {
    if (index >= 0 && index < list.size()) {
      return list.get(index);
    }
    return null;
  }

  /**
   * Get Help Line by help line index.
   * @param index Help line index.
   * @return HelpLine or null
   */
  public HelpLine getByIndex(final int index) {
    for (HelpLine line : list) {
      if (line.getIndex() == index) {
        return line;
      }
    }
    return null;
  }
  /**
   * Get the size of the list.
   * @return Number of elements
   */
  public int getSize() {
    return list.size();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (HelpLine line : list) {
      sb.append(line.toString());
      sb.append("\n");
    }
    return sb.toString();
  }

  /**
   * Update shown tutorial based on array of indexes.
   * @param shownIndexes Array of indexes.
   */
  public void updateShownTutorial(final ArrayList<Integer> shownIndexes) {
    for (HelpLine line : list) {
      line.setShown(false);
    }
    for (Integer value : shownIndexes) {
      HelpLine line = getByIndex(value.intValue());
      if (line != null) {
        line.setShown(true);
      }
    }
  }
  /**
   * Get Shown indexes in array list
   * @return ArrayList of shown indexes.
   */
  public ArrayList<Integer> getShownIndexes() {
    ArrayList<Integer> listIndexes = new ArrayList<>();
    for (HelpLine line : list) {
      if (line.isShown()) {
        Integer value = new Integer(line.getIndex());
        listIndexes.add(value);
      }
    }
    return listIndexes;
  }

  /**
   * Show tutorial text and set flag for shown.
   * @param index Tutorial index
   * @return Text or null
   */
  public String showTutorialText(final int index) {
    HelpLine line = getByIndex(index);
    if (line != null && !line.isShown()) {
      line.setShown(true);
      return TextUtilities.handleEscapes(line.getText());
    }
    return null;
  }
}
