package org.openRealmOfStars.player.diplomacy.speeches;
/*
 * Open Realm of Stars game project
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
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

/**
 * SpeechSet contains raw speech data identified by {@link SpeechType}.
 *
 * There can be zero to N raw strings for each SpeechType,
 * in which case one string should selected randomly.
 */
public class SpeechSet {
  /** ID of the SpeechSet */
  private String spSetId;
  /** Raw speech data by speech type */
  private EnumMap<SpeechType, List<String>> speechData;

  /**
   * Creates new SpeechSet
   * @param id String
   */
  SpeechSet(final String id) {
    this.spSetId = id;
    this.speechData = new EnumMap<>(SpeechType.class);
  }

  /**
   * @return the ID
   */
  public String getId() {
    return spSetId;
  }

  /**
   * Return raw speeches of given type
   * @param type SpeechType
   * @return List<String>
   */
  public List<String> getSpeechData(final SpeechType type) {
    return speechData.getOrDefault(type, Collections.emptyList());
  }

  /**
   * Set raw speech data for given type
   * @param type SpeechType
   * @param data List<String>
   */
  void setSpeechData(final SpeechType type, final List<String> data) {
    speechData.put(type, new ArrayList<>(data));
  }
}
