package it.polimi.se2018.network.server;


import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.controller.ChangeDieEvent;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidDieException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.model.event.*;
import it.polimi.se2018.network.client.ClientInterface;
import it.polimi.se2018.org.json.simple.parser.ParseException;
import it.polimi.se2018.view.ui.ViewInterface;
import it.polimi.se2018.view.viewevents.RemovedUser;
import it.polimi.se2018.view.viewevents.SkipTurnEvent;
import it.polimi.se2018.view.viewevents.VCEvent;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static it.polimi.se2018.ServerConfig.TO_ALL;

/**
 * Class that works as a Proxy on the server side
 * It is observed by the EventsController
 * It observes the Model
 * @author Ibrahim El Shemy
 */
public class VirtualView implements ViewInterface {

    private ArrayList<MyObserver> observerCollection = new ArrayList<>();
    private HashMap<String, ClientInterface> clients;
    private List<String> removedClients;
    private VCEvent event;
    private ClientInterface clientTemp;

    public VirtualView(){

        clients = new HashMap<>();
        removedClients = new ArrayList<>();
    }

    /**
     * Updates a window card
     */
    @Override
    public void updateWindowCard() {
        /*Intentionally left void, not used in this class*/
    }

    public synchronized void addClientToMap(String u, ClientInterface c){
        clients.put(u,c);
    }

    public void removeClientFromMap(String u) throws InvalidDieException, InvalidConnectionException, ParseException, InvalidViewException, IOException {

        clients.remove(u);
        event = new RemovedUser(u, clients.size());
        notifyObservers();
    }

    public Map<String, ClientInterface> getClients() {

        return clients;
    }

    public List<String> getRemovedClients() {

        return removedClients;
    }

    /**
     * Receives an event from the server
     * @param event event received
     * @throws InvalidConnectionException thrown exception
     * @throws RemoteException thrown exception
     * @throws InvalidViewException thrown exception
     */
    public void receiveEvent(VCEvent event) throws InvalidConnectionException, IOException, InvalidViewException, ParseException, InvalidDieException {
        this.event = event;
        notifyObservers();
    }

    @Override
    public void loginScreen() throws RemoteException {
        /*Intentionally left void, not used in this class*/
    }

    @Override
    public void handleMVEvent(LoggedUserEvent event) {
        /*Intentionally left void, not used in this class*/
    }

    @Override
    public void handleMVEvent(DisconnectedEvent event) {
        /*Intentionally left void, not used in this class*/
    }

    @Override
    public void handleMVEvent(PrivateCardEvent privateCardEvent) {
        /*Intentionally left void, not used in this class*/
    }

    @Override
    public void handleMVEvent(WindowCardEvent event) {
        /*Intentionally left void, not used in this class*/
    }

    @Override
    public void handleMVEvent(UpdateGameEvent updateGameEvent) {
        /*Intentionally left void, not used in this class*/
    }

    @Override
    public void handleMVEvent(PublicCardEvent publicCardEvent) {
        /*Intentionally left void, not used in this class*/
    }

    @Override
    public void handleMVEvent(ToolCardEvent toolCardEvent) {
        /*Intentionally left void, not used in this class*/
    }

    @Override
    public void createNH(String choice) throws RemoteException {
        /*Intentionally left void, not used in this class*/
    }

    @Override
    public void setUsername(String u) {
        /*Intentionally left void, not used in this class*/
    }

    @Override
    public void createLoginEvent() throws InvalidConnectionException, IOException, InvalidViewException, ParseException {
        /*Intentionally left void, not used in this class*/
    }

    @Override
    public void handleMVEvent(IsTurnEvent isTurnEvent) throws InvalidConnectionException, InvalidViewException, ParseException, IOException {
        /*Intentionally left void, not used in this class*/
    }

    @Override
    public void handleMVEvent(StopTurnEvent stopTurnEvent) {
        /*Intentionally left void, not used in this class*/
    }

    @Override
    public void handleMVEvent(ChangeDieEvent changeDieEvent) throws InvalidConnectionException, ParseException, InvalidViewException, IOException {
        /*Intentionally left void, not used in this class*/
    }

    @Override
    public void handleMVEvent(ModifiedPlaceEvent modifiedPlaceEvent) throws InvalidConnectionException, ParseException, InvalidViewException, IOException {
        /*Intentionally left void, not used in this class*/
    }

    @Override
    public void handleMVEvent(IsNotYourTurn isNotYourTurn) {
        /*Intentionally left void, not used in this class*/
    }

    @Override
    public void handleMVEvent(ChangedDieEvent changedDieEvent) {
        /*intentionally left empty*/
    }

    @Override
    public void handleMVEvent(MoveDieEvent moveDieEvent) {
        /*Intentionally left void, not used in this class*/
    }

