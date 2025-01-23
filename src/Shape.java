import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public abstract class Shape extends GraphicElement {
    protected int x1;
    protected int y1;
    protected boolean filled;
    protected boolean dotted;

    public Shape() {
        super(Color.BLACK);
    }

    public Shape(int x1, int y1, Color color, boolean filled, boolean dotted) {
        super(color);
        this.x1 = x1;
        this.y1 = y1;
        this.color = color;
        this.filled = filled;
        this.dotted = dotted;
    }

    public void setProperties(int x1, int y1, Color color, boolean filled, boolean dotted) {
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

    public Line() {
    }

    Line(int x1, int y1, int x2, int y2, Color color, boolean filled, boolean dotted) {
        super(x1, y1, color, filled, dotted);
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        if (dotted && !filled) {
            g.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 9 }, 0));
            g.drawLine(x1, y1, x2, y2);
            g.setStroke(new BasicStroke());
        } else
            g.drawLine(x1, y1, x2, y2);
    }

    @Override
    public String serialize() {
        return "Line;" + x1 + ";" + y1 + ";" + x2 + ";" + y2 + ";" + color.getRGB() + ";" + filled + ";" + dotted;
    }

    public void setProperties(int x1, int y1, int x2, int y2, Color color, boolean filled, boolean dotted) {
        super.setProperties(x1, y1, color, filled, dotted);
        this.x2 = x2;
        this.y2 = y2;
    }
}

class Oval extends Shape {
    private int width;
    private int height;

    public Oval() {
    }

    Oval(int x1, int y1, int width, int height, Color color, boolean filled, boolean dotted) {
        super(x1, y1, color, filled, dotted);
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        if (!filled && !dotted)
            g.drawOval(x1, y1, width, height);
        else if (filled)
            g.fillOval(x1, y1, width, height);
        else {
            g.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 9 }, 0));
            g.drawOval(x1, y1, width, height);
            g.setStroke(new BasicStroke());

        }
    }

    @Override
    public String serialize() {
        return "Oval;" + x1 + ";" + y1 + ";" + width + ";" + height + ";" + color.getRGB() + ";" + filled + ";"
                + dotted;
    }

    public void setProperties(int x1, int y1, int x2, int y2, Color color, boolean filled, boolean dotted) {
        super.setProperties(Math.min(x1,x2), Math.min(y1,y2), color, filled, dotted);
        this.width = Math.abs(x2 - x1);
        this.height = Math.abs(y2 - y1);
    }
}

class Rectangle extends Shape {
    protected int width;
    protected int height;

    public Rectangle() {
    }

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
            g.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 9 }, 0));
            g.drawRect(x1, y1, width, height);
            g.setStroke(new BasicStroke());
        }
    }

    @Override
    public String serialize() {
        return "Rectangle;" + x1 + ";" + y1 + ";" + width + ";" + height + ";" + color.getRGB() + ";" + filled + ";"
                + dotted;
    }

    public void setProperties(int x1, int y1, int x2, int y2, Color color, boolean filled, boolean dotted) {
        super.setProperties(Math.min(x1,x2), Math.min(y1,y2), color, filled, dotted);
        this.width = Math.abs(x2 - x1);
        this.height = Math.abs(y2 - y1);
    }
}