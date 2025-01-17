import java.awt.Color;
import java.awt.Graphics;

public abstract class Shape {
    private int x1;
    private int y1;
    private Color color;
    
    public abstract void draw(Graphics g);
    public abstract void fill(Graphics g);
    public abstract String serialize();

    Shape (int x1, int y1, Color color) {
        this.x1 = x1;
        this.y1 = y1;
        this.color = color;
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
    
    Line(int x1, int y1, int x2, int y2, Color color) {
        super(x1, y1, color);
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(getColor());
        g.drawLine(getX1(), getY1(), x2, y2);
    }

    @Override
    public void fill(Graphics g) {
        g.setColor(getColor());
        g.drawLine(getX1(), getY1(), x2, y2);
    }

    @Override
    public String serialize() {
        return "Line:" + getX1() + ":" + getY1() + ":" + x2 + ":" + y2 + ":" + getColor().getRGB();
    }
}

class Oval extends Shape {
    private int width;
    private int height;

    Oval(int x1, int y1, int width, int height, Color color) {
        super(x1, y1, color);
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(getColor());
        g.drawOval(getX1(), getY1(), width, height);
    }

    @Override
    public void fill(Graphics g) {
        g.setColor(getColor());
        g.fillOval(getX1(), getY1(), width, height);
    }

    @Override
    public String serialize() {
        return "Oval:" + getX1() + ":" + getY1() + ":" + width + ":" + height + ":" + getColor().getRGB();
    }
}

class Rectangle extends Shape { 
    protected int width;
    protected int height;

    Rectangle(int x1, int y1, int width, int height, Color color) {
        super(x1, y1, color);
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(getColor());
        g.drawRect(getX1(), getY1(), width, height);
    }

    @Override
    public void fill(Graphics g) {
        g.setColor(getColor());
        g.fillRect(getX1(), getY1(), width, height);
    }

    @Override
    public String serialize() {
        return "Rectangle:" + getX1() + ":" + getY1() + ":" + width + ":" + height + ":" + getColor().getRGB();
    }
}

class RoundRectangle extends Rectangle {
    private int arcWidth;
    private int arcHeight;

    RoundRectangle(int x1, int y1, int width, int height, int arcWidth, int arcHeight, Color color) {
        super(x1, y1, width, height, color);
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(getColor());
        g.drawRoundRect(getX1(), getY1(), width, height, arcWidth, arcHeight);
    }

    @Override
    public void fill(Graphics g) {
        g.setColor(getColor());
        g.fillRoundRect(getX1(), getY1(), width, height, arcWidth, arcHeight);
    }

    @Override
    public String serialize() {
        return "RoundRectangle:" + getX1() + ":" + getY1() + ":" + width + ":" + height + ":" + arcWidth + ":" + arcHeight + ":" + getColor().getRGB();
    }
}



