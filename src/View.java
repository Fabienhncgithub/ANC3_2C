package view;

import ctrl.Ctrl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Model;

import java.util.Observable;
import java.util.Observer;

public class View extends VBox implements Observer {

//    private static final List<String> INIT_DATA = Arrays.asList( TODO un constructeur vide et un avec init_data
//            "Jouer à SuperMario",
//            "Traîner sur FaceBook",
//            "Revoir Pro2",
//            "Twitter",
//            "Travailler au projet Anc3",
//            "Regarder du foot",
//            "Ecouter de la musique"
//    );

    private final ListView<String> toDoList = new ListView<>();
    private final ListView<String> doneList = new ListView<>();
    private final Label toDoLabel = new Label("À faire");
    private final Label doneLabel = new Label("C'est fait");
    private final Label addLabel = new Label("À ajouter : ");
    private final Button setDone = new Button(">>");
    private final Button setToDo = new Button("<<");
    private final Button addButton = new Button(">>");
    private final TextField addText = new TextField();
    private final VBox lBox = new VBox();
    private final VBox cBox = new VBox();
    private final VBox rBox = new VBox();
    private final VBox addBox = new VBox();
    private final Ctrl ctrl;

    public View(Stage primaryStage, Ctrl ctrl) {
        this.ctrl = ctrl;
        configComponents();
        configListeners();
        Parent root = setRoot();
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Liste ToDo");
        primaryStage.setScene(scene);
    }

    private void configComponents() {
        lBox.getChildren().addAll(toDoLabel, toDoList);
        lBox.setAlignment(Pos.CENTER);
        lBox.setSpacing(5);
        lBox.setPrefWidth(250);
        cBox.getChildren().addAll(setDone, setToDo);
        cBox.setSpacing(20);
        cBox.setAlignment(Pos.CENTER);
        rBox.getChildren().addAll(doneLabel, doneList);
        rBox.setAlignment(Pos.CENTER);
        rBox.setSpacing(5);
        rBox.setPrefWidth(250);
        addBox.getChildren().addAll(addLabel, addText, addButton);
        addBox.setAlignment(Pos.CENTER);
        addBox.setSpacing(20);
        addBox.setPrefWidth(250);
        setDone.setMinWidth(50);
        setToDo.setMinWidth(50);
        addButton.setMinWidth(50);
        setToDo.setDisable(true);
        setDone.setDisable(true);
        addButton.setDisable(true);
    }

    private void configListeners() {
        addButton.setOnAction(e -> {
            ctrl.addToDo(addText.getText());
        });

        setToDo.setOnAction(e -> {
            int index = doneList.getSelectionModel().getSelectedIndex();
            ctrl.setToDo(index);
        });

        setDone.setOnAction(e -> {
            int index = toDoList.getSelectionModel().getSelectedIndex();
            ctrl.setDone(index);
        });

        toDoList.setOnMouseClicked(e -> {
            int index = toDoList.getSelectionModel().getSelectedIndex();
            if (e.getClickCount() == 2) {
                ctrl.setDone(index);
            }
        });

        doneList.setOnMouseClicked(e -> {
            int index = doneList.getSelectionModel().getSelectedIndex();
            if (e.getClickCount() == 2) {
                ctrl.setToDo(index);
            }
        });

        toDoList.getSelectionModel().selectedIndexProperty().addListener((obs, old, act) -> {
            setDone.setDisable((int) act == -1);
        });

        doneList.getSelectionModel().selectedIndexProperty().addListener((obs, old, act) -> {
            setToDo.setDisable((int) act == -1);
        });

        addText.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                ctrl.addToDo(addText.getText());
            }
        });

        addText.textProperty().addListener((obs, old, act) -> {
            addButton.setDisable(act.length() <= 2);
        });

        addText.focusedProperty().addListener((obs, old, act) -> {
            if (!act) {
                addText.requestFocus();
            }
        });

    }

    private Parent setRoot() {
        HBox root = new HBox();
        root.setPadding(new Insets(10));
        root.setSpacing(10);
        root.getChildren().addAll(addBox, lBox, cBox, rBox);
        return root;
    }

    @Override
    public void update(Observable o, Object arg) {
        Model model = (Model) o;
        Model.TypeNotif typeNotif = (Model.TypeNotif) arg;
        switch (typeNotif) {
            case INIT:
                toDoList.getItems().setAll(model.getToDoList());
                break;
            case ADD_TODO:
                toDoList.getItems().clear();
                toDoList.getItems().addAll(model.getToDoList());
                break;
            case UPDATE_LISTS:
                toDoList.getItems().clear();
                toDoList.getItems().addAll(model.getToDoList());
                doneList.getItems().clear();
                doneList.getItems().addAll(model.getDoneList());
                break;
        }
    }

}
