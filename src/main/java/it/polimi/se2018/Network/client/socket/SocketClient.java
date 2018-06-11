package it.polimi.se2018.Network.client.socket;

import it.polimi.se2018.Model.Event.MVEvent;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.Network.NetworkHandler;
import it.polimi.se2018.Network.client.ClientInterface;
import it.polimi.se2018.Network.server.socket.ListeningThread;
import it.polimi.se2018.View.ViewEvents.VCEvent;

import java.io.IOException;
import java.net.Socket;
import java.rmi.RemoteException;

public class SocketClient implements ClientInterface{
    private static Socket socket;
    private ListeningThread listeningThread;
    private NetworkHandler networkHandler;

    public SocketClient(String serverIP, Integer port, NetworkHandler nh) {
        networkHandler = nh;
        try {
            socket = new Socket(serverIP, port);
        } catch (IOException e) {
        }


        listeningThread = new ListeningThread(socket);
        listeningThread.start();
    }


    @Override
    public void notify(String message) throws RemoteException {

    }

    @Override
    public void sendMVEvent(MVEvent event) {

    }

    /*@Override
    public void loginRequest(String username) {
        VCEvent loginE = new LoginEvent("Socket", username);
        ObjectOutputStream toServer;
        try {
            toServer = new ObjectOutputStream(socket.getOutputStream());
            toServer.writeObject(loginE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/

    @Override
    public void sendEvent(VCEvent event) throws RemoteException {

    }

    @Override
    public void registerObserver(MyObserver observer) {

    }

    @Override
    public void unregisterObserver(MyObserver observer) {

    }

    @Override
    public void notifyObservers() {

    }

    @Override
    public void update(MyObservable o, Object arg) {

    }
}
