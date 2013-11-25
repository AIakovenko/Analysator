package algo_general;

import algo_arrays.ArraysDataBase;
import algo_arrays.DataStructures;
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

    /*
     * Contains data structures for testing
     */
    private static ArraysDataBase structuresBase;

    /*
     *Contains information about .class files (implementations of algorythms
     */
    private static FileDataBase fileBase;


    /**
     * Returns object which contains structures of data.
     * @return object ArrayDataBase
     */
    public static ArraysDataBase getStructureBase(){
        return structuresBase;
    }

    /**
     * Sets object which contains structures of data.
     * @param data object ArrayDataBase
     */
     public static void setStructureBase(ArraysDataBase data){
         structuresBase = data;
     }

    /**
     * Returns object which contains information about implementation algorythm files.
     * @return object FileDataBase
     */
     public static FileDataBase getFileBase(){
        return fileBase;
    }

    /**
     * Sets object which contains information about implementation algorythm files.
     * @param data object FileDataBase
     */
    public static void setFileBase(FileDataBase data){
        fileBase = data;
    }

    /**
     * Starts main program window
     */
    public static void runDataWindow(){
        frames.runDataWindow();
        frames.getDataWindow().setVisible(true);
    }
    /*public static FileDataBase transferFileDataFromFrameGeneralWindow(){
        return frames.getMainWindow().getFileDataBase();
    }*/
    /**
     * Starts window of algorythm implementation files editor
     */
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

    /*public static void sendDataBaseToMainWindow(FileDataBase data){
        frames.getMainWindow().setDataBase(data);
    }*/
    public static void stopTest(){
        try{
            if (threadExamineAlgorythm.isAlive())
                threadExamineAlgorythm.interrupt();
        }catch(NullPointerException ex){

        }
    }
    public static DataOut runTest(AlgorythmFile algorythmFile, DataStructures arraysData, int numberOfPoints){
//        DataIn dataIn = new DataIn(algorythmFile, arraysData, numberOfPoints);
//        DataCalc calc = new DataCalc(dataIn);


        DataOut dataOut = null;
        ArrayList<Double[]> timeEachKitElem = new ArrayList<Double[]>();
        ArrayList<Double[]> memoryEachKitElem = new ArrayList<Double[]>();

        DataCalc calc = new DataCalc(arraysData, numberOfPoints);

//        calc.run();



        /*Thread calculation = new Thread(calc);
        try {
            calculation.start();
            calculation.join();
        }catch (InterruptedException ex){
            //NOP
        }*/


       /* double [][] timeEachKitElem = new double[arraysData.kitSize()][];
        double [][] memoryEachKitElem = new double[arraysData.kitSize()][];*/
        for(int step = 0; step<arraysData.kitSize(); step++ ){
           /*
            * Prepares data for testing.
            */
            calc.createDataSet(step);
            DataStructures measureKit = calc.getStructureIn();

            /*
             * Creates object where will be saved results of testing
             */
            dataOut = new DataOut(algorythmFile.getAlgorythmName(), measureKit.getLength(),numberOfPoints);
            dataOut.setType(arraysData.getType());

            Double[] time = new Double[numberOfPoints];
            Double[] memory = new Double[numberOfPoints];

            for(int part = 0; part < measureKit.kitSize(); part++){
                ImportingModuleEngine examineAlgorythm = new ImportingModuleEngine(algorythmFile);

                if(arraysData.getFromKit(step).getClass() == Integer[].class){
                    int[] array = new int[measureKit.getLength(part)];
                    for(int i = 0; i < measureKit.getLength(part); i++){
                        array[i] = ((Integer[])measureKit.getFromKit(part))[i];
                    }
                    examineAlgorythm.setIntArray(array);
                }
                if(arraysData.getFromKit(step).getClass() == Float[].class){
                    float[] array = new float[measureKit.getLength(part)];
                    for(int i = 0; i < measureKit.getLength(part); i++){
                        array[i] = ((Float[])measureKit.getFromKit(part))[i];
                    }
                    examineAlgorythm.setFloatArray(array);
                }
                if(arraysData.getFromKit(step).getClass() == String[].class){
                    String[] array = new String[measureKit.getLength(part)];
                    for(int i = 0; i < measureKit.getLength(part); i++){
                        array[i] = ((String[])measureKit.getFromKit(part))[i];
                    }
                    examineAlgorythm.setStringArray(array);
                }

                threadExamineAlgorythm = new Thread(examineAlgorythm);
                /**
                 * Start process algorythms testing
                 */
                try{
                    threadExamineAlgorythm.start();
                    threadExamineAlgorythm.join();
                }catch (InterruptedException ex){
                    ex.getStackTrace();
                }

                /*
                 * Keep in variable time of execute and memory use values
                 */
                time[part] = totalTime/1000;
                //TODO measure used memory
//                memory[part] = totalMemory/1024;
                memory[part] = 0d;

                dataOut.setPoint(measureKit.getLength(part), part);
            }
            timeEachKitElem.add(time);
            memoryEachKitElem.add(memory);


        }
        double[] avgTime = new double[numberOfPoints];
        for(int j = 0; j<numberOfPoints; j++){
            double temp = 0d;
            for (int i = 0; i<timeEachKitElem.size(); i++){
                temp += timeEachKitElem.get(i)[j];
            }
            avgTime[j] = temp/numberOfPoints;
        }
        double[] avgMemory = new double[numberOfPoints];
        for(int j = 0; j<numberOfPoints; j++){
            double temp = 0d;
            for (int i = 0; i<memoryEachKitElem.size(); i++){
                temp += memoryEachKitElem.get(i)[j];
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


    /*
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {

                    frames = new AnalysatorFrames();
                    structuresBase = new ArraysDataBase();
                    fileBase = new FileDataBase();
                    frames.runMainWindow();
                    frames.getMainWindow().setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
