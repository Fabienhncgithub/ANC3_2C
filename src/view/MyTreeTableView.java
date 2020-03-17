package view;

import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.CopyBuilder;
import model.Fichier;
import vm.VM;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * @author momo
 */
public class MyTreeTableView extends VBox {

    private final Label label = new Label();
    Image imageButtonChoose = new Image("Images/flat_folder.png");
    HBox hbox = new HBox();
    private TreeTableView<Fichier> treeTableView = new TreeTableView<>();
    private Button buttonFolder = new Button();
    private DirectoryChooser dirChooser = new DirectoryChooser();
    private Stage primaryStage;

    {
        hbox.getChildren().add(label);
        hbox.getChildren().add(buttonFolder);
        hbox.setSpacing(200);
        hbox.setAlignment(Pos.CENTER);
        getChildren().addAll(hbox, treeTableView);
        buttonFolder.setGraphic(new ImageView(imageButtonChoose));
        buttonFolder.setMaxSize(50, 50);
        treeTableView.getStylesheets().add("model/cssView.css");

    }

    public MyTreeTableView(String labelText, TreeItem<Fichier> f, char side, VM vm) {
        treeTableView.setShowRoot(false);
        label.setText(labelText);
        treeTableView.setRoot(f);
        setPrefWidth(8000);
        addColumn();
        buttonFolder.setOnAction(event -> {
            File dir = dirChooser.showDialog(primaryStage);
            if (dir != null) {
                label.setText(dir.getAbsolutePath());
                switch (side) {
                    case ('R'):
                        try {
                            Fichier newFichierR = new CopyBuilder().build(Paths.get(dir.getAbsolutePath()));
                            vm.setNewDirRight(newFichierR);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        treeTableView.setRoot(vm.getTiRight());
                    case ('L'):
                        try {
                            Fichier newFichierL = new CopyBuilder().build(Paths.get(dir.getAbsolutePath()));
                            vm.setNewDirLeft(newFichierL);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        treeTableView.setRoot(vm.getTiLeft());
                }
            }
        });
    }

    private void addColumn() {
        TreeTableColumn<Fichier, Fichier> nameCol = new TreeTableColumn<>("Nom"),
                typeCol = new TreeTableColumn<>("Type"),
                dateCol = new TreeTableColumn<>("Date modif"),
                sizeCol = new TreeTableColumn<>("Taille");
        nameCol.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getValue()));
        typeCol.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getValue()));
        dateCol.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getValue()));
        sizeCol.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getValue()));
        nameCol.setPrefWidth(300);
        nameCol.setCellFactory((param) -> new NomFichierCell());
        typeCol.setCellFactory((param) -> new TypeFichierCell());
        dateCol.setCellFactory((param) -> new DateModifFichierCell());
        sizeCol.setCellFactory((param) -> new TailleFichierCell());
        treeTableView.getColumns().setAll(nameCol, typeCol, dateCol, sizeCol);
    }

    public TreeTableView<Fichier> getTreeTableView() {
        return treeTableView;
    }

    public void setRoot(TreeItem<Fichier> f) {
        treeTableView.setRoot(f);
    }
}
