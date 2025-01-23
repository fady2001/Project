import java.applet.Applet;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Stack;
import javax.imageio.ImageIO;

public class Paint extends Applet {

    private int x1;
    private int y1;
    private int x2;
    private int y2;

    private final Stack<Drawable> undoStack = new Stack<>();
    private ArrayList<Drawable> drawables = new ArrayList<>();

    private boolean filled = false;
    private boolean dotted = false;
    private Color color = Color.BLACK;
    private Constants.DrawingType drawingType = Constants.DrawingType.FREEHAND;

    private boolean redoAll = false;

    ControlBar controlBar = new ControlBar(this);

    private Line reusableLine = new Line();
    private Oval reusableOval = new Oval();
    private Rectangle reusableRectangle = new Rectangle();
    private Freehand reusableFreehand = new Freehand();
    private Eraser reusableEraser = new Eraser(new ArrayList<>());

    boolean dragged = false;

    @Override
    public void init() {
        class MouseHandler extends MouseAdapter {
            public void mousePressed(MouseEvent e) {
                x1 = e.getX();
                y1 = e.getY();
                if (drawingType == Constants.DrawingType.FREEHAND) {
                    reusableFreehand.setColor(color);
                    reusableFreehand.addPoint(new Point(x1, y1));
                } else if (drawingType == Constants.DrawingType.ERASER) {
                    reusableEraser.addRectangle(
                            new Rectangle(
                                    x1 - (int) (0.5 * Constants.ERASER_SIZE),
                                    y1 - (int) (0.5 * Constants.ERASER_SIZE),
                                    Constants.ERASER_SIZE, Constants.ERASER_SIZE, Color.WHITE, true, false));
                }
                System.out.println("Mouse pressed at " + x1 + ", " + y1);
            }

            public void mouseDragged(MouseEvent e) {
                dragged = true;
                x2 = e.getX();
                y2 = e.getY();
                switch (drawingType) {
                    case LINE:
                        reusableLine.setProperties(x1, y1, x2, y2, color, filled, dotted);
                        break;
                    case OVAL:
                        reusableOval.setProperties(x1, y1, x2, y2, color, filled, dotted);
                        break;
                    case RECTANGLE:
                        reusableRectangle.setProperties(x1, y1, x2, y2, color, filled, dotted);
                        break;
                    case ERASER:
                        reusableEraser.addRectangle(
                                new Rectangle(
                                        x2 - (int) (0.5 * Constants.ERASER_SIZE),
                                        y2 - (int) (0.5 * Constants.ERASER_SIZE),
                                        Constants.ERASER_SIZE, Constants.ERASER_SIZE, Color.WHITE, true, false
                                ));
                        break;
                    case FREEHAND:
                        reusableFreehand.addPoint(new Point(x2, y2));
                        break;
                    default:
                        break;
                }
                repaint();
            }

            public void mouseReleased(MouseEvent e) {
                if (dragged) {
                    dragged = false;
                    switch (drawingType) {
                        case LINE:
                            drawables.add(reusableLine);
                            reusableLine = new Line();
                            break;
                        case OVAL:
                            drawables.add(reusableOval);
                            reusableOval = new Oval();
                            break;
                        case RECTANGLE:
                            drawables.add(reusableRectangle);
                            reusableRectangle = new Rectangle();
                            break;
                        case ERASER:
                            drawables.add(reusableEraser);
                            reusableEraser = new Eraser(new ArrayList<>());
                            break;
                        case FREEHAND:
                            drawables.add(reusableFreehand);
                            reusableFreehand = new Freehand();
                            break;
                        default:
                            break;
                    }
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
    public void update(Graphics g) {
        // create offscreen image
        Image buffer = createImage(getWidth(), getHeight());
        // paint on the offscreen
        paint(buffer.getGraphics());
        // draw offscreen on the on screen
        g.drawImage(buffer, 0, 0, null);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Draw all stored drawings
        for (Drawable drawable : drawables) {
            drawable.draw(g2d);
        }

        // Draw the current shape being dragged
        switch (drawingType) {
            case LINE:
                if (reusableLine != null) {
                    reusableLine.draw(g2d);
                }
                break;
            case OVAL:
                if (reusableOval != null) {
                    reusableOval.draw(g2d);
                }
                break;
            case RECTANGLE:
                if (reusableRectangle != null) {
                    reusableRectangle.draw(g2d);
                }
                break;
            case ERASER:
                if (reusableEraser != null) {
                    reusableEraser.draw(g2d);
                }
                break;
            case FREEHAND:
                if (reusableFreehand != null) {
                    reusableFreehand.draw(g2d);
                }
                break;
            default:
                break;
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

    public void setShape(Constants.DrawingType drawingType) {
        this.drawingType = drawingType;
    }

    public ArrayList<Drawable> getDrawings() {
        return drawables;
    }

    public void setDrawings(ArrayList<Drawable> drawables) {
        this.drawables = drawables;
        repaint();
    }

    public void undo() {
        if (drawables.isEmpty())
            return;
        if (redoAll) {
            while (!drawables.isEmpty()) {
                undoStack.push(drawables.remove(drawables.size() - 1));
            }
            redoAll = false;
        } else {
            undoStack.push(drawables.remove(drawables.size() - 1));
        }
        repaint();
    }

    public void redo() {
        if (undoStack.isEmpty()) {
            return;
        }

        if (redoAll) {
            while (!undoStack.isEmpty()) {
                drawables.add(undoStack.pop());
            }
            redoAll = false;
        } else {
            drawables.add(undoStack.pop());
        }

        repaint();
    }

    public void toggleRedoAll() {
        redoAll = !redoAll;
    }

    public void saveAppletToImage(String filePath) {
        try {
            // Create a BufferedImage with the size of the applet
            BufferedImage bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();

            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, getWidth(), getHeight());
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
            BufferedImage bufferedImage = ImageIO.read(inputFile);
            drawables.add(new ImageDrawing(bufferedImage, filePath));

            repaint();

            System.out.println("Applet loaded from image: " + inputFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}