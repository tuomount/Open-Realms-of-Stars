package org.openRealmOfStars.gui.util;
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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.UIManager;

import org.openRealmOfStars.game.Game;
import org.openRealmOfStars.gui.icons.AnimatedImage;
import org.openRealmOfStars.gui.icons.Icon16x16;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.scheme.BaseScheme;
import org.openRealmOfStars.gui.scheme.ClassicScheme;
import org.openRealmOfStars.gui.scheme.GreyScheme;
import org.openRealmOfStars.gui.scheme.SchemeType;
import org.openRealmOfStars.gui.scheme.YellowScheme;
import org.openRealmOfStars.gui.scrollPanel.SpaceScrollBarUI;
import org.openRealmOfStars.player.race.SpaceRace;
import org.openRealmOfStars.utilities.FileIo.Folders;
import org.openRealmOfStars.utilities.FileIo.IOUtilities;

/**
 *
 * Static GUI components like images and colors.
 *
 */
public final class GuiStatics {

  /** Hiding the constructor for utility class */
  private GuiStatics() {
    // Nothing to do
  }

  /** Classic scheme */
  public static final BaseScheme CLASSIC_SCHEME = new ClassicScheme();
  /** Space grey scheme */
  public static final BaseScheme GREY_SCHEME = new GreyScheme();
  /** Dangerous Yellow scheme */
  public static final BaseScheme YELLOW_SCHEME = new YellowScheme();
  /** Scheme selection for UI. */
  private static BaseScheme schemeType = CLASSIC_SCHEME;

  /** Text field height in pixels. */
  public static final int TEXT_FIELD_HEIGHT = 30;

  /** Line type for text background */
  public static final Stroke TEXT_LINE = new BasicStroke(12,
      BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1, new float[] {1f }, 0);

  /** Sun Line type for text background, opacity 230. */
  public static final Color COLOR_GOLD_TRANS = new Color(210, 181, 44, 230);

  /** Gold color */
  public static final Color COLOR_GOLD = new Color(210, 181, 44);

  /** Space yellow */
  public static final Color COLOR_SPACE_YELLOW = new Color(251, 255, 51);

  /** Bright white */
  public static final Color COLOR_BRIGHT_WHITE = new Color(241, 255, 241, 128);
  /** Transparent black */
  public static final Color COLOR_TRANSPARENT_GREY = new Color(40, 40, 40, 128);
  /** Transparent black */
  public static final Color COLOR_TRANSPARENT_BLACK = new Color(5, 5, 5, 128);

  /** Space grey */
  public static final Color COLOR_SPACE_GREY = new Color(180, 180, 221);

  /** Dark space yellow */
  public static final Color COLOR_SPACE_YELLOW_DARK = new Color(134, 134, 33);

  /** Dark Gold color */
  public static final Color COLOR_GOLD_DARK = new Color(155, 130, 13);

  /** Planet Line type for text background, opacity 65. */
  public static final Color COLOR_GREYBLUE = new Color(180, 180, 200, 65);
  /** Planet Line type for text background. */
  public static final Color COLOR_GREYBLUE_NO_OPAGUE =
      new Color(180, 180, 200);

  /** Green text */
  public static final Color COLOR_GREEN_TEXT = new Color(4, 186, 0, 255);

  /** Green text */
  public static final Color COLOR_GREEN_TEXT_DARK = new Color(2, 102, 0, 255);

  /** Grey text dark one */
  public static final Color COLOR_GREY_TEXT_DARK = new Color(90, 95, 90);

  /** Grey text */
  public static final Color COLOR_GREY_TEXT = new Color(160, 165, 160);

  /** Red text */
  public static final Color COLOR_RED_TEXT = new Color(186, 4, 0, 255);
  /** Red text Dark */
  public static final Color COLOR_RED_TEXT_DARK = new Color(93, 2, 0, 255);

  /** Yellow text */
  public static final Color COLOR_YELLOW_TEXT = new Color(220, 220, 4, 255);

  /** Yellow text Dark */
  public static final Color COLOR_YELLOW_TEXT_DARK = new Color(110, 110, 2,
      255);

  /** Damage 3/4 */
  public static final Color COLOR_DAMAGE_LITTLE = new Color(177, 255, 11, 255);

  /** Damage half */
  public static final Color COLOR_DAMAGE_HALF = new Color(252, 255, 11, 255);

  /** Damage MUCH */
  public static final Color COLOR_DAMAGE_MUCH = new Color(255, 189, 11, 255);

  /** Damage almost destroyed */
  public static final Color COLOR_DAMAGE_ALMOST_DESTROYED = new Color(255, 143,
      11, 255);

  /** Weapon Range Max */
  public static final Color COLOR_WEAPON_RANGE_MAX = new Color(255, 45,
      11, 64);
  /** Weapon Range Min */
  public static final Color COLOR_WEAPON_RANGE_MIN = new Color(253, 248,
      110, 64);

  /** Damage destroyed */
  public static final Color COLOR_DESTROYED = new Color(255, 28, 11, 255);

  /** Dark grey, opacity 128 */
  public static final Color COLOR_VERY_DARK_GREY_TRANS = new Color(20, 20,
      20, 128);

  /** Grey 160 */
  public static final Color COLOR_GREY_160 = new Color(160, 160, 160);

  /** Grey 80 */
  public static final Color COLOR_GREY_80 = new Color(80, 80, 80);

  /** Grey 40 */
  public static final Color COLOR_GREY_40 = new Color(40, 40, 40);

  /** First color for explosion */
  private static final Color EXPLOSION_COLOR_1 = new Color(255, 196, 18);
  /** Second color for explosion */
  private static final Color EXPLOSION_COLOR_2 = new Color(244, 101, 14);
  /** Third color for explosion */
  private static final Color EXPLOSION_COLOR_3 = new Color(255, 218, 72);
  /** Fourth color for explosion */
  private static final Color EXPLOSION_COLOR_4 = new Color(241, 223, 17);
  /** Fifth color for explosion */
  private static final Color EXPLOSION_COLOR_5 = new Color(255, 133, 13);

