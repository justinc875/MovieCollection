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

        if(movieList.isEmpty()) {
            System.out.print("No movie titles match that search term!");
            return;
        }

        sortTitle(movieList);

        for (int i = 0; i < movieList.size(); i++) {
            System.out.println((i + 1) + ". " + movieList.get(i));
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.println("Enter number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < 1 || choice > movieList.size()) {
            System.out.println("Invalid selection!");
            return;
        }

        String selectedTitle = movieList.get(choice - 1);
        Movie selectedMovie = null;


        for (Movie m : movies) {
            if (m.getTitle().equals(selectedTitle)) {
                selectedMovie = m;
                break;
            }
        }

        if (selectedMovie != null) {
            System.out.println("Title: " + selectedMovie.getTitle());
            System.out.println("Runtime: " + selectedMovie.getRuntime() + " minutes");
            System.out.println("Director: " + selectedMovie.getDirector());
            System.out.println("Cast: " + Arrays.toString(selectedMovie.getCast())); //suggested from intellij
            System.out.println("Overview: " + selectedMovie.getOverview());
            System.out.println("User Rating: " + selectedMovie.getUserRating());
        } else {
            System.out.println("Movie details not found!");
        }



    }

    public void searchCast() {
        System.out.println("Enter a cast member search term: ");
        String term = scanner.nextLine().toLowerCase();
        ArrayList<String> matchingCast = new ArrayList<>();


        for (int i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);
            String[] castMembers = movie.getCast();

            for (int j = 0; j < castMembers.length; j++) {
                if (castMembers[j].toLowerCase().contains(term) && !matchingCast.contains(castMembers[j])) {
                        matchingCast.add(castMembers[j]);
                }
            }
        }

        if (matchingCast.isEmpty()) {
            System.out.println("No cast members match that search term!");
            return;
        }

        sortTitle(matchingCast);

        for (int i = 0; i < matchingCast.size(); i++) {
            System.out.println((i + 1) + ". " + matchingCast.get(i));
        }

        System.out.println("Which would you like to see all movies for? ");
        System.out.println("Enter Number: ");
        int castResponse = scanner.nextInt();
        scanner.nextLine();
        String selected = matchingCast.get(castResponse - 1);

        ArrayList<String> castMovies = new ArrayList<>();
        for (int i = 0; i < movies.size(); i++) {
            String[] castMembers = movies.get(i).getCast();
            boolean found = false;
            for (int j = 0; j < castMembers.length && !found; j++) {
                if (castMembers[j].equals(selected)) {
                    castMovies.add(movies.get(i).getTitle());
                    found = true;
                }
            }
        }

        if (castMovies.isEmpty()) {
            System.out.println("No movies found featuring " + selected);
            return;
        }

        sortTitle(castMovies);
        // Display the sorted movies in a numbered list
        System.out.println("Movies featuring " + selected + ":");
        for (int i = 0; i < castMovies.size(); i++) {
            System.out.println((i + 1) + ". " + castMovies.get(i));
        }

        System.out.println("Enter a number from the list to learn more about that movie:");
        int movieChoice = scanner.nextInt();
        scanner.nextLine();
        String selectedMovieTitle = castMovies.get(movieChoice - 1);

        //create new movie object to track which movie has been selected
        Movie selectedMovie = null;
        for(int i = 0; i < movies.size(); i ++) {
            if(movies.get(i).getTitle().equals(selectedMovieTitle)) {
                selectedMovie = movies.get(i);
            }
        }

        if (selectedMovie != null) {
            System.out.println("Title: " + selectedMovie.getTitle());
            System.out.println("Runtime: " + selectedMovie.getRuntime() + " minutes");
            System.out.println("Director: " + selectedMovie.getDirector());
            System.out.println("Cast: " + Arrays.toString(selectedMovie.getCast()));
            System.out.println("Overview: " + selectedMovie.getOverview());
            System.out.println("User Rating: " + selectedMovie.getUserRating());
        } else {
            System.out.println("Movie details not found!");
        }
    }

}