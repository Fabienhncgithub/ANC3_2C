package vm;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;
import model.Etat;
import model.Fichier;
import model.FichierText;
import model.Model;

import java.util.HashSet;
import java.util.Set;

public class VM {

    private final StringProperty selectedFileName = new SimpleStringProperty();
    private final ObjectProperty<TreeItem<Fichier>> selectedTreeLeft = new SimpleObjectProperty<>();
    private final ObjectProperty<TreeItem<Fichier>> selectedTreeRight = new SimpleObjectProperty<>();
    private final EditVM editor;
    private final BooleanProperty newLeft = new SimpleBooleanProperty(false);
    private final BooleanProperty newRight = new SimpleBooleanProperty(false);
    private final BooleanProperty orphans = new SimpleBooleanProperty(false);
    private final BooleanProperty same = new SimpleBooleanProperty(false);
    private final BooleanProperty foldersOnly = new SimpleBooleanProperty(false);
    private final Model model;
    private final ObjectProperty<TreeItem<Fichier>> obsTreeItemLeft = new SimpleObjectProperty<>();
    private final ObjectProperty<TreeItem<Fichier>> obsTreeItemRight = new SimpleObjectProperty<>();
    private final ObjectProperty<FichierText> selectedFileProperty = new SimpleObjectProperty<>();

    public VM(Model model) {
        this.model = model;
        editor = new EditVM(this);
        setRoot();
    }

    public Set<Etat> listeEtat(String side, Set<Etat> liste) {
        if (sameProperty().getValue()) {
            liste.add(Etat.SAME);
        }
        if (orphansProperty().getValue()) {
            liste.add(Etat.ORPHAN);
        }
        if (newLeftProperty().getValue()) {
            if (side.equals("L")) {
                liste.add(Etat.NEWER);
            }
            if (side.equals("R")) {
                liste.add(Etat.OLDER);
            }
        }

        if (newRightProperty().getValue()) {
            if (side.equals("L")) {
                liste.add(Etat.OLDER);
            }
            if (side.equals("R")) {
                liste.add(Etat.NEWER);
            }
        }
        return liste;
    }


    public static TreeItem<Fichier> makeTreeRoot(Fichier root) {
        TreeItem<Fichier> res = new TreeItem<>(root.getValue());
        res.setExpanded(true);
        root.getContent().stream().filter((f) -> (f.isSelected())).forEachOrdered((f) -> {
            res.getChildren().add(makeTreeRoot(f));
        });
        return res;
    }

    public void setRoot() {
        obsTreeItemLeft.setValue(makeTreeRoot(model.predicateEtat(model.getDirLeft(), listeEtat("L", new HashSet<>()), foldersOnly.getValue()).getValue()));
        obsTreeItemRight.setValue(makeTreeRoot(model.predicateEtat(model.getDirRight(), listeEtat("R", new HashSet<>()), foldersOnly.getValue()).getValue()));
        model.getDirRight().changeEtat(model.getDirLeft());
        model.getDirLeft().changeEtat(model.getDirRight());
    }

    public TreeItem<Fichier> getTiLeft() {
        return makeTreeRoot(model.getDirLeft());
    }

    public BooleanProperty newLeftProperty() {
        return newLeft;
    }

    public BooleanProperty newRightProperty() {
        return newRight;
    }

    public BooleanProperty orphansProperty() {
        return orphans;
    }

    public BooleanProperty sameProperty() {
        return same;
    }

    public BooleanProperty foldersOnlyProperty() {
        return foldersOnly;
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
