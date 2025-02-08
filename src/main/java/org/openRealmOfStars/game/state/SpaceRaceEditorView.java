package org.openRealmOfStars.game.state;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2024 Tuomo Untinen
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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.openRealmOfStars.ambient.Bridge;
import org.openRealmOfStars.ambient.BridgeCommandType;
import org.openRealmOfStars.audio.music.MusicFileInfo;
import org.openRealmOfStars.audio.music.MusicPlayer;
import org.openRealmOfStars.audio.soundeffect.SoundPlayer;
import org.openRealmOfStars.game.Game;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.game.state.options.GenderOption;
import org.openRealmOfStars.gui.borders.SimpleBorder;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.graphs.BridgeGraphFactory;
import org.openRealmOfStars.gui.infopanel.EmptyInfoPanel;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.infopanel.traitpanel.TraitCheckBox;
import org.openRealmOfStars.gui.infopanel.traitpanel.TraitPanel;
import org.openRealmOfStars.gui.labels.ImageLabel;
import org.openRealmOfStars.gui.labels.SpaceComboBox;
import org.openRealmOfStars.gui.labels.SpaceLabel;
import org.openRealmOfStars.gui.list.GenderOptionListRenderer;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.ShipInteriorPanel;
import org.openRealmOfStars.gui.panels.SpaceGreyPanel;
import org.openRealmOfStars.gui.util.GuiFonts;
import org.openRealmOfStars.gui.util.GuiStatics;
import org.openRealmOfStars.player.diplomacy.Attitude;
import org.openRealmOfStars.player.diplomacy.speeches.SpeechFactory;
import org.openRealmOfStars.player.leader.Gender;
import org.openRealmOfStars.player.leader.NameGeneratorType;
import org.openRealmOfStars.player.race.SocialSystem;
import org.openRealmOfStars.player.race.SpaceRace;
import org.openRealmOfStars.player.race.SpaceRaceFactory;
import org.openRealmOfStars.player.race.SpaceRaceLoader;
import org.openRealmOfStars.player.race.trait.RaceTrait;
import org.openRealmOfStars.player.race.trait.TraitFactory;
import org.openRealmOfStars.player.ship.ShipImage;
import org.openRealmOfStars.player.ship.ShipImageFactor;
import org.openRealmOfStars.utilities.ErrorLogger;
import org.openRealmOfStars.utilities.TextUtilities;
import org.openRealmOfStars.utilities.FileIo.Folders;

/**
 * Editor for government JSON files with UI.
 *
 */
public class SpaceRaceEditorView extends BlackPanel {

  /**
  *
  */
  private static final long serialVersionUID = 1L;

  /**
   * Screen width.
   */
  private int screenWidth;
  /**
   * Screen height.
   */
  private int screenHeight;
  /** Gap Height in pixels. */
  private int gapY = 5;
  /**
   * SpaceRace name.
   */
  private JTextField spaceRaceNameField;
  /**
   * SpaceRace name in single.
   */
  private JTextField spaceRaceNameSingleField;

  /**
   * Ship timer to change ship image.
   */
  private Timer shipTimer;
  /**
   * Combobox for space ship bridge selection.
   */
  private SpaceComboBox<String> bridgeIdCombo;
  /**
   * Combobox for attitude selection.
   */
  private SpaceComboBox<Attitude> attitudeCombo;
  /**
   * Combobox for social system selection.
   */
  private SpaceComboBox<SocialSystem> socialCombo;
  /**
   * Combobox for gender option selection.
   */
  private SpaceComboBox<GenderOption> genderCombo;
  /**
   * Combobox for speechset selection.
   */
  private SpaceComboBox<String> speechCombo;
  /**
   * Combobox for namegenerator selection.
   */
  private SpaceComboBox<NameGeneratorType> nameGenCombo;
  /**
   * Ship interior panel, to show how space race looks.
   */
  private ShipInteriorPanel interiorPanel;
  /**
   * Ship interior panel, to show how space race looks.
   */
  private ShipInteriorPanel interiorPanel2;
  /**
   * List of built int space race images and custom one.
   */
  private String[] raceImages;
  /**
   * Custom image index.
   */
  private int customImageIndex;
  /**
   * Combobox for space race image
   */
  private SpaceComboBox<String> spaceRaceImageCombo;
  /**
   * Browse button for space race image.
   */
  private SpaceButton browseButton;
  /**
   * Combobox for bridge effect.
   */
  private SpaceComboBox<String> bridgeEffectCombo;
  /**
   * Combobox for space ship id
   */
  private SpaceComboBox<String> spaceShipIdCombo;
  /**
   * Combobox for diplomacy music
   */
  private SpaceComboBox<MusicFileInfo> diplomacyMusicCombo;
  /**
   * Hull Image count
   */
  private int hullImageCount = 0;
  /**
   * Space ship preview image.
   */
  private ImageLabel hullImage;
  /**
   * Space ship hull label
   */
  private SpaceLabel hullNameLabel;
  /**
   * Space race description text.
   */
  private JTextArea descriptionText;
  /** Space race to be created */
  private SpaceRace newRace;
  /**
   * Trait panel containing all the selectable traits.
   */
  private TraitPanel traitPanel;
  /**
   * Conflict with government ID.
   */
  private boolean conflictWithId = false;
  /** Game object. */
  private Game game;


