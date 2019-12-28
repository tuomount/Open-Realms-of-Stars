package org.openRealmOfStars.player.leader;

import org.openRealmOfStars.player.SpaceRace.SpaceRace;

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
   * Leader's experience level
   */
  private int level;

  /**
   * Leaders experience in current level
   */
  private int experience;

  /**
   * Leader's military rank
   */
  private MilitaryRank militaryRank;

  /**
   * Leader's gender. This affects only for leader's title texts.
   */
  private Gender gender;

  /**
   * Leader race
   */
  private SpaceRace race;

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
    age = 28;
    level = 1;
    experience = 0;
    militaryRank = MilitaryRank.CIVILIAN;
    setRace(SpaceRace.HUMAN);
    gender = Gender.NONE;
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

  /**
   * Get military rank for leader
   * @return the militaryRank
   */
  public MilitaryRank getMilitaryRank() {
    return militaryRank;
  }

  /**
   * Set military rank for leader
   * @param militaryRank the militaryRank to set
   */
  public void setMilitaryRank(final MilitaryRank militaryRank) {
    this.militaryRank = militaryRank;
  }

  /**
   * Get level of the leader
   * @return Level
   */
  public int getLevel() {
    return level;
  }

  /**
   * Set level of the leader
   * @param level Leader level
   */
  public void setLevel(final int level) {
    this.level = level;
  }

  /**
   * Get current experience for current level
   * @return Experience
   */
  public int getExperience() {
    return experience;
  }

  /**
   * Set experience for current level
   * @param experience for current level
   */
  public void setExperience(final int experience) {
    this.experience = experience;
  }

  /**
   * Get leader space race
   * @return the race
   */
  public SpaceRace getRace() {
    return race;
  }

  /**
   * Set leader space race.
   * @param race the race to set
   */
  public void setRace(final SpaceRace race) {
    this.race = race;
  }

  /**
   * Get the leader gender.
   * @return the gender
   */
  public Gender getGender() {
    return gender;
  }

  /**
   * Set the leader gender. This will affect only for title that leader
   * receives.
   * @param gender the gender to set
   */
  public void setGender(final Gender gender) {
    this.gender = gender;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Leader [name=");
    builder.append(name);
    builder.append(", homeworld=");
    builder.append(homeworld);
    builder.append(", age=");
    builder.append(age);
    builder.append(", level=");
    builder.append(level);
    builder.append(", experience=");
    builder.append(experience);
    builder.append(", militaryRank=");
    builder.append(militaryRank);
    builder.append(", gender=");
    builder.append(gender);
    builder.append(", race=");
    builder.append(race);
    builder.append(", title=");
    builder.append(title);
    builder.append("]");
    return builder.toString();
  }

}
