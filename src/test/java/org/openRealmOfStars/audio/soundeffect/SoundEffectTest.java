package org.openRealmOfStars.audio.soundeffect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import javax.sound.sampled.AudioFormat;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

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
 * Test for SoundEffect class
 */

public class SoundEffectTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSoundEffect() {
    AudioFormat format = Mockito.mock(AudioFormat.class);
    byte[] data = {0x00,0x00,0x00};
    SoundEffect effect = new SoundEffect(format, "Test", data);
    assertEquals(format, effect.getFormat());
    assertEquals("Test",effect.getName());
    if (data.length == effect.getData().length) {
      for (int i=0;i<data.length;i++) {
        assertEquals(data[i],effect.getData()[i]);
      }
    } else {
      assertFalse(false);
    }

  }

}
