import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Dossier extends Fichier {

    private final List<Fichier> contenu = new ArrayList<>();

    public List<Fichier> getContenu() {
        return contenu;
    }

    public Dossier(String nom, Path path) {
        super(nom, path);
    }

    @Override
    public char type() {
        return 'D'; // D pour Dossier
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
                .append(getNom())
                .append(" - type : ").append(this.type())
                .append(" - date : ").append(getModifDate(getPath()))
                .append(" - taille : ").append(taille())
                .append(" - contient : ").append("\n");
        for (Fichier f : contenu)
            res.append(f.formatAffichage(decalage + 1));
        return res.toString();
    }

    @Override
    public void changeEtat(Fichier fs) throws IOException {
        if (fs instanceof Dossier) {
            Dossier other = (Dossier) fs;
            if (this.contenu.containsAll(other.contenu)) {
                System.out.println("those dir are the same");
            }else{
                System.out.println("those dir dont containt the same files");
//                if (this.getNom() == fs.getNom()) {
//                    if (this.getModifDate(this.getPath().subpath(5, 7)).isEqual(fs.getModifDate(fs.getPath().subpath(3, 4)))) {
//                        fs.setEtat(Etat.SAME);
//                        this.setEtat(Etat.SAME);
//                    } else {
//                        if (this.getModifDate(this.getPath().subpath(5, 7)).isAfter(fs.getModifDate(fs.getPath().subpath(3, 4)))) {
//                            fs.setEtat(Etat.OLDER);
//                            this.setEtat(Etat.NEWER);
//                        } else {
//                            fs.setEtat(Etat.NEWER);
//                            this.setEtat(Etat.OLDER);
//                        }
//                    }
//                }
            }
        }
    }

    @Override
    public void ajoutFichier(Fichier f) {
        contenu.add(f);
    }

    @Override
    public LocalDateTime getModifDate(Path path) throws IOException {
        BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
        LocalDateTime result = attrs.lastModifiedTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        if (Files.isDirectory(path)) {
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
