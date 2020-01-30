import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;

public abstract class Fichier {
    private final String nom;
    private LocalDateTime modifDate;
    private Path path;
    private Etat etat = Etat.ORPHAN;
    private int finalIdxSubPath = 7;

    public Fichier(String nom, Path path) {
        this.nom = nom;
        this.path = path;
    }

    public void setModifDate(LocalDateTime modifDate) {
        this.modifDate = modifDate;
    }

    public abstract char type();

    public Path getPath() {
        return path;
    }

    public abstract long taille() ;

    public abstract void ajoutFichier(Fichier f);

    public abstract LocalDateTime getModifDate(Path path) throws IOException;

    public String getNom() {
        return nom;
    }

    protected String formatAffichage(int decalage) throws IOException {
        String res = "";
        for (int i = 0; i < decalage; ++i)
            res += "\t";
        return res;
    }

//    public Path stringToPath() throws IOException {
//        return Paths.get(getNom()).toRealPath();
//    }

    public abstract Etat getEtat(Fichier fs) throws IOException;

    @Override
    public String toString() {
        String res = "toString Fichier";
        try {
            return formatAffichage(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public void setEtat(Etat etat){
        this.etat = etat;
    }
}
