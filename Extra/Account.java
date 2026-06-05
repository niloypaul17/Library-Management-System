package Extra;

import Frame.LoginFrame;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Account {
    private JFrame parentComponent;
    private String userType;
    private String id;
    private String name;
    private String mail;
    private String password;
    private String gender;
    private File file;
    private BufferedReader reader;
    private LoginFrame loginFrame;

    public Account() {
    }

    public Account(JFrame parentComponent) {
        this.parentComponent = parentComponent;
    }

    public Account(JFrame parentComponent, String userType, String name, String password) {
        this.parentComponent = parentComponent;
        this.userType = userType;
        this.name = name;
        this.password = password;
    }

    public Account(String userType, String id, String name, String mail, String password, String gender) {
        this.userType = userType;
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.gender = gender;
    }

    public JFrame getParentComponent() {
        return parentComponent;
    }

    public String getUserType() {
        return userType;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public void setParentComponent(JFrame parentComponent) {
        this.parentComponent = parentComponent;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void addAccount(String userType, String id, String name, String mail, String password, String gender) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mma");
        String registrationTime = LocalDateTime.now().format(format);
        String[] rawUserInfo = {id, name, mail, password, gender, registrationTime};
        String userInfo = String.join(",", rawUserInfo);

        try {
            if (userType.equals("Librarian")) {
                file = new File("data/librarian.txt");
            } else if (userType.equals("Student")) {
                file = new File("data/student.txt");
            }

            File parentDir = file.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdir();
                file.createNewFile();
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(userInfo);
            writer.newLine();
            writer.flush();
            writer.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    parentComponent,
                    "Account creation failed.",
                    "Failed",
                    JOptionPane.ERROR_MESSAGE
            );
            throw new RuntimeException(e);
        }
    }

    public boolean accountExists(String userType, String name, String id) {
        try {
            if (userType.equals("Librarian")) {
                file = new File("data/librarian.txt");
            } else if (userType.equals("Student")) {
                file = new File("data/student.txt");
            }

            File parentDir = file.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdir();
            }
            file.createNewFile();

            reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(id) || data[1].equals(name)) {
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    parentComponent,
                    "Error while checking account.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            throw new RuntimeException(e);
        }
    }

    public boolean loginAccount(String userType, String name, String password) {
        if (userType.equals("Librarian")) {
            file = new File("data/librarian.txt");
        } else if (userType.equals("Student")) {
            file = new File("data/student.txt");
        }

        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            List<String> lines = new ArrayList<>();
            boolean newPassSet = false;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[1].equals(name) && data[3].equals(password)) {
                    JOptionPane.showMessageDialog(
                            parentComponent,
                            "Login successful.",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    return true;
                } else if (data[1].equals(name) && data[3].equals("NO_PASSWORD_SET")) {
                    JPanel addPassword = new JPanel();
                    addPassword.setPreferredSize(new Dimension(300, 170));

                    JLabel newPassLabel = new JLabel("Enter New Password");
                    newPassLabel.setPreferredSize(new Dimension(250, 20));
                    newPassLabel.setFont(Utils.NORMAL_FONT);

                    JPasswordField newPassField = new JPasswordField();
                    newPassField.setPreferredSize(new Dimension(250, 50));
                    newPassField.setFont(Utils.NORMAL_FONT);
                    newPassField.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createMatteBorder(1, 1, 1, 1, Utils.LIGHT_BLUE),
                            BorderFactory.createEmptyBorder(0, 5, 0, 5)
                    ));

                    JLabel confirmPassLabel = new JLabel("Confirm Password");
                    confirmPassLabel.setPreferredSize(new Dimension(400, 20));
                    confirmPassLabel.setFont(Utils.NORMAL_FONT);

                    JPasswordField confirmPassField = new JPasswordField();
                    confirmPassField.setPreferredSize(new Dimension(250, 50));
                    confirmPassField.setFont(Utils.NORMAL_FONT);
                    confirmPassField.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createMatteBorder(1, 1, 1, 1, Utils.LIGHT_BLUE),
                            BorderFactory.createEmptyBorder(0, 5, 0, 5)
                    ));

                    addPassword.add(newPassLabel);
                    addPassword.add(newPassField);
                    addPassword.add(confirmPassLabel);
                    addPassword.add(confirmPassField);

                    int answer = JOptionPane.showConfirmDialog(
                            parentComponent,
                            addPassword,
                            "Set Password",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.PLAIN_MESSAGE
                    );

                    if (answer == JOptionPane.OK_OPTION) {
                        if (newPassField.getPassword().length == 0 && confirmPassField.getPassword().length == 0) {
                            JOptionPane.showMessageDialog(
                                    parentComponent,
                                    "Password not set.",
                                    "Warning",
                                    JOptionPane.WARNING_MESSAGE
                            );
                            return false;
                        } else if (!Arrays.equals(newPassField.getPassword(), confirmPassField.getPassword())) {
                            JOptionPane.showMessageDialog(
                                    parentComponent,
                                    "Password mismatch.",
                                    "Warning",
                                    JOptionPane.WARNING_MESSAGE
                            );
                            newPassField.setText("");
                            confirmPassField.setText("");
                            return false;
                        } else if (Arrays.equals(newPassField.getPassword(), confirmPassField.getPassword())) {
                            String newPassword = String.valueOf(newPassField.getPassword());
                            String updatedLine = line.replace("NO_PASSWORD_SET", newPassword);
                            lines.add(updatedLine);
                            newPassSet = true;
                        }
                    }
                } else {
                    lines.add(line);

                    if (newPassSet) {
                        JOptionPane.showMessageDialog(
                                parentComponent,
                                "New password set.",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                }
            }
            reader.close();
            if (newPassSet) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                for (int i = 0; i < lines.size(); i++) {
                    writer.write(lines.get(i));
                    writer.newLine();
                }
                writer.flush();
                writer.close();
                return true;
            }
            JOptionPane.showMessageDialog(
                    parentComponent,
                    "Invalid Username or Password",
                    "Login failed.",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    parentComponent,
                    "Error: " + file + " not found.",
                    "Login failed.",
                    JOptionPane.ERROR_MESSAGE
            );
            throw new RuntimeException(e);
        }
    }

    public void deleteAccount(String name) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("data/student.txt"));
            List<String> lines = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();

            List<String> updatedLines = new ArrayList<>();
            boolean deleteMode = false;

            for (String currentLine : lines) {

                if (currentLine.contains("Name: " + name)) {
                    deleteMode = true;
                }

                if (!deleteMode) {
                    updatedLines.add(currentLine);
                }

                if (currentLine.equals("===============================================")) {
                    deleteMode = false;
                }
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter("data/student.txt"));
            for (int i = 0; i < updatedLines.size(); i++) {
                writer.write(updatedLines.get(i));
                writer.newLine();
            }
            writer.flush();
            writer.close();
            System.out.println("Deleted Account: " + name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    // Get count of available student accounts
    public int accountCount() {
        int count = 0;
        try {
            reader = new BufferedReader(new FileReader("data/student.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                count++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return count;
    }
}

