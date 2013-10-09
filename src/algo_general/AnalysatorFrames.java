package algo_general;

import algo_arrays.FrameAddArraysData;
import algo_files.FrameCreateAlgo;
import algo_files.FrameFileDataBaseEditor;
import algo_results.ResultView;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 28.04.13
 * Time: 17:21
 * To change this template use File | Settings | File Templates.
 */
public class AnalysatorFrames {
    private static FrameGeneralWindow mainWindow;
    private static FrameAddArraysData dataWindow;
    private static FrameFileDataBaseEditor fileDataBaseEditor;
    private static FrameCreateAlgo createAlgo;
    private static ResultView resultView;

    public void runMainWindow(){
        mainWindow = new FrameGeneralWindow("Analyzer");
    }
    public FrameGeneralWindow getMainWindow(){
        return mainWindow;
    }

    public void runDataWindow(){
        if(dataWindow == null){
            dataWindow = new FrameAddArraysData();
        }
    }

    public FrameAddArraysData getDataWindow(){
        return dataWindow;
    }

    public void runFileDataBaseEditor(){
        if(fileDataBaseEditor == null){
            fileDataBaseEditor = new FrameFileDataBaseEditor();
        }
    }

    public FrameFileDataBaseEditor getFileDataBaseEditor(){
        return fileDataBaseEditor;
    }

    public void runFrameCreateAlgo(){
        if(createAlgo == null){
            createAlgo = new FrameCreateAlgo();
        }
    }

    public FrameCreateAlgo getFrameCreateAlgo(){
        return createAlgo;
    }

    public void runResultView(){
        if(resultView == null){
            resultView = new ResultView("Result");
        }
    }

    public ResultView getResultView(){
        return resultView;
    }



}
