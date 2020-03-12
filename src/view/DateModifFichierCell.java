package view;

import model.Fichier;

import java.io.IOException;
import java.time.format.DateTimeFormatter;


public class DateModifFichierCell extends FichierCell {

    @Override
    String texte(Fichier elem) {
        String str = "";
        try {
            str += "" + elem.getModifDate(elem.getPath()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        } catch (IOException ex) {
            ex.getMessage();
        }
        return str;

    }

}
