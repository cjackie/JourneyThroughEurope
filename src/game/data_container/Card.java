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
public class Card {
    public enum ColorType {
        GREEN, RED, YELLOW
    }
    
    private boolean hasInstruction;
    private String instructionPath;
    private String cityImgPath;
    private String cityName;
    private ColorType color;
    
    public Card(String cityName, String cityImgPath, boolean t, String instructionPath, ColorType c){
        this.cityName = cityName;
        this.cityImgPath = cityImgPath;
        this.hasInstruction = t;
        this.instructionPath = instructionPath;
        this.color = c;
    }
    
    public Boolean isHasInstruction() {
        return hasInstruction;
    }

    public String getInstructionPath() {
        return instructionPath;
    }

    public String getCityImgPath() {
        return cityImgPath;
    }

    public String getCityName() {
        return cityName;
    }
}
