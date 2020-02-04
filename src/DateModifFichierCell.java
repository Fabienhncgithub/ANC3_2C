import java.io.IOException;

public class DateModifFichierCell {
    

public class TailleFichierCell extends FichierCell {

    @Override
    String texte(Fichier elem) {
        String str = "";
        try {
            str +=  ""+elem.getModifDate(elem.getPath());
        } catch (IOException ex) {
            ex.getMessage();
        }
        return str;
    }
    
}

}
