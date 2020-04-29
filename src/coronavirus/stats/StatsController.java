/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coronavirus.stats;

import help.corona.Scrapper;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Mohamed Kramti
 */
public class StatsController implements Initializable {
    public ArrayList<String> dates;
    public ArrayList<Integer> recovered;
    public ArrayList<Integer> infected;
    public ArrayList<ArrayList> a;
    
    @FXML
    private Label label;
    @FXML
    private Label totalCases;
    @FXML
    private Label totalDeath;
    @FXML
    private Label totalRecovered;
    @FXML
    private Label totalActive;
    @FXML
    private BarChart<?, ?> infectionHistoryDiagram;
    @FXML
    private BarChart<?, ?> recoveredHistoryDiagram;
   
    
    public void setLabel(String text){
        label.setText(text.toUpperCase());
        
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
      
       
    }    
    
    public void countryStats(String country) throws SQLException{
           if (country!=null){
       if (country.indexOf("_")>0){
           country = country.replace("_","-");
         }       
        }
        
        Scrapper scrapper = new Scrapper();
        String[] globalValues = scrapper.globalStats(country);
       
        totalCases.setText(globalValues[0]);
        totalDeath.setText(globalValues[1]);
        totalRecovered.setText(globalValues[2]);
        totalActive.setText(globalValues[3]);
        
         
         
      FXMLDocumentController.db.insertUpdateCountryStats(country,Integer.parseInt(globalValues[0]),
             Integer.parseInt(globalValues[1]),
             Integer.parseInt(globalValues[2]),
             Integer.parseInt(globalValues[3]));
      
      a = FXMLDocumentController.db.getDiagramData(country);
      dates = a.get(0);
      recovered = a.get(1);
      infected = a.get(2);
      this.generateDiagram();
       
    }
    
    public void generateDiagram(){

        XYChart.Series set1 = new XYChart.Series<>();
        XYChart.Series set2 = new XYChart.Series<>();
        for (int i=0; i<dates.size();i++){
            set1.getData().add(new XYChart.Data(dates.get(i),infected.get(i)));
            set2.getData().add(new XYChart.Data(dates.get(i),recovered.get(i)));
        }
       
        infectionHistoryDiagram.getData().addAll(set1);

        recoveredHistoryDiagram.getData().addAll(set2);
    }
    
}
