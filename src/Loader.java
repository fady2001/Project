import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Loader {
    public static ArrayList<Drawing> fileLoader(String fileName) {
        ArrayList<Drawing> drawings = new ArrayList<>();
        try {
            File file = new File(fileName);
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                String[] splits = data.split(";");
                if (splits[0].equals("Line")) {
                    drawings.add(
                            new Line(
                                    Integer.parseInt(splits[1]),
                                    Integer.parseInt(splits[2]),
                                    Integer.parseInt(splits[3]),
                                    Integer.parseInt(splits[4]),
                                    new Color(Integer.parseInt(splits[5])),
                                    Boolean.parseBoolean(splits[6]),
                                    Boolean.parseBoolean(splits[7])));
                } else if (splits[0].equals("Oval")) {
                    drawings.add(
                            new Oval(
                                    Integer.parseInt(splits[1]),
                                    Integer.parseInt(splits[2]),
                                    Integer.parseInt(splits[3]),
                                    Integer.parseInt(splits[4]),
                                    new Color(Integer.parseInt(splits[5])),
                                    Boolean.parseBoolean(splits[6]),
                                    Boolean.parseBoolean(splits[7])));
                } else if (splits[0].equals("Rectangle")) {
                    drawings.add(
                            new Rectangle(
                                    Integer.parseInt(splits[1]),
                                    Integer.parseInt(splits[2]),
                                    Integer.parseInt(splits[3]),
                                    Integer.parseInt(splits[4]),
                                    new Color(Integer.parseInt(splits[5])),
                                    Boolean.parseBoolean(splits[6]),
                                    Boolean.parseBoolean(splits[7])));
                } else if (splits[0].equals("Freehand")) {
                    ArrayList<Point> points = new ArrayList<>();
                    for (int i = 1; i < splits.length - 2; i += 2) {
                        points.add(new Point(Integer.parseInt(splits[i]), Integer.parseInt(splits[i + 1])));
                    }
                    drawings.add(new Freehand(points, new Color(Integer.parseInt(splits[splits.length - 1]))));
                } else if (splits[0].equals("Eraser")) {
                    ArrayList<Rectangle> rectangles = new ArrayList<>();
                    for (int i = 1; i < splits.length - 2; i += 2) {
                        rectangles.add(
                                new Rectangle(
                                        Integer.parseInt(splits[i]),
                                        Integer.parseInt(splits[i + 1]),
                                        Constants.ERASER_SIZE,
                                        Constants.ERASER_SIZE,
                                        Color.BLACK,
                                        true,
                                        false));
                    }
                    drawings.add(new Eraser(rectangles));
                } else if (splits[0].equals("Image")) {
                    drawings.add(new ImageDrawing(ImageIO.read(new File(splits[1])), splits[1]));

                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return drawings;
    }
}
