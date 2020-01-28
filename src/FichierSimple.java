import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class FichierSimple extends Fichier {

    private long taille;

    public FichierSimple(String nom, long taille) throws IOException {
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
                .append(" - nom : ").append(getNom())
                .append(" - type : ").append(this.type())
                .append(" - date : ").append(getModifDate(getPath()))
                .append(" - taille : ").append(taille())
                .append(" - contient : ").append("\n");
        return res.toString();
    }

    @Override
    public void ajoutFichier(Fichier f) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public LocalDateTime getModifDate(Path path) throws IOException {
        BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
        LocalDateTime result = attrs.lastModifiedTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return result;
    }
}
