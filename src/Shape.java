import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.Point;

public abstract class Shape {
    private int x1;
    private int y1;
    private Color color;
    boolean filled = false;
    boolean dotted = false;
    
    public abstract void draw(Graphics2D g);
    public abstract String serialize();

    Shape (int x1, int y1, Color color, boolean filled, boolean dotted) {
        this.x1 = x1;
        this.y1 = y1;
        this.color = color;
        this.filled = filled;
        this.dotted = dotted;
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public Color getColor() {
        return color;
    }
}

class Line extends Shape {
    private int x2;
    private int y2;
    
    Line(int x1, int y1, int x2, int y2, Color color,boolean filled, boolean dotted) {
        super(x1, y1, color, filled, dotted);
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(getColor());
        if (dotted == false)
            g.drawLine(getX1(), getY1(), x2, y2);
        else if (dotted = true) {
            g.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 9 }, 0));
            g.drawLine(getX1(), getY1(), x2, y2);
            g.setStroke(new BasicStroke());
        }
    }

    @Override
    public String serialize() {
        return "Line:" + getX1() + ":" + getY1() + ":" + x2 + ":" + y2 + ":" + getColor().getRGB()+":" + filled + ":" + dotted;
    }
}

class Freehand extends Shape {
    private ArrayList<Point> points;

    public Freehand(ArrayList<Point> points, Color color) {
        super(points.get(0).x, points.get(0).y, color, false,false);
        this.points = new ArrayList<Point>(points);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(getColor());
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
        sb.append(getColor().getRGB());
        return sb.toString();
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
        System.out.println("Drawing oval: "+filled);
        g.setColor(getColor());
        if (filled == false && dotted == false)
            g.drawOval(getX1(), getY1(), width, height);
        else if (filled = true)
            g.fillOval(getX1(), getY1(), width, height);
        else if (dotted = true)
            System.out.println("Dotted");
    }

    @Override
    public String serialize() {
        System.out.println("Oval:" + getX1() + ":" + getY1() + ":" + width + ":" + height + ":" + getColor().getRGB()+":" + filled + ":" + dotted);
        return "Oval:" + getX1() + ":" + getY1() + ":" + width + ":" + height + ":" + getColor().getRGB()+":" + filled + ":" + dotted;
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
        g.setColor(getColor());
        if (filled == false && dotted == false)
            g.drawRect(getX1(), getY1(), width, height);
        else if (filled = true)
            g.fillRect(getX1(), getY1(), width, height);
        else if (dotted = true)
            System.out.println("Dotted");
    }

    @Override
    public String serialize() {
        return "Rectangle:" + getX1() + ":" + getY1() + ":" + width + ":" + height + ":" + getColor().getRGB()+":" + filled + ":" + dotted;
    }
}

// class RoundRectangle extends Rectangle {
//     private int arcWidth;
//     private int arcHeight;

//     RoundRectangle(int x1, int y1, int width, int height, int arcWidth, int arcHeight, Color color, boolean filled, boolean dotted) {
//         super(x1, y1, width, height, color, filled, dotted);
//         this.arcWidth = arcWidth;
//         this.arcHeight = arcHeight;
//     }

//     @Override
//     public void draw(Graphics g) {
//         g.setColor(getColor());
//         if (filled == false && dotted == false)
//             g.drawRoundRect(getX1(), getY1(), width, height, arcWidth, arcHeight);
//         else if (filled = true)
//             g.fillRoundRect(getX1(), getY1(), width, height, arcWidth, arcHeight);
//         else if (dotted = true)
//             System.out.println("Dotted");
//     }

//     @Override
//     public String serialize() {
//         return "RoundRectangle:" + getX1() + ":" + getY1() + ":" + width + ":" + height + ":" + arcWidth + ":" + arcHeight + ":" + getColor().getRGB()+":" + filled + ":" + dotted;
//     }
// }


