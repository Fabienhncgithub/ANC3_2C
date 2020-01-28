import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class Dossier extends Fichier {

    private final List<Fichier> contenu = new ArrayList<>();

    public Dossier(String nom) throws IOException {
        super(nom);
    }

    public List<Fichier> getContenu() {
        return contenu;
    }

    @Override
    public char type() {
        return 'D';
    }

    @Override
    public boolean isDirectory() {
        return true;
    }

//    @Override
//    public int taille() {
//        int res = 0;
//        for (Fichier f : fichiers)
//            res += f.taille();
//        return res;
//    }

    @Override
    public long taille() {
        long result = 0;
        for (Fichier f : contenu) {
            result += f.taille();
        }
        return result;
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
        for (Fichier f : contenu)
            res.append(f.formatAffichage(decalage + 1));
        return res.toString();
    }

    @Override
    public void ajoutFichier(Fichier f) {
        contenu.add(f);
    }

    @Override
    public LocalDateTime getModifDate(Path path) throws IOException {
        BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
        LocalDateTime result = attrs.lastModifiedTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        if (Files.isDirectory(this.getPath())) {
            try (DirectoryStream<Path> dir = Files.newDirectoryStream(path)) {
                for (Path p : dir) {
                    LocalDateTime tmp = getModifDate(p);
                    if (tmp.isAfter(result)) {
                        result = tmp;
                    }
                }
            }
        }
        return result;
    }

}
