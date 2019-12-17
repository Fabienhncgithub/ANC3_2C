/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewModel;

import javafx.beans.property.*;
import model.Model;


public class ViewModel {

    private final IntegerProperty numLineSelectedToDo = new SimpleIntegerProperty(-1);
    private final IntegerProperty numLineSelectedDone = new SimpleIntegerProperty(-1);
    private final BooleanProperty btToDoEnabled = new SimpleBooleanProperty();

    public boolean isBtDoneEnabled() {
        return btDoneEnabled.get();
    }

    public BooleanProperty btDoneEnabledProperty() {
        return btDoneEnabled;
    }

    private final BooleanProperty btDoneEnabled = new SimpleBooleanProperty();
    private final BooleanProperty btAddEnabled = new SimpleBooleanProperty();
    private final StringProperty textToAdd = new SimpleStringProperty();

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


    public ViewModel(Model model) {
        this.model = model;
    }

    public void addToDo(String text) {
        model.addToDo(text);
    }

    public void setToDo(int line) { model.setToDo(line);
    }

    public void setDone(int line) { model.setDone(line);
    }

}
