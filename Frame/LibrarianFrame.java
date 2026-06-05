package Frame;

import Extra.Utils;
import Frame.Panel.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LibrarianFrame extends JFrame implements MouseListener {
    private final JTabbedPane tabbedPane;
    private final JButton homeButton;
    private final JButton bookButton;
    private final JButton accountButton;
    private final JButton aboutButton;
    private final JButton logoutButton;
    private final JButton profileButton;
    private LoginFrame loginFrame;

    public LibrarianFrame(String name, String userType) {
        super("BookNook - Logged in as Librarian");
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
                BorderFactory.createEmptyBorder(0, -5, 0, 0)
        ));
        homeButton.setFocusPainted(false);
        homeButton.addMouseListener(this);

        bookButton = new JButton("Books");
        bookButton.setFont(Utils.NORMAL_BOLD_FONT);
        bookButton.setBackground(Utils.LIGHTER_BLUE);
        bookButton.setFocusPainted(false);
        bookButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        bookButton.addMouseListener(this);

        accountButton = new JButton("Account");
        accountButton.setFont(Utils.NORMAL_BOLD_FONT);
        accountButton.setBackground(Utils.LIGHTER_BLUE);
        accountButton.setFocusPainted(false);
        accountButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        accountButton.addMouseListener(this);

        profileButton = new JButton("Profile");
        profileButton.setFont(Utils.NORMAL_BOLD_FONT);
        profileButton.setBackground(Utils.LIGHTER_BLUE);
        profileButton.setFocusPainted(false);
        profileButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        profileButton.addMouseListener(this);

        aboutButton = new JButton("About");
        aboutButton.setFont(Utils.NORMAL_BOLD_FONT);
        aboutButton.setBackground(Utils.LIGHTER_BLUE);
        aboutButton.setFocusPainted(false);
        aboutButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        aboutButton.addMouseListener(this);

        logoutButton = new JButton("Log Out");
        logoutButton.setFont(Utils.NORMAL_BOLD_FONT);
        logoutButton.setBackground(Utils.LIGHTER_RED);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        logoutButton.setPreferredSize(new Dimension(300, 60));
        logoutButton.addMouseListener(this);

        tabPanel.add(homeButton);
        tabPanel.add(bookButton);
        tabPanel.add(accountButton);
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

        BookTab bookTab = new BookTab();
        AccountTab accountTab = new AccountTab();
        HomeAdminTab homeAdminTab = new HomeAdminTab(name, userType);
        ProfileTab profileTab = new ProfileTab(name, userType);
        AboutTab aboutTab = new AboutTab();

        accountTab.setBookTab(bookTab);

        tabbedPane.add("Home", homeAdminTab);
        tabbedPane.add("Books", bookTab);
        tabbedPane.add("Account", accountTab);
        tabbedPane.add("Profile", profileTab);
        tabbedPane.add("About", aboutTab);

        rightPanel.add(tabbedPane);

        this.add(leftPanel, BorderLayout.WEST);
        this.add(rightPanel, BorderLayout.EAST);

        this.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JButton clickedButton = (JButton) e.getSource();

        homeButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        homeButton.setBackground(Utils.LIGHTER_BLUE);
        bookButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        bookButton.setBackground(Utils.LIGHTER_BLUE);
        accountButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        accountButton.setBackground(Utils.LIGHTER_BLUE);
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
        } else if (e.getSource() == bookButton) {
            tabbedPane.setSelectedIndex(1);
        } else if (e.getSource() == accountButton) {
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
                    selectedButton = bookButton;
                } else if (index == 2) {
                    selectedButton = accountButton;
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
        if (e.getSource() == homeButton && tabbedPane.getSelectedIndex() != 0) {
            homeButton.setBackground(Utils.LIGHT_BLUE);
        } else if (e.getSource() == bookButton && tabbedPane.getSelectedIndex() != 1) {
            bookButton.setBackground(Utils.LIGHT_BLUE);
        } else if (e.getSource() == accountButton && tabbedPane.getSelectedIndex() != 2) {
            accountButton.setBackground(Utils.LIGHT_BLUE);
        } else if (e.getSource() == profileButton && tabbedPane.getSelectedIndex() != 3) {
            profileButton.setBackground(Utils.LIGHT_BLUE);
        } else if (e.getSource() == logoutButton) {
            logoutButton.setBackground(Utils.LIGHT_RED);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == homeButton && tabbedPane.getSelectedIndex() != 0) {
            homeButton.setBackground(Utils.LIGHTER_BLUE);
        } else if (e.getSource() == bookButton && tabbedPane.getSelectedIndex() != 1) {
            bookButton.setBackground(Utils.LIGHTER_BLUE);
        } else if (e.getSource() == accountButton && tabbedPane.getSelectedIndex() != 2) {
            accountButton.setBackground(Utils.LIGHTER_BLUE);
        } else if (e.getSource() == profileButton && tabbedPane.getSelectedIndex() != 3) {
            profileButton.setBackground(Utils.LIGHTER_BLUE);
        } else if (e.getSource() == logoutButton) {
            logoutButton.setBackground(Utils.LIGHTER_RED);
        }
    }
}

