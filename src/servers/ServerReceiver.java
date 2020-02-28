package servers;

import java.io.BufferedReader;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerReceiver implements Runnable
{
    private Socket clientSocket;
    private BufferedReader messageReader;
    LinkedBlockingQueue<String> clientMessageList;

    public ServerReceiver(Socket clientSocket, BufferedReader messageReader, LinkedBlockingQueue<String> clientMessageList) {
        this.clientSocket = clientSocket;
        this.messageReader = messageReader;
        this.clientMessageList = clientMessageList;
    }

    @Override
    public void run()
    {
        String clientMessage;
        while ( !clientSocket.isClosed() )
        {
            clientMessage = receiveClientMessage();
            if ( clientMessage == null || clientMessage.equals("$") )
            {
                closeReader();
                closeSocket();
            }
            else
                pushMessageToQueue(clientMessage);
        }
    }

    private String receiveClientMessage()
    {
        String clientMessage;
        try {
            clientMessage = messageReader.readLine();
        }
        catch (Exception e) {
            System.out.println("ERROR: Couldn't receive Client's message. Maybe client has disconnected.");
            e.printStackTrace();
            return null;
        }
        return clientMessage;
    }

    private void pushMessageToQueue(String clientMessage) {
        try {
            clientMessageList.put(clientMessage);
        } catch (InterruptedException e) {
            System.out.println("ERROR: Couldn't put clientMessage to Server's Queue. Queue might be full.");
            e.printStackTrace();
        }
    }

    private void closeReader() {
        try {
            messageReader.close();
        }
        catch (Exception e) {
            System.out.println("ERROR: Couldn't close BufferedReader. Maybe It's already closed.");
            e.printStackTrace();
        }
    }
    private void closeSocket() {
        try {
            clientSocket.close();
            System.out.println("Connection with the client " +
                    clientSocket.getLocalAddress().getHostAddress() +
                    " has been terminated.");
        }
        catch (Exception e) {
            System.out.println("ERROR: Couldn't close client's socket. Maybe It's already closed.");
            e.printStackTrace();
        }
    }
}
