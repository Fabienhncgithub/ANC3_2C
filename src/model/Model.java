package model;

import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TreeItem;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class Model {

    private static final String INIT_DATA_L = "TestBC/RootBC_Left";
    private static final String INIT_DATA_R = "TestBC/RootBC_Right";
    private static int seqNb = 1;
    private StringProperty pathDirLeft;
    private StringProperty pathDirRight;
    private Fichier dirLeft;
    private Fichier dirRight;

    public Model(){
        try {
            dirLeft = new CopyBuilder().build(Paths.get(INIT_DATA_L));
            dirRight = new CopyBuilder().build(Paths.get(INIT_DATA_R));
            dirLeft.changeEtat(dirRight);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Model(String fLeft, String fRight) {
        try {
            dirLeft = new CopyBuilder().build(Paths.get(fLeft));
            dirRight = new CopyBuilder().build(Paths.get(fRight));
            dirLeft.changeEtat(dirRight);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TreeItem<Fichier> getRootLeft() {
        return dirLeft;
    }

    public TreeItem<Fichier> getRootRight() {
        return dirRight;
    }

    public Property<String> getPathDirLeft() {
        return pathDirLeft;
    }

    public Property<String> getPathDirRight() {
        return pathDirRight;
    }

    public Fichier getDirLeft() {
        return dirLeft;
    }

    public void setDirLeft(Fichier newDirLeft) {
        dirLeft = newDirLeft;
    }

    public Fichier getDirRight() {
        return dirRight;
    }

    public void setDirRight(Fichier newDirRight) {
        dirRight = newDirRight;
    }

    public void modif(Fichier file) {
        if (file.isDirectory()) {
            file.addFile(new FichierSimple("NouveauFichier" + seqNb++, 10));
        } else {
            file.setSize(file.size() + 10);
            file.setDateTime(LocalDateTime.now());
        }
    }
}
