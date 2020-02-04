import java.io.IOException;
import java.nio.file.Paths;

public class Model {

    public static void main(String[] args) {
        try { //    subPath     0   1       2           3                   4           5       6

            String rootRight = "TestBC/RootBC_Right";
            String rootLeft = "TestBC/RootBC_Left";
            Fichier dirLeft = new CopyBuilder().build(Paths.get(rootLeft));
            Fichier dirRight = new CopyBuilder().build(Paths.get(rootRight));

            dirLeft.changeEtat(dirRight);
            System.out.println(dirLeft);
            System.out.println("----------------------------------------------------------");
            System.out.println(dirRight);
        } catch (IOException e) {
            e.getMessage();
        }
    }
}
