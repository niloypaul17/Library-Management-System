package Frame.Panel;

import Extra.Utils;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BorrowTab extends JPanel implements KeyListener, MouseListener {
    private final DefaultTableModel borrowTableModel;
    private final JButton borrowAddButton;
    private final JTable borrowTable;
    private final String name;
    private HomeTab homeTab;
    private ReturnTab returnTab;
    private final JTextField searchField;

    public BorrowTab(String name) {
        this.setLayout(new FlowLayout());

        CompoundBorder textFieldBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 1, 1, 1, Utils.LIGHT_BLUE),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)
        );

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        topPanel.setPreferredSize(new Dimension(940, 150));

        JLabel borrowLabel = new JLabel("Borrow Books");
        borrowLabel.setFont(Utils.TITLE_FONT);
        topPanel.add(borrowLabel);

        JPanel searchPanel = new JPanel();
        searchPanel.setPreferredSize(new Dimension(940, 80));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        ImageIcon searchIcon = new ImageIcon("images/search.png");
        JLabel iconLabel = new JLabel(searchIcon);
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        searchPanel.add(iconLabel);

        searchField = new JTextField();
        searchField.setFont(Utils.BIG_FONT);
        searchField.setPreferredSize(new Dimension(300, 50));
        searchField.setBorder(textFieldBorder);
        searchField.addKeyListener(this);
        searchPanel.add(searchField);
        topPanel.add(searchPanel);

        this.add(topPanel);

        String[] columnTitles = {"ID", "Title", "Author", "Publisher", "Quantity", "Available", "Added dates"};

        borrowTableModel = new DefaultTableModel(columnTitles, 0) {
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
                borrowTableModel.addRow(rowData);
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        borrowTable = new JTable(borrowTableModel);
        borrowTable.setFont(Utils.NORMAL_FONT);
        borrowTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        borrowTable.setSelectionBackground(Utils.LIGHTER_BLUE);

        JTableHeader tableHeader = new JTableHeader(borrowTable.getColumnModel());
        tableHeader.setFont(Utils.NORMAL_FONT);
        tableHeader.setPreferredSize(new Dimension(tableHeader.getWidth(), 40));
        tableHeader.setBackground(Utils.LIGHT_BLUE);
        borrowTable.setTableHeader(tableHeader);

        int rowHeight = 30;
        borrowTable.setRowHeight(rowHeight);

        int[] columnWidths = {50, 100, 135, 135, 95, 95, 120};
        for (int i = 0; i < columnWidths.length; i++) {
            borrowTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }

        JScrollPane borrowPane = new JScrollPane(borrowTable);
        borrowPane.setPreferredSize(new Dimension(700, 400));
        borrowPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(20, 0, 0, 0),
                BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY)
        ));
        this.add(borrowPane);

        JPanel borrowAdd = new JPanel();
        borrowAdd.setPreferredSize(new Dimension(940, 200));
        borrowAdd.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));

        borrowAddButton = new JButton("Borrow Book");
        borrowAddButton.setFont(Utils.BIG_BOLD_FONT);
        borrowAddButton.setPreferredSize(new Dimension(200, 60));
        borrowAddButton.setFocusable(false);
        borrowAddButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        borrowAddButton.addMouseListener(this);

        borrowAdd.add(borrowAddButton);
        this.add(borrowAdd);

        this.name = name;
    }

    public void setHomeTab(HomeTab homeTab) {
        this.homeTab = homeTab;
    }

    public void setReturnTab(ReturnTab returnTab) {
        this.returnTab = returnTab;
    }

    public void updateData() {
        borrowTableModel.setRowCount(0); // Clear existing data

        String borrowPath = "data/library/books.txt";
        File borrowFile = new File(borrowPath);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(borrowFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(",");
                borrowTableModel.addRow(rowData);
            }

            System.out.println("Borrow Tab updated.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getTotalBookCount() {
        int total = 0;
        int quantityColumnIndex = borrowTableModel.findColumn("Quantity");

        for (int i = 0; i < borrowTableModel.getRowCount(); i++) {
            int quantity = Integer.parseInt(borrowTableModel.getValueAt(i, quantityColumnIndex).toString());
            total += quantity;
        }

        return total;
    }

    public int getAvailableBooKCount() {
        int total = 0;
        int issueColumnIndex = borrowTableModel.findColumn("Available");

        for (int i = 0; i < borrowTableModel.getRowCount(); i++) {
            int quantity = Integer.parseInt(borrowTableModel.getValueAt(i, issueColumnIndex).toString());
            total += quantity;
        }

        return total;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(borrowTableModel);
        borrowTable.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter(searchField.getText()));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == borrowAddButton) {
            String borrowPath = "data/library/books.txt";
            String returnPath = "data/library/" + name + "_return.txt";

            int selectedRow = borrowTable.getSelectedRow();
            if (selectedRow >= 0) {
                int issuedColumnIndex = borrowTableModel.findColumn("Available");
                int issuedCount = Integer.parseInt(borrowTableModel.getValueAt(selectedRow, issuedColumnIndex).toString());

                if (issuedCount > 0) {
                    issuedCount--;
                    if (returnTab.getReturnTableRowCount() < 5) {
                        String id = borrowTableModel.getValueAt(selectedRow, 0).toString();
                        String title = borrowTableModel.getValueAt(selectedRow, 1).toString();
                        String author = borrowTableModel.getValueAt(selectedRow, 2).toString();

                        try {
                            BufferedReader reader = new BufferedReader(new FileReader(returnPath));
                            String line;
                            while ((line = reader.readLine()) != null) {
                                String[] rowData = line.split(",");
                                if (rowData[0].equals(id) && rowData[1].equals(title) && rowData[2].equals(author)) {
                                    JOptionPane.showMessageDialog(
                                            this,
                                            "You already borrowed this book.",
                                            "Unavailable",
                                            JOptionPane.WARNING_MESSAGE
                                    );
                                    return;
                                }
                            }
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }

                        borrowTable.setValueAt(issuedCount, selectedRow, issuedColumnIndex);
                    } else {
                        JOptionPane.showMessageDialog(
                                this,
                                "You already borrowed maximum about of books.",
                                "Unavailable",
                                JOptionPane.WARNING_MESSAGE
                        );
                        return;
                    }
                } else if (issuedCount == 0) {
                    JOptionPane.showMessageDialog(
                            this,
                            "No available copies to borrow for this book.",
                            "Unavailable",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                List<String> lines = new ArrayList<>();
                try {
                    File borrowFile = new File(returnPath);
                    if (!borrowFile.getParentFile().exists()) {
                        borrowFile.getParentFile().mkdir();
                        borrowFile.createNewFile();
                    } else if (!borrowFile.exists()) {
                        borrowFile.createNewFile();
                    }

                    BufferedReader reader = new BufferedReader(new FileReader(borrowPath));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        lines.add(line);
                    }
                    reader.close();
                    String[] rowData = lines.get(selectedRow).split(",");
                    rowData[5] = String.valueOf(issuedCount);
                    String updatedLine = String.join(",", rowData);
                    lines.set(selectedRow, updatedLine);
                    BufferedWriter writer = new BufferedWriter(new FileWriter(borrowPath));
                    for (int i = 0; i < lines.size(); i++) {
                        writer.write(lines.get(i));
                        writer.newLine();
                    }
                    writer.flush();
                    writer.close();

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String returnDate = LocalDate.now().plusDays(15).format(formatter);
                    String returnLine = String.join(",", rowData[0], rowData[1], rowData[2], rowData[3], returnDate);
                    BufferedWriter returnWriter = new BufferedWriter(new FileWriter(borrowFile, true));
                    returnWriter.write(returnLine);
                    returnWriter.newLine();
                    returnWriter.flush();
                    returnWriter.close();

                    returnTab.updateData();
                    homeTab.updateBookCount();

                    JOptionPane.showMessageDialog(
                            this,
                            "<html><width><b>Book borrowed successfully.</b><br><br>" +
                                    "<b>User:</b> " + name + "<br>" +
                                    "<b>Book Name:</b> " + rowData[1] + "<br>" +
                                    "<b>Author:</b> " + rowData[2] + "<br>" +
                                    "<b>Return Date:</b> " + returnDate + "</html>",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Please select a book to borrow.",
                        "No Book Selected",
                        JOptionPane.WARNING_MESSAGE
                );
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

