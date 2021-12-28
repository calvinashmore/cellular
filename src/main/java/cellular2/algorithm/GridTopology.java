/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cellular2.algorithm;

import java.util.List;
import utils.linear.LVect2d;
import utils.pointfield.Point;

/**
 *
 * @author Calvin Ashmore
 */
public interface GridTopology<PointType extends Point> {

    public double distanceBetween(Well<PointType> well1, Well<PointType> well2, WellGrid<PointType> grid);

    public double distanceBetween(Well<PointType> well, double x, double y, WellGrid<PointType> grid);

    public List<Well<PointType>> getWellsInNeighborhood(double x, double y, double radius, WellGrid<PointType> grid);

    /**
     * This looks specifically at the 2d topology, as fits into the quadtree
     * @param well1
     * @param well2
     * @return
     */
    public LVect2d directionTo(Well<PointType> from, Well<PointType> to, WellGrid<PointType> grid);
}
