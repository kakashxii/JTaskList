import javax.swing.*;
import java.awt.*;

public class JTaskListApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("JTaskList Application");
            frame.setSize(1000, 800);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(null);
            frame.setLocationRelativeTo(null);

       
            JLabel nameLabel = new JLabel("Enter your name:");
            nameLabel.setBounds(50, 50, 200, 30);
            frame.add(nameLabel);

         
            JTextField nameField = new JTextField();
            nameField.setBounds(260, 50, 200, 30);
            frame.add(nameField);

           
            JButton goButton = new JButton("Go");
            goButton.setBounds(480, 50, 100, 30);
            frame.add(goButton);

            goButton.addActionListener(e -> {
                String userName = nameField.getText().trim();
                if (userName.isEmpty()) {
                    JOptionPane.showMessageDialog(frame,
                        "Please enter your name.",
                        "Input Required",
                        JOptionPane.WARNING_MESSAGE);
                } else {
                    showTodoUI(frame, userName);
                }
            });

            
            frame.setVisible(true);
        });
    }

    private static void showTodoUI(JFrame frame, String name) {
        frame.getContentPane().removeAll();


        JLabel greeting = new JLabel("Hello, " + name + " 👋");
        greeting.setBounds(50, 30, 400, 30);
        frame.add(greeting);

   
        JLabel prompt = new JLabel("What are your plans for today?");
        prompt.setBounds(50, 70, 300, 30);
        frame.add(prompt);

       
        JTextField taskField = new JTextField();
        taskField.setBounds(50, 110, 300, 30);
        frame.add(taskField);

       
        JButton addButton = new JButton("Add Task");
        addButton.setBounds(360, 110, 100, 30);
        frame.add(addButton);

    
        JPanel tasksPanel = new JPanel();
        tasksPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        tasksPanel.setBounds(50, 160, 900, 550);
        frame.add(tasksPanel);

       
        addButton.addActionListener(ev -> {
            String taskText = taskField.getText().trim();
            if (!taskText.isEmpty()) {
                JCheckBox taskBox = new JCheckBox(taskText);
                tasksPanel.add(taskBox);
                tasksPanel.revalidate();
                tasksPanel.repaint();
                taskField.setText("");
            }
        });

      
        frame.revalidate();
        frame.repaint();
    }
}
