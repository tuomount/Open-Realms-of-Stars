package org.openRealmOfStars.utilities;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openRealmOfStars.utilities.namegenerators.RandomSystemNameGenerator;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

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
 * Test for RandomSystemNameGenerator.numberToRoman()
 *
 */
@RunWith(value = Parameterized.class)
public class RandomSystemNameGeneratorNumberToRomanTest {

    private Integer parameter;
    private String expectedValue;

    public RandomSystemNameGeneratorNumberToRomanTest(Integer parameter, String expectedValue) {
        this.parameter = parameter;
        this.expectedValue = expectedValue;
    }

    @Parameterized.Parameters(name = "{index}: RandomSystemNameGenerator.numberToRoman({0}) = {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {0, ""},
                {1, "I"},
                {2, "II"},
                {3, "III"},
                {4, "IV"},
                {5, "V"},
                {6, "VI"},
                {7, "VII"},
                {8, "VIII"},
                {9, "IX"},
                {10, "X"},
        });
    }


    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void testA() {
        assertEquals(expectedValue, RandomSystemNameGenerator.numberToRoman(parameter));
    }
}