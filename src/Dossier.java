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

    public Dossier(String nom, Path path) {
        super(nom, path);
    }

    public Map<String, Integer> getNomEnfant() {
        return nomEnfant;
    }

    public void setNomEnfant() {
        List<String> tmpPathsList = contenu.stream().map(f -> f.getNom()).collect(Collectors.toList());
        for (int i = 0; i < (tmpPathsList.size()); i++) {
            nomEnfant.put(tmpPathsList.get(i), i);
        }
    }

    public boolean areAllChildrenEtatSet() {
        for (Fichier f : this.contenu) {
            if (f.getEtat() == Etat.UNDEFINED) {
                return false;
            }
        }
        return true;
    }

//    public void rempliNomEnfant() {
//        for (Fichier f : contenu) {
//            nomEnfant.put(f.getNom(), contenu.indexOf(f));
//        }
//    }

    public String getLastPathElement(Path path) {
        int nameCount = path.getNameCount();
        return path.getName(nameCount - 1).toString();
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

//    public void compareTopFolders(Dossier d) throws IOException {
//        for (Fichier enfant : this.contenu) {
//            if (d.nomEnfant.containsKey(enfant.getNom())) {
//                if (enfant.type() == d.contenu.get((d.nomEnfant.get(enfant.getNom()))).type()) {
//                    enfant.changeEtat(d.contenu.get(d.nomEnfant.get(enfant.getNom())));
//                } else {
//                    enfant.setEtat(Etat.ORPHAN);
//                }
//            } else {
//                if (enfant.type() == 'F') {
//                    enfant.setEtat(Etat.ORPHAN);
//                } else {
//                    ((Dossier) enfant).setAllChildrenOrphan();
//                }
//            }
//        }
//    }

    @Override
    public void changeEtat(Fichier fs) throws IOException {
        if (fs instanceof Dossier) {
            Dossier other = (Dossier) fs;
            setNomEnfant();
            other.setNomEnfant();
            for (Fichier fichier : this.contenu) {
                if ((!other.nomEnfant.containsKey(fichier.getNom()))) {
                    if (fichier.type() == 'D') {
                        ((Dossier) fichier).setAllChildrenOrphan(); // TODO ces trois lignes dans un func()
                    }
                    fichier.setEtat(Etat.ORPHAN);
                } else {
                    Fichier fCorrespondant = other.contenu.get(other.nomEnfant.get(fichier.getNom()));
                    if (fCorrespondant.type() != fichier.type()) {
                        if (fichier.type() == 'F') {
                            fichier.setEtat(Etat.ORPHAN);
                            other.setAllChildrenOrphan();           // TODO ces trois lignes dans un func()
                            other.setEtat(Etat.ORPHAN);
                        } else {
                            other.setEtat(Etat.ORPHAN);
                            ((Dossier) fichier).setAllChildrenOrphan();
                            fichier.setEtat(Etat.ORPHAN);
                        }
                    } else {
                        fichier.changeEtat(fCorrespondant);
                    }
                }
            }
            for (Fichier f : other.contenu) {
                if ((f.getEtat() == Etat.UNDEFINED)) {
                    if (f.type() == 'D') {
                        ((Dossier) f).setAllChildrenOrphan();
                    }
                    f.setEtat(Etat.ORPHAN);
                    other.setEtat(Etat.ORPHAN);
                }
            }
            if (this.areAllChildrenEtatSet() && other.areAllChildrenEtatSet()) {
                this.checkEmptyDir(other);
                this.setFolderEtat();
                other.setFolderEtat();
            }
        }
    }

    private void setFolderEtat() {
        if (this.toBeOrphan()) {
            this.setEtat(Etat.ORPHAN);
        } else if (this.toBeSame()) {
            this.setEtat(Etat.SAME);
        } else if (this.toBeOlder()) {
            this.setEtat(Etat.OLDER);
        } else if (this.toBeNewer()) {
            this.setEtat(Etat.NEWER);
        } else {
            this.setEtat(Etat.PARTIAL_SAME);
        }
    }

    public void checkEmptyDir(Dossier other) {
        if (this.getNom().equals(other.getNom())) {
            if (this.contenu.size() == 0 && other.contenu.size() == 0) {
                this.setEtat(Etat.SAME);
                other.setEtat(Etat.SAME);
            } else if (this.contenu.size() == 0) {
                this.setEtat(Etat.PARTIAL_SAME);
                other.setEtat(Etat.PARTIAL_SAME);
                other.setAllChildrenOrphan();
            } else if (other.contenu.size() == 0) {
                other.setEtat(Etat.PARTIAL_SAME);
                this.setEtat(Etat.PARTIAL_SAME);
                this.setAllChildrenOrphan();
            }
        }
    }

    private void setAllChildrenOrphan() {
        for (Fichier f : this.contenu) {
            if (f.type() == 'F' && f.getEtat() == Etat.UNDEFINED) {
                f.setEtat(Etat.ORPHAN);
//                String parent = f.getLastDirName(f.getPath());
//                for (Fichier fParent : this.contenu) {
//                    if (fParent.getNom().equals(parent)) {
//                        Dossier d = (Dossier) fParent;
//                        d.setAllChildrenOrphan();
//                    }
//                }
            } else if (f.type() == 'D' && f.getEtat() == Etat.UNDEFINED) {
                f.setEtat(Etat.ORPHAN);
                Dossier d = (Dossier) f;
                d.setAllChildrenOrphan();
            }
        }
        this.setEtat(Etat.ORPHAN);
    }

//    public void changeEtatDossier (Dossier d){
//                Etat tmpEtat = Etat.INDEFINED;
//                for(Fichier fichierDirA: this.contenu){
//                    for(Fichier fichierDirB  : d.contenu){
//                            if(fichierDirA.getEtat().equals(fichierDirB.getEtat())){
//                                if(this.getEtat().equals(Etat.SAME)){
//                                        tmpEtat=Etat.SAME;
//                                    }else if(this.getEtat().equals(Etat.PARTIAL_SAME)){
//                                        tmpEtat=Etat.PARTIAL_SAME;
//                                    }else if(this.getEtat().equals(Etat.ORPHAN)){
//                                        tmpEtat=Etat.ORPHAN;
//
//                                }
//                            }
//                    }
//                }
//    }

    public boolean toBeOrphan() {
//        if(this.contenu.size() != 0) {
        for (Fichier f : this.contenu) {
            if (f.getEtat() != Etat.ORPHAN) {
                return false;
            }
        }
//        }
        return true;
    }

    public boolean toBeSame() {
        for (Fichier f : this.contenu) {
            if (f.getEtat() != Etat.SAME) {
                return false;
            }
        }
        return true;
    }

    //
//    public boolean toBePartialSame() {
//        for (Fichier f : this.contenu) {
//            if (f.getEtat() != Etat.ORPHAN) {
//                return false;
//            }
//        }
//        return true;
//    }

    public boolean toBeOlder() {
        int fOlder = 0;
        for (Fichier f : this.contenu) {
            if (f.getEtat() != Etat.OLDER && f.getEtat() != Etat.SAME) {
                return false;
            } else if (f.getEtat() == Etat.OLDER) {
                fOlder++;
            }
        }
        return fOlder > 0;
    }

    public boolean toBeNewer() {
        int fNewer = 0;
        for (Fichier f : this.contenu) {
            if (f.getEtat() != Etat.NEWER && f.getEtat() != Etat.SAME) {
                return false;
            } else if (f.getEtat() == Etat.NEWER) {
                fNewer++;
            }
        }
        return fNewer > 0;
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

