package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JTextField;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.buttons.SpaceCheckBox;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.SpaceLabel;
import org.openRealmOfStars.gui.panels.BigImagePanel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.InvisiblePanel;
import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.PlanetTypes;

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
* Save game name view when starting new game.
*
*/
public class SaveGameNameView extends BlackPanel {

  /**
  *
  */
  private static final long serialVersionUID = 1L;

  /**
   * Save game name field.
   */
  private JTextField saveGameField;

  /**
   * Check box for overwriting the file.
   */
  private SpaceCheckBox overWriteFile;

  /**
   * Button for start game
   */
  private SpaceButton startGameBtn;

  /**
   * Constructor for save game file name view.
   * @param listener ActionListener
   */
  public SaveGameNameView(final ActionListener listener) {
    Planet planet = new Planet(new Coordinate(1, 1), "Save Game Planet",
        1, false);
    planet.setPlanetType(PlanetTypes.getRandomPlanetType(true, true, true));
    if (planet.getPlanetType().isGasGiant()) {
      planet.setGasGiant(true);
    }
    // Background image
    BigImagePanel imgBase = new BigImagePanel(planet, true, "Game File Name");
    imgBase.setLayout(new BorderLayout());
    this.setLayout(new BorderLayout());

    InvisiblePanel invisible = new InvisiblePanel(imgBase);
    invisible.setLayout(new BoxLayout(invisible, BoxLayout.Y_AXIS));
    invisible.add(Box.createRigidArea(new Dimension(500, 150)));

    InfoPanel info = new InfoPanel();
    info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
    info.setTitle("Game Setup");
    SpaceLabel label = new SpaceLabel("Save game file:");
    label.setAlignmentX(LEFT_ALIGNMENT);
    info.add(label);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    saveGameField = new JTextField("savegame.save");
    saveGameField.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    saveGameField.setForeground(GuiStatics.COLOR_COOL_SPACE_BLUE);
    saveGameField.setFont(GuiStatics.getFontCubellanSmaller());
    saveGameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
    info.add(saveGameField);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    label = new SpaceLabel("Overwrite if save game file exists:");
    label.setAlignmentX(LEFT_ALIGNMENT);
    info.add(label);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    overWriteFile = new SpaceCheckBox("Overwrite existis save game");
    overWriteFile.addActionListener(listener);
    overWriteFile.setAlignmentX(LEFT_ALIGNMENT);
    info.add(overWriteFile);
    info.add(Box.createRigidArea(new Dimension(5, 25)));
    InvisiblePanel xinvis = new InvisiblePanel(invisible);
    xinvis.setLayout(new BoxLayout(xinvis, BoxLayout.X_AXIS));
    xinvis.add(Box.createRigidArea(new Dimension(200, 5)));
    xinvis.add(info);
    xinvis.add(Box.createRigidArea(new Dimension(200, 5)));
    invisible.add(xinvis);
    invisible.add(Box.createRigidArea(new Dimension(500, 150)));

    imgBase.add(invisible, BorderLayout.CENTER);

    invisible = new InvisiblePanel(imgBase);
    invisible.setLayout(new BorderLayout());
    SpaceButton btn = new SpaceButton("Back", GameCommands.COMMAND_CANCEL);
    btn.addActionListener(listener);
    btn.setAlignmentX(Component.CENTER_ALIGNMENT);
    invisible.add(btn, BorderLayout.WEST);
    startGameBtn = new SpaceButton("Start game", GameCommands.COMMAND_NEXT);
    startGameBtn.addActionListener(listener);
    startGameBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
    invisible.add(startGameBtn, BorderLayout.EAST);
    imgBase.add(invisible, BorderLayout.SOUTH);
    this.add(imgBase, BorderLayout.CENTER);
  }

  /**
   * Update panels.
   */
  public void updatePanel() {
    if (!saveGameField.getText().endsWith(".save")) {
      saveGameField.setText(saveGameField.getText() + ".save");
    }
    File file = new File("saves/" + saveGameField.getText());
    if (file.exists()) {
      overWriteFile.setEnabled(true);
    } else {
      overWriteFile.setEnabled(false);
      overWriteFile.setSelected(false);
    }
  }
}
