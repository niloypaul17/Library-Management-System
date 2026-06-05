package Frame.Panel;

import Extra.Utils;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BookTab extends JPanel implements KeyListener, MouseListener {
    private final JTextField searchField;
    private final DefaultTableModel bookTableModel;
    private final JTable bookTable;
    private final JButton addButton;
    private final JTextField titleField;
    private final JTextField authorField;
    private final JTextField pubField;
    private final JTextField quantityField;
    private final JButton removeButton;
    private final JButton clearButton;
    private final JButton updateButton;

    public BookTab() {
        this.setLayout(new FlowLayout());

        CompoundBorder textFieldBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 1, 1, 1, Utils.LIGHT_BLUE),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)
        );

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        topPanel.setPreferredSize(new Dimension(940, 150));

        JLabel bookLabel = new JLabel("Books");
        bookLabel.setFont(Utils.TITLE_FONT);
        topPanel.add(bookLabel);

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

        String[] columnTitles = {"ID", "Title", "Author", "Publisher", "Quantity", "Available", "Added dates"};

        bookTableModel = new DefaultTableModel(columnTitles, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        try {
            BufferedReader reader = new BufferedReader(new FileReader("data/library/books.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(",");
                bookTableModel.addRow(rowData);
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        bookTable = new JTable(bookTableModel);
        bookTable.setFont(Utils.NORMAL_FONT);
        bookTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bookTable.setSelectionBackground(Utils.LIGHTER_BLUE);
        bookTable.addMouseListener(this);

        JTableHeader tableHeader = new JTableHeader(bookTable.getColumnModel());
        tableHeader.setFont(Utils.NORMAL_FONT);
        tableHeader.setBackground(Utils.LIGHT_BLUE);
        tableHeader.setPreferredSize(new Dimension(tableHeader.getWidth(), 40));
        bookTable.setTableHeader(tableHeader);

        int rowHeight = 30;
        bookTable.setRowHeight(rowHeight);

        int[] columnWidths = {50, 100, 135, 135, 95, 95, 120};
        for (int i = 0; i < columnWidths.length; i++) {
            bookTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }

        JScrollPane bookPane = new JScrollPane(bookTable);
        bookPane.setPreferredSize(new Dimension(700, 300));
        bookPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(20, 0, 0, 0),
                BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY)
        ));
        this.add(bookPane);

        JPanel addPanel = new JPanel();
        addPanel.setPreferredSize(new Dimension(800, 120));
        addPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        addPanel.setLayout(new GridLayout(2, 4, 10, 5));

        JLabel titleLabel = new JLabel("Title");
        titleLabel.setFont(Utils.NORMAL_FONT);
        titleField = new JTextField();
        titleField.setFont(Utils.BIG_FONT);
        titleField.setBorder(textFieldBorder);

        JLabel authorLabel = new JLabel("Author");
        authorLabel.setFont(Utils.NORMAL_FONT);
        authorField = new JTextField();
        authorField.setFont(Utils.BIG_FONT);
        authorField.setBorder(textFieldBorder);

        JLabel pubLabel = new JLabel("Publisher");
        pubLabel.setFont(Utils.NORMAL_FONT);
        pubField = new JTextField();
        pubField.setFont(Utils.BIG_FONT);
        pubField.setBorder(textFieldBorder);

        JLabel quantityLabel = new JLabel("Quantity");
        quantityLabel.setFont(Utils.NORMAL_FONT);
        quantityField = new JTextField();
        quantityField.setFont(Utils.BIG_FONT);
        quantityField.setBorder(textFieldBorder);

        addPanel.add(titleLabel);
        addPanel.add(authorLabel);
        addPanel.add(pubLabel);
        addPanel.add(quantityLabel);
        addPanel.add(titleField);
        addPanel.add(authorField);
        addPanel.add(pubField);
        addPanel.add(quantityField);

        this.add(addPanel);

        JPanel managePanel = new JPanel();
        managePanel.setPreferredSize(new Dimension(940, 130));
        managePanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

        addButton = new JButton("Add Book");
        addButton.setPreferredSize(new Dimension(150, 60));
        addButton.setFont(Utils.BIG_BOLD_FONT);
        addButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addButton.setFocusable(false);
        addButton.addMouseListener(this);
        managePanel.add(addButton);

        updateButton = new JButton("Update Book");
        updateButton.setPreferredSize(new Dimension(170, 60));
        updateButton.setFont(Utils.BIG_BOLD_FONT);
        updateButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        updateButton.setFocusable(false);
        updateButton.addMouseListener(this);
        managePanel.add(updateButton);

        removeButton = new JButton("Remove Book");
        removeButton.setPreferredSize(new Dimension(170, 60));
        removeButton.setFont(Utils.BIG_BOLD_FONT);
        removeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        removeButton.setFocusable(false);
        removeButton.addMouseListener(this);
        managePanel.add(removeButton);

        clearButton = new JButton("Clear");
        clearButton.setPreferredSize(new Dimension(100, 60));
        clearButton.setFont(Utils.BIG_BOLD_FONT);
        clearButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        clearButton.setFocusable(false);
        clearButton.addMouseListener(this);
        managePanel.add(clearButton);
        this.add(managePanel);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(bookTableModel);
        bookTable.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter(searchField.getText()));
    }

    public void updateData() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("data/library/books.txt"));
            for (int i = 0; i < bookTableModel.getRowCount(); i++) {
                for (int j = 0; j < bookTableModel.getColumnCount(); j++) {
                    writer.write(bookTableModel.getValueAt(i, j).toString());
                    if (j < bookTableModel.getColumnCount() - 1) {
                        writer.write(",");
                    }
                }
                writer.newLine();
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateBookTable() {
        try {
            bookTableModel.setRowCount(0);
            BufferedReader reader = new BufferedReader(new FileReader("data/library/books.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(",");
                bookTableModel.addRow(rowData);
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int selectedRow = bookTable.getSelectedRow();
        if (e.getSource() == bookTable) {
            titleField.setText((String) bookTableModel.getValueAt(selectedRow, 1));
            authorField.setText((String) bookTableModel.getValueAt(selectedRow, 2));
            pubField.setText((String) bookTableModel.getValueAt(selectedRow, 3));
            quantityField.setText((String) bookTableModel.getValueAt(selectedRow, 4));
        } else if (e.getSource() == addButton) {
            String title = titleField.getText();
            String author = authorField.getText();
            String publisher = pubField.getText();
            String quantify = quantityField.getText();

            if (title.isEmpty() || author.isEmpty() || publisher.isEmpty() || quantify.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "All fields are required to add a book.",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );
            } else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String date = LocalDate.now().format(formatter);

                String[] newRowData = new String[7];
                newRowData[0] = String.valueOf(bookTable.getRowCount() + 1);
                newRowData[1] = titleField.getText();
                newRowData[2] = authorField.getText();
                newRowData[3] = pubField.getText();
                newRowData[4] = quantityField.getText();
                newRowData[5] = quantityField.getText();
                newRowData[6] = date;

                bookTableModel.addRow(newRowData);

                titleField.setText("");
                authorField.setText("");
                pubField.setText("");
                quantityField.setText("");

                String addRowData = String.join(",", newRowData);
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter("data/library/books.txt", true));
                    writer.write(addRowData);
                    writer.newLine();
                    writer.flush();
                    writer.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } else if (e.getSource() == updateButton) {
            if (selectedRow >= 0) {
                String title = titleField.getText();
                String author = authorField.getText();
                String publisher = pubField.getText();
                String quantity = quantityField.getText();

                int currentQuantity = Integer.parseInt(bookTableModel.getValueAt(selectedRow, 4).toString());
                int currentAvailable = Integer.parseInt(bookTableModel.getValueAt(selectedRow, 5).toString());

                if (title.isEmpty() || author.isEmpty() || publisher.isEmpty() || quantity.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            this,
                            "All fields are required to update a book.",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE
                    );
                } else {
                    if (Integer.parseInt(quantityField.getText()) < Integer.parseInt(quantityField.getText())) {
                        JOptionPane.showMessageDialog(
                                this,
                                "Quantity must be greater than or equal to available.",
                                "Warning",
                                JOptionPane.WARNING_MESSAGE
                        );
                    } else {
                        int total = Integer.parseInt(quantityField.getText()) - (currentQuantity - currentAvailable);
                        System.out.println(total);

                        bookTableModel.setValueAt(titleField.getText(), selectedRow, 1);
                        bookTableModel.setValueAt(authorField.getText(), selectedRow, 2);
                        bookTableModel.setValueAt(pubField.getText(), selectedRow, 3);
                        bookTableModel.setValueAt(quantityField.getText(), selectedRow, 4);
                        bookTableModel.setValueAt(String.valueOf(total), selectedRow, 5);

                        updateData();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Please select a row to update.",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        } else if (e.getSource() == removeButton) {
            if (selectedRow >= 0) {
                bookTableModel.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Please select a row to remove.",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );
            }
            updateData();
        } else if (e.getSource() == clearButton) {
            titleField.setText("");
            authorField.setText("");
            pubField.setText("");
            quantityField.setText("");
            bookTable.clearSelection();
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

