package it.polimi.se2018.view.ui.guicontrollers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.view.ui.GUIView;
import it.polimi.se2018.view.viewevents.VCEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GUILoginController implements GUIControllerIF{

    private VCEvent myEvent;
    private GUIView guiView;
    private static final Logger LOGGER = Logger.getLogger( GUILoginController.class.getName() );
    private static final String ALERT_MESSAGE = "Insert a valid connection type and username please.";

    @FXML
    JFXButton loginBtn;
    @FXML
    JFXToggleButton rmiBtn;
    @FXML
    JFXToggleButton socketBtn;
    @FXML
    JFXTextField userField;

    @FXML
    public void handleLogin() throws InvalidConnectionException, IOException, InvalidViewException, ParseException {
        String c;
        if((rmiBtn.isSelected() || socketBtn.isSelected()) && !userField.getText().equals("")) {

            if (rmiBtn.isSelected())
                c = "1";
            else
                c = "2";

            guiView.createNH(c);
            guiView.setUsername(userField.getText());
            guiView.createLoginEvent();
        }
        else
            GUIControllerUtils.makeAlertError(ALERT_MESSAGE);
    }

    public void handleToggle(MouseEvent mouseEvent) {
        if(mouseEvent.getSource().equals(rmiBtn)) {
            rmiBtn.setSelected(true);
            socketBtn.setSelected(false);
        }
        else{
            rmiBtn.setSelected(false);
            socketBtn.setSelected(true);
        }
    }

    @Override
    public void changeScene(){
        Stage stage = (Stage) rmiBtn.getScene().getWindow();

        Scene scene = stage.getScene();

        URL url = null;
        try {
            url = new File("src/main/resources/GUIUtils/waitingLobby.fxml").toURI().toURL();
        } catch (MalformedURLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        Parent root = null;
        FXMLLoader loader = null;
        try{
            loader = new FXMLLoader(url);
            root = loader.load();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        guiView.setGuiWaitingLobbyController(loader.getController());
        stage.setHeight(540);
        stage.setWidth(960);
        scene.setRoot(root);
    }

    @FXML
    public void reLogin(String message){
        resetScreen();
        GUIControllerUtils.makeAlertError(message);
    }

    @Override
    public void setView(GUIView vi) {
        guiView = vi;
    }

    private void resetScreen(){
        loginBtn.setDisableVisualFocus(true);
        rmiBtn.setSelected(false);
        socketBtn.setSelected(false);
        userField.setText("");
    }
}
