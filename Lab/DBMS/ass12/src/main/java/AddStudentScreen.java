import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AddStudentScreen implements ActionListener {
    Manager manager;
    JFrame frame;
    ArrayList<Department> departments;
    JTextField nameField;
    JTextField rollField;
    JTextField addressField;
    JTextField phoneField;
    JComboBox<Department> deptField;
    JButton submitButton;
    JButton cancelButton;
    public AddStudentScreen(Manager manager) {
        this.manager = manager;
        departments = manager.getDepartments();

        frame = new JFrame("Add Student");
        frame.setLayout(null);

        // Name field
        JLabel nameLabel = new JLabel("Name");
        nameLabel.setBounds(10, 10, 100, 30);
        frame.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(120, 10, 200, 30);
        frame.add(nameField);

        // Roll field
        JLabel rollLabel = new JLabel("Roll");
        rollLabel.setBounds(10, 50, 100, 30);
        frame.add(rollLabel);

        rollField = new JTextField();
        rollField.setBounds(120, 50, 200, 30);
        frame.add(rollField);

        // Address field
        JLabel addressLabel = new JLabel("Address");
        addressLabel.setBounds(10, 90, 100, 30);
        frame.add(addressLabel);

        addressField = new JTextField();
        addressField.setBounds(120, 90, 200, 30);
        frame.add(addressField);

        // Phone field
        JLabel phoneLabel = new JLabel("Phone");
        phoneLabel.setBounds(10, 130, 100, 30);
        frame.add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setBounds(120, 130, 200, 30);
        frame.add(phoneField);

        // Department field
        JLabel deptLabel = new JLabel("Department");
        deptLabel.setBounds(10, 170, 100, 30);
        frame.add(deptLabel);

        deptField = new JComboBox<Department>();
        deptField.setBounds(120, 170, 200, 30);
        frame.add(deptField);

        for (Department d : departments) {
            deptField.addItem(d);
        }

        // Cancel button
        cancelButton = new JButton("CANCEL");
        cancelButton.setBounds(10, 210, 100, 30);
        cancelButton.addActionListener(this);
        frame.add(cancelButton);

        // Submit button
        submitButton = new JButton("SAVE");
        submitButton.setBounds(240, 210, 100, 30);
        submitButton.addActionListener(this);
        frame.add(submitButton);

        frame.setSize(360, 300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == submitButton) {
            String name = nameField.getText();
            String roll = rollField.getText();
            String address = addressField.getText();
            String phone = phoneField.getText();
            Department dept = (Department) deptField.getSelectedItem();
            if(roll.isBlank() || roll.isEmpty()){
                JOptionPane.showMessageDialog(frame, "Roll cannot be empty");
                return;
            }
            if(name.isBlank() || name.isEmpty()){
                JOptionPane.showMessageDialog(frame, "Name cannot be empty");
                return;
            }
            if(address.isBlank() || address.isEmpty()){
                JOptionPane.showMessageDialog(frame, "Address cannot be empty");
                return;
            }
            if(phone.isBlank() || phone.isEmpty()){
                JOptionPane.showMessageDialog(frame, "Phone cannot be empty");
                return;
            }
            if(dept == null){
                JOptionPane.showMessageDialog(frame, "Department cannot be empty");
                return;
            }
            int rollInt = Integer.parseInt(roll);

            Student student = new Student(rollInt, dept.deptCode, name, address, phone);
            boolean status = manager.addStudent(student);
            if (status) {
                JOptionPane.showMessageDialog(frame, "Student added successfully");
            } else {
                JOptionPane.showMessageDialog(frame, "Roll no already exists");
            }

            frame.dispose();
        }else if(actionEvent.getSource() == cancelButton){
            frame.dispose();
        }
    }
}
