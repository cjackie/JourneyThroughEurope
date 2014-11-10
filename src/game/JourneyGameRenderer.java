/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import properties_manager.PropertiesManager;
import ui.JourneyUI;
import ui.Main;

/**
 *
 * @author chaojiewang
 */
public class JourneyGameRenderer {


    
    private JourneyGameData gameData;
    private JourneyUI ui;
    
    public JourneyGameRenderer() {}
    
    public void render() {
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        //TODO
        //partially done
        //get the gameData and render it
        
        GraphicsContext cg = ui.getGamePane().getGameCanvas().getGraphicsContext2D();
        int h = Integer.parseInt(props.getProperty(Main.JourneyPropertyType.GAME_HEIGHT));
        int w = Integer.parseInt(props.getProperty(Main.JourneyPropertyType.GAME_WIDTH));
        cg.clearRect(0, 0, w, h);
        System.out.println("map id is: " +gameData.getCurrentMap());
        int mapId = gameData.getCurrentMap();
        if (mapId == 0) {
            Image img = new Image("file:"+props.getProperty(Main.JourneyPropertyType.DATA_PATH)+
                    props.getProperty(Main.JourneyPropertyType.MAP_IMGO_0), w, h, false, true);
            cg.drawImage(img, 0, 0);
            
        } else if (mapId == 1) {
            Image img = new Image("file:"+props.getProperty(Main.JourneyPropertyType.DATA_PATH)+
                    props.getProperty(Main.JourneyPropertyType.MAP_IMGO_1), w, h, false, true);
            cg.drawImage(img, 0, 0);
        } else if (mapId == 2) {
            Image img = new Image("file:"+props.getProperty(Main.JourneyPropertyType.DATA_PATH)+
                    props.getProperty(Main.JourneyPropertyType.MAP_IMGO_2), w, h, false, true);
            cg.drawImage(img, 0, 0);
        } else {
            Image img = new Image("file:"+props.getProperty(Main.JourneyPropertyType.DATA_PATH)+
                    props.getProperty(Main.JourneyPropertyType.MAP_IMGO_3), w, h, false, true);
            cg.drawImage(img, 0, 0);
        }
        
    }
    
    public void changeMsg(String msg) {
        //TODO
        //just chnage the scrollpane in the game pane
    }
    
    
    public JourneyGameData getGameData() {
        return gameData;
    }

    public JourneyUI getUi() {
        return ui;
    }

    public void setGameData(JourneyGameData gameData) {
        this.gameData = gameData;
    }

    public void setUi(JourneyUI ui) {
        this.ui = ui;
    }
}
