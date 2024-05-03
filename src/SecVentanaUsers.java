import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.security.auth.kerberos.DelegationPermission;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Checkbox;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import com.jcraft.jsch.JSchException;

class SecVentanaUsers extends JFrame{
    Border loweredbevel = BorderFactory.createLoweredBevelBorder();
    Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
    Border raisedbevel = BorderFactory.createRaisedBevelBorder();
final private Font tit = new Font("Berlin Sans FB", Font.PLAIN, 30);
final private Font tit2 = new Font("Berlin Sans FB", Font.PLAIN, 20);
JFrame Wuser= new JFrame();
Command ssh = new Command();
Boolean checkbox;
JTextField tssid,thz;
JLabel enom,ehz,essid,ethz;
JButton next;
String result,frec,ssid,ip,nom,numhz;
JPanel panelcenter,panelnorth,panelsouth;
List<String>paneles;
Boolean check;
JCheckBox marcada;
String panel;
public SecVentanaUsers(String ip,String nombre,List<String>paneles){
    this.paneles=paneles;
    nom=nombre;
    this.ip=ip;
Wuser.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
Wuser.setSize(450,500);
Wuser.setVisible(true);
Wuser.setResizable(false);
Wuser.setLocationRelativeTo(null);
Wuser.setBackground(new Color(28, 40, 51));
Wuser.setLayout(new BorderLayout());
initComponent();
}
private void initComponent() {
try {
    result=ssh.FrecuenciaySsid(ip);
} catch (InterruptedException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
} catch (JSchException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
}

frec=result.substring(result.indexOf("=")+1,result.indexOf("\n"));
result=result.substring(result.indexOf("ssid"), result.length());
ssid=result.substring(result.indexOf("=")+1, result.indexOf("\n"));
result=result.substring(result.indexOf("channels"),result.length());
numhz=result.substring(result.indexOf("=")+1,result.length());
numhz=numhz.replaceAll(" ", "");
numhz=numhz.substring(0,3);
inicializarpaneles();
ColocarTitulo(nom);
ColocarTextos();
colocarBotones();
colocarEtiquetas();
ColocarCheckBox();
}
private void ColocarCheckBox() {
    frec=frec.replaceAll(" ", "");
   
check= !frec.contains("disabled");

    marcada= new JCheckBox("frecuencia",check);
    marcada.setBorderPaintedFlat(true);
    marcada.setBounds(170, 218, 20, 20);
    marcada.setBackground(new Color( 17, 122, 101 ));
    checkbox=false;
   panelcenter.add(marcada);
   marcada.addItemListener(new ItemListener(){

    @Override
    public void itemStateChanged(ItemEvent e) {
       if( e.getStateChange() == ItemEvent.SELECTED){
        thz.setVisible(true);
        ethz.setVisible(true);
      
        thz.setText(numhz);
        checkbox=true;
       }else if(e.getStateChange() == ItemEvent.DESELECTED){
        thz.setVisible(false);
        ethz.setVisible(false);
        thz.setText("");
        checkbox=false;
       }
        
    }
    
   });
}
private void colocarEtiquetas() {
    Color colore= new Color(52, 152, 219);
ethz= new JLabel();
    ehz= new JLabel("Frecuencia:");
    ehz.setForeground(Color.white);
    essid= new JLabel();
    AtributosdeEtiquetas(ethz, "Frecuencia", 20, 250, colore);
  
    ethz.setVisible(false);

  
    
    
    AtributosdeEtiquetas(essid, "Seleccione SSID", 20, 60, colore);
    panelcenter.add(essid);
    ehz.setSize(170, 150);
    ehz.setBounds(20,150,170,150);
  ehz.setFont(tit);
    panelcenter.add(ehz);
    panelcenter.add(ethz);

}
private void AtributosdeEtiquetas(JLabel e,String tx,int posx,int posy, Color c) {
        
    TitledBorder title = BorderFactory.createTitledBorder(
            loweredetched, tx, TitledBorder.LEADING, TitledBorder.TOP, tit2, c);
    
    title.setTitleJustification(TitledBorder.LEFT);

    e.setBounds(posx, posy, 390, 70);
    e.setBackground(c);
    e.setBorder(title);

}
private void colocarBotones() {
next = new JButton();
confbotones(next, "next.png", 35);
accionBoton(next);
panelsouth.add(next);
}
private void accionBoton(JButton b) {
    b.addActionListener(new ActionListener(){

        @Override
        public void actionPerformed(ActionEvent e) {
          
           
                if(!checkbox){
                    
                    ssh.FijarSSID(ip, panel);
                    JOptionPane.showMessageDialog(null, "Se ha modificado los parametros","CAMBIO DE PARAMETROS", JOptionPane.INFORMATION_MESSAGE);
                }else{
                   
                  
                   ssh.FrecuenciaySSid(ip, panel, thz.getText().toString());
                   JOptionPane.showMessageDialog(null, "Se ha modificado los parametros","CAMBIO DE PARAMETROS", JOptionPane.INFORMATION_MESSAGE);
                }
               
            
        }
        
    });
    
}
private void confbotones(JButton b,String p, int d) {
    b.setVisible(true);
b.setEnabled(true);
b.setPreferredSize(new Dimension(80,50));
ImageIcon img3 = new ImageIcon(p);
b.setIcon(new ImageIcon(img3.getImage().getScaledInstance(d, d, Image.SCALE_SMOOTH)));
b.setContentAreaFilled(false);
b.setBorderPainted(false);
b.setFocusPainted(false);

b.setPressedIcon(new ImageIcon(img3.getImage().getScaledInstance(d, d, Image.SCALE_SMOOTH)));
b.setRolloverIcon(new ImageIcon(img3.getImage().getScaledInstance(d + 5, d + 5, Image.SCALE_SMOOTH)));
b.setVerticalAlignment(SwingConstants.CENTER);
}
private void inicializarpaneles() {
    panelcenter= new JPanel();
    panelnorth=new JPanel();
    panelsouth= new JPanel();
    
    panelnorth.setPreferredSize(new Dimension(450,50));
    
    panelcenter.setBackground(new Color (22, 21, 28));
    panelcenter.setLayout(null);
    panelsouth.setBackground(new Color (22, 21, 28));
    panelnorth.setBackground(new Color (14, 102, 85));
    panelsouth.setPreferredSize(new Dimension(450,55));
    Wuser.add(panelcenter,BorderLayout.CENTER);
    Wuser.add(panelnorth,BorderLayout.NORTH);
    Wuser.add(panelsouth,BorderLayout.SOUTH);
   
}

private void ColocarTextos() {
    String pan[]= new String[paneles.size()];
    for(int i=0;i<paneles.size();i++){
        pan[i]=paneles.get(i);
    }
    JComboBox <String> despl= new JComboBox<>(pan);
    despl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    despl.setBounds(30,80, 370, 30);
    despl.setVisible(true);
    despl.setBackground(new Color(52, 152, 219));
    despl.setFont(tit2);
    despl.setBorder(null);
    ((JLabel)despl.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
    despl.addItemListener(new ItemListener(){

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
               panel=(e.getItem().toString());
            }
        }

    });
tssid= new JTextField();
thz= new JTextField();
AtributosdeTextos(thz, 30, 270);

    thz.setVisible(false);
    thz.setText(numhz);


AtributosdeTextos(tssid, 30, 80);
thz.addKeyListener(new KeyListener(){

    @Override
    public void keyTyped(KeyEvent e) {
        if(!Character.isDigit(e.getKeyChar()) || thz.getText().length()>4){
            e.consume();
          }
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
       
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }
    
});
panelcenter.add(thz);
panelcenter.add(despl);
}
private void ColocarTitulo(String nom2) {
    
        enom= new JLabel();
        enom.setText(nom2);
        enom.setForeground(Color.white);
        enom.setFont(tit);
        panelnorth.add(enom);
        panelnorth.setAlignmentY(SwingConstants.CENTER);
    }


    private void AtributosdeTextos(JTextField t,int posx,int posy){
        t.setBounds(posx, posy, 370, 40);
        t.setBorder(null);
        t.setFont(new Font("Berlin Sans FB", Font.PLAIN, 30));
        t.setAlignmentX(SwingConstants.CENTER);
        t.setEditable(true);
        t.setHorizontalAlignment(SwingConstants.CENTER);
        t.setBackground(new Color(31, 97, 141));
        t.setForeground(Color.white);

    }
}







