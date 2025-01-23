import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageDrawing implements Drawable {
    private final BufferedImage img;
    private final String filePath;

    public ImageDrawing(BufferedImage img, String filePath) {
        this.img = img;
        this.filePath = filePath;
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(img, 0, 0, Constants.MAX_X, Constants.MAX_Y, null);
    }

    @Override
    public String serialize() {
        return "Image;" + filePath;
    }
}