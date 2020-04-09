package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TreeItem;

import java.nio.file.Paths;

public class Model {

    private StringProperty pathDirLeft = new SimpleStringProperty("TestBC/RootBC_Left");
    private StringProperty pathDirRight = new SimpleStringProperty("TestBC/RootBC_Right");
    private Fichier dirLeft;
    private Fichier dirRight;

    public Model() {
        dirLeft = new CopyBuilder().build(Paths.get(pathDirLeft.getValue()));
        dirRight = new CopyBuilder().build(Paths.get(pathDirRight.getValue()));
        dirLeft.changeEtat(dirRight);
    }

    public Model(String fLeft, String fRight) {
        dirLeft = new CopyBuilder().build(Paths.get(fLeft));
        dirRight = new CopyBuilder().build(Paths.get(fRight));
        dirLeft.changeEtat(dirRight);
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
        if (dir.isDirectory()) {
            for (Fichier f : dir.getContent()) {
                if (!f.isDirectory()) {
                    f.setSelected(false);
                } else {
                    getOnlyFolders(f);
                }
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
        if (dir.isDirectory()) {
            for (Fichier f : dir.getContent()) {
                if (!f.isDirectory()) {
                    if (f.getEtat() != Etat.ORPHAN) {
                        f.setSelected(false);
                    }
                } else {
                    if (f.getEtat() != Etat.ORPHAN) {
                        f.setSelected(false);
                    } else {
                        dir.setSelected(true);
                    }
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
                if (!f.isDirectory()) {
                    if (f.getEtat() != Etat.SAME) {
                        f.setSelected(false);
                    }
                } else {
                    if (f.getEtat() != Etat.SAME) {
                        f.setSelected(false);
                    } else {
                        dir.setSelected(true);
                    }
                    sameSelected(f);
                }
            }
        }

    }

    private void unSelectedSame(Fichier dir) {
        if (dir.isDirectory()) {
            for (Fichier f : dir.getContenu()) {
                if (f.getEtat() != Etat.SAME) {
                    f.setSelected(true);
                }
                if (f.isDirectory()) {
                    unSelectedSame(f);
                }
            }
        }
    }

}
