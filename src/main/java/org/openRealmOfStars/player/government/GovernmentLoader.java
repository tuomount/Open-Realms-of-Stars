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

import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openRealmOfStars.player.government.trait.GovTraitFactory;
import org.openRealmOfStars.utilities.DataLoader;
import org.openRealmOfStars.utilities.ErrorLogger;

/** Government loader */
public class GovernmentLoader extends DataLoader<String, Government> {

  /**
   * Parse Government from a JSON file.
   * <p>
   * JSON Government object must have following format:<ul>
   * <li>ID : String</li>
   * <li>Name : String</li>
   * <li>RulerSelection : String</li>
   * <li>RulerTitleMale : String (OPTINAL)</li>
   * <li>RulerTitleFemale : String (OPTINAL)</li>
   * <li>Traits : List of traits (OPTIONAL) </li>
   * </ul>
   * </p>
   * @param jobj JSONObject to parse Government from
   * @return Parsed Government or empty
   */
  @Override
  public Optional<Government> parseFromJson(final JSONObject jobj) {
    try {
      final var spaceRaceId = jobj.getString("ID");
      final var name = jobj.getString("Name");
      final var rulerSelection = jobj.getString("RulerSelection");
      final var rulerTitleMale = jobj.optString("RulerTitleMale", "President");
      final var rulerTitleFemale = jobj.optString("RulerTitleFemale",
          "President");
      Government tmp = new Government(spaceRaceId, name);
      tmp.setRulerSelection(RulerSelection.getByString(rulerSelection));
      tmp.setRulerTitleMale(rulerTitleMale);
      tmp.setRulerTitleFemale(rulerTitleFemale);
      var jsonTraits = jobj.optJSONArray("Traits", new JSONArray());
      for (int i = 0; i < jsonTraits.length(); i++) {
        String traitName = jsonTraits.getString(i);
        GovTraitFactory.create(traitName).ifPresent(trait -> {
          tmp.addTrait(trait);
        });
      }
      return Optional.of(tmp);
    } catch (JSONException e) {
      ErrorLogger.log(e);
    }

    return Optional.empty();
  }

  @Override
  protected String valueIdGetter(final Government value) {
    return value.getId();
  }

  @Override
  protected String typeNameGetter() {
    return "Government";
  }
}

