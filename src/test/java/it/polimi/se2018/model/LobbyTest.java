package it.polimi.se2018.model;

import it.polimi.se2018.MyObserver;
import it.polimi.se2018.ServerParser;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class LobbyTest {
    Lobby b;
    Player p;
    ServerParser sp = new ServerParser();
    ArrayList<MyObserver> observerCollection;
    MyObserver observer;

    @Before
    public void init() throws InvalidViewException {
        sp.reader(sp.createObj());
        b = new Lobby();
        p = new Player ("Geronimo", "GUI");
        observerCollection = new ArrayList<>();
    }

    @Test
    public void addOnlinePlayer() {
        b.addOnlinePlayer(p.getUsername());
        assertEquals(1, b.getOnlinePlayers().size());

    }

    @Test
    public void removeOnlinePlayer() {
        b.removeOnlinePlayer(p.getUsername());
        assertEquals(0, b.getOnlinePlayers().size());
    }

    @Test
    public void registerObserver(){
        observerCollection.add(observer);
        assertEquals(1, observerCollection.size());
    }

    @Test
    public void unregisterObserver(){
        observerCollection.remove(observer);
        assertEquals(0, observerCollection.size());
    }
}