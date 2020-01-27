import java.io.IOException;
import java.nio.file.Path;

public class FichierSimple extends Fichier {

private long taille;

    public FichierSimple(Path path) {
        super(path);
    }

    public FichierSimple(String nom, long taille) {
        super(nom);
        this.taille = taille;
    }

    @Override
    public char type() {
        return 'F';
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public long taille() {
        return taille;
    }


    @Override
    protected String formatAffichage(int decalage) throws IOException {
        return super.formatAffichage(decalage) + getNom() + " - taille : " + taille() + "\n";
    }

    @Override
    public void ajoutFichier(Fichier f) {
        throw new UnsupportedOperationException("Not supported.");
    }

}
