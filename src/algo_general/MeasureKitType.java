package algo_general;

import algo_arrays.DataStructures;

/**
 * Declares interface for concrete classes which describe measure kits (data structures divide on
 * some different length parts).
 *
 * @autor Alex Iakovenko
 * Date: 11/15/13
 * Time: 7:54 PM
 */
public interface MeasureKitType {
    /**
     * Divides element (for example array) in some parts with different length.
     * @param numberOfPoints sets number of parts
     * @param elementIndex index of element in kit
     */
    public void createFrom(int elementIndex, int numberOfPoints);

    /**
     * Shuffles data in each part
     */
    public void shuffle();

    public DataStructures getKit();
}
