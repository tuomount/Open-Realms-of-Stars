package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;

import org.openRealmOfStars.game.Game;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.game.GameState;
import org.openRealmOfStars.gui.labels.TransparentLabel;
import org.openRealmOfStars.gui.panels.BigImagePanel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.InvisiblePanel;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
 * 
 * Open Realm of Stars game project
 * Copyright (C) 2016  Tuomo Untinen
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
 * AI turn view for Open Realm of Stars
 * 
 */
public class AITurnView extends BlackPanel {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  /**
   * Text for showing human player
   */
  private TransparentLabel label;
  
  /**
   * Reference to the game
   */
  private Game game;

  /**
   * Constructor for main menu
   * @param listener ActionListener
   */
  public AITurnView(Game game, ActionListener listener) {
    this.game = game;
    Planet planet = new Planet(1, 1, "Random Planet",1, false);
    if (DiceGenerator.getRandom(100)<10) {
      planet.setPlanetImageIndex(DiceGenerator.getRandom(1));
      planet.setGasGiant(true);
    } else {
      planet.setPlanetType(DiceGenerator.getRandom(Planet.PLANET_IMAGE_INDEX.length-1));
    }
    // Background image
    BigImagePanel imgBase = new BigImagePanel(planet, true,"Enemy turn");
    this.setLayout(new BorderLayout());
    
    InvisiblePanel invis = new InvisiblePanel(imgBase);    
    invis.setLayout(new BoxLayout(invis, BoxLayout.Y_AXIS));
    invis.add(Box.createRigidArea(new Dimension(500, 500)));

    TransparentLabel label = new TransparentLabel(invis, "Please wait...");
    invis.add(label);
    imgBase.add(invis);
    this.add(imgBase,BorderLayout.CENTER);

  }
  
  public void setText(String text) {
    label.setText(text);
  }

  /**
   * Handle actions for AI Turn view
   * @param arg0 ActionEvent
   */
  public void handleActions(ActionEvent arg0) {
    if (arg0.getActionCommand().equalsIgnoreCase(
        GameCommands.COMMAND_ANIMATION_TIMER) ) {
      if (game.getStarMap().getAIFleet() == null) {
        game.getStarMap().handleAIResearchAndPlanets();
      } else {
        game.getStarMap().handleAIFleet();
      }
      if (game.getStarMap().isAllAIsHandled()) {
        game.getStarMap().updateStarMapToNextTurn();
        for (int i=0;i<game.players.getCurrentMaxPlayers();i++) {
          PlayerInfo info = game.players.getPlayerInfoByIndex(i);
          info.getTechList().updateResearchPointByTurn(game.getStarMap().
             getTotalProductionByPlayerPerTurn(Planet.PRODUCTION_RESEARCH, i),info);
        }
        game.getStarMap().clearAITurn();
        game.changeGameState(GameState.STARMAP);

      }
    }

  }


}
