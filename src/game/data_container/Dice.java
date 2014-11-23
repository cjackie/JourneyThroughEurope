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
public class Dice {
    private String[] diceImgPath;
    private int numOfSides;
    private int diceNum;
    private Image[] diceImgs;

    public Dice(String[] imgPath, int numOfSides) {
        this.diceImgPath = imgPath;
        this.numOfSides = numOfSides;
        roll();
        initImgs();
    }
    
    //get the number between 1 to numOfSides
    public int roll() {
        double random = Math.random();
        this.diceNum = (int)((random * numOfSides) + 1);
        return diceNum;
    }
    
    //path to the img of the dice
    public String getDiceImgPath(int i) {
        if (i <=0 || i>numOfSides+1) {
            return null;
        }
        return diceImgPath[i-1];
    }
    
    //path to the img of the dice
    //for example int 6 iwll be the 6 dice
    public Image getDiceImg(int i) {
        if (i <=0 || i>numOfSides+1) {
            return null;
        }
        return diceImgs[i-1];
    }
    
    public String[] getDiceImgPath() {
        String[] clonePaths = new String[diceImgPath.length];
        for (int i = 0; i < diceImgPath.length; i++) {
            clonePaths[i] = (diceImgPath[i]) + "";
        }
        return clonePaths;
    }
    
    public int getDiceNum() {
        return diceNum;
    }
    
    private void initImgs() {
        diceImgs = new Image[diceImgPath.length];
        for (int i = 0; i < diceImgPath.length; i++) {
            Image img = new Image("file:"+diceImgPath[i]);
            diceImgs[i] = img;
        }
    }
    
    public int getNumOfSides() {
        return numOfSides;
    }
    
}
