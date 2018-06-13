package it.polimi.se2018.View.ViewEvents;

import it.polimi.se2018.Controller.ControllerInterface;

/**
 * Event that notifies that a turn is skipped.
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class SkipTurnEvent implements VCEvent {


    private String username;

    public SkipTurnEvent(String username) {

        this.username = username;
    }

    public String getUsername() {

        return username;
    }

    @Override
    public void accept(ControllerInterface controller) {
        controller.handleVCEvent(this);
    }
}
