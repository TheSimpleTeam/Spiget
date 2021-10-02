package me.bluetree.spiget;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import me.bluetree.spiget.cUtils.U;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static me.bluetree.spiget.Resource.getGson;

@SuppressWarnings("unchecked")
public class Author {

    private final String name;
    @Expose(deserialize = false, serialize = false) private String iconURL;
    private final Map<String, String> icon;
    private final int id;

    private Author(int resourceid, int r) throws IOException {
        JsonObject xAuthor = U.getResource("author", resourceid);
        name = xAuthor.get("name").getAsString();
        id = xAuthor.get("id").getAsInt();
        JsonObject form_data = xAuthor.get("icon").getAsJsonObject();
        icon = getGson().fromJson(form_data, Map.class);
        this.iconURL = getIconURL(icon);
    }

    private Author(int id) throws IOException {
        JsonObject xAuthor = U.getAuthor(id);
        name = xAuthor.get("name").getAsString();
        this.id = xAuthor.get("id").getAsInt();
        JsonObject form_data = xAuthor.get("icon").getAsJsonObject();
        icon = getGson().fromJson(form_data, Map.class);
        this.iconURL = getIconURL(icon);
    }

    private Author(String name, Map<String, String> icon, int id) {
        this.name = name;
        this.id = id;
        this.icon = icon;
        this.iconURL = getIconURL(icon);
    }

    private String getIconURL(Map<String, String> icon) {
        String url = icon.get("url");
        if(url.isEmpty()) return "https://static.spigotmc.org/styles/spigot/xenforo/avatars/avatar_male_l.png";
        else if(url.startsWith("http")) return url;
        else return "https://www.spigotmc.org/" + url;
    }

    public static List<Author> getByName(String name, int size) throws IOException {
        JsonArray array = getGson().fromJson(U.searchAuthor(name, size), JsonArray.class);
        List<Author> authors = new ArrayList<>();
        for (JsonElement element : array) {
            authors.add(Resource.getGson().fromJson(element.getAsJsonObject(), Author.class));
        }
        return Collections.unmodifiableList(authors);
    }

    public static List<Author> getByName(String name) throws IOException {
        return getByName(name, 10);
    }

    public static Author getById(int id) throws IOException {
        return new Author(U.getAuthor(id).get("id").getAsInt());
    }

    public static Author getByResource(int id) throws IOException {
        return new Author(id,1);
    }

    public String getName(){
        return name;
    }

    public int getId(){
        return id;
    }

    public Map<String, String> getIcon(){
        return icon;
    }

    public String getIconURL() {
        if(iconURL == null) iconURL = getIconURL(icon);
        return iconURL;
    }
}
