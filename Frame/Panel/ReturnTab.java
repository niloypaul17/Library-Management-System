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
import java.util.ArrayList;
import java.util.List;

public class ReturnTab extends JPanel implements KeyListener, MouseListener {
    private final JButton returnAddButton;
    private final JTable returnTable;
    private final JTextField searchField;
    private final DefaultTableModel returnTableModel;
    private final String name;
    private HomeTab homeTab;
    private BorrowTab borrowTab;

    public ReturnTab(String name) {
        this.setLayout(new FlowLayout());

        CompoundBorder textFieldBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 1, 1, 1, Utils.LIGHT_BLUE),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)
        );

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.setPreferredSize(new Dimension(940, 150));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JLabel returnLabel = new JLabel("Return Books");
        returnLabel.setFont(Utils.TITLE_FONT);
        topPanel.add(returnLabel);

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


        String[] columnTitle = {"ID", "Name", "Author", "Publisher", "Return Date"};
        returnTableModel = new DefaultTableModel(columnTitle, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String returnPath = "data/library/" + name + "_return.txt";
        File returnFile = new File(returnPath);

        try {
            if (!returnFile.getParentFile().exists()) {
                returnFile.getParentFile().mkdir();
                returnFile.createNewFile();
            } else if (!returnFile.exists()) {
                returnFile.createNewFile();
            }
            BufferedReader reader = new BufferedReader(new FileReader(returnFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(",");
                returnTableModel.addRow(rowData);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        returnTable = new JTable(returnTableModel);
        returnTable.setFont(Utils.NORMAL_FONT);
        returnTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        returnTable.setSelectionBackground(Utils.LIGHTER_BLUE);

        JTableHeader tableHeader = new JTableHeader(returnTable.getColumnModel());
        tableHeader.setFont(Utils.NORMAL_FONT);
        tableHeader.setPreferredSize(new Dimension(tableHeader.getWidth(), 40));
        tableHeader.setBackground(Utils.LIGHT_BLUE);
        returnTable.setTableHeader(tableHeader);

        int rowHeight = 30;
        returnTable.setRowHeight(rowHeight);

        int[] columnWidths = {50, 100, 150, 150, 120};
        for (int i = 0; i < columnWidths.length; i++) {
            returnTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }

        JScrollPane returnPane = new JScrollPane(returnTable);
        returnPane.setPreferredSize(new Dimension(700, 400));
        returnPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(20, 0, 0, 0),
                BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY)
        ));
        this.add(returnPane);

        JPanel returnAdd = new JPanel();
        returnAdd.setPreferredSize(new Dimension(940, 200));
        returnAdd.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));

        returnAddButton = new JButton("Return Book");
        returnAddButton.setFont(Utils.BIG_BOLD_FONT);
        returnAddButton.setPreferredSize(new Dimension(200, 60));
        returnAddButton.setFocusable(false);
        returnAddButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        returnAddButton.addMouseListener(this);

        returnAdd.add(returnAddButton);
        this.add(returnAdd, BorderLayout.SOUTH);

        this.name = name;
    }

    public void setHomeTab(HomeTab homeTab) {
        this.homeTab = homeTab;
    }

    public void setBorrowTab(BorrowTab borrowTab) {
        this.borrowTab = borrowTab;
    }

    public int getReturnTableRowCount() {
        return returnTable.getRowCount();
    }

    public void updateData() {
        returnTableModel.setRowCount(0); // Clear existing data

        String returnPath = "data/library/" + name + "_return.txt";
        File returnFile = new File(returnPath);

        try {
            if (!returnFile.getParentFile().exists()) {
                returnFile.getParentFile().mkdir();
                returnFile.createNewFile();
            } else if (!returnFile.exists()) {
                returnFile.createNewFile();
            }
            BufferedReader reader = new BufferedReader(new FileReader(returnFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(",");
                returnTableModel.addRow(rowData);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Return Tab updated.");
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(returnTableModel);
        returnTable.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter(searchField.getText()));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == returnAddButton) {
            String borrowPath = "data/library/books.txt";
            String returnPath = "data/library/" + name + "_return.txt";

            int selectedRow = returnTable.getSelectedRow();
            if (selectedRow >= 0) {
                int idColumnIndex = returnTableModel.findColumn("ID");
                int id = Integer.parseInt(returnTableModel.getValueAt(selectedRow, idColumnIndex).toString());

                List<String> returnData = new ArrayList<>();
                List<String> borrowData = new ArrayList<>();
                try {
                    // Remove Row from Table
                    File returnFile = new File(returnPath);
                    if (!returnFile.getParentFile().exists()) {
                        returnFile.getParentFile().mkdir();
                        returnFile.createNewFile();
                    } else if (!returnFile.exists()) {
                        returnFile.createNewFile();
                    }
                    BufferedReader returnReader = new BufferedReader(new FileReader(returnFile));
                    String line;
                    while ((line = returnReader.readLine()) != null) {
                        returnData.add(line);
                    }
                    returnReader.close();
                    returnData.remove(selectedRow);
                    BufferedWriter returnWriter = new BufferedWriter(new FileWriter(returnFile));
                    for (int i = 0; i < returnData.size(); i++) {
                        returnWriter.write(returnData.get(i));
                        returnWriter.newLine();
                    }
                    returnWriter.flush();
                    returnWriter.close();
                    returnTableModel.removeRow(selectedRow);

                    // Update Borrow list
                    BufferedReader reader = new BufferedReader(new FileReader(borrowPath));
                    while ((line = reader.readLine()) != null) {
                        borrowData.add(line);
                    }
                    reader.close();

                    String[] rowData = new String[0];
                    for (int i = 0; i < borrowData.size(); i++) {
                        rowData = borrowData.get(i).split(",");
                        int foundId = Integer.parseInt(rowData[0]);
                        if (foundId == id) {
                            int issuedCount = Integer.parseInt(rowData[5]);
                            issuedCount++;
                            rowData[5] = String.valueOf(issuedCount);
                            String updatedLine = String.join(",", rowData);
                            borrowData.set(i, updatedLine);
                            BufferedWriter writer = new BufferedWriter(new FileWriter(borrowPath));
                            for (int j = 0; j < borrowData.size(); j++) {
                                writer.write(borrowData.get(j));
                                writer.newLine();
                            }
                            writer.flush();
                            writer.close();
                            break;
                        }
                    }

                    // Reload Borrow Panel
                    borrowTab.updateData();
                    homeTab.updateBookCount();

                    JOptionPane.showMessageDialog(
                            this,
                            "<html><b>Book returned successfully.</b><br><br>" +
                                    "<b>User: </b>" + name + "<br>" +
                                    "<b>Book Name:</b> " + rowData[1] + "<br>" +
                                    "<b>Author:</b> " + rowData[2] + "</html>",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Please select a book to return.",
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

