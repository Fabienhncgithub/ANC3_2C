/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewModel;

import javafx.beans.property.*;
import javafx.collections.ObservableList;
import model.Model;


public class ViewModel {

    private final IntegerProperty numLineSelectedToDo = new SimpleIntegerProperty(-1);
    private final IntegerProperty numLineSelectedDone = new SimpleIntegerProperty(-1);
    private final BooleanProperty btToDoEnabled = new SimpleBooleanProperty();
    private final BooleanProperty btDoneEnabled = new SimpleBooleanProperty();
    private final BooleanProperty btAddEnabled = new SimpleBooleanProperty();
    private final StringProperty textToAdd = new SimpleStringProperty();


    public boolean isBtDoneEnabled() {
        return btDoneEnabled.get();
    }

    public BooleanProperty btDoneEnabledProperty() {
        return btDoneEnabled;
    }

    private final Model model;

    public ViewModel(Model model) {
        this.model = model;
        btDoneEnabled.bind(numLineSelectedToDo.isNotEqualTo(-1));
        btToDoEnabled.bind(numLineSelectedDone.isNotEqualTo(-1));
        btAddEnabled.bind(textToAdd.length().greaterThan(2));
    }

    public IntegerProperty numLineSelectedToDoProperty() {
        return numLineSelectedToDo;
    }

    public IntegerProperty numLineSelectedDoneProperty() {
        return numLineSelectedToDo;
    }

    public ObservableList toDoListProperty() {
        return model.getToDoList();
    }

    public ObservableList doneListProperty() {
        return model.getDoneList();
    }

    public StringProperty textToAddProperty() {
        return textToAdd;
    }


    public void addToDo(String text) {
        model.addToDo(text);
        textToAdd.set("");
    }

    public void setToDo(int line) { model.setToDo(line);
    }

    public void setDone(int line) { model.setDone(line);
    }

}
