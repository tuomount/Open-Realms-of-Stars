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

import java.util.HashMap;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openRealmOfStars.ambient.BridgeCommandType;
import org.openRealmOfStars.audio.music.MusicPlayer;
import org.openRealmOfStars.player.diplomacy.Attitude;
import org.openRealmOfStars.player.leader.Gender;
import org.openRealmOfStars.player.leader.NameGeneratorType;
import org.openRealmOfStars.player.race.trait.TraitFactory;
import org.openRealmOfStars.utilities.DataLoader;
import org.openRealmOfStars.utilities.ErrorLogger;

/** SpaceRace Factory which reads space races from JSON. */
public final class SpaceRaceFactory {

  /** The Singleton */
  private static final SpaceRaceFactory SINGLETON = new SpaceRaceFactory();

  /**
   * Create/Retrieve SpaceRace for given ID, if loaded
   * @param spaceRaceId ID of trait to retrieve
   * @return SpaceRaceClass or empty
   */
  public static Optional<SpaceRaceClass> create(final String spaceRaceId) {
    return SINGLETON.createById(spaceRaceId);
  }

  /** SpaceRace this factory knows. IDs are used as keys. */
  private HashMap<String, SpaceRaceClass> spaceRaces;
  /** Tracks if factory is initialized with data */
  private boolean initialized;
  /** JSON data loader */
  private DataLoader<String, SpaceRaceClass> loader;

  /** Contructor */
  private SpaceRaceFactory() {
    this.spaceRaces = new HashMap<>();
    this.initialized = false;
    this.loader = new SpaceRaceLoader();
  }

  /**
   * Create/Retrieve SpaceRace for given ID, initialize factory if not yet
   * @param spaceRaceId ID of trait to retrieve
   * @return RaceTrait or empty
   */
  private Optional<SpaceRaceClass> createById(final String spaceRaceId) {
    if (!initialized) {
      initialized = true;
      init();
    }

    final var spaceRace = spaceRaces.get(spaceRaceId);
    return Optional.ofNullable(spaceRace);
  }

  /** (Re)Initialize the factory */
  private void init() {
    spaceRaces.clear();
    final var basePath = "resources/data/spaceraces/";
    final String[] files = {
        "humans" };
    final var traitsLoaded = loader.loadAll(spaceRaces, basePath, files);
    ErrorLogger.log("SpaceRaces loaded: " + traitsLoaded);
  }
}

/** SpaceRace loader */
class SpaceRaceLoader extends DataLoader<String, SpaceRaceClass> {

  /**
   * Parse SpaceRace from a JSON file.
   * <p>
   * JSON SpaceRace object must have following format:<ul>
   * <li>ID : String</li>
   * <li>Name : String</li>
   * <li>NameSingle : String</li>
   * <li>BridgeId: String</li>
   * <li>SpaceShipId : String</li>
   * <li>Traits : List of traits (OPTIONAL) </li>
   * <li>Attitude : String</li>
   * <li>Image : String</li>
   * <li>SocialSystem : String</li>
   * <li>Genders : List of genders</li>
   * <li>SpeechSetId : String</li>
   * <li>BridgeEffect : String</li>
   * <li>DiplomacyMusic : String</li>
   * <li>NameGenerator : String</li>
   * <li>Description : String</li>
   * </ul>
   * </p>
   * @param jobj JSONObject to parse SpaceRace from
   * @return Parsed SpaceRace or empty
   */
  @Override
  protected Optional<SpaceRaceClass> parseFromJson(final JSONObject jobj) {
    try {
      final var spaceRaceId = jobj.getString("ID");
      final var name = jobj.getString("Name");
      final var nameSingle = jobj.getString("NameSingle");
      SpaceRaceClass tmp = new SpaceRaceClass(spaceRaceId, name, nameSingle);
      final var bridgeId = jobj.getString("BridgeId");
      tmp.setBridgeId(bridgeId);
      final var spaceShipId = jobj.getString("SpaceShipId");
      tmp.setSpaceShipId(spaceShipId);
      var jsonTraits = jobj.optJSONArray("Traits", new JSONArray());
      for (int i = 0; i < jsonTraits.length(); i++) {
        String traitName = jsonTraits.getString(i);
        TraitFactory.create(traitName).ifPresent(trait -> {
          tmp.addTrait(trait);
        });
      }
      final var attitude = jobj.getString("Attitude");
      tmp.setAttitude(Attitude.getByString(attitude));
      final var image = jobj.getString("Image");
      tmp.setImage(image);
      final var socialSystem = jobj.getString("SocialSystem");
      tmp.setSocialSystem(SocialSystem.getByString(socialSystem));
      var jsonGenders = jobj.optJSONArray("Genders", new JSONArray());
      for (int i = 0; i < jsonGenders.length(); i++) {
        String genderName = jsonGenders.getString(i);
        tmp.addGender(Gender.getByString(genderName));
      }
      final var speechSetId = jobj.getString("SpeechSetId");
      tmp.setSpeechSetId(speechSetId);
      final var bridgeEffectId = jobj.getString("BridgeEffect");
      tmp.setRaceBridgeEffect(BridgeCommandType.getByString(bridgeEffectId));
      final var music = jobj.getString("DiplomacyMusic");
      tmp.setDiplomacyMusic(MusicPlayer.getByString(music));
      final var nameGeneratorType = jobj.getString("NameGenerator");
      tmp.setLeaderNameGenerator(
          NameGeneratorType.getByString(nameGeneratorType));
      final var description = jobj.getString("Description");
      tmp.setDescription(description);
      return Optional.of(tmp);
    } catch (JSONException e) {
      ErrorLogger.log(e);
    }

    return Optional.empty();
  }

  @Override
  protected String valueIdGetter(final SpaceRaceClass value) {
    return value.getId();
  }

  @Override
  protected String typeNameGetter() {
    return "SpaceRace";
  }
}
