package org.openRealmOfStars.utilities.repository;

import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.diplomacy.Diplomacy;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonus;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusList;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;



/**
*
* Open Realm of Stars game project
* Copyright (C) 2017  Tuomo Untinen
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
* Diplomacy repository class
*
*/
public final class DiplomacyRepository {

  /**
   * Just hiding the constructor
   */
  private DiplomacyRepository() {
    // Nothing to do
  }
  /**
   * Save Diplomacy bonus to Data output Stream.
   * @param dos Data Output Stream
   * @param bonus DiplomacyBonus to write
   * @throws IOException if there is any problem with DataOutputStream
   */
  public static void saveDiplomacyBonus(final DataOutputStream dos,
      final DiplomacyBonus bonus) throws IOException {
    dos.writeByte(bonus.getType().getIndex());
    dos.writeInt(bonus.getBonusValue());
    dos.writeByte(bonus.getBonusLasting());
  }

  /**
   * Load DiplomacyBonus from Data Input Stream.
   * @param dis Data Input Stream
   * @return DiplomacyBonus
   * @throws IOException if reading fails
   */
  public static DiplomacyBonus loadDiplomacyBonus(final DataInputStream dis)
      throws IOException {
    int typeIndex = dis.readInt();
    DiplomacyBonus bonus = new DiplomacyBonus(DiplomacyBonusType
        .getTypeByIndex(typeIndex), SpaceRace.HUMAN);
    bonus.setBonusValue(dis.readInt());
    bonus.setBonusLasting(dis.readInt());
    return bonus;
  }

  /**
   * Save Diplomacy bonus list to Data output Stream.
   * @param dos Data Output Stream
   * @param list DiplomacyBonusList to write
   * @throws IOException if there is any problem with DataOutputStream
   */
  public static void saveDiplomacyBonusList(final DataOutputStream dos,
      final DiplomacyBonusList list) throws IOException {
    if (list == null) {
      dos.writeInt(-1);
    } else {
      dos.writeInt(list.getPlayerIndex());
      dos.writeInt(list.getNumberOfMeetings());
      dos.writeInt(list.getListSize());
      for (int i = 0; i < list.getListSize(); i++) {
        DiplomacyBonus bonus = list.get(i);
        saveDiplomacyBonus(dos, bonus);
      }
    }
  }

  /**
   * Load Diplomacy Bonus list from DataInputStream.
   * @param dis DataInputStream being read
   * @return DiplomacyBonusList
   * @throws IOException if reading fails
   */
  public static DiplomacyBonusList loadDiplomacyBonusList(
      final DataInputStream dis) throws IOException {
    int playerIndex = dis.readInt();
    if (playerIndex == -1) {
      return null;
    }
    DiplomacyBonusList list = new DiplomacyBonusList(playerIndex);
    list.setNumberOfMeetings(dis.readInt());
    int size = dis.readInt();
    for (int i = 0; i < size; i++) {
      DiplomacyBonus bonus = loadDiplomacyBonus(dis);
      list.addBonus(bonus);
    }
    return list;
  }
  /**
   * Save Diplomacy to Data output Stream.
   * @param dos Data Output Stream
   * @param diplomacy Diplomacy to write
   * @throws IOException if there is any problem with DataOutputStream
   */
  public static void saveDiplomacy(final DataOutputStream dos,
      final Diplomacy diplomacy) throws IOException {
    dos.writeByte(diplomacy.getDiplomacySize());
    for (int i = 0; i < diplomacy.getDiplomacySize(); i++) {
      DiplomacyBonusList list = diplomacy.getDiplomacyList(i);
      saveDiplomacyBonusList(dos, list);
    }
  }

  /**
   * Load Diplomacy from DataInputStream.
   * @param dis Data Input Stream
   * @return Diplomacy read from stream
   * @throws IOException if reading fails.
   */
  public static Diplomacy loadDiplomacy(final DataInputStream dis)
      throws IOException {
    int size = dis.readInt();
    Diplomacy diplomacy = new Diplomacy(size);
    for (int i = 0; i < size; i++) {
      DiplomacyBonusList list = loadDiplomacyBonusList(dis);
      if (list != null) {
        diplomacy.setList(list, i);
      }
    }
    return diplomacy;
  }

}
