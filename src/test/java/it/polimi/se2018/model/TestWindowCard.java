package it.polimi.se2018.model;

import it.polimi.se2018.ServerParser;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Marco Gasperini
 * Unit Test Class for WindowCard Class.
 */
public class TestWindowCard {

    WindowCard w;
    private Game g;
    Die d1,d2,d3,d4,d5,d6,d7,d8,d9,d10,d11,d12,d13,d14,d15,d16,d17,d18,d19,d20;
    private ServerParser sp = new ServerParser();


    @Before
    public void init() throws InvalidDieException {
        sp.reader(sp.createObj());

        g = new Game(2);

        //creo una windowcard con questi specifici valori
        w = new WindowCard();
        w.setCell(new Cell(Color.BLUE,0),0,0);
        w.setCell(new Cell(Color.RED,2),0 ,1);
        w.setCell(new Cell(Color.GREEN,3),0,2);
        w.setCell(new Cell(Color.RED,4),0,3);
        w.setCell(new Cell(Color.PURPLE,5),0,4);
        w.setCell(new Cell(Color.RED,2),1,0);
        w.setCell(new Cell(Color.YELLOW,3),1,1);
        w.setCell(new Cell(Color.GREEN,4),1,2);
        w.setCell(new Cell(Color.YELLOW,5),1,3);
        w.setCell(new Cell(Color.GREEN,1),1,4);
        w.setCell(new Cell(Color.YELLOW,3),2,0);
        w.setCell(new Cell(Color.RED,4),2,1);
        w.setCell(new Cell(Color.PURPLE,5),2,2);
        w.setCell(new Cell(Color.BLUE,6),2,3);
        w.setCell(new Cell(Color.PURPLE,2),2,4);
        w.setCell(new Cell(Color.BLUE,4),3,0);
        w.setCell(new Cell(Color.PURPLE,5),3,1);
        w.setCell(new Cell(Color.BLUE,6),3,2);
        w.setCell(new Cell(Color.GREEN,2),3,3);
        w.setCell(new Cell(Color.RED,3),3,4);

        d1 = new Die(g.getColorList());
        d2 = new Die(g.getColorList());
        d3 = new Die(g.getColorList());
        d4 = new Die(g.getColorList());
        d5 = new Die(g.getColorList());
        d6 = new Die(g.getColorList());
        d7 = new Die(g.getColorList());
        d8 = new Die(g.getColorList());
        d9 = new Die(g.getColorList());
        d10 = new Die(g.getColorList());
        d11 = new Die(g.getColorList());
        d12 = new Die(g.getColorList());
        d13 = new Die(g.getColorList());
        d14 = new Die(g.getColorList());
        d15 = new Die(g.getColorList());
        d16 = new Die(g.getColorList());
        d17 = new Die(g.getColorList());
        d18 = new Die(g.getColorList());
        d19 = new Die(g.getColorList());
        d20 = new Die(g.getColorList());


            d1.setValue(3);


            d2.setValue(2);


            d3.setValue(3);


            d4.setValue(4);

            d5.setValue(5);


            d6.setValue(2);


            d7.setValue(3);


            d8.setValue(4);

            d9.setValue(5);


            d10.setValue(1);


            d11.setValue(3);


            d12.setValue(4);


            d13.setValue(5);


            d14.setValue(6);

            d15.setValue(2);


            d16.setValue(4);


            d17.setValue(5);

            d18.setValue(6);


            d19.setValue(2);


            d20.setValue(3);


        d1.setColor(Color.BLUE);
        d2.setColor(Color.RED);
        d3.setColor(Color.YELLOW);
        d4.setColor(Color.RED);
        d5.setColor(Color.PURPLE);
        d6.setColor(Color.RED);
        d7.setColor(Color.YELLOW);
        d8.setColor(Color.GREEN);
        d9.setColor(Color.YELLOW);
        d10.setColor(Color.GREEN);
        d11.setColor(Color.YELLOW);
        d12.setColor(Color.RED);
        d13.setColor(Color.PURPLE);
        d14.setColor(Color.BLUE);
        d15.setColor(Color.PURPLE);
        d16.setColor(Color.BLUE);
        d17.setColor(Color.PURPLE);
        d18.setColor(Color.BLUE);
        d19.setColor(Color.GREEN);
        d20.setColor(Color.RED);
    }

    @Test
    public void testFirstPlacement(){

        assert w.checkLegalPlacement(d1,0,0,true,true, true);
        assert !w.checkLegalPlacement(d1,1,1,true,true, true);
    }

