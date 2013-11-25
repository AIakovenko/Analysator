package algo_results;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 01.06.13
 * Time: 16:05
 * To change this template use File | Settings | File Templates.
 */
public class FrameProcessCalculate extends JFrame {
    private final Dimension WINDOW_SIZE = new Dimension(400,200);
    private JLabel nameAlgorythm;
    private JLabel currentPoint;
    private JLabel totalPoints;
    private JLabel arrayLength;
    private JProgressBar progressBar;
    final int INDENT_LEFT = 10;
    final int INDENT_UP = 10;
    public FrameProcessCalculate(){

        initialize();
    }
    private void initialize(){
        new JFrame("Calculate Processing");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        Dimension dimWindow = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(WINDOW_SIZE);
        setLocation(dimWindow.width / 2 - getWidth() / 2, dimWindow.height / 2 - getHeight() / 2);
        setResizable(false);
        this.getContentPane().setLayout(null);
        initTextFild();
        initProgressBar();

    }
    private void initTextFild(){


        nameAlgorythm = new JLabel("test");
        nameAlgorythm.setFont(new Font(nameAlgorythm.getFont().getName(),Font.BOLD,20));
        nameAlgorythm.setBounds(INDENT_LEFT,INDENT_UP,getWidth()-INDENT_LEFT*2,nameAlgorythm.getFont().getSize()+5);

        JLabel labelTotalPoints = new JLabel("Total points: ");
        labelTotalPoints.setBounds(INDENT_LEFT, nameAlgorythm.getY() + nameAlgorythm.getFont().getSize() + 5,
                110, labelTotalPoints.getFont().getSize()+5);

        totalPoints = new JLabel("100");
        totalPoints.setBounds(labelTotalPoints.getX()+labelTotalPoints.getWidth(),labelTotalPoints.getY(),
                40,totalPoints.getFont().getSize()+5);

        JLabel labelCurrentPoint = new JLabel("Current point: ");
        labelCurrentPoint.setBounds(totalPoints.getX()+totalPoints.getWidth()+10,
                nameAlgorythm.getY() + nameAlgorythm.getFont().getSize() + 5,
                110, labelCurrentPoint.getFont().getSize()+5);

        currentPoint = new JLabel("100");
        currentPoint.setBounds(labelCurrentPoint.getX()+labelCurrentPoint.getWidth(),
                labelCurrentPoint.getY(),40,currentPoint.getFont().getSize()+5);

        JLabel labelArrayLength = new JLabel("Array length: ");
        labelArrayLength.setBounds(INDENT_LEFT,
                currentPoint.getY() + currentPoint.getFont().getSize() + 5,
                110, labelArrayLength.getFont().getSize()+5);

        arrayLength = new JLabel("1000000");
        arrayLength.setBounds(labelArrayLength.getX()+labelArrayLength.getWidth(),
                labelArrayLength.getY(),200,arrayLength.getFont().getSize()+5);

        add(nameAlgorythm);
        add(labelTotalPoints);
        add(totalPoints);
        add(labelCurrentPoint);
        add(currentPoint);
        add(labelArrayLength);
        add(arrayLength);

    }
    private void initProgressBar(){
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setBounds(INDENT_LEFT, getHeight()-50, getWidth()-INDENT_LEFT*2,20);
        progressBar.setVisible(true);
        add(progressBar);

    }
    public void setNameAlgorythm(String name){
        nameAlgorythm.setText(name);
    }
    public  void setTotalPoints(int total){
        totalPoints.setText(Integer.toString(total));
    }
    public void setCurrentPoint(int current){
        currentPoint.setText(Integer.toString(current));
    }

}
