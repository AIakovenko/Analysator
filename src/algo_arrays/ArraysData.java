package algo_arrays;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 06.05.13
 * Time: 9:39
 */
public class ArraysData implements Serializable{
    private String typeData;
    private int kitLength;
    private int dataLength;
    private Object[][] arrays;
    private String state = "Unsorted";

    public ArraysData (Object[][] array){
        kitLength = array.length;
        for(int i = 0; i<array.length; i++){
            dataLength = array[i].length;
            arrays = new Object[i][dataLength];
            arrays = array;
        }
    }

    public void setTypeData(String type){
        typeData = type;
    }

    public String getTypeData(){
        return typeData;
    }

    public int getDataLength(){
        return dataLength;
    }

    public Object[][] getArrays(){
        return arrays;
    }
    public void setState(String state){
        this.state = state;
    }
    public String getState(){
        return state;
    }
    public void setKitLength(int kitLength){
        this.kitLength = kitLength;
    }
    public int getKitLength(){
        return kitLength;
    }

}
