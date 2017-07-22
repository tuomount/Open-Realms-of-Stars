package org.openRealmOfStars.starMap.newsCorp;

import static org.junit.Assert.*;

import org.junit.Test;

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
* Image instruction class.
*
*/
public class ImageInstructionTest {

  @Test
  public void testBackground() {
    ImageInstruction instruction = new ImageInstruction();
    instruction.addBackground(ImageInstruction.BACKGROUND_GREY_GRADIENT);
    assertEquals("background(grey gradient)", instruction.toString());
    assertEquals("background(grey gradient)", instruction.build());
    instruction.addBackground(ImageInstruction.BACKGROUND_BLACK);
    assertEquals("background(grey gradient)+background(black)", instruction.build());
  }

  @Test
  public void testBackgroundAndText() {
    ImageInstruction instruction = new ImageInstruction();
    instruction.addBackground(ImageInstruction.BACKGROUND_STARS);
    instruction.addText("Test called background and(+) Text");
    assertEquals("background(stars)+text(Test called background and Text)", instruction.build());
  }

  @Test
  public void testBackgroundAndPlanet() {
    ImageInstruction instruction = new ImageInstruction();
    instruction.addBackground(ImageInstruction.BACKGROUND_STARS);
    instruction.addPlanet(ImageInstruction.POSITION_CENTER, ImageInstruction.PLANET_IRONWORLD1);
    assertEquals("background(stars)+planet(position center,ironworld1)", instruction.build());
  }

  @Test
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
