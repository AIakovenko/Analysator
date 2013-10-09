
import algo_files.ImportingModule;


public class SortQuickBase implements ImportingModule
{
    private int[] aInt;

    public void load(int[] aInt, float[] aFloat, String[] aString ){
        this.aInt = aInt;
    }
    public int run()
    {
        qSortBase(aInt, 0, aInt.length-1);
        return 0;
    }


    private void swap(int[] array, int left, int right){
        int temp = array[left];
        array[left] = array[right];
        array[right] = temp;
    }

    private  void qSortBase(int array[], int left, int right){
        if(left>=right)
            return;
        int l = left, r = right;
        int pivot = array[r];
        do{
            while(array[l]<pivot)
                l++;            
            while(pivot<array[r])
                r--;
            if(l<=r){
                swap(array,l,r);
                l++; r--;
            }   
        }while(l<r);
        qSortBase(array, left, r);
        qSortBase(array, l, right);
    }

}
