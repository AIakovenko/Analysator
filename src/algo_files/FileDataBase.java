package algo_files;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 18.05.13
 * Time: 15:27
 * To change this template use File | Settings | File Templates.
 */
import java.io.Serializable;
import java.util.*;

public class FileDataBase implements Serializable {
    private ArrayList<AlgorythmFile> dataBaseFiles;
    public FileDataBase(){
        dataBaseFiles = new ArrayList<AlgorythmFile>();
    }

    public boolean addFile(AlgorythmFile objFile){
        return dataBaseFiles.add(objFile);
    }

    public AlgorythmFile getFile(int index){
        if( dataBaseFiles != null){
            return dataBaseFiles.get(index);
        }
        return null;
    }

    public int getLength(){
        return dataBaseFiles.size();
    }

    public AlgorythmFile deleteFile(int index){
        AlgorythmFile file = dataBaseFiles.remove(index);
        if(file != null)
            return file;
        else
            return null;

    }
}
