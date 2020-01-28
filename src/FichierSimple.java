import java.io.IOException;

public class FichierSimple extends Fichier {

private long taille;

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
        StringBuilder res = new StringBuilder();
        res.append(super.formatAffichage(decalage))
                .append(getNom())
                .append(" - nom : ").append(getNom())
                .append(" - type : ").append(this.type())
                .append(" - date : ").append(getModifDate())
                .append(" - taille : ").append(taille())
                .append(" - contient : ").append("\n");
        return res.toString();
    }

    @Override
    public void ajoutFichier(Fichier f) {
        throw new UnsupportedOperationException("Not supported.");
    }

}
