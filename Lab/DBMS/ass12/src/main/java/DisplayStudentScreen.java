import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class DisplayStudentScreen implements ActionListener {
    Manager manager;
    JFrame frame;
    JPanel panel;
    DefaultTableModel tableModel;
    JButton previousButton;
    JButton nextButton;
    int skipCount = 0;
    public DisplayStudentScreen(Manager manager){
        this.manager = manager;

        frame = new JFrame("All Students");
        frame.setLayout(null);
        panel = new JPanel();
        panel.setBounds(10, 10, 480, 500);

        // table
        String[] column ={"Roll","Name","Address", "Phone", "Department"};
        tableModel = new DefaultTableModel(column, 0);
        JTable table = new JTable(tableModel);
//        table.setBounds(10, 10, 480, table.getRowHeight() * 5);
        table.setPreferredScrollableViewportSize(new Dimension(480, table.getRowHeight() * 5));
        JScrollPane sp = new JScrollPane(table);
        sp.setMaximumSize(sp.getPreferredSize());
        panel.add(sp);

        previousButton = new JButton("Previous");
        previousButton.addActionListener(this);
        panel.add(previousButton);

        nextButton = new JButton("Next");
        nextButton.addActionListener(this);
        panel.add(nextButton);

        PrevNextButtonVisibilityChecker();
        panel.setVisible(true);
        frame.add(panel);
        frame.setSize(500, 200);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        displayStudents();
    }

    public void displayStudents(){
        ArrayList<Student> students = manager.getStudents(skipCount, 5);
        tableModel.setRowCount(0);
        for(Student student: students){
            Object[] row = {student.roll, student.name, student.address, student.phone, student.deptCode};
            tableModel.addRow(row);
        }
    }

    public void PrevNextButtonVisibilityChecker(){
        if(skipCount == 0){
            previousButton.setEnabled(false);
        }
        else{
            previousButton.setEnabled(true);
        }
        int totalNoOfStudents = manager.getNoOfStudents();
        if(skipCount+5 >= totalNoOfStudents){
            nextButton.setEnabled(false);
        }
        else{
            nextButton.setEnabled(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource() == previousButton){
            if(skipCount > 0){
                skipCount -= 5;
                displayStudents();
            }
        }
        else if(actionEvent.getSource() == nextButton){
            skipCount += 5;
            displayStudents();
        }
        PrevNextButtonVisibilityChecker();
    }
}
