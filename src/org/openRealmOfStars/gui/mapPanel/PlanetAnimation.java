package org.openRealmOfStars.gui.mapPanel;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.openRealmOfStars.gui.icons.AnimatedImage;
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
 * Class for handling planet animation during the planet bombing view. Contains
 * pixel positions animation frames and so on.
 * 
 */
public class PlanetAnimation {

  
  /**
   * Current X coordinate in pixel
   */
  private double sx;
  /**
   * Current Y coordinate in pixel
   */
  private double sy;
  
  /**
   * End X coordinate in pixel
   */
  private double ex;
  /**
   * End Y coordinate in pixel
   */
  private double ey;
  
  /**
   * speed for X
   */
  private double mx;
  
  /**
   * Speed for Y
   */
  private double my;
  
  /**
   * Generic count value for how long animation is shown
   */
  private int count;
  
  /**
   * Animation frame number
   */
  private int animFrame;
  
  /**
   * Show animation
   */
  private boolean showAnim;
  
  /**
   * List of particle effects
   */
  private List<ParticleEffect> particles;
  
  /**
   * Beam weapon anim count
   */
  private static final int BEAM_ANIM_COUNT = 40;
  
  /**
   * Original distance
   */
  private int distance;
  

  private AnimatedImage explosionAnim;
  
  /**
   * Combat Animation
   * @param shooter Ship who shot
   * @param target Ship who took the shot
   * @param weapon Weapon which was used for shooting
   * @param hit  1 Not even a dent
   *             0 Armor or shield damaged
   *            -1 Component damage
   *            -2 Destoryed
   */
  public PlanetAnimation(int animationType, int sx, int sy, int ex, int ey) {
    this.sx = sx;
    this.sy = sy;
    this.ex = ex;
    this.ey = ey;
    double dx = Math.abs(sx-ex);
    double dy = Math.abs(sy-ey);
    distance = (int) dy;
    if (dx > dy) {
      distance = (int) dx;
    }
    if (distance > 0) {
      mx = (ex-sx)/distance;
      my = (ey-sy)/distance;
    } else {
      mx = 0;
      my = 0;
    }
    animFrame = 0;
    showAnim = false;
    particles = new ArrayList<>();
    count = 40;
  }
  

  /**
   * Is animation ended
   * @return True if animation has finished
   */
  public boolean isAnimationFinished() {
    if (count == 0) {
      return true;
    }
    return false;
  }

  /**
   * Do animation update
   */
  public void doAnimation() {
    ListIterator<ParticleEffect> particleIte = particles.listIterator(0);
    while (particleIte.hasNext()) {
      ParticleEffect particle = particleIte.next();
      if (particle.getTtl() == 0) {
        particleIte.remove();
      } else {
        particle.handleParticle();
      }
    }
    count--;
    int parts = DiceGenerator.getRandom(5, 15);
    for (int i=0;i<parts;i++) {
      int dist = DiceGenerator.getRandom(distance);
      int px = (int) Math.round(dist*mx+sx);
      int py = (int) Math.round(dist*my+sy);
      int nx = DiceGenerator.getRandom(5);
      int ny = DiceGenerator.getRandom(5);
      if (DiceGenerator.getRandom(1)==0) {
        nx = nx*-1;
      }
      if (DiceGenerator.getRandom(1)==0) {
        ny = ny*-1;
      }
      px = px+nx;
      py = py+ny;
      ParticleEffect particle = new ParticleEffect(ParticleEffectType.LASER_PARTICLE, px, py);
      particles.add(particle);
    }
  }
  
  /**
   * Get Animation frame if it should be shown. Otherwise it is null
   * @return BufferedImage
   */
  public BufferedImage getAnimFrame() {
    if (showAnim) {
      return explosionAnim.getFrame(animFrame);
    }
    return null;
  }
  
  public List<ParticleEffect> getParticles() {
    return particles;
  }
  
  public Color getBeamColor() {
    return new Color(255-2*(BEAM_ANIM_COUNT-count), 2*count, 2*count);
  }
  
  public int getSx() {
    return (int) Math.round(sx);
  }


  public int getSy() {
    return (int) Math.round(sy);
  }


  public int getEx() {
    return (int) Math.round(ex);
  }


  public int getEy() {
    return (int) Math.round(ey);
  }

}
