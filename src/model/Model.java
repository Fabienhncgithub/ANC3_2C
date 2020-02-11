package model;

import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.image.Image;
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
        TreeTableView treeTableView = new TreeTableView(makeTreeRoot(dirLeft));
        TreeTableView treeTableView2 = new TreeTableView(makeTreeRoot(dirRight));
        dirLeft.changeEtat(dirRight);

        TreeTableColumn<Fichier, Fichier>

                nameCol = new TreeTableColumn<>("Nom"),
                typeCol = new TreeTableColumn<>("Type"),
                dateCol = new TreeTableColumn<>("Date modif"),
                sizeCol = new TreeTableColumn<>("Taille");

        nameCol.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getValue()));
        typeCol.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getValue()));
        dateCol.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getValue()));
        sizeCol.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getValue()));

        nameCol.setPrefWidth(300);

        nameCol.setCellFactory((param) -> new NomFichierCell());
        typeCol.setCellFactory((param) -> new TypeFichierCell());
        dateCol.setCellFactory((param) -> new DateModifFichierCell());
        sizeCol.setCellFactory((param) -> new TailleFichierCell());


        treeTableView.getColumns().setAll(nameCol, typeCol, dateCol, sizeCol);

        TreeTableColumn<Fichier, Fichier>

                nameCol2 = new TreeTableColumn<>("Nom"),
                typeCol2 = new TreeTableColumn<>("Type"),
                dateCol2 = new TreeTableColumn<>("Date modif"),
                sizeCol2 = new TreeTableColumn<>("Taille");

        nameCol2.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getValue()));
        typeCol2.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getValue()));
        dateCol2.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getValue()));
        sizeCol2.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getValue()));

        nameCol2.setPrefWidth(300);


        nameCol2.setCellFactory((param) -> new NomFichierCell());
        typeCol2.setCellFactory((param) -> new TypeFichierCell());
        dateCol2.setCellFactory((param) -> new DateModifFichierCell());
        sizeCol2.setCellFactory((param) -> new TailleFichierCell());


        treeTableView.getColumns().setAll(nameCol, typeCol, dateCol, sizeCol);
        treeTableView2.getColumns().setAll(nameCol2, typeCol2, dateCol2, sizeCol2);

        treeTableView.setPrefWidth(400);
        treeTableView2.setPrefWidth(400);

        treeTableView.setPrefHeight(500);
        treeTableView2.setPrefHeight(500);

        HBox hBoxCenter = new HBox();
        hBoxCenter.getChildren().addAll(treeTableView, treeTableView2);
        hBoxCenter.setAlignment(Pos.CENTER);

        HBox hBoxTop = new HBox();
        Label label_right = new Label(dirLeft.getPath().toAbsolutePath().toString());
        Label label_left = new Label(dirRight.getPath().toAbsolutePath().toString());
        hBoxTop.setSpacing(60);
        label_right.setStyle(" -fx-font-weight: bold;");
        label_left.setStyle(" -fx-font-weight: bold;");
        hBoxTop.getChildren().addAll(label_right, label_left);
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

        Scene scene = new Scene(vbox, 800, 600);
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

}
