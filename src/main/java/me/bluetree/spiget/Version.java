package me.bluetree.spiget;

import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Version {

    private int downloads;
    private String name;
    private Long releaseDate;
    private int id;

    public Version(int resource, int id) {
        try {
            String url = "https://api.spiget.org/v2/resources/" + resource + "/versions/" + id;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            String res = response.toString();
            JsonObject json = Resource.getGson().fromJson(res, JsonObject.class);
            downloads = json.get("downloads").getAsInt();
            name = json.get("name").getAsString();
            releaseDate = json.get("releaseDate").getAsLong();
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
