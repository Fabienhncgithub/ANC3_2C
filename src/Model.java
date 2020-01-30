import java.io.IOException;
import java.nio.file.Paths;

public class Model {

    public static void main(String[] args) {
        try { //    subPath     0   1       2           3                   4           5       6
            String s1 = "C:\\Users\\GV\\Documents\\IntelliJ_Projects\\Iteration1_ANC3\\TestBC\\RootBC_Left";
            String s2 = "C:\\Users\\GV\\Documents\\IntelliJ_Projects\\Iteration1_ANC3\\TestBC\\RootBC_Right";
            Fichier f1 = new CopyBuilder().build(Paths.get(s1));
            Fichier f2 = new CopyBuilder().build(Paths.get(s2));
//            System.out.println(f1);
//            System.out.println("-------------------------------------------------------------");
//            System.out.println(f2);

            System.out.println(Paths.get(s1).subpath(5,7));
//            for (Fichier f : )
            System.out.println(f1.getEtat(f2));

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

// a afficher :  chemin, nom, type (D pour dossier...), date, taille, tag
    }
}
