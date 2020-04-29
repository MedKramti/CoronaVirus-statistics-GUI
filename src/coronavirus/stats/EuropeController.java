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
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Mohamed Kramti
 */
public class EuropeController implements Initializable {

    @FXML
    private Pane contentPane;
    @FXML
    private Button tunisia;
    @FXML
    private Button algeria;
    @FXML
    private Button morocco;
    @FXML
    private Button egypt;
    @FXML
    private Button mauritania;
    @FXML
    private Button saudi_arabia;

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
         // newLoadedPane = FXMLLoader.load(getClass().getResource("Stats.fxml")); 
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Stats.fxml")); 
        newLoadedPane2 = loader.load();
        //update database ::
        
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
