import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

//класс панели, на которой графика
public class ModelPanel  extends JPanel {
    Model model = new Model(); //эта панель для модели, которуб опишем отдельно
    boolean create = false;
    boolean bobbin = false;
    boolean capacitor = false;
    boolean ball = false;
    boolean wall = false;
    boolean play = false;
    ShootPanel shootPanel = new ShootPanel();
    ArrayList<JLabel> text = new ArrayList();

    public ModelPanel() throws IOException {
        this.setLayout(new GridLayout(11, 1, 0, 0));
        text.add(new JLabel(""));
        text.add(new JLabel(""));
        text.add(new JLabel(""));
        text.add(new JLabel(""));
        text.add(new JLabel(""));
        text.add(new JLabel(""));
        text.add(new JLabel(""));
        text.add(new JLabel(""));
        text.add(new JLabel(""));
        text.add(new JLabel(""));
        text.add(new JLabel(""));
        for (int i = 0; i < text.size(); i++) {
            this.add(text.get(i));
        }
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (create) {
            model.draw(g);
        }
        if (bobbin) {
            try {
                model.bobbin(g,this);
            } catch (IOException e) {
                e.printStackTrace();
            }
            text.get(3).setText("                                                                             " + String.valueOf(model.rBobbin) + " Ом");
            text.get(4).setText("                                                              " + String.valueOf(model.lengthBobbin) + "м");
        }
        if (capacitor) {
            for (int i = 0; i < model.capacitors.size(); i++) {
                try {
                    model.capacitor(g, i, this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            double e = 0.0;
            for (int i = 0; i < model.capacitors.size(); i++) {
                e += model.capacitors.get(i);
            }
            text.get(6).setText("                                               " + String.valueOf(model.cCapacitors) + " Ф                " + String.valueOf(e) + " Дж");
        }
        if(ball){
            try {
                shootPanel.s.draw(g, model, this);
            } catch (IOException e) {
                e.printStackTrace();
            }
            text.get(4).setText( "                                                         " + String.valueOf(model.lengthBobbin) + "м" + "                                             " + String.valueOf(shootPanel.s.ballWeight) + "кг");
            text.get(5).setText("                                                                                                                       " + String.valueOf(shootPanel.s.ballR) + " мм");

        }
        if(wall) {
            model.wall(g);
            text.get(7).setText("                   " + String.valueOf(shootPanel.s.xWall) + " м ");
            text.get(8).setText("                   " + String.valueOf(shootPanel.s.kWall));
        }
        if(play){
            try {
                shootPanel.s.draw(g, model, this);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (shootPanel.s.xBall > 30) {
                text.get(0).setText("Скорость вылета " + this.shootPanel.s.vxx*5 + " м/с");
                text.get(1).setText("Сила удара " + this.shootPanel.s.f + "H");
            }
        }
        this.setBackground(new Color(141, 221, 177));
    }
}