  /** Explosion colors */
  public static final Color[] EXPLOSION_COLORS = {EXPLOSION_COLOR_1,
      EXPLOSION_COLOR_2, EXPLOSION_COLOR_3, EXPLOSION_COLOR_4,
      EXPLOSION_COLOR_5 };

  /** First color for red beam */
  private static final Color BEAM_COLOR_1 = new Color(255, 36, 0);
  /** Second color for red beam */
  private static final Color BEAM_COLOR_2 = new Color(255, 96, 0);
  /** Third color for red beam */
  private static final Color BEAM_COLOR_3 = new Color(255, 128, 0);
  /** Fourth color for red beam */
  private static final Color BEAM_COLOR_4 = new Color(255, 18, 0);
  /** Fifth color for red beam */
  private static final Color BEAM_COLOR_5 = new Color(255, 115, 77);

  /** Red beam colors */
  public static final Color[] BEAM_COLORS = {BEAM_COLOR_1, BEAM_COLOR_2,
      BEAM_COLOR_3, BEAM_COLOR_4, BEAM_COLOR_5, };

  /** Base color for tentacle. */
  public static final Color TENTACLE_COLOR_1 = new Color(65, 50, 44);
  /** Second color for tentacle. */
  public static final Color TENTACLE_COLOR_2 = new Color(88, 73, 52);
  /** Base color for arm spike. */
  public static final Color SPIKE_COLOR_1 = new Color(161, 135, 123);
  /** Second color for arm spike. */
  public static final Color SPIKE_COLOR_2 = new Color(90, 87, 86);
  /** Green beam colors */
  public static final Color[] GREEN_BEAM_COLORS = {
      new Color(36, 255,  0),
      new Color(96, 255, 0),
      new Color(128, 255, 0),
      new Color(18, 255, 0),
      new Color(155, 255, 77)};

  /** Blue beam colors */
  public static final Color[] BLUE_BEAM_COLORS = {
      new Color(36, 0,  255),
      new Color(96, 0, 255),
      new Color(128, 0, 255),
      new Color(18, 18, 255),
      new Color(155, 77, 255)};

  /** Player color red. */
  public static final Color PLAYER_RED = new Color(215, 30, 34);
  /** Player color blue. */
  public static final Color PLAYER_BLUE = new Color(29, 60, 233);
  /** Player color green. */
  public static final Color PLAYER_GREEN = new Color(27, 145, 62);
  /** Player color pink. */
  public static final Color PLAYER_PINK = new Color(237, 84, 186);
  /** Player color orange. */
  public static final Color PLAYER_ORANGE = new Color(255, 148, 28);
  /** Player color yellow. */
  public static final Color PLAYER_YELLOW = new Color(255, 255, 103);
  /** Player color black. */
  public static final Color PLAYER_BLACK = new Color(63, 71, 78);
  /** Player color white. */
  public static final Color PLAYER_WHITE = new Color(214, 224, 240);
  /** Player color purple. */
  public static final Color PLAYER_PURPLE = new Color(107, 49, 188);
  /** Player color brown. */
  public static final Color PLAYER_BROWN = new Color(113, 73, 30);
  /** Player color cyan. */
  public static final Color PLAYER_CYAN = new Color(68, 253, 245);
  /** Player color lime. */
  public static final Color PLAYER_LIME = new Color(80, 239, 57);
  /** Player color chestnut. */
  public static final Color PLAYER_CHESTNUT = new Color(115, 27, 19);
  /** Player color rose. */
  public static final Color PLAYER_ROSE = new Color(236, 192, 211);
  /** Player color banana. */
  public static final Color PLAYER_BANANA = new Color(255, 253, 190);
  /** Player color gray. */
  public static final Color PLAYER_GRAY = new Color(112, 132, 151);
  /** Player color tan. */
  public static final Color PLAYER_TAN = new Color(146, 135, 118);
  /** Player color coral. */
  public static final Color PLAYER_CORAL = new Color(236, 117, 120);

  /** Player color olive. */
  public static final Color PLAYER_OLIVE = new Color(97, 114, 24);

  /** Player color sky. */
  public static final Color PLAYER_SKY = new Color(110, 127, 217);

  /** Relation unknown icon */
  public static final BufferedImage RELATION_UNKNOWN = Icons.loadBigIcon(
      "/resources/images/bigicons.png", 0, 0, 32, 32);
  /** Relation peace icon */
  public static final BufferedImage RELATION_PEACE = Icons.loadBigIcon(
      "/resources/images/bigicons.png", 32, 0, 32, 32);
  /** Relation war icon */
  public static final BufferedImage RELATION_WAR = Icons.loadBigIcon(
      "/resources/images/bigicons.png", 64, 0, 32, 32);
  /** Relation trade alliance icon */
  public static final BufferedImage RELATION_TRADE_ALLIANCE = Icons.loadBigIcon(
      "/resources/images/bigicons.png", 96, 0, 32, 32);
  /** Relation trade embargo icon */
  public static final BufferedImage RELATION_TRADE_EMBARGO = Icons.loadBigIcon(
      "/resources/images/bigicons.png", 96, 32, 32, 32);
  /** Relation alliance icon */
  public static final BufferedImage RELATION_ALLIANCE = Icons.loadBigIcon(
      "/resources/images/bigicons.png", 64, 32, 32, 32);
  /** Defensive Pact icon */
  public static final BufferedImage DEFENSIVE_PACT = Icons.loadBigIcon(
      "/resources/images/bigicons.png", 32, 32, 32, 32);
  /** Photon torpedo */
  public static final BufferedImage PHOTON_TORPEDO = IOUtilities
      .loadImage("/resources/images/photon_torp.png");
  /** Plasma bullet */
  public static final BufferedImage PLASMA_BULLET = IOUtilities
      .loadImage("/resources/images/plasma.png");

