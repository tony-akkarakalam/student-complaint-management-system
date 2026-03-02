/*
 * Student Registration Frame - Dark Theme
 */
package studentcomplaintmanagementsystem.ui;

import studentcomplaintmanagementsystem.db.DBConnection;
import studentcomplaintmanagementsystem.ui.components.CustomTitleBar;
import studentcomplaintmanagementsystem.ui.components.ModernButton;
import studentcomplaintmanagementsystem.ui.components.ModernPasswordField;
import studentcomplaintmanagementsystem.ui.components.ModernTextField;
import studentcomplaintmanagementsystem.ui.components.Toast;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class StudentRegistrationFrame extends JFrame {
    
    private JTextField txtName, txtEmail;
    private JPasswordField txtPassword, txtConfirm;
    private JComboBox<String> cmbDepartment;
    private ModernButton btnRegister, btnBack;
    
    // Dark Theme Colors
    private final Color BG_DARK = new Color(10, 10, 20);
    private final Color BG_CARD = new Color(20, 25, 35);
    private final Color PRIMARY = new Color(99, 102, 241);
    private final Color SUCCESS = new Color(34, 197, 94);
    private final Color TEXT_PRIMARY = new Color(248, 250, 252);
    private final Color TEXT_MUTED = new Color(148, 163, 184);
    private final Color BORDER = new Color(55, 65, 81);
    
    public StudentRegistrationFrame() {
        setTitle("Student Registration");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true);
        
        initComponents();
        fadeIn();
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                goToLogin();
            }
        });
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(10, 10, 25), 0, getHeight(), new Color(20, 20, 45));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        // Title
        JLabel lblTitle = new JLabel("Student Registration");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(TEXT_PRIMARY);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(lblTitle, BorderLayout.NORTH);
        
        // Form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(false);
        
        // Name
        formPanel.add(createLabel("Full Name"));
        formPanel.add(Box.createVerticalStrut(5));
        txtName = new ModernTextField();
        txtName.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtName.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        formPanel.add(txtName);
        formPanel.add(Box.createVerticalStrut(15));
        
        // Email
        formPanel.add(createLabel("Email"));
        formPanel.add(Box.createVerticalStrut(5));
        txtEmail = new ModernTextField();
        txtEmail.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtEmail.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        formPanel.add(txtEmail);
        formPanel.add(Box.createVerticalStrut(15));
        
        // Department
        formPanel.add(createLabel("Department"));
        formPanel.add(Box.createVerticalStrut(5));
        cmbDepartment = new JComboBox<>(new String[]{
            "Computer Science", "Information Technology", "Electronics", 
            "Mechanical", "Civil", "Electrical", "Other"
        });
        cmbDepartment.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbDepartment.setBackground(Color.WHITE);
        cmbDepartment.setForeground(Color.BLACK);
        cmbDepartment.setAlignmentX(Component.LEFT_ALIGNMENT);
        cmbDepartment.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        formPanel.add(cmbDepartment);
        formPanel.add(Box.createVerticalStrut(15));
        
        // Password
        formPanel.add(createLabel("Password"));
        formPanel.add(Box.createVerticalStrut(5));
        txtPassword = new ModernPasswordField();
        txtPassword.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        formPanel.add(txtPassword);
        formPanel.add(Box.createVerticalStrut(15));
        
        // Confirm Password
        formPanel.add(createLabel("Confirm Password"));
        formPanel.add(Box.createVerticalStrut(5));
        txtConfirm = new ModernPasswordField();
        txtConfirm.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtConfirm.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        formPanel.add(txtConfirm);
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        buttonPanel.setOpaque(false);
        
        btnRegister = new ModernButton("Register", SUCCESS, SUCCESS.darker());
        btnBack = new ModernButton("Back to Login", new Color(75, 85, 99), new Color(90, 100, 115));
        
        btnRegister.setPreferredSize(new Dimension(160, 45));
        btnBack.setPreferredSize(new Dimension(160, 45));
        
        buttonPanel.add(btnRegister);
        buttonPanel.add(btnBack);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        wrapperPanel.add(new CustomTitleBar(this), BorderLayout.NORTH);
        wrapperPanel.add(mainPanel, BorderLayout.CENTER);
        
        add(wrapperPanel);
        
        btnRegister.addActionListener(e -> performRegistration());
        btnBack.addActionListener(e -> goToLogin());
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
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(TEXT_MUTED);
        return label;
    }
    
    private void performRegistration() {
        String name = txtName.getText().trim();
        String email = txtEmail.getText().trim();
        String department = (String) cmbDepartment.getSelectedItem();
        String password = new String(txtPassword.getPassword());
        String confirm = new String(txtConfirm.getPassword());
        
        // Validation
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.show(this, "Please fill all fields.", false);
            return;
        }
        
        if (!password.equals(confirm)) {
            Toast.show(this, "Passwords do not match.", false);
            return;
        }
        
        if (password.length() < 4) {
            Toast.show(this, "Password must be at least 4 characters.", false);
            return;
        }
        
        try {
            Connection conn = DBConnection.getConnection();
            
            // Check if email exists
            String checkSql = "SELECT student_id FROM student WHERE email = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, email);
            var rs = checkStmt.executeQuery();
            
            if (rs.next()) {
                Toast.show(this, "This email is already registered.", false);
                rs.close();
                checkStmt.close();
                conn.close();
                return;
            }
            rs.close();
            checkStmt.close();
            
            // Insert new student
            String sql = "INSERT INTO student (name, email, password, department, approval_status, registration_date) " +
                        "VALUES (?, ?, ?, ?, 'Pending', SYSDATE)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.setString(4, department);
            
            int result = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            
            if (result > 0) {
                Toast.show(this, "Registration submitted! Please wait for admin approval.", true);
                goToLogin();
            }
            
        } catch (Exception ex) {
            Toast.show(this, "Database Error: " + ex.getMessage(), false);
        }
    }
    
    private void goToLogin() {
        new LoginFrame().setVisible(true);
        this.dispose();
    }
}
