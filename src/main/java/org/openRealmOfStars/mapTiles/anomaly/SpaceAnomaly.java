package org.openRealmOfStars.mapTiles.anomaly;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.openRealmOfStars.game.Game;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.mapTiles.Tile;
import org.openRealmOfStars.mapTiles.TileNames;
import org.openRealmOfStars.mapTiles.Tiles;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.artifact.ArtifactFactory;
import org.openRealmOfStars.player.combat.Combat;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.leader.Gender;
import org.openRealmOfStars.player.leader.Job;
import org.openRealmOfStars.player.leader.Leader;
import org.openRealmOfStars.player.leader.LeaderUtility;
import org.openRealmOfStars.player.leader.NameGenerator;
import org.openRealmOfStars.player.leader.Perk;
import org.openRealmOfStars.player.message.Message;
import org.openRealmOfStars.player.message.MessageType;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipDamage;
import org.openRealmOfStars.player.ship.ShipHullType;
import org.openRealmOfStars.player.ship.ShipSize;
import org.openRealmOfStars.player.ship.ShipStat;
import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.player.tech.TechFactory;
import org.openRealmOfStars.player.tech.TechType;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2018,2020-2022 Tuomo Untinen
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
   * News station text.
   */
  private static final String NEWS_STATION_TEXT_BEGIN =
      "Ancient but functional news station is floating in "
      + "space. This is the place where all galactic news are sent."
      + " There is single robot sitting behind the desk and automation"
      + " is doing all the news gathering. Robot or station does not"
      + " seem to care about or react on visitors.";
  /**
   * News station text.
   */
  private static final String NEWS_STATION_TEXT =
      NEWS_STATION_TEXT_BEGIN + " Your fleet crew gathers some ancient"
          + " technology for later research.";
  /**
   * News station text already visited.
   */
  private static final String NEWS_STATION_VISITED_TEXT =
      NEWS_STATION_TEXT_BEGIN + " Your fleet crew does not find anything"
          + " unseen electronic.";

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
    boolean addExp = true;
    if (tile != null) {
      switch (tile.getName()) {
        case TileNames.SPACE_ANOMALY_CREDITS: {
          result = new SpaceAnomaly(AnomalyType.CREDIT,
              DiceGenerator.getRandom(10, 30));
          result.setText("There was hidden cache of credits hidden in"
              + " asteroids. Cache contained " + result.getValue()
              + " credits.");
          result.setImage(GuiStatics.IMAGE_ASTEROIDS);
          info.setTotalCredits(info.getTotalCredits() + result.value);
          map.setTile(fleet.getX(), fleet.getY(), empty);
          if (fleet.getCommander() != null) {
            fleet.getCommander().setExperience(
                fleet.getCommander().getExperience() + 10);
          }
          break;
        }
        case TileNames.SPACE_ANOMALY_MAP: {
          result = new SpaceAnomaly(AnomalyType.MAP, 7);
          result.setText("There was floating very old probe. Probe had"
              + " explored near by space. Explored data was added your"
              + " realm's exploration data.");
          result.setImage(GuiStatics.IMAGE_OLD_PROBE);
          map.setTile(fleet.getX(), fleet.getY(), empty);
          if (fleet.getCommander() != null) {
            fleet.getCommander().setExperience(
                fleet.getCommander().getExperience() + 10);
          }
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
          if (fleet.getCommander() != null) {
            fleet.getCommander().setExperience(
                fleet.getCommander().getExperience() + 10);
          }
          break;
        }
        case TileNames.SPACE_ANOMALY_LAIR: {
          result = new SpaceAnomaly(AnomalyType.LAIR, 0);
          result.setText("Pirate station was found on the deep space anchor."
              + " Battle begins...");
          result.setImage(GuiStatics.IMAGE_PIRATE_LAIR);
          Tile anchor = Tiles.getTileByName(TileNames.DEEP_SPACE_ANCHOR1);
          PlayerInfo board = map.getPlayerList().getSpacePiratePlayer();
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
          PlayerInfo board = map.getPlayerList().getSpacePiratePlayer();
          Fleet pirate = map.addSpaceAnomalyEnemy(fleet.getX(), fleet.getY(),
              board, StarMap.ENEMY_PIRATE);
          Combat fight = new Combat(fleet, pirate, info, board);
          result.setCombat(fight);
          break;
        }
        case TileNames.SPACE_ANOMALY_MONSTER: {
          result = new SpaceAnomaly(AnomalyType.MONSTER, 0);
          result.setImage(GuiStatics.IMAGE_KRAKEN);
          map.setTile(fleet.getX(), fleet.getY(), empty);
          PlayerInfo board = map.getPlayerList().getSpaceMonsterPlayer();
          Fleet monster = map.addSpaceAnomalyEnemy(fleet.getX(), fleet.getY(),
              board, StarMap.ENEMY_MONSTER);
          result.setText(monster.getBiggestShip().getName()
              + " was found in the nebulae. Battle begins...");
          Combat fight = new Combat(fleet, monster, info, board);
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
                  && !ship.isStarBase()
                  && ship.getHull().getHullType() != ShipHullType.ORBITAL) {
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
            if (fleet.getCommander() != null) {
              fleet.getCommander().setExperience(
                  fleet.getCommander().getExperience() + 10);
            }
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
            if (fleet.getCommander() != null) {
              fleet.getCommander().setExperience(
                  fleet.getCommander().getExperience() + 10);
            }
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
          Coordinate coord = map.getFreeRandomSpot();
          map.setTile(coord.getX(), coord.getY(), anchor);
          map.clearFleetTiles();
          // Actual drag is done on movement while checking the tile type
          if (fleet.getCommander() != null) {
            fleet.getCommander().setExperience(
                fleet.getCommander().getExperience() + 30);
          }
          break;
        }
        case TileNames.SPACE_ANOMALY_MECHION: {
          result = new SpaceAnomaly(AnomalyType.ANCIENT_ROBOT, 0);
          SpaceRace leaderRace = SpaceRace.MECHIONS;
          String name = NameGenerator.generateName(SpaceRace.MECHIONS,
              Gender.NONE);
          String capitalDesc = "Ancient Mechion";
          String desc = "Mechion";
          if (DiceGenerator.getRandom(99) < 50) {
            leaderRace = SpaceRace.SYNTHDROIDS;
            name = NameGenerator.generateName(SpaceRace.SYNTHDROIDS,
                Gender.FEMALE);
            capitalDesc = "Ancient Synthdroid";
            desc = "Synthdroid";
          }
          result.setText(capitalDesc + " was in long lasting stasis in "
              + "ancient ship floating in vastness of space. When entering "
              + "the ship " + desc + " wakes and is willing to join your "
              + "realm.");
          result.setImage(GuiStatics.IMAGE_OLD_SHIP);
          map.setTile(fleet.getX(), fleet.getY(), empty);
          Leader leader = new Leader(name);
          leader.setAge(DiceGenerator.getRandom(300, 1500));
          leader.setHomeworld("Unknown");
          leader.setLevel(DiceGenerator.getRandom(2, 5));
          leader.setRace(leaderRace);
          leader.setJob(Job.UNASSIGNED);
          leader.setTitle("");
          for (int i = 0; i < leader.getLevel(); i++) {
            Perk[] newPerks = LeaderUtility.getNewPerks(leader,
                LeaderUtility.PERK_TYPE_GOOD);
            int index = DiceGenerator.getRandom(newPerks.length - 1);
            leader.getPerkList().add(newPerks[index]);
            if (DiceGenerator.getRandom(99) < 10) {
              newPerks = LeaderUtility.getNewPerks(leader,
                  LeaderUtility.PERK_TYPE_BAD);
              index = DiceGenerator.getRandom(newPerks.length - 1);
              leader.getPerkList().add(newPerks[index]);
            }
          }
          if (fleet.getCommander() != null) {
            fleet.getCommander().setExperience(
                fleet.getCommander().getExperience() + 10);
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
              + " has increased ");
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
          if (fleet.getCommander() != null) {
            fleet.getCommander().setExperience(
                fleet.getCommander().getExperience() + 40);
          }
          map.setTile(fleet.getX(), fleet.getY(), empty);
          for (Ship ship : fleet.getShips()) {
            int damage = ship.getHull().getSlotHull()
                * ship.getHull().getMaxSlot() / 2;
            ShipDamage shipDamage = new ShipDamage(1, "Time wears the ship.");
            while (damage > 0) {
              damage = ship.damageComponent(damage, shipDamage);
            }
            sb.append(" ");
            sb.append(shipDamage.getMessage());
          }
          result.setText(sb.toString());
          result.setImage(GuiStatics.IMAGE_TIME_WARP);
          break;
        }
        case TileNames.SPACE_ANOMALY_RARE_TECH: {
          result = new SpaceAnomaly(AnomalyType.RARE_TECH, 0);
          Tech tech = TechFactory.getRandomRareTech(
              info.getTechList().getRareTechs());
          if (tech != null) {
            result.setText("Found ancient capsule floating around which"
                + " contained schematics of " + tech.getName()
                + ". This seems to be unusual piece of technology."
                + " This invention is immediately taken to use.");
            result.setImage(GuiStatics.IMAGE_RARE_TECH);
            info.getTechList().addTech(tech);
            if (Game.getTutorial() != null  && info.isHuman()
                && map.isTutorialEnabled()) {
              String tutorialText = Game.getTutorial().showTutorialText(14);
              if (tutorialText != null) {
                Message msg = new Message(MessageType.INFORMATION, tutorialText,
                    Icons.getIconByName(Icons.ICON_TUTORIAL));
                info.getMsgList().addNewMessage(msg);
              }
            }
          } else {
            result = null;
          }
          map.setTile(fleet.getX(), fleet.getY(), empty);
          break;
        }
        case TileNames.SPACE_ANOMALY_ANCIENT_ARTIFACT: {
          result = new SpaceAnomaly(AnomalyType.ANCIENT_ARTIFACT, 0);
          result.setText("Found ancient capsule floating around which"
              + " contained strange piece of ancient artifact. "
              + ". This seems to be unusual piece of history."
              + " This finding requires some research time.");
          if (DiceGenerator.getRandom(1) == 0) {
            result.setImage(GuiStatics.IMAGE_ARTIFACT1);
          } else {
            result.setImage(GuiStatics.IMAGE_ARTIFACT2);
          }
          info.getArtifactLists().addDiscoveredArtifact(
              ArtifactFactory.getRandomNonFacility());
          if (Game.getTutorial() != null  && info.isHuman()
              && map.isTutorialEnabled()) {
            String tutorialText = Game.getTutorial().showTutorialText(15);
            if (tutorialText != null) {
              Message msg = new Message(MessageType.INFORMATION, tutorialText,
                  Icons.getIconByName(Icons.ICON_TUTORIAL));
              info.getMsgList().addNewMessage(msg);
            }
          }
          map.setTile(fleet.getX(), fleet.getY(), empty);
          break;
        }
        case TileNames.SPACE_ANOMALY_NEWS_STATION: {
          result = new SpaceAnomaly(AnomalyType.NEWS_STATION, 0);
          result.setText(NEWS_STATION_TEXT);
          result.setImage(GuiStatics.IMAGE_NEWSTATION);
          info.getArtifactLists().addDiscoveredArtifact(
              ArtifactFactory.createArtifact(
                  ArtifactFactory.BROADCASTING_ELETRONIC));
          if (Game.getTutorial() != null  && info.isHuman()
              && map.isTutorialEnabled()) {
            String tutorialText = Game.getTutorial().showTutorialText(15);
            if (tutorialText != null) {
              Message msg = new Message(MessageType.INFORMATION, tutorialText,
                  Icons.getIconByName(Icons.ICON_TUTORIAL));
              info.getMsgList().addNewMessage(msg);
            }
          }
          Tile station = Tiles.getTileByName(TileNames.NEWSTATION1);
          map.setTile(fleet.getX(), fleet.getY(), station);
          break;
        }
        case TileNames.NEWSTATION1:
        case TileNames.NEWSTATION2: {
          if (!info.getArtifactLists().hasBroadcastingArtifact()) {
            result = new SpaceAnomaly(AnomalyType.NEWS_STATION, 0);
            result.setText(NEWS_STATION_TEXT);
            result.setImage(GuiStatics.IMAGE_NEWSTATION);
            info.getArtifactLists().addDiscoveredArtifact(
                ArtifactFactory.createArtifact(
                    ArtifactFactory.BROADCASTING_ELETRONIC));
            if (Game.getTutorial() != null  && info.isHuman()
                && map.isTutorialEnabled()) {
              String tutorialText = Game.getTutorial().showTutorialText(15);
              if (tutorialText != null) {
                Message msg = new Message(MessageType.INFORMATION, tutorialText,
                    Icons.getIconByName(Icons.ICON_TUTORIAL));
                info.getMsgList().addNewMessage(msg);
              }
            }
          } else {
            addExp = false;
            result = new SpaceAnomaly(AnomalyType.NEWS_STATION, 0);
            result.setText(NEWS_STATION_VISITED_TEXT);
            result.setImage(GuiStatics.IMAGE_NEWSTATION);
          }
          break;
        }
        case TileNames.SPACE_ANOMALY_LEADER_IN_STASIS: {
          result = new SpaceAnomaly(AnomalyType.LEADER_IN_STASIS, 0);
          SpaceRace leaderRace = SpaceRace.getRandomLivingRace();
          Gender gender = Gender.getRandom();
          String name = NameGenerator.generateName(leaderRace, gender);
          String desc = leaderRace.getNameSingle();
          String capitalDesc = desc.substring(0).toUpperCase()
              + desc.substring(1);
          result.setText(capitalDesc + " was in long lasting stasis in "
              + "old ship floating in vastness of space. When entering "
              + "the ship " + desc + " wakes and is willing to join your "
              + "realm because of rescuing " + gender.getHisHer() + " life.");
          result.setImage(GuiStatics.IMAGE_STASIS);
          map.setTile(fleet.getX(), fleet.getY(), empty);
          Leader leader = new Leader(name);
          leader.setAge(DiceGenerator.getRandom(30, 50));
          leader.setHomeworld("Unknown");
          leader.setLevel(DiceGenerator.getRandom(1, 3));
          leader.setRace(leaderRace);
          leader.setJob(Job.UNASSIGNED);
          leader.setTitle("");
          for (int i = 0; i < leader.getLevel(); i++) {
            Perk[] newPerks = LeaderUtility.getNewPerks(leader,
                LeaderUtility.PERK_TYPE_GOOD);
            int index = DiceGenerator.getRandom(newPerks.length - 1);
            leader.getPerkList().add(newPerks[index]);
            if (DiceGenerator.getRandom(99) < 10) {
              newPerks = LeaderUtility.getNewPerks(leader,
                  LeaderUtility.PERK_TYPE_BAD);
              index = DiceGenerator.getRandom(newPerks.length - 1);
              leader.getPerkList().add(newPerks[index]);
            }
          }
          if (fleet.getCommander() != null) {
            fleet.getCommander().setExperience(
                fleet.getCommander().getExperience() + 10);
          }
          info.getLeaderPool().add(leader);
          break;
        }
        default: {
          break;
        }
      }
      if (fleet.getCommander() != null && result != null && addExp) {
        fleet.getCommander().setExperience(
            fleet.getCommander().getExperience() + 50);
      }
    }
    return result;
  }
}
