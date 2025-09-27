package org.openRealmOfStars.game.state;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016-2023 Tuomo Untinen
 * Copyright (C) 2023 BottledByte
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import org.openRealmOfStars.ambient.BridgeCommandType;
import org.openRealmOfStars.audio.music.MusicPlayer;
import org.openRealmOfStars.audio.soundeffect.SoundPlayer;
import org.openRealmOfStars.game.Game;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.infopanel.BattleInfoPanel;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.IconLabel;
import org.openRealmOfStars.gui.labels.InfoTextArea;
import org.openRealmOfStars.gui.labels.SpaceLabel;
import org.openRealmOfStars.gui.list.ShipListRenderer;
import org.openRealmOfStars.gui.mapPanel.PlanetAnimation;
import org.openRealmOfStars.gui.panels.BigImagePanel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.SpaceGreyPanel;
import org.openRealmOfStars.gui.util.GuiFonts;
import org.openRealmOfStars.gui.util.GuiStatics;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.diplomacy.DiplomaticTrade;
import org.openRealmOfStars.player.diplomacy.negotiation.NegotiationType;
import org.openRealmOfStars.player.espionage.EspionageUtility;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.leader.Job;
import org.openRealmOfStars.player.leader.Perk;
import org.openRealmOfStars.player.message.Message;
import org.openRealmOfStars.player.message.MessageType;
import org.openRealmOfStars.player.race.trait.TraitIds;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipComponent;
import org.openRealmOfStars.player.ship.ShipComponentType;
import org.openRealmOfStars.player.ship.ShipDamage;
import org.openRealmOfStars.player.ship.ShipStat;
import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.StarMapUtilities;
import org.openRealmOfStars.starMap.history.event.EventOnPlanet;
import org.openRealmOfStars.starMap.history.event.EventType;
import org.openRealmOfStars.starMap.newsCorp.NewsData;
import org.openRealmOfStars.starMap.newsCorp.NewsFactory;
import org.openRealmOfStars.starMap.planet.BombingShip;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.PlanetNuked;
import org.openRealmOfStars.utilities.DiceGenerator;
import org.openRealmOfStars.utilities.Logger;

/**
 *
 * Planet bombing view for handling attack on planet
 *
 */
public class PlanetBombingView extends BlackPanel {
  /** Maximum log rows */
  static final int MAX_LOG_NUMBER = 11;

  /** Total number of population */
  private IconLabel totalPeople;
  /** Trooper's power */
  private IconLabel troopsPower;
  /** Defense turret value */
  private IconLabel defenseTurret;
  /** Total number of building */
  private IconLabel totalBuildings;

  /** Planet owner's name */
  private SpaceLabel ownerLabel;

  /** Planet to show */
  private Planet planet;

  /** Bombing fleet */
  private Fleet fleet;

  /** Suppression fire against defenders */
  private int suppressionFire;
  /** Ships in fleet */
  private JList<Ship> shipsInFleet;

  /** Background picture */
  private BigImagePanel imgBase;

  /** Info panel on east side */
  private BattleInfoPanel infoPanel;

  /** Text area containing info */
  private InfoTextArea textArea;

  /** Logger for handling logged message rows */
  private Logger textLogger;

  /** Is component used or not */
  private boolean[] componentUsed;

  /** Ship index from fleet */
  private int shipIndex = 0;

  /** Which component was used to attack to planet */
  private int usedComponentIndex;

  /** Player who is attacking */
  private PlayerInfo attacker;

  /** Attack player index */
  private int attackPlayerIndex;

  /** Player who is defending */
  private PlayerInfo defender;

  /** Defending player index */
  private int defendingPlayerIndex;

  /** Is screen controlled by AI */
  private boolean aiControlled;
  /** All players are in AI */
  private boolean allAi;

  /** End button where to quit the screen */
  private SpaceButton endButton;

  /** AI has at least one attack */
  private boolean aiOneAttackFound = false;
  /** AI has done all he or she wants to do */
  private boolean aiExitLoop = false;
  /** AI has troops in the fleet */
  private boolean aiTroops = false;

  /** AI index which component it is checking */
  private int aiComponentIndex = 0;

  /** StarMap */
  private StarMap starMap;

  /** Game Frame for changing ambient lights dynamically */
  private Game game;
  /** Nuking information */
  private PlanetNuked nuked;
  /** News data */
  private NewsData newsData;

  /**
   * List of bombing ships. These will contain extra information that
   * is used while conquering the planet.
   */
  private ArrayList<BombingShip> bombers;
  /**
   * Has ship already spent action?
   */
  private boolean actionSpent;

