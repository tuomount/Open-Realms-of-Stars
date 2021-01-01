package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.openRealmOfStars.ambient.Bridge;
import org.openRealmOfStars.ambient.BridgeCommandType;
import org.openRealmOfStars.ambient.BridgeStatusType;
import org.openRealmOfStars.audio.soundeffect.SoundPlayer;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.game.config.ConfigFile;
import org.openRealmOfStars.gui.borders.SimpleBorder;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.buttons.SpaceCheckBox;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.infopanel.EmptyInfoPanel;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.InfoTextArea;
import org.openRealmOfStars.gui.labels.SpaceLabel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.SpaceSliderPanel;
import org.openRealmOfStars.gui.utilies.GuiStatics;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2020  Tuomo Untinen
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
* AmbientLightView for configuring ambient light and settings.
*
*/
public class AmbientLightView extends BlackPanel {

  /**
  *
  */
  private static final long serialVersionUID = 1L;

  /**
   * Bridge hostname or IP address.
   */
  private JTextField hostnameField;

  /**
   * Username field.
   */
  private JTextField usernameField;
  /**
   * Button for registering bridge or testing connection
   */
  private SpaceButton connectBtn;
  /**
   * Info Text for bridge information.
   */
  private InfoTextArea infoText;
  /**
   * Light selector for center
   */
  private JComboBox<String> centerLightSelection;
  /**
   * Light selector for left
   */
  private JComboBox<String> leftLightSelection;
  /**
   * Light selector for right
   */
  private JComboBox<String> rightLightSelection;
  /**
   * Light effect for testing
   */
  private JComboBox<String> effectSelection;
  /**
   * Ambient lights are enabled.
   */
  private SpaceCheckBox ambientLightsBox;
  /**
   * Ambient lights intense slider
   */
  private SpaceSliderPanel lightsSlider;

