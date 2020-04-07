package vm;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;
import model.Fichier;
import model.FichierText;
import model.Model;

import java.io.IOException;

public class VM {

    private final StringProperty selectedFileName = new SimpleStringProperty();
    private final ObjectProperty<TreeItem<Fichier>> selectedTreeLeft = new SimpleObjectProperty<>();
    private final ObjectProperty<TreeItem<Fichier>> selectedTreeRight = new SimpleObjectProperty<>();
    private final EditVM editor;
    private final BooleanProperty orphans = new SimpleBooleanProperty(false);
    private final BooleanProperty same = new SimpleBooleanProperty(false);
    private final BooleanProperty foldersOnly = new SimpleBooleanProperty(false);
    private Model model;
    private ObjectProperty<TreeItem<Fichier>> obsTreeItemLeft = new SimpleObjectProperty<>();
    private ObjectProperty<TreeItem<Fichier>> obsTreeItemRight = new SimpleObjectProperty<>();
    private ObjectProperty<FichierText> selectedFileProperty = new SimpleObjectProperty<>();

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

    public void setRoot() {
        obsTreeItemLeft.setValue(makeTreeRoot(model.getRootLeft(foldersOnly.getValue(), orphans.getValue(), same.getValue()).getValue()));
        obsTreeItemRight.setValue(makeTreeRoot(model.getRootRight(foldersOnly.getValue(), orphans.getValue(), same.getValue()).getValue()));
        try {
            model.getDirRight().changeEtat(model.getDirLeft());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TreeItem<Fichier> getTiLeft() {
        return makeTreeRoot(model.getDirLeft());
    }

    public BooleanProperty foldersOnlyProperty() {
        return foldersOnly;
    }

    public BooleanProperty orphansProperty() {
        return orphans;
    }

    public BooleanProperty sameProperty() {
        return same;
    }

    public TreeItem<Fichier> getTiRight() {
        return makeTreeRoot(model.getDirRight());
    }

    public StringProperty selectedFileNameProperty() {
        return selectedFileName;
    }

    public void openSelectedFileLeft() {
        if (selectedTreeLeft.getValue().getValue().isFichierText()) {
            FichierText fichierText = (FichierText) selectedTreeLeft.getValue().getValue();
            selectedFileName.setValue(fichierText.getName());
            selectedFileProperty.setValue(fichierText);
            editor.setText(fichierText.getText());
            editor.setVisible(true);
        }
    }

    public void openSelectedFileRight() {
        if (selectedTreeRight.getValue().getValue().isFichierText()) {
            FichierText fichierText = (FichierText) selectedTreeRight.getValue().getValue();
            selectedFileName.setValue(fichierText.getName());
            selectedFileProperty.setValue(fichierText);
            editor.setText(fichierText.getText());
            editor.setVisible(true);
        }
    }

    public EditVM getEditVM() {
        return editor;
    }

    public ObjectProperty<TreeItem<Fichier>> rootPropertyLeft() {
        return obsTreeItemLeft;
    }

    public ObservableValue<TreeItem<Fichier>> rootPropertyRight() {
        return obsTreeItemRight;
    }

    public void setNewDirLeft(Fichier newDirLeft) {
        model.setDirLeft(newDirLeft);
    }

    public void setNewDirRight(Fichier newDirRight) {
        model.setDirRight(newDirRight);
    }

    public ObjectProperty<TreeItem<Fichier>> selectedTreeLeft() {
        return selectedTreeLeft;
    }

    public ObjectProperty<TreeItem<Fichier>> selectedTreeRight() {
        return selectedTreeRight;
    }

    public ObjectProperty<FichierText> selectedFileProperty() {
        return selectedFileProperty;
    }

}
