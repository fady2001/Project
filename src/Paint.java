import java.applet.Applet;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

enum ShapeType {LINE, OVAL, RECTANGLE};

public class Paint extends Applet {
    private int x1;
    private int y1;
    private int x2;
    private int y2;

    private ArrayList<Shape> shapes;
    private Shape currentShape;

    private boolean filled = false;
    private boolean dotted = false;
    private Color color = Color.BLACK;
    private ShapeType shapeType = ShapeType.LINE;

    @Override
    public void init() {
        shapes = new ArrayList<Shape>();

        class MouseHandler extends MouseAdapter {
            public void mousePressed(MouseEvent e) {
                x1 = e.getX();
                y1 = e.getY();
            }

            public void mouseDragged(MouseEvent e) {
                x2 = e.getX();
                y2 = e.getY();
                switch (shapeType) {
                    case LINE:
                        currentShape = new Line(x1, y1, x2, y2, color);
                        break;
                    case OVAL:
                        currentShape = new Oval((x2 - x1) > 0 ? x1 : x2, (y2 - y1) > 0 ? y1 : y2, Math.abs(x2 - x1), Math.abs(y2 - y1), color);
                        break;
                    default:
                        break;
                }
                repaint();
            }

            public void mouseReleased(MouseEvent e) {
                if (currentShape != null) {
                    x2 = e.getX();
                    y2 = e.getY();
                    shapes.add(currentShape);
                    currentShape = null;
                    repaint();
                }
            }
        }
        MouseHandler mousehndl = new MouseHandler();
        addMouseListener(mousehndl);
        addMouseMotionListener(mousehndl);

        ControlBar controlBar = new ControlBar(this);
        controlBar.add();
        controlBar.buttonHandlers();
    }

    @Override
    public void paint(Graphics g) {
        for (Shape shape : shapes) {
            shape.draw(g);
        }
        if (currentShape != null) {
            currentShape.draw(g);
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

    public boolean isFilled() {
        return filled;
    }

    public boolean isDotted() {
        return dotted;
    }

    public Color getColor() {
        return color;
    }

    public void setShape(ShapeType shapeType) {
        this.shapeType = shapeType;
    }

    public ShapeType getShape() {
        return shapeType;
    }

    public ArrayList<Shape> getShapes() {
        return shapes;
    }

    public void setShapes(ArrayList<Shape> shapes) {
        this.shapes = shapes;
        repaint();
    }
}

class Main {
    public static void main(String[] args) {
        Paint app = new Paint();
        Frame frame = new Frame();
        app.init();
        frame.add(app);
        frame.setSize(700, 700);
        frame.setVisible(true);
    }
}