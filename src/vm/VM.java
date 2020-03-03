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

    private final BooleanProperty newerLeftDisabled = new SimpleBooleanProperty(true);
    private final BooleanProperty newerRightDisabled = new SimpleBooleanProperty(true);
    private final BooleanProperty orphansDisabled = new SimpleBooleanProperty(true);
    private final BooleanProperty sameDisabled = new SimpleBooleanProperty(true);
    private final BooleanProperty foldersOnlyDisabled = new SimpleBooleanProperty(true);

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

    public TreeItem<Fichier> makeTreeRoot(Fichier root) {
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

}
