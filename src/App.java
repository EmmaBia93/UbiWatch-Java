import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.MouseInputListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.StyledEditorKit.BoldAction;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.awt.*;

import javax.naming.ldap.SortKey;
import javax.naming.ldap.*;
import javax.swing.*;

public class App extends JFrame {
    // JTabbedPane tabla;
    int dist;
    JLabel cantclientes;
    JLabel eti= new JLabel();
    JButton frecBtn=new JButton();
    JButton cancel= new JButton();
    JButton maxi= new JButton();
    JButton mini= new JButton();
    JButton reinicio = new JButton("REBOOT");
    Command command = new Command();
    JButton back = new JButton("BACKUP");
    JButton info = new JButton("INFO");
    final private Font titu = new Font("Oswald", Font.PLAIN, 20);
    final private Font ti = new Font("Hack NFM", Font.PLAIN, 10);

    Border loweredbevel = BorderFactory.createLoweredBevelBorder();
    Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
    Border raisedbevel = BorderFactory.createRaisedBevelBorder();
    CompoundBorder compound = BorderFactory.createCompoundBorder(
            raisedbevel, loweredbevel);
    DefaultTableModel modelotabla;
    List<Distritos> dis = new ArrayList<>();
    List<Distritos> plat=new ArrayList<>();
    TableRowSorter<DefaultTableModel> sorter;
    Distritos disc;
    JTable tab;
    JLabel titulo;
    JTextField Frectxt = new JTextField();
    JButton lb, ch, ca, co, md, tr,enlaces,estado,state,carp,frec;
    JFrame cont = new JFrame();
    JPanel p1, p2, p3, p4, tit, pr1, pr2;
    JScrollPane scroll;
    Object columnas[] = { "NOMBRE", "IP", "ESTADO", "CLIENTES","HORAS ACTIVO","CABLE","FRECUENCIA", "REINICIO", "BACKUP"};
   List< String> paneles;
   List<String> platos;
    JLabel et;
    Boolean bandera=true;
    Boolean flag=false;
    final private Font tittab= new Font("Oswald", Font.PLAIN, 15);
    public App() throws IOException {
        reinicio.setName("re");
        back.setName("ba");
        
        disc = new Distritos("LosBerrosPa.txt", "Los Berros");

        dis.add(disc);
        disc = new Distritos("MediaAguaPa.txt", "Media Agua");
        dis.add(disc);
        disc = new Distritos("ColoniaPa.txt", "Colonia");
        dis.add(disc);
        disc = new Distritos("CochagualPa.txt", "Cochagual");
        dis.add(disc);
        disc = new Distritos("TresEsquinasPa.txt", "Tres Esquinas");
        dis.add(disc);
        disc = new Distritos("CanadaPa.txt", "Cañada");
        dis.add(disc);
        disc = new Distritos("Carpinteria.txt", "Carpinteria");
        dis.add(disc);
        disc= new Distritos("Enlaces.txt", "Todos");
        dis.add(disc);
       
        cont.setSize(1100, 720);
        cont.setLocationRelativeTo(null);
        cont.setResizable(true);
        cont.setLayout(new BorderLayout(0, 0));
        cont.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cont.setVisible(true);
        
        initComponets();
        this.repaint();
      cont.setExtendedState(6);
    }

    private void initComponets() throws IOException {
        
        p2 = new JPanel();
        p1 = new JPanel();
        tit = new JPanel();
        tit.setLayout(new BorderLayout());
        p1.setLayout(new GridLayout(10, 3, 5, 5));
        p1.setPreferredSize(new Dimension(250, 600));
        tit.setPreferredSize(new Dimension(850, 50));
        p1.setBackground(new Color(29, 29, 30));
        tit.setBackground(new Color(0, 0, 0));

        p2.setLayout(new BorderLayout(0, 0));
        p2.setBackground(new Color(47, 27, 54));

        cont.add(p2, BorderLayout.CENTER);
        cont.add(tit, BorderLayout.NORTH);
        cont.add(p1, BorderLayout.WEST);

        cont.repaint();
        // crearTabbed();
        Botones();
        ColocarTitulo();
      
       
        confBotones(reinicio, new Color(236, 112, 99));
        confBotones(back, new Color(36, 113, 163));
        confBotones(info, new Color(125, 60, 152));
        ConfBotondeEnlaces();
        Colocartabla();
       // ConfBotondeEnlaces();
        cont.repaint();
    }

