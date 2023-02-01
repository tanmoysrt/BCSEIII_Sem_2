import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteStudentScreen implements ActionListener {

    Manager manager;

    JFrame frame;

    JPanel panel;

    JLabel rollLabel;
    JTextField rollTextField;

    JButton deleteButton;

    DeleteStudentScreen(Manager manager) {
        this.manager = manager;

        frame = new JFrame("Delete Student");
        frame.setLayout(null);

        rollLabel = new JLabel("Roll No");
        rollLabel.setBounds(10, 10, 100, 30);
        frame.add(rollLabel);

        rollTextField = new JTextField();
        rollTextField.setBounds(120, 10, 120, 30);
        frame.add(rollTextField);

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(260, 10, 100, 30);
        deleteButton.addActionListener(this);
        frame.add(deleteButton);

        frame.setSize(400, 100);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == deleteButton) {
            String roll = rollTextField.getText();
            if(roll.isEmpty()){
                JOptionPane.showMessageDialog(frame, "Roll No is required");
                return;
            }
            int rollNo = Integer.parseInt(roll);
            boolean status = manager.deleteStudent(rollNo);
            if (status) {
                JOptionPane.showMessageDialog(frame, "Student deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
            }
            else
                JOptionPane.showMessageDialog(frame, "Student not found", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
