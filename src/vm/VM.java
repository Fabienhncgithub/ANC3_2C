package vm;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;
import javafx.stage.DirectoryChooser;
import model.Fichier;
import model.Model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class VM {

    private Model model;
    private final StringProperty selectedFileName = new SimpleStringProperty();
    private final EditVM editor;

    private final StringProperty labelPathLeft = new SimpleStringProperty("");
    private final StringProperty labelPathRight = new SimpleStringProperty("");
    private ObjectProperty<TreeItem<Fichier>> obsTreeItemLeft = new SimpleObjectProperty<>();
    private ObjectProperty<TreeItem<Fichier>> obsTreeItemRight = new SimpleObjectProperty<>();

    private final ObjectProperty<TreeItem<Fichier>> selectedTree = new SimpleObjectProperty<>();

    private final DirectoryChooser choose = new DirectoryChooser();

    private final List<String> fileNames = new ArrayList<>();

    public VM(Model model) {
        this.model = model;
//        labelPathLeft.setValue(model.getDirLeft().getPath().toString());
//        labelPathRight.setValue(model.getDirRight().getPath().toString());
        editor = new EditVM(this);
        setRoot();
    }

    public String getLabelPathLeft() {
        return labelPathLeft.get();
    }

    public String getLabelPathRight() {
        return labelPathRight.get();
    }

    public TreeItem<Fichier> getTiLeft() {
        return makeTreeRoot(model.getDirLeft());
    }

    public TreeItem<Fichier> getTiRight() {
        return makeTreeRoot(model.getDirRight());
    }

//start of code to show file txt

    public StringProperty selectedFileNameProperty() {
        return selectedFileName;
    }

    public void openSelectedFile() {
        editor.setText(loadFile(selectedFileName.getValue()));
        editor.setVisible(true);
    }

    private String loadFile(String fileName) {
        Path path = Paths.get(fileName);
        try {
            return new String(Files.readAllBytes(path));
        } catch (IOException ex) {
            return "";
        }
    }

    public EditVM getEditVM() {
        return editor;
    }

// end of this part

    public static TreeItem<Fichier> makeTreeRoot(Fichier root) {
        TreeItem<Fichier> res = new TreeItem<>(root);
        res.setExpanded(true);
        if (root.isDirectory()) {
            root.getContenu().forEach(se -> {
                if (se.isSelected()) {
                    res.getChildren().add(makeTreeRoot(se));
                }
            });
        }
        return res;
    }

    public ObjectProperty<TreeItem<Fichier>> rootPropertyLeft() {
        return obsTreeItemLeft;
    }

    public ObservableValue<TreeItem<Fichier>> rootPropertyRight() {
        return obsTreeItemRight;
    }

    public ObjectProperty<TreeItem<Fichier>> selectedTreeProperty() {
        return selectedTree;
    }

//
//    public void foldersOnlyAction(MyTreeTableView left, MyTreeTableView right) {
//        model.foldersOnlySet(left, right);
//    }
//
//    public void unSelectedFoldersOnlyAction() {
//        model.unSelectedFoldersOnlySet();
//    }
//
//    public void sameAction() {
//        model.sameSet();
//    }
//
//    public void unSelectedSameAction() {
//        model.unSelectedSameSet();
//    }
//
//    public void orphanAction() {
//        model.orphanSet();
//    }
//
//     public void unSelectedOrphanAction() {
//        model.unSelectedOrphanSet();
//    }
//
//    public void newerRightAction() {
//        model.newerRightSet();
//    }
//
//    public void newerLeftAction() {
//        model.newerLeftSet();
//    }
//
//    public void allAction() {
//        model.showAll();
//    }

    public void setNewDirLeft(Fichier newDirLeft) {
        model.setDirLeft(newDirLeft);
    }

    public void setNewDirRight(Fichier newDirRight) {
        model.setDirRight(newDirRight);
    }

    private void setRoot() {
        obsTreeItemLeft.setValue(model.getRootLeft());
        obsTreeItemRight.setValue(model.getRootRight());
    }

    public void fireAction() {
            model.modif(selectedTree.getValue().getValue());
            setRoot();
    }
}
