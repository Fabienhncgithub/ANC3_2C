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

    private final StringProperty selectedFileName = new SimpleStringProperty();
    private final EditVM editor;
    private final StringProperty labelPathLeft = new SimpleStringProperty("");
    private final StringProperty labelPathRight = new SimpleStringProperty("");
    private final ObjectProperty<TreeItem<Fichier>> selectedTree = new SimpleObjectProperty<>();
    private final DirectoryChooser choose = new DirectoryChooser();
    private final List<String> fileNames = new ArrayList<>();
    private Model model;
    private ObjectProperty<TreeItem<Fichier>> obsTreeItemLeft = new SimpleObjectProperty<>();
    private ObjectProperty<TreeItem<Fichier>> obsTreeItemRight = new SimpleObjectProperty<>();

    public VM(Model model) {
        this.model = model;

        editor = new EditVM(this);
        setRoot();
    }

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

    public String getLabelPathLeft() {
        return labelPathLeft.get();
    }

    public String getLabelPathRight() {
        return labelPathRight.get();
    }

    public TreeItem<Fichier> getTiLeft() {
        return makeTreeRoot(model.getDirLeft());
    }

//start of code to show file txt
    public TreeItem<Fichier> getTiRight() {
        return makeTreeRoot(model.getDirRight());
    }

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

// end of this part
    public EditVM getEditVM() {
        return editor;
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

    public void setNewDirLeft(Fichier newDirLeft) {
        model.setDirLeft(newDirLeft);
    }

    public void setNewDirRight(Fichier newDirRight) {
        model.setDirRight(newDirRight);
    }

    private void setRoot() {
        obsTreeItemLeft.setValue(makeTreeRoot(model.getRootLeft().getValue()));
        obsTreeItemRight.setValue(makeTreeRoot(model.getRootRight().getValue()));
    }

    public void fireAction() {
        model.modif(selectedTree.getValue().getValue());
        setRoot();
    }
}
