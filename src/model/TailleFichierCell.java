package model;

public class TailleFichierCell extends FichierCell {

    @Override
    String texte(Fichier elem) {
        return ""+elem.taille();
    }
    
}
