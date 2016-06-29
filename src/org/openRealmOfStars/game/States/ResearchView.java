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
import org.openRealmOfStars.gui.panels.ResearchTechPanel;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.tech.TechFactory;
import org.openRealmOfStars.player.tech.TechType;

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
  private ResearchTechPanel combatRese;
  /**
   * Defense Research panel
   */
  private ResearchTechPanel defenseRese;
  /**
   * Hull Research panel
   */
  private ResearchTechPanel hullRese;
  /**
   * Improvement Research panel
   */
  private ResearchTechPanel improvementRese;
  /**
   * Propulsion Research panel
   */
  private ResearchTechPanel propulsionRese;
  /**
   * Electronics Research panel
   */
  private ResearchTechPanel electronicsRese;
  
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
    combatRese = new ResearchTechPanel(invis, 
        GameCommands.COMMAND_MINUS_COMBAT_RESEARCH,
        GameCommands.COMMAND_PLUS_COMBAT_RESEARCH, Icons.ICON_COMBAT_TECH,
        "Combat 100%", "Focus on combat technology","Level:1 (1/6)",
        GameCommands.COMMAND_UPGRADE_COMBAT, listener);
    combatRese.setAlignmentX(Component.CENTER_ALIGNMENT);
    invis.add(combatRese);
    invis.add(Box.createRigidArea(new Dimension(10,10)));
    defenseRese = new ResearchTechPanel(invis, 
        GameCommands.COMMAND_MINUS_DEFENSE_RESEARCH,
        GameCommands.COMMAND_PLUS_DEFENSE_RESEARCH, Icons.ICON_DEFENSE_TECH,
        "Defense 100%", "Focus on defense technology","Level:1 (1/6)",
        GameCommands.COMMAND_UPGRADE_DEFENSE, listener);
    defenseRese.setAlignmentX(Component.CENTER_ALIGNMENT);
    invis.add(defenseRese);
    invis.add(Box.createRigidArea(new Dimension(10,10)));
    hullRese = new ResearchTechPanel(invis, 
        GameCommands.COMMAND_MINUS_HULL_RESEARCH,
        GameCommands.COMMAND_PLUS_HULL_RESEARCH, Icons.ICON_HULL_TECH,
        "Hull 100%", "Focus on hull technology", "Level:1 (1/6)",
        GameCommands.COMMAND_UPGRADE_HULL, listener);
    hullRese.setAlignmentX(Component.CENTER_ALIGNMENT);
    invis.add(hullRese);
    invis.add(Box.createRigidArea(new Dimension(10,10)));
    improvementRese = new ResearchTechPanel(invis, 
        GameCommands.COMMAND_MINUS_IMPROVEMENT_RESEARCH,
        GameCommands.COMMAND_PLUS_IMPROVEMENT_RESEARCH, Icons.ICON_IMPROVEMENT_TECH,
        "Planetary improvement 100%", "Focus on planetary improvement technology",
        "Level:1 (1/6)",GameCommands.COMMAND_UPGRADE_IMPROVEMENT, listener);
    improvementRese.setAlignmentX(Component.CENTER_ALIGNMENT);
    invis.add(improvementRese);
    invis.add(Box.createRigidArea(new Dimension(10,10)));
    propulsionRese = new ResearchTechPanel(invis, 
        GameCommands.COMMAND_MINUS_PROPULSION_RESEARCH,
        GameCommands.COMMAND_PLUS_PROPULSION_RESEARCH, Icons.ICON_PROPULSION_TECH,
        "Propulsion 100%", "Focus on propulsion technology", "Level:1 (1/6)",
        GameCommands.COMMAND_UPGRADE_PROPULSION, listener);
    propulsionRese.setAlignmentX(Component.CENTER_ALIGNMENT);
    invis.add(propulsionRese);
    invis.add(Box.createRigidArea(new Dimension(10,10)));
    electronicsRese = new ResearchTechPanel(invis, 
        GameCommands.COMMAND_MINUS_ELECTRONICS_RESEARCH,
        GameCommands.COMMAND_PLUS_ELECTRONICS_RESEARCH, Icons.ICON_ELECTRONICS_TECH,
        "Electronics 100%", "Focus on electronics technology", "Level:1 (1/6)",
        GameCommands.COMMAND_UPGRADE_ELECTRONICS, listener);
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
    
    updatePanel();
    // Add panels to base
    this.add(bottomPanel,BorderLayout.SOUTH);

  }
  
  /**
   * Handle actions for Reseach view.
   * @param arg0 ActionEvent command what player did
   */
  public void handleAction(ActionEvent arg0) {
  }

  
  public void updatePanel() {
    int focus = player.getTechList().getTechFocus(TechType.Combat);
    int level = player.getTechList().getTechLevel(TechType.Combat);
    int subLevel = player.getTechList().getListForTypeAndLevel(TechType.Combat, level).length;
    int maxSubLevel = TechFactory.getListByTechLevel(TechType.Combat, level).length;
    combatRese.setText(TechType.Combat.toString()+" "+focus+"%");
    combatRese.setUpgadeBtnText("Level:"+level+"("+subLevel+"/"+maxSubLevel+")");
    if (subLevel >= Math.ceil(maxSubLevel / 2.0)) {
      combatRese.setEnableUpgradeButton(true);
    }
    
    focus = player.getTechList().getTechFocus(TechType.Defense);
    level = player.getTechList().getTechLevel(TechType.Defense);
    subLevel = player.getTechList().getListForTypeAndLevel(TechType.Defense, level).length;
    maxSubLevel = TechFactory.getListByTechLevel(TechType.Defense, level).length;
    defenseRese.setText(TechType.Defense.toString()+" "+focus+"%");
    defenseRese.setUpgadeBtnText("Level:"+level+"("+subLevel+"/"+maxSubLevel+")");
    if (subLevel >= Math.ceil(maxSubLevel / 2.0)) {
      defenseRese.setEnableUpgradeButton(true);
    }

    focus = player.getTechList().getTechFocus(TechType.Hulls);
    level = player.getTechList().getTechLevel(TechType.Hulls);
    subLevel = player.getTechList().getListForTypeAndLevel(TechType.Hulls, level).length;
    maxSubLevel = TechFactory.getListByTechLevel(TechType.Hulls, level).length;
    hullRese.setText(TechType.Hulls.toString()+" "+focus+"%");
    hullRese.setUpgadeBtnText("Level:"+level+"("+subLevel+"/"+maxSubLevel+")");
    if (subLevel >= Math.ceil(maxSubLevel / 2.0)) {
      hullRese.setEnableUpgradeButton(true);
    }

    focus = player.getTechList().getTechFocus(TechType.Improvements);
    level = player.getTechList().getTechLevel(TechType.Improvements);
    subLevel = player.getTechList().getListForTypeAndLevel(TechType.Improvements, level).length;
    maxSubLevel = TechFactory.getListByTechLevel(TechType.Improvements, level).length;
    improvementRese.setText(TechType.Improvements.toString()+" "+focus+"%");
    improvementRese.setUpgadeBtnText("Level:"+level+"("+subLevel+"/"+maxSubLevel+")");
    if (subLevel >= Math.ceil(maxSubLevel / 2.0)) {
      improvementRese.setEnableUpgradeButton(true);
    }

    focus = player.getTechList().getTechFocus(TechType.Propulsion);
    level = player.getTechList().getTechLevel(TechType.Propulsion);
    subLevel = player.getTechList().getListForTypeAndLevel(TechType.Propulsion, level).length;
    maxSubLevel = TechFactory.getListByTechLevel(TechType.Propulsion, level).length;
    propulsionRese.setText(TechType.Propulsion.toString()+" "+focus+"%");
    propulsionRese.setUpgadeBtnText("Level:"+level+"("+subLevel+"/"+maxSubLevel+")");
    if (subLevel >= Math.ceil(maxSubLevel / 2.0)) {
      propulsionRese.setEnableUpgradeButton(true);
    }

    focus = player.getTechList().getTechFocus(TechType.Electrics);
    level = player.getTechList().getTechLevel(TechType.Electrics);
    subLevel = player.getTechList().getListForTypeAndLevel(TechType.Electrics, level).length;
    maxSubLevel = TechFactory.getListByTechLevel(TechType.Electrics, level).length;
    electronicsRese.setText(TechType.Electrics.toString()+" "+focus+"%");
    electronicsRese.setUpgadeBtnText("Level:"+level+"("+subLevel+"/"+maxSubLevel+")");
    if (subLevel >= Math.ceil(maxSubLevel / 2.0)) {
      electronicsRese.setEnableUpgradeButton(true);
    }
}


}
