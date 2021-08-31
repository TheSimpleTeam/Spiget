package me.bluetree.spiget;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Version {
    private int downloads;
    private String name;
    private Long releaseDate;
    private int id;
    public Version(int Resource, int id) {
        try {
            String url = "https://api.spiget.org/v2/resources/" + Resource + "/versions/" + id;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            String res = response.toString();
            JSONObject json = new JSONObject(res);
            downloads = json.getInt("downloads");
            name = json.getString("name");
            releaseDate = json.getLong("releaseDate");
            this.id = id;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public int getDownloads() {
        return downloads;
    }
    public String getName() {
        return name;
    }
    public Long getReleaseDate() {
        return releaseDate;
    }
    public int getId() {
        return id;
    }
}
