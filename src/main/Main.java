package main;

import ctrl.Ctrl;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Model;
import view.View;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Model model = new Model();
        Ctrl ctrl = new Ctrl(model);
        View view = new View(primaryStage, ctrl);
        model.addObserver(view);
        model.notif(Model.TypeNotif.INIT);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}