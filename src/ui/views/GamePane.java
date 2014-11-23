/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ui.views;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;
import ui.Main;

/**
 *
 * @author chaojiewang
 */
public class GamePane extends BorderPane {


    
    private Canvas gameCanvas;
    private VBox leftSection;
    private VBox cardSection;
    private ScrollPane msgBoard;
    private VBox rightSection;
    private ImageView diceImg;
    private Button selectMapBtn;
    private Button historyBtn;
    private Button flightBtn;
    private Button endBtn;
    private Button aboutBtn;
    private Button quitBtn;
    private StackPane gameContainer;
    
    
    public GamePane() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        int gameWidth = Integer.parseInt(props.getProperty(
                Main.JourneyPropertyType.GAME_WIDTH));
        int gameHeight = Integer.parseInt(props.getProperty(
                Main.JourneyPropertyType.GAME_HEIGHT));
        gameCanvas = new Canvas(gameWidth,gameHeight);
        
        leftSection = new VBox();
        leftSection.setPrefWidth(
           Integer.parseInt(props.getProperty(Main.JourneyPropertyType.LEFT_SEC_WIDTH))
        );
        cardSection = new VBox();
        msgBoard = new ScrollPane();
        msgBoard.setHbarPolicy(ScrollBarPolicy.NEVER);
        msgBoard.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        leftSection.getChildren().addAll(cardSection);
        
        rightSection = new VBox();
        diceImg = new ImageView();
        flightBtn = new Button(
                props.getProperty(Main.JourneyPropertyType.FLIGHT_BTN));
        selectMapBtn = new Button(
                props.getProperty(Main.JourneyPropertyType.SELECT_MAP_BTN));
        endBtn = new Button(
                props.getProperty(Main.JourneyPropertyType.END_BTN));
        historyBtn = new Button(
                props.getProperty(Main.JourneyPropertyType.HISTORY_BTN));
        aboutBtn = new Button(
                props.getProperty(Main.JourneyPropertyType.ABOUT_BTN));
        quitBtn = new Button(
                props.getProperty(Main.JourneyPropertyType.QUIT_BTN));
        rightSection.getChildren().addAll(diceImg, flightBtn, selectMapBtn, endBtn,
                                        historyBtn, aboutBtn, quitBtn,msgBoard);
        rightSection.setPrefWidth(
            Integer.parseInt(props.getProperty(Main.JourneyPropertyType.RIGHT_SEC_WIDTH))
        );
        
        //set up layout
        this.setLeft(leftSection);
        this.setCenter(gameCanvas);
        this.setRight(rightSection);
        this.setStyle("-fx-background-color:white;");
    }
    
    public Canvas getGameCanvas() {
        return gameCanvas;
    }

    public ScrollPane getMsgBoard() {
        return msgBoard;
    }

    public VBox getLeftSection() {
        return leftSection;
    }

    public Button getHistoryBtn() {
        return historyBtn;
    }

    public Button getFlightBtn() {
        return flightBtn;
    }
    
    public Button getEndBtn() {
        return endBtn;
    }

    public Button getAboutBtn() {
        return aboutBtn;
    }

    public Button getQuitBtn() {
        return quitBtn;
    }

    public StackPane getGameContainer() {
        return gameContainer;
    }
    
    public VBox getRightSection() {
        return rightSection;
    }
    
    public VBox getCardSection() {
        return cardSection;
    }
    
    public ImageView getDiceImg() {
        return diceImg;
    }
    
    public Button getSelectMapBtn() {
        return selectMapBtn;
    }
}
