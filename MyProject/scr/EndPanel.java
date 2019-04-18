import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EndPanel extends JPanel {

    ArrayList<JLabel> endText = new ArrayList();
    ShootPanel panel;

    JLabel label1;
    JLabel label2;

    public EndPanel(ShootPanel panel) {
        this.panel = panel;
        this.setLayout(new GridLayout(2, 2));
        label1 = new JLabel("Скорость вылета " + panel.s.vxx + " м/с");
        endText.add(label1);
        label2 = new JLabel("Сила удара " + panel.s.f + "H");
        endText.add(label2);
        for (int i = 0; i < endText.size(); i++) {
            this.add(endText.get(i));
        }
    }

    public void update() {
        label1.setText("tr");
        //endText.add(0, new JLabel("Скорость вылета " + panel.s.vxx + " м/с"));
        //endText.add(new JLabel("Сила удара " + panel.s.f + "H"));
        //for (int i = 0; i < endText.size(); i++) {
            //this.add(endText.get(i));
        //}
    }

    @Override
    protected void paintComponent(Graphics g) {
        //this.update();
        super.paintComponent(g);
    }
}
