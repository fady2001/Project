import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

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

    public ControlButtons() {
        undo = new Button("Undo");
        redo = new Button("Redo");
        clear = new Button("Clear");
        saveToImg = new Button("Save to Image");
        loadFromImg = new Button("Load from Image");
        saveToFile = new Button("Save to File");
        loadFromFile = new Button("Load from File");
        filled = new Checkbox("Filled", false);
        dotted = new Checkbox("Dotted", false);
    }

    public void add(Paint paintApp) {
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

    public void buttonHandlers(Paint paintApp) {
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
                while (paintApp.undo())
                    ;
                paintApp.toggleRedoAll();
            }
        });

        saveToImg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Save");
                FileDialog fileDialog = new FileDialog((Frame) null, "Save into");
                fileDialog.setMode(FileDialog.SAVE);
                fileDialog.setVisible(true);
                String directory = fileDialog.getDirectory();
                String file = fileDialog.getFile();
                if (directory != null && file != null) {
                    String filePath = directory + file;
                    System.out.println("Selected file: " + filePath);
                    paintApp.saveAppletToImage(filePath+".png");
                }
            }
        });

        loadFromImg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog fileDialog = new FileDialog((Frame) null, "Select File to Load");
                fileDialog.setMode(FileDialog.LOAD);
                fileDialog.setVisible(true);
                String directory = fileDialog.getDirectory();
                String file = fileDialog.getFile();
                if (directory != null && file != null) {
                    String filePath = directory + file;
                    System.out.println("Selected file: " + filePath);
                    paintApp.loadAppletFromImage(filePath);
                }
            }
        });

        loadFromFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog fileDialog = new FileDialog((Frame) null, "Save into");
                fileDialog.setMode(FileDialog.LOAD);
                fileDialog.setVisible(true);
                String directory = fileDialog.getDirectory();
                String file = fileDialog.getFile();
                if (directory != null && file != null) {
                    String filePath = directory + file;
                    System.out.println("Selected file: " + filePath);
                    paintApp.setDrawings(Loader.fileLoader(filePath+".txt"));
                }
            }
        });

        saveToFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog fileDialog = new FileDialog((Frame) null, "Select File to Load");
                fileDialog.setMode(FileDialog.SAVE);
                fileDialog.setVisible(true);
                String directory = fileDialog.getDirectory();
                String file = fileDialog.getFile();
                if (directory != null && file != null) {
                    String filePath = directory + file;
                    System.out.println("Selected file: " + filePath);
                    String[] shapes = new String[paintApp.getDrawings().size()];
                    System.out.println("Serialize");
                    for (int i = 0; i < paintApp.getDrawings().size(); i++) {
                        shapes[i] = paintApp.getDrawings().get(i).serialize();
                    }
                    Serializer.Serialize(shapes, filePath);
                }
            }
        });

        filled.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                paintApp.toggleFilled();
            }
        });

        dotted.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                paintApp.toggleDotted();
            }
        });
    }
}
