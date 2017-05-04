package org.openRealmOfStars.player.combat;

public class TopPositionList extends CombatPositionList{
    @Override
    public CombatCoordinate getCoordinate(int combatShipIndex){
        switch (combatShipIndex) {
        case 0:
            return new CombatCoordinate(4,1);
        case 1:
            return new CombatCoordinate(3,1);
        case 2:
            return new CombatCoordinate(5,1);
        case 3:
            return new CombatCoordinate(2,1);
        case 4:
            return new CombatCoordinate(6,1);
        case 5:
            return new CombatCoordinate(4,0);
        case 6:
            return new CombatCoordinate(1,1);
        case 7:
            return new CombatCoordinate(7,1);
        case 8:
            return new CombatCoordinate(3,0);
        case 9:
            return new CombatCoordinate(5,0);
        case 10:
            return new CombatCoordinate(2,0);
        case 11:
            return new CombatCoordinate(6,0);
        case 12:
            return new CombatCoordinate(0,1);
        case 13:
            return new CombatCoordinate(8,1);
        case 14:
            return new CombatCoordinate(1,0);
        case 15:
            return new CombatCoordinate(7,0);
        case 16:
            return new CombatCoordinate(0,0);
        case 17:
            return new CombatCoordinate(8,0);
        default:
          throw new IllegalArgumentException("Fleet contains too many ships!");
        }   
  }
}
