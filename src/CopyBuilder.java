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
                    if (Files.isDirectory(p)){
                        result = new Dossier(p.getFileName().toString());
                        result.ajoutFichier(build(p));
                    }else {
                        result = new FichierSimple(p.getFileName().toString(), attrs.size());
                    }
                    System.out.println(result.formatAffichage(1));
//                    System.out.println(result);
                }
            }
        }
        return result;
    }
}
