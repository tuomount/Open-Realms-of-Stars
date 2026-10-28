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

import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.openRealmOfStars.utilities.ErrorLogger;
import org.openRealmOfStars.utilities.FileIo.DataLoader;

/**
 * Tutorial Loader.
 */
public class TutorialLoader extends DataLoader<String, HelpLine> {

  /**
   * Parse Tutorial line from a JSON file.
   * <p>
   * JSON Tutorial object must have following format:<ul>
   * <li>index : Integer</li>
   * <li>referenceKey : String</li>
   * <li>category : String</li>
   * <li>title: String</li>
   * <li>text : String</li>
   * </ul>
   * </p>
   * @param jobj JSONObject to parse SpaceRace from
   * @return Parsed SpaceRace or empty
   */
  @Override
  protected Optional<HelpLine> parseFromJson(final JSONObject jobj) {
    try {
      final var reference = jobj.getString("referenceKey");
      HelpLine tmp = new HelpLine(reference);
      final int index = jobj.getInt("index");
      tmp.setIndex(index);
      final var category = jobj.getString("category");
      tmp.setCategory(category);
      final var title = jobj.getString("title");
      tmp.setTitle(title);
      final var text = jobj.getString("text");
      tmp.setText(text);
      return Optional.of(tmp);
    } catch (JSONException e) {
      ErrorLogger.log(e);
    }
    return Optional.empty();
  }

  @Override
  protected String valueIdGetter(final HelpLine value) {
    return value.getReference();
  }

  @Override
  protected String typeNameGetter() {
    return "Tutorial line";
  }

}
