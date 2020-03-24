package view;

import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Fichier;
import vm.VM;

import java.nio.file.Path;
import java.time.LocalDateTime;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

/**
 * @author momo
 */
public class MyTreeTableView extends VBox {

    HBox hbox = new HBox();
    private TreeTableView<Fichier> treeTableView = new TreeTableView<>();
    private Label label = new Label();

    {
        hbox.getChildren().add(label);
        hbox.setSpacing(200);
        hbox.setAlignment(Pos.CENTER);
        getChildren().addAll(hbox, treeTableView);
        treeTableView.getStylesheets().add("model/cssView.css");

    }

    public MyTreeTableView(String labelText, TreeItem<Fichier> f, char side, VM vm) {
        treeTableView.setShowRoot(false);
        label.setText(labelText);
        treeTableView.setRoot(f);
        setPrefWidth(8000);
        addColumn();
    }

    private void addColumn() {
        TreeTableColumn<Fichier, Fichier> nameCol = new TreeTableColumn<>("Nom"),
                typeCol = new TreeTableColumn<>("Type"),
                sizeCol = new TreeTableColumn<>("Size");
        TreeTableColumn<Fichier, LocalDateTime> dateCol = new TreeTableColumn<>("date modif");
        nameCol.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getValue()));
        typeCol.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getValue()));
        dateCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("dateTime"));
        sizeCol.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getValue()));
        
        nameCol.setPrefWidth(300);
        
        nameCol.setCellFactory((param) -> new NomFichierCell());
        typeCol.setCellFactory((param) -> new TypeFichierCell());
        dateCol.setCellFactory((param) -> { return new DateModifFichierCell(); });
        sizeCol.setCellFactory((param) -> new SizeFichierCell());
        treeTableView.getColumns().setAll(nameCol, typeCol, dateCol, sizeCol);
    }

    public TreeTableView<Fichier> getTreeTableView() {
        return treeTableView;
    }

    public void setRoot(TreeItem<Fichier> f){
        treeTableView.setRoot(f);
    }

    public void setLabel(Path path) {
        label.setText(path.toString());
    }
}
