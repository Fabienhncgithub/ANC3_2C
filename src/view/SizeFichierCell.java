package view;


public class SizeFichierCell extends FichierCell<Long> {

    @Override
    String texte(Long elem) {
        return "" +elem;
    }
    
}
