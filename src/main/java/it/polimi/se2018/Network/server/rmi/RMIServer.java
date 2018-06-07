package it.polimi.se2018.Network.server.rmi;

import it.polimi.se2018.Network.Lobby;
import it.polimi.se2018.Network.client.Client;
import it.polimi.se2018.Network.client.ClientInterface;
import it.polimi.se2018.Network.client.rmi.RMIClientInterface;
import it.polimi.se2018.Network.server.VirtualView;
import it.polimi.se2018.View.VCEvent;
import it.polimi.se2018.Message;
import it.polimi.se2018.Network.LobbyController;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class RMIServer extends UnicastRemoteObject implements RMIServerInterface {

    private LobbyController lobbyController;
    private static final long serialVersionUID = -7098548671967083832L;

    public RMIServer(LobbyController lc) throws RemoteException {
        super(0);
        lobbyController = lc;
    }


    public int loginUser(VCEvent event) throws RemoteException {
        String user = event.getUsername();
        if(lobbyController.checkUser(user)&&
                lobbyController.checkOnlinePlayers()&&
                lobbyController.checkTime()) {
            lobbyController.addInLobby(user);
            System.out.println("Utente " + user + " loggato!");
            return 0;
        }
        else if(!lobbyController.checkOnlinePlayers())
            return -1;
        else if (!lobbyController.checkUser(user))
            return -2;
        else if (!lobbyController.checkTime());
            return -3;
    }

    public void sendPrivateObjective(VCEvent event) throws RemoteException{

    }

    public void sendUser(String username, ClientInterface client){
        lobbyController.getLobby().getVirtualView().getClients().put(username,client);
    }

    public LobbyController getLobbyController() {
        return lobbyController;
    }
}


