package Frame.Panel;

import Extra.Utils;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProfileTab extends JPanel implements MouseListener, KeyListener {
    private final JButton saveButton;
    private final JTextField nameField;
    private final JTextField emailField;
    private final JTextField idField;
    private final JComboBox<String> genderBox;
    private final JPasswordField passwordField;
    private final JPasswordField confirmPasswordField;
    private String name;
    private String userType;

    public ProfileTab(String name, String userType) {
        this();
        this.name = name;
        this.userType = userType;

        setFields();
    }

    public ProfileTab() {
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(940, 700));

        CompoundBorder textFieldBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 1, 1, 1, Utils.LIGHT_BLUE),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)
        );

        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(940, 100));
        topPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));


        JLabel profileLabel = new JLabel("Edit Profile");
        profileLabel.setFont(Utils.TITLE_FONT);
        topPanel.add(profileLabel);

        this.add(topPanel, BorderLayout.NORTH);

        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(270, 500));

        this.add(leftPanel, BorderLayout.WEST);

        JPanel centerPanel = new JPanel();
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        centerPanel.setPreferredSize(new Dimension(400, 500));

        JLabel nameLabel = new JLabel("Name");
        nameLabel.setFont(Utils.NORMAL_FONT);
        nameLabel.setPreferredSize(new Dimension(360, 30));
        centerPanel.add(nameLabel);

        nameField = new JTextField();
        nameField.setFont(Utils.BIG_FONT);
        nameField.setPreferredSize(new Dimension(360, 50));
        nameField.setBorder(textFieldBorder);
        nameField.addKeyListener(this);
        centerPanel.add(nameField);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(Utils.NORMAL_FONT);
        emailLabel.setPreferredSize(new Dimension(360, 30));
        centerPanel.add(emailLabel);

        emailField = new JTextField();
        emailField.setFont(Utils.BIG_FONT);
        emailField.setPreferredSize(new Dimension(360, 50));
        emailField.setBorder(textFieldBorder);
        emailField.addKeyListener(this);
        centerPanel.add(emailField);

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(2, 2, 20, 0));
        gridPanel.setPreferredSize(new Dimension(360, 90));

        JLabel idLabel = new JLabel("ID");
        idLabel.setFont(Utils.NORMAL_FONT);
        gridPanel.add(idLabel);

        JLabel genderLabel = new JLabel("Gender");
        genderLabel.setFont(Utils.NORMAL_FONT);
        gridPanel.add(genderLabel);

        idField = new JTextField();
        idField.setFont(Utils.BIG_FONT);
        idField.setBorder(textFieldBorder);
        idField.setEditable(false);
        idField.addKeyListener(this);
        gridPanel.add(idField);

        String[] gender = {"Male", "Female", "Other"};
        genderBox = new JComboBox<>(gender);
        genderBox.setFont(Utils.NORMAL_FONT);
        genderBox.setFocusable(false);
        gridPanel.add(genderBox);

        centerPanel.add(gridPanel);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setPreferredSize(new Dimension(360, 30));
        passwordLabel.setFont(Utils.NORMAL_FONT);
        centerPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setFont(Utils.BIG_FONT);
        passwordField.setPreferredSize(new Dimension(360, 50));
        passwordField.setBorder(textFieldBorder);
        passwordField.addKeyListener(this);
        centerPanel.add(passwordField);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password");
        confirmPasswordLabel.setPreferredSize(new Dimension(360, 30));
        confirmPasswordLabel.setFont(Utils.NORMAL_FONT);
        centerPanel.add(confirmPasswordLabel);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setFont(Utils.BIG_FONT);
        confirmPasswordField.setPreferredSize(new Dimension(360, 50));
        confirmPasswordField.setBorder(textFieldBorder);
        confirmPasswordField.addKeyListener(this);
        centerPanel.add(confirmPasswordField);

        this.add(centerPanel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(270, 500));

        this.add(rightPanel, BorderLayout.EAST);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(940, 150));

        saveButton = new JButton("Save");
        saveButton.setFont(Utils.BIG_BOLD_FONT);
        saveButton.setPreferredSize(new Dimension(200, 60));
        saveButton.setFocusPainted(false);
        saveButton.addMouseListener(this);
        bottomPanel.add(saveButton);

        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == saveButton) {
            saveAction();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_ENTER) {
            saveButton.doClick();
            saveAction();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void setFields() {
        File file = null;
        if (userType.equals("Librarian")) {
            file = new File("data/librarian.txt");
        } else if (userType.equals("Student")) {
            file = new File("data/student.txt");
        }

        String line;
        String[] data;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                data = line.split(",");
                if (data[1].equals(name)) {
                    idField.setText(data[0]);
                    nameField.setText(data[1]);
                    emailField.setText(data[2]);
                    genderBox.setSelectedItem(data[4]);
                }
            }
            reader.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void saveAction() {
        String newName = nameField.getText();
        String newEmail = emailField.getText();
        String newId = idField.getText();
        String newGender = (String) genderBox.getSelectedItem();
        String newPassword = String.valueOf(passwordField.getPassword());
        String newConfirmPassword = String.valueOf(confirmPasswordField.getPassword());

        if (newName.isEmpty() || newEmail.isEmpty() || newId.isEmpty() || newPassword.isEmpty() || newConfirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all the fields",
                    "Empty fields",
                    JOptionPane.WARNING_MESSAGE
            );
        } else if (!newPassword.equals(newConfirmPassword)) {
            JOptionPane.showMessageDialog(
                    this,
                    "Password and Confirm Password does not match",
                    "Password mismatch",
                    JOptionPane.WARNING_MESSAGE
            );
        } else {
            File file = null;
            if (userType.equals("Librarian")) {
                file = new File("data/librarian.txt");
            } else if (userType.equals("Student")) {
                file = new File("data/student.txt");
            }

            List<String> lines = new ArrayList<>();
            String line;
            String[] data;
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));

                boolean nameExists = false;

                while ((line = reader.readLine()) != null) {
                    data = line.split(",");
                    if (data[1].equals(newName) && !newName.equals(name)) {
                        nameExists = true;
                    }
                }

                if (nameExists) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Name already exists",
                            "Name exists",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                while ((line = reader.readLine()) != null) {
                    data = line.split(",");
                    if (data[1].equals(name)) {
                        String[] updatedData = {newId, newName, newEmail, newPassword, newGender, data[5]};
                        line = String.join(",", updatedData);
                        name = newName;
                    }
                    lines.add(line);
                }
                reader.close();

                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                for (int i = 0; i < lines.size(); i++) {
                    writer.write(lines.get(i));
                    writer.newLine();
                }
                writer.flush();
                writer.close();

                JOptionPane.showMessageDialog(
                        this,
                        "Profile updated successfully",
                        "Profile updated",
                        JOptionPane.INFORMATION_MESSAGE
                );
                System.out.println("Profile updated successfully");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}

