import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.nio.file.Paths;

public class Model extends Application {

    public static void main(String[] args) { launch(args);}

    @Override
    public void start(Stage primaryStage) throws Exception {
        String rootRight = "TestBC/RootBC_Right";
        String rootLeft = "TestBC/RootBC_Left";
        Fichier dirLeft = new CopyBuilder().build(Paths.get(rootLeft));
        Fichier dirRight = new CopyBuilder().build(Paths.get(rootRight));
        TreeTableView treeTableView = new TreeTableView(makeTreeRoot(dirLeft));
        TreeTableView treeTableView2 = new TreeTableView(makeTreeRoot(dirRight));
        TreeTableColumn<Fichier, Fichier>
                nameCol = new TreeTableColumn<>("Nom"),
                sizeCol = new TreeTableColumn<>("Taille");

        nameCol.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getValue()));
        sizeCol.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getValue()));

        nameCol.setPrefWidth(250);

        nameCol.setCellFactory((param) -> {
            return new NomFichierCell();
        });
        sizeCol.setCellFactory((param) -> {
            return new TailleFichierCell();
        });
        treeTableView.getColumns().setAll(nameCol, sizeCol);

        StackPane root = new StackPane();
        root.getChildren().add(treeTableView);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("ExempleTreeTableView");
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
//        try { //    subPath     0   1       2           3                   4           5       6
//
//            String rootRight = "TestBC/RootBC_Right";
//            String rootLeft = "TestBC/RootBC_Left";
//            Fichier dirLeft = new CopyBuilder().build(Paths.get(rootLeft));
//            Fichier dirRight = new CopyBuilder().build(Paths.get(rootRight));
//
//            dirLeft.changeEtat(dirRight);
//            System.out.println(dirLeft);
//            System.out.println("----------------------------------------------------------");
//            System.out.println(dirRight);
//        } catch (IOException e) {
//            e.getMessage();
//        }
//    }

}
