package studentcomplaintmanagementsystem.ui.components;

import java.awt.*;
import javax.swing.*;

public class Toast extends JPanel {
    private static final int HEIGHT = 40;
    private static final int BOTTOM_MARGIN = 80;

    public Toast(String message, boolean success) {
        setOpaque(false);
        setLayout(new GridBagLayout());
        
        JLabel label = new JLabel(message);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(Color.WHITE);
        add(label);
        
        // Green for success, Red for error
        setBackground(success ? new Color(34, 197, 94) : new Color(239, 68, 68));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        g2.dispose();
        super.paintComponent(g);
    }

    public static void show(JFrame frame, String message, boolean success) {
        JLayeredPane layeredPane = frame.getLayeredPane();
        
        Toast toast = new Toast(message, success);
        
        FontMetrics fm = toast.getFontMetrics(new Font("Segoe UI", Font.BOLD, 14));
        int textWidth = fm.stringWidth(message);
        int width = textWidth + 60; // Padding
        
        int x = (frame.getWidth() - width) / 2;
        int y = frame.getHeight() - BOTTOM_MARGIN - HEIGHT;
        
        toast.setBounds(x, y, width, HEIGHT);
        layeredPane.add(toast, JLayeredPane.POPUP_LAYER);
        
        Timer timer = new Timer(3000, e -> {
            layeredPane.remove(toast);
            layeredPane.revalidate();
            layeredPane.repaint();
        });
        timer.setRepeats(false);
        timer.start();
    }
}