    private void ConfBotondeEnlaces() {
        Frectxt.setText("");
        enlaces.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e) {
              bandera=false;
                flag=true;
                sorter.setRowFilter(RowFilter.regexFilter(""));
                limpiartabla();
                Frectxt.setText("");
                List<Paneles> res = new ArrayList<>();
                res = dis.get(7).get_Paneles();
                String da;
                List<Command> s = new ArrayList<>(res.size());
                Frectxt.setVisible(true);
               int num;
               cantclientes.setVisible(false);
                for (int j = 0; j < res.size(); j++) {
                    try {
                      

                        if (EsAlcanzable(res.get(j).get_ip())) {
                           // command.set_ip(res.get(j).get_ip());
                                                     // command.run();
                           if(state.getName()=="Detallado"){
                            modelotabla.addRow(new Object[] { res.get(j).get_nom(), res.get(j).get_ip(), "Conectado",
                            "-",command.HoraEnlaces(res.get(j).get_ip(), res.get(j).get_tipo()),"-",res.get(j).get_frec(), reinicio, back});
                             
                           }else{
                            modelotabla.addRow(new Object[] { res.get(j).get_nom(), res.get(j).get_ip(), "Conectado",
                            "-","-","-" ,res.get(j).get_frec(), reinicio, back});
                           }
                          
                              
                        } else {
                            modelotabla.addRow(new Object[] { res.get(j).get_nom(), res.get(j).get_ip(), "Desconectado",
                                    "-","-","-",res.get(j).get_frec(), reinicio, back});
                                  
                        }

                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (JSchException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (InterruptedException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            
                
            }

        });
    }

    private void confBotones(JButton b, Color col) {

        b.setBorder(compound);
        b.setForeground(Color.white);
        b.setFocusPainted(false);
        b.setBackground(col);
        b.setVerticalAlignment(SwingConstants.CENTER);
    }
    public Boolean EsAlcanzable(String ip) throws IOException {
        InetAddress geek = InetAddress.getByName(ip);
        return geek.isReachable(100);
    }

    private void Colocartabla() throws IOException {
        modelotabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
       
        tab = new JTable(modelotabla);
        

        tab.setDefaultRenderer(Object.class, new MiRenderer());
       
        tab.setFont(new Font("Oswald", Font.PLAIN, 20));
        tab.setRowHeight(30);
        TableColumnModel columnModel = tab.getColumnModel();
        tab.getTableHeader().setFont(tittab);
       tab.getTableHeader().setBackground(new Color(36, 113, 163));
       tab.getTableHeader().setForeground(Color.white);
       tab.getTableHeader().setPreferredSize(new Dimension(10,20));
       tab.setAutoCreateRowSorter(true);
       Frectxt.setText("");
      sorter= new TableRowSorter<>(modelotabla);
    
      tab.setRowSorter(sorter);
        columnModel.getColumn(0).setPreferredWidth(250);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(3).setPreferredWidth(20);
        columnModel.getColumn(4).setPreferredWidth(80);
        columnModel.getColumn(5).setPreferredWidth(20);
        columnModel.getColumn(6).setPreferredWidth(80);
        columnModel.getColumn(7).setPreferredWidth(20);
        columnModel.getColumn(8).setPreferredWidth(20);
      scroll = new JScrollPane(tab);
        tab.setFillsViewportHeight(true); 
      
        scroll.getViewport().setBackground(new Color (28, 40, 51));    
        scroll.setVisible(true);
        tab.setBackground(new Color (22, 21, 28));
        tab.setForeground(new Color(40, 55, 71));
        scroll.setBorder(null);
        tab.setBorder(null);
        tab.getTableHeader().setReorderingAllowed(false) ;
        tab.setSelectionBackground(new Color(40, 116, 166));
        tab.setSelectionForeground(Color.white);
        EventosdeTabla();
     
        p2.add(scroll, BorderLayout.CENTER);
      // setClassifierFilter();

    }
    

    private void Botones() {
        lb = new JButton();
        md = new JButton();
        co = new JButton();
        ch = new JButton();
        tr = new JButton();
        ca = new JButton();
        carp= new JButton();
        estado= new JButton();
        enlaces= new JButton();
      state = new JButton();
      state.setName("Rapido");
      Frectxt.setText("");
      state.addActionListener(new ActionListener(){

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if(state.getName()=="Rapido"){
                state.setName("Detallado");
                state.setText("Detallado");
                state.setBackground(new Color(230, 126, 34));
            }else{
                state.setName("Rapido");
                state.setText("Rapido");
                state.setBackground(new Color(40, 180, 99));
            }
        }

       });
       ColocarBotones(state, "Rapido");
       state.setForeground(new Color(0,0,0));
        ColocarBotones(md, "Media Agua");
        ColocarBotones(lb, "Los Berros");
        ColocarBotones(co, "Colonia");
        ColocarBotones(ch, "Cochagual");
        ColocarBotones(tr, "Tres Esquinas");
        ColocarBotones(ca, "Cañada");
        ColocarBotones(carp, "Carpinteria");
        ColocarBotones(estado, "Estado General");
        ColocarBotones(enlaces, "Enlaces");
       
       
        state.setBackground(new Color(40, 180, 99));
        AccionesdeBotones(estado);
        AccionesdeBotones(lb);
        AccionesdeBotones(md);
        AccionesdeBotones(co);
        AccionesdeBotones(ch);
        AccionesdeBotones(tr);
        AccionesdeBotones(ca);
        AccionesdeBotones(carp);
        AccionesdeBotones(enlaces);
        TaresdeBotones(lb, 0);
        TaresdeBotones(md, 1);
        TaresdeBotones(co, 2);
        TaresdeBotones(ch, 3);
        TaresdeBotones(tr, 4);
        TaresdeBotones(ca, 5);
        TaresdeBotones(carp, 6);
        TareadeEstado(estado);

        //confBotones(cancel, "cancel.png", 1035, 5, 20);
        //confBotones(maxi, "maximize.png", 1000, 5, 20);
        //confBotones(mini,"minimizar.png",1000,5,20);
        //mini.setVisible(false);
        //tit.add(mini);
        //tit.add(cancel);
        //tit.add(maxi);
       state.setVisible(true);

      
        cancel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                cont.setVisible(false);
                cont.dispose();
                
            }
            
        });
        maxi.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
               cont.setExtendedState(JFrame.MAXIMIZED_BOTH);
                maxi.setVisible(false);
                mini.setVisible(true);
            }
            
        });
        mini.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
               
                cont.setSize(1100, 720);
                cont.setLocationRelativeTo(null);
                mini.setVisible(false);
                maxi.setVisible(true);
            }
            
        });

    }

    private void TareadeEstado(JButton es) {
     
        Frectxt.setText("");
        es.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiartabla();
                Frectxt.setText("BÚSQUEDA");
                cantclientes.setVisible(false);
                sorter.setRowFilter(RowFilter.regexFilter(""));
                List<Paneles> res = new ArrayList<>();
                for(int i=0;i<dis.size()-1;i++){
                    res=dis.get(i).get_Paneles();
                    bandera=false;
                    for(int j=0;j<res.size();j++){
                        try {
                         
                            if (EsAlcanzable(res.get(j).get_ip())){
                                
                                modelotabla.addRow(new Object[] { res.get(j).get_nom(), res.get(j).get_ip(), "Conectado",
                                "-", "-","-" ,res.get(j).get_frec(), reinicio,back});
                                
                         
                    } else {
                        modelotabla.addRow(new Object[] { res.get(j).get_nom(), res.get(j).get_ip(), "Desconectado",
                                "-","-","-",res.get(j).get_frec(), reinicio,back});
                                
                    }
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    }
                }
                
            }
            
        });
    }

    private void confBotones(JButton b, String p, int d,int big) {
       
      
        ImageIcon img3 = new ImageIcon(p);
        b.setIcon(new ImageIcon(img3.getImage().getScaledInstance(d, d, Image.SCALE_SMOOTH)));
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        b.setPressedIcon(new ImageIcon(img3.getImage().getScaledInstance(d, d, Image.SCALE_SMOOTH)));
        b.setRolloverIcon(new ImageIcon(img3.getImage().getScaledInstance(d +5, d + 5, Image.SCALE_SMOOTH)));
        b.setVerticalAlignment(SwingConstants.CENTER);
    }
    private void AccionesdeBotones(JButton btn) {
        btn.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                btn.setBackground(new Color(36, 113, 163));
                btn.setForeground(new Color(204, 209, 209));

            }

            @Override
            public void focusLost(FocusEvent e) {
                btn.setBackground(new Color(28, 40, 51));
                 btn.setForeground(new Color(204, 209, 209));

            }

        });
    }

    private void limpiartabla() {

        int a = modelotabla.getRowCount() - 1;

        for (int i = a; i >= 0; i--) {

            modelotabla.removeRow(i);
        }

    }
    public void setClassifierFilter() {
        if(flag){
            TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tab.getModel());
            tab.setRowSorter(sorter);
       
       List<RowSorter.SortKey> sortKeys = new ArrayList<>();
       sortKeys.add(new RowSorter.SortKey(4, SortOrder.ASCENDING));
       
       sorter.setSortKeys(sortKeys);
        }
      
     
      
      }
    private void ColocarBotones(JButton btn, String t) {
        btn.setVisible(true);
        btn.setBackground(new Color(28, 40, 51));
        btn.setForeground(new Color(204, 209, 209));
        btn.setText(t);
        btn.setFocusPainted(false);
        btn.setSelected(false);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btn.setFont(new Font("Berlin Sans FB", Font.PLAIN, 20));
        btn.setBorder(null);
        btn.setRolloverEnabled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        p1.add(btn);

    }

    private void ColocarTitulo() {
   //  confBotones(frecBtn, "binocular.png", 27, 11);
        titulo = new JLabel();
        titulo.setText("                                                  CIUDAD INTERNET FIBRA");
        titulo.setForeground(new Color(93, 173, 226));
        titulo.setFont(new Font("Deep Shadow", Font.PLAIN, 25));
       cantclientes= new JLabel();
       cantclientes.setForeground(Color.white);
       cantclientes.setFont(new Font("Oswald", Font.PLAIN, 20));
       //tit.add(frecBtn);
      // frecBtn.setVisible(false);
      Frectxt.setBounds(20, 10, 200, 30);
    
      Frectxt.setEditable(true);
      Frectxt.setFont(titu);
      Frectxt.setHorizontalAlignment(SwingConstants.CENTER);
      Frectxt.setBorder(compound);
      Frectxt.setBackground(new Color(   27, 38, 49  ));
      Frectxt.setForeground(new Color(  213, 216, 220 ));
      tit.add(Frectxt);
      Frectxt.addKeyListener(new KeyListener() {

        @Override
        public void keyTyped(KeyEvent e) {
            char car = e.getKeyChar();
            if((car<'0' || car>'9') && (car<'.' || car>'.')) e.consume();
        }

        @Override
        public void keyPressed(KeyEvent e) {
            
         
            if(e.getKeyCode()==KeyEvent.VK_ENTER){
                String x=Frectxt.getText();
                System.out.println(x);
                Filtrar(x);
                
            }
                
           
        }

        @Override
        public void keyReleased(KeyEvent e) {
            
        }
        
      });


     
      Frectxt.setVisible(true);
       tit.add(cantclientes,BorderLayout.EAST);
        tit.add(titulo,BorderLayout.CENTER);
       
    eti.setVisible(false);
        Frectxt.setText("");
        eti.setBounds(15, 7, 220, 40);
        eti.setVisible(false);
       
        
        Frectxt.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                Frectxt.setText("");
                Frectxt.setBackground(new Color(81, 143, 245));
                Frectxt.setForeground(Color.black);
             
            }

            @Override
            public void focusLost(FocusEvent e) {
                Frectxt.setBackground(new Color(27, 38, 49 ));
                Frectxt.setForeground(new Color(  213, 216, 220  ));
                if(Frectxt.getText().isEmpty())
                Frectxt.setText("BÚSQUEDA");
            }
            
        });
      /*   frecBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Filtrar(Frectxt.getText());
                Frectxt.setText("");
                throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
            }
            
        });*/



        /*Frectxt.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
            }

            @Override
            public void keyPressed(KeyEvent e) {
                
                throw new UnsupportedOperationException("Unimplemented method 'keyPressed'");
            }

            @Override
            public void keyReleased(KeyEvent e) {
                /*try {
                    sorter.setRowFilter(RowFilter.regexFilter( Frectxt.getText(), 8));
                } catch (Exception err) {
                    // TODO: handle exception
                }
                throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
            }
            
        });*/
       
    }
    
    private void Filtrar(String busq){
       try {
     
        sorter.setRowFilter(RowFilter.regexFilter(busq,1,6));
       } catch (Exception e) {
        // TODO: handle exception
       }
    }
   
    private void EventosdeTabla() {
        tab.addMouseListener(new MouseInputListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                int column = tab.columnAtPoint(e.getPoint());
                int row = tab.rowAtPoint(e.getPoint());

                
                if(e.getClickCount()==1){
                    column = tab.columnAtPoint(e.getPoint());
                     row = tab.rowAtPoint(e.getPoint());
    
                    if (column < tab.getColumnCount() && column >= 0 && row < tab.getRowCount() && row >= 0) {
                        if (column == 7) {
                            if(tab.getValueAt(row, 2).toString().contains("Desconectado")){
                                JOptionPane.showMessageDialog(null, "No se puede reiniciar un panel Desconectado","ERROR",JOptionPane.ERROR_MESSAGE);
                             }else{
                            String ips = (String) tab.getValueAt(row, 1);
                            if(flag){
                                if(row%2==0){
                                   
                                   
                                   List<Paneles> res = new ArrayList<>();
                                   res=dis.get(7).get_Paneles();
                                   try {
                                    command.ReiniciarEnlaces(res.get(tab.getSelectedRow()).get_ip(),res.get(tab.getSelectedRow()).get_tipo() );
                                    command.ReiniciarEnlaces(res.get(tab.getSelectedRow()+1).get_ip(),res.get(tab.getSelectedRow()+1).get_tipo() );
                                } catch (JSchException | InterruptedException e1) {
                                    // TODO Auto-generated catch block
                                    e1.printStackTrace();
                                }
                                   
                                }else{
                                    
                                    List<Paneles> res = new ArrayList<>();
                                    res=dis.get(7).get_Paneles();
                                    try {
                                     command.ReiniciarEnlaces(res.get(tab.getSelectedRow()-1).get_ip(),res.get(tab.getSelectedRow()-1).get_tipo() );
                                     command.ReiniciarEnlaces(res.get(tab.getSelectedRow()).get_ip(),res.get(tab.getSelectedRow()).get_tipo() );
                                 } catch (JSchException | InterruptedException e1) {
                                     // TODO Auto-generated catch block
                                     e1.printStackTrace();
                                 }
                                }
                            }else{
                                command.REINICIAR(ips);
                            }
                            
                            JOptionPane.showMessageDialog(null, "SE HA REINICIADO " + tab.getValueAt(row, 0), "REINICIO",
                                    JOptionPane.WARNING_MESSAGE);}
                        } else if (column == 8 ) {
                            List<Paneles> res = new ArrayList<>();
                              res = dis.get(7).get_Paneles();
                            String ips = (String) tab.getValueAt(row, 1);
                            String nom = (String) tab.getValueAt(row, 0);
                            try {
                                if(tab.getValueAt(row, 2).toString().contains("Desconectado")){
                                    JOptionPane.showMessageDialog(null, "No se puede realizar el backup de un panel Desconectado","ERROR",JOptionPane.ERROR_MESSAGE);
                                 }else{
                                   if(flag==false){
                                    command.BACKUP(ips,nom);
                                   }else{
                                 
                                        try {
                                            command.BackupEnlaces(res.get(tab.getSelectedRow()).get_ip(), res.get(tab.getSelectedRow()).get_tipo(), nom);
                                        } catch (InterruptedException e1) {
                                            // TODO Auto-generated catch block
                                            e1.printStackTrace();
                                        }
                                    
                                   }
                               
                                 }
                            } catch (JSchException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            } catch (SftpException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                        } else if (column == 1) {
                            if(tab.getValueAt(row, 2).toString().contains("Desconectado")){
                                JOptionPane.showMessageDialog(null, "El panel se encuenta Offline","ERROR",JOptionPane.ERROR_MESSAGE);
                            }else{
                            String ips = (String) tab.getValueAt(row, 1);
                            String url = "http://" + ips + ":83";
                            Desktop dek = Desktop.getDesktop();
                            try {
                                URI ur = new URI(url);
                                dek.browse(ur);
                            } catch (URISyntaxException e2) {
                                // TODO Auto-generated catch block
                                e2.printStackTrace();
                            } catch (IOException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                        }
                        }
                    }
                }else if(e.getClickCount()==2 && column==0 && bandera){
                  
                    List<Usuarios> users;
                 if(tab.getValueAt(row, 2).toString().contains("Desconectado")){
                    JOptionPane.showMessageDialog(null, "No se puede obtener información de un panel Desconectado","ERROR",JOptionPane.ERROR_MESSAGE);
                 }else{
                    users= command.CargadeUsuarios((String)tab.getValueAt(row, 1));
                
               
                    for(int i=0;i<users.size();i++){
                        try {
                            users.get(i).set_Time(command.HoraCliente(users.get(i).get_ipU()));
                        } catch (JSchException | InterruptedException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    }
                    VentadeUsuarios ventanausers= new VentadeUsuarios(users,paneles);
                    
                    }
                   
                 }
               

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseDragged(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                // TODO Auto-generated method stub

            }

        });
    }

    private void TaresdeBotones(JButton btn, int num) {
        
        btn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               
                limpiartabla();
                Frectxt.setText("BÚSQUEDA");
                List<Paneles> res = new ArrayList<>();
                res = dis.get(num).get_Paneles();
  
                int cancli=0,cant;
                List<Command> s = new ArrayList<>(res.size());
                paneles= new ArrayList<>();
                paneles.clear();
               bandera=true;
               Frectxt.setVisible(true);
               sorter.setRowFilter(RowFilter.regexFilter(""));
               flag=false;
               dist=num;
                for (int j = 0; j < res.size(); j++) {
                    try {
                      

                        if (EsAlcanzable(res.get(j).get_ip())) {

                                if(state.getName()=="Detallado"){
                                    command.set_ip(res.get(j).get_ip(),res.get(j).get_tipo());

                                    command.run();
                                    modelotabla.addRow(new Object[] { res.get(j).get_nom(), res.get(j).get_ip(), "Conectado",
                                    cant=command.get_cantidad(), command.get_horas(),command.Cable(),res.get(j).get_frec(), reinicio, back });
                                            
                                        paneles.add(res.get(j).get_nom());
                                        cancli+=cant;
                                }else{
                                    modelotabla.addRow(new Object[] { res.get(j).get_nom(), res.get(j).get_ip(), "Conectado",
                                    "-","-","-",res.get(j).get_frec(), reinicio, back});
                                    paneles.add(res.get(j).get_nom());
                                }
                            
                        } else {
                            modelotabla.addRow(new Object[] { res.get(j).get_nom(), res.get(j).get_ip(), "Desconectado",
                                    "-","-","-",res.get(j).get_frec(), reinicio, back});
                                    paneles.add(res.get(j).get_nom());
                        }

                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    if(state.getName()=="Detallado"){
                        cantclientes.setVisible(true);
                        cantclientes.setText("Clientes activos: "+cancli+"     ");
                    }else{
                        cantclientes.setVisible(false);
                    }
                  
                }
            }

        });

    }
    private void confBotones(JButton b, String p, int x, int y, int d) {
        b.setVisible(true);
        b.setEnabled(true);
        b.setBounds(x, y, 60, 40);
        ImageIcon img3 = new ImageIcon(p);
        b.setIcon(new ImageIcon(img3.getImage().getScaledInstance(d, d, Image.SCALE_SMOOTH)));
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);

        b.setPressedIcon(new ImageIcon(img3.getImage().getScaledInstance(d, d, Image.SCALE_SMOOTH)));
        b.setRolloverIcon(new ImageIcon(img3.getImage().getScaledInstance(d + 5, d + 5, Image.SCALE_SMOOTH)));
        b.setVerticalAlignment(SwingConstants.CENTER);
    }
    /*
     * private void crearTabbed() {
     * tabla= new JTabbedPane();
     * p3= new JPanel();
     * p4=new JPanel();
     * tabla.add("LosBerros", p3);
     * tabla.add("Colonia", p4);
     * p3.setPreferredSize(new Dimension(650,600));
     * p4.setPreferredSize(new Dimension(650,600));
     * p3.setBackground(new Color( 27, 36, 54 ));
     * p4.setBackground(new Color( 27, 36, 54 ));
     * p2.add(tabla);
     * }
     */
    JTable enla;
    DefaultTableModel modenla;
    JScrollPane scrollenla;
    Object columenla[] = {"Nombre","Envia/Recibe","Estado","Reiniciar"};
        private void Enlaces(){
            modenla = new DefaultTableModel(columenla, 0) {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
    
            enla = new JTable(modenla);
    
            enla.setDefaultRenderer(Object.class, new MiRenderer());
          
            enla.setFont(new Font("Berlin Sans FB", Font.PLAIN, 20));
            scrollenla= new JScrollPane(enla);
            p2.add(scrollenla);
            enlaces.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                   
                    
                }
                
            });
            
            
        }
    public static void main(String[] args) throws Exception {
        App vent = new App();
    }
}
