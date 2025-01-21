import java.applet.Applet;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Stack;
import javax.imageio.ImageIO;
import java.awt.Point;

public class Paint extends Applet {
    private int x1;
    private int y1;
    private int x2;
    private int y2;

    private final Stack<Drawing> undoStack = new Stack<>();
    private ArrayList<Drawing> drawings = new ArrayList<>();
    private Drawing currentDrawing = null;
    private final ArrayList<Point> freehandPoints = new ArrayList<>();

    private boolean filled = false;
    private boolean dotted = false;
    private Color color = Color.BLACK;
    private Constants.ShapeType shapeType = Constants.ShapeType.FREEHAND;

    private boolean redoAll = false;

    private BufferedImage bufferedImage = null;

    ControlBar controlBar = new ControlBar(this);

    @Override
    public void init() {
        class MouseHandler extends MouseAdapter {
            public void mousePressed(MouseEvent e) {
                x1 = e.getX();
                y1 = e.getY();
                if (shapeType == Constants.ShapeType.FREEHAND) {
                    freehandPoints.clear();
                    freehandPoints.add(new Point(x1, y1));
                }
                System.out.println("Mouse pressed at " + x1 + ", " + y1);
            }

            public void mouseDragged(MouseEvent e) {
                x2 = e.getX();
                y2 = e.getY();
                switch (shapeType) {
                    case LINE:
                        currentDrawing = new Line(x1, y1, x2, y2, color, filled, dotted);
                        break;
                    case OVAL:
                        currentDrawing = new Oval((x2 - x1) > 0 ? x1 : x2, (y2 - y1) > 0 ? y1 : y2, Math.abs(x2 - x1),
                                Math.abs(y2 - y1), color, filled, dotted);
                        break;
                    case RECTANGLE:
                        currentDrawing = new Rectangle((x2 - x1) > 0 ? x1 : x2, (y2 - y1) > 0 ? y1 : y2, Math.abs(x2 - x1),
                                Math.abs(y2 - y1), color, filled, dotted);
                        break;
                    case ERASER:
                        currentDrawing = new Rectangle(x2, y2, 20, 20, Color.WHITE, true, false);
                        drawings.add(currentDrawing);
                        break;
                    case FREEHAND:
                        freehandPoints.add(new Point(x2, y2));
                        break;
                    default:
                        break;
                }
                repaint();
            }

            public void mouseReleased(MouseEvent e) {
                if (shapeType == Constants.ShapeType.FREEHAND) {
                    drawings.add(new Freehand(freehandPoints, color));
                    freehandPoints.clear();
                } else if (currentDrawing != null) {
                    x2 = e.getX();
                    y2 = e.getY();
                    drawings.add(currentDrawing);
                    currentDrawing = null;
                }
                repaint();
            }
        }
        MouseHandler mouseHandler = new MouseHandler();
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);

        controlBar.add();
        controlBar.buttonHandlers();
    }

    @Override
    public void paint(Graphics g) {
        for (Drawing drawing : drawings) {
            drawing.draw((Graphics2D)g);
        }
        if (shapeType == Constants.ShapeType.FREEHAND) {
            for (int i = 0; i < freehandPoints.size() - 1; i++) {
                Point p1 = freehandPoints.get(i);
                Point p2 = freehandPoints.get(i + 1);
                g.setColor(color);
                g.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }
        if (currentDrawing != null) {
            currentDrawing.draw((Graphics2D)g);
        }
    }

    public void toggleFilled() {
        filled = !filled;
    }

    public void toggleDotted() {
        dotted = !dotted;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setShape(Constants.ShapeType shapeType) {
        this.shapeType = shapeType;
        setCurrentDrawing();
    }

    private void setCurrentDrawing() {
        switch (shapeType) {
            case LINE:
                if (currentDrawing instanceof Line) {
                    return;
                }
                currentDrawing = new Line(x1, y1, x2, y2, color, filled, dotted);
                break;
            case OVAL:
                if (currentDrawing instanceof Oval) {
                    return;
                }
                currentDrawing = new Oval((x2 - x1) > 0 ? x1 : x2, (y2 - y1) > 0 ? y1 : y2, Math.abs(x2 - x1),
                        Math.abs(y2 - y1), color, filled, dotted);
                break;
            case RECTANGLE:
                if (currentDrawing instanceof Oval) {
                    return;
                }
                currentDrawing = new Rectangle((x2 - x1) > 0 ? x1 : x2, (y2 - y1) > 0 ? y1 : y2, Math.abs(x2 - x1),
                        Math.abs(y2 - y1), color, filled, dotted);
                break;
            default:
                break;
        }
    }

    public ArrayList<Drawing> getDrawings() {
        return drawings;
    }

    public void setDrawings(ArrayList<Drawing> drawings) {
        this.drawings = drawings;
        repaint();
    }

    public boolean undo() {
        if (!drawings.isEmpty()) {
            undoStack.push(drawings.remove(drawings.size() - 1));
            repaint();
            return true;
        }
        return false;
    }

    public void redo() {
        if (!undoStack.isEmpty()) {
            if (redoAll) {
                while (!undoStack.isEmpty()) {
                    drawings.add(undoStack.pop());
                }
                redoAll = false;
            } else
                drawings.add(undoStack.pop());
            repaint();
        }
    }

    public void toggleRedoAll() {
        redoAll = !redoAll;
    }

    public void saveAppletToImage(String filePath) {
        try {
            // Create a BufferedImage with the size of the applet
            BufferedImage bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = (Graphics2D)bufferedImage.getGraphics();

            // Render the applet into the BufferedImage
            this.paint(graphics);

            // Save the BufferedImage as a PNG file
            File outputFile = new File(filePath);
            ImageIO.write(bufferedImage, "png", outputFile);

            System.out.println("Applet saved as image: " + outputFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadAppletFromImage(String filePath) {
        try {

            File inputFile = new File(filePath);
            bufferedImage = ImageIO.read(inputFile);
            drawings.add(new Image(bufferedImage,filePath));

            repaint();

            System.out.println("Applet loaded from image: " + inputFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}