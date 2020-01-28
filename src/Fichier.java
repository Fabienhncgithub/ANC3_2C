import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public abstract class Fichier {
    private final String nom;
    private LocalDateTime modifDate;
    private boolean typeDossier;
    private Path path;

    public Fichier(String nom) throws IOException {
        this.nom = nom;
        this.path = stringToPath();
    }

//    public Fichier(File fichier) throws IOException {
//        this.nom = fichier.getName();
//        this.modifDate = getModifDate(this.path);
//        this.typeDossier = fichier.isDirectory();
//        this.path = fichier.toPath();
//    }

    public Fichier(String nom, LocalDateTime modifDate, boolean typeDossier, Path path) throws IOException {
        this.nom = nom;
        this.modifDate = getModifDate(this.path);
        this.typeDossier = typeDossier;
        this.path = path;
        ajoutFichier(this);
    }


    public abstract char type();

    public abstract boolean isDirectory();

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

    public Path stringToPath() throws IOException {
        return Paths.get(getNom()).toAbsolutePath();
    }

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
}
