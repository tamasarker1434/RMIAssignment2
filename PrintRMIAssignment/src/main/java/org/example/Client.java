package org.example;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.util.Scanner;

import static java.rmi.Naming.lookup;

public class Client {
    static Scanner scannerObj = new Scanner(System.in);
    static IPrintServices iPrintServices;

    static {
        try {
            iPrintServices = (IPrintServices) lookup("rmi://localhost:5000/home");
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        System.out.println("------" + iPrintServices.echo("Server is Connected------"));
        ShowLoginMenu();
    }
    private static void ShowLoginMenu() throws RemoteException {
        String userId, password ;
        System.out.printf("Enter Username = ");
        userId = scannerObj.nextLine();
        System.out.printf("Enter Password = ");
        password = scannerObj.nextLine();
        password = passwordConversion(password);
        String sessionId = iPrintServices.signIn(userId, password);
        if (sessionId != null) {
            ShowPrintingMenu(sessionId);
        }
        else {
            System.out.println("Entered Wrong Username or Password");
            ShowLoginMenu();
        }
    }
    private static String passwordConversion(String password){
        StringBuilder hexString = new StringBuilder();
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] sha256Pass = digest.digest(password.getBytes());
            for (byte b : sha256Pass) {
                hexString.append(String.format("%02x", b));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return hexString.toString();
    }
    private static void ShowPrintingMenu(String sessionId) throws RemoteException {
        String choice= "";
        String response;
        do {
            System.out.printf("%n1.Print%n2.Queue%n3.Top Queue%n4.Start%n5.Stop%n6.Restart%n7.Status%n8.Read Config%n9.Set Config%n0.Logout%n");
            System.out.printf("Enter Choice :");
            choice = scannerObj.nextLine();
            switch (choice){
                case "1":
                    System.out.println("Client sending request with SessionId= "+ sessionId);
                    response = iPrintServices.print("fileName","printerName",sessionId);
                    if(response == null) {
                        choice = "0";
                        System.out.println("Unauthorised User!!!");
                    }
                    else
                        System.out.println(response);
                    break;
                case "2":
                    System.out.println("Client send request with SessionId= "+ sessionId);
                    response = iPrintServices.queue("printerName",sessionId);
                    if(response == null) {
                        choice = "0";
                        System.out.println("Unauthorised User!!!");
                    }
                    else
                        System.out.println(response);
                    break;
                case "3":
                    System.out.println("Client send request with SessionId= "+ sessionId);
                    response = iPrintServices.topQueue("printerName",1,sessionId);
                    if(response == null) {
                        choice = "0";
                        System.out.println("Unauthorised User!!!");
                    }
                    else
                        System.out.println(response);
                    break;
                case "4":
                    System.out.println("Client send request with SessionId= "+ sessionId);
                    response = iPrintServices.start(sessionId);
                    if(response == null) {
                        choice = "0";
                        System.out.println("Unauthorised User!!!");
                    }
                    else
                        System.out.println(response);
                    break;
                case "5":
                    System.out.println("Client send request with SessionId= "+ sessionId);
                    response = iPrintServices.stop(sessionId);
                    if(response == null) {
                        choice = "0";
                        System.out.println("Unauthorised User!!!");
                    }
                    else
                        System.out.println(response);
                    break;
                case "6":
                    System.out.println("Client send request with SessionId= "+ sessionId);
                    response = iPrintServices.restart(sessionId);
                    if(response == null) {
                        choice = "0";
                        System.out.println("Unauthorised User!!!");
                    }
                    else
                        System.out.println(response);
                    break;
                case "7":
                    System.out.println("Client send request with SessionId= "+ sessionId);
                    response = iPrintServices.status("printer",sessionId);
                    if(response == null) {
                        choice = "0";
                        System.out.println("Unauthorised User!!!");
                    }
                    else
                        System.out.println(response);
                    break;
                case "8":
                    System.out.println("Client send request with SessionId= "+ sessionId);
                    response = iPrintServices.readConfig("parameter",sessionId);
                    if(response == null) {
                        choice = "0";
                        System.out.println("Unauthorised User!!!");
                    }
                    else
                        System.out.println(response);
                    break;
                case "9":
                    System.out.println("Client send request with SessionId= "+ sessionId);
                    response = iPrintServices.setConfig("parameter","value",sessionId);
                    if(response == null) {
                        choice = "0";
                        System.out.println("Unauthorised User!!!");
                    }
                    else
                        System.out.println(response);
                    break;
                default:
                    if (choice!="0")
                        System.out.println("Enter Valid Choice!!!");
                    break;
            }
        } while (choice != "0");
    }
}
