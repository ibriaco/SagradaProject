package it.polimi.se2018.Model.Event;

import it.polimi.se2018.Model.Color;
import it.polimi.se2018.View.UI.ViewInterface;


/**
 * Event that notifies that a Die has been placed.
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class PlacedDieEvent implements MVEvent{


    private Color color;
    private int value;
    private int coordX;
    private int coordY;
    private String username;


    public Color getColor() {

        return color;
    }

    public int getValue() {

        return value;
    }

    @Override
    public void accept(ViewInterface vi) {

    }

    public String getUsername() {

        return username;
    }

    public int getCoordX() {

        return coordX;
    }

    public int getCoordY() {

        return coordY;
    }
}
