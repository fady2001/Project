import java.awt.Color;

public abstract class GraphicElement implements Drawable {
    protected Color color;
    
    public GraphicElement(Color color) {
        this.color = color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}