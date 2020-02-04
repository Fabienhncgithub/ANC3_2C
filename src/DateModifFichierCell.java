
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DateModifFichierCell {
    

public class TailleFichierCell extends FichierCell {

    @Override
    String texte(Fichier elem) {
        String str = "";
        try {
            str +=  ""+elem.getModifDate(elem.getPath());
        } catch (IOException ex) {
            Logger.getLogger(DateModifFichierCell.class.getName()).log(Level.SEVERE, null, ex);
        }
        return str;
    }
    
}

}
