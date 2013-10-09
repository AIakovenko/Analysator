package algo_arrays;

import algo_general.Main;



/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 13.08.13
 * Time: 17:26
 * To change this template use File | Settings | File Templates.
 */
public abstract class DataStructure {

    protected int dataLength;
    protected String typeData;
    protected int kitLength;
    protected String  state = "Unsorted";

    public abstract Object[][] getStructure();
    public abstract int getLength();


    public void setTypeData (String typeData){
        this.typeData = typeData;
    };

    public String getTypeData(){
        return typeData;
    };

    public void setState ( String state){
        this.state = state;
    };

    public String getState(){
        return state;
    }
    public int getKitLength(){
        return kitLength;
    }
}
