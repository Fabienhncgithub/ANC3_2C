package model;

import java.io.IOException;
import java.nio.file.Paths;

public class Model {

    private static final String INIT_DATA_L = "TestBC/RootBC_Left";
    private static final String INIT_DATA_R = "TestBC/RootBC_Right";

    private Fichier dirLeft;
    private Fichier dirRight;

    public Model() throws IOException {
        dirLeft = new CopyBuilder().build(Paths.get(INIT_DATA_L));
        dirRight = new CopyBuilder().build(Paths.get(INIT_DATA_R));
        dirLeft.changeEtat(dirRight);
    }

    public Fichier getDirLeft() {
        return dirLeft;
    }

    public Fichier getDirRight() {
        return dirRight;
    }

    public void foldersOnlySet(){
        foldersOnly(dirLeft);
        foldersOnly(dirRight);
    }

    public void foldersOnly(Fichier dir) {
        for(Fichier f : dir.getContenu()) {
            if(!f.isDirectory()) {
                f.selected = false;
            }
            else{
                foldersOnly(f);
            }
        }

        for(Fichier f : dirLeft.getContenu()){
            if(!f.isDirectory()){
                f.selected = false;
            }
        }
    }

    public void same() {
        for(Fichier f : dirRight.getContenu()) {
            if(f.getEtat() != Etat.SAME) {
                f.selected = false;
            }
        }
        for(Fichier f : dirLeft.getContenu()){
            if(f.getEtat() != Etat.SAME) {
                f.selected = false;
            }
        }
    }

    public void orphan() {
        for(Fichier f : dirRight.getContenu()) {
            if(f.getEtat() != Etat.ORPHAN) {
                f.selected = false;
            }
        }
        for(Fichier f : dirLeft.getContenu()){
            if(f.getEtat() != Etat.ORPHAN) {
                f.selected = false;
            }
        }
    }

    public void newerRight() {
        for(Fichier f : dirRight.getContenu()) {
            if(f.getEtat() != Etat.NEWER) {
                f.selected = false;
            }
        }
    }

    public void newerLeft() {
        for(Fichier f : dirLeft.getContenu()) {
            if(f.getEtat() != Etat.NEWER) {
                f.selected = false;
            }
        }
    }
}
