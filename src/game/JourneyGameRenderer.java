/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game;

import game.data_container.Card;
import game.data_container.City;
import game.data_container.Player;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.animation.TranslateTransitionBuilder;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javax.swing.RepaintManager;
import properties_manager.PropertiesManager;
import ui.JourneyUI;
import ui.Main;

/**
 *
 * @author chaojiewang
 */
public class JourneyGameRenderer {


    
    private JourneyGameData gameData;
    private JourneyUI ui;
    private boolean initImgsYet;
    
    
    //imgs for the game
    private Image map0;
    private Image map1;
    private Image map2;
    private Image map3;
    
    
    public JourneyGameRenderer() {
        this.initImgsYet = false;
    }
    
    public void render() {
        if (!initImgsYet) {
            initImgs();
        }
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        

        GraphicsContext cg = ui.getGamePane().getGameCanvas().getGraphicsContext2D();
        int h = Integer.parseInt(props.getProperty(Main.JourneyPropertyType.GAME_HEIGHT));
        int w = Integer.parseInt(props.getProperty(Main.JourneyPropertyType.GAME_WIDTH));
        cg.clearRect(0, 0, w, h);
        
        int mapId = gameData.getCurrentMap();
        if (mapId == 0) {
            cg.drawImage(map0, 0, 0);      
        } else if (mapId == 1) {
            cg.drawImage(map1, 0, 0);
        } else if (mapId == 2) {
            cg.drawImage(map2, 0, 0);
        } else {
            cg.drawImage(map3, 0, 0);
        }
        
        //TODO, check the state and render pieces, cards, dice and the message for the board
        //render the dice 
        Image diceImg = gameData.getDice().getDiceImg(gameData.getDice().getDiceNum());
        ui.getGamePane().getDiceImg().setImage(diceImg);
        
        //render the piece
        int pieceHeight = 50;
        int pieceWidth = 50;
        int currrentMap = gameData.getCurrentMap();
        
        for (int i = 0; i < gameData.getAllPlayers().size(); i++) {
            Player p = gameData.getAllPlayers().get(i);
            City c = gameData.getCityByName(p.getCurrentCity());
            if (c == null) {
                System.err.println("current play is in " + p.getCurrentCity());
                System.err.println("and current city is not found!!");
                Platform.exit();
            } else {
                if (c.getMapId() == currrentMap) {
                    cg.drawImage(p.getPieceImg(), c.getPosX()-pieceWidth/2, c.getPosY()- pieceHeight,
                            pieceWidth, pieceHeight);
                            
                }
            }
            
        }
        
        //show red line of current one to adjcent cities
        ArrayList<String> playerLandNeighbors =
                gameData.getGameMap().getLandNeighbors(
                        gameData.getCurrentPlayer().getCurrentCity());
        if (playerLandNeighbors != null) {
            for (String cityName : playerLandNeighbors) {
                City currentPlayerCity = gameData.getCityByName(
                        gameData.getCurrentPlayer().getCurrentCity());
                City c = gameData.getCityByName(cityName);
                if (c == null) {
                    System.err.println("what!!, neighbor is not found:" + cityName);
                    Platform.exit();
                }
                if (currentPlayerCity == null) {
                    System.err.println("player current city is notfoudn!!"
                            + gameData.getCurrentPlayer().getCurrentCity());
                    Platform.exit();
                }
                if (currentPlayerCity.getMapId() == currrentMap
                        && c.getMapId() == currrentMap) {
                    cg.setStroke(Color.RED);
                    cg.setLineWidth(2);
                    cg.strokeLine(currentPlayerCity.getPosX(), currentPlayerCity.getPosY(), c.getPosX(), c.getPosY());
                }
            }
        }
        
        //show green line to neighbor cities.
        if (gameData.getCurrentPlayer().isWaitingOnHarbor()) {
            ArrayList<String> playerSeaNeighbors
                    = gameData.getGameMap().getHarborNeighbors(
                            gameData.getCurrentPlayer().getCurrentCity());
            City currentPlayerCity = gameData.getCityByName(
                    gameData.getCurrentPlayer().getCurrentCity());
            if (playerSeaNeighbors != null) {
                for (String cityName : playerSeaNeighbors) {
                    City c = gameData.getCityByName(cityName);
                    if (c == null) {
                        System.err.println("what!!, neighbor is not found:" + cityName);
                        Platform.exit();
                    }
                    if (currentPlayerCity == null) {
                        System.err.println("player current city is notfoudn!!"
                                + gameData.getCurrentPlayer().getCurrentCity());
                        Platform.exit();
                    }
                    if (currentPlayerCity.getMapId() == currrentMap
                            && c.getMapId() == currrentMap) {
                        cg.setStroke(Color.GREEN);
                        cg.setLineWidth(2);
                        cg.strokeLine(currentPlayerCity.getPosX(), currentPlayerCity.getPosY(), c.getPosX(), c.getPosY());
                    }
                }
            }
        }
        
        //render cards
        refreshCards();
        
           
    }
    
