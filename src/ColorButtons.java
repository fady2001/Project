import java.awt.Button;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorButtons {
    private final Button[] colorBtns;

    public ColorButtons(){
        colorBtns = new Button[5];
        for (int i = 0; i < 5; i++) {
            colorBtns[i] = new Button();
        }
    }

    public void add(Paint paintApp){
        colorBtns[0].setBackground(Color.BLACK);
        colorBtns[1].setBackground(Color.RED);
        colorBtns[2].setBackground(Color.GREEN);
        colorBtns[3].setBackground(Color.BLUE);
        colorBtns[4].setBackground(Color.YELLOW);
        for (int i = 0; i < 5; i++) {
            paintApp.add(colorBtns[i]);
        }
    }

    public void buttonHandlers(Paint paintApp){
        colorBtns[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paintApp.setColor(Color.BLACK);
            }
        });

        colorBtns[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paintApp.setColor(Color.RED);
            }
        });

        colorBtns[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paintApp.setColor(Color.GREEN);
            }
        });

        colorBtns[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paintApp.setColor(Color.BLUE);
            }
        });

        colorBtns[4].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paintApp.setColor(Color.YELLOW);
            }
        });
    }
}