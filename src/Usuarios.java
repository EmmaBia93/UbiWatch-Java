import javax.swing.JFrame;

public class Usuarios extends JFrame{
  private String nombre,ip,mac,time;  
public Usuarios(String nom, String ip, String mac){
    nombre=nom;
    this.ip=ip;
    this.mac=mac;
}
public void set_Time(String time){
    this.time=time;

}
public String get_Time(){
    return time;
}
public String get_nomU(){
    return nombre;
}
public String get_ipU(){
    return ip;
}

public String get_macU(){
    return mac;
}





}
