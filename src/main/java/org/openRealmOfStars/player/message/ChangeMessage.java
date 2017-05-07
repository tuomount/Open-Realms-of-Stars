package org.openRealmOfStars.player.message;

import org.openRealmOfStars.game.States.StarMapView;
import org.openRealmOfStars.starMap.StarMap;

public abstract class ChangeMessage {
    StarMap starMap;
    StarMapView starMapView;

    public ChangeMessage(final StarMap starMap, final StarMapView starMapView) {
        this.starMap = starMap;
        this.starMapView = starMapView;
    }
    public abstract <T> void changeMessage(T data);
}