  /**
   * Constructor for GovernmentEditorView.
   *
   * @param listener ActionListener
   */
  public SpaceRaceEditorView(final ActionListener listener) {
    this.setLayout(new BorderLayout());
    newRace = new SpaceRace("CUSTOM_TERRANS", "Custom Terrans",
        "Custom Terran");
    newRace.setBridgeId("Human");
    newRace.setImage("resources/images/human_race.png");
    newRace.setSpaceShipId("Human");
    newRace.setRaceBridgeEffect(BridgeCommandType.BLUEISH_WHITE);
    screenWidth = 1024;
    screenHeight = 768;
    if (listener instanceof Game) {
      game = (Game) listener;
      screenWidth = game.getWidth();
      screenHeight = game.getHeight();
    } else {
      game = null;
    }
    gapY = 5;
    if (screenHeight <= 768) {
      gapY = 2;
    } else if (screenHeight <= 960) {
      gapY = 3;
    } else if (screenHeight <= 1024) {
      gapY = 4;
    }
    InfoPanel mainPanel = new InfoPanel();
    mainPanel.setTitle("Space Race Editor");
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    JTabbedPane tabs = new JTabbedPane();
    tabs.setFont(GuiFonts.getFontCubellanSmaller());
    tabs.setForeground(GuiStatics.getCoolSpaceColorDarker());
    tabs.setBackground(GuiStatics.getDeepSpaceDarkColor());
    tabs.add("Name and Traits", createSpaceRaceMainTab(listener));
    tabs.add("Behaviour", createBehaviourTab(listener));
    tabs.add("Appearance", createAppearanceTab(listener));
    tabs.add("Description", createDescriptionTab());
    mainPanel.add(tabs);
    // Bottom panel
    InfoPanel bottomPanel = new InfoPanel();
    bottomPanel.setLayout(new BorderLayout());
    bottomPanel.setTitle(null);
    SpaceButton btn = new SpaceButton("Back to main menu",
        GameCommands.COMMAND_MAIN_MENU);
    btn.addActionListener(listener);
    bottomPanel.add(btn, BorderLayout.CENTER);
    btn = new SpaceButton("Load space race",
        GameCommands.COMMAND_LOAD_SPACERACE);
    btn.addActionListener(listener);
    bottomPanel.add(btn, BorderLayout.WEST);
    btn = new SpaceButton("Save space race",
        GameCommands.COMMAND_SAVE_SPACERACE);
    btn.addActionListener(listener);
    bottomPanel.add(btn, BorderLayout.EAST);
    this.add(mainPanel, BorderLayout.CENTER);
    this.add(bottomPanel, BorderLayout.SOUTH);
  }

  /**
   * Create Space Race main tab.
   * @param listener Action Listener
   * @return InfoPanel
   */
  private EmptyInfoPanel createSpaceRaceMainTab(final ActionListener listener) {
    EmptyInfoPanel mainPanel = new EmptyInfoPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    InfoPanel infoPanel = new InfoPanel();
    infoPanel.setTitle("New space race");
    infoPanel.setLayout(new BoxLayout(infoPanel,
        BoxLayout.Y_AXIS));
    SpaceLabel label = new SpaceLabel("Space Race name:");
    label.setAlignmentX(Component.LEFT_ALIGNMENT);
    infoPanel.add(label);
    infoPanel.add(Box.createRigidArea(new Dimension(10, gapY)));
    spaceRaceNameField = new JTextField("Custom Terrans");
    spaceRaceNameField.setBackground(GuiStatics.getDeepSpaceDarkColor());
    spaceRaceNameField.setForeground(GuiStatics.getCoolSpaceColor());
    spaceRaceNameField.setFont(GuiFonts.getFontCubellanSmaller());
    spaceRaceNameField.setMaximumSize(new Dimension(Integer.MAX_VALUE,
        GuiStatics.TEXT_FIELD_HEIGHT));
    spaceRaceNameField.addKeyListener(new KeyListener() {

      @Override
      public void keyTyped(final KeyEvent e) {
        // Nothing to do here
      }

      @Override
      public void keyReleased(final KeyEvent e) {
        String tmp = getEditedSpaceRaceId();
        conflictWithId = false;
        for (SpaceRace race : SpaceRaceFactory.getValues()) {
          if (race.getId().equals(tmp)) {
            conflictWithId = true;
          }
        }
        if (conflictWithId) {
          spaceRaceNameField.setForeground(GuiStatics.COLOR_RED_TEXT);
        } else {
          spaceRaceNameField.setForeground(GuiStatics.getCoolSpaceColor());
        }
        spaceRaceNameField.repaint();
      }

      @Override
      public void keyPressed(final KeyEvent e) {
        // Nothing to do here
      }
    });
    infoPanel.add(spaceRaceNameField);
    infoPanel.add(Box.createRigidArea(new Dimension(10, gapY)));
    label = new SpaceLabel("Space Race name in single:");
    label.setAlignmentX(Component.LEFT_ALIGNMENT);
    infoPanel.add(label);
    infoPanel.add(Box.createRigidArea(new Dimension(10, gapY)));
    spaceRaceNameSingleField = new JTextField("Custom Terran");
    spaceRaceNameSingleField.setBackground(GuiStatics.getDeepSpaceDarkColor());
    spaceRaceNameSingleField.setForeground(GuiStatics.getCoolSpaceColor());
    spaceRaceNameSingleField.setFont(GuiFonts.getFontCubellanSmaller());
    spaceRaceNameSingleField.setMaximumSize(new Dimension(Integer.MAX_VALUE,
        GuiStatics.TEXT_FIELD_HEIGHT));
    infoPanel.add(spaceRaceNameSingleField);
    infoPanel.add(Box.createRigidArea(new Dimension(10, gapY)));

    mainPanel.add(infoPanel);
    traitPanel = new TraitPanel(screenWidth,
        TraitFactory.getAll(), listener);
    mainPanel.add(traitPanel);
    return mainPanel;
  }

