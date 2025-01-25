package org.openRealmOfStars.starMap.event;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2024 BottledByte
 * Copyright (C) 2019-2021 Tuomo Untinen
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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.newsCorp.GalaxyStat;
import org.openRealmOfStars.starMap.newsCorp.NewsData;
import org.openRealmOfStars.starMap.newsCorp.NewsFactory;
import org.openRealmOfStars.starMap.newsCorp.scoreBoard.Row;
import org.openRealmOfStars.starMap.newsCorp.scoreBoard.ScoreBoard;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
 * Galaxy event system dispatching and executing events based on player karma.
 */
public class KarmaEvents implements GalaxyEvents {
  /** How are karma events selected/targeted. */
  private KarmaType karmaSelection;

  /** How fast karma increases. Added to karma counters each turn. */
  private int karmaSpeed;
  /** Good karma count. How likely good stuff is goint to happen. */
  private int goodKarmaCount;
  /** Bad karma count. How likely bad stuff is going to happen. */
  private int badKarmaCount;

  /**
   * Create new karma event system
   * @param karmaSelection KarmaType
   * @param karmaSpeed int
   */
  public KarmaEvents(final KarmaType karmaSelection, final int karmaSpeed) {
    this.karmaSelection = karmaSelection;
    this.karmaSpeed = karmaSpeed;
    this.goodKarmaCount = 0;
    this.badKarmaCount = 0;
  }

  /**
   * Deserialize karma event system
   * @param dis DataInputStream
   * @throws IOException Deserialization error
   */
  public KarmaEvents(final DataInputStream dis) throws IOException {
    karmaSelection = KarmaType.getTypeByInt(dis.read());
    karmaSpeed = dis.read();
    goodKarmaCount = dis.readInt();
    badKarmaCount = dis.readInt();
  }

  /**
   * Save to data stream
   * @param dos DataOutputStream
   * @throws IOException Serialization error
   */
  public void save(final DataOutputStream dos) throws IOException {
    dos.writeByte(karmaSelection.getIndex());
    dos.writeByte(karmaSpeed);
    dos.writeInt(goodKarmaCount);
    dos.writeInt(badKarmaCount);
  }

  @Override
  public void handleEvents(final StarMap map) {
    if (karmaSelection == KarmaType.DISABLED) {
      return;
    }

    goodKarmaCount += karmaSpeed;
    badKarmaCount += karmaSpeed;

    handleGoodKarma(map);
    handleBadKarma(map);
  }

  /**
   * Get realm by karma.
   * @param map StarMap
   * @param bad True for bad karma and false for good karma
   * @return PlayerInfo or empty if not able to find realm.
   */
  private PlayerInfo getRealmByKarma(final StarMap map, final boolean bad) {
    GalaxyStat stat = map.getNewsCorpData().generateScores();
    ScoreBoard scoreBoard = new ScoreBoard();
    for (int i = 0; i < stat.getMaxPlayers(); i++) {
      Row row = new Row(stat.getLatest(i), i);
      scoreBoard.add(row);
    }
    scoreBoard.sort();

    int index = -1;
    if (karmaSelection == KarmaType.FIRST_AND_LAST) {
      if (bad) {
        index = scoreBoard.getRow(0).getRealm();
      } else {
        index = scoreBoard.getRow(scoreBoard.getNumberOfRows() - 1).getRealm();
      }
    }
    if (karmaSelection == KarmaType.SECOND_FIRST_AND_LAST) {
      if (bad) {
        index = scoreBoard.getRow(DiceGenerator.getRandom(0, 1)).getRealm();
      } else {
        int minValue = scoreBoard.getNumberOfRows() - 2;
        int maxValue = scoreBoard.getNumberOfRows() - 1;
        index = scoreBoard.getRow(DiceGenerator.getRandom(minValue, maxValue))
            .getRealm();
      }
    }
    if (karmaSelection == KarmaType.BALANCED) {
      int half = scoreBoard.getNumberOfRows() / 2;
      if (bad) {
        index = scoreBoard.getRow(DiceGenerator.getRandom(0,
            half - 1)).getRealm();
      } else {
        int minValue = half;
        int maxValue = scoreBoard.getNumberOfRows() - 1;
        index = scoreBoard.getRow(DiceGenerator.getRandom(minValue, maxValue))
            .getRealm();
      }
    }
    if (karmaSelection == KarmaType.RANDOM) {
      index = DiceGenerator.getRandom(0,
          map.getPlayerList().getCurrentMaxRealms());
    }
    if (karmaSelection == KarmaType.ONLY_GOODS_FOR_LAST) {
      int half = scoreBoard.getNumberOfRows() / 2;
      if (bad) {
        return null;
      }
      int minValue = half;
      int maxValue = scoreBoard.getNumberOfRows() - 1;
      index = scoreBoard.getRow(DiceGenerator.getRandom(minValue, maxValue))
          .getRealm();
    }
    if (karmaSelection == KarmaType.RANDOM_GOOD_ONES) {
      if (bad) {
        return null;
      }
      index = DiceGenerator.getRandom(0,
          map.getPlayerList().getCurrentMaxRealms());
    }

    if (index != -1) {
      return map.getPlayerByIndex(index);
    }
    return null;
  }

