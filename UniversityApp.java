import java.util.ArrayList;
import java.util.List;

// Step 1: Creating the Basic Classes
abstract class Person {
    protected int id;
    protected String name;
    protected String email;

    public Person(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}

class Student extends Person {
    public String studentId;
    public String major;

    public Student(int id, String name, String email, String studentId, String major) {
        super(id, name, email);
        this.studentId = studentId;
        this.major = major;
    }
}

class Faculty extends Person {
    private String facultyId;
    private String department;

    public Faculty(int id, String name, String email, String facultyId, String department) {
        super(id, name, email);
        this.facultyId = facultyId;
        this.department = department;
    }
}

// Step 2: Implementing the Course Interface
interface Course {
    String getCourseCode();

    String getCourseTitle();

    int getCreditHours();
}

// Step 3: Creating Course Classes
class DataStructure implements Course {
    private String courseCode;
    private String courseTitle;
    private int creditHours;

    public DataStructure(String courseCode, String courseTitle, int creditHours) {
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.creditHours = creditHours;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public int getCreditHours() {
        return creditHours;
    }
}

// Aryan: Create other course classes similarly
// Step 4: Handling Course Enrollment
class Enrollment {
    private Student student;
    public List<Course> enrolledCourses;

    public Enrollment(Student student) {
        this.student = student;
        this.enrolledCourses = new ArrayList<>();
    }

    public void enrollInCourse(Course course) {
        enrolledCourses.add(course);
    }

    public int getTotalCreditHours() {
        int totalCreditHours = 0;
        for (Course course : enrolledCourses) {
            totalCreditHours += course.getCreditHours();
        }
        return totalCreditHours;
    }
}

// Step 5: Managing Grades
class GradeBook {
    private List<Student> students;
    private List<Course> courses;
    private String[][] grades;

    public GradeBook(List<Student> students, List<Course> courses) {
        this.students = students;
        this.courses = courses;
        this.grades = new String[students.size()][courses.size()];
    }

    public void assignGrade(Student student, Course course, String grade) {
        int studentIndex = students.indexOf(student);
        int courseIndex = courses.indexOf(course);
        if (studentIndex != -1 && courseIndex != -1) {
            grades[studentIndex][courseIndex] = grade;
        }
    }

    public double calculateGPA(Student student) {
        int studentIndex = students.indexOf(student);
        if (studentIndex == -1) {
            return 0.0; // Aryan: Student not found
        }
        int totalCreditHours = 0;
        double totalGradePoints = 0.0;
        for (int i = 0; i < courses.size(); i++) {
            String grade = grades[studentIndex][i];
            if (grade == null || grade.isEmpty()) {
                continue; // Aryan: If Grade not assigned
            }
            int creditHours = courses.get(i).getCreditHours();
            totalCreditHours += creditHours;
            switch (grade) {
                case "A":
                    totalGradePoints += 4.0 * creditHours;
                    break;
                case "B":
                    totalGradePoints += 3.0 * creditHours;
                    break;
                case "C":
                    totalGradePoints += 2.0 * creditHours;
                    break;
                case "D":
                    totalGradePoints += 1.0 * creditHours;
                    break;
                case "F":
                    // No grade points for failing
                    break;
            }
        }
        if (totalCreditHours == 0) {
            return 0.0; // Avoid division by zero
        }

        return totalGradePoints / totalCreditHours;
    }
}

// Step 6: UniversityApp - Putting It All Together
public class UniversityApp {
    public static void main(String[] args) {
        // Aryan: Create students
        Student student1 = new Student(1, "Aryan", "211B065@juetguna.in", "211B065", "CSE");
        Student student2 = new Student(2, "Ashutosh", "211B073@juetguna.in", "211B073", "ECE");
        // Aryan: Create courses
        Course dataStructure = new DataStructure("18B11CS101", "Data Structure", 3);
        Course dbms = new DataStructure("18B11DB101", "Database Management System", 4);
        Course os = new DataStructure("18B11OS101", "Operating System", 3);
        // Step 6a: Enroll students in courses
        Enrollment enrollment1 = new Enrollment(student1);
        enrollment1.enrollInCourse(dataStructure);
        enrollment1.enrollInCourse(dbms);
        Enrollment enrollment2 = new Enrollment(student2);
        enrollment2.enrollInCourse(dataStructure);
        enrollment2.enrollInCourse(os);
        // Step 6b: Manage grades
        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        List<Course> courses = new ArrayList<>();
        courses.add(dataStructure);
        courses.add(dbms);
        courses.add(os);
        GradeBook gradeBook = new GradeBook(students, courses);
        gradeBook.assignGrade(student1, dataStructure, "A");
        gradeBook.assignGrade(student1, dbms, "B");
        gradeBook.assignGrade(student2, dataStructure, "B");
        gradeBook.assignGrade(student2, os, "A");
        // Step 6c: Display student information, enrolled courses, and GPA
        System.out.println("Student Information:");
        System.out.println("Name: " + student1.name);
        System.out.println("Student ID: " + student1.studentId);
        System.out.println("Major: " + student1.major);
        System.out.println("\nEnrolled Courses:");
        for (Course course : enrollment1.enrolledCourses) {
            System.out.println("Course Code: " + course.getCourseCode());
            System.out.println("Course Title: " + course.getCourseTitle());
            System.out.println("Credit Hours: " + course.getCreditHours());
        }
        double gpa = gradeBook.calculateGPA(student1);
        System.out.println("\nGPA: " + gpa);
    }
}
