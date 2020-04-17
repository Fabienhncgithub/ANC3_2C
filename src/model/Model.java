package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TreeItem;

import java.nio.file.Paths;
import java.util.Set;
import java.util.function.Predicate;
import static vm.VM.makeTreeRoot;

public class Model {

    private final StringProperty pathDirLeft = new SimpleStringProperty("TestBC/RootBC_Left");
    private final StringProperty pathDirRight = new SimpleStringProperty("TestBC/RootBC_Right");
    private Fichier dirLeft;
    private Fichier dirRight;

    public Model() {
        dirLeft = CopyBuilder.build(Paths.get(pathDirLeft.getValue()));
        dirRight = CopyBuilder.build(Paths.get(pathDirRight.getValue()));
        dirLeft.changeEtat(dirRight);
    }

    public Model(String fLeft, String fRight) {
        dirLeft = CopyBuilder.build(Paths.get(fLeft));
        dirRight = CopyBuilder.build(Paths.get(fRight));
        dirLeft.changeEtat(dirRight);
    }

    public TreeItem<Fichier> predicateEtat(Fichier root, Set<Etat> etat) {
        TreeItem<Fichier> res = new TreeItem<>(root);
        res.setExpanded(true);

        Predicate<Fichier> predicate = (Fichier f) -> etat.contains(f.getEtat());

//        root.getContent().stream().filter((f) -> (predicate.test(f))).forEachOrdered((f) -> {
//            res.getChildren().add(predicateEtat(f, etat));
//            System.out.println(predicate.test(f));
//        });
        if (root.isDirectory()) {
            root.setSelected(true);
            for (Fichier f : root.getContent()) {
                if (!f.isDirectory()) {
                    if ((!etat.isEmpty()) && !predicate.test(f)) {
                        f.setSelected(false);
                        root.setSelected(false);
                    }
                } else {
                    if ((!etat.isEmpty()) && !predicate.test(f)) {
                        f.setSelected(false);
                    }
                    predicateEtat(f, etat);
                }
            }
        }
        return res;
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

        if (onlyFolders) {
            return getOnlyFolders(dirLeft);
        }

        return dirLeft;
    }

    public TreeItem<Fichier> getRootRight(boolean newSelected, boolean newRight, boolean orphansSelected, boolean sameSelected, boolean onlyFolders) {
        if (newSelected) {
            return oldSelected(dirRight);
        }

        if (newRight) {
            return newSelected(dirRight);
        }

        if (onlyFolders) {
            return getOnlyFolders(dirRight);
        }

        return dirRight;
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
        return makeTreeRoot(dir);
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

//        TreeItem<Fichier> res = new TreeItem<>(dir);
//        res.setExpanded(true);
//
//        dir.getContent().stream().filter((f) -> (f.isDirectory())).forEachOrdered((f) -> {
//            res.getChildren().add(getOnlyFolders(f));
//        });
//        System.out.println(res.getValue().getName() + "   " + res.getValue().isDirectory());
        return dir;
    }

}
