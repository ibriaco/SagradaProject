package it.polimi.se2018.model;

import it.polimi.se2018.ServerParser;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Ibrahim El Shemy
 * Unit Test Class for addDie method and replaceDie method of RoundCell Class
 */

public class TestRoundCell {
    Game g;
    RoundCell r;
    private ServerParser sp = new ServerParser();

    @Before
    public void init(){
        sp.reader(sp.createObj());
        g = new Game(2);
        r = new RoundCell(1);
    }

    @Test
    public void testAddDie() {

        Die d = new Die(g.getColorList());

        r.addDie(d);
        assertTrue(r.getDiceList().contains(d));
        assertEquals(d.getColor(), r.getDiceList().get(0).getColor());
        assertEquals(d.getValue(), r.getDiceList().get(0).getValue());
    }


    @Test
    public void testReplaceDie() throws InvalidDieException {

        Die d1 = new Die(g.getColorList());
        Die d2 = new Die(g.getColorList());

        r.addDie(d1);
        r.replaceDie(d2, d1);
        assertTrue(r.getDiceList().contains(d2));
        assertTrue(!r.getDiceList().contains(d1));
    }
}