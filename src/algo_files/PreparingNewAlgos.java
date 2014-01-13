package algo_files;

import algo_general.AnalysatorFrames;
import algo_general.Main;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class contains methods to add new realisation of algorythms.
 * They let read information from file which had already created, save modified data to external file and
 * compile source code to byte-code (class-files).
 *
 * @autor Alex Iakovenko
 * Date: 12/5/13
 * Time: 7:40 PM
 */

public class PreparingNewAlgos {

    private static AnalysatorFrames frames = Main.getFrames();

    private PreparingNewAlgos(){
        /* NOP */
    };

    /**
     * Reads text of course code from file which had already written.
     *
     * @param   file with source code (.java, etc).
     * @return  buffer is a List, contains the lines of text which have ridden from file.
     */
    public  static List readSourceFromFile(File file) {
        List buffer = new ArrayList<String>();
        String temp;

        try(FileInputStream fInput = new FileInputStream(file);
            InputStreamReader streamReader = new InputStreamReader(fInput);
            BufferedReader reader = new BufferedReader(streamReader)) {

            while((temp=reader.readLine()) != null)
                buffer.add(temp);

        }catch (IOException ex){
            JOptionPane.showMessageDialog(frames.getFrameCreateAlgo(),
                    "Error #1000 " + ex.getMessage(), "Read file", JOptionPane.ERROR_MESSAGE);
        }

        return buffer;
    }

    /**
     *  Saves text which had modified to external file.
     *
     *  @param file where will be written the modification.
     *  @param text which will be kept into file
     */
    public static void saveSourceToFile(File file, String text){
        if(file != null){
            try(PrintWriter printWriter = new PrintWriter(file)){
                printWriter.print(text);
            }catch (FileNotFoundException ex){
                JOptionPane.showMessageDialog(frames.getFrameCreateAlgo(),
                        "Error #998 " + ex.getMessage() , "Save error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    /**
     * Produces operation of compiling course code from current file to byte-code (class-file).
     *
     * @param commandLine contains parameters and information for compilation.
     *
     * @return "Done" if compiling was successful.
     *          null if was got exception.
     *          error message if compiling process finished with error.
     *
     */
    public static String compileAlgorythm(String commandLine){
        BufferedReader bufDone;
        BufferedReader bufError;
        String stringDone = "";
        String stringError = "";
        try{

            Process compile = Runtime.getRuntime().exec(commandLine);
            bufDone = new BufferedReader(new InputStreamReader(compile.getInputStream()));
            bufError = new BufferedReader(new InputStreamReader(compile.getErrorStream()));

            String lineDone;
            while ((lineDone = bufDone.readLine())!=null)
                stringDone = stringDone + lineDone + "\n";
            bufDone.close();

            String lineError;
            while ((lineError = bufError.readLine())!=null)
                stringError = stringError +lineError + "\n";
            bufError.close();

        }catch(IOException ex){
            JOptionPane.showMessageDialog(frames.getFrameCreateAlgo(),
                    ex.getMessage() , "Compile error", JOptionPane.ERROR_MESSAGE);
            return null;

        }catch(IllegalArgumentException ex){
            JOptionPane.showMessageDialog(frames.getFrameCreateAlgo(),
                    ex.getMessage() , "Compile error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        if (!stringError.equals(""))
            return stringError;
        return "Done";

    }

}
