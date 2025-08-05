import java.sql.*;
import java.util.Scanner;

public class StudentManagement {
    static final String DB_URL = "jdbc:mysql://localhost:3306/student_db";
    static final String USER = "root";
    static final String PASS = "Saurav@89"; // <-- Replace with your MySQL password

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n===== Student Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addStudent(sc);
                case 2 -> viewStudents();
                case 3 -> updateStudent(sc);
                case 4 -> deleteStudent(sc);
                case 5 -> {
                    System.out.println("Exiting... Bye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }

    static void addStudent(Scanner sc) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            System.out.print("Enter name: ");
            String name = sc.nextLine();
            System.out.print("Enter email: ");
            String email = sc.nextLine();
            System.out.print("Enter age: ");
            int age = sc.nextInt();
            sc.nextLine();

            String sql = "INSERT INTO students (name, email, age) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setInt(3, age);
            ps.executeUpdate();

            System.out.println("Student added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void viewStudents() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT * FROM students";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("\n--- Student List ---");
            while (rs.next()) {
                System.out.printf("ID: %d | Name: %s | Email: %s | Age: %d\n",
                        rs.getInt("id"), rs.getString("name"),
                        rs.getString("email"), rs.getInt("age"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void updateStudent(Scanner sc) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            System.out.print("Enter ID to update: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("New name: ");
            String name = sc.nextLine();
            System.out.print("New email: ");
            String email = sc.nextLine();
            System.out.print("New age: ");
            int age = sc.nextInt();
            sc.nextLine();

            String sql = "UPDATE students SET name=?, email=?, age=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setInt(3, age);
            ps.setInt(4, id);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Student updated successfully!");
            } else {
                System.out.println("Student not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void deleteStudent(Scanner sc) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            System.out.print("Enter ID to delete: ");
            int id = sc.nextInt();
            sc.nextLine();

            String sql = "DELETE FROM students WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Student deleted successfully!");
            } else {
                System.out.println("Student not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
