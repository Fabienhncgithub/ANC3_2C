package main;

import javafx.application.Application;
import javafx.stage.Stage;
import model.Model;
import view.View;
import vm.VM;

public class Main extends Application {

    //    @Override
//    public void start(Stage primaryStage) throws Exception {
//        Model model = new Model();
//        VM vm = new VM(model);
//        View view = new View(primaryStage, vm);
//    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Model model = new Model();
        VM vm = new VM(model);
        View view = new View(primaryStage, vm);
    }
}
