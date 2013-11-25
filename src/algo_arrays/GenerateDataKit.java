package algo_arrays;

import algo_general.Main;


import javax.swing.*;
import java.util.*;
/**
 * This class generates and fills arrays data. Also it adds them into kit.
 * If option "State" sets as "Sorted" then all arrays in kit will sort.
 *
 * @author Alex Iakovenko
 * Date: 11/11/13
 * Time: 10:08 PM
 */
public class GenerateDataKit implements Runnable {
//    private StructureArray data;
    private DataStructures data;
    private int kitSize;
    private int length;
    private Object type;           //Describes structure type (for example: 0 - Integer, 1 - Float)
    private String state;       //Describes structure state (such as sorted or unsorted)


    public GenerateDataKit(int length, Object type, String state, int kitSize){
        this.length = length;
        this.type = type;
        this.state = state;
        this.kitSize = kitSize;
    }

    public void run(){
        data = createArray(type);
//        if(data != null){
            /*ArraysDataBase str = Main.transferArraysDataFromFrameGeneralWindow();*/
        ArraysDataBase str = Main.getStructureBase();
            str.addData(data);
//        }else{
//            throw new UnsupportedOperationException();
//
//        }

    }

    private  DataStructures createArray(Object type){

        Structure structure;

        if(type.getClass() == Integer.class){
            structure = new IntegerArray(kitSize,length);
        }
        else
        if(type.getClass() == Float.class){
            structure = new FloatArray(kitSize,length);
        }
        else
        if(type.getClass() == String.class){
            structure = new StringArray(kitSize,length);
        }
        else {
            return null;
        }

        if(state.equals("Sorted") && structure != null)
            structure.sortedStructure();

        return structure.getStructure();

    }

}
