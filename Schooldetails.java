import java.sql.*;
import java.util.Scanner;

public class Schooldetails {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/StudentDB";
    private static final String JDBC_USER = "root"; // Replace with your MySQL username
    private static final String JDBC_PASSWORD = "Admin"; // Replace with your MySQL password

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

            // Insert student records
            insertStudentData(connection);

            // Get user input for searching
            System.out.println("Choose search criteria:");
            System.out.println("1. Search by student name");
            System.out.println("2. Search by date of birth range");
            System.out.println("3. Search by village name");
            System.out.print("Enter choice (1/2/3): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;

            switch (choice) {
                case 1:
                    // Search by student name
                    System.out.print("Enter student name to search: ");
                    String studentName = scanner.nextLine();
                    String queryName = "SELECT * FROM Students WHERE student_name LIKE ?";
                    preparedStatement = connection.prepareStatement(queryName);
                    preparedStatement.setString(1, "%" + studentName + "%");
                    break;

                case 2:
                    // Search by date of birth range
                    System.out.print("Enter start date (YYYY-MM-DD): ");
                    String startDate = scanner.nextLine();
                    System.out.print("Enter end date (YYYY-MM-DD): ");
                    String endDate = scanner.nextLine();
                    String queryDOB = "SELECT * FROM Students WHERE dob BETWEEN ? AND ?";
                    preparedStatement = connection.prepareStatement(queryDOB);
                    preparedStatement.setDate(1, Date.valueOf(startDate));
                    preparedStatement.setDate(2, Date.valueOf(endDate));
                    break;

                case 3:
                    // Search by village name
                    System.out.print("Enter village name to search: ");
                    String villageName = scanner.nextLine();
                    String queryVillage = "SELECT * FROM Students WHERE village LIKE ?";
                    preparedStatement = connection.prepareStatement(queryVillage);
                    preparedStatement.setString(1, "%" + villageName + "%");
                    break;

                default:
                    System.out.println("Invalid choice.");
                    return;
            }

            // Execute query and display results
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                System.out.println("No results found.");
            } else {
                while (resultSet.next()) {
                    System.out.println("Sl. No: " + resultSet.getInt("slno"));
                    System.out.println("Student Name: " + resultSet.getString("student_name"));
                    System.out.println("Class: " + resultSet.getString("class"));
                    System.out.println("ID No: " + resultSet.getString("idno"));
                    System.out.println("Age: " + resultSet.getInt("age"));
                    System.out.println("Phone No: " + resultSet.getString("phone_no"));
                    System.out.println("Village: " + resultSet.getString("village"));
                    System.out.println("Date of Birth: " + resultSet.getDate("dob"));
                    System.out.println("Gender: " + resultSet.getString("gender"));
                    System.out.println("Hostel/Day Scholar: " + resultSet.getString("hostel_or_day_scholar"));
                    System.out.println("Fees: " + resultSet.getBigDecimal("fees"));
                    System.out.println("Course: " + resultSet.getString("course"));
                    System.out.println("-----------------------------------");
                }
            }

