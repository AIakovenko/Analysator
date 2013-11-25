package algo_arrays;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 07.05.13
 * Time: 23:33
 */

import algo_general.*;
import algo_general.PopupMenu;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class FrameAddArraysData extends JFrame  {
    private final Dimension WINDOW_SIZE = new Dimension(700,400);
    private final int STEP_LEFT = 10;
    private final int STEP_DOWN = 40;
    private final int FIELD_WIDTH = 200;
    private final int FIELD_HEIGHT = 25;
    private final int GAP = 10; // This constant sets space between components

    private JTextField textFieldResult;
    private JComboBox comboBoxType;
    private JComboBox comboBoxState;
    private DefaultTableModel tableModelArrays;
    private JPopupMenu popupMenu;
    private ArraysDataBase dataList;
    private JSpinner numberKitElem;
    private int selectedRow = -1;
    private Map<Integer,Object> structs;

    public FrameAddArraysData(){
        super("Arrays Editor");
        initialize();
    }

    private void initialize() {

        new JFrame();
        this.setResizable(false);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(WINDOW_SIZE);
        this.setLocation(screenSize.width/2-getWidth()/2, screenSize.height/2-getHeight()/2);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.getContentPane().setLayout(null);

//        dataList = Main.transferArraysDataFromFrameGeneralWindow();
        dataList = Main.getStructureBase();

        initTextField();
        initComboBox();
        initTable();
        initButtons();
        initPopupMenu();
        initSpinner();
    }

    private void initButtons(){
        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.setBounds(getWidth() - 135, getHeight() - 65 ,
                Main.buttonSize.width, Main.buttonSize.height);
        buttonCancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });

        JButton buttonOk = new JButton("OK");
        buttonOk.setBounds(getWidth()-255, getHeight()-65,
                Main.buttonSize.width,Main.buttonSize.height);
        buttonOk.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                Main.transferDataArrayToMainWindow(dataList);
                Main.setStructureBase(dataList);
                dispose();
            }
        });

        JButton buttonGenerate = new JButton("Generate");
        buttonGenerate.setSize(Main.buttonSize);
        buttonGenerate.setLocation(STEP_LEFT,
                getHeight()-65);
        buttonGenerate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (checkData()){
                    try{
                        GenerateDataKit dataKit = new GenerateDataKit(Integer.parseInt(textFieldResult.getText()),
                                structs.get(comboBoxType.getSelectedIndex()),
                                comboBoxState.getSelectedItem().toString(),
                                Integer.parseInt(numberKitElem.getValue().toString()));

                        Thread threadDataKit = new Thread(dataKit);
                        threadDataKit.run();
                        threadDataKit.join();
                        /*DataStructure data = Main.transferArraysDataFromFrameGeneralWindow().getData(
                            Main.transferArraysDataFromFrameGeneralWindow().getLength()-1
                        );*/
                        /*DataStructures data = Main.transferArraysDataFromFrameGeneralWindow().getData(
                                Main.transferArraysDataFromFrameGeneralWindow().getLength()-1
                        );*/
                        DataStructures data = Main.getStructureBase().getData(
                                Main.getStructureBase().getLength()-1
                        );
                        tableModelArrays.addRow(loadRow(data));
                    }catch (InterruptedException ex){
                        showMessage("error", "Error# 1001 "+ex.getMessage(),"Add data error!");
                    }catch (UnsupportedOperationException ex){
                        showMessage("error", "Error# 1001 "+ex.getMessage(),"Add data error!");
                        ex.printStackTrace();
                    }

                }
            }
        });

        add(buttonCancel);
        add(buttonOk);
        add(buttonGenerate);

    }
    private void initTable(){
        TableCellEditor nonSelEditor = new TableCellEditor() {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                return null;
            }

            @Override
            public Object getCellEditorValue() {return null; }

            @Override
            public boolean isCellEditable(EventObject anEvent) {return false;}

            @Override
            public boolean shouldSelectCell(EventObject anEvent) {return false;}

            @Override
            public boolean stopCellEditing() {return false;}

            @Override
            public void cancelCellEditing() {/*NOP*/}

            @Override
            public void addCellEditorListener(CellEditorListener l) {/*NOP*/}

            @Override
            public void removeCellEditorListener(CellEditorListener l) {/*NOP*/}
        };
        tableModelArrays = new DefaultTableModel();

        String[] columns = {"N", "Data type", "Length", "State","Kit"};
        for(String s : columns){
            tableModelArrays.addColumn(s);
        }

        if(dataList != null){
            for(int i = 0; i<dataList.getLength(); i++){
                tableModelArrays.addRow(new String[]{
                        Integer.toString(i + 1),
                        /*dataList.getData(i).getTypeData(),*/
                        dataList.getData(i).getType(),
                       /* Integer.toString(dataList.getData(i).getLength()),*/
                        Integer.toString(dataList.getData(i).getLength(0)),
                        dataList.getData(i).getState()
                });
            }
        }

        final JTable tableArrays = new JTable(tableModelArrays);

        tableArrays.addMouseListener(new PopupMenu(popupMenu){
            @Override
            public void maybeShowPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(),
                            e.getX(), e.getY());
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        Point point = e.getPoint();
                        int column = tableArrays.columnAtPoint(point);
                        int row = tableArrays.rowAtPoint(point);
                        tableArrays.setColumnSelectionInterval(column, column);
                        tableArrays.setRowSelectionInterval(row, row);
                        selectedRow = tableArrays.getSelectedRow();
                    }
                }
            }
        });

        tableArrays.setRowHeight(20);
        tableArrays.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tableArrays.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableArrays.getColumnModel().getColumn(0).setPreferredWidth(23);
        tableArrays.getColumnModel().getColumn(1).setPreferredWidth(67);
        tableArrays.getColumnModel().getColumn(2).setPreferredWidth(70);
        tableArrays.getColumnModel().getColumn(3).setPreferredWidth(65);
        tableArrays.getColumnModel().getColumn(4).setPreferredWidth(27);

        for (int i = 0; i<tableArrays.getColumnCount(); i++ ){
            tableArrays.getColumnModel().getColumn(i).setCellEditor(nonSelEditor);
        }

        JScrollPane scrollTable = new JScrollPane(tableArrays);
        scrollTable.setSize(255,getHeight()-150);
        scrollTable.setLocation(getWidth()-scrollTable.getWidth()-15, 40);

        add(scrollTable);

    }
    private void initTextField(){

        final int MIN_DIAPASON = 0;
        final int MAX_DIAPASON = 10000000;

        JLabel labelLength = new JLabel("Array length");
        labelLength.setBounds(STEP_LEFT, STEP_DOWN-40, FIELD_WIDTH, FIELD_HEIGHT);

        JLabel labelX1000000 = new JLabel("x1000000");
        labelX1000000.setBounds(STEP_LEFT, STEP_DOWN-20, FIELD_WIDTH, FIELD_HEIGHT);

        final JTextField textFieldLength = new JTextField("0");
        textFieldLength.setBounds(STEP_LEFT, STEP_DOWN, FIELD_WIDTH, FIELD_HEIGHT);
        textFieldLength.setEditable(false);

        final JSlider sliderLength = new JSlider();
        sliderLength.setBounds(STEP_LEFT,textFieldLength.getY()+35, FIELD_WIDTH, FIELD_HEIGHT);
        sliderLength.setMaximum(MIN_DIAPASON);
        sliderLength.setMaximum(MAX_DIAPASON);

        sliderLength.setValue(sliderLength.getMinimum());
        sliderLength.setMajorTickSpacing(MAX_DIAPASON / 10);

        sliderLength.setPaintTicks(true);
        sliderLength.setSnapToTicks(true);

        JLabel labelX10000 = new JLabel("x10000");
        labelX10000.setBounds(STEP_LEFT, sliderLength.getY()+sliderLength.getHeight()+GAP,
                FIELD_WIDTH, FIELD_HEIGHT);
        final JTextField textFieldLenDiv100 = new JTextField("0");
        textFieldLenDiv100.setBounds(labelX10000.getX(),labelX10000.getY()+GAP*2, FIELD_WIDTH,FIELD_HEIGHT);
        textFieldLenDiv100.setEditable(false);

        final JSlider sliderLenDiv100 = new JSlider();
        sliderLenDiv100.setBounds(textFieldLenDiv100.getX(),textFieldLenDiv100.getY()+35, FIELD_WIDTH, FIELD_HEIGHT);
        sliderLenDiv100.setMaximum(MIN_DIAPASON);
        sliderLenDiv100.setMaximum(MAX_DIAPASON/100);
        sliderLenDiv100.setValue(sliderLenDiv100.getMinimum());
        sliderLenDiv100.setMajorTickSpacing(sliderLenDiv100.getMaximum() / 10);
        sliderLenDiv100.setPaintTicks(true);
        sliderLenDiv100.setSnapToTicks(true);


        JLabel labelX1000 = new JLabel("x1000");
        labelX1000.setBounds(STEP_LEFT, sliderLenDiv100.getY()+sliderLenDiv100.getHeight()+GAP,
                FIELD_WIDTH, FIELD_HEIGHT);
        final JTextField textFieldLenDiv100000 = new JTextField("0");
        textFieldLenDiv100000.setBounds(STEP_LEFT,labelX1000.getY()+GAP*2, FIELD_WIDTH,FIELD_HEIGHT);
        textFieldLenDiv100000.setEditable(false);

        final JSlider sliderLenDiv100000 = new JSlider();
        sliderLenDiv100000.setBounds(textFieldLenDiv100000.getX(),
        textFieldLenDiv100000.getY()+35, FIELD_WIDTH, FIELD_HEIGHT);
        sliderLenDiv100000.setMaximum(MIN_DIAPASON);
        sliderLenDiv100000.setMaximum(MAX_DIAPASON/1000);

        sliderLenDiv100000.setValue(sliderLenDiv100000.getMinimum());
        sliderLenDiv100000.setMajorTickSpacing(sliderLenDiv100000.getMaximum() / 10);

        sliderLenDiv100000.setPaintTicks(true);
        sliderLenDiv100000.setSnapToTicks(true);

        JLabel labelResult = new JLabel("Total");
        labelResult.setBounds(textFieldLength.getX() + textFieldLength.getWidth()+GAP, labelLength.getY(),
                FIELD_WIDTH, FIELD_HEIGHT);
        textFieldResult = new JTextField("0");

        textFieldResult.setBounds(textFieldLength.getX() + textFieldLength.getWidth() + 10,
                STEP_DOWN, FIELD_WIDTH, FIELD_HEIGHT);
        sliderLength.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                textFieldLength.setText(Integer.toString(sliderLength.getValue()));
                textFieldResult.setText(Integer.toString
                        (
                                sliderLength.getValue()+
                                        sliderLenDiv100.getValue()+
                                        sliderLenDiv100000.getValue()
                        )
                );
            }
        });

        sliderLenDiv100.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                textFieldLenDiv100.setText(Integer.toString(sliderLenDiv100.getValue()));
                textFieldResult.setText(Integer.toString
                        (
                                sliderLength.getValue()+
                                        sliderLenDiv100.getValue()+
                                        sliderLenDiv100000.getValue()
                        )
                );
            }
        });

        sliderLenDiv100000.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                textFieldLenDiv100000.setText(Integer.toString(sliderLenDiv100000.getValue()));
                textFieldResult.setText(Integer.toString
                        (
                                sliderLength.getValue()+
                                        sliderLenDiv100.getValue()+
                                        sliderLenDiv100000.getValue()
                        )
                );
            }
        });

        add(labelLength);

        add(labelX1000000);
        add(textFieldLength);
        add(sliderLength);

        add(labelX10000);
        add(textFieldLenDiv100);
        add(sliderLenDiv100);

        add(labelX1000);
        add(textFieldLenDiv100000);
        add(sliderLenDiv100000);

        add(labelResult);
        add(textFieldResult);
    }
    private void initComboBox(){
        String[] valueTypes = {"Integer", "Float", "String"};
        structs = new HashMap<Integer, Object>();
        structs.put(0, new Integer(0));
        structs.put(1, new float[0]);
        structs.put(2, new String());

        comboBoxType = new JComboBox(valueTypes);
        comboBoxType.setBounds(textFieldResult.getX(),
                textFieldResult.getY() + textFieldResult.getHeight()+GAP, FIELD_WIDTH, FIELD_HEIGHT);

        String[] valueItems = {"Unsorted","Sorted"};
        comboBoxState = new JComboBox(valueItems);
        comboBoxState.setBounds(comboBoxType.getX(), comboBoxType.getY()+comboBoxType.getHeight()+10,
                comboBoxType.getWidth(), comboBoxType.getHeight());

        add(comboBoxType);
        add(comboBoxState);
    }
    private void initSpinner(){
        final int MIN_VALUE = 1;
        final int MAX_VALUE = 10;
        final int DEF_VALUE = 5;
        SpinnerNumberModel snm = new SpinnerNumberModel();
        snm.setMinimum(MIN_VALUE);
        snm.setMaximum(MAX_VALUE);
        snm.setValue(DEF_VALUE);

        JLabel labelNumberElemInKit = new JLabel("Elements in kit");//TODO You need to rename this label;
        labelNumberElemInKit.setSize(FIELD_WIDTH*2/3,FIELD_HEIGHT);
        labelNumberElemInKit.setLocation(comboBoxState.getX(), comboBoxState.getY()+comboBoxState.getHeight()+GAP*3);

        numberKitElem = new JSpinner(snm);
        numberKitElem.setSize(FIELD_WIDTH/3, FIELD_HEIGHT);
        numberKitElem.setLocation(comboBoxState.getX()+labelNumberElemInKit.getWidth(),
                comboBoxState.getY()+comboBoxState.getHeight()+GAP*3);



        add(numberKitElem);
        add(labelNumberElemInKit);

    }
    private void initPopupMenu(){
        popupMenu = new JPopupMenu();
        ImageIcon itemDeleteIcon = new ImageIcon(Main.ICO_PATH + "table_row_delete.png");
        JMenuItem itemDelete = new JMenuItem("  Delete    ", itemDeleteIcon);
        itemDelete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {

                if (dataList.deleteData(selectedRow) != null){
                    tableModelArrays.removeRow(selectedRow);
                }
                if(tableModelArrays.getRowCount()!=0){
                    for(int i=0; i<tableModelArrays.getRowCount(); i++){
                        tableModelArrays.setValueAt(i+1,i,0);
                    }
                }
            }
        });
        popupMenu.add(itemDelete);
    }


    private  boolean checkData(){
        int valueLength = Integer.parseInt(textFieldResult.getText());
        if (valueLength == 0){
            showMessage("error","The length of array is null!","Data Error!");
            return false;
        }
        else {
            return true;
        }
    }
    /*private Object[] loadRow(DataStructure obj){
        return new Object[]
                {
                    Integer.toString(tableModelArrays.getRowCount()+1),
                        obj.getTypeData(),
                        Integer.toString(obj.getLength()),
                        obj.getState(),
                        obj.getKitLength()
                };
    }*/
    private Object[] loadRow(DataStructures obj){
        return new Object[]
                {
                        Integer.toString(tableModelArrays.getRowCount()+1),
                        obj.getType(),
                        Integer.toString(obj.getLength(0)),
                        obj.getState(),
                        obj.kitSize()
                };
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
