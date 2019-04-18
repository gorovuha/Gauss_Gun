import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static java.lang.StrictMath.max;
import static java.lang.StrictMath.sqrt;

public class Shoot {

    int xBall = 310;
    int yBall = 175;
    int vx = 0;
    int vy = 2;
    double ballWeight = 0.0;
    double f = 0.0;
    double xWall = 0.0;
    double kWall = 0.0;
    double ballR = 0;
    Image image;
    int vxx;

    public void draw(Graphics g, Model model, ModelPanel panel) throws IOException {
        image = ImageIO.read(ModelPanel.class.getResourceAsStream("b.png"));
        g.drawImage(image, xBall, yBall, 25, 25, panel);
    }

    public void updateBall(double capacitorE, double r, double c, double l, double R){
        vx = (int) sqrt(capacitorE / ballWeight);
        vxx = (int) ((ballWeight * vx - 1.29*0.04*vx*vx*R*R*Math.PI*0.000001/2)/ballWeight) / 5;
        f = ballWeight * vxx*5;
        if (r*c > l*2/vx){
            vxx /= Math.E;
        }
        xBall = this.xBall - vxx;
        yBall = this.yBall + vy;
    }
}