package org.openRealmOfStars.player.race;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016-2024 Tuomo Untinen
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

import java.util.ArrayList;

import org.openRealmOfStars.ambient.BridgeCommandType;
import org.openRealmOfStars.audio.music.MusicFileInfo;
import org.openRealmOfStars.player.diplomacy.Attitude;
import org.openRealmOfStars.player.leader.Gender;
import org.openRealmOfStars.player.leader.NameGeneratorType;
import org.openRealmOfStars.player.race.trait.RaceTrait;
import org.openRealmOfStars.player.race.trait.TraitIds;
import org.openRealmOfStars.starMap.planet.enums.GravityType;
import org.openRealmOfStars.starMap.planet.enums.RadiationType;
import org.openRealmOfStars.starMap.planet.enums.TemperatureType;

/**
 * Space race class.
 */
public class SpaceRace {

  /** Space race id */
  private String id;

  /** Space race name in plural */
  private String name;

  /** Space race name in single */
  private String nameSingle;

  /** Attitude for AI */
  private Attitude attitude;

  /** Image path */
  private String imagePath;

  /** Bridge Id. */
  private String bridgeId;

  /** Space Ship Id. */
  private String spaceShipId;

  /** Social system for space race. */
  private SocialSystem socialSystem;

  /** Genders for space race leader. */
  private ArrayList<Gender> genderList;

  /** Speech set id. */
  private String speechSetId;

  /** Space ship bridge effect for HUE lights. */
  private BridgeCommandType bridgeEffect;

  /** Diplomacy music for space race. */
  private MusicFileInfo diplomacyMusic;

  /** Race traits */
  private ArrayList<RaceTrait> traits;

  /** Leader name generator */
  private NameGeneratorType leaderNameGenerator;

  /** Textual description of the space race. This just flavor text. */
  private String description;

  /** Space Race type */
  private SpaceRaceType spaceRaceType;

  /**
   * Constructor for SpaceRace.
   * @param id SpaceRace ID
   * @param name Name in plural
   * @param nameSingle Name in single
   */
  public SpaceRace(final String id, final String name,
      final String nameSingle) {
    this.id = id;
    this.name = name;
    this.nameSingle = nameSingle;
    traits = new ArrayList<>();
    genderList = new ArrayList<>();
    setSpaceRaceType(SpaceRaceType.REGULAR);
  }

  /**
   * Space race Id.
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * Get race name in single format
   * @return Race single name
   */
  public String getNameSingle() {
    return nameSingle;
  }

  /**
   * Get ID for bridge ID. Currently Bridge ID matches space race name.
   * @return Space ship bridge ID.
   */
  public String getBridgeId() {
    return bridgeId;
  }

  /**
   * Setter for bridge Id. SpaceBridges are recognized by original space
   * race names.
   * @param bridgeId Bridge Id
   */
  public void setBridgeId(final String bridgeId) {
    this.bridgeId = bridgeId;
  }

  /**
   * Get Space ship id. Currently space ship ID matches space race name.
   * @return Space ship bridge ID.
   */
  public String getSpaceShipId() {
    return spaceShipId;
  }

  /**
   * Set Space ship id. Currently space ship ID matches space race name.
   * @param spaceShipId Space ship bridge id.
   */
  public void setSpaceShipId(final String spaceShipId) {
    this.spaceShipId = spaceShipId;
  }

  /**
   * Add RaceTrait to SpaceRace.
   * Throws IllegalArgumentException if adding trait would result
   * in conflicting or duplicate traits.
   * @param trait RaceTrait to add to SpaceRace
   */
  public void addTrait(final RaceTrait trait) {
    var traitArray = this.traits.toArray(new RaceTrait[this.traits.size()]);
    if (RaceTrait.isTraitConflict(trait, traitArray)) {
      throw new IllegalArgumentException(
          "Cannot add RaceTrait because of conflict");
    }
    traits.add(trait);
  }

