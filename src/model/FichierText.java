package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.nio.file.Path;
import java.nio.file.attribute.FileTime;

public class FichierText extends FichierSimple {

    //    private String text = new String();
    private StringProperty textProperty = new SimpleStringProperty();
//    private final StringBinding textBinding = new StringBinding();
//
//        private class StringBinding extends ObjectBinding<String> {
//
//            @Override // La taille est la sommme des taille des enfants
//            protected String computeValue() {
//                return textProperty.get();
//            }
//
//            void addBinding(Observable obs) {
//                super.bind(obs);
//            }
//        }

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
//
//    final void bindTextTo(ObservableValue<String> value) {
//        textProperty.bind(value);
//    }
////
//    void setText(String s) {
//            text.setValue(s);
//        }
//
//        public StringProperty textProperty() {
//            return text;
//        }
//
//        public ObservableIntegerValue textLengthProperty() {
//            return text.length();
//        }

    }
