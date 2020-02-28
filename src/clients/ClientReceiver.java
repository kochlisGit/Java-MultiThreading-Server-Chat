package clients;

import java.io.BufferedReader;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientReceiver extends Thread
{
    private BufferedReader messageReader;
    private LinkedBlockingQueue<String> serverMessageList;
    private boolean isRunning;

    public ClientReceiver(BufferedReader messageReader, LinkedBlockingQueue<String> serverMessageList) {
        this.messageReader = messageReader;
        this.serverMessageList = serverMessageList;
        isRunning = true;
    }

    @Override
    public void interrupt() {
        super.interrupt();
        isRunning = false;
    }

    @Override
    public void run()
    {
        String serverMessage;
        String[] splittedMessage;
        while(isRunning){
            try{
                serverMessage = messageReader.readLine();
                splittedMessage = serverMessage.split("%", 3);
                serverMessage = new String( splittedMessage[0] + "\n" + splittedMessage[1] + "\n" + splittedMessage[2] );
                serverMessageList.put(serverMessage);
                System.out.println("Received Message from Server: " + serverMessage);
            }
            catch(Exception e) {
                System.out.println("ERROR: Couldn't listen to server's messages.");
                isRunning = false;
            }
        }
    }
}
