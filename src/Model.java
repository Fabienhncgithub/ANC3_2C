import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Model {

    public static void main(String[] args) {
        Fichier f1 = CopyBuilder.build("TestBC/RootBC_Left");
        Fichier f2 = CopyBuilder.build("TestBC/RootBC_Right");
        System.out.println(f1);
        System.out.println(f2);

//        Fichier f3 = new CopyBuilder().setNom("DossierTest").setModifDate();

        System.out.println("Ce programme d'exemple montre l'affichage de la date de dernière modification.\n" +
                "La date d'un dossier sera la date de son contenu le plus récent.\n");
        System.out.print("Entrez le nom du fichier (ou dossier) (<ENTER> pour le dossier courant) : ");
        String nom = new Scanner(System.in).nextLine();
        try {
            Path path = Paths.get(nom).toRealPath();
            System.out.println("La date du fichier (dossier) " + path + " est " +
                    lastModificationTime(path).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        } catch (IOException e) {
            System.out.println("Le fichier (dossier) " + e.getMessage() + " n'existe pas");
        }
        try {
            Path path = Paths.get(nom).toRealPath();
            Fichier f = new FichierSimple(path);
            System.out.println("La taille du fichier (dossier) " + path + " est " +
                    f.taille());
        } catch (IOException e) {
            System.out.println("Le fichier (dossier) " + e.getMessage() + " n'existe pas");
        }

    }

    static LocalDateTime lastModificationTime(Path path) throws IOException {
        BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
        LocalDateTime result = attrs.lastModifiedTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        if (Files.isDirectory(path)) {
            try (DirectoryStream<Path> dir = Files.newDirectoryStream(path)) {
                for (Path p : dir) {
                    LocalDateTime tmp = lastModificationTime(p);
                    if (tmp.isAfter(result)) {
                        result = tmp;
                    }
                }
            }
        }
        return result;
    }


//
//    static long size(Path path) throws IOException {
//        long result = 0;
//        if (Files.isDirectory(path)) {
//            try (DirectoryStream<Path> dir = Files.newDirectoryStream(path)) {
//                for (Path p : dir) {
//                    result += size(p);
//                }
//            }
//        } else {
//            result = Files.size(path);
//        }
//        return result;
//    }

// a afficher :  chemin, nom, type (D pour dossier...), date, taille, tag
}
