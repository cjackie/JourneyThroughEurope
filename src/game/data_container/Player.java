/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game.data_container;

import java.util.ArrayList;
import javafx.scene.image.Image;

/**
 *
 * @author chaojiewang
 */
public class Player {
    
    private String currentCity;
    private boolean isHuman;
    private String piecePath;
    private int numberOfFlightPlan;
    private String playerName;
    private ArrayList<Card> cardsOnHand;
    private Image pieceImg;

    public Player(String currentCity, boolean isHuman,
            String piecePath, int numberOfFlightPlan, String playerName) {
        this.currentCity = currentCity;
        this.isHuman = isHuman;
        this.piecePath = piecePath;
        this.numberOfFlightPlan = numberOfFlightPlan;
        this.playerName = playerName;
        initImgs();
    }
    
    public String getCurrentCity() {
        return currentCity;
    }
        
    public String getPlayerName() {
        return playerName;
    }

    public boolean isIsHuman() {
        return isHuman;
    }

    public String getPiecePath() {
        return piecePath;
    }

    public int getNumberOfFlightPlan() {
        return numberOfFlightPlan;
    }
    
    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    public void setNumberOfFlightPlan(int numberOfFlightPlan) {
        this.numberOfFlightPlan = numberOfFlightPlan;
    }
    
    public void setCardsOnHand(ArrayList<Card> cardsOnHand) {
        this.cardsOnHand = cardsOnHand;
    }

    public ArrayList<Card> getCardsOnHand() {
        return cardsOnHand;
    }
    
    public Image getPieceImg() {
        return pieceImg;
    }

    
    private void initImgs() {
        pieceImg = new Image("file:"+piecePath);
    }
    
}
