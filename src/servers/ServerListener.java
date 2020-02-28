package servers;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerListener implements Runnable
{
    private ServerSocket serverSocket;
    private int port;
    private int backlog;
    private boolean serverIsRunning;

    private ArrayList<ClientConnection> clientConnectionList;
    private LinkedBlockingQueue<String> clientMessageList;

    public ServerListener(int port, int backlog,
                          ArrayList<ClientConnection> clientConnectionList,
                          LinkedBlockingQueue<String> clientMessageList)
    {
        this.port = port;
        this.backlog = backlog;
        this.clientConnectionList = clientConnectionList;
        this.clientMessageList = clientMessageList;
    }

    @Override
    public void run()
    {
        openServerSocket();
        while (serverIsRunning)
            connectClients();
        closeServerSocket();
    }

    private void openServerSocket()
    {
        try {
            serverSocket = new ServerSocket( port, backlog, InetAddress.getLocalHost() );
            serverIsRunning = true;
        }
        catch (Exception e) {
            System.out.println("ERROR: Couldn't open ServerSocket with: " +
                    "\nPort: " + String.valueOf(port) +
                    "\nBacklog" + String.valueOf(backlog) +
                    "\nIP Address: Local Host IP Address.\nThe program will now exit.");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private void closeServerSocket() {
        try {
            serverSocket.close();
        }
        catch (Exception e) {
            System.out.println("ERROR: Server's socket couldn't not be closed. It might still be in use.");
            e.printStackTrace();
        }
    }

    private void connectClients()
    {
        System.out.println("Waiting for a new connection...");

        try {
            Socket clientSocket = serverSocket.accept();
            clientConnectionList.add( new ClientConnection(clientSocket, clientMessageList) );

            System.out.println( "Started connection with client " + clientSocket.getLocalAddress() );
        }
        catch (Exception e) {
            System.out.println("ERROR: Server couldn't accept a client.");
            e.printStackTrace();
        }
    }
}