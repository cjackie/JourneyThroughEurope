/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game;

import game.data_container.Card;
import game.data_container.City;
import game.data_container.FlightCluster;
import game.data_container.FlightCluster.Flight;
import game.data_container.Player;
import game.file.JourneyFileManager;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import properties_manager.PropertiesManager;
import sun.nio.ch.NativeThread;
import ui.Main;

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
    private boolean isDraged = false;
    
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
                gameData.getCurrentPlayer().setWaitingOnHarbor(false);
                gameData.getCurrentPlayer().setCurrentCity(cityTo);
                gameData.setState(GameState.MOVING_EFFECT);
                renderer.showMoving(cityFrom, cityTo);
                this.checkCards(cityTo);
            } else if (gameData.getCurrentPlayer().isWaitingOnHarbor()){
                
                if (gameData.getGameMap().neighborHarbor(cityFrom, cityTo)) {
                    gameData.getCurrentPlayer().setWaitingOnHarbor(false);
                    gameData.getCurrentPlayer().setCurrentCity(cityTo);
                    gameData.setState(GameState.MOVING_EFFECT);
                    renderer.showMoving(cityFrom, cityTo);
                    this.checkCards(cityTo);
                    displayMsg("take the boat to " + cityTo);
                }
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
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        double widthResizeRatio = Double.parseDouble(props.getProperty(Main.JourneyPropertyType.FLIGHT_IMG_WIDTH))
                / Double.parseDouble(props.getProperty(Main.JourneyPropertyType.FLIGHT_ORIGINAL_WIDTH));
        double heightResizeRatio = Double.parseDouble(props.getProperty(Main.JourneyPropertyType.FLIGHT_IMG_HEIGHT))
                / Double.parseDouble(props.getProperty(Main.JourneyPropertyType.FLIGHT_ORIGINAL_HEIGHT));
        double clickedX = x / widthResizeRatio;
        double clickedY = y / heightResizeRatio;
        
        String flightFrom = gameData.getCurrentPlayer().getCurrentCity();
        String flightTo = null;
        for (Flight f : gameData.getFlightCluster().getFlights()) {
            double posX = f.getPosX();
            double posY = f.getPosY();
            if ((posX - clickedX)*(posX - clickedX)
                    + (posY - clickedY)*(posY - clickedY) < f.getRadius()*f.getRadius()) {
                flightTo = f.getCityName();
                break;
            }
        }
        
        FlightCluster.FLIGHT_REQUIREMENT req = gameData.getFlightCluster().flightable(flightFrom, flightTo);
        if (req == null) {
            renderer.getUi().getGamePane().toFront();
            displayMsg("Can't not flight to that city");
            return false;
        }
        
        int moves = gameData.getRemainingMove();
        if (req.equals(FlightCluster.FLIGHT_REQUIREMENT.DICE_2)) {
            if (moves >= 2) {
                gameData.getCurrentPlayer().setWaitingOnHarbor(false);
                gameData.setRemainingMove(moves-2);
                gameData.getCurrentPlayer().setCurrentCity(flightTo);
                checkCards(flightTo);
                renderer.getUi().getGamePane().toFront();
                renderer.render(); 
                displayMsg("successfully flew to " + flightTo);
                return true;
            } else {
                displayMsg("Not enough dice point to flight to that city");
                renderer.getUi().getGamePane().toFront();
                renderer.render(); 
                return false;
            }
        }
        
        if (req.equals(FlightCluster.FLIGHT_REQUIREMENT.DICE_4)) {
            if (moves >= 4) {
                gameData.getCurrentPlayer().setWaitingOnHarbor(false);
                gameData.setRemainingMove(moves-4);
                gameData.getCurrentPlayer().setCurrentCity(flightTo);
                checkCards(flightTo);
                renderer.getUi().getGamePane().toFront();
                renderer.render(); 
                displayMsg("successfully flew to " + flightTo);
                return true;
            } else {
                displayMsg("Not enough dice point to flight to that city");
                renderer.getUi().getGamePane().toFront();
                renderer.render(); 
                return false;
            }
        }
   
        return false; //useless
    }
    
    public void cardClick(Card card) {
        //TODO
        for (Node v : 
                renderer.getUi().getGamePane().getCardSection().getChildren()){
            if (((ImageView) v).getImage() == card.getFrontImg()) {
                if (card.isHasInstruction()) {
                    ((ImageView) v).setImage(card.getBackImg());
                    displayMsg("Back information is shown");
                } else {
                    displayMsg("This card has no back information");
                }
                break;
            }
            if (((ImageView) v).getImage() == card.getBackImg()) {
                if (card.isHasInstruction()) {
                    ((ImageView) v).setImage(card.getFrontImg());
                    displayMsg("Back information is shown");
                }
                break;
            }
        }
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
        //TODO. this is where computer might start do the job
        System.out.println("require to end turn");
        if (gameData.getState().equals(GameState.NEXT_PLAYER)) {
            //ready to move on to the next player
            Player currentPlayer = gameData.getCurrentPlayer();
            int index;
            for (index = 0; index < gameData.getAllPlayers().size();) {
                if (gameData.getAllPlayers().get(index) == currentPlayer) {
                    break;
                }
                index++;
            }
            
            if (index == gameData.getAllPlayers().size()) {
                System.err.println("current player is not found on all players?!");
                Platform.exit();
            }
            
            currentPlayer = gameData.getAllPlayers().get((index+1)%
                    gameData.getAllPlayers().size());
            gameData.setCurrentPlayer(currentPlayer);
            renderer.render();
            gameData.setState(GameState.WAIT_DICE);
            displayMsg("next player roll the dice");
            return;   
        }
        
        if (gameData.getGameMap().getHarborNeighbors(
                gameData.getCurrentPlayer().getCurrentCity()) != null 
                /*&& !gameData.getCurrentPlayer().isWaitingOnHarbor()*/) {
            gameData.getCurrentPlayer().setWaitingOnHarbor(true);
            
            //ready to move on to the next player
            Player currentPlayer = gameData.getCurrentPlayer();
            int index;
            for (index = 0; index < gameData.getAllPlayers().size();) {
                if (gameData.getAllPlayers().get(index) == currentPlayer) {
                    break;
                }
                index++;
            }
            
            if (index == gameData.getAllPlayers().size()) {
                System.err.println("current player is not found on all players?!");
                Platform.exit();
            }
            
            currentPlayer = gameData.getAllPlayers().get((index+1)%
                    gameData.getAllPlayers().size());
            gameData.setCurrentPlayer(currentPlayer);
            renderer.render();
            gameData.setState(GameState.WAIT_DICE);
            displayMsg("waiting on the harbor. next player roll the dice");
            return;
        }
        
        displayMsg("can't end the turn");
    }
    
    public void selectMap(int id) {
        gameData.setCurrentMap(id);
    }
    
    public void respondToDrag(double x, double y) {
        if (gameData.getState().equals(GameState.IN_MOVE)) {
            City cityClicked = gameData.getCityByName(
                    gameData.getCurrentPlayer().getCurrentCity());
            if (cityClicked == null) {
                System.err.println("current city is null?!!");
                Platform.exit();
            }

            int cx = cityClicked.getPosX();
            int cy = cityClicked.getPosY();
            int cr = cityClicked.getRadius();
            if ((cx - x) * (cx - x) + (cy - y) * (cy - y) < cr * cr) {
                isDraged = true;
            }
        }
    }
    
    public void respondToDragProgress(double x, double y) {
        if (isDraged) {
            renderer.renderCurrentPlayerAt(x, y);
        }
    }
    
    public void respondToDragRelease(double x, double y) {
        if (isDraged) {
            City c = gameData.getCityByPos(x, y);
            if (c == null) {
                displayMsg("the move is not valid");
            } else if (gameData.getGameMap().hasEdge(c.getName(),gameData.getCurrentPlayer().getCurrentCity())) {
                gameData.getCurrentPlayer().setWaitingOnHarbor(false);
                gameData.getCurrentPlayer().setCurrentCity(c.getName());
                checkCards(c.getName());
                
                //TIP!!!!!!!!!!!!  comment this to debug the map data structure
                
                gameData.setRemainingMove(gameData.getRemainingMove() - 1);
                displayMsg("move remaining:" + getGameData().getRemainingMove());
                if (gameData.getRemainingMove() == 0) {
                    gameData.setState(GameState.NEXT_PLAYER);
                }
                        
                //end of TIP      
            } else if (gameData.getCurrentPlayer().isWaitingOnHarbor()) {
                String cityFrom = gameData.getCurrentPlayer().getCurrentCity();
                if (gameData.getGameMap().neighborHarbor(cityFrom, c.getName())) {
                    checkCards(c.getName());
                    gameData.getCurrentPlayer().setWaitingOnHarbor(false);
                    gameData.setRemainingMove(gameData.getRemainingMove() - 1);
                    displayMsg("take the boat to " + c.getName()
                        + "move remaining:" + getGameData().getRemainingMove());
                    if (gameData.getRemainingMove() == 0) {
                        gameData.setState(GameState.NEXT_PLAYER);
                    }
                }
            }
            renderer.render();
            isDraged = false;
        }
    }
    
    public Boolean won() {
        //TODO
        return false;
    }
    
    
    private void checkCards(String arrivedCity) {
        //check if the user has visit a city with cards on the hand
        for (Card c : gameData.getCurrentPlayer().getCardsOnHand()) {
            if (c.getCityName().equals(arrivedCity)) {
                //yes. card on the hand. TODO special instructions will be 
                //executed.
                displayMsg("a card on your hand has arrived: " + c.getCityName());
                gameData.getCurrentPlayer().getCardsOnHand().remove(c);
                renderer.refreshCards();
            }
        }
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
