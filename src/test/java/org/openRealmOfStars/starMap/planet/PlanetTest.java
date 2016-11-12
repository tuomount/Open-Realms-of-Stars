package org.openRealmOfStars.starMap.planet;

import org.junit.Test;
import org.openRealmOfStars.starMap.Coordinate;

import static org.junit.Assert.*;

public class PlanetTest {

    @Test
    public void testSunCoordinateShouldChangeableWithSideEffect() {
        Coordinate planetCoordinate = new Coordinate(10, 15);
        Planet planet = new Planet(planetCoordinate, "Earth", 1, false);

        planetCoordinate.setX(5);
        planetCoordinate.setY(10);

        assertNotEquals(planet.getCoordinate(), planetCoordinate);

        Coordinate getPlanetCoordinate = planet.getCoordinate();
        getPlanetCoordinate.setX(5);
        getPlanetCoordinate.setY(10);

        assertNotEquals(planet.getCoordinate(), getPlanetCoordinate);
    }
}