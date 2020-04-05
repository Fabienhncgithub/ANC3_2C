package vm;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;
import javafx.stage.DirectoryChooser;
import model.Fichier;
import model.FichierText;
import model.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VM {

    private final StringProperty selectedFileName = new SimpleStringProperty();
    private final ObjectProperty<Fichier> selectedFichier = new SimpleObjectProperty<>();
    private final EditVM editor;
    private final StringProperty labelPathLeft = new SimpleStringProperty("");
    private final StringProperty labelPathRight = new SimpleStringProperty("");
    private final ObjectProperty<TreeItem<Fichier>> selectedTree = new SimpleObjectProperty<>();
    private final DirectoryChooser choose = new DirectoryChooser();
    private final List<String> fileNames = new ArrayList<>();
    private Model model;
    private ObjectProperty<TreeItem<Fichier>> obsTreeItemLeft = new SimpleObjectProperty<>();
    private ObjectProperty<TreeItem<Fichier>> obsTreeItemRight = new SimpleObjectProperty<>();
    private final BooleanProperty foldersOnly = new SimpleBooleanProperty(false);
    private final BooleanProperty orphans = new SimpleBooleanProperty(false);

//    private final StringBinding textBinding = new StringBinding();



    public VM(Model model) {
        this.model = model;
        editor = new EditVM(this);
        setRoot();
        foldersOnly.addListener((obj, old, act) -> {
            if (foldersOnly.get()) {
                setRoot();
            } else {
                setRoot();
            }

        });
        orphans.addListener((obj, old, act) -> {
            if (orphans.get()) {
                setRoot();
            } else {
                setRoot();
            }
        });
    }

    public void setRoot() {
        obsTreeItemLeft.setValue(makeTreeRoot(model.getRootLeft(foldersOnly.getValue(), orphans.getValue()).getValue()));
        obsTreeItemRight.setValue(makeTreeRoot(model.getRootRight(foldersOnly.getValue(), orphans.getValue()).getValue()));
        try {
            model.getDirRight().changeEtat(model.getDirLeft());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
//
//    private class StringBinding extends ObjectBinding<String> {
//
//        @Override // La taille est la sommme des taille des enfants
//        protected String computeValue() {
//            return ;
//        }
//
//        void addBinding(Observable obs) {
//            super.bind(obs);
//        }
//    }

    public String getLabelPathLeft() {
        return labelPathLeft.get();
    }

    public String getLabelPathRight() {
        return labelPathRight.get();
    }

    public TreeItem<Fichier> getTiLeft() {
        return makeTreeRoot(model.getDirLeft());
    }
//
//    public void foldersOnlyAction(TreeTableView<Fichier> left, TreeTableView<Fichier> right) {
//        model.foldersOnlySet(left, right);
//    }

    public BooleanProperty foldersOnlyProperty() {
        return foldersOnly;
    }

    public BooleanProperty orphansProperty() {
        return orphans;
    }

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
//    public void unSelectedOrphanAction() {
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
//
//    public void setNewDirLeft(Fichier newDirLeft) {
//        model.setDirLeft(newDirLeft);
//    }
//
//    public void setNewDirRight(Fichier newDirRight) {
//        model.setDirRight(newDirRight);
//    }
//
//start of code to show file txt
    public TreeItem<Fichier> getTiRight() {
        return makeTreeRoot(model.getDirRight());
    }

    public ObjectProperty<Fichier> selectedFichierProperty() {
        return selectedFichier;
    }

    public void openSelectedFile() {
        editor.setText(loadFile(selectedFichier.get()));
        editor.setVisible(true);
    }

    private String loadFile(Fichier fichier) {
        if(fichier.isFichierText()){
            ((FichierText) fichier).getText();
        }
        return "";
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

    public StringProperty selectedFileNameProperty() {
        return selectedFileName;
    }

    public void setNewDirLeft(Fichier newDirLeft) {
        model.setDirLeft(newDirLeft);
    }

    public void setNewDirRight(Fichier newDirRight) {
        model.setDirRight(newDirRight);
    }

    public void fireAction() {
        model.modif(selectedTree.getValue().getValue());
        setRoot();
    }

    public void setSize() {
        model.modif(obsTreeItemLeft.getValue().getValue());
        model.modif(obsTreeItemRight.getValue().getValue());
    }


}
