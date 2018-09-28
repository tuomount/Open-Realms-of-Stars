package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import org.openRealmOfStars.audio.music.MusicPlayer;
import org.openRealmOfStars.audio.soundeffect.SoundPlayer;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.ListRenderers.ShipListRenderer;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.infopanel.BattleInfoPanel;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.IconLabel;
import org.openRealmOfStars.gui.labels.InfoTextArea;
import org.openRealmOfStars.gui.labels.SpaceLabel;
import org.openRealmOfStars.gui.mapPanel.PlanetAnimation;
import org.openRealmOfStars.gui.panels.BigImagePanel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.SpaceGreyPanel;
import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipComponent;
import org.openRealmOfStars.player.ship.ShipComponentType;
import org.openRealmOfStars.player.ship.ShipDamage;
import org.openRealmOfStars.player.ship.ShipStat;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.history.event.EventOnPlanet;
import org.openRealmOfStars.starMap.history.event.EventType;
import org.openRealmOfStars.starMap.newsCorp.NewsData;
import org.openRealmOfStars.starMap.newsCorp.NewsFactory;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.utilities.DiceGenerator;
import org.openRealmOfStars.utilities.Logger;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016-2018  Tuomo Untinen
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
 * Planet bombing view for handling attack on planet
 *
 */
