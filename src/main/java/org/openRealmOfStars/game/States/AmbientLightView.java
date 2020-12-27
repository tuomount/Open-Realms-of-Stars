package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.openRealmOfStars.ambient.Bridge;
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
   * Light selector
   */
  private JComboBox<String> centerLightSelection;
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
   * Config file.
   */
  private ConfigFile config;
  /**
   * Constructor for AmbientLightView
   * @param config Current ConfigFile
   * @param bridge Active bridge
   * @param listener ActionListener
   */
  public AmbientLightView(final ConfigFile config, final Bridge bridge,
      final ActionListener listener) {
    this.bridge = bridge;
    this.config = config;
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
    connectBtn = new SpaceButton("Register",
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
        Icons.ICON_PROPULSION_TECH, "Light intense", 0, 4, 4,
        GameCommands.COMMAND_LIGHTS_INTENSE, listener);
    lightsSlider.setSliderMajorTick(1);
    lightsSlider.setSliderMinorTick(1);
    xPanel.add(lightsSlider);
    lightsPanel.add(xPanel);
    lightsPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    xPanel = new EmptyInfoPanel();
    xPanel.setLayout(new BoxLayout(xPanel, BoxLayout.X_AXIS));
    xPanel.setAlignmentX(LEFT_ALIGNMENT);
    label = new SpaceLabel("Center light");
    xPanel.add(label);
    String[] lightNames = {"None"};
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

    allOptions.add(lightsPanel);

    base.add(allOptions, BorderLayout.CENTER);
    this.add(base, BorderLayout.CENTER);
  }

  /**
   * Handle events for options view
   * @param arg0 ActionEvent
   */
  public void handleAction(final ActionEvent arg0) {
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_BRIDGE_CONNECT)) {
      SoundPlayer.playMenuSound();
      if (bridge == null) {
        bridge = new Bridge(hostnameField.getText());
      }
      try {
        bridge.register();
        if (bridge.getStatus() != BridgeStatusType.REGISTERED) {
          infoText.setText(bridge.getLastErrorMsg());
        } else {
          config.setBridgeHost(bridge.getHostname());
          config.setBridgeUsername(bridge.getUsername());
          infoText.setText("Registered successfully");
        }
        updatePanels();
      } catch (IOException e) {
        infoText.setText(e.getMessage());
      }
    }
  }

  /**
   * Update panels for ambient lights view.
   */
  public void updatePanels() {
    if (bridge.getUsername() != null) {
      usernameField.setText(bridge.getUsername());
    } else {
      usernameField.setText("Not registered");
    }
    if (bridge.getLastErrorMsg() != null) {
      infoText.setText(bridge.getLastErrorMsg());
    } else {
      infoText.setText("");
    }
    this.repaint();
  }
}
