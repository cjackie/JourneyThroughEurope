/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game.file;

import game.JourneyGameData;
import java.util.HashMap;

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
    }
    
    public void startNewGame(HashMap<String, Integer> config){
        //TODO
        //1 is player, 0 is computer
        //String is the name given to the entity.
        gameData.init();
    }
    
    public JourneyGameData getGameData() {
        return gameData;
    }

    public void setGameData(JourneyGameData gameData) {
        this.gameData = gameData;
    }
    
}
