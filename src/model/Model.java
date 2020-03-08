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

    public void foldersOnly() {
        System.out.println("avant le for");
        for(Fichier f : dirRight.getContenu()) {
            if(f.isDirectory()) {
                f.selected = true;
            }
        }
    }
}