    public void refreshCards() {
       JourneyGameEventHandler gHandler = ui.getGameHandler();
        ui.getGamePane().getCardSection().getChildren().clear();
        for (Card c : gameData.getCurrentPlayer().getCardsOnHand()){
            Image m = c.getFrontImg();
            ImageView v = new ImageView(m);
            v.setOnMouseClicked(e->{
                gHandler.respondToCardClick(c);
            });
            v.setFitWidth(150);
            v.setPreserveRatio(true);
            ui.getGamePane().getCardSection().getChildren().add(v);
        } 
    }
    
    public void renderCurrentPlayerAt(double x, double y) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        GraphicsContext cg = ui.getGamePane().getGameCanvas().getGraphicsContext2D();
        int h = Integer.parseInt(props.getProperty(Main.JourneyPropertyType.GAME_HEIGHT));
        int w = Integer.parseInt(props.getProperty(Main.JourneyPropertyType.GAME_WIDTH));
        cg.clearRect(0, 0, w, h);

        int mapId = gameData.getCurrentMap();
        if (mapId == 0) {
            cg.drawImage(map0, 0, 0);      
        } else if (mapId == 1) {
            cg.drawImage(map1, 0, 0);
        } else if (mapId == 2) {
            cg.drawImage(map2, 0, 0);
        } else {
            cg.drawImage(map3, 0, 0);
        }
        
        //render the piece
        int pieceHeight = 50;
        int pieceWidth = 50;
        int currrentMap = gameData.getCurrentMap();
        
        Player p = gameData.getCurrentPlayer();

