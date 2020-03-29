package view;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableRow;
import model.Fichier;

public abstract class FichierCell<T> extends TreeTableCell<Fichier, T> {

    private static final String CSSPATH = "model/cssView.css";

    public FichierCell() {
        getStylesheets().add(CSSPATH);
    }

    @Override
    public void updateItem(T elem, boolean isEmpty) {
        super.updateItem(elem, isEmpty);
        
//        if (isEmpty || elem == null) {
//            setText("null");
//            return;
//        }

        if(isEmpty) {
            setText("Vide");
            return ;
        } else if(elem == null) {
            setText("Null");
            return ;
        }

        setText(texte(elem));
        
        TreeTableRow<Fichier> currentRow = getTreeTableRow();
        TreeItem<Fichier> treeItem = currentRow.treeItemProperty().getValue();
        
        if(treeItem == null) {
            return;
        }

        Fichier f = treeItem.getValue();
        this.getStyleClass().set(0, f.getEtat().toString());
    }

    String texte(T elem) {
        return "" + elem;
    }

}
