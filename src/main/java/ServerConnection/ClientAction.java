package ServerConnection;

import Database.DatabaseAction;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;

public class ClientAction extends Thread {
    private final Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;
    private final int currentCountClient= ServerConnect.countClient++;

    private final DatabaseAction databaseAction;

    public ClientAction(Socket socket,DatabaseAction databaseAction) throws IOException {

        this.socket = socket;
        this.databaseAction = databaseAction;

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        start();

    }

    @Override
    public void run() {

        try {
            doSomeActions();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void doSomeActions() throws IOException {
        while (true) {
            switch (in.readLine()) {

                case "авторизация":
                    chekAdmin();
                    break;

                case "вход":
                    auth();
                    break;

                case "все рабочии":
                    showAllWorkers();
                    break;

                case "удалить рабочего":
                      deleteWorker();
                    break;

                case "добавить рабочего":
                     addWorker();
                    break;
                case "расчет зарплаты":
                    calculate();
                    break;
            }
        }
    }

    private void auth() {
        String[] messageFromClient;
        try {
            messageFromClient = in.readLine().split(" ");
            if(databaseAction.auth(messageFromClient[0],messageFromClient[1])){
                ServerConnect.serverList.get(currentCountClient).send("true");
            }
            else{
                ServerConnect.serverList.get(currentCountClient).send("false");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void calculate() {
        String[] messageFromClient;
        try {
            messageFromClient = in.readLine().split(" ");
            int hours = Integer.parseInt(messageFromClient[0]);
            int rate = Integer.parseInt(messageFromClient[1]);
            int salaryByContract = Integer.parseInt(messageFromClient[2]);
            int res =hours*rate;

            ServerConnect.serverList.get(currentCountClient).send(String.valueOf(res));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addWorker() {
        String[] messageFromClient;
        try {
            messageFromClient = in.readLine().split(" ");
            databaseAction.addWorkerInDataBase(messageFromClient[0],messageFromClient[1],messageFromClient[2], Integer.parseInt(messageFromClient[3]));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteWorker() {
        try {
            databaseAction.deleteWorker(Integer.parseInt(in.readLine()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAllWorkers() {

            LinkedList<String> workers = new LinkedList<>();
            databaseAction.showAllWorkers(workers);


        ServerConnect.serverList.get(currentCountClient).send(String.valueOf(workers.size()));

            for(String s:workers){
                ServerConnect.serverList.get(currentCountClient).send(s);
             }
    }

    private void chekAdmin() {

        String[] messageFromClient;
        try {
            messageFromClient = in.readLine().split(" ");

           if( databaseAction.authorization(messageFromClient[0],messageFromClient[1])){
               ServerConnect.serverList.get(currentCountClient).send("true");

           }
           else{
               ServerConnect.serverList.get(currentCountClient).send("false");

           }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    private void send(String message) {
        try {
            out.write(message + "\n");
            out.flush();
        } catch (IOException ignored) {}
    }
}
