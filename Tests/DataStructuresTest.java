import algo_arrays.DataArrays;
import algo_arrays.DataStructures;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * @autor Alex Iakovenko
 * Date: 11/14/13
 * Time: 5:42 PM
 */
public class DataStructuresTest {
    DataStructures d;
    @Before
    public void setUp(){
        d = new DataArrays<int[]>();
        for(int i = 0; i<5; i++){
            int[] a = new int[10];
            Arrays.fill(a, i);
            d.addToKit(a);
        }
    }
    @Test
    public void containTest(){
        int[] b = new int[10];
        Arrays.fill(b,0);
        Assert.assertArrayEquals((int[])d.getFromKit(0),b);
    }
    @Test
    public void typeTest(){
        Object o = new int[0];
        Assert.assertTrue(o.getClass() == d.getFromKit(0).getClass());
    }
}
