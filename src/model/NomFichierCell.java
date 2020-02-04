package model;

public class NomFichierCell extends FichierCell {

    @Override
    String texte(Fichier elem) {
        return elem.getNom();
    }
    
}
