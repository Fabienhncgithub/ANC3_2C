import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;

public abstract class Fichier {
    private final String nom;
    private Date modifDate;
    private boolean typeDossier;
    private Path path;

    public Fichier(Path path) {
        this(path.toFile());
    }

    public Fichier(File fichier) {
        this.nom = fichier.getName();
        this.modifDate = new Date(fichier.lastModified()*1000);
        this.typeDossier = fichier.isDirectory();
        this.path = fichier.toPath();
    }


    public abstract char type();

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

    public boolean isTypeDossier() {
        return typeDossier;
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


    static class FileBuilder {

    }
}
