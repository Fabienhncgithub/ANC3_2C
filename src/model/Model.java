package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;


public class Model {

    private static final int MIN_WORD_LENGTH = 3;
    private final ObservableList<String> toDoList = FXCollections.observableArrayList();
    private final ObservableList<String> doneList = FXCollections.observableArrayList();

    public Model() {
        initialize();
    }

    public Model(List<String> list) {
        for (String elem : list) {
            if (!toDoList.contains(elem) && elem.length() >= MIN_WORD_LENGTH) {
                toDoList.add(elem);
            }
        }
    }

    public ObservableList<String> getToDoList() {
        return FXCollections.unmodifiableObservableList(toDoList);
    }

    public ObservableList<String> getDoneList() {
        return FXCollections.unmodifiableObservableList(doneList);
    }

    public void initialize() {
        addToDo("Sit behind the table");
        addToDo("Turn on the pc");
        addToDo("Download the project");
        addToDo("Open the project");
        addToDo("Find bugs");
        addToDo("Fix bugs");
        addToDo("Check with prof");
        addToDo("Submit the Project");
    }

    public boolean addToDo(String word) {
        if (word.length() >= MIN_WORD_LENGTH && !toDoList.contains(word) && !doneList.contains(word)) {
            toDoList.add(word);
            return true;
        }
        return false;
    }

    public void setDone(int index) {
        if (validIndexDone(index)) {
            doneList.add(toDoList.get(index));
            toDoList.remove(index);
        } else {
            System.out.println("Invalid transfer Exception");
        }
    }

    public void setToDo(int index) {
        if (validIndexToDo(index)) {
            toDoList.add(doneList.get(index));
            doneList.remove(index);
        } else {
            System.out.println("Invalid transfer Exception");
        }
    }

    public boolean validIndexToDo(int index) {
        return index >= 0 && index <= this.doneList.size();
    }

    public boolean validIndexDone(int index) {
        return index >= 0 && index <= this.toDoList.size();
    }

}
