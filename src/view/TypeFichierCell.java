package view;

import model.Fichier;

public class TypeFichierCell extends FichierCell {

    @Override
    String texte(Fichier elem) {
        return elem.isDirectory() ? "D" : "F";
    }
    
}


