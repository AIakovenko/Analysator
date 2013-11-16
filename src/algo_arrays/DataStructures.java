package algo_arrays;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class describes a kit of data structures.
 *
 * @author Alex Iakovenko
 * @since 1.0.3
 * Date: 11/11/13
 * Time: 10:08 PM
 */

public abstract class DataStructures<T> implements Serializable {

    protected String  state = "Unsorted";
    protected List<T> kit = new ArrayList<T>();

    public abstract int getLength();
    public abstract int getLength(int index);
    public abstract String getType();

    public boolean addToKit(T elem){
        if(kit.add(elem))
            return true;
        else
            return false;
    }

    public boolean removeFromKit(int index){
        try{
            kit.remove(index);
            return true;
        }catch(UnsupportedOperationException e){
            return false;
        }catch (IndexOutOfBoundsException e){
            return false;
        }
    }
    public int kitSize(){
        return kit.size();
    }
    public void setState (String state){
        this.state = state;
    }
    public String getState(){
        return state;
    }

    public T getFromKit(int index){
        return kit.get(index);
    }
}
