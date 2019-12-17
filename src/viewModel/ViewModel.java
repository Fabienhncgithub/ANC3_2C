/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewModel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import model.Model;


public class ViewModel {

    private final IntegerProperty numLineSelectedToDo = new SimpleIntegerProperty(-1);
    private final IntegerProperty numLineSelectedDone = new SimpleIntegerProperty(-1);
    private final BooleanProperty btToDo = new SimpleBooleanProperty();
    private final BooleanProperty btDone = new SimpleBooleanProperty();
    private final BooleanProperty btAdd = new SimpleBooleanProperty();
    private final BooleanProperty focusAddLine = new SimpleBooleanProperty();

    private final Model model;

    public ViewModel(Model model) {
        this.model = model;
        btDone.bind(numLineSelectedToDo.isNotEqualTo(-1));
        btToDo.bind(numLineSelectedDone.isNotEqualTo(-1));
//        btAdd.bind(focusAddLine.isNotEqualTo());
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
