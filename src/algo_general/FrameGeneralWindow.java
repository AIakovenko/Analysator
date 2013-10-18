package algo_general;

import algo_arrays.ArraysDataBase;
import algo_arrays.DataStructure;
import algo_files.AlgorythmFile;
import algo_files.FileDataBase;
import algo_results.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.EventObject;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 01.06.13
 * Time: 12:52
 * To change this template use File | Settings | File Templates.
 */
public class FrameGeneralWindow extends JFrame {

    private File openingFile;
    private JMenuBar menuBar;
    private JToolBar statusBar;
    private JToolBar menuBarWithButtons;
    private JTree algoTreeView;
    private JScrollPane scrollTreeView;
    private JTable mainTable;
    private JTable resultTable;

    private Dimension dimWindow;

    private DefaultMutableTreeNode algo;
    private JSplitPane upperSplit;
    private JSplitPane splitPane;
    private DefaultMutableTreeNode sort;
    private DefaultMutableTreeNode search;
    private DefaultTableModel modelMainTable;
    private DefaultTableModel modelResultTable;
    private JPopupMenu mainPopupMenu;
    private JPopupMenu resultPopupMenu;
    private ArraysDataBase dataArrays;
    private FileDataBase dataBase;
    private int selectedRow = -1;
    private ListDataOut listDataOut;
    private JLabel statusLabel;
    private int[] selResultRows;

    public FrameGeneralWindow(String title) {
        super(title);
        initialize();
    }


    public void setDataBase(FileDataBase data){

        dataBase = data;
        algoTreeView = new JTree(loadModelTreeView());
        scrollTreeView = new JScrollPane(algoTreeView);

        addNodeListener();
        algoTreeView.expandPath(new TreePath(sort.getPath()));
        algoTreeView.expandPath(new TreePath(search.getPath()));
        upperSplit.setLeftComponent(scrollTreeView);
        repaint();
    }

//    public void setArrayDataBase(ArraysDataBase data){
//        dataArrays = data;
//        setUpArraysColumn(mainTable.getColumnModel().getColumn(4));
//        mainTable.updateUI();
//    }

    public  void setListDataOut(ListDataOut data){
        listDataOut = data;

        //add rows to result table;
        for(int i = 0; i<listDataOut.getLength(); i++){
            addResultToTable(listDataOut.getData(i).getAlgoName(), listDataOut.getData(i).getMaxTime(),
                    listDataOut.getData(i).getMaxMemory(),
                    listDataOut.getData(i).getMaxValueLength(),
                    listDataOut.getData(i).getNumberOfPoints(),
                    listDataOut.getData(i).getType());
        }
    }

    public ArraysDataBase getDataArrays(){
        return dataArrays;
    }

    public FileDataBase getFileDataBase(){
        return dataBase;
    }

    public void setDataArrays(ArraysDataBase obj){
        dataArrays = new ArraysDataBase();
        for(int i = 0; i<obj.getLength(); i++){
            if(!dataArrays.addData(obj.getData(i))){
                JOptionPane.showMessageDialog(this, "Transfer Error!", "Data Transfer",JOptionPane.ERROR_MESSAGE);
                break;
            }

        }
        setUpArraysColumn(mainTable.getColumnModel().getColumn(4));
        mainTable.updateUI();
    }

    private void initialize() {

        final int INSET = 50;
        dimWindow = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension minDimWindows = new Dimension(300,300);
        new JDesktopPane();
        setResizable(true);
        setBounds(INSET, INSET,
                dimWindow.width  - INSET*2,
                dimWindow.height - INSET*2);
        this.setMinimumSize(minDimWindows);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);

        ImageIcon icon = new ImageIcon(Main.ICO_PATH + "chart_stock.png");

        this.setIconImage(icon.getImage());

        listDataOut = new ListDataOut();

        initMenuBar();
        initButtonBar();
        initStatusBar();

        dataBase = new FileDataBase();
        initTreeView();
        dataArrays = new ArraysDataBase();
        initTable();