    @Test
    public void testWrongCoordPlacement() throws InvalidDieException {

        Die d1 = new Die(g.getColorList());

            d1.setValue(1);

        d1.setColor(Color.BLUE);

        //se il dado lo voglio mettere in coordinate sbagliate allora non va bene
        try {
            assert !w.checkLegalPlacement(d1, 7, 2, true, true, true);
        }
        catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testFullCardPlacement() throws InvalidDieException {

        w.setCell(new Cell(Color.YELLOW,3),0,2);
        Die d21 = new Die(g.getColorList());

        d21.setValue(3);

        d21.setColor(Color.BLUE);

        w.getGridCell(0, 0).setPlacedDie(d1);
        w.getGridCell(0, 1).setPlacedDie(d2);
        w.getGridCell(0, 2).setPlacedDie(d3);
        w.getGridCell(0, 3).setPlacedDie(d4);
        w.getGridCell(0, 4).setPlacedDie(d5);
        w.getGridCell(1, 0).setPlacedDie(d6);
        w.getGridCell(1, 1).setPlacedDie(d7);
        w.getGridCell(1, 2).setPlacedDie(d8);
        w.getGridCell(1, 3).setPlacedDie(d9);
        w.getGridCell(1, 4).setPlacedDie(d10);
        w.getGridCell(2, 0).setPlacedDie(d11);
        w.getGridCell(2, 1).setPlacedDie(d12);
        w.getGridCell(2, 2).setPlacedDie(d13);
        w.getGridCell(2, 3).setPlacedDie(d14);
        w.getGridCell(2, 4).setPlacedDie(d15);
        w.getGridCell(3, 0).setPlacedDie(d16);
        w.getGridCell(3, 1).setPlacedDie(d17);
        w.getGridCell(3, 2).setPlacedDie(d18);
        w.getGridCell(3, 3).setPlacedDie(d19);
        w.getGridCell(3, 4).setPlacedDie(d20);

        assert (!w.checkLegalPlacement(d21,0,0,true,true, true));


    }

    @Test
    public void testAlreadyPlaced(){
        w.getGridCell(0,1).setPlacedDie(d1);
        assert (!w.checkLegalPlacement(d2,0,1,true,true, true));

    }

    @Test
    public void testCheckOrtogonal(){
        w.getGridCell(0,0).setPlacedDie(d1);
        assert(w.checkLegalPlacement(d2, 1, 0,true,true, true));
    }

    @Test
    public void testWrongCheckOrtogonal(){
        d3.setColor(Color.GREEN);
        w.getGridCell(0,2).setPlacedDie(d3);
        assert(!w.checkLegalPlacement(d8, 1, 2,true,true, true));
    }

    @Test
    public void testCheckAround(){
        w.getGridCell(0, 0).setPlacedDie(d1);
        assert (w.checkLegalPlacement(d7,1,1,true,true, true));

    }

    @Test
    public void testWrongCheckAround(){
        w.getGridCell(0, 0).setPlacedDie(d1);
        assert (!w.checkLegalPlacement(d13,2,2,true,true, true));

    }

    @Test
    public void testPlaceColorRestriction(){
        w.placeDie(d1,0,0,true,false, true);
        assertEquals(true, w.getGridCell(0,0).isPlaced());
        assertEquals(d1, w.getGridCell(0,0).getPlacedDie());
    }
    @Test
    public void testWrongPlaceColorRestriction(){
        w.placeDie(d2,0,0,true,false, true);
        assertEquals(false, w.getGridCell(0,0).isPlaced());
        assertNull(w.getGridCell(0,0).getPlacedDie());
    }
    @Test
    public void testPlaceShadeRestriction(){
        w.placeDie(d2,0,1,false,true, true);
        assertEquals(true, w.getGridCell(0,1).isPlaced());
        assertEquals(d2, w.getGridCell(0,1).getPlacedDie());
    }
    @Test
    public void testWrongPlaceShadeRestriction(){
        w.placeDie(d2,0,0,false,true, true);
        assertEquals(true, w.getGridCell(0,0).isPlaced());
        assertEquals(d2, w.getGridCell(0,0).getPlacedDie());
    }

    @Test
    public void testWrongPlace(){
        w.placeDie(d1,2,2,true,true, true);
        assertEquals(false, w.getGridCell(2,2).isPlaced());
    }



    @Test
    public void testComleteCheckColor() throws InvalidDieException {
            d8.setValue(3);
            d10.setValue(4);

        w.placeDie(d8,0,2,true,true, true);
        w.placeDie(d10,1,2,true,true, true);
        assertEquals(d8, w.getGridCell(0,2).getPlacedDie());
        assertNull(w.getGridCell(1,2).getPlacedDie());

    }

    @Test
    public void testRemove(){
        w.getGridCell(0, 0).setPlacedDie(d1);
        w.removeDie(0,0);
        assertEquals(false, w.getGridCell(0,0).isPlaced());
    }


}
