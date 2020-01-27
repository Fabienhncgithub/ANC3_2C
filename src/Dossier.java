import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Dossier extends Fichier {

    private final List<Fichier> fichiers = new ArrayList<>();

    public Dossier(Path path) {
        super(path);
    }

    public Dossier(File file) {
        super(file);
    }

    @Override
    public char type() {
        return 'D';
    }

    @Override
    public boolean isDirectory() {
        return true;
    }

//    @Override
//    public int taille() {
//        int res = 0;
//        for (Fichier f : fichiers)
//            res += f.taille();
//        return res;
//    }

    @Override
    protected String formatAffichage(int decalage) throws IOException {
        StringBuilder res = new StringBuilder();
        res.append(super.formatAffichage(decalage))
                .append(getNom())
                .append(" - taille : ").append(taille(getPath()))
                .append(" - contient : ").append("\n");
        for (Fichier f : fichiers)
            res.append(f.formatAffichage(decalage + 1));
        return res.toString();
    }

    @Override
    public void ajoutFichier(Fichier f) {
        fichiers.add(f);
    }

}
