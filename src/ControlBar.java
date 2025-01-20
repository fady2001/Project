public class ControlBar {
    private Paint paintApp;
    private ShapesButtons shapesButtons;
    private ControlButtons controlButtons;
    private ColorButtons colorButtons;

    public ControlBar(Paint paintApp){
        this.paintApp = paintApp;
        shapesButtons = new ShapesButtons();
        controlButtons = new ControlButtons();
        colorButtons = new ColorButtons();
    }

    public void add(){
        shapesButtons.add(paintApp);
        controlButtons.add(paintApp);
        colorButtons.add(paintApp);
    }

    public void buttonHandlers(){
        shapesButtons.buttonHandlers(paintApp);
        controlButtons.buttonHandlers(paintApp);
        colorButtons.buttonHandlers(paintApp);
    }
}