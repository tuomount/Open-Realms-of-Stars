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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.openRealmOfStars.audio.soundeffect.SoundPlayer;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.ListRenderers.ArtifactListRenderer;
import org.openRealmOfStars.gui.ListRenderers.TechListRenderer;
import org.openRealmOfStars.gui.buttons.IconButton;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.InfoTextArea;
import org.openRealmOfStars.gui.labels.SpaceLabel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.ResearchTechPanel;
import org.openRealmOfStars.gui.panels.SpaceGreyPanel;
import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.artifact.Artifact;
import org.openRealmOfStars.player.leader.Leader;
import org.openRealmOfStars.player.leader.LeaderUtility;
import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.player.tech.TechFactory;
import org.openRealmOfStars.player.tech.TechList;
import org.openRealmOfStars.player.tech.TechType;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016-2019,2021-2023  Tuomo Untinen
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
public class ResearchView extends BlackPanel implements ListSelectionListener {

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
   * Artifacts which have been researched.
   */
  private JList<Artifact> artifactList;
  /**
   * Info Text for tech
   */
  private InfoTextArea infoText;

  /**
   * Maximum game length
   */
  private int maximumGameLength;
  /**
   * Choice whether to playe sound from sliders or not.
   * -1 Never player sounds
   * 0 Do not play sound, but set to 1 when timer occurs
   * 1 Play sound
   */
  private int playSoundFromSliders = -1;
  /**
   * Create new research for player
   * @param player whom clicked research button
   * @param totalResearch how much player is currently researching per turn
   * @param focusTech which tech will be focus on start, if null then none
   * @param gameLength Maximum game length in turns
   * @param listener ActionListener
   */
  public ResearchView(final PlayerInfo player, final int totalResearch,
      final String focusTech, final int gameLength,
      final ActionListener listener) {
    this.player = player;
    this.totalResearch = totalResearch;
    maximumGameLength = gameLength;
    InfoPanel base = new InfoPanel();
    base.setTitle("Research");
    this.setLayout(new BorderLayout());
    base.setLayout(new BorderLayout());
    InfoPanel focusPanel = new InfoPanel();
    focusPanel.setTitle("Research focus");
    focusPanel.setLayout(new BoxLayout(focusPanel, BoxLayout.Y_AXIS));
    // focusPanel.add(Box.createRigidArea(new Dimension(10,15)));
    combatRese = new ResearchTechPanel(
        GameCommands.COMMAND_MINUS_COMBAT_RESEARCH,
        GameCommands.COMMAND_PLUS_COMBAT_RESEARCH, Icons.ICON_COMBAT_TECH,
        TechType.Combat.toString() + " 100% 1000 star years",
        "Level:10 (1/6)", GameCommands.COMMAND_UPGRADE_COMBAT, 16,
        GameCommands.COMMAND_SLIDER_COMBAT_RESEARCH, listener);
    combatRese.setLabelToolTip("Focus on combat technology");
    combatRese.setAlignmentX(Component.CENTER_ALIGNMENT);
    SpaceGreyPanel techPane = new SpaceGreyPanel();
    techPane.setLayout(new BoxLayout(techPane, BoxLayout.X_AXIS));
    IconButton iBtn = new IconButton(
        Icons.getIconByName(Icons.ICON_COMBAT_TECH),
        Icons.getIconByName(Icons.ICON_COMBAT_TECH), false,
        GameCommands.COMMAND_COMBAT_INFO, null);
    iBtn.addActionListener(listener);
    iBtn.setToolTipText("Show information about combat technology");
    techPane.add(iBtn);
    techPane.add(combatRese);
    focusPanel.add(techPane);
    focusPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    defenseRese = new ResearchTechPanel(
        GameCommands.COMMAND_MINUS_DEFENSE_RESEARCH,
        GameCommands.COMMAND_PLUS_DEFENSE_RESEARCH, Icons.ICON_DEFENSE_TECH,
        TechType.Defense.toString() + " 100% 1000 star years", "Level:10 (1/6)",
        GameCommands.COMMAND_UPGRADE_DEFENSE, 16,
        GameCommands.COMMAND_SLIDER_DEFENSE_RESEARCH, listener);
    defenseRese.setLabelToolTip("Focus on defense technology");
    defenseRese.setAlignmentX(Component.CENTER_ALIGNMENT);
    techPane = new SpaceGreyPanel();
    techPane.setLayout(new BoxLayout(techPane, BoxLayout.X_AXIS));
    iBtn = new IconButton(
        Icons.getIconByName(Icons.ICON_DEFENSE_TECH),
        Icons.getIconByName(Icons.ICON_DEFENSE_TECH), false,
        GameCommands.COMMAND_DEFENSE_INFO, null);
    iBtn.addActionListener(listener);
    iBtn.setToolTipText("Show information about defense technology");
    techPane.add(iBtn);
    techPane.add(defenseRese);
    focusPanel.add(techPane);
    focusPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    hullRese = new ResearchTechPanel(
        GameCommands.COMMAND_MINUS_HULL_RESEARCH,
        GameCommands.COMMAND_PLUS_HULL_RESEARCH, Icons.ICON_HULL_TECH,
        TechType.Hulls.toString() + " 100% 1000 star years", "Level:10 (1/6)",
        GameCommands.COMMAND_UPGRADE_HULL, 16,
        GameCommands.COMMAND_SLIDER_HULL_RESEARCH, listener);
    hullRese.setLabelToolTip("Focus on hull technology");
    hullRese.setAlignmentX(Component.CENTER_ALIGNMENT);
    techPane = new SpaceGreyPanel();
    techPane.setLayout(new BoxLayout(techPane, BoxLayout.X_AXIS));
    iBtn = new IconButton(
        Icons.getIconByName(Icons.ICON_HULL_TECH),
        Icons.getIconByName(Icons.ICON_HULL_TECH), false,
        GameCommands.COMMAND_HULL_INFO, null);
    iBtn.setToolTipText("Show information about hull technology");
    iBtn.addActionListener(listener);
    techPane.add(iBtn);
    techPane.add(hullRese);
    focusPanel.add(techPane);
    focusPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    improvementRese = new ResearchTechPanel(
        GameCommands.COMMAND_MINUS_IMPROVEMENT_RESEARCH,
        GameCommands.COMMAND_PLUS_IMPROVEMENT_RESEARCH,
        Icons.ICON_IMPROVEMENT_TECH,
        TechType.Improvements.toString() + " 100% 1000 star years",
        "Level:10 (1/6)", GameCommands.COMMAND_UPGRADE_IMPROVEMENT, 16,
        GameCommands.COMMAND_SLIDER_IMPROVEMENT_RESEARCH, listener);
    improvementRese.setLabelToolTip("Focus on planetary improvement "
        + "technology");
    improvementRese.setAlignmentX(Component.CENTER_ALIGNMENT);
    techPane = new SpaceGreyPanel();
    techPane.setLayout(new BoxLayout(techPane, BoxLayout.X_AXIS));
    iBtn = new IconButton(
        Icons.getIconByName(Icons.ICON_IMPROVEMENT_TECH),
        Icons.getIconByName(Icons.ICON_IMPROVEMENT_TECH), false,
        GameCommands.COMMAND_IMPROVEMENT_INFO, null);
    iBtn.addActionListener(listener);
    iBtn.setToolTipText("Show information about planetary improvement "
        + "technology");
    techPane.add(iBtn);
    techPane.add(improvementRese);
    focusPanel.add(techPane);
    focusPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    propulsionRese = new ResearchTechPanel(
        GameCommands.COMMAND_MINUS_PROPULSION_RESEARCH,
        GameCommands.COMMAND_PLUS_PROPULSION_RESEARCH,
        Icons.ICON_PROPULSION_TECH,
        TechType.Propulsion.toString() + " 100% 1000 star years",
        "Level:10 (1/6)",
        GameCommands.COMMAND_UPGRADE_PROPULSION, 16,
        GameCommands.COMMAND_SLIDER_PROPULSION_RESEARCH, listener);
    propulsionRese.setLabelToolTip("Focus on propulsion technology");
    propulsionRese.setAlignmentX(Component.CENTER_ALIGNMENT);
    techPane = new SpaceGreyPanel();
    techPane.setLayout(new BoxLayout(techPane, BoxLayout.X_AXIS));
    iBtn = new IconButton(
        Icons.getIconByName(Icons.ICON_PROPULSION_TECH),
        Icons.getIconByName(Icons.ICON_PROPULSION_TECH), false,
        GameCommands.COMMAND_PROPULSION_INFO, null);
    iBtn.addActionListener(listener);
    iBtn.setToolTipText("Show information about propulsion technology");
    techPane.add(iBtn);
    techPane.add(propulsionRese);
    focusPanel.add(techPane);
    focusPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    electronicsRese = new ResearchTechPanel(
        GameCommands.COMMAND_MINUS_ELECTRONICS_RESEARCH,
        GameCommands.COMMAND_PLUS_ELECTRONICS_RESEARCH,
        Icons.ICON_ELECTRONICS_TECH,
        TechType.Electrics.toString() + " 100% 1000 star years",
        "Level:10 (1/6)",
        GameCommands.COMMAND_UPGRADE_ELECTRONICS, 16,
        GameCommands.COMMAND_SLIDER_ELECTRONICS_RESEARCH, listener);
    electronicsRese.setLabelToolTip("Focus on electronics technology");
    electronicsRese.setAlignmentX(Component.CENTER_ALIGNMENT);
    techPane = new SpaceGreyPanel();
    techPane.setLayout(new BoxLayout(techPane, BoxLayout.X_AXIS));
    iBtn = new IconButton(
        Icons.getIconByName(Icons.ICON_ELECTRONICS_TECH),
        Icons.getIconByName(Icons.ICON_ELECTRONICS_TECH), false,
        GameCommands.COMMAND_ELECTRONICS_INFO, null);
    iBtn.addActionListener(listener);
    iBtn.setToolTipText("Show information about electronics technology");
    techPane.add(iBtn);
    techPane.add(electronicsRese);
    focusPanel.add(techPane);
    focusPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    InfoPanel artifactPanel = new InfoPanel();
    artifactPanel.setTitle("Ancient artifacts");
    artifactPanel.setLayout(new BoxLayout(artifactPanel, BoxLayout.Y_AXIS));
    Leader scientist = LeaderUtility.getBestScientist(player);
    String scientistName = "No scientist available";
    if (scientist != null) {
      scientistName = scientist.getCallName();
    }
    SpaceLabel label = new SpaceLabel("Best scientist: " + scientistName);
    artifactPanel.add(label);
    artifactPanel.add(Box.createRigidArea(new Dimension(2, 2)));
    label = new SpaceLabel("Discovered artifacts: "
        + player.getArtifactLists().getDiscoveredArtifacts().length);
    artifactPanel.add(label);
    artifactPanel.add(Box.createRigidArea(new Dimension(2, 2)));
    label = new SpaceLabel("Researched artifacts: ");
    artifactPanel.add(label);
    Artifact[] artifacts = player.getArtifactLists().getResearchedArtifacts();
    artifactList = new JList<>(artifacts);
    artifactList.setCellRenderer(new ArtifactListRenderer());
    artifactList.addListSelectionListener(this);
    JScrollPane scroll = new JScrollPane(artifactList);
    scroll.setBackground(GuiStatics.getDeepSpaceDarkerColor());
    artifactList.setBackground(Color.BLACK);
    artifactList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    artifactPanel.add(scroll);
    focusPanel.add(artifactPanel);
    base.add(focusPanel, BorderLayout.EAST);

    SpaceGreyPanel greyPanel = new SpaceGreyPanel();
    greyPanel.setLayout(new BoxLayout(greyPanel, BoxLayout.X_AXIS));
    Tech[] techs = player.getTechList().getList();
    techList = new JList<>(techs);
    techList.setCellRenderer(new TechListRenderer());
    techList.addListSelectionListener(this);
    scroll = new JScrollPane(techList);
    scroll.setBackground(GuiStatics.getDeepSpaceDarkerColor());
    techList.setBackground(Color.BLACK);
    techList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    greyPanel.add(scroll);
    greyPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    infoText = new InfoTextArea(20, 35);
    infoText.setEditable(false);
    infoText.setFont(GuiStatics.getFontCubellanSmaller());
    infoText.setCharacterWidth(8);
    greyPanel.add(infoText);
    greyPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    if (focusTech != null) {
      for (int i = 0; i < techs.length; i++) {
        if (techs[i].getName().equals(focusTech)) {
          techList.setSelectedIndex(i);
          break;
        }
      }
    }

    base.add(greyPanel, BorderLayout.CENTER);

    this.add(base, BorderLayout.CENTER);

    // Bottom panel
    InfoPanel bottomPanel = new InfoPanel();
    bottomPanel.setLayout(new BorderLayout());
    bottomPanel.setTitle(null);
    SpaceButton btn = new SpaceButton("Back to star map",
        GameCommands.COMMAND_VIEW_STARMAP);
    btn.addActionListener(listener);
    bottomPanel.add(btn, BorderLayout.CENTER);

    playSoundFromSliders = -1;
    updatePanel();
    playSoundFromSliders = 1;
    // Add panels to base
    this.add(bottomPanel, BorderLayout.SOUTH);

  }

