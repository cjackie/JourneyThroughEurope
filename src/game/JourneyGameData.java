/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game;

import game.data_container.Card;
import game.data_container.City;
import game.data_container.Dice;
import game.data_container.FlightCluster;
import game.data_container.GameMap;
import game.data_container.Player;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
public class JourneyGameData {

    private int currentMap;
    private int remainingMove;
    private GameMap gameMap;
    private FlightCluster flightCluster;
    private Player currentPlayer;
    private ArrayList<Player> allPlayers;
    private ArrayList<City> allCities;
    private Dice dice;
    private ArrayList<Card> cards;
    private ArrayList<Player> humanPlayers;

    
    public JourneyGameData() {}
    
    public void init() {
        //load all relevent datas
        this.currentMap = 0;
        this.remainingMove = -1;
        
        //setting up gameMap
        initGameMap();
        
        //setting up flightCluster
        initFlightCluster();
        
        //setting up allCities
        initAllCity();
        
        //setting up dice
        initDice();
        
        //setting up cards
        initAllCards();
        
        
        //TODO
        /*
        currentPlayer and humanPlayers are specified later by the user
        or load it from the file.
        */
    }
    
    private void initAllCity() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        String cityFilePath = props.getProperty(Main.JourneyPropertyType.DATA_PATH) +
                props.getProperty(Main.JourneyPropertyType.CITIES);
        
        Path path = Paths.get(cityFilePath);
        List<String> lines = null;
        try {
            lines = Files.readAllLines(path);
        } catch (Exception err) {
            System.out.println(path.toAbsolutePath()+"   is not found!!!");
            err.printStackTrace();
            Platform.exit();
        }
        
        allCities = new ArrayList<City>();
        int gameHeight = Integer.parseInt(props.getProperty(Main.JourneyPropertyType.GAME_HEIGHT));
        int gameWidth = Integer.parseInt(props.getProperty(Main.JourneyPropertyType.GAME_WIDTH));
        int gameOrgHeight = Integer.parseInt(props.getProperty(Main.JourneyPropertyType.GAME_ORIGINAL_HEIGHT));
        int gameOrgWidth = Integer.parseInt(props.getProperty(Main.JourneyPropertyType.GAME_ORIGINAL_WIDTH));
        int radius = Integer.parseInt(props.getProperty(Main.JourneyPropertyType.RADIUS));
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            City c = null;
            String[] tokens = line.split(",");
            try {
                String name = tokens[0];
                String color = tokens[1];
                int mapId = Integer.parseInt(tokens[2]) - 1;
                int w = (int) (Double.parseDouble(tokens[3])/gameOrgWidth*gameWidth);
                int h = (int) (Double.parseDouble(tokens[4])/gameOrgHeight*gameHeight);
                c = new City(w, h, radius, mapId, name, color);
                
            } catch (Exception err) {
                err.printStackTrace();
                c = null;
            }
            
            if (c != null) {
                allCities.add(c);
            }
        }
    }
    
    public void initDice() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String dataPath = props.getProperty(Main.JourneyPropertyType.DATA_PATH);
        String diceImgFormat = props.getProperty(Main.JourneyPropertyType.DICE_IMG_FORMAT);
        int sides = Integer.parseInt(props.getProperty(Main.JourneyPropertyType.DICE_SIDES));
        String[] diceImg  = new String[sides];
        for (int i = 1; i <= sides; i++) {
            String format = diceImgFormat + "";
            String imgName = format.replace('?', (char)('0'+i));
            String diceImgPath = dataPath + imgName;
            if (!existFile(diceImgPath)) {
                System.err.println("dice img not found!!" + diceImgPath);
                Platform.exit();
            }
            diceImg[i-1] = diceImgPath;
        }
        this.dice = new Dice(diceImg, sides);
    }
  
    
    public void initAllCards() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        String cardFilePath = props.getProperty(Main.JourneyPropertyType.DATA_PATH) +
                props.getProperty(Main.JourneyPropertyType.CARDS);
        
        List<String> lines = getFile(cardFilePath);
        
        cards = new ArrayList<>();
        
        for (String line : lines) {
            String dataPath = props.getProperty(Main.JourneyPropertyType.DATA_PATH);
            String[] tokens = line.split(",");
            String color = tokens[0];
            System.out.println(color);
            Card.ColorType colorType = null;
            if (color.equals("RED")) {
                colorType = Card.ColorType.RED;
                dataPath = dataPath + "red/";
            }
            if (color.equals("GREEN")) {
                colorType = Card.ColorType.GREEN;
                dataPath = dataPath + "green/";
            }
            if (color.equals("YELLOW")) {
                colorType = Card.ColorType.YELLOW;
                dataPath = dataPath + "yellow/";
            }
            if (colorType == null) {
                System.err.println("init cards, invalid format");
                Platform.exit();
            }
            
            for (int i = 1; i < tokens.length; i++) {
                String cityName = tokens[i];
                String frontImg = cityName+".jpg";
                String backImg = cityName+"_I.jpg";
                if (!existFile(dataPath + frontImg)) {
                    System.err.println("city card img is nor found" + dataPath + frontImg);
                    continue;
                }
                
                if (!existFile(dataPath + backImg)) {
                    //no instruction card
                    Card c = new Card(cityName, dataPath + frontImg, false, null, colorType);
                    cards.add(c);
                } else {
                    Card c = new Card(cityName, dataPath + frontImg, true, dataPath + backImg, colorType);
                    cards.add(c);
                }    
            }
        }
        
    }
    
    public void initFlightCluster() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String flightsDataPath = props.getProperty(Main.JourneyPropertyType.DATA_PATH)
                + props.getProperty(Main.JourneyPropertyType.FLIGHT_TXT);
        List<String> lines = getFile(flightsDataPath);
        flightCluster = new FlightCluster();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (String cityName : line.split(",")) {
                flightCluster.getFlightZone().put(cityName, i+1);
            }
        }
    }
    
    public void initGameMap() {
        gameMap = new GameMap();
    }
    
    public void constructNewGame(HashMap<String, Integer> config) {
        //TODO
        //init players and related
        
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
    
    private Boolean existFile(String filePath){
        File f = new File(filePath);
        return f.exists() && !f.isDirectory();
    }
    
    public GameMap getGameMap() {
        return gameMap;
    }

    public FlightCluster getFlightCluster() {
        return flightCluster;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public ArrayList<Player> getAllPlayers() {
        return allPlayers;
    }

    public ArrayList<City> getAllCities() {
        return allCities;
    }

    public Dice getDice() {
        return dice;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public int getRemainingMove() {
        return remainingMove;
    }

    public ArrayList<Player> getHumanPlayers() {
        return humanPlayers;
    }

    public int getCurrentMap() {
        return currentMap;
    }

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public void setFlightCluster(FlightCluster flightCluster) {
        this.flightCluster = flightCluster;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setAllPlayers(ArrayList<Player> allPlayers) {
        this.allPlayers = allPlayers;
    }

    public void setAllCities(ArrayList<City> allCities) {
        this.allCities = allCities;
    }

    public void setDice(Dice dice) {
        this.dice = dice;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public void setRemainingMove(int remainingMove) {
        this.remainingMove = remainingMove;
    }

    public void setHumanPlayers(ArrayList<Player> humanPlayers) {
        this.humanPlayers = humanPlayers;
    }

    public void setCurrentMap(int currentMap) {
        this.currentMap = currentMap;
    }
}
