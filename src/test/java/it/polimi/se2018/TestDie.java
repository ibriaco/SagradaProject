package it.polimi.se2018;

import it.polimi.se2018.Model.Color;
import it.polimi.se2018.Model.Die;
import it.polimi.se2018.Model.InvalidDieException;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

public class TestDie {

    @Test
    public void testDieCreation(){
        Die d = null;
        try {
            d = new Die(Color.BLUE, 3);
        } catch (InvalidDieException e) {
            e.printStackTrace();
        }

        assertEquals (Color.BLUE,d.getColor());
        assertEquals(3, d.getValue());
    }

    @Test
    public void testDieFailCreation(){
        Die d = null;
        try {
            d = new Die(Color.BLUE, 9);
            fail();
        } catch (InvalidDieException e) {

        }
    }

    @Test
    public void testDieReverse(){
        Die d = null;
        try {
            d = new Die(Color.BLUE, 3);
        } catch (InvalidDieException e) {
            e.printStackTrace();
        }

        d.reverse(d);
        assertEquals (Color.BLUE, d.getColor());
        assertEquals(4, d.getValue());
    }

    @Test
    public void testRollDie(){
        Die d = null;
        try {
            d = new Die(Color.BLUE, 3);
        } catch (InvalidDieException e) {
            e.printStackTrace();
        }

        d = d.rollDie();

        assert(d.getColor() != null);
        assert(d.getValue() > 0 && d.getValue() < 7);
    }

    //creare test per verificare se vengono tolti realmente i colori disponibili
}
