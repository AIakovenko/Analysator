package algo_files;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 18.05.13
 * Time: 14:41
 * To change this template use File | Settings | File Templates.
 */
import java.io.File;
import java.io.Serializable;

public class AlgorythmFile implements Serializable {
    private String algorythmName;
    private String algorythmType;
    private File algorythmFile;

    public AlgorythmFile(String name, String type, File file){
        algorythmName = name;
        algorythmType = type;
        algorythmFile = file;
    }

    public String getAlgorythmName(){
        return algorythmName;
    }
    public void setAlgorythmName(String name){
        algorythmName = name;
    }
    public String getAlgorythmType(){
        return algorythmType;
    }
    public void setAlgorythmType(String type){
        algorythmType = type;
    }
    public File getAlgorythmFile(){
        return algorythmFile;
    }
    public void setAlgorythmFile(File file){
        algorythmFile = file;
    }





}
