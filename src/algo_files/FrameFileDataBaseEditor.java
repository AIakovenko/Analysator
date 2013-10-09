package algo_files;

import algo_general.*;
import algo_general.PopupMenu;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 18.05.13
 * Time: 18:24
 * To change this template use File | Settings | File Templates.
 */
import java.io.*;

public class FrameFileDataBaseEditor extends JFrame {


    class FrameNewFile extends JFrame{

        JTextField nameTextField;
        JTextField pathFileTextField;
        JComboBox<String> algoTypeComboBox;
        AlgorythmFile algorythmFile;

        File file;

        FrameNewFile(){
            super("New...");
            initialize();
        }


        private void initialize(){
            new JFrame();
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            this.setResizable(false);
            this.setSize(500, 200);
            this.setLocation(screenSize.width/2-getWidth()/2, screenSize.height/2-getHeight()/2);
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            this.getContentPane().setLayout(null);
            ImageIcon icon = new ImageIcon(Main.ICO_PATH +"chart_stock.png");
            this.setIconImage(icon.getImage());
            initButtons();
            initTextFields();
            initComboBox();
            //initDataBase();



        }

        private void initButtons(){
            JButton buttCancel = new JButton("Cancel");
            buttCancel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    dispose();
                }
            });

            buttCancel.setBounds(this.getWidth()-130, this.getHeight()-40,117,25);

            JButton buttOk = new JButton("Ok");
            buttOk.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {

                        createFile();
                        addNewRow(algorythmFile);
                        dispose();
                }
            });
            buttOk.setBounds(this.getWidth()-255,this.getHeight()-40,117,25);

            JButton buttOpen = new JButton("...");
            buttOpen.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    openFileDialog();
                }
            });
            buttOpen.setBounds(440, 80,50,25);


            add(buttCancel);
            add(buttOk);
            add(buttOpen);
            add(buttOpen);
        }
        private void initTextFields(){
/*===================Text Field of algorithm`s name==============*/
            nameTextField = new JTextField();
            nameTextField.setBounds(20, 30, 200,27);
            nameTextField.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    nameTextField.setText(e.getActionCommand());
                    //To change body of implemented methods use File | Settings | File Templates.
                }
            });

            JLabel nameLabel = new JLabel("Algorythm Name");
            nameLabel.setBounds(nameTextField.getX(), nameTextField.getY()-25, 200, 27);


            add(nameTextField);
            add(nameLabel);


