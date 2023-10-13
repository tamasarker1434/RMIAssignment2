package org.example;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PrintServices extends UnicastRemoteObject implements IPrintServices {

    public PrintServices() throws RemoteException{
        super();
    }
    @Override
    public String echo(String input) throws RemoteException {
        return "From Server: "+ input;
    }

    @Override
    public String print(String filename, String printer) throws RemoteException {
        return "Filename:" + filename +" Prints on printer:" + printer ;
    }

    @Override
    public String queue(String printer) throws RemoteException {
        return "Printing Queue for the printer:" + printer;
    }

    @Override
    public String topQueue(String printer, int job) throws RemoteException {
        return "Move the Job no:" + job + " of printer no:" + printer +" to the top.";
    }

    @Override
    public String start() throws RemoteException {
        return "Start The Print Server.";
    }

    @Override
    public String stop() throws RemoteException {
    return "Stop the Print Server";
    }

    @Override
    public String restart() throws RemoteException {
    return "Restart the Print Server";
    }

    @Override
    public String status(String printer) throws RemoteException {
        return "Showing the status of the printer to the user";
    }

    @Override
    public String readConfig(String parameter) throws RemoteException {
        return "Server Parameter:" + parameter;
    }

    @Override
    public String setConfig(String parameter, String value) throws RemoteException {
        return "Set theParameter:"+parameter + " with value:" + value;
    }
}