  /** Explosion animation */
  public static final AnimatedImage EXPLOSION1 = new AnimatedImage(64, 64,
      "/resources/images/explosion1.png");

  /** Blue explosion animation */
  public static final AnimatedImage EXPLOSION2 = new AnimatedImage(64, 64,
      "/resources/images/explosion2.png");

  /** Small explosion animation */
  public static final AnimatedImage EXPLOSION3 = new AnimatedImage(32, 32,
      "/resources/images/explosion3.png");

  /** Nuke animation */
  public static final AnimatedImage EXPLOSION4 = new AnimatedImage(64, 64,
      "/resources/images/explosion4.png");

  /** Ground combat animation */
  public static final AnimatedImage GROUND_COMBAT = new AnimatedImage(64, 64,
      "/resources/images/ground_combat.png");

  /** Spore attack animation */
  public static final AnimatedImage SPORE_ATTACK = new AnimatedImage(64, 64,
      "/resources/images/spore_attack.png");

  /** Gravity ripper animation */
  public static final AnimatedImage GRAVITY_RIPPER = new AnimatedImage(64, 64,
      "/resources/images/gravity_ripper.png");

  /** Lighting animation */
  public static final AnimatedImage LIGHTNING = new AnimatedImage(64, 64,
      "/resources/images/lightning.png");

  /** Shield animation */
  public static final AnimatedImage SHIELD1 = new AnimatedImage(64, 64,
      "/resources/images/shield.png");

  /** Scanner animation */
  public static final AnimatedImage SCANNER = new AnimatedImage(64, 64,
      "/resources/images/scanner.png");

  /** Privateering animation */
  public static final AnimatedImage PRIVATEER = new AnimatedImage(64, 64,
      "/resources/images/privateer.png");

  /** Bite animation */
  public static final AnimatedImage BITE = new AnimatedImage(64, 64,
      "/resources/images/bite.png");

  /** Wormhole animation */
  public static final AnimatedImage WORMHOLE = new AnimatedImage(64, 64,
      "/resources/images/wormhole.png");

  /** Crosshair for combat */
  public static final BufferedImage CROSSHAIR = IOUtilities
      .loadImage("/resources/images/crosshair.png");

  /** ORoS icon 32x32 */
  public static final BufferedImage LOGO32 = IOUtilities
      .loadImage("/resources/images/oros-logo32.png");
  /** ORoS icon 48x48 */
  public static final BufferedImage LOGO48 = IOUtilities
      .loadImage("/resources/images/oros-logo48.png");
  /** ORoS icon 64x64 */
  public static final BufferedImage LOGO64 = IOUtilities
      .loadImage("/resources/images/oros-logo64.png");
  /** ORoS icon 128x128 */
  public static final BufferedImage LOGO128 = IOUtilities
      .loadImage("/resources/images/oros-logo128.png");

  /** Red Crosshair for combat */
  public static final BufferedImage RED_CROSSHAIR = IOUtilities
      .loadImage("/resources/images/red_crosshair.png");

  /** Big planet screen Rock 1 */
  public static final BufferedImage BIG_PLANET_ROCK1 = IOUtilities
      .loadImage("/resources/images/rock1.png");

  /** Big planet screen water world 1 */
  public static final BufferedImage BIG_PLANET_WATERWORLD1 = IOUtilities
      .loadImage("/resources/images/waterworld1.png");

  /** Big planet screen water world 2 */
  public static final BufferedImage BIG_PLANET_WATERWORLD2 = IOUtilities
      .loadImage("/resources/images/waterworld2.png");

  /** Big planet screen water world 3 */
  public static final BufferedImage BIG_PLANET_WATERWORLD3 = IOUtilities
      .loadImage("/resources/images/waterworld3.png");

  /** Big planet screen water world 4 */
  public static final BufferedImage BIG_PLANET_WATERWORLD4 = IOUtilities
      .loadImage("/resources/images/waterworld4.png");
  /** Big planet screen water world 5 */
  public static final BufferedImage BIG_PLANET_WATERWORLD5 = IOUtilities
      .loadImage("/resources/images/waterworld5.png");
  /** Big planet screen water world 6 */
  public static final BufferedImage BIG_PLANET_WATERWORLD6 = IOUtilities
      .loadImage("/resources/images/waterworld6.png");
  /** Big planet screen water world 7 */
  public static final BufferedImage BIG_PLANET_WATERWORLD7 = IOUtilities
      .loadImage("/resources/images/waterworld7.png");
  /** Big planet screen water world 8 */
  public static final BufferedImage BIG_PLANET_WATERWORLD8 = IOUtilities
      .loadImage("/resources/images/waterworld8.png");
  /** Big planet screen water world 9 */
  public static final BufferedImage BIG_PLANET_WATERWORLD9 = IOUtilities
      .loadImage("/resources/images/waterworld9.png");

  /** Big planet screen ice world 1 */
  public static final BufferedImage BIG_PLANET_ICEWORLD1 = IOUtilities
      .loadImage("/resources/images/iceworld1.png");

  /** Big planet screen ice world 2 */
  public static final BufferedImage BIG_PLANET_ICEWORLD2 = IOUtilities
      .loadImage("/resources/images/iceworld2.png");
  /** Big planet screen ice world 3 */
  public static final BufferedImage BIG_PLANET_ICEWORLD3 = IOUtilities
      .loadImage("/resources/images/iceworld3.png");
  /** Big planet screen ice world 4 */
  public static final BufferedImage BIG_PLANET_ICEWORLD4 = IOUtilities
      .loadImage("/resources/images/iceworld4.png");

