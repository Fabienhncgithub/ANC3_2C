import java.io.IOException;
import java.nio.file.Paths;

public class Model {

    public static void main(String[] args) {
        try { //    subPath     0   1       2           3                   4           5       6
            String s1 = "TestBC/RootBC_Left";
            String s2 = "TestBC/RootBC_Right";
            String rootRight = "TestBC/RootBC_Right";
            String rootLeft = "TestBC/RootBC_Left";
            Fichier f1 = new CopyBuilder().build(Paths.get(s1));
            Fichier f2 = new CopyBuilder().build(Paths.get(s2));
            Fichier dirLeft = new CopyBuilder().build(Paths.get(rootLeft));
            Fichier dirRight = new CopyBuilder().build(Paths.get(rootRight));
//           System.out.println(f1);
//            System.out.println("-------------------------------------------------------------");
//           System.out.println(f2);

            //          System.out.println(Paths.get(s1).subpath(7,7));

//            System.out.println(dirLeft);
//            System.out.println(dirRight);
//            System.out.println(((Dossier)dirLeft).getNomEnfant().keySet());
//            System.out.println(((Dossier)dirLeft).getLastPathElement(dirLeft.getPath()));
//            ((Dossier)dirLeft).compareTopFolders((Dossier)dirRight);
            dirLeft.changeEtat(dirRight);
//            dirLeft.changeEtat(dirRight);
            System.out.println(dirLeft);
            System.out.println("----------------------------------------------------------");
            System.out.println(dirRight);
        } catch (IOException e) {
            e.getMessage();
        }
    }
}
