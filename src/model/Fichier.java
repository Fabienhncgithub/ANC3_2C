package model;

import javafx.beans.property.*;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

public abstract class Fichier extends TreeItem<Fichier> {

    private final StringProperty name;
    private final LongProperty size = new SimpleLongProperty(0L);
    private boolean selected = true;
    private ObjectProperty<LocalDateTime> dateTime;
    private Path path;
    private Etat etat = Etat.UNDEFINED;

    public Fichier(String nom, Path path, Long size) {
        this.name = new SimpleStringProperty(nom);
        this.dateTime = new SimpleObjectProperty<>();
        this.path = path;
        this.size.set(size);
        setExpanded(true);
        setValue(this);
    }

    Fichier(String name, Path path) {
        this.name = new SimpleStringProperty(name);
        this.path = path;
        dateTime = new SimpleObjectProperty<>();
        setExpanded(true);
        setValue(this); // L'info du TreeItem se trouve dans lui-même
    }

    Fichier(String name) {
        this.name = new SimpleStringProperty(name);
        this.path = path;
        dateTime = new SimpleObjectProperty<>();
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

    public void setName(String name) {
        this.name.setValue(name);
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

    protected String formatAffichage(int decalage) {
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
        return formatAffichage(0);
    }

    final void _addFile(Fichier f) {
        // Utilise super car la version redéfinie renvoie un immuable
        super.getChildren().add(f);
    }

    public abstract void changeEtat(Fichier fs);

    public abstract Iterable<Fichier> getContenu();

    public abstract void addFile(Fichier file);

    public abstract void ajoutFichier(Fichier f);

    //public abstract LocalDateTime getModifDate(Path path);
    public abstract boolean isDirectory();

    public abstract boolean isFichierText();

    public List<Fichier> getContent() {
        return getChildren().stream().map(ti -> ti.getValue()).collect(toList());
    }

}
