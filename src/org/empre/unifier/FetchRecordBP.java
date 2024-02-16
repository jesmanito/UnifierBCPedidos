package org.empre.unifier;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.json.JSONObject;

public class FetchRecordBP {

    private static final String VERSION = "3";
    private static final String ENDPOINT = "https://eu1.unifier.oraclecloud.com";


    public  JSONObject callRestURL(String json, String metodo, JsonNode token, String resource) throws IOException, KeyManagementException, NoSuchAlgorithmException {
        HttpURLConnection conn = null;
        JSONObject response = null;

        try {
            
            
            
            
            trustAllCert();
            //JsonNode authTokenResponse = getAuthTokenDetails();
            JsonNode authTokenResponse = token;
    //  System.out.println("5: "+authTokenResponse.get("token").asText());
            URL apiUrl = new URL(ENDPOINT+resource);
            conn = (HttpURLConnection) apiUrl.openConnection();
            conn.setRequestProperty("Version", VERSION);
    //        System.out.println("Token Generated : " + authTokenResponse.get("token").asText());
            conn.setRequestProperty("Authorization", "Bearer " + authTokenResponse.get("token").asText());
          /*
            conn.setRequestProperty("x-prime-tenant-code", authTokenResponse.get("primeTenantCode").asText());
            Iterator<Entry<String, JsonNode>> requestHeadersIterator = authTokenResponse.get("requestHeaders").fields();
            while (requestHeadersIterator.hasNext()) {
                Entry<String, JsonNode> header = requestHeadersIterator.next();
                conn.setRequestProperty(header.getKey(), header.getValue().asText());
            }
    */
    
    
    
    
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod(metodo);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            
            String jsonInputString = json;
            try(OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);                   
            }
            
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode() + "Error: " + readStreamData(conn.getErrorStream()));
            }
            //return readStreamData(conn.getInputStream());
           return new JSONObject(readStreamData(conn.getInputStream()));
            //return response;
            
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
    
    

    private static String readStreamData(InputStream is) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String output;
            StringBuilder buff = new StringBuilder();
            while ((output = br.readLine()) != null) {
                buff.append(output);
            }
            return buff.toString();
        }
    }
    
    private static void trustAllCert() throws NoSuchAlgorithmException, KeyManagementException {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
    
            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
    
            @Override
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }
    
            @Override
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        } };
        // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = (hostname, session) -> true;
        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }


}
