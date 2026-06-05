package Frame;

import Extra.Utils;
import Frame.Panel.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class StudentFrame extends JFrame implements ActionListener, MouseListener {
    private final JTabbedPane tabbedPane;
    private final JButton homeButton;
    private final JButton borrowButton;
    private final JButton returnButton;
    private final JButton profileButton;
    private final JButton aboutButton;
    private final JButton logoutButton;
    private LoginFrame loginFrame;

    public StudentFrame(String name, String userType) {
        super("BookNook - Logged in as Student");
        Image iconImage = Toolkit.getDefaultToolkit().getImage("images/library.png");
        this.setIconImage(iconImage);
        this.setSize(1260, 800);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(300, 800));
        leftPanel.setBackground(Utils.BACKGROUND_COLOR);

        JLabel titleLabel = new JLabel("BookNook");
        titleLabel.setFont(Utils.TITLE_FONT);
        titleLabel.setPreferredSize(new Dimension(300, 120));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        JPanel tabPanel = new JPanel();
        tabPanel.setLayout(new GridLayout(8, 1, 5, 0));
        tabPanel.setPreferredSize(new Dimension(300, 500));
        tabPanel.setBackground(Utils.BACKGROUND_COLOR);

        homeButton = new JButton("Home");
        homeButton.setFont(Utils.NORMAL_BOLD_FONT);
        homeButton.setBackground(Utils.LIGHT_BLUE);
        homeButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 5, 0, 0, Utils.BLUE),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));
        homeButton.setFocusPainted(false);
        homeButton.addMouseListener(this);

        borrowButton = new JButton("Borrow");
        borrowButton.setFont(Utils.NORMAL_BOLD_FONT);
        borrowButton.setBackground(Utils.LIGHTER_BLUE);
        borrowButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        borrowButton.setFocusPainted(false);
        borrowButton.addMouseListener(this);

        returnButton = new JButton("Return");
        returnButton.setFont(Utils.NORMAL_BOLD_FONT);
        returnButton.setBackground(Utils.LIGHTER_BLUE);
        returnButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        returnButton.setFocusPainted(false);
        returnButton.addMouseListener(this);

        profileButton = new JButton("Profile");
        profileButton.setFont(Utils.NORMAL_BOLD_FONT);
        profileButton.setBackground(Utils.LIGHTER_BLUE);
        profileButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        profileButton.setFocusPainted(false);
        profileButton.addMouseListener(this);

        aboutButton = new JButton("About");
        aboutButton.setFont(Utils.NORMAL_BOLD_FONT);
        aboutButton.setBackground(Utils.LIGHTER_BLUE);
        aboutButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        aboutButton.setFocusPainted(false);
        aboutButton.addMouseListener(this);

        logoutButton = new JButton("Log Out");
        logoutButton.setFont(Utils.NORMAL_BOLD_FONT);
        logoutButton.setBackground(Utils.LIGHTER_RED);
        logoutButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        logoutButton.setPreferredSize(new Dimension(300, 60));
        logoutButton.setFocusPainted(false);
        logoutButton.addMouseListener(this);

        tabPanel.add(homeButton);
        tabPanel.add(borrowButton);
        tabPanel.add(returnButton);
        tabPanel.add(profileButton);
        tabPanel.add(aboutButton);

        JPanel logoutPanel = new JPanel();
        logoutPanel.setPreferredSize(new Dimension(300, 100));
        logoutPanel.setBackground(Utils.BACKGROUND_COLOR);
        logoutPanel.add(logoutButton);

        leftPanel.add(titleLabel, BorderLayout.NORTH);
        leftPanel.add(tabPanel, BorderLayout.CENTER);
        leftPanel.add(logoutPanel, BorderLayout.SOUTH);

        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(940, 800));
        rightPanel.setLayout(null);
        tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(-2, -25, 944, 825);

        BorrowTab borrowTab = new BorrowTab(name);
        ReturnTab returnTab = new ReturnTab(name);
        ProfileTab profileTab = new ProfileTab(name, userType);
        HomeTab homeTab = new HomeTab(borrowTab, returnTab, name, userType);
        AboutTab aboutTab = new AboutTab();

        borrowTab.setHomeTab(homeTab);
        borrowTab.setReturnTab(returnTab);
        returnTab.setHomeTab(homeTab);
        returnTab.setBorrowTab(borrowTab);

        tabbedPane.add("Home", homeTab);
        tabbedPane.add("Borrow", borrowTab);
        tabbedPane.add("Return", returnTab);
        tabbedPane.add("Profile", profileTab);
        tabbedPane.add("About", aboutTab);

        rightPanel.add(tabbedPane);

        this.add(leftPanel, BorderLayout.WEST);
        this.add(rightPanel, BorderLayout.EAST);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JButton clickedButton = (JButton) e.getSource();

        homeButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        homeButton.setBackground(Utils.LIGHTER_BLUE);
        borrowButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        borrowButton.setBackground(Utils.LIGHTER_BLUE);
        returnButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        returnButton.setBackground(Utils.LIGHTER_BLUE);
        profileButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        profileButton.setBackground(Utils.LIGHTER_BLUE);
        aboutButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        aboutButton.setBackground(Utils.LIGHTER_BLUE);
        logoutButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        logoutButton.setBackground(Utils.LIGHTER_RED);

        clickedButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 5, 0, 0, Utils.BLUE),
                BorderFactory.createEmptyBorder(0, -5, 0, 0)
        ));
        clickedButton.setBackground(Utils.LIGHT_BLUE);

        if (e.getSource() == homeButton) {
            tabbedPane.setSelectedIndex(0);
        } else if (e.getSource() == borrowButton) {
            tabbedPane.setSelectedIndex(1);
        } else if (e.getSource() == returnButton) {
            tabbedPane.setSelectedIndex(2);
        } else if (e.getSource() == profileButton) {
            tabbedPane.setSelectedIndex(3);
        } else if (e.getSource() == aboutButton) {
            tabbedPane.setSelectedIndex(4);
        } else if (e.getSource() == logoutButton) {
            logoutButton.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 5, 0, 0, Utils.RED),
                    BorderFactory.createEmptyBorder(0, -5, 0, 0)
            ));
            logoutButton.setBackground(Utils.LIGHT_RED);

            int answer = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to log out?",
                    "Log Out Confirmation",
                    JOptionPane.YES_NO_OPTION
            );

            if (answer == JOptionPane.YES_OPTION) {
                File rememberFile = new File("data/remember.txt");
                try {
                    FileWriter writer = new FileWriter(rememberFile);
                    writer.write("");
                    writer.flush();
                    writer.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                if (loginFrame == null) {
                    loginFrame = new LoginFrame();
                }
                loginFrame.setVisible(true);
                this.dispose();
            } else {
                int index = tabbedPane.getSelectedIndex();
                tabbedPane.setSelectedIndex(index);

                JButton selectedButton = null;
                if (index == 0) {
                    selectedButton = homeButton;
                } else if (index == 1) {
                    selectedButton = borrowButton;
                } else if (index == 2) {
                    selectedButton = returnButton;
                } else if (index == 3) {
                    selectedButton = profileButton;
                } else if (index == 4) {
                    selectedButton = aboutButton;
                }

                if (selectedButton != null) {
                    selectedButton.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createMatteBorder(0, 5, 0, 0, Utils.BLUE),
                            BorderFactory.createEmptyBorder(0, 0, 0, 0)
                    ));
                    selectedButton.setBackground(Utils.LIGHT_BLUE);
                }

                logoutButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
                logoutButton.setBackground(Utils.LIGHTER_RED);
            }
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
}

