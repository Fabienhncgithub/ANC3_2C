package model;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TreeItem;

import java.io.IOException;
import java.nio.file.Paths;import static vm.VM.makeTreeRoot;
;


public class Model {

    private StringProperty pathDirLeft = new SimpleStringProperty("TestBC/RootBC_Left");
    private StringProperty pathDirRight = new SimpleStringProperty("TestBC/RootBC_Right");
    private Fichier dirLeft;
    private Fichier dirRight;

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

    public TreeItem<Fichier> getRootLeft(boolean onlyFolders, boolean orphansSelected, boolean sameSelected) {
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
        if (sameSelected) {
            sameSelected(dirLeft);
        } else {
            unSelectedSame(dirLeft);
        }
        return dirLeft;
    }

    public TreeItem<Fichier> getRootRight(boolean onlyFolders, boolean orphansSelected, boolean sameSelected) {
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
        if (sameSelected) {
            sameSelected(dirRight);
        } else {
            unSelectedSame(dirRight);
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
        if(dir.isDirectory()) {
            for(Fichier f : dir.getContent()) {
                if(f.getEtat() == Etat.ORPHAN) {
                    f.setSelected(true);
                } else {
                    f.setSelected(false);
                }
                
                if(f.isDirectory()) {
                    orphansSelected(f);
                }
            }
        }
        
    }

    private void unSelectedOrphans(Fichier dir) {
        if (dir.isDirectory()) {
            for (Fichier f : dir.getContenu()) {
                if (f.getEtat() != Etat.ORPHAN) {
                    f.setSelected(true);
                }
                if (f.isDirectory()) {
                    unSelectedOrphans(f);
                }
            }
        }
    }

    public void sameSelected(Fichier dir) {
        if (dir.isDirectory()) {
            for (Fichier f : dir.getContent()) {
                if (f.getEtat().equals(Etat.SAME)) {
                    f.setSelected(true);
                } else {
                    dir.setSelected(false);
                }
                if (f.isDirectory()) {
                    sameSelected(f);
                }
            }
        }

    }

    private void unSelectedSame(Fichier dir) {
        if (dir.isDirectory()) {
            for (Fichier f : dir.getContenu()) {
                if (f.getEtat() != Etat.SAME) {
                    dir.setSelected(true);
                }
                if (f.isDirectory()) {
                    unSelectedSame(f);
                }
            }
        }
    }

}
