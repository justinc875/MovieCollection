import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;

public class MovieCollection {
    //instance variables
    private ArrayList<Movie> movies = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    //constructor
    public MovieCollection() {
        importData();
        menu();
    }

    public void importData() {
        /* ----- EXAMPLE 1 ----- */
        // IMPORT DATA FROM A FILE
        try {
            File myFile = new File("src\\movies_data.csv");
            Scanner fileScanner = new Scanner(myFile);
                fileScanner.nextLine();

            while (fileScanner.hasNext()) {
                String data = fileScanner.nextLine();
                String[] splitData = data.split(",");
                String title = splitData[0];
                String[] cast = splitData[1].split("\\|");
                String director = splitData[2];
                String overview = splitData[3];
                int runtime = Integer.parseInt(splitData[4]);
                double userRating = Double.parseDouble(splitData[5]);
                Movie s = new Movie(title, cast, director, overview, runtime, userRating);
                movies.add(s);
            }
            fileScanner.close();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }
    
    public void menu() {
        System.out.println("Welcome to the movie collection!");
        String menuOption = "";

        while (!menuOption.equals("q")) {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (c)ast");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (menuOption.equals("t")) {
                searchTitles();
            } else if (menuOption.equals("c")) {
                searchCast();
            } else if (menuOption.equals("q")) {
                System.out.println("Goodbye!");
            } else {
                System.out.println("Invalid choice!");
            }
        }
    }

    private void sortTitle(ArrayList<String> m) {
        for (int i = 0; i < m.size(); i++) {
            String temp = m.get(i);
            int index = i;
            while (index > 0 && temp.compareTo(m.get(index - 1)) < 0) {
                m.set(index, m.get(index - 1));
                index --;
            }
            m.set(index, temp);
        }


    }

    public void searchTitles() {
        ArrayList<String> movieList = new ArrayList<>();
        System.out.println("Enter a title search term: ");
        String term = scanner.nextLine().toLowerCase();
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getTitle().toLowerCase().contains(term)) {
                movieList.add(movies.get(i).getTitle());
            }
        }
    }

    public void searchCast() {
        System.out.println("cast");
    }
}
