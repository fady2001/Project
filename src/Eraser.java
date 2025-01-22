import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Eraser extends Drawing {
    private final ArrayList<Rectangle> eraser;

    public Eraser(ArrayList<Rectangle> rectangles) {
        super(Color.WHITE);
        this.eraser = rectangles;
    }

    @Override
    public void draw(Graphics2D g) {
        for (Rectangle r : eraser) {
            r.draw(g);
        }
    }

    @Override
    public String serialize() {
        StringBuilder sb = new StringBuilder("Eraser;");
        for (Rectangle r : eraser) {
            // get only the coordinates x and y of the rectangle
            String[] splits = r.serialize().split(";");
            sb.append(splits[1] + ';' + splits[2]).append(";");
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    public void addRectangle(Rectangle rectangle) {
        eraser.add(rectangle);
    }

}
