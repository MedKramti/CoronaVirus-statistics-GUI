/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package help.corona;

import coronavirus.stats.FXMLDocumentController;
import coronavirus.stats.OfflineException;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author Mohamed Kramti
 */
public class Scrapper {
   //private String dates;
    
   public String[] globalStats(String country){
      // Because the id can't contain ' - ' and corona.help uses ' - ' for some countries like
      // saudi-arabia so i changed ' - ' in the id to ' _ '
      // here i change back saudi_arabia tp saudi-arabia so corona.help recognize it
       if (country!=null){
       if (country.indexOf("_")>0){
           country = country.replace("_","-");
       }       
      }
       String confirmedCases,totalDeath,totalRecovered,totalActiveCases;
       String values[] = new String[5];
        try{
        if (!FXMLDocumentController.scrap){
            throw new OfflineException();
        }
        // Connecting to Corona.help
        Document doc;
        if (country == null){
             doc = Jsoup.connect("https://corona.help/").get();
             country = "world";
        }else{
             doc = Jsoup.connect("https://corona.help/country/"+country).get();
        }
        
        // Get total Confirmed Cases,death ,Recovery, Active confirmed ( worldwide )
         Elements worldStats = doc.select(".match-height");
         
         confirmedCases = worldStats.select(".warning").first().text().replace(",", "");
         totalDeath = worldStats.select(".danger").first().text().replace(",", "");
         totalRecovered = worldStats.select(".success").first().text().replace(",", "");
         totalActiveCases = worldStats.select(".info").first().text().replace(",", "");
         
         //System.out.println("Confirmed Cases : "+confirmedCases);
         //System.out.println("Total Death : "+totalDeath);
         //System.out.println("Total Recovered : "+totalRecovered);
         //System.out.println("Total Active Cases : "+totalActiveCases);
        
         // Store new data in the database
     FXMLDocumentController.db.insertUpdateCountryStats(country,Integer.parseInt(confirmedCases),
             Integer.parseInt(totalDeath),
             Integer.parseInt(totalRecovered),
             Integer.parseInt(totalActiveCases));
        
       
        
        }catch(Exception e){
         // if exception caught -> use previous data stored in the datatbase
            if (country!=null){
              if (country.indexOf("_")>0){
           country = country.replace("_","-");
            }       
            }else{
                country = "world";
            }
            
            values =  FXMLDocumentController.db.getCountryStats(country);
            confirmedCases= values[0];
            totalDeath = values[1];
            totalRecovered =  values[2];
            totalActiveCases =  values[3];
        }
        
       return new String[]{confirmedCases,totalDeath,totalRecovered,totalActiveCases};

   }

   public void digaramsData(String country) throws IOException{
       if (country!=null){
       if (country.indexOf("_")>0){
           country = country.replace("_","-");
       }       
      }
       try{
        if (!FXMLDocumentController.scrap){
            //trow exception
            throw new OfflineException();
        }
       Document doc = Jsoup.connect("https://corona.help/country/"+country).get();
       
       String Ndoc = getJavaScriptCode(doc.toString(), "days = ['","var infections");
       
       String days =  getJavaScriptCode(Ndoc.toString(), "days = ['","total_infected");
       Ndoc = Ndoc.replace(days,"");
       
       String total_infected  =  getJavaScriptCode(Ndoc.toString(), "total_infected = [", "total_dead");
       Ndoc = Ndoc.replace(total_infected,"");
       String total_recovered  =  getJavaScriptCode(Ndoc.toString(), "total_recovered = [", "daily_infected");

       System.out.print(days);
       System.out.print(total_infected);
       System.out.print(total_recovered);
      
       days = trimJavaScriptCode(days);
       total_infected = trimJavaScriptCode(total_infected);
       total_recovered = trimJavaScriptCode(total_recovered);
       
       String daysArray[] = days.split(",");
       String infectedArray[] = total_infected.split(",");
       String recoveredArray[] = total_recovered.split(",");
         String insert;
       for (int i =0;i<daysArray.length;i++){
           insert = "INSERT INTO `dailycases` VALUES ("+daysArray[i]+",'"+country+"',"+recoveredArray[i]+","+infectedArray[i]+")";
           // System.out.println(insert);
           FXMLDocumentController.db.execute(insert);
       
       }
       }catch(Exception e){
          System.out.print(e.getMessage());
           
       }
    
     
     
   }
   
   public String getJavaScriptCode(String text,String start, String end){
      int firstIndex = text.indexOf(start);
      int  lastIndex = text.indexOf(end);
      return text.substring(firstIndex,lastIndex);
   }
   
   public String trimJavaScriptCode(String javaScriptCode){
       String data;
       data = javaScriptCode.substring(javaScriptCode.indexOf('[')+1, javaScriptCode.indexOf(']')-1);
       return data;
   }
   
}
