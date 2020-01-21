import java.nio.file.Files;
import java.util.Date;

public class FichierSimple extends Fichier {


    public FichierSimple(String nom, Date modifDate, Etat etat) {
        super(nom,modifDate,etat);
    }

    @Override
    public int taille() {
        return taille;
    }

    @Override
    public boolean isDirectory() {
        return false;
    }


    @Override
    protected String formatAffichage(int decalage) {
        return super.formatAffichage(decalage) + getNom() + " - taille : " + taille() + "\n";
    }

    @Override
    public void ajoutFichier(Fichier f) {
        throw new UnsupportedOperationException("Not supported."); 
    }

}
