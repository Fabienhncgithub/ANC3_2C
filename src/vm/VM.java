package vm;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TreeItem;
import javafx.stage.DirectoryChooser;
import model.Fichier;
import model.Model;
import view.MyTreeTableView;

public class VM {

    private Model model;

    private final StringProperty labelPathLeft = new SimpleStringProperty("");
    private final StringProperty labelPathRight = new SimpleStringProperty("");
    private ObjectProperty<TreeItem<Fichier>> obsTreeItemLeft = new SimpleObjectProperty<>();
    private ObjectProperty<TreeItem<Fichier>> obsTreeItemRight = new SimpleObjectProperty<>();

    private final DirectoryChooser choose = new DirectoryChooser();

    public VM(Model model) {
        this.model = model;
//        labelPathLeft.addListener((obj, old, act) -> {
//            System.out.println("test left choose");
//            this.model = new Model(act, model.getDirRight().getPath().toString());
//            obsTreeItemLeft.bind(model.getDirLeft());
//        });
//        labelPathRight.addListener((obj, old, act) -> {
//            this.model = new Model(act, model.getDirLeft().getPath().toString());
//            obsTreeItemRight.setValue(makeTreeRoot(model.getDirRight()));
//        });
        labelPathLeft.setValue(model.getDirLeft().getPath().toString());
        labelPathRight.setValue(model.getDirRight().getPath().toString());
//        labelPathLeft.bind(model.getPathDirLeft());
//        labelPathRight.bind(model.getPathDirRight());
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

    public void foldersOnlyAction(MyTreeTableView left, MyTreeTableView right) {
        model.foldersOnlySet(left, right);
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

    public void setNewDirLeft(Fichier newDirLeft) {
        model.setDirLeft(newDirLeft);
    }

    public void setNewDirRight(Fichier newDirRight) {
        model.setDirRight(newDirRight);
    }

}
