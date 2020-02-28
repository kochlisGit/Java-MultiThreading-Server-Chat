package clients;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

// ServerConnection Handles connections. It start the receiver's thread and sends messages to server.
public class ServerConnection
{
    private Socket socket;

    private PrintWriter messageWriter;
    private BufferedReader messageReader;

    private Thread clientReceiverThread;

    public ServerConnection(Socket socket, LinkedBlockingQueue<String> serverMessageList) {
        this.socket = socket;

        getIOStreams();
        clientReceiverThread = new Thread( new ClientReceiver(messageReader, serverMessageList) );
        clientReceiverThread.start();
    }

    private void getIOStreams() {
        try {
            messageWriter = new PrintWriter(socket.getOutputStream(), true);
            messageReader = new BufferedReader( new InputStreamReader(socket.getInputStream() ) );
        }
        catch(Exception e) {
            System.out.println("ERROR: Couldn't read server's IO Streams.");
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        try {
            clientReceiverThread.interrupt();
            socket.close();
            messageReader.close();
            messageWriter.close();
        }
        catch (Exception e) {
            System.out.println("ERROR: Couldn't ");
            e.printStackTrace();
        }
    }

    // Send messages to Server. When message.equals( "$" ), the connection is terminated.
    public void sendMessage(String clientMessage) {
        messageWriter.println(clientMessage);
        if ( clientMessage.equals("$") )
            closeConnection();
    }
}
