package org.openRealmOfStars.utilities;

import java.util.Random;

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
 * Class for getting random values. Contains actually three different
 * Pseudo random functions.
 * 
 */
public class DiceGenerator {

  /**
   * Is generator initialized or not
   */
  private static boolean initialized=false;
  /**
   * First Java's RNG
   */
  private static Random generator1;
  /**
   * Second Java's RNG
   */
  private static Random generator2;
  /**
   * Seed of MultiplyWithCarry generator
   */
  private static int m_z;
  /**
   * Second seed of MultiplyWithCarry generator
   */
  private static int m_w;
  /**
   * Seed of XORShift generator
   */
  private static long x;
  
  /**
   * Get random number between 0 and maxValue.
   * This generates random by combining Java RNG, MultiplyWithCarry
   * and XORShift. 
   * @param maxValue, inclusive
   * @return A random number
   */
  public static int getRandom(int maxValue) {
    if (initialized == false) {
      generator1 = new Random(System.nanoTime());
      generator2 = new Random(generator1.nextLong());
      m_z =(int) System.nanoTime();
      m_w = (int) System.currentTimeMillis();
      m_w = m_w >> 8;
      x = System.nanoTime();
      initialized = true;
    }
    int result = 0;
    switch (getRandomJava(3)) {
    case 0: result = getRandomJava(maxValue+1); break;
    case 1: result = getRandomMultiplyWithCarry(maxValue+1); break;
    case 2: result = getRandomXORShift(maxValue+1); break;
    }
    return result;
  }

  /**
   * Get random value between minValue and maxValue
   * This generates random by combining Java RNG, MultiplyWithCarry
   * and XORShift. 
   * @param minValue, inclusive
   * @param maxValue, inclusive
   * @return A random number
   */
  public static int getRandom(int minValue, int maxValue) {
    if (initialized == false) {
      generator1 = new Random(System.nanoTime());
      generator2 = new Random(generator1.nextLong());
      m_z =(int) System.nanoTime();
      m_w = (int) System.currentTimeMillis();
      m_w = m_w >> 8;
      x = System.nanoTime();
      initialized = true;
    }
    int sub=0;
    int result=0;
    if (maxValue >= minValue) {
      sub = maxValue-minValue;
    }
    switch (getRandomJava(3)) {
    case 0: result = getRandomJava(sub+1); break;
    case 1: result = getRandomMultiplyWithCarry(sub+1); break;
    case 2: result = getRandomXORShift(sub+1); break;
    }
    result = result +minValue;
    return result;
  }

  /** Get random with using java's random
   * @param maxValue, exclusive
   * @return int 
   */
  private static int getRandomJava(int maxValue) {
    int result = generator2.nextInt(maxValue);
    int result2 = generator1.nextInt();
    if ((result2 % 5)==0) {
      generator1 = new Random(System.nanoTime());
      generator2 = new Random(generator1.nextLong());
    }
    return result;
    
  }
  
  /**
   * Get random with Multiply Carry
   * @param maxValue exclusive
   * @return A random number
   */
  private static int getRandomMultiplyWithCarry(int maxValue) {
    m_z = 36969 * (m_z & 65535) + (m_z >> 16);
    m_w = 18000 * (m_w & 65535) + (m_w >> 16);
    int i = (m_z << 16) + m_w;  /* 32-bit result */
    i = Math.abs(i);
    return i % maxValue;
  }
  
  /**
   * Get random with XORShift function
   * @param maxValue exclusive
   * @return int
   */
  private static int getRandomXORShift(int maxValue) {
    x ^= (x << 21);
    x ^= (x >>> 35);
    x ^= (x << 4);
    int i = (int) x;
    i = Math.abs(i);
    i = i % maxValue;
    return i;    
  }
  
}
