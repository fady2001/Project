import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Serializer {
    private static void fileCreator(String fileName) {
        try {
            File file = new File(fileName);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void Serialize(String[] shapes) {
        fileCreator("shapes.txt");
        try {
            FileWriter writer = new FileWriter("shapes.txt");
            for (String shape : shapes) {
                writer.write(shape + "\n");
            }
            writer.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
