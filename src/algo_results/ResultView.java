package algo_results;



/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 01.06.13
 * Time: 20:40
 * To change this template use File | Settings | File Templates.
 */


import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ResultView extends JFrame {




    private Dimension dimention;
    private GraphPanel memory;
    private GraphPanel time;

    private DefaultTableModel modelMainTable;
    private JTable mainTable;
    private DefaultTableModel modelAuxiliaryTable;
    private JTable auxiliaryTable;
    private ListSelectionModel tableSelection;
    private JComboBox comboTime;
    private JComboBox comboMemory;

    private Object scaleTime = 1;
    private int scaleMemory = 1;
    private int selectedRow = -1;
    private ListDataOut listDataOut;
    private int[] selResultRows;

    public ResultView(String title){
        super(title);
        initialize();

    }


    public void setData(ListDataOut listDataOut, int[] selResultRows){
        this.listDataOut = listDataOut;
        this.selResultRows = selResultRows;
        addRowMainTable();
        initViewPort();
        initComboBox();
//        time.setPoints(timeGrafPoints(50));
//        memory.setPoints(memoryGrafPoints(20));
    }
    private void initialize(){
        new JFrame();
        dimention = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(dimention.width-200, dimention.height-350);
        setLocation(100,100);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        getContentPane().setLayout(null);
        initTable();
//        initViewPort();
        initButtons();


    }
    private void initViewPort(){
        memory = new GraphPanel("Memory, kB");
        memory.setBounds(getWidth()-320,10,300,300);
        memory.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        memory.setBackground(Color.white);
        memory.setPoints(memoryGrafPoints(), setColorsGraph());// getMaxMemory()); //TODO
        memory.setScale(100);

        time = new GraphPanel("Time, c.");
        time.setBounds(getWidth() - memory.getWidth() - 330, 10, 300, 300);
        time.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        time.setBackground(Color.white);
        time.setPoints(timeGrafPoints(), setColorsGraph());
        time.setScale(100);

        add(memory);
        add(time);
    }
    private void initComboBox(){

        Object[] itemScale = {0.25,0.5,1,10,100,500,1000,10000,100000};
        //================================
        comboTime = new JComboBox();

        comboTime.setBounds(time.getX(),time.getY()+time.getHeight()+10, time.getWidth(), 20);

        for(Object i : itemScale)
            comboTime.addItem(i);
        comboTime.setSelectedIndex(4);
        comboTime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object scale = comboTime.getSelectedItem();
                repaintGraphTime(scale);
            }
        });


        //=================================
        comboMemory = new JComboBox();


        comboMemory.setBounds(memory.getX(),memory.getY()+memory.getHeight()+10, memory.getWidth(), 20);

        for(Object i : itemScale)
            comboMemory.addItem(i);
        comboMemory.setSelectedIndex(4);
        comboMemory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object scale = comboMemory.getSelectedItem();
                repaintGraphMemory(scale);
            }
        });

        add(comboTime);
        add(comboMemory);
    }
    private void initTable(){
        TableCellEditor nonSelEditor = new TableCellEditor() {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public Object getCellEditorValue() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean isCellEditable(EventObject anEvent) {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean shouldSelectCell(EventObject anEvent) {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean stopCellEditing() {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void cancelCellEditing() {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void addCellEditorListener(CellEditorListener l) {

                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void removeCellEditorListener(CellEditorListener l) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        };
        String[] mainTableTitle = {"","Name","Time","Memory","Max.value","Data type"};
        modelMainTable = new DefaultTableModel();
        for(String s: mainTableTitle)
            modelMainTable.addColumn(s);


        mainTable = new JTable(modelMainTable);
        mainTable.setRowHeight(20);
        mainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        mainTable.getColumnModel().getColumn(0).setPreferredWidth(22);
        mainTable.getColumnModel().getColumn(1).setPreferredWidth(175);
        mainTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        mainTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        mainTable.getColumnModel().getColumn(4).setPreferredWidth(80);
        mainTable.getColumnModel().getColumn(5).setPreferredWidth(80);
        for (int i = 0; i<mainTable.getColumnCount(); i++){
            mainTable.getColumnModel().getColumn(i).setCellEditor(nonSelEditor);
        }

        tableSelection = mainTable.getSelectionModel();
        tableSelection.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedRow = mainTable.getSelectedRow();
                if (selectedRow >= 0){
                    addRowAuxiliaryTable(selectedRow);
                }
            }
        });

        JScrollPane paneMainTable = new JScrollPane(mainTable);
        paneMainTable.setBounds(10,10, 520,150);


        String[] auxiliaryTableTitle = {"Point","Time","Memory"};
        modelAuxiliaryTable = new DefaultTableModel();
        for(String s: auxiliaryTableTitle)
            modelAuxiliaryTable.addColumn(s);

        auxiliaryTable = new JTable(modelAuxiliaryTable);
        auxiliaryTable.setRowHeight(20);
        auxiliaryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        for (int i = 0; i<auxiliaryTable.getColumnCount(); i++){
            auxiliaryTable.getColumnModel().getColumn(i).setCellEditor(nonSelEditor);
        }

        JScrollPane paneAuxiliaryTable = new JScrollPane(auxiliaryTable);
        paneAuxiliaryTable.setBounds(paneMainTable.getX(),paneMainTable.getY()+paneMainTable.getHeight()+10,
                paneMainTable.getWidth(),140);

        add(paneMainTable);
        add(paneAuxiliaryTable);
    }
    private void initButtons(){
        JButton buttonClose = new JButton("Close");
        buttonClose.setBounds(getWidth()-135, getHeight()-50, 117,25);
        buttonClose.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });
        add(buttonClose);

    }
    private void addRowMainTable(){
           for (int i = 0; i<selResultRows.length; i++){
               modelMainTable.addRow(new Object[]{"",listDataOut.getData(selResultRows[i]).getAlgoName(),
             listDataOut.getData(selResultRows[i]).getMaxTime(), listDataOut.getData(selResultRows[i]).getMaxMemory(),
             listDataOut.getData(selResultRows[i]).getMaxValueLength(),
               listDataOut.getData(selResultRows[i]).getType()});
           }
//         for(int i = 0; i<listDataOut.getLength(); i++){
//             modelMainTable.addRow(new Object[]{"",listDataOut.getData(i).getAlgoName(),
//             listDataOut.getData(i).getMaxTime(), listDataOut.getData(i).getMaxMemory(),
//             listDataOut.getData(i).getMaxValueLength()});
//         }
//        Color colorGraph = new Color(255, 0, 0);
////        DefaultTableModel s = new DefaultC
//        TableColumn ddd = mainTable.getColumnModel().getColumn(0);
//        ddd.setCellEditor(new DefaultCellEditor());

    }

    private void addRowAuxiliaryTable(int index){
        Object[] auxiliaryTableTitle = {"Point","Time","Memory"};
        DefaultTableModel model = new DefaultTableModel(auxiliaryTableTitle,0);
        auxiliaryTable.setModel(model);

            for (int i = 0; i<listDataOut.getData(index).getPoints().length; i++){
                model.addRow(new Object[]{listDataOut.getData(index).getPoints()[i],
                listDataOut.getData(index).getTime()[i],
                        listDataOut.getData(index).getMemory()[i]});
            }


    }
    private ArrayList<Coordinates[]> memoryGrafPoints(){

        ArrayList<Coordinates[]> allPoints = new ArrayList<Coordinates[]>();
        Coordinates[] oncePoint;
        if(listDataOut != null){
            for (int i = 0; i<selResultRows.length; i++){
                oncePoint = new Coordinates[listDataOut.getData(selResultRows[i]).getMemory().length];
                int interval = 240/(oncePoint.length-1);
                int x = 0;
                for (int j = 0; j<listDataOut.getData(selResultRows[i]).getMemory().length; j++){
                    oncePoint[j] = new Coordinates();
                    oncePoint[j].y = listDataOut.getData(selResultRows[i]).getMemory()[j];
                    oncePoint[j].x = x;
                    x = x + interval;
                }
                allPoints.add(oncePoint);
            }
        }
        return allPoints;
    }



    private ArrayList<Coordinates[]> timeGrafPoints(){
        ArrayList<Coordinates[]> allPoints = new ArrayList<Coordinates[]>();
        Coordinates[] oncePoint;
        if(listDataOut != null){
            for (int i = 0; i<selResultRows.length; i++){
                oncePoint = new Coordinates[listDataOut.getData(selResultRows[i]).getTime().length];
                int interval = 240/(oncePoint.length-1);
                int x = 0;
                for (int j = 0; j<listDataOut.getData(selResultRows[i]).getTime().length; j++){
                    oncePoint[j] = new Coordinates();
                    oncePoint[j].y = listDataOut.getData(selResultRows[i]).getTime()[j];
                    oncePoint[j].x = x;
                    x = x + interval;
                }
                allPoints.add(oncePoint);
            }
        }
        return allPoints;
    }
    private void repaintGraphMemory(Object scale){
        Float scl = new Float(scale.toString());
        float f = scl.floatValue();
        memory.setPoints(memoryGrafPoints(), setColorsGraph());
        memory.setScale(f);
        memory.repaint();

    }

    private void repaintGraphTime(Object scale){
        Float scl = new Float(scale.toString());
        float f = scl.floatValue();
        time.setPoints(timeGrafPoints(), setColorsGraph());
        time.setScale(f);
        time.repaint();

    }
    private Color[] setColorsGraph(){
        Color[] colorGraph = new Color[10];
        colorGraph[0] = new Color(255, 0, 0);
        colorGraph[1] = new Color(255, 0, 255);
        colorGraph[2] = new Color(0, 0, 255);
        colorGraph[3] = new Color(0, 150, 200);
        colorGraph[4] = new Color(0, 255, 0);
        colorGraph[5] = new Color(255, 255, 0);
        colorGraph[6] = new Color(21, 100, 25);
        colorGraph[7] = new Color(0, 0, 100);
        colorGraph[8] = new Color(0, 110, 100);
        colorGraph[9] = new Color(100, 100, 0);
        return colorGraph;
    }

}

