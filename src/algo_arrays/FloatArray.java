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
    private Float[] array;
    public FloatArray(int kit, int length, int chars){
        this.kitSize = kit;
        this.length = length;
        this.chars = chars;

        createStructure();
    }

    private void createStructure() {
        Random random = new Random((int)Math.random()*100);
        data = new DataArrays<Float[]>();
        for(int i=0; i<kitSize; i++){
            array = new Float[length];
            for (int j=0; j<array.length; j++){
                array[j] = random.nextFloat()*chars;
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
