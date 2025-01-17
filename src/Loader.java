import java.awt.Color;
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
                    shapes.add(new Line(Integer.parseInt(splitted[1]), Integer.parseInt(splitted[2]), Integer.parseInt(splitted[3]), Integer.parseInt(splitted[4]), new Color(Integer.parseInt(splitted[5]))));
                } else if (splitted[0].equals("Oval")) {
                    shapes.add(new Oval(Integer.parseInt(splitted[1]), Integer.parseInt(splitted[2]), Integer.parseInt(splitted[3]), Integer.parseInt(splitted[4]), new Color(Integer.parseInt(splitted[5]))));
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
