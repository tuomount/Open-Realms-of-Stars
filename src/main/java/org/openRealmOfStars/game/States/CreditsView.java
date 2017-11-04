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
 * Copyright (C) 2016, 2017  Tuomo Untinen
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
  private static final int NUMBER_OF_LINES = 45;

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
      + "All graphics under CC-BY-SA 3.0 license.\n"
      + "See http://creativecommons.org/licenses/by-sa/3.0/\n"
      + "# Graphics by\n\n"
      + "Moons and planet made by Unnamed (Viktor.Hahn@web.de)\n"
      + "(http://opengameart.org/content/16-planet-sprites)\n\n"
      + "SunRed by Priest865 "
      + "(http://opengameart.org/content/space-assets)\n\n"
      + "141 Military Icons Set by AngryMeteor.com - "
      + "http://opengameart.org/content/140-military-icons-set-fixed\n\n"
      + "Nebulae, star field and space panel by Tuomo Untinen\n\n"
      + "Route dot by Tuomo Untinen\n\n"
      + "Relations icons by Tuomo Untinen\n\n"
      + "Photon torpedo by Tuomo Untinen\n\n"
      + "Space ships are done with Surt's modular ships\n\n"
      + "Explosions by Csaba Felvegi aka Chabull\n\n"
      + "Alien pictures by Surt - "
      + "http://opengameart.org/forumtopic/cc0-scraps\n\n"
      + "Space Captain by Justin Nichol - "
      + "http://opengameart.org/content/space-captain\n\n"
      + "The Husk- Human Analog Android by Justin Nichol - "
      + "https://opengameart.org/content/the-husk-human-analog-android\n\n"
      + "GBNC logo by Tuomo Untinen\n\n"
      + "Worm hole by Tuomo Untinen\n\n"
      + "Fonts are under SIL Open Font License, Version 1.1.\n"
      + "# Fonts by\n\n"
      + "Cubellan font by Jyrki Ihalainen (yardan74@gmail.com)\n\n"
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
    creditsText = creditsText + "\n\n"
        + "# GNU GENERAL PUBLIC LICENSE Version 2, June 1991\n" + gpl2License
        + "\n\n" + "# SIL Open Font License, Version 1.1.\n\n"
        + cubellanLicense;
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
