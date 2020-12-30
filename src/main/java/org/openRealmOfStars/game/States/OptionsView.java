package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;

import org.openRealmOfStars.audio.music.MusicPlayer;
import org.openRealmOfStars.audio.soundeffect.SoundPlayer;
import org.openRealmOfStars.game.Game;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.game.config.ConfigFile;
import org.openRealmOfStars.gui.borders.SimpleBorder;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.buttons.SpaceCheckBox;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.infopanel.EmptyInfoPanel;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.SpaceLabel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.SpaceSliderPanel;
import org.openRealmOfStars.gui.utilies.GuiStatics;
/**
*
* Open Realm of Stars game project
* Copyright (C) 2018,2020  Tuomo Untinen
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
* OptionsView for configuring volumes and resolution
*
*/
public class OptionsView extends BlackPanel {

  /**
  *
  */
  private static final long serialVersionUID = 1L;

  /**
   * Game for changing resolution
   */
  private Game game;

  /**
   * Resolution selector
   */
  private JComboBox<String> resolutionSelection;

  /**
   * Music volume slider
   */
  private SpaceSliderPanel musicSlider;

  /**
   * Sound volume slider
   */
  private SpaceSliderPanel soundSlider;

  /**
   * Borderless checkbox options
   */
  private SpaceCheckBox borderlessBox;

  /**
   * Larger fonts checkbox options
   */
  private SpaceCheckBox largerFontsBox;

  /**
   * Ambient lights are enabled.
   */
  private SpaceCheckBox ambientLightsBox;
  /**
   * Ambient lights intense slider
   */
  private SpaceSliderPanel lightsSlider;

  /**
   * Screen has been resized;
   */
  private boolean resized = false;

