package view;

import model.Fichier;

public class TypeFichierCell extends FichierCell<Fichier> {

    @Override
    String texte(Fichier elem) {
            return elem.isDirectory() ? "D" : "F";
    }
    
}


