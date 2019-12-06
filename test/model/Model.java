package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import miniproject.MiniProject;

/**
 *
 * @author f.hance
 */
public class Model {

    private final List<String> todoList = new ArrayList<>();
    private final List<String> doneList = new ArrayList<>();
    private static final int MIN_WORD_LENGTH = 3;

    public Model() {
    }

    public Model(List<String> list) {
        for (String elem : list) {
            if (!todoList.contains(elem) && elem.length() >= MIN_WORD_LENGTH) {
                todoList.add(elem);
            }
        }
    }

    public List<String> getToDoList() {
        return Collections.unmodifiableList(todoList);
    }

    public List<String> getDoneList() {
        return Collections.unmodifiableList(doneList);
    }

    public void setDone(int t) {
        this.doneList.add(this.doneList.get(t));
    }

    public boolean addTodo(String word) {
        if (word.length() >= MIN_WORD_LENGTH && (!todoList.contains(word)) && (!doneList.contains(word))) {
            return todoList.add(word);
        }
        return false;
    }


    public void setToDo(int index) {
        
    }
    
    public boolean validIndexToDo(int index){
    }
    
    public boolean validIndexDone(int index){
    }
    
    public static void main(String[] args) {
        
    }

}
