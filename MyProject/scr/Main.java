import jdk.nashorn.internal.scripts.JO;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        //для начала окно, где всё будет происходить
        JFrame mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setSize(640, 480);



        mainFrame.setIconImage(ImageIO.read(new File("C:\\Users\\Света\\IdeaProjects\\Project\\1.png")));
        mainFrame.setTitle("Пушка Гаусса");

        //панель
        JPanel panel = new JPanel();
        mainFrame.add(panel);
        panel.setLayout(new BorderLayout());

        //панель с моделью
        ModelPanel panelForModel = new ModelPanel();
        panel.add(panelForModel, BorderLayout.CENTER); //расположение панелей с моделью и кнопками

        //панель для кнопок
        ButtonPanel buttonPanel = new ButtonPanel();
        panel.add(buttonPanel, BorderLayout.EAST);
        buttonPanel.setLayout(new GridLayout(6, 1)); //менеджер упаковки, чтобы кнопк были расположены правильно

        //кнопки

        //создать модель
        JButton create = new JButton();
        buttonPanel.add(create);
        create.setText("Создать");
        create.addActionListener(new ActionListener() { //слушатель кнопки
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!panelForModel.create){
                    panelForModel.create = true; //флажок, включаюий рисование модели
                } else{
                    panelForModel.model.capacitors.clear(); //если уже было что-то нарисовано, то всё стирается
                    panelForModel.bobbin = false;
                    panelForModel.play = false;
                    panelForModel.capacitor = false;
                    panelForModel.wall = false;
                    panelForModel.ball = false;
                    for (int i = 0; i < panelForModel.text.size(); i++) {
                        panelForModel.text.get(i).setText("");
                    }
                    panelForModel.shootPanel.s.xBall = 310;
                    panelForModel.shootPanel.s.yBall = 175;
                }

            }
        });
        create.setBackground(new Color (143, 186, 232));


        //обмотка
        JButton bobbin = new JButton();
        buttonPanel.add(bobbin);
        bobbin.setText("Катушка");
        bobbin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(panelForModel.create){ //диалоговые окна для длины и сопротивления катушки
                    String l = JOptionPane.showInputDialog(mainFrame, "Введите длину катушки, см", JOptionPane.QUESTION_MESSAGE);
                    String r = JOptionPane.showInputDialog(mainFrame, "Введите сопротивление катушки, Ом", JOptionPane.QUESTION_MESSAGE);
                    if (l != null && r != null) {
                        panelForModel.bobbin = true; //флажок включает рисование катушки
                        panelForModel.model.lengthBobbin = Double.parseDouble(l)*0.01; //изменение полей модели, прикрплённой к панели для модели
                        panelForModel.model.rBobbin = Double.parseDouble(r);
                    } else {
                        JOptionPane.showMessageDialog(mainFrame, "Вы не ввели параметры", "Предупреждение", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        bobbin.setBackground(new Color (143, 186, 232));



        //конденсатор
        JButton capacitor = new JButton();
        buttonPanel.add(capacitor);
        capacitor.setText("Конденсатор");
        capacitor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(panelForModel.create && panelForModel.model.capacitors.size() < 8){ //диалоговые окна для емкости и рабочего напряжения конденсатора
                    String c = JOptionPane.showInputDialog(mainFrame, "Введите ёмкость, мкФ", JOptionPane.QUESTION_MESSAGE);
                    String u = JOptionPane.showInputDialog(mainFrame, "Введите напряжение, В", JOptionPane.QUESTION_MESSAGE);
                    if (c != null && u != null)  {
                        panelForModel.model.capacitors.add(Double.parseDouble(c)*0.000001 * Double.parseDouble(u) * Double.parseDouble(u)); //энергия c*u^2, тк её значение связано с кинетической энергии, то на 2 не делится
                        panelForModel.capacitor = true;                                                                                     //не забывая про размерности, перевдим всё в единицы СИ
                        panelForModel.model.cCapacitors += Double.parseDouble(c)*0.000001;
                        if (panelForModel.play){
                            panelForModel.play = false;
                            panelForModel.shootPanel.s.xBall = 320; //если моделирование уже был запущено, то снаряд возвращается на значальное место
                            panelForModel.shootPanel.s.yBall = 190;
                        }
                    } else {
                        JOptionPane.showMessageDialog(mainFrame, "Вы не ввели параметры", "Предупреждение", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    if (panelForModel.model.capacitors.size() == 8){ //если конденсаторы уже некуда рисовать да и нет смсла, потому что время разрядки слишком большое - ошибка
                        JOptionPane.showMessageDialog(mainFrame, "Слишком много конденсаторов", "Предупреждение", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        capacitor.setBackground(new Color (143, 186, 232));

        //снаряд
        JButton ball = new JButton();
        buttonPanel.add(ball);
        ball.setText("Снаряд");
        ball.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { //диалоговые окна для массы и радиуса снаряда
                if(panelForModel.create) {
                    String w = JOptionPane.showInputDialog(mainFrame, "Введите массу снаряда, г", JOptionPane.QUESTION_MESSAGE);
                    String r = JOptionPane.showInputDialog(mainFrame, "Введите радиус снаряда, мм", JOptionPane.QUESTION_MESSAGE);
                    if (w != null && r != null) {
                        panelForModel.shootPanel.s.ballWeight = Double.parseDouble(w) * 0.001;
                        System.out.println(Double.parseDouble((w)));
                        panelForModel.shootPanel.s.ballR = Double.parseDouble(r);
                        panelForModel.ball = true;
                    } else {
                        JOptionPane.showMessageDialog(mainFrame, "Вы не ввели параметры", "Предупреждение", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        ball.setBackground(new Color (143, 186, 232));

        //стена
        JButton wall = new JButton();
        buttonPanel.add(wall);
        wall.setText("Препятствие");
        wall.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { //диалоговые окна для коэффициента жесткости и толщины стенки
                String k = JOptionPane.showInputDialog(mainFrame, "Введите коэффициент материала препятствия", JOptionPane.QUESTION_MESSAGE);
                String x = JOptionPane.showInputDialog(mainFrame, "Введите толщину препятствия, см", JOptionPane.QUESTION_MESSAGE);
                if (k != null && x != null) {
                    panelForModel.shootPanel.s.xWall = Double.parseDouble(x)*0.01;
                    panelForModel.shootPanel.s.kWall = Double.parseDouble(k);
                    panelForModel.wall = true;
                } else{
                    JOptionPane.showMessageDialog(mainFrame, "Вы не ввели параметры", "Предупреждение", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        wall.setBackground(new Color (143, 186, 232));

        //моделирование
        JButton play = new JButton();
        buttonPanel.add(play);
        play.setText("Запуск");
        play.addActionListener(new ActionListener() { //включаются флажки, отвечающие за движение мяча
            @Override
            public void actionPerformed(ActionEvent e) {
                if(panelForModel.create) {
                    panelForModel.shootPanel.s.xBall = 310;
                    panelForModel.shootPanel.s.yBall = 190;
                    panelForModel.play = true;
                }
            }
        });

        play.setBackground(new Color (143, 186, 232));
        mainFrame.setVisible(true);

        double e = 0.0;

        while (true) {
            mainFrame.repaint();
            if (panelForModel.play //если можелирование включено
                    && panelForModel.shootPanel.s.xBall > 0 && panelForModel.shootPanel.s.xBall < 640 //х в пределах экрана
                    && panelForModel.shootPanel.s.yBall > 0 && panelForModel.shootPanel.s.yBall < 480 //у в пределах экрана
                    && ( panelForModel.shootPanel.s.f > panelForModel.shootPanel.s.kWall * panelForModel.shootPanel.s.xWall  //сила уара мяча больше, чем сила упругости стены
                    || panelForModel.shootPanel.s.xBall >= 30)) { //или мяч ещё не долеел до стены
                for (int i = 0; i < panelForModel.model.capacitors.size(); i++) {
                    e += panelForModel.model.capacitors.get(i); //суммирование энергий конденсатора
                }
                panelForModel.shootPanel.s.updateBall(e, panelForModel.model.rBobbin, panelForModel.model.cCapacitors, panelForModel.model.lengthBobbin, panelForModel.shootPanel.s.ballR);
                e = 0.0;
            }
            Thread.sleep(15);
        }
    }
}
