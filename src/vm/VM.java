package vm;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TreeItem;
import javafx.stage.DirectoryChooser;
import model.Fichier;
import model.Model;

public class VM {

    private final Model model;

//    private final BooleanProperty newerLeftSelected = new SimpleBooleanProperty(false);
//    private final BooleanProperty newerRightSelected = new SimpleBooleanProperty(false);
//    private final BooleanProperty orphansSelected = new SimpleBooleanProperty(false);
//    private final BooleanProperty sameSelected = new SimpleBooleanProperty(false);
//    private final BooleanProperty foldersOnlySelected = new SimpleBooleanProperty(false);

    private final StringProperty labelPathLeft = new SimpleStringProperty("");
    private final StringProperty labelPathRight = new SimpleStringProperty("");
    private ObjectProperty<TreeItem<Fichier>> obsTreeItemLeft = new SimpleObjectProperty<>();
    private ObjectProperty<TreeItem<Fichier>> obsTreeItemRight = new SimpleObjectProperty<>();

//    private final ObservableList<TreeItem<Fichier>> obsTreeItem = new SimpleListProperty<>();

    private final DirectoryChooser choose = new DirectoryChooser();

    public VM(Model model) {
        this.model = model;
        labelPathLeft.setValue(model.getDirLeft().getPath().toString());
        labelPathRight.setValue(model.getDirRight().getPath().toString());
        obsTreeItemLeft.setValue(makeTreeRoot(model.getDirLeft()));
        obsTreeItemRight.setValue(makeTreeRoot(model.getDirRight()));

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

    public void foldersOnlyAction() {
        model.foldersOnlySet();
    }

    public void unSelectedFoldersOnlyAction() {
        model.unSelectedFoldersOnlySet();
    }

    public void sameAction() {
        model.sameSet();
    }

    public void unSelectedSameAction() {
        model.unSelectedSameSet();
    }

    public void orphanAction() {
        model.orphanSet();
    }
    
     public void unSelectedOrphanAction() {
        model.unSelectedOrphanSet();
    }

    public void newerRightAction() {
        model.newerRightSet();
    }

    public void newerLeftAction() {
        model.newerLeftSet();
    }

    public void allAction() {
        model.showAll();
    }
}
