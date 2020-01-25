import java.io.IOException;
import java.nio.file.Path;

public class FichierSimple extends Fichier {


    public FichierSimple(Path path) {
        super(path);
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
    protected String formatAffichage(int decalage) throws IOException {
        return super.formatAffichage(decalage) + getNom() + " - taille : " + taille(getPath()) + "\n";
    }

    @Override
    public void ajoutFichier(Fichier f) {
        throw new UnsupportedOperationException("Not supported."); 
    }

}
