package org.openRealmOfStars.gui.panels;

import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.SpaceRace.SpaceRaceUtility;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016,2017 Tuomo Untinen
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
 * Race image panel of player setup
 *
 */

public class RaceImagePanel extends ImagePanel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;


  /**
   * Maximum width
   */
  private static final int MAXX = 172;

  /**
   * Maximum height
   */
  private static final int MAXY = 200;

  /**
   * Which race to show
   */
  private String raceToShow;

  /**
   * Create picture frame for player setup
   */
  public RaceImagePanel() {
    super(MAXX, MAXY);
    raceToShow = "Not in use";
  }

  /**
   * Get Race to show.
   *
   * @return Race to show
   */
  public String getRaceToShow() {
    return raceToShow;
  }

  /**
   * Set Race to show.
   *
   * @param raceName Race to show
   */
  public void setRaceToShow(final String raceName) {
    if (raceName != null) {
      raceToShow = raceName;
      SpaceRace race = SpaceRaceUtility.getRaceByName(raceToShow);
      if (race == null) {
        setText(raceToShow);
      } else {
        setText(null);
        setImage(race.getRaceImage());
      }
      repaint();
    } else {
      raceToShow = "Not in use";
      setText("Not in use");
      repaint();
    }
  }

}
