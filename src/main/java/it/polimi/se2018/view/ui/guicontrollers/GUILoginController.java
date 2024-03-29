package it.polimi.se2018.view.ui.guicontrollers;

import static it.polimi.se2018.view.ui.guicontrollers.GUIControllerUtils.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidDieException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.model.event.UpdateGameEvent;
import it.polimi.se2018.org.json.simple.parser.ParseException;
import it.polimi.se2018.view.ui.GUIView;
import it.polimi.se2018.view.viewevents.VCEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**Controller class of the GUI scene where the client can login to the server, selecting the connection type and the username.
 * @author Gregorio Galletti
 */

public class GUILoginController implements GUIControllerIF{

    private VCEvent myEvent;
    private GUIView guiView;
    private static final Logger LOGGER = Logger.getLogger( GUILoginController.class.getName() );
    private static final String ALERT_MESSAGE = "Insert a valid connection type and username please.";
    private StackPane stack = new StackPane();

    @FXML
    JFXButton loginBtn;
    @FXML
    JFXToggleButton rmiBtn;
    @FXML
    JFXToggleButton socketBtn;
    @FXML
    JFXTextField userField;
    @FXML
    AnchorPane anchor;
    private boolean firstLogin = true;

    @FXML
    public void initialize(){
        stack.setLayoutX(100);
        stack.setLayoutY(100);
        anchor.getChildren().add(stack);
    }

    @FXML
    public void handleLogin() throws InvalidConnectionException, IOException, InvalidViewException, ParseException, InvalidDieException {
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
            setupDialog(ALERT_MESSAGE);
    }

    private void setupDialog(String message) {
        makeDialog(message, stack, ERROR_TYPE, "");
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


        Parent root = null;
        FXMLLoader loader = null;
        try{
            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUIUtils/waitingLobby.fxml"));

            root = loader.load();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        guiView.setGuiWaitingLobbyController(loader.getController());
        stage.setHeight(540);
        stage.setWidth(960);
        stage.setOnCloseRequest(event -> System.exit(0));
        scene.setRoot(root);
    }

    @FXML
    public void reLogin(String message) throws RemoteException {
        resetScreen();
        guiView.destroyNH();
        setupDialog(message);
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

    public void returnToGame(UpdateGameEvent updateGameEvent) {
        Stage stage = (Stage) anchor.getScene().getWindow();

        Scene scene = stage.getScene();

        /*
        URL url = null;

        try {
            url = new File("src/main/resources/GUIUtils/gameScreen.fxml").toURI().toURL();
        } catch (MalformedURLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        */
        Parent root = null;
        FXMLLoader loader = null;
        try{
            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUIUtils/gameScreen.fxml"));

            root = loader.load();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        GUIGameScreenController gameCtrl = loader.getController();
        gameCtrl.setView(guiView);
        guiView.setGuiGameScreenController(gameCtrl);

        stage.setHeight(600);
        stage.setWidth(800);
        //stage.setResizable(true);
        stage.setMinHeight(600);
        stage.setMinWidth(800);
        //stage.minHeightProperty().bind(stage.widthProperty().multiply(0.75));
        //stage.maxHeightProperty().bind(stage.widthProperty().multiply(0.75));
        //stage.setFullScreen(false);
        stage.centerOnScreen();
        scene.setRoot(root);
        stage.setOnCloseRequest(event -> System.exit(0));

        guiView.getGuiGameScreenController().updateScreen(updateGameEvent);
    }

}