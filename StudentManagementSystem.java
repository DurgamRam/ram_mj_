import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

public class StudentManagementSystem {
    private LinkedList<Student> studentList = new LinkedList<>();

    private JFrame frame;

    public StudentManagementSystem() {
        frame = new JFrame("Student Management System");
        frame.setSize(500, 400);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        JButton addStudentButton = new JButton("Add Student");
        addStudentButton.addActionListener(e -> addStudent());
        panel.add(addStudentButton);

        JButton viewStudentsButton = new JButton("View Students");
        viewStudentsButton.addActionListener(e -> viewStudents());
        panel.add(viewStudentsButton);

        JButton deleteStudentButton = new JButton("Delete Student");
        deleteStudentButton.addActionListener(e -> deleteStudent());
        panel.add(deleteStudentButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        panel.add(exitButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void addStudent() {
        JTextField nameField = new JTextField();
        JTextField rollNoField = new JTextField();
        JTextField courseField = new JTextField();
        JTextField attendanceField = new JTextField();

        Object[] message = {
            "Name:", nameField,
            "Roll No:", rollNoField,
            "Course:", courseField,
            "Attendance (%):", attendanceField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Add Student", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                int rollNo = Integer.parseInt(rollNoField.getText());
                String course = courseField.getText();
                double attendance = Double.parseDouble(attendanceField.getText());

                studentList.add(new Student(name, rollNo, course, attendance));
                JOptionPane.showMessageDialog(frame, "Student added successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Roll No and Attendance must be numbers!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void viewStudents() {
        if (studentList.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No students to display!");
            return;
        }

        StringBuilder studentData = new StringBuilder();
        for (Student student : studentList) {
            studentData.append(student).append("\n");
        }

        JTextArea textArea = new JTextArea(studentData.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JOptionPane.showMessageDialog(frame, scrollPane, "Student List", JOptionPane.INFORMATION_MESSAGE);
    }

    private void deleteStudent() {
        String rollNoStr = JOptionPane.showInputDialog(frame, "Enter Roll No to delete:");
        if (rollNoStr != null) {
            try {
                int rollNo = Integer.parseInt(rollNoStr);
                boolean removed = studentList.removeIf(student -> student.getRollNo() == rollNo);
                if (removed) {
                    JOptionPane.showMessageDialog(frame, "Student removed successfully!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Student not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Roll No must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentManagementSystem::new);
    }
}

class Student {
    private String name;
    private int rollNo;
    private String course;
    private double attendance;

    public Student(String name, int rollNo, String course, double attendance) {
        this.name = name;
        this.rollNo = rollNo;
        this.course = course;
        this.attendance = attendance;
    }

    public int getRollNo() {
        return rollNo;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Roll No: " + rollNo + ", Course: " + course + ", Attendance: " + attendance + "%";
    }
}