        cg.drawImage(p.getPieceImg(), x - pieceWidth / 2, y - pieceHeight,
                pieceWidth, pieceHeight);    
    }
    
    public void showMoving(String cityFrom, String cityTo) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        City cityFrom1 = gameData.getCityByName(cityFrom);
        City cityTo1 = gameData.getCityByName(cityTo);
        if (cityFrom1 == null || cityTo1 == null) {
            System.err.println("when show moving, two city not all found");
            return;
        }
        
        AnimationTimer timer = new AnimationTimer() {
            long timeout = 100000000;
            long duration = 1000000;
            int time = 0;
            int endTime = 50;
            long before = -1;
            
            double stepSize = endTime;
            double range = 3;
            int pieceHeight = 50;
            int pieceWidth = 50;
            
            //animation
            double x = cityFrom1.getPosX();
            double y = cityFrom1.getPosY();
            double destX = cityTo1.getPosX();
            double destY = cityTo1.getPosY();
            double deltaX = (destX - x)/stepSize;
            double deltaY = (destY - y)/stepSize;
            
            

            @Override
            public void handle(long now) {

                if (before == -1) {
                    before = now;
                }
                
                if (time  > endTime ) {
                    if (now - before > 100000000)
                        this.stop();
                }
                
                if (time == endTime) {
                    
                    render();
                    gameData.setState(JourneyGameManager.GameState.IN_MOVE);
                    gameData.setRemainingMove(gameData.getRemainingMove() - 1);
                    updateMoveNumber();

                    if (gameData.getRemainingMove() == 0) {
                        gameData.setState(JourneyGameManager.GameState.NEXT_PLAYER);
                        ui.getGameHandler().getGameManager().nextPlayer();
                    }
                    time++;
                    
                }
                
                if (now - before > duration && time < endTime) {
                    before = now;
                    time++;
                    GraphicsContext cg = ui.getGamePane().getGameCanvas().getGraphicsContext2D();
                    int h = Integer.parseInt(props.getProperty(Main.JourneyPropertyType.GAME_HEIGHT));
                    int w = Integer.parseInt(props.getProperty(Main.JourneyPropertyType.GAME_WIDTH));
                    cg.clearRect(0, 0, w, h);
                    
                    int mapId = gameData.getCurrentMap();
                    if (mapId == 0) {
                        cg.drawImage(map0, 0, 0);      
                    } else if (mapId == 1) {
                        cg.drawImage(map1, 0, 0);
                    } else if (mapId == 2) {
                        cg.drawImage(map2, 0, 0);
                    } else {
                        cg.drawImage(map3, 0, 0);
                    }

                    //render the piece
                    int pieceHeight = 50;
                    int pieceWidth = 50;
                    int currrentMap = gameData.getCurrentMap();
                    x = x + deltaX;
                    y = y + deltaY;

                    
                    Player p = gameData.getCurrentPlayer();
                    if (cityFrom1.getMapId() == currrentMap) {
                            cg.drawImage(p.getPieceImg(), x-pieceWidth/2, y-pieceHeight,
                                    pieceWidth, pieceHeight);  
                    }
                }
            }
        };
        
        timer.start();
    }
    
    public void showDiceAnimation() {
        
        
        AnimationTimer timer = new AnimationTimer() {
            private long before = -1;
            private long interval = 50000000;
            private int time = 0;
            @Override
            public void handle(long now) {
                if (before == -1) {
                    before = now;
                }
                
                if (time  > 10 ) {
                    updateMoveNumber();
                    if (now - before > 100000000)
                        this.stop();
                }
                
                
                if (now - before > interval && time <= 10) {
                    if (time == 10) {
                        ui.getGamePane().getDiceImg().setImage(
                                gameData.getDice().getDiceImg(
                                        gameData.getDice().getDiceNum()));
                        gameData.setState(JourneyGameManager.GameState.IN_MOVE);
                        time++;
                    } else {
                        int sides = gameData.getDice().getNumOfSides();

                        int aSide = (int) (sides * Math.random()) + 1;
                        Image aSideImg = gameData.getDice().getDiceImg(aSide);
                        ui.getGamePane().getDiceImg().setImage(aSideImg);
                        before = now;
                        time++;
                    }
                }
                
            }
        };
        timer.start();
        return;
        
    }
    
    public void changeMsg(String msg) {
        //just chnage the scrollpane in the game pane
        Text t = new Text(msg);
        t.setWrappingWidth(180);
        ui.getGamePane().getMsgBoard().setContent(t);
    }
    
    public void updateMoveNumber() {
        //render move:
        if (gameData.getRemainingMove() == -1 ) {
            ui.getGamePane().getMoves().setText("Remaining move: NA");
        } else {
            int moves = gameData.getRemainingMove();
            ui.getGamePane().getMoves().setText("Remaining move: " + moves);
        }
    }
    
    
    
    private void initImgs() {
        //init all image needed for the game
        //init four maps
        try {
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            int h = Integer.parseInt(props.getProperty(Main.JourneyPropertyType.GAME_HEIGHT));
            int w = Integer.parseInt(props.getProperty(Main.JourneyPropertyType.GAME_WIDTH));
            map0 = new Image("file:"+props.getProperty(Main.JourneyPropertyType.DATA_PATH)+
                        props.getProperty(Main.JourneyPropertyType.MAP_IMGO_0), w, h, false, true);
            map1 = new Image("file:"+props.getProperty(Main.JourneyPropertyType.DATA_PATH)+
                        props.getProperty(Main.JourneyPropertyType.MAP_IMGO_1), w, h, false, true);
            map2 = new Image("file:"+props.getProperty(Main.JourneyPropertyType.DATA_PATH)+
                        props.getProperty(Main.JourneyPropertyType.MAP_IMGO_2), w, h, false, true);
            map3 = new Image("file:"+props.getProperty(Main.JourneyPropertyType.DATA_PATH)+
                        props.getProperty(Main.JourneyPropertyType.MAP_IMGO_3), w, h, false, true);
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("when in init imgs for renderer... error");
            Platform.exit();
        }

        this.initImgsYet = true;
    }
    
    public void showGameOver() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String congraImagePath = props.getProperty(Main.JourneyPropertyType.DATA_PATH)
                +props.getProperty(Main.JourneyPropertyType.CONGRATZ_IMG);
        Image v = new Image("file:"+congraImagePath);
        
        GraphicsContext cg = ui.getGamePane().getGameCanvas().getGraphicsContext2D();
        int drawH = Integer.parseInt(props.getProperty(Main.JourneyPropertyType.CONGRATZ_HEIGHT));
        int drawW = Integer.parseInt(props.getProperty(Main.JourneyPropertyType.CONGRATZ_WIDTH));
        int h = Integer.parseInt(props.getProperty(Main.JourneyPropertyType.GAME_HEIGHT));
        int w = Integer.parseInt(props.getProperty(Main.JourneyPropertyType.GAME_WIDTH));
        cg.clearRect(0, 0, w, h);
        
        cg.drawImage(v, 0, 0);
                    
    }
    
    public void showInitEffect() {
        TranslateTransition translateTransition =
            new TranslateTransition(Duration.millis(2000), ui.getGamePane().getCardSection());
        translateTransition.setFromY(700);
        translateTransition.setToY(0);
        translateTransition.setCycleCount(1);
        translateTransition.setDelay(Duration.ZERO);
        translateTransition.play();
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
    
    class CardImage extends Card {

        private Image cardImg;
        
        public CardImage(String cityName, String cityImgPath, boolean t, String instructionPath, ColorType c) {
            super(cityName, cityImgPath, t, instructionPath, c);
            cardImg = new Image(cityImgPath);
        }
        
        public Image getCardImg() {
            return cardImg;
        }  
    }
}
