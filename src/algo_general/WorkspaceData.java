package algo_general;

import java.io.Serializable;
import algo_arrays.ArraysDataBase;
import algo_files.FileDataBase;
import algo_results.*;
/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 10.06.13
 * Time: 20:58
 * To change this template use File | Settings | File Templates.
 */
public class WorkspaceData implements Serializable {
    private ArraysDataBase arraysDataBase;
    private FileDataBase fileDataBase;
    private ListDataOut listDataOut;

    public WorkspaceData(ArraysDataBase arraysDataBase, FileDataBase fileDataBase, ListDataOut listDataOut){
        this.arraysDataBase = arraysDataBase;
        this.fileDataBase = fileDataBase;
        this.listDataOut = listDataOut;
    }

    public ArraysDataBase getArraysDataBase(){
        return arraysDataBase;
    }
    public FileDataBase getFileDataBase(){
        return fileDataBase;
    }
    public ListDataOut getListDataOut(){
        return listDataOut;
    }


}