  /**
   * Constructor for PLanet bombing view. This view is used when
   * player is conquering planet with bombs and/or troops.
   * @param planet Planet to be conquered
   * @param fleet Fleet conquering the planet
   * @param attacker Player info who is attacking
   * @param attackerPlayerIndex Player index who is attacking
   * @param listener Action listener for commands
   */
  public PlanetBombingView(final Planet planet, final Fleet fleet,
      final PlayerInfo attacker, final int attackerPlayerIndex,
      final ActionListener listener) {
    if (listener instanceof Game) {
      game = (Game) listener;
    } else {
      game = null;
    }
    this.setPlanet(planet);
    bombers = new ArrayList<>();
    for (Ship ship : fleet.getShips()) {
      bombers.add(new BombingShip(ship.getTacticSpeed() * 4));
    }
    whatHappened = NO_EFFECT;
    this.fleet = fleet;
    this.attacker = attacker;
    this.defender = planet.getPlanetPlayerInfo();
    suppressionFire = 0;
    this.attackPlayerIndex = attackerPlayerIndex;
    this.newsData = null;
    nuked = new PlanetNuked();
    aiControlled = false;
    actionSpent = false;
    allAi = false;
    // Background image
    imgBase = new BigImagePanel(planet, true, null);
    this.setLayout(new BorderLayout());

    // Top Panel
    InfoPanel topPanel = new InfoPanel();
    topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

    topPanel.add(Box.createRigidArea(new Dimension(15, 25)));
    SpaceGreyPanel panel = new SpaceGreyPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    if (planet.getPlanetPlayerInfo() != null) {
      ownerLabel = new SpaceLabel(planet.getPlanetPlayerInfo()
          .getEmpireName());
    } else {
      ownerLabel = new SpaceLabel("Uncolonized planet");
    }
    panel.add(ownerLabel);
    topPanel.add(panel);
    topPanel.add(Box.createRigidArea(new Dimension(15, 25)));

    panel = new SpaceGreyPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    totalPeople = new IconLabel(null,
        Icons.getIconByName(Icons.ICON_PEOPLE),
        "Population: " + planet.getTotalPopulation());
    totalPeople.setToolTipText("Total number of people on planet.");
    totalPeople.setAlignmentX(Component.LEFT_ALIGNMENT);
    panel.add(totalPeople);
    defenseTurret = new IconLabel(null,
        Icons.getIconByName(Icons.ICON_PLANETARY_TURRET),
        "Turrets: " + planet.getTurretLvl());
    defenseTurret.setToolTipText("Total defense value of turrets.");
    defenseTurret.setAlignmentX(Component.LEFT_ALIGNMENT);
    panel.add(defenseTurret);
    topPanel.add(panel);
    topPanel.add(Box.createRigidArea(new Dimension(15, 25)));

    panel = new SpaceGreyPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    troopsPower = new IconLabel(null,
        Icons.getIconByName(Icons.ICON_TROOPS),
        "Troops power: " + planet.getTroopPower() + " -100 suppression");
    troopsPower.setToolTipText("Total power of defending troops.");
    troopsPower.setAlignmentX(Component.LEFT_ALIGNMENT);
    panel.add(troopsPower);
    totalBuildings = new IconLabel(null,
        Icons.getIconByName(Icons.ICON_IMPROVEMENT_TECH),
        "Buildings: " + planet.getNumberOfBuildings());
    totalBuildings.setToolTipText("Total number of buildings on planet.");
    totalBuildings.setAlignmentX(Component.LEFT_ALIGNMENT);
    panel.add(totalBuildings);
    topPanel.add(panel);
    topPanel.add(Box.createRigidArea(new Dimension(15, 25)));

    topPanel.setTitle(planet.getName());

    // East panel
    InfoPanel eastPanel = new InfoPanel();
    eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
    eastPanel.setTitle(fleet.getName());
    eastPanel.add(Box.createRigidArea(new Dimension(150, 5)));
    shipsInFleet = new JList<>();
    shipsInFleet.setListData(fleet.getShips());
    shipsInFleet.setCellRenderer(new ShipListRenderer());
    shipsInFleet.setBackground(Color.BLACK);
    shipsInFleet.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    JScrollPane scroll = new JScrollPane(shipsInFleet);
    scroll.setBackground(GuiStatics.getDeepSpaceDarkColor());
    eastPanel.add(scroll);

    infoPanel = new BattleInfoPanel(fleet.getFirstShip(), null, null, false,
        listener);
    infoPanel.showShip(fleet.getFirstShip());
    infoPanel.setBorder(null);
    eastPanel.add(infoPanel);
    eastPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    panel = new SpaceGreyPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
    SpaceButton btn = new SpaceButton("Next ship",
        GameCommands.COMMAND_NEXT_TARGET);
    btn.addActionListener(listener);
    if (!attacker.isHuman()) {
      btn.setEnabled(false);
    }
    panel.add(btn);
    panel.add(Box.createRigidArea(new Dimension(5, 5)));
    endButton = new SpaceButton("Abort conquest",
        GameCommands.COMMAND_VIEW_STARMAP);
    endButton.addActionListener(listener);
    if (!attacker.isHuman()) {
      endButton.setEnabled(false);
      infoPanel.setBtnEnabled(false);
      aiControlled = true;
      if (planet.getPlanetPlayerInfo() != null
          && !planet.getPlanetPlayerInfo().isHuman()) {
        allAi = true;
      }
    }
    if (!allAi) {
      MusicPlayer.play(MusicPlayer.FIGHT_THEME01);
    }
    panel.add(endButton);
    eastPanel.add(panel);

    // Bottom panel
    InfoPanel bottomPanel = new InfoPanel();
    bottomPanel.setLayout(new BorderLayout());
    bottomPanel.setTitle(null);
    int numberOfRows = 10;
    if (GuiFonts.isLargerFonts()) {
      numberOfRows = MAX_LOG_NUMBER;
    }
    textArea = new InfoTextArea(numberOfRows, 30);
    textArea.setEditable(false);
    textArea.setLineWrap(true);
    bottomPanel.add(textArea, BorderLayout.CENTER);
    textLogger = new Logger(MAX_LOG_NUMBER);

    this.updatePanel();

    // Add panels to base
    JPanel centerPanel = new JPanel();
    centerPanel.setLayout(new BorderLayout());
    centerPanel.add(imgBase, BorderLayout.CENTER);
    centerPanel.add(bottomPanel, BorderLayout.SOUTH);
    if (planet.getPlanetOwnerIndex() != -1) {
      centerPanel.add(topPanel, BorderLayout.NORTH);
    }
    this.add(centerPanel, BorderLayout.CENTER);
    this.add(eastPanel, BorderLayout.EAST);

    componentUsed = new boolean[12];
    usedComponentIndex = -1;
  }

