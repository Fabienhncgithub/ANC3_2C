package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.CopyBuilder;
import model.Etat;
import model.Fichier;

import java.io.IOException;
import java.nio.file.Paths;

/**
 *
 * @author momo
 */
public class View extends VBox{

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
    private HBox hBoxCenter = new HBox();
    private HBox hBoxBot = new HBox();
    private VBox vbox = new VBox();

    public View(Stage primaryStage) throws IOException {
        rootRight = "TestBC/RootBC_Right";
        rootLeft = "TestBC/RootBC_Left";
        dirLeft = new CopyBuilder().build(Paths.get(rootLeft));
        dirRight = new CopyBuilder().build(Paths.get(rootRight));
        left = new MyTreeTableView(Paths.get(rootLeft).toAbsolutePath().toString(), makeTreeRoot(dirLeft));
        right = new MyTreeTableView(Paths.get(rootRight).toAbsolutePath().toString(), makeTreeRoot(dirRight));
        dirLeft.changeEtat(dirRight);
        etatValues(hBoxCenter);

        hBoxCenter.getChildren().addAll(left,right);
        hBoxBot = hBoxBot();

    vbox.getChildren().addAll(hBoxCenter,hBoxBot);

        Scene scene = new Scene(vbox, 900, 600);
        scene.getStylesheets().add("model/cssView.css");
        primaryStage.setTitle("Beyond Compare");
        primaryStage.getIcons().add(new Image("Images/syncFilesIcon.png"));
        primaryStage.setScene(scene);
        primaryStage.show();

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
