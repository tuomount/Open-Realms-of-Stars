package org.openRealmOfStars.starMap;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class SunTest {

    private Sun sun;

    @Test
    public void testCreateSunWithName(){
        sun = new Sun(10, 15, "Sun name");

        assertEquals("Sun name", sun.getName());
        assertEquals(10, sun.getCenterX());
        assertEquals(15, sun.getCenterY());
    }

    @Test
    @Ignore(value = "static RandomSystemNameGenerator.generate() should be mockable.")
    public void testCreateSunWhereNameIsNull(){
        sun = new Sun(10, 15, null);

        assertEquals("Sun name", sun.getName());
        assertEquals(10, sun.getCenterX());
        assertEquals(15, sun.getCenterY());
    }

}