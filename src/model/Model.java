package model;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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
 

        HBox hBoxCenter = new HBox();
        hBoxCenter.getChildren().addAll(treeView1,treeView2);
        hBoxCenter.setAlignment(Pos.CENTER);
        hBoxCenter.setMinSize(900, 800);

        HBox hBoxTop = new HBox();
        hBoxTop.setSpacing(60);
        hBoxTop.setAlignment(Pos.CENTER);

        HBox hBoxBot = new HBox();
        VBox vbox = new VBox(hBoxTop, hBoxCenter, hBoxBot);
        vbox.autosize();
        hBoxBot.getStylesheets().add("model/cssView.css");
        hBoxBot.setAlignment(Pos.CENTER);
        hBoxBot.setSpacing(30);
        for (Etat e : Etat.values()) {
            if (e != Etat.UNDEFINED) {
                Label l = new Label(e.toString());
                l.getStyleClass().add(e.toString());
                hBoxBot.getChildren().add(l);
            }
        }

        Scene scene = new Scene(vbox, 800, 900);
        primaryStage.setTitle("Beyond Compare");
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

}
