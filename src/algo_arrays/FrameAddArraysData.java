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
    private final Dimension WINDOW_SIZE = new Dimension(730,400);
    private final int STEP_LEFT = 10;
    private final int STEP_DOWN = 40;
    private final int FIELD_WIDTH = 200;
    private final int FIELD_HEIGHT = 25;
    private final int TABLE_WIDTH = 255;
    private final int GAP = 10; // This constant sets space between components

    private JTextField textFieldResult;
    private JComboBox comboBoxType;
    private JComboBox comboBoxState;
    private JComboBox wordLength;
    private DefaultTableModel tableModelArrays;
    private JPopupMenu popupMenu;
    private ArraysDataBase dataList;
    private JSpinner numberKitElem;

    /**
     * Variable sets number of selected row in the table there contain notices about structures
     * which have been generated.
     */
    private int selectedRow = -1;

    /**
     * Map contains template-objects for creation structure given type.
     */
    private Map<Integer,Object> structs;

    /**
     * Sets max number of characters in the elements into data structure.
     * Default value x10.0
     */
    private int numberOfLetters = 1;
    private Map valueWords;

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

        dataList = Main.getStructureBase();

        controlsStructLength();
        controlsDataParam();
        initTable();
        initButtons();
        initPopupMenu();
        controlsKitParam();
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
        buttonOk.setBounds(getWidth()-260, getHeight()-65,
                Main.buttonSize.width,Main.buttonSize.height);
        buttonOk.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
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
                generateData();
            }
        });

        add(buttonCancel);
        add(buttonOk);
        add(buttonGenerate);

    }
    private void initTable(){

        /*
         * This gives opportunity to set sells in table as manually no editable
         */
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

        /*
         * Add information about have generated structures to the table model
         */
        if(dataList != null){
            for(int i = 0; i<dataList.getLength(); i++){
                tableModelArrays.addRow(new String[]{
                        Integer.toString(i + 1),
                        dataList.getData(i).getType(),
                        Integer.toString(dataList.getData(i).getLength(0)),
                        dataList.getData(i).getState()
                });
            }
        }

        /*
         * Create table from table model
         */
        final JTable tableArrays = new JTable(tableModelArrays);

        /*
         * Runs popup menu for edition notices which contain in table.
         */
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

        /*
         * Changes width all the columns in the table
         */
        tableArrays.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableArrays.getColumnModel().getColumn(0).setPreferredWidth(23);
        tableArrays.getColumnModel().getColumn(1).setPreferredWidth(67);
        tableArrays.getColumnModel().getColumn(2).setPreferredWidth(70);
        tableArrays.getColumnModel().getColumn(3).setPreferredWidth(65);
        tableArrays.getColumnModel().getColumn(4).setPreferredWidth(27);

        /*
         * Each notice sets as manually no editable
         */
        for (int i = 0; i<tableArrays.getColumnCount(); i++ ){
            tableArrays.getColumnModel().getColumn(i).setCellEditor(nonSelEditor);
        }

        /*
         * Add scroll controls to table
         */
        JScrollPane scrollTable = new JScrollPane(tableArrays);
        scrollTable.setSize(TABLE_WIDTH,getHeight()-FIELD_HEIGHT-150);
        scrollTable.setLocation(getWidth()-scrollTable.getWidth()-15, FIELD_HEIGHT+60);

        /*
         * Add table to frame
         */
        add(scrollTable);

    }

    private void controlsStructLength(){

        final int MIN_DIAPASON = 0;                 /*Minimum value of slider position */
        final int MAX_DIAPASON = 10000000;          /*Maximum value of slider position */
        final int KIT_OF_ELEMENTS_HEIGHT = 300;     /*Height of joined graphical components*/

        /*
         * Join sliders for set array length together
         */
        JPanel groupStructureLength = new JPanel();
        LayoutManager layoutSliders = getContentPane().getLayout();
        groupStructureLength.setLayout(layoutSliders);
        groupStructureLength.setBorder(BorderFactory.createTitledBorder("Structure length"));
        groupStructureLength.setBounds(STEP_LEFT,STEP_DOWN-20,FIELD_WIDTH+20,KIT_OF_ELEMENTS_HEIGHT);


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

        /*
         * Join components which show total length
         */
        JPanel groupTotal = new JPanel();
        LayoutManager layoutGroupTotal = getContentPane().getLayout();
        groupTotal.setLayout(layoutGroupTotal);
        groupTotal.setBorder(BorderFactory.createTitledBorder("Total length"));

        groupTotal.setSize(TABLE_WIDTH, FIELD_HEIGHT+30);

        groupTotal.setLocation(
                getWidth()- groupTotal.getWidth()-15,
                groupStructureLength.getY()
        );

        textFieldResult = new JTextField("0");

        textFieldResult.setBounds(10, 20, groupTotal.getWidth()-20, FIELD_HEIGHT);


        /*
         * Describe slider Change listeners for setting total value of length
         */
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

        /*
         * Add sliders to group "Structure length"
         */
        groupStructureLength.add(labelX1000000);
        groupStructureLength.add(textFieldLength);
        groupStructureLength.add(sliderLength);

        groupStructureLength.add(labelX10000);
        groupStructureLength.add(textFieldLenDiv100);
        groupStructureLength.add(sliderLenDiv100);

        groupStructureLength.add(labelX1000);
        groupStructureLength.add(textFieldLenDiv100000);
        groupStructureLength.add(sliderLenDiv100000);

        /*
         * Add text field of total length to "Total length"
         */
        groupTotal.add(textFieldResult);

        /*
         *Add group "Structure length" and "Total length" to frame
         */
        add(groupStructureLength);
        add(groupTotal);
    }

    private void controlsDataParam(){
        final int X_SIDE = 235;
        final int Y_SIDE = STEP_DOWN-20;
        final int KIT_PARAM_HEIGHT = 150;

        /*
         * Create panel for group of "Data parameters"
         */
        JPanel groupParam = new JPanel();
        LayoutManager layoutGroupParam = getContentPane().getLayout();
        groupParam.setLayout(layoutGroupParam);
        groupParam.setBorder(BorderFactory.createTitledBorder("Parameters data"));

        groupParam.setSize(FIELD_WIDTH+20, KIT_PARAM_HEIGHT);

        groupParam.setLocation( X_SIDE, Y_SIDE);

        /*
         * Create control which let choice type of data.
         * Data of this type will keep into structure.
         */
        String[] valueTypes = {"Integer", "Float", "String"};
        structs = new HashMap<Integer, Object>();
        structs.put(0, new Integer(0));
        structs.put(1, new float[0]);
        structs.put(2, new String());

        comboBoxType = new JComboBox(valueTypes);
        comboBoxType.setBounds(10, 20, FIELD_WIDTH, FIELD_HEIGHT);

        /*
         *  Create control which let choice future of state structure.
         *  If future sets as "Sorted" then data in structure must be sorted.
         */
        String[] valueItems = {"Unsorted","Sorted"};
        comboBoxState = new JComboBox(valueItems);
        comboBoxState.setBounds(comboBoxType.getX(), comboBoxType.getY()+comboBoxType.getHeight()+10,
                comboBoxType.getWidth(), comboBoxType.getHeight());

        /*
         * Sets value of word length in data structure.
         * It is important for some algorythms when necessary manage a number of duplicate values
         * (for example, quick sort median-of-three works faster if a lot of duplicate values in the array)
         */
        JLabel numbOfLetters = new JLabel("Characters: ");
        numbOfLetters.setBounds(comboBoxState.getX(), comboBoxState.getY()+comboBoxState.getHeight()+20,
                130, comboBoxState.getHeight());
        valueWords = new HashMap<String, Integer>();
        valueWords.put("1", 10);
        valueWords.put("2", 100);
        valueWords.put("3", 1000);
        valueWords.put("4", 10000);
        valueWords.put("5", 100000);
        valueWords.put("6", 1000000);

        Object[] ar =  valueWords.keySet().toArray();
        Arrays.sort(ar);
        wordLength = new JComboBox(ar);
        wordLength.setBounds(comboBoxState.getX()+numbOfLetters.getWidth(), comboBoxState.getY()+comboBoxState.getHeight()+20,
                comboBoxState.getWidth() - numbOfLetters.getWidth(), comboBoxState.getHeight());

        /* Sets the default value as "x100" */
        numberOfLetters = 1;
        wordLength.setSelectedIndex(numberOfLetters);

        groupParam.add(comboBoxType);
        groupParam.add(comboBoxState);
        groupParam.add(numbOfLetters);
        groupParam.add(wordLength);

        add(groupParam);
    }
    private void controlsKitParam(){

        final int X_SIDE = 235;
        final int Y_SIDE = 180;
        final int HEIGHT_KIT_PARAM_GROUP = 100;
        final int MIN_VALUE = 1;
        final int MAX_VALUE = 10;
        final int DEF_VALUE = 5;

        JPanel groupKitParam = new JPanel();
        LayoutManager layoutGroupKitParam = getContentPane().getLayout();
        groupKitParam.setLayout(layoutGroupKitParam);
        groupKitParam.setBorder(BorderFactory.createTitledBorder("Parameters of kit"));

        groupKitParam.setSize(FIELD_WIDTH+20, HEIGHT_KIT_PARAM_GROUP);

        groupKitParam.setLocation( X_SIDE, Y_SIDE);

        SpinnerNumberModel snm = new SpinnerNumberModel();
        snm.setMinimum(MIN_VALUE);
        snm.setMaximum(MAX_VALUE);
        snm.setValue(DEF_VALUE);

        JLabel labelNumberElemInKit = new JLabel("Elements in kit");
        labelNumberElemInKit.setSize(FIELD_WIDTH*2/3,FIELD_HEIGHT);
        labelNumberElemInKit.setLocation(10, 20);

        numberKitElem = new JSpinner(snm);
        numberKitElem.setSize(FIELD_WIDTH/3, FIELD_HEIGHT);
        numberKitElem.setLocation(groupKitParam.getWidth()-80, 20);



        groupKitParam.add(numberKitElem);
        groupKitParam.add(labelNumberElemInKit);
        add(groupKitParam);
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

    private void generateData(){
        numberOfLetters = wordLength.getItemCount();
        int cap = (Integer)valueWords.get(Integer.toString(numberOfLetters-1));
        if (checkData()){
            try{
                GenerateDataKit dataKit = new GenerateDataKit(Integer.parseInt(textFieldResult.getText()),
                        structs.get(comboBoxType.getSelectedIndex()),
                        comboBoxState.getSelectedItem().toString(),
                        Integer.parseInt(numberKitElem.getValue().toString()), cap);

                Thread threadDataKit = new Thread(dataKit);
                threadDataKit.start();
                while(threadDataKit.isAlive()){
                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                }
                setCursor(Cursor.getDefaultCursor());
                DataStructures data = Main.getStructureBase().getData(
                        Main.getStructureBase().getLength()-1
                );
                tableModelArrays.addRow(loadRow(data));
            }catch (UnsupportedOperationException ex){
                showMessage("error", "Error# 1001 "+ex.getMessage(),"Add data error!");
                ex.printStackTrace();
            }

        }
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
