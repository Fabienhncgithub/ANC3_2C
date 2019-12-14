package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import static java.util.stream.Collectors.toList;


public class Model extends Observable {

    public enum TypeNotif {
        INIT, ADD_TODO, UPDATE_LISTS
    }

    private static final int MIN_WORD_LENGTH = 3;
    private List<String> toDoList = new ArrayList<>();
    private List<String> doneList = new ArrayList<>();

    public Model() {
             initialize();
             notif(TypeNotif.INIT);
    }

    public List<String> getToDoList() {
        return Collections.unmodifiableList(toDoList);
    }

    public List<String> getDoneList() {
        return Collections.unmodifiableList(doneList);
    }

    public Model(List<String> list) {
        for (String elem : list) {
            if (!toDoList.contains(elem) && elem.length() >= MIN_WORD_LENGTH) {
                toDoList.add(elem);
            }
        }
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
        List<String> upperToDoList = toDoList.stream()
                .map(w -> w.toUpperCase())
                .collect(toList());
        List<String> upperDoneList = doneList.stream()
                .map(w -> w.toUpperCase())
                .collect(toList());
        if (word.length() >= MIN_WORD_LENGTH && !upperToDoList.contains(word.toUpperCase()) && !upperDoneList.contains(word.toUpperCase())) {
            toDoList.add(word);
            notif(TypeNotif.ADD_TODO);
            return true;
        }
        return false;
    }

    public void setDone(int index) {
        if (validIndexDone(index)) {
            doneList.add(toDoList.get(index));
            toDoList.remove(index);
            notif(TypeNotif.UPDATE_LISTS);
        } else {
            System.out.println("Invalid transfer Exception");
        }

    }

    public void setToDo(int index) {
        if (validIndexToDo(index)) {
            toDoList.add(doneList.get(index));
            doneList.remove(index);
            notif(TypeNotif.UPDATE_LISTS);

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

    public void notif(TypeNotif typeNotif) {
        setChanged();
        notifyObservers(typeNotif);
    }
 
}
