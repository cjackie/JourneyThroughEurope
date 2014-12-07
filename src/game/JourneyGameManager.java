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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.Duration;
import java.util.ArrayList;
import java.util.TreeSet;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import properties_manager.PropertiesManager;
import sun.nio.ch.NativeThread;
import ui.JourneyUI;
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
        WAIT_DICE, IN_MOVE, NEXT_PLAYER, MOVING_EFFECT,
        COMPUTER, WON
    }
    
    private JourneyFileManager fileManager;
    private JourneyGameData gameData;
    private JourneyGameRenderer renderer;
    private boolean isDraged = false;
    private boolean finished = false;
    
    public JourneyGameManager() {}
    
    private void triggerSpecialEvent(Card c) {
        //TODO
    }
    
    public Boolean moveTo(double x, double y) {
        if (finished) {
            return false;
        }
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
            String cityFrom = this.gameData.getCurrentPlayer().getCurrentCity();
            String cityTo = cityClicked.getName();
            if (gameData.getGameMap().hasEdge(cityFrom, cityTo)) {
                gameData.getCurrentPlayer().setWaitingOnHarbor(false);
                gameData.getCurrentPlayer().setCurrentCity(cityTo);
                gameData.setState(GameState.MOVING_EFFECT);
                renderer.showMoving(cityFrom, cityTo);
                this.checkCards(cityTo);
                return true;
            } else if (gameData.getCurrentPlayer().isWaitingOnHarbor()){
                
                if (gameData.getGameMap().neighborHarbor(cityFrom, cityTo)) {
                    gameData.getCurrentPlayer().setWaitingOnHarbor(false);
                    gameData.getCurrentPlayer().setCurrentCity(cityTo);
                    gameData.setState(GameState.MOVING_EFFECT);
                    renderer.showMoving(cityFrom, cityTo);
                    this.checkCards(cityTo);
                    displayMsg("take the boat to " + cityTo);
                    JourneyGameData.historyContent += gameData.getCurrentPlayer().getPlayerName() 
                        +" took the boat to " + cityTo+ " successfully and had " + 
                        (gameData.getRemainingMove()) + " moves left\n";
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
        System.out.println("remaining moves:" + moves);
        if (req.equals(FlightCluster.FLIGHT_REQUIREMENT.DICE_2)) {
            if (moves >= 2) {
                gameData.getCurrentPlayer().setWaitingOnHarbor(false);
                gameData.setRemainingMove(moves-2);
                gameData.getCurrentPlayer().setCurrentCity(flightTo);
                checkCards(flightTo);
                renderer.getUi().getGamePane().toFront();
                renderer.render(); 
                displayMsg("successfully flew to " + flightTo);
                JourneyGameData.historyContent += gameData.getCurrentPlayer().getPlayerName() 
                        +" flew to " + flightTo + " successfully and had " + 
                        (gameData.getRemainingMove()) + " moves left\n";
                if (moves-2 == 0) {
                    gameData.setState(GameState.NEXT_PLAYER);
                    this.endOfTurn();
                }
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
                JourneyGameData.historyContent += gameData.getCurrentPlayer().getPlayerName() 
                        +" flew to " + flightTo + " successfully and had " + 
                        (gameData.getRemainingMove()) + " moves left\n";
                if (moves-4 == 0) {
                    gameData.setState(GameState.NEXT_PLAYER);
                    this.endOfTurn();
                }
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
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String path = props.getProperty(Main.JourneyPropertyType.DATA_PATH)
                + props.getProperty(Main.JourneyPropertyType.SAVED_DATA_TEXT);
        int numberOfPlayer = gameData.getAllPlayers().size();
        int numberOfMoves = gameData.getRemainingMove();
        BufferedWriter writer = null;
        try {
            File logFile = new File(path);
            writer = new BufferedWriter(new FileWriter(logFile));
            writer.write(numberOfPlayer+","+gameData.getCurrentPlayer().getPlayerName()+
                    ","+numberOfMoves+",");
            writer.write(gameData.getState().toString()+"\n");
            for (Player p : gameData.getAllPlayers()) {
                //player(1) or computer player(0);
                if (p.isIsHuman()) {
                    writer.write(p.getPlayerName()+","+1+","+p.getCurrentCity());
                    for (Card c : p.getCardsOnHand()) {
                        writer.write(","+c.getCityName());
                    }
                    writer.write("\n");
                } else {
                    writer.write(p.getPlayerName()+","+0+","+p.getCurrentCity());
                    for (Card c : p.getCardsOnHand()) {
                        writer.write(","+c.getCityName());
                    }
                    writer.write("\n");
                }
            }
            writer.write(JourneyGameData.historyContent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the writer regardless of what happens...
                writer.close();
            } catch (Exception e) {
            }
        }
        //TODO
    }
    
    public void resume() {
        renderer.render();
        switch (gameData.getState()) {
            case COMPUTER :
                computerMove();
                break;
            case IN_MOVE :
                displayMsg("welcome back, you have points left, "
                        + "please choose your move");
                if (!gameData.getCurrentPlayer().isIsHuman())
                    computerMove();
                break;
            case MOVING_EFFECT:
                displayMsg("welcome back, you have points left, "
                        + "please choose your move");
                if (!gameData.getCurrentPlayer().isIsHuman())
                    computerMove();
                break;
            case NEXT_PLAYER:
                this.endOfTurn();
                break;
            case WAIT_DICE:
                displayMsg(gameData.getCurrentPlayer().getPlayerName()+"'s turn,"
                            +" roll the dice");
                if (!gameData.getCurrentPlayer().isIsHuman())
                    computerMove();
                break;
            case WON:
                displayMsg(gameData.getCurrentPlayer().getPlayerName()+" has "
                            +" won the game");
                break;
        }
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
        if (finished){
            displayMsg("Congratulation, "+gameData.getCurrentPlayer().getPlayerName()
                                    +"!!" +" You have won the game!!!");
            renderer.showGameOver();
            return;
        }
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
            displayMsg(currentPlayer.getPlayerName()+"'s turn. Please roll the dice");
            if (!currentPlayer.isIsHuman()) {
                computerMove();
            }
            return;   
        } else {
            displayMsg("can't end the turn. You have "+ gameData.getRemainingMove() 
                    +" remainning moves.");
            
        }
        
        if (gameData.getGameMap().getHarborNeighbors(
                gameData.getCurrentPlayer().getCurrentCity()) != null 
                && !gameData.getCurrentPlayer().isWaitingOnHarbor()
                && !gameData.getState().equals(GameState.COMPUTER)) {
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
            if (!currentPlayer.isIsHuman()) {
                computerMove();
            }
            return;
        } else if (!gameData.getState().equals(GameState.COMPUTER)
                && gameData.getGameMap().getHarborNeighbors(
                gameData.getCurrentPlayer().getCurrentCity()) == null ) {
            displayMsg("Can't end the turn. It's not a harbor that you can wait!"
                +"You have "+ gameData.getRemainingMove() +" remainning moves.");
        } else if (gameData.getState().equals(GameState.COMPUTER)) {
            displayMsg("Computer is moving, please wait!");
        }
        
        
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
                displayMsg(gameData.getCurrentPlayer().getPlayerName() + " moved to" 
                        + c.getName() + "successfully!! " +
                        "move remaining:" + getGameData().getRemainingMove());
                JourneyGameData.historyContent += gameData.getCurrentPlayer().getPlayerName() +" moved to " 
                            + c.getName() + " and had " + (gameData.getRemainingMove()) + " moves left\n";
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
        if (gameData.getCurrentPlayer().getCardsOnHand().size() == 0)
            return true;
        return false;
    }
    
    public void computerMove() {
        //TODO make the computer move
        //set the state.
        gameData.setState(GameState.COMPUTER);
        AnimationTimer timer;
        timer = new AnimationTimer() {
            private int remMoves = -1;
            private long before = -1;
            private final long interval = Duration.ofMillis(1500).toNanos();
            private boolean ended = false;
            private ArrayList<String> route = gameData.getGameMap()
                    .getShortestPath(gameData.getCurrentPlayer().getCurrentCity()
                            ,gameData.getCurrentPlayer().getCardsOnHand().get(0).getCityName());
            
            @Override
            public void handle(long now) {
                
                if (remMoves == -1) {
                    //init before
                    before = now;
                    
                    //init computer player related stuff.
                    renderer.render();
                    gameData.getDice().roll();
                    remMoves = gameData.getDice().getDiceNum();
                    gameData.setRemainingMove(remMoves);
                    renderer.showDiceAnimation();
                    if (route ==  null) {
                        System.err.println("the city from " + gameData.getCurrentPlayer().getCurrentCity()
                        + " to " + gameData.getCurrentPlayer().getCardsOnHand().get(0).getCityName());
                        System.err.println("no routes!!");
                        Platform.exit();
                    }
                }
                
                if (remMoves  == 0) {
                    if (now - before > 1000000000) {
                        this.stop();

                        gameData.setState(GameState.NEXT_PLAYER);
                        JourneyGameManager.this.endOfTurn();
                    }
                }
                
                
                if (now - before > interval && remMoves > 0 && !finished) {
                    
                    before = now;
                    remMoves--;
                    gameData.setRemainingMove(remMoves);
                    renderer.updateMoveNumber();
                    if (route.size() == 0) {
                        return;
                    }
                    String nextCity = route.get(0)+"";
                    route.remove(0);
                    displayMsg(gameData.getCurrentPlayer().getPlayerName() +
                            " moving to " + nextCity);
                    JourneyGameData.historyContent += gameData.getCurrentPlayer().getPlayerName() +" moved to " 
                            + nextCity + " and had " + gameData.getRemainingMove()  + " moves left\n";
                    if (gameData.getCurrentMap() == gameData.getCityByName(nextCity).getMapId()) {
                        renderer.showMoving(gameData.getCurrentPlayer().getCurrentCity(), nextCity);
                        gameData.getCurrentPlayer().setCurrentCity(nextCity);
                    } else {
                        gameData.getCurrentPlayer().setCurrentCity(nextCity);
                        City c = gameData.getCityByName(nextCity);
                        gameData.setCurrentMap(c.getMapId());
                        renderer.render(); 
                    }
                    if (checkCards(nextCity)) {
                        if (gameData.getCurrentPlayer().getCardsOnHand().size() == 0)
                            finished = true;
                        remMoves = 0;
                        ended = true;
                    }    
                    
                }
                
            }
        };
        timer.start();
        return;
    }
    
    
    private boolean checkCards(String arrivedCity) {
        //check if the user has visit a city with cards on the hand
        for (Card c : gameData.getCurrentPlayer().getCardsOnHand()) {
            if (c.getCityName().equals(arrivedCity)) {
                //yes. card on the hand. TODO special instructions will be 
                //executed.
                displayMsg("a card on your hand has arrived: " + c.getCityName());
                gameData.getCurrentPlayer().getCardsOnHand().remove(c);
                renderer.refreshCards();
                return true;
            }
        }
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
