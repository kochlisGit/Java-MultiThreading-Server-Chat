package components;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;


public class ConnectDialogBox extends Dialog< ArrayList<String> >
{
    private ArrayList<String> resultList;
    private boolean ipEntered;
    private boolean portEntered;
    private boolean usernameEntered;

    public ConnectDialogBox()
    {
        super();

        resultList = new ArrayList<String>();
        ipEntered = false;
        portEntered = false;
        usernameEntered = false;

        // Create the custom dialog.
        setTitle("Connect");
        setHeaderText("Connectiion Settings");

        // Set the button types.
        ButtonType connectButtonType = new ButtonType("Connect", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(connectButtonType, ButtonType.CANCEL);

        // Create the labels & fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding( new Insets(20, 150, 10, 10) );

        TextField ipAddressText = new TextField();
        ipAddressText.setPromptText("IP Address");
        TextField portText = new TextField();
        portText.setPromptText("Port");
        TextField usernameText = new TextField();
        usernameText.setPromptText("Username");

        grid.add(new Label("IP Address:"), 0, 0);
        grid.add(ipAddressText, 1, 0);
        grid.add(new Label("Port:"), 0, 1);
        grid.add(portText, 1, 1);
        grid.add(new Label("Username:"), 0, 2);
        grid.add(usernameText, 1, 2);

        // Enable/Disable connect button depending on whether all fields are completed.
        Node loginButton = getDialogPane().lookupButton(connectButtonType);
        loginButton.setDisable(true);

        ipAddressText.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty() && !portEntered && !usernameEntered);
        });
        portText.textProperty().addListener((observable, oldValue, newValue) -> {
            if ( !newValue.matches("\\d*") )
                portText.setText( newValue.replaceAll("[^\\d]", "") );
            loginButton.setDisable(newValue.trim().isEmpty() && !ipEntered && !usernameEntered);
        });
        usernameText.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty() && !ipEntered && !portEntered);
        });

        getDialogPane().setContent(grid);

        setResultConverter(dialogButton -> {
            if (dialogButton == connectButtonType)
            {
                resultList.add( ipAddressText.getText() );
                resultList.add( portText.getText() );
                resultList.add( usernameText.getText() );
                return resultList;
            }
            return null;
        });
    }
}