public class PlanetBombingView extends BlackPanel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;
  /**
   * Maximum log rows
   */
  static final int MAX_LOG_NUMBER = 11;

  /**
   * Total number of population
   */
  private IconLabel totalPeople;
  /**
   * Trooper's power
   */
  private IconLabel troopsPower;
  /**
   * Defense turret value
   */
  private IconLabel defenseTurret;
  /**
   * Total number of building
   */
  private IconLabel totalBuildings;

  /**
   * Planet owner's name
   */
  private SpaceLabel ownerLabel;

  /**
   * Planet to show
   */
  private Planet planet;

  /**
   * Bombing fleet
   */
  private Fleet fleet;

  /**
   * Ships in fleet
   */
  private JList<Ship> shipsInFleet;

  /**
   * Background picture
   */
  private BigImagePanel imgBase;

  /**
   * Info panel on east side
   */
  private BattleInfoPanel infoPanel;

  /**
   * Text area containing info
   */
  private InfoTextArea textArea;

  /**
   * Logger for handling logged message rows.
   */
  private Logger textLogger;

  /**
   * Is component used or not
   */
  private boolean[] componentUsed;

  /**
   * Ship index from fleet
   */
  private int shipIndex = 0;

  /**
   * Which component was used to attack to planet
   */
  private int usedComponentIndex;

  /**
   * Player who is attacking
   */
  private PlayerInfo attacker;

  /**
   * Attack player index
   */
  private int attackPlayerIndex;

  /**
   * Is screen controlled by AI
   */
  private boolean aiControlled;

  /**
   * All players are in AI
   */
  private boolean allAi;

  /**
   * End button where to quit the screen
   */
  private SpaceButton endButton;

  /**
   * AI has at least one attack
   */
  private boolean aiOneAttackFound = false;
  /**
   * AI has done all he or she wants to do
   */
  private boolean aiExitLoop = false;
  /**
   * AI has troops in the fleet
   */
  private boolean aiTroops = false;

  /**
   * AI index which component it is checking
   */
  private int aiComponentIndex = 0;

  /**
   * StarMap
   */
  private StarMap starMap;

  /**
   * Text for nuking. Null if nukes were not used.
   */
  private String nukeText;

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
    this.setPlanet(planet);
    this.fleet = fleet;
    this.attacker = attacker;
    this.attackPlayerIndex = attackerPlayerIndex;
    MusicPlayer.play(MusicPlayer.FIGHT_THEME01);
    aiControlled = false;
    allAi = false;
    nukeText = null;
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
        "Troops power: " + planet.getTroopPower());
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
    scroll.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    eastPanel.add(scroll);

    infoPanel = new BattleInfoPanel(fleet.getFirstShip(), null, listener);
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
    panel.add(endButton);
    eastPanel.add(panel);

    // Bottom panel
    InfoPanel bottomPanel = new InfoPanel();
    bottomPanel.setLayout(new BorderLayout());
    bottomPanel.setTitle(null);
    int numberOfRows = 10;
    if (GuiStatics.isLargerFonts()) {
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
    troopsPower.setText("Troops power: " + planet.getTroopPower());

    StringBuilder sb = new StringBuilder();
    for (int i = textLogger.size() - 1; i >= 0; i--) {
      sb.append(textLogger.getMessage(i));
      if (i != 0) {
        sb.append("\n");
      }
    }
    textArea.setText(sb.toString());
    textArea.repaint();
    infoPanel.updatePanel();

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
   * Use component on ship. Currently Orbital bombs/nukes and
   * planetary invasion module work
   * @param index Ship Component index
   */
  public void shipComponentUsage(final int index) {
    if (index < 0 || index > componentUsed.length) {
      // Do nothing if index is out ouf bounds
      return;
    }
    Ship ship = fleet.getShipByIndex(shipIndex);
    if (!componentUsed[index] && ship.componentIsWorking(index)) {
      componentUsed[index] = true;
      ShipComponent comp = ship.getComponent(index);
      if (comp != null) {
        if (comp.getType() == ShipComponentType.ORBITAL_BOMBS
            || comp.getType() == ShipComponentType.ORBITAL_NUKE) {
          planetTurretShoot();
          updatePanel();
          usedComponentIndex = index;
        }
        if (comp.getType() == ShipComponentType.PLANETARY_INVASION_MODULE) {
          if (ship.getColonist() > 0) {
            planetTurretShoot();
            updatePanel();
            usedComponentIndex = index;
          } else {
            textLogger.addLog("No more troops on board!");
          }

        }
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
    if (turret > 0) {
      if (ship.getShield() > turret) {
        ship.setShield(ship.getShield() - 1);
        textLogger.addLog("Planetary defense is deflected to ship's shield.");
      } else {
        turret = turret - ship.getShield();
        ship.setShield(ship.getShield() - 1);
        if (turret > 0) {
          if (ship.getArmor() > turret) {
            textLogger
                .addLog("Planetary defense is deflected to ship's armor.");
            ship.setArmor(ship.getArmor() - 1);
          } else {
            turret = turret - ship.getArmor();
            ship.setShield(ship.getShield() - 1);
            ShipDamage shipDamage = new ShipDamage(1, "NOT USED!");
            while (turret > 0 && ship.getHullPoints() > 0) {
              // FIXME SHIPDAMAGE is not really used
              turret = ship.damageComponent(turret, shipDamage);
            }
            if (ship.getHullPoints() <= 0) {
              textLogger.addLog("Planetary defense damages " + ship.getName()
                  + " and destroys it.");
            } else {
              textLogger
                  .addLog("Planetary defense damages " + ship.getName() + ".");
            }
          }
        }
      }
    } else {
      textLogger.addLog("Planet does not have planetary turrets...");
    }
  }

  /**
   * Check if ship has been destroyed and remove it if it has.
   */
  public void removeDestroyedShip() {
    Ship ship = fleet.getShipByIndex(shipIndex);
    if (ship.getHullPoints() <= 0) {
      fleet.removeShip(ship);
      ShipStat stat = attacker.getShipStatByName(ship.getName());
      if (stat != null) {
        stat.setNumberOfLoses(stat.getNumberOfLoses() + 1);
        stat.setNumberOfInUse(stat.getNumberOfInUse() - 1);
      }
    }
  }

  /**
   * Attack with bombs or troops. This handles killing/destroying
   * the buildings and animation.
   * @return true if planet was conquered
   */
  public boolean attackBombOrTroops() {
    boolean conquered = false;
    if (usedComponentIndex != -1) {
      Ship ship = fleet.getShipByIndex(shipIndex);
      ShipComponent comp = ship.getComponent(usedComponentIndex);
      if (ship.componentIsWorking(usedComponentIndex)) {
        infoPanel.useComponent(usedComponentIndex);
        if (comp.getType() == ShipComponentType.ORBITAL_NUKE) {
          imgBase.setAnimation(new PlanetAnimation(
              PlanetAnimation.ANIMATION_TYPE_NUKE_AIM, 0, 0, 1, 1));
          nukeText = planet.nukem(comp.getDamage(), comp.getName());
          textLogger.addLog(ship.getName() + " nukes the planet!");
        }
        if (comp.getType() == ShipComponentType.ORBITAL_BOMBS) {
          imgBase.setAnimation(new PlanetAnimation(
              PlanetAnimation.ANIMATION_TYPE_BOMBING_AIM, 0, 0, 1, 1));
          int hit = DiceGenerator.getRandom(1, 100);
          if (hit <= comp.getDamage()) {
            planet.killOneWorker();
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
        }
        if (comp.getType() == ShipComponentType.PLANETARY_INVASION_MODULE) {
          imgBase.setAnimation(null);
          int shipTroop = ship.getHull().getRace().getTrooperPower()
              * (100 + comp.getDamage()) / 100;
          int shipTroops = ship.getHull().getRace().getTrooperPower()
              * ship.getColonist() * (100 + comp.getDamage()) / 100;
          int planetTroops = planet.getTroopPower();
          if (shipTroops > planetTroops) {
            planet.fightAgainstAttacker(shipTroops);
            int left = shipTroops - planetTroops;
            left = left / shipTroop;
            if (left <= 0) {
              left = 1;
            }
            ship.setColonist(0);
            planet.setPlanetOwner(attackPlayerIndex, attacker);
            conquered = true;
            if (attacker.getRace() == SpaceRace.MECHIONS) {
              planet.setWorkers(Planet.PRODUCTION_WORKERS, left);
            } else {
              planet.setWorkers(Planet.PRODUCTION_FOOD, left);
            }
            textLogger.addLog("Your troops colonize the planet!");
          } else {
            planet.fightAgainstAttacker(shipTroops);
            ship.setColonist(0);
            textLogger.addLog("Your troops are killed during the attack!");
          }
        }
      }
      usedComponentIndex = -1;
    }
    return conquered;
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
      for (int i = 0; i < ship.getNumberOfComponents(); i++) {
        ShipComponent component = ship.getComponent(i);
        if (component.getType() == ShipComponentType.ORBITAL_BOMBS
            || component.getType() == ShipComponentType.ORBITAL_NUKE) {
          // Always bombing
          shipComponentUsage(i);
        }
        if (component.getType() == ShipComponentType
            .PLANETARY_INVASION_MODULE) {
          if (ship.getTroopPower() > planet.getTroopPower() || troops) {
            // Using troops if only going to win
            // Or there is at least two troop carriers
            shipComponentUsage(i);
          }
          troops = true;
        }
        if (usedComponentIndex != -1) {
          oneAttackFound = true;
          PlayerInfo defender = planet.getPlanetPlayerInfo();
          if (attackBombOrTroops()) {
            // Planet conquered
            exitLoop = true;
            if (starMap != null) {
              NewsData news = NewsFactory.makePlanetConqueredNews(attacker,
                  defender, planet, nukeText);
              starMap.getNewsCorpData().addNews(news);
              EventOnPlanet event = new EventOnPlanet(
                  EventType.PLANET_CONQUERED,
                  planet.getCoordinate(), planet.getName(),
                  starMap.getPlayerList().getIndex(attacker));
              event.setText(news.getNewsText());
              starMap.getHistory().addEvent(event);
            }
          }
          removeDestroyedShip();
          skipAnimation();
        }
      }
      nextShip();
      if (shipIndex == 0) {
        if (!oneAttackFound) {
          exitLoop = true;
        }
        if (planet.getTotalPopulation() == 0 && !troops) {
          // No attack force exiting
          exitLoop = true;
        }
        oneAttackFound = false;
        troops = false;
      }
    } while (!exitLoop);
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
        } else {
          PlayerInfo defender = planet.getPlanetPlayerInfo();
          if (attackBombOrTroops() && starMap != null) {
            NewsData news = NewsFactory.makePlanetConqueredNews(attacker,
                defender, planet, nukeText);
            starMap.getNewsCorpData().addNews(news);
            EventOnPlanet event = new EventOnPlanet(EventType.PLANET_CONQUERED,
                planet.getCoordinate(), planet.getName(),
                starMap.getPlayerList().getIndex(attacker));
            event.setText(news.getNewsText());
            starMap.getHistory().addEvent(event);
          }
        }
        updatePanel();
        imgBase.repaint();
      } else {
        if (aiControlled && !aiExitLoop) {
          Ship ship = fleet.getShipByIndex(shipIndex);
          ShipComponent component = ship.getComponent(aiComponentIndex);
          if (component.getType() == ShipComponentType.ORBITAL_BOMBS
             || component.getType() == ShipComponentType.ORBITAL_NUKE) {
            // Always bombing
            shipComponentUsage(aiComponentIndex);
          }
          if (component.getType() == ShipComponentType
             .PLANETARY_INVASION_MODULE) {
            if (ship.getTroopPower() > planet.getTroopPower() || aiTroops) {
              // Using troops if only going to win
              // Or there is at least two troop carriers
              shipComponentUsage(aiComponentIndex);
            }
            aiTroops = true;
          }
          if (usedComponentIndex != -1) {
            aiOneAttackFound = true;
            PlayerInfo defender = planet.getPlanetPlayerInfo();
            if (attackBombOrTroops()) {
              // Planet conquered
              aiExitLoop = true;
              if (starMap != null) {
                starMap.getNewsCorpData().addNews(
                    NewsFactory.makePlanetConqueredNews(attacker, defender,
                        planet, nukeText));
              }
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
                // No attack force exiting
                aiExitLoop = true;
              }
              aiOneAttackFound = false;
              aiTroops = false;
            }
          }
        } else {
          endButton.setEnabled(true);
        }
      }
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_NEXT_TARGET)) {
      nextShip();
    }
    if (arg0.getActionCommand().startsWith(GameCommands.COMMAND_COMPONENT_USE)
        && imgBase.getAnimation() == null) {
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
}
