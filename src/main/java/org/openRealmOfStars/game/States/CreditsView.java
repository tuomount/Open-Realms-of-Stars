package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.labels.StarFieldTextArea;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.utilities.IOUtilities;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016-2021 Tuomo Untinen
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
 * Credits view for Open Realm of Stars
 *
 */

public class CreditsView extends BlackPanel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * Text area to show credits
   */
  private StarFieldTextArea textArea;

  /**
   * Number of lines to show in text area
   */
  private static final int NUMBER_OF_LINES = 60;

  /**
   * Main Credits string
   */
  public static final String MAIN_CREDITS =
      "# Programming and Design by\n\n"
      + "Tuomo Untinen\n\n"
      + "# Additional programming\n\n"
      + "Dávid Szigecsán \"sigee\"\n\n"
      + "Diocto\n\n"
      + "dlahtl1004\n\n"
      + "Krlucete\n\n"
      + "wksdn18\n\n"
      + "All graphics under CC-BY-SA 3.0 license. Unless otherwise noted.\n"
      + "See http://creativecommons.org/licenses/by-sa/3.0/\n\n"
      + "# Graphics by\n\n"
      + "Moons and planet made by Unnamed (Viktor.Hahn@web.de)\n"
      + "(http://opengameart.org/content/16-planet-sprites)\n"
      + "(https://opengameart.org/content/17-planet-sprites)\n\n"
      + "SunRed by Priest865 "
      + "(http://opengameart.org/content/space-assets)\n\n"
      + "141 Military Icons Set by AngryMeteor.com - "
      + "http://opengameart.org/content/140-military-icons-set-fixed\n\n"
      + "Nebulae, star field and space panel by Tuomo Untinen\n\n"
      + "Route dot by Tuomo Untinen\n\n"
      + "Relations icons by Tuomo Untinen\n\n"
      + "Photon torpedo and plasma bullet by Tuomo Untinen\n\n"
      + "Space ships are done with Surt's modular ships\n\n"
      + "Scaurian ships are done with "
      + "BizmasterStudios's SpaceShip Building Kit\n"
      + "Licensed under CC BY 4.0\n"
      + "https://creativecommons.org/licenses/by/4.0/\n\n"
      + "Explosions by Csaba Felvegi aka Chabull\n\n"
      + "Alien pictures by Surt - "
      + "http://opengameart.org/forumtopic/cc0-scraps\n\n"
      + "Scaurian trader picture by Tuomo Untinen, original art by Surt - "
      + "http://opengameart.org/forumtopic/cc0-scraps\n\n"
      + "Chiraloid race picture by Tuomo Untinen, original art by Surt - "
      + "http://opengameart.org/forumtopic/cc0-scraps\n\n"
      + "Reborgian race picture by Tuomo Untinen, original art by Surt - "
      + "http://opengameart.org/forumtopic/cc0-scraps\n\n"
      + "Lithorian race picture by Tuomo Untinen, original art by Surt - "
      + "http://opengameart.org/forumtopic/cc0-scraps\n\n"
      + "Alteirian race picture by Tuomo Untinen, original art by Surt - "
      + "http://opengameart.org/forumtopic/cc0-scraps\n\n"
      + "Smaugirian race picture by Tuomo Untinen, original art by Surt - "
      + "http://opengameart.org/forumtopic/cc0-scraps\n\n"
      + "Space Captain by Justin Nichol \n"
      + "Tuomo Untinen added legs for the captain. -"
      + "https://opengameart.org/content/space-captain-with-legs\n\n"
      + "The Husk- Human Analog Android by Justin Nichol - "
      + "https://opengameart.org/content/the-husk-human-analog-android\n\n"
      + "GBNC logo by Tuomo Untinen\n\n"
      + "Worm hole by Tuomo Untinen\n\n"
      + "Privateering effect in combat by Tuomo Untinen\n\n"
      + "Privateer race picture by Tuomo Untinen\n\n"
      + "Homarian and Scaurian ships are done with "
      + "BizmasterStudios's SpaceShip Building Kit\n"
      + "Licensed under CC BY 4.0\n"
      + "https://creativecommons.org/licenses/by/4.0/\n\n"
      + "Repair symbol from 141 Military Icons Set by AngryMeteor.com\n"
      + "http://opengameart.org/content/140-military-icons-set-fixed\n\n"
      + "Defense symbol modified to look like shield by Tuomo Untinen\n"
      + "Original from 141 Military Icons Set by AngryMeteor.com \n"
      + "http://opengameart.org/content/140-military-icons-set-fixed\n\n"
      + "Music note and Speaker icon by Tuomo Untinen\n\n"
      + "Antenna icon by Tuomo Untinen\n\n"
      + "Prison icon by Tuomo Untinen\n\n"
      + "Happy and Sad faces by Tuomo Untinen\n\n"
      + "Space anomaly cloud tile by Vyntershtoff\n\n"
      + "Wormhole tiles modifications by Tuomo Untinen\n"
      + "Original art by Vyntershtoff\n\n"
      + "Asteroids image\n"
      + "Asteroids by phaelax\n"
      + "Background stars by Tuomo Untinen\n\n"
      + "Pirate pilot image by Tuomo Untinen\n\n"
      + "Blackhole image by Tuomo Untinen\n\n"
      + "Old Probe image contains 3D model of satellite by Grefuntor\n"
      + " http://atmostatic.blogspot.com\n"
      + "Background stars by Tuomo Untinen\n\n"
      + "Old ship image contains "
      + "3D model of Scifi plane model and texture by Ulf\n"
      + "Background stars by Tuomo Untinen\n\n"
      + "Black star ship by canisferus (CC0)\n\n"
      + "Electron nebula by kitart360\n\n"
      + "Space ship with turrets by gfx0 (CC0)\n\n"
      + "Space ship trader vessels by gfx0\n\n"
      + "Space ship bridge by Jani Mäkinen aka gfx0\n\n"
      + "Centaur Space Ship Bridge by Tuomo Untinen\n\n"
      + "Artificial Planet by Tuomo Untinen\n\n"
      + "Shield animation from Sci-fi effects by Skorpio\n"
      + "https://opengameart.org/content/sci-fi-effects\n"
      + "Licensed under CC BY 4.0\n"
      + "https://creativecommons.org/licenses/by/4.0/\n\n"
      + "ORoS logos by Tuomo Untinen\n\n"
      + "Scaurian Space Ship Bridge by Tuomo Untinen\n\n"
      + "Galactic Olympics logo by Tuomo Untinen\n\n"
      + "Fat Man by Renderwahn\n\n"
      + "Galaxy image by Tuomo Untinen\n\n"
      + "Big Ban and peace logos by Tuomo Untinen\n\n"
      + "Pirate ship (Night Raider) by dravenx\n\n"
      + "United Galaxy Tower by Tuomo Untinen\n\n"
      + "Big missile icon by qubodup (CC0)\n\n"
      + "Big money icon by Tuomo Untinen\n\n"
      + "Original money images done by photoshopwizard (CC0)\n\n"
      + "Mechion Space Ship Bridge by Tuomo Untinen\n\n"
      + "Sun with and without solar flares by Tuomo Untinen\n\n"
      + "Desert by Cethiel (CC0)\n\n"
      + "Prehistoric nature landscape by Pepper Racoon - "
      + "https://opengameart.org/content/landscape\n\n"
      + "Viruses (Cold, Flu, Chicken Pox) by bart (CC0)\n\n"
      + "Human Space Ship Bridge by Tuomo Untinen\n\n"
      + "Space ship images by Viktor Hahn (Viktor.Hahn@web.de)\n"
      + "using space ship models by greyoxide, Michael Davies,"
      + " and Viktor Hahn,\n"
      + "is licensed under the Creative Commons Attribution 3.0 Unported"
      + " License.\n"
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Tutorial icon by Tuomo Untinen\n\n"
      + "Meteor by Tuomo Untinen\n\n"
      + "Mothoid Space Ship Bridge by Tuomo Untinen\n\n"
      + "Mysterious Signal by Tuomo Untinen\n\n"
      + "Technical Breakthrough by Tuomo Untinen\n\n"
      + "Greyan Space Ship Bridge by Tuomo Untinen\n\n"
      + "Time warp by Tuomo Untinen\n\n"
      + "Homarian Space Ship Bridge by Tuomo Untinen\n\n"
      + "Spinosaurus by Tuomo Untinen\n\n"
      + "Lightning animation from Sci-fi effects by Skorpio\n"
      + "https://opengameart.org/content/sci-fi-effects\n"
      + "Licensed under CC BY 4.0\n\n"
      + "Tractor beam, organic armor and solar armor"
      + " icons modified by Tuomo Untinen\n"
      + "Originals from 141 Military Icons Set by AngryMeteor.com \n\n"
      + "Plasma, Ion cannon and distortion shield"
      + " icons modified by Tuomo Untinen\n"
      + "Originals from 141 Military Icons Set by AngryMeteor.com \n\n"
      + "Teuthidae Space Ship Bridge by Tuomo Untinen\n\n"
      + "Spork Space Ship Bridge by Tuomo Untinen\n\n"
      + "Chiraloid Space Ship Bridge by Tuomo Untinen\n\n"
      + "Sci-Fi containers by Rubberduck (CC0)\n\n"
      + "Space Pirate Space Ship Bridge by Tuomo Untinen\n\n"
      + "Rare tech capsule by Tuomo Untinen\n\n"
      + "Lithorian ships are done by using"
      + " Space ship building bits, volume 1 ships\n"
      + "by Stephen Challener (Redshrike), hosted by OpenGameArt.org\n"
      + "https://opengameart.org/content/space-ship-building-bits-volume-1\n\n"
      + "Cloaked ship and shuttle 2 by Tuomo Untinen\n\n"
      + "Lithorian Space Ship Bridge by Tuomo Untinen\n\n"
      + "Scanner animation by Tuomo Untinen\n\n"
      + "Warzone Concept by Justin Nichol\n"
      + "https://opengameart.org/content/warzone-concept\n\n"
      + "\n\nFonts are under SIL Open Font License, Version 1.1.\n"
      + "# Fonts by\n\n"
      + "Cubellan font by Jyrki Ihalainen (yardan74@gmail.com)\n\n"
      + "Squarrion font by Cristiano Sobral (cssobral2013@gmail.com)\n"
      + "from Reserved Font Name EXO by Natanael Gama (exo@ndiscovered.com)\n\n"
      + "# Sounds by\n\n"
      + "Space combat weapons sounds by\n"
      + "Michel Baradari apollo-music.de\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Space combat explosions sounds by\n"
      + "Michel Baradari apollo-music.de\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Menu click sounds by\n"
      + "Tim Mortimer\n"
      + "http://www.archive.org/details/TimMortimer\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Radio call sound by\n"
      + "Tuomo Untinen\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Engine sounds by\n"
      + "Tuomo Untinen\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Starbase deploy sound\n"
      + "Adaptation by Tuomo Untinen, original sounds by\n"
      + "Lee Barkovich and Michel Baradari\n"
      + "http://www.archive.org/details/Berklee44Barkovich"
      + " http://www.lbarkovich.com/\n"
      + "https://opengameart.org/content/robotic-mechanic-step-sounds\n"
      + "https://opengameart.org/content/9-sci-fi-computer-sounds-and-beeps\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Ship repair sound\n"
      + "Adaptation by Tuomo Untinen, original sounds by\n"
      + "Lee Barkovich\n"
      + "http://www.archive.org/details/Berklee44Barkovich"
      + " http://www.lbarkovich.com/\n"
      + "https://opengameart.org/content/robotic-mechanic-step-sounds\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Laser_07 as EMP explosion by\n"
      + "Little Robot Sound Factory\n"
      + "www.littlerobotsoundfactory.com\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Teleport sound by\n"
      + "Michel Baradari\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Wormhole sound\n"
      + "Adaptation by Tuomo Untinen, original sounds by\n"
      + "Écrivain\n"
      + "https://opengameart.org/content/4-space-portal-sounds\n"
      + "Licensed under CC0\n\n"
      + "Muffled Distant Explosion by NenadSimic (CC0)\n\n"
      + "Nuke Sound\n"
      + "Adaptation by Tuomo Untinen, from\n"
      + "Chunky Explosion by Joth(CC0)\n\n"
      + "Disabled menu sound by\n"
      + "Lokif(CC0)\n\n"
      + "Shield sounds by Bart(CC0)\n\n"
      + "Alarm loop by\n"
      + "Little Robot Sound Factory\n"
      + "www.littlerobotsoundfactory.com\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Warp engine engage by Bart(CC0)\n\n"
      + "Destroy building sound from 100 CC0 SFX #2 by Rubberduck (CC0)\n\n"
      + "Whipping sound by\n"
      + "Tuomo Untinen\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Coins sound by\n"
      + "Tuomo Untinen\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Robotic Transformations (Dissamble) sound by\n"
      + "Mekaal - https://opengameart.org/content/robotic-transformations\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "SpaceEngine_Start_00 as colonized by\n"
      + "Little Robot Sound Factory\n"
      + "www.littlerobotsoundfactory.com\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Glitch sound by Tuomo Untinen(CC0)\n\n"
      + "Electricity Sound Effects by Erich Izdepski\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Overloaded engine, jammer, targeting computer and cloaking device\n"
      + "sounds from 50 CC0 SFX #2 by Rubberduck (CC0)\n\n"
      + "Tractor beam sound by BLACK LODGE GAMES, LLC (CC0)\n"
      + "https://creativecommons.org/publicdomain/zero/1.0/\n\n"
      + "Ion Cannon Sound by \n"
      + "colmmullally\n"
      + "https://freesound.org/people/colmmullally/sounds/462220/\n"
      + "Licensed under CC BY 3.0 "
      + "https://creativecommons.org/licenses/by/3.0/\n\n"
      + "Plasma Cannon Sound Effects by Erokia\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Transport inbound Sci-Fi Trooper voice by Angus Macnaughton\n"
      + "https://opengameart.org/content/sci-fi-trooper-voice-pack-54-lines\n"
      + "Licensed under CC BY 4.0 "
      + "http://creativecommons.org/licenses/by/4.0/\n\n"
      + "Scanner overload sound by\n"
      + "Tuomo Untinen\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "# Musics By\n\n"
      + "Observing The Star by\n"
      + "YD (CC0)\n\n"
      + "A million light years between us by\n"
      + "Alexandr Zhelanov https://soundcloud.com/alexandr-zhelanov\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Neon Transit by\n"
      + "Alexandr Zhelanov https://soundcloud.com/alexandr-zhelanov\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Thrust Sequence by\n"
      + "Matthew Pablo http://www.matthewpablo.com\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Nautilus by\n"
      + "poinl (CC0)\n\n"
      + "Brave Space Explorers by\n"
      + "Alexandr Zhelanov https://soundcloud.com/alexandr-zhelanov\n"
      + "Licensed under CC BY 4.0 "
      + "http://creativecommons.org/licenses/by/4.0/\n\n"
      + "Lost Signal by\n"
      + "PetterTheSturgeon\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Walking with Poseidon by\n"
      + "mvrasseli\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Conquerors by\n"
      + "Alexandr Zhelanov https://soundcloud.com/alexandr-zhelanov\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Diplomacy by\n"
      + "Ove Melaa\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Pressure by\n"
      + "YD (CC0)\n\n"
      + "Fantasy choir 2 by\n"
      + "www.punchytap.com (CC0)\n\n"
      + "Space Theme by\n"
      + "Joshua Stephen Kartes\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Fight Music Theme01 by\n"
      + "GTDStudio - Pavel Panferov\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Trogl by\n"
      + "oglsdl https://soundcloud.com/oglsdl\n"
      + "Licensed under CC BY 4.0 "
      + "http://creativecommons.org/licenses/by/4.0/\n\n"
      + "Abandoned Steel Mill by\n"
      + "Spring (CC0)\n\n"
      + "Interplanetary Odyssey by\n"
      + "Patrick de Arteaga https://patrickdearteaga.com/\n"
      + "Licensed under CC BY 4.0 "
      + "http://creativecommons.org/licenses/by/4.0/\n\n"
      + "Malloga Balling by\n"
      + "Joe Reynolds - Professorlamp  jrtheories.webs.com\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Menace by\n"
      + "YD (CC0)\n\n"
      + "Law In The City by\n"
      + "Alexandr Zhelanov https://soundcloud.com/alexandr-zhelanov\n"
      + "Licensed under CC BY 4.0 "
      + "http://creativecommons.org/licenses/by/4.0/\n\n"
      + "I Do Know You by\n"
      + "Memoraphile @ You're Perfect Studio (CC0)\n\n"
      + "Out of Time by\n"
      + "Hitctrl https://soundcloud.com/hitctrl\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Drifting Beyond the Stars by\n"
      + "Hitctrl https://soundcloud.com/hitctrl\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Set Fire to Reality - dark/electronic by \n"
      + "Justin Dalessandro(ColdOneK)\n"
      + "Licensed under CC BY 4.0 "
      + "http://creativecommons.org/licenses/by/4.0/\n\n"
      + "Death Is Just Another Path by \n"
      + "Otto Halmén\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Braindead by \n"
      + "Kim Lightyear\n"
      + "Licensed under  CC-BY-SA 3.0 "
      + "http://creativecommons.org/licenses/by-sa/3.0/\n\n"
      + "Techno DRIVE!!! by \n"
      + "Centurion_of_war\n"
      + "Licensed under CC-BY 4.0 "
      + "http://creativecommons.org/licenses/by/4.0/\n\n"
      + "Mysterious Anomaly by \n"
      + "Eric Matyas Soundimage.org\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Sky Portal by\n"
      + "Alexandr Zhelanov https://soundcloud.com/alexandr-zhelanov\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n";




  /**
   * Constructor for Credits view
   * @param listener Action Listener for command
   * @param title Program Title
   * @param version Program Version
   * @throws IOException If error happens on reading license files.
   */
  public CreditsView(final ActionListener listener, final String title,
      final String version) throws IOException {
    String creditsText = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
        + "\n\n\n\n\n\n\n\n\n\n\n" + "# " + title + " " + version + "\n\n"
        + "# Credits\n\n"
        + MAIN_CREDITS;

    InputStream is = CreditsView.class
        .getResourceAsStream("/resources/GPL2.txt");
    BufferedInputStream bis = new BufferedInputStream(is);
    String gpl2License = "";
    try (DataInputStream dis = new DataInputStream(bis)) {
      gpl2License = IOUtilities.readTextFile(dis);
    }
    is = CreditsView.class.getResourceAsStream(
        "/resources/fonts/Cubellan_v_0_7/Cubellan_License_SIL_OFL.txt");
    bis = new BufferedInputStream(is);
    String cubellanLicense = "";
    try (DataInputStream dis = new DataInputStream(bis)) {
      cubellanLicense = IOUtilities.readTextFile(dis);
    }
    is = CreditsView.class.getResourceAsStream(
        "/resources/fonts/Squarion/License.txt");
    bis = new BufferedInputStream(is);
    String squarionLicense = "";
    try (DataInputStream dis = new DataInputStream(bis)) {
      squarionLicense = IOUtilities.readTextFile(dis);
    }
    creditsText = creditsText + "\n\n"
        + "# GNU GENERAL PUBLIC LICENSE Version 2, June 1991\n" + gpl2License
        + "\n\n" + "# Cubellan Font license\n\n"
        + cubellanLicense + "\n\n" + "# Squarion Font License\n\n"
        + squarionLicense;
    this.setLayout(new BorderLayout());
    textArea = new StarFieldTextArea();
    textArea.setScrollText(creditsText, NUMBER_OF_LINES);
    textArea.setText(creditsText);
    textArea.setSmoothScroll(true);
    textArea.setEditable(false);
    this.add(textArea, BorderLayout.CENTER);

    SpaceButton btn = new SpaceButton("OK", GameCommands.COMMAND_OK);
    btn.addActionListener(listener);
    this.add(btn, BorderLayout.SOUTH);

  }

  /**
   * Get full credits as a String
   * @return Full credits
   */
  public String getCreditsText() {
    return textArea.getText();
  }
  /**
   * Update Text area
   */
  public void updateTextArea() {
    if (textArea.getSmoothScrollNextRow()) {
      textArea.getNextLine();
    }
    repaint();
  }

}
