package servers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientConnection
{
    private Socket clientSocket;
    private PrintWriter messageSender;
    private BufferedReader messageReader;

    private LinkedBlockingQueue<String> clientMessageList;

    public ClientConnection(Socket clientSocket, LinkedBlockingQueue<String> clientMessageList) {
        this.clientSocket = clientSocket;
        this.clientMessageList = clientMessageList;

        getIOStreams();
        new Thread ( new ServerReceiver(clientSocket, messageReader, clientMessageList) ).start();
    }

    private void getIOStreams() {
        try {
            messageSender = new PrintWriter(clientSocket.getOutputStream(), true);
            messageReader = new BufferedReader( new InputStreamReader( clientSocket.getInputStream() ) );
        }
        catch (Exception e) {
            System.out.println("ERROR: Couldn't open Client Socket's IO Streams.");
            e.printStackTrace();
        }
    }

    public void sendMessage(String clientMessage)
    {
        try{
            messageSender.println(clientMessage);
        }
        catch(Exception e) {
            System.out.println("Couldn't write message to client. Perhaps He has disconnected.");
            e.printStackTrace();
        }
    }
}