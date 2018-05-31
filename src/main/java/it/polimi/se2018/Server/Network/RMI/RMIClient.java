package it.polimi.se2018.Server.Network.RMI;

import it.polimi.se2018.Server.Network.ServerInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIClient implements RMIClientInterface {
    private ServerInterface server;

    public RMIClient(String serverIP, Integer port) {
        try {
            Registry registry = LocateRegistry.getRegistry(serverIP, port);
            server = (ServerInterface) registry.lookup("RMIServer");
            UnicastRemoteObject.exportObject(this, 0);

        } catch (RemoteException | NotBoundException e) {
        }
    }
}