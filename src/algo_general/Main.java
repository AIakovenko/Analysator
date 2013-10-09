package algo_general;

import algo_arrays.ArraysData;
import algo_arrays.ArraysDataBase;
import algo_arrays.DataStructure;
import algo_files.*;
import algo_results.DataOut;
import algo_results.ListDataOut;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 28.04.13
 * Time: 17:20
 * To change this template use File | Settings | File Templates.
 */
public class Main {

    public static final String ICO_PATH = "ico/";
    public static final String CLASS_PATH = "Classes/";

    public static final Dimension buttonSize = new Dimension(120,25);
    public static double totalTime = 0;
    public static double totalMemory = 0;

    private static Thread threadExamineAlgorythm;
    private static AnalysatorFrames frames;

    public static void transferDataArrayToMainWindow(ArraysDataBase array){
        frames.getMainWindow().setDataArrays(array);
    }

    public static void runDataWindow(){
        frames.runDataWindow();
        frames.getDataWindow().setVisible(true);
    }
    public static ArraysDataBase transferArraysDataFromFrameGeneralWindow(){
        return frames.getMainWindow().getDataArrays();
    }
    public static FileDataBase transferFileDataFromFrameGeneralWindow(){
        return frames.getMainWindow().getFileDataBase();
    }

    public static void runFileDataBaseEditor(){
        frames.runFileDataBaseEditor();
        frames.getFileDataBaseEditor().setVisible(true);
    }
    public static void runFrameCreateAlgo(){
        frames.runFrameCreateAlgo();
        frames.getFrameCreateAlgo().setVisible(true);
    }
    public static void runResultView(){
        frames.runResultView();
        frames.getResultView().setVisible(true);
    }
    public static void transferDataToResultView(ListDataOut obj, int[] selResultRows){
        frames.getResultView().setData(obj, selResultRows);
    }

