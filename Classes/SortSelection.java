import algo_files.*;
/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 03.10.13
 * Time: 16:25
 * To change this template use File | Settings | File Templates.
 */
public class SortSelection implements ImportingModule {
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
        for(int i = 1; i<array.length; i++ ){
            int minInd = i-1;
            for(int j = i; j<array.length; j++){
                if(array[minInd] > array[j]){
                    minInd = j;
                }
            }
            swap(array, minInd, i-1 );
        }
    }

}
