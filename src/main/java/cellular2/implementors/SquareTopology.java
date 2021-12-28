/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cellular2.implementors;

import cellular2.algorithm.GridTopology;
import cellular2.algorithm.Well;
import cellular2.algorithm.WellGrid;
import java.util.ArrayList;
import java.util.List;
import utils.linear.LVect2d;
import utils.pointfield.Point;

/**
 *
 * @author Calvin Ashmore
 */
public class SquareTopology<PointType extends Point> implements GridTopology<PointType> {

    public double distanceBetween(Well<PointType> well1, Well<PointType> well2, WellGrid<PointType> grid) {
        return distanceBetween(well1, well2.getPosition().x, well2.getPosition().y, grid);
    }

    public double distanceBetween(Well<PointType> well, double x, double y, WellGrid<PointType> grid) {
        return Math.sqrt(well.getPosition().distanceSquared(x, y));
    }

    public List<Well<PointType>> getWellsInNeighborhood(double x, double y, double radius, WellGrid<PointType> grid) {
        List<PointType> contents = grid.getQuadtree().getContents(x, y, radius);
        List<Well<PointType>> results = new ArrayList<Well<PointType>>();
        for (PointType point : contents) {
            results.add(grid.getWell(point));
        }
        return results;
    }

    public LVect2d directionTo(Well<PointType> from, Well<PointType> to, WellGrid<PointType> grid) {
        double dx = to.getPosition().x - from.getPosition().x;
        double dy = to.getPosition().y - from.getPosition().y;
        return new LVect2d(dx, dy);
    }
}