  /**
   * Set star map for news generator
   * @param map StarMap
   */
  public void setStarMap(final StarMap map) {
    starMap = map;
  }

  /**
   * Reset ship's component usage
   */
  public void resetComponentUsage() {
    for (int i = 0; i < componentUsed.length; i++) {
      componentUsed[i] = false;
    }
    infoPanel.resetComponentUses();
  }

  /**
   * Get attacking fleet
   * @return Fleet
   */
  public Fleet getFleet() {
    return fleet;
  }

  /**
   * Change attacking fleet
   * @param fleet to change
   */
  public void setFleet(final Fleet fleet) {
    this.fleet = fleet;
  }

  /**
   * Update Planet view panels
   */
  public void updatePanel() {
    totalPeople.setText("Population: " + planet.getTotalPopulation());
    defenseTurret.setText("Turrets: " + planet.getTurretLvl());
    totalBuildings.setText("Buildings: " + planet.getNumberOfBuildings());
    if (suppressionFire == 0) {
      troopsPower.setText("Troops power: " + planet.getTroopPower());
    } else {
      troopsPower.setText("Troops power: " + planet.getTroopPower() + " -"
          + suppressionFire + " suppression");
    }

    StringBuilder sb = new StringBuilder();
    for (int i = textLogger.size() - 1; i >= 0; i--) {
      sb.append(textLogger.getMessage(i));
      if (i != 0) {
        sb.append("\n");
      }
    }
    textArea.setText(sb.toString());
    textArea.repaint();
    sb = new StringBuilder();
    sb.append("Turns left: ");
    if (shipIndex < bombers.size()
        && bombers.get(shipIndex) != null) {
      sb.append(bombers.get(shipIndex).getActions());
      sb.append("/");
      sb.append(bombers.get(shipIndex).getMaxActions());
    } else {
      sb.append("0/0");
    }
    infoPanel.updatePanel(sb.toString());

    /*
     * Set the orbiting ships
     */
    Ship[] ships = fleet.getShips();
    BufferedImage[] images = new BufferedImage[ships.length];
    for (int i = 0; i < ships.length; i++) {
      images[i] = ships[i].getHull().getImage();
    }
    imgBase.setShipImage(images);

  }

  /**
   * @return the planet
   */
  public Planet getPlanet() {
    return planet;
  }

  /**
   * @param planet the planet to set
   */
  public void setPlanet(final Planet planet) {
    this.planet = planet;
  }

  /**
   * Change attacking ship to next one and reset all
   * component usages on ship
   */
  public void nextShip() {
    shipIndex++;
    actionSpent = false;
    if (shipIndex >= getFleet().getNumberOfShip()) {
      shipIndex = 0;
    }
    infoPanel.showShip(getFleet().getShipByIndex(shipIndex));
    textLogger.addLog("Changing to next ship in fleet...");
    resetComponentUsage();
    updatePanel();
    if (!allAi) {
      SoundPlayer.playMenuSound();
    }
  }

  /**
   * Use component on ship during bombing/conquer.
   * @param index Ship Component index
   */
  public void shipComponentUsage(final int index) {
    if (index < 0 || index > componentUsed.length) {
      // Do nothing if index is out ouf bounds
      return;
    }

    Ship ship = fleet.getShipByIndex(shipIndex);
    if (ship == null || componentUsed[index] || !ship.componentIsWorking(index)
        || bombers.get(shipIndex).getActions() <= 0) {
      return;
    }

    componentUsed[index] = true;
    ShipComponent comp = ship.getComponent(index);
    if (comp == null) {
      return;
    }

    if (comp.getType() == ShipComponentType.WEAPON_BEAM
        || comp.getType() == ShipComponentType.WEAPON_HE_MISSILE
        || comp.getType() == ShipComponentType.WEAPON_PHOTON_TORPEDO
        || comp.getType() == ShipComponentType.WEAPON_RAILGUN
        || comp.getType() == ShipComponentType.MULTICANNON
        || comp.getType() == ShipComponentType.GRAVITY_RIPPER
        || comp.getType() == ShipComponentType.PLASMA_CANNON
        || comp.getType() == ShipComponentType.PLASMA_SPIT) {
      planetTurretShoot();
      updatePanel();
      usedComponentIndex = index;
      bombers.get(shipIndex).setActions(
          bombers.get(shipIndex).getActions() - 1);
      actionSpent = true;
    }

    if (comp.getType() == ShipComponentType.ORBITAL_BOMBS
        || comp.getType() == ShipComponentType.ORBITAL_NUKE
        || comp.getType() == ShipComponentType.SPORE_MODULE) {
      planetTurretShoot();
      updatePanel();
      usedComponentIndex = index;
      if (!actionSpent) {
        bombers.get(shipIndex).setActions(
            bombers.get(shipIndex).getActions() - 1);
        actionSpent = true;
      }
    }

    if (comp.getType() == ShipComponentType.PLANETARY_INVASION_MODULE) {
      if (ship.getColonist() > 0) {
        planetTurretShoot();
        updatePanel();
        usedComponentIndex = index;
        if (!actionSpent) {
          bombers.get(shipIndex).setActions(
              bombers.get(shipIndex).getActions() - 1);
          actionSpent = true;
        }
      } else {
        textLogger.addLog("No more troops on board!");
      }
    }
  }

