/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cellular2.implementors;

import cellular2.algorithm.Well;
import cellular2.algorithm.WellProducer;
import java.util.ArrayList;
import java.util.List;
import utils.pointfield.attractor.APoint2d;

/**
 * This produces wells in a grid, with LDouble contents. 
 * @author Calvin Ashmore
 */
public class SimpleWellProducer implements WellProducer<APoint2d> {

    private int size;

    /**
     * create a new simple well producer with the given size. The size must be greater than 1.
     * @param size
     */
    public SimpleWellProducer(int size) {
        if (size <= 1) {
            throw new IllegalArgumentException("size must be greater than 1");
        }
        this.size = size;
    }

    public List<Well<APoint2d>> produceWells() {
        List<Well<APoint2d>> r = new ArrayList<Well<APoint2d>>();

        for (int ix = 0; ix < size; ix++) {
            for (int iy = 0; iy < size; iy++) {
                double x = (double) ix / (size - 1);
                double y = (double) iy / (size - 1);

                r.add(createWell(x, y));
            }
        }

        return r;
    }

    protected Well<APoint2d> createWell(double x, double y) {
        APoint2d point = new APoint2d();
        point.x = x;
        point.y = y;

        double radius = 1.0 / size;
        //double value = Math.random();

        //return new Well<APoint2d, LDouble>(point, radius, new LDouble(value));
        return new Well<APoint2d>(point, radius);
    }
}
