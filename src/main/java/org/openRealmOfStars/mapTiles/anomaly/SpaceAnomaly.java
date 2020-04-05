package org.openRealmOfStars.mapTiles.anomaly;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.mapTiles.Tile;
import org.openRealmOfStars.mapTiles.TileNames;
import org.openRealmOfStars.mapTiles.Tiles;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.combat.Combat;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.leader.Gender;
import org.openRealmOfStars.player.leader.Job;
import org.openRealmOfStars.player.leader.Leader;
import org.openRealmOfStars.player.leader.LeaderUtility;
import org.openRealmOfStars.player.leader.NameGenerator;
import org.openRealmOfStars.player.leader.Perk;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipDamage;
import org.openRealmOfStars.player.ship.ShipSize;
import org.openRealmOfStars.player.ship.ShipStat;
import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.player.tech.TechType;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2018  Tuomo Untinen
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
* Space anomaly
*
*/
public class SpaceAnomaly {
  /**
   * Space anomaly type
   */
  private AnomalyType type;
  /**
   * Anomaly value for example credits
   */
  private int value;

  /**
   * Textual description what found or happened
   */
  private String text;

  /**
   * Image of Space anomaly
   */
  private BufferedImage image;

  /**
   * Combat about to happen in space anomaly
   */
  private Combat combat;

  /**
   * Constructor for Space anomaly
   * @param type AnomalyType
   * @param value Space anomaly value
   */
  public SpaceAnomaly(final AnomalyType type, final int value) {
    this.type = type;
    this.value = value;
    this.text = null;
    this.image = null;
    this.combat = null;
  }

  /**
   * Get Space anomaly type
   * @return AnomalyType
   */
  public AnomalyType getType() {
    return type;
  }

  /**
   * Get Space anomaly value
   * @return value
   */
  public int getValue() {
    return value;
  }

  /**
   * Get textual description of space anomaly.
   * @return Textual description
   */
  public String getText() {
    return text;
  }

  /**
   * Set textual description of space anomaly
   * @param text To set
   */
  public void setText(final String text) {
    this.text = text;
  }

  /**
   * Set image of space anomaly
   * @param image to set
   */
  public void setImage(final BufferedImage image) {
    this.image = image;
  }

  /**
   * Get image of space anomaly
   * @return BufferedImage
   */
  public BufferedImage getImage() {
    return image;
  }

  /**
   * Set combat for space anomaly
   * @param combat Combat to happen
   */
  public void setCombat(final Combat combat) {
    this.combat = combat;
  }

