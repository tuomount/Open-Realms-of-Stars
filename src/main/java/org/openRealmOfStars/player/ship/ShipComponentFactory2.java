package org.openRealmOfStars.player.ship;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2025 Tuomo Untinen
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

import org.json.JSONException;
import org.json.JSONObject;
import org.openRealmOfStars.utilities.ErrorLogger;
import org.openRealmOfStars.utilities.FileIo.DataLoader;

/**
 * Ship component factory
 */
public final class ShipComponentFactory2 {

  /** The Singleton */
  private static final ShipComponentFactory2 SINGLETON =
      new ShipComponentFactory2();

  /** Map of all building definitons, with IDs as keys */
  private HashMap<String, ShipComponent> shipComponents;
  /** Tracks if the factory is initialized with data */
  private boolean initialized;
  /** JSON data loader */
  private DataLoader<String, ShipComponent> loader;

  /** Constructor */
  private ShipComponentFactory2() {
    this.shipComponents = new HashMap<>();
    this.initialized = false;
    this.loader = new ShipComponentLoader();
  }

  /**
   * Create ShipComponent with specified name
   * @param name Building name
   * @return ShipComponent or null if not found
   */
  public static ShipComponent createByName(final String name) {
    return SINGLETON.createByNameImpl(name).orElse(null);
  }

  /**
   * Create/Retrieve ShipComponent with given name,
   * initialize factory if not yet initialized.
   *
   * @param name Name of ShipComponent to retrieve
   * @return ShipComponent or empty
   */
  private Optional<ShipComponent> createByNameImpl(final String name) {
    if (!initialized) {
      init();
      this.initialized = true;
    }

    final var cachedTrait = shipComponents.get(name);
    return Optional.ofNullable(cachedTrait);
  }

  /** Initialize the factory */
  private void init() {
    final var dataFilesBase = "resources/data/ship_component/";
    final String[] dataFiles = {
        "weapons", "defense", "hulls", "improvements", "propulsion",
        "electronics"};

    var loadedCount = loader.loadAll(shipComponents, dataFilesBase, dataFiles);
    ErrorLogger.log("Ship Components loaded: " + loadedCount);
  }
  /** ShipComponent JSON data loader */
  class ShipComponentLoader extends DataLoader<String, ShipComponent> {

    @Override
    protected Optional<ShipComponent> parseFromJson(final JSONObject jobj) {
      try {
        // Mandatory fields
        final var type = jobj.getEnum(ShipComponentType.class, "type");
        final var name = jobj.getString("name");
        final var cost = jobj.getInt("cost");
        final var metalCost = jobj.getInt("metalCost");

        var tmp = new ShipComponent(name, cost, metalCost, type);
        // Optional fields
        tmp.setEnergyRequirement(jobj.optInt("energyRequirement", 0));
        tmp.setEnergyResource(jobj.optInt("energySource", 0));
        tmp.setFtlSpeed(jobj.optInt("ftlSpeed", 0));
        tmp.setBaySize(jobj.optInt("baySize", 0));
        tmp.setSpeed(jobj.optInt("speed", 0));
        tmp.setTacticSpeed(jobj.optInt("tacticSpeed", 0));
        tmp.setScannerRange(jobj.optInt("scannerRange", 0));
        tmp.setCloakDetection(jobj.optInt("cloakDetection", 0));
        tmp.setCloaking(jobj.optInt("cloaking", 0));
        tmp.setDefenseValue(jobj.optInt("defense", 0));
        tmp.setDamage(jobj.optInt("damage", 0));
        tmp.setInitiativeBoost(jobj.optInt("initiativeBoost", 0));
        tmp.setWeaponRange(jobj.optInt("weaponRange", 0));
        tmp.setResearchBonus(jobj.optInt("researchBonus", 0));
        tmp.setCreditBonus(jobj.optInt("creditBonus", 0));
        tmp.setCultureBonus(jobj.optInt("cultureBonus", 0));
        tmp.setEspionageBonus(jobj.optInt("espionageBonus", 0));
        tmp.setHitBonus(jobj.optInt("hitBonus", 0));
        tmp.setFleetCapacityBonus(jobj.optInt("fleetCapacityBonus", 0));

        return Optional.of(tmp);
      } catch (JSONException e) {
        ErrorLogger.log(e);
      }

      return Optional.empty();
    }

    @Override
    protected String valueIdGetter(final ShipComponent value) {
      return value.getName();
    }

    @Override
    protected String typeNameGetter() {
      return "Building";
    }
  }
}