            // Close resources
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private static void insertStudentData(Connection connection) throws SQLException {
        String[] studentData = {
            "('Akhil Reddy', '10', 'S1001', 15, '555-0101', 'Guntur', '2009-03-15', 'Male', 'Day Scholar', 1500.00, 'Mathematics')",
            "('Bhavani Devi', '10', 'S1002', 16, '555-0102', 'puttaparthi', '2008-06-22', 'Female', 'Hostel', 1600.00, 'Science')",
            "('Chaitanya Krishna', '11', 'S1003', 17, '555-0103', 'Anantapur', '2007-12-09', 'Male', 'Day Scholar', 1550.00, 'History')",
            "('Deepika Reddy', '11', 'S1004', 16, '555-0104', 'Nellore', '2008-07-01', 'Female', 'Hostel', 1650.00, 'Literature')",
            "('Eshwar Rao', '12', 'S1005', 18, '555-0105', 'Tirupati', '2006-11-23', 'Male', 'Day Scholar', 1700.00, 'Physics')",
            "('Falguni Sharma', '12', 'S1006', 17, '555-0106', 'Kurnool', '2007-09-16', 'Female', 'Hostel', 1750.00, 'Biology')",
            "('Gautam Reddy', '10', 'S1007', 15, '555-0107', 'Kadapa', '2009-05-30', 'Male', 'Day Scholar', 1500.00, 'Mathematics')",
            "('Haritha Varma', '10', 'S1008', 14, '555-0108', 'Anantapur', '2010-02-14', 'Female', 'Hostel', 1600.00, 'Science')",
            "('Ishaan Verma', '11', 'S1009', 17, '555-0109', 'kurnool', '2007-08-05', 'Male', 'Day Scholar', 1550.00, 'History')",
            "('Janaki Devi', '11', 'S1010', 16, '555-0110', 'Anantapur', '2008-10-30', 'Female', 'Hostel', 1650.00, 'Literature')",
            "('Karthik Reddy', '12', 'S1011', 18, '555-0111', 'kadapa', '2006-06-17', 'Male', 'Day Scholar', 1700.00, 'Physics')",
            "('Lakshmi Priya', '12', 'S1012', 17, '555-0112', 'Srikakulam', '2007-01-23', 'Female', 'Hostel', 1750.00, 'Biology')",
            "('Manoj Kumar', '10', 'S1013', 15, '555-0113', 'Banglore', '2009-11-08', 'Male', 'Day Scholar', 1500.00, 'Mathematics')",
            "('Neha Sharma', '10', 'S1014', 14, '555-0114', 'kadapa', '2010-03-22', 'Female', 'Hostel', 1600.00, 'Science')",
            "('Omkar Rao', '11', 'S1015', 17, '555-0115', 'Proddatur', '2007-05-15', 'Male', 'Day Scholar', 1550.00, 'History')",
            "('Pranavi Reddy', '11', 'S1016', 16, '555-0116', 'Banglore', '2008-06-28', 'Female', 'Hostel', 1650.00, 'Literature')",
            "('Qadir Khan', '12', 'S1017', 18, '555-0117', 'Nandyal', '2006-04-30', 'Male', 'Day Scholar', 1700.00, 'Physics')",
            "('Radhika Varma', '12', 'S1018', 17, '555-0118', 'puttaparthi', '2007-08-16', 'Female', 'Hostel', 1750.00, 'Biology')",
            "('Siddharth Reddy', '10', 'S1019', 15, '555-0119', 'Puttaparthi', '2009-10-23', 'Male', 'Day Scholar', 1500.00, 'Mathematics')",
            "('Tara Devi', '10', 'S1020', 14, '555-0120', 'kadapa', '2010-01-12', 'Female', 'Hostel', 1600.00, 'Science')",
            "('Uday Kiran', '11', 'S1021', 17, '555-0121', 'Banglore', '2007-07-04', 'Male', 'Day Scholar', 1550.00, 'History')",
            "('Varsha Reddy', '11', 'S1022', 16, '555-0122', 'Proddatur', '2008-09-15', 'Female', 'Hostel', 1650.00, 'Literature')",
            "('Waseem Khan', '12', 'S1023', 18, '555-0123', 'Proddatur', '2006-05-18', 'Male', 'Day Scholar', 1700.00, 'Physics')",
            "('Yamini Sharma', '12', 'S1024', 17, '555-0124', 'Bhavani Island', '2007-02-07', 'Female', 'Hostel', 1750.00, 'Biology')",
            "('Zaid Khan', '10', 'S1025', 15, '555-0125', 'Banglore', '2009-04-14', 'Male', 'Day Scholar', 1500.00, 'Mathematics')",
            "('Aarav Reddy', '10', 'S1026', 15, '555-0126', 'Proddatur', '2009-09-09', 'Male', 'Hostel', 1600.00, 'Science')",
            "('Bala Krishna', '11', 'S1027', 17, '555-0127', 'kadapa', '2007-06-27', 'Male', 'Day Scholar', 1550.00, 'History')",
            "('Charan Reddy', '11', 'S1028', 16, '555-0128', 'Banglore', '2008-11-02', 'Male', 'Hostel', 1650.00, 'Literature')",
            "('Dharani Devi', '12', 'S1029', 18, '555-0129', 'Anantapur', '2006-08-25', 'Female', 'Day Scholar', 1700.00, 'Physics')",
            "('Eswar Rao', '12', 'S1030', 17, '555-0130', 'kadapa', '2007-05-13', 'Male', 'Hostel', 1750.00, 'Biology')"
        };

        String insertSQL = "INSERT INTO Students (student_name, class, idno, age, phone_no, village, dob, gender, hostel_or_day_scholar, fees, course) VALUES ";

        for (String student : studentData) {
            insertSQL += student + ",";
        }

        // Remove trailing comma
        insertSQL = insertSQL.substring(0, insertSQL.length() - 1) + ";";

        Statement statement = connection.createStatement();
        statement.executeUpdate(insertSQL);
        statement.close();
    }
}