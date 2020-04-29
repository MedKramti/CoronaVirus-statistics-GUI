/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coronavirus.stats;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



/**
 *
 * @author Mohamed Kramti
 */
public class DBConnection {
    private Connection conDB;
    private Statement stmt;
    
    public  DBConnection(){
        
            String url = "jdbc:mysql://localhost:3306/?serverTimezone=UTC";
            String user = "root";
            String password = "";
            
        try{
            // connecting to database
             Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
             conDB = DriverManager.getConnection(url, user, password);
            
            // create and select database
            stmt = conDB.createStatement();
            stmt.execute("CREATE DATABASE IF NOT EXISTS covid19_stats");
            stmt.execute("USE covid19_stats");
            
            // generating tables
            stmt.execute("CREATE table if not exists globalstatsscrapped("
                   + "confirmedCases bigint,"
                   + "totalDeath bigint,"
                   + "totalRecovered bigint,"
                   + "totalActiveCases bigint)");
           
            
            //String sql = "INSERT INTO `globalstatsscrapped` VALUES (111,222,333,444)";
            //stmt.execute(sql);
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void execute(String sql){
        try{

        stmt = conDB.createStatement();
        stmt.execute(sql);
                    
        }catch(Exception e){
            System.out.print("can't insert ");
            e.getMessage();
        }
    }
    
    public boolean exist(String sql){
        int count = 0;
        try{
        stmt = conDB.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
       
        while (rs.next()) {
            count = rs.getInt("c");
            System.out.println(count + "\n");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
       // System.out.print("COUNT ////// "+count);
        return count==0 ? false : true;
    }
    
    public void insertUpdateCountryStats(String country, int confirmedCases,int totalDeath,
            int totalRecovered,int totalActiveCases){
        
        
        String query = "SELECT count(*) c FROM `globalstatsscrapped` WHERE `country`='"+country+"'";

          if (FXMLDocumentController.db.exist(query)){
               String updateQuery = "UPDATE `globalstatsscrapped` SET "
                    + "`confirmedCases`="+confirmedCases
                    + ",`totalDeath`="+totalDeath
                    + ",`totalRecovered`="+totalRecovered
                    + ",`totalActiveCases`="+totalActiveCases
                    + " WHERE `country`= ' "+country+"'";
            this.execute(updateQuery);
          }else{
         String sql = "INSERT INTO `globalstatsscrapped` VALUES ("+confirmedCases+","
         +totalDeath+","
         +totalRecovered+","
         +totalActiveCases+",'"
         +country+"')";

        
         this.execute(sql);
          }
       
    }
    
    public String[] getCountryStats(String country){
           if (country!=null){
       if (country.indexOf("_")>0){
           country = country.replace("_","-");
       }       
      }
       String[] result = new String[4];
        try{
//confirmedCases,totalDeath,totalRecovered,totalActiveCases
        Statement stmt = conDB.createStatement();
        ResultSet rs = stmt.executeQuery("select * from globalstatsscrapped where country = '"+country+"'");
        while (rs.next())
        {
        
            result[0]= String.valueOf(rs.getLong("confirmedCases"));
            result[1]= String.valueOf(rs.getLong("totalDeath"));
            result[2]= String.valueOf(rs.getLong("totalRecovered"));
            result[3]= String.valueOf(rs.getLong("totalActiveCases"));
    
        }
                stmt.close();     
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    
    public ArrayList getDiagramData(String country){
           if (country!=null){
       if (country.indexOf("_")>0){
           country = country.replace("_","-");
       }       
      }
        ArrayList<String> dates = new ArrayList<String>();
        ArrayList<Integer> recovered = new ArrayList<Integer>();
        ArrayList<Integer> infected = new ArrayList<Integer>();
        try{

        Statement stmt = conDB.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT `date`, `country`, `recovered`, `infected` FROM `dailycases` WHERE `country`='"+country+"'");
         while (rs.next())
        {
        
        Date date = rs.getDate("date");
        dates.add(date.toString());
        
        recovered.add(rs.getInt("recovered"));
        recovered.add(rs.getInt("recovered"));
        
        infected.add(rs.getInt("infected"));
        infected.add(rs.getInt("infected"));
        
        
        // print the results
        
        }
                stmt.close();     
        }
        catch(Exception e){
            e.printStackTrace();
        }
      
       ArrayList<ArrayList> a = new ArrayList<ArrayList>();
       a.add(dates);
       a.add(recovered);
       a.add(infected);
       
       return a;
    }
}
