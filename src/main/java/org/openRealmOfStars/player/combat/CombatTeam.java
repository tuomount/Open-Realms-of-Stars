package org.openRealmOfStars.player.combat;

public enum CombatTeam {
    BOTTOMTEAM(0), TOPTEAM(1);
    private int teamNumber;
    
    CombatTeam(int teamNumber) {
        this.teamNumber = teamNumber;
    }
    
    int getValue() {
        return this.teamNumber;
    }
    
}
