import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

public abstract class Fichier {
    private final String nom;
    private Date modifDate;
    private boolean typeDossier;
    private Path path;

    public Fichier(Path path) {
        this(path.toFile());
    }

    public Fichier(String nom) {
        this.nom = nom;
    }

    public Fichier(File fichier) {
        this.nom = fichier.getName();
        this.modifDate = new Date(fichier.lastModified()*1000);
        this.typeDossier = fichier.isDirectory();
        this.path = fichier.toPath();
    }

    public Fichier(String nom, Date modifDate, boolean typeDossier, Path path) {
        this.nom = nom;
        this.modifDate = modifDate;
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

    public Date getModifDate() {
        return modifDate;
    }

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
        return Paths.get(getNom()).toRealPath();
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
