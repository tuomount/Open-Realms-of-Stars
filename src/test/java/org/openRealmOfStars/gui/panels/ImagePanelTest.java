package org.openRealmOfStars.gui.panels;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;

import org.junit.Test;
import org.mockito.Mockito;

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
 * 
 * Image Panel Test
 * 
 */
public class ImagePanelTest {

  @Test
  public void testBasic() {
    BufferedImage picture = Mockito.mock(BufferedImage.class);
    Mockito.when(picture.getWidth()).thenReturn(100);
    Mockito.when(picture.getHeight()).thenReturn(100);
    BufferedImage picture2 = Mockito.mock(BufferedImage.class);
    Mockito.when(picture2.getWidth()).thenReturn(100);
    Mockito.when(picture2.getHeight()).thenReturn(100);
    ImagePanel panel = new ImagePanel(picture);
    assertEquals(picture, panel.getImage());
    assertEquals(null, panel.getText());
    panel.setText("Test");
    assertEquals("Test", panel.getText());
    panel.setImage(picture2);
    assertEquals(picture2, panel.getImage());
    panel.repaint();
  }

}
