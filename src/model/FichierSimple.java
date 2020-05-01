package model;

import java.nio.file.Path;
import java.time.LocalDateTime;

public class FichierSimple extends Fichier {

    public FichierSimple(String name, long size, LocalDateTime date, Path path) {
        super(name, path, size);
        setDateTime(date);
    }


    public FichierSimple(FichierSimple f ){
        this(f.getName(), f.getSize(), f.getDateTime(), f.getPath());
    }


    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public boolean isFichierText() {
        return false;
    }

    @Override
    public void changeEtat(Fichier fs) {
        if (this.getLastDirName(getPath()).equals(fs.getLastDirName(fs.getPath()))) { 
            if (this.getName().equals(fs.getName())) {
                if (this.getDateTime().isEqual(fs.getDateTime())) {
                    fs.setEtat(Etat.SAME);
                    this.setEtat(Etat.SAME);
                } else {
                    if (this.getDateTime().isAfter(fs.getDateTime())) {
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
    protected String formatAffichage(int decalage) {
        StringBuilder res = new StringBuilder();
        res.append(super.formatAffichage(decalage))
                .append(" ").append(getName())
                .append(" - type : ").append("F") 
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
    

}
