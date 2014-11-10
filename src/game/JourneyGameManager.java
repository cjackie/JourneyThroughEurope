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

    
    private JourneyFileManager fileManager;
    private JourneyGameData gameData;
    private JourneyGameRenderer renderer;
    
    public JourneyGameManager() {}
    
    private void triggerSpecialEvent(Card c) {
        //TODO
    }
    
    public Boolean moveTo(double x, double y) {
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
        }
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
        renderer.render();
    }
    
    public void selectMap(int id) {
        gameData.setCurrentMap(id);
    }
    
    public Boolean won() {
        //TODO
        return false;
    }
    
    public void displayMsg(String msg) {
        //TODO
        renderer.getUi().getGamePane().getMsgBoard().setContent(new Text(msg));
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
