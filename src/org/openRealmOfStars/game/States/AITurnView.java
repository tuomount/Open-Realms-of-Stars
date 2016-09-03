package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;

import org.openRealmOfStars.AI.Mission.Mission;
import org.openRealmOfStars.AI.Mission.MissionPhase;
import org.openRealmOfStars.AI.Mission.MissionType;
import org.openRealmOfStars.AI.PathFinding.AStarSearch;
import org.openRealmOfStars.AI.PathFinding.PathPoint;
import org.openRealmOfStars.game.Game;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.game.GameState;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.labels.TransparentLabel;
import org.openRealmOfStars.gui.panels.BigImagePanel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.InvisiblePanel;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.message.Message;
import org.openRealmOfStars.player.message.MessageType;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.starMap.Route;
import org.openRealmOfStars.starMap.Sun;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
 * 
 * Open Realm of Stars game project
 * Copyright (C) 2016  Tuomo Untinen
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
 * AI turn view for Open Realm of Stars
 * 
 */
public class AITurnView extends BlackPanel {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  /**
   * Text for showing human player
   */
  private TransparentLabel label;
  
  /**
   * Reference to the game
   */
  private Game game;
  
  /**
   * Text animation
   */
  private int textAnim; 

  /**
   * Constructor for main menu
   * @param listener ActionListener
   */
  public AITurnView(Game game, ActionListener listener) {
    this.game = game;
    Planet planet = new Planet(1, 1, "Random Planet",1, false);
    if (DiceGenerator.getRandom(100)<10) {
      planet.setPlanetImageIndex(DiceGenerator.getRandom(1));
      planet.setGasGiant(true);
    } else {
      planet.setPlanetType(DiceGenerator.getRandom(Planet.PLANET_IMAGE_INDEX.length-1));
    }
    // Background image
    BigImagePanel imgBase = new BigImagePanel(planet, true,"Enemy turn");
    this.setLayout(new BorderLayout());
    
    InvisiblePanel invis = new InvisiblePanel(imgBase);    
    invis.setLayout(new BoxLayout(invis, BoxLayout.Y_AXIS));
    invis.add(Box.createRigidArea(new Dimension(500, 500)));

    label = new TransparentLabel(invis, "Please wait.....");
    label.setAlignmentX(JComponent.CENTER_ALIGNMENT);

    invis.add(label);
    imgBase.add(invis);
    this.add(imgBase,BorderLayout.CENTER);
    textAnim = 0;

    
  }
  
  public void setText(String text) {
    label.setText(text);
  }
  
  public void updateText() {
    switch (textAnim) {
    case 0: setText("Please wait....."); break;
    case 1: setText("Please wait*...."); break;
    case 2: setText("Please wait.*..."); break;
    case 3: setText("Please wait..*.."); break;
    case 4: setText("Please wait...*."); break;
    case 5: setText("Please wait....*"); break;
    }
    textAnim++;
    if (textAnim > 5) {
      textAnim = 0;
    }
  }

