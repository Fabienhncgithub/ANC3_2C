import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Dossier extends Fichier {

    private final List<Fichier> contenu = new ArrayList<>();

    public Dossier(String nom) {
        super(nom);
    }

    public List<Fichier> getContenu() {
        return contenu;
    }

    public Dossier(File file) {
        super(file);
    }

    public Dossier(String nom, Date modifDate, boolean typeDossier, Path path) {
        super(nom, modifDate, typeDossier, path);
    }

    @Override
    public char type() {
        return 'D';
    }

    @Override
    public boolean isDirectory() {
        return true;
    }

    @Override
    public long taille() {
        long result = 0;
                for (Fichier f : contenu) {
                    result += f.taille();
                }
        return result;
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
                .append(" - nom : ").append(getNom())
                .append(" - type : ").append(type())
                .append(" - date : ").append(getModifDate())
                .append(" - taille : ").append(taille())
                .append(" - contient : ").append("\n");
        for (Fichier f : contenu)
            res.append(f.formatAffichage(decalage + 1));
        return res.toString();
    }

    @Override
    public void ajoutFichier(Fichier f) {
        contenu.add(f);
    }

}
