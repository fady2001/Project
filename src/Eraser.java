import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Eraser extends Drawing {
    private ArrayList<Rectangle> eraser = new ArrayList<>();
    
    public Eraser() {
        super(Color.WHITE);
    }

    @Override
    public void draw(Graphics2D g) {
        for (Rectangle r : eraser) {
            r.draw(g);
        }
    }

    @Override
    public String serialize() {
        StringBuilder sb = new StringBuilder("Eraser:");
        for (Rectangle r : eraser) {
            sb.append(r.serialize()).append(":");
        }
        return sb.toString();
    }

    public void addRectangle(Rectangle rectangle) {
        eraser.add(rectangle);
    }
    
}
