package algo_arrays;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 30.09.13
 * Time: 13:43
 * To change this template use File | Settings | File Templates.
 */
public class StructureArray extends DataStructure implements Serializable {

    private Object[][] arrays;

    public StructureArray (Object[][] array){
        kitLength = array.length;
        for(int i = 0; i<array.length; i++){
            dataLength = array[i].length;
            arrays = new Object[i][dataLength];
            arrays = array;
        }
    }
    @Override
    public  Object[][] getStructure(){
        return arrays;
    };

    @Override
    public  int getLength(){
        return dataLength;
    };

}
