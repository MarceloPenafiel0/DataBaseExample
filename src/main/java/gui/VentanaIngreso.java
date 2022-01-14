package gui;

import logic.LogicPersona;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaIngreso extends JFrame{
    public JPanel panel1;
    private JTextField textField1;
    private JLabel ingreseInformacionLabel;
    private JTextField textField2;
    private JButton crearButton;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JLabel resultadoLabel;


    public VentanaIngreso() {
        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //LogicPersona.capaLogicaPersona(textField1.getText(),textField2.getText(),textField3.getText());
                if(LogicPersona.crear(textField1.getText(),textField2.getText(),Integer.valueOf(textField4.getText()),
                        Integer.valueOf(textField5.getText()),textField6.getText(),textField7.getText(),
                        Integer.valueOf(textField8.getText())))
                    resultadoLabel.setText("Yes");
                else
                    resultadoLabel.setText("No");

//                JOptionPane.showMessageDialog(crearButton,textField1.getText());

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("VentanaIngreso");
        frame.setContentPane(new VentanaIngreso().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
