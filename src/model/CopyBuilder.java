package model;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyBuilder {
    public static Fichier build(Path path) throws IOException {
        BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
        Fichier result;
        if (Files.isDirectory(path)) {
            result = new Dossier(path.getFileName().toString(), path);
            try (DirectoryStream<Path> dir = Files.newDirectoryStream(path)) {
                for (Path p : dir) {
                    result.ajoutFichier(build(p));
                }
            }
        } else {
            //BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
            if(path.getFileName().toString().endsWith(".txt")) {
                result = new FichierText(path.getFileName().toString(), attrs.size(), attrs.lastModifiedTime(), path);
            }else{
                result = new FichierSimple(path.getFileName().toString(), attrs.size(), attrs.lastModifiedTime(), path);
            }
        }
        return result;
    }
}
