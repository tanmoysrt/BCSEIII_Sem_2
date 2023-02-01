import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class screen  implements ActionListener {
    Manager manager = new Manager();

    JFrame frame;
    JPanel panel;
    JButton addStudentButton;
    JButton searchStudentButton;
    JButton deleteStudentButton;
    JButton editStudentButton;
    JButton displayAllStudentButton;
    public screen(){
        manager.loadDepartments();
        manager.loadStudents();

        frame = new JFrame("Student Management System");

        // add padding
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Add Student Button
        addStudentButton = new JButton("Add Student");
        addStudentButton.addActionListener(this);
        frame.add(addStudentButton);

        // Search Student Button
        searchStudentButton = new JButton("Search Student");
        searchStudentButton.addActionListener(this);
        frame.add(searchStudentButton);

        // Edit Student Button
        editStudentButton = new JButton("Edit Student");
        editStudentButton.addActionListener(this);
        frame.add(editStudentButton);

        // Delete Student Button
        deleteStudentButton = new JButton("Delete Student");
        deleteStudentButton.addActionListener(this);
        frame.add(deleteStudentButton);


        // Display All Student Button
        displayAllStudentButton = new JButton("Display All Student");
        displayAllStudentButton.addActionListener(this);
        frame.add(displayAllStudentButton);


        frame.setSize(400, 200);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new screen();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == searchStudentButton){
            new SearchStudentScreen(manager);
        }else if(e.getSource() == deleteStudentButton){
            new DeleteStudentScreen(manager);
        }else if (e.getSource() == displayAllStudentButton){
            new DisplayStudentScreen(manager);
        }else if(e.getSource() == addStudentButton){
            new AddStudentScreen(manager);
        }else if(e.getSource() == editStudentButton){
            new EditStudentScreen(manager);
        }
    }
}
