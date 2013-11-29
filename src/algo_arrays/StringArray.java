package algo_arrays;

import java.util.Arrays;
import java.util.Random;

/**
 * This class creates a kit of String arrays;
 * Also it can sort the kit elements if the State option sets as "Sorted";
 *
 * @autor Alex Iakovenko
 * Date: 11/13/13
 * Time: 10:50 AM
 */
public class StringArray extends Structure {
    private String[] array;
    public StringArray(int kit, int length, int chars){
        this.kitSize = kit;
        this.length = length;
        this.chars = chars;

        createStructure();
    }

    private void createStructure() {
        Random random = new Random((int)Math.random()*100);
        data = new DataArrays<String[]>();
        for(int i=0; i<kitSize; i++){
            array = new String[length];
            for (int j=0; j<array.length; j++){
                array[j] = Integer.toString(random.nextInt(chars));
            }
            data.addToKit(array);
        }

    }

    @Override
    public void sortedStructure() {
        for (int i = 0; i < data.kitSize(); i++){
            Arrays.sort((String[]) data.getFromKit(i));
        }
        data.setState("Sorted");
    }

    @Override
    public DataStructures getStructure() {
        return data;
    }
}
