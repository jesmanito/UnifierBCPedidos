package org.empre.service;

import com.fasterxml.jackson.databind.JsonNode;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.empre.ServiceAuth;
import org.empre.bean.BPJson;
import org.empre.bean.FetchFilterCriteria;
import org.empre.unifier.FetchRecordBP;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainProcess {
   
//public void init() 
 public static void main(String[] args)
 {
        

    FetchRecordBP fetchRecordBP = new FetchRecordBP();  
    List<FetchFilterCriteria.Filter> filterlist = new ArrayList<>();
      String entorno = GlobalInfo.ENTORNO;
    String codBC = "A3";
    int rev = 1;
    String codBP = null;
    String codBPCH = null;
    String bpname = null;
    String value = "ID091-15000";
    String  fetchJson = null;
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

     fetchJson = new Gson().toJson(fetchFilterCriteria);

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
System.out.println("REQUEST JSON:  "+fetchJson.toString());
          responseJson = fetchRecordBP.callRestURL(fetchJson, "POST", tok, prop.getProperty(entorno+"_fetch_resource"));
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


      BPJson bpJson = null;
      String sendBPjson = null;

      List<BPJson.DataItems> dataItems = new ArrayList<>();
      List<BPJson.Bp_lineitems> lineasItems = new ArrayList<>();
      List<BPJson.Cost_allocation> costItems = new ArrayList<>();
      
      
      BPJson.Options options = new BPJson.Options(codBP);
      
    //costItems.add(BPJson.Cost_allocation(1,50,"01.~~01.00.~~01.00.02.~~01.00.02.00.","short_desc_cost"));
             
    costItems.add(new BPJson.Cost_allocation(1.0,50.00,"01.~~01.00.~~01.00.02.~~01.00.02.00.","short_desc_cost"));
             
      
     lineasItems.add(new BPJson.Bp_lineitems(1.0,"each",50.00,"descripción línea","Unit Cost","First Wall Panels 1~~MIL00900","short_desc",costItems));
      
     dataItems.add(new BPJson.DataItems("095-104-P-Q-16010" 
                                        ,"5008" 
                                        ,"GMP" 
                                        ,"Dynamics" 
                                        ,"ContractNumber" 
                                        ,"SupplierOrderNumber" 
                                        ,"(TEST) " 
                                        ,"First Wall Panels 1~~MIL00900" 
                                        ,"ContractDesc" 
                                        ,50.00 
                                        ,"2000"
                                        ,"Company Administrator"
                                        ,"Pending"
                                        ,lineasItems));
      
      bpJson = new BPJson(options,dataItems);
      
      sendBPjson = new Gson().toJson(bpJson);
      System.out.println("***333*** "+sendBPjson);

     
  }

}

