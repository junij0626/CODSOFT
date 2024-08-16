import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private JFrame frame;
    private DefaultListModel<String> courseListModel;
    private DefaultListModel<String> studentListModel;
    private final List<Course> courses;
    private List<Student> students;

    public Main() {
        courses = new ArrayList<>();
        students = new ArrayList<>();
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Course Registration System");
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        courseListModel = new DefaultListModel<>();
        studentListModel = new DefaultListModel<>();

        JPanel coursePanel = new JPanel();
        coursePanel.setLayout(new BorderLayout());
        coursePanel.add(new JLabel("Available Courses"), BorderLayout.NORTH);
        JList<String> courseList = new JList<>(courseListModel);
        coursePanel.add(new JScrollPane(courseList), BorderLayout.CENTER);

        JPanel studentPanel = new JPanel();
        studentPanel.setLayout(new BorderLayout());
        studentPanel.add(new JLabel("Students"), BorderLayout.NORTH);
        JList<String> studentList = new JList<>(studentListModel);
        studentPanel.add(new JScrollPane(studentList), BorderLayout.CENTER);

        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new GridLayout(2, 1));

        JButton registerButton = new JButton("Register Course");
        JButton removeButton = new JButton("Remove Course");

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCourse = courseList.getSelectedValue();
                String selectedStudent = studentList.getSelectedValue();
                if (selectedCourse != null && selectedStudent != null) {
                    registerCourse(selectedStudent, selectedCourse);
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCourse = courseList.getSelectedValue();
                String selectedStudent = studentList.getSelectedValue();
                if (selectedCourse != null && selectedStudent != null) {
                    removeCourse(selectedStudent, selectedCourse);
                }
            }
        });

        actionPanel.add(registerButton);
        actionPanel.add(removeButton);

        frame.add(coursePanel, BorderLayout.WEST);
        frame.add(studentPanel, BorderLayout.EAST);
        frame.add(actionPanel, BorderLayout.SOUTH);

        loadSampleData();
    }

    private void loadSampleData() {
        courses.add(new Course("CS101", "Intro to Computer Science", "Basic concepts of computer science.", 30, "MWF 10-11"));
        courses.add(new Course("MATH101", "Calculus I", "Introduction to calculus.", 40, "TTh 9-10:30"));
        courses.add(new Course("PHYS101", "Physics I", "Fundamentals of physics.", 35, "MWF 11-12"));

        students.add(new Student("S001", "John Doe"));
        students.add(new Student("S002", "Jane Smith"));
        students.add(new Student("S003", "Alice Johnson"));

        for (Course course : courses) {
            courseListModel.addElement(course.getCourseCode() + " - " + course.getTitle());
        }

        for (Student student : students) {
            studentListModel.addElement(student.getStudentId() + " - " + student.getName());
        }
    }

    private void registerCourse(String studentInfo, String courseInfo) {
        String studentId = studentInfo.split(" - ")[0];
        String courseCode = courseInfo.split(" - ")[0];

        Student student = findStudentById(studentId);
        Course course = findCourseByCode(courseCode);

        if (student != null && course != null) {
            student.registerCourse(course);
            JOptionPane.showMessageDialog(frame, "Course registered successfully!");
        }
    }

    private void removeCourse(String studentInfo, String courseInfo) {
        String studentId = studentInfo.split(" - ")[0];
        String courseCode = courseInfo.split(" - ")[0];

        Student student = findStudentById(studentId);
        Course course = findCourseByCode(courseCode);

        if (student != null && course != null) {
            student.removeCourse(course);
            JOptionPane.showMessageDialog(frame, "Course removed successfully!");
        }
    }

    private Student findStudentById(String studentId) {
        for (Student student : students) {
            if (student.getStudentId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }

    private Course findCourseByCode(String courseCode) {
        for (Course course : courses) {
            if (course.getCourseCode().equals(courseCode)) {
                return course;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main window = new Main();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

class Course {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private String schedule;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }
}

class Student {
    private String studentId;
    private String name;
    private List<Course> registeredCourses;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public void registerCourse(Course course) {
        registeredCourses.add(course);
    }

    public void removeCourse(Course course) {
        registeredCourses.remove(course);
    }
}

