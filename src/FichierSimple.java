public class FichierSimple extends Fichier {
    private final int taille;

    public FichierSimple(String nom, int taille) {
        super(nom);
        this.taille = taille;
    }

    @Override
    public int taille() {
        return taille;
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
