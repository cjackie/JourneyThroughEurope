/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game.data_container;

/**
 *
 * @author chaojiewang
 */
public class Player {
    
    private String currentCity;
    private boolean isHuman;
    private String flagPath;
    private String piecePath;
    private int numberOfFlightPlan;
    
    public Player(String currentCity, boolean isHuman, String flagPath, 
            String piecePath, int numberOfFlightPlan) {
        this.currentCity = currentCity;
        this.isHuman = isHuman;
        this.flagPath = flagPath;
        this.piecePath = piecePath;
        this.numberOfFlightPlan = numberOfFlightPlan;
    }
    
    public String getCurrentCity() {
        return currentCity;
    }

    public boolean isIsHuman() {
        return isHuman;
    }

    public String getFlagPath() {
        return flagPath;
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
}