  /**
   * Create Space Race behaviour tab.
   * @param listener Action Listener
   * @return InfoPanel
   */
  private EmptyInfoPanel createBehaviourTab(final ActionListener listener) {
    EmptyInfoPanel mainPanel = new EmptyInfoPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    InfoPanel infoPanel = new InfoPanel();
    infoPanel.setTitle("New space race");
    infoPanel.setLayout(new BoxLayout(infoPanel,
        BoxLayout.Y_AXIS));
    SpaceLabel label = new SpaceLabel("Default AI Attitude:");
    label.setAlignmentX(Component.LEFT_ALIGNMENT);
    infoPanel.add(label);
    infoPanel.add(Box.createRigidArea(new Dimension(10, gapY)));
    attitudeCombo = new SpaceComboBox<>(Attitude.values());
    attitudeCombo.setBackground(
        GuiStatics.getDeepSpaceDarkColor());
    attitudeCombo.setForeground(
        GuiStatics.getCoolSpaceColor());
    attitudeCombo.setBorder(new SimpleBorder());
    attitudeCombo.setFont(GuiFonts.getFontCubellan());
    attitudeCombo.getModel()
        .setSelectedItem(Attitude.LOGICAL);
    DefaultListCellRenderer dlcr = new DefaultListCellRenderer();
    dlcr.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
    attitudeCombo.setRenderer(dlcr);
    attitudeCombo.addActionListener(listener);
    attitudeCombo.setActionCommand(
        GameCommands.COMMAND_SPACERACE_EDITOR_ATTITUDE_SELECT);
    infoPanel.add(attitudeCombo);
    infoPanel.add(Box.createRigidArea(new Dimension(10, gapY)));
    label = new SpaceLabel("Social system:");
    label.setAlignmentX(Component.LEFT_ALIGNMENT);
    infoPanel.add(label);
    infoPanel.add(Box.createRigidArea(new Dimension(10, gapY)));
    socialCombo = new SpaceComboBox<>(SocialSystem.values());
    socialCombo.setBackground(
        GuiStatics.getDeepSpaceDarkColor());
    socialCombo.setForeground(
        GuiStatics.getCoolSpaceColor());
    socialCombo.setBorder(new SimpleBorder());
    socialCombo.setFont(GuiFonts.getFontCubellan());
    socialCombo.getModel()
        .setSelectedItem(SocialSystem.EQUAL);
    dlcr = new DefaultListCellRenderer();
    dlcr.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
    socialCombo.setRenderer(dlcr);
    socialCombo.addActionListener(listener);
    socialCombo.setActionCommand(
        GameCommands.COMMAND_SPACERACE_EDITOR_SOCIAL_SELECT);
    infoPanel.add(socialCombo);
    infoPanel.add(Box.createRigidArea(new Dimension(10, gapY)));
    label = new SpaceLabel("Leader genders:");
    label.setAlignmentX(Component.LEFT_ALIGNMENT);
    infoPanel.add(label);
    infoPanel.add(Box.createRigidArea(new Dimension(10, gapY)));
    genderCombo = new SpaceComboBox<>(GenderOption.values());
    genderCombo.setBackground(
        GuiStatics.getDeepSpaceDarkColor());
    genderCombo.setForeground(
        GuiStatics.getCoolSpaceColor());
    genderCombo.setBorder(new SimpleBorder());
    genderCombo.setFont(GuiFonts.getFontCubellan());
    genderCombo.getModel()
        .setSelectedItem(GenderOption.MALE_AND_FEMALE);
    genderCombo.setRenderer(new GenderOptionListRenderer());
    genderCombo.addActionListener(listener);
    genderCombo.setActionCommand(
        GameCommands.COMMAND_SPACERACE_EDITOR_GENDER_SELECT);
    infoPanel.add(genderCombo);
    infoPanel.add(Box.createRigidArea(new Dimension(10, gapY)));
    label = new SpaceLabel("Speech set:");
    label.setAlignmentX(Component.LEFT_ALIGNMENT);
    infoPanel.add(label);
    infoPanel.add(Box.createRigidArea(new Dimension(10, gapY)));
    speechCombo = new SpaceComboBox<>(SpeechFactory.getAllIds());
    speechCombo.setBackground(
        GuiStatics.getDeepSpaceDarkColor());
    speechCombo.setForeground(
        GuiStatics.getCoolSpaceColor());
    speechCombo.setBorder(new SimpleBorder());
    speechCombo.setFont(GuiFonts.getFontCubellan());
    speechCombo.getModel()
        .setSelectedItem(SpeechFactory.getAllIds()[0]);
    dlcr = new DefaultListCellRenderer();
    dlcr.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
    speechCombo.setRenderer(dlcr);
    speechCombo.addActionListener(listener);
    speechCombo.setActionCommand(
        GameCommands.COMMAND_SPACERACE_EDITOR_SPEECH_SELECT);
    infoPanel.add(speechCombo);
    infoPanel.add(Box.createRigidArea(new Dimension(10, gapY)));
    label = new SpaceLabel("Leader name generator:");
    label.setAlignmentX(Component.LEFT_ALIGNMENT);
    infoPanel.add(label);
    infoPanel.add(Box.createRigidArea(new Dimension(10, gapY)));
    nameGenCombo = new SpaceComboBox<>(NameGeneratorType.values());
    nameGenCombo.setBackground(
        GuiStatics.getDeepSpaceDarkColor());
    nameGenCombo.setForeground(
        GuiStatics.getCoolSpaceColor());
    nameGenCombo.setBorder(new SimpleBorder());
    nameGenCombo.setFont(GuiFonts.getFontCubellan());
    nameGenCombo.getModel()
        .setSelectedItem(NameGeneratorType.ANCIENT_ROMAN);
    dlcr = new DefaultListCellRenderer();
    dlcr.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
    nameGenCombo.setRenderer(dlcr);
    nameGenCombo.addActionListener(listener);
    nameGenCombo.setActionCommand(
        GameCommands.COMMAND_SPACERACE_EDITOR_NAMEGEN_SELECT);
    infoPanel.add(nameGenCombo);
    infoPanel.add(Box.createRigidArea(new Dimension(10, gapY)));

    mainPanel.add(infoPanel);
    return mainPanel;
  }