  /**
   * Handle bad karma
   * @param map StarMap
   */
  private void handleBadKarma(final StarMap map) {
    int badChance = badKarmaCount / 10;
    if (DiceGenerator.getRandom(1, 100) >= badChance) {
      return;
    }

    PlayerInfo info = getRealmByKarma(map, true);
    if (info == null) {
      return;
    }

    RandomEvent event = createBadRandomEvent(info);
    if (!RandomEventUtility.handleRandomEvent(event, map)) {
      return;
    }

    badKarmaCount /= 2;
    if (event.isNewsWorthy()) {
      var news = NewsFactory.makeRandomEventNews(event, map.getStarYear());
      map.getNewsCorpData().addNews(news);
    }
    info.setRandomEventOccured(event);
  }

  /**
   * Handle good karma
   * @param map StarMap
   */
  private void handleGoodKarma(final StarMap map) {
    int goodChance = goodKarmaCount / 10;
    if (DiceGenerator.getRandom(1, 100) >= goodChance) {
      return;
    }

    PlayerInfo info = getRealmByKarma(map, false);
    if (info == null || info.getRandomEventOccured() != null) {
      return;
    }

    RandomEvent event = createGoodRandomEvent(info);
    if (!RandomEventUtility.handleRandomEvent(event, map)) {
      return;
    }

    goodKarmaCount /= 2;
    if (event.isNewsWorthy()) {
      NewsData news = NewsFactory.makeRandomEventNews(event,
          map.getStarYear());
      map.getNewsCorpData().addNews(news);
    }
    info.setRandomEventOccured(event);
  }

  // TODO: Replace "event selection" methods with WeightedLists
  /**
   * Create good random event.
   * @param realm Realm who is getting the event
   * @return RandomEvent
   */
  private static RandomEvent createGoodRandomEvent(final PlayerInfo realm) {
    var values = Arrays.stream(RandomEventType.values())
        .filter(type -> !type.isBad())
        .collect(Collectors.toList());
    var choice = DiceGenerator.pickRandom(values);
    RandomEvent event = new RandomEvent(choice, realm);
    return event;
  }

  /**
   * Create bad random event.
   * @param realm Realm who is getting the event
   * @return RandomEvent
   */
  private static RandomEvent createBadRandomEvent(final PlayerInfo realm) {
    var values = Arrays.stream(RandomEventType.values())
        .filter(type -> type.isBad())
        .collect(Collectors.toList());
    var choice = DiceGenerator.pickRandom(values);
    RandomEvent event = new RandomEvent(choice, realm);
    return event;
  }
}
