public class Paneles{
  private  String nom,ip,tipo,frec;

    public Paneles(String nom, String ip,String frec, String tipo){
        this.nom=nom;
        this.ip=ip;
        this.frec=frec;
        this.tipo=tipo;
    }

public String get_tipo(){
    return tipo;
}
public String get_frec(){
    return frec;
}
public String get_nom(){
    return nom;
}
public String get_ip(){
    return ip;
}
}
