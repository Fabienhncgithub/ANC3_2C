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
                    } else  {
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
                
                
                if(f.isSelected()) {
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
}
