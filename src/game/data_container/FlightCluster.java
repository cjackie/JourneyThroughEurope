/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game.data_container;

import java.util.HashMap;

/**
 *
 * @author chaojiewang
 */
public class FlightCluster {
    
    private HashMap<String, Integer> flightZone;

    public FlightCluster() {
        this.flightZone = new HashMap<>();
    }
    
    public HashMap<String, Integer> getFlightZone() {
        return flightZone;
    }
    
    public boolean flightable(String from, String to) {
        //TODO
        return false;
    }
    
}
