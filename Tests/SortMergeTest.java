/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 10/18/13
 * Time: 9:44 AM
 * To change this template use File | Settings | File Templates.
 */
import static org.junit.Assert.*;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

public class SortMergeTest {
    SortMergerSort sortMergerSort;
    @Test
    public void testSortMerge(){
        sortMergerSort = new SortMergerSort();
        List<int[]> arrays = new ArrayList<int[]>();
        int[] firstArray = {1,3,5,7,9,11};
        int[] secondArray = {2,4,6,8,10,12,14};
        arrays.add(firstArray);
        arrays.add(secondArray);

        int[] mergerArray = {1,2,3,4,5,6,7,8,9,10,11,12,14};
        sortMergerSort.load(arrays);
        sortMergerSort.run();
        int[] sortedArray = sortMergerSort.getSortedTarget();
        assertArrayEquals(sortedArray, mergerArray);
    }
}
