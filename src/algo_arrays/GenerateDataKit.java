package algo_arrays;

import algo_general.Main;

import javax.swing.*;
import java.util.*;
/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 24.07.13
 * Time: 15:07
 * To change this template use File | Settings | File Templates.
 */
public class GenerateDataKit implements Runnable {
    private StructureArray data;
    private int length;
    private int type;
    private String state;
    private int kit;
    public GenerateDataKit(int length, int type, String state, int kit){
        this.length = length;
        this.type = type;
        this.state = state;
        this.kit = kit;
    }
    public void run(){
//        SwingWorker<ArraysData, Void> createArr = new SwingWorker<ArraysData, Void>() {
//            @Override
//            protected ArraysData doInBackground() throws Exception {
                data = (StructureArray)createArray(length, type, kit);
                if (state.equals("Sorted")){
                    for (int i = 0; i < data.getStructure().length; i++){
                        Arrays.sort(data.getStructure()[i]);
                    }
                    data.setState(state);
                }
                ArraysDataBase str = Main.transferArraysDataFromFrameGeneralWindow();
                str.addData(data);

    }

    private DataStructure createArray(int valueLength, int type, int kit){
        Object[][] array;
        DataStructure data;
        array = new Object[kit][valueLength];
        switch (type){
            case 0:
                for(int i=0; i<kit; i++){
                    for (int j=0; j<array[i].length; j++){
                        array[i][j] = (int)(Math.random()*100000);
                    }
                }
                data = new StructureArray(array);
                data.setTypeData("Integer");
                return data;

            case 1:
                for(int i=0; i<kit; i++){
                    for (int j=0; j<array[i].length; j++){
                        array[i][j] = (float)(Math.random()*100);
                    }
                }
                data = new StructureArray(array);
                data.setTypeData("Float");
                return data;

            case 2: //TODO You have to change randomize string generation
                for(int i=0; i<kit; i++){
                    for (int j=0; j<array[i].length; j++){
                        array[i][j] ="testString " + (float)(Math.random()*100);
                    }
                }
                data = new StructureArray(array);
                data.setTypeData("String");
                return data;
        }
        return null;
    }
}
