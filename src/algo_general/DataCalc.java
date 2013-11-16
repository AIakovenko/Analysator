package algo_general;


import algo_arrays.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 27.05.13
 * Time: 16:23
 * To change this template use File | Settings | File Templates.
 */

public class DataCalc /*implements Runnable*/{

//    private DataIn dataIn;
//    private List<DataStructures> listArrays;
    private DataStructures dataIn;
    private int numberOfPoints;
    private DataStructures dataOut;



    /*public DataCalc(DataIn dataIn){
        this.dataIn = dataIn;
        listArrays = new ArrayList<DataStructures>();
    }*/
    public DataCalc(DataStructures dataIn, int numberOfPoints){
        this.dataIn = dataIn;
        this.numberOfPoints = numberOfPoints;
    }

    /*public DataStructures getStructureIn(int index){
        return listArrays.get(index);
    }*/
    public DataStructures getStructureIn(){
        return dataOut;
    }
    /*public void run(){
        createDataSet();
    }*/

   /* private void createDataSet(){
        DataStructures arraysIn;
        for (int k = 0; k < dataIn.getArrData().getLength(); k++){
            int size = 0;
            arraysIn = new DataArrays<Integer[]>();
            for(int i = 0; i<dataIn.getNumberOfPoints(); i++){
                size = size + dataIn.getArrData().getLength()/dataIn.getNumberOfPoints();
//                arraysIn[i] = new Object[size];
                Integer[] array  = new Integer[size];
                for(int j = 0; j<size; j++){
//                     arraysIn[i][j] = dataIn.getArrData().getStructure()[k][j];
                    Integer [] ar = (Integer[])dataIn.getArrData().getFromKit(k);
                    array[j] =ar[j];
                }
                arraysIn.addToKit(array);
//                shuffleDataSet(arraysIn.getFromKit(i));
            }
            listArrays.add(arraysIn);
        }
    }*/
   public void createDataSet(int indexElement){

       MeasureKit measureKit = new MeasureKit(dataIn,numberOfPoints);
       dataOut = measureKit.createMeasureKit(indexElement);


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
