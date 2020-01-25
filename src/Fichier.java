import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;

public abstract class Fichier {
    private final String nom;
    private Date modifDate;
    private Etat etat;
    private Path path;

    public Fichier(String nom, Date modifDate, Etat etat, Path path) {
        this.nom = nom;
        this.modifDate = modifDate;
        this.etat = etat;
        this.path = path;
    }

    public abstract boolean isDirectory();

    public Path getPath() {
        return path;
    }

    public long taille(Path path) throws IOException {
        long result = 0;
        if (Files.isDirectory(path)) {
            try (DirectoryStream<Path> dir = Files.newDirectoryStream(path)) {
                for (Path p : dir) {
                    result += taille(p);
                }
            } catch (IOException e) {
                throw new IOException();
            }
        } else {
            result = Files.size(path);
        }
        return result;
    }

    public abstract void ajoutFichier(Fichier f);

    public Date getModifDate() {
        return modifDate;
    }


    public Etat getEtat() {
        return etat;
    }


    public String getNom() {
        return nom;
    }

    protected String formatAffichage(int decalage) {
        String res = "";
        for (int i = 0; i < decalage; ++i)
            res += "\t";
        return res;
    }

    @Override
    public String toString() {
        return formatAffichage(0);
    }


    static class FileBuilder {

    }
}
