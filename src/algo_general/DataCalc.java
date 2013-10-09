package algo_general;

import algo_arrays.ArraysData;
import algo_arrays.DataStructure;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 27.05.13
 * Time: 16:23
 * To change this template use File | Settings | File Templates.
 */

public class DataCalc implements Runnable{

    private final int POINTS = 10;
    private DataIn dataIn;
    private Object[][] arraysIn;
    private ArrayList<Object[][]> listArrays;



    public DataCalc(DataIn obj){
        dataIn = obj;
        listArrays = new ArrayList<Object[][]>(POINTS);
    }

    public Object[][] getArraysIn(int index){
        return listArrays.get(index);
    }

    public void run(){
        createDataSet();
    }

    private void createDataSet(){

        for (int k = 0; k< dataIn.getArrData().getStructure().length; k++){
            int size = 0;
            arraysIn = new Object[dataIn.getNumberOfPoints()][];
            for(int i = 0; i<arraysIn.length; i++){
                size = size + dataIn.getArrData().getLength()/dataIn.getNumberOfPoints();
                arraysIn[i] = new Object[size];
                for(int j = 0; j<size; j++){
                     arraysIn[i][j] = dataIn.getArrData().getStructure()[k][j];
                }
                shuffleDataSet(arraysIn[i]);
            }
            listArrays.add(arraysIn);
        }
    }

    private void shuffleDataSet(Object[] array){
        int n = array.length;
        Random random = new Random();
        random.nextInt();
        for (int i = 0; i < n; i++) {
            int change = i + random.nextInt(n - i);
            swap(array, i, change);
        }
    }
    private void swap(Object[] array, int i, int change) {
        Object helper = array[i];
        array[i] = array[change];
        array[change] = helper;
    }




}
