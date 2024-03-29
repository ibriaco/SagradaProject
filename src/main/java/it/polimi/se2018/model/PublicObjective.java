package it.polimi.se2018.model;

/**
 * PublicObective Class of the Game. Contains a constructor, a getter and a method, calculateBonus.
 * This method is overriden by 10 different classes, which represent the different PublicObjectives that a Game may have.
 */
public abstract class PublicObjective extends Card {
    private int score;

    public PublicObjective(String title, String description, int score) {
        super(title, description);
        this.score = score;
    }

    public int getScore() {

        return score;
    }

    public void calculateBonus(Player p){

    }
}
