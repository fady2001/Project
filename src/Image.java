import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Image extends Drawing {
    private final BufferedImage img;
    private String filePath;
    public Image(BufferedImage img, String filePath) {
        super(Color.BLACK);
        this.img = img;
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(img, 0, 0, Constants.MAX_X, Constants.MAX_Y, null);
    }

    @Override
    public String serialize() {
        return "Image:" + filePath;
    }
}