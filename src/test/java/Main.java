import me.bluetree.spiget.Author;
import me.bluetree.spiget.Resource;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        /*Resource r = Resource.getResourcesByName("viaversion").get(0);
        URL imgURL = r.getResourceIconLink();
        if(imgURL == null) {
            imgURL = new URL("https://static.spigotmc.org/styles/spigot/xenresource/resource_icon.png");
        }*/
        List<Author> authors = Author.getByName("md_5");
        Author author = authors.stream().findFirst().orElse(Author.getById(1));
        URL imgURL = new URL(author.getIconURL());
        BufferedImage img = ImageIO.read(imgURL);
        JFrame f = new JFrame();
        JPanel p = new JPanel();
        JLabel l = new JLabel(new ImageIcon(img));
        JLabel a = new JLabel(author.getName());
        p.add(l);
        a.setBounds((int) a.getBounds().getX(), (int) a.getBounds().getY(), 100, 250);
        p.add(a);
        f.add(p);
        f.setVisible(true);
        f.setSize(300, 300);
        f.setFocusable(true);
        f.setEnabled(true);
        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
}
