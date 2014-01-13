package algo_general;

import algo_arrays.DataStructures;
import algo_files.AlgorythmFile;
import algo_files.ImportingModuleEngine;
import algo_results.DataOut;

import java.util.ArrayList;

/**
 * Class runs measuring algorythm performance
 *
 * @autor Alex Iakovenko
 * Date: 12/6/13
 * Time: 8:57 AM
 */
public class TestRunner {

    public static double totalTime = 0;
    public static double totalMemory = 0;
    private static Thread threadExamineAlgorythm;

    private TestRunner(){/* NOP */}
    /**
     * Runs process of testing current target (implementation of algorythm).
     */
    public static DataOut runTest(AlgorythmFile algorythmFile, DataStructures arraysData, int numberOfPoints){

        DataOut dataOut = null;
        ArrayList<Double[]> timeEachKitElem = new ArrayList<Double[]>();
        ArrayList<Double[]> memoryEachKitElem = new ArrayList<Double[]>();

        DataCalc calc = new DataCalc(arraysData, numberOfPoints);

        for(int step = 0; step<arraysData.kitSize(); step++ ){

            /* Prepares data for testing. */
            calc.createDataSet(step);
            DataStructures measureKit = calc.getStructureIn();

            /* Creates object where will be saved results of testing */
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

                /* Start process algorythms testing */
                try{
                    threadExamineAlgorythm.start();
                    threadExamineAlgorythm.join();
                }catch (InterruptedException ex){
                    ex.getStackTrace();
                }

                /* Keep in variable time of execute and memory use values */
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

    /**
     * Interrupts the process of testing.
     */
    //TODO this method mast be modified.
    public static void stopTest(){
        try{
            if (threadExamineAlgorythm.isAlive())
                threadExamineAlgorythm.interrupt();
        }catch(NullPointerException ex){

        }
    }

}
