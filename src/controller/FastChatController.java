package controller;

import clients.Client;
import components.ConnectDialogBox;
import components.TextAreaReceiver;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import utils.PDFExporter;

import java.util.ArrayList;
import java.util.Optional;

// This controller connects the front-end with the back-end.
public class FastChatController
{
    @FXML
    private TextArea chatTextArea;
    @FXML
    private TextField messageText;
    @FXML
    private Button sendBtn;
    @FXML
    private MenuItem connectMenu;
    @FXML
    private MenuItem disconnectMenu;

    private Client client;
    private String username;

    public FastChatController() {
        client = null;
    }

    public void sendMessage() {
        client.sendMessage( messageText.getText() );
        messageText.clear();
    }

    public void onEnter(KeyEvent key) {
        if ( key.getCode().equals(KeyCode.ENTER) ) {
            sendMessage();
        }
    }

    public void clearChatArea() {
        chatTextArea.clear();
    }

    public void exportToPDF() {
        new Thread( new PDFExporter( chatTextArea.getText() ) ).start();
    }

    public void connectToServer()
    {
        Optional< ArrayList<String> > optionalList = new ConnectDialogBox().showAndWait();
        if ( optionalList.isPresent() )
        {
            ArrayList<String> settingsList = optionalList.get();
            username = settingsList.get(2);
            client = new Client(settingsList.get(0), Integer.valueOf( settingsList.get(1) ), username);

            if ( client.connectToServer() )
            {
                new Thread( new TextAreaReceiver(client, chatTextArea) ).start();

                messageText.setDisable(false);
                sendBtn.setDisable(false);
                connectMenu.setDisable(true);
                disconnectMenu.setDisable(false);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Connection is Successful");
                alert.setContentText("You may type and receive messages!");

                alert.showAndWait();
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Connection Failed");
                alert.setContentText("Oops, You have failed to connect with the server." +
                                        "\nPlease try again later." +
                                        "\nAlso, make sure the settings are correct.");
                alert.showAndWait();
            }
        }
    }

    public void disconnectFromServer()
    {
        if (client != null)
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Disconnecting from server...");
            alert.setContentText("Are you sure you want to disconnect?");

            Optional<ButtonType> result = alert.showAndWait();
            if( result.isPresent() )
            {
                if (result.get() == ButtonType.OK) {
                    client.sendMessage("$");
                    messageText.setDisable(true);
                    sendBtn.setDisable(true);
                    connectMenu.setDisable(false);
                    disconnectMenu.setDisable(true);
                    client = null;
                }
            }
        }
    }

    public void exitApplication() {
        disconnectFromServer();
        if (client == null)
        {
            Platform.exit();
            System.exit(0);
        }
    }

    public void openHelpBox()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Simple & Fun Project");
        alert.setContentText("This simple chat application was created as a school project." +
                                "There are still lots of improvements to be made." +
                                "I hope You liked it." +
                                "If You can't get it to work, check the instructions on github.");

        alert.showAndWait();
    }
}
