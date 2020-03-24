package view;

import model.Fichier;

public class NomFichierCell extends FichierCell<Fichier> {

    @Override
    String texte(Fichier elem) {
        return elem.getNom();
    }
    
}
