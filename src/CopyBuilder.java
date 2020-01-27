import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyBuilder {
    static Fichier build(Path path) throws IOException {
        BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
        Fichier result = new FichierSimple(path.getFileName().toString(), attrs.size());
        if (Files.isDirectory(path)) {
            try (DirectoryStream<Path> dir = Files.newDirectoryStream(path)) {
                for (Path p : dir) {
                    Dossier d = new Dossier(p.getFileName().toString());
                    result = d;
//                    d.ajoutFichier(build(p));
//                    System.out.println(d.getContenu());
                    System.out.println(result);
                }
            }
        }
        return result;
    }
}
