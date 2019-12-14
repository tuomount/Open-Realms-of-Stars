package org.openRealmOfStars.game.tutorial;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.experimental.categories.Category;
/**
*
* Open Realm of Stars game project
* Copyright (C) 2019 Tuomo Untinen
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
* Test tutorial list class.
*
*/
public class TutorialListTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCorrectOrder() {
    TutorialList list = new TutorialList();
    HelpLine line = new HelpLine(0);
    list.add(line);
    assertEquals(1, list.getSize());
    line = new HelpLine(1);
    list.add(line);
    assertEquals(2, list.getSize());
    line = new HelpLine(2);
    list.add(line);
    assertEquals(3, list.getSize());
    line = new HelpLine(3);
    list.add(line);
    assertEquals(4, list.getSize());
    line = new HelpLine(4);
    list.add(line);
    assertEquals(5, list.getSize());
    assertEquals("0:  - \n" + 
        "1:  - \n" + 
        "2:  - \n" + 
        "3:  - \n" + 
        "4:  - \n", list.toString());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testWrongOrder() {
    TutorialList list = new TutorialList();
    HelpLine line = new HelpLine(4);
    list.add(line);
    line = new HelpLine(3);
    list.add(line);
    line = new HelpLine(2);
    list.add(line);
    line = new HelpLine(1);
    list.add(line);
    line = new HelpLine(0);
    list.add(line);
    assertEquals("0:  - \n" + 
        "1:  - \n" + 
        "2:  - \n" + 
        "3:  - \n" + 
        "4:  - \n", list.toString());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testWrongOrderSkipIndexes() {
    TutorialList list = new TutorialList();
    HelpLine line = new HelpLine(8);
    list.add(line);
    line = new HelpLine(6);
    list.add(line);
    line = new HelpLine(4);
    list.add(line);
    line = new HelpLine(2);
    list.add(line);
    line = new HelpLine(0);
    list.add(line);
    assertEquals("0:  - \n" + 
        "2:  - \n" + 
        "4:  - \n" + 
        "6:  - \n" + 
        "8:  - \n", list.toString());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCorrectOrderSkipIndexes() {
    TutorialList list = new TutorialList();
    HelpLine line = new HelpLine(0);
    list.add(line);
    line = new HelpLine(2);
    list.add(line);
    line = new HelpLine(4);
    list.add(line);
    line = new HelpLine(6);
    list.add(line);
    line = new HelpLine(8);
    list.add(line);
    assertEquals("0:  - \n" + 
        "2:  - \n" + 
        "4:  - \n" + 
        "6:  - \n" + 
        "8:  - \n", list.toString());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testFirstIndex() {
    TutorialList list = new TutorialList();
    assertEquals(null, list.get(0));
    HelpLine line = new HelpLine(0);
    list.add(line);
    assertEquals(line, list.get(0));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testWrongOrderSkipIndexesGet() {
    TutorialList list = new TutorialList();
    HelpLine line = new HelpLine(8);
    list.add(line);
    line = new HelpLine(6);
    list.add(line);
    line = new HelpLine(4);
    list.add(line);
    line = new HelpLine(2);
    list.add(line);
    line = new HelpLine(0);
    list.add(line);
    assertEquals("0:  - \n" + 
        "2:  - \n" + 
        "4:  - \n" + 
        "6:  - \n" + 
        "8:  - \n", list.toString());
    assertEquals(0, list.get(0).getIndex());
    assertEquals(2, list.get(1).getIndex());
    assertEquals(4, list.get(2).getIndex());
    assertEquals(6, list.get(3).getIndex());
    assertEquals(8, list.get(4).getIndex());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testWrongOrderReplace() {
    TutorialList list = new TutorialList();
    HelpLine line = new HelpLine(8);
    list.add(line);
    line = new HelpLine(6);
    list.add(line);
    line = new HelpLine(4);
    list.add(line);
    line = new HelpLine(8);
    list.add(line);
    line = new HelpLine(4);
    list.add(line);
    line = new HelpLine(3);
    list.add(line);
    assertEquals("3:  - \n" + 
        "4:  - \n" + 
        "6:  - \n" + 
        "8:  - \n", list.toString());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testImmediateReplace() {
    TutorialList list = new TutorialList();
    HelpLine line = new HelpLine(8);
    list.add(line);
    line = new HelpLine(8);
    list.add(line);
    assertEquals("8:  - \n", list.toString()); 
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testOddOrderGetIndex() {
    TutorialList list = new TutorialList();
    assertEquals(null, list.getByIndex(42));
    HelpLine line = new HelpLine(25);
    list.add(line);
    line = new HelpLine(16);
    list.add(line);
    line = new HelpLine(21);
    list.add(line);
    line = new HelpLine(42);
    list.add(line);
    line = new HelpLine(12);
    list.add(line);
    assertEquals("12:  - \n" + 
        "16:  - \n" + 
        "21:  - \n" + 
        "25:  - \n" + 
        "42:  - \n", list.toString());
    assertEquals(42, list.getByIndex(42).getIndex());
    assertEquals(25, list.getByIndex(25).getIndex());
    assertEquals(21, list.getByIndex(21).getIndex());
    assertEquals(16, list.getByIndex(16).getIndex());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testShownIndexList() {
    TutorialList list = new TutorialList();
    HelpLine line = new HelpLine(5);
    line.setShown(false);
    list.add(line);
    line = new HelpLine(7);
    line.setShown(true);
    list.add(line);
    line = new HelpLine(2);
    line.setShown(true);
    list.add(line);
    line = new HelpLine(3);
    line.setShown(false);
    list.add(line);
    ArrayList<Integer> indexes = list.getShownIndexes();
    assertEquals(2, indexes.size());
    assertEquals(2, indexes.get(0).intValue());
    assertEquals(7, indexes.get(1).intValue());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testUpdateShownIndexList() {
    TutorialList list = new TutorialList();
    HelpLine line = new HelpLine(5);
    list.add(line);
    line = new HelpLine(7);
    list.add(line);
    line = new HelpLine(2);
    list.add(line);
    line = new HelpLine(3);
    list.add(line);
    line = new HelpLine(12);
    list.add(line);
    line = new HelpLine(19);
    list.add(line);
    line = new HelpLine(11);
    list.add(line);
    ArrayList<Integer> indexes = new ArrayList<>();
    Integer value = new Integer(7);
    indexes.add(value);
    value = new Integer(13);
    indexes.add(value);
    value = new Integer(12);
    indexes.add(value);
    value = new Integer(11);
    indexes.add(value);
    list.updateShownTutorial(indexes);
    assertEquals(false, list.getByIndex(2).isShown());
    assertEquals(false, list.getByIndex(3).isShown());
    assertEquals(false, list.getByIndex(5).isShown());
    assertEquals(true, list.getByIndex(7).isShown());
    assertEquals(true, list.getByIndex(12).isShown());
    assertEquals(true, list.getByIndex(11).isShown());
    assertEquals(null, list.getByIndex(13));
    assertEquals(false, list.getByIndex(19).isShown());
  }

}
