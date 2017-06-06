package org.openRealmOfStars.player.diplomacy;

import java.util.ArrayList;

import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.player.tech.TechList;
import org.openRealmOfStars.player.tech.TechType;
import org.openRealmOfStars.starMap.StarMap;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2017 Tuomo Untinen
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
 * Diplomatic Trade between two players. This class is able to
 * tell players if trade is good for them or not. Also this
 * class is able to generate trade for AI player.
 *
 */
public class DiplomaticTrade {

  /**
   * Starmap object containing all player infos and map infos.
   */
  private StarMap starMap;

  /**
   * Index for First player.
   */
  private int first;

  /**
   * Index for second player.
   */
  private int second;

  /**
   * Tech List for first player
   */
  private ArrayList<Tech> techListForFirst;

  /**
   * Tech List for second player
   */
  private ArrayList<Tech> techListForSecond;

  /**
   * Constructor for Diplomatic trade.
   * @param map Starmap
   * @param index1 First player index
   * @param index2 Second player index
   * @throws IllegalArgumentException if player indexes are out of bounds.
   */
  public DiplomaticTrade(final StarMap map, final int index1,
      final int index2) throws IllegalArgumentException {
    starMap = map;
    first = index1;
    second = index2;
    int max = starMap.getPlayerList().getCurrentMaxPlayers();
    if (first >= max || first < 0) {
      throw new IllegalArgumentException("First player index out of bounds!");
    }
    if (second >= max || second < 0) {
      throw new IllegalArgumentException("Second player index out of bounds!");
    }
  }

  /**
   * Generate Tech list containing techs whichs are tradeable.
   */
  private void generateTechList() {
    techListForFirst = new ArrayList<>();
    techListForSecond = new ArrayList<>();
    for (int type = 0; type < 6; type++) {
      for (int lvl = 1; lvl < 11; lvl++) {
        PlayerInfo info = starMap.getPlayerByIndex(first);
        if (!info.getTechList()
            .isTechListForLevelFull(TechType.getTypeByIndex(type), lvl)) {
              Tech[] tradeTechs = starMap.getPlayerByIndex(second)
                  .getTechList().getListForTypeAndLevel(TechType
                      .getTypeByIndex(type), lvl);
              Tech[] ownTechs = starMap.getPlayerByIndex(first)
                  .getTechList().getListForTypeAndLevel(TechType
                      .getTypeByIndex(type), lvl);
              Tech[] techs = TechList.getTechDifference(tradeTechs, ownTechs);
              for (Tech tech : techs) {
                techListForFirst.add(tech);
              }
        }
        info = starMap.getPlayerByIndex(second);
        if (!info.getTechList()
            .isTechListForLevelFull(TechType.getTypeByIndex(type), lvl)) {
              Tech[] tradeTechs = starMap.getPlayerByIndex(first)
                  .getTechList().getListForTypeAndLevel(TechType
                      .getTypeByIndex(type), lvl);
              Tech[] ownTechs = starMap.getPlayerByIndex(second)
                  .getTechList().getListForTypeAndLevel(TechType
                      .getTypeByIndex(type), lvl);
              Tech[] techs = TechList.getTechDifference(tradeTechs, ownTechs);
              for (Tech tech : techs) {
                techListForSecond.add(tech);
              }
            }
      }
    }
  }

  /**
   * Get tradeable tech list for player according the tech known
   * by the second player.
   * @return Array of Tech. Can be empty.
   */
  public Tech[] getTradeableTechListForFirst() {
    if (techListForFirst == null) {
      generateTechList();
    }
    return techListForFirst.toArray(new Tech[techListForFirst.size()]);
  }

  /**
   * Get tradeable tech list for player according the tech known
   * by the first player.
   * @return Array of Tech. Can be empty.
   */
  public Tech[] getTradeableTechListForSecond() {
    if (techListForSecond == null) {
      generateTechList();
    }
    return techListForSecond.toArray(new Tech[techListForSecond.size()]);
  }

  /**
   * Get First Player index
   * @return First player index
   */
  public int getFirstIndex() {
    return first;
  }

  /**
   * Get Second Player index
   * @return Second player index
   */
  public int getSecondIndex() {
    return second;
  }

}
