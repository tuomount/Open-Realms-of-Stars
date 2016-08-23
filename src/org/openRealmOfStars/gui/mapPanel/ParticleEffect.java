package org.openRealmOfStars.gui.mapPanel;

import java.awt.Color;

import org.openRealmOfStars.gui.GuiStatics;
import org.openRealmOfStars.utilities.DiceGenerator;

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
 * Particle effect for combat effects to be more interesting
 * 
 */

public class ParticleEffect {
  
  /**
   * Particle X coordinate
   */
  private double x;
  /**
   * Particle Y coordinate
   */
  private double y;
  
  /**
   * Particle Movement vector X part
   */
  private double mx;

  /**
   * Particle Movement vector Y part
   */
  private double my;
  
  /**
   * Particle time to live, how many animation rounds
   */
  private int ttl;
  
  /**
   * Particle effect type
   */
  private ParticleEffectType type;
  
  /**
   * Effect color
   */
  private Color color;

  /**
   * Create a new particle effect with default movement vectors
   * @param type Particle Effect Type
   * @param x X coordinate in pixels
   * @param y Y coordinate in pixels
   */
  public ParticleEffect(ParticleEffectType type, int x, int y) {
    this.x = x;
    this.y = y;
    this.type  = type;
    switch (this.type) {
    case EXPLOSION_PARTICLE: {
      mx = DiceGenerator.getRandom(5, 50);
      mx = mx/10;
      my = DiceGenerator.getRandom(5, 50);
      my = my/10;
      if (DiceGenerator.getRandom(1)==0) {
        mx = mx*-1;
      }
      if (DiceGenerator.getRandom(1)==0) {
        my = my*-1;
      }
      ttl = DiceGenerator.getRandom(15, 30);
      color = GuiStatics.EXPLOSION_COLORS[DiceGenerator.getRandom(
          GuiStatics.EXPLOSION_COLORS.length-1)];
      break;
    }
    case LASER_PARTICLE: {
      mx = 0;
      my = 0;
      ttl = 2;
      color = GuiStatics.BEAM_COLORS[DiceGenerator.getRandom(
          GuiStatics.BEAM_COLORS.length-1)];
      break;
    }
    case PHOTON_TORP_PARTICILE: {
      color = new Color(132,255,0);
      mx = DiceGenerator.getRandom(5, 20);
      mx = mx/10;
      my = DiceGenerator.getRandom(5, 20);
      my = my/10;
      if (DiceGenerator.getRandom(1)==0) {
        mx = mx*-1;
      }
      if (DiceGenerator.getRandom(1)==0) {
        my = my*-1;
      }
      ttl = 10;
      break;
    }
    case MISSILE_FIRE: {
      ttl = 30;
      color = new Color(255,255-3*(30-ttl),0);
      mx = 0;
      my = 0;
      break;
    }
    }
  }
  
  /**
   * Set new movement vector
   * @param mx X part of movement vector
   * @param my Y part of movement vector
   */
  public void setMovementVector(double mx, double my) {
    this.mx = mx;
    this.my = my;
  }
  
  /**
   * Handle particle effect animation and time to live
   */
  public void handleParticle() {
    x = x+mx;
    y = y+my;
    if (ttl > 0) {
      ttl--;
    }
    if (type == ParticleEffectType.MISSILE_FIRE) {
      color = new Color(255,255-3*(30-ttl),0);
    }
  }
  
  public int getX() {
    return (int) Math.round(x);
  }

  public int getY() {
    return (int) Math.round(y);
  }

  public int getTtl() {
    return ttl;
  }

  public ParticleEffectType getType() {
    return type;
  }

  public Color getColor() {
    return color;
  }
  
  

}
