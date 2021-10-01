package me.bluetree.spiget.cUtils;

import com.google.gson.JsonObject;
import me.bluetree.spiget.Resource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class U {

    public static JsonObject getResource(String x, int id) throws Exception{
        String url;
        if(x == null){
            url = "https://api.spiget.org/v2/resources/"+id;
        }else{
            url = "https://api.spiget.org/v2/resources/"+id+"/"+x;
        }
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // optional default is GET
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        //print in String
        //Read JSON response and print
        String res = response.toString();
        if(x != null) {
            res = res.replaceFirst("\\[", "");
            res = res.replaceFirst("]", "");
        }
        return Resource.getGson().fromJson(res, JsonObject.class);
    }

    public static JsonObject searchAuthor(String x)throws Exception{
        String url = "https://api.spiget.org/v2/search/authors/"+x;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        String res = response.toString();
        res = res.replaceFirst( "\\[","");
        res = res.replaceFirst( "]","");
        return Resource.getGson().fromJson(res, JsonObject.class);
    }

    public static JsonObject getAuthor(int x) throws Exception{
        String url = "https://api.spiget.org/v2/authors/"+x;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        String res = response.toString();
        res = res.replaceFirst( "\\[","");
        res = res.replaceFirst( "]","");
        return Resource.getGson().fromJson(res, JsonObject.class);
    }
}