  /** Big planet screen carbon world 1 */
  public static final BufferedImage BIG_PLANET_SWAMPWORLD1 = IOUtilities
      .loadImage("/resources/images/carbonworld1.png");
  /** Big planet screen carbon world 2 */
  public static final BufferedImage BIG_PLANET_SWAMPWORLD2 = IOUtilities
      .loadImage("/resources/images/carbonworld2.png");
  /** Big planet screen carbon world 3 */
  public static final BufferedImage BIG_PLANET_SWAMPWORLD3 = IOUtilities
      .loadImage("/resources/images/carbonworld3.png");

  /** Big planet screen iron planet 1 */
  public static final BufferedImage BIG_PLANET_VOLCANICPLANET1 = IOUtilities
      .loadImage("/resources/images/ironplanet1.png");

  /** Big planet screen iron planet 2 */
  public static final BufferedImage BIG_PLANET_VOLCANICPLANET2 = IOUtilities
      .loadImage("/resources/images/ironplanet2.png");

  /** Big planet screen iron planet 3 */
  public static final BufferedImage BIG_PLANET_VOLCANICPLANET3 = IOUtilities
      .loadImage("/resources/images/ironplanet3.png");
  /** Big planet screen iron planet 4 */
  public static final BufferedImage BIG_PLANET_VOLCANICPLANET4 = IOUtilities
      .loadImage("/resources/images/ironplanet4.png");
  /** Big planet screen iron planet 5 */
  public static final BufferedImage BIG_PLANET_VOLCANICPLANET5 = IOUtilities
      .loadImage("/resources/images/ironplanet5.png");
  /** Big planet screen iron planet 6 */
  public static final BufferedImage BIG_PLANET_VOLCANICPLANET6 = IOUtilities
      .loadImage("/resources/images/ironplanet6.png");

  /** Big planet gas world */
  public static final BufferedImage BIG_GASWORLD1 = IOUtilities
      .loadImage("/resources/images/gasworld1.png");

  /** Big planet screen gas world */
  public static final BufferedImage BIG_GASWORLD2 = IOUtilities
      .loadImage("/resources/images/gasworld2.png");
  /** Big planet screen gas world */
  public static final BufferedImage BIG_GASWORLD3 = IOUtilities
      .loadImage("/resources/images/gasworld3.png");
  /** Big planet screen desert planet 1 */
  public static final BufferedImage BIG_PLANET_DESERTWORLD1 = IOUtilities
      .loadImage("/resources/images/desertplanet1.png");
  /** Big planet screen desert planet 2 */
  public static final BufferedImage BIG_PLANET_DESERTWORLD2 = IOUtilities
      .loadImage("/resources/images/desertplanet2.png");
  /** Big planet screen desert planet 3 */
  public static final BufferedImage BIG_PLANET_DESERTWORLD3 = IOUtilities
      .loadImage("/resources/images/desertplanet3.png");
  /** Big planet screen artificial planet 1 */
  public static final BufferedImage BIG_PLANET_ARTIFICIALPLANET1 = IOUtilities
      .loadImage("/resources/images/artificialworld1.png");
  /** Big planet screen Earth */
  public static final BufferedImage BIG_PLANET_EARTH = IOUtilities
      .loadImage("/resources/images/earth.png");
  /** Big planet screen Mars */
  public static final BufferedImage BIG_PLANET_MARS = IOUtilities
      .loadImage("/resources/images/mars.png");

  /** Big planet screen Jupiter */
  public static final BufferedImage BIG_PLANET_JUPITER = IOUtilities
      .loadImage("/resources/images/jupiter.png");
  /** Big planet screen Saturn */
  public static final BufferedImage BIG_PLANET_SATURN = IOUtilities
      .loadImage("/resources/images/saturn.png");
  /** Big planet screen IceGiant1 */
  public static final BufferedImage BIG_PLANET_ICEGIANT1 = IOUtilities
      .loadImage("/resources/images/icegiant1.png");
  /** Big planet screen IceGiant2 */
  public static final BufferedImage BIG_PLANET_ICEGIANT2 = IOUtilities
      .loadImage("/resources/images/icegiant2.png");

  /** Big sports logo */
  public static final String BIG_SPORT_LOGO = "/resources/images/olympics.png";

  /** Star field image for parallax scrolling */
  private static final BufferedImage STAR_FIELD_IMAGE = IOUtilities
      .loadImage("/resources/images/starfield.png");

  /** Generated star field image. */
  private static BufferedImage starField = null;

  /** Generated star nebula image. */
  private static BufferedImage starNebulae = null;
  /** Separate thread to generate background stars. */
  private static ProceduralRenderer proceduralRenderer = null;
  /** Nebula image for parallax scrolling */
  public static final BufferedImage NEBULAE_IMAGE = IOUtilities
      .loadImage("/resources/images/nebulae.png");

  /** Image used for calculation text width and heights. */
  private static final BufferedImage TEMP_IMAGE = new BufferedImage(100, 100,
      BufferedImage.TYPE_4BYTE_ABGR);

  /**
   * List of built-in space race images.
   */
  public static final String[] BUILT_IN_SPACE_RACE_IMAGES = {
      "resources/images/human_race.png",
      "resources/images/teuthidae_race.png",
      "resources/images/fernid_race.png",
      "resources/images/scaurian_race.png",
      "resources/images/homarian_race.png",
      "resources/images/mothoid_race.png",
      "resources/images/centaur_race.png",
      "resources/images/greyan_race.png",
      "resources/images/mechion_race.png",
      "resources/images/spork_race.png",
      "resources/images/chiraloid_race.png",
      "resources/images/reborgian_race.png",
      "resources/images/lithorian_race.png",
      "resources/images/smaugirian_race.png",
      "resources/images/synthdroid_race.png",
      "resources/images/alteirian_race.png",
      "resources/images/mushroom.png",
      "resources/images/alonian_race.png"
  };

