package org.openRealmOfStars.player.combat;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.openRealmOfStars.audio.soundeffect.SoundPlayer;
import org.openRealmOfStars.gui.icons.AnimatedImage;
import org.openRealmOfStars.gui.mapPanel.ParticleEffect;
import org.openRealmOfStars.gui.mapPanel.ParticleEffectType;
import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.player.ship.ShipComponent;
import org.openRealmOfStars.player.ship.ShipComponentType;
import org.openRealmOfStars.player.ship.ShipImage;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016-2018  Tuomo Untinen
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
 * Class for handling combat animation during the combat. Contains
 * pixel positions animation frames and so on.
 *
 */
public class CombatAnimation {

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
   * Weapon which was fired
   */
  private ShipComponent weapon;

  /**
   * Was shot hit or miss
   */
  private boolean hit;

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
   * Combat ship which is the target
   */
  private CombatShip target;

  /**
   * Combat ship which is the shooter
   */
  private CombatShip shooter;

  /**
   * Explosion animation for combat animation
   */
  private AnimatedImage explosionAnim;

  /**
   * Is combat animation drawing first time and sound needs to played
   */
  private boolean firstDraw;

  /**
   * Name for explosion sound effect
   */
  private String explosionSfx;

  /**
   * Combat Animation
   * @param shooter Ship who shot
   * @param target Ship who took the shot
   * @param weapon Weapon which was used for shooting
   * @param hit  1 Not even a dent
   *             0 Armor or shield damaged
   *            -1 Component damage
   *            -2 Destroyed
   */
  public CombatAnimation(final CombatShip shooter, final CombatShip target,
      final ShipComponent weapon, final int hit) {
    this.weapon = weapon;
    this.firstDraw = true;
    this.target = target;
    this.shooter = shooter;
    if (hit <= 0) {
      this.hit = true;
    } else {
      this.hit = false;
    }
    sx = shooter.getX() * ShipImage.MAX_WIDTH + ShipImage.MAX_WIDTH / 2;
    sy = shooter.getY() * ShipImage.MAX_HEIGHT + ShipImage.MAX_HEIGHT / 2;
    ex = target.getX() * ShipImage.MAX_WIDTH + ShipImage.MAX_WIDTH / 2;
    ey = target.getY() * ShipImage.MAX_HEIGHT + ShipImage.MAX_HEIGHT / 2;
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
    if (weapon.getType() == ShipComponentType.WEAPON_ECM_TORPEDO) {
      explosionAnim = GuiStatics.EXPLOSION2;
      explosionSfx = SoundPlayer.EXPLOSION_EMP;
    } else if (weapon.getType() == ShipComponentType.PRIVATEERING_MODULE) {
      explosionAnim = GuiStatics.PRIVATEER;
      // TODO Change better sound effect for privateering
      explosionSfx = SoundPlayer.REPAIR;
    } else {
      if (hit == 0) {
        explosionAnim = GuiStatics.EXPLOSION3;
        explosionSfx = SoundPlayer.EXPLOSION_SMALL;
      } else {
        explosionAnim = GuiStatics.EXPLOSION1;
        if (target.getShip().getHullPoints() <= 0) {
          explosionSfx = SoundPlayer.SHIP_EXPLODE;
        } else {
          explosionSfx = SoundPlayer.EXPLOSION;
        }
      }
    }
    switch (weapon.getType()) {
    case WEAPON_BEAM: {
      count = 40;
      break;
    }
    case WEAPON_RAILGUN: {
      count = explosionAnim.getMaxFrames();
      break;
    }
    case WEAPON_ECM_TORPEDO: {
      count = explosionAnim.getMaxFrames();
      break;
    }
    case WEAPON_HE_MISSILE: {
      count = explosionAnim.getMaxFrames();
      break;
    }
    case WEAPON_PHOTON_TORPEDO: {
      count = explosionAnim.getMaxFrames();
      break;
    }
    case PRIVATEERING_MODULE: {
      count = explosionAnim.getMaxFrames();
      break;
    }
    default:
      count = 40;
      break;
    }
  }

  /**
   * Get Target combat ship for animation
   * @return Combat Ship
   */
  public CombatShip getTarget() {
    return target;
  }

