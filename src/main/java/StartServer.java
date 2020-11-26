import ServerConnection.ServerConnect;

public class StartServer {
    public static void main(String[] args) {
        ServerConnect serverConnect = new ServerConnect();
        serverConnect.startServer();
        serverConnect.connectNewClientInToServer();
        serverConnect.closeAll();
    }

}
