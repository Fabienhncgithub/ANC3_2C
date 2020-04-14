package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TreeItem;

import java.nio.file.Paths;
import static vm.VM.makeTreeRoot;

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

    public TreeItem<Fichier> getRootLeft(boolean newSelected, boolean newRight, boolean orphansSelected, boolean sameSelected, boolean onlyFolders) {

        if (newSelected) {
            return newSelected(dirLeft);
        }

        if (newRight) {
            return oldSelected(dirLeft);
        }

        if (sameSelected) {
            return sameSelected(dirLeft);
        }

        if (orphansSelected) {
            return orphansSelected(dirLeft);
        }

        if (onlyFolders) {
            return getOnlyFolders(dirLeft);
        }

        return makeTreeRoot(dirLeft);
    }

    public TreeItem<Fichier> getRootRight(boolean newSelected, boolean newRight, boolean orphansSelected, boolean sameSelected, boolean onlyFolders) {
        if (newSelected) {
            return oldSelected(dirRight);
        }

        if (newRight) {
            return newSelected(dirRight);
        }

        if (sameSelected) {
            return sameSelected(dirRight);
        }

        if (orphansSelected) {
            return orphansSelected(dirRight);
        }
        if (onlyFolders) {
            return getOnlyFolders(dirRight);
        }                                                                       

        return makeTreeRoot(dirRight);
    }

    public TreeItem<Fichier> newSelected(Fichier dir) {
        if (dir.isDirectory()) {
            for (Fichier f : dir.getContent()) {
                if (!f.isDirectory()) {
                    if (f.getEtat() != Etat.NEWER) {
                        f.setSelected(false);
                    } else {
                        dir.setSelected(true);
                    }
                } else {
                    if (f.getEtat() != Etat.NEWER) {
                        f.setSelected(false);
                    } else {
                        dir.setSelected(true);
                    }
                    newSelected(f);
                }
                System.out.println(f.getName() + "   " + f.getEtat() + "   " + f.isSelected());
            }
        }
        return dir;
    }

    public TreeItem<Fichier> oldSelected(Fichier dir) {
        if (dir.isDirectory()) {
            for (Fichier f : dir.getContent()) {
                if (!f.isDirectory()) {
                    if (f.getEtat() != Etat.OLDER) {
                        f.setSelected(false);
                    } else {
                        dir.setSelected(true);
                    }
                } else {
                    if (f.getEtat() != Etat.OLDER) {
                        f.setSelected(false);
                    } else {
                        dir.setSelected(true);
                    }
                    oldSelected(f);
                }
            }
        }
        return dir;
    }

    public TreeItem<Fichier> getOnlyFolders(Fichier dir) {
        if (dir.isDirectory()) {
            for (Fichier f : dir.getContent()) {
                if (!f.isDirectory()) {
                    f.setSelected(false);
                } else {
                    getOnlyFolders(f);
                }
            }
        }
        return dir;
    }

    public TreeItem<Fichier> orphansSelected(Fichier dir) {
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
        return dir;
    }

    public TreeItem<Fichier> sameSelected(Fichier dir) {
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
        return dir;
    }
}
