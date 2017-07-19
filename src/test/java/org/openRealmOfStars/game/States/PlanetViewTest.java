package org.openRealmOfStars.game.States;

import static org.junit.Assert.*;

import java.awt.event.ActionListener;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.construction.Construction;

public class PlanetViewTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    Planet planet = Mockito.mock(Planet.class);
    Planet planet2 = Mockito.mock(Planet.class);
    Construction[] constructionList = new Construction[2];
    constructionList[0] = Mockito.mock(Construction.class);
    constructionList[1] = Mockito.mock(Construction.class);
    Mockito.when(planet.getProductionList()).thenReturn(constructionList);
    PlayerInfo player = Mockito.mock(PlayerInfo.class);
    ActionListener listener = Mockito.mock(ActionListener.class);
    PlanetView view = new PlanetView(planet, false, player, listener);
    assertEquals(planet, view.getPlanet());
    view.setPlanet(planet2);
    assertEquals(planet2, view.getPlanet());
  }

}
