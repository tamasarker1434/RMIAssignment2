package org.example;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
public class PrintServices extends UnicastRemoteObject implements IPrintServices {

    public PrintServices() throws RemoteException{
        super();
    }

    @Override
    public boolean signIn(String userId, String password) throws RemoteException {

        boolean loginReturn = false;
        String url = "jdbc:mysql://localhost:3306/jdbcPrinterDB", username ="root", dbPassword="";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,username,dbPassword);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from userprofile");
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(password.getBytes("utf8"));
            String sha1Pass = String.format("%040x", new BigInteger(1, digest.digest()));
            while (resultSet.next()){
                if (resultSet.getString("userid").equals(userId) && resultSet.getString("password").equals(sha1Pass))
                    loginReturn = true;
            }
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return loginReturn;
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
