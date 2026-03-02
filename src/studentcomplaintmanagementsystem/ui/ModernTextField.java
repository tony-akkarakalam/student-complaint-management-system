package studentcomplaintmanagementsystem.ui.components;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ModernTextField extends JTextField {
    private final Color backgroundColor = new Color(20, 25, 35);
    private final Color borderColor = new Color(55, 65, 81);
    
    public ModernTextField() {
        setBackground(new Color(0,0,0,0));
        setOpaque(false);
        setBorder(new EmptyBorder(10, 15, 10, 15));
        setFont(new Font("Segoe UI", Font.PLAIN, 14));
        setForeground(new Color(248, 250, 252));
        setCaretColor(new Color(248, 250, 252));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setColor(backgroundColor);
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
        
        g2.setColor(borderColor);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
        
        super.paintComponent(g);
        g2.dispose();
    }
}