import java.io.File;
import java.nio.file.Path;
import java.util.Date;

public class CopyBuilder {
    private String nom;
    private Date modifDate;
    private boolean typeDossier;
    private Path path;

    public static Fichier build(String path) {
        //Path p = Paths.get(path);
        File file = new File(path);
        return new Dossier(file);
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setModifDate(Date modifDate) {
        this.modifDate = modifDate;
    }

    public void setTypeDossier(boolean typeDossier) {
        this.typeDossier = typeDossier;
    }

    public void setPath(Path path) {
        this.path = path;
    }

}
