package view;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import vm.EditVM;

public class EditView extends Stage{
    
    public EditView(Stage primaryStage, EditVM editVM) {
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        TextArea textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.textProperty().bindBidirectional(editVM.textProperty());
        StackPane stackPane = new StackPane(textArea);
        
        setOnHiding((e) -> editVM.setVisible(false));
        editVM.showingProperty().addListener((obj, old, act) -> {
            if(act) showAndWait();
        });
        
        Scene scene = new Scene(stackPane, 600, 400);        
        setScene(scene);
        titleProperty().bind(editVM.fileNameProperty().
                concat(" : ").
                concat(editVM.textLengthProperty()).
                concat(" octets")
        );
    }
    
}
