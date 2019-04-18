import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Model {

    double lengthBobbin = 0;
    double rBobbin = 0.0;

    int xCapacitor = 200;
    int yCapacitor = 225;
    int widthCapacitor = 10;
    int heightCapacitor = 10;
    double cCapacitors = 0.0;
    Image imageCapacitor;
    Image imageBobbin;

    ArrayList<Double> capacitors = new ArrayList<>();

    public Model() throws IOException {
    }

    public void draw(Graphics g){
        g.drawRect(200, 200, 150, 30);
        g.drawRect(320, 230, 30, 100);
    }

    public void bobbin(Graphics g, ModelPanel panel) throws IOException {
        imageBobbin = ImageIO.read(ModelPanel.class.getResourceAsStream("bb.png"));
        g.drawImage(imageBobbin, 180, 100, panel);
    }

    public void capacitor(Graphics g, int i, ModelPanel panel) throws IOException {
        imageCapacitor = ImageIO.read(ModelPanel.class.getResourceAsStream("c.png"));
        g.drawImage(imageCapacitor, xCapacitor + widthCapacitor*i + 7*i, yCapacitor, 27, 27, panel);
    }
    public void wall(Graphics g){
        g.drawRect(30, 70, 10, 480);
    }
}
