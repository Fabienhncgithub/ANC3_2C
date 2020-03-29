package view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateModifFichierCell extends FichierCell<LocalDateTime> {

    @Override
    String texte(LocalDateTime elem) {
        return elem.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

}
