package Frame;

import Extra.*;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;


public class LoginFrame extends JFrame implements MouseListener, KeyListener {
    private ButtonGroup loginGroup;
    private JRadioButton librarianButton;
    private JRadioButton studentButton;
    private JTextField nameField;
    private JPasswordField passField;
    private JToggleButton showHideButton;
    private JCheckBox rememberBox;
    private JButton forgotButton;
    private JButton loginButton;
    private JButton signupButton;
    private ImageIcon showIcon;
    private ImageIcon hideIcon;
    private File file;

    public LoginFrame() {
        super("LogIn");
        Image iconImage = Toolkit.getDefaultToolkit().getImage("images/library.png");
        this.setIconImage(iconImage);
        this.setSize(1260, 700);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        lookAndFeel();

        // Start Right panel or Login panel
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
        loginPanel.setPreferredSize(new Dimension(560, 700));
        loginPanel.setBackground(Utils.BACKGROUND_COLOR);

        JPanel introPanel = new JPanel();
        introPanel.setPreferredSize(new Dimension(560, 130));
        introPanel.setBorder(BorderFactory.createEmptyBorder(60, 0, 20, 0));
        introPanel.setBackground(Utils.BACKGROUND_COLOR);

        JLabel introLabel = new JLabel("Log In");
        introLabel.setFont(Utils.INTRO_FONT);
        introPanel.add(introLabel);
        loginPanel.add(introPanel);

        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new BorderLayout());
        radioPanel.setPreferredSize(new Dimension(200, 50));
        radioPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        radioPanel.setBackground(Utils.BACKGROUND_COLOR);

        ImageIcon checkedIcon = new ImageIcon("images/radio_checked.png");
        ImageIcon unCheckedIcon = new ImageIcon("images/radio_unchecked.png");

        librarianButton = new JRadioButton("Librarian", unCheckedIcon);
        librarianButton.setActionCommand("Librarian");
        librarianButton.setSelectedIcon(checkedIcon);
        librarianButton.setFont(Utils.SMALL_BOLD_FONT);
        librarianButton.setBackground(Utils.BACKGROUND_COLOR);
        librarianButton.setFocusable(false);
        librarianButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        studentButton = new JRadioButton("Student", unCheckedIcon);
        studentButton.setActionCommand("Student");
        studentButton.setSelectedIcon(checkedIcon);
        studentButton.setFont(Utils.SMALL_BOLD_FONT);
        studentButton.setBackground(Utils.BACKGROUND_COLOR);
        studentButton.setFocusable(false);
        studentButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        loginGroup = new ButtonGroup();
        loginGroup.add(librarianButton);
        loginGroup.add(studentButton);
        // loginGroup.setSelected(studentButton.getModel(), true);

        radioPanel.add(librarianButton, BorderLayout.WEST);
        radioPanel.add(studentButton, BorderLayout.EAST);
        loginPanel.add(radioPanel);

        JLabel nameLabel = new JLabel("User Name");
        nameLabel.setFont(Utils.NORMAL_FONT);
        nameLabel.setPreferredSize(new Dimension(420, 28));
        nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        loginPanel.add(nameLabel);

