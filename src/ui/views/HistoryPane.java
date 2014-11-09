/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ui.views;

import java.io.BufferedReader;
import java.io.FileReader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import properties_manager.PropertiesManager;
import ui.Main;

/**
 *
 * @author chaojiewang
 */
public class HistoryPane extends BorderPane{
    
    private ScrollPane historyContent;
    private Button goBackBtn;
    
    public HistoryPane() {
        super();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String historyHeader = props.getProperty(Main.JourneyPropertyType.HISTORY_HEADER);
        Label header = new Label(historyHeader);
        String backText = props.getProperty(Main.JourneyPropertyType.ABOUT_BACK_BTN);
        goBackBtn = new Button(backText);
        
        this.refresh();
        
        this.setTop(header);
        this.setCenter(historyContent);
        this.setStyle("-fx-background-color:white;");
        
    }
    
    public void refresh() {
        if (historyContent == null) {
            historyContent = new ScrollPane();
        }
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String textName = props.getProperty(Main.JourneyPropertyType.HISTORY_TEXT);
        String root = props.getProperty(Main.JourneyPropertyType.DATA_PATH);
        
        String content = "";
        try {
            System.out.println("file:"+root+textName);
            BufferedReader reader = new BufferedReader(new FileReader(root+textName));
            
            String tmp;
            while ((tmp = reader.readLine()) != null) {
                content += tmp + '\n';
            }
        } catch(Exception e) {
            System.err.println("!!!path is " + root+textName);
            e.printStackTrace();
        }
        
        if (content == "") {
            content = "file: " + root+textName +" is not found!!!!";
        }
        
        historyContent.setContent(new Text(content));
        return;
    }
    
    public Button getGoBackBtn() {
        return goBackBtn;
    }
}
