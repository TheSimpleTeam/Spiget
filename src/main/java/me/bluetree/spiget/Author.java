package me.bluetree.spiget;


import me.bluetree.spiget.utils.Utils;
import org.json.JSONObject;

public class Author {
    private int resourceid;
    private JSONObject xAuthor;
    private String Name;
    private String icon;
    private int id;
    private Author(int resourceid,int r) throws Exception{
        this.resourceid = resourceid;
        xAuthor = Utils.getResource("author",resourceid);
        Name = xAuthor.getString("name");
        id = xAuthor.getInt("id");
        JSONObject form_data = xAuthor.getJSONObject("icon");
        icon = "https://spigotmc.org/" + form_data.getString("url");
    }
    private Author (int id) throws Exception{
        xAuthor = Utils.getAuthor(id);
        Name = xAuthor.getString("name");
        id = xAuthor.getInt("id");
        JSONObject form_data = xAuthor.getJSONObject("icon");
        icon = "https://spigotmc.org/" + form_data.getString("url");
    }

    public static Author getByName(String name)throws Exception{
        return new Author( Utils.getAuthor(Utils.searchAuthor(name).getInt("id")).getInt("id"));
    }

    public static Author getById(int id)throws Exception{
        return new Author( Utils.getAuthor(id).getInt("id"));
    }

    public static Author getByResource(int id)throws Exception{
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
