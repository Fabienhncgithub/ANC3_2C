/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctrl;

import model.Model;


public class Ctrl {
    private final Model model;


    public Ctrl(Model model) {
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
