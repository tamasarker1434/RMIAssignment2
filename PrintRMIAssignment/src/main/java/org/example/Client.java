package org.example;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
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
        System.out.println("------" + iPrintServices.echo("Server is Connected"));
        ShowLoginMenu();
    }
    private static void ShowLoginMenu() throws RemoteException {
        String userId, password ;
        System.out.printf("Enter Username = ");
        userId = scannerObj.nextLine();
        System.out.printf("Enter Password = ");
        password = scannerObj.nextLine();
        boolean logIn = iPrintServices.signIn(userId, password);
        if (logIn) {
            ShowPrintingMenu();
        }
        else {
            System.out.println("Entered Wrong Username or Password");
            ShowLoginMenu();
        }
    }
    private static void ShowPrintingMenu() throws RemoteException {
        int choice= -1;
        do {
            System.out.printf("%n1.Print%n2.Queue%n3.Top Queue%n4.Start%n5.Stop%n6.Restart%n7.Status%n8.Read Config%n9.Set Config%n0.Logout%n");
            System.out.printf("Enter Choice :");
            String fileName, printerName, parameter;
            int jobNo;
            choice = Integer.parseInt(scannerObj.nextLine());
            switch (choice){
                case 1:
                    System.out.printf("File Name :");
                    fileName = scannerObj.nextLine();
                    System.out.printf("Printer Name :");
                    printerName = scannerObj.nextLine();
                    System.out.println(iPrintServices.print(fileName,printerName));
                    break;
                case 2:
                    System.out.printf("Printer Name :");
                    printerName = scannerObj.nextLine();
                    System.out.println(iPrintServices.queue(printerName));
                    break;
                case 3:
                    System.out.printf("Printer Name :");
                    printerName = scannerObj.nextLine();
                    System.out.printf("Job No :");
                    jobNo = Integer.parseInt(scannerObj.nextLine());
                    System.out.println(iPrintServices.topQueue(printerName,jobNo));
                    break;
                case 4:
                    System.out.println(iPrintServices.start());
                    break;
                case 5:
                    System.out.println(iPrintServices.stop());
                    break;
                case 6:
                    System.out.println(iPrintServices.restart());
                    break;
                case 7:
                    System.out.printf("Printer Name :");
                    printerName = scannerObj.nextLine();
                    System.out.println(iPrintServices.status(printerName));
                    break;
                case 8:
                    System.out.printf("Parameter :");
                    parameter = scannerObj.nextLine();
                    System.out.println(iPrintServices.readConfig(parameter));
                    break;
                case 9:
                    System.out.printf("Parameter :");
                    parameter = scannerObj.nextLine();
                    System.out.printf("Value :");
                    String value = scannerObj.nextLine();
                    System.out.println(iPrintServices.setConfig(parameter,value));
                    break;
                default:
                    if (choice!=0)
                        System.out.println("Enter Valid Choice!!!");
                    break;
            }
        } while (choice != 0);
    }
}
