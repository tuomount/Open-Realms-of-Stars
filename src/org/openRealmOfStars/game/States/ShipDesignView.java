package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.BlackPanel;
import org.openRealmOfStars.gui.GuiStatics;
import org.openRealmOfStars.gui.ListRenderers.ShipComponentListRenderer;
import org.openRealmOfStars.gui.ListRenderers.ShipHullListRenderer;
import org.openRealmOfStars.gui.borders.SimpleBorder;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.InfoTextArea;
import org.openRealmOfStars.gui.labels.TransparentLabel;
import org.openRealmOfStars.gui.panels.InvisiblePanel;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.ship.ShipComponent;
import org.openRealmOfStars.player.ship.ShipComponentFactory;
import org.openRealmOfStars.player.ship.ShipDesign;
import org.openRealmOfStars.player.ship.ShipHull;
import org.openRealmOfStars.player.ship.ShipHullFactory;
import org.openRealmOfStars.player.tech.Tech;
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
 * Ship Design view for design new ship designs
 * 
 */

public class ShipDesignView extends BlackPanel {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Player Info
   */
  private PlayerInfo player;

  /**
   * Current ship Design
   */
  private ShipDesign design;

  /**
   * ComboBox where to select hull
   */
  private JComboBox<ShipHull> hullSelect;

  /**
   * ComboBox where to select component
   */
  private JComboBox<ShipComponent> componentSelect;

  /**
   * Text is containing information about the ship hull
   */
  private InfoTextArea hullInfoText;

  /**
   * Text is containing information about the ship component
   */
  private InfoTextArea componentInfoText;

  /**
   * Design's name Text
   */
  private JTextField designNameText;
  
