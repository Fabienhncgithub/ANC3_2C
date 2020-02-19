package model;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.MyTreeTableView;

import java.nio.file.Paths;

public class Model extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        String rootRight = "TestBC/RootBC_Right";
        String rootLeft = "TestBC/RootBC_Left";
        Fichier dirLeft = new CopyBuilder().build(Paths.get(rootLeft));
        Fichier dirRight = new CopyBuilder().build(Paths.get(rootRight));
        MyTreeTableView treeView1 = new MyTreeTableView(dirLeft.getPath().toAbsolutePath().toString(), makeTreeRoot(dirLeft));
        MyTreeTableView treeView2 = new MyTreeTableView(dirRight.getPath().toAbsolutePath().toString(), makeTreeRoot(dirRight));
        dirLeft.changeEtat(dirRight);


        HBox hBoxFilter  = hBoxFilter();

        HBox hBoxCenter = hBoxCenter(treeView1, treeView2);

        HBox hBoxTop = hBoxTop();

        HBox hBoxBot = hBoxBot();

        VBox vbox = new VBox(hBoxFilter, hBoxTop, hBoxCenter, hBoxBot);



        etatValues(hBoxBot);

        Scene scene = new Scene(vbox, 900, 600);
        scene.getStylesheets().add("model/cssView.css");
        primaryStage.setTitle("Beyond Compare");
        primaryStage.getIcons().add(new Image("Images/syncFilesIcon.png"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private HBox hBoxBot() {
        HBox hBoxBot = new HBox();
        hBoxBot.getStylesheets().add("model/cssView.css");
        hBoxBot.setAlignment(Pos.TOP_CENTER);
        hBoxBot.setSpacing(30);
        return hBoxBot;
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

    private HBox hBoxFilter(){
        HBox hBoxFilter  = new HBox();
        ToggleButton all = new ToggleButton("All");
        all.setSelected(false);
        ToggleButton newerLeft = new ToggleButton("Newer Left");
        newerLeft.setSelected(false);
        ToggleButton newerRight = new ToggleButton("Newer Right");
        newerRight.setSelected(false);
        ToggleButton orphans = new ToggleButton("Orphans");
        orphans.setSelected(false);
        ToggleButton same = new ToggleButton("Same");
        same.setSelected(false);
        ToggleButton foldersOnly = new ToggleButton("Folders Only");
        foldersOnly.setSelected(false);
        hBoxFilter.getChildren().addAll(all, newerLeft, newerRight, orphans, same,foldersOnly);
        hBoxFilter.setAlignment(Pos.CENTER);
        hBoxFilter.setSpacing(30);
        return hBoxFilter;
    }


    private HBox hBoxTop() {
        HBox hBoxTop = new HBox();
        hBoxTop.setSpacing(60);
        hBoxTop.setAlignment(Pos.CENTER);
        return hBoxTop;
    }

    private HBox hBoxCenter(MyTreeTableView treeView1, MyTreeTableView treeView2) {
        HBox hBoxCenter = new HBox();
        hBoxCenter.getChildren().addAll(treeView1,treeView2);
        hBoxCenter.getStylesheets().add("model/cssView.css");
        hBoxCenter.setAlignment(Pos.CENTER);
        hBoxCenter.setMinSize(800, 500);
        return hBoxCenter;
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

}
