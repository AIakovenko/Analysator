/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 03.10.13
 * Time: 17:49
 * To change this template use File | Settings | File Templates.
 */
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class SortInsertionTest {
    SortInsertion sortInsertion;
    int[] array = {3,2,5,4,6,7,8,1,9,0};
    int[] arrayActuals = {0,1,2,3,4,5,6,7,8,9};

    @Before
    public void init(){
        sortInsertion = new SortInsertion();

    }
    @Test
    public void testBubbleSort(){
        sortInsertion.load(array,null,null);
        sortInsertion.run();
        assertArrayEquals(array, arrayActuals);
    }
}
