package algo_general;

import algo_arrays.ArraysDataBase;
import algo_files.*;
import algo_results.ListDataOut;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 28.04.13
 * Time: 17:20
 * To change this template use File | Settings | File Templates.
 */
public class Main {

    /* Directory path to contain icon images */
    public static final String ICO_PATH = "ico/";

    /* Directory path to contain compiled byte-codes of testing algorythms */
    public static final String CLASS_PATH = "Classes/";

    /* Sets dimension of application buttons */
    public static final Dimension buttonSize = new Dimension(120,25);

    /* Contains objects of application frames */
    private static AnalysatorFrames frames;

    /* Contains data structures for testing */
    private static ArraysDataBase structuresBase;

    /* Contains information about .class files (implementations of algorythms) */
    private static FileDataBase fileBase;

    /**
     * Returns a container contains all frames of program.
     */
    public static AnalysatorFrames getFrames(){
        return frames;
    }

    /**
     * Returns object which contains structures of data.
     * @return object ArrayDataBase
     */
    public static ArraysDataBase getStructureBase(){
        return structuresBase;
    }

    /**
     * Sets object which contains structures of data.
     * @param data object ArrayDataBase
     */
     public static void setStructureBase(ArraysDataBase data){
         structuresBase = data;
     }

    /**
     * Returns object which contains information about implementation algorythm files.
     * @return object FileDataBase
     */
     public static FileDataBase getFileBase(){
        return fileBase;
    }

    /**
     * Sets object which contains information about implementation algorythm files.
     * @param data object FileDataBase
     */
    public static void setFileBase(FileDataBase data){
        fileBase = data;
    }

    /**
     * Starts main program window
     */
    public static void runDataWindow(){
        frames.runDataWindow();
        frames.getDataWindow().setVisible(true);
    }

    /**
     * Starts window of algorythm implementation files editor
     */
    public static void runFileDataBaseEditor(){
        frames.runFileDataBaseEditor();
        frames.getFileDataBaseEditor().setVisible(true);
    }

    /**
     * Creates and shows a window for writing and compiling new implementation of algorythm.
     * This implementation of algorythm will be used afterwards.
     */
    public static void runFrameCreateAlgo(){
        frames.runFrameCreateAlgo();
        frames.getFrameCreateAlgo().setVisible(true);
    }

    /**
     * Creates and shows a window of testing results. Window shows data which have got after testing.
     */
    public static void runResultView(){
        frames.runResultView();
        frames.getResultView().setVisible(true);
    }

    //TODO this method must be changed
    public static void transferDataToResultView(ListDataOut obj, int[] selResultRows){
        frames.getResultView().setData(obj, selResultRows);
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frames = new AnalysatorFrames();
                    structuresBase = new ArraysDataBase();
                    fileBase = new FileDataBase();
                    frames.runMainWindow();
                    frames.getMainWindow().setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
