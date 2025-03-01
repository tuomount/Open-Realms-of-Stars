package org.openRealmOfStars.game.state;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016-2018 Tuomo Untinen
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
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import org.openRealmOfStars.audio.soundeffect.SoundPlayer;
import org.openRealmOfStars.game.Game;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.game.SavedGame;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.buttons.SpaceCheckBox;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.list.SaveGameListRenderer;
import org.openRealmOfStars.gui.panels.BigImagePanel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.InvisiblePanel;
import org.openRealmOfStars.gui.util.GuiStatics;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.enums.PlanetTypes;
import org.openRealmOfStars.utilities.ErrorLogger;
import org.openRealmOfStars.utilities.FileIo.Folders;
import org.openRealmOfStars.utilities.FileIo.GenericFileFilter;

/**
 *
 * Load Game View
 *
 */
public class LoadGameView extends BlackPanel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * Saved Game list
   *
   */
  private JList<SavedGame> saveGamesList;

  /**
   * CheckBox for confirm delete.
   */
  private SpaceCheckBox confirmDeleteBox;

  /**
   * Delete Button.
   */
  private SpaceButton deleteBtn;

  /**
   * Load Button.
   */
  private SpaceButton loadBtn;

  /**
   * Constructor for Load Game View
   * @param listener ActionListener
   */
  public LoadGameView(final ActionListener listener) {
    Planet planet = new Planet(new Coordinate(1, 1), "Load Game Planet", 1,
        false);
    planet.setPlanetType(PlanetTypes.getRandomPlanetType(true, true, true));
    if (planet.getPlanetType().isGasGiant()) {
      planet.setGasGiant(true);
    }
    int topSpace = 250;
    int sideSpace = 200;
    if (Game.getScreenWidth() < 1280) {
      topSpace = 100;
      sideSpace = 50;
    } else if (Game.getScreenWidth() < 1440) {
      topSpace = 150;
      sideSpace = 100;
    } else if (Game.getScreenWidth() < 1680) {
      topSpace = 200;
      sideSpace = 150;
    }

    // Background image
    BigImagePanel imgBase = new BigImagePanel(planet, true, "Load game");
    imgBase.setLayout(new BorderLayout());
    this.setLayout(new BorderLayout());

    InvisiblePanel invisible = new InvisiblePanel(imgBase);
    invisible.setLayout(new BoxLayout(invisible, BoxLayout.Y_AXIS));
    invisible.add(Box.createRigidArea(new Dimension(500, topSpace)));

    InvisiblePanel xinvis = new InvisiblePanel(invisible);
    xinvis.setLayout(new BoxLayout(xinvis, BoxLayout.X_AXIS));
    xinvis.add(Box.createRigidArea(new Dimension(sideSpace, 5)));
    InfoPanel info = new InfoPanel();
    info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
    info.setTitle("Saved games");
    info.add(Box.createRigidArea(new Dimension(5, 5)));


    saveGamesList = new JList<>(findSavedGames());

    saveGamesList.setCellRenderer(new SaveGameListRenderer());
    JScrollPane scroll = new JScrollPane(saveGamesList);
    scroll.setBackground(GuiStatics.getDeepSpaceDarkColor());
    saveGamesList.setBackground(Color.BLACK);
    saveGamesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    saveGamesList.addListSelectionListener(actionEvent -> {
      if (actionEvent.getValueIsAdjusting()) {
        deleteBtn.setEnabled(false);
        confirmDeleteBox.setSelected(false);
        loadBtn.setEnabled(true);
        confirmDeleteBox.setEnabled(true);
        SoundPlayer.playMenuSound();
      }
    });
    info.add(scroll);
    info.add(Box.createRigidArea(new Dimension(10, 10)));
    loadBtn = new SpaceButton("Load saved game", GameCommands.COMMAND_NEXT);
    loadBtn.addActionListener(listener);
    loadBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
    loadBtn.setEnabled(false);
    info.add(loadBtn);
    info.add(Box.createRigidArea(new Dimension(10, 10)));
    confirmDeleteBox = new SpaceCheckBox("Confirm save game delete");
    confirmDeleteBox.addActionListener(actionEvent -> {
      if (actionEvent.getActionCommand().equalsIgnoreCase(
          GameCommands.COMMAND_CONFIRM_DELETE)) {
        if (confirmDeleteBox.isSelected()) {
          deleteBtn.setEnabled(true);
          SoundPlayer.playSound(SoundPlayer.ALARM);
        } else {
          deleteBtn.setEnabled(false);
          SoundPlayer.playMenuDisabled();
        }
      }
    });
    confirmDeleteBox.setActionCommand(GameCommands.COMMAND_CONFIRM_DELETE);
    confirmDeleteBox.setAlignmentX(Component.CENTER_ALIGNMENT);
    confirmDeleteBox.setEnabled(false);
    info.add(confirmDeleteBox);
    info.add(Box.createRigidArea(new Dimension(10, 10)));
    deleteBtn = new SpaceButton("Delete game",
        GameCommands.COMMAND_DELETE_GAME);
    deleteBtn.setSpaceIcon(Icons.getIconByName(Icons.ICON_DELETE));
    deleteBtn.addActionListener(listener);
    deleteBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
    deleteBtn.setEnabled(false);
    info.add(deleteBtn);
    info.add(Box.createRigidArea(new Dimension(10, 10)));
    xinvis.add(info);
    xinvis.add(Box.createRigidArea(new Dimension(sideSpace, 5)));
    invisible.add(xinvis);

    imgBase.add(invisible, BorderLayout.CENTER);

    invisible = new InvisiblePanel(imgBase);
    invisible.setLayout(new BorderLayout());
    SpaceButton btn = new SpaceButton("Cancel", GameCommands.COMMAND_CANCEL);
    btn.addActionListener(listener);
    btn.setAlignmentX(Component.CENTER_ALIGNMENT);
    invisible.add(btn, BorderLayout.WEST);
    imgBase.add(invisible, BorderLayout.SOUTH);
    this.add(imgBase, BorderLayout.CENTER);

  }

  /**
   * Delete selected game. This will also play propriate sound and update list
   * after delete.
   */
  public void deleteSelectedGame() {
    String filename = Folders.getSavegamePath() + "/" + getSelectedSaveFile();
    Path path = Paths.get(filename);
    boolean success = false;
    try {
      Files.delete(path);
      success = true;
    } catch (IOException e) {
      ErrorLogger.log("Delete fails with exception: ");
      ErrorLogger.log(e);
    }
    if (success) {
      SoundPlayer.playMenuSound();
    } else {
      SoundPlayer.playMenuDisabled();
    }
    saveGamesList.setListData(findSavedGames());
    deleteBtn.setEnabled(false);
    confirmDeleteBox.setSelected(false);
    confirmDeleteBox.setEnabled(false);
    loadBtn.setEnabled(false);
    this.repaint();
  }

  /**
   * Find Saved games
   * @return Array of SavedGames
   */
  private static SavedGame[] findSavedGames() {
    File folder = new File(Folders.getSavegamePath());
    File[] files = folder.listFiles(new GenericFileFilter(".save"));
    if (files == null) {
      files = new File[0];
    }

    ArrayList<SavedGame> listOfGames = new ArrayList<>();
    for (int i = 0; i < files.length; i++) {
      try {
        listOfGames.add(new SavedGame(Folders.getSavegamePath(),
                                 files[i].getName()));
      } catch (IOException e) {
        ErrorLogger.log("Failed reading save game " + files[i].getName());
      }
    }
    Collections.sort(listOfGames, Collections.reverseOrder());
    SavedGame[] games = listOfGames.toArray(new SavedGame[listOfGames.size()]);
    return games;
  }

  /**
   * Get Selected Save file name
   * @return String as saved game file name or null if not selected.
   */
  public String getSelectedSaveFile() {
    if (saveGamesList.getSelectedValue() != null) {
      return saveGamesList.getSelectedValue().getFilename();
    }
    return null;
  }

}
