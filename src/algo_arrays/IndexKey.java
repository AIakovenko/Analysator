package algo_arrays;


import java.util.Arrays;
import java.util.Random;


/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 14.08.13
 * Time: 17:20
 * To change this template use File | Settings | File Templates.
 */
public class IndexKey {

    private int [][] indexes;
    private ArraysData data;
    public IndexKey(int numberOfPoints, ArraysData data){
        indexes = new int [numberOfPoints][];
        this.data = data;
        setIndex();
    }

    private void setIndex(){
        int size= 0;
        for (int i = 0; i<indexes.length; i++){
            size += data.getDataLength()/indexes.length;
            indexes[i] = new int [size];
            for (int j = 0; j<size; j++){
                indexes[i][j] = j;
            }
            shuffleArray(indexes[i]);

        }

    }
    private void shuffleArray(int[] array){
        int n = array.length;
        Random random = new Random();
        random.nextInt();
        for (int i = 0; i < n; i++) {
            int change = i + random.nextInt(n - i);
            swap(array, i, change);
        }
    }
    private void swap(int[] a, int i, int change) {
        int helper = a[i];
        a[i] = a[change];
        a[change] = helper;
    }
}
