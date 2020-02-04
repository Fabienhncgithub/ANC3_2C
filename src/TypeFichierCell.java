public class TypeFichierCell {
    
public class TailleFichierCell extends FichierCell {

    @Override
    String texte(Fichier elem) {
        return ""+elem.type();
    }
    
}

}
