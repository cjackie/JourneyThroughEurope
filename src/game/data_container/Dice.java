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
public class Dice {
    String[] diceImgPath;
    int numOfSides;
    
    public Dice(String[] imgPath, int numOfSides) {
        this.diceImgPath = imgPath;
        this.numOfSides = numOfSides;
    }
    
    //get the number between 1 to numOfSides
    public int roll() {
        double random = Math.random();
        return (int)((random * numOfSides) + 1);
    }
    
    public String getDiceImgPath(int i) {
        if (i <=0 || i>numOfSides+1) {
            return null;
        }
        return diceImgPath[i-1];
    }
}