  /**
   * Planet turret shoots bombing ship
   */
  public void planetTurretShoot() {
    imgBase.setAnimation(
        new PlanetAnimation(PlanetAnimation.ANIMATION_TYPE_AIM, 0, 0, 1, 1));
    Ship ship = fleet.getShipByIndex(shipIndex);
    int turret = planet.getTurretLvl();
    if (turret <= 0) {
      textLogger.addLog("Planet does not have planetary turrets...");
      return;
    }

    if (ship.getShield() > turret) {
      ship.setShield(ship.getShield() - 1);
      textLogger.addLog("Planetary defense is deflected to ship's shield.");
      return;
    }

    turret -= ship.getShield();
    ship.setShield(ship.getShield() - 1);
    if (turret > 0) {
      if (ship.getArmor() > turret) {
        textLogger
            .addLog("Planetary defense is deflected to ship's armor.");
        ship.setArmor(ship.getArmor() - 1);
      } else {
        turret -= ship.getArmor();
        ship.setArmor(ship.getArmor() - 1);
        ShipDamage shipDamage = new ShipDamage(1,
            "Planetary defense hit the ship.");
        while (turret > 0 && ship.getHullPoints() > 0) {
          turret = ship.damageComponent(turret, shipDamage);
        }
        if (ship.getHullPoints() <= 0) {
          shipDamage.addText("Planetary defense damages " + ship.getName()
              + " and destroys it.");
        } else {
          shipDamage.addText("Planetary defense damages " + ship.getName()
              + ".");
        }
        String[] logs = shipDamage.getMessage().split("\n");
        for (String log : logs) {
          textLogger.addLog(log);
        }
      }
    }

    infoPanel.updateShip();
  }

  /**
   * Check if ship has been destroyed and remove it if it has.
   */
  public void removeDestroyedShip() {
    Ship ship = fleet.getShipByIndex(shipIndex);
    if (ship == null) {
      return;
    }
    if (ship.getHullPoints() > 0) {
      return;
    }

    fleet.removeShip(ship);
    bombers.remove(shipIndex);
    ShipStat stat = attacker.getShipStatByName(ship.getName());
    if (stat != null) {
      stat.setNumberOfLoses(stat.getNumberOfLoses() + 1);
      stat.setNumberOfInUse(stat.getNumberOfInUse() - 1);
    }
    attacker.getFleets().recalculateList();
  }

  /**
   * Method to kill planet governor with reason explanation
   * @param attackType conquest, bombing, nuking
   * @param reason Explain why governor died
   */
  public void killGovernor(final String attackType, final String reason) {
    final var governor = planet.getGovernor();
    if (planet.getTotalPopulation() != 0 || governor == null) {
      return;
    }

    final var planetPlayer = planet.getPlanetPlayerInfo();
    if (governor.hasPerk(Perk.WEALTHY)) {
      final var tplEscape = "%1$s has paid massive amount of credits"
          + " to save %2$s life. Private shuttle was used to save %3$s.";
      Message msg = new Message(MessageType.LEADER,
          String.format(tplEscape, governor.getCallName(),
              governor.getGender().getHisHer(), governor.getName()),
          Icons.getIconByName(Icons.ICON_DEATH));
      msg.setMatchByString("Index:" + planetPlayer.getLeaderIndex(governor));
      planetPlayer.getMsgList().addNewMessage(msg);

      NewsData news = NewsFactory.makeLeaderEscape(governor,
          planetPlayer, null, attackType);
      if (starMap != null) {
        if (starMap.hasHumanMet(planetPlayer)) {
          starMap.getNewsCorpData().addNews(news);
        }
        starMap.getHistory().addEvent(
            NewsFactory.makeLeaderEvent(governor, planetPlayer, starMap, news));
      }

      governor.useWealth();
      governor.setJob(Job.UNASSIGNED);
      planet.setGovernor(null);
    } else {
      governor.setJob(Job.DEAD);

      final var tplDied = "%1$s has died at %2$s of %3$s.";
      Message msg = new Message(MessageType.LEADER,
          String.format(tplDied, governor.getCallName(), attackType,
              planet.getName()),
          Icons.getIconByName(Icons.ICON_DEATH));
      msg.setMatchByString("Index:" + planetPlayer.getLeaderIndex(governor));
      planetPlayer.getMsgList().addNewMessage(msg);

      NewsData news = NewsFactory.makeLeaderDies(governor,
          planetPlayer, reason, starMap.getStarYear());
      if (starMap != null) {
        if (starMap.hasHumanMet(planetPlayer)) {
          starMap.getNewsCorpData().addNews(news);
        }
        starMap.getHistory().addEvent(
            NewsFactory.makeLeaderEvent(governor, planetPlayer, starMap, news));
      }

      planet.setGovernor(null);
    }

  }

  /** Planet conquered */
  private static final int CONQUERED = 1;
  /** Planet only bombed */
  private static final int ONLY_BOMBED = 2;
  /** Planet only shot */
  private static final int ONLY_SHOOT = 3;
  /** No effect */
  private static final int NO_EFFECT = 0;

  /** What happened for planet */
  private int whatHappened;

