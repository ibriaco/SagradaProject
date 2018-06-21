package it.polimi.se2018.view.ui.guicontrollers;

import it.polimi.se2018.model.WindowCard;
import it.polimi.se2018.model.Color;
import it.polimi.se2018.model.event.WindowCardEvent;
import it.polimi.se2018.view.ui.GUIView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;


public class GUIChoiceController implements GUIControllerIF {

    private static final double R_HEIGHT = 50;
    private static final double R_WIDTH = 50;

    private GUIView guiView;
    private WindowCardEvent event;

    @FXML
    private Label titleNW;
    @FXML
    private Label titleNE;
    @FXML
    private Label titleSW;
    @FXML
    private Label titleSE;
    @FXML
    private Label diffNW;
    @FXML
    private Label diffNE;
    @FXML
    private Label diffSW;
    @FXML
    private Label diffSE;
    @FXML
    private GridPane gridNW;
    @FXML
    private GridPane gridNE;
    @FXML
    private GridPane gridSW;
    @FXML
    private GridPane gridSE;

    private int index = 0;

    public void setEvent(WindowCardEvent event){
        this.event = event;
        for (WindowCard w : event.getWindowCards()){
            showCard(w, index);
            index++;
        }
    }

    private void showCard(WindowCard windowCard, int index) {
        for (int i = 0; i < windowCard.getRows(); i++) {
            for (int j = 0; j < windowCard.getCols(); j++) {
                putInGrid(index, createRect(windowCard, i, j), j, i);
                putNameAndDiff(windowCard);
            }
        }

    }

    private void putNameAndDiff(WindowCard windowCard) {
        if(index == 0) {
            titleNW.setText(windowCard.getWindowName());
            diffNW.setText(String.valueOf(windowCard.getDifficulty()));
        }
        else if(index == 1) {
            titleNE.setText(windowCard.getWindowName());
            diffNE.setText(String.valueOf(windowCard.getDifficulty()));
        }
        else if(index == 2) {
            titleSW.setText(windowCard.getWindowName());
            diffSW.setText(String.valueOf(windowCard.getDifficulty()));
        }
        else if(index == 3){
            titleSE.setText(windowCard.getWindowName());
            diffSE.setText(String.valueOf(windowCard.getDifficulty()));
        }
    }

    private void putInGrid(int index, Rectangle rect, int j, int i) {
        if(index == 0)
            gridNW.add(rect, j, i);
        else if(index == 1)
            gridNE.add(rect, j, i);
        else if(index == 2)
            gridSW.add(rect, j, i);
        else if(index == 3)
            gridSE.add(rect, j, i);

    }

    private Rectangle createRect(WindowCard windowCard, int i, int j) {
        Rectangle rect = new Rectangle(R_WIDTH, R_HEIGHT, getMatch(windowCard.getGridCell(i, j).getColor()));
        rect.setStrokeType(StrokeType.INSIDE);
        rect.setStroke(javafx.scene.paint.Color.BLACK);
        rect.setStrokeWidth(2);

        return rect;
    }

    private Paint getMatch(Color c){

        return javafx.scene.paint.Color.valueOf(c.toString());
    }


    @Override
    public void setView(GUIView vi) {
        guiView = vi;
    }

    @Override
    public void changeScene() {

    }

    @Override
    public void changeScene(WindowCardEvent event) {

    }

    @Override
    public void reLogin(String state) {

    }
}
