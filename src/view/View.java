package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Etat;
import vm.VM;

/**
 * @author momo
 */
public class View extends VBox {

    private Button all = new Button("All");
    private ToggleButton newerLeft = new ToggleButton("Newer left");
    private ToggleButton newerRight = new ToggleButton("Newer right");
    private ToggleButton orphans = new ToggleButton("Orphans");
    private ToggleButton same = new ToggleButton("Same");
    private ToggleButton foldersOnly = new ToggleButton("Folders only");
    private ToggleGroup newerGroup = new ToggleGroup();
    private MyTreeTableView left;
    private MyTreeTableView right;
    private HBox hBoxCenter = new HBox();
    private HBox hBoxBot = hBoxBot();
    private HBox hBoxFilter = new HBox();
    private VBox vbox = new VBox();
    private VM vm;

    public View(Stage primaryStage, VM vm) {
        left = new MyTreeTableView(vm.getLabelPathLeft(), vm.getTiLeft());
        right = new MyTreeTableView(vm.getLabelPathRight(), vm.getTiRight());
        hBoxCenter.getChildren().addAll(left, right);
        newerLeft.setToggleGroup(newerGroup);
        newerRight.setToggleGroup(newerGroup);
        hBoxFilter.getChildren().addAll(all, newerLeft, newerRight, orphans, same, foldersOnly);
        hBoxFilter.setAlignment(Pos.CENTER);
        hBoxFilter.setSpacing(30);
        etatValues(hBoxBot);
        all.setOnMouseClicked(e -> {
            newerLeft.setSelected(false);
            newerRight.setSelected(false);
            orphans.setSelected(false);
            same.setSelected(false);
            foldersOnly.setSelected(false);
        });

        foldersOnly.selectedProperty().addListener(e -> {
            if (foldersOnly.isSelected()) {
                vm.foldersOnlyAction();
            } else {
                vm.allAction();
            }
            left.setRoot(vm.getTiLeft());
            right.setRoot(vm.getTiRight());
        });

        same.setOnAction(e -> {
            if (same.isSelected()) {
                vm.sameAction();
            }else {
                vm.allAction();
            }
            left.setRoot(vm.getTiLeft());
            right.setRoot(vm.getTiRight());
        });

        orphans.setOnAction(e -> {
            if (orphans.isSelected()) {
                vm.orphanAction();
            }else {
                vm.allAction();
            }
            left.setRoot(vm.getTiLeft());
            right.setRoot(vm.getTiRight());
        });

        newerRight.setOnAction(e -> {
            if (newerRight.isSelected()) {
                vm.newerRightAction();
            }else {
                vm.allAction();
            }
            left.setRoot(vm.getTiLeft());
            right.setRoot(vm.getTiRight());
        });

        newerLeft.setOnAction(e -> {
            if (newerLeft.isSelected()) {
                vm.newerLeftAction();
            }else {
                vm.allAction();
            }
            left.setRoot(vm.getTiLeft());
            right.setRoot(vm.getTiRight());
        });


        vbox.getChildren().addAll(hBoxFilter, hBoxCenter, hBoxBot);

        Scene scene = new Scene(vbox, 900, 500);
        scene.getStylesheets().add("model/cssView.css");
        primaryStage.setTitle("Beyond Compare");
        primaryStage.getIcons().add(new Image("Images/syncFilesIcon.png"));
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void etatValues(HBox hBoxBot) {  //TODO en public temporairement
        for (Etat e : Etat.values()) {
            if (e != Etat.UNDEFINED) {
                Label l = new Label(e.toString());
                l.getStyleClass().add(e.toString());
                hBoxBot.getChildren().add(l);
            }
        }
    }

    private HBox hBoxBot() {
        HBox hBoxBot = new HBox();
        hBoxBot.getStylesheets().add("model/cssView.css");
        hBoxBot.setAlignment(Pos.TOP_CENTER);
        hBoxBot.setSpacing(30);
        return hBoxBot;
    }

}