  /**
   * Handle missions for AI player and single fleet
   * @param fleet Fleet for doing the missing
   * @param info PlayerInfo
   */
  private void handleMissions(Fleet fleet, PlayerInfo info) {
    Mission mission = info.getMissions().getMissionForFleet(fleet.getName());
    if (mission != null) {
      if (mission.getType() == MissionType.EXPLORE) {
        if (mission.getPhase() == MissionPhase.TREKKING && fleet.getRoute() == null) {
          // Fleet has encounter obstacle, taking a detour round it
          Sun sun = game.getStarMap().locateSolarSystem(fleet.getX(), fleet.getY());
          if (sun != null && sun.getName() == mission.getSunName()) {
            // Fleet is in correct solar system, starting explore execution mode
            mission.setPhase(MissionPhase.EXECUTING);
            fleet.setaStarSearch(null);
          } else if (fleet.getaStarSearch() == null) {
            // No A star search made yet, so let's do it
            AStarSearch search = new AStarSearch(game.getStarMap(), 
                fleet.getX(), fleet.getY(), mission.getX(), mission.getY(), 7);
            search.doSearch();
            search.doRoute();
            fleet.setaStarSearch(search);
            for (int mv = 0;mv<fleet.movesLeft;mv++) {
              PathPoint point = search.getMove();
              if (!game.getStarMap().isBlocked(point.getX(), point.getY())) {
                //   Not blocked so fleet is moving
                game.fleetMakeMove(info, fleet, point.getX(), point.getY());
                search.nextMove();
              }
            }
            fleet.movesLeft = 0;
            if (search.isLastMove()) {
              fleet.setRoute(new Route(fleet.getX(), fleet.getY(), 
                  mission.getX(), mission.getY(), fleet.getFleetFtlSpeed()));
            }
          } else {
            AStarSearch search = fleet.getaStarSearch();
            for (int mv = 0;mv<fleet.movesLeft;mv++) {
              PathPoint point = search.getMove();
              if (!game.getStarMap().isBlocked(point.getX(), point.getY())) {
              //   Not blocked so fleet is moving
                game.fleetMakeMove(info, fleet, point.getX(), point.getY());
                search.nextMove();
              }
            }
            fleet.movesLeft = 0;
            if (search.isLastMove()) {
              fleet.setRoute(new Route(fleet.getX(), fleet.getY(), 
                  mission.getX(), mission.getY(), fleet.getFleetFtlSpeed()));
            }
          }
        } 
        if (mission.getPhase() == MissionPhase.EXECUTING) {
          //FIXME Not done yet
          if (fleet.getaStarSearch() == null) {
            Sun sun = game.getStarMap().locateSolarSystem(fleet.getX(), fleet.getY());
            PathPoint point = info.getUnchartedSector(sun, fleet);
            if (point != null) {
              mission.setTarget(point.getX(), point.getY());
              AStarSearch search = new AStarSearch(game.getStarMap(), 
                  fleet.getX(), fleet.getY(), mission.getX(), mission.getY());
              search.doSearch();
              search.doRoute();
              fleet.setaStarSearch(search);
              for (int mv = 0;mv<fleet.movesLeft;mv++) {
                point = search.getMove();
                if (point != null) {
                  if (!game.getStarMap().isBlocked(point.getX(), point.getY())) {
                  //   Not blocked so fleet is moving
                    game.fleetMakeMove(info, fleet, point.getX(), point.getY());
                    search.nextMove();
                  }
                }
              }
              fleet.movesLeft = 0;
              if (search.isLastMove()) {
                fleet.setaStarSearch(null);
              }
            }
          } else {
            AStarSearch search = fleet.getaStarSearch();
            for (int mv = 0;mv<fleet.movesLeft;mv++) {
              PathPoint point = search.getMove();
              if (point != null) {

                if (!game.getStarMap().isBlocked(point.getX(), point.getY())) {
                //   Not blocked so fleet is moving
                  game.fleetMakeMove(info, fleet, point.getX(), point.getY());
                  search.nextMove();
                }
              }
            }
            fleet.movesLeft = 0;
            if (search.isLastMove()) {
              fleet.setaStarSearch(null);
            }

          }
        }
      } // End Of Explore
    } else {
      // No mission for fleet yet
      if (fleet.isScoutFleet()) {
        // Scout fleet should go to explore
        // FIXME: Now selecting nearest Solar system
        Sun sun = game.getStarMap().getNearestSolarSystem(fleet.getX(), 
            fleet.getY());
        mission = new Mission(MissionType.EXPLORE, 
            MissionPhase.TREKKING, sun.getCenterX(), sun.getCenterY());
        mission.setFleetName(fleet.getName());
        mission.setSunName(sun.getName());
        info.getMissions().add(mission);
        fleet.setRoute(new Route(fleet.getX(), fleet.getY(), 
            mission.getX(), mission.getY(), fleet.getFleetFtlSpeed()));
      }
    }
    
  }

  /**
   * Merge fleet with in same space and starting with same fleet names
   * @param fleet Fleet where to merge
   * @param info PlayerInfo for both fleets
   */
  private void mergeFleets(Fleet fleet, PlayerInfo info) {
    // Merging fleets
    String[] part = fleet.getName().split("#");          
    for (int j=0;j<info.Fleets().getNumberOfFleets();j++) {
      // Merge fleets in same space with same starting of fleet name
      Fleet mergeFleet = info.Fleets().getByIndex(j);
      if (mergeFleet != fleet && mergeFleet.getX() == fleet.getX() &&
          mergeFleet.getY() == fleet.getY() && mergeFleet.getName()
          .startsWith(part[0])) {
        for (int k=0;k<mergeFleet.getNumberOfShip();k++) {
          Ship ship = mergeFleet.getShipByIndex(k);
          if (ship != null) {
            fleet.addShip(ship);
          }
        }
        info.Fleets().remove(j);
        break;
      }
    }    
  }

  
  /**
   * Handle single AI Fleet. If fleet was last then increase AI turn number
   * and set aiFleet to null.
   */
  public void handleAIFleet() {
    PlayerInfo info = game.players.getPlayerInfoByIndex(game.getStarMap()
        .getAiTurnNumber());
    if (info != null && !info.isHuman() && game.getStarMap().getAIFleet() != null) {
      // Handle fleet
      
      mergeFleets(game.getStarMap().getAIFleet(), info);
      handleMissions(game.getStarMap().getAIFleet(), info);
      game.getStarMap().setAIFleet(info.Fleets().getNext());
      if (info.Fleets().getIndex()==0) {
        game.getStarMap().setAIFleet(null);
        game.getStarMap().setAiTurnNumber(game.getStarMap().getAiTurnNumber()+1);
      }
      
    }

  }

