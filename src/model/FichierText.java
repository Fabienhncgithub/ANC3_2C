package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.nio.file.Path;
import java.time.LocalDateTime;

public class FichierText extends FichierSimple {


    private final StringProperty textProperty = new SimpleStringProperty();

    public FichierText(String nom, long size, LocalDateTime date, Path path, String text) {
        super(nom, size, date, path);
        textProperty.setValue(text);
    }
    
    public FichierText(FichierText f) {
        this(f.getName(), f.getSize(), f.getDateTime(), f.getPath(), f.getText());
    }


    @Override
    public boolean isFichierText() {
        return true;
    }

    public String getText() {
        return textProperty.get();
    }

    public void setText(String s) {
        textProperty.setValue(s);
    }
}