  /**
   * Attack with bombs or troops. This handles killing/destroying
   * the buildings and animation.
   * @return int 1 for conquered,
   *             2 only for bombing
   *             3 only for shooting
   *             0 that nothing happened.
   */
  private int attackBombOrTroops() {
    AttackResult attackResult = new AttackResult();
    if (usedComponentIndex == -1) {
      return NO_EFFECT;
    }

    Ship ship = fleet.getShipByIndex(shipIndex);
    if (ship == null) {
      return NO_EFFECT;
    }
    ShipComponent comp = ship.getComponent(usedComponentIndex);
    if (!ship.componentIsWorking(usedComponentIndex)) {
      return NO_EFFECT;
    }

    infoPanel.useComponent(usedComponentIndex);

    final var compType = comp.getType();
    if (compType == ShipComponentType.ORBITAL_NUKE) {
      attackResult = attackNuke(ship, comp);
    }
    if (compType == ShipComponentType.WEAPON_BEAM
        || compType == ShipComponentType.WEAPON_RAILGUN
        || compType == ShipComponentType.MULTICANNON
        || compType == ShipComponentType.PLASMA_CANNON
        || compType == ShipComponentType.PLASMA_SPIT) {
      attackResult = attackConventionalWeapon(ship, comp.getDamage() * 3,
          comp.getDamage());
    }
    if (compType == ShipComponentType.WEAPON_PHOTON_TORPEDO
        || compType == ShipComponentType.WEAPON_HE_MISSILE) {
      attackResult = attackConventionalWeapon(ship, comp.getDamage() * 2,
          comp.getDamage() * 2);
    }
    if (compType == ShipComponentType.GRAVITY_RIPPER) {
      attackResult = attackConventionalWeapon(ship, comp.getDamage() * 2,
          comp.getDamage() * 6);
    }
    if (compType == ShipComponentType.ORBITAL_BOMBS) {
      attackResult = attackConvetionalBomb(ship, comp.getDamage());
    }
    if (compType == ShipComponentType.PLANETARY_INVASION_MODULE) {
      attackResult = attackInvasionModule(ship, comp);
    }
    if (compType == ShipComponentType.SPORE_MODULE) {
      attackResult = attackSporeModule(ship);
    }

    usedComponentIndex = -1;
    if (attackResult.conquered || planet.getTotalPopulation() == 0) {
      killGovernor(attackResult.attackType, attackResult.reason);
    }

    if (attackResult.conquered) {
      return CONQUERED;
    }
    if (attackResult.bombed) {
      return ONLY_BOMBED;
    }
    if (attackResult.shot) {
      return ONLY_SHOOT;
    }
    return NO_EFFECT;
  }

  /** Result of a single attack on a planet. Mutable. */
  class AttackResult {
    /** Shot */
    private boolean shot;
    /** Bombed */
    private boolean bombed;
    /** Conquered */
    private boolean conquered;
    /** Attack type */
    private String attackType;
    /** Reason */
    private String reason;

    /**
     * Empty attack result.
     */
    AttackResult() {
      conquered = false;
      bombed = false;
      shot = false;
      attackType = "conquest";
      reason = " conquest of planet";
    }
  }

  /**
   * Attack the planet with a orbital nuke.
   * @param ship Ship which is attacking
   * @param comp ShipComponent Orbital nuke component
   * @return AttackResult
   */
  private AttackResult attackNuke(final Ship ship,
      final ShipComponent comp) {
    AttackResult result = new AttackResult();
    if (!allAi && game != null) {
      game.setBridgeCommand(BridgeCommandType.NUKE_START);
    }
    imgBase.setAnimation(new PlanetAnimation(
        PlanetAnimation.ANIMATION_TYPE_NUKE_AIM, 0, 0, 1, 1));

    if (planet.isShieldForBombing()) {
      textLogger.addLog("Orbital shield protects the planet!");
      return result;
    }

    nuked = planet.nukem(comp.getDamage(), comp.getName(), starMap,
        nuked);
    textLogger.addLog(ship.getName() + " nukes the planet!");
    result.attackType = "nuking";
    result.reason = " nuclear attack";
    result.bombed = true;
    if (planet.getTotalPopulation() == 0) {
      planet.setPlanetOwner(-1, null);
    }
    return result;
  }

  /**
   * Attack the planet with conventional bomb
   * @param ship Ship
   * @param hitDmg int
   * @return AttackResult
   */
  private AttackResult attackConvetionalBomb(final Ship ship,
      final int hitDmg) {
    AttackResult result = new AttackResult();
    if (!allAi && game != null) {
      game.setBridgeCommand(BridgeCommandType.YELLOW_ALERT);
    }
    imgBase.setAnimation(new PlanetAnimation(
        PlanetAnimation.ANIMATION_TYPE_BOMBING_AIM, 0, 0, 1, 1));

    if (planet.isShieldForBombing()) {
      textLogger.addLog("Orbital shield protects the planet!");
      return result;
    }

    int hit = DiceGenerator.getRandom(1, 100);
    if (hit <= hitDmg) {
      planet.killOneWorker("bombing", "massive bombing", starMap);
      textLogger.addLog(ship.getName() + " bombs population!");
    } else {
      if (planet.bombOneBuilding()) {
        textLogger.addLog(
            ship.getName() + " misses population but hits building!");
      } else {
        textLogger.addLog(
            ship.getName() + " misses population and buildings...");
      }
    }
    result.bombed = true;
    result.attackType = "bombing";
    result.reason = " massive orbital bombing";
    return result;
  }

  /**
   * Attack the planet with conventional weapon
   * @param ship Ship which attacks.
   * @param suppressionChance Suppression chance
   * @param destructionChance Building destruction chance
   * @return AttackResult
   */
  private AttackResult attackConventionalWeapon(final Ship ship,
      final int suppressionChance, final int destructionChance) {
    AttackResult result = new AttackResult();
    if (!allAi && game != null) {
      game.setBridgeCommand(BridgeCommandType.YELLOW_ALERT);
    }
    imgBase.setAnimation(new PlanetAnimation(
        PlanetAnimation.ANIMATION_TYPE_BOMBING_AIM, 0, 0, 1, 1));

    if (planet.isShieldForBombing()) {
      textLogger.addLog("Orbital shield protects the planet!");
      return result;
    }

    int hit = DiceGenerator.getRandom(1, 100);
    StringBuilder sb = new StringBuilder();
    if (hit <= suppressionChance) {
      suppressionFire++;
      sb.append(ship.getName());
      sb.append(" causes suppression against defenders");
      result.shot = true;
    }
    if (hit <= destructionChance && planet.bombOneBuilding()) {
      if (result.shot) {
        sb.append(" and destroyers building...");
      } else {
        sb.append(ship.getName());
        sb.append(" destroyers building...");
        result.shot = true;
      }
    }
    if (!result.shot) {
      sb.append(ship.getName());
      sb.append(" does not hit anything...");
    }
    result.attackType = "shooting";
    result.reason = " space ship weapon";
    textLogger.addLog(sb.toString());
    return result;
  }

