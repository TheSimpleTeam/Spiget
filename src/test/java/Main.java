import me.bluetree.spiget.Resource;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class Main {

    public static void main(String[] args) throws Exception {
        Resource r = new Resource(36081);
        URL imgURL = r.getResourceIconLink();
        if(imgURL == null) {
            imgURL = new URL("https://static.spigotmc.org/styles/spigot/xenresource/resource_icon.png");
        }
        BufferedImage img = ImageIO.read(imgURL);
        JFrame f = new JFrame();
        JPanel p = new JPanel();
        JLabel l = new JLabel(new ImageIcon(img));
        p.add(l);
        f.add(p);
        f.setVisible(true);
        f.setSize(300, 300);
    }
}
