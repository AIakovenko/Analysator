package algo_general;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 27.05.13
 * Time: 13:32
 * To change this template use File | Settings | File Templates.
 */
import algo_files.*;
import algo_arrays.*;

public class DataIn {

    private AlgorythmFile algorythmFile;
    private DataStructures arrData;
    private int numberOfPoints;

    public DataIn(AlgorythmFile algorythmFile, DataStructures arrData, int numberOfPoints){
        this.algorythmFile = algorythmFile;
        this.arrData =  arrData;
        this.numberOfPoints = numberOfPoints;
    }
    public AlgorythmFile getAlgorythmFile(){
        return algorythmFile;
    }

    public DataStructures getArrData(){
        return arrData;
    }
    public void setNumberOfPoints(int count){
        numberOfPoints = count;
    }
    public int getNumberOfPoints(){
        return numberOfPoints;
    }

}
