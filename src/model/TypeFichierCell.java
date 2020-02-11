package model;

public class TypeFichierCell extends FichierCell {

    @Override
    String texte(Fichier elem) {
        return elem.isDirectory() ? "D" : "F";
    }
    
}


