package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableValue;

import java.nio.file.Path;
import java.nio.file.attribute.FileTime;

public class FichierText extends FichierSimple {

    //    private String text = new String();
    private StringProperty textProperty = new SimpleStringProperty();

        public FichierText(String nom, long size, FileTime fileTime, Path path, String text) {
            super(nom, size, fileTime, path);
            textProperty.setValue(text);
//            addToTextBinding(textProperty);
//            bindTextTo(textBinding);
            System.out.println(text);
//        setSize(textLengthProperty().longValue());
        }
//
//    private void addToTextBinding(ObservableObjectValue obs) {
//        textBinding.addBinding(obs);
//        textBinding.invalidate();
//    }

    @Override
    public boolean isFichierText() {
        return true;
    }

    public String getTextProperty() {
        return textProperty.get();
    }

    public StringProperty textPropertyProperty() {
        return textProperty;
    }

    final void bindTextTo(ObservableValue<String> value) {
        textProperty.bind(value);
    }

    public String getText() {
            return textProperty.getValue();
        }

    void setText(String s) {
            textProperty.setValue(s);
        }

        public StringProperty textProperty() {
            return textProperty;
        }

        public ObservableIntegerValue textLengthProperty() {
            return textProperty.length();
        }

    }
