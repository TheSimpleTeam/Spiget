package me.bluetree.spiget;

import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Update {

    private String title;
    private String description;
    private Long date;
    private int likes;
    private int id;

    public Update(int resource, int id) {
        try {
            String url = "https://api.spiget.org/v2/resources/" + resource + "/updates/" + id;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            String res = response.toString();
            JsonObject json = Resource.getGson().fromJson(res, JsonObject.class);
            title = json.get("title").getAsString();
            description = new String(Base64.getDecoder().decode(json.get("description").getAsString()));
            date = json.get("date").getAsLong();
            likes = json.get("likes").getAsInt();
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