  /**
   * List of ship's components in priority order
   */
  private JList<ShipComponent> componentList;


  
  public ShipDesignView(PlayerInfo player, ShipDesign oldDesign,
      ActionListener listener) {
    this.player = player;
    if (oldDesign != null) {
      design = oldDesign;
    } else {
      design = new ShipDesign(ShipHullFactory.createByName("Colony", player.getRace()));
    }
    this.setLayout(new BorderLayout());
    InfoPanel base = new InfoPanel();
    base.setLayout(new BorderLayout());
    base.setTitle("Ship design");
    
    // Hull Panel
    InfoPanel hullPanel = new InfoPanel();
    hullPanel.setLayout(new BoxLayout(hullPanel, BoxLayout.X_AXIS));
    hullPanel.setTitle("Ship's hull");
    
    hullPanel.add(Box.createRigidArea(new Dimension(25,25)));
    Tech[] hullTech = this.player.getTechList().getListForType(TechType.Hulls);
    ShipHull[] hulls  = new ShipHull[hullTech.length];
    for (int i = 0;i<hulls.length;i++) {
      hulls[i] = ShipHullFactory.createByName(hullTech[i].getHull(), this.player.getRace());
    }
    
    hullSelect = new JComboBox<>(hulls);
    hullSelect.addActionListener(listener);
    hullSelect.setActionCommand(GameCommands.COMMAND_SHIPDESIGN_HULLSELECTED);
    hullSelect.setBackground(GuiStatics.COLOR_COOL_SPACE_BLUE_DARK);
    hullSelect.setForeground(GuiStatics.COLOR_COOL_SPACE_BLUE);
    hullSelect.setBorder(new SimpleBorder());
    hullSelect.setFont(GuiStatics.getFontCubellan());
    hullSelect.setRenderer(new ShipHullListRenderer());
    InvisiblePanel invis = new InvisiblePanel(hullPanel);
    invis.setLayout(new BoxLayout(invis, BoxLayout.Y_AXIS));
    TransparentLabel label = new TransparentLabel(invis, "Design's Name: ");
    label.setAlignmentX(Component.LEFT_ALIGNMENT);
    invis.add(label);
    invis.add(Box.createRigidArea(new Dimension(5,5)));
    designNameText = new JTextField();
    designNameText.setFont(GuiStatics.getFontCubellan());
    designNameText.setForeground(GuiStatics.COLOR_GREEN_TEXT);
    designNameText.setBackground(Color.BLACK);
    invis.add(designNameText);
    invis.add(Box.createRigidArea(new Dimension(5,5)));
    label = new TransparentLabel(invis, "Ship's hull: ");
//    label.setAlignmentX(Component.);
    invis.add(label);
    invis.add(Box.createRigidArea(new Dimension(5,5)));
    invis.add(hullSelect);
    invis.add(Box.createRigidArea(new Dimension(25,25)));
    hullPanel.add(invis);
    
    hullInfoText = new InfoTextArea(10, 30);
    hullInfoText.setEditable(false);
    hullInfoText.setFont(GuiStatics.getFontCubellanSmaller());
    JScrollPane scroll = new JScrollPane(hullInfoText);
    hullPanel.add(Box.createRigidArea(new Dimension(25,25)));
    hullPanel.add(scroll);
    hullPanel.add(Box.createRigidArea(new Dimension(25,25)));
    
    invis = new InvisiblePanel(hullPanel);
    invis.setLayout(new BoxLayout(invis, BoxLayout.Y_AXIS));
    label = new TransparentLabel(invis, "Confirm hull change: ");
    label.setAlignmentX(Component.LEFT_ALIGNMENT);
    invis.add(label);
    invis.add(Box.createRigidArea(new Dimension(5,5)));
    SpaceButton btn = new SpaceButton("Change hull", GameCommands.COMMAND_SHIPDESIGN_CHANGEHULL);
    btn.addActionListener(listener);
    invis.add(btn);
    invis.add(Box.createRigidArea(new Dimension(25,75)));
    hullPanel.add(invis);


    base.add(hullPanel,BorderLayout.NORTH);
    
    // Component Panel
    InfoPanel componentPanel = new InfoPanel();
    componentPanel.setLayout(new BoxLayout(componentPanel, BoxLayout.X_AXIS));
    componentPanel.setTitle("Ship's components");
    componentPanel.add(Box.createRigidArea(new Dimension(25,25)));
    Tech[] allTech = this.player.getTechList().getList();
    ArrayList<ShipComponent> components = new ArrayList<>();
    for (int i = 0;i<allTech.length;i++) {
      if (allTech[i].getComponent() != null) {
        ShipComponent comp = ShipComponentFactory.createByName(allTech[i].getComponent());
        if (comp != null) {
          components.add(comp);
        }
      }
    }

    invis = new InvisiblePanel(componentPanel);
    invis.setLayout(new BoxLayout(invis, BoxLayout.Y_AXIS));
    componentSelect = new JComboBox<>(components.toArray(new ShipComponent[components.size()]));
    componentSelect.setActionCommand(GameCommands.COMMAND_SHIPDESIGN_COMPONENTSELECTED);
    componentSelect.setBackground(GuiStatics.COLOR_COOL_SPACE_BLUE_DARK);
    componentSelect.setForeground(GuiStatics.COLOR_COOL_SPACE_BLUE);
    componentSelect.setBorder(new SimpleBorder());
    componentSelect.setFont(GuiStatics.getFontCubellan());
    componentSelect.setRenderer(new ShipComponentListRenderer());
    componentSelect.addActionListener(listener);
    invis.add(componentSelect);
    componentInfoText = new InfoTextArea(10, 30);
    componentInfoText.setEditable(false);
    componentInfoText.setFont(GuiStatics.getFontCubellanSmaller());
    scroll = new JScrollPane(componentInfoText);
    componentPanel.add(Box.createRigidArea(new Dimension(25,25)));
    componentPanel.add(scroll,BorderLayout.CENTER);
    invis.add(componentInfoText);
    btn = new SpaceButton("Add component", GameCommands.COMMAND_SHIPDESIGN_COMPONENTADDED);
    btn.addActionListener(listener);
    invis.add(btn);
    componentPanel.add(invis);

    invis = new InvisiblePanel(componentPanel);
    invis.setLayout(new BoxLayout(invis, BoxLayout.Y_AXIS));
    componentList = new JList<>(design.getComponentList());
    componentList.setCellRenderer(new ShipComponentListRenderer());
    componentList.setBackground(Color.BLACK);
    componentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    scroll = new JScrollPane(componentList);
    invis.add(scroll);
    componentPanel.add(invis);

    
    base.add(componentPanel,BorderLayout.CENTER);


    this.add(base,BorderLayout.CENTER);

    // Bottom panel
    InfoPanel bottomPanel = new InfoPanel();
    bottomPanel.setLayout(new GridLayout(1, 2));
    bottomPanel.setTitle(null);
    btn = new SpaceButton("Cancel",GameCommands.COMMAND_SHIPS);
    btn.addActionListener(listener);
    bottomPanel.add(btn);
    
    btn = new SpaceButton("Accept design", GameCommands.COMMAND_SHIPS);
    btn.addActionListener(listener);
    bottomPanel.add(btn);
    
    //updatePanel();
    // Add panels to base
    this.add(bottomPanel,BorderLayout.SOUTH);

    updatePanels();
  }
  
  /**
   * Update visible panels
   */
  public void updatePanels() {
    ShipHull hull = (ShipHull) hullSelect.getSelectedItem();
    if (hull != null) {
      hullInfoText.setText(hull.toString());
    }
    if (design != null) {
      designNameText.setText(design.getName());
    }
    ShipComponent component = (ShipComponent) componentSelect.getSelectedItem();
    if (component != null) {
      componentInfoText.setText(component.toString());
    }
    componentList.setListData(design.getComponentList());
  }
  
  /**
   * Handle action events for ShipDesignView
   * @param arg0 ActionEvent
   */
  public void handleAction(ActionEvent arg0) {
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_SHIPDESIGN_HULLSELECTED)) {
      updatePanels();
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_SHIPDESIGN_COMPONENTSELECTED)) {
      updatePanels();
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_SHIPDESIGN_CHANGEHULL)) {
      ShipHull hull = (ShipHull) hullSelect.getSelectedItem();
      if (hull != null) {
        design = new ShipDesign(hull);
        design.setName(designNameText.getText());
      }
      updatePanels();
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_SHIPDESIGN_COMPONENTADDED)) {
      if (componentSelect.getSelectedItem() != null) {
        design.addComponent((ShipComponent) componentSelect.getSelectedItem()); 
        updatePanels();
      }
    }
    
  }
}
