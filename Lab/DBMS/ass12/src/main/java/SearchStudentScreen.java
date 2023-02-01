import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchStudentScreen implements ActionListener {
    Manager manager;
    JFrame frame;
    JPanel resultPanel;
    JTextField rollTextField;
    JButton searchButton;
    SearchStudentScreen(Manager manager){
        this.manager = manager;
        frame = new JFrame("Search Student");
        frame.setLayout(null);

        JLabel rollLabel = new JLabel("Roll No");
        rollLabel.setBounds(10, 10, 100, 30);
        frame.add(rollLabel);

        rollTextField = new JTextField();
        rollTextField.setBounds(120, 10, 120, 30);
        frame.add(rollTextField);

        searchButton = new JButton("Search");
        searchButton.setBounds(260, 10, 100, 30);
        searchButton.addActionListener(this);
        frame.add(searchButton);

        resultPanel = new JPanel();
        resultPanel.setVisible(false);
        resultPanel.setBounds(10, 50, 400, 200);
        resultPanel.setLayout(null);

        JLabel nameLabel = new JLabel("Name");
        nameLabel.setBounds(10, 10, 100, 20);
        resultPanel.add(nameLabel);

        JLabel nameValueLabel = new JLabel("");
        nameValueLabel.setBounds(120, 10, 100, 20);
        resultPanel.add(nameValueLabel);

        JLabel addressLabel = new JLabel("Address");
        addressLabel.setBounds(10, 50, 100, 20);
        resultPanel.add(addressLabel);

        JLabel addressValueLabel = new JLabel("");
        addressValueLabel.setBounds(120, 50, 100, 20);
        resultPanel.add(addressValueLabel);

        JLabel phoneLabel = new JLabel("Phone");
        phoneLabel.setBounds(10, 90, 100, 20);
        resultPanel.add(phoneLabel);

        JLabel phoneValueLabel = new JLabel("");
        phoneValueLabel.setBounds(120, 90, 100, 20);
        resultPanel.add(phoneValueLabel);

        JLabel deptLabel = new JLabel("Department");
        deptLabel.setBounds(10, 130, 100, 20);
        resultPanel.add(deptLabel);

        JLabel deptValueLabel = new JLabel("");
        deptValueLabel.setBounds(120, 130, 100, 20);
        resultPanel.add(deptValueLabel);

        frame.add(resultPanel);


        frame.setSize(400, 300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == searchButton){
            int roll = Integer.parseInt(rollTextField.getText());
            Student student = manager.searchStudent(roll);
            if (student != null){
                resultPanel.setVisible(true);
                JLabel nameLabel = (JLabel) resultPanel.getComponent(1);
                JLabel addressLabel = (JLabel) resultPanel.getComponent(3);
                JLabel phoneLabel = (JLabel) resultPanel.getComponent(5);
                JLabel deptLabel = (JLabel) resultPanel.getComponent(7);
                nameLabel.setText(student.name);
                addressLabel.setText(student.address);
                phoneLabel.setText(student.phone);
                deptLabel.setText(student.deptCode);
            } else {
                JOptionPane.showMessageDialog(frame, "Student not found");
            }
        }
    }
}
