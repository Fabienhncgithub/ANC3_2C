package model;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TreeItem;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class Model {

    //    private static final String INIT_DATA_L = "TestBC/RootBC_Left";
//    private static final String INIT_DATA_R = "TestBC/RootBC_Right";
    private static int seqNb = 1;
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

    public void foldersOnlySet(Fichier left, Fichier right) {
        foldersOnly(left);
        foldersOnly(right);
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

    public TreeItem<Fichier> getRootLeft(boolean onlyFolders) {
        if (onlyFolders){
            return getOnlyFolders(dirLeft);
    }else{


        return dirLeft;
    }

}
    public TreeItem<Fichier> getRootRight(boolean onlyFolders) {
        if(onlyFolders) {
            return getOnlyFolders(dirRight);
        }else {
            return dirRight;
        }
    }

//    private TreeItem<Fichier> getOnlyFolders(Fichier folder) {
//        TreeItem<Fichier> result = new TreeItem<>(folder);
//        System.out.println("dans getOnlyFolders");
//        result.setExpanded(true);
//        folder.getContent().stream().filter((f) -> (f.isDirectory())).forEachOrdered((f) -> {
//            result.getChildren().add(getOnlyFolders(f));
//            System.out.println("dans le lambda");
//        });
//        return result;
//    }

    public TreeItem<Fichier> getOnlyFolders(Fichier dir) {
        for (Fichier f : dir.getContenu()) {
            if (!f.isDirectory()) {
                f.setSelected(false);
            } else {
                foldersOnly(f);
            }
        }
        return dir;
    }

}
