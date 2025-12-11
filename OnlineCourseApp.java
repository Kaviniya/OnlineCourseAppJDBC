import java.sql.*;
import java.util.Scanner;

public class OnlineCourseApp {
    static final String DB_URL = "jdbc:mysql://localhost:3306/online_course_db";
    static final String USER = "root"; // change if needed
    static final String PASS = "kavi@123";     // change if needed

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            while (true) {
                System.out.println("\n--- Online Course Registration ---");
                System.out.println("1. Manage Students");
                System.out.println("2. Manage Courses");
                System.out.println("3. Enrollment");
                System.out.println("4. Reports");
                System.out.println("5. Exit");
                System.out.print("Choose: ");
                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1 -> manageStudents(conn);
                    case 2 -> manageCourses(conn);
                    case 3 -> manageEnrollment(conn);
                    case 4 -> reports(conn);
                    case 5 -> {
                        System.out.println("Exiting...");
                        return;
                    }
                    default -> System.out.println("Invalid choice!");
                }
            }
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
    }

    // --- Student Management ---
    private static void manageStudents(Connection conn) throws SQLException {
        while (true) {
            System.out.println("\n--- Manage Students ---");
            System.out.println("1. Add Student");
            System.out.println("2. Update Student");
            System.out.println("3. Delete Student");
            System.out.println("4. View Students");
            System.out.println("5. Back");
            System.out.print("Choose: ");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1 -> {
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    String sql = "INSERT INTO students (name, email) VALUES (?, ?)";
                    try (PreparedStatement pst = conn.prepareStatement(sql)) {
                        pst.setString(1, name);
                        pst.setString(2, email);
                        pst.executeUpdate();
                        System.out.println("Student added!");
                    }
                }
                case 2 -> {
                    System.out.print("Student ID to update: ");
                    int id = Integer.parseInt(sc.nextLine());
                    System.out.print("New Name: ");
                    String name = sc.nextLine();
                    System.out.print("New Email: ");
                    String email = sc.nextLine();
                    String sql = "UPDATE students SET name=?, email=? WHERE student_id=?";
                    try (PreparedStatement pst = conn.prepareStatement(sql)) {
                        pst.setString(1, name);
                        pst.setString(2, email);
                        pst.setInt(3, id);
                        int rows = pst.executeUpdate();
                        if (rows > 0) System.out.println("Student updated!");
                        else System.out.println("Student not found!");
                    }
                }
                case 3 -> {
                    System.out.print("Student ID to delete: ");
                    int id = Integer.parseInt(sc.nextLine());
                    String sql = "DELETE FROM students WHERE student_id=?";
                    try (PreparedStatement pst = conn.prepareStatement(sql)) {
                        pst.setInt(1, id);
                        int rows = pst.executeUpdate();
                        if (rows > 0) System.out.println("Student deleted!");
                        else System.out.println("Student not found!");
                    }
                }
                case 4 -> {
                    String sql = "SELECT * FROM students";
                    try (Statement st = conn.createStatement();
                         ResultSet rs = st.executeQuery(sql)) {
                        System.out.println("ID\tName\tEmail");
                        while (rs.next()) {
                            System.out.println(rs.getInt("student_id") + "\t" +
                                    rs.getString("name") + "\t" +
                                    rs.getString("email"));
                        }
                    }
                }
                case 5 -> { return; }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    // --- Course Management ---
    private static void manageCourses(Connection conn) throws SQLException {
        while (true) {
            System.out.println("\n--- Manage Courses ---");
            System.out.println("1. Add Course");
            System.out.println("2. Update Course");
            System.out.println("3. Delete Course");
            System.out.println("4. View Courses");
            System.out.println("5. Back");
            System.out.print("Choose: ");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1 -> {
                    System.out.print("Course Name: ");
                    String name = sc.nextLine();
                    System.out.print("Duration: ");
                    String dur = sc.nextLine();
                    String sql = "INSERT INTO courses (course_name, duration) VALUES (?, ?)";
                    try (PreparedStatement pst = conn.prepareStatement(sql)) {
                        pst.setString(1, name);
                        pst.setString(2, dur);
                        pst.executeUpdate();
                        System.out.println("Course added!");
                    }
                }
                case 2 -> {
                    System.out.print("Course ID to update: ");
                    int id = Integer.parseInt(sc.nextLine());
                    System.out.print("New Course Name: ");
                    String name = sc.nextLine();
                    System.out.print("New Duration: ");
                    String dur = sc.nextLine();
                    String sql = "UPDATE courses SET course_name=?, duration=? WHERE course_id=?";
                    try (PreparedStatement pst = conn.prepareStatement(sql)) {
                        pst.setString(1, name);
                        pst.setString(2, dur);
                        pst.setInt(3, id);
                        int rows = pst.executeUpdate();
                        if (rows > 0) System.out.println("Course updated!");
                        else System.out.println("Course not found!");
                    }
                }
                case 3 -> {
                    System.out.print("Course ID to delete: ");
                    int id = Integer.parseInt(sc.nextLine());
                    String sql = "DELETE FROM courses WHERE course_id=?";
                    try (PreparedStatement pst = conn.prepareStatement(sql)) {
                        pst.setInt(1, id);
                        int rows = pst.executeUpdate();
                        if (rows > 0) System.out.println("Course deleted!");
                        else System.out.println("Course not found!");
                    }
                }
                case 4 -> {
                    String sql = "SELECT * FROM courses";
                    try (Statement st = conn.createStatement();
                         ResultSet rs = st.executeQuery(sql)) {
                        System.out.println("ID\tCourse Name\tDuration");
                        while (rs.next()) {
                            System.out.println(rs.getInt("course_id") + "\t" +
                                    rs.getString("course_name") + "\t" +
                                    rs.getString("duration"));
                        }
                    }
                }
                case 5 -> { return; }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    // --- Enrollment Management ---
    private static void manageEnrollment(Connection conn) throws SQLException {
        while (true) {
            System.out.println("\n--- Enrollment ---");
            System.out.println("1. Enroll Student");
            System.out.println("2. Drop Course");
            System.out.println("3. View Enrollments");
            System.out.println("4. Back");
            System.out.print("Choose: ");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1 -> {
                    System.out.print("Student ID: ");
                    int sid = Integer.parseInt(sc.nextLine());
                    System.out.print("Course ID: ");
                    int cid = Integer.parseInt(sc.nextLine());
                    String sql = "INSERT INTO enrollments (student_id, course_id) VALUES (?, ?)";
                    try (PreparedStatement pst = conn.prepareStatement(sql)) {
                        pst.setInt(1, sid);
                        pst.setInt(2, cid);
                        pst.executeUpdate();
                        System.out.println("Enrollment successful!");
                    }
                }
                case 2 -> {
                    System.out.print("Enrollment ID to delete: ");
                    int eid = Integer.parseInt(sc.nextLine());
                    String sql = "DELETE FROM enrollments WHERE enroll_id=?";
                    try (PreparedStatement pst = conn.prepareStatement(sql)) {
                        pst.setInt(1, eid);
                        int rows = pst.executeUpdate();
                        if (rows > 0) System.out.println("Enrollment deleted!");
                        else System.out.println("Not found!");
                    }
                }
                case 3 -> {
                    String sql = "SELECT e.enroll_id, s.name AS student, c.course_name AS course " +
                            "FROM enrollments e " +
                            "JOIN students s ON e.student_id = s.student_id " +
                            "JOIN courses c ON e.course_id = c.course_id";
                    try (Statement st = conn.createStatement();
                         ResultSet rs = st.executeQuery(sql)) {
                        System.out.println("EnrollID\tStudent\tCourse");
                        while (rs.next()) {
                            System.out.println(rs.getInt("enroll_id") + "\t" +
                                    rs.getString("student") + "\t" +
                                    rs.getString("course"));
                        }
                    }
                }
                case 4 -> { return; }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    // --- Reports ---
    private static void reports(Connection conn) throws SQLException {
        while (true) {
            System.out.println("\n--- Reports ---");
            System.out.println("1. Students per Course");
            System.out.println("2. Courses per Student");
            System.out.println("3. Back");
            System.out.print("Choose: ");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1 -> {
                    System.out.print("Course ID: ");
                    int cid = Integer.parseInt(sc.nextLine());
                    String sql = "SELECT s.student_id, s.name, s.email " +
                            "FROM enrollments e " +
                            "JOIN students s ON e.student_id = s.student_id " +
                            "WHERE e.course_id=?";
                    try (PreparedStatement pst = conn.prepareStatement(sql)) {
                        pst.setInt(1, cid);
                        ResultSet rs = pst.executeQuery();
                        System.out.println("ID\tName\tEmail");
                        while (rs.next()) {
                            System.out.println(rs.getInt("student_id") + "\t" +
                                    rs.getString("name") + "\t" +
                                    rs.getString("email"));
                        }
                    }
                }
                case 2 -> {
                    System.out.print("Student ID: ");
                    int sid = Integer.parseInt(sc.nextLine());
                    String sql = "SELECT c.course_id, c.course_name, c.duration " +
                            "FROM enrollments e " +
                            "JOIN courses c ON e.course_id = c.course_id " +
                            "WHERE e.student_id=?";
                    try (PreparedStatement pst = conn.prepareStatement(sql)) {
                        pst.setInt(1, sid);
                        ResultSet rs = pst.executeQuery();
                        System.out.println("ID\tCourse Name\tDuration");
                        while (rs.next()) {
                            System.out.println(rs.getInt("course_id") + "\t" +
                                    rs.getString("course_name") + "\t" +
                                    rs.getString("duration"));
                        }
                    }
                }
                case 3 -> { return; }
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}
