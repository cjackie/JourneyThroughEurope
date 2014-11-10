/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ui.views;

import java.io.BufferedReader;
import java.io.FileReader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import properties_manager.PropertiesManager;
import ui.Main.JourneyPropertyType;

/**
 *
 * @author chaojiewang
 */
public class AboutPane extends BorderPane {
    
    private Button goBackBtn;
    
    public AboutPane() {
        super();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String aboutTextFileName = props.getProperty(
                JourneyPropertyType.ABOUT_FILE);
        String path = props.getProperty(
                JourneyPropertyType.DATA_PATH);
        path = path+aboutTextFileName;
        
        String content = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            
            String tmp;
            while ((tmp = reader.readLine()) != null) {
                content += tmp + '\n';
            }
        } catch(Exception e) {
            System.err.println("!!!path is " + path);
            e.printStackTrace();
        }
        
        if (content == "" ) 
            content = "Missing text file";
        
        int w = Integer.parseInt(props.getProperty(JourneyPropertyType.WINDOW_WIDTH));
        ScrollPane textHolder = new ScrollPane();
        Text txt = new Text(content);
        txt.setWrappingWidth(w);
        textHolder.setContent(txt);
        this.setCenter(textHolder);
        
        String btnText = props.getProperty(
                JourneyPropertyType.ABOUT_BACK_BTN);
        goBackBtn = new Button(btnText);
        this.setAlignment(goBackBtn, Pos.CENTER);
        this.setBottom(goBackBtn);
        this.setStyle("-fx-background-color:white;");
    }
    
    public Button getGoBackBtn() {
        return goBackBtn;
    }
    
}