  /**
   * Constructor for OptionsView
   * @param config Current ConfigFile
   * @param gameFrame Game frame
   * @param listener ActionListener
   */
  public OptionsView(final ConfigFile config, final Game gameFrame,
      final ActionListener listener) {
    game = gameFrame;
    //game.setResizable(true);
    InfoPanel base = new InfoPanel();
    base.setTitle("Options");
    this.setLayout(new BorderLayout());
    base.setLayout(new BorderLayout());
    // Accept panel starts here
    InfoPanel acceptPanel = new InfoPanel();
    acceptPanel.setTitle("Options accept and apply");
    acceptPanel.setLayout(new BoxLayout(acceptPanel, BoxLayout.X_AXIS));
    SpaceButton btn = new SpaceButton("Apply", GameCommands.COMMAND_APPLY);
    btn.addActionListener(listener);
    btn.createToolTip();
    btn.setToolTipText("Apply changes but stay in options");
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
    EmptyInfoPanel xPanel = new EmptyInfoPanel();
    xPanel.setLayout(new BoxLayout(xPanel, BoxLayout.X_AXIS));
    xPanel.setAlignmentX(LEFT_ALIGNMENT);
    // Screen panel starts here
    InfoPanel screenPanel = new InfoPanel();
    screenPanel.setLayout(new BoxLayout(screenPanel, BoxLayout.Y_AXIS));
    screenPanel.setTitle("Screen Options");
    SpaceLabel label = new SpaceLabel("Screen resolution");
    xPanel.add(label);
    xPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    String[] resolutions = {"1024x768", "1280x768", "1280x960", "1280x1024",
        "1440x960", "1680x1050", "1920x1080",
        "Custom (" + game.getCurrentResolution() + ")"};
    resolutionSelection = new JComboBox<>(resolutions);
    resolutionSelection.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    resolutionSelection.setForeground(GuiStatics.COLOR_COOL_SPACE_BLUE);
    resolutionSelection.setBorder(new SimpleBorder());
    resolutionSelection.setFont(GuiStatics.getFontCubellan());
    resolutionSelection.setMaximumSize(new Dimension(Integer.MAX_VALUE,
        GuiStatics.TEXT_FIELD_HEIGHT));
    String actualResolution = game.getWidth() + "x" + game.getHeight();
    boolean found = false;
    for (int i = 0; i < resolutions.length; i++) {
      if (actualResolution.equals(resolutions[i])) {
        resolutionSelection.setSelectedIndex(i);
        found = true;
        break;
      }
    }
    if (!found) {
      resolutionSelection.setSelectedIndex(resolutions.length - 1);
    }
    xPanel.add(resolutionSelection);
    xPanel.add(Box.createRigidArea(new Dimension(50, 10)));
    btn = new SpaceButton("Resize", GameCommands.COMMAND_RESIZE);
    btn.addActionListener(listener);
    btn.createToolTip();
    btn.setToolTipText("You can also resize game window here for custom size.");
    xPanel.add(btn);
    screenPanel.add(xPanel);
    label = new SpaceLabel("NOTE: Restart is recommended if resolution is"
        + " changed.");
    screenPanel.add(label);
    xPanel = new EmptyInfoPanel();
    xPanel.setLayout(new BoxLayout(xPanel, BoxLayout.X_AXIS));
    xPanel.setAlignmentX(LEFT_ALIGNMENT);
    borderlessBox = new SpaceCheckBox("Borderless frame");
    borderlessBox.setToolTipText("Removes OS's borders from game frame.");
    borderlessBox.createToolTip();
    borderlessBox.setSelected(config.getBorderless());
    xPanel.add(borderlessBox);
    label = new SpaceLabel("Changing this takes affect after restart.");
    xPanel.add(label);
    screenPanel.add(Box.createRigidArea(new Dimension(20, 20)));
    screenPanel.add(xPanel);
    xPanel = new EmptyInfoPanel();
    xPanel.setLayout(new BoxLayout(xPanel, BoxLayout.X_AXIS));
    xPanel.setAlignmentX(LEFT_ALIGNMENT);
    largerFontsBox = new SpaceCheckBox("Larger fonts");
    largerFontsBox.setToolTipText("Makes all fonts slightly larger."
        + " Y-Resolution should be higher than 768 pixels.");
    largerFontsBox.createToolTip();
    largerFontsBox.setSelected(config.getLargerFonts());
    xPanel.add(largerFontsBox);
    screenPanel.add(xPanel);
    screenPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    allOptions.add(Box.createRigidArea(new Dimension(20, 20)));
    allOptions.add(screenPanel);
    allOptions.add(Box.createRigidArea(new Dimension(20, 20)));
    // Music panel starts here
    InfoPanel musicPanel = new InfoPanel();
    musicPanel.setTitle("Music Options");
    musicPanel.setLayout(new BoxLayout(musicPanel, BoxLayout.Y_AXIS));
    xPanel = new EmptyInfoPanel();
    xPanel.setLayout(new BoxLayout(xPanel, BoxLayout.X_AXIS));
    xPanel.setAlignmentX(LEFT_ALIGNMENT);
    label = new SpaceLabel("Music volume");
    xPanel.add(label);
    xPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    musicSlider = new SpaceSliderPanel(
        GameCommands.COMMAND_MUSIC_VOLUME_DN,
        GameCommands.COMMAND_MUSIC_VOLUME_UP,
        Icons.ICON_MUSIC, "Music volume", 0, 100, MusicPlayer.getVolume(),
        GameCommands.COMMAND_MUSIC_VOLUME, listener);
    musicSlider.setSliderMajorTick(10);
    musicSlider.setSliderMinorTick(10);
    xPanel.add(musicSlider);
    musicPanel.add(xPanel);
    musicPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    allOptions.add(musicPanel);
    allOptions.add(Box.createRigidArea(new Dimension(20, 20)));
    // Sound panel starts here
    InfoPanel soundPanel = new InfoPanel();
    soundPanel.setTitle("Sound Options");
    soundPanel.setLayout(new BoxLayout(soundPanel, BoxLayout.Y_AXIS));
    xPanel = new EmptyInfoPanel();
    xPanel.setLayout(new BoxLayout(xPanel, BoxLayout.X_AXIS));
    xPanel.setAlignmentX(LEFT_ALIGNMENT);
    label = new SpaceLabel("Sound volume");
    xPanel.add(label);
    xPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    soundSlider = new SpaceSliderPanel(
        GameCommands.COMMAND_SOUND_VOLUME_DN,
        GameCommands.COMMAND_SOUND_VOLUME_UP,
        Icons.ICON_SOUND, "Sound volume", 0, 100, SoundPlayer.getSoundVolume(),
        GameCommands.COMMAND_SOUND_VOLUME, listener);
    soundSlider.setSliderMajorTick(10);
    soundSlider.setSliderMinorTick(10);
    xPanel.add(soundSlider);
    soundPanel.add(xPanel);
    soundPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    allOptions.add(soundPanel);
    allOptions.add(Box.createRigidArea(new Dimension(20, 20)));
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
    label = new SpaceLabel("Light bridge at <IP HERE OR HOSTNAME WHICH"
        + " CAN BE LONG>");
    if (config.getBridgeHost() != null) {
      label.setText("Light bridge at " + config.getBridgeHost());
    } else {
      label.setText("Light bridge not setup yet.");
    }
    xPanel.add(label);
    lightsPanel.add(xPanel);
    lightsPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    xPanel = new EmptyInfoPanel();
    xPanel.setLayout(new BoxLayout(xPanel, BoxLayout.X_AXIS));
    xPanel.setAlignmentX(LEFT_ALIGNMENT);
    btn = new SpaceButton("Setup lights", GameCommands.COMMAND_SETUP_LIGHTS);
    btn.addActionListener(listener);
    xPanel.add(btn);
    lightsPanel.add(xPanel);
    lightsPanel.add(Box.createRigidArea(new Dimension(10, 10)));
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
    allOptions.add(lightsPanel);
    allOptions.add(Box.createRigidArea(new Dimension(20,
        game.getHeight() - 280)));
    base.add(allOptions, BorderLayout.CENTER);
    this.add(base, BorderLayout.CENTER);
  }

