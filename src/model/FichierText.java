package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableValue;

import java.nio.file.Path;
import java.nio.file.attribute.FileTime;

public class FichierText extends FichierSimple {


    private StringProperty textProperty = new SimpleStringProperty();

    public FichierText(String nom, long size, FileTime fileTime, Path path, String text) {
        super(nom, size, fileTime, path);
        textProperty.setValue(text);
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

    final void bindTextTo(ObservableValue<String> value) {
        textProperty.bind(value);
    }

    public StringProperty textProperty() {
        return textProperty;
    }

    public ObservableIntegerValue textLengthProperty() {
        return textProperty.length();
    }

}
