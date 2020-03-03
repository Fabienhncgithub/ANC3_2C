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

    private static final String INIT_DATA_L = "TestBC/RootBC_Left"; // à mettre dans le model...
    private static final String INIT_DATA_R = "TestBC/RootBC_Right";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Model model = new Model(INIT_DATA_L, INIT_DATA_R);
        VM vm = new VM(model);
        View view = new View(primaryStage, vm);
    }
}