  /**
   * Set volumes according the sliders
   */
  private void setVolumes() {
    MusicPlayer.setVolume(musicSlider.getSliderValue());
    SoundPlayer.setSoundVolume(soundSlider.getSliderValue());
  }
  /**
   * Handle events for options view
   * @param arg0 ActionEvent
   */
  public void handleAction(final ActionEvent arg0) {
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_RESIZE)) {
      SoundPlayer.playMenuSound();
      game.setResizable(true);
      resized = true;
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_MUSIC_VOLUME_UP)
        && musicSlider.getSliderValue() + 10 <= musicSlider.getMaximumValue()) {
      musicSlider.setSliderValue(musicSlider.getSliderValue() + 10);
      setVolumes();
      SoundPlayer.playMenuSound();
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_MUSIC_VOLUME_DN)
        && musicSlider.getSliderValue() - 10 >= musicSlider.getMinimumValue()) {
      musicSlider.setSliderValue(musicSlider.getSliderValue() - 10);
      setVolumes();
      SoundPlayer.playMenuSound();
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_SOUND_VOLUME_UP)
        && soundSlider.getSliderValue() + 10 <= soundSlider.getMaximumValue()) {
      soundSlider.setSliderValue(soundSlider.getSliderValue() + 10);
      setVolumes();
      SoundPlayer.playMenuSound();
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_SOUND_VOLUME_DN)
        && soundSlider.getSliderValue() - 10 >= soundSlider.getMinimumValue()) {
      soundSlider.setSliderValue(soundSlider.getSliderValue() - 10);
      setVolumes();
      SoundPlayer.playMenuSound();
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_MUSIC_VOLUME)) {
      setVolumes();
      SoundPlayer.playMenuSound();
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_SOUND_VOLUME)) {
      setVolumes();
      SoundPlayer.playMenuSound();
    }
  }

  /**
   * Get selected resolution.
   * @return Resolution as String
   */
  public String getResolution() {
    String result = (String) resolutionSelection.getSelectedItem();
    if (result.startsWith("Custom ") || resized) {
      result = game.getCurrentResolution();
    }
    return result;
  }

  /**
   * Get Music slider value
   * @return Music volume
   */
  public int getMusicVolume() {
    return musicSlider.getSliderValue();
  }

  /**
   * Get Sound slider value
   * @return Music volume
   */
  public int getSoundVolume() {
    return soundSlider.getSliderValue();
  }

  /**
   * Get borderless value
   * @return Borderless value
   */
  public boolean getBorderless() {
    return borderlessBox.isSelected();
  }

  /**
   * Get larger fonts value
   * @return Larger fonts value
   */
  public boolean getLargerFonts() {
    return largerFontsBox.isSelected();
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
