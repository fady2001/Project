import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShapesButtons {
    private Button lineBtn;
    private Button ovalBtn;
    private Button rectangleBtn;
    private Button eraserBtn;
    private Button freehandBtn;

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
                System.out.println("Line");
                paintApp.setShape(Constants.ShapeType.LINE);
            }
        });

        ovalBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Oval");
                paintApp.setShape(Constants.ShapeType.OVAL);
            }
        });

        rectangleBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Rectangle");
                paintApp.setShape(Constants.ShapeType.RECTANGLE);
            }
        });

        eraserBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Eraser");
                paintApp.setShape(Constants.ShapeType.ERASER);
            }
        });

        freehandBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Freehand");
                paintApp.setShape(Constants.ShapeType.FREEHAND);
            }
        });
    }
}