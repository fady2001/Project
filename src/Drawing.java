import java.awt.Color;
import java.awt.Graphics2D;

public abstract class Drawing {
    protected Color color;
    
    public Drawing(Color color) {
        this.color = color;
    }
    public abstract void draw(Graphics2D g);
    public abstract String serialize();
}


