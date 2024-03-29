package it.polimi.se2018.model;

import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.model.event.MVEvent;
import it.polimi.se2018.org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.polimi.se2018.ServerConfig.TWO_VALUE;

/**
 * Lobby class of the game
 * @author Gregorio Galletti
 */
public class Lobby implements MyObservable{

    private int onlinePlayersN;
    private ArrayList<String> onlinePlayers;
    private ArrayList<MyObserver> observerCollection = new ArrayList<>();
    private MVEvent mvEvent;
    private static final Object lock = new Object();
    private static final Logger LOGGER = Logger.getGlobal();

    public Object getLock() {
        return lock;
    }

    public Lobby(){
        onlinePlayersN = 0;
        onlinePlayers = new ArrayList<>();
    }


    /**
     * Adds a player in the lobby
     * @param p player added in the lobby
     */
    public void addOnlinePlayer(String p){
        synchronized (lock) {
            if (getOnlinePlayersN() == TWO_VALUE)
                lock.notifyAll();
        }
        onlinePlayers.add(p);
        onlinePlayersN++;
    }

    /**
     * Removes a player from the lobby
     * @param p player removed from the lobby
     */
    public void removeOnlinePlayer(String p){
        onlinePlayers.remove(p);
        onlinePlayersN--;
    }

    public int getOnlinePlayersN(){
        return onlinePlayersN;
    }

    public List<String> getOnlinePlayers(){
        return onlinePlayers;
    }

    @Override
    public void registerObserver(MyObserver observer) {
        observerCollection.add(observer);
    }

    @Override
    public void unregisterObserver(MyObserver observer){
        observerCollection.remove(observer);
    }

    @Override
    public void notifyObservers() throws IOException, InvalidConnectionException, InvalidViewException, ParseException {
        for (MyObserver o: observerCollection) {
            try {
                o.update(this, mvEvent);
            } catch (InvalidDieException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }
        }
    }
}
