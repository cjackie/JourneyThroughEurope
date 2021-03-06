/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ui;

import game.file.JourneyFileManager;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import ui.views.GameSetupPane;

/**
 *
 * @author chaojiewang
 */
public class JourneyMenuEventHandler {
    
    private JourneyUI ui;
    private JourneyFileManager fileManager;
    
    public JourneyMenuEventHandler() {}
    
    public void setUi(JourneyUI ui) {
        this.ui = ui;
    }

    public void setFileManager(JourneyFileManager fileManager) {
        this.fileManager = fileManager;
    }
    
    public void respondToStartBtn() {
        ui.getGameSetupPane().toFront();
    }
    
    public void respondToLoadBtn() {
        ui.getGamePane().toFront();
        fileManager.load();
        ui.getGameHandler().getGameManager().resume();
    }
    
    public void respondToAboutBtn() {
        ui.getAboutPane().toFront();
    }
    
    public void respondToQuitBtn() {
        Platform.exit();
    }
    
    public void respondToGoBtn() {
        if (ui.getGameSetupPane().getNumberOfPlayer() == 0) {
            return;
        }
        HashMap<String, Integer> config = ui.getGameSetupPane().getConfig();
        if (config == null) {
            System.out.println("The form is not complete!");
        } else {
            fileManager.startNewGame(config);
            ui.getGameHandler().refresh();
            ui.getGameHandler().getGameManager().displayMsg("Welcome to Journey through europe - The "
                + "worst game I have ever played");
            ui.getGamePane().toFront();
            ui.getGameHandler().showInitEffects();
            if (!fileManager.getGameData().getCurrentPlayer().isIsHuman()) {
                ui.getGameHandler().getGameManager().computerMove();
            }
        }   
    }
    
    public void respondToConfigDropdown(int numOfPlayers) {
        GameSetupPane p = ui.getGameSetupPane();
        p.setNumberOfPlayer(numOfPlayers);

        GridPane gridPane = p.getSelectionGrid();
        gridPane.getChildren().clear();
        
        int x = 0;
        int y = 0;
        for (int i = 0; i < numOfPlayers; i++) {
            gridPane.add(p.getGrid().get(i), x, y);
            x++;
            if (x >= 3) {
                x=0;
                y++;
            }
        }
    }
    
    public void respondToHistoryBtn() {
        //TODO the line below is deprecated should be moved to fileManager
        //ui.getHistoryPane().refresh();
        ui.getHistoryPane().toFront();
        ui.getHistoryPane().refreshFromGameData();
    }
    
    public void respondToBackBtn(Pane p) {
        p.toBack();
    }
    
    public void respondToFlightBtn() {
        String currentCity = 
                ui.getGameHandler().getGameManager().getGameData().getCurrentPlayer().getCurrentCity();
        if (ui.getGameHandler().getGameManager().
                getGameData().getFlightCluster().getFlightByName(currentCity) == null) {
            ui.getGameHandler().getGameManager().displayMsg("This city doesn't have flight!!");
        } else {
            ui.getFlightPane().toFront();
        }
    }
    
    public void respondToSelectMapBtn() {
        ui.getSelectMapPane().toFront();
    }
}