  /**
   * Attack the planet with invasion module
   * @param ship Ship which attacks
   * @param comp ShipComponent for invasion module.
   * @return AttackResult
   */
  private AttackResult attackInvasionModule(final Ship ship,
      final ShipComponent comp) {
    AttackResult result = new AttackResult();
    if (!allAi && game != null) {
      game.setBridgeCommand(BridgeCommandType.YELLOW_ALERT);
    }
    imgBase.setAnimation(new PlanetAnimation(
        PlanetAnimation.ANIMATION_TYPE_GROUND_COMBAT_AIM, 0, 0, 1, 1));
    int shipTroop = ship.getHull().getRace().getTrooperPower()
        * (100 + comp.getDamage()) / 100;
    int shipTroops = ship.getHull().getRace().getTrooperPower()
        * ship.getColonist() * (100 + comp.getDamage()) / 100;
    int planetTroops = planet.getTroopPower() - suppressionFire;
    if (planet.getTroopPower() > 0 && planetTroops < 0) {
      planetTroops = 1;
    }

    suppressionFire = 0;
    if (shipTroops <= planetTroops) {
      planet.fightAgainstAttacker(shipTroops, starMap);
      ship.setColonist(0);
      textLogger.addLog("Your troops are killed during the attack!");
      return result;
    }

    int origPop = planet.getTotalPopulation();
    Tech[] stealableTechs = null;
    if (planet.getPlanetPlayerInfo() != null) {
      stealableTechs = EspionageUtility.getStealableTech(planet, attacker);
    }
    String extraPop = "";
    if (planet.getPlanetPlayerInfo() != null
        && attacker.getRace().hasTrait(TraitIds.ASSIMILATION)
        && !planet.getPlanetPlayerInfo().getRace().isRoboticRace()) {
      extraPop = " Killed population is synthesized into your population.";
      if (stealableTechs != null && stealableTechs.length > 0) {
        Tech tech = DiceGenerator.pickRandom(stealableTechs);
        attacker.getTechList().addTech(tech);
        final var tplStolenTech = "%1$s has stolen %2$s technology"
            + "from %3$s. This was gained via %4$s special ability.";
        Message msg = new Message(MessageType.RESEARCH,
            String.format(tplStolenTech, attacker.getEmpireName(),
                tech.getName(),
                planet.getPlanetPlayerInfo().getEmpireName(),
                attacker.getRace().getNameSingle()),
            Icons.getIconByName(Icons.ICON_RESEARCH));
        msg.setCoordinate(planet.getCoordinate());
        msg.setMatchByString(planet.getName());
        attacker.getMsgList().addNewMessage(msg);
      }
    }

    planet.fightAgainstAttacker(shipTroops, starMap);
    int left = shipTroops - planetTroops;
    left = left / shipTroop;
    if (left <= 0) {
      left = 1;
    }
    if (!extraPop.isEmpty()) {
      left += origPop;
    }
    ship.setColonist(0);

    planet.setPlanetOwner(attackPlayerIndex, attacker);
    if (attacker.getRace().hasTrait(TraitIds.ZERO_GRAVITY_BEING)) {
      planet.colonizeWithOrbital();
    }
    result.conquered = true;
    if (!attacker.getRace().isEatingFood()) {
      planet.setWorkers(Planet.PRODUCTION_WORKERS, left);
    } else {
      planet.setWorkers(Planet.PRODUCTION_FOOD, left);
    }
    textLogger.addLog("Your troops colonize the planet!" + extraPop);
    result.attackType = "conquest";
    result.reason = " conquest of planet";
    return result;
  }

