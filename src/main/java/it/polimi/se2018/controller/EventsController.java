package it.polimi.se2018.controller;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.event.*;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.network.server.VirtualView;
import it.polimi.se2018.view.viewevents.*;
import it.polimi.se2018.view.viewevents.RollDiceEvent;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller class that handles events
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 * @author Gregorio Galletti
 */
public class EventsController implements ControllerInterface, MyObserver, MyObservable {

    private Game game;
    private boolean reconnection = false;

    private LobbyController lobbyController;
    private  TurnController turnController;
    private ToolCardController toolCardController;

    private ArrayList<MyObserver> observerCollection = new ArrayList<>();
    private MVEvent mvEvent;
    private VirtualView virtualView;
    private TimerThread timer;
    private int counter = 0;
    private int playerIndex;
    private static final Logger LOGGER = Logger.getLogger(EventsController.class.getName());

    public EventsController(VirtualView virtualView){
        this.virtualView = virtualView;
        lobbyController = new LobbyController(this, virtualView);
        toolCardController = new ToolCardController(this, lobbyController);
        timer = new TimerThread(this, 0);
        turnController = new TurnController(this);
    }

    /**
     *
     * @param username user of the player
     * @return return true if it's the player turn, false otherwise
     */
    private boolean checkPlayer(String username) {

        return (game.getPlayers().indexOf(game.findPlayer(username)) == game.getTurn());

    }


    public void setReconnection(boolean reconnection) {

        this.reconnection = reconnection;
    }
    public Game getGame() {
        return game;
    }
    public MVEvent getMvEvent() {
        return mvEvent;
    }
    public VirtualView getVirtualView() {
        return virtualView;
    }
    public ToolCardController getToolCardController() {
        return toolCardController;
    }

    public TimerThread getTimer() {
        return timer;
    }


    public void setMvEvent(MVEvent mvEvent) {
        this.mvEvent = mvEvent;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }
    public void setTimer(TimerThread timer) {
        this.timer = timer;
    }



    //METODI OBSERVER E OBSERVABLE

    @Override
    public void registerObserver(MyObserver observer) {
        observerCollection.add(observer);
    }

    @Override
    public void unregisterObserver(MyObserver observer) {
        observerCollection.remove(observer);
    }

