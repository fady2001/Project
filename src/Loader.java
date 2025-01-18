import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Loader {
    public static ArrayList<Shape> fileLoader(String fileName) {
        ArrayList<Shape> shapes = new ArrayList<Shape>();
        try {
            File file = new File(fileName);
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                String[] splitted = data.split(":");
                if (splitted[0].equals("Line")) {
                    shapes.add(
                        new Line(
                            Integer.parseInt(splitted[1]), 
                            Integer.parseInt(splitted[2]), 
                            Integer.parseInt(splitted[3]), 
                            Integer.parseInt(splitted[4]), 
                            new Color(Integer.parseInt(splitted[5])), 
                            Boolean.parseBoolean(splitted[6]), 
                            Boolean.parseBoolean(splitted[7])
                        )
                    );
                } else if (splitted[0].equals("Oval")) {
                    shapes.add(
                        new Oval(
                            Integer.parseInt(splitted[1]), 
                            Integer.parseInt(splitted[2]), 
                            Integer.parseInt(splitted[3]), 
                            Integer.parseInt(splitted[4]), 
                            new Color(Integer.parseInt(splitted[5])), 
                            Boolean.parseBoolean(splitted[6]), 
                            Boolean.parseBoolean(splitted[7])
                        )
                    );
                } else if (splitted[0].equals("Freehand")) {
                    ArrayList<Point> points = new ArrayList<Point>();
                    for (int i = 1; i < splitted.length - 2; i += 2) {
                        points.add(new Point(Integer.parseInt(splitted[i]), Integer.parseInt(splitted[i + 1])));
                    }
                    shapes.add(new Freehand(points, new Color(Integer.parseInt(splitted[splitted.length - 1]))));
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return shapes;
    }
}
