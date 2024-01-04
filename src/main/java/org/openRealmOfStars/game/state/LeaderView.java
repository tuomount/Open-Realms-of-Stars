package org.openRealmOfStars.game.state;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2020-2023 Tuomo Untinen
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
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.openRealmOfStars.audio.soundeffect.SoundPlayer;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.InfoTextArea;
import org.openRealmOfStars.gui.labels.SpaceLabel;
import org.openRealmOfStars.gui.list.LeaderListRenderer;
import org.openRealmOfStars.gui.list.LeaderTreeCellRenderer;
import org.openRealmOfStars.gui.mapPanel.MapPanel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.util.GuiFonts;
import org.openRealmOfStars.gui.util.GuiStatics;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.leader.Job;
import org.openRealmOfStars.player.leader.Leader;
import org.openRealmOfStars.player.leader.LeaderBiography;
import org.openRealmOfStars.player.leader.LeaderUtility;
import org.openRealmOfStars.player.leader.Perk;
import org.openRealmOfStars.player.leader.RecruitableLeader;
import org.openRealmOfStars.player.message.Message;
import org.openRealmOfStars.player.message.MessageType;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.planet.Planet;

/**
*
* Leader view for the realm.
*
*/
public class LeaderView extends BlackPanel
    implements TreeSelectionListener, ListSelectionListener {

  /**
  *
  */
  private static final long serialVersionUID = 1L;
  /**
   * Player Info
   */
  private PlayerInfo player;
  /**
   * Star map
   */
  private StarMap map;

  /**
   * JTree of leaders in order and their heirs.
   */
  private JTree leaderTree;
  /**
   * JList of recruitable leaders.
   */
  private JList<RecruitableLeader> leaderList;
  /**
   * SpaceLabel for realm credits.
   */
  private SpaceLabel credits;
  /**
   * Planet population
   */
  private SpaceLabel planetPopulation;

  /**
   * Info Text for Leader
   */
  private InfoTextArea infoText;

  /**
   * Map Panel for drawing small starmap where leader is assigned.
   */
  private MapPanel mapPanel;

  /**
   * Planet where training happens
   */
  private Planet trainingPlanet;

  /**
   * Recruit button.
   */
  private SpaceButton recruitBtn;
  /**
   * Set leader button.
   */
  private SpaceButton setLeaderBtn;
  /**
   * Active planet from planet view
   */
  private Planet activePlanet;
  /**
   * Active fleet from fleet view
   */
  private Fleet activeFleet;
  /**
   * Local version of realm's leader pool for recruits.
   */
  private RecruitableLeader[] leadersInPool;
  /**
   * Was regular/standard leader selected.
   */
  private boolean standardLeaderSelected;
  /**
   * View Leader view.
   * @param info Player info
   * @param starMap Star map data
   * @param listener ActionListener.
   */
  public LeaderView(final PlayerInfo info, final StarMap starMap,
      final ActionListener listener) {
    player = info;
    map = starMap;
    activeFleet = null;
    activePlanet = null;
    standardLeaderSelected = true;
    leadersInPool = LeaderUtility.buildRecuitableLeaderPool(map, player);
    InfoPanel recruitPanel = new InfoPanel();
    recruitPanel.setTitle("Leader recruiment");
    recruitPanel.setLayout(new BoxLayout(recruitPanel, BoxLayout.Y_AXIS));
    credits = new SpaceLabel("Realm credits: " + player.getTotalCredits());
    recruitPanel.add(credits, BorderLayout.CENTER);
    recruitPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    planetPopulation = new SpaceLabel("Alpha Centauri II - Population: 12");
    recruitPanel.add(planetPopulation);
    leaderList = new JList<>(leadersInPool);
    leaderList.setCellRenderer(new LeaderListRenderer());
    leaderList.addListSelectionListener(this);
    JScrollPane scroll = new JScrollPane(leaderList);
    scroll.setBackground(GuiStatics.getDeepSpaceDarkColor());
    leaderList.getSelectionModel().setSelectionMode(
        TreeSelectionModel.SINGLE_TREE_SELECTION);
    leaderList.setBackground(Color.BLACK);
    recruitPanel.add(scroll);

    InfoPanel base = new InfoPanel();
    base.setTitle("Leaders");
    this.setLayout(new BorderLayout());
    base.setLayout(new BoxLayout(base, BoxLayout.Y_AXIS));
    InfoPanel center = new InfoPanel();
    center.setTitle("Leader");
    center.setLayout(new BorderLayout());
    leaderTree = new JTree(buildTreeOfLeaders());
    leaderTree.setCellRenderer(new LeaderTreeCellRenderer());
    leaderTree.addTreeSelectionListener(this);
    scroll = new JScrollPane(leaderTree);
    scroll.setBackground(GuiStatics.getDeepSpaceDarkColor());
    leaderTree.getSelectionModel().setSelectionMode(
        TreeSelectionModel.SINGLE_TREE_SELECTION);
    leaderTree.setBackground(Color.BLACK);
    base.add(scroll);
    scroll.setAlignmentX(CENTER_ALIGNMENT);
    recruitBtn = new SpaceButton("Recruit leader ",
        GameCommands.COMMAND_RECRUIT_LEADER);
    trainingPlanet = LeaderUtility.getBestLeaderTrainingPlanet(
        map.getPlanetList(), player);
    recruitBtn.addActionListener(listener);
    recruitBtn.setAlignmentX(CENTER_ALIGNMENT);
    recruitPanel.add(recruitBtn);
    base.add(recruitPanel);
    infoText = new InfoTextArea(20, 35);
    infoText.setEditable(false);
    infoText.setFont(GuiFonts.getFontCubellanSmaller());
    infoText.setWrapStyleWord(true);
    infoText.setLineWrap(true);
    infoText.setCharacterWidth(7);
    scroll = new JScrollPane(infoText);
    scroll.setBackground(GuiStatics.getDeepSpaceDarkColor());
    center.add(scroll, BorderLayout.WEST);
    setLeaderBtn = new SpaceButton("Assign leader",
        GameCommands.COMMAND_ASSIGN_LEADER);
    setLeaderBtn.addActionListener(listener);
    center.add(setLeaderBtn, BorderLayout.SOUTH);
    updateButtonToolTips();
    mapPanel = new MapPanel(false);
    center.add(mapPanel, BorderLayout.CENTER);
    // Bottom panel
    InfoPanel bottomPanel = new InfoPanel();
    bottomPanel.setLayout(new BorderLayout());
    bottomPanel.setTitle(null);
    SpaceButton btn = new SpaceButton("Back to star map",
        GameCommands.COMMAND_VIEW_STARMAP);
    btn.addActionListener(listener);
    bottomPanel.add(btn, BorderLayout.CENTER);
    // Add panels to base
    this.add(bottomPanel, BorderLayout.SOUTH);
    this.add(base, BorderLayout.WEST);
    this.add(center, BorderLayout.CENTER);
    updatePanel();
  }



  /**
   * Get Leader heirs
   * @param leader Leader whose heirs are being searched
   * @param leaders List of all leaders
   * @return List of heirs
   */
  private static Leader[] getHeirs(final Leader leader,
      final Leader[] leaders) {
    ArrayList<Leader> heirs = new ArrayList<>();
    for (int i = 0; i < leaders.length; i++) {
      if (leaders[i].getParent() == leader) {
        heirs.add(leaders[i]);
      }
    }
    return heirs.toArray(new Leader[heirs.size()]);
  }
  /**
   * Searches recursive for heirs and addes them to node.
   * @param node Tree node where to add.
   * @param leader Leader whose heirs are being searched.
   * @param leaders List of all leaders.
   * @return Tree node with leader and heirs.
   */
  private DefaultMutableTreeNode searchForHeirs(
      final DefaultMutableTreeNode node, final Leader leader,
      final Leader[] leaders) {
    DefaultMutableTreeNode result = node;
    if (result == null) {
      result = new DefaultMutableTreeNode(leader);
    }
    Leader[] heirs = getHeirs(leader, leaders);
    for (Leader heir : heirs) {
      DefaultMutableTreeNode heirNode = searchForHeirs(null, heir, leaders);
      result.add(heirNode);
    }
    return result;
  }
  /**
   * Build tree of heirs and leaders.
   * @return Root node for tree.
   */
  private DefaultMutableTreeNode buildTreeOfLeaders() {
    DefaultMutableTreeNode root = new DefaultMutableTreeNode("Leaders of "
        + player.getEmpireName());
    Leader[] leaders = sortLeaders(player.getLeaderPool());
    for (Leader leader : leaders) {
      DefaultMutableTreeNode leaderNode = searchForHeirs(null, leader, leaders);
      root.add(leaderNode);
    }
    return root;
  }
  /**
   * Set active planet.
   * @param planet Planet where to set leader
   */
  public void setPlanet(final Planet planet) {
    activePlanet = planet;
    updatePanel();
  }
  /**
   * Set active fleet.
   * @param fleet Fleet where to set leader
   */
  public void setFleet(final Fleet fleet) {
    activeFleet = fleet;
    updatePanel();
  }
  /**
   * Set selected by index
   * @param index Leader pool index.
   */
  public void setFocusToIndex(final int index) {
    Leader leaderToFind = player.getLeaderPool().get(index);
    if (leaderToFind != null) {
      TreeModel model = leaderTree.getModel();
      for (int i = 0; i < model.getChildCount(model.getRoot()); i++) {
        if (model.getChild(model.getRoot(), i)
            instanceof DefaultMutableTreeNode) {
          Object object = ((DefaultMutableTreeNode)
            model.getChild(model.getRoot(), i)).getUserObject();
          if (object instanceof Leader) {
            Leader leader = (Leader) object;
            if (leaderToFind == leader) {
             Object[] array = new Object[2];
             array[0] = model.getRoot();
             array[1] = model.getChild(model.getRoot(), i);
             TreePath path = new TreePath(array);
             leaderTree.setSelectionPath(path);
             updatePanel();
             break;
            }
          }
        }
      }
    }
  }
  /**
   * Update button tool tips.
   */
  private void updateButtonToolTips() {
    int leaderCost = LeaderUtility.leaderRecruitCost(player);
    if (leaderList.getModel().getSize() > 0) {
      if (!player.getGovernment().isXenophopic()) {
        recruitBtn.setToolTipText("<html>Leaders can be recruit from your own "
            + "realm which least amount of credits,<br> but this will also use"
            + " one population from that planet where leader is.<br>"
            + "Trade alliance or defensive pact with another realm allows"
            + "hiring from their leader pool. This will cost more credits, but"
            + " does not consume population."
            + "</html>");
      } else {
        recruitBtn.setToolTipText("<html>Leaders can be recruit from your own "
            + "realm which least amount of credits,<br> but this will also use"
            + " one population from that planet where leader is.<br>"
            + "Hiring from another realm is not allowed due government's"
            + " xenophobia."
            + "</html>");
      }
    } else if (trainingPlanet == null) {
      recruitBtn.setToolTipText("<html>Your realm does not have more than"
          + "<br>" + player.getRace().getMinimumPopulationForLeader()
          + " population on any of your planets."
          + "</html>");
      recruitBtn.setEnabled(false);
    } else if (leaderCost > player.getTotalCredits()) {
      recruitBtn.setToolTipText("<html>Your realm does not have enough"
          + "credits to recruit leader.<br> Leader recruit costs "
          + leaderCost + " credits. Your realm has " + player.getTotalCredits()
          + " credits."
          + "</html>");
      recruitBtn.setEnabled(false);
    }
    if (activeFleet != null || activePlanet != null) {
      if (activeFleet != null) {
        setLeaderBtn.setToolTipText("<html>"
            + "Assign current leader as a commander to "
            + activeFleet.getName() + ".</html>");
      }
      if (activePlanet != null) {
        setLeaderBtn.setToolTipText("<html>"
            + "Assign current leader as a govennor to "
            + activePlanet.getName() + ".</html>");
      }
      setLeaderBtn.setEnabled(true);
    } else {
      setLeaderBtn.setEnabled(false);
    }
  }
  /**
   * Get Selected leader from JList.
   * @return Leader or null
   */
  private Leader getSelectedLeaderFromList() {
    Leader leader = null;
    if (leaderList.getSelectedValue() != null) {
      leader = leaderList.getSelectedValue().getLeader();
    }
    return leader;
  }
  /**
   * Get Selected leader from JTree.
   * @return Leader or null
   */
  private Leader getSelectedLeaderFromTree() {
    Leader leader = null;
    if (leaderTree.getSelectionPath() != null
        && leaderTree.getSelectionPath().getLastPathComponent()
        instanceof DefaultMutableTreeNode) {
      Object object = ((DefaultMutableTreeNode)
          leaderTree.getSelectionPath().getLastPathComponent())
          .getUserObject();
      if (object instanceof Leader) {
        leader = (Leader) object;
      }
    }
    return leader;
  }
  /**
   * Update all panels.
   */
  public void updatePanel() {
    Leader leader = null;
    credits.setText("Realm credits: " + player.getTotalCredits());
    if (standardLeaderSelected) {
      if (leaderTree.getSelectionPath() != null
          && leaderTree.getSelectionPath().getLastPathComponent()
          instanceof DefaultMutableTreeNode) {
        Object object = ((DefaultMutableTreeNode)
            leaderTree.getSelectionPath().getLastPathComponent())
            .getUserObject();
        if (object instanceof Leader) {
          leader = (Leader) object;
        }
      }
    } else {
      leader = getSelectedLeaderFromList();
      boolean planetFound = false;
      if (leader != null) {
        for (Planet planet : map.getPlanetList()) {
          if (leader.getHomeworld().equalsIgnoreCase(planet.getName())) {
            map.setDrawPos(planet.getX(), planet.getY());
            planetPopulation.setText(planet.getName() + " - Population:"
            + planet.getTotalPopulation());
            planetFound = true;
            break;
          }
        }
        if (!planetFound) {
          map.setDrawPos(map.getMaxX() / 2, map.getMaxY() / 2);
          planetPopulation.setText("No home planet selected");
        }
      }
    }
    if (leader != null) {
      if (leader.getJob() == Job.RULER) {
        for (Planet planet : map.getPlanetList()) {
          if (planet.getPlanetPlayerInfo() == player) {
            map.setDrawPos(planet.getX(), planet.getY());
            break;
          }
        }
      } else if (leader.getJob() == Job.GOVERNOR) {
        for (Planet planet : map.getPlanetList()) {
          if (planet.getGovernor() == leader) {
            map.setDrawPos(planet.getX(), planet.getY());
            break;
          }
        }
      } else if (leader.getJob() == Job.COMMANDER) {
        for (int i = 0; i < player.getFleets().getNumberOfFleets(); i++) {
          Fleet fleet = player.getFleets().getByIndex(i);
          if (fleet.getCommander() == leader) {
            map.setDrawPos(fleet.getX(), fleet.getY());
            break;
          }
        }
      } else if (standardLeaderSelected) {
        map.setDrawPos(map.getMaxX() / 2, map.getMaxY() / 2);
      }
      mapPanel.drawMap(map);
      StringBuilder sb = new StringBuilder();
      sb.append(leader.getDescription());
      sb.append("\n\nPerks\n");
      if (leader.getPerkList().size() == 0) {
        sb.append("None");
      } else {
        for (Perk perk : leader.getPerkList()) {
          sb.append("* ");
          sb.append(perk.getName());
          sb.append(" ");
          sb.append(perk.getDescription());
          sb.append("\n");
        }
      }
      sb.append("\n");
      sb.append(LeaderBiography.createBioForLeader(leader, player));
      infoText.setText(sb.toString());
      String target = null;
      String job = null;
      if (activePlanet != null) {
        target = activePlanet.getName();
        job = "governor";
      }
      if (activeFleet != null) {
        target = activeFleet.getName();
        job = "commander";
      }
      if (target != null) {
        setLeaderBtn.setText("Assign " + job + " to " + target);
      } else {
        setLeaderBtn.setText("Assign leader");
      }

    }
    if (leader == null) {
      planetPopulation.setText("No leader selected.");

    }
    updateButtonToolTips();
    this.repaint();
  }
  /**
   * Sort all leaders in following order:
   * Ruler, Unassinged, Governor, Commander, Too young
   * @param leaders Leaders in arraylist
   * @return Array of leaders in order.
   */
  public static final Leader[] sortLeaders(final ArrayList<Leader> leaders) {
    Leader[] result = new Leader[leaders.size()];
    int index = 0;
    // Get leader
    for (Leader leader : leaders) {
      if (leader.getJob() == Job.RULER) {
        result[index] = leader;
        index++;
        break;
      }
    }
    // Get all unassigned
    for (Leader leader : leaders) {
      if (leader.getJob() == Job.UNASSIGNED) {
        result[index] = leader;
        index++;
      }
    }
    // Get all governors
    for (Leader leader : leaders) {
      if (leader.getJob() == Job.GOVERNOR) {
        result[index] = leader;
        index++;
      }
    }
    // Get all commanders
    for (Leader leader : leaders) {
      if (leader.getJob() == Job.COMMANDER) {
        result[index] = leader;
        index++;
      }
    }
    for (Leader leader : leaders) {
      if (leader.getJob() == Job.PRISON) {
        result[index] = leader;
        index++;
      }
    }
    // Get all too youngs
    for (Leader leader : leaders) {
      if (leader.getJob() == Job.TOO_YOUNG) {
        result[index] = leader;
        index++;
      }
    }
    // Get all too deads
    for (Leader leader : leaders) {
      if (leader.getJob() == Job.DEAD) {
        result[index] = leader;
        index++;
      }
    }
    return result;
  }

  /**
   * Handle actions in leader view.
   * @param arg0 Action event to handle
   */
  public void handleActions(final ActionEvent arg0) {
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_ASSIGN_LEADER)) {
      boolean soundPlayed = false;
      Leader leader = getSelectedLeaderFromTree();
      if (leader != null && (leader.getTimeInJob() > 19
          || leader.getJob() == Job.UNASSIGNED)) {
        Object target = null;
        if (activePlanet != null) {
          target = activePlanet;
        }
        if (activeFleet != null) {
          target = activeFleet;
        }
        soundPlayed = LeaderUtility.assignLeader(leader, player,
            map.getPlanetList(), target);
        TreeModel model = new DefaultTreeModel(buildTreeOfLeaders());
        leaderTree.setModel(model);
        updatePanel();
      }
      if (soundPlayed) {
        SoundPlayer.playMenuSound();
      } else {
        SoundPlayer.playMenuDisabled();
      }
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_RECRUIT_LEADER)) {
      if (player.getTotalCredits() >= leaderList.getSelectedValue().getCost()
        && leaderList.getSelectedValue() != null) {
        Leader leader = leaderList.getSelectedValue().getLeader();
        leader.assignJob(Job.UNASSIGNED, player);
        player.getLeaderPool().add(leader);
        int realmIndex = leaderList.getSelectedValue().getRealmIndex();
        PlayerInfo realm = map.getPlayerByIndex(realmIndex);
        realm.removeRecruitLeader(leader);
        int leaderCost = leaderList.getSelectedValue().getCost();
        if (realm == player) {
          player.setTotalCredits(player.getTotalCredits() - leaderCost);
          Planet planet = map.getPlanetByName(leader.getHomeworld());
          if (planet != null) {
            planet.takeColonist();
          }
        } else {
          player.setTotalCredits(player.getTotalCredits() - leaderCost);
          realm.setTotalCredits(realm.getTotalCredits() + leaderCost);
          Message msg = new Message(MessageType.LEADER, player.getEmpireName()
              + " hire leader called " + leader.getCallName() + " from "
              + realm.getEmpireName() + " with " + leaderCost + " credits."
              + " This leader is from " + leader.getHomeworld() + ".",
              Icons.getIconByName(Icons.ICON_GOVERNOR));
          realm.getMsgList().addUpcomingMessage(msg);
        }
        leadersInPool = LeaderUtility.buildRecuitableLeaderPool(map, player);
        leaderList.setListData(leadersInPool);
        TreeModel model = new DefaultTreeModel(buildTreeOfLeaders());
        leaderTree.setModel(model);
        SoundPlayer.playMenuSound();
        updatePanel();
      } else {
        SoundPlayer.playMenuDisabled();
      }
    }
  }

  @Override
  public void valueChanged(final TreeSelectionEvent e) {
    standardLeaderSelected = true;
    updatePanel();
  }

  @Override
  public void valueChanged(final ListSelectionEvent e) {
    standardLeaderSelected = false;
    updatePanel();
  }
}
