package org.openRealmOfStars.gui.mapPanel;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.mapTiles.anomaly.AnomalyType;
import org.openRealmOfStars.mapTiles.anomaly.SpaceAnomaly;
import org.openRealmOfStars.player.combat.Combat;

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
* Class for handling popups on map panel
*
*/
public class PopupPanelTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    PopupPanel panel = new PopupPanel("Test", "Test title");
    BufferedImage image = Mockito.mock(BufferedImage.class);
    assertEquals("Test", panel.getText());
    assertEquals(null, panel.getImage());
    panel.setImage(image);
    assertEquals(image, panel.getImage());
    panel.setText("Testing");
    assertEquals("Testing", panel.getText());
    assertEquals("Test title", panel.getTitle());
    panel.setTitle("Another");
    assertEquals("Another", panel.getTitle());
    assertEquals(false, panel.isDismissed());
    panel.dismiss();
    assertEquals(true, panel.isDismissed());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSplitting() {
    PopupPanel panel = new PopupPanel("This is just text to try out"
        + " popup panel text drawing capabilities. There are no line"
        + " changes here at all. Popup panel should automatically"
        + " handle line changes without no hickup. Mostly this text"
        + " is just text flow without any purpose. This could basicly"
        + " contain whatever content, but it needs to be polite and"
        + " correct. This is the reason I did not want to copy paste"
        + " random lorem ipsum text from the internet. Plus I would"
        + " need to check the license for that. This was just easier.",
        "Test title");
    String[] rows = panel.splitText(400, 10);
    assertEquals(13, rows.length);
    assertEquals("This is just text to try out popup panel", rows[0]);
    assertEquals("text drawing capabilities. There are no", rows[1]);
    assertEquals("line changes here at all. Popup panel", rows[2]);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSpaceAnomalyCredit() {
    BufferedImage image = Mockito.mock(BufferedImage.class);
    SpaceAnomaly anomaly = Mockito.mock(SpaceAnomaly.class);
    Mockito.when(anomaly.getType()).thenReturn(AnomalyType.CREDIT);
    Mockito.when(anomaly.getImage()).thenReturn(image);
    Mockito.when(anomaly.getText()).thenReturn("Test");
    PopupPanel panel = new PopupPanel(anomaly);
    assertEquals("Test", panel.getText());
    assertEquals(image, panel.getImage());
    assertEquals("Credit cache!", panel.getTitle());
    assertEquals(null, panel.getCombat());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSpaceAnomalyDSA() {
    BufferedImage image = Mockito.mock(BufferedImage.class);
    SpaceAnomaly anomaly = Mockito.mock(SpaceAnomaly.class);
    Mockito.when(anomaly.getType()).thenReturn(AnomalyType.DEEP_SPACE_ANCHOR);
    Mockito.when(anomaly.getImage()).thenReturn(image);
    Mockito.when(anomaly.getText()).thenReturn("Test");
    PopupPanel panel = new PopupPanel(anomaly);
    assertEquals("Test", panel.getText());
    assertEquals(image, panel.getImage());
    assertEquals("Deep space anchor!", panel.getTitle());
    assertEquals(null, panel.getCombat());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSpaceAnomalyTech() {
    BufferedImage image = Mockito.mock(BufferedImage.class);
    SpaceAnomaly anomaly = Mockito.mock(SpaceAnomaly.class);
    Mockito.when(anomaly.getType()).thenReturn(AnomalyType.TECH);
    Mockito.when(anomaly.getImage()).thenReturn(image);
    Mockito.when(anomaly.getText()).thenReturn("Test");
    PopupPanel panel = new PopupPanel(anomaly);
    assertEquals("Test", panel.getText());
    assertEquals(image, panel.getImage());
    assertEquals("Tech discovered!", panel.getTitle());
    assertEquals(null, panel.getCombat());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSpaceAnomalyLair() {
    BufferedImage image = Mockito.mock(BufferedImage.class);
    SpaceAnomaly anomaly = Mockito.mock(SpaceAnomaly.class);
    Mockito.when(anomaly.getType()).thenReturn(AnomalyType.LAIR);
    Mockito.when(anomaly.getImage()).thenReturn(image);
    Mockito.when(anomaly.getText()).thenReturn("Test");
    Combat combat = Mockito.mock(Combat.class);
    Mockito.when(anomaly.getCombat()).thenReturn(combat);
    PopupPanel panel = new PopupPanel(anomaly);
    assertEquals("Test", panel.getText());
    assertEquals(image, panel.getImage());
    assertEquals("Pirate station found!", panel.getTitle());
    assertEquals(combat, panel.getCombat());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSpaceAnomalyPirate() {
    BufferedImage image = Mockito.mock(BufferedImage.class);
    SpaceAnomaly anomaly = Mockito.mock(SpaceAnomaly.class);
    Mockito.when(anomaly.getType()).thenReturn(AnomalyType.PIRATE);
    Mockito.when(anomaly.getImage()).thenReturn(image);
    Mockito.when(anomaly.getText()).thenReturn("Test");
    Combat combat = Mockito.mock(Combat.class);
    Mockito.when(anomaly.getCombat()).thenReturn(combat);
    PopupPanel panel = new PopupPanel(anomaly);
    assertEquals("Test", panel.getText());
    assertEquals(image, panel.getImage());
    assertEquals("Pirate ship encoutered!", panel.getTitle());
    assertEquals(combat, panel.getCombat());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSpaceAnomalyMonster() {
    BufferedImage image = Mockito.mock(BufferedImage.class);
    SpaceAnomaly anomaly = Mockito.mock(SpaceAnomaly.class);
    Mockito.when(anomaly.getType()).thenReturn(AnomalyType.MONSTER);
    Mockito.when(anomaly.getImage()).thenReturn(image);
    Mockito.when(anomaly.getText()).thenReturn("Test");
    Combat combat = Mockito.mock(Combat.class);
    Mockito.when(anomaly.getCombat()).thenReturn(combat);
    PopupPanel panel = new PopupPanel(anomaly);
    assertEquals("Test", panel.getText());
    assertEquals(image, panel.getImage());
    assertEquals("Monster encoutered!", panel.getTitle());
    assertEquals(combat, panel.getCombat());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSpaceAnomalyMap() {
    BufferedImage image = Mockito.mock(BufferedImage.class);
    SpaceAnomaly anomaly = Mockito.mock(SpaceAnomaly.class);
    Mockito.when(anomaly.getType()).thenReturn(AnomalyType.MAP);
    Mockito.when(anomaly.getImage()).thenReturn(image);
    Mockito.when(anomaly.getText()).thenReturn("Test");
    PopupPanel panel = new PopupPanel(anomaly);
    assertEquals("Test", panel.getText());
    assertEquals(image, panel.getImage());
    assertEquals("Old probe found!", panel.getTitle());
    assertEquals(null, panel.getCombat());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSpaceAnomalyShip() {
    BufferedImage image = Mockito.mock(BufferedImage.class);
    SpaceAnomaly anomaly = Mockito.mock(SpaceAnomaly.class);
    Mockito.when(anomaly.getType()).thenReturn(AnomalyType.SHIP);
    Mockito.when(anomaly.getImage()).thenReturn(image);
    Mockito.when(anomaly.getText()).thenReturn("Test");
    PopupPanel panel = new PopupPanel(anomaly);
    assertEquals("Test", panel.getText());
    assertEquals(image, panel.getImage());
    assertEquals("Ship with crew!", panel.getTitle());
    assertEquals(null, panel.getCombat());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSpaceAnomalyWormhole() {
    BufferedImage image = Mockito.mock(BufferedImage.class);
    SpaceAnomaly anomaly = Mockito.mock(SpaceAnomaly.class);
    Mockito.when(anomaly.getType()).thenReturn(AnomalyType.WORMHOLE);
    Mockito.when(anomaly.getImage()).thenReturn(image);
    Mockito.when(anomaly.getText()).thenReturn("Test");
    PopupPanel panel = new PopupPanel(anomaly);
    assertEquals("Test", panel.getText());
    assertEquals(image, panel.getImage());
    assertEquals("Space anomaly!", panel.getTitle());
    assertEquals(null, panel.getCombat());
  }

}
