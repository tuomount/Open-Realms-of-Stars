package org.openRealmOfStars.gui.infopanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.labels.IconLabel;
import org.openRealmOfStars.gui.labels.InfoTextArea;
import org.openRealmOfStars.gui.labels.SpaceLabel;
import org.openRealmOfStars.gui.utilies.GuiStatics;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2018  Tuomo Untinen
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
* Espionage panel for showing espionage info for one realm.
*
*/
public class EspionagePanel extends InfoPanel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;
  /**
   * Realm name to show in title
   */
  private String realmName;
  /**
   * Description about the espionage
   */
  private String description;
  /**
   * Espionage value
   */
  private int value;

  /**
   * Constructor for espionage panel. Contains title,
   * espionage value and description info text
   * @param realm Realm name to show
   * @param text Espionage text
   * @param espionageValue Espionage value
   * @param relation Realm relation text
   * @param relationColor what color is used to draw relation text.
   * @param listener ActionListener for showing realm information button.
   */
  public EspionagePanel(final String realm, final String text,
      final int espionageValue, final String relation,
      final Color relationColor, final ActionListener listener) {
    realmName = realm;
    description = text;
    value = espionageValue;
    this.setTitle(realmName);
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    IconLabel iconLabel = new IconLabel(null,
        Icons.getIconByName(Icons.ICON_SPY_GOGGLES), "Espionage: "
        + value + "/10");
    this.add(iconLabel);
    SpaceLabel likenessLabel = new SpaceLabel(relation);
    likenessLabel.setForeground(relationColor);
    this.add(likenessLabel);
    this.add(Box.createRigidArea(new Dimension(5, 5)));
    InfoTextArea infoText = new InfoTextArea();
    infoText.setEditable(false);
    infoText.setFont(GuiStatics.getFontCubellanSmaller());
    infoText.setCharacterWidth(8);
    infoText.setLineWrap(true);
    JScrollPane scroll = new JScrollPane(infoText);
    scroll.setBackground(GuiStatics.getDeepSpaceDarkColor());
    infoText.setText(description);
    this.add(scroll);
    this.add(Box.createRigidArea(new Dimension(5, 5)));
    SpaceButton btn = new SpaceButton("Realm", realmName + "|"
        + GameCommands.COMMAND_REALM_VIEW);
    btn.addActionListener(listener);
    this.add(btn);
  }

  /**
   * Get realm name to show
   * @return Realm name
   */
  public String getRealmName() {
    return realmName;
  }

  /**
   * Get description from info text which is shown
   * @return Espionage description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Get espionage value
   * @return espionage value
   */
  public int getValue() {
    return value;
  }

}
