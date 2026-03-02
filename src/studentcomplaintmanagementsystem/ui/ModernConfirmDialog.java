package studentcomplaintmanagementsystem.ui.components;

import java.awt.*;
import javax.swing.*;

public class ModernConfirmDialog extends JDialog {
    private boolean confirmed = false;

    public static boolean show(JFrame parent, String message) {
        ModernConfirmDialog dialog = new ModernConfirmDialog(parent, message);
        dialog.setVisible(true);
        return dialog.confirmed;
    }

    private ModernConfirmDialog(JFrame parent, String message) {
        super(parent, "Confirm", true);
        setUndecorated(true);
        setSize(400, 180);
        setLocationRelativeTo(parent);
        setBackground(new Color(0,0,0,0)); // for rounded corners

        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(20, 25, 35));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.setColor(new Color(55, 65, 81));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblMessage = new JLabel("<html><div style='text-align: center;'>" + message + "</div></html>");
        lblMessage.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblMessage.setForeground(new Color(248, 250, 252));
        lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblMessage, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        ModernButton btnYes = new ModernButton("Yes", new Color(34, 197, 94), new Color(26, 147, 71));
        btnYes.setPreferredSize(new Dimension(100, 40));
        btnYes.addActionListener(e -> {
            confirmed = true;
            dispose();
        });

        ModernButton btnNo = new ModernButton("No", new Color(239, 68, 68), new Color(220, 38, 38));
        btnNo.setPreferredSize(new Dimension(100, 40));
        btnNo.addActionListener(e -> {
            confirmed = false;
            dispose();
        });

        buttonPanel.add(btnYes);
        buttonPanel.add(btnNo);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }
}