  /**
   * Clear timer
   */
  public void clearTimer() {
    shipTimer.stop();
  }

  /**
   * Init space race images list.
   * Gets values from GuiStatics plus add custom one.
   */
  private void initSpaceRaceImages() {
    raceImages = new String[GuiStatics.BUILT_IN_SPACE_RACE_IMAGES.length + 1];
    customImageIndex = GuiStatics.BUILT_IN_SPACE_RACE_IMAGES.length;
    for (int i = 0; i < GuiStatics.BUILT_IN_SPACE_RACE_IMAGES.length; i++) {
      raceImages[i] = GuiStatics.BUILT_IN_SPACE_RACE_IMAGES[i];
    }
    raceImages[customImageIndex] = "Custom";
  }
  /**
   * Create Space Race appearance tab.
   * @param listener Action Listener
   * @return InfoPanel
   */
  private EmptyInfoPanel createAppearanceTab(final ActionListener listener) {
    shipTimer = new Timer(3000, listener);
    shipTimer.setActionCommand(
        GameCommands.COMMAND_SPACERACE_EDITOR_SHIP_TIMER);
    shipTimer.start();
    EmptyInfoPanel mainPanel = new EmptyInfoPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    SpaceGreyPanel panel = new SpaceGreyPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
    panel.setMaximumSize(new Dimension(700, 400));
    interiorPanel = new ShipInteriorPanel(newRace, null);
    setAmbientEffect(newRace.getRaceBridgeEffect());
    interiorPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    panel.add(interiorPanel);
    mainPanel.add(panel);
    mainPanel.add(Box.createRigidArea(new Dimension(10, gapY)));
    EmptyInfoPanel borderPanel = new EmptyInfoPanel();
    borderPanel.setLayout(new BorderLayout());
    EmptyInfoPanel gridPanel = new EmptyInfoPanel();
    gridPanel.setLayout(new GridLayout(1, 0));

    // First column
    EmptyInfoPanel infoPanel = new EmptyInfoPanel();
    infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
    SpaceLabel label = new SpaceLabel("Space Race Image:");
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    infoPanel.add(label);
    infoPanel.add(Box.createRigidArea(new Dimension(10, gapY)));
    initSpaceRaceImages();
    spaceRaceImageCombo = new SpaceComboBox<>(raceImages);
    spaceRaceImageCombo.setBackground(
        GuiStatics.getDeepSpaceDarkColor());
    spaceRaceImageCombo.setForeground(
        GuiStatics.getCoolSpaceColor());
    spaceRaceImageCombo.setBorder(new SimpleBorder());
    spaceRaceImageCombo.setFont(GuiFonts.getFontCubellan());
    spaceRaceImageCombo.getModel()
        .setSelectedItem(raceImages[0]);
    DefaultListCellRenderer dlcr = new DefaultListCellRenderer();
    dlcr.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
    spaceRaceImageCombo.setRenderer(dlcr);
    spaceRaceImageCombo.addActionListener(listener);
    spaceRaceImageCombo.setActionCommand(
        GameCommands.COMMAND_SPACERACE_EDITOR_IMAGE_SELECT);
    infoPanel.add(spaceRaceImageCombo);
    infoPanel.add(Box.createRigidArea(new Dimension(10, gapY)));
    browseButton = new SpaceButton("Select Image",
        GameCommands.COMMAND_SPACERACE_EDITOR_BROWSE_IMAGE);
    browseButton.addActionListener(listener);
    browseButton.setEnabled(false);
    browseButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
    infoPanel.add(browseButton);
    gridPanel.add(infoPanel);

    // second column
    infoPanel = new EmptyInfoPanel();
    infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
    label = new SpaceLabel("Space ship bridge:");
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    infoPanel.add(label);
    infoPanel.add(Box.createRigidArea(new Dimension(10, gapY)));
    bridgeIdCombo = new SpaceComboBox<>(BridgeGraphFactory.getAllIds());
    bridgeIdCombo.setBackground(
        GuiStatics.getDeepSpaceDarkColor());
    bridgeIdCombo.setForeground(
        GuiStatics.getCoolSpaceColor());
    bridgeIdCombo.setBorder(new SimpleBorder());
    bridgeIdCombo.setFont(GuiFonts.getFontCubellan());
    bridgeIdCombo.getModel()
        .setSelectedItem(BridgeGraphFactory.getAllIds()[0]);
    dlcr = new DefaultListCellRenderer();
    dlcr.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
    bridgeIdCombo.setRenderer(dlcr);
    bridgeIdCombo.addActionListener(listener);
    bridgeIdCombo.setActionCommand(
        GameCommands.COMMAND_SPACERACE_EDITOR_BRIDGE_SELECT);
    infoPanel.add(bridgeIdCombo);
    infoPanel.add(Box.createRigidArea(new Dimension(10, gapY)));
    String text = "Space ship bridge effect (Requires ambient lights setup):";
    if (screenWidth == 1024) {
      text = "Bridge effect (Req. ambient lights):";
    } else if (screenWidth <= 1280) {
      text = "Bridge effect (Requires ambient lights):";
    } else if (screenWidth <= 1440) {
      text = "Space ship bridge effect (Req. ambient lights):";
    }
    label = new SpaceLabel(text);
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    infoPanel.add(label);
    infoPanel.add(Box.createRigidArea(new Dimension(10, gapY)));
    bridgeEffectCombo = new SpaceComboBox<>(Bridge.getEffectIds());
    bridgeEffectCombo.setBackground(
        GuiStatics.getDeepSpaceDarkColor());
    bridgeEffectCombo.setForeground(
        GuiStatics.getCoolSpaceColor());
    bridgeEffectCombo.setBorder(new SimpleBorder());
    bridgeEffectCombo.setFont(GuiFonts.getFontCubellan());
    bridgeEffectCombo.getModel()
        .setSelectedItem(Bridge.getEffectIds()[0]);
    dlcr = new DefaultListCellRenderer();
    dlcr.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
    bridgeEffectCombo.setRenderer(dlcr);
    bridgeEffectCombo.addActionListener(listener);
    bridgeEffectCombo.setActionCommand(
        GameCommands.COMMAND_SPACERACE_EDITOR_BRIDGE_SELECT);
    infoPanel.add(bridgeEffectCombo);
    infoPanel.add(Box.createRigidArea(new Dimension(10, gapY)));
    label = new SpaceLabel("Theme music:");
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    infoPanel.add(label);
    infoPanel.add(Box.createRigidArea(new Dimension(10, gapY)));
    diplomacyMusicCombo = new SpaceComboBox<>(MusicPlayer.DIPLOMACY_MUSIC_LIST);
    diplomacyMusicCombo.setBackground(
        GuiStatics.getDeepSpaceDarkColor());
    diplomacyMusicCombo.setForeground(
        GuiStatics.getCoolSpaceColor());
    diplomacyMusicCombo.setBorder(new SimpleBorder());
    diplomacyMusicCombo.setFont(GuiFonts.getFontCubellan());
    diplomacyMusicCombo.getModel()
        .setSelectedItem(MusicPlayer.DIPLOMACY_MUSIC_LIST[0]);
    dlcr = new DefaultListCellRenderer();
    dlcr.setHorizontalAlignment(DefaultListCellRenderer.LEFT);
    diplomacyMusicCombo.setRenderer(dlcr);
    diplomacyMusicCombo.addActionListener(listener);
    diplomacyMusicCombo.setActionCommand(
        GameCommands.COMMAND_SPACERACE_EDITOR_MUSIC_SELECT);
    infoPanel.add(diplomacyMusicCombo);
    infoPanel.add(Box.createRigidArea(new Dimension(10, gapY)));
    gridPanel.add(infoPanel);

    // third column
    infoPanel = new EmptyInfoPanel();
    infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
    label = new SpaceLabel("Space ship type:");
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    infoPanel.add(label);
    infoPanel.add(Box.createRigidArea(new Dimension(10, gapY)));
    spaceShipIdCombo = new SpaceComboBox<>(ShipImageFactor.getAllIds());
    spaceShipIdCombo.setBackground(
        GuiStatics.getDeepSpaceDarkColor());
    spaceShipIdCombo.setForeground(
        GuiStatics.getCoolSpaceColor());
    spaceShipIdCombo.setBorder(new SimpleBorder());
    spaceShipIdCombo.setFont(GuiFonts.getFontCubellan());
    spaceShipIdCombo.getModel()
        .setSelectedItem(ShipImageFactor.getAllIds()[0]);
    dlcr = new DefaultListCellRenderer();
    dlcr.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
    spaceShipIdCombo.setRenderer(dlcr);
    spaceShipIdCombo.addActionListener(listener);
    spaceShipIdCombo.setActionCommand(
        GameCommands.COMMAND_SPACERACE_EDITOR_SHIP_SELECT);
    infoPanel.add(spaceShipIdCombo);
    infoPanel.add(Box.createRigidArea(new Dimension(10, gapY)));
    hullImageCount = ShipImage.MASSIVE_FREIGHTER;
    hullImage = new ImageLabel(
        ShipImageFactor.create(newRace.getSpaceShipId())
        .getShipImage(hullImageCount), true);
    hullImage.setFillColor(Color.BLACK);
    hullImage.setAlignmentX(JComponent.CENTER_ALIGNMENT);
    infoPanel.add(hullImage);
    infoPanel.add(Box.createRigidArea(new Dimension(10, 2)));
    hullNameLabel = new SpaceLabel("Preview of "
        + ShipImage.getShipType(hullImageCount));
    hullNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    infoPanel.add(hullNameLabel);
    infoPanel.add(Box.createRigidArea(new Dimension(10, gapY)));
    gridPanel.add(infoPanel);
    borderPanel.add(gridPanel, BorderLayout.CENTER);

    // South Panel
    infoPanel = new EmptyInfoPanel();
    infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
    SpaceButton button = new SpaceButton("Apply",
        GameCommands.COMMAND_SPACERACE_EDITOR_APPLY_APPEARANCE);
    button.addActionListener(listener);
    button.setAlignmentX(JComponent.CENTER_ALIGNMENT);
    infoPanel.add(button);
    borderPanel.add(infoPanel, BorderLayout.SOUTH);

    // Finalize main panel
    mainPanel.add(borderPanel);
    return mainPanel;
  }

