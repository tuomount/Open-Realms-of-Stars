package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JTabbedPane;

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
* Copyright (C) 2018,2020-2022  Tuomo Untinen
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
   * Fullscreen checkbox options
   */
  private SpaceCheckBox fullscreenBox;

  /**
   * Hardware acceleration checkbox options
   */
  private SpaceCheckBox hardwareAccelerationBox;

  /**
   * Improved parallax checkbox options
   */
  private SpaceCheckBox improvedParallaxBox;

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
   * Borderscrolling checkbox options
   */
  private SpaceCheckBox borderScrollingBox;

  /**
   * Show Minimap checkbox options
   */
  private SpaceCheckBox showMinimapBox;

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
    JTabbedPane tabs = new JTabbedPane();
    tabs.setFont(GuiStatics.getFontCubellanSmaller());
    tabs.setForeground(GuiStatics.COLOR_COOL_SPACE_BLUE_DARKER);
    tabs.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    // Screen panel starts here
    InfoPanel screenPanel = new InfoPanel();
    screenPanel.setLayout(new BoxLayout(screenPanel, BoxLayout.Y_AXIS));
    screenPanel.setTitle("Screen Options");
    fullscreenBox = new SpaceCheckBox("Fullscreen mode");
    fullscreenBox.setToolTipText("Game runs in fullscreen mode."
        + " Requires restart.");
    fullscreenBox.createToolTip();
    fullscreenBox.setSelected(config.isFullscreen());
    xPanel.add(fullscreenBox);
    screenPanel.add(xPanel);
    xPanel = new EmptyInfoPanel();
    xPanel.setLayout(new BoxLayout(xPanel, BoxLayout.X_AXIS));
    xPanel.setAlignmentX(LEFT_ALIGNMENT);
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
    if (gameFrame.isFullscreenMode()) {
      resolutionSelection.setEnabled(false);
    }
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
    if (gameFrame.isFullscreenMode()) {
      btn.setEnabled(false);
    }
    xPanel.add(btn);
    screenPanel.add(xPanel);
    if (!gameFrame.isFullscreenMode()) {
      label = new SpaceLabel("NOTE: Restart is recommended if resolution is"
          + " changed.");
    } else {
      label = new SpaceLabel("NOTE: Resolution can be changed only in windowed"
          + " mode.");
    }
    screenPanel.add(label);
    xPanel = new EmptyInfoPanel();
    xPanel.setLayout(new BoxLayout(xPanel, BoxLayout.X_AXIS));
    xPanel.setAlignmentX(LEFT_ALIGNMENT);
    borderlessBox = new SpaceCheckBox("Borderless frame");
    borderlessBox.setToolTipText("Removes OS's borders from game frame.");
    borderlessBox.createToolTip();
    borderlessBox.setSelected(config.getBorderless());
    if (gameFrame.isFullscreenMode()) {
      borderlessBox.setEnabled(false);
    }
    xPanel.add(borderlessBox);
    if (!gameFrame.isFullscreenMode()) {
      label = new SpaceLabel("Changing this takes affect after restart.");
    } else {
      label = new SpaceLabel("Borderless frame is only for windowed mode.");
    }
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
    xPanel = new EmptyInfoPanel();
    xPanel.setLayout(new BoxLayout(xPanel, BoxLayout.X_AXIS));
    xPanel.setAlignmentX(LEFT_ALIGNMENT);
    hardwareAccelerationBox = new SpaceCheckBox("OpenGL Hardware acceleration");
    hardwareAccelerationBox.setToolTipText("Enables Java's OpenGL support.");
    hardwareAccelerationBox.createToolTip();
    hardwareAccelerationBox.setSelected(config.isHardwareAcceleration());
    xPanel.add(hardwareAccelerationBox);
    screenPanel.add(xPanel);
    xPanel = new EmptyInfoPanel();
    xPanel.setLayout(new BoxLayout(xPanel, BoxLayout.X_AXIS));
    xPanel.setAlignmentX(LEFT_ALIGNMENT);
    improvedParallaxBox = new SpaceCheckBox("Improved parallax on space map");
    improvedParallaxBox.setToolTipText("<html>Enables 2 layer parallax effect"
        + " on space map.<br>This requires quite a lot of CPU.<br>"
        + "This has great impact on battery usage on laptops.</html>");
    improvedParallaxBox.createToolTip();
    improvedParallaxBox.setSelected(config.isImprovedParallax());
    xPanel.add(improvedParallaxBox);
    screenPanel.add(xPanel);
    screenPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    tabs.add("Graphics", screenPanel);
    // Gameplay panel starts here
    InfoPanel gameplayPanel = new InfoPanel();
    gameplayPanel.setLayout(new BoxLayout(gameplayPanel, BoxLayout.Y_AXIS));
    gameplayPanel.setTitle("Gameplay Options");
    xPanel = new EmptyInfoPanel();
    xPanel.setLayout(new BoxLayout(xPanel, BoxLayout.X_AXIS));
    xPanel.setAlignmentX(LEFT_ALIGNMENT);
    borderScrollingBox = new SpaceCheckBox("Automatic border scrolling");
    borderScrollingBox.setToolTipText("<html>Starmap can be scrolled by moving"
        + " mouse on border.<br>"
        + "Other option is by dragging the mouse over starmap.</html>");
    borderScrollingBox.createToolTip();
    borderScrollingBox.setSelected(config.isBorderScrolling());
    xPanel.add(borderScrollingBox);
    gameplayPanel.add(xPanel);
    gameplayPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    xPanel = new EmptyInfoPanel();
    xPanel.setLayout(new BoxLayout(xPanel, BoxLayout.X_AXIS));
    xPanel.setAlignmentX(LEFT_ALIGNMENT);
    showMinimapBox = new SpaceCheckBox("Show minimap on starmap");
    showMinimapBox.setToolTipText("<html>Minimap is shown when game starts."
        + "<br>This can be changed during game.</html>");
    showMinimapBox.createToolTip();
    showMinimapBox.setSelected(config.isShowMinimap());
    xPanel.add(showMinimapBox);
    gameplayPanel.add(xPanel);
    gameplayPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    tabs.add("Gameplay", gameplayPanel);

    // Sound & Music panel starts here
    EmptyInfoPanel soundMusicPanel = new EmptyInfoPanel();
    soundMusicPanel.setLayout(new BoxLayout(soundMusicPanel, BoxLayout.Y_AXIS));
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
    soundMusicPanel.add(musicPanel);
    soundMusicPanel.add(Box.createRigidArea(new Dimension(20, 20)));
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
    soundMusicPanel.add(soundPanel);
    soundMusicPanel.add(Box.createRigidArea(new Dimension(20, 20)));
    tabs.add("Sound", soundMusicPanel);
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
    ambientLightsBox.setToolTipText("<html>Are ambient light effects enabled"
        + " during game.<br>During game play following keys have special"
        + " effect on lights.<br>"
        + "<li> F1 forces warm white ambient light.<li> F2 forces all lights"
        + " go dark.</html>");
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
    lightsSlider.setLabelToolTip("<html>Light intensity value."
        + " More intensive lights will create stronger ambient"
        + " light effects.<br>Effect can be also faster"
        + " compared to lower intensive level.<br>"
        + " Some of the effect this setting has no affect.</html>");
    xPanel.add(lightsSlider);
    lightsPanel.add(xPanel);
    lightsPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    tabs.add("Ambient Lights", lightsPanel);
    allOptions.add(tabs);
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
   * Get Fullscreen value.
   * @return Fullscreen value
   */
  public boolean getFullscreen() {
    return fullscreenBox.isSelected();
  }
  /**
   * Get improved parallax
   * @return Improved parallax value
   */
  public boolean getImprovedParallax() {
    return improvedParallaxBox.isSelected();
  }
  /**
   * Get hardware acceleration value
   * @return Hardware acceleration value
   */
  public boolean getHardwareAcceleration() {
    return hardwareAccelerationBox.isSelected();
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

  /**
   * Is border scrolling enabled or not?
   * @return True if border scrolling is enabled.
   */
  public boolean isBorderScrolling() {
    return borderScrollingBox.isSelected();
  }

  /**
   * Is show minimap enabled or not?
   * @return True if show minimap is enabled.
   */
  public boolean isShowMinimap() {
    return showMinimapBox.isSelected();
  }

}
