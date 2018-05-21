package it.polimi.se2018.Model;

/**WindowCard Class of the Game.
 * @author Ibrahim El Shemy
 * @author Gregorio Galletti
 */
public class WindowCard {
    static final int ROWS = 4;
    static final int COLS = 5;
    private Cell[][] grid;
    private String windowName;
    private int difficulty;

    public WindowCard() {

        grid = new Cell[ROWS][COLS];
    }

    /**
     *Method that check if a certain die can be placed in a certain position of the WindowCard.
     * @param d Refers to the die the Player wants to place.
     * @param row Refers to the row where the Players wants to place the die.
     * @param col Refers to the column where the Players wants to place the die.
     * @return true if the die can be placed, either way, false is returned.
     */
    public boolean checkLegalPlacement(Die d, int row, int col){

        int previousR = row - 1;
        int previousC = col - 1;
        int nextR = row + 1;
        int nextC = col + 1;

        if (isEmpty())
            return ((row >= 0 && row <= 3) && (col == 0 || col == 4) || (col >= 0 && col <= 4) && (row == 0 || row == 3));

        if(isFull())
            return false;

        if(!checkCoords(row, col))
            return false;

        if(getGridCell(row, col).isPlaced())
            return false;

        return checkOrtogonal(d, row, col, previousR, previousC, nextC, nextR) && checkAround(previousR, previousC, nextC, nextR);
    }

    private boolean checkOrtogonal(Die d, int row, int col, int previousR, int previousC, int nextC, int nextR) {

        boolean ok1 = checkNSEW(d, previousR, col);
        boolean ok2 = checkNSEW(d, nextR, col);
        boolean ok3 = checkNSEW(d, row, previousC);
        boolean ok4 = checkNSEW(d, row, nextC);

        return(ok1 && ok2 && ok3 && ok4);
    }

    private boolean checkAround(int previousR, int previousC, int nextC, int nextR) {

        for(int i = previousR; i <= nextR; i++){
            for (int j = previousC; j <= nextC; j++){
                try{
                    if(getGridCell(i, j).isPlaced())
                        return true;
                }
                catch (ArrayIndexOutOfBoundsException E){}
            }
        }
        return false;
    }

    private boolean checkCoords(int r, int c){

        return (r <= ROWS && r >= 0) && (c <= COLS && c >= 0);

    }

    private boolean checkNSEW(Die d, int r, int c){

        Die toCheck;
        boolean ok = false;

        try {
            ok = getGridCell(r,c).isPlaced();
        }catch (ArrayIndexOutOfBoundsException e){}

        if(ok)
            toCheck = getGridCell(r,c).getPlacedDie();
        else
            return true;
        return !(toCheck.getColor() == d.getColor() || toCheck.getValue() == d.getValue());
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
    public void placeDie(Die d, int row, int col){

        if(checkLegalPlacement(d,row,col))
            getGridCell(row,col).placeDie(d);
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
