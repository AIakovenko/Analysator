package algo_arrays;

import java.util.Arrays;
import java.util.Random;

/**
 * This class creates a kit of Integer arrays;
 * Also it can sort the kit elements if the State option sets as "Sorted";
 *
 * @autor Alex Iakovenko
 * Date: 11/13/13
 * Time: 9:38 AM
 */
public class IntegerArray extends Structure {
    private Integer[] array;

    public IntegerArray(int kit, int length){
        this.kitSize = kit;
        this.length = length;

        createStructure();
    }

    private void createStructure() {
        Random random = new Random((int)Math.random()*100);
        data = new DataArrays<Integer[]>();
        for(int i=0; i<kitSize; i++){
            array = new Integer[length];
            for (int j=0; j<array.length; j++){
                array[j] = random.nextInt();
            }
            data.addToKit(array);
        }

    }

    @Override
    public void sortedStructure() {
        for (int i = 0; i < data.kitSize(); i++){
            Arrays.sort((Integer[]) data.getFromKit(i));
        }
        data.setState("Sorted");
    }

    @Override
    public DataStructures getStructure() {
        return data;
    }
}
