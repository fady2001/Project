import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShapesButtons {
    private final Button lineBtn;
    private final Button ovalBtn;
    private final Button rectangleBtn;
    private final Button eraserBtn;
    private final Button freehandBtn;

    public ShapesButtons(){
        lineBtn = new Button("Line");
        ovalBtn = new Button("Oval");
        rectangleBtn = new Button("Rectangle");
        eraserBtn = new Button("Eraser");
        freehandBtn = new Button("Freehand");
    }

    public void add(Paint paintApp){
        paintApp.add(lineBtn);
        paintApp.add(ovalBtn);
        paintApp.add(rectangleBtn);
        paintApp.add(eraserBtn);
        paintApp.add(freehandBtn);
    }

    public void buttonHandlers(Paint paintApp){
        lineBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paintApp.setShape(Constants.ShapeType.LINE);
            }
        });

        ovalBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paintApp.setShape(Constants.ShapeType.OVAL);
            }
        });

        rectangleBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paintApp.setShape(Constants.ShapeType.RECTANGLE);
            }
        });

        eraserBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paintApp.setShape(Constants.ShapeType.ERASER);
            }
        });

        freehandBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paintApp.setShape(Constants.ShapeType.FREEHAND);
            }
        });
    }
}