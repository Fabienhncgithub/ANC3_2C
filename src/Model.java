import java.io.IOException;
import java.nio.file.Paths;

public class Model {

    public static void main(String[] args) {
        try { //    subPath     0   1       2           3                   4           5       6
            String s1 = "TestBC/RootBC_Left";
            String s2 = "TestBC/RootBC_Right";
            String pathFileR = "TestBC/RootBC_Right/";
            String pathFileL = "TestBC/RootBC_Left/";
            Fichier f1 = new CopyBuilder().build(Paths.get(s1));
            Fichier f2 = new CopyBuilder().build(Paths.get(s2));
            Fichier pathSameFileLf = new CopyBuilder().build(Paths.get(pathFileL));
            Fichier pathSameFileRf = new CopyBuilder().build(Paths.get(pathFileR));
//           System.out.println(f1);
//            System.out.println("-------------------------------------------------------------");
//           System.out.println(f2);

  //          System.out.println(Paths.get(s1).subpath(7,7));
//
//            Files.list(Paths.get(s1))
//                    .filter(Files.isDirectory());

            System.out.println(pathSameFileLf);
            pathSameFileRf.changeEtat(pathSameFileLf);
            pathSameFileLf.changeEtat(pathSameFileRf);
            System.out.println(pathSameFileLf);
            System.out.println(pathSameFileRf);
        } catch (IOException e) {           e.getMessage();        }

//        String nom = new Scanner(System.in).nextLine();
//        try {
//            Path path = Paths.get(nom).toRealPath();
//            System.out.println("La date du fichier (dossier) " + path + " est " +
//                    lastModificationTime(path).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
//        } catch (IOException e) {
//            System.out.println("Le fichier (dossier) " + e.getMessage() + " n'existe pas");
//        }try {
//            Path path = Paths.get(nom).toRealPath();
//            Fichier f = new FichierSimple(path);
//            System.out.println("La taille du fichier (dossier) " + path + " est " +
//                    f.taille());
//        } catch (IOException e) {
//            System.out.println("Le fichier (dossier) " + e.getMessage() + " n'existe pas");
//        }
//    }
    }
}
