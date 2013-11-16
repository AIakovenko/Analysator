package algo_arrays;

import java.io.Serializable;

/**
 * This class describes one array which will add to kit.
 *
 * @author Alex Iakovenko
 * @since 1.0.3
 * Date: 11/11/13
 * Time: 10:08 PM
 */
public class DataArrays<T> extends DataStructures<T[]> implements Serializable {

    /**
     * Returns length of last element from kit
     * If kit size is 0 then will be returned -1
     */
    @Override
    public int getLength() {
        return kitSize() != 0 ? kit.get(kitSize()-1).length : -1;
    }

    /**
     * Returns length of element from kit.
     * If kit size is 0 then will be returned -1
     *
     * @param index Element's number in kit.
     */
    @Override
    public int getLength(int index){
        return kitSize() != 0 ? kit.get(index).length : -1;
    };
    /**
     * Returns data type
     *
     * @throws IndexOutOfBoundsException
     */
    @Override
    public String getType() throws IndexOutOfBoundsException{
        String[] type = kit.get(0).getClass().toString().split("\\.");
        return type[type.length-1].replace(";","");
    }

}
