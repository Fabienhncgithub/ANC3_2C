package view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Fichier;

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
//
//    public MyTreeTableView(String labelText, TreeItem<Fichier> f, char side, VM vm) {
//        treeTableView.setShowRoot(false);
//        label.setText(labelText);
//        treeTableView.setRoot(f);
//        setPrefWidth(8000);
//        addColumn();
//    }
//
//    private void addColumn() {
//        TreeTableColumn<Fichier, String> nameCol = new TreeTableColumn<>("Nom");
//        TreeTableColumn<Fichier, Fichier> typeCol = new TreeTableColumn<>("Type");
//        TreeTableColumn<Fichier, Long> sizeCol = new TreeTableColumn<>("Size");
//        TreeTableColumn<Fichier, LocalDateTime> dateCol = new TreeTableColumn<>("Date modif");
//
//        nameCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
//        typeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("type"));
//        dateCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("dateTime"));
//        sizeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("size"));
//
//        nameCol.setPrefWidth(300);
//
//        nameCol.setCellFactory((param) -> {
//            return new NomFichierCell();
//        });
//        typeCol.setCellFactory((param) -> {
//            return new TypeFichierCell();
//        });
//        dateCol.setCellFactory((param) -> {
//            return new DateModifFichierCell();
//        });
//        sizeCol.setCellFactory((param) -> {
//            return new SizeFichierCell();
//        });
//        treeTableView.getColumns().setAll(nameCol, typeCol, dateCol, sizeCol);
//    }
//
//    public TreeTableView<Fichier> getTreeTableView() {
//        return treeTableView;
//    }
//
//    public void setRoot(TreeItem<Fichier> f) {
//        treeTableView.setRoot(f);
//    }
//
//    public void setLabel(Path path) {
//        label.setText(path.toString());
//    }
}
