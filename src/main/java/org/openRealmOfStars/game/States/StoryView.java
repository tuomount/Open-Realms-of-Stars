package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;

import org.openRealmOfStars.audio.music.MusicPlayer;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.InfoTextArea;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.ImagePanel;
import org.openRealmOfStars.gui.panels.ShipInteriorPanel;
import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.newsCorp.ImageInstruction;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
*
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
*
*
* Story view which can show background story of the realm.
*
*/
public class StoryView extends BlackPanel {
  /**
  *
  */
  private static final long serialVersionUID = 1L;

  /**
   * Text are where story is being shown.
   */
  private InfoTextArea textArea;

  /**
   * StarMap
   */
  private StarMap map;

  /**
   * PlayerInfo aka realm.
   */
  private PlayerInfo realm;

  /**
   * Story view.
   * @param map StarMap
   * @param realm PlayerInfo
   * @param listener ActionListener
   */
  public StoryView(final StarMap map, final PlayerInfo realm,
      final ActionListener listener) {
    if (MusicPlayer.getNowPlaying() != realm.getRace().getDiplomacyMusic()) {
      MusicPlayer.play(realm.getRace().getDiplomacyMusic());
    }
    this.setLayout(new BorderLayout());
    this.map = map;
    this.realm = realm;
    InfoPanel base = new InfoPanel();
    base.setLayout(new BorderLayout());
    base.setTitle("Story so far. . .");
    ImageInstruction instructions = new ImageInstruction();
    Planet planet = getHomePlanet();
    if (planet != null) {
      if (DiceGenerator.getRandom(1) == 0) {
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
    ImagePanel imagePanel = new ImagePanel(image);
    base.add(imagePanel, BorderLayout.NORTH);
    this.add(base, BorderLayout.NORTH);
    InfoPanel centerPanel = new InfoPanel();
    centerPanel.setTitle(this.realm.getEmpireName());
    centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
    centerPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    textArea = new InfoTextArea();
    textArea.setEditable(false);
    textArea.setFont(GuiStatics.getFontCubellan());
    textArea.setWrapStyleWord(true);
    textArea.setLineWrap(true);
    textArea.setCharacterWidth(9);
    JScrollPane scroll = new JScrollPane(textArea);
    textArea.setText(realm.getBackgroundStory());
    centerPanel.add(scroll);
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
}
