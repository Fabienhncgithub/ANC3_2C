import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;

public abstract class Fichier {
    private final String nom;
    private LocalDateTime modifDate;
    private Path path;
    private Etat etat = Etat.UNDEFINED;
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

    public Etat getEtat() {
        return etat;
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

    public String getLastDirName(Path path){ //TODO at the moment it checks only the parent name and not the entire path... so 2 folders in different places...
        int nameCount = path.getNameCount();
        return path.getName(nameCount-2).toString();
    }

    public abstract void changeEtat(Fichier fs) throws IOException;

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
