import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Paint app = new Paint();
        Frame frame = new Frame();
        app.init();
        frame.add(app);
        frame.setSize(Constants.MAX_X, Constants.MAX_Y);
        frame.setVisible(true);
    }
}
