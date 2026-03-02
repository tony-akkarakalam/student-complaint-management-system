package studentcomplaintmanagementsystem.ui;

import java.awt.*;
import javax.swing.*;

public class SplashScreen extends JFrame {
    
    private JProgressBar progressBar;
    private Timer timer;
    private int progress = 0;
    
    public SplashScreen() {
        setUndecorated(true);
        setSize(600, 400);
        setLocationRelativeTo(null);
        
        // Main Panel with Gradient
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(10, 10, 25), 0, getHeight(), new Color(20, 20, 45));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Background Glow
                g2d.setColor(new Color(99, 102, 241, 20));
                g2d.fillOval(150, 50, 300, 300);
            }
        };
        
        // Content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(80, 0, 0, 0));
        
        // Logo
        JLabel lblLogo = new JLabel("SCMS");
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 60));
        lblLogo.setForeground(new Color(248, 250, 252));
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblTitle = new JLabel("Student Complaint Management System");
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblTitle.setForeground(new Color(148, 163, 184));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        contentPanel.add(lblLogo);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(lblTitle);
        
        // Progress Bar
        progressBar = new JProgressBar();
        progressBar.setPreferredSize(new Dimension(400, 6));
        progressBar.setMaximumSize(new Dimension(400, 6));
        progressBar.setForeground(new Color(99, 102, 241));
        progressBar.setBackground(new Color(30, 35, 55));
        progressBar.setBorderPainted(false);
        progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblLoading = new JLabel("Loading modules...");
        lblLoading.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblLoading.setForeground(new Color(100, 116, 139));
        lblLoading.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
        bottomPanel.add(progressBar);
        bottomPanel.add(Box.createVerticalStrut(10));
        bottomPanel.add(lblLoading);
        
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        startLoading();
    }
    
    private void startLoading() {
        timer = new Timer(30, e -> {
            progress += 1;
            progressBar.setValue(progress);
            
            if (progress >= 100) {
                timer.stop();
                new LoginFrame().setVisible(true);
                dispose();
            }
        });
        timer.start();
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        
        SwingUtilities.invokeLater(() -> new SplashScreen().setVisible(true));
    }
}