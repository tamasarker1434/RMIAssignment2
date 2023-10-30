package org.example;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class PrintServices extends UnicastRemoteObject implements IPrintServices {
    HashMap<String,Long> sessionHashMap = new HashMap<String,Long>();
    public PrintServices() throws RemoteException{
        super();
    }

    private boolean sessionCheck(String sessionId){
        if (sessionHashMap.containsKey(sessionId)){
            long timeNano = System.nanoTime() - sessionHashMap.get(sessionId);
            long timeSec = TimeUnit.SECONDS.convert( timeNano,TimeUnit.NANOSECONDS);
            System.out.println("Time Diff= " + timeSec);
            if (timeSec <= 10) {
                System.out.println("Reply from server for SessionId = " + sessionId);
                sessionHashMap.put(sessionId,System.nanoTime());
                return true;
            }
            else {
                sessionHashMap.remove(sessionId);
            }
        }
        return false;
    }
    @Override
    public String signIn(String userId, String password) throws RemoteException {
        String session = null;
        String url = "jdbc:mysql://localhost:3306/jdbcPrinterDB", username ="root", dbPassword="";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,username,dbPassword);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from userprofile");
            while (resultSet.next()){
                if (resultSet.getString("userid").equals(userId) && resultSet.getString("password").equals(password)) {
                    Random random = new Random();
                    int sessionId = random.nextInt();
                    session =Integer.toString(sessionId);
                    sessionHashMap.put(session,System.nanoTime());
                }
            }
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return session;
    }

    @Override
    public String echo(String input) throws RemoteException {
        return "From Server: "+ input;
    }

    @Override
    public String print(String filename, String printer, String sessionId) throws RemoteException {
        if (sessionCheck(sessionId))
            return "Response from server!!!" ;
        else
            return null;
    }

    @Override
    public String queue(String printer, String sessionId) throws RemoteException {
        if (sessionCheck(sessionId))
            return "Response from server!!!" ;
        else
            return null;
    }

    @Override
    public String topQueue(String printer, int job, String sessionId) throws RemoteException {
        if (sessionCheck(sessionId))
            return "Response from server!!!" ;
        else
            return null;
    }

    @Override
    public String start(String sessionId) throws RemoteException {
        if (sessionCheck(sessionId))
            return "Response from server!!!" ;
        else
            return null;
    }

    @Override
    public String stop(String sessionId) throws RemoteException {
        if (sessionCheck(sessionId))
            return "Response from server!!!" ;
        else
            return null;
    }

    @Override
    public String restart(String sessionId) throws RemoteException {
        if (sessionCheck(sessionId))
            return "Response from server!!!" ;
        else
            return null;
    }

    @Override
    public String status(String printer, String sessionId) throws RemoteException {
        if (sessionCheck(sessionId))
            return "Response from server!!!" ;
        else
            return null;
    }

    @Override
    public String readConfig(String parameter, String sessionId) throws RemoteException {
        if (sessionCheck(sessionId))
            return "Response from server!!!" ;
        else
            return null;
    }

    @Override
    public String setConfig(String parameter, String value, String sessionId) throws RemoteException {
        if (sessionCheck(sessionId))
            return "Response from server!!!" ;
        else
            return null;
    }
}
