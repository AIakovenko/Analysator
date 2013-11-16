package algo_general;

import algo_arrays.DataArrays;
import algo_arrays.DataStructures;

import java.util.List;

/**
 *
 * @autor Alex Iakovenko
 * Date: 11/15/13
 * Time: 7:32 PM
 */
public class MeasureKit {

    private DataStructures inData;
    private int numberOfPoints;

    public MeasureKit(DataStructures inData, int numberOfPoints){
        this.inData = inData;
        this.numberOfPoints = numberOfPoints;
    }

    public DataStructures createMeasureKit(int indexElement){

        DataStructures outData;
        MeasureKitType measureKit;

        if(inData.getFromKit(indexElement).getClass() == Integer[].class){
            measureKit = new MeasureKitInteger(inData);
            measureKit.createFrom(indexElement,numberOfPoints);
            outData = measureKit.getKit();
            return outData;
        }
        if(inData.getFromKit(indexElement).getClass() == Float[].class){
            measureKit = new MeasureKitFloat(inData);
            measureKit.createFrom(indexElement,numberOfPoints);
            outData = measureKit.getKit();
            return outData;
        }
        if(inData.getFromKit(indexElement).getClass() == Float[].class){
            measureKit = new MeasureKitString(inData);
            measureKit.createFrom(indexElement,numberOfPoints);
            outData = measureKit.getKit();
            return outData;
        }
        return null;
    }

}
