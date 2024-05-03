import java.awt.Color;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;



public class SecondRender extends DefaultTableCellRenderer{
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,                                            boolean isSelected, 
                                                   boolean hasFocus, 
                                                   int row, 
                                                   int column) {
                                                   
      setHorizontalAlignment(SwingConstants.CENTER);
    if(row%2==0){
      setBackground(new Color( 40, 180, 99  ));
    }else{
      setBackground(new Color( 88, 214, 141));
    }
   if(value instanceof JCheckBox){
     JCheckBox check= (JCheckBox)value;
   
     setOpaque(true);
     setBackground(new Color( 82, 190, 128 ));
     return check;
   }

  return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    
}
}

