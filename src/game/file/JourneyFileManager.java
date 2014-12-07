/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game.file;

import game.JourneyGameData;
import game.data_container.Card;
import game.data_container.Player;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.application.Platform;
import jdk.nashorn.internal.parser.TokenStream;
import properties_manager.PropertiesManager;
import ui.Main;

/**
 *
 * @author chaojiewang
 */
public class JourneyFileManager {
   
    private JourneyGameData gameData;
    
    public JourneyFileManager() {}
    
    
    public void save() {
        //TODO
        //save the state of the game from gameData
    }
    
    public void load() {
        //TODO
        //clear out game data and restore the state
        System.out.println("require to load the data");
        gameData.init();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String path = props.getProperty(Main.JourneyPropertyType.DATA_PATH)
                + props.getProperty(Main.JourneyPropertyType.SAVED_DATA_TEXT);
        try {
            List<String> lines = getFile(path);
            String[] firstLine = lines.get(0).split(",");
            
            int numberOfPlayer = Integer.parseInt(firstLine[0]);
            String currentPlayer = firstLine[1];
            int remainingMoves = Integer.parseInt(firstLine[2]);
            String state = firstLine[3];
            
            int numberOfFlights = Integer.parseInt(
                    props.getProperty(Main.JourneyPropertyType.NUM_FLIGHT_PLANS));
            String pieceFormat = props.getProperty(Main.JourneyPropertyType.DATA_PATH) +
                    props.getProperty(Main.JourneyPropertyType.PIECE_PATH_FORMAT);
            ArrayList<Player> players = new ArrayList<>();
            int i;
            for (i = 1; i <= numberOfPlayer; i++) {
                String[] tokens = lines.get(i).split(",");
                String playerName = tokens[0];
                int computerOrNot = Integer.parseInt(tokens[1]);
                String atCity = tokens[2];
                ArrayList<String> cards = new ArrayList<>();
                for (int j = 0; j < tokens.length - 3; j++) {
                    cards.add(tokens[j+3]);
                }
                
                ArrayList<Card> cardsOnHand = new ArrayList<>();
                for (int j = 0; j < cards.size(); j++) {
                    cardsOnHand.add(gameData.getCardByName(cards.get(j)));
                }
                
                String piecePath = pieceFormat.replace('?', (char)('0'+i-1));
                
                if (computerOrNot == 0){
                    Player p = new Player(atCity, false, piecePath, numberOfFlights,playerName);
                    p.setCardsOnHand(cardsOnHand);
                    players.add(p);
                } else {
                    Player p = new Player(atCity, true, piecePath, numberOfFlights,playerName);
                    p.setCardsOnHand(cardsOnHand);
                    players.add(p);
                }         
            }
            
            String historyContent = "";
            while (i < lines.size()) {
                historyContent += lines.get(i) + "\n";
                i++;
            }
            
            gameData.loadGame(currentPlayer, remainingMoves, state, players, historyContent);
            
        } catch (Exception e) {
            System.err.println("error when loading the data!!!");
            e.printStackTrace();
            Platform.exit();
        }
    }
    
    public void startNewGame(HashMap<String, Integer> config){
        //TODO
        //1 is player, 0 is computer
        //String is the name given to the entity.
        gameData.init();
        gameData.constructNewGame(config);
    }
    
    public JourneyGameData getGameData() {
        return gameData;
    }

    public void setGameData(JourneyGameData gameData) {
        this.gameData = gameData;
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
