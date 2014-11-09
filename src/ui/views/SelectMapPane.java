/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ui.views;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import properties_manager.PropertiesManager;
import ui.Main;

/**
 *
 * @author chaojiewang
 */
public class SelectMapPane extends GridPane {


    private ImageView map0;
    private ImageView map1;
    private ImageView map2;
    private ImageView map3;
    
    public SelectMapPane() {
        super();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String root = props.getProperty(Main.JourneyPropertyType.DATA_PATH);
        String img_path0 = root + props.getProperty(Main.JourneyPropertyType.MAP_IMG_0);
        String img_path1 = root + props.getProperty(Main.JourneyPropertyType.MAP_IMG_1);
        String img_path2 = root + props.getProperty(Main.JourneyPropertyType.MAP_IMG_2);
        String img_path3 = root + props.getProperty(Main.JourneyPropertyType.MAP_IMG_3);
        int windowHeight = Integer.parseInt(props.getProperty(Main.JourneyPropertyType.WINDOW_HEIGHT));
        int windowWidth = Integer.parseInt(props.getProperty(Main.JourneyPropertyType.WINDOW_WIDTH));
        int viewHeight = windowHeight/2;
        int viewWidth = windowWidth/2;
       
        map0 = new ImageView("file:" + img_path0);
        map1 = new ImageView("file:" + img_path1);
        map2 = new ImageView("file:" + img_path2);
        map3 = new ImageView("file:" + img_path3);
        
        //set the layout
        this.setAlignment(Pos.CENTER);
        this.add(map0, 0, 0);
        this.add(map1, 1, 0);
        this.add(map2, 0, 1);
        this.add(map3, 1, 1);
        this.setStyle("-fx-background-color:white;");
    }
    
    public ImageView getMap0() {
        return map0;
    }

    public ImageView getMap1() {
        return map1;
    }

    public ImageView getMap2() {
        return map2;
    }

    public ImageView getMap3() {
        return map3;
    }
    
}
