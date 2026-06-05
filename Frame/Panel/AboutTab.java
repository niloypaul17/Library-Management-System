package Frame.Panel;

import Extra.Utils;

import javax.swing.*;
import java.awt.*;

public class AboutTab extends JPanel {
    public AboutTab() {
        this.setLayout(new BorderLayout());

        JEditorPane editorPane = new JEditorPane();
        editorPane.setContentType("text/html");
        editorPane.setText(
                "<html><body style=\"font-family: Inter, sans-serif; font-size: 14px;\">" +
                        "<center><p style=\"font-size: 18px;\"><b>Welcome to BookNook</b></p><br>" +
                        "This is a library management system that allows you to manage your library with ease.<br>" +
                        "You can add, edit, delete, and search for books, authors, and publishers.<br>" +
                        "You can also add, edit, delete, and search for members.<br>" +
                        "You can also borrow and return books.<br>" +
                        "<br><br><p style=\"font-size: 16px;\"><b>Developed by:</b></p>" +
                        "NILOY PAUL (ID: 23-51773-2)<br>" +
                        "MD RAKIBUL HASAN(ID: 23-51750-2)<br>" +
                        "ARIJEET TALUKDER(ID: 23-51751-2)<br>" +
                        "MD ABDUR RAHMAN SHAHED(ID: 23-51741-2)<br>" +

                        "</center></html>"
        );
        editorPane.setEditable(false);
        editorPane.setBackground(Utils.GRAY);

        JScrollPane scrollPane = new JScrollPane(editorPane);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(70, 150, 0, 150));
        this.add(scrollPane, BorderLayout.CENTER);
    }
}