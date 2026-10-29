package org.openRealmOfStars.game.tutorial;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2026 Tuomo Untinen
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.openRealmOfStars.utilities.ErrorLogger;
import org.openRealmOfStars.utilities.TextUtilities;
import org.openRealmOfStars.utilities.FileIo.DataLoader;

/**
 * Tutorial Class for handling tutorial text.
 */
public class Tutorial {

  /**
   * Retrieve tutorial based on index
   * @param index Index of tutorial line to retrieve
   * @return HelpLine or null
   */
  public HelpLine getByIndex(final int index) {
    HelpLine[] tmp = getAll();
    for (HelpLine help : tmp) {
      if (help.getIndex() == index) {
        return help;
      }
    }
    return null;
  }

  /**
   * Get all categories in array.
   * @return Array of categories
   */
  public String[] getCategories() {
    ArrayList<String> categories = new ArrayList<>();
    for (HelpLine line : getAll()) {
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
    for (HelpLine line : getAll()) {
      if (line.getCategory().equals(category)) {
        lines.add(line);
      }
    }
    return lines.toArray(new HelpLine[lines.size()]);
  }

  /**
   * Update shown tutorial based on array of indexes.
   * @param shownIndexes Array of indexes.
   */
  public void updateShownTutorial(final ArrayList<Integer> shownIndexes) {
    for (HelpLine line : getAll()) {
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
    for (HelpLine line : getAll()) {
      if (line.isShown()) {
        Integer value = Integer.valueOf(line.getIndex());
        listIndexes.add(value);
      }
    }
    return listIndexes;
  }

  /**
   * Show tutorial text and set flag for shown.
   * @param reference Tutorial reference
   * @return Text or null
   */
  public String showTutorialText(final String reference) {
    HelpLine line = getSingle(reference);
    if (line != null && !line.isShown()) {
      line.setShown(true);
      return TextUtilities.handleEscapes(line.getText());
    }
    return null;
  }

  /** HelpLine this factory knows. IDs are used as keys. */
  private HashMap<String, HelpLine> tutorial;
  /**
   * Tutorial list in order of indexes.
   */
  private ArrayList<String> tutorialList;

  /** Tracks if factory is initialized with data */
  private boolean initialized;
  /** JSON data loader */
  private DataLoader<String, HelpLine> loader;

  /** Contructor */
  public Tutorial() {
    this.tutorial = new HashMap<>();
    this.initialized = false;
    this.loader = new TutorialLoader();
    this.tutorialList = new ArrayList<String>();
  }

  /**
   * Retrieve every tutorial, initialize factory if not yet
   * @return HelpLine array
   */
  private HelpLine[] getAll() {
    if (!initialized) {
      initialized = true;
      init();
    }
    // Reserve result array for each tutorial line.
    HelpLine[] result = new HelpLine[tutorialList.size()];
    int count = 0;
    for (String reference : tutorialList) {
      // Get single line by reference
      HelpLine line = tutorial.get(reference);
      if (line != null && count < result.length) {
        // Found one and not out of buffer
        result[count] = line;
        count++;
      }
    }
    return result;
  }

  /**
   * Retrieve tutorial by reference, initialize factory if not yet
   * @param reference String
   * @return HelpLine or null
   */
  private HelpLine getSingle(final String reference) {
    if (!initialized) {
      initialized = true;
      init();
    }
    return tutorial.get(reference);
  }

  /** (Re)Initialize the factory */
  private void init() {
    ((TutorialLoader) loader).resetCount();
    tutorial.clear();
    tutorialList.clear();
    final var basePath = "resources/data/tutorial/";
    String[] files = {"tutorial"};
    int linesLoaded = loader.loadAll(tutorial, basePath, files);
    // Get tutorials just out from hash map
    HelpLine[] lines = tutorial.values().toArray(new HelpLine[0]);
    List<HelpLine> linesList = Arrays.asList(lines);
    // Sort them in linesList with built-in collections.
    Collections.sort(linesList,
        Comparator.comparingInt(HelpLine::getIndex));
    // Go through sorted list and add them into local tutorial list
    for (HelpLine line : linesList) {
      tutorialList.add(line.getReference());
    }
    ErrorLogger.log("Tutorial lines loaded: " + linesLoaded);
  }
}
