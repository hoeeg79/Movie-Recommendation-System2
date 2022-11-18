package easv.mrs.DAL;

import easv.mrs.BE.Movie;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.nio.file.StandardOpenOption.APPEND;

public class MovieDAO implements IMovieDataAccess {

    private static final String MOVIES_FILE = "data/movie_titles_test.txt";
    private static final Path MOVIES_PATH = Path.of(MOVIES_FILE);

    public List<Movie> getAllMovies() throws IOException {

        //Read all lines from file
        List<String> lines = Files.readAllLines(MOVIES_PATH);
        List<Movie> movies = new ArrayList<>();

        //Parse each line
        for (String line: lines){
            String[] separatedLine = line.split(",");

            //Map each line to Movie object
            int id = Integer.parseInt(separatedLine[0]);
            int year = Integer.parseInt(separatedLine[1]);
            String title = separatedLine[2];

            //add to movies list.
            movies.add(new Movie(id,year,title));
        }

        movies.sort(Comparator.comparingInt(Movie::getId));
        return movies;
    }

    @Override
    public Movie createMovie(String title, int year) throws Exception {
        String movieToFile = "\n" + (lines.size() + 1) + "," + year + "," + title;
        Files.writeString(MOVIES_PATH, movieToFile, APPEND);

        return new Movie(lines.size(), year, title);
    }

    @Override
    public void updateMovie(Movie movie) throws Exception {

    }

    @Override
    public void deleteMovie(Movie movie) throws Exception {

    }

    /**
     * Get the next ID
     * @return - the next available ID
     * @throws IOException
     */
    private int getNextID() throws IOException {
        List<Movie> movies = getAllMovies();

        Movie lastMovie = movies.get(movies.size() - 1);

        return lastMovie.getId() + 1;
    }





    /*
    public List<Movie> getAllMovies() {
        List<Movie> allMovieList = new ArrayList<>();

        File moviesFile = new File(MOVIES_FILE);


        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(moviesFile))) {
            boolean hasLines = true;
            while (hasLines) {
                String line = bufferedReader.readLine();
                hasLines = (line != null);
                if (hasLines && !line.isBlank())
                {
                    String[] separatedLine = line.split(",");

                    int id = Integer.parseInt(separatedLine[0]);
                    int year = Integer.parseInt(separatedLine[1]);
                    String title = separatedLine[2];
                    if(separatedLine.length > 3)
                    {
                        for(int i = 3; i < separatedLine.length; i++)
                        {
                            title += "," + separatedLine[i];
                        }
                    }
                    Movie movie = new Movie(id, title, year);
                    allMovieList.add(movie);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allMovieList;
    }
    */

}