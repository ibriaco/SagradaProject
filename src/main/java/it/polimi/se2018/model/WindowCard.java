package it.polimi.se2018.model;


import java.io.Serializable;

/**WindowCard Class of the Game.
 * @author Ibrahim El Shemy
 * @author Gregorio Galletti
 */
public class WindowCard implements Serializable{
    private static final int ROWS = 4;
    private static final int COLS = 5;
    static final long serialVersionUID = 42L;

    private Cell[][] grid;
    private String windowName;
    private int difficulty;

    public WindowCard() {

        grid = new Cell[ROWS][COLS];
    }

    public int getRows(){
        return ROWS;
    }

    public int getCols(){
        return COLS;
    }

    /**
     *Method that check if a certain die can be placed in a certain position of the WindowCard.
     * @param d Refers to the die the Player wants to place.
     * @param row Refers to the row where the Players wants to place the die.
     * @param col Refers to the column where the Players wants to place the die.
     * @return true if the die can be placed, either way, false is returned.
     */
    public boolean checkLegalPlacement(Die d, int row, int col, Boolean color, Boolean shade, boolean around){

        if(!checkCoords(row, col)) {
            return false;

        }

        if(!checkPlacement(getGridCell(row, col), d, color, shade)) {
            return false;
        }

        if (isEmpty()){
            return ((row >= 0 && row <= 3) && (col == 0 || col == 4) || (col >= 0 && col <= 4) && (row == 0 || row == 3));
        }

        if(isFull())
            return false;

        if(getGridCell(row, col).isPlaced())
            return false;

        if (!checkOrthogonal(d, row, col, color, shade))
            return false;

        if (around)//se è true devo controllare anche checkAround
            if(!checkAround(row, col))
                return false;

        return true;
    }

    private boolean checkOrthogonal(Die d, int row, int col, Boolean color, Boolean shade) {

        int previousR = row - 1;
        int previousC = col - 1;
        int nextR = row + 1;
        int nextC = col + 1;

        boolean ok1 = checkNSEW(d, previousR, col, color, shade);
        boolean ok2 = checkNSEW(d, nextR, col, color, shade);
        boolean ok3 = checkNSEW(d, row, previousC, color, shade);
        boolean ok4 = checkNSEW(d, row, nextC, color, shade);

        return(ok1 && ok2 && ok3 && ok4);
    }
//metodo per controllare che il dado da piazzare sia adiacente ad altri dadi
    private boolean checkAround(int row, int col) {

        int previousR = row - 1;
        int previousC = col - 1;
        int nextR = row + 1;
        int nextC = col + 1;

        for(int i = previousR; i <= nextR; i++){
            for (int j = previousC; j <= nextC; j++){
                try{
                    if(getGridCell(i, j).isPlaced()){
                        return true;}
                }
                catch (ArrayIndexOutOfBoundsException E){}
            }
        }
        return false;
    }

    private boolean checkCoords(int r, int c){

        return (r <= ROWS && r >= 0) && (c <= COLS && c >= 0);

    }
//metodo che controlla se i dadi adiacenti (a croce) sono uguali per colore o numero al dado che vuoi piazzare
    private boolean checkNSEW(Die d, int r, int c, Boolean color, Boolean shade) {

        Die toCheck;
        boolean ok = false;
        boolean okColor;
        boolean okShade;

        try {
            ok = getGridCell(r, c).isPlaced();
        } catch (ArrayIndexOutOfBoundsException e) {
        }

        if (ok)
            toCheck = getGridCell(r, c).getPlacedDie();
        else
            return true;


        okColor = toCheck.getColor() != d.getColor();
        okShade = toCheck.getValue() != d.getValue();
        return okColor && okShade;
    }

    /**
     *
     * @param c Refers to the cell where the Player wants to place the die.
     * @param d Refers to the die to be placed.
     * @return true if the placement can be done, false otherwise.
     */
    private boolean checkPlacement(Cell c, Die d, boolean color, boolean shade){

        if(c.getColor() == Color.WHITE && c.getShade() == 0)
            return true;
        if(d != null) {
            if (color && shade)
                return c.getColor() == d.getColor() || c.getShade() == d.getValue();
            else if (color) {
                if(c.getColor() == Color.WHITE)
                    return true;
                else
                    return c.getColor() == d.getColor();
            }
            else if (shade) {
                if(c.getShade()==0)
                    return true;
                else
                    return c.getShade() == d.getValue();
            }
        }
        return false;
    }

    public void setCell(Cell c, int row, int col) {

        grid[row][col] = c;
    }

    /**
     * Method that lets the Player place a die in a specific position.
     * To make sure that it works properly, checkLegalPlacement is called.
     * @param d Refers to the die to be placed.
     * @param row Refers to the row where the Player wants to place the die.
     * @param col Refers to the column where the Player wants to place the die.
     */
    public void placeDie(Die d, int row, int col, boolean color, boolean shade, boolean around){

        if(checkLegalPlacement(d,row,col,color,shade, around)) {
            getGridCell(row, col).placeDie(d);
        }
    }

    /**
     * Method that removes a die from a certain position.
     * @param row Refers to the row where die to be removed is placed in.
     * @param col Refers to the column where die to be removed is placed in.
     */
    public void removeDie(int row, int col){

        if(grid[row][col].isPlaced())
            grid[row][col].setPlacedDie(null);
    }

    /**
     * Method that returns a specific cell, given a row and a column.
     * @param row Refers to a row of the WindowCard.
     * @param col Refers to a column of the WindowCard.
     * @return a specific cell, found by row and col.
     */
    public Cell getGridCell(int row, int col){

       return grid[row][col];
    }


    public String getWindowName(){

        return windowName;
    }


    public int getDifficulty(){

        return difficulty;
    }


    public boolean isFull(){

        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                if(!grid[i][j].isPlaced())
                    return false;
            }
        }
        return true;
    }

    /**
     * Method that checks if the WindowCard is empty or not.
     * @return true if the WindowCard is empty, either way, false is returned.
     */
    public boolean isEmpty(){

        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                if(grid[i][j].isPlaced())
                    return false;
            }
        }
        return true;
    }

    public void setWindowName(String windowName) {

        this.windowName = windowName;
    }

    public void setDifficulty(int difficulty) {

        this.difficulty = difficulty;
    }
}
