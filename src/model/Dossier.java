package model;

import javafx.beans.Observable;
import javafx.beans.binding.ObjectBinding;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Dossier extends Fichier {
    private final SizeBinding sizeBinding = new SizeBinding();
    private final DateTimeBinding dateTimeBinding = new DateTimeBinding();
    List<String> listNames = null;
    private Map<String, Integer> nomEnfant = new TreeMap<>();
    private List<Fichier> contenu = new ArrayList<>();

    public Dossier(String nom, Path path) {
        super(nom, path);
    }

    public List<String> getFileNamesContenu() {
        for (Fichier f : contenu) {
          listNames.add(f.getName());
        }
        return Collections.unmodifiableList(listNames);
    }

    public void setNomEnfant() {
        List<String> tmpPathsList = contenu.stream().map(f -> f.getName()).collect(Collectors.toList());
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

    public String getLastPathElement(Path path) {
        int nameCount = path.getNameCount();
        return path.getName(nameCount - 1).toString();
    }

    @Override
    public List<Fichier> getContenu() {
        return contenu;
    }

//    @Override
//    public char type() { //supprimer cette methode et gerer le char dans l'affichage.
//        return 'D'; // D pour Dossier
//    }

    @Override
    public boolean isDirectory() {
        return true;
    }

    @Override
    public long size() {
        long result = 0;
        for (Fichier f : contenu) {
            result += f.size();
        }
        return result;
    }

    @Override
    public void addFile(Fichier file) {
        // Taille et date dépendent des tailles et dates des enfants
        addToSizeBinding(file.sizeProperty());
        addToDateTimeBinding(file.dateTimeProperty());
        _addFile(file);
    }

    // Ajoute une Observable dont dépend le Binding et provoque un recalcul
    private void addToSizeBinding(javafx.beans.Observable obs) {
        sizeBinding.addBinding(obs);
        sizeBinding.invalidate();
    }


    @Override
    protected String formatAffichage(int decalage) throws IOException {
        StringBuilder res = new StringBuilder();
        res.append(super.formatAffichage(decalage))
                .append(getName())
                .append(" - type : ").append("D") //(this.isDirectory() ? "D" : "F")
                .append(" - date : ").append(getModifDate(this.getPath()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
                .append(" - size : ").append(size())
                .append(" - etat : ").append(getEtat())
                .append(" - contient : ").append("\n");
        for (Fichier f : contenu)
            res.append(f.formatAffichage(decalage + 1));
        return res.toString();
    }

    @Override
    public void changeEtat(Fichier fs) throws IOException {
        if (fs.isDirectory()) { // remplacer ce code par fd.isDirectory()
            Dossier other = (Dossier) fs;
            setNomEnfant();
            other.setNomEnfant();
            for (Fichier fichier : this.contenu) {
                if ((!other.nomEnfant.containsKey(fichier.getName()))) {
                    if (fichier.isDirectory()) {
                        ((Dossier) fichier).setAllChildrenOrphan(); // TODO ces trois lignes dans un func()
                    }
                    fichier.setEtat(Etat.ORPHAN);
                } else {
                    Fichier fCorrespondant = other.contenu.get(other.nomEnfant.get(fichier.getName()));
                    if (fCorrespondant.isDirectory() != fichier.isDirectory()) {
                        if (!fichier.isDirectory()) {
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
                if ((f.getEtat() == Etat.UNDEFINED)) { //Mettre PARTIAL_SAME comme etat de base cela va permettre de faire un teste en moin.
                    if (f.isDirectory()) {
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
            this.setEtat(Etat.PARTIAL_SAME); //ce teste peut être enlever si on met PARTIAL_SAME comme valeure de base
        }
    }

    public void checkEmptyDir(Dossier other) {
        if (this.getName().equals(other.getName())) {
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
            if (!f.isDirectory() && f.getEtat() == Etat.UNDEFINED) {
                f.setEtat(Etat.ORPHAN);
            } else if (f.isDirectory() && f.getEtat() == Etat.UNDEFINED) { //changer f.type() == 'D' par f.isDirectory()
                f.setEtat(Etat.ORPHAN);
                Dossier d = (Dossier) f;
                d.setAllChildrenOrphan();
            }
        }
        this.setEtat(Etat.ORPHAN);
    }

    public boolean toBeOrphan() {
        for (Fichier f : this.contenu) {
            if (f.getEtat() != Etat.ORPHAN) {
                return false;
            }
        }
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

    // Ajoute une Observable dont dépend le Binding et provoque un recalcul
    private void addToDateTimeBinding(Observable obs) {
        dateTimeBinding.addBinding(obs);
        dateTimeBinding.invalidate();
    }

    // Un Binding pour le recalcul de la taille
    private class SizeBinding extends ObjectBinding<Long> {

        @Override // La taille est la sommme des taille des enfants
        protected Long computeValue() {
            return getChildren().stream().map(f -> f.getValue().getSize()).reduce(0L, (s1, s2) -> s1 + s2);
        }

        // Chaque Observable modifiée provoque un recalcul
        // Ajoute une Observable dont dépend le Binding
        void addBinding(Observable obs) {
            super.bind(obs);
        }

    }

    // Un Binding pour le recalcul de la date
    private class DateTimeBinding extends ObjectBinding<LocalDateTime> {

        @Override // La date est la plus récente des dates des enfants ou la date actuelle
        protected LocalDateTime computeValue() {
            return getChildren().isEmpty() ? LocalDateTime.now() : getChildren().stream().map(f -> f.getValue().getDateTime()).max(LocalDateTime::compareTo).get();
        }

        // Chaque Observable modifiée provoque un recalcul
        // Ajoute une Observable dont dépend le Binding
        void addBinding(Observable obs) {
            super.bind(obs);
        }

    }
}

