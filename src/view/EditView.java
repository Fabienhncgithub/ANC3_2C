package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import vm.EditVM;

public class EditView extends Stage{


    
    public EditView(Stage primaryStage, EditVM editVM) {
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        TextArea textArea = new TextArea();
        Button saveButton = new Button("Save");
        textArea.setWrapText(true);
        textArea.textProperty().bindBidirectional(editVM.textProperty());
        StackPane stackPane = new StackPane(textArea);
        VBox vBox = new VBox(stackPane, saveButton);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(30);
        setOnHiding((e) -> editVM.setVisible(false));
        editVM.showingProperty().addListener((obj, old, act) -> {
            if(act) showAndWait();
        });
        
        Scene scene = new Scene(vBox, 600, 400);
        setScene(scene);
        titleProperty().bind(editVM.fileNameProperty().
                concat(" : ").
                concat(editVM.textLengthProperty()).
                concat(" octets")
        );

       saveButton.setOnMouseClicked(e -> {
               editVM.saveTxt();          
       });
    }






}
