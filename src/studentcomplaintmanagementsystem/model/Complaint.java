/*
 * Complaint Model Class
 * Represents a complaint record in the system
 */
package studentcomplaintmanagementsystem.model;

import java.util.Date;

public class Complaint {
    
    private int complaintId;
    private int studentId;
    private String studentName;
    private String studentEmail;
    private int categoryId;
    private String categoryName;
    private int statusId;
    private String statusName;
    private String complaintText;
    private Date complaintDate;
    
    // Default constructor
    public Complaint() {
    }
    
    // Constructor with all fields
    public Complaint(int complaintId, int studentId, String studentName, String studentEmail,
                     int categoryId, String categoryName, int statusId, String statusName,
                     String complaintText, Date complaintDate) {
        this.complaintId = complaintId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.statusId = statusId;
        this.statusName = statusName;
        this.complaintText = complaintText;
        this.complaintDate = complaintDate;
    }
    
    // Getters and Setters
    public int getComplaintId() {
        return complaintId;
    }
    
    public void setComplaintId(int complaintId) {
        this.complaintId = complaintId;
    }
    
    public int getStudentId() {
        return studentId;
    }
    
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    
    public String getStudentName() {
        return studentName;
    }
    
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    
    public String getStudentEmail() {
        return studentEmail;
    }
    
    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }
    
    public int getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public int getStatusId() {
        return statusId;
    }
    
    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }
    
    public String getStatusName() {
        return statusName;
    }
    
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
    
    public String getComplaintText() {
        return complaintText;
    }
    
    public void setComplaintText(String complaintText) {
        this.complaintText = complaintText;
    }
    
    public Date getComplaintDate() {
        return complaintDate;
    }
    
    public void setComplaintDate(Date complaintDate) {
        this.complaintDate = complaintDate;
    }
    
    @Override
    public String toString() {
        return "Complaint{" +
                "complaintId=" + complaintId +
                ", studentName='" + studentName + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", statusName='" + statusName + '\'' +
                ", complaintText='" + complaintText + '\'' +
                ", complaintDate=" + complaintDate +
                '}';
    }
}
