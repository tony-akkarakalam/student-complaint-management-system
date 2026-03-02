# Student Complaint Management System

## Project Overview
A complete Java Swing-based application for managing student complaints with Oracle database connectivity.

---

## User Roles & Functionality

### 1. STUDENT Role
**Login Credentials:**
- Email: tony@gmail.com
- Password: 1234

**Features Available:**
- ✅ **Login** - Authenticate using email and password
- ✅ **Register Complaint** - Submit new complaints with:
  - Category selection (Hostel, Lab, Library, Classroom, Canteen, Transport, Other)
  - Complaint description (max 500 characters)
  - Automatic date stamping
  - Default status: "Pending"
- ✅ **View My Complaints** - See all your submitted complaints with:
  - Complaint ID
  - Category
  - Status (Pending/In Progress/Resolved)
  - Complaint description
  - Submission date
- ✅ **Refresh** - Update the complaint list
- ✅ **Logout** - Exit to login screen

---

### 2. ADMIN Role
**Login Credentials:**
- Username: admin
- Password: admin123

**Features Available:**
- ✅ **Login** - Authenticate using username and password
- ✅ **View All Complaints** - See ALL student complaints with:
  - Complaint ID
  - Student Name
  - Student Email
  - Category
  - Status
  - Complaint description
  - Date
- ✅ **Update Status** - Change complaint status:
  - Pending → In Progress → Resolved
- ✅ **Delete Complaint** - Remove complaints from the system
- ✅ **Refresh** - Update the complaint table
- ✅ **Logout** - Exit to login screen

---

## Database Tables

### 1. STUDENT (Parent Table)
| Column | Type | Description |
|--------|------|-------------|
| student_id | NUMBER | Primary Key (Auto-generated) |
| name | VARCHAR2(100) | Student name |
| email | VARCHAR2(100) | Unique email |
| password | VARCHAR2(100) | Login password |
| department | VARCHAR2(50) | Department |

### 2. ADMIN
| Column | Type | Description |
|--------|------|-------------|
| admin_id | NUMBER | Primary Key |
| username | VARCHAR2(50) | Unique username |
| password | VARCHAR2(100) | Login password |

### 3. CATEGORY
| Column | Type | Description |
|--------|------|-------------|
| category_id | NUMBER | Primary Key |
| category_name | VARCHAR2(50) | Category name |

### 4. STATUS
| Column | Type | Description |
|--------|------|-------------|
| status_id | NUMBER | Primary Key |
| status_name | VARCHAR2(50) | Status name |

### 5. COMPLAINT (Child Table)
| Column | Type | Description |
|--------|------|-------------|
| complaint_id | NUMBER | Primary Key |
| student_id | NUMBER | Foreign Key → Student |
| category_id | NUMBER | Foreign Key → Category |
| status_id | NUMBER | Foreign Key → Status |
| complaint_text | VARCHAR2(500) | Complaint description |
| complaint_date | DATE | Submission date |

---

## Project Structure

```
StudentComplaintManagementSystem/
├── src/studentcomplaintmanagementsystem/
│   ├── db/
│   │   └── DBConnection.java          # Database connection
│   ├── model/
│   │   └── Complaint.java              # Model class
│   ├── ui/
│   │   ├── LoginFrame.java             # Login screen
│   │   ├── StudentDashboard.java       # Student dashboard
│   │   ├── RegisterComplaintFrame.java # Register complaint
│   │   └── AdminDashboard.java         # Admin dashboard
│   ├── Main.java                       # Entry point
│   └── StudentComplaintManagementSystem.java
├── DATABASE_SETUP.sql                  # Database setup script
└── nbproject/                         # NetBeans project files
```

---

## Technology Stack
- **IDE:** Apache NetBeans 28
- **Java:** Version 17
- **GUI:** Swing (JFrame)
- **Database:** Oracle 23ai Free
- **JDBC Driver:** ojdbc11.jar
- **Architecture:** Layered (UI → DBConnection → Oracle DB)

---

## How to Run

1. **Setup Database:**
   - Run `DATABASE_SETUP.sql` in Oracle SQL Developer
   - Creates all tables and inserts test data

2. **Run Application:**
   - Open project in NetBeans
   - Clean and Build (Shift+F11)
   - Run (F6)

3. **Login:**
   - Select "Student" role → Enter: tony@gmail.com / 1234
   - Select "Admin" role → Enter: admin / admin123

---

## Features Implemented
- ✅ Login with role selection (Student/Admin)
- ✅ Professional modern UI design
- ✅ Responsive window with minimize/maximize/close
- ✅ Oracle database connectivity
- ✅ CRUD operations (Create, Read, Update, Delete)
- ✅ Parent-Child table relationships
- ✅ PreparedStatement for SQL security
- ✅ Proper error handling and validation

---

## Color Scheme
- **Primary:** Dark Blue (#1E3A8A)
- **Secondary:** Blue (#3B82F6)
- **Success:** Green (#22C55E)
- **Danger:** Red (#EF4444)
- **Background:** Light Gray (#F0F4F8)
- **Text:** Dark Gray (#1E293B)

---

*Project Created for Java Subject - NetBeans IDE*
