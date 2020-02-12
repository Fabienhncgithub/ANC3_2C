package model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class FichierSimple extends Fichier {

    private long taille;

    public FichierSimple(String nom, long taille, FileTime fileTime, Path path) {
        super(nom, path);
        this.taille = taille;
        setModifDate(fileTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
    }

//    @Override
//    public char type() { //supprimer cette methode et gerer le char dans l'affichage.
//        return 'F'; //F pour Fichier
//    }

    @Override
    public Iterable<Fichier> getContenu() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public long taille() {
        return taille;
    }

    public void changeEtat(Fichier fs) throws IOException {
        if (this.getLastDirName(getPath()).equals(fs.getLastDirName(fs.getPath()))) { // TODO check getLastDirName()
           // System.out.println(this.getLastDirName(getPath()));
            if (this.getNom().equals(fs.getNom())) {
//                System.out.println(this.getNom() + fs.getNom());

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
//                } else {
//                    this.setEtat(Etat.ORPHAN);
//                    fs.setEtat(Etat.ORPHAN);
//                }
            }
        }


    @Override
    protected String formatAffichage(int decalage) throws IOException {
        StringBuilder res = new StringBuilder();
        res.append(super.formatAffichage(decalage))
                .append(" ").append(getNom())
                .append(" - type : ").append("F") //changer cette ligne par (this.isDirectory() ? "D" : "F")
                .append(" - date : ").append(getModifDate(getPath()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
                .append(" - taille : ").append(taille())
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
