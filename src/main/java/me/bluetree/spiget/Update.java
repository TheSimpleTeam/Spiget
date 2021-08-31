package me.bluetree.spiget;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Update {
    private String title;
    private String description;
    private Long date;
    private int likes;
    private int id;
    public Update(int Resource, int id) {
        try {
            String url = "https://api.spiget.org/v2/resources/" + Resource + "/updates/" + id;
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
            title = json.getString("title");
            description = new String(Base64.getDecoder().decode(json.getString("description")));
            date = json.getLong("date");
            likes = json.getInt("likes");
            this.id = id;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public String getTitle() {
        return title;
    }
    public String getDescriptionHtml() {
        return description;
    }
    public Long getDate() {
        return date;
    }
    public int getLikes() {
        return likes;
    }
    public int getId() {
        return id;
    }


}
