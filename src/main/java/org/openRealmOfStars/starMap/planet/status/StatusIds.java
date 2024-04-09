package org.openRealmOfStars.starMap.planet.status;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2023 BottledByte
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

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class containing hardcoded IDs of {@link PlanetaryStatus}es,
 * that are used in game code.
 *
 * <p>
 * When PlanetaryStatus dependency arises, it's ID
 * should be immediately written here as a constant.
 * There is a method that allows retrieval of all hardcoded IDs.
 * This method can be used for sanity checks at data-loading, etc.
 * </p>
 *
 * This class relies on Reflection for simple, error-free
 * addition of new IDs and their easy use in code,
 * while allowing listing of all hardcoded IDs.
 * TODO: Use code generation instead, the reflection is not needed for this
 *
 */
public final class StatusIds {

  /** Planet surface is metal rich surface. Cannot combine with
   * FERTILE_SOIL or  MOLTEN_LAVA */
  public static final String METAL_RICH_SURFACE = "METAL_RICH_SURFACE";

  /** Planet surface has fertile soil. Cannot combine with
   * METAL_RICH_SURFACE or  MOLTEN_LAVA */
  public static final String FERTILE_SOIL = "FERTILE_SOIL";

  /** Planet has molten lava. Cannot combine with
   * METAL_RICH_SURFACE or  FERTILE_SOIL */
  public static final String MOLTEN_LAVA = "MOLTEN_LAVA";

  /** Planet has tectonic quakes. */
  public static final String TECTONIC_QUAKE = "TECTONIC_QUAKE";

  /** Planet has precious gems on surface. */
  public static final String PRECIOUS_GEMS = "PRECIOUS_GEMS";

  /** Planet has hidden pirate base and they will control planet
   *  at some point. */
  public static final String PIRATE_WORLD = "PIRATE_WORLD";

  /** List storing all hardcoded IDs. Populated at runtime, via reflection. */
  private static List<String> hardcodedIds = null;

  /**
   * Get List of all hardcoded IDs
   * @return List of all hardcoded IDs
   */
  public static List<String> getHardcodedIds() {
    if (hardcodedIds != null) {
      return hardcodedIds;
    }

    // Use class reflection to get all public static
    // fields of this class, then cache the result.
    final var idList = Arrays.stream(StatusIds.class.getDeclaredFields())
        .filter(field -> Modifier.isPublic(field.getModifiers()))
        .filter(field -> Modifier.isStatic(field.getModifiers()))
        .map(field -> field.getName())
        .collect(Collectors.toList());
    hardcodedIds = idList;
    return idList;
  }

  /** Hidden constructor */
  private StatusIds() {
  }
}