  /** Teuthidae race selection image */
  public static final BufferedImage IMAGE_TEUTHIDAE_RACE = IOUtilities
      .loadImage("/resources/images/teuthidae_race.png");
  /** Teuthidae race selection image */
  public static final BufferedImage IMAGE_FERNID_RACE = IOUtilities
      .loadImage("/resources/images/fernid_race.png");
  /** Scaurian race selection image */
  public static final BufferedImage IMAGE_SCAURIAN_RACE = IOUtilities
      .loadImage("/resources/images/scaurian_race.png");
  /** Homarian race selection image */
  public static final BufferedImage IMAGE_HOMARIAN_RACE = IOUtilities
      .loadImage("/resources/images/homarian_race.png");
  /** Mothoid race selection image */
  public static final BufferedImage IMAGE_MOTHOID_RACE = IOUtilities
      .loadImage("/resources/images/mothoid_race.png");
  /** Centaur race selection image */
  public static final BufferedImage IMAGE_CENTAUR_RACE = IOUtilities
      .loadImage("/resources/images/centaur_race.png");
  /** Greyan race selection image */
  public static final BufferedImage IMAGE_GREYAN_RACE = IOUtilities
      .loadImage("/resources/images/greyan_race.png");
  /** Mechion race selection image */
  public static final BufferedImage IMAGE_MECHION_RACE = IOUtilities
      .loadImage("/resources/images/mechion_race.png");
  /** Spork race selection image */
  public static final BufferedImage IMAGE_SPORK_RACE = IOUtilities
      .loadImage("/resources/images/spork_race.png");

  /** Human race selection image */
  public static final BufferedImage IMAGE_HUMAN_RACE = IOUtilities
      .loadImage("/resources/images/human_race.png");

  /** Chiraloid race selection image */
  public static final BufferedImage IMAGE_CHIRALOID_RACE = IOUtilities
      .loadImage("/resources/images/chiraloid_race.png");

  /** Reborgian race selection image */
  public static final BufferedImage IMAGE_REBORGIAN_RACE = IOUtilities
      .loadImage("/resources/images/reborgian_race.png");

  /** Lithorian race selection image */
  public static final BufferedImage IMAGE_LITHORIAN_RACE = IOUtilities
      .loadImage("/resources/images/lithorian_race.png");

  /** Alteirian race selection image */
  public static final BufferedImage IMAGE_ALTEIRIAN_RACE = IOUtilities
      .loadImage("/resources/images/alteirian_race.png");

  /** Smaugirian race selection image */
  public static final BufferedImage IMAGE_SMAUGIRIAN_RACE = IOUtilities
      .loadImage("/resources/images/smaugirian_race.png");

  /** Synthdroid race selection image */
  public static final BufferedImage IMAGE_SYNTHDROID_RACE = IOUtilities
      .loadImage("/resources/images/synthdroid_race.png");

  /** Privateer race selection image */
  public static final BufferedImage IMAGE_PRIVATEER_RACE = IOUtilities
      .loadImage("/resources/images/privateer_race.png");

  /** Newsreader image for GBNC */
  public static final BufferedImage IMAGE_NEWSREADER = IOUtilities
      .loadImage("/resources/images/newsreader.png");

  /** GBNC logo */
  public static final BufferedImage IMAGE_GBNC = IOUtilities
      .loadImage("/resources/images/gbnc-logo.png");

  /** ASTEROIDS */
  public static final BufferedImage IMAGE_ASTEROIDS = IOUtilities
      .loadImage("/resources/images/asteroids.png");

  /** Pirate pilot */
  public static final String IMAGE_PIRATE_PILOT =
      "/resources/images/pirate_pilot.png";

  /** Space kraken */
  public static final String IMAGE_KRAKEN = "/resources/images/kraken.png";

  /** Blueish black hole */
  public static final String IMAGE_BLUEISH_BLACKHOLE =
      "/resources/images/blueish_blackhole.png";

  /** Pirate raiders */
  public static final String IMAGE_PIRATE_RAIDERS =
      "/resources/images/pirate_raiders.png";

  /** Big corvette */
  public static final String IMAGE_CORVETTE =
      "/resources/images/corvette.png";
  /** Big cruiser */
  public static final String IMAGE_CRUISER =
      "/resources/images/cruiser.png";

  /** Mutiny */
  public static final String IMAGE_MUTINY = "/resources/images/mutiny.png";
  /** Dataloss */
  public static final String IMAGE_DATALOSS = "/resources/images/dataloss.png";

  /** Newstation */
  public static final String IMAGE_NEWSTATION =
      "/resources/images/newstation.png";

  /** Destroyed PLanet */
  public static final String IMAGE_DESTROYED_PLANET =
      "/resources/images/destroyedPlanet.png";

  /** Shuttle */
  public static final String IMAGE_SHUTTLE = "/resources/images/shuttle.png";

  /** Blackhole pilot */
  public static final BufferedImage IMAGE_BLACKHOLE = IOUtilities
      .loadImage("/resources/images/blackhole.png");

  /** Old probe */
  public static final BufferedImage IMAGE_OLD_PROBE = IOUtilities
      .loadImage("/resources/images/oldprobe.png");

  /** Rare tech */
  public static final BufferedImage IMAGE_RARE_TECH = IOUtilities
      .loadImage("/resources/images/raretech.png");

  /** Artifact 1 */
  public static final BufferedImage IMAGE_ARTIFACT1 = IOUtilities
      .loadImage("/resources/images/artifact1.png");
  /** Artifact 2 */
  public static final BufferedImage IMAGE_ARTIFACT2 = IOUtilities
      .loadImage("/resources/images/artifact2.png");

  /** Old Ship */
  public static final String IMAGE_OLD_SHIP = "/resources/images/oldship.png";

