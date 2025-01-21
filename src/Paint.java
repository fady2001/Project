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

    private final Stack<Shape> undoStack = new Stack<>();
    private ArrayList<Shape> shapes = new ArrayList<>();
    private Shape currentShape = null;
    private ArrayList<Point> freehandPoints = new ArrayList<>();

    private boolean filled = false;
    private boolean dotted = false;
    private Color color = Color.BLACK;
    private Constants.ShapeType shapeType = Constants.ShapeType.FREEHAND;

    private boolean redoAll = false;

    private Image bufferedImage = null;

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
                        currentShape = new Line(x1, y1, x2, y2, color, filled, dotted);
                        break;
                    case OVAL:
                        currentShape = new Oval((x2 - x1) > 0 ? x1 : x2, (y2 - y1) > 0 ? y1 : y2, Math.abs(x2 - x1),
                                Math.abs(y2 - y1), color, filled, dotted);
                        break;
                    case RECTANGLE:
                        currentShape = new Rectangle((x2 - x1) > 0 ? x1 : x2, (y2 - y1) > 0 ? y1 : y2, Math.abs(x2 - x1),
                                Math.abs(y2 - y1), color, filled, dotted);
                        break;
                    case ERASER:
                        currentShape = new Rectangle(x2, y2, 20, 20, Color.WHITE, true, false);
                        shapes.add(currentShape);
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
                    shapes.add(new Freehand(freehandPoints, color));
                    freehandPoints = new ArrayList<>();
                } else if (currentShape != null) {
                    x2 = e.getX();
                    y2 = e.getY();
                    shapes.add(currentShape);
                    currentShape = null;
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
        for (Shape shape : shapes) {
            shape.draw((Graphics2D)g);
        }
        if (shapeType == Constants.ShapeType.FREEHAND) {
            for (int i = 0; i < freehandPoints.size() - 1; i++) {
                Point p1 = freehandPoints.get(i);
                Point p2 = freehandPoints.get(i + 1);
                g.setColor(color);
                g.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        } else if (currentShape != null) {
            currentShape.draw((Graphics2D)g);
        }
        if (bufferedImage != null) {
            g.drawImage(bufferedImage, 0, 0, this);
        }
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public void setDotted(boolean dotted) {
        this.dotted = dotted;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setShape(Constants.ShapeType shapeType) {
        this.shapeType = shapeType;
    }

    public ArrayList<Shape> getShapes() {
        return shapes;
    }

    public void setShapes(ArrayList<Shape> shapes) {
        this.shapes = shapes;
        repaint();
    }

    public boolean undo() {
        if (!shapes.isEmpty()) {
            undoStack.push(shapes.remove(shapes.size() - 1));
            repaint();
            return true;
        }
        return false;
    }

    public void redo() {
        if (!undoStack.isEmpty()) {
            if (redoAll) {
                while (!undoStack.isEmpty()) {
                    shapes.add(undoStack.pop());
                }
                redoAll = false;
            } else
                shapes.add(undoStack.pop());
            repaint();
        }
    }

    public void setRedoAll(boolean redoAll) {
        this.redoAll = redoAll;
    }

    public void saveAppletAsImage() {
        try {
            // Create a BufferedImage with the size of the applet
            BufferedImage bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = (Graphics2D)bufferedImage.getGraphics();

            // Render the applet into the BufferedImage
            this.paint(graphics);

            // Save the BufferedImage as a PNG file
            File outputFile = new File("appletImage.png");
            ImageIO.write(bufferedImage, "png", outputFile);

            System.out.println("Applet saved as image: " + outputFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadAppletFromImage() {
        try {
            // Load the image from the file
            File inputFile = new File("appletImage.png");
            bufferedImage = ImageIO.read(inputFile);

            // Repaint the applet
            repaint();

            System.out.println("Applet loaded from image: " + inputFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}