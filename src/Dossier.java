import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Dossier extends Fichier {
    private Map<String, Integer> nomEnfant = new TreeMap<>();
    private List<Fichier> contenu = new ArrayList<>();

    public Dossier(String nom, Path path) throws IOException {
        super(nom, path);
                List<Path> tmpPathsList = Files.list(path).collect(Collectors.toList());
        for(int i = 0; i < (tmpPathsList.size()); i++){
            nomEnfant.put(getLastPathElement(tmpPathsList.get(i)), i);
        }
//        for (int i = 0; i < contenu.size(); i++) {
//            nomEnfant.put(contenu.get(i).getNom(), i);
//        }
    }

    public Map<String, Integer> getNomEnfant() {
        return nomEnfant;
    }

//    public void rempliNomEnfant() {
//        for (Fichier f : contenu) {
//            nomEnfant.put(f.getNom(), contenu.indexOf(f));
//        }
//    }

    public String getLastPathElement(Path path){
        int nameCount = path.getNameCount();
        return path.getName(nameCount -1).toString();
    }

    public List<Fichier> getContenu() {
        return contenu;
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
                .append(" - etat : ").append(getEtat())
                .append(" - contient : ").append("\n");
        for (Fichier f : contenu)
            res.append(f.formatAffichage(decalage + 1));
        return res.toString();
    }

    public void compareTopFolders(Dossier d) throws IOException {
        for (Fichier enfant : this.contenu) {
            System.out.println(d.nomEnfant.keySet());
            if (d.nomEnfant.containsKey(enfant.getNom())) {
                enfant.changeEtat(d.contenu.get(d.nomEnfant.get(enfant.getNom())));
            } else {
                if (enfant.type() == 'F') {
                    System.out.println("orphanforever");
                    enfant.setEtat(Etat.ORPHAN);
                } else {
                    ((Dossier) enfant).setAllChildrenOrphan();
                }
            }
        }
    }


    @Override
    public void changeEtat(Fichier fs) throws IOException {

        if (fs instanceof Dossier) {
            Dossier other = (Dossier) fs;


            if (this.getNom() == other.getNom()) {

                while (this.getEtat().equals(Etat.INDEFINED)) {
                    System.out.println("test");
                    if (this.contenu.size() == 0 && other.contenu.size() == 0) {
                        System.out.println("test1");
                        this.setEtat(Etat.SAME);
                        other.setEtat(Etat.SAME);
                    } else if (this.contenu.size() == 0) {
                        System.out.println("test2");
                        this.setEtat(Etat.PARTIAL_SAME);
                        other.setEtat(Etat.PARTIAL_SAME);
                        other.setAllChildrenOrphan();
                    } else if (other.contenu.size() == 0) {
                        System.out.println("test3");
                        other.setEtat(Etat.PARTIAL_SAME);
                        this.setEtat(Etat.PARTIAL_SAME);
                        this.setAllChildrenOrphan();
                    }

                }
            }
            //  System.out.println("This Folder are not the same");


        }
    }

    private void setAllChildrenOrphan() {

        for (Fichier f : this.contenu) {
            if (f.type() == 'F' && f.getEtat() == Etat.INDEFINED) {
                f.setEtat(Etat.ORPHAN);
                String parent = f.getLastDirName(f.getPath());
                for (Fichier fParent : this.contenu) {
                    if (fParent.getNom() == parent) {
                        Dossier d = (Dossier) fParent;
                        d.setAllChildrenOrphan();
                    }
                }
            } else if (f.type() == 'D' && f.getEtat() == Etat.INDEFINED) {
                f.setEtat(Etat.ORPHAN);
                Dossier d = (Dossier) f;
                d.setAllChildrenOrphan();
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
//            }


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
//            }
//        }

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
