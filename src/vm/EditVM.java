package vm;

import javafx.beans.property.*;
import javafx.beans.value.ObservableIntegerValue;
import model.FichierText;

import java.time.LocalDateTime;

public class EditVM {

    private final StringProperty text = new SimpleStringProperty();
    private final BooleanProperty showing = new SimpleBooleanProperty(false);
    private final VM viewModel;

    EditVM(VM vm) {
        viewModel = vm;
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

    public StringProperty fileNameProperty() {
        return viewModel.selectedFileNameProperty();
    }

    public ReadOnlyBooleanProperty showingProperty() {
        return showing;
    }

    public void setVisible(boolean b) {
        showing.setValue(b);
    }

    public void saveTxt() {
        FichierText selectedFichierText = viewModel.selectedFileProperty().getValue();
        selectedFichierText.setDateTime((LocalDateTime.now()));
        selectedFichierText.setSize(textLengthProperty().get());
        selectedFichierText.setText(text.get());
        
    }

}
