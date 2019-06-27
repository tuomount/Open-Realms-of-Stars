package org.openRealmOfStars.player.fleet;

import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.starMap.planet.Planet;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2018,2019  Tuomo Untinen
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
* Trade route calculation for fleet
*
*/
public class TradeRoute {

  /**
   * Origin world where fleet left
   */
  private Planet originWorld;
  /**
   * Trade world where actual trade is about to happen
   */
  private Planet tradeWorld;

  /**
   * Realm who is trading
   */
  private PlayerInfo trader;

  /**
   * Total trade value
   */
  private int tradeValue;

  /**
   * Trade route for estimating trade route values
   * @param originWorld Origin world where fleet left
   * @param tradeWorld Trade world where trade is about to happen
   * @param trader Realm who is trading
   * @param fleet Trade fleet
   */
  public TradeRoute(final Planet originWorld, final Planet tradeWorld,
      final PlayerInfo trader, final Fleet fleet) {
    this.originWorld = originWorld;
    this.tradeWorld = tradeWorld;
    this.trader = trader;
    tradeValue = 0;
    double dist = fleet.getCoordinate().calculateDistance(
        this.originWorld.getCoordinate());
    if (dist <= 1) {
      tradeValue = fleet.calculateTrade(this.tradeWorld.getCoordinate());
      if (tradeValue > 1) {
        tradeValue = tradeValue / 2;
      }
      tradeValue = tradeValue + this.trader.getGovernment().getTradeBonus();
      if (tradeValue > 0 && this.trader.getRace() == SpaceRace.SCAURIANS) {
        tradeValue = tradeValue * 3 / 2;
      }
    } else {
      dist = fleet.getCoordinate().calculateDistance(
          this.tradeWorld.getCoordinate());
      if (dist <= 1) {
        tradeValue = fleet.calculateTrade(this.originWorld.getCoordinate());
        if (tradeValue > 1) {
          tradeValue = tradeValue / 2;
        }
        tradeValue = tradeValue + this.trader.getGovernment().getTradeBonus();
        if (tradeValue > 0 && this.trader.getRace() == SpaceRace.SCAURIANS) {
          tradeValue = tradeValue * 3 / 2;
        }
      }
    }

  }

  /**
   * Get trade value of trade route.
   * This is estimation of trade value.
   * @return Trade value
   */
  public int getTradeValue() {
    return tradeValue;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(originWorld.getName());
    sb.append(" <-> ");
    sb.append(tradeWorld.getName());
    sb.append(" ");
    sb.append(tradeValue);
    sb.append(" credit");
    if (tradeValue > 1) {
      sb.append("s");
    }
    return sb.toString();
  }

  /**
   * Get the origin world where fleet will start.
   * @return Origin world
   */
  public Planet getOriginWorld() {
    return originWorld;
  }

  /**
   * Get the trade world where fleet will do the trade.
   * @return Trade world
   */
  public Planet getTradeWorld() {
    return tradeWorld;
  }

}
