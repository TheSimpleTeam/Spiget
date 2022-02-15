package me.bluetree.spiget;

import com.google.gson.*;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;
import me.bluetree.spiget.cUtils.U;
import org.apache.commons.codec.binary.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Resource {

    private static final Gson gson = new GsonBuilder().create();
    private boolean isVersionsLoaded = false;
    private boolean isUpdatesLoaded = false;
    private boolean isReviewsLoaded = false;
    private List<Version> versions;
    private List<Review> reviews;
    private List<Update> updates;
    private final JsonObject resource;
    private final String latestVersion;
    @SerializedName("id") private final int resourceID;
    private final Author author;
    private final String resourceName;
    private final boolean premium;
    private final int price;
    private final int releaseDate;
    private final int downloads;
    private final int likes;
    private final String downloadLink;
    private final String resourceLink;
    private final URL resourceIconLink;
    private final Rating rating;
    private final List<String> links;
    private final List<String> testedVersions;
    private String description;

    public Resource(int resourceID) throws IOException {
        this.resourceID = resourceID;
        resource = U.getResource(null, resourceID);
        this.resourceName = resource.get("name").getAsString();
        premium = resource.get("premium") != null && resource.get("premium").getAsBoolean();
        price = resource.get("price").getAsInt();
        releaseDate = resource.get("releaseDate").getAsInt();
        downloads = resource.get("downloads").getAsInt();
        description = new String(Base64.decodeBase64(resource.get("description").getAsString().getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        description = description.replaceAll("<.*?>", "");
        description = description.replaceAll("(?m)^[ \t]*\r?\n", "");
        likes = resource.get("likes").getAsInt();
        links = new ArrayList<>();
        JsonObject object = resource.get("links").getAsJsonObject();
        String o = object.toString();
        String[] x = o.split(",");
        links.addAll(Arrays.asList(x));
        object = resource.get("rating").getAsJsonObject();
        rating = new Rating(object.get("count").getAsInt(), object.get("average").getAsInt());
        JsonArray array = resource.get("testedVersions").getAsJsonArray();
        testedVersions = new ArrayList<>();
        for(int i = 0; i < array.size(); i++){
            testedVersions.add(array.get(i).getAsString());
        }
        JsonObject ox = resource.get("file").getAsJsonObject();
        downloadLink = "https://www.spigotmc.org/" + ox.get("url").getAsString();
        String z = resourceName;
        z = z.replaceAll(" - ","-");
        z = z.replaceAll(" ","-");
        resourceLink = "https://spigotmc.org/resources/" + z + "." + resourceID;
        author = Author.getByResource(resourceID);
        try (Scanner scanner = new Scanner(new URL("https://api.spigotmc.org/legacy/update.php?resource=" + resourceID).openStream())) {
            StringBuilder args = new StringBuilder();
            while (scanner.hasNext()) {
                args.append(scanner.next()).append(" ");
            }
            latestVersion = args.toString().replaceAll("\\s+$", "");
        }
        String iconLink = resource.get("icon").getAsJsonObject().get("url").getAsString();
        resourceIconLink = iconLink.isEmpty() ? null : new URL("https://www.spigotmc.org/" + iconLink);
    }

    private void loadUpdates() {
        if (!isUpdatesLoaded) {
            isUpdatesLoaded = true;
            JsonArray jsonArray = resource.get("updates").getAsJsonArray();
            updates = new ArrayList<>();
            jsonArray.forEach(item -> {
                JsonObject update = (JsonObject) item;
                updates.add(new Update(resourceID, update.get("id").getAsInt()));
            });
        }
    }

    private void loadVersions() {
        if (!isVersionsLoaded) {
            isVersionsLoaded = true;
            JsonArray jsonArray = resource.get("versions").getAsJsonArray();
            versions = new ArrayList<>();
            jsonArray.forEach(item -> {
                JsonObject version = item.getAsJsonObject();
                versions.add(new Version(resourceID, version.get("id").getAsInt()));
            });
        }
    }

    private void loadReviews() {
        if (!isReviewsLoaded) {
            isReviewsLoaded = true;
            reviews = new ArrayList<>();
            try {
                String url = "https://api.spiget.org/v2/resources/" + resourceID + "/reviews";
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                int responseCode = con.getResponseCode();
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                String res = response.toString();
                JsonArray json = gson.fromJson(res, JsonArray.class);
                json.forEach(item -> {
                    JsonObject data = item.getAsJsonObject();
                    String message = new String(Base64.decodeBase64(data.get("message").getAsString())).replace("<br> ", "");
                    String version = data.get("version").getAsString();
                    Long date = data.get("date").getAsLong();
                    int ID = data.get("id").getAsInt();
                    JsonObject rejson = data.get("rating").getAsJsonObject();
                    Rating rating = new Rating(rejson.get("count").getAsInt(), rejson.get("average").getAsInt());
                    reviews.add(new Review(message, version, date, ID, rating));
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    /**
     * @param name The name of the plugin
     * @return An immutable list of resources that has the same name
     * @throws IOException If {@link U#searchResources(String)} cannot open a stream
     */
    public static List<Resource> getResourcesByName(String name) throws IOException {
        JsonArray array = gson.fromJson(U.searchResources(name), JsonArray.class);
        List<Resource> resourceList = new ArrayList<>();
        for (JsonElement element : array) {
            resourceList.add(new Resource(element.getAsJsonObject().get("id").getAsInt()));
        }
        return Collections.unmodifiableList(resourceList);
    }

    public String getTag(){
        return resource.get("tag").getAsString();
    }

    public URL getResourceIconLink() {
        return resourceIconLink;
    }

    public String getDescription() {
        return description;
    }

    public String getResourceName(){
        return resourceName;
    }

    public int getResourceID(){
        return resourceID;
    }

    public Author getAuthor(){
        return author;
    }

    public boolean isPermium() {
        return premium;
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
        return latestVersion;
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

    public static Gson getGson() {
        return gson;
    }
}
