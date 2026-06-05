package Frame;

import Extra.*;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ForgotPassFrame extends JFrame implements MouseListener, KeyListener {
    private final JButton backButton;
    private final JRadioButton librarianButton;
    private final JRadioButton studentButton;
    private LoginFrame loginFrame;
    private final JButton resetButton;
    private final JTextField nameField;
    private final JTextField emailField;

    public ForgotPassFrame() {
        super("Reset Password");
        Image iconImage = Toolkit.getDefaultToolkit().getImage("images/library.png");
        this.setIconImage(iconImage);
        this.setSize(1260, 700);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(new BorderLayout());

        // Start of Left Panel
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(660, 700));
        leftPanel.setBackground(Color.WHITE);

        JPanel naviPanel = new JPanel();
        naviPanel.setLayout(new BorderLayout());
        naviPanel.setPreferredSize(new Dimension(60, 60));
        naviPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 0));
        naviPanel.setBackground(Color.WHITE);

        ImageIcon backIcon = new ImageIcon("images/arrow_back.png");
        backButton = new JButton(backIcon);
        backButton.setBackground(Color.WHITE);
        backButton.setFocusable(false);
        backButton.setBorderPainted(false);
        backButton.setToolTipText("Go Back");
        backButton.addMouseListener(this);

        naviPanel.add(backButton, BorderLayout.WEST);
        leftPanel.add(naviPanel, BorderLayout.NORTH);

        ImageIcon image = new ImageIcon("images/forgot-pass.jpg");
        JLabel imageLabel = new JLabel(image);
        imageLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 70, 0));
        leftPanel.add(imageLabel, BorderLayout.CENTER);
        // End of Left Panel

        // Start of Right Panel
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new FlowLayout());
        rightPanel.setPreferredSize(new Dimension(600, 700));
        rightPanel.setBackground(Utils.BACKGROUND_COLOR);

        JPanel introPanel = new JPanel();
        introPanel.setPreferredSize(new Dimension(600, 130));
        introPanel.setBorder(BorderFactory.createEmptyBorder(60, 0, 20, 0));
        introPanel.setBackground(Utils.BACKGROUND_COLOR);

        JLabel introLabel = new JLabel("Forgot Password?");
        introLabel.setFont(Utils.INTRO_FONT);
        introPanel.add(introLabel);
        rightPanel.add(introPanel);

        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new BorderLayout());
        radioPanel.setPreferredSize(new Dimension(200, 50));
        radioPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        radioPanel.setBackground(Utils.BACKGROUND_COLOR);

        ImageIcon checkedIcon = new ImageIcon("images/radio_checked.png");
        ImageIcon unCheckedIcon = new ImageIcon("images/radio_unchecked.png");

        librarianButton = new JRadioButton("Librarian", unCheckedIcon);
        librarianButton.setSelectedIcon(checkedIcon);
        librarianButton.setFont(Utils.SMALL_BOLD_FONT);
        librarianButton.setBackground(Utils.BACKGROUND_COLOR);
        librarianButton.setFocusable(false);
        librarianButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        studentButton = new JRadioButton("Student", unCheckedIcon);
        studentButton.setSelectedIcon(checkedIcon);
        studentButton.setFont(Utils.SMALL_BOLD_FONT);
        studentButton.setBackground(Utils.BACKGROUND_COLOR);
        studentButton.setFocusable(false);
        studentButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        ButtonGroup loginGroup = new ButtonGroup();
        loginGroup.add(librarianButton);
        loginGroup.add(studentButton);

        radioPanel.add(librarianButton, BorderLayout.WEST);
        radioPanel.add(studentButton, BorderLayout.EAST);
        rightPanel.add(radioPanel);

        JLabel nameLabel = new JLabel("Name");
        nameLabel.setFont(Utils.NORMAL_FONT);
        nameLabel.setPreferredSize(new Dimension(400, 30));
        rightPanel.add(nameLabel);

        CompoundBorder textFieldBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 1, 1, 1, Utils.LIGHT_BLUE),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)
        );

        nameField = new JTextField();
        nameField.setFont(Utils.BIG_FONT);
        nameField.setPreferredSize(new Dimension(400, 60));
        nameField.setBorder(textFieldBorder);
        nameField.addKeyListener(this);
        rightPanel.add(nameField);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(Utils.NORMAL_FONT);
        emailLabel.setPreferredSize(new Dimension(400, 40));
        emailLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        rightPanel.add(emailLabel);

        emailField = new JTextField();
        emailField.setFont(Utils.BIG_FONT);
        emailField.setPreferredSize(new Dimension(400, 60));
        emailField.setBorder(textFieldBorder);
        emailField.addKeyListener(this);
        rightPanel.add(emailField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(400, 150));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        buttonPanel.setBackground(Utils.BACKGROUND_COLOR);

        resetButton = new JButton("Reset Password");
        resetButton.setFont(Utils.BIG_BOLD_FONT);
        resetButton.setPreferredSize(new Dimension(400, 60));
        resetButton.setFocusPainted(false);
        resetButton.addMouseListener(this);
        resetButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        buttonPanel.add(resetButton);

        rightPanel.add(buttonPanel);
        // End of Right Panel

        this.add(leftPanel, BorderLayout.WEST);
        this.add(rightPanel, BorderLayout.EAST);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == backButton) {
            backAction();
        } else if (e.getSource() == resetButton) {
            resetAction();
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
        if (e.getSource() == backButton) {
            backButton.setBackground(Color.LIGHT_GRAY);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == backButton) {
            backButton.setBackground(Color.WHITE);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_ENTER) {
            resetButton.doClick();
            resetAction();
        } else if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
            backButton.doClick();
            backAction();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void backAction() {
        if (loginFrame == null) {
            loginFrame = new LoginFrame();
        }
        this.setVisible(false);
        loginFrame.setVisible(true);
    }

    public void resetAction() {
        String name = nameField.getText();
        String email = emailField.getText();
        if (!librarianButton.isSelected() && !studentButton.isSelected()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select your account type",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        } else if (name.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter your name",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        } else if (email.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter your email",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Password reset link has been sent to your email",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
}

