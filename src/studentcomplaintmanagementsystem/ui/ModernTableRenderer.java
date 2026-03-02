package studentcomplaintmanagementsystem.ui.components;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class ModernTableRenderer extends DefaultTableCellRenderer {
    
    private final Color TABLE_DARK = new Color(25, 30, 45);
    private final Color TABLE_ROW_DARK = new Color(30, 35, 50);
    private final Color TEXT_PRIMARY = new Color(248, 250, 252);
    private final Color SELECTION_BG = new Color(99, 102, 241);
    
    public ModernTableRenderer(int alignment) {
        setHorizontalAlignment(alignment);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        // Set Colors
        if (isSelected) {
            c.setBackground(SELECTION_BG);
            c.setForeground(Color.WHITE);
        } else {
            if (row % 2 == 0) {
                c.setBackground(TABLE_DARK);
            } else {
                c.setBackground(TABLE_ROW_DARK);
            }
            c.setForeground(TEXT_PRIMARY);
        }
        
        // Add Padding
        if (c instanceof JLabel) {
            ((JLabel) c).setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        }
        
        return c;
    }
}