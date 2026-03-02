-- ============================================================
-- Student Complaint Management System - Database Setup Script
-- Oracle 23ai Free Edition
-- ============================================================

-- ============================================================
-- 1. DROP EXISTING TABLES (in correct order)
-- ============================================================

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE complaint';
EXCEPTION
    WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE status';
EXCEPTION
    WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE category';
EXCEPTION
    WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE admin';
EXCEPTION
    WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE student';
EXCEPTION
    WHEN OTHERS THEN NULL;
END;
/

-- ============================================================
-- 2. CREATE TABLES
-- ============================================================

-- Student Table (Parent)
CREATE TABLE student (
    student_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR2(100) NOT NULL,
    email VARCHAR2(100) UNIQUE NOT NULL,
    password VARCHAR2(100) NOT NULL,
    department VARCHAR2(50)
);

-- Admin Table
CREATE TABLE admin (
    admin_id NUMBER PRIMARY KEY,
    username VARCHAR2(50) UNIQUE NOT NULL,
    password VARCHAR2(100) NOT NULL
);

-- Category Table
CREATE TABLE category (
    category_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    category_name VARCHAR2(50) NOT NULL
);

-- Status Table
CREATE TABLE status (
    status_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    status_name VARCHAR2(50) NOT NULL
);

-- Complaint Table (Child)
CREATE TABLE complaint (
    complaint_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    student_id NUMBER NOT NULL,
    category_id NUMBER NOT NULL,
    status_id NUMBER NOT NULL,
    complaint_text VARCHAR2(500) NOT NULL,
    complaint_date DATE DEFAULT SYSDATE,
    FOREIGN KEY (student_id) REFERENCES student(student_id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES category(category_id) ON DELETE CASCADE,
    FOREIGN KEY (status_id) REFERENCES status(status_id) ON DELETE CASCADE
);

-- ============================================================
-- 3. INSERT CATEGORIES
-- ============================================================
INSERT INTO category (category_name) VALUES ('Hostel');
INSERT INTO category (category_name) VALUES ('Lab');
INSERT INTO category (category_name) VALUES ('Library');
INSERT INTO category (category_name) VALUES ('Classroom');
INSERT INTO category (category_name) VALUES ('Canteen');
INSERT INTO category (category_name) VALUES ('Transport');
INSERT INTO category (category_name) VALUES ('Other');

-- ============================================================
-- 4. INSERT STATUS
-- ============================================================
INSERT INTO status (status_name) VALUES ('Pending');
INSERT INTO status (status_name) VALUES ('In Progress');
INSERT INTO status (status_name) VALUES ('Resolved');

-- ============================================================
-- 5. INSERT TEST DATA
-- ============================================================

-- Student Test Data
INSERT INTO student (name, email, password, department) VALUES ('Tony', 'tony@gmail.com', '1234', 'Computer Science');
INSERT INTO student (name, email, password, department) VALUES ('John Doe', 'john@gmail.com', '1234', 'Information Technology');
INSERT INTO student (name, email, password, department) VALUES ('Jane Smith', 'jane@gmail.com', '1234', 'Computer Science');

-- Admin Test Data
INSERT INTO admin (admin_id, username, password) VALUES (1, 'admin', 'admin123');

-- Complaint Test Data
INSERT INTO complaint (student_id, category_id, status_id, complaint_text, complaint_date) 
VALUES (1, 1, 1, 'Hostel room AC not working properly', SYSDATE - 5);

INSERT INTO complaint (student_id, category_id, status_id, complaint_text, complaint_date) 
VALUES (1, 2, 2, 'Computer lab printer not functioning', SYSDATE - 3);

INSERT INTO complaint (student_id, category_id, status_id, complaint_text, complaint_date) 
VALUES (2, 3, 1, 'Need more books in library section', SYSDATE - 2);

INSERT INTO complaint (student_id, category_id, status_id, complaint_text, complaint_date) 
VALUES (3, 4, 3, 'Classroom projector issues resolved', SYSDATE - 1);

-- ============================================================
-- 6. COMMIT CHANGES
-- ============================================================
COMMIT;

-- ============================================================
-- VERIFICATION QUERIES
-- ============================================================

SELECT * FROM student;
SELECT * FROM admin;
SELECT * FROM category;
SELECT * FROM status;
SELECT * FROM complaint;

SELECT c.complaint_id, s.name as student_name, s.email, 
       cat.category_name, st.status_name, c.complaint_text, c.complaint_date
FROM complaint c
JOIN student s ON c.student_id = s.student_id
JOIN category cat ON c.category_id = cat.category_id
JOIN status st ON c.status_id = st.status_id
ORDER BY c.complaint_date DESC;

-- ============================================================
-- END OF SCRIPT
-- ============================================================
