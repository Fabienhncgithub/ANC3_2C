package view;

import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.*;

import java.io.File;

/**
 * @author momo
 */
public class MyTreeTableView extends VBox {

    private final Label label = new Label();
    private TreeTableView treeView = new TreeTableView();
    private Button buttonFolder = new Button("Choose");
    private DirectoryChooser dirChooser = new DirectoryChooser();
    private Stage primaryStage;

    {
        getChildren().addAll(label, buttonFolder, treeView);
        treeView.getStylesheets().add("model/cssView.css");
    }

    public MyTreeTableView(String labelText, TreeItem<Fichier> f) {
        this.label.setText(labelText);
        treeView.setRoot(f);
        setPrefWidth(8000);
        addColumn();
        buttonFolder.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                File dir = dirChooser.showDialog(primaryStage);
                if (dir != null) {
                    label.setText(dir.getAbsolutePath());
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
        treeView.getColumns().setAll(nameCol, typeCol, dateCol, sizeCol);
    }

}
