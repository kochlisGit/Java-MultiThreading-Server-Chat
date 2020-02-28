package servers;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerSender implements Runnable
{
    private ArrayList<ClientConnection> clientConnectionList;
    private LinkedBlockingQueue<String> clientMessageList;

    public ServerSender(ArrayList<ClientConnection> clientConnectionList,
                        LinkedBlockingQueue<String> clientMessageList) {
        this.clientConnectionList = clientConnectionList;
        this.clientMessageList = clientMessageList;
    }

    @Override
    public void run()
    {
        while (true)
        {
            if ( !clientMessageList.isEmpty() )
            {
                String clientMessage = getClientMessage();
                if (clientMessage != null)
                    for (ClientConnection client : clientConnectionList)
                        client.sendMessage(clientMessage);
            }
        }
    }

    private String getClientMessage() {
        String clientMessage = null;
        try {
            clientMessage = clientMessageList.take();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return clientMessage;
    }
}