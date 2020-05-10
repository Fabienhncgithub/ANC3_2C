package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TreeItem;

import java.nio.file.Paths;
import java.util.Set;
import java.util.function.Predicate;

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

    public TreeItem<Fichier> predicateEtat(Fichier root, Set<Etat> etat, boolean onlyFolders) {
        TreeItem<Fichier> res = new TreeItem<>(root);
        res.setExpanded(true);
        Predicate<Fichier> predicate = (Fichier f) -> etat.contains(f.getEtat());
        if (root.isDirectory()) {
            for (Fichier f : root.getContent()) {
                if (!f.isDirectory()) {
                    if ((!etat.isEmpty()) && !predicate.test(f)) {
                        f.setSelected(false);
                    } else {
                        f.setSelected(true);
                        f.getParent().getValue().setSelected(true);
                    }
                } else {
                    if ((!etat.isEmpty()) && !predicate.test(f)) {
                        f.setSelected(false);
                    } else {
                        f.setSelected(true);
                        f.getParent().getValue().setSelected(true);
                    }
                    predicateEtat(f, etat, onlyFolders);
                }
                if (f.isSelected()) {
                    f.getParent().getValue().setSelected(true);
                }
                if (onlyFolders) {
                    res = getOnlyFolders(root);
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

    public TreeItem<Fichier> getFileToMove(Fichier dir, Set<Etat> etat) {
        Dossier newDossier = new Dossier((Dossier) dir);
        Predicate<Fichier> predicate = (Fichier f) -> etat.contains(f.getEtat());
        for (Fichier f : newDossier.getContent()) {
            if (predicate.test(f)) {
                f.setSelected(true);
                f.getParent().getValue().setSelected(true);
            }
            if (f.isDirectory()) {
                getFileToMove(f, etat);
            }
        }
        return newDossier;
    }

    public void moveCopyToRight(Fichier copyLeft) {
        for (Fichier fLeft : copyLeft.getContent()) {
            if (fLeft.isDirectory()) {
                moveCopyToRight(fLeft);
            } else {
//                System.out.println("***************" + fLeft.getPath() + "**************************");
                for (Fichier fRight : dirRight.getValue().getContent()) {
//                    System.out.println("***************" + fRight.getPath() + "**************************");
                    replaceInRight(copyLeft, fRight);
                }
            }
        }
    }

    public void replaceInRight(TreeItem<Fichier> copyLeft, TreeItem<Fichier> dirInRight) {
        for (Fichier fRight : dirInRight.getValue().getContent()) {
            if (fRight.isDirectory()) {
                replaceInRight(copyLeft, fRight);
            } else {
                for (Fichier fLeft : copyLeft.getValue().getContent()) {
                    if (fLeft.getPath().subpath(2, fLeft.getPath().getNameCount()).equals(fRight.getPath().subpath(2, fRight.getPath().getNameCount()))) {
                        System.out.println(fRight);
                        fRight.getParent().getValue().getChildren().add(fLeft);
                        fRight.getParent().getValue().getChildren().remove(fRight);
                        System.out.println("après remove, add");
                    } else if (fLeft.getEtat() == Etat.ORPHAN) {

                    }
                }
            }
        }
    }

    public void addOrphan(TreeItem<Fichier> copyLeft, TreeItem<Fichier> dirInRight) {
        for (Fichier orphanInLeft : copyLeft.getValue().getContent()) {
            System.out.println(orphanInLeft.getPath());
            System.out.println(copyLeft.getValue().getPath());
            System.out.println(dirInRight.getValue().getPath());
            if (dirInRight.getValue().getChildren().contains(orphanInLeft)) {
                for (Fichier f : dirInRight.getValue().getContent()) {
                    if (f.getName().equals(orphanInLeft.getName())) {
                        addOrphan(orphanInLeft, f);
                    }
                }
            }else{
                dirInRight.getValue()._addFile(orphanInLeft);
            }
        }
    }
}




