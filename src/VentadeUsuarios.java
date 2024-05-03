import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.StyledEditorKit.BoldAction;

class VentadeUsuarios extends JFrame {
    final private Font tittab= new Font("Berlin Sans FB", Font.PLAIN, 15);
    JFrame cont;
    JButton btnFrecuencia,btnreset;
    JTable tab;
    JPanel p1, p2;
    DefaultTableModel modelotabla;
    Command ssh= new Command();
    Object columnas[] = { "NOMBRE", "IP", "MAC", "HORAS", "SELECCIONAR" };
    Boolean editable[] = { false, false, false, false, true };
    JCheckBox jc;
    SecVentanaUsers second;
    List<String> paneles;
    public VentadeUsuarios(List<Usuarios> us, List<String>paneles) {
this.paneles=paneles;
        cont = new JFrame();

        cont.setVisible(true);
        cont.setSize(1100, 720);
        cont.setResizable(true);
        cont.setBackground(new Color(28, 40, 51));
        cont.setLayout(new BorderLayout());
        cont.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponent(us);

    }

    private void initComponent(List<Usuarios> us) {

        modelotabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int column) {
                return editable[column];
            }

            public Class<?> getColumnClass(int column) {

                if (column == 4)
                    return Boolean.class;
                else
                    return String.class;
            }
        };
        tab = new JTable(modelotabla);
        tab.setRowHeight(30);
        tab.setFont(new Font("Berlin Sans FB", Font.PLAIN, 20));
        tab.setDefaultRenderer(Object.class, new SecondRender());
        tab.getTableHeader().setFont(tittab);
        tab.getTableHeader().setBackground(new Color(118, 68, 138));
        tab.getTableHeader().setForeground(Color.white);
        tab.getTableHeader().setPreferredSize(new Dimension(10,20));
        tab.setSelectionBackground(new Color(40, 116, 166));
        tab.setSelectionForeground(Color.white);
        TableColumnModel columnModel = tab.getColumnModel();
        columnModel.getColumn(4).setPreferredWidth(10);
        tab.getTableHeader().setReorderingAllowed(false) ;
        llenarTabla(us);

        JScrollPane js;
        js = new JScrollPane(tab);
        tab.setFillsViewportHeight(true);
        js.getViewport().setBackground(new Color (28, 40, 51)); 
   
        js.setVisible(true);
        tab.setBackground(new Color(22, 21, 28));
        tab.setForeground(Color.white);
        js.setBorder(null);
        tab.setBorder(null);

        cont.add(js, BorderLayout.CENTER);
        p1 = new JPanel();

        p1.setBorder(null);
        p1.setBackground(Color.black);
        p1.setPreferredSize(new Dimension(1100, 80));

        cont.add(p1, BorderLayout.SOUTH);
        EventosdeMouse();
        colocarBotones();
    }

   private void EventosdeMouse(){
       tab.addMouseListener(new MouseListener(){
      

        @Override
        public void mouseClicked(MouseEvent e) {
           int  column = tab.columnAtPoint(e.getPoint());
           int  row = tab.rowAtPoint(e.getPoint());
           if(e.getClickCount()==2 && column==0){
            second=new SecVentanaUsers(tab.getValueAt(row,1).toString(),tab.getValueAt(row, 0).toString(),paneles);
           }
         
            
        }

        @Override
        public void mousePressed(MouseEvent e) {
            // TODO Auto-generated method stub
            
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

       } );
   }

    private void colocarBotones() {
        btnreset = new JButton();
        btnFrecuencia= new JButton();
        btnFrecuencia.setToolTipText("Desmarcar Frecuencia");
        btnreset.setToolTipText("Reiniciar");
        confBotones(btnreset, "reset.png", 10, 10, 40);
        confBotones(btnFrecuencia, "check.png", 10, 10, 40);
        //JLabel run = new JLabel("COMING SOON");
       // run.setForeground(Color.white);
        btnreset.addActionListener(new ActionListener() {
            String res = "";

            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i=0;i<tab.getRowCount();i++){
                    if((Boolean) tab.getValueAt(i, 4)){
                       if(ssh.REINICIAR(tab.getValueAt(i, 1).toString())){
                          // JOptionPane.showMessageDialog(null, "Se ha reiniciado la ip"+tab.getValueAt(i, 1).toString());
                       }else{
                        JOptionPane.showMessageDialog(null, "No se ha podido reiniciado la ip"+tab.getValueAt(i, 1).toString());
                       }
                    }
                  
                    
                    
                }
                
            }
        });


        btnFrecuencia.addActionListener(new ActionListener() {
            String res = "";

            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i=0;i<tab.getRowCount();i++){
                    if((Boolean) tab.getValueAt(i, 4)){
                       if(ssh.Frecuenciaout(tab.getValueAt(i, 1).toString())){
                          // JOptionPane.showMessageDialog(null, "Se ha reiniciado la ip"+tab.getValueAt(i, 1).toString());
                       }else{
                        JOptionPane.showMessageDialog(null, "No se ha podido desmarcar frecuencia de la ip"+tab.getValueAt(i, 1).toString());
                       }
                    }
                  
                    
                    
                }
                
            }
        });

        p1.add(btnreset);
        p1.add(btnFrecuencia);
    }

    private void llenarTabla(List<Usuarios> user) {

        for (Usuarios n : user) {
            modelotabla.addRow(new Object[] { n.get_nomU(), n.get_ipU(), n.get_macU(), n.get_Time(), false });

        }

    }

    private void confBotones(JButton b, String p, int x, int y, int d) {
        b.setVisible(true);
        b.setEnabled(true);
        b.setBounds(x, y, 100, 100);
        ImageIcon img3 = new ImageIcon(p);
        b.setIcon(new ImageIcon(img3.getImage().getScaledInstance(d, d, Image.SCALE_SMOOTH)));
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);

        b.setPressedIcon(new ImageIcon(img3.getImage().getScaledInstance(d, d, Image.SCALE_SMOOTH)));
        b.setRolloverIcon(new ImageIcon(img3.getImage().getScaledInstance(d + 5, d + 5, Image.SCALE_SMOOTH)));
        b.setVerticalAlignment(SwingConstants.CENTER);
    }

}