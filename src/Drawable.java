import java.awt.Graphics2D;

public interface Drawable {
    void draw(Graphics2D g);
    String serialize();
}
