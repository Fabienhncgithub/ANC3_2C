import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyBuilder {
    static Fichier build(Path path) throws IOException {
        BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
        Fichier result = new Dossier(path.getFileName().toString());
        if (Files.isDirectory(path)) {
            result = new Dossier(path.getFileName().toString(), path);
            try (DirectoryStream<Path> dir = Files.newDirectoryStream(path)) {
                for (Path p : dir) {
                    result.ajoutFichier(build(p));
                }
            }
        } else {
            BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
            result = new FichierSimple(path.getFileName().toString(), attrs.size(), attrs.lastModifiedTime(), path);
        }
        return result;
    }
}
