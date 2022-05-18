package org.openRealmOfStars.utilities;

import java.io.File;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Random;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016,2018,2020,2022  Tuomo Untinen
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
public final class DiceGenerator {

  /**
   * Hiding the constructor
   */
  private DiceGenerator() {
    // Nothing to do
  }

  /**
   * Is generator initialized or not
   */
  private static boolean initialized = false;
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
  private static int mz;
  /**
   * Second seed of MultiplyWithCarry generator
   */
  private static int mw;
  /**
   * Seed of XORShift generator
   */
  private static long x;

  /**
   * Static numbers to generate
   */
  private static int[] numbers;
  /**
   * Number index
   */
  private static int numberIndex;

  /**
   * Get random number between 0 and maxValue.
   * This generates random by combining Java RNG, MultiplyWithCarry
   * and XORShift.
   * @param maxValue inclusive
   * @return A random number
   */
  public static int getRandom(final int maxValue) {
    initializeGenerators();
    return getRandomResult(maxValue);
  }

  /**
   * Get Java envinronment safely
   * @param param Envinronment parameter
   * @return Envinronment parameter or default value.
   */
  private static String getEnvSafely(final String param) {
    try {
      return System.getenv(param);
    } catch (SecurityException e) {
      ErrorLogger.log(e);
    }
    return param + " not available";
  }
  /**
   * Gather Envinronment data. This data will be run through
   * digest function if any of digest function is available.
   * This should be good for random generator starting value.
   * @return Digested envinronment data.
   */
  private static byte[] gatherEnvData() {
    StringBuilder sb = new StringBuilder();
    long startTime = System.nanoTime();
    sb.append(startTime);
    for (File file : File.listRoots()) {
      try {
        sb.append(file.getAbsolutePath());
        sb.append(": ");
        sb.append(file.getFreeSpace());
        sb.append("/");
        sb.append(file.getTotalSpace());
      } catch (SecurityException e) {
        ErrorLogger.log(e);
      }
    }
    sb.append(getEnvSafely("PATH"));
    try {
      Map<String, String> map = System.getenv();
      sb.append(map.toString());
    } catch (SecurityException e) {
      ErrorLogger.log(e);
    }
    sb.append("CPUs: " + Runtime.getRuntime().availableProcessors());
    sb.append("Free memory: " + Runtime.getRuntime().freeMemory());
    sb.append("Total memory: " + Runtime.getRuntime().totalMemory());
    sb.append("Max memory: " + Runtime.getRuntime().maxMemory());
    long endTime = System.nanoTime();
    sb.append(endTime);
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      md.update(sb.toString().getBytes(StandardCharsets.UTF_8));
      return md.digest();
    } catch (NoSuchAlgorithmException e) {
      try {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(sb.toString().getBytes(StandardCharsets.UTF_8));
        return md.digest();
      } catch (NoSuchAlgorithmException e1) {
        try {
          MessageDigest md = MessageDigest.getInstance("MD5");
          md.update(sb.toString().getBytes(StandardCharsets.UTF_8));
          return md.digest();
        } catch (NoSuchAlgorithmException e2) {
          ErrorLogger.log("All MessageDigests failed.");
        }
      }
    }
    return sb.toString().getBytes(StandardCharsets.US_ASCII);
  }
  /**
   * Pseudo random function.
   * @param initialValue Initial value for PRF.
   * @param loops How many times PRF is looped.
   * @return PRF value
   */
  private static byte[] loopPrf(final byte[] initialValue, final int loops) {
    byte[] value = initialValue;
    for (int i = 0; i < loops; i++) {
      StringBuilder sb = new StringBuilder();
      sb.append(System.nanoTime());
      sb.append(i);
      sb.append(new String(value, StandardCharsets.UTF_8));
      try {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(sb.toString().getBytes(StandardCharsets.UTF_8));
        value = md.digest();
      } catch (NoSuchAlgorithmException e) {
        try {
          MessageDigest md = MessageDigest.getInstance("SHA-1");
          md.update(sb.toString().getBytes(StandardCharsets.UTF_8));
          value = md.digest();
        } catch (NoSuchAlgorithmException e1) {
          try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sb.toString().getBytes(StandardCharsets.UTF_8));
            value = md.digest();
          } catch (NoSuchAlgorithmException e2) {
            ErrorLogger.log("All MessageDigests failed.");
          }
        }
      }
    }
    return value;
  }
  /**
   * Initialize generator. This only needs to be called once.
   */
  private static void initializeGenerators() {
    if (!initialized) {
      BigInteger big = new BigInteger(gatherEnvData());
      generator1 = new Random(big.longValue());
      big = new BigInteger(loopPrf(gatherEnvData(),
          generator1.nextInt(512) + 512));
      generator2 = new Random(big.longValue());
      mz = big.intValue();
      big = new BigInteger(loopPrf(gatherEnvData(),
          generator1.nextInt(512) + 512));
      mw = big.intValue();
      mw = mw >> 8;
      big = new BigInteger(loopPrf(gatherEnvData(),
        generator1.nextInt(512) + 512));
      x = big.longValue();
      numbers = null;
      initialized = true;
    }
  }

  /**
   * Initialize random seed with fixed values.
   * Initialized multiple generator. Some generators require long
   * for seed and some for integer.
   * @param seed Long seed
   * @param shortSeed seed for integer generators.
   */
  public static void initializeGenerators(final long seed,
      final int shortSeed) {
    generator1 = new Random(seed);
    generator2 = new Random(seed + 1);
    mz = shortSeed;
    mw = shortSeed + 1;
    mw = mw >> 8;
    x = seed;
    initialized = true;
    numbers = null;
  }

  /**
   * Set fixed values for random generators.
   * @param values Fixed values
   */
  public static void initializeGenerators(final int... values) {
    numberIndex = 0;
    numbers = values;
    initialized = true;
  }
  /**
   * Get Random result from three different pseudo random functions
   * @param maxValue inclusive
   * @return Random value
   */
  private static int getRandomResult(final int maxValue) {
    int result = 0;
    if (maxValue < 0) {
      throw new IllegalArgumentException("Negative value for random!");
    }
    if (maxValue == 0) {
      return 0;
    }
    if (numbers == null) {
      switch (generator1.nextInt(3)) {
      case 0:
        result = getRandomJava(maxValue + 1);
        break;
      case 1:
        result = getRandomMultiplyWithCarry(maxValue + 1);
        break;
      case 2:
        result = getRandomXORShift(maxValue + 1);
        break;
      default:
        throw new IllegalArgumentException("Bad behaving PRF!");
      }
    } else {
      result = numbers[numberIndex] % (maxValue + 1);
      numberIndex++;
      if (numberIndex >= numbers.length) {
        // All fixed numbers have been used
        initialized = false;
      }
    }
    return result;
  }

  /**
   * Get random value between minValue and maxValue
   * This generates random by combining Java RNG, MultiplyWithCarry
   * and XORShift.
   * @param minValue inclusive
   * @param maxValue inclusive
   * @return A random number
   */
  public static int getRandom(final int minValue, final int maxValue) {
    initializeGenerators();
    int sub = 0;
    if (maxValue >= minValue) {
      sub = maxValue - minValue;
    } else {
      throw new IllegalArgumentException("Max value is bigger than min value!");
    }
    int result = getRandomResult(sub);
    result = result + minValue;
    return result;
  }

  /** Get random with using java's random
   * @param maxValue exclusive
   * @return int
   */
  private static int getRandomJava(final int maxValue) {
    int result = generator2.nextInt(maxValue);
    return result;

  }

  /**
   * Multiply carry multiplier 1
   */
  private static final int MC_MULTIPLIER1 = 36969;

  /**
   * Multiply carry multiplier 2
   */
  private static final int MC_MULTIPLIER2 = 18000;

  /**
   * Mask for lower 16 bits.
   */
  private static final int MASK_FOR_LOWER_16BITS = 65535;

  /**
   * 16 bits
   */
  private static final int BIT16 = 16;

  /**
   * Get random with Multiply Carry
   * @param maxValue exclusive
   * @return A random number
   */
  private static int getRandomMultiplyWithCarry(final int maxValue) {
    mz = MC_MULTIPLIER1 * (mz & MASK_FOR_LOWER_16BITS) + (mz >> BIT16);
    mw = MC_MULTIPLIER2 * (mw & MASK_FOR_LOWER_16BITS) + (mw >> BIT16);
    int i = (mz << BIT16) + mw; /* 32-bit result */
    i = Math.abs(i);
    return i % maxValue;
  }

  /**
   * Magic number 1 for XOR shift
   */
  private static final int XOR_SHIFT_MAGIC1 = 21;

  /**
   * Magic number 2 for XOR shift
   */
  private static final int XOR_SHIFT_MAGIC2 = 35;
  /**
   * Get random with XORShift function
   * @param maxValue exclusive
   * @return int
   */
  private static int getRandomXORShift(final int maxValue) {
    x ^= x << XOR_SHIFT_MAGIC1;
    x ^= x >>> XOR_SHIFT_MAGIC2;
    x ^= x << 4;
    int i = (int) x;
    i = Math.abs(i);
    i = i % maxValue;
    return i;
  }

}
