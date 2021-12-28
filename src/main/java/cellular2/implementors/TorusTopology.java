/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cellular2.implementors;

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
public class TorusTopology<PointType extends Point> extends SquareTopology<PointType> {

    @Override
    public double distanceBetween(Well<PointType> well, double x, double y, WellGrid<PointType> grid) {
        double minDistance = Double.MAX_VALUE;

        double xShift = grid.getQuadtree().getMaxX() - grid.getQuadtree().getMinX();
        double yShift = grid.getQuadtree().getMaxY() - grid.getQuadtree().getMinY();

        for (int ix = -1; ix <= 1; ix++) {
            for (int iy = -1; iy <= 1; iy++) {
                double dist = super.distanceBetween(well, x + xShift * ix, y + yShift * iy, grid);
                if (dist < minDistance) {
                    minDistance = dist;
                }
            }
        }

        return minDistance;
    }

    @Override
    public List<Well<PointType>> getWellsInNeighborhood(double x, double y, double radius, WellGrid<PointType> grid) {

        List<Well<PointType>> results = new ArrayList<Well<PointType>>();

        double xShift = grid.getQuadtree().getMaxX() - grid.getQuadtree().getMinX();
        double yShift = grid.getQuadtree().getMaxY() - grid.getQuadtree().getMinY();


        for (int ix = -1; ix <= 1; ix++) {
            for (int iy = -1; iy <= 1; iy++) {
                List<Well<PointType>> neighborhood = super.getWellsInNeighborhood(x + xShift * ix, y + yShift * iy, radius, grid);

                for (Well<PointType> well : neighborhood) {
                    if (!results.contains(well)) {
                        results.add(well);
                    }
                }
            }
        }

        return results;
    }

    @Override
    public LVect2d directionTo(Well<PointType> from, Well<PointType> to, WellGrid<PointType> grid) {
        double minDistance = Double.MAX_VALUE;
        LVect2d minDirection = null;

        double xShift = grid.getQuadtree().getMaxX() - grid.getQuadtree().getMinX();
        double yShift = grid.getQuadtree().getMaxY() - grid.getQuadtree().getMinY();

        for (int ix = -1; ix <= 1; ix++) {
            for (int iy = -1; iy <= 1; iy++) {

                double dx = to.getPosition().x - from.getPosition().x - xShift * ix;
                double dy = to.getPosition().y - from.getPosition().y - yShift * iy;

                double dsquared = dx * dx + dy * dy;
                if (dsquared < minDistance) {
                    minDistance = dsquared;

                    minDirection = new LVect2d(dx, dy);
                }
            }
        }

        return minDirection;
    }
}
