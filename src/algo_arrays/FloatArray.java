package algo_arrays;

import java.util.Arrays;
import java.util.Random;

/**
 * This class creates a kit of Float arrays;
 * Also it can sort the kit elements if the State option sets as "Sorted";
 *
 * @autor Alex Iakovenko
 * Date: 11/13/13
 * Time: 10:42 AM
 */
public class FloatArray extends Structure {
    private float[] array;
    public FloatArray(int kit, int length){
        this.kitSize = kit;
        this.length = length;

        createStructure();
    }

    private void createStructure() {
        Random random = new Random((int)Math.random()*100);
        data = new DataArrays<float[]>();
        for(int i=0; i<kitSize; i++){
            array = new float[length];
            for (int j=0; j<array.length; j++){
                array[j] = random.nextFloat();
            }
            data.addToKit(array);
        }

    }

    @Override
    public void sortedStructure() {
        for (int i = 0; i < data.kitSize(); i++){
            Arrays.sort((float[]) data.getFromKit(i));
        }
        data.setState("Sorted");
    }

    @Override
    public DataStructures getStructure() {
        return data;
    }
}
