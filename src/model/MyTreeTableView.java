package model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.VBox;

/**
 *
 * @author momo
 */
public class MyTreeTableView extends TreeTableView<Fichier> {

    private final Label label = new Label();
    private TreeTableView treeView = new TreeTableView();

    {
        getChildren().addAll(label, treeView);
    }

    public MyTreeTableView(String labelText, TreeItem<Fichier> f) {
        this.label.setText(labelText);
        treeView.setRoot(f);
        setPrefWidth(400);

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

        label.setStyle(" -fx-font-weight: bold;");
        label.setStyle(" -fx-font-weight: bold;");
        
        treeView.getStylesheets().add("model/cssView.css");
    }
}
