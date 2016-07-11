package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.BlackPanel;
import org.openRealmOfStars.gui.GuiStatics;
import org.openRealmOfStars.gui.ListRenderers.TechListRenderer;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.IconLabel;
import org.openRealmOfStars.gui.labels.InfoTextArea;
import org.openRealmOfStars.gui.panels.InvisiblePanel;
import org.openRealmOfStars.gui.panels.ResearchTechPanel;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.player.tech.TechFactory;
import org.openRealmOfStars.player.tech.TechList;
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
   * Techs which have been researched
   */
  private JList<Tech> techList;
  
  /**
   * Info Text for tech
   */
  private InfoTextArea infoText;
  
  /**
   * Create new research for player
   * @param player whom clicked research button
   * @param totalResearch how much player is currently researching per turn
   * @param focusTech, which tech will be focus on start, if null then none
   * @param listener ActionListener
   */
  public ResearchView(PlayerInfo player,int totalResearch,String focusTech, ActionListener listener) {
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
        TechType.Combat.toString()+" 100% 1000 turns", 
        "Focus on combat technology","Level:10 (1/6)",
        GameCommands.COMMAND_UPGRADE_COMBAT, listener);
    combatRese.setAlignmentX(Component.CENTER_ALIGNMENT);
    invis.add(combatRese);
    invis.add(Box.createRigidArea(new Dimension(10,10)));
    defenseRese = new ResearchTechPanel(invis, 
        GameCommands.COMMAND_MINUS_DEFENSE_RESEARCH,
        GameCommands.COMMAND_PLUS_DEFENSE_RESEARCH, Icons.ICON_DEFENSE_TECH,
        TechType.Defense.toString()+" 100% 1000 turns", 
        "Focus on defense technology","Level:10 (1/6)",
        GameCommands.COMMAND_UPGRADE_DEFENSE, listener);
    defenseRese.setAlignmentX(Component.CENTER_ALIGNMENT);
    invis.add(defenseRese);
    invis.add(Box.createRigidArea(new Dimension(10,10)));
    hullRese = new ResearchTechPanel(invis, 
        GameCommands.COMMAND_MINUS_HULL_RESEARCH,
        GameCommands.COMMAND_PLUS_HULL_RESEARCH, Icons.ICON_HULL_TECH,
        TechType.Hulls.toString()+" 100% 1000 turns", 
        "Focus on hull technology", "Level:10 (1/6)",
        GameCommands.COMMAND_UPGRADE_HULL, listener);
    hullRese.setAlignmentX(Component.CENTER_ALIGNMENT);
    invis.add(hullRese);
    invis.add(Box.createRigidArea(new Dimension(10,10)));
    improvementRese = new ResearchTechPanel(invis, 
        GameCommands.COMMAND_MINUS_IMPROVEMENT_RESEARCH,
        GameCommands.COMMAND_PLUS_IMPROVEMENT_RESEARCH, Icons.ICON_IMPROVEMENT_TECH,
        TechType.Improvements.toString()+" 100% 1000 turns", 
        "Focus on planetary improvement technology",
        "Level:10 (1/6)",GameCommands.COMMAND_UPGRADE_IMPROVEMENT, listener);
    improvementRese.setAlignmentX(Component.CENTER_ALIGNMENT);
    invis.add(improvementRese);
    invis.add(Box.createRigidArea(new Dimension(10,10)));
    propulsionRese = new ResearchTechPanel(invis, 
        GameCommands.COMMAND_MINUS_PROPULSION_RESEARCH,
        GameCommands.COMMAND_PLUS_PROPULSION_RESEARCH, Icons.ICON_PROPULSION_TECH,
        TechType.Propulsion.toString()+" 100% 1000 turns", 
        "Focus on propulsion technology", "Level:10 (1/6)",
        GameCommands.COMMAND_UPGRADE_PROPULSION, listener);
    propulsionRese.setAlignmentX(Component.CENTER_ALIGNMENT);
    invis.add(propulsionRese);
    invis.add(Box.createRigidArea(new Dimension(10,10)));
    electronicsRese = new ResearchTechPanel(invis, 
        GameCommands.COMMAND_MINUS_ELECTRONICS_RESEARCH,
        GameCommands.COMMAND_PLUS_ELECTRONICS_RESEARCH, Icons.ICON_ELECTRONICS_TECH,
        TechType.Electrics.toString()+" 100% 1000 turns", 
        "Focus on electronics technology", "Level:10 (1/6)",
        GameCommands.COMMAND_UPGRADE_ELECTRONICS, listener);
    electronicsRese.setAlignmentX(Component.CENTER_ALIGNMENT);
    invis.add(electronicsRese);
    invis.add(Box.createRigidArea(new Dimension(10,10)));
    base.add(invis,BorderLayout.EAST);
    
    invis = new InvisiblePanel(base);
    invis.setLayout(new BoxLayout(invis, BoxLayout.X_AXIS));
    Tech[] techs = player.getTechList().getList();
    techList = new JList<>(techs);
    techList.setCellRenderer(new TechListRenderer());
    JScrollPane scroll = new JScrollPane(techList);
    techList.setBackground(Color.BLACK);
    techList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    if (focusTech != null) {
      for (int i=0;i<techs.length;i++) {
        if (techs[i].getName().equals(focusTech)) {
          techList.setSelectedIndex(i);
          break;
        }
      }
    }
    invis.add(scroll);
    invis.add(Box.createRigidArea(new Dimension(10,10)));
    infoText = new InfoTextArea(25, 35);
    infoText.setEditable(false);
    infoText.setFont(GuiStatics.getFontCubellanSmaller());
    invis.add(infoText);
    
    base.add(invis,BorderLayout.WEST);
    
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
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_ANIMATION_TIMER)) {
      if (techList.getSelectedIndex() != -1) {
        Tech tech = techList.getSelectedValue();
        infoText.setText(tech.getTechInfo());
      } else {
        infoText.setText("");
      }
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_PLUS_COMBAT_RESEARCH)) {
      int value = player.getTechList().getTechFocus(TechType.Combat);
      if (value <= 100-TechList.FINE_TUNE_VALUE) {
        value = value +TechList.FINE_TUNE_VALUE;
        player.getTechList().setTechFocus(TechType.Combat, value);
        updatePanel();
      }
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_MINUS_COMBAT_RESEARCH)) {
      int value = player.getTechList().getTechFocus(TechType.Combat);
      if (value >= TechList.FINE_TUNE_VALUE) {
        value = value -TechList.FINE_TUNE_VALUE;
        player.getTechList().setTechFocus(TechType.Combat, value);
        updatePanel();
      }
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_UPGRADE_COMBAT)) {
      // Assuming that upgrade button is disabled so no need to make check here
      int lvl = player.getTechList().getTechLevel(TechType.Combat);
      lvl = lvl +1;
      player.getTechList().setTechLevel(TechType.Combat, lvl);
      combatRese.setEnableUpgradeButton(false);
      updatePanel();
    }

    if (arg0.getActionCommand().equals(GameCommands.COMMAND_PLUS_DEFENSE_RESEARCH)) {
      int value = player.getTechList().getTechFocus(TechType.Defense);
      if (value <= 100-TechList.FINE_TUNE_VALUE) {
        value = value +TechList.FINE_TUNE_VALUE;
        player.getTechList().setTechFocus(TechType.Defense, value);
        updatePanel();
      }
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_MINUS_DEFENSE_RESEARCH)) {
      int value = player.getTechList().getTechFocus(TechType.Defense);
      if (value >= TechList.FINE_TUNE_VALUE) {
        value = value -TechList.FINE_TUNE_VALUE;
        player.getTechList().setTechFocus(TechType.Defense, value);
        updatePanel();
      }
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_UPGRADE_DEFENSE)) {
      // Assuming that upgrade button is disabled so no need to make check here
      int lvl = player.getTechList().getTechLevel(TechType.Defense);
      lvl = lvl +1;
      player.getTechList().setTechLevel(TechType.Defense, lvl);
      defenseRese.setEnableUpgradeButton(false);
      updatePanel();
    }

    if (arg0.getActionCommand().equals(GameCommands.COMMAND_PLUS_HULL_RESEARCH)) {
      int value = player.getTechList().getTechFocus(TechType.Hulls);
      if (value <= 100-TechList.FINE_TUNE_VALUE) {
        value = value +TechList.FINE_TUNE_VALUE;
        player.getTechList().setTechFocus(TechType.Hulls, value);
        updatePanel();
      }
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_MINUS_HULL_RESEARCH)) {
      int value = player.getTechList().getTechFocus(TechType.Hulls);
      if (value >= TechList.FINE_TUNE_VALUE) {
        value = value -TechList.FINE_TUNE_VALUE;
        player.getTechList().setTechFocus(TechType.Hulls, value);
        updatePanel();
      }
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_UPGRADE_HULL)) {
      // Assuming that upgrade button is disabled so no need to make check here
      int lvl = player.getTechList().getTechLevel(TechType.Hulls);
      lvl = lvl +1;
      player.getTechList().setTechLevel(TechType.Hulls, lvl);
      hullRese.setEnableUpgradeButton(false);
      updatePanel();
    }
    
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_PLUS_IMPROVEMENT_RESEARCH)) {
      int value = player.getTechList().getTechFocus(TechType.Improvements);
      if (value <= 100-TechList.FINE_TUNE_VALUE) {
        value = value +TechList.FINE_TUNE_VALUE;
        player.getTechList().setTechFocus(TechType.Improvements, value);
        updatePanel();
      }
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_MINUS_IMPROVEMENT_RESEARCH)) {
      int value = player.getTechList().getTechFocus(TechType.Improvements);
      if (value >= TechList.FINE_TUNE_VALUE) {
        value = value -TechList.FINE_TUNE_VALUE;
        player.getTechList().setTechFocus(TechType.Improvements, value);
        updatePanel();
      }
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_UPGRADE_IMPROVEMENT)) {
      // Assuming that upgrade button is disabled so no need to make check here
      int lvl = player.getTechList().getTechLevel(TechType.Improvements);
      lvl = lvl +1;
      player.getTechList().setTechLevel(TechType.Improvements, lvl);
      improvementRese.setEnableUpgradeButton(false);
      updatePanel();
    }

    if (arg0.getActionCommand().equals(GameCommands.COMMAND_PLUS_PROPULSION_RESEARCH)) {
      int value = player.getTechList().getTechFocus(TechType.Propulsion);
      if (value <= 100-TechList.FINE_TUNE_VALUE) {
        value = value +TechList.FINE_TUNE_VALUE;
        player.getTechList().setTechFocus(TechType.Propulsion, value);
        updatePanel();
      }
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_MINUS_PROPULSION_RESEARCH)) {
      int value = player.getTechList().getTechFocus(TechType.Propulsion);
      if (value >= TechList.FINE_TUNE_VALUE) {
        value = value -TechList.FINE_TUNE_VALUE;
        player.getTechList().setTechFocus(TechType.Propulsion, value);
        updatePanel();
      }
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_UPGRADE_PROPULSION)) {
      // Assuming that upgrade button is disabled so no need to make check here
      int lvl = player.getTechList().getTechLevel(TechType.Propulsion);
      lvl = lvl +1;
      player.getTechList().setTechLevel(TechType.Propulsion, lvl);
      propulsionRese.setEnableUpgradeButton(false);
      updatePanel();
    }

    if (arg0.getActionCommand().equals(GameCommands.COMMAND_PLUS_ELECTRONICS_RESEARCH)) {
      int value = player.getTechList().getTechFocus(TechType.Electrics);
      if (value <= 100-TechList.FINE_TUNE_VALUE) {
        value = value +TechList.FINE_TUNE_VALUE;
        player.getTechList().setTechFocus(TechType.Electrics, value);
        updatePanel();
      }
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_MINUS_ELECTRONICS_RESEARCH)) {
      int value = player.getTechList().getTechFocus(TechType.Electrics);
      if (value >= TechList.FINE_TUNE_VALUE) {
        value = value -TechList.FINE_TUNE_VALUE;
        player.getTechList().setTechFocus(TechType.Electrics, value);
        updatePanel();
      }
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_UPGRADE_ELECTRONICS)) {
      // Assuming that upgrade button is disabled so no need to make check here
      int lvl = player.getTechList().getTechLevel(TechType.Electrics);
      lvl = lvl +1;
      player.getTechList().setTechLevel(TechType.Electrics, lvl);
      electronicsRese.setEnableUpgradeButton(false);
      updatePanel();
    }

  }

  
  public void updatePanel() {
    int focus = player.getTechList().getTechFocus(TechType.Combat);
    int level = player.getTechList().getTechLevel(TechType.Combat);
    int subLevel = player.getTechList().getListForTypeAndLevel(TechType.Combat, level).length;
    int maxSubLevel = TechFactory.getListByTechLevel(TechType.Combat, level).length;
    int required = TechFactory.getTechCost(level);
    int turns = (int) Math.ceil((required-player.getTechList().
        getTechResearchPoints(TechType.Combat))/(focus*totalResearch/100.0));
    String turnsInStr = turns+" turns";
    if (turns > 10000) {
      turnsInStr = "never";
    }
    combatRese.setText(TechType.Combat.toString()+" "+focus+"% "+turnsInStr);
    combatRese.setUpgadeBtnText("Level:"+level+"("+subLevel+"/"+maxSubLevel+")");
    if (subLevel >= Math.ceil(maxSubLevel / 2.0) && level < 10) {
      combatRese.setEnableUpgradeButton(true);
      combatRese.setUpgadeBtnToolTip("<html>Upgrade combat to "+level+1+" level.<br>"
          + " By upgrading you skip rest of technologies on your current level.</html>");
    }
    
    focus = player.getTechList().getTechFocus(TechType.Defense);
    level = player.getTechList().getTechLevel(TechType.Defense);
    subLevel = player.getTechList().getListForTypeAndLevel(TechType.Defense, level).length;
    maxSubLevel = TechFactory.getListByTechLevel(TechType.Defense, level).length;
    required = TechFactory.getTechCost(level);
    turns = (int) Math.ceil((required-player.getTechList().
        getTechResearchPoints(TechType.Defense))/(focus*totalResearch/100.0));
    turnsInStr = turns+" turns";
    if (turns > 10000) {
      turnsInStr = "never";
    }
    defenseRese.setText(TechType.Defense.toString()+" "+focus+"% "+turnsInStr);
    defenseRese.setUpgadeBtnText("Level:"+level+"("+subLevel+"/"+maxSubLevel+")");
    if (subLevel >= Math.ceil(maxSubLevel / 2.0) && level < 10) {
      defenseRese.setEnableUpgradeButton(true);
      defenseRese.setUpgadeBtnToolTip("<html>Upgrade defense to "+level+1+" level.<br>"
          + " By upgrading you skip rest of technologies on your current level.</html>");
    }

    focus = player.getTechList().getTechFocus(TechType.Hulls);
    level = player.getTechList().getTechLevel(TechType.Hulls);
    subLevel = player.getTechList().getListForTypeAndLevel(TechType.Hulls, level).length;
    maxSubLevel = TechFactory.getListByTechLevel(TechType.Hulls, level).length;
    required = TechFactory.getTechCost(level);
    turns = (int) Math.ceil((required-player.getTechList().
        getTechResearchPoints(TechType.Hulls))/(focus*totalResearch/100.0));
    turnsInStr = turns+" turns";
    if (turns > 10000) {
      turnsInStr = "never";
    }
    hullRese.setText(TechType.Hulls.toString()+" "+focus+"% "+turnsInStr);
    hullRese.setUpgadeBtnText("Level:"+level+"("+subLevel+"/"+maxSubLevel+")");
    if (subLevel >= Math.ceil(maxSubLevel / 2.0) && level < 10) {
      hullRese.setEnableUpgradeButton(true);
      hullRese.setUpgadeBtnToolTip("<html>Upgrade hulls to "+level+1+" level.<br>"
          + " By upgrading you skip rest of technologies on your current level.</html>");
    }

    focus = player.getTechList().getTechFocus(TechType.Improvements);
    level = player.getTechList().getTechLevel(TechType.Improvements);
    subLevel = player.getTechList().getListForTypeAndLevel(TechType.Improvements, level).length;
    maxSubLevel = TechFactory.getListByTechLevel(TechType.Improvements, level).length;
    required = TechFactory.getTechCost(level);
    turns = (int) Math.ceil((required-player.getTechList().
        getTechResearchPoints(TechType.Improvements))/(focus*totalResearch/100.0));
    turnsInStr = turns+" turns";
    if (turns > 10000) {
      turnsInStr = "never";
    }
    improvementRese.setText(TechType.Improvements.toString()+" "+focus+"% "+turnsInStr);
    improvementRese.setUpgadeBtnText("Level:"+level+"("+subLevel+"/"+maxSubLevel+")");
    if (subLevel >= Math.ceil(maxSubLevel / 2.0) && level < 10) {
      improvementRese.setEnableUpgradeButton(true);
      improvementRese.setUpgadeBtnToolTip("<html>Upgrade improvements to "+level+1+" level.<br>"
          + " By upgrading you skip rest of technologies on your current level.</html>");
    }

    focus = player.getTechList().getTechFocus(TechType.Propulsion);
    level = player.getTechList().getTechLevel(TechType.Propulsion);
    subLevel = player.getTechList().getListForTypeAndLevel(TechType.Propulsion, level).length;
    maxSubLevel = TechFactory.getListByTechLevel(TechType.Propulsion, level).length;
    required = TechFactory.getTechCost(level);
    turns = (int) Math.ceil((required-player.getTechList().
        getTechResearchPoints(TechType.Propulsion))/(focus*totalResearch/100.0));
    turnsInStr = turns+" turns";
    if (turns > 10000) {
      turnsInStr = "never";
    }
    propulsionRese.setText(TechType.Propulsion.toString()+" "+focus+"% "+turnsInStr);
    propulsionRese.setUpgadeBtnText("Level:"+level+"("+subLevel+"/"+maxSubLevel+")");
    if (subLevel >= Math.ceil(maxSubLevel / 2.0) && level < 10) {
      propulsionRese.setEnableUpgradeButton(true);
      propulsionRese.setUpgadeBtnToolTip("<html>Upgrade propulsion to "+level+1+" level.<br>"
          + " By upgrading you skip rest of technologies on your current level.</html>");
    }

    focus = player.getTechList().getTechFocus(TechType.Electrics);
    level = player.getTechList().getTechLevel(TechType.Electrics);
    subLevel = player.getTechList().getListForTypeAndLevel(TechType.Electrics, level).length;
    maxSubLevel = TechFactory.getListByTechLevel(TechType.Electrics, level).length;
    required = TechFactory.getTechCost(level);
    turns = (int) Math.ceil((required-player.getTechList().
        getTechResearchPoints(TechType.Electrics))/(focus*totalResearch/100.0));
    turnsInStr = turns+" turns";
    if (turns > 10000) {
      turnsInStr = "never";
    }
    electronicsRese.setText(TechType.Electrics.toString()+" "+focus+"% "+turnsInStr);
    electronicsRese.setUpgadeBtnText("Level:"+level+"("+subLevel+"/"+maxSubLevel+")");
    if (subLevel >= Math.ceil(maxSubLevel / 2.0) && level < 10) {
      electronicsRese.setEnableUpgradeButton(true);
      electronicsRese.setUpgadeBtnToolTip("<html>Upgrade electronics to "+level+1+" level.<br>"
          + " By upgrading you skip rest of technologies on your current level.</html>");
    }
}


}
