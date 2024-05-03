import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
public class Distritos {
    List<Paneles> paneles;
List <String> Text,r;
String cadena,nom,ip,tipo,second,frec;
int pos,id;
    public Distritos(String dir,String nom) throws IOException{
       paneles=new ArrayList<>();
        this.nom=nom;
        Text= new ArrayList<>();
        Path path = Path.of(dir);
        Text = Files.readAllLines(path);
  
        for(int i=0;i<Text.size();i++){
            cadena=Text.get(i);
            pos=cadena.indexOf(",");
            nom=cadena.substring(0,pos);
            second=cadena.substring(pos+1,cadena.length());
            pos=second.indexOf(",");
            ip=second.substring(0,pos);
            second=second.substring(pos+1,second.length());
            pos=second.indexOf(",");
            frec=second.substring(0, pos);
            tipo=second.substring (pos+1,second.length());
            Paneles pan= new Paneles(nom, ip,frec,tipo);
            paneles.add(pan);
        }
    }
   
    
    public String  get_nom(){
        return nom;
    }
    public List<Paneles> get_Paneles(){
        return paneles;
    }
    public void Mostrar_paneles(){
        for(Paneles n :paneles){
            System.out.println(n.get_nom());
        }
    }
}
