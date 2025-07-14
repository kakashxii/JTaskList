import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

public class JTaskListApp {
    public static void main(String[] args) {
        try (InputStream is = JTaskListApp.class.getResourceAsStream("/fonts/Orbitron-Regular.ttf")) {
            Font orbitron = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(16f);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(orbitron);
            UIManager.put("Label.font", orbitron);
            UIManager.put("Button.font", orbitron.deriveFont(14f));
            UIManager.put("CheckBox.font", orbitron.deriveFont(14f));
            UIManager.put("ComboBox.font", orbitron.deriveFont(14f));
            UIManager.put("TextField.font", orbitron.deriveFont(14f));
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("JTaskList Application");
            frame.setSize(1000, 800);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    
            frame.setContentPane(new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2 = (Graphics2D) g;
                    GradientPaint gp = new GradientPaint(
                        0, 0, new Color(45, 45, 70),
                        0, getHeight(), new Color(120, 150, 180)
                    );
                    g2.setPaint(gp);
                    g2.fillRect(0, 0, getWidth(), getHeight());
                }
            });
            frame.getContentPane().setLayout(null);
            frame.setLocationRelativeTo(null);

            JLabel nameLabel = new JLabel("Enter your name:");
            nameLabel.setForeground(Color.WHITE);
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
                    JOptionPane.showMessageDialog(
                        frame,
                        "Please enter your name.",
                        "Input Required",
                        JOptionPane.WARNING_MESSAGE
                    );
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
        greeting.setForeground(Color.WHITE);
        greeting.setBounds(50, 30, 400, 30);
        frame.add(greeting);

        JLabel prompt = new JLabel("What are your plans for today?");
        prompt.setForeground(Color.WHITE);
        prompt.setBounds(50, 70, 300, 30);
        frame.add(prompt);


        JTextField taskField = new JTextField();
        taskField.setBounds(50, 110, 200, 30);
        frame.add(taskField);

  
        JComboBox<String> priorityBox = new JComboBox<>(new String[]{"High","Medium","Low"});
        priorityBox.setBounds(260, 110, 100, 30);
        frame.add(priorityBox);

 
        JButton addButton = new JButton("Add Task");
        addButton.setBounds(380, 110, 100, 30);
        frame.add(addButton);


        JButton deleteAllButton = new JButton("Delete All");
        deleteAllButton.setBounds(500, 110, 120, 30);
        frame.add(deleteAllButton);


        JPanel tasksPanel = new JPanel();
        tasksPanel.setOpaque(false);
        tasksPanel.setLayout(new BoxLayout(tasksPanel, BoxLayout.Y_AXIS));

        JScrollPane scroll = new JScrollPane(tasksPanel);
        scroll.setBounds(50, 160, 900, 550);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        frame.add(scroll);

   
        deleteAllButton.addActionListener(ev -> {
            tasksPanel.removeAll();
            tasksPanel.revalidate();
            tasksPanel.repaint();
        });

        addButton.addActionListener(ev -> {
            String text = taskField.getText().trim();
            String pri  = (String) priorityBox.getSelectedItem();
            if (!text.isEmpty()) {
                JPanel taskPanel = createTaskPanel(text, pri, tasksPanel);
                taskPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                tasksPanel.add(taskPanel);
                tasksPanel.add(Box.createVerticalStrut(5));  // small gap
                tasksPanel.revalidate();
                tasksPanel.repaint();
                taskField.setText("");
            }
        });

        frame.revalidate();
        frame.repaint();
    }


    private static JPanel createTaskPanel(String text, String priority, JPanel tasksPanel) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel.setOpaque(false);

        JLabel priorityLabel = new JLabel("[" + priority + "]");
        priorityLabel.setForeground(Color.WHITE);
        panel.add(priorityLabel);

        JCheckBox checkBox = new JCheckBox(text);
        checkBox.setOpaque(false);
        checkBox.setForeground(Color.WHITE);
        panel.add(checkBox);

        JButton deleteBtn = new JButton("❌");
        deleteBtn.setBorderPainted(false);
        deleteBtn.setContentAreaFilled(false);
        deleteBtn.setFocusPainted(false);
        deleteBtn.setToolTipText("Remove this task");
        deleteBtn.addActionListener(e -> {
            tasksPanel.remove(panel);
            tasksPanel.revalidate();
            tasksPanel.repaint();
        });
        panel.add(deleteBtn);

        return panel;
    }
}
