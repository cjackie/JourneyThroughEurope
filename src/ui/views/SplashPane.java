/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ui.views;

import game.file.JourneyFileManager;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import properties_manager.PropertiesManager;
import ui.Main;

/**
 *
 * @author chaojiewang
 */
public class SplashPane extends BorderPane{

    private Button startBtn;
    private Button loadBtn;
    private Button aboutBtn;
    private Button quitBtn;
    
    public SplashPane() {
        super();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        String root = props.getProperty(Main.JourneyPropertyType.DATA_PATH);
        int splashWidth = Integer.parseInt(props.getProperty(
                Main.JourneyPropertyType.SPLASH_WIDTH));
        int splashHeight = Integer.parseInt(props.getProperty(
                Main.JourneyPropertyType.SPLASH_HEIGHT));
        String splashImgPath = root + props.getProperty(Main.JourneyPropertyType.SPLASH_IMG);
        String startBtnStr = props.getProperty(Main.JourneyPropertyType.START_BTN);
        String loadBtnStr = props.getProperty(Main.JourneyPropertyType.LOAD_BTN);
        String aboutBtnStr = props.getProperty(Main.JourneyPropertyType.ABOUT_BTN);
        String quitBtnStr = props.getProperty(Main.JourneyPropertyType.QUIT_BTN);
        
        Image splashImg = new Image("file:"+splashImgPath, splashWidth, splashHeight, false, true);
        ImageView splash = new ImageView(splashImg);
        startBtn = new Button(startBtnStr);
        loadBtn = new Button(loadBtnStr);
        aboutBtn = new Button(aboutBtnStr);
        quitBtn = new Button(quitBtnStr);
        HBox box = new HBox(startBtn, loadBtn, aboutBtn, quitBtn);
        box.setAlignment(Pos.CENTER);
        
        this.setCenter(splash);
        this.setBottom(box);
        this.setStyle("-fx-background-color:white;");
    }
    
    public Button getStartBtn() {
        return startBtn;
    }

    public Button getLoadBtn() {
        return loadBtn;
    }

    public Button getAboutBtn() {
        return aboutBtn;
    }

    public Button getQuitBtn() {
        return quitBtn;
    }
    
}
