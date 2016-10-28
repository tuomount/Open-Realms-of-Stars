package org.openRealmOfStars.starMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
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
 * Test for Sun class
 * 
 */
public class SunTest {


    @Test
    public void testCreateSunWithName(){
        Sun sun = new Sun(10, 15, "Sun name");

        assertEquals("Sun name", sun.getName());
        assertEquals(10, sun.getCenterX());
        assertEquals(15, sun.getCenterY());
    }

    @Test
    public void testCreateSunWhereNameIsNull(){
        Sun sun = new Sun(10, 15, null);

        assertNotNull(sun.getName());
        assertEquals(true, sun.getName().contains(" "));
        assertEquals(10, sun.getCenterX());
        assertEquals(15, sun.getCenterY());
    }

}