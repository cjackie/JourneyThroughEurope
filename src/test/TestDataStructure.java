/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javafx.application.Application;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import ui.Main;

/**
 *
 * @author chaojiewang
 */
public class TestDataStructure extends Application{
    static String PROPERTY_TYPES_LIST = "property_types.txt";
    static String UI_PROPERTIES_FILE_NAME = "properties.xml";
    static String PROPERTIES_SCHEMA_FILE_NAME = "properties_schema.xsd";
    static String DATA_PATH = "data/";
    
    
    public static void main(String[] args) throws IOException {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        try {
            props.addProperty(Main.JourneyPropertyType.UI_PROPERTIES_FILE_NAME,
                    UI_PROPERTIES_FILE_NAME);
            props.addProperty(Main.JourneyPropertyType.PROPERTIES_SCHEMA_FILE_NAME,
                    PROPERTIES_SCHEMA_FILE_NAME);
            props.addProperty(Main.JourneyPropertyType.DATA_PATH.toString(),
                    DATA_PATH);
            props.loadProperties(UI_PROPERTIES_FILE_NAME,
                    PROPERTIES_SCHEMA_FILE_NAME);
            props.loadProperties(UI_PROPERTIES_FILE_NAME,
                    PROPERTIES_SCHEMA_FILE_NAME);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        initAllCity();
    }
    
    public static void initAllCity() throws IOException {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        String cityFilePath = props.getProperty(Main.JourneyPropertyType.DATA_PATH) +
                props.getProperty(Main.JourneyPropertyType.CITIES);
        
	
        Path path = Paths.get(cityFilePath);
        List<String> i = Files.readAllLines(path);
        for (String ii : i) {
            System.out.println(ii);
            for (String iii : ii.split(",")){
                System.out.println(iii);
            }
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        
}