  /**
   * Get Shooting combat ship for animation
   * @return Combat Ship
   */
  public CombatShip getShooter() {
    return shooter;
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
   * When there are frames left below this limit animation
   * should start showing the explosion animation. Limit needs to
   * be big enough that whole animation can be shown.
   */
  private static final int FRAME_MARKER_WHEN_EXPLODE = 24;

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
    if (weapon.getType() == ShipComponentType.WEAPON_BEAM) {
      count--;
      if (count < FRAME_MARKER_WHEN_EXPLODE) {
        doAnimationHit(20);
        if (animFrame < explosionAnim.getMaxFrames()) {
          if (animFrame == 0 && hit) {
              SoundPlayer.playSound(explosionSfx);
          }
          animFrame++;
        } else {
          showAnim = false;
        }
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
    } else if (weapon.getType() == ShipComponentType.WEAPON_RAILGUN) {
      if (Math.round(sx) == Math.round(ex)
          && Math.round(sy) == Math.round(ey)) {
        count--;
        doAnimationHit(13);
        if (animFrame < explosionAnim.getMaxFrames()) {
          if (animFrame == 0 && hit) {
            SoundPlayer.playSound(explosionSfx);
          }
          animFrame++;
        } else {
          showAnim = false;
        }
      } else {
        for (int i = 0; i < 10; i++) {
          sx = sx + mx;
          sy = sy + my;
          int px = (int) Math.round(sx);
          int py = (int) Math.round(sy);
          ParticleEffect particle = new ParticleEffect(
              ParticleEffectType.RAILGUN_TRAIL, px, py);
          particles.add(particle);
          if (Math.round(sx) == Math.round(ex)
              && Math.round(sy) == Math.round(ey)) {
            break;
          }
        }
      }
    } else if (weapon.getType() == ShipComponentType.WEAPON_PHOTON_TORPEDO) {
      if (Math.round(sx) == Math.round(ex)
          && Math.round(sy) == Math.round(ey)) {
        count--;
        doAnimationHit(13);
        if (animFrame < explosionAnim.getMaxFrames()) {
          if (animFrame == 0 && hit) {
            SoundPlayer.playSound(explosionSfx);
          }
          animFrame++;
        } else {
          showAnim = false;
        }
      } else {
        for (int i = 0; i < 10; i++) {
          sx = sx + mx;
          sy = sy + my;
          int px = (int) Math.round(sx);
          int py = (int) Math.round(sy);
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
              ParticleEffectType.PHOTON_TORP_PARTICILE, px, py);
          particles.add(particle);
          if (Math.round(sx) == Math.round(ex)
              && Math.round(sy) == Math.round(ey)) {
            break;
          }
        }
      }
    } else if (weapon.getType() == ShipComponentType.WEAPON_ECM_TORPEDO
        || weapon.getType() == ShipComponentType.WEAPON_HE_MISSILE) {
      if (Math.round(sx) == Math.round(ex)
          && Math.round(sy) == Math.round(ey)) {
        count--;
        doAnimationHit(13);
        if (animFrame < explosionAnim.getMaxFrames()) {
          if (animFrame == 0 && hit) {
            SoundPlayer.playSound(explosionSfx);
          }
          animFrame++;
        } else {
          showAnim = false;
        }
      } else {
        for (int i = 0; i < 5; i++) {
          sx = sx + mx;
          sy = sy + my;
          int px = (int) Math.round(sx);
          int py = (int) Math.round(sy);
          ParticleEffect particle = new ParticleEffect(
              ParticleEffectType.MISSILE_FIRE, px, py);
          particles.add(particle);
          if (Math.round(sx) == Math.round(ex)
              && Math.round(sy) == Math.round(ey)) {
            break;
          }
        }
      }
    } else if (weapon.getType() == ShipComponentType.PRIVATEERING_MODULE) {
      count--;
      if (animFrame < explosionAnim.getMaxFrames()) {
        showAnim = true;
        if (animFrame == 0 && hit) {
          SoundPlayer.playSound(explosionSfx);
        }
        animFrame++;
      } else {
        showAnim = false;
      }
    }
  }

  /**
   * Do hit animation with particle effect
   * @param frameWhenAddParticleEffect Add particle after this frame number
   */
  private void doAnimationHit(final int frameWhenAddParticleEffect) {
    if (hit) {
      showAnim = true;
      if (count > frameWhenAddParticleEffect
          && target.getShip().getHullPoints() <= 0) {
        int parts = DiceGenerator.getRandom(15, 25);
        int px = (int) Math.round(ex);
        int py = (int) Math.round(ey);
        for (int i = 0; i < parts; i++) {
          ParticleEffect particle = new ParticleEffect(
              ParticleEffectType.EXPLOSION_PARTICLE, px, py);
          particles.add(particle);
        }
      }
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

  /**
   * Get List of Particle effects
   * @return List of Particle Effects
   */
  public List<ParticleEffect> getParticles() {
    return particles;
  }

  /**
   * Single color channel maximum value
   */
  private static final int COLOR_MAX = 255;

  /**
   * Get Beam color
   * @return Beam color
   */
  public Color getBeamColor() {
    return new Color(COLOR_MAX - 2 * (BEAM_ANIM_COUNT - count), 2 * count,
        2 * count);
  }

  /**
   * Get current Y coordinate in pixels
   * @return Current X coordinate
   */
  public int getSx() {
    return (int) Math.round(sx);
  }

  /**
   * Get current Y coordinate in pixels
   * @return Current Y coordinate
   */
  public int getSy() {
    return (int) Math.round(sy);
  }

  /**
   * End X coordinate in pixels
   * @return End X coordinate
   */
  public int getEx() {
    return (int) Math.round(ex);
  }

  /**
   * End Y coordinate in pixels
   * @return End Y coordinate
   */
  public int getEy() {
    return (int) Math.round(ey);
  }

  /**
   * Get shooters weapon as ship component
   * @return Ship component
   */
  public ShipComponent getWeapon() {
    return weapon;
  }

  /**
   * Was attack actual hit or not
   * @return True if attack hit
   */
  public boolean isHit() {
    return hit;
  }

  /**
   * Is combat animation being drawn first time. This can be
   * used to sync audio to screen.
   * @return True if this if first drawn
   */
  public boolean isFirstDraw() {
    return firstDraw;
  }

  /**
   * Set first draw
   * @param firstDraw False if first drawn has been drawn.
   */
  public void setFirstDraw(final boolean firstDraw) {
    this.firstDraw = firstDraw;
  }

}
