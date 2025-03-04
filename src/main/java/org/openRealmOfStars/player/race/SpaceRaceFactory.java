package org.openRealmOfStars.player.race;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.openRealmOfStars.utilities.DiceGenerator;
import org.openRealmOfStars.utilities.ErrorLogger;
import org.openRealmOfStars.utilities.FileIo.DataLoader;
import org.openRealmOfStars.utilities.FileIo.DataSources;
import org.openRealmOfStars.utilities.FileIo.Folders;

/** SpaceRace Factory which reads space races from JSON. */
public final class SpaceRaceFactory {

  /** Constant for pseudo race SPACEPIRATES */
  public static final String SPACE_PIRATE = "SPACEPIRATES";
  /** Constant for pseudo race SPACEMONSTERS */
  public static final String SPACE_MONSTER = "SPACEMONSTERS";
  /** The Singleton */
  private static final SpaceRaceFactory SINGLETON = new SpaceRaceFactory();

  /**
   * Create/Retrieve SpaceRace for given ID, if loaded
   * @param spaceRaceId ID of trait to retrieve
   * @return SpaceRaceClass or empty
   */
  public static Optional<SpaceRace> create(final String spaceRaceId) {
    return SINGLETON.createById(spaceRaceId);
  }

  /**
   * Create/Retrieve SpaceRace for given ID, if loaded
   * @param spaceRaceId ID of trait to retrieve
   * @return SpaceRaceClass or empty
   */
  public static SpaceRace createOne(final String spaceRaceId) {
    return SINGLETON.makeById(spaceRaceId);
  }

  /**
   * Get All Space races in array.
   * @return Space race array
   */
  public static SpaceRace[] getValues() {
    return SINGLETON.getAll();
  }

  /**
   * Get All Space races in array but not pseudo races.
   * @return Space race array
   */
  public static SpaceRace[] getValuesNoPseudo() {
    ArrayList<SpaceRace> list = new ArrayList<>();
    for (SpaceRace race : getValues()) {
      if (!race.getId().equals(SPACE_MONSTER)
          && !race.getId().equals(SPACE_PIRATE)) {
        list.add(race);
      }
    }
    return list.toArray(new SpaceRace[list.size()]);
  }

  /**
   * Get All Space race names in array. This will filter out pseudo races.
   * @return Space race name array
   */
  public static String[] getNames() {
    SpaceRace[] races = SINGLETON.getAll();
    ArrayList<String> names = new ArrayList<>();
    for (SpaceRace race : races) {
      if (race.getSpaceRaceType() == SpaceRaceType.REGULAR) {
        names.add(race.getNameSingle());
      }
    }
    return names.toArray(new String[names.size()]);
  }

  /**
   * Get All Space race ids in array. This will filter out pseudo races.
   * @return Space race ids array
   */
  public static String[] getIds() {
    SpaceRace[] races = SINGLETON.getAll();
    ArrayList<String> names = new ArrayList<>();
    for (SpaceRace race : races) {
      if (race.getSpaceRaceType() == SpaceRaceType.REGULAR) {
        names.add(race.getId());
      }
    }
    return names.toArray(new String[names.size()]);
  }

  /**
   * Get random living race.
   * @return Living SpaceRace
   */
  public static SpaceRace getRandomLivingRace() {
    var nonRoboticRaces = Stream.of(getValues())
        .filter(race -> !race.isRoboticRace())
        // Filter out "pseudo-races"
        .filter(race -> race.getSpaceRaceType() == SpaceRaceType.REGULAR)
        .collect(Collectors.toList());
    if (nonRoboticRaces.isEmpty()) {
      return null;
    }
    return DiceGenerator.pickRandom(nonRoboticRaces);
  }

  /**
   * Get random space race, filtered out monsters and pirates.
   * @return SpaceRace
   */
  public static SpaceRace getRandomRace() {
    var races = Stream.of(getValues())
        // Filter out "pseudo-races"
        .filter(race -> race.getSpaceRaceType() == SpaceRaceType.REGULAR)
        .collect(Collectors.toList());
    if (races.isEmpty()) {
      return null;
    }
    return DiceGenerator.pickRandom(races);
  }

  /**
   * Get random robotic race
   * @return Robotic SpaceRace
   */
  public static SpaceRace getRandomRoboticRace() {
    var roboticRaces = Stream.of(getValues())
        .filter(race -> race.isRoboticRace())
        .collect(Collectors.toList());
    if (roboticRaces.isEmpty()) {
      return null;
    }
    return DiceGenerator.pickRandom(roboticRaces);
  }

  /**
   * Restart factory and reload everything again when needed.
   */
  public static void restartFactory() {
    SINGLETON.initialized = false;
    SINGLETON.spaceRaces.clear();
  }

  /** SpaceRace this factory knows. IDs are used as keys. */
  private HashMap<String, SpaceRace> spaceRaces;
  /** Tracks if factory is initialized with data */
  private boolean initialized;
  /** JSON data loader */
  private DataLoader<String, SpaceRace> loader;

  /** Contructor */
  private SpaceRaceFactory() {
    this.spaceRaces = new HashMap<>();
    this.initialized = false;
    this.loader = new SpaceRaceLoader();
  }

  /**
   * Create/Retrieve SpaceRace for given ID, initialize factory if not yet
   * @param spaceRaceId ID of trait to retrieve
   * @return SpaceRace or empty
   */
  private Optional<SpaceRace> createById(final String spaceRaceId) {
    if (!initialized) {
      initialized = true;
      init();
    }

    final var spaceRace = spaceRaces.get(spaceRaceId);
    return Optional.ofNullable(spaceRace);
  }

  /**
   * Create/Retrieve SpaceRace for given ID, initialize factory if not yet
   * @param spaceRaceId ID of trait to retrieve
   * @return SpaceRace or humans by default
   */
  private SpaceRace makeById(final String spaceRaceId) {
    if (!initialized) {
      initialized = true;
      init();
    }

    SpaceRace spaceRace = spaceRaces.get(spaceRaceId);
    if (spaceRace == null) {
      spaceRace = spaceRaces.get("HUMANS");
      if (spaceRace == null) {
        throw new IllegalArgumentException(
            "Space race factory does not contain "
            + spaceRaceId + " or humans.");
      }
    }
    return spaceRace;
  }

  /**
   * Create/Retrieve all SpaceRaces, initialize factory if not yet
   * @return SpaceRaces array
   */
  private SpaceRace[] getAll() {
    if (!initialized) {
      initialized = true;
      init();
    }
    return spaceRaces.values().toArray(new SpaceRace[0]);
  }

  /** (Re)Initialize the factory */
  private void init() {
    spaceRaces.clear();
    final var basePath = "resources/data/spaceraces/";
    String[] files = {
        "arthropods", "humanoids", "lithovorians", "robots", "pseudoraces",
        "plants"};
    int spaceRacesLoaded = loader.loadAll(spaceRaces, basePath, files);
    files = DataSources.findJsonFilesInPath(Folders.getCustomSpaceRacePath());
    spaceRacesLoaded = spaceRacesLoaded + loader.loadAll(spaceRaces,
        Folders.getCustomSpaceRacePath() + "/", files);
    ErrorLogger.log("SpaceRaces loaded: " + spaceRacesLoaded);
  }
}
