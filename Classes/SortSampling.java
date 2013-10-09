
import algo_files.ImportingModule;


public class SortSampling implements ImportingModule
{
    private int[] aInt;

    public void load(int[] aInt, float[] aFloat, String[] aString ){
        this.aInt = aInt;
    }


    private void swap(int[] array, int left, int right){
        int temp = array[left];
        array[left] = array[right];
        array[right] = temp;
}
    public void sortSampling(int array[])  {
        int min;
        for(int k=0; k<array.length; k++) {
            min = k;
            for(int i=k+1; i<array.length; i++)
                if (array[i] < array[min])  
                    min = i;
            swap(array, k, min);
        }
    }
    public int run()
    {
        sortSampling(aInt);
        return 0;
    }
}