    @Override
    public void handleMVEvent(WrongPlaceEvent wrongPlaceEvent) throws InvalidDieException, InvalidConnectionException, InvalidViewException, ParseException, IOException {
        /*Intentionally left void, not used in this class*/
    }

    @Override
    public void handleMVEvent(IncDecEvent incDecEvent) {
        /*Intentionally left void, not used in this class*/
    }

    @Override
    public void handleMVEvent(InvalidToolEvent invalidToolEvent) {
        /*Intentionally left void, not used in this class*/
    }

    @Override
    public void handleMVEvent(PerformActionEvent performActionEvent) throws InvalidDieException, InvalidConnectionException, InvalidViewException, ParseException, IOException {
        /*Intentionally left void, not used in this class*/
    }

    @Override
    public void handleMVEvent(RetryToolEvent retryToolEvent) throws InvalidDieException, InvalidConnectionException, InvalidViewException, ParseException, IOException {
        /*Intentionally left void, not used in this class*/
    }

    @Override
    public void handleMVEvent(RollingDiceEvent rollDiceEvent) {
        /*Intentionally left void, not used in this class*/
    }

    @Override
    public void handleMVEvent(SetDieEvent setDieEvent) throws InvalidDieException, InvalidConnectionException, ParseException, InvalidViewException, IOException {

    }

    @Override
    public void handleMVEvent(SwapDieEvent swapDieEvent) throws InvalidDieException, InvalidConnectionException, ParseException, InvalidViewException, IOException {

    }

    @Override
    public void handleMVEvent(MiniMenuEvent miniMenuEvent) {

    }

    @Override
    public void handleMVEvent(DoublePlaceEvent doublePlaceEvent) {

    }

    @Override
    public void handleMVEvent(EndGameEvent endGameEvent) {

    }

    @Override
    public void handleMVEvent(RequestCoordEvent requestCoordEvent) throws InvalidDieException, InvalidConnectionException, ParseException, InvalidViewException, IOException {

    }

    @Override
    public void handleMVEvent(RequestColorAndNumberEvent requestColorAndNumberEvent) throws InvalidDieException, InvalidConnectionException, ParseException, InvalidViewException, IOException {

    }


    /**
     * Shows the user interface
     */
    @Override
    public void showUI() {
        /*Intentionally left void, not used in this class*/
    }

    public void setClientTemp(ClientInterface clientTemp) {
        this.clientTemp = clientTemp;
    }

    public ClientInterface getClientTemp() {
        return clientTemp;
    }

    @Override
    public void registerObserver(MyObserver observer) {

        observerCollection.add(observer);
    }

    @Override
    public void unregisterObserver(MyObserver observer) {

        observerCollection.remove(observer);
    }

    @Override
    public void notifyObservers() throws InvalidConnectionException, IOException, InvalidViewException, ParseException, InvalidDieException {
        for (MyObserver o: observerCollection) {
            o.update(this, event);
        }
    }

    @Override
    public void update(MyObservable o, VCEvent arg) throws RemoteException, InvalidConnectionException, InvalidViewException {

    }

    //se lo username dell'evento è ALL
    //inoltro l'evento a tutti i client
    //alrimenti solo al client corrispondente allo username
    @Override
    public void update(MyObservable o, MVEvent arg) throws IOException, InvalidConnectionException, InvalidViewException, ParseException {
        if (arg.getUsername().equals(TO_ALL)) {
            for (String user : clients.keySet()) {
                clients.get(user).sendMVEvent(arg);
            }
        }
        else{
            clients.get(arg.getUsername()).sendMVEvent(arg);
        }
    }

    public void disconnectAlert(MVEvent event) throws InvalidConnectionException, ParseException, InvalidViewException, IOException {
        for (String user : clients.keySet()) {
            clients.get(user).sendMVEvent(event);
        }
    }

    /**
     * Method that pings current player
     * //@param user user of the current player
     */
    public synchronized void serverBeat(String user){
        new Thread(new Runnable(){
            public void run() {
                boolean ok = true;
                while (ok) {
                    try {
                        clients.get(user).ping();
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        removedClients.add(user);
                        try {
                            removeClientFromMap(user);
                        } catch (InvalidDieException |InvalidConnectionException |ParseException |InvalidViewException | IOException e1) {
                            e1.printStackTrace();
                        }


                        ok = false;
                        DisconnectedEvent disconnectedEvent = new DisconnectedEvent(user);
                        try {
                            disconnectAlert(disconnectedEvent);
                        } catch (InvalidConnectionException | ParseException | IOException | InvalidViewException e1) {
                            e1.printStackTrace();

                        }
                    }
                }
            }
        }).start();
    }

    public void createSkipTurnEvent(String username) throws InvalidConnectionException, IOException, InvalidViewException, ParseException, InvalidDieException {
        event = new SkipTurnEvent(username);
        notifyObservers();
    }


    }
