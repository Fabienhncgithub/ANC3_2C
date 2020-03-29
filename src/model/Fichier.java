package model;

import javafx.beans.property.*;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;

public abstract class Fichier extends TreeItem<Fichier> {

    private final StringProperty name;
    private final LongProperty size;
    boolean selected = true;
    private ObjectProperty<LocalDateTime> dateTime;
    private Path path;
    private Etat etat = Etat.UNDEFINED;

    public Fichier(String nom, Path path) {
        this.name = new SimpleStringProperty(nom);
        this.dateTime = new SimpleObjectProperty<>(LocalDateTime.now());
        this.path = path;
        this.size = new SimpleLongProperty(0L);
        setExpanded(true);
        setValue(this);
    }

    Fichier(String name) {
        this.name = new SimpleStringProperty(name);
        size = new SimpleLongProperty(0L);
        dateTime = new SimpleObjectProperty<>(LocalDateTime.now());
        setExpanded(true);
        setValue(this); // L'info du TreeItem se trouve dans lui-même
    }

    Fichier(String name, long size) {
        this(name);
        this.size.set(size);
    }

    final void bindSizeTo(ObservableValue<Long> value) {
        size.bind(value);
    }

    public long getSize() {
        return size.get();
    }

    public void setSize(long size) {
        this.size.set(size);
    }

    public long size() {
        return size.get();
    }

    public LongProperty sizeProperty() {
        return size;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public LocalDateTime getDateTime() {
        return dateTime.getValue();
    }

    public void setDateTime(LocalDateTime modifDate) {
        this.dateTime.setValue(modifDate);
    }

    public ReadOnlyObjectProperty<LocalDateTime> dateTimeProperty() {
        return dateTime;
    }

    final void bindDateTimeTo(ObservableValue<LocalDateTime> value) {
        dateTime.bind(value);
    }

    public Path getPath() {
        return path;
    }

    public String getName() {
        return name.get();
    }

    public ObservableStringValue nameProperty() {
        return name;
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

    @Override
    public String toString() {
        try {
            return formatAffichage(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    final void _addFile(Fichier f) {
        // Utilise super car la version redéfinie renvoie un immuable
        super.getChildren().add(f);
    }

    public abstract void changeEtat(Fichier fs) throws IOException;

    public abstract Iterable<Fichier> getContenu();

    //public abstract long size();

//    public ReadOnlyLongProperty sizeProperty() {
//        return size;
//    }

    public abstract void addFile(Fichier file);

    public abstract void ajoutFichier(Fichier f);

    public abstract LocalDateTime getModifDate(Path path) throws IOException;

    public abstract boolean isDirectory();

//    public long getSize() {
//        return size.get();
//    }
//
//    void setSize(long size) {
//        this.size.set(size);
//    }
}
