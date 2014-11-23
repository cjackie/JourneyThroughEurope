/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game.data_container;

import javafx.scene.image.Image;

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
    private Image frontImg;
    private Image backImg;

    public Card(String cityName, String cityImgPath, boolean t, String instructionPath, ColorType c){
        this.cityName = cityName;
        this.cityImgPath = cityImgPath;
        this.hasInstruction = t;
        this.instructionPath = instructionPath;
        this.color = c;
        initImgs();
    }
    
    
    public boolean isHasInstruction() {
        return hasInstruction;
    }

    public Image getFrontImg() {
        return frontImg;
    }

    public Image getBackImg() {
        return backImg;
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
    
    public ColorType getColor() {
        return color;
    }
    
    private void initImgs() {
        Image front = new Image("file:"+cityImgPath);
        frontImg = front;
        if (isHasInstruction()) {
            backImg = new Image("file:"+instructionPath);
        } 
    }
    
}