  /**
   * Attack the planet with spore module
   * @param ship Ship which attacks
   * @return AttackResult
   */
  private AttackResult attackSporeModule(final Ship ship) {
    AttackResult result = new AttackResult();
    if (!allAi && game != null) {
      game.setBridgeCommand(BridgeCommandType.YELLOW_ALERT);
    }
    imgBase.setAnimation(new PlanetAnimation(
        PlanetAnimation.ANIMATION_TYPE_SPORE_ATTACK_AIM, 0, 0, 1, 1));
    int damage = ship.getHull().getSlotHull()
        * ship.getHull().getMaxSlot();
    ShipDamage shipDamage = new ShipDamage(1, "Spore module activated.");
    while (damage > 0) {
      damage = ship.damageComponent(damage, shipDamage);
    }
    suppressionFire = 0;
    if (planet.getTotalPopulation() > 1) {
      planet.killOneWorker();
      textLogger.addLog("Your spore attack kills one of the planet's"
          + " population. Your ship is destroyed in process.");
      removeDestroyedShip();
      return result;
    }

    int origPop = planet.getTotalPopulation();
    Tech[] stealableTechs = null;
    if (planet.getPlanetPlayerInfo() != null) {
      stealableTechs = EspionageUtility.getStealableTech(planet, attacker);
    }
    String extraPop = "";
    if (planet.getPlanetPlayerInfo() != null
        && attacker.getRace().hasTrait(TraitIds.ASSIMILATION)
        && !planet.getPlanetPlayerInfo().getRace().isRoboticRace()) {
      extraPop = " Killed population is synthesized into your population.";
      if (stealableTechs != null && stealableTechs.length > 0) {
        Tech tech = DiceGenerator.pickRandom(stealableTechs);
        attacker.getTechList().addTech(tech);
        final var tplStolenTech = "%1$s has stolen %2$s technology"
            + "from %3$s. This was gained via %4$s special ability.";
        Message msg = new Message(MessageType.RESEARCH,
            String.format(tplStolenTech, attacker.getEmpireName(),
                tech.getName(),
                planet.getPlanetPlayerInfo().getEmpireName(),
                attacker.getRace().getNameSingle()),
            Icons.getIconByName(Icons.ICON_RESEARCH));
        msg.setCoordinate(planet.getCoordinate());
        msg.setMatchByString(planet.getName());
        attacker.getMsgList().addNewMessage(msg);
      }
    }

    planet.killOneWorker("Conquest", "spore attack", starMap);
    int left = 1;
    if (!extraPop.isEmpty()) {
      left += origPop;
    }

    planet.setPlanetOwner(attackPlayerIndex, attacker);
    if (attacker.getRace().hasTrait(TraitIds.ZERO_GRAVITY_BEING)) {
      planet.colonizeWithOrbital();
    }
    result.conquered = true;
    if (!attacker.getRace().isEatingFood()) {
      planet.setWorkers(Planet.PRODUCTION_WORKERS, left);
    } else {
      planet.setWorkers(Planet.PRODUCTION_FOOD, left);
    }
    textLogger.addLog("Your troops colonize the planet with spores but"
        + " your ship is destroyed in process." + extraPop);
    result.attackType = "conquest";
    result.reason = " conquest of planet";
    removeDestroyedShip();
    return result;
  }

  /**
   * Handle bombing/conquer when AI is attacking
   * And all parties are Ai
   */
  public void handleAiToAiAttack() {
    boolean oneAttackFound = false;
    boolean exitLoop = false;
    boolean troops = false;
    do {
      Ship ship = fleet.getShipByIndex(shipIndex);
      if (ship != null) {
        for (int i = 0; i < ship.getNumberOfComponents(); i++) {
          ShipComponent component = ship.getComponent(i);
          if (planet.getTotalPopulation() > 0
              && (component.getType() == ShipComponentType.ORBITAL_BOMBS
                  || component.getType() == ShipComponentType.ORBITAL_NUKE)) {
            // Always bombing if population is more than 0
            shipComponentUsage(i);
          }
          if (component.getType() == ShipComponentType.PLANETARY_INVASION_MODULE
              && ship.getColonist() > 0) {
            if (ship.getTroopPower() > planet.getTroopPower() || troops) {
              // Using troops if only going to win
              // Or there is at least two troop carriers
              shipComponentUsage(i);
            }
            troops = true;
          }
          if (usedComponentIndex != -1) {
            int result = attackBombOrTroops();
            if (result == CONQUERED) {
              oneAttackFound = true;
              // Planet conquered
              exitLoop = true;
              if (starMap != null) {
                newsData = NewsFactory.makePlanetConqueredNews(attacker,
                    defender, planet, nuked.getText(), starMap.getStarYear());
              }
            }
            if (result == ONLY_BOMBED && starMap != null) {
              oneAttackFound = true;
              newsData = NewsFactory.makePlanetBombedNews(attacker,
                  defender, planet, nuked.getText(), starMap.getStarYear());
            }
            removeDestroyedShip();
            skipAnimation();
          }
          if (exitLoop) {
            break;
          }
        }
      }
      nextShip();
      if (shipIndex == 0) {
        if (!oneAttackFound) {
          exitLoop = true;
        }
        if (planet.getTotalPopulation() == 0 && !troops) {
          killGovernor("conquest", " conquest of planet");
          // No attack force exiting
          // Population was killed but no trooper so no owner either
          planet.setPlanetOwner(-1, null);
          exitLoop = true;
        }
        oneAttackFound = false;
        troops = false;
      }
      if (!actionsLeft()) {
        exitLoop = true;
      }
    } while (!exitLoop);
    handleLastNewsAndReputation();
  }

