import algo_files.ImportingModule;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 03.10.13
 * Time: 17:15
 * To change this template use File | Settings | File Templates.
 */
public class SortInsertion implements ImportingModule {

    private int[] array;
    public void load(int[] aInt, float[] aFloat, String[] aString){
        this.array = aInt;
    }
    public int run(){
        sort(array);
        return 0;
    }
    private void swap(int[] array, int left, int right){
        int temp = array[left];
        array[left] = array[right];
        array[right] = temp;
    }
    private void sort(int[] array){
        for(int i = 0; i<array.length-1; i++){
            if(array[i] > array[i+1])
                swap(array, i, i+1);
            int j = i;
            while((j>0)&&(array[j-1]>array[j])){
                swap(array, j-1, j);
                j--;
            }
        }

    }
}
