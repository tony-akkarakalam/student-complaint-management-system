/*
 * Register Complaint Frame - Dark Theme
 */
package studentcomplaintmanagementsystem.ui;

import studentcomplaintmanagementsystem.db.DBConnection;
import studentcomplaintmanagementsystem.ui.components.CustomTitleBar;
import studentcomplaintmanagementsystem.ui.components.ModernButton;
import studentcomplaintmanagementsystem.ui.components.Toast;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RegisterComplaintFrame extends JFrame {
    
    private StudentDashboard parentFrame;
    private int studentId;
    private JComboBox<String> cmbCategory;
    private JTextArea txtComplaint;
    private ModernButton btnSubmit, btnBack;
    private Vector<Integer> categoryIds;
    
    // Dark Theme Colors
    private final Color BG_DARK = new Color(10, 10, 20);
    private final Color BG_CARD = new Color(20, 25, 35);
    private final Color PRIMARY = new Color(99, 102, 241);
    private final Color SUCCESS = new Color(34, 197, 94);
    private final Color TEXT_PRIMARY = new Color(248, 250, 252);
    private final Color TEXT_MUTED = new Color(148, 163, 184);
    private final Color BORDER = new Color(55, 65, 81);
    
    public RegisterComplaintFrame(StudentDashboard parent, int studentId) {
        this.parentFrame = parent;
        this.studentId = studentId;
        this.categoryIds = new Vector<>();
        
        setTitle("Register Complaint");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true);
        
        initComponents();
        loadCategories();
        fadeIn();
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                goBack();
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
        
        JLabel lblTitle = new JLabel("Register New Complaint");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(TEXT_PRIMARY);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(lblTitle, BorderLayout.NORTH);
        
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(BG_DARK);
        formPanel.setOpaque(false);
        
        // Category
        JLabel lblCategory = new JLabel("Select Complaint Category");
        lblCategory.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblCategory.setForeground(TEXT_MUTED);
        lblCategory.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        cmbCategory = new JComboBox<>();
        cmbCategory.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbCategory.setBackground(Color.WHITE);
        cmbCategory.setForeground(Color.BLACK);
        cmbCategory.setAlignmentX(Component.LEFT_ALIGNMENT);
        cmbCategory.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        
        // Complaint
        JLabel lblComplaint = new JLabel("Describe your complaint");
        lblComplaint.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblComplaint.setForeground(TEXT_MUTED);
        lblComplaint.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        txtComplaint = new JTextArea(10, 30);
        txtComplaint.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtComplaint.setForeground(TEXT_PRIMARY);
        txtComplaint.setBackground(BG_CARD);
        txtComplaint.setCaretColor(TEXT_PRIMARY);
        txtComplaint.setLineWrap(true);
        txtComplaint.setWrapStyleWord(true);
        txtComplaint.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(txtComplaint);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        scrollPane.getViewport().setBackground(BG_CARD);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        formPanel.add(lblCategory);
        formPanel.add(Box.createVerticalStrut(8));
        formPanel.add(cmbCategory);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(lblComplaint);
        formPanel.add(Box.createVerticalStrut(8));
        formPanel.add(scrollPane);
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setBackground(BG_DARK);
        
        btnSubmit = new ModernButton("Submit Complaint", SUCCESS, SUCCESS.darker());
        btnBack = new ModernButton("Back", new Color(75, 85, 99), new Color(90, 100, 115));
        
        btnSubmit.setPreferredSize(new Dimension(180, 45));
        btnBack.setPreferredSize(new Dimension(120, 45));
        
        buttonPanel.add(btnSubmit);
        buttonPanel.add(btnBack);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        wrapperPanel.add(new CustomTitleBar(this), BorderLayout.NORTH);
        wrapperPanel.add(mainPanel, BorderLayout.CENTER);
        
        add(wrapperPanel);
        
        btnSubmit.addActionListener(e -> submitComplaint());
        btnBack.addActionListener(e -> goBack());
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
    
    private void loadCategories() {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT category_id, category_name FROM category ORDER BY category_name";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            
            categoryIds.clear();
            cmbCategory.removeAllItems();
            
            while (rs.next()) {
                categoryIds.add(rs.getInt("category_id"));
                cmbCategory.addItem(rs.getString("category_name"));
            }
            
            rs.close();
            pstmt.close();
            conn.close();
            
            if (cmbCategory.getItemCount() == 0) {
                Toast.show(this, "No complaint categories found.", false);
            }
            
        } catch (Exception ex) {
            Toast.show(this, "Database Error: " + ex.getMessage(), false);
        }
    }
    
    private void submitComplaint() {
        int selectedIndex = cmbCategory.getSelectedIndex();
        String complaintText = txtComplaint.getText().trim();
        
        if (selectedIndex < 0) {
            Toast.show(this, "Please select a category.", false);
            return;
        }
        
        if (complaintText.isEmpty()) {
            Toast.show(this, "Please enter your complaint.", false);
            return;
        }
        
        try {
            Connection conn = DBConnection.getConnection();
            
            String sql = "INSERT INTO complaint (student_id, category_id, status_id, complaint_text, complaint_date) " +
                        "VALUES (?, ?, 1, ?, SYSDATE)";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, categoryIds.get(selectedIndex));
            pstmt.setString(3, complaintText);
            
            int result = pstmt.executeUpdate();
            
            pstmt.close();
            conn.close();
            
            if (result > 0) {
                Toast.show(parentFrame, "Complaint registered successfully!", true);
                goBack();
            }
            
        } catch (Exception ex) {
            Toast.show(this, "Database Error: " + ex.getMessage(), false);
        }
    }
    
    private void goBack() {
        parentFrame.setVisible(true);
        parentFrame.loadComplaints();
        this.dispose();
    }
}
