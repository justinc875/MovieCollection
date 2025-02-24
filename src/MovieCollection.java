import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class MovieCollection {
    //instance variables
    private ArrayList<Movie> movies;

    //constructor
    public MovieCollection() {

    }

    public void importData() {
        /* ----- EXAMPLE 1 ----- */
        // IMPORT DATA FROM A FILE
        try {
            File file = new File("src\\movies_data.csv");
            if (!file.exists()) {
                return;
            }
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String data = fileScanner.nextLine();
                String[] splitData = data.split(",");
                if (splitData.length == 2) {
                    String name = splitData[0];
                    double price = Double.parseDouble(splitData[1]);
                    movies.add(new Movie(name, price));
                }
            }
            fileScanner.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


}
