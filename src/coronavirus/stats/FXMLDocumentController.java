/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coronavirus.stats;

import com.jfoenix.controls.JFXButton;
import help.corona.Scrapper;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 *
 * @author Mohamed Kramti
 */
public class FXMLDocumentController implements Initializable {
    public static DBConnection db = new DBConnection();
    public static boolean scrap;
    @FXML
    private Label label;
    @FXML
    private JFXButton africa;
    @FXML
    private JFXButton europe;
    @FXML
    private JFXButton america;
    @FXML
    private Pane contentPane;
    @FXML
    private Label confirmedLabel;
    @FXML
    private Label deathLabel;
    @FXML
    private Label recoveredLabel;
    @FXML
    private Label activeLabel;
    @FXML
    private JFXButton Asia;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        scrap = true;
        Scrapper scrapper = new Scrapper();
        String[] globalValues = scrapper.globalStats(null);
        
        String pattern = "###,###.###";
       
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
      
        // String format = decimalFormat.format(123145555);
        
        
        confirmedLabel.setText("Total Cases"+System.lineSeparator()+decimalFormat.format(Long.parseLong(globalValues[0])));
        deathLabel.setText("Total Death"+System.lineSeparator()+decimalFormat.format(Long.parseLong(globalValues[1])));
        recoveredLabel.setText("Total Recovered"+System.lineSeparator()+decimalFormat.format(Long.parseLong(globalValues[2])));
        activeLabel.setText("Total Active"+System.lineSeparator()+decimalFormat.format(Long.parseLong(globalValues[3])));
    }    

    public void menuBtnClicked (String FXMLName){
        Pane newLoadedPane = null;
        try{
        newLoadedPane = FXMLLoader.load(getClass().getResource(FXMLName+".fxml"));   
        }catch(Exception e){
            // unable to load africa.fxml
        }
         contentPane.getChildren().clear();
        contentPane.getChildren().add(newLoadedPane);
    }
    @FXML
    private void africaBtnClicked(ActionEvent event) {
        menuBtnClicked("Africa");
    }

    @FXML
    private void europeBtnClicked(ActionEvent event) {
        menuBtnClicked("Europe");
    }

    @FXML
    private void americaBtnClicked(ActionEvent event) {
         menuBtnClicked("America");
    }

    @FXML
    private void asiaBtnClicked(ActionEvent event) {
         menuBtnClicked("Asia");
    }
    

}
