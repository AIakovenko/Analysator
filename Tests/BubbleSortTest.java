import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 03.10.13
 * Time: 12:33
 * To change this template use File | Settings | File Templates.
 */
public class BubbleSortTest {
    SortBubble bubbleSort;
    int[] array = {3,2,5,4,6,7,8,1,9,0};
    int[] arrayActuals = {0,1,2,3,4,5,6,7,8,9};

    @Before
    public void init(){
        bubbleSort = new SortBubble();

    }
    @Test
    public void testBubbleSort(){
        bubbleSort.load(array,null,null);
        bubbleSort.run();
        assertArrayEquals(array, arrayActuals);
    }

}