  /** Alien Vessel */
  public static final String IMAGE_ALIEN_SHIP =
      "/resources/images/alienship.png";
  /** Time Warp */
  public static final BufferedImage IMAGE_TIME_WARP = IOUtilities
      .loadImage("/resources/images/timewarp.png");
  /** Factory */
  public static final String IMAGE_FACTORY = "/resources/images/factory.png";
  /** Pirate lair */
  public static final BufferedImage IMAGE_PIRATE_LAIR = IOUtilities
      .loadImage("/resources/images/piratelair.png");
  /** Electron nebula */
  public static final BufferedImage IMAGE_DSA = IOUtilities
      .loadImage("/resources/images/electron nebula.png");
  /** Space ship */
  public static final BufferedImage IMAGE_SPACE_SHIP = IOUtilities
      .loadImage("/resources/images/spaceship_final.png");
  /** Trade Space ship 1 */
  public static final String IMAGE_TRADE_SHIP =
      "/resources/images/tradeship-learmarch.png";
  /** Trade Space ship 2 */
  public static final String IMAGE_TRADE_SHIP2 =
      "/resources/images/tradeship-learmarch1.png";
  /** Cloaked ship */
  public static final String IMAGE_CLOAKED_SHIP =
      "/resources/images/cloaked_ship.png";
  /** Shuttle 2 */
  public static final String IMAGE_SHUTTLE2 = "/resources/images/shuttle2.png";
  /** Big nuke image */
  public static final String IMAGE_BIG_NUKE =
      "/resources/images/bignuke.png";
  /** Big ban icon */
  public static final String IMAGE_BIG_BAN_ICON =
      "/resources/images/bigban.png";
  /** Big ban peace */
  public static final String IMAGE_BIG_PEACE_ICON =
      "/resources/images/bigpeace.png";
  /** Big pirate ship. */
  public static final String IMAGE_PRIVATEER =
      "/resources/images/pirateship.png";
  /** Galaxy image */
  public static final String IMAGE_GALAXY =
      "/resources/images/galaxy.png";
  /** United Galaxy Tower image */
  public static final String IMAGE_UNITED_GALAXY_TOWER =
      "/resources/images/unitedgalaxytower.png";
  /** Big missile image */
  public static final String IMAGE_BIG_MISSILE =
      "/resources/images/bigmissile.png";
  /** Big money image */
  public static final String IMAGE_BIG_MONEY =
      "/resources/images/bigmoney.png";
  /** Solar flares images */
  public static final String IMAGE_SOLAR_FLARES =
      "/resources/images/sun-flares.png";
  /** Solar no flares images */
  public static final String IMAGE_SOLAR_NO_FLARES =
      "/resources/images/sun-noflares.png";
  /** Image desert planet image */
  public static final String IMAGE_DESERT = "/resources/images/desert.png";
  /** Image old table with book, lamp and court hammer */
  public static final BufferedImage IMAGE_DESKTOP = IOUtilities
      .loadImage("/resources/images/olddesk.png");
  /** Image paradise planet */
  public static final String IMAGE_PARADISE = "/resources/images/landscape.png";
  /** Image viruses */
  public static final String IMAGE_VIRUSES = "/resources/images/viruses.png";
  /** Image city in fire */
  public static final String IMAGE_FIRE_IN_CITY =
      "/resources/images/city-fire.png";
  /** Image mysterious signal */
  public static final String IMAGE_SIGNAL =
      "/resources/images/mysterious_signal.png";
  /** Image technical breakthrough */
  public static final String IMAGE_TECHNICAL_BREAKTHROUGH =
      "/resources/images/technical_breakthrough.png";

  /** Image meteor */
  public static final String IMAGE_METEOR =
      "/resources/images/meteor.png";
  /** Image meteor hit explosion */
  public static final String IMAGE_METEOR_HIT =
      "/resources/images/meteorhit.png";
  /** Image ship destroyed */
  public static final String IMAGE_SHIP_DESTROYED =
      "/resources/images/ship_destroyed.png";
  /** Image spinosaurus */
  public static final String IMAGE_SPINOSAURUS =
      "/resources/images/spinosaurus.png";
  /** Image stasis */
  public static final String IMAGE_STASIS = "/resources/images/stasis.png";
  /** Image containers */
  public static final String IMAGE_CONTAINERS =
      "/resources/images/containers.png";
  /** Image terror strike */
  public static final String IMAGE_TERROR = "/resources/images/terror.png";
  /** Image metal rich surface */
  public static final String IMAGE_METAL_RICH_SURFACE =
      "/resources/images/metal_rich_surface.png";
  /** Image precious gems */
  public static final String IMAGE_PRECIOUS_GEMS =
      "/resources/images/gems.png";
  /** Image ancient laboratory */
  public static final String IMAGE_ANCIENT_LABORATORY =
      "/resources/images/ancientlaboratory.png";
  /** Image ancient research */
  public static final String IMAGE_ANCIENT_RESEARCH =
      "/resources/images/ancientresearch.png";
  /** Image ancient factory */
  public static final String IMAGE_ANCIENT_FACTORY =
      "/resources/images/ancientfactory.png";
  /** Image ancient temple */
  public static final String IMAGE_ANCIENT_TEMPLE =
      "/resources/images/ancienttemple.png";
  /** Image ancient palace */
  public static final String IMAGE_ANCIENT_PALACE =
      "/resources/images/ancientpalace.png";
  /** Image Black Monolith */
  public static final String IMAGE_BLACK_MONOLITH =
      "/resources/images/blackmonolith.png";
  /** Image Molten lava */
  public static final String IMAGE_MOLTEN_LAVA =
      "/resources/images/moltenlava.png";
  /** Image Arid */
  public static final String IMAGE_ARID = "/resources/images/arid.png";
  /** Image Lush vegetation */
  public static final String IMAGE_LUSH_VEGETATION =
      "/resources/images/lushvegetation.png";
  /** Image Artifact on planet */
  public static final String IMAGE_ARTIFACT_ON_PLANET =
      "/resources/images/artifactonplanet.png";
  /** Big Orbital. */
  public static final String IMAGE_BIG_ORBITAL =
      "/resources/images/bigorbital.png";
  /** Rift portal. */
  public static final String IMAGE_RIFT_PORTAL =
      "/resources/images/rift_portal.png";
  /** Devourer */
  public static final String IMAGE_DEVOURER =
      "/resources/images/devourer.png";
  /** Big Explosion */
  public static final String IMAGE_BIG_EXPLOSION =
      "/resources/images/bigexplosion.png";

