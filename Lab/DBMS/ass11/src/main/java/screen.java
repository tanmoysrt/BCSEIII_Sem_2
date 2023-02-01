import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class screen implements ActionListener {

    JFrame frame;
    JPanel panel;
    JTextField num1Field, num2Field, resultField;
    JButton addButton, differenceButton;
    JLabel num1Label, num2Label, resultLabel;

    public screen() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        frame = new JFrame("Welcome user "+ formatter.format(date));
        panel = new JPanel();
        GridLayout layout = new GridLayout(4,2);
        layout.setHgap(10);
        layout.setVgap(10);
        panel.setLayout(layout);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        num1Label = new JLabel("Num 1:");
        panel.add(num1Label);
        num1Field = new JTextField();
        panel.add(num1Field);

        num2Label = new JLabel("Num 2:");
        panel.add(num2Label);
        num2Field = new JTextField();
        panel.add(num2Field);

        addButton = new JButton("Add");
        addButton.addActionListener(this);
        panel.add(addButton);

        differenceButton = new JButton("Difference");
        differenceButton.addActionListener(this);
        panel.add(differenceButton);

        resultLabel = new JLabel("Result:");
        panel.add(resultLabel);
        resultField = new JTextField();
        resultField.setEditable(false);
        panel.add(resultField);

        frame.add(panel);
        frame.setSize(400, 200);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new screen();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        double num1 = Double.parseDouble(num1Field.getText());
        double num2 = Double.parseDouble(num2Field.getText());
        double result = 0;

        if (e.getSource() == addButton) {
            result = num1 + num2;
        } else if (e.getSource() == differenceButton) {
            result = Math.abs(num1 - num2);
        }

        resultField.setText(String.valueOf(result));
    }
}
