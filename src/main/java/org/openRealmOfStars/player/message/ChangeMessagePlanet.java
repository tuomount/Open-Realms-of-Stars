package org.openRealmOfStars.player.message;

import org.openRealmOfStars.game.States.StarMapView;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.planet.Planet;

public class ChangeMessagePlanet extends ChangeMessage {

    public ChangeMessagePlanet(final StarMap starMap,
            final StarMapView starMapView) {
        super(starMap, starMapView);
    }

    @Override
    public <T> void changeMessage(final T data) {
        Planet planet = (Planet) data;
        if (planet != null) {
            starMap.setCursorPos(planet.getX(), planet.getY());
            starMap.setDrawPos(planet.getX(), planet.getY());
            starMapView.setShowPlanet(planet);
            starMapView.getStarMapMouseListener().setLastClickedFleet(null);
            starMapView.getStarMapMouseListener().setLastClickedPlanet(planet);
        }
    }
}