  /** Ship bridge interior 1 */
  public static final BufferedImage IMAGE_INTERIOR1 = IOUtilities
      .loadImage("/resources/images/bridge1.png");

  /**
   * Return image for specified race
   * @param race SpaceRace
   * @return BufferedImage
   */
  public static BufferedImage getRaceImg(final SpaceRace race) {
    if (race.getImage().startsWith("resources/images")) {
      return IOUtilities.loadImage(race.getImage());
    }
    return IOUtilities.loadImage(Folders.getCustomSpaceRaceImage()
        + "/" + race.getImage());
  }

  /**
   * Get text width for certain font
   * @param font Font to use
   * @param text String
   * @return Text width in pixels
   */
  public static int getTextWidth(final Font font, final String text) {
    if (font != null && text != null) {
      Graphics2D g2d = TEMP_IMAGE.createGraphics();
      int width = (int) font.getStringBounds(text, g2d.getFontRenderContext())
          .getWidth();
      g2d.dispose();
      return width;
    }
    return 0;
  }

  /**
   * Get text height for certain font
   * @param font Font to use
   * @param text String
   * @return Text height in pixels
   */
  public static int getTextHeight(final Font font, final String text) {
    if (font != null && text != null) {
      Graphics2D g2d = TEMP_IMAGE.createGraphics();
      int height = (int) font.getStringBounds(text, g2d.getFontRenderContext())
          .getHeight();
      g2d.dispose();
      return height;
    }
    return 0;
  }

  /**
   * Scale Image to half size of the original size
   * @param source Buffered image to scale
   * @return Scaled buffered image
   */
  public static BufferedImage scaleToHalf(final BufferedImage source) {
    int halfWidth = source.getWidth() / 2;
    int halfHeight = source.getHeight() / 2;
    int origWidth = source.getWidth();
    int origHeight = source.getHeight();
    BufferedImage target = new BufferedImage(halfWidth, halfHeight,
        BufferedImage.TYPE_4BYTE_ABGR);
    if (source.getHeight() == origHeight && source.getWidth() == origWidth) {
      int mx = origWidth / halfWidth;
      int my = origHeight / halfHeight;
      for (int y = 0; y < halfHeight; y++) {
        for (int x = 0; x < halfWidth; x++) {
          int color = source.getRGB(x * mx, y * my);
          target.setRGB(x, y, color);
        }
      }
    }
    return target;
  }

  /**
   * Get star field image and generate if not available.
   * @return BufferedImage
   */
  public static synchronized BufferedImage getStarField() {
    if (starField == null) {
      if (!Game.isMainMethodCalled()) {
        return STAR_FIELD_IMAGE;
      }
      if (proceduralRenderer == null) {
        proceduralRenderer = new ProceduralRenderer();
        proceduralRenderer.start();
      }
      starField = proceduralRenderer.getStars();
      if (starField == null) {
        return STAR_FIELD_IMAGE;
      }
    }
    return starField;
  }

  /**
   * Generate both stars and nebulae in same pictue.
   * @return Buffered Image.
   */
  public static BufferedImage getStarNebulae() {
    if (starNebulae == null) {
      int width = Math.min(NEBULAE_IMAGE.getWidth(), getStarField().getWidth());
      int height = Math.min(NEBULAE_IMAGE.getHeight(),
          getStarField().getHeight());
      starNebulae = new BufferedImage(width, height,
          BufferedImage.TYPE_4BYTE_ABGR);
      Graphics graphics = starNebulae.getGraphics();
      graphics.drawImage(getStarField(), 0, 0, null);
      graphics.drawImage(NEBULAE_IMAGE, 0, 0, null);
    }
    return starNebulae;
  }

  /**
   * Set Scheme type and initialize UI Manager.
   * @param schemeType the BaseScheme to set
   */
  public static void setSchemeType(final BaseScheme schemeType) {
    GuiStatics.schemeType = schemeType;
    initializeUiDefaults();
  }

  /** Initialize UI manager. */
  public static void initializeUiDefaults() {
    UIManager.put("ScrollBarUI", SpaceScrollBarUI.class.getName());
    UIManager.put("Tree.paintLines", false);
    UIManager.put("Tree.line", GuiStatics.getInfoTextColor());
    UIManager.put("Tree.closedIcon", Icons.getIconByName(
        Icons.ICON_CLOSED).getAsIcon());
    UIManager.put("Tree.openIcon", Icons.getIconByName(
        Icons.ICON_AIRLOCK_OPEN).getAsIcon());
    UIManager.put("Tree.expandedIcon", Icons.getIconByName(
        Icons.ICON_EXPANDED).getAsIcon());
    UIManager.put("Tree.collapsedIcon", Icons.getIconByName(
        Icons.ICON_COLLAPSED).getAsIcon());
    UIManager.put("Tree.leafIcon", Icons.getIconByName(
        Icons.ICON_ARROW_RIGHT).getAsIcon());
    UIManager.put("Tree.background", Color.BLACK);
    UIManager.put("Tree.selectionBackground",
        GuiStatics.getDeepSpaceColor());
    UIManager.put("Tree.selectionForeground",
        GuiStatics.getInfoTextColor());
    UIManager.put("Tree.selectionBorderColor",
        GuiStatics.getDeepSpaceDarkColor());
    UIManager.put("Tree.textBackground", Color.BLACK);
    UIManager.put("Tree.textForeground", GuiStatics.getInfoTextColor());
    UIManager.put("ToolTip.background",
        GuiStatics.getCoolSpaceColorDark());
    UIManager.put("ToolTip.foreground",
        GuiStatics.getCoolSpaceColor());
    UIManager.put("ToolTip.border", BorderFactory
        .createLineBorder(GuiStatics.getCoolSpaceColorDarker()));
    UIManager.put("TabbedPane.selected", GuiStatics.getActivitionColor());
    UIManager.put("ComboBox.selectionBackground",
        GuiStatics.getActivitionColor());
  }