  /**
   * Check if bombers have actions left.
   * @return True if actions left.
   */
  public boolean actionsLeft() {
    boolean actionsLeft = false;
    for (BombingShip ship : bombers) {
      if (ship.getActions() > 0) {
        actionsLeft = true;
      }
    }
    return actionsLeft;
  }
  /**
   * Handle actions for Planet view.
   * @param arg0 ActionEvent command what player did
   */
  public void handleAction(final ActionEvent arg0) {
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_ANIMATION_TIMER)) {
      if (imgBase.getAnimation() != null) {
        PlanetAnimation anim = imgBase.getAnimation();
        if (anim.isAnimationFinished()) {
          removeDestroyedShip();
          nextShip();
        } else {
          int result = attackBombOrTroops();
          if (result == CONQUERED && starMap != null) {
            newsData = NewsFactory.makePlanetConqueredNews(attacker,
                defender, planet, nuked.getText(), starMap.getStarYear());
          }
          if (result == ONLY_BOMBED && starMap != null) {
            newsData = NewsFactory.makePlanetBombedNews(attacker,
                defender, planet, nuked.getText(), starMap.getStarYear());
          }
        }
        updatePanel();
        imgBase.repaint();
      } else {
        if (aiControlled && !aiExitLoop && bombers.size() > 0
            && bombers.get(shipIndex).getActions() > 0) {
          Ship ship = fleet.getShipByIndex(shipIndex);
          ShipComponent component = ship.getComponent(aiComponentIndex);
          if (component.getType() == ShipComponentType.ORBITAL_BOMBS
              || component.getType() == ShipComponentType.ORBITAL_NUKE
              || component.getType() == ShipComponentType.WEAPON_BEAM
              || component.getType() == ShipComponentType.WEAPON_HE_MISSILE
              || component.getType() == ShipComponentType.WEAPON_PHOTON_TORPEDO
              || component.getType() == ShipComponentType.WEAPON_RAILGUN
              || component.getType() == ShipComponentType.MULTICANNON
              || component.getType() == ShipComponentType.GRAVITY_RIPPER
              || component.getType() == ShipComponentType.PLASMA_CANNON
              || component.getType() == ShipComponentType.SPORE_MODULE
              || component.getType() == ShipComponentType.PLASMA_SPIT) {
            // Always bombing or shooting
            shipComponentUsage(aiComponentIndex);
          }
          if (component
              .getType() == ShipComponentType.PLANETARY_INVASION_MODULE) {
            if (ship.getTroopPower() > planet.getTroopPower() || aiTroops) {
              // Using troops if only going to win
              // Or there is at least two troop carriers
              shipComponentUsage(aiComponentIndex);
            }
            aiTroops = true;
          }
          if (usedComponentIndex != -1) {
            aiOneAttackFound = true;
            int result = attackBombOrTroops();
            if (result == CONQUERED) {
              // Planet conquered
              aiExitLoop = true;
              if (starMap != null) {
                newsData = NewsFactory.makePlanetConqueredNews(attacker,
                    defender, planet, nuked.getText(), starMap.getStarYear());
              }
            }
            if (result == ONLY_BOMBED && starMap != null) {
              newsData = NewsFactory.makePlanetBombedNews(attacker,
                  defender, planet, nuked.getText(), starMap.getStarYear());
            }
          }
          if (aiComponentIndex < ship.getNumberOfComponents() - 1) {
            aiComponentIndex++;
          } else {
            aiComponentIndex = 0;
            nextShip();
            if (shipIndex == 0) {
              if (!aiOneAttackFound) {
                aiExitLoop = true;
              }
              if (planet.getTotalPopulation() == 0 && !aiTroops) {
                killGovernor("conquest", " conquest of planet");
                // No attack force exiting
                // Population was killed but no trooper so no owner either
                planet.setPlanetOwner(-1, null);
                aiExitLoop = true;
              }
              aiOneAttackFound = false;
              aiTroops = false;
            }
          }
        } else {
          if (!actionsLeft()) {
            aiExitLoop = true;
          }
          endButton.setEnabled(true);
        }
      }
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_NEXT_TARGET)) {
      if (!allAi && game != null) {
        game.setBridgeCommand(BridgeCommandType.YELLOW_ALERT);
      }
      nextShip();
    }
    if (arg0.getActionCommand().startsWith(GameCommands.COMMAND_COMPONENT_USE)
        && imgBase.getAnimation() == null
        && bombers.get(shipIndex).getActions() > 0) {
      String number = arg0.getActionCommand()
          .substring(GameCommands.COMMAND_COMPONENT_USE.length());
      int index = Integer.valueOf(number);
      shipComponentUsage(index);
    }
  }

  /**
   * Return used ship component index
   * @return Component index
   */
  public int getUsedComponentIndex() {
    return usedComponentIndex;
  }

  /**
   * Tells if AI has done everything for conquering or not
   * @return True if AI is ready
   */
  public boolean isAiDone() {
    return aiExitLoop;
  }

  /**
   * Quickly skip animation
   */
  public void skipAnimation() {
    imgBase.setAnimation(null);
  }

  /**
   * Handle last news about planet conquer/bombing and give nuking reputation
   * if needed.
   */
  public void handleLastNewsAndReputation() {
    if (starMap == null || newsData == null) {
      return;
    }

    defendingPlayerIndex = starMap.getPlayerList().getIndex(defender);
    if (defendingPlayerIndex != -1
        && !attacker.getDiplomacy().isWar(defendingPlayerIndex)) {
      boolean attackerHasReveal = false;
      if (!fleet.isPrivateerFleet() || whatHappened == CONQUERED) {
        attackerHasReveal = true;
      }
      if (attackerHasReveal) {
        // No war between these two players, trying to conquer another
        // player's planet is act of war.
        DiplomaticTrade trade = new DiplomaticTrade(starMap,
            attackPlayerIndex, defendingPlayerIndex);
        trade.generateEqualTrade(NegotiationType.WAR);
        trade.doTrades();
        boolean casusbelli = attacker.getDiplomacy().hasCasusBelli(
            defendingPlayerIndex);
        StarMapUtilities.addWarDeclatingReputation(starMap, attacker,
            defender);
        NewsData warNews = NewsFactory.makeWarNews(attacker, defender, fleet,
            starMap, casusbelli);
        starMap.getNewsCorpData().addNews(warNews);
        starMap.getHistory().addEvent(NewsFactory.makeDiplomaticEvent(
            fleet, warNews));
        String[] defenseList = defender.getDiplomacy().activateDefensivePact(
            starMap, attacker);
        if (defenseList != null) {
          starMap.getNewsCorpData().addNews(
              NewsFactory.makeDefensiveActivation(attacker, defenseList));
        }
      }
    }

    if (nuked.isNuked()) {
      StarMapUtilities.addNukeReputation(starMap, attacker);
    }

    starMap.getNewsCorpData().addNews(newsData);
    EventOnPlanet event = new EventOnPlanet(EventType.PLANET_CONQUERED,
        planet.getCoordinate(), planet.getName(),
        starMap.getPlayerList().getIndex(attacker));
    event.setText(newsData.getNewsText());
    starMap.getHistory().addEvent(event);
  }
}
