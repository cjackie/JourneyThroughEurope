/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game;

import game.data_container.Card;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author chaojiewang
 */
public class JourneyGameEventHandler {
    
    private JourneyGameManager gameManager;
    
    public JourneyGameEventHandler() {}
    
    public void setGameManager(JourneyGameManager msg) {
        gameManager = msg;
    }
    
    public Boolean moveTo(MouseEvent e) {
        return gameManager.moveTo(e.getX(), e.getY());
    }
    
    public void respondToCardClick(Card c) {
        gameManager.cardClick(c);
    }
    
    public void respondToEndBtn() {
        gameManager.endOfTurn();
    }
    
    public void respondToRollDice() {
        gameManager.rollDice();
    }
    
    public void SelectMap(int id) {
        gameManager.selectMap(id);
        gameManager.getRenderer().getUi().getGamePane().toFront();
        gameManager.render();
    }
    
    public Boolean flightTo(MouseEvent e) {
        return gameManager.flightTo(e.getX(), e.getY());
    }
    
    public void respondToSave() {
        gameManager.save();
    }
    
    public void refresh() {
        gameManager.render();
    }
    
    public Boolean rollDce() {
        return gameManager.rollDice();
    }
    
    
    public JourneyGameManager getGameManager() {
        return gameManager;
    }
    
}
