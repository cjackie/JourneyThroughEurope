/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ui;

import game.JourneyGameData;
import game.JourneyGameEventHandler;
import game.JourneyGameManager;
import game.JourneyGameRenderer;
import game.file.JourneyFileManager;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;

/**
 *
 * @author chaojiewang
 */
public class Main extends Application {
    
    static String PROPERTY_TYPES_LIST = "property_types.txt";
    static String UI_PROPERTIES_FILE_NAME = "properties.xml";
    static String PROPERTIES_SCHEMA_FILE_NAME = "properties_schema.xsd";
    static String DATA_PATH = "data/";
    
    @Override
    public void start(Stage primaryStage) {
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        try {
            props.addProperty(JourneyPropertyType.UI_PROPERTIES_FILE_NAME,
                    UI_PROPERTIES_FILE_NAME);
            props.addProperty(JourneyPropertyType.PROPERTIES_SCHEMA_FILE_NAME,
                    PROPERTIES_SCHEMA_FILE_NAME);
            props.addProperty(JourneyPropertyType.DATA_PATH.toString(),
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
                props.getProperty(JourneyPropertyType.WINDOW_WIDTH));
        int height = Integer.parseInt(
                props.getProperty(JourneyPropertyType.WINDOW_HEIGHT));
        Scene scene = new Scene(root, width, height);
        
        primaryStage.setTitle(
                props.getProperty(JourneyPropertyType.WINDOW_TITLE));
        primaryStage.setScene(scene);
        
        //set up overall structure of the code
        JourneyUI ui = new JourneyUI(root);
        JourneyMenuEventHandler menuHandler = new JourneyMenuEventHandler();
        JourneyGameEventHandler gHandler = new JourneyGameEventHandler();
        JourneyGameData data = new JourneyGameData();
        JourneyGameManager msg = new JourneyGameManager();
        JourneyGameRenderer renderer = new JourneyGameRenderer();
        JourneyFileManager fileManager = new JourneyFileManager();
        
        //wire up
        ui.setMenuHandler(menuHandler);
        ui.setGameHandler(gHandler);
        
        menuHandler.setFileManager(fileManager);
        menuHandler.setUi(ui);
        
        gHandler.setGameManager(msg);
        
        msg.setFileManager(fileManager);
        msg.setGameData(data);
        msg.setRenderer(renderer);
        
        renderer.setUi(ui);
        renderer.setGameData(data);
        
        fileManager.setGameData(data);

        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public enum JourneyPropertyType {

        /* SETUP FILE NAMES */
        UI_PROPERTIES_FILE_NAME, PROPERTIES_SCHEMA_FILE_NAME,
        /* DIRECTORIES FOR FILE LOADING */
        DATA_PATH, ABOUT_FILE, HISTORY_TEXT, SPLASH_IMG,
        /* WINDOW */
        WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_TITLE,
        /*path of images*/
        FLIGHT_IMG, MAP_IMG_0, MAP_IMG_1, MAP_IMG_2, MAP_IMG_3,
        /*game stuff*/
        GAME_WIDTH, GAME_HEIGHT, FLIGHT_IMG_HEIGHT, FLIGHT_IMG_WIDTH,LEFT_SEC_WIDTH,
        RIGHT_SEC_WIDTH, SPLASH_WIDTH, SPLASH_HEIGHT,
        /*text string*/
        ABOUT_BACK_BTN, FLIGHT_BTN, SELECT_MAP_BTN, HISTORY_BTN, ABOUT_BTN,
        QUIT_BTN, NUM_OF_PLAYER, NUM_LIST, GO_BTN, PLAYER_NAME, COMPUTER_NAME, 
        NAME,HISTORY_HEADER,START_BTN, LOAD_BTN,
        /*game config stuff*/
        MAX_NUM_OF_PLAYERS, PLAYER_FLAG_IMGS
    }
}
