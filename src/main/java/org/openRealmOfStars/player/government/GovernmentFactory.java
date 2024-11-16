package org.openRealmOfStars.player.government;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2024 Tuomo Untinen
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

import java.util.HashMap;

import org.openRealmOfStars.game.Game;
import org.openRealmOfStars.utilities.DataLoader;
import org.openRealmOfStars.utilities.DataSources;
import org.openRealmOfStars.utilities.DiceGenerator;
import org.openRealmOfStars.utilities.ErrorLogger;

/**
 * Government factory class.
 *
 */
public final class GovernmentFactory {

  /** The Singleton */
  private static final GovernmentFactory SINGLETON = new GovernmentFactory();
  /** Governments this factory knows. IDs are used as keys. */
  private HashMap<String, Government> governments;
  /** Tracks if factory is initialized with data */
  private boolean initialized;
  /** JSON data loader */
  private DataLoader<String, Government> loader;

  /**
   * Create/Retrieve Government for given ID, if loaded
   * @param governmentId ID of government to retrieve
   * @return Government
   */
  public static Government createOne(final String governmentId) {
    return SINGLETON.makeById(governmentId);
  }

  /**
   * Get random Government
   * @return Government
   */
  public static Government getRandomGovernment() {
    return DiceGenerator.pickRandom(SINGLETON.getAll());
  }
  /**
   * Get all Governments in array
   * @return array of Governments
   */
  public static Government[] getValues() {
    return SINGLETON.getAll();
  }
  /** Contructor */
  private GovernmentFactory() {
    this.governments = new HashMap<>();
    this.initialized = false;
    this.loader = new GovernmentLoader();
  }

  /**
   * Create/Retrieve Government for given ID, initialize factory if not yet
   * @param governmentId ID of government to retrieve
   * @return Government or Democracy by default
   */
  private Government makeById(final String governmentId) {
    if (!initialized) {
      initialized = true;
      init();
    }

    Government government = governments.get(governmentId);
    if (government == null) {
      government = governments.get("DEMOCRACY");
      if (government == null) {
        throw new IllegalArgumentException(
            "Government factory does not contain "
            + governmentId + " or DEMOCRACY.");
      }
    }
    return government;
  }

  /** (Re)Initialize the factory */
  private void init() {
    governments.clear();
    final var basePath = "resources/data/governments/";
    String[] files = {
        "base"};
    int governmentsLoaded = loader.loadAll(governments, basePath, files);
    files = DataSources.findJsonFilesInPath(Game.getCustomGovPath());
    governmentsLoaded = governmentsLoaded + loader.loadAll(governments,
        Game.getCustomGovPath() + "/", files);
    ErrorLogger.log("Governments loaded: " + governmentsLoaded);
  }

  /**
   * Create/Retrieve all Governments, initialize factory if not yet
   * @return Governments array
   */
  private Government[] getAll() {
    if (!initialized) {
      initialized = true;
      init();
    }
    return governments.values().toArray(new Government[0]);
  }
}
