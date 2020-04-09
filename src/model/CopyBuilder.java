package model;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CopyBuilder {

    public static Fichier build(Path path) {
        Fichier result = null;
        try {
            BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
            if (Files.isDirectory(path)) {
                result = new Dossier(path.getFileName().toString(), path);
                try (DirectoryStream<Path> dir = Files.newDirectoryStream(path)) {
                    for (Path p : dir) {
                        result.ajoutFichier(build(p));
                    }
                } catch (IOException ex) {
                    Logger.getLogger(CopyBuilder.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                if (path.getFileName().toString().endsWith(".txt")) {
                    String text = new String(Files.readAllBytes(path));
                    result = new FichierText(path.getFileName().toString(), attrs.size(), getModifDate(path), path, text);
                } else {
                    result = new FichierSimple(path.getFileName().toString(), attrs.size(), getModifDate(path), path);
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(CopyBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    
    public static  LocalDateTime getModifDate(Path path) {
        LocalDateTime result = null;
        try {
            BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
            result = attrs.lastModifiedTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            if (Files.isDirectory(path)) {
                try (DirectoryStream<Path> dir = Files.newDirectoryStream(path)) {
                    for (Path p : dir) {
                        LocalDateTime tmp = getModifDate(p);
                        if (tmp.isAfter(result)) {
                            result = tmp;
                        }
                    }
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Dossier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
