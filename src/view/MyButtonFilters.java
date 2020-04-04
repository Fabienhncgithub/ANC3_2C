package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import vm.VM;

public class MyButtonFilters extends VBox {

    HBox hbox = new HBox();
    private Button all = new Button("All");
    private ToggleButton newerLeft = new ToggleButton("Newer left");
    private ToggleButton newerRight = new ToggleButton("Newer right");
    private ToggleButton orphans = new ToggleButton("Orphans");
    private ToggleButton same = new ToggleButton("Same");
    private ToggleButton foldersOnly = new ToggleButton("Folders only");
    private ToggleGroup newerGroup = new ToggleGroup();
    private VM vm;
    private View view;

    {
        hbox.getChildren().addAll(all, newerLeft, newerRight, orphans, same, foldersOnly);
        hbox.setSpacing(30);
        hbox.setAlignment(Pos.CENTER);
        getChildren().addAll(hbox);
        hbox.getStylesheets().add("model/cssView.css");
    }

    public MyButtonFilters(){
    }

    public MyButtonFilters(VM vm, View view){

        newerLeft.setToggleGroup(newerGroup);
        newerRight.setToggleGroup(newerGroup);
        
        foldersOnly.selectedProperty().bindBidirectional(vm.foldersOnlyProperty());

        all.setOnMouseClicked(e -> {
            newerLeft.setSelected(false);
            newerRight.setSelected(false);
            orphans.setSelected(false);
            same.setSelected(false);
            foldersOnly.setSelected(false);
        });


//
//        foldersOnly.selectedProperty().addListener(e -> {
//            if (foldersOnly.isSelected()) {
//                vm.foldersOnlyAction(view.getLeft(), view.getRight());
//            } else {
//                vm.unSelectedFoldersOnlyAction();
//            }
//            view.getLeft().setRoot(vm.getTiLeft());
//            view.getRight().setRoot(vm.getTiRight());
//        });
//
//        same.selectedProperty().addListener(e -> {
//            if (same.isSelected()) {
//                vm.sameAction();
//            }else {
//               vm.unSelectedSameAction();
//            }
//            view.getLeft().setRoot(vm.getTiLeft());
//            view.getRight().setRoot(vm.getTiRight());
//        });
//
//        orphans.selectedProperty().addListener(e -> {
//            if (orphans.isSelected()) {
//                vm.orphanAction();
//            }else {
//                vm.unSelectedOrphanAction();
//            }
//            view.getLeft().setRoot(vm.getTiLeft());
//            view.getRight().setRoot(vm.getTiRight());
//        });
//
//        newerRight.selectedProperty().addListener(e -> {
//            if (newerRight.isSelected()) {
//                vm.allAction();
//                vm.newerRightAction();
//            }else {
//                vm.allAction();
//            }
//            view.getLeft().setRoot(vm.getTiLeft());
//            view.getRight().setRoot(vm.getTiRight());
//        });
//
//        newerLeft.selectedProperty().addListener(e -> {
//            if (newerLeft.isSelected()) {
//                vm.allAction();
//                vm.newerLeftAction();
//            }else {
//                vm.allAction();
//            }
//            view.getLeft().setRoot(vm.getTiLeft());
//            view.getRight().setRoot(vm.getTiRight());
//        });
    }

    public Button getAll() {
        return all;
    }

    public ToggleButton getNewerLeft() {
        return newerLeft;
    }

    public ToggleButton getNewerRight() {
        return newerRight;
    }

    public ToggleButton getOrphans() {
        return orphans;
    }

    public ToggleButton getSame() {
        return same;
    }

    public ToggleButton getFoldersOnly() {
        return foldersOnly;
    }
}