  /**
   * Active bridge
   */
  private Bridge bridge;
  /**
   * Has light list been updated.
   */
  private boolean lightListUpdated;
  /**
   * Constructor for AmbientLightView
   * @param config Current ConfigFile
   * @param bridge Active bridge
   * @param listener ActionListener
   */
  public AmbientLightView(final ConfigFile config, final Bridge bridge,
      final ActionListener listener) {
    this.bridge = bridge;
    if (bridge == null && config.getBridgeHost() != null
          && config.getBridgeUsername() != null) {
      this.bridge = new Bridge(config.getBridgeHost());
      this.bridge.setUsername(config.getBridgeUsername());
      this.bridge.setCenterLightName(config.getCenterLight());
      this.bridge.setLeftLightName(config.getLeftLight());
      this.bridge.setRightLightName(config.getRightLight());
      this.bridge.setIntense(config.getLightIntense());
      this.bridge.setLightsEnabled(true);
    }
    InfoPanel base = new InfoPanel();
    base.setTitle("Ambient Lights (EXPERIMENTAL)");
    this.setLayout(new BorderLayout());
    base.setLayout(new BorderLayout());
    // Accept panel starts here
    InfoPanel acceptPanel = new InfoPanel();
    acceptPanel.setTitle("Ambient lights accept and apply");
    acceptPanel.setLayout(new BoxLayout(acceptPanel, BoxLayout.X_AXIS));
    SpaceButton btn = new SpaceButton("Apply", GameCommands.COMMAND_APPLY);
    btn.addActionListener(listener);
    btn.createToolTip();
    btn.setToolTipText("Apply changes but stay in light setup");
    acceptPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    acceptPanel.add(btn);
    acceptPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    btn = new SpaceButton("Cancel", GameCommands.COMMAND_CANCEL);
    btn.addActionListener(listener);
    btn.createToolTip();
    btn.setToolTipText("Cancel changes and exit");
    acceptPanel.add(btn);
    acceptPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    btn = new SpaceButton("OK", GameCommands.COMMAND_OK);
    btn.addActionListener(listener);
    btn.createToolTip();
    btn.setToolTipText("Apply changes and exit");
    acceptPanel.add(btn);
    base.add(acceptPanel, BorderLayout.NORTH);
    EmptyInfoPanel allOptions = new EmptyInfoPanel();
    allOptions.setLayout(new BoxLayout(allOptions, BoxLayout.Y_AXIS));
    // Bridge panel starts here
    InfoPanel bridgePanel = new InfoPanel();
    bridgePanel.setLayout(new BoxLayout(bridgePanel, BoxLayout.Y_AXIS));
    bridgePanel.setTitle("Bridge setup");
    SpaceLabel label = new SpaceLabel("Bridge hostname");
    EmptyInfoPanel xPanel = new EmptyInfoPanel();
    xPanel.setLayout(new BoxLayout(xPanel, BoxLayout.X_AXIS));
    xPanel.setAlignmentX(LEFT_ALIGNMENT);
    xPanel.add(label);
    xPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    hostnameField = new JTextField();
    hostnameField.setText(config.getBridgeHost());
    hostnameField.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    hostnameField.setForeground(GuiStatics.COLOR_COOL_SPACE_BLUE);
    hostnameField.setFont(GuiStatics.getFontCubellanSmaller());
    hostnameField.setMaximumSize(new Dimension(Integer.MAX_VALUE,
        GuiStatics.TEXT_FIELD_HEIGHT));
    xPanel.add(hostnameField);
    xPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    connectBtn = new SpaceButton("Connect",
        GameCommands.COMMAND_BRIDGE_CONNECT);
    connectBtn.addActionListener(listener);
    xPanel.add(connectBtn);
    xPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    bridgePanel.add(xPanel);
    bridgePanel.add(Box.createRigidArea(new Dimension(10, 10)));
    xPanel = new EmptyInfoPanel();
    xPanel.setLayout(new BoxLayout(xPanel, BoxLayout.X_AXIS));
    xPanel.setAlignmentX(LEFT_ALIGNMENT);
    xPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    SpaceLabel usernameLabel = new SpaceLabel("Username");
    xPanel.add(usernameLabel);
    usernameField = new JTextField();
    usernameField.setText(config.getBridgeHost());
    usernameField.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    usernameField.setForeground(GuiStatics.COLOR_COOL_SPACE_BLUE);
    usernameField.setFont(GuiStatics.getFontCubellanSmaller());
    usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE,
        GuiStatics.TEXT_FIELD_HEIGHT));
    if (config.getBridgeUsername() != null) {
      usernameField.setText(config.getBridgeUsername());
    }
    xPanel.add(usernameField);
    bridgePanel.add(xPanel);
    bridgePanel.add(Box.createRigidArea(new Dimension(10, 10)));
    xPanel = new EmptyInfoPanel();
    xPanel.setLayout(new BoxLayout(xPanel, BoxLayout.X_AXIS));
    xPanel.setAlignmentX(LEFT_ALIGNMENT);
    xPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    infoText = new InfoTextArea();
    infoText.setLineWrap(true);
    infoText.setCharacterWidth(7);
    infoText.setEditable(false);
    infoText.setFont(GuiStatics.getFontCubellanSmaller());
    xPanel.add(infoText);
    xPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    bridgePanel.add(xPanel);
    bridgePanel.add(Box.createRigidArea(new Dimension(10, 10)));
    allOptions.add(bridgePanel);
    // Lights panel starts here
    InfoPanel lightsPanel = new InfoPanel();
    lightsPanel.setTitle("Ambient lights");
    lightsPanel.setLayout(new BoxLayout(lightsPanel, BoxLayout.Y_AXIS));
    xPanel = new EmptyInfoPanel();
    xPanel.setLayout(new BoxLayout(xPanel, BoxLayout.X_AXIS));
    xPanel.setAlignmentX(LEFT_ALIGNMENT);
    ambientLightsBox = new SpaceCheckBox("Enable ambient lights");
    if (config.getAmbientLights()) {
      ambientLightsBox.setSelected(true);
    }
    xPanel.add(ambientLightsBox);
    lightsPanel.add(xPanel);
    xPanel = new EmptyInfoPanel();
    xPanel.setLayout(new BoxLayout(xPanel, BoxLayout.X_AXIS));
    xPanel.setAlignmentX(LEFT_ALIGNMENT);
    label = new SpaceLabel("Light intense");
    xPanel.add(label);
    lightsSlider = new SpaceSliderPanel(
        GameCommands.COMMAND_LIGHTS_DN,
        GameCommands.COMMAND_LIGHTS_UP,
        Icons.ICON_PROPULSION_TECH, "Light intense", 1, 5,
        config.getLightIntense(), GameCommands.COMMAND_LIGHTS_INTENSE,
        listener);
    lightsSlider.setSliderMajorTick(1);
    lightsSlider.setSliderMinorTick(1);
    xPanel.add(lightsSlider);
    lightsPanel.add(xPanel);
    lightsPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    xPanel = new EmptyInfoPanel();
    xPanel.setLayout(new BoxLayout(xPanel, BoxLayout.X_AXIS));
    xPanel.setAlignmentX(LEFT_ALIGNMENT);
    label = new SpaceLabel("Left light");
    xPanel.add(label);
    String[] lightNames = {"none"};
    leftLightSelection = new JComboBox<>(lightNames);
    leftLightSelection.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    leftLightSelection.setForeground(GuiStatics.COLOR_COOL_SPACE_BLUE);
    leftLightSelection.setBorder(new SimpleBorder());
    leftLightSelection.setFont(GuiStatics.getFontCubellan());
    leftLightSelection.setMaximumSize(new Dimension(Integer.MAX_VALUE,
        GuiStatics.TEXT_FIELD_HEIGHT));
    xPanel.add(leftLightSelection);
    xPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    lightsPanel.add(xPanel);
    lightsPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    xPanel = new EmptyInfoPanel();
    xPanel.setLayout(new BoxLayout(xPanel, BoxLayout.X_AXIS));
    xPanel.setAlignmentX(LEFT_ALIGNMENT);
    label = new SpaceLabel("Center light");
    xPanel.add(label);
    centerLightSelection = new JComboBox<>(lightNames);
    centerLightSelection.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    centerLightSelection.setForeground(GuiStatics.COLOR_COOL_SPACE_BLUE);
    centerLightSelection.setBorder(new SimpleBorder());
    centerLightSelection.setFont(GuiStatics.getFontCubellan());
    centerLightSelection.setMaximumSize(new Dimension(Integer.MAX_VALUE,
        GuiStatics.TEXT_FIELD_HEIGHT));
    xPanel.add(centerLightSelection);
    xPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    lightsPanel.add(xPanel);
    lightsPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    xPanel = new EmptyInfoPanel();
    xPanel.setLayout(new BoxLayout(xPanel, BoxLayout.X_AXIS));
    xPanel.setAlignmentX(LEFT_ALIGNMENT);
    label = new SpaceLabel("Right light");
    xPanel.add(label);
    rightLightSelection = new JComboBox<>(lightNames);
    rightLightSelection.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    rightLightSelection.setForeground(GuiStatics.COLOR_COOL_SPACE_BLUE);
    rightLightSelection.setBorder(new SimpleBorder());
    rightLightSelection.setFont(GuiStatics.getFontCubellan());
    rightLightSelection.setMaximumSize(new Dimension(Integer.MAX_VALUE,
        GuiStatics.TEXT_FIELD_HEIGHT));
    xPanel.add(rightLightSelection);
    xPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    lightsPanel.add(xPanel);
    lightsPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    xPanel = new EmptyInfoPanel();
    xPanel.setLayout(new BoxLayout(xPanel, BoxLayout.X_AXIS));
    xPanel.setAlignmentX(LEFT_ALIGNMENT);
    label = new SpaceLabel("Effect for testing");
    xPanel.add(label);
    String[] effectList = {Bridge.EFFECT_WARM_WHITE, Bridge.EFFECT_DARKEST,
        Bridge.EFFECT_RED_ALERT, Bridge.EFFECT_YELLOW_ALERT,
        Bridge.EFFECT_NUKE, Bridge.EFFECT_FLOAT_IN_SPACE,
        Bridge.EFFECT_GREEN_CONSOLE, Bridge.EFFECT_SPACE_CONSOLE};
    effectSelection = new JComboBox<>(effectList);
    effectSelection.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    effectSelection.setForeground(GuiStatics.COLOR_COOL_SPACE_BLUE);
    effectSelection.setBorder(new SimpleBorder());
    effectSelection.setFont(GuiStatics.getFontCubellan());
    effectSelection.setMaximumSize(new Dimension(Integer.MAX_VALUE,
        GuiStatics.TEXT_FIELD_HEIGHT));
    xPanel.add(effectSelection);
    xPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    btn = new SpaceButton("Test", GameCommands.COMMAND_LIGHT_TEST);
    btn.addActionListener(listener);
    btn.createToolTip();
    btn.setToolTipText("Test effect with selected lights");
    xPanel.add(btn);
    xPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    lightsPanel.add(xPanel);
    lightsPanel.add(Box.createRigidArea(new Dimension(10, 10)));

    allOptions.add(lightsPanel);

    base.add(allOptions, BorderLayout.CENTER);
    this.add(base, BorderLayout.CENTER);
    if (bridge.getStatus() == BridgeStatusType.CONNECTED
        && bridge.getLigths() != null) {
      updateLightsFromBridge();
    }
  }

  /**
   * Handle events for options view
   * @param arg0 ActionEvent
   */
  public void handleAction(final ActionEvent arg0) {
    if (arg0.getActionCommand()
        .equalsIgnoreCase(GameCommands.COMMAND_ANIMATION_TIMER)) {
      updatePanels();
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_BRIDGE_CONNECT)) {
      SoundPlayer.playMenuSound();
      if (bridge == null) {
        bridge = new Bridge(hostnameField.getText());
      }
      if (bridge.getHostname() != hostnameField.getText()) {
        bridge.setHostname(hostnameField.getText());
      }
      if (bridge.getUsername() != usernameField.getText()) {
        bridge.setUsername(usernameField.getText());
      }
      if (usernameField.getText().isEmpty()) {
        bridge.setNextCommand(BridgeCommandType.REGISTER);
      } else {
        bridge.setNextCommand(BridgeCommandType.TEST);
      }
      updatePanels();
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_LIGHT_TEST)) {
      SoundPlayer.playMenuSound();
      if (bridge != null && bridge.getStatus() == BridgeStatusType.CONNECTED) {
        updateBridge();
        String effectName = (String) effectSelection.getSelectedItem();
        if (effectName.equals(Bridge.EFFECT_WARM_WHITE)) {
          bridge.setNextCommand(BridgeCommandType.WARM_WHITE);
        }
        if (effectName.equals(Bridge.EFFECT_DARKEST)) {
          bridge.setNextCommand(BridgeCommandType.DARKEST);
        }
        if (effectName.equals(Bridge.EFFECT_RED_ALERT)) {
          bridge.setNextCommand(BridgeCommandType.RED_ALERT);
        }
        if (effectName.equals(Bridge.EFFECT_YELLOW_ALERT)) {
          bridge.setNextCommand(BridgeCommandType.YELLOW_ALERT);
        }
        if (effectName.equals(Bridge.EFFECT_NUKE)) {
          bridge.setNextCommand(BridgeCommandType.NUKE_START);
        }
        if (effectName.equals(Bridge.EFFECT_FLOAT_IN_SPACE)) {
          bridge.setNextCommand(BridgeCommandType.FLOAT_IN_SPACE);
        }
        if (effectName.equals(Bridge.EFFECT_GREEN_CONSOLE)) {
          bridge.setNextCommand(BridgeCommandType.GREEN_CONSOLE);
        }
        if (effectName.equals(Bridge.EFFECT_SPACE_CONSOLE)) {
          bridge.setNextCommand(BridgeCommandType.SPACE_CONSOLE);
        }
      }
    }
  }

  /**
   * Update bridge lights and other stuff from settings.
   */
  public void updateBridge() {
    if (bridge != null && bridge.getStatus() == BridgeStatusType.CONNECTED) {
      bridge.setLeftLightName(getLeftLight());
      bridge.setRightLightName(getRightLight());
      bridge.setCenterLightName(getCenterLight());
      bridge.setIntense(getIntense());
    }
  }
  /**
   * Update lights from the bridge.
   */
  public void updateLightsFromBridge() {
    String[] lightNames = new String[bridge.getLigths().size() + 1];
    lightNames[0] = "none";
    for (int i = 0; i < bridge.getLigths().size(); i++) {
      lightNames[i + 1] = bridge.getLigths().get(i)
          .getHumanReadablename();
    }
    leftLightSelection.setModel(
        new DefaultComboBoxModel<>(lightNames));
    centerLightSelection.setModel(
        new DefaultComboBoxModel<>(lightNames));
    rightLightSelection.setModel(
        new DefaultComboBoxModel<>(lightNames));
    centerLightSelection.setSelectedItem(bridge.getCenterLightName());
    leftLightSelection.setSelectedItem(bridge.getLeftLightName());
    rightLightSelection.setSelectedItem(bridge.getRightLightName());
    lightListUpdated = false;
  }
  /**
   * Update panels for ambient lights view.
   */
  public void updatePanels() {
    if (bridge == null) {
      return;
    }
    if (bridge.getStatus() == BridgeStatusType.NOT_CONNECTED
        || bridge.getStatus() == BridgeStatusType.REGISTERED) {
      if (bridge.getHostname() != null && bridge.getUsername() != null) {
        usernameField.setText(bridge.getUsername());
        bridge.setNextCommand(BridgeCommandType.TEST);
      } else {
        infoText.setText("Register bridge. Press sync button on HUE"
            + " bridge before clicking register button.");
      }
      if (bridge.getStatus() == BridgeStatusType.REGISTERED) {
        infoText.setText("Registered successfully.");
      }
    } else if (bridge.getStatus() == BridgeStatusType.REGISTERING) {
      infoText.setText("Register bridge ...");
    } else if (bridge.getStatus() == BridgeStatusType.CONNECTING) {
      infoText.setText("Connecting bridge ...");
    } else if (bridge.getStatus() == BridgeStatusType.CONNECTED) {
      infoText.setText("Connected to bridge.");
      if (bridge.getLigths() == null) {
        bridge.setNextCommand(BridgeCommandType.FETCH_LIGHTS);
        lightListUpdated = true;
      } else {
        infoText.setText("Connected to bridge."
            + " Found " + bridge.getLigths().size() + " lights.");
        if (lightListUpdated) {
          updateLightsFromBridge();
        }
      }
    } else if (bridge.getStatus() == BridgeStatusType.ERROR) {
      infoText.setText("Error: " + bridge.getLastErrorMsg());
    }
    this.repaint();
  }

  /**
   * Get bridge from view.
   * @return Bridge
   */
  public Bridge getBridge() {
    return bridge;
  }

  /**
   * Get left light name
   * @return Left light name as String.
   */
  public String getLeftLight() {
    return (String) leftLightSelection.getSelectedItem();
  }

  /**
   * Get right light name
   * @return Right light name as String.
   */
  public String getRightLight() {
    return (String) rightLightSelection.getSelectedItem();
  }

  /**
   * Get center light name
   * @return Center light name as String.
   */
  public String getCenterLight() {
    return (String) centerLightSelection.getSelectedItem();
  }

  /**
   * Get intense value from slider.
   * @return Intense value from slider.
   */
  public int getIntense() {
    return lightsSlider.getSliderValue();
  }

  /**
   * Is ambient lights enabled or not?
   * @return True if lights are enabled.
   */
  public boolean isLightsEnabled() {
    return ambientLightsBox.isSelected();
  }
}