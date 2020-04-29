/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coronavirus.stats;

import help.corona.Scrapper;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Mohamed Kramti
 */
public class AsiaController implements Initializable {

    @FXML
    private Pane contentPane;
    @FXML
    private Button taiwan;
    @FXML
    private Button algeria;
    @FXML
    private ImageView japan;
    @FXML
    private Button hong_kong;
    @FXML
    private Button south_korea;
    @FXML
    private Button china;
    @FXML
    private Button indonesia;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void showStats(ActionEvent event) {
         Button clickedBtn = (Button) event.getSource();
       String btnID = clickedBtn.getId();
     
        Pane newLoadedPane2 = null;
        try{ 
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Stats.fxml")); 
        newLoadedPane2 = loader.load();
        
        
        Scrapper scrapper = new Scrapper();
        scrapper.digaramsData(btnID);
        
        StatsController statsController = loader.getController();
        statsController.setLabel(btnID);
        statsController.countryStats(btnID);
        
        contentPane.getChildren().clear();
        contentPane.getChildren().add(newLoadedPane2);
        
        }catch(Exception e){
            // unable to load africa.fxml
        }   
    }
    
}
