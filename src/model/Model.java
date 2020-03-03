package model;

import java.io.IOException;
import java.nio.file.Paths;

public class Model {

    private Fichier dirLeft;
    private Fichier dirRight;

    public Model(String rootLeft, String rootRight) throws IOException {
        dirLeft = new CopyBuilder().build(Paths.get(rootLeft));
        dirRight = new CopyBuilder().build(Paths.get(rootRight));
        dirLeft.changeEtat(dirRight);
    }

    public Fichier getDirLeft() {
        return dirLeft;
    }

    public Fichier getDirRight() {
        return dirRight;
    }
}
