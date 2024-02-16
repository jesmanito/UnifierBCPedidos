package org.empre.service;

import com.fasterxml.jackson.databind.JsonNode;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.empre.ServiceAuth;
import org.empre.bean.FetchFilterCriteria;
import org.empre.unifier.FetchRecordBP;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainProcess {
   
public void init() 
 //public static void main(String[] args)
 {
        
      System.out.println("21 DENTRO DE MAIN");
    FetchRecordBP fetchRecordBP = new FetchRecordBP();  
    List<FetchFilterCriteria.Filter> filterlist = new ArrayList<>();
      String entorno = GlobalInfo.ENTORNO;
    String codBC = "A3";
    int rev = 4;
    String codBP = null;
    String codBPCH = null;
    String bpname = null;
    String value = "ID091-15000";
    String  json = null;
    JsonNode tok = null;
     
     if (codBC.equals("A11")){
         codBP = "Hours Direct Staff contract";
         codBPCH = "Hours Direct Staff Changes";
     }else if (codBC.equals("A12")){
         codBP = "Hours Subcontracted Contracts";
         codBPCH = "Hours Subcontracted Contract Changes";
     }else if (codBC.equals("A21")){
         codBP = "Material Contracts";
         codBPCH = "Material contract changes";
     }else if (codBC.equals("A22")){
         codBP = "Subcontractor contracts";
         codBPCH = "Subcontractor Contract changes";
     }else if (codBC.equals("A3")){
         codBP = "Equipment contracts";
         codBPCH = "Equipment contract changes";
     }else if (codBC.startsWith("G&A")){
         codBP = "G&A Costs Contract";
         codBPCH = "G&A Costs Contract Changes";
     }else if (codBC.startsWith("COH")){
         codBP = "Overheads (COH) Contract";
         codBPCH = "Overheads (COH) Contract Changes";
     }else{
      System.out.println("NO EXISTE BP");
     }
     
     
      Properties prop;
      String dirLog = null;
          InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream ("config.properties");
      prop    = new Properties();
      try {
          prop.load(inputStream);
      } catch (IOException e) {
      }


      
       filterlist.add(new FetchFilterCriteria.Filter("uconContractNumberTXT120",value,"eq"));
      bpname = codBP;
     if(rev>1)
     {
         bpname = codBPCH;
        filterlist.add(new FetchFilterCriteria.Filter("RevNumber",String.valueOf(rev),"eq"));
     }
         FetchFilterCriteria.FilterCriteria filterCriteria = new FetchFilterCriteria.FilterCriteria("",filterlist);
       FetchFilterCriteria fetchFilterCriteria = new FetchFilterCriteria(bpname,"yes","uuu_tab_id;title",filterCriteria,"uuu_user_id;uuu_record_last_update_date;uconContractNumberTXT120;status");

     json = new Gson().toJson(fetchFilterCriteria);

      System.out.println("DENTRO DE MAIN 2");

      String urlFetch = null;
         try {

             tok = ServiceAuth.getAuthTokenDetails(prop.getProperty(entorno+"_uniconn"),prop.getProperty(entorno+"_uniconnuser"),prop.getProperty(entorno+"_uniconnpass"));
             urlFetch = prop.getProperty(entorno+"_fetch_resource");
             System.out.println("token_: "+tok.get("token").asText());
         } catch (IOException | RuntimeException e) {
           
         }
      System.out.println("DENTRO DE MAIN 3");
      
      JSONObject responseJson = null;   
      try { //A UNIFIER
System.out.println("REQUEST JSON:  "+json.toString());
          responseJson = fetchRecordBP.callRestURL(json, "POST", tok, prop.getProperty(entorno+"_fetch_resource"));
System.out.println("RESPUESTA JSON:  "+responseJson.toString());
      }catch(Exception e){
      }
      
      
      JSONArray messageArray = responseJson.getJSONArray("data");
      String record_no = null;
      boolean contentRecord = false;
      for (int i = 0; i < messageArray.length(); i++) {
          JSONObject jsonObject = messageArray.getJSONObject(i);
          
          if(jsonObject.has("record_no")){
                  record_no = jsonObject.getString("record_no");
                  contentRecord = true;
              }
              
      }

      if(contentRecord){
      System.out.println("record_no:  "+record_no);          
      }else{
          System.out.println("record_no: NO TIENE  "+record_no);          
      }
     
  }

}

