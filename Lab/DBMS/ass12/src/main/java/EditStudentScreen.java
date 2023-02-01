import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EditStudentScreen implements ActionListener {
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
    JButton searchButton;
    JLabel nameLabel;
    JLabel rollLabel;
    JLabel addressLabel;
    JLabel phoneLabel;
    JLabel deptLabel;

    public EditStudentScreen(Manager manager) {
        this.manager = manager;
        departments = manager.getDepartments();

        frame = new JFrame("Edit Student");
        frame.setLayout(null);

        // roll no field with search btn
        JLabel rollLabel = new JLabel("Roll No");
        rollLabel.setBounds(10, 10, 100, 30);
        frame.add(rollLabel);

        rollField = new JTextField();
        rollField.setBounds(120, 10, 200, 30);
        frame.add(rollField);

        searchButton = new JButton("Search");
        searchButton.setBounds(330, 10, 100, 30);
        searchButton.addActionListener(this);
        frame.add(searchButton);

        // Name field
        nameLabel = new JLabel("Name");
        nameLabel.setBounds(10, 50, 100, 30);
        frame.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(120, 50, 200, 30);
        frame.add(nameField);

        // Address field
        addressLabel = new JLabel("Address");
        addressLabel.setBounds(10, 90, 100, 30);
        frame.add(addressLabel);

        addressField = new JTextField();
        addressField.setBounds(120, 90, 200, 30);
        frame.add(addressField);

        // Phone field
        phoneLabel = new JLabel("Phone");
        phoneLabel.setBounds(10, 130, 100, 30);
        frame.add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setBounds(120, 130, 200, 30);
        frame.add(phoneField);

        // Department field
        deptLabel = new JLabel("Department");
        deptLabel.setBounds(10, 170, 100, 30);
        frame.add(deptLabel);

        deptField = new JComboBox<Department>();
        deptField.setBounds(120, 170, 200, 30);

        for (Department d : departments) {
            deptField.addItem(d);
        }

        frame.add(deptField);

        // Cancel button
        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(120, 210, 100, 30);
        cancelButton.addActionListener(this);
        frame.add(cancelButton);

        // Submit button
        submitButton = new JButton("Save");
        submitButton.setBounds(230, 210, 100, 30);
        submitButton.addActionListener(this);
        frame.add(submitButton);

        hideStudentDetailsField();
        frame.setSize(450, 300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void hideStudentDetailsField(){
        nameLabel.setVisible(false);
        nameField.setVisible(false);
        addressLabel.setVisible(false);
        addressField.setVisible(false);
        phoneLabel.setVisible(false);
        phoneField.setVisible(false);
        deptLabel.setVisible(false);
        deptField.setVisible(false);
        submitButton.setVisible(false);
        cancelButton.setVisible(false);
    }

    public void showStudentDetailsField(){
        nameLabel.setVisible(true);
        nameField.setVisible(true);
        addressLabel.setVisible(true);
        addressField.setVisible(true);
        phoneLabel.setVisible(true);
        phoneField.setVisible(true);
        deptLabel.setVisible(true);
        deptField.setVisible(true);
        submitButton.setVisible(true);
        cancelButton.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == cancelButton) {
            frame.dispose();
        } else if (actionEvent.getSource() == searchButton) {
            String roll = rollField.getText();
            if (roll.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Roll no is required");
                return;
            }
            int rollNo = Integer.parseInt(roll);
            Student student = manager.searchStudent(rollNo);
            if (student == null) {
                JOptionPane.showMessageDialog(frame, "Student not found");
                hideStudentDetailsField();
            } else {
                nameField.setText(student.name);
                addressField.setText(student.address);
                phoneField.setText(student.phone);
                Department selectDepartment = null;
                for (int i = 0; i < departments.size(); i++) {
                    Department tmp = departments.get(i);
                    if (tmp.deptCode.equals(student.deptCode)) {
                        selectDepartment = tmp;
                        break;
                    }
                }
                deptField.setSelectedItem(selectDepartment);
                showStudentDetailsField();
            }
        } else if (actionEvent.getSource() == submitButton){
            String roll = rollField.getText();
            int rollNo = Integer.parseInt(roll);

            String name = nameField.getText();
            String address = addressField.getText();
            String phone = phoneField.getText();
            Department department = (Department) deptField.getSelectedItem();
            assert department != null;
            String departmentCode = department.deptCode;
            manager.updateStudent(rollNo, name, address, phone, departmentCode);
            JOptionPane.showMessageDialog(frame, "Student updated successfully");
            frame.dispose();
        }
    }
}
