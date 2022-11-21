package easv.mrs.DAL.db;

import easv.mrs.BE.Movie;
import easv.mrs.DAL.IMovieDataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO_DB implements IMovieDataAccess {

    private MyDatabaseConnector databaseConnector;

    public MovieDAO_DB() {
        databaseConnector = new MyDatabaseConnector();
    }

    public List<Movie> getAllMovies() throws Exception {
        List<Movie> allMovies = new ArrayList<>();

        try (Connection connection = databaseConnector.getConnection();
             Statement statement = connection.createStatement();){
            String sql = "SELECT * FROM Movie;";

            if(statement.execute(sql)){
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()){
                    int id = resultSet.getInt("id");
                    int year = resultSet.getInt("year");
                    String title = resultSet.getString("title");

                    Movie movie = new Movie(id,year,title);
                    allMovies.add(movie);
                }
            }
        }
        return allMovies;
    }

    public Movie createMovie(String title, int year) throws Exception {
        String sql = "INSERT INTO Movie (Title,Year)VALUES (?,?);";
        int id = 0;
        try ( Connection conn = databaseConnector.getConnection();
              PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);){

            //Bind parameters
            stmt.setString(1,title);
            stmt.setInt(2,year);

            //Run the specified SQL statement
            stmt.executeUpdate();

            //Get generated Id from DB
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()){
                id = rs.getInt(1);
            }
        }

        //Generate new movie object to return
        Movie newMovie = new Movie(id,year,title);
        return newMovie;
    }

    public void updateMovie(Movie movie) throws Exception {
        //TODO Do this
        throw new UnsupportedOperationException();
    }

    public void deleteMovie(Movie movie) throws Exception {
        //TODO Do this
        throw new UnsupportedOperationException();
    }

    public List<Movie> searchMovies(String query) throws Exception {

        //TODO Do this
        throw new UnsupportedOperationException();
    }

}
