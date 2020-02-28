package servers;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class Server
{
    private static final int DEFAULT_PORT = 4;
    private static final int DEFAULT_BACKLOG = 10;

    private int port;
    private int backlog;

    private ArrayList<ClientConnection> clientConnectionList;
    private LinkedBlockingQueue<String> clientMessageList;

    public Server(int port, int backlog) {
        this.port = port;
        this.backlog = backlog;
        clientConnectionList = new ArrayList<ClientConnection>();
        clientMessageList = new LinkedBlockingQueue<String>();
    }

    public void start()
    {
        new Thread ( new ServerListener(port, backlog, clientConnectionList, clientMessageList) ).start();
        new Thread( new ServerSender(clientConnectionList, clientMessageList) ).start();
    }

    public static void main(String[] Args) {
        Server server = new Server(Server.DEFAULT_PORT, Server.DEFAULT_BACKLOG);
        server.start();
    }
}
