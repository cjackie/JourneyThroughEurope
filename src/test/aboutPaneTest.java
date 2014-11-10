/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import java.util.TimerTask;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import ui.Main;
import ui.views.AboutPane;
import ui.views.FlightPane;
import ui.views.GamePane;
import ui.views.GameSetupPane;
import ui.views.HistoryPane;
import ui.views.SelectMapPane;
import ui.views.SplashPane;

/**
 *
 * @author chaojiewang
 */
public class aboutPaneTest extends Application{
    
    static String PROPERTY_TYPES_LIST = "property_types.txt";
    static String UI_PROPERTIES_FILE_NAME = "properties.xml";
    static String PROPERTIES_SCHEMA_FILE_NAME = "properties_schema.xsd";
    static String DATA_PATH = "data/";
    
    @Override
    public void start(Stage primaryStage) {
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        try {
            props.addProperty(Main.JourneyPropertyType.UI_PROPERTIES_FILE_NAME,
                    UI_PROPERTIES_FILE_NAME);
            props.addProperty(Main.JourneyPropertyType.PROPERTIES_SCHEMA_FILE_NAME,
                    PROPERTIES_SCHEMA_FILE_NAME);
            props.addProperty(Main.JourneyPropertyType.DATA_PATH.toString(),
                    DATA_PATH);
            props.loadProperties(UI_PROPERTIES_FILE_NAME,
                    PROPERTIES_SCHEMA_FILE_NAME);
            props.loadProperties(UI_PROPERTIES_FILE_NAME,
                    PROPERTIES_SCHEMA_FILE_NAME);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        
        
        StackPane root = new StackPane();
        
        int width = Integer.parseInt(
                props.getProperty(Main.JourneyPropertyType.WINDOW_WIDTH));
        int height = Integer.parseInt(
                props.getProperty(Main.JourneyPropertyType.WINDOW_HEIGHT));
        Scene scene = new Scene(root, width, height);
        
        primaryStage.setTitle(
                props.getProperty(Main.JourneyPropertyType.WINDOW_TITLE));
        primaryStage.setScene(scene);
        
        
        //root.getChildren().add(new AboutPane());  //test AboutPane
        
        //root.getChildren().add(new FlightPane()); //test FlightPane
        
        /*
        GamePane p = new GamePane();
        int gameHeight = Integer.parseInt(props.getProperty(Main.JourneyPropertyType.GAME_HEIGHT));
        int gameWidth = Integer.parseInt(props.getProperty(Main.JourneyPropertyType.GAME_WIDTH));
        p.getGameCanvas().getGraphicsContext2D().setFill(Color.BLUE);;
        p.getGameCanvas().getGraphicsContext2D().fillRect(0,0,gameWidth,gameHeight);
        p.getMsgBoard().setContent(new Text("msg board"));
        p.getLeftSection().setStyle("-fx-background-color:green");
        root.getChildren().add(p);  //test GamePane
        */
        
        /*
        int numOfPlayers = 5;
        GameSetupPane p = new GameSetupPane();
        p.setNumberOfPlayer(numOfPlayers);

        GridPane gridPane = p.getSelectionGrid();
        for (int i = 0; i < numOfPlayers; i++) {
            gridPane.getChildren().remove(p.getGrid().get(i));
        }
        
        int x = 0;
        int y = 0;
        for (int i = 0; i < numOfPlayers; i++) {
            gridPane.add(p.getGrid().get(i), x, y);
            x++;
            if (x >= 3) {
                x=0;
                y++;
            }
        }
        root.getChildren().add(p);    //test game setup pane
        */
        
        //root.getChildren().add(new HistoryPane());  //test history pane
        
        //root.getChildren().add(new SelectMapPane());  //test select map pane
        
        //root.getChildren().add(new SplashPane());
        
        System.out.println("uncomment lines to test..!!!");
        
        
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    
}
