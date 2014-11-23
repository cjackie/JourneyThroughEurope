/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game;

import game.data_container.Card;
import game.data_container.City;
import game.file.JourneyFileManager;
import java.util.ArrayList;
import javafx.scene.text.Text;

/**
 *
 * @author chaojiewang
 */
public class JourneyGameManager {
    
    /**
     * WAIT_DICE: wait for player to roll dice
     * IN_MOVE: in moving state. 
     * NEXT_PLAYER: Ready for next player to move
     * MOVING_EFFECT: in the state of animation for moving
     */
    public static enum GameState {
        WAIT_DICE, IN_MOVE, NEXT_PLAYER, MOVING_EFFECT
    }
    
    private JourneyFileManager fileManager;
    private JourneyGameData gameData;
    private JourneyGameRenderer renderer;
    
    public JourneyGameManager() {}
    
    private void triggerSpecialEvent(Card c) {
        //TODO
    }
    
    public Boolean moveTo(double x, double y) {
        if (!gameData.getState().equals(GameState.IN_MOVE)) {
            displayMsg("can't move yet");
            return false;
        }
        
        ArrayList<City> cities = gameData.getAllCities();
        City cityClicked  =  null;
        if (cities == null) {
            System.out.println("city null!!!");
        }
        for (int i = 0; i < cities.size(); i++){
            City c = cities.get(i);
            if (c.getMapId() != gameData.getCurrentMap())
                continue;
         
            int posX = c.getPosX();
            int posY = c.getPosY();
            int radius = c.getRadius();
            if (radius*radius > ((posX-x)*(posX-x) + (posY-y)*(posY-y))) {
                cityClicked = c;
                break;
            }  
        }
        if (cityClicked == null) {
            this.displayMsg("click again..");
        } else{
            this.displayMsg(cityClicked.getName() + " is clicked!!");
            String cityFrom = this.gameData.getCurrentPlayer().getCurrentCity();
            String cityTo = cityClicked.getName();
            if (gameData.getGameMap().hasEdge(cityFrom, cityTo)) {
                gameData.getCurrentPlayer().setCurrentCity(cityTo);
                gameData.setState(GameState.MOVING_EFFECT);
                renderer.showMoving(cityFrom, cityTo);
                
            } else {
                displayMsg("moving to "+cityTo+" is not valid");
            }
        }
        return false;
    }
    
    public void nextPlayer() {
        //TODO switch to next player
        //like just set the current player to the next one and 
        //update game state
    }
    
    public Boolean flightTo(double x, double y) {
        //TODO
        System.out.println("wanna fly to "+x +"," +y);
        return false;
    }
    
    public void cardClick(Card card) {
        //TODO
        System.out.println("card " + card.getCityName()+ " is clicked");
        
    }
    
    public void save() {
        System.out.println("save btn clicked.");
        //TODO
    }
    
    public Boolean rollDice() {
        System.out.println("require rollDice");
        if (gameData.getState().equals(GameState.WAIT_DICE)) {
            gameData.getDice().roll();
            renderer.showDiceAnimation();
            gameData.setRemainingMove(gameData.getDice().getDiceNum());
            return true;
        }
        displayMsg("can't roll the dice again!!!");
        return false;
    }
    
    public void render() {
        renderer.render();
    }
    
    public void endOfTurn() {
        System.out.println("require to end turn");
        //TODO
        //it end of the turn;
    }
    
    public void selectMap(int id) {
        gameData.setCurrentMap(id);
    }
    
    public Boolean won() {
        //TODO
        return false;
    }
    
    public void displayMsg(String msg) {
        renderer.changeMsg(msg);
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
