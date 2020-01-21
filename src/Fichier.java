import java.util.Date;

public abstract class Fichier {
    private final String nom;
    private Date modifDate;
    private Etat etat;

    public Fichier(String nom, Date modifDate, Etat etat) {
        this.nom = nom;
        this.modifDate = modifDate;
        this.etat = etat;
    }

    public abstract boolean isDirectory();

    public abstract int taille();

    public abstract void ajoutFichier(Fichier f);

    public Date getModifDate() {
        return modifDate;
    }


    public Etat getEtat() {
        return etat;
    }


    public String getNom() {
        return nom;
    }

    protected String formatAffichage(int decalage) {
        String res = "";
        for (int i = 0; i < decalage; ++i)
            res += "\t";
        return res;
    }

    @Override
    public String toString() {
        return formatAffichage(0);
    }


}
