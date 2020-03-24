package view;

import model.Fichier;

public class SizeFichierCell extends FichierCell {

    @Override
    String texte(Fichier elem) {
        return ""+elem.size();
    }
    
}
