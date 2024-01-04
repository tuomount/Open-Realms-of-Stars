package org.openRealmOfStars.game.state;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2023 Tuomo Untinen
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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;

import org.openRealmOfStars.audio.music.MusicPlayer;
import org.openRealmOfStars.audio.soundeffect.SoundPlayer;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.buttons.IconButton;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.infopanel.EmptyInfoPanel;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.InfoTextPane;
import org.openRealmOfStars.gui.labels.SpaceLabel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.ImagePanel;
import org.openRealmOfStars.gui.panels.ShipInteriorPanel;
import org.openRealmOfStars.gui.util.GuiFonts;
import org.openRealmOfStars.gui.util.GuiStatics;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.newsCorp.ImageInstruction;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
*
* Story view which can show background story of the realm.
*
*/
public class EndStoryView extends BlackPanel {
  /**
  *
  */
  private static final long serialVersionUID = 1L;

  /**
   * Text are where story is being shown.
   */
  private InfoTextPane textArea;

  /**
   * StarMap
   */
  private StarMap map;

  /**
   * Center panel.
   */
  private InfoPanel centerPanel;

  /**
   * Image panel.
   */
  private ImagePanel imagePanel;

  /**
   * Realm index.
   */
  private int index;
  /**
   * PlayerInfo aka realm.
   */
  private PlayerInfo realm;

  /**
   * Story view.
   * @param map StarMap
   * @param listener ActionListener
   */
  public EndStoryView(final StarMap map, final ActionListener listener) {
    index = 0;
    realm = map.getPlayerByIndex(index);
    final var raceMusic = GuiStatics.getRaceDiplomacyMusic(realm.getRace());
    if (MusicPlayer.getNowPlaying() != raceMusic) {
      MusicPlayer.play(raceMusic);
    }
    this.setLayout(new BorderLayout());
    this.map = map;
    InfoPanel base = new InfoPanel();
    base.setLayout(new BorderLayout());
    base.setTitle("Story so far. . .");
    ImageInstruction instructions = new ImageInstruction();
    Planet planet = getHomePlanet();
    if (planet != null) {
      if (DiceGenerator.getBoolean()) {
        instructions.addBackground(ImageInstruction.BACKGROUND_STARS);
      } else {
        instructions.addBackground(ImageInstruction.BACKGROUND_NEBULAE);
      }
      String position = ImageInstruction.POSITION_CENTER;
      switch (DiceGenerator.getRandom(2)) {
        default:
        case 0: {
          position = ImageInstruction.POSITION_CENTER;
          break;
        }
        case 1: {
          position = ImageInstruction.POSITION_LEFT;
          break;
        }
        case 2: {
          position = ImageInstruction.POSITION_RIGHT;
          break;
        }
      }
      String size = ImageInstruction.SIZE_FULL;
      switch (DiceGenerator.getRandom(1)) {
        default:
        case 0: {
          size = ImageInstruction.SIZE_FULL;
          break;
        }
        case 1: {
          size = ImageInstruction.SIZE_HALF;
          break;
        }
      }
      instructions.addPlanet(position, planet.getImageInstructions(), size);
    } else {
      instructions.addBackground(ImageInstruction.BACKGROUND_NEBULAE);
    }
    instructions.addBridge(realm.getRace().getNameSingle());
    instructions.addCaptain(realm.getRace().getNameSingle(),
        ShipInteriorPanel.getAdjustment(realm.getRace()));
    int widthHeadLine = 711;
    int heightHeadLine = 400;
    BufferedImage image = new BufferedImage(widthHeadLine, heightHeadLine,
        BufferedImage.TYPE_4BYTE_ABGR);
    image = ImageInstruction.parseImageInstructions(image,
        instructions.build());
    imagePanel = new ImagePanel(image);
    base.add(imagePanel, BorderLayout.NORTH);
    this.add(base, BorderLayout.NORTH);
    centerPanel = new InfoPanel();
    centerPanel.setTitle(this.realm.getEmpireName());
    centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
    centerPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    textArea = new InfoTextPane();
    textArea.setEditable(false);
    textArea.setFont(GuiFonts.getFontSquarion());
    JScrollPane scroll = new JScrollPane(textArea);
    textArea.setText(realm.getBackgroundStory());
    textArea.setCaretPosition(0);
    centerPanel.add(scroll);
    EmptyInfoPanel panel = new EmptyInfoPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
    panel.setAlignmentX(Component.CENTER_ALIGNMENT);
    IconButton iBtn = new IconButton(GuiStatics.getArrowLeft(),
        GuiStatics.getArrowLeft(), false,
        GameCommands.COMMAND_PREV_REALM, this);
    iBtn.setToolTipText("Previous realm");
    iBtn.addActionListener(listener);
    panel.add(iBtn);
    SpaceLabel label = new SpaceLabel("Change realm");
    panel.add(label);
    iBtn = new IconButton(GuiStatics.getArrowRight(),
        GuiStatics.getArrowRightPressed(), false,
        GameCommands.COMMAND_NEXT_REALM, this);
    iBtn.setToolTipText("Next realm");
    iBtn.addActionListener(listener);
    panel.add(iBtn);
    centerPanel.add(panel);
    this.add(centerPanel, BorderLayout.CENTER);

    InfoPanel bottomPanel = new InfoPanel();
    bottomPanel.setLayout(new BorderLayout());
    bottomPanel.setTitle(null);
    SpaceButton btn = new SpaceButton("Continue",
        GameCommands.COMMAND_VIEW_STARMAP);
    btn.addActionListener(listener);
    bottomPanel.add(btn, BorderLayout.CENTER);

    // Add panels to base
    this.add(bottomPanel, BorderLayout.SOUTH);

  }

