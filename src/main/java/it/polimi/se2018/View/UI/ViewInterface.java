package it.polimi.se2018.View.UI;

import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.View.ViewEvents.VCEvent;

public interface ViewInterface extends MyObserver, MyObservable {

    void updateWindowCard();

    void showUI();

    void getEvent(VCEvent event);

    void loginScreen();

    /*
    public void updateRoundTrack(Game game){

    }
    public void updateRoundDice(Game game){

    }
    public void updateRolledDice(Game game){

    }

    public void updateToolCard(){

    }

    public void showRoundTrack(Game game){

    }
    public void showRoundDice(Game game){

    }
    public void showRolledDice(Game game){

    }
    public void showPublicCards(Game game){

    }
    public void showToolCards(Game game){

    }
    public void showPrivateCard(Game game){

    }
    public void showPlayersWindow(Game game){

    }
    public void rollDice(){

    }
    public void skipTurn(){

    }

    public void update(VCEvent event) {

    }
*/

}

