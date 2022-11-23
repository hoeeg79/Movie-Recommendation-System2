package easv.mrs.GUI.Controller;

import easv.mrs.BE.Movie;
import easv.mrs.GUI.Model.MovieModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class MovieViewController implements Initializable {

    @FXML
    private Button btnDelete;
    @FXML
    private Button btnUpdate;
    @FXML
    private TextField txtMovieSearch;
    @FXML
    private ListView<Movie> lstMovies;
    @FXML
    private TextField txtYear;
    @FXML
    private TextField txtTitle;

    private MovieModel movieModel;

    public MovieViewController()  {

        try {
            movieModel = new MovieModel();
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        lstMovies.setItems(movieModel.getObservableMovies());

        txtMovieSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                movieModel.searchMovie(newValue);
            } catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            }
        });

        lstMovies.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Movie>() {
            @Override
            public void changed(ObservableValue<? extends Movie> observable, Movie oldValue, Movie newValue) {
                if(newValue != null) {
                    btnUpdate.setDisable(false);
                    btnDelete.setDisable(false);
                    txtTitle.setText(newValue.getTitle());
                    txtYear.setText(String.valueOf(newValue.getYear()));
                } else{
                    btnUpdate.setDisable(true);
                    btnDelete.setDisable(true);
                }
            }
        });

    }

    private void displayError(Throwable t) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }

    @FXML
    private void handleAddMovie(ActionEvent actionEvent) {
        System.out.println("Add Movie button have been pressed");
        String title = txtTitle.getText();
        int year = Integer.parseInt(txtYear.getText());

        try {
            movieModel.createMovie(title, year);
        } catch (Exception e) {
            displayError(e);
        }
    }

    @FXML
    private void handleUpdate(ActionEvent actionEvent) {
        System.out.println("Update button have been pressed");

        try {
            //Take selected movie, and creates a variable to hold it.
            Movie updatedMovie = lstMovies.getSelectionModel().getSelectedItem();

            //Update the title and year in the variable.
            String newTitle = txtTitle.getText();
            int newYear = Integer.parseInt(txtYear.getText());

            updatedMovie.setTitle(newTitle);
            updatedMovie.setYear(newYear);

            //Calls the Model to update movie.
            movieModel.updateMovie(updatedMovie);

        } catch (Exception e) {
            displayError(e);
        }
    }

    @FXML
    private void handleDelete(ActionEvent actionEvent) {
        System.out.println("Delete button have been pressed");

        try {
            Movie deleteMovie = lstMovies.getSelectionModel().getSelectedItem();

            movieModel.deleteMovie(deleteMovie);
        } catch (Exception e) {
            displayError(e);
        }
    }
}
