import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ControlBar {
    private Paint paintApp;

    private Button undo;
    private Button redo;
    private Button clear;
    private Button save;
    private Button load;
    private Button serialize;

    private Button[] colorBtns;

    private Checkbox filled;
    private Checkbox dotted;

    private Button lineBtn;
    private Button ovalBtn;
    private Button rectangleBtn;

    private Button eraserBtn;

    public ControlBar(Paint paintApp){
        this.paintApp = paintApp;

        undo = new Button("Undo");
        redo = new Button("Redo");
        clear = new Button("Clear");
        save = new Button("Save");
        load = new Button("Load");
        serialize = new Button("Serialize");

        colorBtns = new Button[5];
        for (int i = 0; i < 5; i++) {
            colorBtns[i] = new Button();
        }

        filled = new Checkbox("Filled");
        dotted = new Checkbox("Dotted");

        lineBtn = new Button("Line");
        ovalBtn = new Button("Oval");
        rectangleBtn = new Button("Rectangle");

        eraserBtn = new Button("Eraser");
    }

    public void add(){
        paintApp.add(undo);
        paintApp.add(redo);
        paintApp.add(clear);
        paintApp.add(save);
        paintApp.add(load);
        paintApp.add(serialize);
        paintApp.add(filled);
        paintApp.add(dotted);

        colorBtns[0].setBackground(Color.BLACK);
        colorBtns[1].setBackground(Color.RED);
        colorBtns[2].setBackground(Color.GREEN);
        colorBtns[3].setBackground(Color.BLUE);
        colorBtns[4].setBackground(Color.YELLOW);
        for (int i = 0; i < 5; i++) {
            paintApp.add(colorBtns[i]);
        }

        paintApp.add(lineBtn);
        paintApp.add(ovalBtn);
        paintApp.add(rectangleBtn);

        paintApp.add(eraserBtn);
    }

    public void buttonHandlers(){
        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Undo");
                paintApp.undo();
            }
        });

        redo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Redo");
                paintApp.redo();
            }
        });

        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Clear");
                Graphics g = paintApp.getGraphics();
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, paintApp.getWidth(), paintApp.getHeight());
                while(paintApp.undo());
                paintApp.setRedoAll(true);
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Save");
                paintApp.saveAppletAsImage();
            }
        });

        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Load");
                // paintApp.setShapes(Loader.fileLoader("shapes.txt"));
                paintApp.loadAppletFromImage();
            }
        });

        serialize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] shapes = new String[paintApp.getShapes().size()];
                System.out.println("Serialize");
                for (int i = 0; i < paintApp.getShapes().size(); i++) {
                    shapes[i] = paintApp.getShapes().get(i).serialize();
                }
                Serializer.Serialize(shapes);
            }
        });

        colorBtns[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Black");
                paintApp.setColor(Color.BLACK);
            }
        });

        colorBtns[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Red");
                paintApp.setColor(Color.RED);
            }
        });

        colorBtns[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Green");
                paintApp.setColor(Color.GREEN);
            }
        });

        colorBtns[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Blue");
                paintApp.setColor(Color.BLUE);
            }
        });

        colorBtns[4].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Yellow");
                paintApp.setColor(Color.YELLOW);
            }
        });


        filled.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (filled.getState()) {
                    System.out.println("Filled");
                    paintApp.setFilled(true);
                }
                else {
                    System.out.println("Not Filled");
                    paintApp.setFilled(false);
                }
            }
        });

        dotted.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (dotted.getState()) {
                    System.out.println("Dotted");
                    paintApp.setDotted(true);
                }
                else {
                    System.out.println("Not Dotted");
                    paintApp.setDotted(false);
                }
            }
        });

        lineBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Line");
                paintApp.setShape(Constants.ShapeType.LINE);
            }
        });

        ovalBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Oval");
                paintApp.setShape(Constants.ShapeType.OVAL);
            }
        });

        rectangleBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Rectangle");
                paintApp.setShape(Constants.ShapeType.RECTANGLE);
            }
        });

        eraserBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Eraser");
                paintApp.setShape(Constants.ShapeType.RECTANGLE);
            }
        });
    }
}