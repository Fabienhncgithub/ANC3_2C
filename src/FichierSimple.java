import java.util.Date;

public class FichierSimple extends Fichier {


    public FichierSimple(String nom, Date modifDate, Etat etat, String path) {
        super(nom, modifDate, etat, path);
    }

    @Override
    public boolean isDirectory() {
        return false;
    }


    @Override
    protected String formatAffichage(int decalage) {
        return super.formatAffichage(decalage) + getNom() + " - taille : " + taille(getPath()) + "\n";
    }

    @Override
    public void ajoutFichier(Fichier f) {
        throw new UnsupportedOperationException("Not supported."); 
    }

}
