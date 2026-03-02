/*
 * Student Management Frame - Dark Theme
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

public class StudentManagementFrame extends JFrame {
    
    private AdminDashboard parentFrame;
    private JTable studentsTable, requestsTable;
    private DefaultTableModel studentsModel, requestsModel;
    private ModernButton btnApprove, btnReject, btnDeleteStudent, btnRefresh;
    private Vector<Integer> studentIds, requestIds;
    
    // Dark Theme Colors
    private final Color BG_DARK = new Color(10, 10, 20);
    private final Color PRIMARY = new Color(99, 102, 241);
    private final Color SUCCESS = new Color(34, 197, 94);
    private final Color DANGER = new Color(239, 68, 68);
    private final Color TEXT_PRIMARY = new Color(248, 250, 252);
    private final Color TEXT_MUTED = new Color(148, 163, 184);
    private final Color BORDER = new Color(55, 65, 81);
    private final Color TABLE_DARK = new Color(25, 30, 45);
    private final Color TABLE_ROW_DARK = new Color(30, 35, 50);
    
    public StudentManagementFrame(AdminDashboard parent) {
        this.parentFrame = parent;
        this.studentIds = new Vector<>();
        this.requestIds = new Vector<>();
        
        setTitle("Student Management");
        setSize(1100, 680);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setUndecorated(true);
        
        initComponents();
        loadPendingRequests();
        loadApprovedStudents();
        fadeIn();
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                goBack();
            }
        });
    }
    
    private void initComponents() {
        // Main panel
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
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 15, 30));
        
        JLabel lblTitle = new JLabel("Student Registration Management");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(TEXT_PRIMARY);
        
        ModernButton btnBack = new ModernButton("← Back", PRIMARY, PRIMARY.darker());
        btnBack.addActionListener(e -> goBack());
        
        headerPanel.add(lblTitle, BorderLayout.WEST);
        headerPanel.add(btnBack, BorderLayout.EAST);
        
        // Content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 25, 30));
        
        // Pending Requests Section
        JLabel lblRequests = new JLabel("Pending Registration Requests");
        lblRequests.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblRequests.setForeground(TEXT_PRIMARY);
        
        // Pending requests table
        String[] reqColumns = {"ID", "Name", "Email", "Department", "Request Date"};
        requestsModel = new DefaultTableModel(reqColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        requestsTable = new JTable(requestsModel);
        styleTable(requestsTable);
        
        JScrollPane reqScroll = new JScrollPane(requestsTable);
        reqScroll.setBackground(TABLE_DARK);
        reqScroll.setBorder(BorderFactory.createLineBorder(PRIMARY, 2));
        reqScroll.getViewport().setBackground(TABLE_DARK);
        
        // Approve/Reject buttons
        JPanel reqButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        reqButtons.setOpaque(false);
        
        btnApprove = new ModernButton("✓ Approve", SUCCESS, SUCCESS.darker());
        btnReject = new ModernButton("✗ Reject", DANGER, DANGER.darker());
        btnRefresh = new ModernButton("↻ Refresh", PRIMARY, PRIMARY.darker());
        
        btnApprove.setPreferredSize(new Dimension(140, 40));
        btnReject.setPreferredSize(new Dimension(140, 40));
        btnRefresh.setPreferredSize(new Dimension(140, 40));
        
        reqButtons.add(btnApprove);
        reqButtons.add(btnReject);
        reqButtons.add(btnRefresh);
        
        // Approved Students Section
        JLabel lblStudents = new JLabel("Approved Students");
        lblStudents.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblStudents.setForeground(TEXT_PRIMARY);
        lblStudents.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        
        // Students table
        String[] studentColumns = {"ID", "Name", "Email", "Department", "Status"};
        studentsModel = new DefaultTableModel(studentColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        studentsTable = new JTable(studentsModel);
        styleTable(studentsTable);
        
        JScrollPane studentScroll = new JScrollPane(studentsTable);
        studentScroll.setBackground(TABLE_DARK);
        studentScroll.setBorder(BorderFactory.createLineBorder(PRIMARY, 2));
        studentScroll.getViewport().setBackground(TABLE_DARK);
        
        // Delete button
        JPanel studentButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        studentButtons.setOpaque(false);
        
        btnDeleteStudent = new ModernButton("Delete Student", DANGER, DANGER.darker());
        btnDeleteStudent.setPreferredSize(new Dimension(160, 40));
        
        studentButtons.add(btnDeleteStudent);
        
        // Add to content
        contentPanel.add(lblRequests);
        contentPanel.add(reqScroll);
        contentPanel.add(reqButtons);
        contentPanel.add(lblStudents);
        contentPanel.add(studentScroll);
        contentPanel.add(studentButtons);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        wrapperPanel.add(new CustomTitleBar(this), BorderLayout.NORTH);
        wrapperPanel.add(mainPanel, BorderLayout.CENTER);
        
        add(wrapperPanel);
        
        // Actions
        btnApprove.addActionListener(e -> approveStudent());
        btnReject.addActionListener(e -> rejectStudent());
        btnRefresh.addActionListener(e -> { loadPendingRequests(); loadApprovedStudents(); });
        btnDeleteStudent.addActionListener(e -> deleteStudent());
    }
    
    private void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.setRowHeight(38);
        table.setSelectionBackground(PRIMARY);
        table.setSelectionForeground(TEXT_PRIMARY);
        table.setGridColor(BORDER);
        table.setShowGrid(true);
        table.setOpaque(true);
        table.setBackground(TABLE_DARK);
        
        // Apply Modern Renderer
        for (int i = 0; i < table.getColumnCount(); i++) {
            // Default to Center for ID/Date/Status, Left for Name/Email
            int align = (i == 1 || i == 2) ? SwingConstants.LEFT : SwingConstants.CENTER;
            table.getColumnModel().getColumn(i).setCellRenderer(new ModernTableRenderer(align));
        }
        
        // Dark headers
        table.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
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
        table.getTableHeader().setPreferredSize(new Dimension(0, 45));
        table.getTableHeader().setBorder(BorderFactory.createLineBorder(BORDER, 2));
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
    
    private void loadPendingRequests() {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT student_id, name, email, department, registration_date " +
                        "FROM student WHERE approval_status = 'Pending' ORDER BY registration_date DESC";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            
            requestsModel.setRowCount(0);
            requestIds.clear();
            
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("student_id"));
                row.add(rs.getString("name"));
                row.add(rs.getString("email"));
                row.add(rs.getString("department"));
                row.add(rs.getDate("registration_date"));
                requestsModel.addRow(row);
                requestIds.add(rs.getInt("student_id"));
            }
            
            rs.close();
            pstmt.close();
            conn.close();
            
        } catch (Exception ex) {
            Toast.show(this, "Error loading requests: " + ex.getMessage(), false);
        }
    }
    
    private void loadApprovedStudents() {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT student_id, name, email, department, approval_status " +
                        "FROM student WHERE approval_status = 'Approved' ORDER BY name";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            
            studentsModel.setRowCount(0);
            studentIds.clear();
            
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("student_id"));
                row.add(rs.getString("name"));
                row.add(rs.getString("email"));
                row.add(rs.getString("department"));
                row.add(rs.getString("approval_status"));
                studentsModel.addRow(row);
                studentIds.add(rs.getInt("student_id"));
            }
            
            rs.close();
            pstmt.close();
            conn.close();
            
        } catch (Exception ex) {
            Toast.show(this, "Error loading students: " + ex.getMessage(), false);
        }
    }
    
    private void approveStudent() {
        int selectedRow = requestsTable.getSelectedRow();
        if (selectedRow < 0) {
            Toast.show(this, "Please select a request to approve.", false);
            return;
        }
        
        int studentId = requestIds.get(selectedRow);
        String studentName = (String) requestsModel.getValueAt(selectedRow, 1);
        
        if (ModernConfirmDialog.show(this, "Approve registration for " + studentName + "?")) {
            try {
                Connection conn = DBConnection.getConnection();
                String sql = "UPDATE student SET approval_status = 'Approved' WHERE student_id = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, studentId);
                pstmt.executeUpdate();
                pstmt.close();
                conn.close();
                
                Toast.show(this, "Student approved successfully!", true);
                loadPendingRequests();
                loadApprovedStudents();
                
            } catch (Exception ex) {
                Toast.show(this, "Database Error: " + ex.getMessage(), false);
            }
        }
    }
    
    private void rejectStudent() {
        int selectedRow = requestsTable.getSelectedRow();
        if (selectedRow < 0) {
            Toast.show(this, "Please select a request to reject.", false);
            return;
        }
        
        int studentId = requestIds.get(selectedRow);
        String studentName = (String) requestsModel.getValueAt(selectedRow, 1);
        
        if (ModernConfirmDialog.show(this, "Reject and delete registration for " + studentName + "?")) {
            try {
                Connection conn = DBConnection.getConnection();
                String sql = "DELETE FROM student WHERE student_id = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, studentId);
                pstmt.executeUpdate();
                pstmt.close();
                conn.close();
                
                Toast.show(this, "Request rejected and deleted.", true);
                loadPendingRequests();
                
            } catch (Exception ex) {
                Toast.show(this, "Database Error: " + ex.getMessage(), false);
            }
        }
    }
    
    private void deleteStudent() {
        int selectedRow = studentsTable.getSelectedRow();
        if (selectedRow < 0) {
            Toast.show(this, "Please select a student to delete.", false);
            return;
        }
        
        int studentId = studentIds.get(selectedRow);
        String studentName = (String) studentsModel.getValueAt(selectedRow, 1);
        
        if (ModernConfirmDialog.show(this, "Delete student " + studentName + "?<br>This will also delete all their complaints.")) {
            try {
                Connection conn = DBConnection.getConnection();
                
                String sql1 = "DELETE FROM complaint WHERE student_id = ?";
                PreparedStatement pstmt1 = conn.prepareStatement(sql1);
                pstmt1.setInt(1, studentId);
                pstmt1.executeUpdate();
                pstmt1.close();
                
                String sql2 = "DELETE FROM student WHERE student_id = ?";
                PreparedStatement pstmt2 = conn.prepareStatement(sql2);
                pstmt2.setInt(1, studentId);
                pstmt2.executeUpdate();
                pstmt2.close();
                
                conn.close();
                
                Toast.show(this, "Student deleted successfully!", true);
                loadApprovedStudents();
                
            } catch (Exception ex) {
                Toast.show(this, "Database Error: " + ex.getMessage(), false);
            }
        }
    }
    
    private void goBack() {
        parentFrame.setVisible(true);
        this.dispose();
    }
}
