package org.openRealmOfStars.player.combat;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.openRealmOfStars.gui.GuiStatics;
import org.openRealmOfStars.gui.mapPanel.ParticleEffect;
import org.openRealmOfStars.gui.mapPanel.ParticleEffectType;
import org.openRealmOfStars.player.ship.ShipComponent;
import org.openRealmOfStars.player.ship.ShipComponentType;
import org.openRealmOfStars.player.ship.ShipImage;
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
  
  public CombatAnimation(CombatShip shooter, CombatShip target, 
      ShipComponent weapon, boolean hit) {
    this.weapon = weapon;
    this.hit = hit;
    sx = shooter.getX()*ShipImage.MAX_WIDTH+ShipImage.MAX_WIDTH/2;
    sy = shooter.getY()*ShipImage.MAX_HEIGHT+ShipImage.MAX_HEIGHT/2;
    ex = target.getX()*ShipImage.MAX_WIDTH+ShipImage.MAX_WIDTH/2;
    ey = target.getY()*ShipImage.MAX_HEIGHT+ShipImage.MAX_HEIGHT/2;
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
    switch (weapon.getType()) {
    case WEAPON_BEAM: { count = 40; break; }
    case WEAPON_RAILGUN: { count = GuiStatics.EXPLOSION1.getMaxFrames(); break; }
    case WEAPON_ECM_TORPEDO: { count = GuiStatics.EXPLOSION2.getMaxFrames(); break; }
    case WEAPON_HE_MISSILE: { count = GuiStatics.EXPLOSION1.getMaxFrames(); break; }
    case WEAPON_PHOTON_TORPEDO: { count = GuiStatics.EXPLOSION1.getMaxFrames(); break; }
    default:
      count = 40;
      break;
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
    if (weapon.getType() == ShipComponentType.WEAPON_BEAM) {
      count--;
      if (count < 24) {
        if (hit) {
          showAnim = true;
        }
        if (animFrame < GuiStatics.EXPLOSION1.getMaxFrames()) {
          animFrame++;
        } else {
          showAnim = false;
        }
      }
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
    } else if (weapon.getType() == ShipComponentType.WEAPON_RAILGUN) {
      if (Math.round(sx) == Math.round(ex) && Math.round(sy)==Math.round(ey)) {
        count--;
        if (hit) {
          showAnim = true;
        }
        if (animFrame < GuiStatics.EXPLOSION1.getMaxFrames()) {
          animFrame++;
        } else {
          showAnim = false;
        }
      } else {
        for (int i=0;i<10;i++) {
          sx = sx+mx;
          sy = sy+my;
          int px = (int) Math.round(sx);
          int py = (int) Math.round(sy);
          ParticleEffect particle = new ParticleEffect(ParticleEffectType.RAILGUN_TRAIL, px, py);
          particles.add(particle);
          if (Math.round(sx) == Math.round(ex) && Math.round(sy)==Math.round(ey)) {
            break;
          }
        }
      }
    } else if (weapon.getType() == ShipComponentType.WEAPON_PHOTON_TORPEDO) {
      if (Math.round(sx) == Math.round(ex) && Math.round(sy)==Math.round(ey)) {
        count--;
        if (hit) {
          showAnim = true;
        }
        if (animFrame < GuiStatics.EXPLOSION1.getMaxFrames()) {
          animFrame++;
        } else {
          showAnim = false;
        }
      } else {
        for (int i=0;i<10;i++) {
          sx = sx+mx;
          sy = sy+my;
          int px = (int) Math.round(sx);
          int py = (int) Math.round(sy);
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
          ParticleEffect particle = new ParticleEffect(ParticleEffectType.PHOTON_TORP_PARTICILE, px, py);
          particles.add(particle);
          if (Math.round(sx) == Math.round(ex) && Math.round(sy)==Math.round(ey)) {
            break;
          }
        }
      }
    } else if (weapon.getType() == ShipComponentType.WEAPON_ECM_TORPEDO ||
        weapon.getType() == ShipComponentType.WEAPON_HE_MISSILE) {
      if (Math.round(sx) == Math.round(ex) && Math.round(sy)==Math.round(ey)) {
        count--;
        if (hit) {
          showAnim = true;
        }
        if (weapon.getType() == ShipComponentType.WEAPON_ECM_TORPEDO) {
          if (animFrame < GuiStatics.EXPLOSION2.getMaxFrames()) {
            animFrame++;
          } else {
            showAnim = false;
          }
        } else {
          if (animFrame < GuiStatics.EXPLOSION1.getMaxFrames()) {
            animFrame++;
          } else {
            showAnim = false;
          }          
        }
      } else {
        for (int i=0;i<5;i++) {
          sx = sx+mx;
          sy = sy+my;
          int px = (int) Math.round(sx);
          int py = (int) Math.round(sy);
          ParticleEffect particle = new ParticleEffect(ParticleEffectType.MISSILE_FIRE, px, py);
          particles.add(particle);
          if (Math.round(sx) == Math.round(ex) && Math.round(sy)==Math.round(ey)) {
            break;
          }
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
      if (weapon.getType() == ShipComponentType.WEAPON_ECM_TORPEDO) {
        return GuiStatics.EXPLOSION2.getFrame(animFrame);
      }
      return GuiStatics.EXPLOSION1.getFrame(animFrame);
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


  public ShipComponent getWeapon() {
    return weapon;
  }


  public boolean isHit() {
    return hit;
  }

}