  /**
   * Create Space Race description tab.
   * @return InfoPanel
   */
  private EmptyInfoPanel createDescriptionTab() {
    EmptyInfoPanel mainPanel = new EmptyInfoPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    SpaceGreyPanel panel = new SpaceGreyPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
    panel.setMaximumSize(new Dimension(700, 400));
    interiorPanel2 = new ShipInteriorPanel(newRace, null);
    setAmbientEffect(newRace.getRaceBridgeEffect());
    interiorPanel2.setAlignmentX(Component.LEFT_ALIGNMENT);
    panel.add(interiorPanel2);
    mainPanel.add(panel);
    mainPanel.add(Box.createRigidArea(new Dimension(10, gapY)));

    SpaceLabel label = new SpaceLabel("Space Race description:");
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    mainPanel.add(label);
    mainPanel.add(Box.createRigidArea(new Dimension(10, gapY)));

    descriptionText = new JTextArea(20, 120);
    descriptionText.setText("");
    descriptionText.setEditable(true);
    descriptionText.setLineWrap(true);
    descriptionText.setFont(GuiFonts.getFontSquarion());
    descriptionText.setForeground(GuiStatics.getInfoTextColor());
    descriptionText.setBackground(Color.BLACK);
    descriptionText.setBorder(new SimpleBorder());
    descriptionText.setToolTipText("<html>Describe new space race looks and"
        + " exterior features.<br> Game will automatically generate"
        + " description for selected traits.</html>");

    mainPanel.add(descriptionText);
    return mainPanel;
  }

