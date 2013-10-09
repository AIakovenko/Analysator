package algo_arrays; /**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 07.05.13
 * Time: 22:57
 * To change this template use File | Settings | File Templates.
 */

import java.io.Serializable;
import java.util.*;
public class ArraysDataBase implements Serializable{

    private ArrayList<DataStructure> arraysBase;

    public ArraysDataBase(){
        arraysBase = new ArrayList<DataStructure>();
    }

    public boolean addData(DataStructure obj){
        return arraysBase.add(obj);

    }

    public DataStructure getData(int index){
        if(arraysBase != null)
            return arraysBase.get(index);
        else
            return null;
    }

    public int getLength(){
        return arraysBase.size();
    }

    public DataStructure deleteData(int index){
        DataStructure array = arraysBase.remove(index);
        if(array != null)
            return array;
        else
            return null;
    }
}
