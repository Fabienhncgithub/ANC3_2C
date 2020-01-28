import java.io.IOException;

public class Model {

    public static void main(String[] args) {
        try {
            Fichier f1 = new CopyBuilder().build(new Dossier("TestBC/RootBC_Left").stringToPath());
            Fichier f2 = new CopyBuilder().build(new Dossier("TestBC/RootBC_Right").stringToPath());
            System.out.println(f1);
            System.out.println(f2);
            f1.formatAffichage(1);
            f2.formatAffichage(1);
        } catch (IOException e) {
            e.getMessage();
        }

//
//        System.out.println("Ce programme d'exemple montre l'affichage de la date de dernière modification.\n" +
//                "La date d'un dossier sera la date de son contenu le plus récent.\n");
//        System.out.print("Entrez le nom du fichier (ou dossier) (<ENTER> pour le dossier courant) : ");
//        String nom = new Scanner(System.in).nextLine();
//        try {
//            Path path = Paths.get(nom).toRealPath();
//            System.out.println("La date du fichier (dossier) " + path + " est " +
//                    lastModificationTime(path).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
//        } catch (IOException e) {
//            System.out.println("Le fichier (dossier) " + e.getMessage() + " n'existe pas");
//        }
//        try {
//            Path path = Paths.get(nom).toRealPath();
//            Fichier f = new FichierSimple(path);
//            System.out.println("La taille du fichier (dossier) " + path + " est " +
//                    f.taille());
//        } catch (IOException e) {
//            System.out.println("Le fichier (dossier) " + e.getMessage() + " n'existe pas");
//        }
//
//    }
//
//    static LocalDateTime lastModificationTime(Path path) throws IOException {
//        BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
//        LocalDateTime result = attrs.lastModifiedTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
//        if (Files.isDirectory(path)) {
//            try (DirectoryStream<Path> dir = Files.newDirectoryStream(path)) {
//                for (Path p : dir) {
//                    LocalDateTime tmp = lastModificationTime(p);
//                    if (tmp.isAfter(result)) {
//                        result = tmp;
//                    }
//                }
//            }
//        }
//        return result;
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
