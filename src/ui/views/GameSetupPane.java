/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ui.views;

import com.sun.corba.se.spi.orbutil.fsm.Input;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import properties_manager.PropertiesManager;
import ui.Main;

/**
 * 
 * @author chaojiewang
 */
public class GameSetupPane extends BorderPane {
    
    private HBox header;
    private ChoiceBox numOfPlayerDropdown;
    private Button goBtn;
    private GridPane selectionGrid;
    private int numberOfPlayer;
    private ArrayList<SelectionPane> grid;
    
    public GameSetupPane() {
        super();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String numOfPlayerStr = props.getProperty(Main.JourneyPropertyType.NUM_OF_PLAYER);
        Label l = new Label(numOfPlayerStr);
        
        numOfPlayerDropdown = new ChoiceBox<String>();
        ArrayList<String> numList = props
                .getPropertyOptionsList(Main.JourneyPropertyType.NUM_LIST);
        numOfPlayerDropdown.setItems(FXCollections.observableArrayList(numList));
        
        String goBtnStr = props.getProperty(Main.JourneyPropertyType.GO_BTN);
        goBtn = new Button(goBtnStr);
        
        //set up the header
        header = new HBox();
        header.getChildren().addAll(l, numOfPlayerDropdown, goBtn);
        
        int maxPlayers = Integer.parseInt(
                props.getProperty(Main.JourneyPropertyType.MAX_NUM_OF_PLAYERS));
        ArrayList<String> flagImgs = props.getPropertyOptionsList(
                Main.JourneyPropertyType.PLAYER_FLAG_IMGS);
        String root = props.getProperty(Main.JourneyPropertyType.DATA_PATH);
        String computerStr = props.getProperty(Main.JourneyPropertyType.COMPUTER_NAME);
        String playerStr = props.getProperty(Main.JourneyPropertyType.PLAYER_NAME);
        String nameStr = props.getProperty(Main.JourneyPropertyType.NAME);
        grid = new ArrayList<SelectionPane>();
        for (int i = 0; i < maxPlayers; i++) {
            String imgPath = root + flagImgs.get(i);
            grid.add(new SelectionPane(imgPath, playerStr, computerStr, nameStr));
        }
        numberOfPlayer = 0; //by default is 0.
        
        selectionGrid = new GridPane();
        
        //set up the layout
        this.setTop(header);
        this.setCenter(selectionGrid);
        this.setStyle("-fx-background-color:white;");
        
    }
    
    /*
    * return HashMap. String is the name, Interger to 
    *   indicate if is human player(1) or computer player(0)
    * return null if the user didnt complete the form
    */
    public HashMap<String, Integer> getConfig() {
        HashMap<String, Integer> config = new HashMap<String, Integer>();
        for (int i = 0; i < numberOfPlayer; i++) {
            SelectionPane p = grid.get(i);
            if (p.humanPlayerChoice.isSelected()
                    && p.computerPlayerChoice.isSelected()) {
                return null;
            }
            if ((!p.humanPlayerChoice.isSelected())
                    && (!p.computerPlayerChoice.isSelected())) {
                return null;
            }
            
            if (p.humanPlayerChoice.isSelected()) {
                config.put(p.nameInput.getText(), 1);
            } else {
                config.put(p.nameInput.getText(), 0);
            }
        }
        
        //check if there is a duplicates
        if (config.entrySet().size() < numberOfPlayer) {
            return null;
        }
        
        return config;
    }
    
    public void setNumberOfPlayer(int numberOfPlayer) {
        if (numberOfPlayer > grid.size()) return;
        this.numberOfPlayer = numberOfPlayer;
    }

    public int getNumberOfPlayer() {
        return numberOfPlayer;
    }
    
    public HBox getHeader() {
        return header;
    }

    public ChoiceBox getNumOfPlayerDropdown() {
        return numOfPlayerDropdown;
    }

    public Button getGoBtn() {
        return goBtn;
    }

    public GridPane getSelectionGrid() {
        return selectionGrid;
    }

    public ArrayList<SelectionPane> getGrid() {
        return grid;
    }
    
    class SelectionPane extends GridPane{
        
        public RadioButton humanPlayerChoice;
        public RadioButton computerPlayerChoice;
        public TextField nameInput;
        
        public SelectionPane(String imgPath, String humanPlayer, String computerPlayer, String name) {
            super();
            
            //prepare elems
            ImageView img = new ImageView();
            img.setImage(new Image("file:"+imgPath));
            
            humanPlayerChoice = new RadioButton(humanPlayer);
            computerPlayerChoice = new RadioButton(computerPlayer);
            
            Label nameLable = new Label(name);
            nameInput = new TextField();
            
            //set up the layout
            this.setAlignment(Pos.CENTER);
            this.add(img, 0, 0);
            this.add(humanPlayerChoice, 0, 1);
            this.add(computerPlayerChoice, 0, 2);
            this.add(nameLable, 1, 1);
            this.add(nameInput, 1, 2);   
        }
        
    }   
    
}
