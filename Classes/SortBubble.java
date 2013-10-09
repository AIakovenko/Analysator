import algo_files.ImportingModule;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 03.10.13
 * Time: 12:31
 * To change this template use File | Settings | File Templates.
 */
public class SortBubble implements ImportingModule {
    private int[] aInt;
    private float[] aFloat;
    private String[] aString;

    public void load(int[] aInt, float[] aFloat, String[] aString ){
        this.aInt = aInt;
        this.aFloat = aFloat;
        this.aString = aString;
    }

    public int run(){
        sort(aInt);
        return 0;
    }
    private void swap(int[] array, int left, int right){
        int temp = array[left];
        array[left] = array[right];
        array[right] = temp;
    }
    private void sort(int[] array){
        for (int i = array.length - 1; i>-1; i--){
            for(int j = 1; j<=i; j++){
                if(array[j-1]>array[j])
                    swap(array, j-1, j);
            }

        }

    }

}
