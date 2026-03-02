package studentcomplaintmanagementsystem.ui.components;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class ModernButton extends JButton {
    private Color color;
    private Color colorOver;
    private Color colorClick;
    private int radius = 15;
    private boolean over;

    public ModernButton(String text, Color color, Color colorOver) {
        super(text);
        this.color = color;
        this.colorOver = colorOver;
        this.colorClick = colorOver.darker();
        
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setForeground(Color.WHITE);
        setFont(new Font("Segoe UI", Font.BOLD, 14));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                over = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent me) {
                over = false;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent me) {
                setBackground(colorClick);
                repaint();
            }
            
            @Override
            public void mouseReleased(MouseEvent me) {
                if (over) {
                    setBackground(colorOver);
                } else {
                    setBackground(color);
                }
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(over ? colorOver : color);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        super.paintComponent(g);
        g2.dispose();
    }
}