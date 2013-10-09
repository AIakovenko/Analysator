package algo_results;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 27.05.13
 * Time: 15:15
 * To change this template use File | Settings | File Templates.
 */
public class ListDataOut implements Serializable {

    private ArrayList<DataOut> listDataOut;

    public ListDataOut(){
        listDataOut = new ArrayList<DataOut>();
    }

    public boolean addData(DataOut obj){
        return listDataOut.add(obj);
    }

    public DataOut getData(int index){
        if(listDataOut != null)
            return listDataOut.get(index);
        else return null;
    }

    public DataOut deleteData(int index){
        if(listDataOut != null)
            return listDataOut.remove(index);
        else return null;
    }

    public int getLength(){
        return listDataOut.size();
    }


}
