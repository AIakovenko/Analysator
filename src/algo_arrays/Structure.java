package algo_arrays;

/**
 * This abstract class describes interface a kit of Object arrays;
 * Also elements in the kit can be sorted.

 * @autor Alex Iakovenko
 * Date: 11/13/13
 * Time: 9:37 AM
 */
public abstract class Structure {

    protected DataStructures data;
    protected int kitSize;
    protected int length;

    public abstract void sortedStructure();
    public abstract DataStructures getStructure();


}
