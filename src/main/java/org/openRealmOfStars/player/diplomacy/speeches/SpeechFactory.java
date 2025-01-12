package org.openRealmOfStars.player.diplomacy.speeches;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2017-2023 Tuomo Untinen
 * Copyright (C) 2024 BottledByte
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

import org.json.JSONException;
import org.json.JSONObject;
import org.openRealmOfStars.player.race.SpaceRace;
import org.openRealmOfStars.utilities.DiceGenerator;
import org.openRealmOfStars.utilities.ErrorLogger;
import org.openRealmOfStars.utilities.FileIo.DataLoader;
import org.openRealmOfStars.utilities.FileIo.DataSources;
import org.openRealmOfStars.utilities.FileIo.Folders;

/**
 * Factory for creating {@link SpeechLine}s
 */
public final class SpeechFactory {
  /** The Singleton */
  private static final SpeechFactory SINGLETON = new SpeechFactory();

  /** String for suggesting trade embargo */
  public static final String SUGGEST_EMBARGO = "Suggest trade embargo (more)";

  /** Map of SpeechSets by their IDs */
  private HashMap<String, SpeechSet> speechSets;
  /** Tracks if factory is initialized with data */
  private boolean initialized;
  /** JSON data loader */
  private DataLoader<String, SpeechSet> loader;

  /** Constructor */
  private SpeechFactory() {
    this.speechSets = new HashMap<>();
    this.initialized = false;
    this.loader = new SpeechSetLoader();
  }

  /**
   * Create line for suggesting trade embargo, this is only
   * for human UI.
   * @return Speechline for suggesting trade embargo
   */
  public static SpeechLine createEmbargoSuggestion() {
    SpeechType type = SpeechType.TRADE_EMBARGO;
    return new SpeechLine(type, SUGGEST_EMBARGO);
  }

  /**
   * Create SpeechLine of given type from given SpeechSet,
   * return generic text if not in SpeechSet
   * @param type SpeechType
   * @param race SpaceRace
   * @param dynamicContent Dynamic content used in lines,
   *        for example fleet name to move
   * @return SpeechLine
   */
  public static SpeechLine createLine(final SpeechType type,
      final SpaceRace race, final String dynamicContent) {
    String tmp = dynamicContent;
    if (dynamicContent == null) {
      tmp = "";
    }
    return SINGLETON.create(type, race.getSpeechSetId(), tmp);
  }

  /**
   * Get Speeches
   * @return Array of speeches ID.
   */
  public static String[] getAllIds() {
    if (!SINGLETON.initialized) {
      SINGLETON.init();
      SINGLETON.initialized = true;
    }
    return SINGLETON.speechSets.keySet().toArray(new String[0]);
  }

  /**
   * Restart factory and reload everything again when needed.
   */
  public static void restartFactory() {
    SINGLETON.initialized = false;
    SINGLETON.speechSets.clear();
  }

  /**
   * Create SpeechLine of given type from given SpeechSet,
   * return generic text if not in SpeechSet
   * @param type SpeechType
   * @param ssetId SpeechSet ID
   * @param dynamicContent Dynamic content used in lines,
   *        for example fleet name to move
   * @return SpeechLine
   */
  public SpeechLine create(final SpeechType type, final String ssetId,
      final String dynamicContent) {
    if (!initialized) {
      initialized = true;
      init();
    }

    if (!speechSets.containsKey(ssetId)) {
      return new SpeechLine(type, type.toString());
    }
    var data = speechSets.get(ssetId).getSpeechData(type);
    if (data.isEmpty()) {
      return new SpeechLine(type, type.toString());
    }
    var text = DiceGenerator.pickRandom(data);
    text = String.format(text, dynamicContent);
    return new SpeechLine(type, text);
  }

  /** (Re)Initialize the factory */
  private void init() {
    speechSets.clear();
    final var basePath = "resources/data/speech/";
    String[] files = {
        "alteirians", "centaurs", "chiraloids", "greyans",
        "homarians", "human", "lithorians", "mechions",
        "mothoids", "reborgians", "scaurians",
        "smaugirians", "space_pirate", "sporks",
        "synthdroids", "teuthidaes", "fernids", "dwarf" };
    int speechesLoaded = loader.loadAll(speechSets, basePath, files);
    files = DataSources.findJsonFilesInPath(Folders.getCustomSpeeches());
    speechesLoaded = speechesLoaded + loader.loadAll(speechSets,
        Folders.getCustomSpeeches() + "/", files);
    ErrorLogger.log("SpeechSets loaded: " + speechesLoaded);
  }

}

/** SpeechSet loader */
class SpeechSetLoader extends DataLoader<String, SpeechSet> {

  @Override
  protected Optional<SpeechSet> parseFromJson(final JSONObject jobj) {
    try {
      final var ssetId = jobj.getString("id");
      final var data = jobj.getJSONObject("data");

      var tmpSpeechSet = new SpeechSet(ssetId);
      for (var speechTypeId : data.keySet()) {
        var jarr = data.getJSONArray(speechTypeId);
        var list = new ArrayList<String>();
        for (var entry : jarr) {
          list.add(entry.toString());
        }
        tmpSpeechSet.setSpeechData(SpeechType.valueOf(speechTypeId), list);
      }

      return Optional.of(tmpSpeechSet);
    } catch (JSONException e) {
      ErrorLogger.log(e);
    }

    return Optional.empty();
  }

  @Override
  protected String valueIdGetter(final SpeechSet value) {
    return value.getId();
  }

  @Override
  protected String typeNameGetter() {
    return "SpeechSet";
  }
}