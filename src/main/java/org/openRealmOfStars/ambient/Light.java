package org.openRealmOfStars.ambient;

import org.openRealmOfStars.utilities.json.values.ObjectValue;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2020 Tuomo Untinen
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
*
*
* Single light on bridge.
*
*/
public class Light {

  /**
   * Light name, actually the number.
   */
  private String name;

  /**
   * Human readable name for the light.
   */
  private String humanReadablename;
  /**
   * Is light on?
   */
  private boolean on;

  /**
   * Brightness of the light.
   */
  private int bri;

  /**
   * Saturation of the light.
   */
  private int sat;

  /**
   * Hue of the light.
   */
  private int hue;

  /**
   * Has lamp updated?
   */
  private boolean updated;
  /**
   * Constructor for the light.
   * @param name Light name.
   */
  public Light(final String name) {
    this.name = name;
    on = false;
    bri = 0;
    sat = 0;
    hue = 0;
    humanReadablename = "not set";
    updated = false;
  }

  /**
   * Get the lamp's human readable name.
   * @return the humanReadablename
   */
  public String getHumanReadablename() {
    return humanReadablename;
  }

  /**
   * Set lamp's human readable name.
   * @param humanReadablename the humanReadablename to set
   */
  public void setHumanReadablename(final String humanReadablename) {
    this.humanReadablename = humanReadablename;
  }

  /**
   * Is lamp on? Last know value.
   * @return the on
   */
  public boolean getOn() {
    return on;
  }

  /**
   * Set lamp on or off.
   * @param on the on to set
   */
  public void setOn(final boolean on) {
    if (on != this.on) {
      this.on = on;
      updated = true;
    }
  }

  /**
   * Get lamp's current known brightnees.
   * @return the bri
   */
  public int getBri() {
    return bri;
  }

  /**
   * Set lamp's brightness
   * @param bri the bri to set
   */
  public void setBri(final int bri) {
    if (bri >= 0 && bri < 256 && bri != this.bri) {
      this.bri = bri;
      updated = true;
    }
  }

  /**
   * Get lamps' current known saturation.
   * @return the sat
   */
  public int getSat() {
    return sat;
  }

  /**
   * Set lamp's saturation.
   * @param sat the sat to set
   */
  public void setSat(final int sat) {
    if (sat >= 0 && sat < 256 && sat != this.sat) {
      updated = true;
      this.sat = sat;
    }
  }

  /**
   * Get the lamp's known hue value.
   * @return the hue
   */
  public int getHue() {
    return hue;
  }

  /**
   * Set lamp's new hue value.
   * @param hue the hue to set
   */
  public void setHue(final int hue) {
    if (hue >= 0 && hue < 65536 && hue != this.hue) {
      updated = true;
      this.hue = hue;
    }
  }

  /**
   * Get lamp's number or "name"
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Update Lamp's JSON.
   * This can be used for updating the lamp at bridge.
   * @return Lamp's JSON.
   */
  public ObjectValue updateLampJson() {
    ObjectValue value = new ObjectValue();
    if (updated) {
      value.addBooleanMember("on", getOn());
      value.addIntegerMember("bri", getBri());
      value.addIntegerMember("hue", getHue());
      value.addIntegerMember("sat", getSat());
      updated = false;
    }
    return value;
  }
}
