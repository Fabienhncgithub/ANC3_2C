package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.CopyBuilder;
import model.Etat;
import model.Fichier;
import vm.VM;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * @author momo
 */
public class View extends VBox {

    private final MyTreeTableView left;
    private final MyTreeTableView right;
    private MyButtonFilters myButtonFilters;
    private HBox hBoxCenter = new HBox();
    Image imageButtonChoose = new Image("Images/flat_folder.png");
    private HBox hBoxBot = hBoxBot();
    private HBox hBoxFilter = new HBox();
    private VBox vbox = new VBox();
    private VM vm;
    private HBox hBoxButtonFolder = new HBox();
    private Button buttonFolderL = new Button();
    private Button buttonFolderR = new Button();
    private DirectoryChooser dirChooser = new DirectoryChooser();
    private Stage primaryStage;


    public View(Stage primaryStage, VM vm) {
        left = new MyTreeTableView(vm.getLabelPathLeft(), vm.getTiLeft(), 'L', vm);
        right = new MyTreeTableView(vm.getLabelPathRight(), vm.getTiRight(), 'R', vm);
        left.getTreeTableView().getRoot().parentProperty();
        right.getTreeTableView().getRoot().parentProperty();
        buttonFolderL.setOnAction(event -> {
            File dir = dirChooser.showDialog(primaryStage);
            if (dir != null) {
                Fichier newFichierL = null;
                try {
                    newFichierL = new CopyBuilder().build(Paths.get(dir.getAbsolutePath()));
                    left.setLabel(newFichierL.getPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                vm.setNewDirLeft(newFichierL);
                vm.setNewDirRight(right.getTreeTableView().getRoot().getValue());
                try {
                    vm.getTiRight().getValue().changeEtat(vm.getTiLeft().getValue());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                left.setRoot(vm.getTiLeft());
                right.setRoot(vm.getTiRight());
            }
        });
        buttonFolderR.setOnAction(event -> {
            File dir = dirChooser.showDialog(primaryStage);
            if (dir != null) {
                Fichier newFichierR = null;
                try {
                    newFichierR = new CopyBuilder().build(Paths.get(dir.getAbsolutePath()));
                    right.setLabel(newFichierR.getPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                vm.setNewDirRight(newFichierR);
                vm.setNewDirLeft(left.getTreeTableView().getRoot().getValue());
                try {
                    vm.getTiRight().getValue().changeEtat(vm.getTiLeft().getValue());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                left.setRoot(vm.getTiLeft());
                right.setRoot(vm.getTiRight());
            }
        });
        hBoxCenter.getChildren().addAll(left, right);
        buttonFolderL.setGraphic(new ImageView(imageButtonChoose));
        buttonFolderR.setGraphic(new ImageView(imageButtonChoose));


        hBoxButtonFolder.getChildren().addAll(buttonFolderL,buttonFolderR);
        hBoxButtonFolder.setAlignment(Pos.CENTER);
        hBoxButtonFolder.setSpacing(30);
        myButtonFilters = new MyButtonFilters(vm, this);
        hBoxFilter.getChildren().addAll(myButtonFilters);
        hBoxFilter.setAlignment(Pos.CENTER);
        hBoxFilter.setSpacing(100);
        hBoxFilter.setPadding(new Insets(15,20, 10,10));
        etatValues(hBoxBot);

        vbox.getChildren().addAll(hBoxFilter, hBoxButtonFolder, hBoxCenter, hBoxBot);



        Scene scene = new Scene(vbox, 900, 500);
        scene.getStylesheets().add("model/cssView.css");
        primaryStage.setTitle("Beyond Compare");
        primaryStage.getIcons().add(new Image("Images/syncFilesIcon.png"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public MyTreeTableView getLeft() {
        return left;
    }

    public MyTreeTableView getRight() {
        return right;
    }

    private void etatValues(HBox hBoxBot) {
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
