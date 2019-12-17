package main;

import javafx.application.Application;
import javafx.stage.Stage;
import model.Model;
import view.View;
import viewModel.ViewModel;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Model model = new Model();
        ViewModel viewModel = new ViewModel(model);
        View view = new View(primaryStage, viewModel);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}