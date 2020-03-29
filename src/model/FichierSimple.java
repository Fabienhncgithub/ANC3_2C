package model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.value.ObservableValue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class FichierSimple extends Fichier {

    private LongProperty size;

    public FichierSimple(String nom, long size, FileTime fileTime, Path path) {
        super(nom, path);
        this.size = new SimpleLongProperty(size);
        setDateTime(fileTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
    }

    FichierSimple(String name, long size) {
        super(name, size);
    }

    @Override
    public Iterable<Fichier> getContenu() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isDirectory() {
        return false;
    }


    @Override
    public void addFile(Fichier f) {
        throw new UnsupportedOperationException("Not supported operation.");
    }

    @Override
    public long getSize() {
        return size.get();
    }

    @Override
    public long size() {
        return size.get();
    }

    public void setSize(long size) {
        this.size.set(size);
    }

    public LongProperty sizeProperty() {
        return size;
    }

    final  void bindSizeTo(ObservableValue<Long>value){
        size.bind(value);
    }

    @Override
    public void changeEtat(Fichier fs) throws IOException {
        if (this.getLastDirName(getPath()).equals(fs.getLastDirName(fs.getPath()))) { // TODO check getLastDirName()
            if (this.getName().equals(fs.getName())) {
                if (this.getModifDate(this.getPath()).isEqual(fs.getModifDate(fs.getPath()))) {
                    fs.setEtat(Etat.SAME);
                    this.setEtat(Etat.SAME);
                } else {
                    if (this.getModifDate(this.getPath()).isAfter(fs.getModifDate(fs.getPath()))) {
                        fs.setEtat(Etat.OLDER);
                        this.setEtat(Etat.NEWER);
                    } else {
                        fs.setEtat(Etat.NEWER);
                        this.setEtat(Etat.OLDER);
                    }
                }
            }
        }
    }


    @Override
    protected String formatAffichage(int decalage) throws IOException {
        StringBuilder res = new StringBuilder();
        res.append(super.formatAffichage(decalage))
                .append(" ").append(getName())
                .append(" - type : ").append("F") //changer cette ligne par (this.isDirectory() ? "D" : "F")
                .append(" - date : ").append(getDateTime())
                .append(" - size : ").append(size())
                .append(" - etat : ").append(getEtat())
                .append("\n");
        return res.toString();
    }

    @Override
    public void ajoutFichier(Fichier f) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public LocalDateTime getModifDate(Path path) throws IOException {
        return Files.getLastModifiedTime(path).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
