/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game;

import ui.JourneyUI;

/**
 *
 * @author chaojiewang
 */
public class JourneyGameRenderer {


    
    private JourneyGameData gameData;
    private JourneyUI ui;
    
    public JourneyGameRenderer() {}
    
    public void render() {
        //TODO
        //get the gameData and render it
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
