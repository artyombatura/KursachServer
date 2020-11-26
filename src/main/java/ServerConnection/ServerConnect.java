package ServerConnection;

import Constants.Constants;
import Database.DatabaseAction;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class ServerConnect {
    public static LinkedList<ClientAction> serverList = new LinkedList<>();
    public static  int countClient=0;

    private ServerSocket server;

    public void startServer(){
        try {
            server = new ServerSocket(Constants.PORT);
            System.out.println("Server start ...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void connectNewClientInToServer(){
        DatabaseAction databaseAction = new DatabaseAction();


        try {
            while (true) {
                Socket socket = server.accept();

                try {
                    serverList.add(new ClientAction(socket,databaseAction));
                    System.out.println("Клиент подключился !");
                } catch (IOException e) {

                    socket.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void closeAll(){
        try {

            server.close();
            System.out.println("Сервер остановился !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
