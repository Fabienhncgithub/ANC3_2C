package model;

import model.InvalidTransferException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Model {

    private String word;
    private List<String> toDoList = new ArrayList<>();
    private List<String> doneList = new ArrayList<>();
    private static final int MIN_WORD_LENGTH = 3;


    public Model() {
    }

    public Model(List<String> list) {
        for (String elem : list) {
            if (!toDoList.contains(elem) && elem.length() >= MIN_WORD_LENGTH)
                toDoList.add(elem);
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
        if (word.length() >= MIN_WORD_LENGTH && !toDoList.contains(word) && !doneList.contains(word)) {
            return toDoList.add(word);
        }
        return false;
    }

    public List<String> getToDoList() {
        return Collections.unmodifiableList(toDoList);
    }

    public List<String> getDoneList() {
        return Collections.unmodifiableList(doneList);
    }

    public void setDone(int index) {
        if (validIndexDone(index) && !toDoList.isEmpty() && index < toDoList.size()) {
            doneList.add(toDoList.get(index));
            toDoList.remove(index);
        } else throw new InvalidTransferException();
    }

    public void setToDo(int index) {
        if (validIndexToDo(index) && !doneList.isEmpty() && index < doneList.size()) {
            toDoList.add(doneList.get(index));
            doneList.remove(index);
        } else throw new InvalidTransferException();
    }

    public boolean validIndexToDo(int index) {
        return index >= 0 && index <= this.toDoList.size();
    }

    public boolean validIndexDone(int index) {
        return index >= 0 && index <= this.doneList.size();
    }

    public static void main(String[] args) {
        Model m = new Model();
        System.out.println(m.getDoneList());
    }

}
