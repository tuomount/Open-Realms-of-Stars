package org.openRealmOfStars.starMap.newsCorp;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017,2018  Tuomo Untinen
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
* Image instruction class.
*
*/
public class ImageInstructionTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBackground() {
    ImageInstruction instruction = new ImageInstruction();
    instruction.addBackground(ImageInstruction.BACKGROUND_GREY_GRADIENT);
    assertEquals("background(grey gradient)", instruction.toString());
    assertEquals("background(grey gradient)", instruction.build());
    instruction.addBackground(ImageInstruction.BACKGROUND_BLACK);
    assertEquals("background(grey gradient)+background(black)", instruction.build());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testBackgroundBlackDraw() {
    ImageInstruction instruction = new ImageInstruction();
    instruction.addBackground(ImageInstruction.BACKGROUND_BLACK);
    BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_4BYTE_ABGR);
    image = ImageInstruction.parseImageInstructions(image, instruction.build());
    assertEquals(100, image.getWidth());
    assertEquals(100, image.getHeight());
    assertEquals(Color.BLACK.getRGB(), image.getRGB(5, 5));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testBackgroundStarsDraw() {
    ImageInstruction instruction = new ImageInstruction();
    instruction.addBackground(ImageInstruction.BACKGROUND_STARS);
    BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_4BYTE_ABGR);
    image = ImageInstruction.parseImageInstructions(image, instruction.build());
    assertEquals(100, image.getWidth());
    assertEquals(100, image.getHeight());
    assertEquals(-16579836, image.getRGB(5, 5));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testBackgroundNebulaeDraw() {
    ImageInstruction instruction = new ImageInstruction();
    instruction.addBackground(ImageInstruction.BACKGROUND_NEBULAE);
    BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_4BYTE_ABGR);
    image = ImageInstruction.parseImageInstructions(image, instruction.build());
    assertEquals(100, image.getWidth());
    assertEquals(100, image.getHeight());
    assertEquals(-15594676, image.getRGB(5, 5));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testBackgroundGradientDraw() {
    ImageInstruction instruction = new ImageInstruction();
    instruction.addBackground(ImageInstruction.BACKGROUND_GREY_GRADIENT);
    BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_4BYTE_ABGR);
    image = ImageInstruction.parseImageInstructions(image, instruction.build());
    assertEquals(100, image.getWidth());
    assertEquals(100, image.getHeight());
    assertEquals(Color.BLACK.getRGB(), image.getRGB(49, 0));
    assertEquals(-14211289, image.getRGB(49, 99));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBackgroundAndText() {
    ImageInstruction instruction = new ImageInstruction();
    instruction.addBackground(ImageInstruction.BACKGROUND_STARS);
    instruction.addText("Test called background and(+) Text");
    assertEquals("background(stars)+text(Test called background and Text)", instruction.build());
  }
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBackgroundAndRelationSymbol() {
    ImageInstruction instruction = new ImageInstruction();
    instruction.addBackground(ImageInstruction.BACKGROUND_STARS);
    instruction.addRelationSymbol(ImageInstruction.PEACE);
    assertEquals("background(stars)+relation_symbol(peace)", instruction.build());
    instruction = new ImageInstruction();
    instruction.addBackground(ImageInstruction.BACKGROUND_STARS);
    instruction.addRelationSymbol(ImageInstruction.WAR);
    assertEquals("background(stars)+relation_symbol(war)", instruction.build());
    instruction = new ImageInstruction();
    instruction.addBackground(ImageInstruction.BACKGROUND_STARS);
    instruction.addRelationSymbol(ImageInstruction.TRADE_ALLIANCE);
    assertEquals("background(stars)+relation_symbol(trade alliance)", instruction.build());
    instruction = new ImageInstruction();
    instruction.addBackground(ImageInstruction.BACKGROUND_STARS);
    instruction.addRelationSymbol(ImageInstruction.ALLIANCE);
    assertEquals("background(stars)+relation_symbol(alliance)", instruction.build());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testTextDraw() {
    ImageInstruction instruction = new ImageInstruction();
    instruction.addBackground(ImageInstruction.BACKGROUND_BLACK);
    instruction.addText("Liirum laarum leerum laarum");
    BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_4BYTE_ABGR);
    image = ImageInstruction.parseImageInstructions(image, instruction.build());
    assertEquals(100, image.getWidth());
    assertEquals(100, image.getHeight());
    assertEquals(Color.BLACK.getRGB(), image.getRGB(49, 0));
    assertEquals(-10956033, image.getRGB(50, 15));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testTextRelationPeace() {
    ImageInstruction instruction = new ImageInstruction();
    instruction.addBackground(ImageInstruction.BACKGROUND_BLACK);
    instruction.addRelationSymbol(ImageInstruction.PEACE);
    BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_4BYTE_ABGR);
    image = ImageInstruction.parseImageInstructions(image, instruction.build());
    assertEquals(100, image.getWidth());
    assertEquals(100, image.getHeight());
    assertEquals(Color.BLACK.getRGB(), image.getRGB(49, 49));
    assertEquals(-8405247, image.getRGB(50, 15));
  }
  
  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testTextRelationWar() {
    ImageInstruction instruction = new ImageInstruction();
    instruction.addBackground(ImageInstruction.BACKGROUND_BLACK);
    instruction.addRelationSymbol(ImageInstruction.WAR);
    BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_4BYTE_ABGR);
    image = ImageInstruction.parseImageInstructions(image, instruction.build());
    assertEquals(100, image.getWidth());
    assertEquals(100, image.getHeight());
    assertEquals(Color.BLACK.getRGB(), image.getRGB(49, 49));
    assertEquals(-2432036, image.getRGB(50, 30));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testTextRelationTradeAlliance() {
    ImageInstruction instruction = new ImageInstruction();
    instruction.addBackground(ImageInstruction.BACKGROUND_BLACK);
    instruction.addRelationSymbol(ImageInstruction.TRADE_ALLIANCE);
    BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_4BYTE_ABGR);
    image = ImageInstruction.parseImageInstructions(image, instruction.build());
    assertEquals(100, image.getWidth());
    assertEquals(100, image.getHeight());
    assertEquals(Color.BLACK.getRGB(), image.getRGB(49, 49));
    assertEquals(-8034783, image.getRGB(50, 30));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testTextRelationAlliance() {
    ImageInstruction instruction = new ImageInstruction();
    instruction.addBackground(ImageInstruction.BACKGROUND_BLACK);
    instruction.addRelationSymbol(ImageInstruction.ALLIANCE);
    BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_4BYTE_ABGR);
    image = ImageInstruction.parseImageInstructions(image, instruction.build());
    assertEquals(100, image.getWidth());
    assertEquals(100, image.getHeight());
    assertEquals(Color.BLACK.getRGB(), image.getRGB(49, 49));
    assertEquals(-14935272, image.getRGB(49, 29));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testTextPlanet() {
    ImageInstruction instruction = new ImageInstruction();
    instruction.addBackground(ImageInstruction.BACKGROUND_BLACK);
    instruction.addPlanet(ImageInstruction.POSITION_LEFT, ImageInstruction.PLANET_ROCK1,
        ImageInstruction.SIZE_FULL);
    BufferedImage image = new BufferedImage(800, 400, BufferedImage.TYPE_4BYTE_ABGR);
    image = ImageInstruction.parseImageInstructions(image, instruction.build());
    assertEquals(800, image.getWidth());
    assertEquals(400, image.getHeight());
    assertEquals(Color.BLACK.getRGB(), image.getRGB(700, 200));
    assertNotEquals(Color.BLACK.getRGB(), image.getRGB(100, 200));

    instruction = new ImageInstruction();
    instruction.addBackground(ImageInstruction.BACKGROUND_BLACK);
    instruction.addPlanet(ImageInstruction.POSITION_LEFT, ImageInstruction.PLANET_GASGIANT1,
        ImageInstruction.SIZE_HALF);
    image = new BufferedImage(800, 400, BufferedImage.TYPE_4BYTE_ABGR);
    image = ImageInstruction.parseImageInstructions(image, instruction.build());
    assertEquals(800, image.getWidth());
    assertEquals(400, image.getHeight());
    assertEquals(Color.BLACK.getRGB(), image.getRGB(700, 200));
    assertNotEquals(Color.BLACK.getRGB(), image.getRGB(100, 200));

    instruction = new ImageInstruction();
    instruction.addBackground(ImageInstruction.BACKGROUND_BLACK);
    instruction.addPlanet(ImageInstruction.POSITION_LEFT, ImageInstruction.PLANET_GASGIANT2,
        ImageInstruction.SIZE_HALF);
    image = new BufferedImage(800, 400, BufferedImage.TYPE_4BYTE_ABGR);
    image = ImageInstruction.parseImageInstructions(image, instruction.build());
    assertEquals(800, image.getWidth());
    assertEquals(400, image.getHeight());
    assertEquals(Color.BLACK.getRGB(), image.getRGB(700, 200));
    assertNotEquals(Color.BLACK.getRGB(), image.getRGB(100, 200));

    instruction = new ImageInstruction();
    instruction.addBackground(ImageInstruction.BACKGROUND_BLACK);
    instruction.addPlanet(ImageInstruction.POSITION_RIGHT, ImageInstruction.PLANET_IRONWORLD1,
        ImageInstruction.SIZE_FULL);
    image = new BufferedImage(800, 400, BufferedImage.TYPE_4BYTE_ABGR);
    image = ImageInstruction.parseImageInstructions(image, instruction.build());
    assertEquals(800, image.getWidth());
    assertEquals(400, image.getHeight());
    assertEquals(Color.BLACK.getRGB(), image.getRGB(100, 200));
    assertNotEquals(Color.BLACK.getRGB(), image.getRGB(700, 200));

    instruction = new ImageInstruction();
    instruction.addBackground(ImageInstruction.BACKGROUND_BLACK);
    instruction.addPlanet(ImageInstruction.POSITION_RIGHT, ImageInstruction.PLANET_IRONWORLD2,
        ImageInstruction.SIZE_FULL);
    image = new BufferedImage(800, 400, BufferedImage.TYPE_4BYTE_ABGR);
    image = ImageInstruction.parseImageInstructions(image, instruction.build());
    assertEquals(800, image.getWidth());
    assertEquals(400, image.getHeight());
    assertEquals(Color.BLACK.getRGB(), image.getRGB(100, 200));
    assertNotEquals(Color.BLACK.getRGB(), image.getRGB(700, 200));

    instruction = new ImageInstruction();
    instruction.addBackground(ImageInstruction.BACKGROUND_BLACK);
    instruction.addPlanet(ImageInstruction.POSITION_CENTER, ImageInstruction.PLANET_WATERWORLD1,
        ImageInstruction.SIZE_FULL);
    image = new BufferedImage(800, 400, BufferedImage.TYPE_4BYTE_ABGR);
    image = ImageInstruction.parseImageInstructions(image, instruction.build());
    assertEquals(800, image.getWidth());
    assertEquals(400, image.getHeight());
    assertEquals(Color.BLACK.getRGB(), image.getRGB(0, 0));
    assertNotEquals(Color.BLACK.getRGB(), image.getRGB(400, 200));

    instruction = new ImageInstruction();
    instruction.addBackground(ImageInstruction.BACKGROUND_BLACK);
    instruction.addPlanet(ImageInstruction.POSITION_CENTER, ImageInstruction.PLANET_WATERWORLD2,
        ImageInstruction.SIZE_FULL);
    image = new BufferedImage(800, 400, BufferedImage.TYPE_4BYTE_ABGR);
    image = ImageInstruction.parseImageInstructions(image, instruction.build());
    assertEquals(800, image.getWidth());
    assertEquals(400, image.getHeight());
    assertEquals(Color.BLACK.getRGB(), image.getRGB(0, 0));
    assertNotEquals(Color.BLACK.getRGB(), image.getRGB(400, 200));

  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testTextImage() {
    ImageInstruction instruction = new ImageInstruction();
    instruction.addBackground(ImageInstruction.BACKGROUND_BLACK);
    instruction.addImage(SpaceRace.MECHIONS.getNameSingle());
    BufferedImage image = new BufferedImage(800, 400, BufferedImage.TYPE_4BYTE_ABGR);
    image = ImageInstruction.parseImageInstructions(image, instruction.build());
    assertEquals(800, image.getWidth());
    assertEquals(400, image.getHeight());
    assertEquals(Color.BLACK.getRGB(), image.getRGB(0, 0));
    assertNotEquals(Color.BLACK.getRGB(), image.getRGB(402, 200));

    instruction = new ImageInstruction();
    instruction.addBackground(ImageInstruction.BACKGROUND_BLACK);
    instruction.addImage(ImageInstruction.LOGO);
    image = new BufferedImage(800, 400, BufferedImage.TYPE_4BYTE_ABGR);
    image = ImageInstruction.parseImageInstructions(image, instruction.build());
    assertEquals(800, image.getWidth());
    assertEquals(400, image.getHeight());
    assertEquals(Color.BLACK.getRGB(), image.getRGB(0, 0));
    assertNotEquals(Color.BLACK.getRGB(), image.getRGB(405, 200));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBackgroundAndPlanet() {
    ImageInstruction instruction = new ImageInstruction();
    instruction.addBackground(ImageInstruction.BACKGROUND_STARS);
    instruction.addPlanet(ImageInstruction.POSITION_CENTER, ImageInstruction.PLANET_IRONWORLD1,
        ImageInstruction.SIZE_HALF);
    assertEquals("background(stars)+planet(position center,ironworld1,half)", instruction.build());
  }

  @Test(expected=IllegalArgumentException.class)
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBackgroundAndPlanetPositionWeird() {
    ImageInstruction instruction = new ImageInstruction();
    instruction.addBackground(ImageInstruction.BACKGROUND_STARS);
    instruction.addPlanet("Weird", ImageInstruction.PLANET_IRONWORLD1,
        ImageInstruction.SIZE_HALF);
    assertEquals("background(stars)+planet(position center,ironworld1,half)", instruction.build());
  }

  @Test(expected=IllegalArgumentException.class)
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testParserWithNoParameters() {
    BufferedImage image = Mockito.mock(BufferedImage.class);
    ImageInstruction.parseImageInstructions(image, "command");
  }

  @Test(expected=IllegalArgumentException.class)
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBackgroundAndPlanetTypeWeird() {
    ImageInstruction instruction = new ImageInstruction();
    instruction.addBackground(ImageInstruction.BACKGROUND_STARS);
    instruction.addPlanet(ImageInstruction.POSITION_RIGHT, "Weird",
        ImageInstruction.SIZE_HALF);
    assertEquals("background(stars)+planet(position center,ironworld1)", instruction.build());
  }
  @Test(expected=IllegalArgumentException.class)
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBackgroundAndImageWeird() {
    ImageInstruction instruction = new ImageInstruction();
    instruction.addBackground(ImageInstruction.BACKGROUND_STARS);
    instruction.addImage("Weird");
    assertEquals("background(stars)+planet(position center,ironworld1)", instruction.build());
  }
  @Test(expected=IllegalArgumentException.class)
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBackgroundAndSizeWeird() {
    ImageInstruction instruction = new ImageInstruction();
    instruction.addBackground(ImageInstruction.BACKGROUND_STARS);
    instruction.addPlanet(ImageInstruction.POSITION_RIGHT, ImageInstruction.PLANET_GASGIANT1,
        "Weird");
    assertEquals("background(stars)+planet(position center,ironworld1)", instruction.build());
  }
  @Test(expected=IllegalArgumentException.class)
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBackgroundAndWeirdSymbol() {
    ImageInstruction instruction = new ImageInstruction();
    instruction.addBackground(ImageInstruction.BACKGROUND_STARS);
    instruction.addRelationSymbol("weird");
    assertEquals("background(stars)+relation_symbol(weird)", instruction.build());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSanitize() {
    ImageInstruction instruction = new ImageInstruction();
    instruction.addBackground("grad+ient");
    assertEquals("background(gradient)", instruction.toString());
    instruction = new ImageInstruction();
    instruction.addBackground("grad(ient");
    assertEquals("background(gradient)", instruction.toString());
    instruction = new ImageInstruction();
    instruction.addBackground("grad)ient");
    assertEquals("background(gradient)", instruction.toString());
    instruction = new ImageInstruction();
    instruction.addBackground("grad,ient");
  }

}
