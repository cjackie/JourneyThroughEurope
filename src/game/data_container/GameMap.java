/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game.data_container;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.application.Platform;
import properties_manager.PropertiesManager;
import ui.Main;

/**
 *
 * @author chaojiewang
 */
public class GameMap {
    private HashMap<String, ArrayList<String> > cityMap;
    private HashMap<String, ArrayList<String> > harborMap;
    
    public GameMap() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        String cityMapPath = props.getProperty(Main.JourneyPropertyType.DATA_PATH) +
                props.getProperty(Main.JourneyPropertyType.CITY_NEIGHBOR_FILE);
        String harborMapPath = props.getProperty(Main.JourneyPropertyType.DATA_PATH) +
                props.getProperty(Main.JourneyPropertyType.CITY_NEIGHBOR_FILE);
        
        //construct city map
        cityMap = new HashMap<>();
        List<String> lines = getFile(cityMapPath);
        for (String line : lines) {
            String[] tokens = line.split(",");
            if (tokens.length == 1) {
                //no edge
                continue;
            } else {
                ArrayList<String> edges = new ArrayList<>();
                for (int i = 1; i < tokens.length; i++) {
                    edges.add(tokens[i]);
                }
                cityMap.put(tokens[0], edges);
            }     
        }
        
        //construct sea map
        harborMap = new HashMap<>();
        lines = getFile(harborMapPath);
        for (String line : lines) {
            String[] tokens = line.split(",");
            if (tokens.length == 1) {
                //no edge
                continue;
            } else {
                ArrayList<String> edges = new ArrayList<>();
                for (int i = 1; i < tokens.length; i++) {
                    edges.add(tokens[i]);
                }
                harborMap.put(tokens[0], edges);
            }
        }
        
        
    }
    
    public boolean hasEdge(String cityName1, String cityName2) {
        //TODO
        return false;
    }
    
    public boolean neigborHarbor(String harbor1, String harbor2) {
        //TODO
        return false;
    }
    
    private List<String> getFile(String filePath) {
        Path path = Paths.get(filePath);
        List<String> lines = null;
        try {
            lines = Files.readAllLines(path);
        } catch (Exception err) {
            System.out.println(path.toAbsolutePath()+"   is not found!!!");
            err.printStackTrace();
            Platform.exit();
        }
        return lines;
    }
}
