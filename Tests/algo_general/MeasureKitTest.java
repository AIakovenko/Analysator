package algo_general;

import algo_arrays.DataArrays;
import algo_arrays.DataStructures;
import org.junit.*;

import java.util.Arrays;

/**
 * @autor Alex Iakovenko
 * Date: 11/15/13
 * Time: 8:32 PM
 */
public class MeasureKitTest {
    DataCalc calc;
    DataStructures inData;
    DataStructures outData;
    int numberOfPoints = 5;


    @Before
    public void setUp(){

        inData = new DataArrays<Integer>();
        numberOfPoints = 5;
        for(int i = 0; i < numberOfPoints; i++){
            Integer[] array = new Integer[10];
            Arrays.fill(array, i);
            inData.addToKit(array);
        }

    }
    @Test
    public void testCreateObject(){
        calc = new DataCalc(inData, numberOfPoints);
        calc.createDataSet(0);
        outData = calc.getStructureIn();
        Assert.assertNotNull(outData);
    }
    @Test
    public void testKitSize(){
        int size = 5;
        calc = new DataCalc(inData, numberOfPoints);
        calc.createDataSet(0);
        outData = calc.getStructureIn();
        Assert.assertTrue(outData.kitSize() == size);
    }
    @Test
    public void testLengthLast(){
        int len = 10;
        calc = new DataCalc(inData, numberOfPoints);
        calc.createDataSet(0);
        outData = calc.getStructureIn();
        Assert.assertTrue(outData.getLength() == len);
    }
    @Test
    public void testLengthFirst(){
        int len = 2;
        calc = new DataCalc(inData, numberOfPoints);
        calc.createDataSet(0);
        outData = calc.getStructureIn();
        Assert.assertTrue(outData.getLength(0) == len);
    }
}