  /**
   * Return true if race has trait specified by ID.
   * @param traitId RaceTrait ID
   * @return True if race has specified trait
   */
  public boolean hasTrait(final String traitId) {
    for (var trait : traits) {
      if (trait.getId().equals(traitId)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Get scientist research speed
   * @return The research speed
   */
  public int getResearchSpeed() {
    int result = 100;
    if (hasTrait(TraitIds.SLOW_RESEARCH)) {
      result = 50;
    }
    if (hasTrait(TraitIds.FAST_RESEARCH)) {
      result = 150;
    }
    if (hasTrait(TraitIds.VERY_FAST_RESEARCH)) {
      result = 200;
    }
    return result;
  }

  /**
   * Get attitude from space race.
   * @return Attitude
   */
  public Attitude getAttitude() {
    return attitude;
  }

  /**
   * SpaceRace default attitude
   * @param attitude Attitude
   */
  public void setAttitude(final Attitude attitude) {
    this.attitude = attitude;
  }

  /**
   * Get race maximum Radiation
   * @return The race maximum radiation
   */
  public RadiationType getMaxRad() {
    RadiationType result = RadiationType.LOW_RADIATION;
    if (hasTrait(TraitIds.TOLERATE_NO_RADIATION)) {
      result = RadiationType.NO_RADIATION;
    }
    if (hasTrait(TraitIds.TOLERATE_HIGH_RADIATION)) {
      result = RadiationType.HIGH_RADIATION;
    }
    if (hasTrait(TraitIds.TOLERATE_EXTREME_RADIATION)) {
      result = RadiationType.VERY_HIGH_RAD;
    }
    return result;
  }

  /**
   * Return path to race's image
   * @return String path to image resource
   */
  public String getImage() {
    return imagePath;
  }

  /**
   * Set space race image path to resources.
   * @param image Image path
   */
  public void setImage(final String image) {
    this.imagePath = image;
  }
  /**
   * Get race picture to wiki page with correct path
   * @return Path to image in master
   */
  private String getRaceImagePathToWiki() {
    String start = "https://github.com/tuomount/Open-Realms-of-Stars/blob"
        + "/master/src/main/resources/";
    return start + this.getImage();
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Get miners mining speed based on gravity.
   * If gravity is null then just return production speed bonus.
   * @param gravity GravityType or null.
   * @return normal 100, half 50, double 200
   */
  public int getMiningSpeed(final GravityType gravity) {
    var result = 100;
    if (hasTrait(TraitIds.STRONG)) {
      result += 50;
    }
    if (hasTrait(TraitIds.WEAK)) {
      result -= 50;
    }
    if (isLithovorian()) {
      result += 50;
    }

    // Return "base" bonus when gravity is not considered
    if (gravity == null) {
      return result;
    }

    if (hasTrait(TraitIds.ZERO_GRAVITY_BEING)) {
      result -= 50;
    }
    if (hasTrait(TraitIds.LOW_GRAVITY_BEING)
        && (gravity == GravityType.NORMAL_GRAVITY
        || gravity == GravityType.HIGH_GRAVITY)) {
      result -= 50;
    }
    if (hasTrait(TraitIds.HIGH_GRAVITY_BEING)
        && (gravity == GravityType.NORMAL_GRAVITY
        || gravity == GravityType.LOW_GRAVITY)) {
      result += 50;
    }
    return result;
  }

  /**
   * Get artists culture speed
   * @return normal 100, half 50, double 200
   */
  public int getCultureSpeed() {
    int result = 100;
    if (hasTrait(TraitIds.SLOW_CULTURE)) {
      result = 50;
    }
    if (hasTrait(TraitIds.FAST_CULTURE)) {
      result = 150;
    }
    return result;
  }

  /**
   * Get Trooper power
   * @return normal 10
   */
  public int getTrooperPower() {
    var result = 10;
    if (hasTrait(TraitIds.STRONG)) {
      result += 2;
    }
    if (hasTrait(TraitIds.HIGH_GRAVITY_BEING)) {
      result += 1;
    }
    if (hasTrait(TraitIds.ZERO_GRAVITY_BEING)) {
      result -= 2;
    }
    if (hasTrait(TraitIds.LOW_GRAVITY_BEING)) {
      result -= 1;
    }
    if (hasTrait(TraitIds.WEAK)) {
      result -= 2;
    }
    if (hasTrait(TraitIds.SLOW_METABOLISM)) {
      result -= 1;
    }
    if (hasTrait(TraitIds.MASSIVE_SIZE)) {
      result += 2;
    }
    return result;
  }

  /**
   * Get worker production speed based on gravity.
   * If gravity is null then just return production speed bonus.
   * @param gravity GravityType or null.
   * @return normal 100, half 50, double 200
   */
  public int getProductionSpeed(final GravityType gravity) {
    var result = 100;
    if (hasTrait(TraitIds.HANDY)) {
      result += 50;
    }
    if (hasTrait(TraitIds.IMPRACTICAL)) {
      result -= 50;
    }
    if (gravity == null) {
      // Just returning production speed bonus
      return result;
    }
    if (hasTrait(TraitIds.ZERO_GRAVITY_BEING)
        && (gravity == GravityType.NORMAL_GRAVITY
        || gravity == GravityType.HIGH_GRAVITY)) {
      result -= 50;
    }
    if (hasTrait(TraitIds.LOW_GRAVITY_BEING)
        && gravity == GravityType.HIGH_GRAVITY) {
      result -= 50;
    }
    if (hasTrait(TraitIds.HIGH_GRAVITY_BEING)
            && gravity == GravityType.LOW_GRAVITY) {
      result += 50;
    }
    return result;
  }

  /**
   * Get population growth speed
   * @return normal 100, half 50, double 200
   */
  public int getGrowthSpeed() {
    int result = 100;
    if (hasTrait(TraitIds.CONSTRUCTED_POP)) {
      result = 0;
    }
    if (hasTrait(TraitIds.SLOW_GROWTH)) {
      result = 50;
    }
    if (hasTrait(TraitIds.FAST_GROWTH)) {
      result = 150;
    }
    return result;
  }

  /**
   * Get Food growth speed based on gravity.
   * If gravity is null then just return production speed bonus
   * @param gravity GravityType or null.
   * @return normal 100, half 50, double 200
   */
  public int getFoodSpeed(final GravityType gravity) {
    int result = 100;
    if (!isEatingFood()) {
      result = 0;
    }
    if (hasTrait(TraitIds.FAST_FOOD_PROD)) {
      result = 200;
    }
    if (gravity == null) {
      // Just returning production speed bonus
      return result;
    }
    if (hasTrait(TraitIds.ZERO_GRAVITY_BEING)
        && gravity == GravityType.HIGH_GRAVITY) {
      result -= 50;
    }
    return result;
  }

  /**
   * Get population food requirement
   * @return normal 100, half 50, double 200
   */
  public int getFoodRequire() {
    int result = 100;
    if (!isEatingFood()) {
      result = 0;
    }
    if (hasTrait(TraitIds.EAT_LESS)) {
      result = result / 2;
    }
    return result;
  }

  /**
   * Get diplomacy bonus for race
   * @return normal 0, +2 positive, -2 negative
   */
  public int getDiplomacyBonus() {
    var result = 0;
    if (hasTrait(TraitIds.NATURAL_CHARM)) {
      result += 2;
    }
    if (hasTrait(TraitIds.REPULSIVE)) {
      result -= 2;
    }
    if (hasTrait(TraitIds.DISGUSTING)) {
      result -= 7;
    }
    return result;
  }

  /**
   * Get war fatigue resistance.
   * Very weak 30,
   * Weak 40,
   * Normal 50,
   * Strong 60,
   * Very Strong 70
   * @return War fatigue resistance
   */
  public int getWarFatigueResistance() {
    int result = 50;
    if (hasTrait(TraitIds.EXCELLENT_WAR_RESILIENCE)) {
      result = 70;
    }
    if (hasTrait(TraitIds.GOOD_WAR_RESILIENCE)) {
      result = 60;
    }
    if (hasTrait(TraitIds.POOR_WAR_RESILIENCE)) {
      result = 40;
    }
    if (hasTrait(TraitIds.WEAK_WAR_RESILIENCE)) {
      result = 30;
    }
    return result;
  }

  /**
   * Is race eating organic food?
   * @return True if eating organic food
   */
  public boolean isEatingFood() {
    if (isLithovorian()) {
      return false;
    }
    if (hasTrait(TraitIds.ENERGY_POWERED)) {
      return false;
    }

    return true;
  }

  /**
   * Is space race eating metal?
   * @return True if eating metal.
   */
  public boolean isLithovorian() {
    return hasTrait(TraitIds.LITHOVORIC);
  }

  /**
   * Is space race robotic race?
   * @return True if robotic.
   */
  public boolean isRoboticRace() {
    return hasTrait(TraitIds.ROBOTIC);
  }

  /**
   * Extra population for each planet.
   * @return Extra population per planet
   */
  public int getExtraPopulation() {
    if (this.hasTrait(TraitIds.SOLITARY)) {
      return -2;
    }
    if (this.hasTrait(TraitIds.COMMUNAL)) {
      return 2;
    }
    return 0;
  }

  /**
   * Get life span of leaders.
   * This is amount the age leaders will always get.
   * After this there is increasing 1% change over this age to get killed.
   * @return Life span of leaders
   */
  public int getLifeSpan() {
    int result = 80;
    if (hasTrait(TraitIds.SHORT_LIFE_SPAN)) {
      result -= 10;
    }
    if (hasTrait(TraitIds.LONG_LIFE_SPAN)) {
      result += 10;
    }
    if (hasTrait(TraitIds.VERY_LONG_LIFE_SPAN)) {
      result += 20;
    }
    if (hasTrait(TraitIds.SLOW_METABOLISM)) {
      result += 10;
    }
    if (hasTrait(TraitIds.CYBORG_LIFE_SPAN)) {
      result += 70;
    }
    if (hasTrait(TraitIds.MASSIVE_SIZE)) {
      result += 20;
    }
    if (hasTrait(TraitIds.ROBOTIC)) {
      result = 2000;
    }
    return result;
  }

  /**
   * Get social system for empires and kingdoms.
   * @return SocialSystem
   */
  public SocialSystem getSocialSystem() {
    return socialSystem;
  }

  /**
   * Set Space race social system
   * @param socialSystem Social System
   */
  public void setSocialSystem(final SocialSystem socialSystem) {
    this.socialSystem = socialSystem;
  }
  /**
   * Get Minimum population for leader on planet.
   * @return Minimum population number.
   */
  public int getMinimumPopulationForLeader() {
    int result = 5;
    if (this.hasTrait(TraitIds.NATURAL_LEADERS)) {
      result--;
    }
    return result;
  }

  /**
   * Get planet's base value based on temperature for space race.
   * This will tell how much of population world type can
   * hold.
   * @param temperature TemperatureType
   * @return Base value between 0 - 100 %.
   */
  public int getTemperatureBaseValue(final TemperatureType temperature) {
    if (temperature == TemperatureType.ARCTIC) {
      int result = 25;
      if (hasTrait(TraitIds.TOLERATE_COLD)) {
        result = result + 25;
      }
      if (hasTrait(TraitIds.TOLERATE_LAVA)) {
        result = result - 25;
      }
      return result;
    }

    if (temperature == TemperatureType.COLD) {
      int result = 50;
      if (hasTrait(TraitIds.TOLERATE_COLD)) {
        result = result + 25;
      }
      if (hasTrait(TraitIds.TOLERATE_LAVA)) {
        result = result - 25;
      }
      return result;
    }

    if (temperature == TemperatureType.TEMPERATE) {
      int result = 100;
      if (hasTrait(TraitIds.TOLERATE_LAVA)) {
        result = result - 25;
      }
      return result;
    }

    if (temperature == TemperatureType.TROPICAL) {
      int result = 75;
      if (hasTrait(TraitIds.TOLERATE_HOT)) {
        result = result + 25;
      }
      if (hasTrait(TraitIds.TOLERATE_LAVA)) {
        result = result + 25;
      }
      return result;
    }

    if (temperature == TemperatureType.HOT) {
      int result = 50;
      if (hasTrait(TraitIds.TOLERATE_HOT)) {
        result = result + 25;
      }
      if (hasTrait(TraitIds.TOLERATE_LAVA)) {
        result = result + 25;
      }
      return result;
    }

    if (temperature == TemperatureType.VOLCANIC) {
      int result = 0;
      if (hasTrait(TraitIds.TOLERATE_LAVA)) {
        result = result + 50;
      }
      return result;
    }
    // Frozen and Inferno are here
    return 0;
  }

  /**
   * Genders the race's leaders can have.
   * @return Array of Genders
   */
  public Gender[] getGenders() {
    return genderList.toArray(new Gender[genderList.size()]);
  }

  /**
   * Add gender to gender list.
   * @param gender Gender
   */
  public void addGender(final Gender gender) {
    genderList.add(gender);
  }

  /**
   * Get ID of SpeechSet this race is using
   * TODO: Move this from SpaceRace to PlayerInfo or "Realm Preset"
   * @return SpeechSet ID
   */
  public String getSpeechSetId() {
    return speechSetId;
  }

  /**
   * Set space race speech set id. This is used in diplomacy view.
   * @param speechSetId Speech set id.
   */
  public void setSpeechSetId(final String speechSetId) {
    this.speechSetId = speechSetId;
  }

  /**
   * Get Racial description text. This should contain only physical attributes
   * and description. Nothing about the government or such.
   * @return String of description.
   */
  public String getRacialDescription() {
    return description;
  }

  /**
   * Set space race description. This is purely for flavor text.
   * @param description Space race description.
   */
  public void setDescription(final String description) {
    this.description = description;
  }

  /**
   * Get full description about the race, including the stats.
   * @param markDown if true then markDown is being used, otherwise HTML.
   * @param image Add image to wiki page if true
   * @return Full description
   */
  public String getFullDescription(final boolean markDown,
      final boolean image) {
    String lf = "<br>";
    String dot = "<li>";
    if (markDown) {
      lf = "\n";
      dot = "*";
    }
    StringBuilder sb = new StringBuilder(100);
    if (!markDown) {
      sb.append("<html>");
    } else {
      sb.append("### ");
    }
    sb.append(name);
    sb.append(lf);
    if (markDown && image) {
      sb.append(lf);
      sb.append("![](");
      sb.append(getRaceImagePathToWiki());
      sb.append(")");
      sb.append(lf);
      sb.append(lf);
    }
    if (!markDown) {
      sb.append("<p>");
    }
    sb.append(getRacialDescription());
    if (!markDown) {
      sb.append("</p>");
    }
    sb.append(lf);
    sb.append(dot);
    sb.append(" Max radiation: ");
    sb.append(getMaxRad());
    sb.append(lf);
    sb.append(dot);
    sb.append(" Troop power: ");
    sb.append(getTrooperPower());
    sb.append(lf);
    sb.append(dot);
    sb.append(" Leader lifespan: ");
    sb.append(getLifeSpan());
    sb.append(lf);
    sb.append(dot);
    sb.append(" Minimum leader population: ");
    sb.append(getMinimumPopulationForLeader());
    sb.append(lf);
    sb.append(dot);
    sb.append(" Growth: ");
    switch (getGrowthSpeed()) {
    case 0: sb.append("never"); break;
    case 50: {
      if (hasTrait(TraitIds.FIXED_GROWTH)) {
        sb.append("always ");
      }
      sb.append("20 star years");
      if (hasTrait(TraitIds.LIMITED_GROWTH)) {
        sb.append(" limited");
      }
      break;
    }
    default:
    case 100: sb.append("10 star years"); break;
    case 150: sb.append("6 star years"); break;
    }

    if (traits.size() > 0) {
      if (!markDown) {
        sb.append("<p>");
      } else {
        sb.append(lf + lf);
      }
      int points = 0;
      sb.append(lf + "Racial attributes (trait points):" + lf);
      for (var trait : traits) {
        sb.append(dot);
        sb.append(" ");
        sb.append(trait.getName());
        points = points + trait.getPoints();
        sb.append(String.format(" (%1$+d)", trait.getPoints()));
        sb.append(" - ");
        sb.append(trait.getDescription());
        sb.append(lf);
      }
      sb.append("\nTotal trait points: ");
      sb.append(points);
      sb.append(lf);
      if (!markDown) {
        sb.append("</p>");
      }
    }

    if (!markDown) {
      sb.append("</html>");
    }
    return sb.toString();
  }

  /**
   * Set Race bridge effect.
   * @param type BridgeCommandType
   */
  public void setRaceBridgeEffect(final BridgeCommandType type) {
    bridgeEffect = type;
  }

  /**
   * Get bridge effect for diplomacy screen.
   * @return BridgeCommandType
   */
  public BridgeCommandType getRaceBridgeEffect() {
    return bridgeEffect;
  }

  /**
   * Get race specific diplomacy music.
   * @return MusicFileInfo
   */
  public MusicFileInfo getRaceDiplomacyMusic() {
    return diplomacyMusic;
  }

  /**
   * Set space race diplomacy music.
   * @param diplomacyMusic Diplomacy music.
   */
  public void setDiplomacyMusic(final MusicFileInfo diplomacyMusic) {
    this.diplomacyMusic = diplomacyMusic;
  }

  /**
   * Get Name Generator type for space race.
   * @return NameGeneratorType Name generator type.
   */
  public NameGeneratorType getNameGeneratorType() {
    return leaderNameGenerator;
  }

  /**
   * Set space race leader name generator typ.
   * @param leaderNameGenerator Namegenerator type.
   */
  public void setLeaderNameGenerator(
      final NameGeneratorType leaderNameGenerator) {
    this.leaderNameGenerator = leaderNameGenerator;
  }

  /**
   * Get SpaceRaceType
   * @return the spaceRaceType
   */
  public SpaceRaceType getSpaceRaceType() {
    return spaceRaceType;
  }

  /**
   * Set SpaceRaceType.
   * @param spaceRaceType the spaceRaceType to set
   */
  public void setSpaceRaceType(final SpaceRaceType spaceRaceType) {
    this.spaceRaceType = spaceRaceType;
  }

  /**
   * Is space race actually space monster or not?
   * @return True if monster
   */
  public boolean isMonster() {
    return this.spaceRaceType == SpaceRaceType.SPACE_MONSTER;
  }

  /**
   * Is space race actually space pirate or not?
   * @return True if pirate
   */
  public boolean isPirate() {
    return this.spaceRaceType == SpaceRaceType.SPACE_PIRATE;
  }

  @Override
  public String toString() {
    return getName();
  }
}
