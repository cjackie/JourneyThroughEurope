/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game.data_container;

import java.util.ArrayList;
import java.util.HashMap;
import properties_manager.PropertiesManager;
import ui.Main;

/**
 *
 * @author chaojiewang
 */
public class GameMap {
    private HashMap<String, ArrayList<String> > dict;
    
    public GameMap() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        String path = props.getProperty(Main.JourneyPropertyType.DATA_PATH) +
                props.getProperty(Main.JourneyPropertyType.CITY_NEIGHBOR_FILE);
        
        //TODO open the path and construct the dict!!!
    }
    
    public Boolean hasEdge(String cityName1, String cityName2) {
        //TODO
        return false;
    }
}
