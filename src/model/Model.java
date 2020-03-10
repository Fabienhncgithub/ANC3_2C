package model;

import java.io.IOException;
import java.nio.file.Paths;

public class Model {

    private static final String INIT_DATA_L = "TestBC/RootBC_Left";
    private static final String INIT_DATA_R = "TestBC/RootBC_Right";

    private Fichier dirLeft;
    private Fichier dirRight;

    private Fichier currentDirLeft;
    private Fichier currentDirRight;

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

    public void setDirLeft(Fichier dirLeft) {
        this.dirLeft = dirLeft;
    }

    public void setDirRight(Fichier dirRight) {
        this.dirRight = dirRight;
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
    }

    public void sameSet(){
        same(dirLeft);
        same(dirRight);
    }

    public void same(Fichier dir) {
        for(Fichier f : dir.getContenu()) {
            if(f.getEtat() != Etat.SAME) {
                f.selected = false;
            }else {
                dir.selected = true;
            }
            if (f.isDirectory()) {
                same(f);
            }
        }
    }

    public void orphanSet(){
        orphan(dirLeft);
        orphan(dirRight);
    }

    public void orphan(Fichier dir) {
        for(Fichier f : dir.getContenu()) {
            if(f.getEtat() != Etat.ORPHAN) {
                f.selected = false;
            }else {
                dir.selected = true;
            }
            if (f.isDirectory()) {
                same(f);
            }
        }
    }

    public void newerRightSet(){
        newerRight(dirRight);
    }

    public void newerRight(Fichier dir) {
        for(Fichier f : dir.getContenu()) {
            if(f.getEtat() != Etat.NEWER) {
                f.selected = false;
            }else {
                dir.selected = true;
            }
            if (f.isDirectory()) {
                same(f);
            }
        }
    }

    public void newerLeftSet(){
        newerLeft(dirLeft);
    }

    public void newerLeft(Fichier dir) {
        for(Fichier f : dir.getContenu()) {
            if(f.getEtat() != Etat.NEWER) {
                f.selected = false;
            }else {
                dir.selected = true;
            }
            if (f.isDirectory()) {
                same(f);
            }
        }
    }

    public void showAll() {
        setAll(dirLeft);
        setAll(dirRight);
    }

    private void setAll(Fichier dir) {
        for(Fichier f : dir.getContenu()) {
            f.selected = true;
            if (f.isDirectory()) {
                setAll(f);
            }
        }
    }
}
