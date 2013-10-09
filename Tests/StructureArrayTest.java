import algo_arrays.DataStructure;
import algo_arrays.StructureArray;
import algo_general.Main;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 30.09.13
 * Time: 13:54
 * To change this template use File | Settings | File Templates.
 */
public class StructureArrayTest {
    DataStructure array;
    Integer[][] intArray = {{1,1,1}, {2,2,2}, {3,3,3}, {4,4,4}, {4,4,4}};
    @Before
    public void init(){
        array = new StructureArray(intArray);
        array.setTypeData("Integer");
    }
    @Test
    public void testGetTypeData(){
        assertEquals("Integer", array.getTypeData());

    };
    @Test
    public void testGetState(){
        assertEquals("Unsorted", array.getState());
    }
    @Test
    public void testGetLength(){
        assertTrue(3 == intArray[0].length );
    }

}
