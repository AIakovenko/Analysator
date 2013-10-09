
/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 03.10.13
 * Time: 16:44
 * To change this template use File | Settings | File Templates.
 */
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class SortSelectionTest {
    SortSelection sortSelection;
    int[] array = {3,2,5,4,6,7,8,1,9,0};
    int[] arrayActuals = {0,1,2,3,4,5,6,7,8,9};

    @Before
    public void init(){
        sortSelection = new SortSelection();

    }
    @Test
    public void testBubbleSort(){
        sortSelection.load(array,null,null);
        sortSelection.run();
        assertArrayEquals(array, arrayActuals);
    }

}
