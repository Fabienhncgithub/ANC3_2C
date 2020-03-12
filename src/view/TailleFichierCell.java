package view;

import model.Fichier;

public class TailleFichierCell extends FichierCell {

    @Override
    String texte(Fichier elem) {
        return ""+elem.taille();
    }
    
}
