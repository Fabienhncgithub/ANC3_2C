package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import vm.VM;

public class MyButtonFilters extends VBox {

    private final HBox hbox = new HBox();
    private final Button all = new Button("All");
    private final ToggleGroup newerGroup = new ToggleGroup();
    private final ToggleButton newerLeft = new ToggleButton("Newer left");
    private final ToggleButton newerRight = new ToggleButton("Newer right");
    private final ToggleButton orphans = new ToggleButton("Orphans");
    private final ToggleButton same = new ToggleButton("Same");
    private final ToggleButton foldersOnly = new ToggleButton("Folders only");

    {
        hbox.getChildren().addAll(all, newerLeft, newerRight, orphans, same, foldersOnly);
        hbox.setSpacing(30);
        hbox.setAlignment(Pos.CENTER);
        getChildren().addAll(hbox);
        hbox.getStylesheets().add("model/cssView.css");
    }

    public MyButtonFilters(VM vm, View view) {

        newerLeft.setToggleGroup(newerGroup);
        newerRight.setToggleGroup(newerGroup);

        newerLeft.selectedProperty().bindBidirectional(vm.newLeftProperty());
        newerRight.selectedProperty().bindBidirectional(vm.newRightProperty());
        orphans.selectedProperty().bindBidirectional(vm.orphansProperty());
        same.selectedProperty().bindBidirectional(vm.sameProperty());
        foldersOnly.selectedProperty().bindBidirectional(vm.foldersOnlyProperty());

        configListeners(vm);
    }

    private void configListeners(VM vm) {
        all.setOnMouseClicked(e -> {
            newerLeft.setSelected(false);
            newerRight.setSelected(false);
            orphans.setSelected(false);
            same.setSelected(false);
            foldersOnly.setSelected(false);
            vm.setRoot();
        });

        newerLeft.setOnAction(e -> {
            vm.setRoot();
        });

        newerRight.setOnAction(e -> {
            vm.setRoot();
        });

        foldersOnly.setOnAction(e -> {
            vm.setRoot();
        });
        orphans.setOnAction(e -> {
            vm.setRoot();
        });
        same.setOnAction(e -> {
            vm.setRoot();
        });
    }

    public Button getAll() {
        return all;
    }

    public ToggleButton getSame() {
        return same;
    }
}
