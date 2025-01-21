import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.Point;

public abstract class Shape extends Drawing {
    protected int x1;
    protected int y1;
    protected boolean filled;
    protected boolean dotted;

    Shape(int x1, int y1, Color color, boolean filled, boolean dotted) {
        super(color);
        this.x1 = x1;
        this.y1 = y1;
        this.color = color;
        this.filled = filled;
        this.dotted = dotted;
    }
}

class Line extends Shape {
    private int x2;
    private int y2;

    Line(int x1, int y1, int x2, int y2, Color color, boolean filled, boolean dotted) {
        super(x1, y1, color, filled, dotted);
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        if (!dotted)
            g.drawLine(x1, y1, x2, y2);
        else {
            g.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 9 }, 0));
            g.drawLine(x1, y1, x2, y2);
            g.setStroke(new BasicStroke());
        }
    }

    @Override
    public String serialize() {
        return "Line:" + x1 + ":" + y1 + ":" + x2 + ":" + y2 + ":" + color.getRGB() + ":" + filled + ":" + dotted;
    }
}

class Oval extends Shape {
    private int width;
    private int height;

    Oval(int x1, int y1, int width, int height, Color color, boolean filled, boolean dotted) {
        super(x1, y1, color, filled, dotted);
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics2D g) {
        System.out.println("Drawing oval: " + filled);
        g.setColor(color);
        if (!filled && !dotted)
            g.drawOval(x1, y1, width, height);
        else if (filled)
            g.fillOval(x1, y1, width, height);
        else {
            System.out.println("Dotted");
            g.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 9 }, 0));
            g.drawOval(x1, y1, width, height);
            g.setStroke(new BasicStroke());

        }
    }

    @Override
    public String serialize() {
        System.out.println("Oval:" + x1 + ":" + y1 + ":" + width + ":" + height + ":" + color.getRGB() + ":" + filled
                + ":" + dotted);
        return "Oval:" + x1 + ":" + y1 + ":" + width + ":" + height + ":" + color.getRGB() + ":" + filled + ":"
                + dotted;
    }
}

class Rectangle extends Shape {
    protected int width;
    protected int height;

    Rectangle(int x1, int y1, int width, int height, Color color, boolean filled, boolean dotted) {
        super(x1, y1, color, filled, dotted);
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        if (!filled && !dotted)
            g.drawRect(x1, y1, width, height);
        else if (filled)
            g.fillRect(x1, y1, width, height);
        else {
            System.out.println("Dotted");
            g.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 9 }, 0));
            g.drawRect(x1, y1, width, height);
            g.setStroke(new BasicStroke());
        }
    }

    @Override
    public String serialize() {
        return "Rectangle:" + x1 + ":" + y1 + ":" + width + ":" + height + ":" + color.getRGB() + ":" + filled + ":"
                + dotted;
    }
}

class Freehand extends Drawing {
    private ArrayList<Point> points;

    public Freehand(ArrayList<Point> points, Color color) {
        super(color);
        this.points = new ArrayList<>(points);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        for (int i = 0; i < points.size() - 1; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get(i + 1);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }

    }

    @Override
    public String serialize() {
        StringBuilder sb = new StringBuilder("Freehand:");
        for (Point p : points) {
            sb.append(p.x).append(":").append(p.y).append(":");
        }
        sb.append(color.getRGB());
        return sb.toString();
    }
}
