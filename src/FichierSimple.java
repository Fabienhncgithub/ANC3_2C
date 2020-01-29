import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class FichierSimple extends Fichier {

    private long taille;

    public FichierSimple(String nom, long taille, FileTime fileTime, Path path) throws IOException {
        super(nom, path);
        this.taille = taille;
        setModifDate(fileTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
    }

    @Override
    public char type() {
        return 'F'; //F pour Fichier
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
                .append(" - date : ").append(getModifDate(getPath()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
                .append(" - taille : ").append(taille())
                .append("\n");
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
