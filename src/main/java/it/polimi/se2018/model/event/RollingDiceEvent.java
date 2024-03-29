package it.polimi.se2018.model.event;

import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidDieException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.org.json.simple.parser.ParseException;
import it.polimi.se2018.view.ui.ViewInterface;

import java.io.IOException;
import java.util.List;

/**
 * event that notifies that some dice have been rolled
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class RollingDiceEvent implements MVEvent{

    String username;

    public RollingDiceEvent(String user){
        this.username = user;
    }

    @Override
    public void accept(ViewInterface vi) throws InvalidDieException, InvalidConnectionException, InvalidViewException, ParseException, IOException {
        vi.handleMVEvent(this);
    }

    @Override
    public String getUsername() {
        return username;
    }
}
