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

    public TreeItem<Fichier> getRoot(Fichier root, Set<Etat> setEtat, boolean foldersOnly) {
        updateTrees(setEtat);
        TreeItem<Fichier> res = new TreeItem<>(root);
        res.setExpanded(true);
        root.getContent().stream().filter(f -> getPredicatesFilters(setEtat).getPredicateLeft().test(f)).forEachOrdered((f) -> {
            res.getChildren().add(getRoot(f, setEtat, foldersOnly));
        });
        if (foldersOnly) {
            TreeItem<Fichier> folders = res;
            folders = getOnlyFolders(res.getValue());
            return folders;
        }
        return res;
    }

    public void updateTrees(Set<Etat> etats) {
        PredicateFilters predicateFilters = getPredicatesFilters(etats);
        dirLeft.toSelect(predicateFilters.getPredicateLeft());
        dirRight.toSelect(predicateFilters.getPredicateRight());
    }

    public PredicateFilters getPredicatesFilters(Set<Etat> setEtats) {
        Predicate<Fichier> pLeft = fichier -> false;
        Predicate<Fichier> pRight = fichier -> false;
        for (Etat etat : setEtats) {
            if (etat == Etat.ORPHAN) {
                pLeft = pLeft.or(fichier -> fichier.getEtat() == Etat.ORPHAN);
                pRight = pRight.or(fichier -> fichier.getEtat() == Etat.ORPHAN);
            }
            if (etat == Etat.SAME) {
                pLeft = pLeft.or(fichier -> fichier.getEtat() == Etat.SAME);
                pRight = pRight.or(fichier -> fichier.getEtat() == Etat.SAME);
            }
            if (etat == Etat.NEWER_LEFT) {
                pLeft = pLeft.or(fichier -> fichier.getEtat() == Etat.NEWER);
                pRight = pRight.or(fichier -> fichier.getEtat() == Etat.OLDER);
            }
            if (etat == Etat.NEWER_RIGHT) {
                pLeft = pLeft.or(fichier -> fichier.getEtat() == Etat.OLDER);
                pRight = pRight.or(fichier -> fichier.getEtat() == Etat.NEWER);
            }
        }
        if (setEtats.isEmpty()) {
            pLeft = pRight = fichier -> true;
        }
        return new PredicateFilters(pLeft, pRight);
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

        return dirLeft;
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
