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
public class City {

    private int posX;
    private int posY;
    private int radius;
    private int mapId;
    private String name;
    private String color;


    public City(int x, int y, int radius, int mapId, String name, String color) {
        this.posX = x;
        this.posY = y;
        this.radius = radius;
        this.mapId = mapId;
        this.name = name;
        this.color = color;
    }
            
    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getRadius() {
        return radius;
    }
    
    public int getMapId() {
        return mapId;
    }

    public String getName() {
        return name;
    }
    
    public String getColor() {
        return color;
    }
    
    
}
