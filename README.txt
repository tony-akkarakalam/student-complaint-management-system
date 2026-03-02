================================================================================
STUDENT COMPLAINT MANAGEMENT SYSTEM - SETUP GUIDE
================================================================================

This guide will help you set up and run the application from scratch.

================================================================================
PREREQUISITES
================================================================================

Before starting, make sure you have:
1. Apache NetBeans 28 installed
2. Java 17 installed
3. Oracle 23ai Free installed and running
4. Oracle SQL Developer installed (comes with Oracle)

================================================================================
STEP 1: SET UP ORACLE DATABASE
================================================================================

1. Open Oracle SQL Developer
   - Search for "SQL Developer" in your computer or find it in Oracle folder

2. Create a new connection:
   - Click the green "+" icon or go to File → New Connection
   - Fill in the details:
     * Connection Name: MyOracleDB
     * Username: C##TONY17
     * Password: 7897
     * Hostname: localhost
     * Port: 1521
     * SID: FREE
   - Click "Test" - it should say "Status: Success"
   - Click "Connect"

3. Run the SQL setup script:
   - In SQL Developer, go to File → Open
   - Navigate to: d:/RCSS/SEM 2/JAVA/Project/StudentComplaintManagementSystem/
   - Select: DATABASE_SETUP.sql
   - Click "Run Statement" (green play button) or press F9
   - You should see messages like "Table created" and "1 row inserted"

4. Verify the data was inserted:
   - Run this query to check:
     SELECT * FROM student;
   - You should see student records

================================================================================
STEP 2: OPEN PROJECT IN NETBEANS
================================================================================

1. Open Apache NetBeans

2. Open the project:
   - Go to File → Open Project (Ctrl+Shift+O)
   - Navigate to: d:/RCSS/SEM 2/JAVA/Project/StudentComplaintManagementSystem
   - Click "Open Project"
   - You should see the project appear in the Projects panel (left side)

3. Fix any missing library (if shown):
   - Right-click on the project → Properties
   - Go to Libraries
   - If ojdbc11.jar shows as missing, browse to:
     D:\RCSS\SEM 2\JAVA\Project\ojdbc11-full\ojdbc11.jar
   - Click OK

================================================================================
STEP 3: BUILD THE PROJECT
================================================================================

1. In NetBeans:
   - Right-click on the project name in the Projects panel
   - Select "Clean and Build" (or press Shift+F11)
   - Wait for the build to complete
   - Check the "Output" window at the bottom - it should say "BUILD SUCCESSFUL"

================================================================================
STEP 4: RUN THE APPLICATION
================================================================================

1. After successful build:
   - Right-click on the project name
   - Select "Run" (or press F6)

2. The Login window should appear!

================================================================================
STEP 5: TEST THE APPLICATION
================================================================================

TEST AS STUDENT:
---------------
1. In the Login window:
   - Username: tony@gmail.com
   - Password: 1234
   - Role: Select "Student"
   - Click "Login"

2. You should see the Student Dashboard with:
   - Your name (Tony)
   - Table showing your complaints
   - Buttons: "Register Complaint", "View My Complaints", "Logout"

3. Try registering a new complaint:
   - Click "Register Complaint"
   - Select a Category (e.g., "Hostel")
   - Type a complaint in the text area
   - Click "Submit"
   - Your complaint should appear in the table

4. Click "Logout" to return to login screen


TEST AS ADMIN:
-------------
1. In the Login window:
   - Username: admin
   - Password: admin123
   - Role: Select "Admin"
   - Click "Login"

2. You should see the Admin Dashboard with:
   - Table showing ALL student complaints
   - Dropdown to change status
   - Buttons: "Update Status", "Delete", "Refresh", "Logout"

3. Try updating a complaint status:
   - Select a row in the table
   - Choose a new status from the dropdown (e.g., "In Progress")
   - Click "Update Status"
   - You should see a success message

4. Try deleting a complaint:
   - Select a row in the table
   - Click "Delete"
   - Confirm the deletion

5. Click "Logout" to return to login screen

================================================================================
TROUBLESHOOTING
================================================================================

PROBLEM: "Cannot connect to database"
SOLUTION: 
- Make sure Oracle 23ai Free is running
- Check that the database service is started

PROBLEM: "ojdbc11.jar not found"
SOLUTION:
- Right-click project → Properties → Libraries
- Add the jar file from: D:\RCSS\SEM 2\JAVA\Project\ojdbc11-full\ojdbc11.jar

PROBLEM: "Login failed"
SOLUTION:
- Make sure you ran the DATABASE_SETUP.sql script
- Check the test data exists in the database

PROBLEM: "Package does not exist" errors
SOLUTION:
- This is just NetBeans being slow to index
- Try: Right-click project → Clean → Build
- Wait a few seconds for NetBeans to finish indexing

================================================================================
WHAT THE APPLICATION DOES
================================================================================

1. LOGIN: Users can login as Student or Admin
2. STUDENT: Can register new complaints and view their own complaints
3. ADMIN: Can view all complaints, update status, and delete complaints

The data is stored in Oracle database and persists even after closing the app.

================================================================================
END OF GUIDE
================================================================================
