package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableIntegerValue;

import java.nio.file.Path;
import java.nio.file.attribute.FileTime;

public class FichierText extends FichierSimple {

    private final StringProperty text = new SimpleStringProperty();


    public FichierText(String nom, long size, FileTime fileTime, Path path) {
        super(nom, size, fileTime, path);
//        setSize(textLengthProperty().longValue());
    }

    void setText(String s) {
        text.setValue(s);
    }

    public StringProperty textProperty() {
        return text;
    }

    public ObservableIntegerValue textLengthProperty() {
        return text.length();
    }

}
