package components;

import clients.Client;
import javafx.scene.control.TextArea;

public class TextAreaReceiver implements Runnable
{
    Client client;
    TextArea chatTextArea;

    public TextAreaReceiver(Client client, TextArea chatTextArea) {
        this.client = client;
        this.chatTextArea = chatTextArea;
    }

    @Override
    public void run()
    {
        String serverMessage;
        while ( client != null )
        {
            serverMessage = client.getServerMessage();
            if (serverMessage != null)
                chatTextArea.appendText(serverMessage + "\n");
        }
    }
}