  /**
   * Get Government ID
   * @return GovernmentId.
   */
  private String getEditedSpaceRaceId() {
    String tmp = spaceRaceNameField.getText().toUpperCase().trim();
    return tmp.replaceAll(" ", "_");
  }

  /**
   * Set Ambient light effect during edit.
   * @param command BridgeCommandType.
   */
  private void setAmbientEffect(final BridgeCommandType command) {
    if (game != null) {
      game.setBridgeCommand(command);
    }
  }

  /**
   * Is conflict with government ID.
   * @return true or false
   */
  public boolean isConflictWithId() {
    return conflictWithId;
  }
  /**
   * Build Json file based on government editor
   * @return String as a JSON.
   */
  public String buildJson() {
    StringBuilder sb = new StringBuilder();
    sb.append("[\n");
    sb.append("  {\n");
    sb.append("    \"ID\": \"");
    sb.append(getEditedSpaceRaceId());
    sb.append("\",\n");
    sb.append("    \"Name\": \"");
    sb.append(spaceRaceNameField.getText());
    sb.append("\",\n");
    sb.append("    \"NameSingle\": \"");
    sb.append(spaceRaceNameSingleField.getText());
    sb.append("\",\n");
    sb.append("    \"BridgeId\": \"");
    sb.append(newRace.getBridgeId());
    sb.append("\",\n");
    sb.append("    \"SpaceShipId\": \"");
    sb.append(newRace.getSpaceShipId());
    sb.append("\",\n");
    sb.append("    \"Traits\": [\n");
    boolean notFirst = false;
    for (TraitCheckBox box : traitPanel.getAllBoxes()) {
      if (box.isSelected()) {
        if (notFirst) {
          sb.append(",\n");
        }
        sb.append("      \"");
        sb.append(box.getTraitId());
        sb.append("\"");
        notFirst = true;
      }
    }
    sb.append("\n");
    sb.append("     ],\n");
    sb.append("    \"Attitude\": \"");
    Attitude attitude = (Attitude) attitudeCombo.getSelectedItem();
    sb.append(attitude.getName().toUpperCase());
    sb.append("\",\n");
    sb.append("    \"Image\": \"");
    sb.append(newRace.getImage());
    sb.append("\",\n");
    sb.append("    \"SocialSystem\": \"");
    SocialSystem social = (SocialSystem) socialCombo.getSelectedItem();
    sb.append(social.toString());
    sb.append("\",\n");
    sb.append("    \"Genders\": [\n");
    GenderOption genderOption = (GenderOption) genderCombo.getSelectedItem();
    if (genderOption == GenderOption.FEMALE_ONLY) {
      sb.append("                 \"FEMALE\"\n");
    }
    if (genderOption == GenderOption.MALE_ONLY) {
      sb.append("                 \"MALE\"\n");
    }
    if (genderOption == GenderOption.MALE_AND_FEMALE) {
      sb.append("                 \"MALE\",\n");
      sb.append("                 \"FEMALE\"\n");
    }
    if (genderOption == GenderOption.NONE) {
      sb.append("                 \"NONE\"\n");
    }
    sb.append("                 ],\n");
    sb.append("    \"SpeechSetId\": \"");
    sb.append((String) speechCombo.getSelectedItem());
    sb.append("\",\n");
    sb.append("    \"BridgeEffect\": \"");
    String tmp = (String) bridgeEffectCombo.getSelectedItem();
    tmp = (String) bridgeEffectCombo.getSelectedItem();
    tmp = tmp.replaceAll(" ", "_");
    sb.append(tmp.toUpperCase());
    sb.append("\",\n");
    sb.append("    \"DiplomacyMusic\": \"");
    MusicFileInfo music = (MusicFileInfo) diplomacyMusicCombo.getSelectedItem();
    sb.append(music.getName());
    sb.append("\",\n");
    sb.append("    \"NameGenerator\": \"");
    NameGeneratorType nameGen = (NameGeneratorType) nameGenCombo
        .getSelectedItem();
    sb.append(nameGen.getName().toUpperCase());
    sb.append("\",\n");
    sb.append("    \"Description\": \"");
    sb.append(TextUtilities.escapeJson(descriptionText.getText()));
    sb.append("\",\n");
    sb.append("  }\n");
    sb.append("]\n");
    return sb.toString();
  }
  /**
   * Handle image selection for space race image.
   */
  public void handleImageSelection() {
    boolean imageSelected = false;
    JFileChooser loadFileChooser = new JFileChooser(
        new File(Folders.getCustomGovPath()));
    loadFileChooser.setFileFilter(new FileNameExtensionFilter(
        "PNG Image", "png"));
    String fileName = "*.png";
    loadFileChooser.setSelectedFile(
        new File(Folders.getCustomSpaceRaceImage() + "/" + fileName));
    loadFileChooser.setApproveButtonText("Load");
    loadFileChooser.setDialogTitle("Load image file");
    int returnValue = loadFileChooser.showOpenDialog(this);
    if (returnValue == JFileChooser.APPROVE_OPTION) {
      File file = loadFileChooser.getSelectedFile();
      if (file.exists()) {
        if (file.getAbsolutePath().contains(
            Folders.getCustomSpaceRaceImage())) {
          String str = file.getAbsolutePath();
          int index = str.lastIndexOf('/');
          str = str.substring(index + 1);
          raceImages[customImageIndex] = str;
        } else {
          raceImages[customImageIndex] = file.getPath();
        }
        imageSelected = true;
        SoundPlayer.playMenuSound();
        spaceRaceImageCombo.setSelectedIndex(customImageIndex);
        spaceRaceImageCombo.getModel().setSelectedItem(
            raceImages[customImageIndex]);
      }
    } else {
      SoundPlayer.playMenuDisabled();
    }
    if (!imageSelected) {
      spaceRaceImageCombo.setSelectedIndex(0);
    }
  }
  /**
   * Handle actions for Government editor
   * @param arg0 ActionEvent command what player did
   */
  public void handleAction(final ActionEvent arg0) {
    if (arg0.getActionCommand().startsWith(
        GameCommands.COMMAND_RACE_TRAIT_SELECTED)) {
      String[] param = arg0.getActionCommand().split("\\+");
      if (param.length == 2) {
        traitPanel.handleTraitSelection(param[1]);
      }
      SoundPlayer.playMenuSound();
    }
    if (arg0.getActionCommand().equals(
        GameCommands.COMMAND_SPACERACE_EDITOR_SHIP_TIMER)) {
      hullImageCount++;
      if (hullImageCount >= ShipImage.NUMBER_OF_IMAGES) {
        hullImageCount = 0;
      }
      hullImage.setImage(ShipImageFactor.create(
          newRace.getSpaceShipId()).getShipImage(hullImageCount));
      hullNameLabel.setText("Preview of "
          + ShipImage.getShipType(hullImageCount));
      hullNameLabel.repaint();
      hullImage.repaint();
    }
    if (arg0.getActionCommand().equals(
        GameCommands.COMMAND_ANIMATION_TIMER)) {
      interiorPanel.repaint();
      interiorPanel2.repaint();
    }
    if (arg0.getActionCommand().equals(
        GameCommands.COMMAND_SPACERACE_EDITOR_IMAGE_SELECT)) {
      if (spaceRaceImageCombo.getSelectedIndex() == customImageIndex) {
        browseButton.setEnabled(true);
      } else {
        browseButton.setEnabled(false);
      }
    }
    if (arg0.getActionCommand().equals(
        GameCommands.COMMAND_SPACERACE_EDITOR_BROWSE_IMAGE)) {
      handleImageSelection();
    }
    if (arg0.getActionCommand().equals(
        GameCommands.COMMAND_SPACERACE_EDITOR_APPLY_APPEARANCE)) {
      String tmp = (String) spaceRaceImageCombo.getSelectedItem();
      if (!tmp.equals("Custom")) {
        newRace.setImage(tmp);
      }
      newRace.setBridgeId((String) bridgeIdCombo.getSelectedItem());
      newRace.setSpaceShipId((String) spaceShipIdCombo.getSelectedItem());
      hullImage.setImage(ShipImageFactor.create(
          newRace.getSpaceShipId()).getShipImage(hullImageCount));
      hullNameLabel.setText("Preview of "
          + ShipImage.getShipType(hullImageCount));
      hullNameLabel.repaint();
      hullImage.repaint();
      interiorPanel.setRace(newRace);
      tmp = (String) bridgeEffectCombo.getSelectedItem();
      tmp = tmp.replaceAll(" ", "_");
      newRace.setRaceBridgeEffect(BridgeCommandType.getByString(tmp));
      setAmbientEffect(newRace.getRaceBridgeEffect());
      interiorPanel.repaint();
      MusicPlayer.play((MusicFileInfo) diplomacyMusicCombo.getSelectedItem());
      SoundPlayer.playMenuSound();
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_SAVE_SPACERACE)) {
      JFileChooser saveFileChooser = new JFileChooser(
          new File(Folders.getCustomSpaceRacePath()));
      saveFileChooser.setFileFilter(new FileNameExtensionFilter(
          "JSON Data file", "json"));
      String fileName = getEditedSpaceRaceId().toLowerCase() + ".json";
      saveFileChooser.setSelectedFile(
          new File(Folders.getCustomSpaceRacePath() + "/" + fileName));
      saveFileChooser.setApproveButtonText("Save");
      saveFileChooser.setDialogTitle("Save space race file");
      int returnValue = saveFileChooser.showOpenDialog(this);
      if (returnValue == JFileChooser.APPROVE_OPTION) {
        File file = saveFileChooser.getSelectedFile();
        try (FileOutputStream fos = new FileOutputStream(file)) {
          fos.write(buildJson().getBytes(StandardCharsets.UTF_8));
          fos.flush();
        } catch (FileNotFoundException e) {
          ErrorLogger.log(e);
        } catch (IOException e) {
          ErrorLogger.log(e);
        }
        SoundPlayer.playMenuSound();
      } else {
        SoundPlayer.playMenuDisabled();
      }
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_LOAD_SPACERACE)) {
      JFileChooser loadFileChooser = new JFileChooser(
          new File(Folders.getCustomSpaceRacePath()));
      loadFileChooser.setFileFilter(new FileNameExtensionFilter(
          "JSON Data file", "json"));
      String fileName = getEditedSpaceRaceId().toLowerCase() + ".json";
      loadFileChooser.setSelectedFile(
          new File(Folders.getCustomSpaceRacePath() + "/" + fileName));
      loadFileChooser.setApproveButtonText("Load");
      loadFileChooser.setDialogTitle("Load space race file");
      int returnValue = loadFileChooser.showOpenDialog(this);
      if (returnValue == JFileChooser.APPROVE_OPTION) {
        SpaceRaceLoader loader = new SpaceRaceLoader();
        File file = loadFileChooser.getSelectedFile();
        try (FileInputStream fis = new FileInputStream(file)) {
          JSONArray jsonArray = new JSONArray(new JSONTokener(fis));

          for (var obj : jsonArray) {
            if (!(obj instanceof JSONObject)) {
              ErrorLogger.log("Malformed JSON file: " + file.getAbsolutePath());
            }
            var spaceRaceOpt = loader.parseFromJson((JSONObject) obj);
            if (spaceRaceOpt.isEmpty()) {
              ErrorLogger.log("Malformed JSON file: " + file.getAbsolutePath());
            } else {
              newRace = spaceRaceOpt.get();
              spaceRaceNameField.setText(newRace.getName());
              spaceRaceNameSingleField.setText(newRace.getNameSingle());
              bridgeIdCombo.setSelectedItem(newRace.getBridgeId());
              attitudeCombo.setSelectedItem(newRace.getAttitude());
              socialCombo.setSelectedItem(newRace.getSocialSystem());
              GenderOption genderOpt = GenderOption.NONE;
              for (Gender gender : newRace.getGenders()) {
                if (gender == Gender.MALE && genderOpt == GenderOption.NONE) {
                  genderOpt = GenderOption.MALE_ONLY;
                } else if (gender == Gender.FEMALE
                    && genderOpt == GenderOption.NONE) {
                  genderOpt = GenderOption.FEMALE_ONLY;
                } else if (gender == Gender.MALE
                    && genderOpt == GenderOption.FEMALE_ONLY) {
                  genderOpt = GenderOption.MALE_AND_FEMALE;
                } else if (gender == Gender.FEMALE
                    && genderOpt == GenderOption.MALE_ONLY) {
                  genderOpt = GenderOption.MALE_AND_FEMALE;
                }
              }
              genderCombo.setSelectedItem(genderOpt);
              speechCombo.setSelectedItem(newRace.getSpeechSetId());
              nameGenCombo.setSelectedItem(newRace.getNameGeneratorType());
              traitPanel.uncheckAllTraits();
              for (RaceTrait trait : newRace.getAllTraits()) {
                traitPanel.checkTrait(trait.getId());
                traitPanel.handleTraitSelection(trait.getId());
              }
              spaceShipIdCombo.setSelectedItem(newRace.getSpaceShipId());
              spaceRaceImageCombo.setSelectedItem(newRace.getImage());
              diplomacyMusicCombo.setSelectedItem(
                  newRace.getRaceDiplomacyMusic());
              descriptionText.setText(newRace.getRacialDescription());
              hullImage.setImage(ShipImageFactor.create(
                  newRace.getSpaceShipId()).getShipImage(hullImageCount));
              hullNameLabel.setText("Preview of "
                  + ShipImage.getShipType(hullImageCount));
              hullNameLabel.repaint();
              hullImage.repaint();
              interiorPanel.setRace(newRace);
              interiorPanel2.setRace(newRace);
              String tmp = (String) bridgeEffectCombo.getSelectedItem();
              tmp = tmp.replaceAll(" ", "_");
              newRace.setRaceBridgeEffect(BridgeCommandType.getByString(tmp));
              setAmbientEffect(newRace.getRaceBridgeEffect());
              interiorPanel.repaint();
              MusicPlayer.play(
                  (MusicFileInfo) diplomacyMusicCombo.getSelectedItem());
            }
          }
        } catch (IOException e) {
          ErrorLogger.log("Failed reading Government: " + e);
        }
        SoundPlayer.playMenuSound();
      } else {
        SoundPlayer.playMenuDisabled();
      }
    }

  }
}
