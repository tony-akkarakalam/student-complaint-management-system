/*
 * Admin Dashboard - Dark Theme
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

public class AdminDashboard extends JFrame {
    
    private JTable complaintsTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> cmbStatus;
    private ModernButton btnUpdate, btnDelete, btnRefresh, btnLogout, btnManageStudents;
    private Vector<Integer> statusIds;
    private Vector<Integer> complaintIds;
    
    // Dark Theme Colors
    private final Color BG_DARK = new Color(10, 10, 20);
    private final Color BG_CARD = new Color(20, 25, 35);
    private final Color PRIMARY = new Color(99, 102, 241);
    private final Color SUCCESS = new Color(34, 197, 94);
    private final Color DANGER = new Color(239, 68, 68);
    private final Color PURPLE = new Color(139, 92, 246);
    private final Color TEXT_PRIMARY = new Color(248, 250, 252);
    private final Color TEXT_MUTED = new Color(148, 163, 184);
    private final Color BORDER = new Color(55, 65, 81);
    private final Color TABLE_DARK = new Color(25, 30, 45);
    private final Color TABLE_ROW_DARK = new Color(30, 35, 50);
    
    public AdminDashboard() {
        setTitle("Admin Dashboard - Complaint Management");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setUndecorated(true);
        
        statusIds = new Vector<>();
        complaintIds = new Vector<>();
        
        initComponents();
        loadComplaints();
        loadStatus();
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
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 20, 30));
        
        JLabel lblTitle = new JLabel("Admin Dashboard");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(TEXT_PRIMARY);
        
        JLabel lblSub = new JLabel("Manage Student Complaints & Registrations");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblSub.setForeground(TEXT_MUTED);
        
        headerPanel.add(lblTitle);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(lblSub);
        
        // Content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 25, 30));
        
        // Table title
        JLabel lblTableTitle = new JLabel("All Complaints");
        lblTableTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTableTitle.setForeground(TEXT_PRIMARY);
        
        // Table - Dark Theme
        String[] columns = {"ID", "Student Name", "Email", "Category", "Status", "Complaint", "Date"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        complaintsTable = new JTable(tableModel);
        complaintsTable.setFont(new Font("Segoe UI", Font.BOLD, 12));
        complaintsTable.setRowHeight(38);
        complaintsTable.setSelectionBackground(PRIMARY);
        complaintsTable.setSelectionForeground(TEXT_PRIMARY);
        complaintsTable.setGridColor(BORDER);
        complaintsTable.setShowGrid(true);
        complaintsTable.setOpaque(true);
        complaintsTable.setBackground(TABLE_DARK);
        
        // Apply Modern Renderer with specific alignments
        complaintsTable.getColumnModel().getColumn(0).setCellRenderer(new ModernTableRenderer(SwingConstants.CENTER)); // ID
        complaintsTable.getColumnModel().getColumn(1).setCellRenderer(new ModernTableRenderer(SwingConstants.LEFT));   // Name
        complaintsTable.getColumnModel().getColumn(2).setCellRenderer(new ModernTableRenderer(SwingConstants.LEFT));   // Email
        complaintsTable.getColumnModel().getColumn(3).setCellRenderer(new ModernTableRenderer(SwingConstants.CENTER)); // Category
        complaintsTable.getColumnModel().getColumn(4).setCellRenderer(new ModernTableRenderer(SwingConstants.CENTER)); // Status
        complaintsTable.getColumnModel().getColumn(5).setCellRenderer(new ModernTableRenderer(SwingConstants.LEFT));   // Complaint
        complaintsTable.getColumnModel().getColumn(6).setCellRenderer(new ModernTableRenderer(SwingConstants.CENTER)); // Date
        
        // Dark column headers
        complaintsTable.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(new Color(30, 35, 55));
                c.setForeground(TEXT_PRIMARY);
                c.setFont(new Font("Segoe UI", Font.BOLD, 13));
                ((JLabel)c).setHorizontalAlignment(JLabel.CENTER);
                return c;
            }
        });
        complaintsTable.getTableHeader().setPreferredSize(new Dimension(0, 45));
        complaintsTable.getTableHeader().setBorder(BorderFactory.createLineBorder(BORDER, 2));
        
        // Column widths
        complaintsTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        complaintsTable.getColumnModel().getColumn(1).setPreferredWidth(120);
        complaintsTable.getColumnModel().getColumn(2).setPreferredWidth(160);
        complaintsTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        complaintsTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        complaintsTable.getColumnModel().getColumn(5).setPreferredWidth(250);
        complaintsTable.getColumnModel().getColumn(6).setPreferredWidth(100);
        
        JScrollPane scrollPane = new JScrollPane(complaintsTable);
        scrollPane.setBackground(TABLE_DARK);
        scrollPane.setBorder(BorderFactory.createLineBorder(PRIMARY, 2));
        scrollPane.getViewport().setBackground(TABLE_DARK);
        
        // Action panel
        JPanel actionPanel = new JPanel(new BorderLayout(15, 0));
        actionPanel.setOpaque(false);
        actionPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        leftPanel.setOpaque(false);
        
        JLabel lblStatus = new JLabel("Change Status:");
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblStatus.setForeground(TEXT_PRIMARY);
        
        cmbStatus = new JComboBox<>(new String[]{});
        cmbStatus.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbStatus.setBackground(Color.WHITE);
        cmbStatus.setForeground(Color.BLACK);
        cmbStatus.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1),
            BorderFactory.createEmptyBorder(0, 15, 0, 15)
        ));
        cmbStatus.setPreferredSize(new Dimension(170, 42));
        
        leftPanel.add(lblStatus);
        leftPanel.add(cmbStatus);
        
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightPanel.setOpaque(false);
        
        btnUpdate = new ModernButton("Update Status", SUCCESS, SUCCESS.darker());
        btnDelete = new ModernButton("Delete", DANGER, DANGER.darker());
        btnManageStudents = new ModernButton("Students", PURPLE, PURPLE.darker());
        btnRefresh = new ModernButton("Refresh", PRIMARY, PRIMARY.darker());
        btnLogout = new ModernButton("Logout", new Color(75, 85, 99), new Color(90, 100, 115));
        
        btnUpdate.setPreferredSize(new Dimension(140, 42));
        btnDelete.setPreferredSize(new Dimension(130, 42));
        btnManageStudents.setPreferredSize(new Dimension(130, 42));
        btnRefresh.setPreferredSize(new Dimension(130, 42));
        btnLogout.setPreferredSize(new Dimension(130, 42));

        rightPanel.add(btnUpdate);
        rightPanel.add(btnDelete);
        rightPanel.add(btnManageStudents);
        rightPanel.add(btnRefresh);
        rightPanel.add(btnLogout);
        
        actionPanel.add(leftPanel, BorderLayout.WEST);
        actionPanel.add(rightPanel, BorderLayout.EAST);
        
        contentPanel.add(lblTableTitle);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(scrollPane);
        contentPanel.add(actionPanel);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        wrapperPanel.add(new CustomTitleBar(this), BorderLayout.NORTH);
        wrapperPanel.add(mainPanel, BorderLayout.CENTER);
        
        add(wrapperPanel);
        
        btnUpdate.addActionListener(e -> updateComplaintStatus());
        btnDelete.addActionListener(e -> deleteComplaint());
        btnManageStudents.addActionListener(e -> openStudentManagement());
        btnRefresh.addActionListener(e -> loadComplaints());
        btnLogout.addActionListener(e -> logoutUser());
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
    
    private void loadStatus() {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT status_id, status_name FROM status ORDER BY status_id";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            
            statusIds.clear();
            cmbStatus.removeAllItems();
            
            while (rs.next()) {
                statusIds.add(rs.getInt("status_id"));
                cmbStatus.addItem(rs.getString("status_name"));
            }
            
            rs.close();
            pstmt.close();
            conn.close();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void loadComplaints() {
        try {
            Connection conn = DBConnection.getConnection();
            
            String sql = "SELECT c.complaint_id, s.name as student_name, s.email, " +
                        "cat.category_name, st.status_name, c.complaint_text, c.complaint_date " +
                        "FROM complaint c " +
                        "JOIN student s ON c.student_id = s.student_id " +
                        "JOIN category cat ON c.category_id = cat.category_id " +
                        "JOIN status st ON c.status_id = st.status_id " +
                        "ORDER BY c.complaint_date DESC";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            
            tableModel.setRowCount(0);
            complaintIds.clear();
            
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("complaint_id"));
                row.add(rs.getString("student_name"));
                row.add(rs.getString("email"));
                row.add(rs.getString("category_name"));
                row.add(rs.getString("status_name"));
                row.add(rs.getString("complaint_text"));
                row.add(rs.getDate("complaint_date"));
                tableModel.addRow(row);
                complaintIds.add(rs.getInt("complaint_id"));
            }
            
            rs.close();
            pstmt.close();
            conn.close();
            
        } catch (Exception ex) {
            Toast.show(this, "Database Error: " + ex.getMessage(), false);
        }
    }
    
    private void updateComplaintStatus() {
        int selectedRow = complaintsTable.getSelectedRow();
        if (selectedRow < 0) {
            Toast.show(this, "Please select a complaint to update.", false);
            return;
        }
        
        int selectedStatus = cmbStatus.getSelectedIndex();
        if (selectedStatus < 0) {
            Toast.show(this, "Please select a status.", false);
            return;
        }
        
        int complaintId = complaintIds.get(selectedRow);
        
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "UPDATE complaint SET status_id = ? WHERE complaint_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, statusIds.get(selectedStatus));
            pstmt.setInt(2, complaintId);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            
            Toast.show(this, "Status updated successfully!", true);
            loadComplaints();
            
        } catch (Exception ex) {
            Toast.show(this, "Database Error: " + ex.getMessage(), false);
        }
    }
    
    private void deleteComplaint() {
        int selectedRow = complaintsTable.getSelectedRow();
        if (selectedRow < 0) {
            Toast.show(this, "Please select a complaint to delete.", false);
            return;
        }
        
        if (ModernConfirmDialog.show(this, "Are you sure you want to delete this complaint?")) {
            int complaintId = complaintIds.get(selectedRow);
            
            try {
                Connection conn = DBConnection.getConnection();
                String sql = "DELETE FROM complaint WHERE complaint_id = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, complaintId);
                pstmt.executeUpdate();
                pstmt.close();
                conn.close();
                
                Toast.show(this, "Complaint deleted successfully!", true);
                loadComplaints();
                
            } catch (Exception ex) {
                Toast.show(this, "Database Error: " + ex.getMessage(), false);
            }
        }
    }
    
    private void openStudentManagement() {
        new StudentManagementFrame(this).setVisible(true);
        this.setVisible(false);
    }
    
    private void logoutUser() {
        if (ModernConfirmDialog.show(this, "Are you sure you want to logout?")) {
            new LoginFrame().setVisible(true);
            this.dispose();
        }
    }
}
