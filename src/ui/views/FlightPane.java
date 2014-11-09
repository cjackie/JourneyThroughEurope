/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ui.views;

import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import properties_manager.PropertiesManager;
import ui.Main;

/**
 *
 * @author chaojiewang
 */
public class FlightPane extends StackPane {
    
    private ImageView flightImg;
    
    public FlightPane() {
        super();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String path = props.getProperty(Main.JourneyPropertyType.DATA_PATH)
                    + props.getProperty(Main.JourneyPropertyType.FLIGHT_IMG);
        int width = Integer.parseInt(
                props.getProperty(Main.JourneyPropertyType.FLIGHT_IMG_WIDTH));
        int height = Integer.parseInt(
                props.getProperty(Main.JourneyPropertyType.FLIGHT_IMG_HEIGHT));
        
        
        Image img = new Image("file:" + path, width, height, false, true);
           
        flightImg = new ImageView(img);
        this.getChildren().add(flightImg);
        this.setStyle("-fx-background-color:white;");
    }
    
    public ImageView getFlightImg() {
        return flightImg;
    }
}
