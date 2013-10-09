
import algo_files.ImportingModule;


public class SortQuickMedianThree implements ImportingModule
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

    public void qSortMedianThree(int[] array, int left, int right){
    if((right-left)<=0)
      return;
    if((right-left)==1){
      if(array[left]>array[right])
        swap(array, left, right);
    }else{
      int median = medianOf3(array, left, right);
      int part = partIt(array, left, right, median);  
      qSortMedianThree(array, left, part-1);
      qSortMedianThree(array, part+1, right);
    }
  }
  
  private int medianOf3(int[] array, int left, int right){
    int center = (left+right)/2;      
    if( array[left] > array[center] )
      swap(array,left, center); 
    if( array[left] > array[right] )
      swap(array,left, right);
    if( array[center] > array[right] )
      swap(array, center, right);
    swap(array, center, right-1);
    return array[right-1];
  }
  
  private int partIt(int[] a,int left, int right, int pivot){
    int leftPtr = left;
    int rightPtr = right - 1;
    while(true){
      while(a[++leftPtr] < pivot);
      while(a[--rightPtr] > pivot);
      if(leftPtr >= rightPtr)         
        break;
      else
        swap(a, leftPtr, rightPtr); 
    }
    swap(a, leftPtr, right-1); 
    return leftPtr; 
  }  
    public int run()
    {
        qSortMedianThree(aInt, 0, aInt.length-1);
        return 0;
    }
}
