package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.BlackPanel;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.IconLabel;
import org.openRealmOfStars.gui.panels.InvisiblePanel;
import org.openRealmOfStars.gui.panels.WorkerProductionPanel;
import org.openRealmOfStars.player.PlayerInfo;

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
 * Research view for handling researching technology for player
 * 
 */
public class ResearchView extends BlackPanel {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  /**
   * Player Info
   */
  private PlayerInfo player;
  
  /**
   * Total research for player
   */
  private int totalResearch;
  
  /**
   * Combat Research panel
   */
  private WorkerProductionPanel combatRese;
  /**
   * Defense Research panel
   */
  private WorkerProductionPanel defenseRese;
  /**
   * Hull Research panel
   */
  private WorkerProductionPanel hullRese;
  /**
   * Improvement Research panel
   */
  private WorkerProductionPanel improvementRese;
  /**
   * Propulsion Research panel
   */
  private WorkerProductionPanel propulsionRese;
  /**
   * Electronics Research panel
   */
  private WorkerProductionPanel electronicsRese;
  
  /**
   * Create new research for player
   * @param player whom clicked research button
   * @param totalResearch how much player is currently researching per turn
   * @param listener ActionListener
   */
  public ResearchView(PlayerInfo player,int totalResearch, ActionListener listener) {
    this.player = player;
    this.totalResearch = totalResearch;
    InfoPanel base = new InfoPanel();
    base.setTitle("Research");
    this.setLayout(new BorderLayout());
    base.setLayout(new BorderLayout());
    InvisiblePanel invis = new InvisiblePanel(base);
    invis.setLayout(new BoxLayout(invis, BoxLayout.Y_AXIS));
    IconLabel label = new IconLabel(invis, null, "Reseach focus");
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    invis.add(label);
    invis.add(Box.createRigidArea(new Dimension(10,15)));
    combatRese = new WorkerProductionPanel(invis, 
        GameCommands.COMMAND_MINUS_COMBAT_RESEARCH,
        GameCommands.COMMAND_PLUS_COMBAT_RESEARCH, Icons.ICON_COMBAT_TECH,
        "Combat 100%", "Focus on combat technology", listener);
    combatRese.setAlignmentX(Component.CENTER_ALIGNMENT);
    invis.add(combatRese);
    invis.add(Box.createRigidArea(new Dimension(10,10)));
    defenseRese = new WorkerProductionPanel(invis, 
        GameCommands.COMMAND_MINUS_DEFENSE_RESEARCH,
        GameCommands.COMMAND_PLUS_DEFENSE_RESEARCH, Icons.ICON_DEFENSE_TECH,
        "Defense 100%", "Focus on defense technology", listener);
    defenseRese.setAlignmentX(Component.CENTER_ALIGNMENT);
    invis.add(defenseRese);
    invis.add(Box.createRigidArea(new Dimension(10,10)));
    hullRese = new WorkerProductionPanel(invis, 
        GameCommands.COMMAND_MINUS_HULL_RESEARCH,
        GameCommands.COMMAND_PLUS_HULL_RESEARCH, Icons.ICON_HULL_TECH,
        "Hull 100%", "Focus on hull technology", listener);
    hullRese.setAlignmentX(Component.CENTER_ALIGNMENT);
    invis.add(hullRese);
    invis.add(Box.createRigidArea(new Dimension(10,10)));
    improvementRese = new WorkerProductionPanel(invis, 
        GameCommands.COMMAND_MINUS_IMPROVEMENT_RESEARCH,
        GameCommands.COMMAND_PLUS_IMPROVEMENT_RESEARCH, Icons.ICON_IMPROVEMENT_TECH,
        "Planetary improvement 100%", "Focus on planetary improvement technology", listener);
    improvementRese.setAlignmentX(Component.CENTER_ALIGNMENT);
    invis.add(improvementRese);
    invis.add(Box.createRigidArea(new Dimension(10,10)));
    propulsionRese = new WorkerProductionPanel(invis, 
        GameCommands.COMMAND_MINUS_PROPULSION_RESEARCH,
        GameCommands.COMMAND_PLUS_PROPULSION_RESEARCH, Icons.ICON_PROPULSION_TECH,
        "Propulsion 100%", "Focus on propulsion technology", listener);
    propulsionRese.setAlignmentX(Component.CENTER_ALIGNMENT);
    invis.add(propulsionRese);
    invis.add(Box.createRigidArea(new Dimension(10,10)));
    electronicsRese = new WorkerProductionPanel(invis, 
        GameCommands.COMMAND_MINUS_ELECTRONICS_RESEARCH,
        GameCommands.COMMAND_PLUS_ELECTRONICS_RESEARCH, Icons.ICON_ELECTRONICS_TECH,
        "Electronics 100%", "Focus on electronics technology", listener);
    electronicsRese.setAlignmentX(Component.CENTER_ALIGNMENT);
    invis.add(electronicsRese);
    invis.add(Box.createRigidArea(new Dimension(10,10)));
    base.add(invis,BorderLayout.EAST);
    
    this.add(base, BorderLayout.CENTER);
    
    // Bottom panel
    InfoPanel bottomPanel = new InfoPanel();
    bottomPanel.setLayout(new BorderLayout());
    bottomPanel.setTitle(null);
    SpaceButton btn = new SpaceButton("Back to star map", 
        GameCommands.COMMAND_VIEW_STARMAP);
    btn.addActionListener(listener);
    bottomPanel.add(btn,BorderLayout.CENTER);
    
    
    // Add panels to base
    this.add(bottomPanel,BorderLayout.SOUTH);

  }
  
  /**
   * Handle actions for Reseach view.
   * @param arg0 ActionEvent command what player did
   */
  public void handleAction(ActionEvent arg0) {
  }


}
