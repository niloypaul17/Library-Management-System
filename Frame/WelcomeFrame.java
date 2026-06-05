package Frame;

import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeFrame extends JFrame {
    private final JProgressBar progressBar;
    JButton startButton ;

    public WelcomeFrame() {
        super("BookNook");
        Image iconImage = Toolkit.getDefaultToolkit().getImage("images/library.png");
        this.setIconImage(iconImage);
        this.setSize(1260, 700);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel();
        centerPanel.setPreferredSize(new Dimension(1260, 420));

        JLabel imageLabel = new JLabel(new ImageIcon("images/welcome.jpg"));
        imageLabel.setPreferredSize(new Dimension(1260, 400));
        centerPanel.add(imageLabel);

        //Welcome Title
        JLabel titleLabel = new JLabel("Welcome to BookNook");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 30)); // Assuming Utils.TITLE_FONT is a Font object
        titleLabel.setPreferredSize(new Dimension(300, 30));
        centerPanel.add(titleLabel);

        this.add(centerPanel, BorderLayout.CENTER);

        startButton  = new JButton("Start");
        startButton .setFocusable(false);
        startButton .setFont(new Font("Comic Sans", Font.BOLD, 25));
        startButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                openLoginFrame();
            }
        });

        centerPanel.add(startButton , BorderLayout.SOUTH);

        this.add(centerPanel, BorderLayout.CENTER);

        progressBar = new JProgressBar();
        progressBar.setPreferredSize(new Dimension(1260, 30));
        progressBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.WHITE)); // Assuming Utils.LIGHT_BLUE is a Color object
        progressBar.setStringPainted(true);
        progressBar.setFont(new Font("Serif", Font.BOLD, 22)); // Assuming Utils.SMALL_BOLD_FONT is a Font object
        progressBar.setForeground(new Color(0, 128, 0)); // Assuming Utils.LIGHT_BLUE is a Color object
        progressBar.setBackground(Color.WHITE); // Assuming Utils.BACKGROUND_COLOR is a Color object
        progressBar.setUI(new BasicProgressBarUI() {
            protected Color getSelectionBackground() {
                return Color.BLACK;
            }

            protected Color getSelectionForeground() {
                return Color.WHITE;
            }
        });

        this.add(progressBar, BorderLayout.PAGE_END);

        this.setVisible(true);
    }

    public void setProgress(int progress) {
        progressBar.setValue(progress);
    }

    public void run() {
        for (int i = 0; i <= 100; i++) {
            try {
                Thread.sleep(20);
                setProgress(i);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void openLoginFrame() {
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
        this.dispose(); // Close the WelcomeFrame
    }
}
