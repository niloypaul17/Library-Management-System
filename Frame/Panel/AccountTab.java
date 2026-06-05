package Frame.Panel;

import Extra.Account;
import Extra.Utils;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AccountTab extends JPanel implements KeyListener, MouseListener {
    private final JTextField searchField;
    private final DefaultTableModel accountTableModel;
    private final JTable accountTable;
    private final JButton addButton;
    private final JTextField nameField;
    private final JTextField idField;
    private final JTextField mailField;
    private final JButton removeButton;
    private final JComboBox<String> genderBox;
    private final JButton updateButton;
    private final JButton clearButton;
    private BookTab bookTab;

    public AccountTab() {
        this.setLayout(new FlowLayout());

        CompoundBorder textFieldBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 1, 1, 1, Utils.LIGHT_BLUE),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)
        );

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        topPanel.setPreferredSize(new Dimension(940, 150));

        JLabel accountLabel = new JLabel("Accounts");
        accountLabel.setFont(Utils.TITLE_FONT);
        topPanel.add(accountLabel);

        JPanel searchPanel = new JPanel();
        searchPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        searchPanel.setPreferredSize(new Dimension(940, 80));

        ImageIcon searchIcon = new ImageIcon("images/search.png");
        JLabel iconLabel = new JLabel(searchIcon);
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        searchPanel.add(iconLabel);

        searchField = new JTextField(20);
        searchField.setPreferredSize(new Dimension(300, 50));
        searchField.setFont(Utils.BIG_FONT);
        searchField.setBorder(textFieldBorder);
        searchField.addKeyListener(this);
        searchPanel.add(searchField);
        topPanel.add(searchPanel);

        this.add(topPanel);

        String[] columnTitles = {"ID", "Name", "Email", "Issued", "Gender", "Registration Date"};

        accountTableModel = new DefaultTableModel(columnTitles, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        try {
            File fileName = new File("data/student.txt");
            fileName.createNewFile();
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rawData = line.split(",");
                String[] rowData = new String[6];

                String name = rawData[1];

                File returnFile = new File("data/library/" + name + "_return.txt");
                try {
                    returnFile.createNewFile();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                // Skip password column
                for (int i = 0; i < rawData.length; i++) {
                    if (i == 3) {
                        rowData[i] = String.valueOf(countLine("data/library/" + name + "_return.txt"));
                    } else {
                        rowData[i] = rawData[i];
                    }
                }

                accountTableModel.addRow(rowData);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        accountTable = new JTable(accountTableModel);
        accountTable.setFont(Utils.NORMAL_FONT);
        accountTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        accountTable.setSelectionBackground(Utils.LIGHTER_BLUE);
        accountTable.addMouseListener(this);

        JTableHeader tableHeader = new JTableHeader(accountTable.getColumnModel());
        tableHeader.setFont(Utils.NORMAL_FONT);
        tableHeader.setPreferredSize(new Dimension(tableHeader.getWidth(), 40));
        tableHeader.setBackground(Utils.LIGHT_BLUE);
        accountTable.setTableHeader(tableHeader);

        int rowHeight = 30;
        accountTable.setRowHeight(rowHeight);

        int[] columnWidths = {85, 85, 150, 45, 70, 165};
        for (int i = 0; i < columnWidths.length; i++) {
            accountTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }

        JScrollPane accountPane = new JScrollPane(accountTable);
        accountPane.setPreferredSize(new Dimension(700, 300));
        accountPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(20, 0, 0, 0),
                BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY)
        ));
        this.add(accountPane);

        JPanel addPanel = new JPanel();
        addPanel.setPreferredSize(new Dimension(800, 120));
        addPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        addPanel.setLayout(new GridLayout(2, 4, 10, 5));

        JLabel nameLabel = new JLabel("Name");
        nameLabel.setFont(Utils.NORMAL_FONT);
        nameField = new JTextField();
        nameField.setFont(Utils.BIG_FONT);
        nameField.setBorder(textFieldBorder);

        JLabel idLabel = new JLabel("ID");
        idLabel.setFont(Utils.NORMAL_FONT);
        idField = new JTextField();
        idField.setFont(Utils.BIG_FONT);
        idField.setBorder(textFieldBorder);

        JLabel mailLabel = new JLabel("Email");
        mailLabel.setFont(Utils.NORMAL_FONT);
        mailField = new JTextField();
        mailField.setFont(Utils.BIG_FONT);
        mailField.setBorder(textFieldBorder);

        JLabel genderLabel = new JLabel("Gender");
        genderLabel.setFont(Utils.NORMAL_FONT);
        String[] gender = {"Male", "Female", "Other"};
        genderBox = new JComboBox<>(gender);
        genderBox.setFont(Utils.NORMAL_FONT);
        genderBox.setFocusable(false);

        addPanel.add(nameLabel);
        addPanel.add(idLabel);
        addPanel.add(mailLabel);
        addPanel.add(genderLabel);
        addPanel.add(nameField);
        addPanel.add(idField);
        addPanel.add(mailField);
        addPanel.add(genderBox);

        this.add(addPanel);

        JPanel managePanel = new JPanel();
        managePanel.setPreferredSize(new Dimension(940, 130));
        managePanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

        addButton = new JButton("Add Account");
        addButton.setPreferredSize(new Dimension(150, 60));
        addButton.setFont(Utils.BIG_BOLD_FONT);
        addButton.setFocusable(false);
        addButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addButton.addMouseListener(this);
        managePanel.add(addButton);

        updateButton = new JButton("Update Account");
        updateButton.setPreferredSize(new Dimension(200, 60));
        updateButton.setFont(Utils.BIG_BOLD_FONT);
        updateButton.setFocusable(false);
        updateButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        updateButton.addMouseListener(this);
        managePanel.add(updateButton);

        removeButton = new JButton("Remove Account");
        removeButton.setPreferredSize(new Dimension(200, 60));
        removeButton.setFont(Utils.BIG_BOLD_FONT);
        removeButton.setFocusable(false);
        removeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        removeButton.addMouseListener(this);
        managePanel.add(removeButton);

        clearButton = new JButton("Clear");
        clearButton.setPreferredSize(new Dimension(100, 60));
        clearButton.setFont(Utils.BIG_BOLD_FONT);
        clearButton.setFocusable(false);
        clearButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        clearButton.addMouseListener(this);
        managePanel.add(clearButton);
        this.add(managePanel);
    }

    public void setBookTab(BookTab bookTab) {
        this.bookTab = bookTab;
    }

    public int countLine(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        int lines = 0;
        while (reader.readLine() != null) {
            lines++;
        }
        reader.close();
        return lines;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(accountTableModel);
        accountTable.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter(searchField.getText()));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == accountTable) {
            idField.setText(accountTable.getValueAt(accountTable.getSelectedRow(), 0).toString());
            nameField.setText(accountTable.getValueAt(accountTable.getSelectedRow(), 1).toString());
            mailField.setText(accountTable.getValueAt(accountTable.getSelectedRow(), 2).toString());
            genderBox.setSelectedItem(accountTable.getValueAt(accountTable.getSelectedRow(), 4).toString());
        } else if (e.getSource() == addButton) {
            addButton();
        } else if (e.getSource() == updateButton) {
            updateButton();
        } else if (e.getSource() == removeButton) {
            removeButton();
        } else if (e.getSource() == clearButton) {
            clearButton();
        }
    }

    public void addButton() {
        String userType = "Student";
        String name = nameField.getText();
        String id = idField.getText();
        String mail = mailField.getText();
        String gender = (String) genderBox.getSelectedItem();
        String password = "NO_PASSWORD_SET";

        Account account = new Account();

        if (name.isEmpty() || id.isEmpty() || mail.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "All fields are required to add an account.",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
            );
        } else if (!account.accountExists(userType, name, id)) {
            String[] rawData = new String[6];
            rawData[0] = id;
            rawData[1] = name;
            rawData[2] = mail;
            rawData[3] = password;
            rawData[4] = gender;
            rawData[5] = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mma"));

            File returnFile = new File("data/library/" + name + "_return.txt");
            try {
                returnFile.createNewFile();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            String[] rowData = new String[6];
            for (int i = 0; i < rawData.length; i++) {
                if (i == 3) {
                    try {
                        rowData[i] = String.valueOf(countLine("data/library/" + name + "_return.txt"));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    rowData[i] = rawData[i];
                }
            }

            accountTableModel.addRow(rowData);

            String writeData = String.join(",", rawData);
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("data/student.txt", true));
                writer.write(writeData);
                writer.newLine();
                writer.flush();
                writer.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Account added successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearButton();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Account already exists.",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    public void updateButton() {
        int selectedRow = accountTable.getSelectedRow();
        if (selectedRow >= 0) {
            String oldId = accountTableModel.getValueAt(selectedRow, 0).toString();
            String oldName = accountTableModel.getValueAt(selectedRow, 1).toString();

            String id = idField.getText();
            String name = nameField.getText();
            String mail = mailField.getText();
            String gender = (String) genderBox.getSelectedItem();

            boolean idExists = false;
            boolean nameExists = false;

            // Check if the id or name already exists
            for (int row = 0; row < accountTableModel.getRowCount(); row++) {
                if (row != selectedRow) {
                    String existingId = accountTableModel.getValueAt(row, 0).toString();
                    String existingName = accountTableModel.getValueAt(row, 1).toString();

                    if (existingId.equals(id)) {
                        idExists = true;
                    } else if (existingName.equals(name)) {
                        nameExists = true;
                    }
                }
            }

            if (idExists) {
                JOptionPane.showMessageDialog(
                        this,
                        "ID already exists.",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );
            } else if (nameExists) {
                JOptionPane.showMessageDialog(
                        this,
                        "Name already exists.",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );
            } else {
                accountTableModel.setValueAt(id, selectedRow, 0);
                accountTableModel.setValueAt(name, selectedRow, 1);
                accountTableModel.setValueAt(mail, selectedRow, 2);
                accountTableModel.setValueAt(gender, selectedRow, 4);

                try {
                    BufferedReader reader = new BufferedReader(new FileReader("data/student.txt"));
                    String line;
                    List<String> lines = new ArrayList<>();
                    while ((line = reader.readLine()) != null) {
                        String[] rawData = line.split(",");
                        if (rawData[0].equals(oldId) && rawData[1].equals(oldName)) {
                            rawData[0] = id;
                            rawData[1] = name;
                            rawData[2] = mail;
                            rawData[4] = gender;
                            String updatedLine = String.join(",", rawData);
                            lines.add(updatedLine);
                        } else {
                            lines.add(line);
                        }
                    }
                    reader.close();

                    BufferedWriter writer = new BufferedWriter(new FileWriter("data/student.txt"));
                    for (int i = 0; i < lines.size(); i++) {
                        writer.write(lines.get(i));
                        writer.newLine();
                    }
                    writer.flush();
                    writer.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                JOptionPane.showMessageDialog(
                        this,
                        "Account updated successfully.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select an account to update.",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    public void removeButton() {
        int selectedRow = accountTable.getSelectedRow();

        if (selectedRow >= 0) {
            String id = accountTableModel.getValueAt(selectedRow, 0).toString();
            String name = accountTableModel.getValueAt(selectedRow, 1).toString();

            File returnfile = new File("data/library/" + name + "_return.txt");
            try {
                returnfile.createNewFile();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            try {
                BufferedReader returnReader = new BufferedReader(new FileReader(returnfile));

                while (returnReader.readLine() != null) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Please notify the student to return all the books before removing the account.",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                List<String> lines = new ArrayList<>();
                BufferedReader reader = new BufferedReader(new FileReader("data/student.txt"));
                String line;

                // Run through student.txt and remove the student
                while ((line = reader.readLine()) != null) {
                    String[] rawData = line.split(",");
                    if (!(rawData[0].equals(id) && rawData[1].equals(name))) {
                        lines.add(line);
                    }
                }
                reader.close();

                // Write the updated student.txt
                BufferedWriter writer = new BufferedWriter(new FileWriter("data/student.txt"));
                for (int i = 0; i < lines.size(); i++) {
                    writer.write(lines.get(i));
                    writer.newLine();
                }
                writer.flush();
                writer.close();
                FileWriter fileWriter = new FileWriter("data/library/" + name + "_return.txt");
                fileWriter.write("");
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            // Remove the row from the table
            accountTableModel.removeRow(selectedRow);
            bookTab.updateBookTable();

            JOptionPane.showMessageDialog(
                    this,
                    "Account removed successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearButton();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select an account to remove.",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    public void clearButton() {
        nameField.setText("");
        idField.setText("");
        mailField.setText("");
        genderBox.setSelectedIndex(0);
        accountTable.clearSelection();
    }

    /*
    public void updateTableData() {
        accountTableModel.setRowCount(0);
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("data/student.txt"));
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(",");
                accountTableModel.addRow(rowData);
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    */

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