        initPopupMenu();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                menuBar.setBounds(0, 0, getWidth(), 21);
                menuBarWithButtons.setBounds(0, 21, getWidth(), 30);
                setSplitPaneBounds();
                statusBar.setBounds(0,getHeight()-50,getWidth(),25);
                splitPane.repaint();
                scrollTreeView.repaint();
            }
        });
    }

    private void initStatusBar(){
        statusBar = new JToolBar();
        statusBar.setBounds(0,getHeight()-25,getWidth(),25);
        statusBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
        statusLabel = new JLabel("Status");
        statusBar.add(statusLabel);
        add(statusBar);
        }


    private void initTreeView(){

        algoTreeView = new JTree(loadModelTreeView());
        scrollTreeView = new JScrollPane(algoTreeView);
        addNodeListener();

        algoTreeView.expandPath(new TreePath(sort.getPath()));
        algoTreeView.expandPath(new TreePath(search.getPath()));

    }
    private void addNodeListener(){
        algoTreeView.addMouseListener( new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int selRow = algoTreeView.getRowForLocation(e.getX(), e.getY());
                TreePath selPath = algoTreeView.getPathForLocation(e.getX(), e.getY());

                if(selRow != -1) {
                    if(e.getClickCount() == 2) {
                        addRowToChoiceWindow(selPath);
                    }
                }
            }
        });
    }
    private DefaultMutableTreeNode loadModelTreeView(){

        algo = new DefaultMutableTreeNode("Algorithms");
        sort = new DefaultMutableTreeNode("Sorts");
        search = new DefaultMutableTreeNode("Search");
        ArrayList<DefaultMutableTreeNode> listSorts = new ArrayList<DefaultMutableTreeNode>();
        ArrayList<DefaultMutableTreeNode> listSearches = new ArrayList<DefaultMutableTreeNode>();
        DefaultMutableTreeNode node;

        int indexSort = 0, indexSearch = 0;

        if (dataBase != null){
            for(int i = 0; i<dataBase.getLength(); i++){
                if(dataBase.getFile(i).getAlgorythmType().equals("Sort")){

                    node = new DefaultMutableTreeNode(dataBase.getFile(i).getAlgorythmName());
                    listSorts.add(node) ;
                    sort.add(listSorts.get(indexSort));
                    indexSort++;
                }
                if(dataBase.getFile(i).getAlgorythmType().equals("Search")){
                    node = new DefaultMutableTreeNode(dataBase.getFile(i).getAlgorythmName());
                    listSearches.add(node);
                    search.add(listSearches.get(indexSearch));
                    indexSearch++;
                }
            }
        }

        algo.add(sort);
        algo.add(search);

        return algo;
    }
    private void addRowToChoiceWindow(TreePath selPath){
        String name = selPath.getPathComponent(selPath.getPathCount()-1).toString();
        AlgorythmFile currentAlgorithm = null;
        for(int i = 0; i<dataBase.getLength(); i++){
            if (dataBase.getFile(i).getAlgorythmName().equals(name)){
                currentAlgorithm = dataBase.getFile(i);
            }
        }
        if(currentAlgorithm != null){
            modelMainTable.addRow(new Object[]{currentAlgorithm.getAlgorythmType(),
                    currentAlgorithm.getAlgorythmName(),currentAlgorithm.getAlgorythmFile().getName(),5,0});
        }
    }

    private void setUpArraysColumn(TableColumn arraysColumn) {

        JComboBox comboBox = new JComboBox();
        if(dataArrays != null){
            for(int i = 0; i<dataArrays.getLength(); i++){
                comboBox.addItem(dataArrays.getData(i).getTypeData()+
                        "[" + Integer.toString(dataArrays.getData(i).getLength()) +"] "+
                dataArrays.getData(i).getState() + "; Kit=" + dataArrays.getData(i).getKitLength());
            }
        }
        arraysColumn.setCellEditor(new DefaultCellEditor(comboBox));

        DefaultTableCellRenderer renderer =
                new DefaultTableCellRenderer();
        renderer.setToolTipText("Click for combo box");
        arraysColumn.setCellRenderer(renderer);
    }


    private void initTable(){
        TableCellEditor nonSelEditor = new TableCellEditor() {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                return null;
            }
            @Override
            public Object getCellEditorValue() {return null;}
            @Override
            public boolean isCellEditable(EventObject anEvent) {return false;}
            @Override
            public boolean shouldSelectCell(EventObject anEvent) {return false;}
            @Override
            public boolean stopCellEditing() {return false;}
            @Override
            public void cancelCellEditing() {}
            @Override
            public void addCellEditorListener(CellEditorListener l) {}
            @Override
            public void removeCellEditorListener(CellEditorListener l) {}
        };

        modelMainTable = new DefaultTableModel();

        String[] columnsMainTable = {"Algorithm","Name","File name","Points","Array"};
        for(String column : columnsMainTable){
            modelMainTable.addColumn(column);
        }

        mainTable = new JTable(modelMainTable);

        mainTable.setRowHeight(20);
        mainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        mainTable.getColumnModel().getColumn(0).setPreferredWidth(getWidth() / 10);
        mainTable.getColumnModel().getColumn(1).setPreferredWidth(getWidth() / 5);
        mainTable.getColumnModel().getColumn(2).setPreferredWidth(getWidth() / 5);
        mainTable.getColumnModel().getColumn(3).setPreferredWidth(getWidth() / 20);
        mainTable.getColumnModel().getColumn(4).setPreferredWidth(getWidth() / 5);

        mainTable.getColumnModel().getColumn(0).setCellEditor(nonSelEditor);
        mainTable.getColumnModel().getColumn(1).setCellEditor(nonSelEditor);
        mainTable.getColumnModel().getColumn(2).setCellEditor(nonSelEditor);

        setUpArraysColumn(mainTable.getColumnModel().getColumn(4));   //Insert a comboBox into forth table column

        mainTable.addMouseListener(new PopupMenu(mainPopupMenu){
            @Override
            public void maybeShowPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    mainPopupMenu.show(e.getComponent(),
                            e.getX(), e.getY());
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    Point point = e.getPoint();
                    int column = mainTable.columnAtPoint(point);
                    int row = mainTable.rowAtPoint(point);
                    mainTable.setColumnSelectionInterval(column, column);
                    mainTable.setRowSelectionInterval(row, row);
                    selectedRow = mainTable.getSelectedRow();
                }

            }
        });

        JScrollPane scrollMainTable = new JScrollPane(mainTable);
        upperSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollTreeView, scrollMainTable);

        modelResultTable = new DefaultTableModel();
        String[] columnsChoiceTable = {"Name","Time, c","Memory, kB","Max_value","Number points", "Type"};

        for(String column : columnsChoiceTable){
            modelResultTable.addColumn(column);
        }

        resultTable = new JTable(modelResultTable);
        resultTable.setRowHeight(20);

        resultTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        for (int i = 0; i<resultTable.getColumnCount(); i++)
            resultTable.getColumnModel().getColumn(i).setCellEditor(nonSelEditor);

        resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        resultTable.getColumnModel().getColumn(0).setPreferredWidth(getWidth() / 3);
        resultTable.getColumnModel().getColumn(1).setPreferredWidth(getWidth() / 10);
        resultTable.getColumnModel().getColumn(2).setPreferredWidth(getWidth() / 10);
        resultTable.getColumnModel().getColumn(3).setPreferredWidth(getWidth() / 8);
        resultTable.getColumnModel().getColumn(4).setPreferredWidth(getWidth() / 8);
        resultTable.getColumnModel().getColumn(5).setPreferredWidth(getWidth() / 5);

        resultTable.addMouseListener(new PopupMenu(resultPopupMenu){
            @Override
            public void maybeShowPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    resultPopupMenu.show(e.getComponent(),
                            e.getX(), e.getY());
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    Point point = e.getPoint();
                    int column = resultTable.columnAtPoint(point);
                    int row = resultTable.rowAtPoint(point);
                    resultTable.setColumnSelectionInterval(column, column);
                    resultTable.setRowSelectionInterval(row, row);
                    selectedRow = resultTable.getSelectedRow();
                }
            }
        });

        JScrollPane scrollChoiceTable = new JScrollPane(resultTable);
        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,upperSplit,scrollChoiceTable);
        setSplitPaneBounds();
        add(splitPane);


    }
    private void setSplitPaneBounds(){
        splitPane.setBounds(5, menuBar.getHeight() + menuBarWithButtons.getHeight(),
                getWidth() - 15, getHeight() - menuBar.getHeight() - menuBarWithButtons.getHeight()-55 );
        upperSplit.setDividerSize(5);
        splitPane.setDividerSize(5);
        upperSplit.setDividerLocation(200);
        splitPane.setDividerLocation(0.7);
    }
    private void addResultToTable(String name, double time, double memory, int max_value,int numberOfPoints, String type){
        modelResultTable.addRow(new Object[]{name, time, memory, max_value, numberOfPoints, type});
    }
    private void initPopupMenu(){

        mainPopupMenu = new JPopupMenu();
        ImageIcon itemDeleteIcon = new ImageIcon(Main.ICO_PATH + "table_row_delete.png");
        JMenuItem itemDelete = new JMenuItem("  Delete    ", itemDeleteIcon);
        itemDelete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {

                if (dataArrays.deleteData(selectedRow) != null){
                    modelMainTable.removeRow(selectedRow);
                }
            }
        });
        resultPopupMenu = new JPopupMenu();
        JMenuItem itemDeleteResult = new JMenuItem("  Delete    ", itemDeleteIcon);
        itemDeleteResult.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {

                if (listDataOut.deleteData(selectedRow) != null){
                    modelResultTable.removeRow(selectedRow);
                }
            }
        });

        mainPopupMenu.add(itemDelete);
        resultPopupMenu.add(itemDeleteResult);
    }

    private void initMenuBar(){
        menuBar = new JMenuBar();

        menuBar.setBounds(0, 0, this.getWidth(), 21);

        //*********************Menu File************************
        JMenu file = new JMenu ("File");
        JMenuItem menuItemFileNew = new JMenuItem("New                  ");

        ImageIcon iconButtonNew = new ImageIcon(Main.ICO_PATH + "page_white.png");
        menuItemFileNew.setIcon(iconButtonNew);
        menuItemFileNew.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                newWorkSpace();
            }
        });
        file.add(menuItemFileNew);

        JMenuItem menuItemFileOpen = new JMenuItem("Open                  ");
        ImageIcon iconButtonOpen = new ImageIcon(Main.ICO_PATH + "folder.png");
        menuItemFileOpen.setIcon(iconButtonOpen);
        menuItemFileOpen.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                openDialog();
                setUpArraysColumn(mainTable.getColumnModel().getColumn(4));
                mainTable.updateUI();
            }
        });
        file.add(menuItemFileOpen);

        file.addSeparator();

        JMenuItem menuItemFileSave = new JMenuItem("Save               ");
        ImageIcon iconButtonSave = new ImageIcon(Main.ICO_PATH + "disk.png");
        menuItemFileSave.setIcon(iconButtonSave);
        menuItemFileSave.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(openingFile != null)
                    saveWorkspace(openingFile);
            }
        });
        file.add(menuItemFileSave);

        JMenuItem menuItemFileSaveAs = new JMenuItem("Save As               ");
        menuItemFileSaveAs.setIcon(iconButtonSave);
        menuItemFileSaveAs.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                saveAsDialog();
            }
        });
        file.add(menuItemFileSaveAs);

        file.addSeparator();

        JMenuItem menuItemFileQuit = new JMenuItem("Quit");
        menuItemFileQuit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                dispose();

            }
        });
        file.add(menuItemFileQuit);

        //***********************Menu Change***********************
        JMenu edit = new JMenu("Edit");

