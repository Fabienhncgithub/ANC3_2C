package view;

import model.Fichier;

public class NomFichierCell extends FichierCell {

    @Override
    String texte(Fichier elem) {
        return elem.getNom();
    }
    
}