  /**
   * Return combat about to happen in space anomly
   * @return Combat or null
   */
  public Combat getCombat() {
    return combat;
  }
  /**
   * Create Space anomaly and handle space anomaly tile
   * @param map StarMap
   * @param info Player who found the anomaly
   * @param fleet Fleet which found anomaly
   * @return SpaceAnomaly or null if coordinates are illegal.
   */
  public static SpaceAnomaly createAnomalyEvent(final StarMap map,
      final PlayerInfo info, final Fleet fleet) {
    SpaceAnomaly result = null;
    Tile tile = map.getTile(fleet.getX(), fleet.getY());
    Tile empty = Tiles.getTileByName(TileNames.EMPTY);
    if (tile != null) {
      switch (tile.getName()) {
        case TileNames.SPACE_ANOMALY_CREDITS: {
          result = new SpaceAnomaly(AnomalyType.CREDIT,
              DiceGenerator.getRandom(2, 10));
          result.setText("There was hidden cache of credits hidden in"
              + " asteroids. Cache contained " + result.getValue()
              + " credits.");
          result.setImage(GuiStatics.IMAGE_ASTEROIDS);
          info.setTotalCredits(info.getTotalCredits() + result.value);
          map.setTile(fleet.getX(), fleet.getY(), empty);
          break;
        }
        case TileNames.SPACE_ANOMALY_MAP: {
          result = new SpaceAnomaly(AnomalyType.MAP, 7);
          result.setText("There was floating very old probe. Probe had"
              + " explored near by space. Explored data was added your"
              + " realm's exploration data.");
          result.setImage(GuiStatics.IMAGE_OLD_PROBE);
          map.setTile(fleet.getX(), fleet.getY(), empty);
          for (int x = -3; x < 4; x++) {
            for (int y = -3; y < 4; y++) {
              if (x == -3 && y == -3) {
                continue;
              }
              if (x == -3 && y == 3) {
                continue;
              }
              if (x == 3 && y == -3) {
                continue;
              }
              if (x == 3 && y == 3) {
                continue;
              }
              if (map.isValidCoordinate(fleet.getX() + x, fleet.getY() + y)) {
                info.setSectorVisibility(fleet.getX() + x, fleet.getY() + y,
                    PlayerInfo.VISIBLE);
              }
            }
          }
          break;
        }
        case TileNames.SPACE_ANOMALY_DSA: {
          result = new SpaceAnomaly(AnomalyType.DEEP_SPACE_ANCHOR, 0);
          result.setText("Deep Space Anchor was found behind the nebulae.");
          result.setImage(GuiStatics.IMAGE_DSA);
          Tile anchor = Tiles.getTileByName(TileNames.DEEP_SPACE_ANCHOR1);
          map.setTile(fleet.getX(), fleet.getY(), anchor);
          break;
        }
        case TileNames.SPACE_ANOMALY_LAIR: {
          result = new SpaceAnomaly(AnomalyType.LAIR, 0);
          result.setText("Pirate station was found on the deep space anchor."
              + " Battle begins...");
          result.setImage(GuiStatics.IMAGE_PIRATE_LAIR);
          Tile anchor = Tiles.getTileByName(TileNames.DEEP_SPACE_ANCHOR1);
          PlayerInfo board = map.getPlayerList().getBoardPlayer();
          Fleet lair = map.addSpaceAnomalyEnemy(fleet.getX(), fleet.getY(),
              board, StarMap.ENEMY_PIRATE_LAIR);
          Combat fight = new Combat(fleet, lair, info, board);
          result.setCombat(fight);
          map.setTile(fleet.getX(), fleet.getY(), anchor);
          break;
        }
        case TileNames.SPACE_ANOMALY_PIRATE: {
          result = new SpaceAnomaly(AnomalyType.PIRATE, 0);
          result.setText("Pirate ship was found in the nebulae."
              + " Battle begins...");
          result.setImage(GuiStatics.IMAGE_PIRATE_PILOT);
          map.setTile(fleet.getX(), fleet.getY(), empty);
          PlayerInfo board = map.getPlayerList().getBoardPlayer();
          Fleet pirate = map.addSpaceAnomalyEnemy(fleet.getX(), fleet.getY(),
              board, StarMap.ENEMY_PIRATE);
          Combat fight = new Combat(fleet, pirate, info, board);
          result.setCombat(fight);
          break;
        }
        case TileNames.SPACE_ANOMALY_SHIP: {
          ShipStat[] stats = info.getShipStatList();
          ArrayList<ShipStat> listStats = new ArrayList<>();
          for (ShipStat stat : stats) {
            if (!stat.isObsolete()) {
              Ship ship = new Ship(stat.getDesign());
              if (ship.getHull().getSize() == ShipSize.SMALL
                  && !ship.isStarBase()) {
                listStats.add(stat);
              }
            }
          }
          if (listStats.size() > 0) {
            ShipStat stat = listStats.get(DiceGenerator.getRandom(
                listStats.size() - 1));
            Ship ship = new Ship(stat.getDesign());
            stat.setNumberOfBuilt(stat.getNumberOfBuilt() + 1);
            stat.setNumberOfInUse(stat.getNumberOfInUse() + 1);
            Fleet newFleet = new Fleet(ship, fleet.getX(), fleet.getY());
            result = new SpaceAnomaly(AnomalyType.SHIP, 0);
            result.setText("Ship was found in the nebulae and"
                + " it crew decides to join your forces...");
            result.setImage(GuiStatics.IMAGE_SPACE_SHIP);
            map.setTile(fleet.getX(), fleet.getY(), empty);
            info.getFleets().add(newFleet);
          } else {
            result = new SpaceAnomaly(AnomalyType.SHIP, 0);
            result.setText("Ship pieces were found in "
                + "the asteroid field..."
                + " There isn't anything useful to find...");
            result.setImage(GuiStatics.IMAGE_ASTEROIDS);
            map.setTile(fleet.getX(), fleet.getY(), empty);
          }
          break;
        }
        case TileNames.SPACE_ANOMALY_TECH: {
          result = new SpaceAnomaly(AnomalyType.TECH, 0);
          Tech tech = info.getTechList().addNewRandomTech(info);
          if (tech != null) {
            result.setText("Found ancient ship floating around which contained"
                + " schematics of " + tech.getName() + ". This invention"
                + " is immediately taken to use.");
            result.setImage(GuiStatics.IMAGE_OLD_SHIP);
          } else {
            result = null;
          }
          map.setTile(fleet.getX(), fleet.getY(), empty);
          break;
        }
        case TileNames.SPACE_ANOMALY: {
          result = new SpaceAnomaly(AnomalyType.WORMHOLE, 0);
          result.setText("Wormhole discovered and it drags"
              + " your ship through it...");
          result.setImage(GuiStatics.IMAGE_BLACKHOLE);
          Tile anchor = Tiles.getTileByName(TileNames.WORM_HOLE1);
          map.setTile(fleet.getX(), fleet.getY(), anchor);
          map.clearFleetTiles();
          Coordinate coord = map.getFreeRandomSpot();
          fleet.setPos(coord);
          map.doFleetScanUpdate(info, fleet, null);
          break;
        }
        case TileNames.SPACE_ANOMALY_MECHION: {
          result = new SpaceAnomaly(AnomalyType.ANCIENT_MECHION, 0);
          result.setText("Ancient Mechion was in long lasting stasis in "
              + "ancient ship floating in vastness of space. When entering "
              + "the ship Mechion wakes and is willing to join your"
              + "realm.");
          result.setImage(GuiStatics.IMAGE_OLD_SHIP);
          map.setTile(fleet.getX(), fleet.getY(), empty);
          String name = NameGenerator.generateName(SpaceRace.MECHIONS,
              Gender.NONE);
          Leader leader = new Leader(name);
          leader.setAge(DiceGenerator.getRandom(300, 1500));
          leader.setHomeworld("Unknown");
          leader.setLevel(DiceGenerator.getRandom(2, 5));
          leader.setRace(SpaceRace.MECHIONS);
          leader.setJob(Job.UNASSIGNED);
          leader.setTitle("");
          for (int i = 0; i < leader.getLevel(); i++) {
            Perk[] newPerks = LeaderUtility.getNewPerks(leader,
                LeaderUtility.PERK_TYPE_GOOD);
            int index = DiceGenerator.getRandom(newPerks.length - 1);
            leader.getPerkList().add(newPerks[index]);
            if (DiceGenerator.getRandom(99)  < 10) {
              newPerks = LeaderUtility.getNewPerks(leader,
                  LeaderUtility.PERK_TYPE_BAD);
              index = DiceGenerator.getRandom(newPerks.length - 1);
              leader.getPerkList().add(newPerks[index]);
            }
          }
          info.getLeaderPool().add(leader);
          break;
        }
        case TileNames.SPACE_ANOMALY_TIME_WARP: {
          result = new SpaceAnomaly(AnomalyType.TIME_WARP,
              DiceGenerator.getRandom(20, 80));
          StringBuilder sb = new StringBuilder();
          sb.append("Space nebulae contained warpped time. Entering"
              + " it has immediately increase time in this sector. Sector time"
              + " has increased");
          sb.append(result.getValue());
          sb.append(" turns. After this"
              + " time returns normal in sector. Your fleet ships have"
              + " taken damage due the passing time.");
          if (fleet.getCommander() != null) {
            sb.append(" Fleet leader has also got older due the time warp. ");
            fleet.getCommander().setAge(fleet.getCommander().getAge()
                + result.value);
            int rp = result.getValue() / 2;
            int multiplier = 1;
            if (fleet.getCommander().hasPerk(Perk.ACADEMIC)
                || fleet.getCommander().hasPerk(Perk.SCIENTIST)
                || fleet.getCommander().hasPerk(Perk.FTL_ENGINEER)) {
              rp = rp * 2;
              multiplier = multiplier * 2;
            }
            if (fleet.getCommander().hasPerk(Perk.STUPID)
                || fleet.getCommander().hasPerk(Perk.SLOW_LEARNER)) {
              rp = rp / 2;
              multiplier = multiplier / 2;
            }
            switch (multiplier) {
              case 2: {
                sb.append(fleet.getCommander().getCallName());
                sb.append(" was able to research event and propulsion research "
                    + " rushes forward!");
                break;
              }
              default:
              case 1: {
                sb.append(fleet.getCommander().getCallName());
                sb.append(" was able to study event and propulsion research "
                    + " moves forward!");
                break;
              }
              case 0: {
                sb.append(fleet.getCommander().getCallName());
                sb.append(" was trying to learn the event but"
                    + " results were not great. Propulsion research got some "
                    + " new ideas.");
                break;
              }
            }
            info.getTechList().setTechResearchPoints(TechType.Propulsion,
                info.getTechList().getTechResearchPoints(TechType.Propulsion)
                + rp);
          }
          map.setTile(fleet.getX(), fleet.getY(), empty);
          for (Ship ship : fleet.getShips()) {
            int damage = ship.getHull().getSlotHull() / 2;
            ShipDamage shipDamage = new ShipDamage(1, "Time wears the ship.");
            while (damage > 0) {
              damage = ship.damageComponent(damage, shipDamage);
            }
            sb.append(" ");
            sb.append(shipDamage.getMessage());
          }
          result.setText(sb.toString());
          result.setImage(GuiStatics.IMAGE_TIME_WARP);
        }
        default: {
          break;
        }
      }
      if (fleet.getCommander() != null && result != null) {
        fleet.getCommander().setExperience(
            fleet.getCommander().getExperience() + 50);
      }
    }
    return result;
  }
}
