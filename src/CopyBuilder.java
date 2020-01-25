import java.io.File;
import java.nio.file.Path;
import java.util.Date;

public  class CopyBuilder{
    private final String nom;
    private Date modifDate;
    private boolean typeDossier;
    private Path path;


    public CopyBuilder(String nom){
        this.nom = nom;
    }



      public Fichier make(String path) {
        //Path p = Paths.get(path);
          File file = new File(path);
          this(file.getName());

        return ;
    }



}