  /**
   * Handle actions for Research view.
   * @param arg0 ActionEvent command what player did
   */
  public void handleAction(final ActionEvent arg0) {
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_ANIMATION_TIMER)) {
      if (playSoundFromSliders == 0) {
        playSoundFromSliders = 1;
      }
      return;
    }
    if (arg0.getActionCommand()
        .equals(GameCommands.COMMAND_SLIDER_COMBAT_RESEARCH)) {
      int value = combatRese.getSliderValue();
      player.getTechList().setTechFocus(TechType.Combat, value);
      if (playSoundFromSliders == 1) {
        SoundPlayer.playMenuSound();
        playSoundFromSliders = 0;
      }
      updatePanel();
      return;
    }
    if (arg0.getActionCommand()
        .equals(GameCommands.COMMAND_PLUS_COMBAT_RESEARCH)) {
      int value = player.getTechList().getTechFocus(TechType.Combat);
      if (value <= 100 - TechList.FINE_TUNE_VALUE) {
        value = value + TechList.FINE_TUNE_VALUE;
        player.getTechList().setTechFocus(TechType.Combat, value);
        updateTechInfo(TechType.Combat);
        SoundPlayer.playMenuSound();
        playSoundFromSliders = 0;
        updatePanel();
      }
      return;
    }
    if (arg0.getActionCommand()
        .equals(GameCommands.COMMAND_MINUS_COMBAT_RESEARCH)) {
      int value = player.getTechList().getTechFocus(TechType.Combat);
      if (value >= TechList.FINE_TUNE_VALUE) {
        value = value - TechList.FINE_TUNE_VALUE;
        player.getTechList().setTechFocus(TechType.Combat, value);
        updateTechInfo(TechType.Combat);
        SoundPlayer.playMenuSound();
        playSoundFromSliders = 0;
        updatePanel();
      }
      return;
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_UPGRADE_COMBAT)) {
      // Assuming that upgrade button is disabled so no need to make check here
      int lvl = player.getTechList().getTechLevel(TechType.Combat);
      lvl = lvl + 1;
      player.getTechList().setTechLevel(TechType.Combat, lvl);
      combatRese.setEnableUpgradeButton(false);
      updateTechInfo(TechType.Combat);
      SoundPlayer.playMenuSound();
      updatePanel();
      return;
    }

    if (arg0.getActionCommand()
        .equals(GameCommands.COMMAND_SLIDER_DEFENSE_RESEARCH)) {
      int value = defenseRese.getSliderValue();
      player.getTechList().setTechFocus(TechType.Defense, value);
      if (playSoundFromSliders == 1) {
        SoundPlayer.playMenuSound();
        playSoundFromSliders = 0;
      }
      updatePanel();
      return;
    }
    if (arg0.getActionCommand()
        .equals(GameCommands.COMMAND_PLUS_DEFENSE_RESEARCH)) {
      int value = player.getTechList().getTechFocus(TechType.Defense);
      if (value <= 100 - TechList.FINE_TUNE_VALUE) {
        value = value + TechList.FINE_TUNE_VALUE;
        player.getTechList().setTechFocus(TechType.Defense, value);
        updateTechInfo(TechType.Defense);
        SoundPlayer.playMenuSound();
        playSoundFromSliders = 0;
        updatePanel();
      }
      return;
    }
    if (arg0.getActionCommand()
        .equals(GameCommands.COMMAND_MINUS_DEFENSE_RESEARCH)) {
      int value = player.getTechList().getTechFocus(TechType.Defense);
      if (value >= TechList.FINE_TUNE_VALUE) {
        value = value - TechList.FINE_TUNE_VALUE;
        player.getTechList().setTechFocus(TechType.Defense, value);
        SoundPlayer.playMenuSound();
        playSoundFromSliders = 0;
        updatePanel();
      }
      return;
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_UPGRADE_DEFENSE)) {
      // Assuming that upgrade button is disabled so no need to make check here
      int lvl = player.getTechList().getTechLevel(TechType.Defense);
      lvl = lvl + 1;
      player.getTechList().setTechLevel(TechType.Defense, lvl);
      defenseRese.setEnableUpgradeButton(false);
      updateTechInfo(TechType.Defense);
      SoundPlayer.playMenuSound();
      updatePanel();
    }

    if (arg0.getActionCommand()
        .equals(GameCommands.COMMAND_SLIDER_HULL_RESEARCH)) {
      int value = hullRese.getSliderValue();
      player.getTechList().setTechFocus(TechType.Hulls, value);
      if (playSoundFromSliders == 1) {
        SoundPlayer.playMenuSound();
        playSoundFromSliders = 0;
      }
      updatePanel();
    }
    if (arg0.getActionCommand()
        .equals(GameCommands.COMMAND_PLUS_HULL_RESEARCH)) {
      int value = player.getTechList().getTechFocus(TechType.Hulls);
      if (value <= 100 - TechList.FINE_TUNE_VALUE) {
        value = value + TechList.FINE_TUNE_VALUE;
        player.getTechList().setTechFocus(TechType.Hulls, value);
        updateTechInfo(TechType.Hulls);
        SoundPlayer.playMenuSound();
        playSoundFromSliders = 0;
        updatePanel();
      }
    }
    if (arg0.getActionCommand()
        .equals(GameCommands.COMMAND_MINUS_HULL_RESEARCH)) {
      int value = player.getTechList().getTechFocus(TechType.Hulls);
      if (value >= TechList.FINE_TUNE_VALUE) {
        value = value - TechList.FINE_TUNE_VALUE;
        player.getTechList().setTechFocus(TechType.Hulls, value);
        updateTechInfo(TechType.Hulls);
        SoundPlayer.playMenuSound();
        playSoundFromSliders = 0;
        updatePanel();
      }
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_UPGRADE_HULL)) {
      // Assuming that upgrade button is disabled so no need to make check here
      int lvl = player.getTechList().getTechLevel(TechType.Hulls);
      lvl = lvl + 1;
      player.getTechList().setTechLevel(TechType.Hulls, lvl);
      hullRese.setEnableUpgradeButton(false);
      updateTechInfo(TechType.Hulls);
      SoundPlayer.playMenuSound();
      updatePanel();
    }

    if (arg0.getActionCommand()
        .equals(GameCommands.COMMAND_SLIDER_IMPROVEMENT_RESEARCH)) {
      int value = improvementRese.getSliderValue();
      player.getTechList().setTechFocus(TechType.Improvements, value);
      if (playSoundFromSliders == 1) {
        SoundPlayer.playMenuSound();
        playSoundFromSliders = 0;
      }
      updatePanel();
    }
    if (arg0.getActionCommand()
        .equals(GameCommands.COMMAND_PLUS_IMPROVEMENT_RESEARCH)) {
      int value = player.getTechList().getTechFocus(TechType.Improvements);
      if (value <= 100 - TechList.FINE_TUNE_VALUE) {
        value = value + TechList.FINE_TUNE_VALUE;
        player.getTechList().setTechFocus(TechType.Improvements, value);
        updateTechInfo(TechType.Improvements);
        SoundPlayer.playMenuSound();
        playSoundFromSliders = 0;
        updatePanel();
      }
    }
    if (arg0.getActionCommand()
        .equals(GameCommands.COMMAND_MINUS_IMPROVEMENT_RESEARCH)) {
      int value = player.getTechList().getTechFocus(TechType.Improvements);
      if (value >= TechList.FINE_TUNE_VALUE) {
        value = value - TechList.FINE_TUNE_VALUE;
        player.getTechList().setTechFocus(TechType.Improvements, value);
        updateTechInfo(TechType.Improvements);
        SoundPlayer.playMenuSound();
        playSoundFromSliders = 0;
        updatePanel();
      }
    }
    if (arg0.getActionCommand()
        .equals(GameCommands.COMMAND_UPGRADE_IMPROVEMENT)) {
      // Assuming that upgrade button is disabled so no need to make check here
      int lvl = player.getTechList().getTechLevel(TechType.Improvements);
      lvl = lvl + 1;
      player.getTechList().setTechLevel(TechType.Improvements, lvl);
      improvementRese.setEnableUpgradeButton(false);
      updateTechInfo(TechType.Improvements);
      SoundPlayer.playMenuSound();
      updatePanel();
    }

    if (arg0.getActionCommand()
        .equals(GameCommands.COMMAND_SLIDER_PROPULSION_RESEARCH)) {
      int value = propulsionRese.getSliderValue();
      player.getTechList().setTechFocus(TechType.Propulsion, value);
      if (playSoundFromSliders == 1) {
        SoundPlayer.playMenuSound();
        playSoundFromSliders = 0;
      }
      updatePanel();
    }
    if (arg0.getActionCommand()
        .equals(GameCommands.COMMAND_PLUS_PROPULSION_RESEARCH)) {
      int value = player.getTechList().getTechFocus(TechType.Propulsion);
      if (value <= 100 - TechList.FINE_TUNE_VALUE) {
        value = value + TechList.FINE_TUNE_VALUE;
        player.getTechList().setTechFocus(TechType.Propulsion, value);
        updateTechInfo(TechType.Propulsion);
        SoundPlayer.playMenuSound();
        playSoundFromSliders = 0;
        updatePanel();
      }
    }
    if (arg0.getActionCommand()
        .equals(GameCommands.COMMAND_MINUS_PROPULSION_RESEARCH)) {
      int value = player.getTechList().getTechFocus(TechType.Propulsion);
      if (value >= TechList.FINE_TUNE_VALUE) {
        value = value - TechList.FINE_TUNE_VALUE;
        player.getTechList().setTechFocus(TechType.Propulsion, value);
        updateTechInfo(TechType.Propulsion);
        SoundPlayer.playMenuSound();
        playSoundFromSliders = 0;
        updatePanel();
      }
    }
    if (arg0.getActionCommand()
        .equals(GameCommands.COMMAND_UPGRADE_PROPULSION)) {
      // Assuming that upgrade button is disabled so no need to make check here
      int lvl = player.getTechList().getTechLevel(TechType.Propulsion);
      lvl = lvl + 1;
      player.getTechList().setTechLevel(TechType.Propulsion, lvl);
      propulsionRese.setEnableUpgradeButton(false);
      updateTechInfo(TechType.Propulsion);
      SoundPlayer.playMenuSound();
      updatePanel();
    }

    if (arg0.getActionCommand()
        .equals(GameCommands.COMMAND_SLIDER_ELECTRONICS_RESEARCH)) {
      int value = electronicsRese.getSliderValue();
      player.getTechList().setTechFocus(TechType.Electrics, value);
      if (playSoundFromSliders == 1) {
        SoundPlayer.playMenuSound();
        playSoundFromSliders = 0;
      }
      updatePanel();
    }
    if (arg0.getActionCommand()
        .equals(GameCommands.COMMAND_PLUS_ELECTRONICS_RESEARCH)) {
      int value = player.getTechList().getTechFocus(TechType.Electrics);
      if (value <= 100 - TechList.FINE_TUNE_VALUE) {
        value = value + TechList.FINE_TUNE_VALUE;
        player.getTechList().setTechFocus(TechType.Electrics, value);
        updateTechInfo(TechType.Electrics);
        SoundPlayer.playMenuSound();
        playSoundFromSliders = 0;
        updatePanel();
      }
    }
    if (arg0.getActionCommand()
        .equals(GameCommands.COMMAND_MINUS_ELECTRONICS_RESEARCH)) {
      int value = player.getTechList().getTechFocus(TechType.Electrics);
      if (value >= TechList.FINE_TUNE_VALUE) {
        value = value - TechList.FINE_TUNE_VALUE;
        player.getTechList().setTechFocus(TechType.Electrics, value);
        updateTechInfo(TechType.Electrics);
        SoundPlayer.playMenuSound();
        playSoundFromSliders = 0;
        updatePanel();
      }
    }
    if (arg0.getActionCommand()
        .equals(GameCommands.COMMAND_UPGRADE_ELECTRONICS)) {
      // Assuming that upgrade button is disabled so no need to make check here
      int lvl = player.getTechList().getTechLevel(TechType.Electrics);
      lvl = lvl + 1;
      player.getTechList().setTechLevel(TechType.Electrics, lvl);
      electronicsRese.setEnableUpgradeButton(false);
      updateTechInfo(TechType.Electrics);
      SoundPlayer.playMenuSound();
      updatePanel();
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_COMBAT_INFO)) {
      updateTechInfo(TechType.Combat);
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_DEFENSE_INFO)) {
      updateTechInfo(TechType.Defense);
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_HULL_INFO)) {
      updateTechInfo(TechType.Hulls);
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_IMPROVEMENT_INFO)) {
      updateTechInfo(TechType.Improvements);
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_PROPULSION_INFO)) {
      updateTechInfo(TechType.Propulsion);
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_ELECTRONICS_INFO)) {
      updateTechInfo(TechType.Electrics);
    }

  }

  /**
   * How many turns researching takes when marking it as it will never be
   * finished on estimate
   */
  private static final int TIME_LIMIT_NEVER = 10000;
  /**
   * Update all component on research view panel.
   */
  public void updatePanel() {
    int focus = player.getTechList().getTechFocus(TechType.Combat);
    int level = player.getTechList().getTechLevel(TechType.Combat);
    int subLevel = player.getTechList().getListForTypeAndLevel(TechType.Combat,
        level).length;
    int maxSubLevel = TechFactory.getListByTechLevel(TechType.Combat,
        level, player.getRace()).length;
    int required = TechFactory.getTechCost(level, maximumGameLength);
    int turns = (int) Math.ceil(
        (required - player.getTechList().getTechResearchPoints(TechType.Combat))
            / (focus * totalResearch / 100.0));
    String turnsInStr = turns + " star years";
    if (turns > TIME_LIMIT_NEVER) {
      turnsInStr = "never";
    }
    combatRese.setSliderValue(focus);
    combatRese
        .setText(TechType.Combat.toString() + " " + focus + "% " + turnsInStr);
    combatRese.setUpgadeBtnText(
        "Level:" + level + "(" + subLevel + "/" + maxSubLevel + ")");
    if (player.getTechList().isUpgradeable(TechType.Combat)) {
      combatRese.setEnableUpgradeButton(true);
      combatRese.setUpgadeBtnToolTip("<html>Upgrade combat to " + (level + 1)
          + " level.<br>"
          + " By upgrading you skip rest of technologies on your current level."
          + "</html>");
    }

    focus = player.getTechList().getTechFocus(TechType.Defense);
    level = player.getTechList().getTechLevel(TechType.Defense);
    subLevel = player.getTechList().getListForTypeAndLevel(TechType.Defense,
        level).length;
    maxSubLevel = TechFactory.getListByTechLevel(TechType.Defense,
        level, player.getRace()).length;
    required = TechFactory.getTechCost(level, maximumGameLength);
    turns = (int) Math.ceil((required
        - player.getTechList().getTechResearchPoints(TechType.Defense))
        / (focus * totalResearch / 100.0));
    turnsInStr = turns + " star years";
    if (turns > TIME_LIMIT_NEVER) {
      turnsInStr = "never";
    }
    defenseRese.setSliderValue(focus);
    defenseRese
        .setText(TechType.Defense.toString() + " " + focus + "% " + turnsInStr);
    defenseRese.setUpgadeBtnText(
        "Level:" + level + "(" + subLevel + "/" + maxSubLevel + ")");
    if (player.getTechList().isUpgradeable(TechType.Defense)) {
      defenseRese.setEnableUpgradeButton(true);
      defenseRese.setUpgadeBtnToolTip("<html>Upgrade defense to " + (level + 1)
          + " level.<br>"
          + " By upgrading you skip rest of technologies on your current level."
          + "</html>");
    }

    focus = player.getTechList().getTechFocus(TechType.Hulls);
    level = player.getTechList().getTechLevel(TechType.Hulls);
    subLevel = player.getTechList().getListForTypeAndLevel(TechType.Hulls,
        level).length;
    maxSubLevel = TechFactory.getListByTechLevel(TechType.Hulls, level,
        player.getRace()).length;
    required = TechFactory.getTechCost(level, maximumGameLength);
    turns = (int) Math.ceil(
        (required - player.getTechList().getTechResearchPoints(TechType.Hulls))
            / (focus * totalResearch / 100.0));
    turnsInStr = turns + " star years";
    if (turns > TIME_LIMIT_NEVER) {
      turnsInStr = "never";
    }
    hullRese.setSliderValue(focus);
    hullRese
        .setText(TechType.Hulls.toString() + " " + focus + "% " + turnsInStr);
    hullRese.setUpgadeBtnText(
        "Level:" + level + "(" + subLevel + "/" + maxSubLevel + ")");
    if (player.getTechList().isUpgradeable(TechType.Hulls)) {
      hullRese.setEnableUpgradeButton(true);
      hullRese.setUpgadeBtnToolTip("<html>Upgrade hulls to " + (level + 1)
          + " level.<br>"
          + " By upgrading you skip rest of technologies on your current level."
          + "</html>");
    }

    focus = player.getTechList().getTechFocus(TechType.Improvements);
    level = player.getTechList().getTechLevel(TechType.Improvements);
    subLevel = player.getTechList()
        .getListForTypeAndLevel(TechType.Improvements, level).length;
    maxSubLevel = TechFactory.getListByTechLevel(TechType.Improvements,
        level, player.getRace()).length;
    required = TechFactory.getTechCost(level, maximumGameLength);
    turns = (int) Math.ceil((required
        - player.getTechList().getTechResearchPoints(TechType.Improvements))
        / (focus * totalResearch / 100.0));
    turnsInStr = turns + " star years";
    if (turns > TIME_LIMIT_NEVER) {
      turnsInStr = "never";
    }
    improvementRese.setSliderValue(focus);
    improvementRese.setText(
        TechType.Improvements.toString() + " " + focus + "% " + turnsInStr);
    improvementRese.setUpgadeBtnText(
        "Level:" + level + "(" + subLevel + "/" + maxSubLevel + ")");
    if (player.getTechList().isUpgradeable(TechType.Improvements)) {
      improvementRese.setEnableUpgradeButton(true);
      improvementRese.setUpgadeBtnToolTip("<html>Upgrade improvements to "
          + (level + 1) + " level.<br>"
          + " By upgrading you skip rest of technologies on your current level."
          + "</html>");
    }

    focus = player.getTechList().getTechFocus(TechType.Propulsion);
    level = player.getTechList().getTechLevel(TechType.Propulsion);
    subLevel = player.getTechList().getListForTypeAndLevel(TechType.Propulsion,
        level).length;
    maxSubLevel = TechFactory.getListByTechLevel(TechType.Propulsion,
        level, player.getRace()).length;
    required = TechFactory.getTechCost(level, maximumGameLength);
    turns = (int) Math.ceil((required
        - player.getTechList().getTechResearchPoints(TechType.Propulsion))
        / (focus * totalResearch / 100.0));
    turnsInStr = turns + " star years";
    if (turns > TIME_LIMIT_NEVER) {
      turnsInStr = "never";
    }
    propulsionRese.setSliderValue(focus);
    propulsionRese.setText(
        TechType.Propulsion.toString() + " " + focus + "% " + turnsInStr);
    propulsionRese.setUpgadeBtnText(
        "Level:" + level + "(" + subLevel + "/" + maxSubLevel + ")");
    if (player.getTechList().isUpgradeable(TechType.Propulsion)) {
      propulsionRese.setEnableUpgradeButton(true);
      propulsionRese.setUpgadeBtnToolTip("<html>Upgrade propulsion to "
          + (level + 1) + " level.<br>"
          + " By upgrading you skip rest of technologies on your current level."
          + "</html>");
    }

    focus = player.getTechList().getTechFocus(TechType.Electrics);
    level = player.getTechList().getTechLevel(TechType.Electrics);
    subLevel = player.getTechList().getListForTypeAndLevel(TechType.Electrics,
        level).length;
    maxSubLevel = TechFactory.getListByTechLevel(TechType.Electrics,
        level, player.getRace()).length;
    required = TechFactory.getTechCost(level, maximumGameLength);
    turns = (int) Math.ceil((required
        - player.getTechList().getTechResearchPoints(TechType.Electrics))
        / (focus * totalResearch / 100.0));
    turnsInStr = turns + " star years";
    if (turns > TIME_LIMIT_NEVER) {
      turnsInStr = "never";
    }
    electronicsRese.setSliderValue(focus);
    electronicsRese.setText(
        TechType.Electrics.toString() + " " + focus + "% " + turnsInStr);
    electronicsRese.setUpgadeBtnText(
        "Level:" + level + "(" + subLevel + "/" + maxSubLevel + ")");
    if (player.getTechList().isUpgradeable(TechType.Electrics)) {
      electronicsRese.setEnableUpgradeButton(true);
      electronicsRese.setUpgadeBtnToolTip("<html>Upgrade electronics to "
          + (level + 1) + " level.<br>"
          + " By upgrading you skip rest of technologies on your current level."
          + "</html>");
    }
  }

  /**
   * Get Technology description and missing tech list to info text.
   * @param type TechType
   */
  public void updateTechInfo(final TechType type) {
    techList.clearSelection();
    StringBuilder sb = new StringBuilder(type.getDescription());
    int level = player.getTechList().getTechLevel(type);
    Tech[] missingTechs = player.getTechList().getListMissingTech(type, level);
    Tech[] missingRareTech = player.getTechList().checkRareTechTree(type,
        level);
    sb.append("\n\n");
    sb.append("Missing techs for level ");
    sb.append(level);
    sb.append(":\n");
    for (int i = 0; i < missingTechs.length; i++) {
      sb.append(missingTechs[i].getName());
      sb.append("\n");
    }
    if (missingRareTech != null) {
      sb.append("\nMissing rare tech for level ");
      sb.append(level);
      sb.append(":\n");
      for (int i = 0; i < missingRareTech.length; i++) {
        sb.append(missingRareTech[i].getName());
        sb.append("\n");
      }
    }
    infoText.setText(sb.toString());
    infoText.repaint();
    infoText.setLineWrap(true);
    infoText.setCharacterWidth(7);
  }

  @Override
  public void valueChanged(final ListSelectionEvent e) {
    if (e.getSource() == techList && techList.getSelectedIndex() != -1) {
      Tech tech = techList.getSelectedValue();
      String strTmp = tech.getTechInfo(player.getRace());
      if (!strTmp.equals(infoText.getText())) {
        infoText.setLineWrap(false);
        infoText.setText(strTmp);
        infoText.repaint();
      }
    }
    if (e.getSource() == artifactList
        && artifactList.getSelectedIndex() != -1) {
      Artifact artifact = artifactList.getSelectedValue();
      String strTmp = artifact.getFullDescription();
      if (!strTmp.equals(infoText.getText())) {
        infoText.setLineWrap(true);
        infoText.setText(strTmp);
        infoText.repaint();
      }
    }
  }

}
