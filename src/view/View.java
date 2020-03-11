package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Etat;
import vm.VM;

/**
 * @author momo
 */
public class View extends VBox {

    private MyButtonFilters myButtonFilters;
    private final MyTreeTableView left;
    private final MyTreeTableView right;
    private HBox hBoxCenter = new HBox();
    private HBox hBoxBot = hBoxBot();
    private HBox hBoxFilter = new HBox();
    private VBox vbox = new VBox();
    private VM vm;

    public View(Stage primaryStage, VM vm) {
        left = new MyTreeTableView(vm.getLabelPathLeft(), vm.getTiLeft());
        right = new MyTreeTableView(vm.getLabelPathRight(), vm.getTiRight());
        hBoxCenter.getChildren().addAll(left, right);
        myButtonFilters = new MyButtonFilters(vm, this);
        hBoxFilter.getChildren().addAll(myButtonFilters);
        hBoxFilter.setAlignment(Pos.CENTER);
        hBoxFilter.setSpacing(30);
        etatValues(hBoxBot);

        vbox.getChildren().addAll(hBoxFilter, hBoxCenter, hBoxBot);

        Scene scene = new Scene(vbox, 900, 500);
        scene.getStylesheets().add("model/cssView.css");
        primaryStage.setTitle("Beyond Compare");
        primaryStage.getIcons().add(new Image("Images/syncFilesIcon.png"));
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public MyTreeTableView getLeft() {
        return left;
    }

    public MyTreeTableView getRight() {
        return right;
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
