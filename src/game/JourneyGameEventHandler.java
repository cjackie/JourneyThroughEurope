/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game;

import game.data_container.Card;
import game.data_container.Player;
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
        if (gameManager.moveTo(e.getX(), e.getY())) {
            Player p = gameManager.getGameData().getCurrentPlayer();
            gameManager.displayMsg("move to " + p.getCurrentCity() 
                    + " successfully!" + "You have "
                    + (gameManager.getGameData().getRemainingMove() - 1)
                    +" remainning moves."); 
            JourneyGameData.historyContent += p.getPlayerName() +" moved to " 
                            +gameManager.getGameData().getCurrentPlayer().getCurrentCity()
                            + " and had " + (gameManager.getGameData().getRemainingMove()-1) 
                            + " moves left\n";
            return true;
        }
        return false;
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
    
    public void showInitEffects() {
        gameManager.getRenderer().showInitEffect();
    }
    
    public void respondToDrag(MouseEvent e) {
        gameManager.respondToDrag(e.getX(), e.getY());
    }
    
    public void respondToDragProgress(MouseEvent e) {
        //TODO
        gameManager.respondToDragProgress(e.getX(), e.getY());
    }
    
    public void respondToDragRelease(MouseEvent e) {
        //TODO
        gameManager.respondToDragRelease(e.getX(), e.getY());
        gameManager.getRenderer().updateMoveNumber();
    }
    
    public void SelectMap(int id) {
        gameManager.selectMap(id);
        gameManager.getRenderer().getUi().getGamePane().toFront();
        gameManager.render();
    }
    
    public Boolean flightTo(MouseEvent e) {
        boolean t = gameManager.flightTo(e.getX(), e.getY());
        return t;
    }
    
    public void respondToSave() {
        gameManager.save();
    }
    
    public void refresh() {
        gameManager.getRenderer().updateMoveNumber();
        gameManager.render();
    }
    
    public Boolean rollDce() {
        boolean t = gameManager.rollDice();
        return t;
    }
    
    
    public JourneyGameManager getGameManager() {
        return gameManager;
    }
    
}