class  Coordinates{
    public double x;
    public  double y;

    public Coordinates(){
        this.x = 0;
        this.y = 0;
    }
}

class GraphPanel extends JPanel {
    private ArrayList<Coordinates[]> allPoints;
    private Color[] colorGraph;
    private float scale;
    private String name;
    private int coordX0;
    private int coordXX;
    private int coordY0;
    private int coordYY;

    public GraphPanel(String name) {
        this.allPoints = null;
        this.name = name;

    }
    public void setPoints(ArrayList<Coordinates[]> points, Color[] colorGraph){
        this.allPoints = points;
        this.colorGraph = colorGraph;

    }
    public  void setScale(float scale){
        this.scale = scale;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        coordX0 = 30;
        coordXX = this.getWidth() - 30;
        coordY0 = this.getHeight() - 30;
        coordYY = 30;
        drawPlace(g);
        drawGrafMem(g);
    }
    private void drawPlace(Graphics g){
        //draw coordinates axises
        g.drawLine(coordX0, coordY0, coordXX, coordY0);
        g.drawLine(coordX0, coordY0, coordX0, coordYY);

        //draw grid
        g.setColor(Color.LIGHT_GRAY);
        int stepX = 20;
        int stepGridX =(coordXX-coordX0)/stepX;
        for (int x = coordX0+stepGridX; x<=coordXX; x=x+stepGridX){
            g.drawLine(x, coordYY+1, x, coordY0-1);
        }
        int stepY = 20;
        int stepGridY =(coordY0-coordYY)/stepY;
        for (int y = coordY0-stepGridY; y>=coordYY; y=y-stepGridY){
            g.drawLine(coordX0+1, y, coordXX-1, y);
        }
        g.setColor(Color.black);
        g.drawString("N",coordXX-5,coordY0+15);
        g.drawString(name, coordX0, coordYY-5);

    }
    private void drawGrafMem(Graphics g){
        g.setColor(Color.BLUE);

        if(allPoints != null){
            for(int i = 0; i<allPoints.size(); i++){
                for (int j = 1; j<allPoints.get(i).length; j++){
                    g.setColor(colorGraph[i]);
                    if((coordX0+allPoints.get(i)[j].x>coordXX) ||(coordY0-allPoints.get(i)[j].y * scale <coordYY))
                        break;
                    else{
                        g.drawLine(coordX0 + (int)allPoints.get(i)[j-1].x, (coordY0 -(int)(allPoints.get(i)[j-1].y * scale)),
                           coordX0 + (int)allPoints.get(i)[j].x, coordY0 - (int)(allPoints.get(i)[j].y * scale));

                        Color gTemp = g.getColor();
                        g.setColor(Color.black);
                        g.drawOval(coordX0 + (int)(allPoints.get(i)[j].x) - 1,
                                coordY0 - (int)(allPoints.get(i)[j].y * scale) - 1,2,2);
                        g.setColor(gTemp);
                    }
                }
            }
        }
    }

}
