import java.io.File;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;

public class CopyBuilder {
    private String nom;
    private Date modifDate;
    private boolean typeDossier;
    private Path path;
    private static List<Fichier> contenu;

    public static Dossier build(String path) {
        File file = new File(path);
        return new Dossier(file);
    }

    public CopyBuilder setNom(String nom) {
        this.nom = nom;
        return this;
    }

    public CopyBuilder setModifDate(Date modifDate) {
        this.modifDate = modifDate;
        return this;
    }

    public CopyBuilder setTypeDossier(boolean typeDossier) {
        this.typeDossier = typeDossier;
        return this;
    }

    public CopyBuilder setPath(Path path) {
        this.path = path;
        return this;
    }

    Dossier buildD() {
        return new Dossier(nom, modifDate, typeDossier, path);
    }
}
