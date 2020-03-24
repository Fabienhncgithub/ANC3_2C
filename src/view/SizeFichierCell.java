package view;

import model.Fichier;

public class SizeFichierCell extends FichierCell<Fichier> {

    @Override
    String texte(Fichier elem) {
        return ""+elem.size();
    }
    
}
