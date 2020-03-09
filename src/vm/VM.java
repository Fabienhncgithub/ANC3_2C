package vm;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TreeItem;
import javafx.stage.DirectoryChooser;
import model.Fichier;
import model.Model;

public class VM {
    private final Model model;

    private final BooleanProperty newerLeftSelected = new SimpleBooleanProperty(false);
    private final BooleanProperty newerRightSelected = new SimpleBooleanProperty(false);
    private final BooleanProperty orphansSelected = new SimpleBooleanProperty(false);
    private final BooleanProperty sameSelected = new SimpleBooleanProperty(false);
    private final BooleanProperty foldersOnlySelected = new SimpleBooleanProperty(false);
//    private final BooleanProperty newerDisabled = new SimpleBooleanProperty(false);


    private final StringProperty labelPathLeft = new SimpleStringProperty("");
    private final StringProperty labelPathRight = new SimpleStringProperty("");
    private final DirectoryChooser choose = new DirectoryChooser();


    public VM(Model model) {
        this.model = model;
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
        return makeTreeRoot(model.getDirLeft());
    }

    public TreeItem getTiRight() {
        return makeTreeRoot(model.getDirRight());
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
        model.foldersOnlySet();
//        VM vmUD = new VM(model);
        labelPathLeft.setValue(model.getDirLeft().getPath().toString());
        labelPathRight.setValue(model.getDirRight().getPath().toString());
//        MyTreeTableView mTTV = new MyTreeTableView()
    }

    public void SameAction() {
        model.same();
        VM vmUD = new VM(model);
    }

    public void OrphanAction() {
        model.orphan();
        VM vmUD = new VM(model);
    }

    public void NewerRightAction() {
        model.newerRight();
        VM vmUD = new VM(model);
    }

    public void NewerLeftAction() {
        model.newerLeft();
        VM vmUD = new VM(model);
    }

//    public ObservableValue<? extends Boolean> newerDisabledProperty() {
//        return newerDisabled;
//    }
}
