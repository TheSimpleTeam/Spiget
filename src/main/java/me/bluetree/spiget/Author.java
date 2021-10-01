package me.bluetree.spiget;

import com.google.gson.JsonObject;
import me.bluetree.spiget.cUtils.U;

public class Author {

    private final JsonObject xAuthor;
    private final String Name;
    private final String icon;
    private final int id;

    private Author(int resourceid, int r) throws Exception{
        xAuthor = U.getResource("author",resourceid);
        Name = xAuthor.get("name").getAsString();
        id = xAuthor.get("id").getAsInt();
        JsonObject form_data = xAuthor.get("icon").getAsJsonObject();
        icon = form_data.get("url").getAsString();
    }

    private Author(int id) throws Exception {
        xAuthor = U.getAuthor(id);
        Name = xAuthor.get("name").getAsString();
        this.id = xAuthor.get("id").getAsInt();
        JsonObject form_data = xAuthor.get("icon").getAsJsonObject();
        icon = "https://spigotmc.org/" + form_data.get("url").getAsString();
    }

    public static Author getByName(String name) throws Exception {
        return new Author(U.getAuthor(U.searchAuthor(name).get("id").getAsInt()).get("id").getAsInt());
    }

    public static Author getById(int id) throws Exception {
        return new Author(U.getAuthor(id).get("id").getAsInt());
    }

    public static Author getByResource(int id)throws Exception {
        return new Author(id,1);
    }

    public String getName(){
        return Name;
    }

    public int getId(){
        return id;
    }

    public String getIcon(){
        return icon;
    }
}
