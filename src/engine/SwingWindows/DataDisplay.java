package engine.SwingWindows;

import javax.swing.*;

public class DataDisplay extends JFrame {

    private static JLabel data;

    public DataDisplay() {
        this.setTitle("Data");

        data = new JLabel("asdf");
        data.setBounds(0, 0, 400, 300);
        this.getContentPane().add(data);


        this.setLayout(null);
        this.setBounds(0, 0, 400, 300);
        this.setVisible(true);
    }

    public static void showWindow(){
        new DataDisplay();
    }

    public static void setText(String text){
        data.setText(text);
    }

}
