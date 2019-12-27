package org.openRealmOfStars.player.leader;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2019  Tuomo Untinen
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
* Leader information
*
*/
public class Leader {

  /**
   * Leader Name, probably surname is enough.
   */
  private String name;

  /**
   * Name of the homeworld.
   */
  private String homeworld;

  /**
   * Leader age in turns.
   */
  private int age;

  /**
   * Highest title.
   * Examples:
   * empty just showing the military rank.
   * Governor of Planet
   * 5th President of Terran Democracy
   * Empire of Centaur Empire
   */
  private String title;

  /**
   * Constructor for leader. Leader must have name.
   * @param name Leader name
   */
  public Leader(final String name) {
    this.name = name;
  }

  /**
   * Get Home world of leader.
   * @return Homeworld name
   */
  public String getHomeworld() {
    return homeworld;
  }

  /**
   * Set homeworld of leader
   * @param homeworld Planet name
   */
  public void setHomeworld(final String homeworld) {
    this.homeworld = homeworld;
  }

  /**
   * Get age of the leader in turns.
   * @return Age in turns
   */
  public int getAge() {
    return age;
  }

  /**
   * Set age of the leader in turns
   * @param age in turns.
   */
  public void setAge(final int age) {
    this.age = age;
  }

  /**
   * Get the highest title for leader
   * @return Highest title for leader
   */
  public String getTitle() {
    return title;
  }

  /**
   * Set the higheest title for leader
   * @param title Highest title for leader
   */
  public void setTitle(final String title) {
    this.title = title;
  }

  /**
   * Get the leader name
   * @return Leader name as String
   */
  public String getName() {
    return name;
  }

}
