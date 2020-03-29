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

    private StringProperty pathDirLeft;
    private StringProperty pathDirRight;

    private Fichier dirLeft;
    private Fichier dirRight;

    private static int seqNb = 1;

    public Model(){
        try {
            dirLeft = new CopyBuilder().build(Paths.get(INIT_DATA_L));
            dirRight = new CopyBuilder().build(Paths.get(INIT_DATA_R));
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

    public Model(String fLeft, String fRight) {
        try {
            dirLeft = new CopyBuilder().build(Paths.get(fLeft));
            dirRight = new CopyBuilder().build(Paths.get(fRight));
            dirLeft.changeEtat(dirRight);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public Fichier getDirRight() {
        return dirRight;
    }
//
//    public void foldersOnlySet(MyTreeTableView left, MyTreeTableView right) {
//        foldersOnly(left.getTreeTableView().getRoot().getValue());
//        foldersOnly(right.getTreeTableView().getRoot().getValue());
//    }
//
//    public void foldersOnly(Fichier dir) {
//        for (Fichier f : dir.getContenu()) {
//            if (!f.isDirectory()) {
//                f.setSelected(false);
//            } else {
//                foldersOnly(f);
//            }
//        }
//    }
//
//    public void unSelectedFoldersOnlySet() {
//        unSelectedFoldersOnly(dirLeft);
//        unSelectedFoldersOnly(dirRight);
//    }
//
//    public void unSelectedFoldersOnly(Fichier dir) {
//        for (Fichier f : dir.getContenu()) {
//            if(!f.isDirectory()) {
//                f.setSelected(true);
//            } else {
//                unSelectedFoldersOnly(f);
//            }
//        }
//    }
//
//    public void sameSet() {
//        same(dirLeft);
//        same(dirRight);
//    }
//
//    public void same(Fichier dir) {
//        for (Fichier f : dir.getContenu()) {
//            if (f.getEtat() != Etat.SAME) {
//                f.setSelected(false);
//            }
//            if (f.isDirectory()) {
//                same(f);
//                for (Fichier fichier : f.getContenu()) {
//                    if (fichier.isSelected()) {
//                        f.setSelected(true);
//                    }
//                }
//            }
//        }
//    }
//
//    public void unSelectedSameSet() {
//        unSelectedSame(dirLeft);
//        unSelectedSame(dirRight);
//    }
//
//    public void unSelectedSame(Fichier dir) {
//        for (Fichier f : dir.getContenu()) {
//            if (f.getEtat() != Etat.SAME) {
//                f.setSelected(true);
//            }
//            if (f.isDirectory()) {
//                unSelectedSame(f);
//            }
//        }
//    }
//
//    public void orphanSet() {
//        orphan(dirLeft);
//        orphan(dirRight);
//    }
//
//    public void orphan(Fichier dir) {
//        for (Fichier f : dir.getContenu()) {
//            if (f.getEtat() != Etat.ORPHAN) {
//                f.selected = false;
//            } else {
//                dir.selected = true;
//            }
//            if (f.isDirectory()) {
//                orphan(f);
//            }
//        }
//    }
//
//    public void unSelectedOrphanSet() {
//        unSelectedOrphan(dirLeft);
//        unSelectedOrphan(dirRight);
//    }
//
//    public void unSelectedOrphan(Fichier dir) {
//         for (Fichier f : dir.getContenu()) {
//            if (f.getEtat() != Etat.ORPHAN) {
//                f.setSelected(true);
//            } if (f.isDirectory()) {
//                unSelectedOrphan(f);
//            }
//        }
//    }
//
//    public void newerRightSet() {
//        newer(dirRight);
//        older(dirLeft);
//    }
//
//    public void newerLeftSet() {
//        newer(dirLeft);
//        older(dirRight);
//    }
//
//    public void newer(Fichier dir) {
//        for (Fichier f : dir.getContenu()) {
//            if (f.getEtat() != Etat.NEWER) {
//                f.selected = false;
//            }
//            if (f.isDirectory()) {
//                newer(f);
//                for (Fichier fichier : f.getContenu()) {
//                    if (fichier.isSelected()) {
//                        f.selected = true;
//                    }
//                }
//            }
//        }
//    }
//
//    public void older(Fichier dir) {
//        for (Fichier f : dir.getContenu()) {
//            if (f.getEtat() != Etat.OLDER) {
//                f.selected = false;
//            }
//            if (f.isDirectory()) {
//                older(f);
//                for (Fichier fichier : f.getContenu()) {
//                    if (fichier.isSelected()) {
//                        f.selected = true;
//                    }
//                }
//            }
//        }
//    }
//
//    public void showAll() {
//        setAll(dirLeft);
//        setAll(dirRight);
//    }
//
//    private void setAll(Fichier dir) {
//        for (Fichier f : dir.getContenu()) {
//            f.selected = true;
//            if (f.isDirectory()) {
//                setAll(f);
//            }
//        }
//    }

    public void setDirRight(Fichier newDirRight) {
        dirRight = newDirRight;
    }

    public void setDirLeft(Fichier newDirLeft) {
        dirLeft = newDirLeft;
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