  /**
   * Update whole star map to next turn
   */
  public void updateStarMapToNextTurn() {
    game.getStarMap().resetCulture();
    for (int i=0;i<game.players.getCurrentMaxPlayers();i++) {
      PlayerInfo info = game.players.getPlayerInfoByIndex(i);
      if (info != null) {
        info.resetVisibilityDataAfterTurn();
        info.getMsgList().clearMessages();
        for (int j = 0;j<info.Fleets().getNumberOfFleets();j++) {
          Fleet fleet = info.Fleets().getByIndex(j);
          if (fleet.getRoute() != null) {
            fleet.getRoute().makeNextMove();
            if (!game.getStarMap().isBlocked(fleet.getRoute().getX(), 
                fleet.getRoute().getY())) {
              // Not blocked so fleet is moving
              game.fleetMakeMove(info, fleet, fleet.getRoute().getX(), fleet.getRoute().getY());
              if (fleet.getRoute().isEndReached()) {
                // End is reached giving a message
                fleet.setRoute(null);
                Message msg = new Message(MessageType.FLEET,
                  fleet.getName()+" has reached it's target and waiting for orders.",
                  Icons.getIconByName(Icons.ICON_HULL_TECH));
                msg.setMatchByString(fleet.getName());
                msg.setCoordinate(fleet.getX(), fleet.getY());
                info.getMsgList().addNewMessage(msg);
              }
            } else {
              // Movement was blocked, giving a message
              fleet.setRoute(null);
              Message msg = new Message(MessageType.FLEET,
                fleet.getName()+" has encouter obstacle and waiting for more orders.",
                Icons.getIconByName(Icons.ICON_HULL_TECH));
              msg.setMatchByString(fleet.getName());
              msg.setCoordinate(fleet.getX(), fleet.getY());
              info.getMsgList().addNewMessage(msg);
            }
          } else {
            Message msg = new Message(MessageType.FLEET,
                fleet.getName()+" is waiting for orders.",
                Icons.getIconByName(Icons.ICON_HULL_TECH));
            msg.setMatchByString(fleet.getName());
            msg.setCoordinate(fleet.getX(), fleet.getY());
            info.getMsgList().addNewMessage(msg);
          }
          fleet.movesLeft = fleet.getFleetSpeed();
          game.getStarMap().doFleetScanUpdate(info, fleet,null);
        }
      }
    }
    for (int i=0;i<game.getStarMap().getPlanetList().size();i++) {
      Planet planet = game.getStarMap().getPlanetList().get(i);
      if (planet.getPlanetPlayerInfo() != null) {
        PlayerInfo info = planet.getPlanetPlayerInfo();
        planet.updateOneTurn();
        int index =game.players.getIndex(info);
        if (index > -1) {
          game.getStarMap().calculateCulture(planet.getX(), planet.getY(), 
              planet.getCulture(), index);
        }
        game.getStarMap().doFleetScanUpdate(info, null,planet);
      }
    }
    game.getStarMap().setTurn(game.getStarMap().getTurn()+1);
  }

  
  /**
   * Handle actions for AI Turn view
   * Since AI Turn View can be null while handling the all the AI. AI handling
   * variables are stored in StarMap. These variables are AIFleet and AITurnNumber.
   * @param arg0 ActionEvent
   */
  public void handleActions(ActionEvent arg0) {
    if (arg0.getActionCommand().equalsIgnoreCase(
        GameCommands.COMMAND_ANIMATION_TIMER) ) {
      updateText();
      if (game.getStarMap().getAIFleet() == null) {
        game.getStarMap().handleAIResearchAndPlanets();
      } else {
        handleAIFleet();
      }
      if (game.getStarMap().isAllAIsHandled()) {
        updateStarMapToNextTurn();
        for (int i=0;i<game.players.getCurrentMaxPlayers();i++) {
          PlayerInfo info = game.players.getPlayerInfoByIndex(i);
          info.getTechList().updateResearchPointByTurn(game.getStarMap().
             getTotalProductionByPlayerPerTurn(Planet.PRODUCTION_RESEARCH, i),info);
        }
        game.getStarMap().clearAITurn();
        game.changeGameState(GameState.STARMAP);

      }
    }

  }


}
