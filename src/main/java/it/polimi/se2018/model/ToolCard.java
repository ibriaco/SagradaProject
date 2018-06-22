package it.polimi.se2018.model;

public class ToolCard extends Card {
    private boolean used = false;

    public ToolCard(String title, String description) {
        super(title, description);
        this.used = used;

    }

    public boolean isUsed() {

        return used;
    }
    public void setUsed(boolean used) {
        this.used = used;
    }


    public void applyEffect(Player p, Die d, RoundCell r){

    }
}