/*===================Text Field Path file======================*/
            pathFileTextField = new JTextField();
            pathFileTextField.setBounds(nameTextField.getX(), nameTextField.getY()+50, 410, 27);

            JLabel pathFileLabel = new JLabel("File path");
            pathFileLabel.setBounds(pathFileTextField.getX(), pathFileTextField.getY()-25, 200, 27);

            add(pathFileTextField);
            add(pathFileLabel);
        }
        private void initComboBox(){
            String[] algoTypes = {"Sort", "Search"};
            algoTypeComboBox = new JComboBox<String>(algoTypes);
            algoTypeComboBox.setBounds(290, 30, 200, 27);

            add(algoTypeComboBox);

        }


        private void openFileDialog(){
            JFileChooser fileOpen = new JFileChooser("./");
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Java Class files .class", "class");
            fileOpen.setFileFilter(filter);
            int ret = fileOpen.showDialog(null, "Open file");
            if (ret == JFileChooser.APPROVE_OPTION) {
                file = fileOpen.getSelectedFile();
                pathFileTextField.setText(file.getPath());
                nameTextField.setText(file.getName().split(".class")[0]);


            }

        }

        private void createFile(){
            if ((nameTextField != null)
                    && (pathFileTextField != null)){
                algorythmFile = new AlgorythmFile(
                        nameTextField.getText(),
                        algoTypeComboBox.getSelectedItem().toString(),
                        file

                );
            }
            else
                JOptionPane.showMessageDialog(this, "Error! File hasn't added to base!", "Data Transfer",
                        JOptionPane.ERROR_MESSAGE);


        }
    }

    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton buttonApply;
    private JButton buttonOpen;

    private JPopupMenu popupMenu;

    private DefaultTableModel tableModel;
    private JTable table;
    private JScrollPane scrollTable;
    private ListSelectionModel tableSelection;

    private JTextField textFieldName;
    private JTextField textFieldFilePath;

    private JComboBox<String> comboBoxType;

    private File file;
    private FileDataBase dataBase;

    private int selectedRow = -1;

    public FrameFileDataBaseEditor(){
        super("Edit Base");
        initialize();
    }


    private void initialize(){
        new JFrame();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setResizable(false);
        this.setSize(500, 300);
        this.setLocation(screenSize.width/2-getWidth()/2, screenSize.height/2-getHeight()/2);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getContentPane().setLayout(null);
        ImageIcon icon = new ImageIcon(Main.ICO_PATH +"chart_stock.png");
        this.setIconImage(icon.getImage());

        dataBase = Main.transferFileDataFromFrameGeneralWindow();

        initButtons();
        initPopupMenu();
        initTable();
        initTextField();
        initComboBox();

    }



    private void initButtons(){
        buttonApply = new JButton("Apply");
        buttonApply.setBounds(127, getHeight()-40, 117, 25);
        buttonApply.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                applyData();
                Main.sendDataBaseToMainWindow(dataBase);
            }
        });

        buttonOK = new JButton("OK");
        buttonOK.setBounds(buttonApply.getX()+buttonApply.getWidth()+5, getHeight()-40, 117, 25);
        buttonOK.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                applyData();
                Main.sendDataBaseToMainWindow(dataBase);
                dispose();
            }
        });

        buttonCancel = new JButton("Cancel");
        buttonCancel.setBounds(buttonOK.getX()+buttonOK.getWidth()+5, getHeight()-40, 117, 25);
        buttonCancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });

        buttonOpen = new JButton("...");
        buttonOpen.setBounds(getWidth()-60, getHeight()-80, 50, 25);
        buttonOpen.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openFileDialog();
            }
        });
        add(buttonApply);
        add(buttonOK);
        add(buttonCancel);
        add(buttonOpen);

    }
    private void initPopupMenu(){
        popupMenu = new JPopupMenu();
        ImageIcon itemDeleteIcon = new ImageIcon(Main.ICO_PATH + "table_row_delete.png");
        JMenuItem itemDelete = new JMenuItem("  Delete    ", itemDeleteIcon);
        itemDelete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {

                if (dataBase.deleteFile(selectedRow) != null){
                    tableModel.removeRow(selectedRow);
                }


            }
        });
        ImageIcon itemAddNewIcon = new ImageIcon(Main.ICO_PATH + "table_row_insert.png");
        JMenuItem itemAddNew = new JMenuItem("  New     ", itemAddNewIcon);
        itemAddNew.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                FrameNewFile newFile = new FrameNewFile();
                newFile.setVisible(true);

            }
        });
        popupMenu.add(itemAddNew);
        popupMenu.addSeparator();
        popupMenu.add(itemDelete);
    }


    private void initTable(){

        tableModel = new DefaultTableModel();
        String[] columns = {"Name", "Type", "File name"};

        for (String s : columns){
            tableModel.addColumn(s);
        }


        dataBase = Main.transferFileDataFromFrameGeneralWindow();
        for (int i = 0; i<dataBase.getLength(); i++){
                tableModel.addRow(new String[]
                        {
                                dataBase.getFile(i).getAlgorythmName(),
                                dataBase.getFile(i).getAlgorythmType(),
                                dataBase.getFile(i).getAlgorythmFile().getName()
                        }
                );
        }

        table = new JTable(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(160);
        table.getColumnModel().getColumn(1).setPreferredWidth(80);
        table.getColumnModel().getColumn(2).setPreferredWidth(240);

        scrollTable = new JScrollPane(table);
        scrollTable.setBounds(10,10, getWidth()-20, 130);

        add(scrollTable);

        tableSelection = table.getSelectionModel();
        tableSelection.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedRow = table.getSelectedRow();
                if(selectedRow >= 0){
                    textFieldName.setText(dataBase.getFile(selectedRow).getAlgorythmName());
                    if(dataBase.getFile(selectedRow).getAlgorythmType().equals("Sort"))
                        comboBoxType.setSelectedIndex(1);
                    else
                    if(dataBase.getFile(selectedRow).getAlgorythmType().equals("Search"))
                        comboBoxType.setSelectedIndex(2);
                    else
                        comboBoxType.setSelectedIndex(0);
                    textFieldFilePath.setText(dataBase.getFile(selectedRow).getAlgorythmFile().getAbsolutePath() );
                    file = dataBase.getFile(selectedRow).getAlgorythmFile();
                }
                else
                {
                    textFieldName.setText("");
                    comboBoxType.setSelectedIndex(0);
                    textFieldFilePath.setText("");
                }
            }
        });

        table.addMouseListener(new algo_general.PopupMenu(popupMenu){

            @Override
            public void maybeShowPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(),
                            e.getX(), e.getY());
                    if (e.getButton() == MouseEvent.BUTTON3) {

                        Point point = e.getPoint();
                        int column = table.columnAtPoint(point);
                        int row = table.rowAtPoint(point);
                        if (row >= 0 ){
                            table.setColumnSelectionInterval(column, column);
                            table.setRowSelectionInterval(row, row);
                            selectedRow = table.getSelectedRow();
                        }
                    }
                }
            }
        });
        scrollTable.addMouseListener(new PopupMenu(popupMenu){
            @Override
            public void maybeShowPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(),
                            e.getX(), e.getY());
                    if (e.getButton() == MouseEvent.BUTTON3) {

                        Point point = e.getPoint();
                        int column = table.columnAtPoint(point);
                        int row = table.rowAtPoint(point);
                        if (row >= 0 ){
                            table.setColumnSelectionInterval(column, column);
                            table.setRowSelectionInterval(row, row);
                            selectedRow = table.getSelectedRow();
                        }
                    }
                }
            }

        });
    }

    private void initTextField(){
        JLabel labelName = new JLabel("Name");
        labelName.setBounds(10 , scrollTable.getY()+scrollTable.getHeight()+10, 100, 25 );
        textFieldName = new JTextField();
        textFieldName.setBounds(labelName.getX()+labelName.getWidth()+10,
                scrollTable.getY()+scrollTable.getHeight()+10, 370,25);

        JLabel labelFilePath = new JLabel("File name");
        labelFilePath.setBounds(10 , textFieldName.getY()+textFieldName.getHeight()+45, 100, 25 );
        textFieldFilePath = new JTextField();
        textFieldFilePath.setBounds(textFieldName.getX(),
                textFieldName.getY()+textFieldName.getHeight()+45, 310,25);

        add(labelName);
        add(textFieldName);
        add(labelFilePath);
        add(textFieldFilePath);



    }
    private void initComboBox(){
        JLabel labelType = new JLabel("Type");
        labelType.setBounds(10 , textFieldName.getY()+textFieldName.getHeight()+10, 100, 25 );

        String[] types = {"","Sort", "Search"};
        comboBoxType = new JComboBox<String>(types);
        comboBoxType.setBounds(textFieldName.getX(),
                textFieldName.getY()+textFieldName.getHeight()+10, 370,25);

        add(labelType);
        add(comboBoxType);

    }

    private void openFileDialog(){
        JFileChooser fileOpen = new JFileChooser("./");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Java Class files .class", "class");
        fileOpen.setFileFilter(filter);
        int ret = fileOpen.showDialog(null, "Open file");
        if (ret == JFileChooser.APPROVE_OPTION) {
            file = fileOpen.getSelectedFile();
            textFieldFilePath.setText(file.getPath()+file.getName());


        }

    }
    private void applyData(){
        if (selectedRow != -1){
            dataBase.getFile(selectedRow).setAlgorythmName(textFieldName.getText());
            dataBase.getFile(selectedRow).setAlgorythmType(comboBoxType.getSelectedItem().toString());
            dataBase.getFile(selectedRow).setAlgorythmFile(file);

            tableModel.setValueAt(dataBase.getFile(selectedRow).getAlgorythmName(),selectedRow, 0);
            tableModel.setValueAt(dataBase.getFile(selectedRow).getAlgorythmType(),selectedRow, 1);
            tableModel.setValueAt(file.getName(),selectedRow, 2);
        }

    }


    private void addNewRow(AlgorythmFile data){
        tableModel.addRow(new String[]
                {
                        data.getAlgorythmName(),
                        data.getAlgorythmType(),
                        data.getAlgorythmFile().getName()
                }
        );
        dataBase.addFile(data);

    }


}
