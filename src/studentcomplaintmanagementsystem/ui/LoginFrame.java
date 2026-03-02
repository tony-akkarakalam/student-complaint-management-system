/*
 * Login Frame - Dark Theme
 */
package studentcomplaintmanagementsystem.ui;

import studentcomplaintmanagementsystem.db.DBConnection;
import studentcomplaintmanagementsystem.ui.components.CustomTitleBar;
import studentcomplaintmanagementsystem.ui.components.ModernButton;
import studentcomplaintmanagementsystem.ui.components.ModernPasswordField;
import studentcomplaintmanagementsystem.ui.components.ModernConfirmDialog;
import studentcomplaintmanagementsystem.ui.components.ModernTextField;
import studentcomplaintmanagementsystem.ui.components.Toast;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginFrame extends JFrame {
    
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbRole;
    private ModernButton btnLogin, btnExit;
    private JButton btnRegister;
    private Timer animationTimer;
    private float glowAlpha = 0.1f;
    private boolean glowIncreasing = true;
    
    // Dark Theme Colors
    private final Color BG_DARK = new Color(10, 10, 20);
    private final Color BG_CARD = new Color(20, 25, 35);
    private final Color PRIMARY = new Color(99, 102, 241);
    private final Color PRIMARY_HOVER = new Color(129, 140, 248);
    private final Color ACCENT = new Color(251, 191, 36);
    private final Color TEXT_PRIMARY = new Color(248, 250, 252);
    private final Color TEXT_MUTED = new Color(148, 163, 184);
    private final Color BORDER = new Color(55, 65, 81);
    private final Color SUCCESS = new Color(34, 197, 94);
    
    public LoginFrame() {
        setTitle("Student Complaint Management System");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setUndecorated(true);
        
        initComponents();
        startAnimation();
        fadeIn();
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (ModernConfirmDialog.show(LoginFrame.this, "Are you sure you want to exit?")) {
                    System.exit(0);
                }
            }
        });
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Dark gradient background
                GradientPaint gp = new GradientPaint(0, 0, new Color(10, 10, 25), 0, getHeight(), new Color(20, 20, 45));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Animated glow effect
                g2d.setColor(new Color(99, 102, 241, (int)(glowAlpha * 255)));
                for (int i = 0; i < 5; i++) {
                    g2d.fillOval(50 - i*10, 100 - i*5, 300 + i*20, 300 + i*20);
                }
            }
        };
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        
        // Left Panel - Decorative with glow
        JPanel leftPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                GradientPaint gp = new GradientPaint(0, 0, new Color(25, 25, 50), 0, getHeight(), new Color(15, 15, 35));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Glow circles
                g2d.setColor(new Color(99, 102, 241, 40));
                g2d.fillOval(30, 80, 180, 180);
                g2d.fillOval(80, 280, 120, 120);
                g2d.fillOval(40, 420, 80, 80);
                g2d.setColor(new Color(251, 191, 36, 20));
                g2d.fillOval(100, 200, 100, 100);
            }
        };
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(true);
        
        // Brand Logo Area
        JPanel logoPanel = new JPanel();
        logoPanel.setOpaque(false);
        logoPanel.setBorder(BorderFactory.createEmptyBorder(120, 0, 0, 0));
        
        // Logo circle with SCMS
        JLabel lblLogo = new JLabel("SCMS") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Glow effect
                GradientPaint gp = new GradientPaint(0, 0, new Color(99, 102, 241), getWidth(), getHeight(), new Color(129, 140, 248));
                g2d.setPaint(gp);
                g2d.fillOval(0, 0, getWidth(), getHeight());
            }
        };
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 42));
        lblLogo.setForeground(TEXT_PRIMARY);
        lblLogo.setHorizontalTextPosition(SwingConstants.CENTER);
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblLogo.setPreferredSize(new Dimension(120, 120));
        
        logoPanel.add(lblLogo);
        
        JLabel lblBrandFull = new JLabel("Student Complaint");
        lblBrandFull.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        lblBrandFull.setForeground(new Color(255, 255, 255, 180));
        lblBrandFull.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblBrandFull2 = new JLabel("Management System");
        lblBrandFull2.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        lblBrandFull2.setForeground(new Color(255, 255, 255, 180));
        lblBrandFull2.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        leftPanel.add(logoPanel);
        leftPanel.add(Box.createVerticalStrut(15));
        leftPanel.add(lblBrandFull);
        leftPanel.add(lblBrandFull2);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.42;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(leftPanel, gbc);
        
        // Right Panel - Login Form
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setOpaque(false);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(70, 50, 70, 50));
        
        // Title
        JLabel lblTitle = new JLabel("Welcome Back");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblTitle.setForeground(TEXT_PRIMARY);
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblSubtitle = new JLabel("Sign in to access your dashboard");
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblSubtitle.setForeground(TEXT_MUTED);
        lblSubtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblSubtitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));
        
        // Form Fields
        // Username
        JLabel lblUser = new JLabel("Email Address");
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblUser.setForeground(TEXT_MUTED);
        lblUser.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        txtUsername = new ModernTextField();
        txtUsername.setPreferredSize(new Dimension(0, 45));
        txtUsername.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Password
        JLabel lblPass = new JLabel("Password");
        lblPass.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblPass.setForeground(TEXT_MUTED);
        lblPass.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblPass.setBorder(BorderFactory.createEmptyBorder(20, 0, 8, 0));
        
        txtPassword = new ModernPasswordField();
        txtPassword.setPreferredSize(new Dimension(0, 45));
        txtPassword.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Role
        JLabel lblRole = new JLabel("Login As");
        lblRole.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblRole.setForeground(TEXT_MUTED);
        lblRole.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblRole.setBorder(BorderFactory.createEmptyBorder(20, 0, 8, 0));
        
        cmbRole = new JComboBox<>(new String[]{"Student", "Admin"});
        cmbRole.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        cmbRole.setForeground(Color.BLACK);
        cmbRole.setBackground(Color.WHITE);
        cmbRole.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1),
            BorderFactory.createEmptyBorder(0, 18, 0, 18)
        ));
        cmbRole.setPreferredSize(new Dimension(0, 48));
        cmbRole.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        cmbRole.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (isSelected) {
                    setBackground(PRIMARY);
                    setForeground(Color.WHITE);
                } else {
                    setBackground(Color.WHITE);
                    setForeground(Color.BLACK);
                }
                return c;
            }
        });
        
        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(35, 0, 0, 0));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        btnLogin = new ModernButton("Sign In", ACCENT, new Color(245, 158, 11));
        btnLogin.setPreferredSize(new Dimension(200, 45));
        
        btnExit = new ModernButton("Exit", new Color(55, 65, 81), new Color(75, 85, 99));
        btnExit.setPreferredSize(new Dimension(120, 45));
        
        buttonPanel.add(btnLogin);
        buttonPanel.add(Box.createHorizontalStrut(15));
        buttonPanel.add(btnExit);
        
        // Register Link
        JPanel registerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        registerPanel.setOpaque(false);
        registerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        registerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblNewStudent = new JLabel("New student? ");
        lblNewStudent.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblNewStudent.setForeground(TEXT_MUTED);
        
        btnRegister = new JButton("Register Here");
        btnRegister.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRegister.setForeground(PRIMARY_HOVER);
        btnRegister.setOpaque(false);
        btnRegister.setBorderPainted(false);
        btnRegister.setFocusPainted(false);
        btnRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        registerPanel.add(lblNewStudent);
        registerPanel.add(btnRegister);
        
        // Add to right panel
        rightPanel.add(lblTitle);
        rightPanel.add(lblSubtitle);
        rightPanel.add(lblUser);
        rightPanel.add(Box.createVerticalStrut(8));
        rightPanel.add(txtUsername);
        rightPanel.add(lblPass);
        rightPanel.add(txtPassword);
        rightPanel.add(lblRole);
        rightPanel.add(Box.createVerticalStrut(8));
        rightPanel.add(cmbRole);
        rightPanel.add(buttonPanel);
        rightPanel.add(registerPanel);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.58;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(rightPanel, gbc);
        
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        wrapperPanel.add(new CustomTitleBar(this), BorderLayout.NORTH);
        wrapperPanel.add(mainPanel, BorderLayout.CENTER);
        
        add(wrapperPanel);
        
        // Actions
        btnLogin.addActionListener(e -> performLogin());
        // Dispatch a window closing event to trigger the listener
        btnExit.addActionListener(e -> dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));
        btnRegister.addActionListener(e -> openRegistration());
        txtPassword.addActionListener(e -> performLogin());
    }
    
    private void fadeIn() {
        setOpacity(0f);
        Timer timer = new Timer(20, new java.awt.event.ActionListener() {
            float opacity = 0f;
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                opacity += 0.05f;
                if (opacity >= 1f) {
                    opacity = 1f;
                    ((Timer)e.getSource()).stop();
                }
                setOpacity(opacity);
            }
        });
        timer.start();
    }
    
    private void startAnimation() {
        animationTimer = new Timer(50, e -> {
            if (glowIncreasing) {
                glowAlpha += 0.005f;
                if (glowAlpha >= 0.2f) glowIncreasing = false;
            } else {
                glowAlpha -= 0.005f;
                if (glowAlpha <= 0.05f) glowIncreasing = true;
            }
            repaint();
        });
        animationTimer.start();
    }
    
    private void performLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        String role = (String) cmbRole.getSelectedItem();
        
        if (username.isEmpty() || password.isEmpty()) {
            Toast.show(this, "Please enter username and password!", false);
            return;
        }
        
        try {
            Connection conn = DBConnection.getConnection();
            
            if (role.equals("Student")) {
                String sql = "SELECT student_id, name, approval_status FROM student WHERE email = ? AND password = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                ResultSet rs = pstmt.executeQuery();
                
                if (rs.next()) {
                    String approvalStatus = rs.getString("approval_status");
                    if (!"Approved".equals(approvalStatus)) {
                        Toast.show(this, "Registration is " + approvalStatus + ". Wait for approval.", false);
                    } else {
                        int studentId = rs.getInt("student_id");
                        String studentName = rs.getString("name");
                        conn.close();
                        // Success - Open Dashboard directly without popup
                        new StudentDashboard(studentId, studentName).setVisible(true);
                        this.dispose();
                        return;
                    }
                } else {
                    Toast.show(this, "Invalid email or password!", false);
                }
                rs.close();
                pstmt.close();
            } else {
                String sql = "SELECT admin_id FROM admin WHERE username = ? AND password = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                ResultSet rs = pstmt.executeQuery();
                
                if (rs.next()) {
                    conn.close();
                    new AdminDashboard().setVisible(true);
                    this.dispose();
                    return;
                } else {
                    Toast.show(this, "Invalid username or password!", false);
                }
                rs.close();
                pstmt.close();
            }
            
            if (!conn.isClosed()) conn.close();
        } catch (Exception ex) {
            Toast.show(this, "Database Error: " + ex.getMessage(), false);
            ex.printStackTrace();
        }
    }
    
    private void openRegistration() {
        new StudentRegistrationFrame().setVisible(true);
        this.dispose();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new LoginFrame().setVisible(true);
        });
    }
}
