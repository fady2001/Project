import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.CheckboxGroup;

public class ControlButtons {
    private final Button undo;
    private final Button redo;
    private final Button clear;
    private final Button saveToImg;
    private final Button loadFromImg;
    private final Button saveToFile;
    private final Button loadFromFile;

    private final Checkbox filled;
    private final Checkbox dotted;

    public ControlButtons(){
        undo = new Button("Undo");
        redo = new Button("Redo");
        clear = new Button("Clear");
        saveToImg = new Button("Save to Image");    
        loadFromImg = new Button("Load from Image");
        saveToFile = new Button("Save to File");
        loadFromFile = new Button("Load from File");
        CheckboxGroup group = new CheckboxGroup();
        filled = new Checkbox("Filled", group, true);
        dotted = new Checkbox("Dotted", group, false);
    }

    public void add(Paint paintApp){
        paintApp.add(undo);
        paintApp.add(redo);
        paintApp.add(clear);
        paintApp.add(saveToImg);
        paintApp.add(loadFromImg);
        paintApp.add(saveToFile);
        paintApp.add(loadFromFile);
        paintApp.add(filled);
        paintApp.add(dotted);
    }

    public void buttonHandlers(Paint paintApp){
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

        saveToImg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Save");
                paintApp.saveAppletAsImage();
            }
        });

        loadFromImg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Load");
                paintApp.loadAppletFromImage();
            }
        });

        loadFromFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Load");
                paintApp.setShapes(Loader.fileLoader("shapes.txt"));
            }
        });

        saveToFile.addActionListener(new ActionListener() {
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
    }
}
