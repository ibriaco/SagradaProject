package it.polimi.se2018.Model.Event;

import it.polimi.se2018.Controller.ToolCard;

public class UseToolCardEvent extends Event {

    private ToolCard toolCard;

    public ToolCard getToolCard() {
        return toolCard;
    }
}