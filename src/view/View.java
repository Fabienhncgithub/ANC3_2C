package view;

import java.io.IOException;
import java.nio.file.Paths;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;
import model.CopyBuilder;
import model.Etat;
import model.Fichier;
import model.MyTreeTableView;

/**
 *
 * @author momo
 */
public class View extends HBox{

    private ToggleButton all = new ToggleButton("All");
    private ToggleButton newerLeft = new ToggleButton("Newer left");
    private ToggleButton newerRight = new ToggleButton("Newer right");
    private ToggleButton orphans = new ToggleButton("Orphans");
    private ToggleButton same = new ToggleButton("Same");
    private ToggleButton foldersOnly = new ToggleButton("Folers only");
    private String rootLeft;
    private String rootRight;
    private MyTreeTableView left;
    private MyTreeTableView right;
    private Fichier dirLeft;
    private Fichier dirRight;
    private HBox hBoxBot;

    public View() throws IOException {
        rootRight = "TestBC/RootBC_Right";
        rootLeft = "TestBC/RootBC_Left";
        dirLeft = new CopyBuilder().build(Paths.get(rootLeft));
        dirRight = new CopyBuilder().build(Paths.get(rootRight));
        left = new MyTreeTableView(Paths.get(rootLeft).toAbsolutePath().toString(), makeTreeRoot(dirLeft));
        right = new MyTreeTableView(Paths.get(rootRight).toAbsolutePath().toString(), makeTreeRoot(dirRight));
        etatValues(hBoxBot);
    }

    public TreeItem<Fichier> makeTreeRoot(Fichier root) {
        TreeItem<Fichier> res = new TreeItem<>(root);
        res.setExpanded(true);
        if (root.isDirectory()) {
            root.getContenu().forEach(se -> {
                res.getChildren().add(makeTreeRoot(se));
            });
        }

        return res;
    }
    
    private void etatValues(HBox hBoxBot) {
        for (Etat e : Etat.values()) {
            if (e != Etat.UNDEFINED) {
                Label l = new Label(e.toString());
                l.getStyleClass().add(e.toString());
                hBoxBot.getChildren().add(l);
            }
        }
    }
    
    private HBox hBoxBot() {
        HBox hBoxBot = new HBox();
        hBoxBot.getStylesheets().add("model/cssView.css");
        hBoxBot.setAlignment(Pos.TOP_CENTER);
        hBoxBot.setSpacing(30);
        return hBoxBot;
    }

}