//        ImageIcon iconButtonEditDataBase = new ImageIcon(Main.ICO_PATH + "database_edit.png");
        JMenuItem menuItemCreateAlgo = new JMenuItem("Create algorythm file   " );
        menuItemCreateAlgo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Main.runFrameCreateAlgo();
            }
        });

        JMenuItem menuItemEditFileBase = new JMenuItem("Edit Algorythms base   ");
        menuItemEditFileBase.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Main.runFileDataBaseEditor();
            }
        });
        ImageIcon iconButtonEditDataBase = new ImageIcon(Main.ICO_PATH + "database_edit.png");
        JMenuItem menuItemAddArray = new JMenuItem("Edit Arrays base   " , iconButtonEditDataBase);
        menuItemAddArray.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Main.runDataWindow();
            }
        });

        edit.add(menuItemCreateAlgo);
        edit.addSeparator();
        edit.add(menuItemEditFileBase);
        edit.add(menuItemAddArray);

        menuBar.add(file);
        menuBar.add(edit);

        this.getContentPane().add(menuBar);
    }
    private void initButtonBar(){

        menuBarWithButtons = new JToolBar();
        menuBarWithButtons.setBounds(0,menuBar.getHeight(),getWidth(),35);
        menuBarWithButtons.setBorderPainted(true);
        menuBarWithButtons.setBorder(BorderFactory.createLineBorder(Color.gray, 1));


        JToggleButton mBarButtonOpen = new JToggleButton();

        ImageIcon iconButtonOpen = new ImageIcon(Main.ICO_PATH + "folder.png");
        mBarButtonOpen.setIcon(iconButtonOpen);
        mBarButtonOpen.setBorderPainted(false);
        mBarButtonOpen.setContentAreaFilled(false);
        mBarButtonOpen.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openDialog();

            }
        });

        JToggleButton mBarButtonNew = new JToggleButton();

        ImageIcon iconButtonNew = new ImageIcon(Main.ICO_PATH + "page_white.png");
        mBarButtonNew.setIcon(iconButtonNew);
        mBarButtonNew.setBorderPainted(false);
        mBarButtonNew.setContentAreaFilled(false);
        mBarButtonNew.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                newWorkSpace();
            }
        });

        JToggleButton mBarButtonViewResult = new JToggleButton();

        ImageIcon iconButtonViewResult = new ImageIcon(Main.ICO_PATH + "document_inspector.png");
        mBarButtonViewResult.setIcon(iconButtonViewResult);
        mBarButtonViewResult.setBorderPainted(false);
        mBarButtonViewResult.setContentAreaFilled(false);
        mBarButtonViewResult.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selResultRows = resultTable.getSelectedRows();
                Main.runResultView();
                Main.transferDataToResultView(listDataOut, selResultRows);
            }
        });

        JToggleButton mBarButtonEditDataBase = new JToggleButton();
        ImageIcon iconButtonEditDataBase = new ImageIcon(Main.ICO_PATH + "database_edit.png");
        mBarButtonEditDataBase.setIcon(iconButtonEditDataBase);
        mBarButtonEditDataBase.setBorderPainted(false);
        mBarButtonEditDataBase.setContentAreaFilled(false);
        mBarButtonEditDataBase.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Main.runDataWindow();
            }
        });

        final JToggleButton mBarButtonStop = new JToggleButton();
        ImageIcon iconButtonStop = new ImageIcon(Main.ICO_PATH + "stop.png");
        mBarButtonStop.setIcon(iconButtonStop);
        mBarButtonStop.setBorderPainted(false);
        mBarButtonStop.setContentAreaFilled(false);
        mBarButtonStop.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Main.stopTest();
            }
        });
        mBarButtonStop.setEnabled(false);


        final JToggleButton mBarButtonRun = new JToggleButton();
        ImageIcon iconButtonRun = new ImageIcon(Main.ICO_PATH + "resultset_next.png");
        mBarButtonRun.setIcon(iconButtonRun);
        mBarButtonRun.setBorderPainted(false);
        mBarButtonRun.setContentAreaFilled(false);
        mBarButtonRun.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                mBarButtonStop.setEnabled(true);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                runTest();
            }
        });


        menuBarWithButtons.add(mBarButtonOpen);
        menuBarWithButtons.add(mBarButtonNew);
        menuBarWithButtons.addSeparator();
        menuBarWithButtons.add(mBarButtonRun);
        menuBarWithButtons.add(mBarButtonStop);
        menuBarWithButtons.add(mBarButtonViewResult);
        menuBarWithButtons.addSeparator();
        menuBarWithButtons.add(mBarButtonEditDataBase);

        getContentPane().add(menuBarWithButtons);
    }
    private void openDialog(){
        JFileChooser fileOpen = new JFileChooser("./");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Algorythm workspace files .wks", "wks");
        fileOpen.setFileFilter(filter);
        int ret = fileOpen.showDialog(null, "Open file");
        if (ret == JFileChooser.APPROVE_OPTION) {
            openingFile = fileOpen.getSelectedFile();
            if(openWorkspace(openingFile))
                JOptionPane.showMessageDialog(this, "File open!", "Open file",JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Open filed!", "Open file",JOptionPane.ERROR_MESSAGE);

        }
    }
    private void saveAsDialog(){
        JFileChooser fileSave = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Algorythm workspace files .wks", "wks");
        fileSave.setFileFilter(filter);
        fileSave.setDialogTitle("Save file...");
        if (fileSave.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            openingFile = fileSave.getSelectedFile();
            if(saveWorkspace(openingFile))
                JOptionPane.showMessageDialog(this, "File saved!", "Save file",JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Save filed!", "Save file",JOptionPane.ERROR_MESSAGE);

        }
    }
    private void newWorkSpace(){
        dataBase = null;
        dataArrays = null;
        listDataOut = null;
        setDataBase(dataBase);

        int mainTableRowCount = modelMainTable.getRowCount();
        int resultTableRowCount = modelResultTable.getRowCount();

        for(int i = 0; i<mainTableRowCount; i++)
            modelMainTable.removeRow(0);

        for(int i = 0; i<resultTableRowCount; i++)
            modelResultTable.removeRow(0);

    }
    private boolean saveWorkspace(File saveFile){
        WorkspaceData saveData = new WorkspaceData(dataArrays,dataBase,listDataOut);
        try{
            FileOutputStream outputStream = new FileOutputStream(saveFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(saveData);
            objectOutputStream.close();
            return true;
        }catch(IOException ex){
            return false;
        }

    }
    private boolean openWorkspace(File openFile){
        WorkspaceData openData;

        try{
            FileInputStream inputStream = new FileInputStream(openFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            openData = (WorkspaceData) objectInputStream.readObject();
            objectInputStream.close();

//            setArrayDataBase(openData.getArraysDataBase());
            setDataArrays(openData.getArraysDataBase());
            setDataBase(openData.getFileDataBase());
            setListDataOut(openData.getListDataOut());
            return true;

        } catch(Exception ex){
            return false;
        }
    }


    private void runTest(){
        selectedRow = mainTable.getSelectedRow();
        if(selectedRow == -1)
            return;
        if((mainTable.getValueAt(selectedRow,4) == null) ||(mainTable.getValueAt(selectedRow,4).equals(0))){
            showMessage("error","Data didn't select","Data error");
        }
        else{
            AlgorythmFile aF = null;
            DataStructure  aD = null;
            for (int i = 0; i<dataBase.getLength(); i++){
                if(dataBase.getFile(i).getAlgorythmName().equals(mainTable.getValueAt(selectedRow,1).toString()))
                    aF = dataBase.getFile(i);
            }
            for (int i = 0; i<dataArrays.getLength(); i++){
                if(
                        (dataArrays.getData(i).getLength() ==
                                parseArrayLength(mainTable.getValueAt(selectedRow,4).toString()) )
                                && (dataArrays.getData(i).getTypeData().equals(parseArrayType(mainTable.getValueAt(selectedRow, 4).toString())))
                        )
                        aD = dataArrays.getData(i);
            }

            DataOut dataOut = Main.runTest(aF, aD, Integer.parseInt(mainTable.getValueAt(selectedRow, 3).toString()));


            if(listDataOut.addData(dataOut))
                addResultToTable(dataOut.getAlgoName(), dataOut.getMaxTime(), dataOut.getMaxMemory(),
                        dataOut.getMaxValueLength(), dataOut.getNumberOfPoints(), dataOut.getType());
        }
    }


    private int parseArrayLength(String str){
        int len;
        String digits = "";
        int i = 0;
        while(i<str.length()-1){
            do
                i++;
            while(str.charAt(i) != '[');
            i++;
            while (str.charAt(i) !=']'){
                digits = digits+str.charAt(i++);
            }
            break;
        }
        try{
            len = Integer.parseInt(digits);
        }catch (NumberFormatException ex){
            return -1;
        }
        return len;
    }
    private String parseArrayType(String str){
        String type = "";
        int i = 0;

        do {
            type = type+str.charAt(i);
            i++;
        }while(str.charAt(i)!='[');

        return type;
    }
    private void showMessage(String typeMessage, String text, String title){
        if(typeMessage.equals("error")){
            JOptionPane.showMessageDialog(this, text, title,JOptionPane.ERROR_MESSAGE);
        }
        if(typeMessage.equals("info")){
            JOptionPane.showMessageDialog(this, text, title,JOptionPane.INFORMATION_MESSAGE);
        }
    }

}
