import java.awt.Color;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class MiRenderer extends DefaultTableCellRenderer{
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,                                            boolean isSelected, 
                                                   boolean hasFocus, 
                                                   int row, 
                                                   int column) {
    String vpar="par";
    String rpar="par";

    setHorizontalAlignment(SwingConstants.CENTER);
    String valor = (String) table.getValueAt(row, 2);
    
                if(valor=="Desconectado"){
                   if(rpar=="par"){
                    setBackground(new Color( 203, 67, 53 ));
                    rpar="impar";
                   }else{
                    setBackground(new Color( 88, 214, 141  ));
                    rpar="par";
                   }
                   
                    
                }else{
                    if(row%2==0){
                        setBackground(new Color( 40, 180, 99  ));
                       
                    }else{
                        setBackground(new Color( 88, 214, 141));
                       
                    }
               
                   
                }   

                if(value instanceof JButton){
                    JButton btn= (JButton) value;
                    return btn;
                }
                
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);                           
     }
    








    }



