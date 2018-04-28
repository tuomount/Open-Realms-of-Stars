package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import org.openRealmOfStars.audio.soundeffect.SoundPlayer;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.GuiStatics;
import org.openRealmOfStars.gui.ListRenderers.ShipComponentListRenderer;
import org.openRealmOfStars.gui.ListRenderers.ShipHullListRenderer;
import org.openRealmOfStars.gui.borders.SimpleBorder;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.BaseInfoTextArea;
import org.openRealmOfStars.gui.labels.ImageLabel;
import org.openRealmOfStars.gui.labels.InfoTextArea;
import org.openRealmOfStars.gui.labels.SpaceLabel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.SpaceGreyPanel;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.ship.ShipComponent;
import org.openRealmOfStars.player.ship.ShipComponentFactory;
import org.openRealmOfStars.player.ship.ShipComponentType;
import org.openRealmOfStars.player.ship.ShipHull;
import org.openRealmOfStars.player.ship.ShipHullFactory;
import org.openRealmOfStars.player.ship.ShipImage;
import org.openRealmOfStars.player.ship.ShipImages;
import org.openRealmOfStars.player.ship.ShipStat;
import org.openRealmOfStars.player.ship.shipdesign.ShipDesign;
import org.openRealmOfStars.player.ship.shipdesign.ShipDesignConsts;
import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.player.tech.TechType;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016,2018  Tuomo Untinen
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
   * ComboBox where to filter components
   */
  private JComboBox<String> componentFilter;

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

  /**
   * Text is containing information about the ship design
   */
  private BaseInfoTextArea designInfoText;

  /**
   * Text is containing information about the ship design what is missing
   */
  private BaseInfoTextArea designFlawsText;

  /**
   * Ship's hull image
   */
  private ImageLabel hullImage;

  /**
   * Is design name illegal or not?
   */
  private boolean illegalName;
  /**
   * Constructor for ShipDesignView
   * @param player Player whom is design the new ship design
   * @param oldDesign Possible old design which is being copied
   * @param listener ActionListener
   */
  public ShipDesignView(final PlayerInfo player, final ShipDesign oldDesign,
      final ActionListener listener) {
    this.player = player;
    if (oldDesign != null) {
      design = oldDesign.copy(player.getRace());
    } else {
      design = new ShipDesign(
          ShipHullFactory.createByName("Colony", player.getRace()));
    }
    this.setLayout(new BorderLayout());
    InfoPanel base = new InfoPanel();
    base.setLayout(new BorderLayout());
    base.setTitle("Ship design");

    // Hull Panel
    InfoPanel hullPanel = new InfoPanel();
    hullPanel.setLayout(new BoxLayout(hullPanel, BoxLayout.X_AXIS));
    hullPanel.setTitle("Ship's hull");

    hullPanel.add(Box.createRigidArea(new Dimension(25, 25)));
    Tech[] hullTech = this.player.getTechList().getListForType(TechType.Hulls);
    ArrayList<ShipHull> hullList = new ArrayList<>();
    ShipHull[] hulls = new ShipHull[hullTech.length];
    int hullIndex = 0;
    int listLastIndex = 0;
    for (int i = 0; i < hulls.length; i++) {
      ShipHull hull = ShipHullFactory.createByName(hullTech[i].getHull(),
          this.player.getRace());
      if (hull != null) {
        hullList.add(hull);
        if (design.getHull().getName().equals(hull.getName())) {
          hullIndex = listLastIndex;
        }
        listLastIndex++;
      }
    }
    hulls = hullList.toArray(new ShipHull[hullList.size()]);

    hullSelect = new JComboBox<>(hulls);
    hullSelect.setSelectedIndex(hullIndex);
    hullSelect.addActionListener(listener);
    hullSelect.setActionCommand(GameCommands.COMMAND_SHIPDESIGN_HULLSELECTED);
    hullSelect.setBackground(GuiStatics.COLOR_COOL_SPACE_BLUE_DARK);
    hullSelect.setForeground(GuiStatics.COLOR_COOL_SPACE_BLUE);
    hullSelect.setBorder(new SimpleBorder());
    hullSelect.setFont(GuiStatics.getFontCubellan());
    hullSelect.setRenderer(new ShipHullListRenderer());
    SpaceGreyPanel greyPanel = new SpaceGreyPanel();
    greyPanel.setLayout(new BoxLayout(greyPanel, BoxLayout.Y_AXIS));
    SpaceLabel label = new SpaceLabel("Design's Name: ");
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    greyPanel.add(label);
    greyPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    designNameText = new JTextField();
    designNameText.setFont(GuiStatics.getFontCubellan());
    designNameText.setForeground(GuiStatics.COLOR_GREEN_TEXT);
    designNameText.setBackground(Color.BLACK);
    designNameText.addFocusListener(new FocusListener() {

      @Override
      public void focusLost(final FocusEvent e) {
        updatePanels();
      }

      @Override
      public void focusGained(final FocusEvent e) {
        // TODO Auto-generated method stub
      }
    });
    designNameText.addKeyListener(new KeyListener() {

      @Override
      public void keyTyped(final KeyEvent e) {
        // Nothing to do here
      }

      @Override
      public void keyReleased(final KeyEvent e) {
        design.setName(designNameText.getText());
        designInfoText.setText(design.getDesignInfo());
        designInfoText.repaint();
      }

      @Override
      public void keyPressed(final KeyEvent e) {
        // Nothing to do here
      }
    });
    greyPanel.add(designNameText);
    greyPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    label = new SpaceLabel("Ship's hull: ");
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    greyPanel.add(label);
    greyPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    greyPanel.add(hullSelect);
    greyPanel.add(Box.createRigidArea(new Dimension(25, 25)));
    hullPanel.add(greyPanel);

    hullInfoText = new InfoTextArea(10, 30);
    hullInfoText.setEditable(false);
    hullInfoText.setFont(GuiStatics.getFontCubellanSmaller());
    JScrollPane scroll = new JScrollPane(hullInfoText);
    scroll.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    hullPanel.add(Box.createRigidArea(new Dimension(25, 25)));
    hullPanel.add(scroll);
    hullPanel.add(Box.createRigidArea(new Dimension(25, 25)));

    greyPanel = new SpaceGreyPanel();
    greyPanel.setLayout(new BoxLayout(greyPanel, BoxLayout.Y_AXIS));
    hullImage = new ImageLabel(
        ShipImages.humans().getShipImage(ShipImage.COLONY), true);
    hullImage.setFillColor(Color.BLACK);
    greyPanel.add(hullImage);
    greyPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    label = new SpaceLabel("Confirm hull change: ");
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    greyPanel.add(label);
    greyPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    SpaceButton btn = new SpaceButton("Change hull",
        GameCommands.COMMAND_SHIPDESIGN_CHANGEHULL);
    btn.addActionListener(listener);
    greyPanel.add(btn);
    greyPanel.add(Box.createRigidArea(new Dimension(25, 40)));
    hullPanel.add(greyPanel);

    base.add(hullPanel, BorderLayout.NORTH);

    JPanel back = new JPanel();
    back.setLayout(new BoxLayout(back, BoxLayout.Y_AXIS));

    // Component Panel
    InfoPanel componentPanel = new InfoPanel();
    componentPanel.setLayout(new BoxLayout(componentPanel, BoxLayout.X_AXIS));
    componentPanel.setTitle("Ship's components");
    componentPanel.add(Box.createRigidArea(new Dimension(25, 25)));

    greyPanel = new SpaceGreyPanel();
    greyPanel.setLayout(new BoxLayout(greyPanel, BoxLayout.Y_AXIS));
    label = new SpaceLabel("Add components: ");
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    greyPanel.add(label);
    greyPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    ArrayList<String> componentTypes = new ArrayList<>();
    componentTypes.add("All");
    componentTypes.add("Weapons");
    componentTypes.add("Propulsion");
    componentTypes.add("Defense");
    componentTypes.add("Electronics");
    componentTypes.add("Modules");
    componentTypes.add("Invasion");
    for (int i = 0; i < ShipComponentType.values().length; i++) {
      componentTypes.add(ShipComponentType.getTypeByIndex(i).toString());
    }
    componentFilter = new JComboBox<>(
        componentTypes.toArray(new String[componentTypes.size()]));
    componentFilter
        .setActionCommand(GameCommands.COMMAND_SHIPDESIGN_COMPONENTFILTERED);
    componentFilter.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    componentFilter.setForeground(GuiStatics.COLOR_COOL_SPACE_BLUE);
    componentFilter.setBorder(new SimpleBorder());
    componentFilter.setFont(GuiStatics.getFontCubellan());
    componentFilter.addActionListener(listener);
    greyPanel.add(componentFilter);

    greyPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    componentSelect = new JComboBox<>(filterComponents("All"));
    componentSelect
        .setActionCommand(GameCommands.COMMAND_SHIPDESIGN_COMPONENTSELECTED);
    componentSelect.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    componentSelect.setForeground(GuiStatics.COLOR_COOL_SPACE_BLUE);
    componentSelect.setBorder(new SimpleBorder());
    componentSelect.setFont(GuiStatics.getFontCubellan());
    componentSelect.setRenderer(new ShipComponentListRenderer());
    componentSelect.addActionListener(listener);
    greyPanel.add(componentSelect);
    greyPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    componentInfoText = new InfoTextArea(10, 30);
    componentInfoText.setEditable(false);
    componentInfoText.setFont(GuiStatics.getFontCubellanSmaller());
    scroll = new JScrollPane(componentInfoText);
    scroll.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    greyPanel.add(scroll);
    greyPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    btn = new SpaceButton("Add component",
        GameCommands.COMMAND_SHIPDESIGN_COMPONENTADDED);
    btn.addActionListener(listener);
    btn.setSpaceIcon(Icons.getIconByName(Icons.ICON_OK));
    greyPanel.add(btn);
    componentPanel.add(greyPanel);
    componentPanel.add(Box.createRigidArea(new Dimension(5, 5)));

    greyPanel = new SpaceGreyPanel();
    greyPanel.setLayout(new BoxLayout(greyPanel, BoxLayout.Y_AXIS));
    label = new SpaceLabel("Component's energy priority: ");
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    greyPanel.add(label);
    greyPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    componentList = new JList<>(design.getComponentList());
    componentList.setCellRenderer(new ShipComponentListRenderer());
    componentList.setBackground(Color.BLACK);
    componentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    scroll = new JScrollPane(componentList);
    scroll.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    greyPanel.add(scroll);
    greyPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    btn = new SpaceButton("Higher priority",
        GameCommands.COMMAND_SHIPDESIGN_COMPONENT_PRIORITYHI);
    btn.addActionListener(listener);
    btn.setSpaceIcon(Icons.getIconByName(Icons.ICON_ARROWUP));
    greyPanel.add(btn);
    greyPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    btn = new SpaceButton("Lower priority",
        GameCommands.COMMAND_SHIPDESIGN_COMPONENT_PRIORITYLO);
    btn.addActionListener(listener);
    btn.setSpaceIcon(Icons.getIconByName(Icons.ICON_ARROWDOWN));
    greyPanel.add(btn);
    greyPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    btn = new SpaceButton("Remove component",
        GameCommands.COMMAND_SHIPDESIGN_COMPONENTREMOVED);
    btn.addActionListener(listener);
    btn.setSpaceIcon(Icons.getIconByName(Icons.ICON_DELETE));
    greyPanel.add(btn);
    componentPanel.add(greyPanel);
    componentPanel.add(Box.createRigidArea(new Dimension(25, 5)));

    // Design Panel
    InfoPanel designPanel = new InfoPanel();
    designPanel.setLayout(new BoxLayout(designPanel, BoxLayout.X_AXIS));
    designPanel.setTitle("Design information");
    greyPanel = new SpaceGreyPanel();
    greyPanel.setLayout(new BoxLayout(greyPanel, BoxLayout.Y_AXIS));
    designInfoText = new BaseInfoTextArea(10, 30);
    designInfoText.setEditable(false);
    designInfoText.setFont(GuiStatics.getFontCubellanSmaller());
    scroll = new JScrollPane(designInfoText);
    scroll.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    label = new SpaceLabel("Design info:");
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    greyPanel.add(label);
    greyPanel.add(scroll);
    designPanel.add(Box.createRigidArea(new Dimension(15, 5)));
    designPanel.add(greyPanel);
    designPanel.add(Box.createRigidArea(new Dimension(15, 5)));

    greyPanel = new SpaceGreyPanel();
    greyPanel.setLayout(new BoxLayout(greyPanel, BoxLayout.Y_AXIS));
    designFlawsText = new BaseInfoTextArea(10, 30);
    designFlawsText.setEditable(false);
    designFlawsText.setFont(GuiStatics.getFontCubellanSmaller());
    scroll = new JScrollPane(designFlawsText);
    scroll.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    label = new SpaceLabel("Design flaws:");
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    greyPanel.add(label);
    greyPanel.add(scroll);
    designPanel.add(greyPanel);
    designPanel.add(Box.createRigidArea(new Dimension(15, 5)));

    back.add(componentPanel);
    back.add(designPanel);

    base.add(back, BorderLayout.CENTER);

    this.add(base, BorderLayout.CENTER);

    // Bottom panel
    InfoPanel bottomPanel = new InfoPanel();
    bottomPanel.setLayout(new GridLayout(1, 2));
    bottomPanel.setTitle(null);
    btn = new SpaceButton("Cancel", GameCommands.COMMAND_SHIPS);
    btn.addActionListener(listener);
    bottomPanel.add(btn);

    btn = new SpaceButton("Accept design",
        GameCommands.COMMAND_SHIPDESIGN_DONE);
    btn.addActionListener(listener);
    bottomPanel.add(btn);

    // updatePanel();
    // Add panels to base
    this.add(bottomPanel, BorderLayout.SOUTH);

    designInfoText.setText(design.getDesignInfo());
    designInfoText.repaint();
    updatePanels();
  }

  /**
   * Filter component by component tech types. "All" is special filter
   * and all techs are then added.
   * @param filter as a String
   * @return List of ship component
   */
  public ShipComponent[] filterComponents(final String filter) {
    Tech[] allTech = this.player.getTechList().getList();
    ArrayList<ShipComponent> components = new ArrayList<>();
    for (int i = 0; i < allTech.length; i++) {
      if (allTech[i].getComponent() != null) {
        ShipComponent comp = ShipComponentFactory
            .createByName(allTech[i].getComponent());
        if (comp != null && filter.equalsIgnoreCase("All")) {
          components.add(comp);
          continue;
        }
        if (comp != null && filter.equalsIgnoreCase("Weapons")
            && comp.isWeapon()) {
          components.add(comp);
          continue;
        }
        if (comp != null && filter.equalsIgnoreCase("Defense")
            && (comp.getType() == ShipComponentType.ARMOR
                || comp.getType() == ShipComponentType.SHIELD
                || comp.getType() == ShipComponentType.JAMMER)) {
          components.add(comp);
          continue;
        }
        if (comp != null && filter.equalsIgnoreCase("Propulsion")
            && (comp.getType() == ShipComponentType.ENGINE
                || comp.getType() == ShipComponentType.POWERSOURCE
                || comp.getType() == ShipComponentType.THRUSTERS)) {
          components.add(comp);
          continue;
        }
        if (comp != null && filter.equalsIgnoreCase("Electronics")
            && (comp.getType() == ShipComponentType.CLOAKING_DEVICE
                || comp.getType() == ShipComponentType.SCANNER
                || comp.getType() == ShipComponentType.JAMMER
                || comp.getType() == ShipComponentType.SHIELD_GENERATOR
                || comp.getType() == ShipComponentType.TARGETING_COMPUTER
                || comp.getType() == ShipComponentType.ESPIONAGE_MODULE)) {
          components.add(comp);
          continue;
        }
        if (comp != null && filter.equalsIgnoreCase("Invasion")
            && (comp.getType() == ShipComponentType.ORBITAL_BOMBS
                || comp.getType() == ShipComponentType.PLANETARY_INVASION_MODULE
                || comp.getType() == ShipComponentType.ORBITAL_NUKE)) {
          components.add(comp);
          continue;
        }
        if (comp != null && filter.equalsIgnoreCase("Modules")
            && (comp.getType() == ShipComponentType.COLONY_MODULE
                || comp.getType() == ShipComponentType.ESPIONAGE_MODULE
                || comp.getType() == ShipComponentType.PLANETARY_INVASION_MODULE
                || comp.getType() == ShipComponentType.PRIVATEERING_MODULE
                || comp.getType() == ShipComponentType.STARBASE_COMPONENT
                || comp.getType() == ShipComponentType.FIGHTER_BAY)) {
          components.add(comp);
          continue;
        }
        if (comp != null
            && filter.equalsIgnoreCase(comp.getType().toString())) {
          components.add(comp);
          continue;
        }
      }
    }
    return components.toArray(new ShipComponent[components.size()]);
  }

  /**
   * Update visible panels
   */
  public void updatePanels() {
    ShipHull hull = (ShipHull) hullSelect.getSelectedItem();
    if (hull != null) {
      hullInfoText.setText(hull.toString());
      hullImage.setImage(hull.getImage());
    }
    if (design != null) {
      designNameText.setText(design.getName());
      String flaws = design.getFlaws();
      boolean duplicate = player.duplicateShipDesignName(design.getName());
      if (duplicate) {
        illegalName = true;
        designNameText.setForeground(GuiStatics.COLOR_RED_TEXT);
        flaws = flaws + " Ship design with name " + design.getName()
            + " already exists!";
      } else {
        illegalName = false;
        designNameText.setForeground(GuiStatics.COLOR_GREEN_TEXT);
      }
      if (flaws.equals(ShipDesignConsts.DESIGN_OK)) {
        designFlawsText.setForeground(GuiStatics.COLOR_GREEN_TEXT);
      } else {
        designFlawsText.setForeground(GuiStatics.COLOR_RED_TEXT);
      }
      designFlawsText.setText(flaws);
    }
    ShipComponent component = (ShipComponent) componentSelect.getSelectedItem();
    if (component != null) {
      componentInfoText.setText(component.toString());
    }
    if (design != null) {
      componentList.setListData(design.getComponentList());
    }
    this.repaint();
  }

  /**
   * Handle action events for ShipDesignView
   * @param arg0 ActionEvent
   */
  public void handleAction(final ActionEvent arg0) {
    if (arg0.getActionCommand()
        .equals(GameCommands.COMMAND_SHIPDESIGN_HULLSELECTED)) {
      SoundPlayer.playMenuSound();
      updatePanels();
    }
    if (arg0.getActionCommand()
        .equals(GameCommands.COMMAND_SHIPDESIGN_COMPONENTFILTERED)) {
      String filter = (String) componentFilter.getSelectedItem();
      if (filter != null) {
        componentSelect
            .setModel(new DefaultComboBoxModel<>(filterComponents(filter)));
        SoundPlayer.playMenuSound();
        updatePanels();
      }
    }
    if (arg0.getActionCommand()
        .equals(GameCommands.COMMAND_SHIPDESIGN_COMPONENTSELECTED)) {
      SoundPlayer.playMenuSound();
      updatePanels();
    }
    if (arg0.getActionCommand()
        .equals(GameCommands.COMMAND_SHIPDESIGN_CHANGEHULL)) {
      ShipHull hull = (ShipHull) hullSelect.getSelectedItem();
      if (hull != null) {
        design = new ShipDesign(hull);
        design.setName(designNameText.getText());
        designInfoText.setText(design.getDesignInfo());
        designInfoText.repaint();
      }
      SoundPlayer.playMenuSound();
      updatePanels();
    }
    if (arg0.getActionCommand()
        .equals(GameCommands.COMMAND_SHIPDESIGN_COMPONENTADDED)
        && componentSelect.getSelectedItem() != null
        && design.getNumberOfComponents() < design.getHull().getMaxSlot()) {
      design.addComponent((ShipComponent) componentSelect.getSelectedItem());
      design.setName(designNameText.getText());
      designInfoText.setText(design.getDesignInfo());
      designInfoText.repaint();
      SoundPlayer.playMenuSound();
      updatePanels();
    }
    if (arg0.getActionCommand()
        .equals(GameCommands.COMMAND_SHIPDESIGN_COMPONENTREMOVED)
        && componentList.getSelectedValue() != null) {
      int index = componentList.getSelectedIndex();
      design.removeComponent(index);
      design.setName(designNameText.getText());
      designInfoText.setText(design.getDesignInfo());
      designInfoText.repaint();
      SoundPlayer.playMenuSound();
      updatePanels();
    }
    if (arg0.getActionCommand()
        .equals(GameCommands.COMMAND_SHIPDESIGN_COMPONENT_PRIORITYHI)
        && componentList.getSelectedValue() != null) {
      int index = componentList.getSelectedIndex();
      index = design.changePriority(index, true);
      int[] indices = new int[1];
      indices[0] = index;
      updatePanels();
      SoundPlayer.playMenuSound();
      componentList.setSelectedIndices(indices);
    }
    if (arg0.getActionCommand()
        .equals(GameCommands.COMMAND_SHIPDESIGN_COMPONENT_PRIORITYLO)
        && componentList.getSelectedValue() != null) {
      int index = componentList.getSelectedIndex();
      index = design.changePriority(index, false);
      int[] indices = new int[1];
      indices[0] = index;
      updatePanels();
      SoundPlayer.playMenuSound();
      componentList.setSelectedIndices(indices);
    }

  }

  /**
   * Is design OK and ready for going?
   * @return True if design is ready false if not
   */
  public boolean isDesignOK() {
    updatePanels();
     if (design != null
            && design.getFlaws().equals(ShipDesignConsts.DESIGN_OK)
            && !illegalName) {
      return true;
    }
    return false;
  }

  /**
   * Add new design to player and keep it.
   */
  public void keepDesign() {
    if (isDesignOK()) {
      ShipStat stat = new ShipStat(design);
      player.addShipStat(stat);
    }
  }
}