  /**
   * Update panels
   */
  public void updatePanels() {
    final var raceMusic = GuiStatics.getRaceDiplomacyMusic(realm.getRace());
    if (MusicPlayer.getNowPlaying() != raceMusic) {
      MusicPlayer.play(raceMusic);
    }
    ImageInstruction instructions = new ImageInstruction();
    Planet planet = getHomePlanet();
    if (planet != null) {
      if (DiceGenerator.getBoolean()) {
        instructions.addBackground(ImageInstruction.BACKGROUND_STARS);
      } else {
        instructions.addBackground(ImageInstruction.BACKGROUND_NEBULAE);
      }
      String position = ImageInstruction.POSITION_CENTER;
      switch (DiceGenerator.getRandom(2)) {
        default:
        case 0: {
          position = ImageInstruction.POSITION_CENTER;
          break;
        }
        case 1: {
          position = ImageInstruction.POSITION_LEFT;
          break;
        }
        case 2: {
          position = ImageInstruction.POSITION_RIGHT;
          break;
        }
      }
      String size = ImageInstruction.SIZE_FULL;
      switch (DiceGenerator.getRandom(1)) {
        default:
        case 0: {
          size = ImageInstruction.SIZE_FULL;
          break;
        }
        case 1: {
          size = ImageInstruction.SIZE_HALF;
          break;
        }
      }
      instructions.addPlanet(position, planet.getImageInstructions(), size);
    } else {
      instructions.addBackground(ImageInstruction.BACKGROUND_NEBULAE);
    }
    instructions.addBridge(realm.getRace().getNameSingle());
    instructions.addCaptain(realm.getRace().getNameSingle(),
        ShipInteriorPanel.getAdjustment(realm.getRace()));
    int widthHeadLine = 711;
    int heightHeadLine = 400;
    BufferedImage image = new BufferedImage(widthHeadLine, heightHeadLine,
        BufferedImage.TYPE_4BYTE_ABGR);
    image = ImageInstruction.parseImageInstructions(image,
        instructions.build());
    imagePanel.setImage(image);
    centerPanel.setTitle(this.realm.getEmpireName());
    textArea.setText(realm.getBackgroundStory());
    textArea.setCaretPosition(0);
    this.repaint();
  }
  /**
   * Get Home planet for realm.
   * This will calculate current best planet for home world.
   * Original home world will be always preferred.
   * @return Planet or null
   */
  private Planet getHomePlanet() {
    Planet result = null;
    int planetValueForHome = -1;
    int realmIndex = map.getPlayerList().getIndex(realm);
    for (Planet planet : map.getPlanetList()) {
      if (planet.getPlanetPlayerInfo() == realm) {
        int planetValue = planet.getTotalPopulation() * 2
            + planet.getNumberOfBuildings();
        if (planet.getHomeWorldIndex() == realmIndex) {
          planetValue = planetValue + 255;
        } else if (planet.getHomeWorldIndex() != -1) {
          planetValue = planetValue + 32;
        }
        if (planetValue > planetValueForHome) {
          result = planet;
          planetValueForHome = planetValue;
        }
      }
    }
    return result;
  }

  /**
   * Handle actions for end story view
   * @param arg0 ActionEvent command what player did
   */
  public void handleAction(final ActionEvent arg0) {
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_PREV_REALM)) {
      if (index > 0) {
        SoundPlayer.playMenuSound();
        index = index - 1;
        realm = map.getPlayerByIndex(index);
        updatePanels();
      } else {
        SoundPlayer.playMenuDisabled();
      }
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_NEXT_REALM)) {
      if (index < map.getPlayerList().getCurrentMaxRealms() - 1) {
        SoundPlayer.playMenuSound();
        index = index + 1;
        realm = map.getPlayerByIndex(index);
        updatePanels();
      } else {
        SoundPlayer.playMenuDisabled();
      }
    }
  }

  /**
   * Get currently selected realm.
   * @return PlayerInfo
   */
  public PlayerInfo getCurrentRealm() {
    return realm;
  }
}
