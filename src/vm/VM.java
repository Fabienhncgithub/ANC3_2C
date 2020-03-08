package vm;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;
import javafx.stage.DirectoryChooser;
import model.Fichier;
import model.Model;

public class VM {
    private final Model model;

    private final BooleanProperty newerLeftSelected = new SimpleBooleanProperty(true);
    private final BooleanProperty newerRightSelected = new SimpleBooleanProperty(true);
    private final BooleanProperty orphansSelected = new SimpleBooleanProperty(true);
    private final BooleanProperty sameSelected = new SimpleBooleanProperty(true);
    private final BooleanProperty foldersOnlySelected = new SimpleBooleanProperty(true);
    private final BooleanProperty newerDisabled = new SimpleBooleanProperty(false);


    private final StringProperty labelPathLeft = new SimpleStringProperty("");
    private final StringProperty labelPathRight = new SimpleStringProperty("");
    private final DirectoryChooser choose = new DirectoryChooser();
    private TreeItem tiLeft;
    private TreeItem tiRight;

    public VM(Model model) {
        this.model = model;
        this.tiLeft = makeTreeRoot(model.getDirLeft());
        this.tiRight = makeTreeRoot(model.getDirRight());
        labelPathLeft.setValue(model.getDirLeft().getPath().toString());
        labelPathRight.setValue(model.getDirRight().getPath().toString());
    }

    public String getLabelPathLeft() {
        return labelPathLeft.get();
    }

    public StringProperty labelPathLeftProperty() {
        return labelPathLeft;
    }

    public String getLabelPathRight() {
        return labelPathRight.get();
    }

    public StringProperty labelPathRightProperty() {
        return labelPathRight;
    }

    public TreeItem getTiLeft() {
        return tiLeft;
    }

    public TreeItem getTiRight() {
        return tiRight;
    }
    
    public BooleanProperty getNewerLeftSelected() {
        return newerLeftSelected;
    }

    public BooleanProperty getNewerRightSelected() {
        return newerRightSelected;
    }

    public BooleanProperty getOrphansSelected() {
        return orphansSelected;
    }

    public BooleanProperty getSameSelected() {
        return sameSelected;
    }

    public BooleanProperty getFoldersOnlySelected() {
        return foldersOnlySelected;
    }

    public static TreeItem<Fichier> makeTreeRoot(Fichier root) {
        TreeItem<Fichier> res = new TreeItem<>(root);
        res.setExpanded(true);
        if (root.isDirectory()) {
            root.getContenu().forEach(se -> {
                if(se.isSelected())
                    res.getChildren().add(makeTreeRoot(se));
            });
        }
        return res;
    }

    public void foldersOnlyAction() {
        model.foldersOnly();
        VM vmUD = new VM(model);
    }

    public ObservableValue<? extends Boolean> newerDisabledProperty() {
        return newerDisabled;
    }
}
