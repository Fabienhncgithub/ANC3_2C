package model;

import javafx.scene.control.cell.TextFieldTreeTableCell;

public abstract class FichierCell extends TextFieldTreeTableCell<Fichier, Fichier> {

    private static final String CSSPATH = "model/cssView.css";

    public FichierCell() {
        getStylesheets().add(CSSPATH);
    }

    @Override
    public void updateItem(Fichier elem, boolean isEmpty) {
        super.updateItem(elem, isEmpty);
        if (elem == null) {
            return;
        }
        this.setText(texte(elem));
        this.getStyleClass().set(0, elem.getEtat().name());

    }

    abstract String texte(Fichier elem);

}
