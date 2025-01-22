import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class Freehand extends Drawing {
    private final ArrayList<Point> points;

    public Freehand(ArrayList<Point> points, Color color) {
        super(color);
        this.points = new ArrayList<>(points);
    }

    public Freehand() {
        super(Color.BLACK);
        points = new ArrayList<>();
    }

    public void addPoint(Point p) {
        points.add(p);
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
        StringBuilder sb = new StringBuilder("Freehand;");
        for (Point p : points) {
            sb.append(p.x).append(";").append(p.y).append(";");
        }
        sb.append(color.getRGB());
        return sb.toString();
    }
}