package algo_general;

import algo_arrays.DataArrays;
import algo_arrays.DataStructures;

/**
 * Describe measure kits (data structures divide on some different length parts).
 * Kits contain String data.
 *
 * @autor Alex Iakovenko
 * Date: 11/16/13
 * Time: 8:31 AM
 */
public class MeasureKitString implements MeasureKitType {

    private DataStructures inData;
    private DataStructures outData;

    public MeasureKitString(DataStructures inData) {
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
    public void createFrom(int elementIndex, int numberOfPoints) {
        int size = 0;
        for(int i = 0; i<numberOfPoints; i++){
            size = size + inData.getLength()/numberOfPoints;
            String[] array = new String[size];
            for(int j = 0; j < size; j++ ){
                array[j] = ((String[])inData.getFromKit(elementIndex))[j];
            }
            outData.addToKit(array);
        }
    }

    /**
     * Shuffles data in each part
     */
    @Override
    public void shuffle() {

    }

    @Override
    public DataStructures getKit() {
        return outData;
    }
}
