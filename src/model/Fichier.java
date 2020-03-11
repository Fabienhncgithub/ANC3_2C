package model;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;

public abstract class Fichier {

    private final String nom;
    private LocalDateTime modifDate;
    private Path path;
    private Etat etat = Etat.UNDEFINED; //Mettre comme valeure par défaut PARTIAL_SAME ca va permettre de faire un test en moin.
    boolean selected = true;

    public Fichier(String nom, Path path) {
        this.nom = nom;
        this.path = path;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
    

    public abstract Iterable<Fichier> getContenu();

    public void setModifDate(LocalDateTime modifDate) {
        this.modifDate = modifDate;
    }

    public abstract boolean isDirectory();

    public Path getPath() {
        return path; //on peut mettre Paths.get(getNom()).toRealPath ce qui va permettre d'avoir une variable en moin.
    }

    public abstract long taille();

    public abstract void ajoutFichier(Fichier f);

    public abstract LocalDateTime getModifDate(Path path) throws IOException;

    public String getNom() {
        return nom;
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
