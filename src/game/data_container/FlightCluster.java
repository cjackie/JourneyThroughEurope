/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game.data_container;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.application.Platform;
import properties_manager.PropertiesManager;
import sun.security.ec.ECKeyFactory;
import ui.Main;

/**
 *
 * @author chaojiewang
 */
public class FlightCluster {
    
    //flight requirement that needs to be satisfy.
    public enum FLIGHT_REQUIREMENT{
        DICE_2, DICE_4
    }
    
    private ArrayList<Flight> flights;

    public FlightCluster() {
        this.flights = new ArrayList<>();
        //construct the flights
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String flightTextPath = props.getProperty(Main.JourneyPropertyType.DATA_PATH) +
                props.getProperty(Main.JourneyPropertyType.FLIGHT_TXT);
        List<String> flightTxt =  getFile(flightTextPath);
        int i = 0;
        for (String line : flightTxt) {
            i++;
            String[] tokens = line.split(",");
            
            try {
                String cityName = tokens[0];
                int posX = Integer.parseInt(tokens[1]);
                int posY = Integer.parseInt(tokens[2]);
                int zone = Integer.parseInt(tokens[3]);
                Flight f = new Flight(cityName, posX, posY, zone);
                this.flights.add(f);
            } catch (Exception e) {
                System.err.println("when contructing the flight in line " + i);
                System.err.println(e.getMessage());
            }
        }
    }
    
    //return null if not flightable
    public FLIGHT_REQUIREMENT flightable(String from, String to) {
        Flight flightFrom = getFlightByName(from);
        Flight flightTo = getFlightByName(to);
        if (flightFrom == null || flightTo == null) {
            //can't flight
            return null;
        }
        int toZone = flightTo.getFlightZone();
        switch (flightFrom.getFlightZone()) {
            case 1:
                if (toZone == 2 || toZone == 4)
                    return FLIGHT_REQUIREMENT.DICE_4;
                if (toZone == 1) 
                    return FLIGHT_REQUIREMENT.DICE_2;
                break;
            case 2 :
                if (toZone == 1 || toZone == 3)
                    return FLIGHT_REQUIREMENT.DICE_4;
                if (toZone == 2) 
                    return FLIGHT_REQUIREMENT.DICE_2;
                break;
            case 3:
                if (toZone == 2 || toZone == 4 || toZone == 6)
                    return FLIGHT_REQUIREMENT.DICE_4;
                if (toZone == 3) 
                    return FLIGHT_REQUIREMENT.DICE_2;
                break;
            case 4 :
                if (toZone == 1 || toZone == 3 || toZone == 5)
                    return FLIGHT_REQUIREMENT.DICE_4;
                if (toZone == 4) 
                    return FLIGHT_REQUIREMENT.DICE_2;
                break;
            case 5:
                if (toZone == 4 || toZone == 6)
                    return FLIGHT_REQUIREMENT.DICE_4;
                if (toZone == 5) 
                    return FLIGHT_REQUIREMENT.DICE_2;
                break;
            case 6:
                if (toZone == 3 || toZone == 5)
                    return FLIGHT_REQUIREMENT.DICE_4;
                if (toZone == 6) 
                    return FLIGHT_REQUIREMENT.DICE_2;
                break;
            default:
                System.err.println("waht!!!, invalid flight zone!");       
        }
        
        return null;
    }
    
    //return null if not found
    public Flight getFlightByName(String cityName) {
        for (Flight f : flights) {
            if (f.getCityName().equals(cityName)) {
                return f;
            }
        }
        return null;
    }
    
    public class Flight {
        
        private String cityName;
        private int posX;
        private int posY;
        private int flightZone;
        private int radius;


        public Flight(String cityName, int posX, int posY, int flightZone) {
            this.cityName = cityName;
            this.posX = posX;
            this.posY = posY;
            this.flightZone = flightZone;
            this.radius = 50;
        }
        
        public String getCityName() {
            return cityName;
        }

        public int getPosX() {
            return posX;
        }

        public int getPosY() {
            return posY;
        }

        public int getFlightZone() {
            return flightZone;
        }
        
        public int getRadius() {
            return radius;
        }
        
    }
    
     private List<String> getFile(String filePath) {
        Path path = Paths.get(filePath);
        List<String> lines = null;
        try {
            lines = Files.readAllLines(path);
        } catch (Exception err) {
            System.out.println(path.toAbsolutePath()+"   is not found!!!");
            err.printStackTrace();
            Platform.exit();
        }
        return lines;
    }
     
    public ArrayList<Flight> getFlights() {
        return flights;
    }
    
}
