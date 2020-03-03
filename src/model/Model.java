package model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.nio.file.Paths;

public class Model {

    private Fichier dirLeft;
    private Fichier dirRight;

    public Model(String rootLeft, String rootRight) throws IOException {
        dirLeft = new CopyBuilder().build(Paths.get(rootLeft));
        dirRight = new CopyBuilder().build(Paths.get(rootRight));
        dirLeft.changeEtat(dirRight);
    }

    public Fichier getDirLeft() {
        return dirLeft;
    }

    public Fichier getDirRight() {
        return dirRight;
    }

    private void etatValues(HBox hBoxBot) {
        for (Etat e : Etat.values()) {
            if (e != Etat.UNDEFINED) {
                Label l = new Label(e.toString());
                l.getStyleClass().add(e.toString());
                hBoxBot.getChildren().add(l);
            }
        }
    }

    private HBox hBoxBot() {
        HBox hBoxBot = new HBox();
        hBoxBot.getStylesheets().add("model/cssView.css");
        hBoxBot.setAlignment(Pos.TOP_CENTER);
        hBoxBot.setSpacing(30);
        return hBoxBot;
    }
}
