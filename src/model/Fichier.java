package model;

import javafx.scene.control.TreeTableView;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

public abstract class Fichier extends TreeTableView<Fichier> {

    private final StringProperty name;
    private ObjectProperty<LocalDateTime> dateTime;
    private Path path;
    private Etat etat = Etat.UNDEFINED;
    boolean selected = true;

    public Fichier(String nom, Path path) {
        this.name = new SimpleStringProperty(nom);
        this.dateTime = new SimpleObjectProperty<>(LocalDateTime.now());
        this.path = path;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
    

    public abstract Iterable<Fichier> getContenu();

    public void setDateTime(LocalDateTime modifDate) {
        this.dateTime.setValue(modifDate);
    }
    
    public LocalDateTime getDateTime() {
        return dateTime.getValue();
    }
    
    
    public ReadOnlyObjectProperty<LocalDateTime> dateTimeProperty() {
        return dateTime;
    }
    
    final void bindDateTimeTo(ObservableValue<LocalDateTime> value) {
        dateTime.bind(value);
    }
    

    public abstract boolean isDirectory();

    public Path getPath() {
        return path; 
    }

    public abstract long size();

    public abstract void ajoutFichier(Fichier f);

    public abstract LocalDateTime getModifDate(Path path) throws IOException;

    public String getNom() {
        return name.get();
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    protected String formatAffichage(int decalage) throws IOException {
        String res = "";
        for (int i = 0; i < decalage; ++i) {
            res += "\t";
        }
        return res;
    }

    public String getLastDirName(Path path) { //TODO at the moment it checks only the parent name and not the entire path... so 2 folders in different places...
        int nameCount = path.getNameCount();
        return path.getName(nameCount - 2).toString();
    }

    public abstract void changeEtat(Fichier fs) throws IOException;

    @Override
    public String toString() {
        try {
            return formatAffichage(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
