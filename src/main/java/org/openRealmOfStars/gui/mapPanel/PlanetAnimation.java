package org.openRealmOfStars.gui.mapPanel;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.openRealmOfStars.audio.soundeffect.SoundPlayer;
import org.openRealmOfStars.gui.icons.AnimatedImage;
import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016, 2018  Tuomo Untinen
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

  /**
   * Explosion animation
   */
  private AnimatedImage explosionAnim;

  /**
   * Animation type for turret fire
   */
  public static final int ANIMATION_TYPE_TURRET = 1;

  /**
   * Animation type for turret aim
   */
  public static final int ANIMATION_TYPE_AIM = 0;

  /**
   * Animation type for bombing aim
   */
  public static final int ANIMATION_TYPE_BOMBING_AIM = 1;

  /**
   * Animation type for bombing
   */
  public static final int ANIMATION_TYPE_BOMBING = 2;

  /**
   * Animation type for nuke aim
   */
  public static final int ANIMATION_TYPE_NUKE_AIM = 3;

  /**
   * Animation type for dropping nuke
   */
  public static final int ANIMATION_TYPE_NUKING = 4;

  /**
   * Animation type
   */
  private int animationType;

  /**
   * Ship index in BigImagePanel
   */
  private int shipIndex;

  /**
   * Planet animation for turret fire, bombing etc. If animation type is
   * AIM then coordinates can be anything.
   * @param animationType See ANIMATION_TYPEs
   * @param sx Where animation starts X coordinate
   * @param sy Where animation starts Y coordinate
   * @param ex Where animation ends X coordinate
   * @param ey Where animation ends Y coordinate
   */
  public PlanetAnimation(final int animationType, final int sx, final int sy,
      final int ex, final int ey) {
    this.sx = sx;
    this.sy = sy;
    this.ex = ex;
    this.ey = ey;
    double dx = Math.abs(sx - ex);
    double dy = Math.abs(sy - ey);
    distance = (int) dy;
    if (dx > dy) {
      distance = (int) dx;
    }
    if (distance > 0) {
      mx = (ex - sx) / distance;
      my = (ey - sy) / distance;
    } else {
      mx = 0;
      my = 0;
    }
    animFrame = 0;
    showAnim = false;
    particles = new ArrayList<>();
    shipIndex = 0;
    if (animationType == ANIMATION_TYPE_AIM) {
      count = 40;
      explosionAnim = GuiStatics.EXPLOSION1;
    }
    if (animationType == ANIMATION_TYPE_BOMBING_AIM) {
      explosionAnim = GuiStatics.EXPLOSION1;
      count = explosionAnim.getMaxFrames();
    }
    if (animationType == ANIMATION_TYPE_NUKE_AIM) {
      explosionAnim = GuiStatics.EXPLOSION4;
      count = explosionAnim.getMaxFrames() + 10;
    }
    this.setAnimationType(animationType);
  }

  /**
   * Set animation coordinates
  * @param startX Where animation starts X coordinate
  * @param startY Where animation starts Y coordinate
  * @param endX Where animation ends X coordinate
  * @param endY Where animation ends Y coordinate
  */
  public void setCoords(final int startX, final int startY, final int endX,
      final int endY) {
    sx = startX;
    sy = startY;
    ex = endX;
    ey = endY;
    double dx = Math.abs(sx - ex);
    double dy = Math.abs(sy - ey);
    distance = (int) dy;
    if (dx > dy) {
      distance = (int) dx;
    }
    if (distance > 0) {
      mx = (ex - sx) / distance;
      my = (ey - sy) / distance;
    } else {
      mx = 0;
      my = 0;
    }
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
    if (animationType == ANIMATION_TYPE_TURRET) {
      if (count == 39 && animFrame == 0) {
        // Make sound for turret shooting
        SoundPlayer.playSound(SoundPlayer.WEAPON_BEAM);
      }
      int parts = DiceGenerator.getRandom(5, 15);
      for (int i = 0; i < parts; i++) {
        int dist = DiceGenerator.getRandom(distance);
        int px = (int) Math.round(dist * mx + sx);
        int py = (int) Math.round(dist * my + sy);
        int nx = DiceGenerator.getRandom(5);
        int ny = DiceGenerator.getRandom(5);
        if (DiceGenerator.getRandom(1) == 0) {
          nx = nx * -1;
        }
        if (DiceGenerator.getRandom(1) == 0) {
          ny = ny * -1;
        }
        px = px + nx;
        py = py + ny;
        ParticleEffect particle = new ParticleEffect(
            ParticleEffectType.LASER_PARTICLE, px, py);
        particles.add(particle);
      }
      if (count < explosionAnim.getMaxFrames()) {
        showAnim = true;
        if (animFrame == 0) {
          SoundPlayer.playSound(SoundPlayer.EXPLOSION);
        }
        animFrame++;
      }
    }
    if (animationType == ANIMATION_TYPE_BOMBING
        && count < explosionAnim.getMaxFrames()) {
      if (animFrame == 0) {
        SoundPlayer.playSound(SoundPlayer.BOMB);
      }
      showAnim = true;
      animFrame++;
    }
    if (animationType == ANIMATION_TYPE_NUKING) {
      showAnim = true;
      if (animFrame == 0) {
        SoundPlayer.playSound(SoundPlayer.NUKE);
      }
      if (animFrame < explosionAnim.getMaxFrames()) {
        animFrame++;
      } else {
        showAnim = false;
      }
    }
  }

  /**
   * Get Animation frame if it should be shown. Otherwise it is null
   * @return BufferedImage
   */
  public BufferedImage getAnimFrame() {
    if (showAnim && animFrame < explosionAnim.getMaxFrames()) {
      return explosionAnim.getFrame(animFrame);
    }
    return null;
  }

  /**
   * Get list of particle effects
   * @return List of particles
   */
  public List<ParticleEffect> getParticles() {
    return particles;
  }

  /**
   * Color component maximum
   */
  private static final int COLOR_MAX = 255;
  /**
   * Get current beam clor
   * @return Beam color
   */
  public Color getBeamColor() {
    return new Color(COLOR_MAX - 2 * (BEAM_ANIM_COUNT - count), 2 * count,
        2 * count);
  }

  /**
   * Get nuke color for painting whole frame
   * @return nuke color
   */
  public Color getNukeColor() {
    int max = explosionAnim.getMaxFrames();
    int mult = COLOR_MAX / max;
    int opaque = (max - animFrame) * mult;
    if (opaque > COLOR_MAX) {
      opaque = COLOR_MAX;
    }
    return new Color(COLOR_MAX, COLOR_MAX, COLOR_MAX, opaque);
  }

  /**
   * Get start X coordinate
   * @return X coordinate
   */
  public int getSx() {
    return (int) Math.round(sx);
  }

  /**
   * Get start Y coordinate
   * @return Y coordinate
   */
  public int getSy() {
    return (int) Math.round(sy);
  }

  /**
   * Get end X coordinate
   * @return X coordinate
   */
  public int getEx() {
    return (int) Math.round(ex);
  }

  /**
   * Get end Y coordinate
   * @return Y coordinate
   */
  public int getEy() {
    return (int) Math.round(ey);
  }

  /**
   * Get Animation type
   * @return Animation type
   */
  public int getAnimationType() {
    return animationType;
  }

  /**
   * Set animation type
   * @param animationType as int
   */
  public void setAnimationType(final int animationType) {
    this.animationType = animationType;
  }

  /**
   * Get ship index who is attacking or being shot by turret
   * @return Ship index in fleet
   */
  public int getShipIndex() {
    return shipIndex;
  }

  /**
   * Chnage ship index
   * @param shipIndex in fleet
   */
  public void setShipIndex(final int shipIndex) {
    this.shipIndex = shipIndex;
  }

}
