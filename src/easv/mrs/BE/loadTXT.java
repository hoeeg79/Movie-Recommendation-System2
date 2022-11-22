package easv.mrs.BE;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import easv.mrs.DAL.db.MovieDAO_DB;
import easv.mrs.DAL.db.MyDatabaseConnector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class loadTXT {
    private static final String MOVIES_FILE = "data/movie_titles.txt";
    private static final Path MOVIES_PATH = Path.of(MOVIES_FILE);
    private MovieDAO_DB MDB;

    public loadTXT() {
        MDB = new MovieDAO_DB();
    }

    public List<Movie> createListOfMovies() throws IOException {
        //Read all lines from file
        List<String> lines = Files.readAllLines(MOVIES_PATH);
        List<Movie> movies = new ArrayList<>();

        //Parse each line
        for (String line : lines) {
            String[] separatedLine = line.split(",");

            //Map each line to Movie object
            int id = Integer.parseInt(separatedLine[0]);
            int year = Integer.parseInt(separatedLine[1]);
            String title = separatedLine[2];

            //add to movies list.
            movies.add(new Movie(id, year, title));
        }

        movies.sort(Comparator.comparingInt(Movie::getId));
        return movies;
    }

    public void addMoviesToSQL() throws Exception {
        List<Movie> movies = createListOfMovies();

        for (Movie m : movies) {
            int year = m.getYear();
            String title = m.getTitle();

            MDB.createMovie(title,year);
        }
    }

    public static void main(String[] args) throws SQLException {

        loadTXT lt = new loadTXT();

        try {

            lt.addMoviesToSQL();

        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}