    @Override
    public void notifyObservers() throws IOException, InvalidConnectionException, InvalidViewException, ParseException {
        for (MyObserver o: observerCollection) {
            try {
                o.update(this, mvEvent);
            } catch (InvalidDieException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(MyObservable o, VCEvent arg) throws IOException, InvalidConnectionException, InvalidViewException, ParseException, InvalidDieException {

        arg.accept(this);
    }

    @Override
    public void update(MyObservable o, MVEvent arg){

    }

    //METODI PER GENERARE EVENTI MV

    public void createGameUpdateEvent () throws InvalidConnectionException, IOException, InvalidViewException, ParseException {
        mvEvent = new GameUpdateEvent("ALL");
        notifyObservers();
    }

    /**
     * Method that handles a login event
     * @param event login event
     * @throws InvalidConnectionException exception
     * @throws IOException exception
     * @throws InvalidViewException exception
     * @throws ParseException exception
     */
    @Override
    public void handleVCEvent(LoginEvent event) throws InvalidConnectionException, IOException, InvalidViewException, ParseException {
        if (!reconnection)
            lobbyController.handleLogin(event);
        else
            lobbyController.handleReconnection(event);
    }

    /**
     * Method that handles a place die event
     * @param event place die event
     * @throws InvalidConnectionException exception
     * @throws ParseException exception
     * @throws InvalidViewException exception
     * @throws IOException exception
     */
    @Override
    public void handleVCEvent(PlaceDieEvent event) throws InvalidConnectionException, ParseException, InvalidViewException, IOException {
        if (event.getUsername().equals(game.getPlayers().get(playerIndex).getUsername())) {
            game.findPlayer(event.getUsername()).getWindowCard().placeDie(game.getRolledDice().get(event.getPos()), event.getCoordY(), event.getCoordX(), true, true);
            game.updateWindowCardList();
            if(game.findPlayer(event.getUsername()).getWindowCard().getGridCell(event.getCoordY(), event.getCoordX()).isPlaced()) {
                game.getRolledDice().remove(event.getPos());
            }
            mvEvent = new UpdateGameEvent(game.getWindowCardList(), lobbyController.getUsername(), game.getRolledDice(), game.getRoundCells());
            notifyObservers();
        }
        else {
            mvEvent = new IsNotYourTurn(event.getUsername());
            notifyObservers();
        }
    }

    @Override
    public void handleVCEvent(RollDiceEvent event) {
        //still needs to be implemented
    }

    /**
     * Method that handles a skip turn event
     * @param event skip turn event
     * @throws InvalidConnectionException exception
     * @throws ParseException exception
     * @throws InvalidViewException exception
     * @throws IOException exception
     */
    @Override
    public void handleVCEvent(SkipTurnEvent event) throws InvalidConnectionException, ParseException, InvalidViewException, IOException {
        System.out.println("nome giocatore che ha mandato evento: " + event.getUsername());
        System.out.println("turno in arrivo: " + game.getTurn());
        if (event.getUsername().equals(game.getPlayers().get(playerIndex).getUsername())){
            //if cont del timer è 60 allora genero eventi di skip turn e isotyouturn
            if (timer.getCont() == 59){
                timerExpired();
            }
            else{
                timer.interrupt();
                turnController.handleSkipTurn(game.getPlayers().indexOf(game.findPlayer(event.getUsername())));

            }
        }
        else {
            mvEvent = new IsNotYourTurn(event.getUsername());
            notifyObservers();
        }
    }

    public void timerExpired() throws InvalidConnectionException, ParseException, InvalidViewException, IOException {
        timer.interrupt();
        System.out.println("invio stopturnevent");
        mvEvent = new StopTurnEvent(game.getPlayers().get(playerIndex).getUsername());
        notifyObservers();
        System.out.println("invio skipturn a virtualview");
        try {
            virtualView.createSkipTurnEvent(game.getPlayers().get(playerIndex).getUsername());
        } catch (InvalidDieException | InvalidViewException | InvalidConnectionException | IOException | ParseException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void handleVCEvent(SelectDieEvent event) throws InvalidConnectionException, InvalidViewException, ParseException, IOException, InvalidDieException {
        toolCardController.handleVCEvent(event);
    }

    @Override
    public void handleVCEvent(UseToolEvent event) throws InvalidConnectionException, InvalidViewException, ParseException, IOException {

        toolCardController.handleVCEvent(event);
    }

    /**
     * Method that handles choice of a Window Card
     * @param event choose card event
     * @throws InvalidConnectionException exception
     * @throws IOException exception
     * @throws InvalidViewException exception
     * @throws ParseException exception
     */
    @Override
    public void handleVCEvent(ChooseCardEvent event) throws InvalidConnectionException, IOException, InvalidViewException, ParseException {
        counter++;
        LOGGER.log(Level.INFO, "Risposte ricevute: " + counter);
        lobbyController.handleWindowCard(event);
        if(counter == game.getPlayerNumber()) {
            game.dealPublicCards();
            game.dealToolCards();
            lobbyController.newGame();
        }
        LOGGER.log(Level.INFO, "Sono tornato nel lobbycontroller(windowcard)");
    }

    @Override
    public void handleVCEvent(PlaceModifiedDie placeModifiedDie) {

    }

    /*@Override
    public void handleVCEvent(PlaceModifiedDie placeModifiedDie) {

        toolCardController.handleVCEvent(placeModifiedDie);
    }*/

    public void setGame(Game game) {

        this.game = game;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }
}
