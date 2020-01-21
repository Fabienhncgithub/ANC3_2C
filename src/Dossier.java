import java.util.ArrayList;
import java.util.List;

public class Dossier extends Fichier {

    private final List<Fichier> fichiers = new ArrayList<>();

    public Dossier(String nom) {
        super(nom);
    }

    @Override
    public int taille() {
        int res = 0;
        for (Fichier f : fichiers)
            res += f.taille();
        return res;
    }

    @Override
    protected String formatAffichage(int decalage) {
        StringBuilder res = new StringBuilder();
        res.append(super.formatAffichage(decalage))
                .append(getNom())
                .append(" - taille : ").append(taille())
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
