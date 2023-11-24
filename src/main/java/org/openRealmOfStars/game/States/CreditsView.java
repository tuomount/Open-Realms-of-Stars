package org.openRealmOfStars.game.States;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016-2023 Tuomo Untinen
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
   * Selection for credits and license text.
   */
  public static final int CREDITS_AND_LICENSE = 0;

  /**
   * Selection for change log.
   */
  public static final int CHANGE_LOG = 1;

  /**
   * Constructor for Credits view
   * @param listener Action Listener for command
   * @param title Program Title
   * @param version Program Version
   * @param authors Program Authors
   * @param textType See CREDITS_AND_LICENSE and CHANGE_LOG.
   * @throws IOException If error happens on reading license files.
   */
  public CreditsView(final ActionListener listener, final String title,
      final String version, final String authors, final int textType)
      throws IOException {
    String creditsText = "";
    if (textType == CREDITS_AND_LICENSE) {
      creditsText = generateCreditsAndLicense(title, version, authors);
    }
    if (textType == CHANGE_LOG) {
      creditsText = generateChangeLog(title, version);
    }
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
  * Generate credits and license text.
  * @param title Program Title
  * @param version Program Version
  * @param authors Program Authors
  * @return Credit and license text
  * @throws IOException If error happens on reading license files.
  */
  public String generateCreditsAndLicense(final String title,
      final String version, final String authors) throws IOException {
    StringBuilder sb = new StringBuilder();
    sb.append("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
        + "\n\n\n\n\n\n\n\n\n\n\n# ");
    sb.append(title);
    sb.append(" ");
    sb.append(version);
    sb.append("\n\n");
    sb.append(authors);

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
    sb.append("\n\n"
        + "# GNU GENERAL PUBLIC LICENSE Version 2, June 1991\n");
    sb.append(gpl2License);
    sb.append("\n\n# Cubellan Font license\n\n");
    sb.append(cubellanLicense);
    sb.append("\n\n# Squarion Font License\n\n");
    sb.append(squarionLicense);
    return sb.toString();
  }

  /**
  * Generate Change lgo.
  * @param title Program Title
  * @param version Program Version
  * @return Change log text
  * @throws IOException If error happens on reading license files.
  */
  public String generateChangeLog(final String title,
      final String version) throws IOException {
    StringBuilder sb = new StringBuilder();
    sb.append("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
        + "\n\n\n\n\n\n\n\n\n\n\n# ");
    sb.append(title);
    sb.append(" ");
    sb.append(version);
    sb.append("\n\n# Change log\n\n");

    InputStream is = CreditsView.class
        .getResourceAsStream("/resources/changelog.txt");
    BufferedInputStream bis = new BufferedInputStream(is);
    String changeLog = "";
    try (DataInputStream dis = new DataInputStream(bis)) {
      changeLog = IOUtilities.readTextFile(dis);
    }
    sb.append(changeLog);
    return sb.toString();
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
