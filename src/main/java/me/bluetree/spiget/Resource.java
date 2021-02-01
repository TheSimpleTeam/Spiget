package me.bluetree.spiget;

import me.bluetree.spiget.cUtils.U;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;


public class Resource {

    private boolean isVersionsLoaded = false;
    private boolean isUpdatesLoaded = false;
    private boolean isReviewsLoaded = false;
    private List<Version> versions;
    private List<Review> reviews;
    private List<Update> updates;
    private JSONObject resoure;
    private String LatestVersion;
    private int resourceid;
    private Author author;
    private String resourcename;
    private boolean permium;
    private int price;
    private int releaseDate;
    private int downloads;
    private int likes;
    private String downloadLink;
    private String resourceLink;
    private String resourceIconLink;
    private Rating rating;
    private List<String> links;
    private List<String> testedVersions;
    private String description,descriptionAsXml;
    public Resource(int resourceid) throws Exception {
        this.resourceid = resourceid;
        resoure = U.getResource(null,resourceid);
        this.resourcename = resoure.getString("name");
        permium = (Boolean) resoure.get("premium");
        price = resoure.getInt("price");
        releaseDate = resoure.getInt("releaseDate");
        downloads = resoure.getInt("downloads");
        descriptionAsXml = resoure.getString("description");
        descriptionAsXml = new String(Base64.getDecoder().decode(descriptionAsXml));
        description = descriptionAsXml;
        description=description.replaceAll("<.*?>", "");
        description = description.replaceAll("(?m)^[ \t]*\r?\n", "");
        likes = resoure.getInt("likes");
        links = new ArrayList<>();
        JSONObject object = resoure.getJSONObject("links");
        String o = object.toString();
        String[] x = o.split(",");
        for(String l : x){
            links.add(l);
        }
        object = resoure.getJSONObject("rating");
        rating = new Rating(object.getInt("count"),object.getDouble("average"));
        JSONArray array = resoure.getJSONArray("testedVersions");
        testedVersions = new ArrayList<>();
        for(int i = 0; i < array.length();i++){
            testedVersions.add(array.getString(i));
        }
        JSONObject ox = resoure.getJSONObject("file");
        downloadLink = "https://www.spigotmc.org/"+ox.getString("url");
        String z = resourcename;
        z = z.replaceAll(" - ","-");
        z = z.replaceAll(" ","-");
        resourceLink = "https://spigotmc.org/resources/"+z+"."+ resourceid;
        author = Author.getByResource(resourceid);
        try (Scanner scanner = new Scanner(new URL("https://api.spigotmc.org/legacy/update.php?resource=85958").openStream())) {
            String argss = "";
            while (scanner.hasNext()) {
                argss = argss + scanner.next() + " ";
            }
            LatestVersion = argss.replaceAll("\\s+$", "");
        }
    }
    public String getResourceIconLink() {
        return resourceIconLink;
    }
    public String getTag(){
        String i;
        try {
            i = resoure.getString("tag");
            return  i;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }
    private void loadUpdates() {
        if (!isUpdatesLoaded) {
            isUpdatesLoaded = true;
            JSONArray jsonArray = resoure.getJSONArray("updates");
            updates = new ArrayList<>();
            jsonArray.forEach(item -> {
                JSONObject update = (JSONObject) item;
                updates.add(new Update(resourceid, update.getInt("id")));
            });
        }
    }
    private void loadVersions() {
        if (!isVersionsLoaded) {
            isVersionsLoaded = true;
            JSONArray jsonArray = resoure.getJSONArray("versions");
            versions = new ArrayList<>();
            jsonArray.forEach(item -> {
                JSONObject version = (JSONObject) item;
                versions.add(new Version(resourceid, version.getInt("id")));
            });
        }
    }
    private void loadReviews() {
        if (!isReviewsLoaded) {
            isReviewsLoaded = true;
            reviews = new ArrayList<>();
            try {
                String url = "https://api.spiget.org/v2/resources/" + resourceid + "/reviews";
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                int responseCode = con.getResponseCode();
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                String res = response.toString();
                JSONArray json = new JSONArray(res);
                json.forEach(item -> {
                    JSONObject data = (JSONObject) item;
                    String message = new String(Base64.getDecoder().decode(data.getString("message"))).replace("<br> ", "");
                    String version = data.getString("version");
                    Long date = data.getLong("date");
                    int ID = data.getInt("id");
                    JSONObject rejson = data.getJSONObject("rating");
                    Rating rating = new Rating(rejson.getInt("count"), rejson.getDouble("average"));
                    reviews.add(new Review(message, version, date, ID, rating));
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    public String getDescription() {
        return description;
    }

    public String getResourceName(){
        return resourcename;
    }

    public int getResourceId(){
        return resourceid;
    }

    public Author getAuthor(){
        return author;
    }

    public int getResourceid() {
        return resourceid;
    }

    public boolean isPermium() {
        return permium;
    }

    public int getDownloads() {
        return downloads;
    }

    public int getLikes() {
        return likes;
    }

    public int getReleaseDate() {
        return releaseDate;
    }

    public Rating getRating() {
        return rating;
    }

    public String getDownloadLink(){
        return downloadLink;
    }

    public String getResourceLink() {
        return resourceLink;
    }

    public int getPrice() {
        return price;
    }

    public List<String> getLinks() {
        return links;
    }

    public List<String> getTestedVersions() {
        return testedVersions;
    }
    public String getLatestVersion() {
        return LatestVersion;
    }
    public List<Update> getUpdates() {
        loadUpdates();
        return updates;
    }
    public List<Review> getReviews() {
        loadReviews();
        return reviews;
    }
    public List<Version> getVersions() {
        loadVersions();
        return versions;
    }

}
