
import algo_files.ImportingModule;


public class SortQuickTreePart implements ImportingModule
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

    public  void qSortTreePart(int array[], int left, int right)  {
        if (right <= left) return;
        int pivot = array[right];
        int l = left-1, r = right,  p = left-1,  q = right;
        while(true) {
               while (array[++l] < pivot);
               while (array[--r] > pivot)
                   if (r == left)   break;
               if (l >= r)    break;
               swap(array, l, r);
               if (array[l] == pivot) {
                   p++;
                   swap(array, p, l);   
               }
               if (array[r] == pivot) {
                   q--;   
                   swap(array, q, r);
               }
        }
        swap(array, l, right);
        r = l-1;      
        l = l+1;
        for (int n=left; n<=p; n++, r--) 
            swap(array, n, r);
        
        for (int n=right-1; n>=q; n--, l++) 
            swap(array,n,l);
        
        qSortTreePart(array, left, r);
        qSortTreePart(array, l, right);
} 
    public int run()
    {
        qSortTreePart(aInt, 0, aInt.length-1);
        return 0;
    }
}
