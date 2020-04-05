package model;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TreeItem;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Model {

    //    private static final String INIT_DATA_L = "TestBC/RootBC_Left";
//    private static final String INIT_DATA_R = "TestBC/RootBC_Right";
    private static int seqNb = 1;
    private StringProperty pathDirLeft = new SimpleStringProperty("TestBC/RootBC_Left");
    private StringProperty pathDirRight = new SimpleStringProperty("TestBC/RootBC_Right");
    private Fichier dirLeft;
    private Fichier dirRight;
    private final List<String> fileNames = new ArrayList<>();


    public Model() {
        try {
            dirLeft = new CopyBuilder().build(Paths.get(pathDirLeft.getValue()));
            dirRight = new CopyBuilder().build(Paths.get(pathDirRight.getValue()));
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



    public void foldersOnly(Fichier dir) {
        for (Fichier f : dir.getContenu()) {
            if (!f.isDirectory()) {
                f.setSelected(false);
            } else {
                foldersOnly(f);
            }
        }
    }

    public TreeItem<Fichier> getRootLeft(boolean onlyFolders, boolean orphansSelected) {
        if (onlyFolders) {
            getOnlyFolders(dirLeft);
        } else {
            unSelectedOnlyFolders(dirLeft);
        }
        if (orphansSelected) {
            orphansSelected(dirLeft);
        } else {
            unSelectedOrphans(dirLeft);
        }
        return dirLeft;
    }

    public TreeItem<Fichier> getRootRight(boolean onlyFolders, boolean orphansSelected) {
        if (onlyFolders) {
            getOnlyFolders(dirRight);
        } else {
            unSelectedOnlyFolders(dirRight);
        }
        if (orphansSelected) {
            orphansSelected(dirRight);
        } else {
            unSelectedOrphans(dirRight);
        }
        return dirRight;
    }


    public void getOnlyFolders(Fichier dir) {
        for (Fichier f : dir.getContenu()) {
            if (!f.isDirectory()) {
                f.setSelected(false);
            } else {
                getOnlyFolders(f);
            }
        }
    }

    public void unSelectedOnlyFolders(Fichier dir) {
        for (Fichier f : dir.getContenu()) {
            if (!f.isDirectory()) {
                f.setSelected(true);
            } else {
                unSelectedOnlyFolders(f);
            }
        }
    }


    public void orphansSelected(Fichier dir) {
        if (dir.isDirectory())
            for (Fichier f : dir.getContenu()) {
                if (f.getEtat() != Etat.ORPHAN) {
                    f.selected = false;
                } else {
                    dir.selected = true;
                }
                if (f.isDirectory()) {
                    orphansSelected(f);
                }
            }

    }


    private void unSelectedOrphans(Fichier dir) {
        if (dir.isDirectory())
            for (Fichier f : dir.getContenu()) {
                if (f.getEtat() != Etat.ORPHAN) {
                    dir.selected = true;
                }
                if (f.isDirectory()) {
                    unSelectedOrphans(f);
                }
            }
    }
}
