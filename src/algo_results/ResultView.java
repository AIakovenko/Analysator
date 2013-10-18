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
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class ResultView extends JFrame {

    private Dimension dimention;
    private JPanel memory;
    private JPanel time;

    private DefaultTableModel modelMainTable;
    private JTable mainTable;
    private DefaultTableModel modelAuxiliaryTable;
    private JTable auxiliaryTable;
    private ListSelectionModel tableSelection;

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
        initButtons();


    }
    private void initViewPort(){

        memory = new PaneGraph("Memory, kB", memoryGrafPoints()).getPaneGraf();
        memory.setBounds(getWidth()-320,10,300,300);
        memory.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        memory.setBackground(Color.white);
//        memory.setPoints(memoryGrafPoints(), setColorsGraph());// getMaxMemory()); //TODO
//        memory.setScale(100);

        time = new PaneGraph("Time, c.", timeGrafPoints()).getPaneGraf();
        time.setBounds(getWidth() - memory.getWidth() - 330, 10, 300, 300);
        time.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        time.setBackground(Color.white);
//        time.setPoints(timeGrafPoints(), setColorsGraph());
//        time.setScale(100);

        add(memory);
        add(time);
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
        buttonClose.setBounds(getWidth()-135, getHeight()-70, 117,25);
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


}
class  Coordinates{
    public double x;
    public  double y;

    public Coordinates(){
        this.x = 0;
        this.y = 0;
    }
}

class PaneGraph extends JPanel{
    private JPanel p;
    private String titleGraph;
    private ArrayList<Coordinates[]> listCoordinates;
    public PaneGraph(String titleGraph, List<Coordinates[]> listCoordinates){
        this.titleGraph = titleGraph;
        this.listCoordinates =(ArrayList)listCoordinates;
    }
    public JPanel getPaneGraf(){
        p = new JPanel();
        p.add(getChartPanel());

        return p;
    }

    public ChartPanel getChartPanel(){
        //створюємо панель для графіка
        final ChartPanel chartPanel = new ChartPanel(getChart());
           //встановлюємо розмір діаграми (можна також скористатись методами JFreeChart цього)
        chartPanel.setPreferredSize(new java.awt.Dimension(280, 280));
        //додаємо панель на створений нами фрейм
        chartPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(p, "text", "title",JOptionPane.ERROR_MESSAGE);
            }
        });
//    frame.setContentPane(chartPanel);
//    //підганяємо розміри фрейму
//    frame.pack();
//    //робимо усе видимим
        return chartPanel;
    }
    private JFreeChart getChart(){
        /*Create data for building all graphs */
        XYSeriesCollection data = new XYSeriesCollection();

        /*Mark line*/
        int index = 0;

        for(Coordinates[] kit: listCoordinates ){

            /*Add data to kit of one line*/
            XYSeries series = new XYSeries("Line #"+ ++index);
            for(int i = 0; i<kit.length; i++){
                series.add(kit[i].x, kit[i].y);
            }
            data.addSeries(series);
        }


       /* //створюємо 1 ряд даних

        XYSeries series = new XYSeries("a");
        //додаємо точки на графіку
        series.add(1, 11);
        series.add(2, 12);
        series.add(3, 13);
        series.add(4, 14);
        series.add(5, 15);
        series.add(6, 16);
        series.add(7, 17);
        series.add(8, 14);
        series.add(9, 13.5);
        series.add(10, 11);

        XYSeries series2 = new XYSeries("b");
        //додаємо точки на графіку
        series2.add(0, 21);
        series2.add(4, 22);
        series2.add(5, 23);
        series2.add(6, 24);
        series2.add(7, 25);
        series2.add(8, 26);
        series2.add(9, 27);
        series2.add(10, 24);
        series2.add(11, 23.5);
        series2.add(12, 21);


        // зразу ж додаємо ряд в набір даних

        data.addSeries(series);
        data.addSeries(series2);
       */
        //створюємо діаграму
        final JFreeChart chart = ChartFactory.createXYLineChart(
                titleGraph, //Заголовок діаграми
                "X",  //назва осі X
                "Y",  //назва осі Y
                data, //дані
                PlotOrientation.VERTICAL, //орієнтація
                true, // включити легенду
                true, //підказки
                false // urls
        );
        return chart;
    }



}
