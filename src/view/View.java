package view;

import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.CopyBuilder;
import model.Etat;
import model.Fichier;
import vm.VM;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDateTime;

/**
 * @author momo
 */
public class View extends VBox {

    private Stage primaryStage;
    private MyButtonFilters myButtonFilters;
    private final HBox hBoxCenter = new HBox();
    private final Image imageButtonChoose = new Image("Images/flat_folder.png");
    private final HBox hBoxBot = hBoxBot();
    private final HBox hBoxFilter = new HBox();
    private final HBox hBoxBonus = new HBox();
    private final VBox vbox = new VBox();
    private final VM vm;

    private final HBox hBoxButtonFolder = new HBox();
    private final Button buttonFolderL = new Button("L");
    private final Button buttonFolderR = new Button("R");
    private final DirectoryChooser dirChooser = new DirectoryChooser();

    private final TreeTableView<Fichier> tTVLeft = new TreeTableView<>();
    private final TreeTableView<Fichier> tTVRight = new TreeTableView<>();
    private final Label labelL = new Label();
    private final Label labelR = new Label();
    
    private final Button move = new Button("Move Left -> Right");

    public View(Stage primaryStage, VM vm) {
        this.vm = vm;
        EditView editView = new EditView(primaryStage, vm.getEditVM());
        myButtonFilters = new MyButtonFilters(vm, this);
        setBindingAndListeners(vm);
        configColumns(tTVLeft);
        configColumns(tTVRight);
        dirChooserButtons(buttonFolderR, vm);
        dirChooserButtons(buttonFolderL, vm);
        setupScene(primaryStage);
    }

    private void dirChooserButtons(Button button, VM vm) {
        button.setOnAction(event -> {
            File dir = dirChooser.showDialog(primaryStage);
            if (dir != null) {
                if (button.getText() == "R") {
                    labelR.setText(dir.getAbsolutePath());
                    Fichier newFichierR = new CopyBuilder().build(Paths.get(dir.getAbsolutePath()));
                    vm.setNewDirRight(newFichierR);
                    vm.setNewDirLeft(vm.getTiLeft().getValue());
                    vm.setRoot();
                } else {
                    labelL.setText(dir.getAbsolutePath());
                    Fichier newFichierL = new CopyBuilder().build(Paths.get(dir.getAbsolutePath()));
                    vm.setNewDirLeft(newFichierL);
                    vm.setNewDirRight(vm.getTiRight().getValue());
                    vm.setRoot();
                }
                vm.setRoot();
            }
        });
    }

    private void setBindingAndListeners(VM vm) {
        tTVLeft.rootProperty().bind(vm.rootPropertyLeft());
        tTVRight.rootProperty().bind(vm.rootPropertyRight());
        vm.selectedTreeLeft().bind(tTVLeft.getSelectionModel().selectedItemProperty());
        vm.selectedTreeRight().bind(tTVRight.getSelectionModel().selectedItemProperty());
        move.disableProperty().bind(vm.moveProperty());

        tTVLeft.setOnMousePressed(e -> {
            if (e.getClickCount() == 2 && !tTVLeft.getSelectionModel().isEmpty()) {
                vm.openSelectedFileLeft();
            }
        });
        tTVRight.setOnMousePressed(e -> {
            if (e.getClickCount() == 2 && !tTVRight.getSelectionModel().isEmpty()) {
                vm.openSelectedFileRight();
            }
        });

        move.setOnAction( e -> {
            vm.test();
        });

    }

    private void configColumns(TreeTableView<Fichier> tTV) {
        tTV.setShowRoot(false);
        setPrefWidth(8000);
        TreeTableColumn<Fichier, String> nameCol = new TreeTableColumn<>("Nom");
        TreeTableColumn<Fichier, Fichier> typeCol = new TreeTableColumn<>("Type");
        TreeTableColumn<Fichier, Long> sizeCol = new TreeTableColumn<>("Size");
        TreeTableColumn<Fichier, LocalDateTime> dateCol = new TreeTableColumn<>("Date modif");

        nameCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        typeCol.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getValue()));
        dateCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("dateTime"));
        sizeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("size"));

        nameCol.setPrefWidth(300);

        nameCol.setCellFactory((param) -> {
            return new NomFichierCell();
        });
        typeCol.setCellFactory((param) -> new TypeFichierCell());

        dateCol.setCellFactory((param) -> {
            return new DateModifFichierCell();
        });
        sizeCol.setCellFactory((param) -> {
            return new SizeFichierCell();
        });
        tTV.getColumns().setAll(nameCol, typeCol, dateCol, sizeCol);
    }

    private void setupScene(Stage primaryStage) {
        hBoxCenter.getChildren().addAll(tTVLeft, tTVRight);
        labelL.setText(tTVLeft.getRoot().getValue().getPath().toString());
        labelR.setText(tTVRight.getRoot().getValue().getPath().toString());
        buttonFolderL.setGraphic(new ImageView(imageButtonChoose));
        buttonFolderR.setGraphic(new ImageView(imageButtonChoose));
        hBoxButtonFolder.getChildren().addAll(labelL, buttonFolderL, buttonFolderR, labelR);
        hBoxButtonFolder.setAlignment(Pos.CENTER);
        hBoxButtonFolder.setSpacing(30);
        myButtonFilters = new MyButtonFilters(vm, this);
        hBoxFilter.getChildren().addAll(myButtonFilters);
        hBoxFilter.setAlignment(Pos.CENTER);
        hBoxFilter.setSpacing(100);
        hBoxFilter.setPadding(new Insets(15, 20, 10, 10));
        etatValues(hBoxBot);
        hBoxBonus();
        vbox.getChildren().addAll(hBoxFilter, hBoxButtonFolder, hBoxCenter, hBoxBot, hBoxBonus);
        Scene scene = new Scene(vbox, 1100, 500);
        scene.getStylesheets().add("model/cssView.css");
        primaryStage.setTitle("Beyond Compare");
        primaryStage.getIcons().add(new Image("Images/syncFilesIcon.png"));
        primaryStage.setScene(scene);
        primaryStage.show();
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
        HBox hBoxBotom = new HBox();
        hBoxBotom.getStylesheets().add("model/cssView.css");
        hBoxBotom.setAlignment(Pos.TOP_CENTER);
        hBoxBotom.setSpacing(30);
        return hBoxBotom;
    }
    
    private HBox hBoxBonus() {
        hBoxBonus.setAlignment(Pos.CENTER);
        hBoxBonus.getChildren().add(move);
        return hBoxBonus;
    }
}
