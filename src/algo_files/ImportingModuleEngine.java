package algo_files;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 06.06.13
 * Time: 8:09
 * To change this template use File | Settings | File Templates.
 */

import algo_general.Main;

import javax.swing.*;
import java.io.File;

public class ImportingModuleEngine implements Runnable {
    private int[] intArray;
    private float[] floatArray;
    private String[] stringArray;
    private AlgorythmFile algorythmFile;

    public ImportingModuleEngine(AlgorythmFile algorythmFile){
        this.algorythmFile = algorythmFile;
//        int[] intArray = null;
//        float[] floatArray = null;
//        String[] stringArray = null;
    }


    public void setIntArray(int[] intArray){
        this.intArray = intArray;
    }
    public void setFloatArray(float[] floatArray){
        this.floatArray = floatArray;
    }
    public void setStringArray(String[] stringArray){
        this.stringArray = stringArray;
    }

    public void run() {

                String modulePath = Main.CLASS_PATH;
                ImportingModuleLoader loader = new ImportingModuleLoader(modulePath, ClassLoader.getSystemClassLoader());


            try {
//                String moduleName = module.split(".class")[0];
                String moduleName = algorythmFile.getAlgorythmFile().getName().split(".class")[0];
                Class clazz = loader.loadClass(moduleName);
                ImportingModule execute = (ImportingModule) clazz.newInstance();

//                if(intArray != null)
//                    execute.load(intArray);
//                if(floatArray !=null)
//                    execute.load(floatArray);
//                if(stringArray != null)
//                    execute.load(stringArray);
                execute.load(intArray, floatArray, stringArray);

                long startTime = System.currentTimeMillis();
                long firstMemory = Runtime.getRuntime().freeMemory();

                execute.run();

                long lastMemory = Runtime.getRuntime().freeMemory();
                long stopTime = System.currentTimeMillis();
                Main.totalMemory = (double)(firstMemory - lastMemory);
                Main.totalTime =(double)(stopTime - startTime);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }


    }


}

