package algo_results;


import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 27.05.13
 * Time: 14:55
 * To change this template use File | Settings | File Templates.
 */

public class DataOut implements Serializable {

    private String algoName;
    private double[] time;
    private double[] memory;
    private int[] points;
    private int maxValueLength;
    private int numberOfPoints;
    private String type;


    public DataOut (String algoName, int maxValueLength, int numberOfPoints){
        this.algoName = algoName;
        this.maxValueLength = maxValueLength;
        this.numberOfPoints = numberOfPoints;
        time = new double[numberOfPoints];
        memory = new double[numberOfPoints];
        points = new int [numberOfPoints];
    }
    public double getMaxTime (){
        double maxTime = 0;
        for(int i = 0; i<time.length; i++){
            if(time[i]>maxTime)
                maxTime = time[i];
        }
        return maxTime;
    }
    public double getMaxMemory (){
        double maxMemory = 0;
        for(int i = 0; i<memory.length; i++){
            if(memory[i]>maxMemory)
                maxMemory = memory[i];
        }
        return maxMemory;
    }
    public String getAlgoName(){
        return algoName;
    }

    public int getMaxValueLength(){
        return maxValueLength;
    }

    public int getNumberOfPoints(){
        return  numberOfPoints;
    }

    public void setTime(double time, int index){
        this.time[index] = time;
    }
    public double[] getTime(){
        return time;
    }
    public void setMemory(double memory, int index){
        this.memory[index] = memory;
    }
    public double[] getMemory(){
        return memory;
    }
    public void setPoint(int point, int index){
        this.points[index] = point;
    }
    public int[] getPoints(){
        return points;
    }
    public void setType(String type){
        this.type = type;
    }
    public String getType(){
        return type;
    }







}
