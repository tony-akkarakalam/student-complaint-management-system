/*
 * Student Dashboard - Dark Theme
 */
package studentcomplaintmanagementsystem.ui;

import studentcomplaintmanagementsystem.db.DBConnection;
import studentcomplaintmanagementsystem.ui.components.CustomTitleBar;
import studentcomplaintmanagementsystem.ui.components.ModernButton;
import studentcomplaintmanagementsystem.ui.components.ModernConfirmDialog;
import studentcomplaintmanagementsystem.ui.components.ModernTableRenderer;
import studentcomplaintmanagementsystem.ui.components.Toast;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class StudentDashboard extends JFrame {
    
    private int studentId;
    private String studentName;
    private JTable complaintsTable;
    private DefaultTableModel tableModel;
    private ModernButton btnRegisterComplaint, btnViewComplaints, btnLogout;
    
    // Dark Theme Colors
    private final Color BG_DARK = new Color(10, 10, 20);
    private final Color BG_CARD = new Color(20, 25, 35);
    private final Color PRIMARY = new Color(99, 102, 241);
    private final Color SUCCESS = new Color(34, 197, 94);
    private final Color DANGER = new Color(239, 68, 68);
    private final Color TEXT_PRIMARY = new Color(248, 250, 252);
    private final Color TEXT_MUTED = new Color(148, 163, 184);
    private final Color BORDER = new Color(55, 65, 81);
    private final Color TABLE_DARK = new Color(25, 30, 45);
    private final Color TABLE_ROW_DARK = new Color(30, 35, 50);
    
    public StudentDashboard(int studentId, String studentName) {
        this.studentId = studentId;
        this.studentName = studentName;
        
        setTitle("Student Dashboard - " + studentName);
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setUndecorated(true);
        
        initComponents();
        loadComplaints();
        fadeIn();
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }
    
    private void initComponents() {
        // Main panel with dark gradient
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
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);
        
        JLabel lblTitle = new JLabel("Welcome, " + studentName);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(TEXT_PRIMARY);
        
        JLabel lblId = new JLabel("Student ID: " + studentId);
        lblId.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblId.setForeground(TEXT_MUTED);
        
        titlePanel.add(lblTitle);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(lblId);
        
        headerPanel.add(titlePanel, BorderLayout.WEST);
        
        // Content area
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 25, 30));
        
        // Table title
        JPanel titleRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 10));
        titleRow.setOpaque(false);
        
        JLabel lblTableTitle = new JLabel("My Complaints");
        lblTableTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTableTitle.setForeground(TEXT_PRIMARY);
        
        titleRow.add(lblTableTitle);
        
        // Table - Dark Theme
        String[] columns = {"ID", "Category", "Status", "Complaint", "Date"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        complaintsTable = new JTable(tableModel);
        complaintsTable.setFont(new Font("Segoe UI", Font.BOLD, 13));
        complaintsTable.setRowHeight(42);
        complaintsTable.setSelectionBackground(PRIMARY);
        complaintsTable.setSelectionForeground(TEXT_PRIMARY);
        complaintsTable.setGridColor(BORDER);
        complaintsTable.setShowGrid(true);
        complaintsTable.setOpaque(true);
        complaintsTable.setBackground(TABLE_DARK);
        
        // Apply Modern Renderer with specific alignments
        complaintsTable.getColumnModel().getColumn(0).setCellRenderer(new ModernTableRenderer(SwingConstants.CENTER)); // ID
        complaintsTable.getColumnModel().getColumn(1).setCellRenderer(new ModernTableRenderer(SwingConstants.CENTER)); // Category
        complaintsTable.getColumnModel().getColumn(2).setCellRenderer(new ModernTableRenderer(SwingConstants.CENTER)); // Status
        complaintsTable.getColumnModel().getColumn(3).setCellRenderer(new ModernTableRenderer(SwingConstants.LEFT));   // Complaint
        complaintsTable.getColumnModel().getColumn(4).setCellRenderer(new ModernTableRenderer(SwingConstants.CENTER)); // Date
        
        // Dark column headers
        complaintsTable.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(new Color(30, 35, 55));
                c.setForeground(TEXT_PRIMARY);
                c.setFont(new Font("Segoe UI", Font.BOLD, 14));
                ((JLabel)c).setHorizontalAlignment(JLabel.CENTER);
                return c;
            }
        });
        complaintsTable.getTableHeader().setPreferredSize(new Dimension(0, 48));
        complaintsTable.getTableHeader().setBorder(BorderFactory.createLineBorder(BORDER, 2));
        
        
        // Column widths
        complaintsTable.getColumnModel().getColumn(0).setPreferredWidth(60);
        complaintsTable.getColumnModel().getColumn(1).setPreferredWidth(120);
        complaintsTable.getColumnModel().getColumn(2).setPreferredWidth(120);
        complaintsTable.getColumnModel().getColumn(3).setPreferredWidth(350);
        complaintsTable.getColumnModel().getColumn(4).setPreferredWidth(120);
        
        JScrollPane scrollPane = new JScrollPane(complaintsTable);
        scrollPane.setBackground(TABLE_DARK);
        scrollPane.setBorder(BorderFactory.createLineBorder(PRIMARY, 2));
        scrollPane.getViewport().setBackground(TABLE_DARK);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 20));
        buttonPanel.setOpaque(false);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        btnRegisterComplaint = new ModernButton("Register Complaint", SUCCESS, SUCCESS.darker());
        btnViewComplaints = new ModernButton("Refresh", PRIMARY, PRIMARY.darker());
        btnLogout = new ModernButton("Logout", DANGER, DANGER.darker());
        
        btnRegisterComplaint.setPreferredSize(new Dimension(180, 45));
        btnViewComplaints.setPreferredSize(new Dimension(140, 45));
        btnLogout.setPreferredSize(new Dimension(140, 45));
        
        buttonPanel.add(btnRegisterComplaint);
        buttonPanel.add(btnViewComplaints);
        buttonPanel.add(btnLogout);
        
        // Add to content
        contentPanel.add(titleRow);
        contentPanel.add(scrollPane);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(buttonPanel);
        
        // Add to main
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        wrapperPanel.add(new CustomTitleBar(this), BorderLayout.NORTH);
        wrapperPanel.add(mainPanel, BorderLayout.CENTER);
        
        add(wrapperPanel);
        
        // Actions
        btnRegisterComplaint.addActionListener(e -> openRegisterComplaint());
        btnViewComplaints.addActionListener(e -> loadComplaints());
        btnLogout.addActionListener(e -> logout());
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
    
    public void loadComplaints() {
        try {
            Connection conn = DBConnection.getConnection();
            
            String sql = "SELECT c.complaint_id, cat.category_name, st.status_name, " +
                        "c.complaint_text, c.complaint_date " +
                        "FROM complaint c " +
                        "JOIN category cat ON c.category_id = cat.category_id " +
                        "JOIN status st ON c.status_id = st.status_id " +
                        "WHERE c.student_id = ? " +
                        "ORDER BY c.complaint_date DESC";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            
            tableModel.setRowCount(0);
            
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("complaint_id"));
                row.add(rs.getString("category_name"));
                row.add(rs.getString("status_name"));
                row.add(rs.getString("complaint_text"));
                row.add(rs.getDate("complaint_date"));
                tableModel.addRow(row);
            }
            
            rs.close();
            pstmt.close();
            conn.close();
            
        } catch (Exception ex) {
            Toast.show(this, "Database Error: " + ex.getMessage(), false);
        }
    }
    
    private void openRegisterComplaint() {
        new RegisterComplaintFrame(this, studentId).setVisible(true);
        this.setVisible(false);
    }
    
    private void logout() {
        if (ModernConfirmDialog.show(this, "Are you sure you want to logout?")) {
            new LoginFrame().setVisible(true);
            this.dispose();
        }
    }
}
