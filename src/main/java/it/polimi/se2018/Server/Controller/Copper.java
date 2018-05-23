package it.polimi.se2018.Server.Controller;


import it.polimi.se2018.Client.View.PlaceDieEvent;
import it.polimi.se2018.Client.View.SelectDieEvent;
import it.polimi.se2018.Server.Model.Color;
import it.polimi.se2018.Server.Model.Die;
import it.polimi.se2018.Server.Model.Player;


//Muovi  un  qualsiasi  dado  nella  tua  vetrata  ignorando  le  restrizioni  di  valore. Devi  rispettare
// tutte  le  altre  restrizioni  di  piazzamento

public class Copper extends ToolCard {
    public Copper(int number, String title, String description, boolean used, int cost, Color color, int shade) {
        super(number, title, description, used, cost, color, shade);
    }
    protected void applyEffect(Player p, PlaceDieEvent placeDieE, SelectDieEvent selectDieE){
        Die d = p.getWindowCard().getGridCell(selectDieE.getCoordX(),selectDieE.getCoordY()).getPlacedDie();
        p.getWindowCard().removeDie( selectDieE.getCoordX(),selectDieE.getCoordY());
        p.getWindowCard().placeDie(d, placeDieE.getCoordX(), placeDieE.getCoordY(),true,false);

    }

}
