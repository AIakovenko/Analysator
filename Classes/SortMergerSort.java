import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 10/18/13
 * Time: 9:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class SortMergerSort {
    private int[] firstArray;
    private int[] secondArray;
    private int[] target = null;
    public void load(List<int[]> list){
        int size = 0;
        for(int[] t : list){
            size +=t.length;
        }
        target = new int[size];
        firstArray = list.get(0);
        secondArray = list.get(1);

    }
    public void run(){
        sort();
    }
    public int[] getSortedTarget(){
        return target;
    }

    private void sort(){
        int indexFirstArray = 0;
        int indexSecondArray = 0;
        for(int i = 0; i<target.length; i++){

            if(indexFirstArray<firstArray.length && indexSecondArray<secondArray.length
                    &&(firstArray[indexFirstArray] < secondArray[indexSecondArray]))
                target[i] = firstArray[indexFirstArray++];
            else
            if(indexSecondArray<secondArray.length && indexFirstArray<firstArray.length
                    &&(firstArray[indexFirstArray] > secondArray[indexSecondArray]))
                target[i] = secondArray[indexSecondArray++];
            else
                if(indexFirstArray>=firstArray.length)
                    target[i] = secondArray[indexSecondArray++];
                else
                    if(indexSecondArray>=secondArray.length)
                        target[i] = firstArray[indexFirstArray++];


        }
    }
}