  /**
   * Set Scheme type.
   * @param schemeType the BaseScheme to set
   */
  public static void setSchemeType(final SchemeType schemeType) {
    if (schemeType == SchemeType.CLASSIC_SPACE_GREY_BLUE) {
      setSchemeType(CLASSIC_SCHEME);
    }
    if (schemeType == SchemeType.SPACE_GREY) {
      setSchemeType(GREY_SCHEME);
    }
    if (schemeType == SchemeType.DANGEROUS_YELLOW) {
      setSchemeType(YELLOW_SCHEME);
    }
  }

  /**
   * Get UI Scheme type
   * @return SchemeType
   */
  public static SchemeType getSchemeType() {
    return schemeType.getType();
  }

  /**
   * Get Panel background color from scheme.
   * @return Color
   */
  public static Color getPanelBackground() {
    return schemeType.getPanelBackground();
  }

  /**
   * Get cool space color
   * @return Color
   */
  public static Color getCoolSpaceColor() {
    return schemeType.getCoolSpaceColor();
  }

  /**
   * Get Scifi border text color.
   * @return Color
   */
  public static Color getScifiBorderTextColor() {
    return schemeType.getScifiBorderTextColor();
  }
  /**
   * Get cool space color transparent.
   * @return Color
   */
  public static Color getCoolSpaceColorTransparent() {
    return schemeType.getCoolSpaceColorTransparent();
  }

  /**
   * Get Info text color dark.
   * @return Color
   */
  public static Color getInfoTextColorDark() {
    return schemeType.getInfoTextColorDark();
  }

  /**
   * Get Info text color.
   * @return Color
   */
  public static Color getInfoTextColor() {
    return schemeType.getInfoTextColor();
  }

  /**
   * Get Cool Space Color Dark
   * @return Color
   */
  public static Color getCoolSpaceColorDark() {
    return schemeType.getCoolSpaceColorDark();
  }

  /**
   * Get Cool Space Color Darker
   * @return Color
   */
  public static Color getCoolSpaceColorDarker() {
    return schemeType.getCoolSpaceColorDarker();
  }

  /**
   * Get Cool Space Color Darker transparent
   * @return Color
   */
  public static Color getCoolSpaceColorDarkerTransparent() {
    return schemeType.getCoolSpaceColorDarkerTransparent();
  }

  /**
   * Get Deep Space Color
   * @return Color
   */
  public static Color getDeepSpaceColor() {
    return schemeType.getDeepSpaceColor();
  }

  /**
   * Get Deep Space Dark Color
   * @return Color
   */
  public static Color getDeepSpaceDarkColor() {
    return schemeType.getDeepSpaceDarkColor();
  }

  /**
   * Get Deep Space Activity Color
   * @return Color
   */
  public static Color getDeepSpaceActivityColor() {
    return schemeType.getDeepSpaceActivityColor();
  }

  /**
   * Get Checkbox enabled Color
   * @return Color
   */
  public static Color getCheckBoxEnabledColor() {
    return schemeType.getCheckBoxEnabledColor();
  }

  /**
   * Get Checkbox disabled Color
   * @return Color
   */
  public static Color getCheckBoxDisabledColor() {
    return schemeType.getCheckBoxDisabledColor();
  }

  /**
   * Get Selection Activity Color
   * @return Color
   */
  public static Color getSelectionActivityColor() {
    return schemeType.getSeclectionActivityColor();
  }

  /**
   * Get Activation color
   * @return Color
   */
  public static Color getActivitionColor() {
    return schemeType.getActivationColor();
  }

  /**
   * Get warning active color
   * @return Color
   */
  public static Color getWarningActiveColor() {
    return schemeType.getWarningColorActive();
  }

  /**
   * Get warning shadow color
   * @return Color
   */
  public static Color getWarningShadowColor() {
    return schemeType.getWarningColorShadow();
  }

  /**
   * Get Small arrow icon based on scheme.
   * @param name Arrow type name
   * @return Icon16x16
   */
  public static Icon16x16 getSmallArrow(final String name) {
    return schemeType.getSmallArrowIcon(name);
  }

  /**
   * Get arrow left based on scheme.
   * @return BufferedImage
   */
  public static BufferedImage getArrowLeft() {
    return schemeType.getArrowLeft();
  }

  /**
   * Get arrow left pressed based on scheme.
   * @return BufferedImage
   */
  public static BufferedImage getArrowLeftPressed() {
    return schemeType.getArrowLeftPressed();
  }

  /**
   * Get arrow right based on scheme.
   * @return BufferedImage
   */
  public static BufferedImage getArrowRight() {
    return schemeType.getArrowRight();
  }

  /**
   * Get arrow right pressed based on scheme.
   * @return BufferedImage
   */
  public static BufferedImage getArrowRightPressed() {
    return schemeType.getArrowRightPressed();
  }

  /**
   * Get horizontal thumb based on scheme.
   * @return BufferedImage
   */
  public static BufferedImage getHorizontalThumb() {
    return schemeType.getHorizontalThumb();
  }

  /**
   * Get vertical thumb based on scheme.
   * @return BufferedImage
   */
  public static BufferedImage getVerticalThumb() {
    return schemeType.getVerticalThumb();
  }

}
