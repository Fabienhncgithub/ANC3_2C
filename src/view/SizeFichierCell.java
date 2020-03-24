package view;

import model.FichierSimple;

public class SizeFichierCell extends FichierCell<FichierSimple> {

    @Override
    String texte(FichierSimple elem) {
        return ""+elem.size();
    }
    
}
