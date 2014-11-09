/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game;

import game.data_container.Card;
import game.file.JourneyFileManager;

/**
 *
 * @author chaojiewang
 */
public class JourneyGameManager {

    
    private JourneyFileManager fileManager;
    private JourneyGameData gameData;
    private JourneyGameRenderer renderer;
    
    public JourneyGameManager() {}
    
    private void triggerSpecialEvent(Card c) {
        //TODO
    }
    
    public Boolean moveTo(double x, double y) {
        //TODO
        return false;
    }
    
    public Boolean flightTo(double x, double y) {
        //TODO
        return false;
    }
    
    public void save() {
        //TODO
    }
    
    public Boolean rollDice() {
        //TODO
        return false;
    }
    
    public void render() {
        //TODO
    }
    
    public void selectMap(int id) {
        //TODO
        //0: left top, 1:right top, 2:left bottom, 3, right bottom
    }
    
    public Boolean won() {
        //TODO
        return false;
    }
    
    public void displayMsg(String msg) {
        //TODO
    }
        
    public void setFileManager(JourneyFileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void setGameData(JourneyGameData gameData) {
        this.gameData = gameData;
    }

    public void setRenderer(JourneyGameRenderer renderer) {
        this.renderer = renderer;
    }

    public JourneyFileManager getFileManager() {
        return fileManager;
    }

    public JourneyGameData getGameData() {
        return gameData;
    }

    public JourneyGameRenderer getRenderer() {
        return renderer;
    }
}
