package org.openRealmOfStars.player.message;

import org.openRealmOfStars.game.States.StarMapView;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.starMap.StarMap;


public class ChangeMessageFleet extends ChangeMessage {
	public ChangeMessageFleet(StarMap starMap,StarMapView starMapView) {
		super(starMap,starMapView);
	}

	@Override
	public <T> void changeMessage(T data) {
		Fleet fleet = (Fleet) data;
	    if (fleet != null) {
	        starMap.setCursorPos(fleet.getX(), fleet.getY());
	        starMap.setDrawPos(fleet.getX(), fleet.getY());
	        starMapView.setShowFleet(fleet);
	        starMapView.getStarMapMouseListener().setLastClickedFleet(fleet);
	        starMapView.getStarMapMouseListener().setLastClickedPlanet(null);
	    }
	}
}
