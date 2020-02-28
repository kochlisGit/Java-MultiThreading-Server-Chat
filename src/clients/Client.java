package clients;

import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

public class Client
{
    public static final int DEFAULT_PORT = 4;

    private ServerConnection serverConnection;
    private LinkedBlockingQueue<String> serverMessageList;
    private Socket socket;

    private String IPAddress;
    private int port;
    private String username;

    public Client(String IPAddress, int port, String username) {
        this.IPAddress = IPAddress;
        this.port = port;
        this.username = username;
        serverMessageList = new LinkedBlockingQueue<String>();
    }

    // Connects with Server. If connection is accepted, then It starts Listening.
    public boolean connectToServer()
    {
        try {
            socket = new Socket(IPAddress, port);
        } catch (IOException e) {
            System.out.println("ERROR: Couldn't establish connection with the server. Try again.");
            e.printStackTrace();
            return false;
        }

        serverConnection = new ServerConnection(socket, serverMessageList);
        return true;
    }

    public void sendMessage(String clientMessage) {
        if (clientMessage.equals("$"))
            serverConnection.sendMessage(clientMessage);
        else
        {
            String processedMessage = username + "%" +
                    clientMessage + "%" +
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            serverConnection.sendMessage(processedMessage);
        }
    }

    public String getServerMessage()
    {
        if ( !serverMessageList.isEmpty() ) {
            try {
                return serverMessageList.take();
            } catch (InterruptedException e) {
                System.out.println("ERROR: Couldn't take a message from queue.");
                e.printStackTrace();
            }
        }
        return null;
    }
}
