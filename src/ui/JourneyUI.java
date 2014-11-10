/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ui;

import game.JourneyGameEventHandler;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import ui.views.*;

/**
 *
 * @author chaojiewang
 */
public class JourneyUI {


    private AboutPane aboutPane;
    private FlightPane flightPane;
    private GamePane gamePane;
    private GameSetupPane gameSetupPane;
    private HistoryPane historyPane;
    private SelectMapPane selectMapPane;
    private SplashPane splashPane;
    private JourneyMenuEventHandler mHandler;
    private JourneyGameEventHandler gameHandler;
    private StackPane root;


    public JourneyUI(StackPane root) {
        this.root = root;
        aboutPane = new AboutPane();
        flightPane = new FlightPane();
        gamePane = new GamePane();
        gameSetupPane = new GameSetupPane();
        historyPane = new HistoryPane();
        selectMapPane = new SelectMapPane();
        splashPane = new SplashPane();
        root.getChildren().addAll(aboutPane, flightPane, 
                gamePane, gameSetupPane, historyPane, 
                selectMapPane, splashPane);
        splashPane.toFront();
    }
    
    public GamePane getGamePane() {
        return gamePane;
    }
    
    //set up the eventhandlers for buttons
    public void setMenuHandler(JourneyMenuEventHandler handler) {
        mHandler = handler;
        
        //set up all listeners
        splashPane.getStartBtn().setOnAction(e->{
            mHandler.respondToStartBtn();
        });
        splashPane.getLoadBtn().setOnAction(e->{
            mHandler.respondToLoadBtn();
        });
        splashPane.getAboutBtn().setOnAction(e->{
            mHandler.respondToAboutBtn();
        });
        splashPane.getQuitBtn().setOnAction(e->{
            mHandler.respondToQuitBtn();
        });
        
        gameSetupPane.getGoBtn().setOnAction(e->{
            mHandler.respondToGoBtn();
        });
        gameSetupPane.getNumOfPlayerDropdown().getSelectionModel()
        .selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                System.out.println(gameSetupPane.getNumOfPlayerDropdown().getItems().get((Integer) number2));
                int n = Integer.parseInt((String) gameSetupPane.getNumOfPlayerDropdown().getItems().get((Integer) number2));
                if (n < 0 || n > 6) {
                    System.err.println("item is : "+n+"..not good!!!");
                } else {
                    mHandler.respondToConfigDropdown(n);
                }
            }
        });
        
        aboutPane.getGoBackBtn().setOnAction(e->{
            mHandler.respondToBackBtn(aboutPane);
        });
        
        historyPane.getGoBackBtn().setOnAction(e->{
            mHandler.respondToBackBtn(historyPane);
        });
        
        gamePane.getAboutBtn().setOnAction(e->{
            mHandler.respondToAboutBtn();
        });
        gamePane.getHistoryBtn().setOnAction(e->{
            mHandler.respondToHistoryBtn();;
        });
        gamePane.getFlightBtn().setOnAction(e->{
            mHandler.respondToFlightBtn();
        });
        gamePane.getSelectMapBtn().setOnAction(e->{
            mHandler.respondToSelectMapBtn();
        });
    }
    
    //set up the eventhandlers for game related stuff
    public void setGameHandler(JourneyGameEventHandler handler) {
        gameHandler = handler;
        //attach event:
        //action of clicking on the game map
        gamePane.getGameCanvas().setOnMouseClicked(
                new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        gameHandler.moveTo(event);
                    }
                }
        );
        gamePane.getQuitBtn().setOnAction(e->{
            gameHandler.respondToSave();
            Platform.exit();
        });
        
        flightPane.getFlightImg().setOnMouseClicked(
                new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                       if (gameHandler.flightTo(event)) {
                           gamePane.toFront();
                       }
                    }
                }
        );
        selectMapPane.getMap0().setOnMouseClicked(
                new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        gameHandler.SelectMap(0);
                        gamePane.toFront();
                    }
                });
        selectMapPane.getMap1().setOnMouseClicked(
                new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        gameHandler.SelectMap(1);
                        gamePane.toFront();
                    }
                });
        selectMapPane.getMap2().setOnMouseClicked(
                new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        gameHandler.SelectMap(2);
                        gamePane.toFront();
                    }
                });
        selectMapPane.getMap3().setOnMouseClicked(
                new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        gameHandler.SelectMap(3);
                        gamePane.toFront();
                    }
                });
        
        
        
    }
    
    public AboutPane getAboutPane() {
        return aboutPane;
    }

    public FlightPane getFlightPane() {
        return flightPane;
    }

    public GameSetupPane getGameSetupPane() {
        return gameSetupPane;
    }

    public HistoryPane getHistoryPane() {
        return historyPane;
    }

    public SelectMapPane getSelectMapPane() {
        return selectMapPane;
    }

    public SplashPane getSplashPane() {
        return splashPane;
    }
    
    public JourneyMenuEventHandler getmHandler() {
        return mHandler;
    }

    public JourneyGameEventHandler getGameHandler() {
        return gameHandler;
    }
    
    public StackPane getRoot() {
        return root;
    }
    
    
}
