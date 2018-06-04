package it.polimi.se2018.Network.Socket;

import it.polimi.se2018.View.VCEvent;
import it.polimi.se2018.Message;
import it.polimi.se2018.Network.LobbyController;
import it.polimi.se2018.Network.RMI.RMIClientInterface;
import it.polimi.se2018.Network.ServerInterface;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.ConnectException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SocketServer implements ServerInterface{
    private static ServerSocket serverSocket;
    private static ConnectionManager connectionsHandler;
    private static List<SocketConnection> socketConnections;
    private LobbyController lobbyController;

    public SocketServer(int port, LobbyController lc){
        try {
            lobbyController = lc;
            serverSocket = new ServerSocket(port);
            connectionsHandler = new ConnectionManager(serverSocket, this);
            socketConnections = new ArrayList<>();
            connectionsHandler.start();
        } catch(IOException ex) {
        }
    }

    public List<SocketConnection> getSocketConnections() {
        return socketConnections;
    }

    public void addSocketConnection(SocketConnection sc){
        socketConnections.add(sc);
    }

    @Override
    public void addClient(RMIClientInterface client){

    }

    @Override
    public void removeClient(RMIClientInterface client){

    }

    @Override
    public void send(Message message){
        ObjectOutputStream b;
        Iterator<SocketConnection> clientIterator = socketConnections.iterator();
        while(clientIterator.hasNext()){
            try {
                b = new ObjectOutputStream(clientIterator.next().getConnectionSocket().getOutputStream());
                b.writeObject(message);

            } catch (IOException e) {
                e.printStackTrace();
            }

            }
        }



    @Override
    public void loginUser(VCEvent event){
        String user = event.getUsername();

        if(lobbyController.checkUser(user)) {
            lobbyController.addInLobby(user);

            System.out.println("Utente loggato!");
        }
        else{
            System.out.println("Utente non loggato!");
        }

    }
}
