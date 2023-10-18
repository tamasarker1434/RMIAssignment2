package org.example;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPrintServices extends Remote {
    //Sign in Method
    public boolean signIn(String userId, String password) throws RemoteException;
    public String echo(String input) throws RemoteException;
    // prints file filename on the specified printer
    public String print(String filename, String printer) throws RemoteException;
    // lists the print queue for a given printer on the user's display in lines of the form <job number>   <file name>
    public String queue(String printer) throws RemoteException;
    // moves job to the top of the queue
    public String topQueue(String printer, int job) throws RemoteException;
    // starts the print server
    public String start() throws RemoteException;
    // stop the print server
    public String stop() throws RemoteException;
    // stops the print server, clears the print queue and starts the print server again
    public String restart() throws RemoteException;
    // prints status of printer on the user's display
    public String status(String printer) throws RemoteException;
    // prints the value of the parameter on the print server to the user's display
    public String readConfig(String parameter) throws RemoteException;
    // sets the parameter on the print server to value
    public String setConfig(String parameter, String value) throws RemoteException;
}
