package org.openRealmOfStars.player.combat;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.gui.mapPanel.ParticleEffect;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipComponent;
import org.openRealmOfStars.player.ship.ShipComponentType;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016,2019  Tuomo Untinen
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
 * Test for Combat Animation
 *
 */
public class CombatAnimationTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testLaserBeamWeaponAnimation() {
    CombatShip shooter = Mockito.mock(CombatShip.class);
    CombatShip target = Mockito.mock(CombatShip.class);
    ShipComponent weapon = Mockito.mock(ShipComponent.class);
    Mockito.when(weapon.getType()).thenReturn(ShipComponentType.WEAPON_BEAM);
    Mockito.when(weapon.getName()).thenReturn("Laser Mk1");
    Mockito.when(shooter.getX()).thenReturn(5);
    Mockito.when(shooter.getY()).thenReturn(7);
    Mockito.when(target.getX()).thenReturn(6);
    Mockito.when(target.getY()).thenReturn(6);
    Ship ship = Mockito.mock(Ship.class);
    Mockito.when(ship.getHullPoints()).thenReturn(0);
    Mockito.when(target.getShip()).thenReturn(ship);

    CombatAnimation anim = new CombatAnimation(shooter, target, weapon, -2);
    assertEquals(true,anim.isFirstDraw());
    anim.setFirstDraw(false);
    assertEquals(false,anim.isFirstDraw());
    assertEquals(shooter,anim.getShooter());
    assertEquals(target,anim.getTarget());
    assertEquals(weapon,anim.getWeapon());
    assertEquals(5*64+32,anim.getSx());
    assertEquals(7*64+32,anim.getSy());
    assertEquals(6*64+32,anim.getEx());
    assertEquals(6*64+32,anim.getEy());
    boolean gotParticles = false;
    boolean gotFrame = false;
    while (!anim.isAnimationFinished()) {
      anim.doAnimation();
      List<ParticleEffect> particles = anim.getParticles();
      if (!particles.isEmpty()) {
        gotParticles = true;
      }
      // Just calling the beam color
      anim.getBeamColor();
      if (anim.getAnimFrame() != null) {
        gotFrame = true;
      }
    }
    assertEquals("No particles!",true,gotParticles);
    assertEquals("No frame!",true,gotFrame);
    assertEquals("No Hit",true,anim.isHit());
  }
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testPhasorBeamWeaponAnimation() {
    CombatShip shooter = Mockito.mock(CombatShip.class);
    CombatShip target = Mockito.mock(CombatShip.class);
    ShipComponent weapon = Mockito.mock(ShipComponent.class);
    Mockito.when(weapon.getType()).thenReturn(ShipComponentType.WEAPON_BEAM);
    Mockito.when(weapon.getName()).thenReturn("Phasors Mk1");
    Mockito.when(shooter.getX()).thenReturn(5);
    Mockito.when(shooter.getY()).thenReturn(7);
    Mockito.when(target.getX()).thenReturn(6);
    Mockito.when(target.getY()).thenReturn(6);
    Ship ship = Mockito.mock(Ship.class);
    Mockito.when(ship.getHullPoints()).thenReturn(0);
    Mockito.when(target.getShip()).thenReturn(ship);

    CombatAnimation anim = new CombatAnimation(shooter, target, weapon, -2);
    assertEquals(true,anim.isFirstDraw());
    anim.setFirstDraw(false);
    assertEquals(false,anim.isFirstDraw());
    assertEquals(shooter,anim.getShooter());
    assertEquals(target,anim.getTarget());
    assertEquals(weapon,anim.getWeapon());
    assertEquals(5*64+32,anim.getSx());
    assertEquals(7*64+32,anim.getSy());
    assertEquals(6*64+32,anim.getEx());
    assertEquals(6*64+32,anim.getEy());
    boolean gotParticles = false;
    boolean gotFrame = false;
    while (!anim.isAnimationFinished()) {
      anim.doAnimation();
      List<ParticleEffect> particles = anim.getParticles();
      if (!particles.isEmpty()) {
        gotParticles = true;
      }
      // Just calling the beam color
      anim.getBeamColor();
      if (anim.getAnimFrame() != null) {
        gotFrame = true;
      }
    }
    assertEquals("No particles!",true,gotParticles);
    assertEquals("No frame!",true,gotFrame);
    assertEquals("No Hit",true,anim.isHit());
  }
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testAntimatterBeamWeaponAnimation() {
    CombatShip shooter = Mockito.mock(CombatShip.class);
    CombatShip target = Mockito.mock(CombatShip.class);
    ShipComponent weapon = Mockito.mock(ShipComponent.class);
    Mockito.when(weapon.getType()).thenReturn(ShipComponentType.WEAPON_BEAM);
    Mockito.when(weapon.getName()).thenReturn("Antimatter beam Mk1");
    Mockito.when(shooter.getX()).thenReturn(5);
    Mockito.when(shooter.getY()).thenReturn(7);
    Mockito.when(target.getX()).thenReturn(6);
    Mockito.when(target.getY()).thenReturn(6);
    Ship ship = Mockito.mock(Ship.class);
    Mockito.when(ship.getHullPoints()).thenReturn(0);
    Mockito.when(target.getShip()).thenReturn(ship);

    CombatAnimation anim = new CombatAnimation(shooter, target, weapon, -2);
    assertEquals(true,anim.isFirstDraw());
    anim.setFirstDraw(false);
    assertEquals(false,anim.isFirstDraw());
    assertEquals(shooter,anim.getShooter());
    assertEquals(target,anim.getTarget());
    assertEquals(weapon,anim.getWeapon());
    assertEquals(5*64+32,anim.getSx());
    assertEquals(7*64+32,anim.getSy());
    assertEquals(6*64+32,anim.getEx());
    assertEquals(6*64+32,anim.getEy());
    boolean gotParticles = false;
    boolean gotFrame = false;
    while (!anim.isAnimationFinished()) {
      anim.doAnimation();
      List<ParticleEffect> particles = anim.getParticles();
      if (!particles.isEmpty()) {
        gotParticles = true;
      }
      // Just calling the beam color
      anim.getBeamColor();
      if (anim.getAnimFrame() != null) {
        gotFrame = true;
      }
    }
    assertEquals("No particles!",true,gotParticles);
    assertEquals("No frame!",true,gotFrame);
    assertEquals("No Hit",true,anim.isHit());
  }
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRailWeaponAnimation() {
    CombatShip shooter = Mockito.mock(CombatShip.class);
    CombatShip target = Mockito.mock(CombatShip.class);
    ShipComponent weapon = Mockito.mock(ShipComponent.class);
    Mockito.when(weapon.getType()).thenReturn(ShipComponentType.WEAPON_RAILGUN);
    Mockito.when(weapon.getName()).thenReturn("Railgun Mk1");
    Mockito.when(shooter.getX()).thenReturn(5);
    Mockito.when(shooter.getY()).thenReturn(7);
    Mockito.when(target.getX()).thenReturn(6);
    Mockito.when(target.getY()).thenReturn(6);
    Ship ship = Mockito.mock(Ship.class);
    Mockito.when(ship.getHullPoints()).thenReturn(0);
    Mockito.when(target.getShip()).thenReturn(ship);

    CombatAnimation anim = new CombatAnimation(shooter, target, weapon, -2);
    
    assertEquals(shooter,anim.getShooter());
    assertEquals(target,anim.getTarget());
    assertEquals(weapon,anim.getWeapon());
    assertEquals(5*64+32,anim.getSx());
    assertEquals(7*64+32,anim.getSy());
    assertEquals(6*64+32,anim.getEx());
    assertEquals(6*64+32,anim.getEy());
    boolean gotParticles = false;
    boolean gotFrame = false;
    while (!anim.isAnimationFinished()) {
      anim.doAnimation();
      List<ParticleEffect> particles = anim.getParticles();
      if (!particles.isEmpty()) {
        gotParticles = true;
      }
      // Just calling the beam color
      anim.getBeamColor();
      if (anim.getAnimFrame() != null) {
        gotFrame = true;
      }
    }
    assertEquals("No particles!",true,gotParticles);
    assertEquals("No frame!",true,gotFrame);
    assertEquals("No Hit",true,anim.isHit());
  }
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTorpedoWeaponAnimation() {
    CombatShip shooter = Mockito.mock(CombatShip.class);
    CombatShip target = Mockito.mock(CombatShip.class);
    ShipComponent weapon = Mockito.mock(ShipComponent.class);
    Mockito.when(weapon.getType()).thenReturn(
        ShipComponentType.WEAPON_PHOTON_TORPEDO);
    Mockito.when(weapon.getName()).thenReturn("Photon torpedo Mk1");
    Mockito.when(shooter.getX()).thenReturn(5);
    Mockito.when(shooter.getY()).thenReturn(7);
    Mockito.when(target.getX()).thenReturn(6);
    Mockito.when(target.getY()).thenReturn(6);
    Ship ship = Mockito.mock(Ship.class);
    Mockito.when(ship.getHullPoints()).thenReturn(0);
    Mockito.when(target.getShip()).thenReturn(ship);

    CombatAnimation anim = new CombatAnimation(shooter, target, weapon, -2);
    
    assertEquals(shooter,anim.getShooter());
    assertEquals(target,anim.getTarget());
    assertEquals(weapon,anim.getWeapon());
    assertEquals(5*64+32,anim.getSx());
    assertEquals(7*64+32,anim.getSy());
    assertEquals(6*64+32,anim.getEx());
    assertEquals(6*64+32,anim.getEy());
    boolean gotParticles = false;
    boolean gotFrame = false;
    while (!anim.isAnimationFinished()) {
      anim.doAnimation();
      List<ParticleEffect> particles = anim.getParticles();
      if (!particles.isEmpty()) {
        gotParticles = true;
      }
      // Just calling the beam color
      anim.getBeamColor();
      if (anim.getAnimFrame() != null) {
        gotFrame = true;
      }
    }
    assertEquals("No particles!",true,gotParticles);
    assertEquals("No frame!",true,gotFrame);
    assertEquals("No Hit",true,anim.isHit());
  }
  
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testECMWeaponAnimation() {
    CombatShip shooter = Mockito.mock(CombatShip.class);
    CombatShip target = Mockito.mock(CombatShip.class);
    ShipComponent weapon = Mockito.mock(ShipComponent.class);
    Mockito.when(weapon.getType()).thenReturn(
        ShipComponentType.WEAPON_ECM_TORPEDO);
    Mockito.when(weapon.getName()).thenReturn("ECM missile Mk1");
    Mockito.when(shooter.getX()).thenReturn(5);
    Mockito.when(shooter.getY()).thenReturn(7);
    Mockito.when(target.getX()).thenReturn(6);
    Mockito.when(target.getY()).thenReturn(6);
    Ship ship = Mockito.mock(Ship.class);
    Mockito.when(ship.getHullPoints()).thenReturn(0);
    Mockito.when(target.getShip()).thenReturn(ship);

    CombatAnimation anim = new CombatAnimation(shooter, target, weapon, -2);
    
    assertEquals(shooter,anim.getShooter());
    assertEquals(target,anim.getTarget());
    assertEquals(weapon,anim.getWeapon());
    assertEquals(5*64+32,anim.getSx());
    assertEquals(7*64+32,anim.getSy());
    assertEquals(6*64+32,anim.getEx());
    assertEquals(6*64+32,anim.getEy());
    boolean gotParticles = false;
    boolean gotFrame = false;
    while (!anim.isAnimationFinished()) {
      anim.doAnimation();
      List<ParticleEffect> particles = anim.getParticles();
      if (!particles.isEmpty()) {
        gotParticles = true;
      }
      // Just calling the beam color
      anim.getBeamColor();
      if (anim.getAnimFrame() != null) {
        gotFrame = true;
      }
    }
    assertEquals("No particles!",true,gotParticles);
    assertEquals("No frame!",true,gotFrame);
    assertEquals("No Hit",true,anim.isHit());
  }
  
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testMissileWeaponAnimation() {
    CombatShip shooter = Mockito.mock(CombatShip.class);
    CombatShip target = Mockito.mock(CombatShip.class);
    ShipComponent weapon = Mockito.mock(ShipComponent.class);
    Mockito.when(weapon.getType()).thenReturn(
        ShipComponentType.WEAPON_HE_MISSILE);
    Mockito.when(weapon.getName()).thenReturn("HE Missile Mk1");
    Mockito.when(shooter.getX()).thenReturn(5);
    Mockito.when(shooter.getY()).thenReturn(7);
    Mockito.when(target.getX()).thenReturn(6);
    Mockito.when(target.getY()).thenReturn(6);
    Ship ship = Mockito.mock(Ship.class);
    Mockito.when(ship.getHullPoints()).thenReturn(0);
    Mockito.when(target.getShip()).thenReturn(ship);

    CombatAnimation anim = new CombatAnimation(shooter, target, weapon, -2);
    
    assertEquals(shooter,anim.getShooter());
    assertEquals(target,anim.getTarget());
    assertEquals(weapon,anim.getWeapon());
    assertEquals(5*64+32,anim.getSx());
    assertEquals(7*64+32,anim.getSy());
    assertEquals(6*64+32,anim.getEx());
    assertEquals(6*64+32,anim.getEy());
    boolean gotParticles = false;
    boolean gotFrame = false;
    while (!anim.isAnimationFinished()) {
      anim.doAnimation();
      List<ParticleEffect> particles = anim.getParticles();
      if (!particles.isEmpty()) {
        gotParticles = true;
      }
      // Just calling the beam color
      anim.getBeamColor();
      if (anim.getAnimFrame() != null) {
        gotFrame = true;
      }
    }
    assertEquals("No particles!",true,gotParticles);
    assertEquals("No frame!",true,gotFrame);
    assertEquals("No Hit",true,anim.isHit());
  }

}
