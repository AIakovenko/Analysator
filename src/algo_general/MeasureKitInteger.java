package algo_general;

import algo_arrays.DataArrays;
import algo_arrays.DataStructures;

/**
 * Describe measure kits (data structures divide on some different length parts).
 * Kits contain Integer data.
 *
 * @autor Alex Iakovenko
 * Date: 11/15/13
 * Time: 7:47 PM
 */
public class MeasureKitInteger implements MeasureKitType {

//    private DataIn dataIn;
    private DataStructures inData;
    private DataStructures outData;

    public MeasureKitInteger(DataStructures inData){
        this.inData = inData;
        outData = new DataArrays<Integer[]>();
    }
    /**
     * Divides element (for example array) in some parts with different length.
     *
     * @param numberOfPoints sets number of parts
     * @param elementIndex   index of element in kit
     */
    @Override
    public void createFrom(int elementIndex, int numberOfPoints){
        int size = 0;
        for(int i = 0; i<numberOfPoints; i++){
            size = size + inData.getLength()/numberOfPoints;
            Integer[] array = new Integer[size];
            for(int j = 0; j < size; j++ ){
                array[j] = ((Integer[])inData.getFromKit(elementIndex))[j];
            }
            outData.addToKit(array);
        }
    }
    /**
     * Shuffles data in each part
     */
    @Override
    public void shuffle(){

    }
    @Override
    public DataStructures getKit(){
        return outData;
    }
}