        CompoundBorder textFieldBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 1, 1, 0, Utils.LIGHT_BLUE),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)
        );

        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(420, 56));
        nameField.setFont(Utils.BIG_FONT);
        nameField.setToolTipText("Enter your user name");
        nameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 1, 1, 1, Utils.LIGHT_BLUE),
                BorderFactory.createEmptyBorder(0, 5, 0, 0)
        ));
        nameField.addKeyListener(this);
        loginPanel.add(nameField);

        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(Utils.NORMAL_FONT);
        passLabel.setPreferredSize(new Dimension(420, 28));
        passLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        loginPanel.add(passLabel);

        passField = new JPasswordField();
        passField.setPreferredSize(new Dimension(385, 56));
        passField.setFont(Utils.BIG_FONT);
        passField.setToolTipText("Enter your password");
        passField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 1, 1, 0, Utils.LIGHT_BLUE),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
        passField.addKeyListener(this);
        loginPanel.add(passField);


        showIcon = new ImageIcon("images/show.png");
        hideIcon = new ImageIcon("images/hide.png");
        showHideButton = new JToggleButton(showIcon);
        showHideButton.setPreferredSize(new Dimension(35, 56));
        showHideButton.setFocusable(false);
        showHideButton.setBackground(Color.WHITE);
        showHideButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 1, 1, Utils.LIGHT_BLUE),
                BorderFactory.createEmptyBorder(0, 0, 0, 10)
        ));
        showHideButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        showHideButton.setUI(new BasicButtonUI() {
            private Color getSelectColor() {
                return Utils.BACKGROUND_COLOR;
            }
        });
        showHideButton.addMouseListener(this);
        loginPanel.add(showHideButton);

        JPanel rememberPanel = new JPanel();
        rememberPanel.setLayout(new BorderLayout());
        rememberPanel.setPreferredSize(new Dimension(420, 40));
        rememberPanel.setBackground(Utils.BACKGROUND_COLOR);

        rememberBox = new JCheckBox("Remember me");
        rememberBox.setFocusable(false);
        rememberBox.setIcon(new ImageIcon("images/checkbox_blank.png"));
        rememberBox.setSelectedIcon(new ImageIcon("images/checkbox_selected.png"));
        rememberBox.setFont(Utils.SMALL_FONT);
        rememberBox.setBackground(Utils.BACKGROUND_COLOR);
        rememberBox.setSelected(false);
        rememberBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        rememberPanel.add(rememberBox, BorderLayout.WEST);

        forgotButton = new JButton("Forgot password?");
        forgotButton.setFont(Utils.SMALL_FONT);
        forgotButton.setBorderPainted(false);
        forgotButton.setFocusable(false);
        forgotButton.setBackground(Utils.BACKGROUND_COLOR);
        forgotButton.setForeground(Utils.BLUE);
        forgotButton.setMargin(new Insets(0, 0, 0, 0));
        forgotButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        forgotButton.setUI(new BasicButtonUI() {
            private Color getSelectColor() {
                return Utils.BACKGROUND_COLOR;
            }
        });
        forgotButton.addMouseListener(this);
        rememberPanel.add(forgotButton, BorderLayout.EAST);

        loginPanel.add(rememberPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 1));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(45, 0, 35, 0));
        buttonPanel.setPreferredSize(new Dimension(420, 140));
        buttonPanel.setBackground(Utils.BACKGROUND_COLOR);

        loginButton = new JButton("Log in");
        loginButton.setFont(Utils.BIG_BOLD_FONT);
        loginButton.setFocusPainted(false);
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.addMouseListener(this);
        buttonPanel.add(loginButton);
        loginPanel.add(buttonPanel);

        JLabel signupLabel = new JLabel("Don't have an account yet?");
        signupLabel.setPreferredSize(new Dimension(210, 50));
        signupLabel.setFont(Utils.NORMAL_FONT);
        loginPanel.add(signupLabel);

        signupButton = new JButton("Sign up");
        signupButton.setPreferredSize(new Dimension(70, 50));
        signupButton.setFont(Utils.NORMAL_BOLD_FONT);
        signupButton.setBorderPainted(false);
        signupButton.setFocusable(false);
        signupButton.setBackground(Utils.BACKGROUND_COLOR);
        signupButton.setMargin(new Insets(0, 0, 0, 0));
        signupButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signupButton.addMouseListener(this);
        signupButton.setUI(new BasicButtonUI() {
            private Color getSelectColor() {
                return Utils.BACKGROUND_COLOR;
            }
        });
        loginPanel.add(signupButton);
        //End Right panel

        //Start Left panel
        JPanel imagePanel = new JPanel();
        imagePanel.setPreferredSize(new Dimension(700, 700));
        imagePanel.setBackground(Color.WHITE);

        ImageIcon image = new ImageIcon("images/signup.png");
        JLabel imageLabel = new JLabel(image);
        imageLabel.setBorder(BorderFactory.createEmptyBorder(85, 0, 0, 20));
        imagePanel.add(imageLabel);

        this.add(loginPanel, BorderLayout.EAST);
        this.add(imagePanel, BorderLayout.WEST);

        this.setVisible(true);
    }

    public LoginFrame(String userType, String name, String password) {
        lookAndFeel();
        login(userType, name, password);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == showHideButton) {
            if (showHideButton.isSelected()) {
                passField.setEchoChar((char) 0);
                showHideButton.setIcon(hideIcon);
            } else {
                passField.setEchoChar((char) UIManager.get("PasswordField.echoChar"));
                showHideButton.setIcon(showIcon);
            }
        } else if (e.getSource() == forgotButton) {
            ForgotPassFrame forgotPassFrame = new ForgotPassFrame();
            this.setVisible(false);
            forgotPassFrame.setVisible(true);
        } else if (e.getSource() == loginButton) {
            loginAction();
        } else if (e.getSource() == signupButton) {
            SignupFrame signupFrame = new SignupFrame();
            this.setVisible(false);
            signupFrame.setVisible(true);
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
        if (e.getSource() == forgotButton) {
            forgotButton.setForeground(Color.RED);
        } else if (e.getSource() == signupButton) {
            signupButton.setForeground(Utils.BLUE);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == forgotButton) {
            forgotButton.setForeground(Utils.BLUE);
        } else if (e.getSource() == signupButton) {
            signupButton.setForeground(Color.BLACK);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_ENTER) {
            loginButton.doClick();
            loginAction();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void login(String userType, String name, String password) {
        Account account = new Account(this);
        boolean isLoggedIn = account.loginAccount(userType, name, password);
        if (isLoggedIn) {
            try {
                rememberMe();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            if (userType == "Librarian" ) {
                LibrarianFrame librarianFrame = new LibrarianFrame(name, userType);
                librarianFrame.setVisible(true);
            } else if (userType == "Student") {
                StudentFrame studentFrame = new StudentFrame(name, userType);
                studentFrame.setVisible(true);
            }

            this.setVisible(false);
            System.out.println("Logged in as \"" + name + " - " + userType + "\"");
        }
    }

    public void loginAction() {
        if (!librarianButton.isSelected() && !studentButton.isSelected()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select either 'Librarian' or 'Student'.",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE
            );
        } else {
            String userType = loginGroup.getSelection().getActionCommand();
            String name = nameField.getText();
            String password = String.valueOf(passField.getPassword());

            if (userType.equals("Librarian")) {
                file = new File("data/librarian.txt");
            } else if (userType.equals("Student")) {
                file = new File("data/student.txt");
            }

            boolean newPassSet = false;
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data[0].equals(name) && data[3].equals("NO_PASSWORD_SET")) {
                        newPassSet = true;
                    }
                }
                reader.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            if (nameField.getText().isEmpty() && passField.getPassword().length == 0 && !newPassSet) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please, Fill the black areas.",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );
            } else {
                login(userType, name, password);
            }
        }
    }

    public void rememberMe() throws IOException {
        if (this.rememberBox != null && this.rememberBox.isSelected()) {
            String userType = loginGroup.getSelection().getActionCommand();
            String name = nameField.getText();
            String password = String.valueOf(passField.getPassword());

            File rememberFile = new File("data/remember.txt");
            if (!rememberFile.exists()) {
                rememberFile.createNewFile();
            }

            String rememberData = userType + "," + name + "," + password;
            BufferedWriter writer = new BufferedWriter(new FileWriter(rememberFile));
            writer.write(rememberData);
            writer.flush();
            writer.close();
        }
    }

    public void lookAndFeel() {
        UIManager.put("OptionPane.messageFont", Utils.NORMAL_FONT);
        UIManager.put("OptionPane.buttonFont", Utils.NORMAL_FONT);
        UIManager.put("Button.focus", new Color(0, 0, 0, 0));
    }
}
