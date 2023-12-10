package org.openRealmOfStars.utilities;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2023 BottledByte
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
 */

import java.util.ArrayList;
import java.util.Objects;

/**
 * List for randomly selecting values considering their
 * relative weights.
 *
 * <p>
 * The weighted list works as follows:
 * Weight of each entry is added to sum of weight, which is maximum
 * random number the list will handle. Probability of selection is
 * relative to other weights, absolute to the sum of weights.
 * </p>
 * <p>
 * For example, assume there are elements A, B, C with weights 5, 10, 15,
 * sum of weights is 30. It means that:
 * <ol>
 * <li>A will get selected with 5/30 chance (~16.66%)</li>
 * <li>B will get selected with 10/30 chance (~33.33%)</li>
 * <li>C will get selected with 15/30 chance (50%)</li>
 * </ol>
 * If A would have weight of 10, the odds of selection would
 * change for all elements in the list in a following way:
 * <ol>
 * <li>A will get selected with 10/35 chance (~28.57%)</li>
 * <li>B will get selected with 10/35 chance (~28.57%)</li>
 * <li>C will get selected with 15/35 chance (~42.85%)</li>
 * </ol>
 * </p>
 *
 * @param <T> Type of values in the list
 */
public class WeightedList<T> {

  /**
   * WeightedList entry
   * @param <V> Type of value in the Entry
   */
  public static final class Entry<V> {
    /** Entry weight */
    private double weight;
    /** Entry value */
    private V value;

    /**
     * Creates new entry
     * @param weight weight
     * @param value value
     */
    private Entry(final double weight, final V value) {
      this.weight = weight;
      this.value = value;
    }

    @Override
    public String toString() {
      return "Entry [weight=" + weight + ", value=" + value + "]";
    }
  }

  /**
   * Creates new WeightedList Entry.
   * @param <T> Type of Entry Value
   * @param weight Weight
   * @param value Value
   * @return New entry
   */
  public static <T> WeightedList.Entry<T> entry(final double weight,
      final T value) {
    return new Entry<T>(weight, value);
  }

  /** Entries */
  private ArrayList<Entry<T>> entries;
  /** Sum of all weights inserted to list */
  private double total;

  /**
   * Create WeightedList from array of weighted entries.
   * <p>
   * <b>WARNING! This is a convenience, type-unsafe constructor!</b>
   * It allows WeightedLists to be constructed with initializer lists.
   * Plus, it cannot fail due to oversights in array sizes when adding
   * the entries by hand.
   * </p>
   * <p>
   * But because it is not possible to instantiate array with generics using
   * initializer list, this ignores Value type of Entry values.
   * And, due to type erasure, it then cannot verify at compile-time
   * that entries provided are of valid type. So, this can actually
   * make a mess if used incorrectly.
   * </p>
   * <p>
   * Use at your own risk, when want to get comfort and pretty code.
   * You have been warned.
   * </p>
   *
   * @param entries Array of untyped entries
   * @apiNote <b>JAVA'S GENERICS ARE GARBAGE!</b>
   *          Java's type system is a joke. A bad joke.
   */
  @SuppressWarnings("unchecked")
  public WeightedList(final Entry<?>[] entries) {
    Objects.requireNonNull(entries);
    this.entries = new ArrayList<>();
    for (var entry : entries) {
      add((Entry<T>) entry);
    }
  }

  /**
   * Create WeightedList from array of weights and array of values.
   * If number of entries in one array is different to the other,
   * this fails with IllegalArgumentException.
   * @param weights Array of weights
   * @param values Arrat of values
   */
  public WeightedList(final double[] weights, final T[] values) {
    Objects.requireNonNull(weights);
    Objects.requireNonNull(values);
    if (values.length != weights.length) {
      throw new IllegalArgumentException(
          "Value and Weight lists have different size");
    }

    this.total = 0;
    this.entries = new ArrayList<>();
    for (int i = 0; i < values.length; i++) {
      add(weights[i], values[i]);
    }
  }

  /**
   * Create empty WeightedList.
   */
  public WeightedList() {
    this.entries = new ArrayList<>();
    total = 0;
  }

  /**
   * Select random element from the list based on it's weight
   * relative to other elements.
   * Trying to select from empty list will result
   * in UnsupportedOperationException.
   * @return Value selected
   */
  public T pickRandom() {
    final int randNumber = DiceGenerator.getRandom(Integer.MAX_VALUE);
    return pickRandom(randNumber);
  }

  /**
   * Select random element from the list based on it's weight
   * relative to other elements, using provided number as base random value.
   * Trying to select from empty list will result
   * in UnsupportedOperationException.
   * @param randNumber
   * @return Value selected
   */
  private T pickRandom(final int randNumber) {
    if (isEmpty()) {
      throw new UnsupportedOperationException(
          "Cannot pick random entry from empty list");
    }

    final double clampedNum = randNumber % Integer.MAX_VALUE;
    final double rngVal = (clampedNum / Integer.MAX_VALUE) * total;

    for (int i = 0; i < entries.size(); i++) {
      var entry = entries.get(i);
      var nextEntryIdx = Math.min(entries.size() - 1, i + 1);
      var nextEntry = entries.get(nextEntryIdx);

      if (rngVal >= entry.weight && rngVal < nextEntry.weight) {
        return entry.value;
      }
    }

    var entry = entries.get(entries.size() - 1);
    return entry.value;
  }

  /**
   * Add entry to the list, affecting selection probability
   * of other entries respectively. Weight of added entry
   * must be greater than 0.
   * @param entry Entry with weight greater than 0
   * @return True if added successfully
   */
  public boolean add(final Entry<T> entry) {
    Objects.requireNonNull(entry);
    if (entry.weight < 0) {
      return false;
    }
    total += entry.weight;
    entry.weight = total - entry.weight;
    return entries.add(entry);
  }

  /**
   * Add decomposed entry to the list, affecting selection probability
   * of other entries respectively. Weight of added entry
   * must be greater than 0.
   * @param weight Floating-point number greater than 0
   * @param value Value
   * @return True if added successfully
   */
  public boolean add(final double weight, final T value) {
    return add(entry(weight, value));
  }

  /** Remove all entries from the list */
  public void clear() {
    entries.clear();
    total = 0;
  }

  /**
   * @return True if list is empty
   */
  public boolean isEmpty() {
    return entries.isEmpty();
  }

  /**
   * @return Number of entries in the list
   */
  public int size() {
    return entries.size();
  }

  @Override
  public String toString() {
    return "WeightedList [entries=" + entries + ", total=" + total + "]";
  }

}
