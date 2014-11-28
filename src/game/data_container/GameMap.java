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
import java.util.Map;
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
                props.getProperty(Main.JourneyPropertyType.SEA_NEIGBOR_FILE);
        
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
        ArrayList<String> potentialEdges = getLandNeighbors(cityName1);
        if (potentialEdges == null) {
            return false;
        }
        for (String edge : potentialEdges) {
            if (edge.equals(cityName2)) 
                return true;
        }
        return false;
    }
    
    //return false if they are not neighbor at sea
    public boolean neighborHarbor(String harbor1, String harbor2) {
        ArrayList<String> edges = getHarborNeighbors(harbor1);
        if (edges ==  null) {
            return false;
        }
        for (String edge : edges) {
            if (edge.equals(harbor2))
                return true;
        }
        return false;
    }
    
    //null is no neigbors
    public ArrayList<String> getLandNeighbors(String cityName) {
        for (Map.Entry<String, ArrayList<String>> entry : cityMap.entrySet()) {
            if (entry.getKey().equals(cityName)) 
                return entry.getValue();
        }
        return null;
    }
    
    //return null if the city is not a harbor
    public ArrayList<String> getHarborNeighbors(String cityName) {
        for (Map.Entry<String, ArrayList<String>> entry : harborMap.entrySet()) {
            if (entry.getKey().equals(cityName)) 
                return entry.getValue();
        }
        return null;
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
