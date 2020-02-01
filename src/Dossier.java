import java.io.IOException;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Dossier extends Fichier {

    private final List<Fichier> contenu = new ArrayList<>();

    public List<Fichier> getContenu() {
        return contenu;
    }

    public Dossier(String nom, Path path) {
        super(nom, path);
    }

    @Override
    public char type() {
        return 'D'; // D pour Dossier
    }

    @Override
    public long taille() {
        long result = 0;
        for (Fichier f : contenu) {
            result += f.taille();
        }
        return result;
    }

    @Override
    protected String formatAffichage(int decalage) throws IOException {
        StringBuilder res = new StringBuilder();
        res.append(super.formatAffichage(decalage))
                .append(getNom())
                .append(" - type : ").append(this.type())
                .append(" - date : ").append(getModifDate(this.getPath()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
                .append(" - taille : ").append(taille())
                .append(" - contient : ").append("\n");
        for (Fichier f : contenu)
            res.append(f.formatAffichage(decalage + 1));
        return res.toString();
    }

    @Override
    public void changeEtat(Fichier fs) throws IOException {
        if (fs instanceof Dossier) {
            Dossier other = (Dossier) fs;


            while( this.getEtat().equals(Etat.INDEFINED)){
                    if(this.contenu.size() == 0 && other.contenu.size() == 0){
                        this.setEtat(Etat.SAME);
                        other.setEtat(Etat.SAME);
                    }else if(this.contenu.size() == 0){
                        this.setEtat(Etat.PARTIAL_SAME);
                        other.setEtat(Etat.PARTIAL_SAME);
                        
                        for(Fichier f : other.contenu){
                            f.setEtat(Etat.ORPHAN);
                        }

                    }else{


                    }

                    }

            }


//            Etat tmpEtat = Etat.INDEFINED;
//            for(Fichier fichierDirA: this.contenu){
//                for(Fichier fichierDirB  : other.contenu){
//                        if(fichierDirA.getEtat().equals(fichierDirB.getEtat())){
//                            if(this.getEtat().equals(Etat.SAME)){
//                                if (Etat.values().equals(Etat.SAME) && ) {
//                                    tmpEtat = Etat.SAME;
//                                } else if (Etat.values().equals(Etat.PARTIAL_SAME)) {
//                                    tmpEtat = Etat.PARTIAL_SAME;
//                                } else if (Etat.values().equals(Etat.ORPHAN)) {
//                                    tmpEtat = Etat.ORPHAN;
//                                }
//
//
//                                }else if(){
//
//
//                            }
//
//
//                        }
//
//                }
            }



//            if (this.contenu.containsAll(other.contenu)) {
//                System.out.println("those dir are the same");
//            }else{
//                System.out.println("those dir dont containt the same files");
//                if (this.getNom() == fs.getNom()) {
//                    if (this.getModifDate(this.getPath().subpath(5, 7)).isEqual(fs.getModifDate(fs.getPath().subpath(3, 4)))) {
//                        fs.setEtat(Etat.SAME);
//                        this.setEtat(Etat.SAME);
//                    } else {
//                        if (this.getModifDate(this.getPath().subpath(5, 7)).isAfter(fs.getModifDate(fs.getPath().subpath(3, 4)))) {
//                            fs.setEtat(Etat.OLDER);
//                            this.setEtat(Etat.NEWER);
//                        } else {
//                            fs.setEtat(Etat.NEWER);
//                            this.setEtat(Etat.OLDER);
//                        }
//                    }
//                }
            }
        }

    @Override
    public void ajoutFichier(Fichier f) {
        contenu.add(f);
    }

    @Override
    public LocalDateTime getModifDate(Path path) throws IOException {
        BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
        LocalDateTime result = attrs.lastModifiedTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
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
        return result;
    }

}
