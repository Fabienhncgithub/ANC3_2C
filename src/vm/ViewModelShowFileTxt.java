//package vm;
//
//import javafx.beans.property.SimpleStringProperty;
//import javafx.beans.property.StringProperty;
//import model.Dossier;
//import model.Model;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.List;
//
//public class ViewModelShowFileTxt {
//    private final Model model;
//    private final StringProperty selectedFileName = new SimpleStringProperty();
//    private final EditVM editor;
//
//    public ViewModelShowFileTxt(Model model) {
//        this.model = model;
//        editor = new EditVM(this);
//    }
//
//    public List<String> getFileNames() {
//        return Dossier.getFileNamesContenu();
//    }
//
//    public StringProperty selectedFileNameProperty() {
//        return selectedFileName;
//    }
//
//    public void openSelectedFile() {
//        editor.setText(loadFile(selectedFileName.getValue()));
//        editor.setVisible(true);
//    }
//
//    private String loadFile(String fileName) {
//        Path path = Paths.get(fileName);
//        try {
//            return new String(Files.readAllBytes(path));
//        } catch (IOException ex) {
//            return "";
//        }
//    }
//
//    public EditVM getEditVM() {
//        return editor;
//    }
//
//}