    public static void sendDataBaseToMainWindow(FileDataBase data){
        frames.getMainWindow().setDataBase(data);
    }
    public static void stopTest(){
        try{
            if (threadExamineAlgorythm.isAlive())
                threadExamineAlgorythm.interrupt();
        }catch(NullPointerException ex){

        }
    }
    public static DataOut runTest(AlgorythmFile algorythmFile, DataStructure arraysData, int numberOfPoints){
        DataIn dataIn = new DataIn(algorythmFile, arraysData, numberOfPoints);
        DataCalc calc = new DataCalc(dataIn);
//        calc.run();

        Thread calculation = new Thread(calc);
        try {
            calculation.start();
            calculation.join();
        }catch (InterruptedException ex){

        }

        DataOut dataOut = null;
        double [][] timeEachKitElem = new double[arraysData.getKitLength()][];
        double [][] memoryEachKitElem = new double[arraysData.getKitLength()][];
        for(int step = 0; step<arraysData.getKitLength(); step++ ){

            Object[][] obj = calc.getArraysIn(step);
//            Object[][] obj = calc.getArraysIn();
            String type = arraysData.getTypeData();

            int[][] arrayInt = null;
            if(type.equals("Integer")){
                arrayInt = new int[obj.length][];
                for(int i = 0; i<obj.length; i++){
                    arrayInt[i] = new int[obj[i].length];
                    for(int j = 0; j<obj[i].length; j++)
                        arrayInt[i][j] = ((Integer)obj[i][j]).intValue();
                }
            }
            float[][] arrayFloat = null;
            if(type.equals("Float")){
                arrayFloat = new float[obj.length][];
                for(int i = 0; i<obj.length; i++){
                    arrayFloat[i] = new float[obj[i].length];
                    for(int j = 0; j<obj[i].length; j++)
                        arrayFloat[i][j] = ((Float)obj[i][j]).floatValue();
                }
            }
            String[][] arrayString = null;
            if(type.equals("String")){
                arrayString = new String[obj.length][];
                for(int i = 0; i<obj.length; i++){
                    arrayString[i] = new String[obj[i].length];
                    for(int j = 0; j<obj[i].length; j++)
                        arrayString[i][j] = obj[i][j].toString();
                }
            }

            dataOut = new DataOut(algorythmFile.getAlgorythmName(), obj[numberOfPoints-1].length,numberOfPoints);
            dataOut.setType(arraysData.getTypeData());
            timeEachKitElem[step] = new double[numberOfPoints];
            memoryEachKitElem[step] = new double[numberOfPoints];

            for (int i = 0; i<obj.length; i++){

                ImportingModuleEngine examineAlgorythm = new ImportingModuleEngine(algorythmFile);
                if(arraysData.getTypeData().equals("Integer"))
                    examineAlgorythm.setIntArray(arrayInt[i]);
                if(arraysData.getTypeData().equals("Float"))
                    examineAlgorythm.setFloatArray(arrayFloat[i]);
                if(arraysData.getTypeData().equals("String"))
                    examineAlgorythm.setStringArray(arrayString[i]);

                threadExamineAlgorythm = new Thread(examineAlgorythm);

                try{
                    threadExamineAlgorythm.start();
                    threadExamineAlgorythm.join();
                }catch (InterruptedException ex){
                    ex.getStackTrace();
                }
                timeEachKitElem[step][i] = totalTime/1000;
                memoryEachKitElem[step][i] = totalMemory/1024;
//                dataOut.setTime(totalTime/1000, i);
//                dataOut.setMemory(totalMemory/1024, i);
                dataOut.setPoint(obj[i].length,i);

            }

        }
        double[] avgTime = new double[numberOfPoints];
        for(int j = 0; j<numberOfPoints; j++){
            double temp = 0d;
            for (int i = 0; i<timeEachKitElem.length; i++){
                temp += timeEachKitElem[i][j];
            }
            avgTime[j] = temp/numberOfPoints;
        }
        double[] avgMemory = new double[numberOfPoints];
        for(int j = 0; j<numberOfPoints; j++){
            double temp = 0d;
            for (int i = 0; i<memoryEachKitElem.length; i++){
                temp += memoryEachKitElem[i][j];
            }
            avgMemory[j] = temp/numberOfPoints;
        }
        for(int i = 0; i<numberOfPoints; i++){
            dataOut.setTime(avgTime[i], i);
            dataOut.setMemory(avgMemory[i], i);
        }
        return dataOut;
    }
    public  static ArrayList<String> readSourceFromFile(File file){
        ArrayList<String> buffer = new ArrayList<String>();
        String temp;
        try{
            FileInputStream fInput = new FileInputStream(file);
            InputStreamReader streamReader = new InputStreamReader(fInput);
            BufferedReader reader = new BufferedReader(streamReader);
            while((temp=reader.readLine()) != null)
                buffer.add(temp);

            reader.close();

        }catch (FileNotFoundException ex){
            JOptionPane.showMessageDialog(frames.getFrameCreateAlgo(),
                    "Error #999 " + ex.getMessage(), "Read file",JOptionPane.ERROR_MESSAGE);
        }catch (IOException ex){
            JOptionPane.showMessageDialog(frames.getFrameCreateAlgo(),
                    "Error #1000 " + ex.getMessage(), "Read file",JOptionPane.ERROR_MESSAGE);
        }
        return buffer;
    }
    public static void saveSourceToFile(File file, String text){
        if(file != null){
            try{
                PrintWriter printWriter = new PrintWriter(file);
                try{
                    printWriter.print(text);
                }finally {
                    printWriter.close();
                }
            }catch (FileNotFoundException ex){
                JOptionPane.showMessageDialog(frames.getFrameCreateAlgo(),
                        "Error #998 " + ex.getMessage() , "Compile error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    public static String compileAlgorythm(String commandLine){
        BufferedReader bufDone;
        BufferedReader bufError;
        String stringDone = "";
        String stringError = "";
        try{

            Process compile = Runtime.getRuntime().exec(commandLine);
            bufDone = new BufferedReader(new InputStreamReader(compile.getInputStream()));
            bufError = new BufferedReader(new InputStreamReader(compile.getErrorStream()));

            String lineDone;
            while ((lineDone = bufDone.readLine())!=null)
                stringDone = stringDone + lineDone + "\n";
            bufDone.close();

            String lineError;
            while ((lineError = bufError.readLine())!=null)
                stringError = stringError +lineError + "\n";
            bufError.close();

        }catch(IOException ex){
            JOptionPane.showMessageDialog(frames.getFrameCreateAlgo(),
                    ex.getMessage() , "Compile error", JOptionPane.ERROR_MESSAGE);
            return null;

        }catch(IllegalArgumentException ex){
            JOptionPane.showMessageDialog(frames.getFrameCreateAlgo(),
                    ex.getMessage() , "Compile error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        if (!stringError.equals(""))
            return stringError;
        return "Done";

    }


    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {

                    frames = new AnalysatorFrames();

                    frames.runMainWindow();
                    frames.getMainWindow().setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
