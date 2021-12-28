/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cellular2.implementors;

import cellular2.algorithm.GridData;
import cellular2.algorithm.GridOperation;
import cellular2.algorithm.Well;
import cellular2.algorithm.WellGrid;
import java.util.List;
import utils.linear.LDouble;
import utils.pointfield.Point;

/**
 *
 * @author Calvin Ashmore
 */
public class DiffuserOperation_d<PointType extends Point> implements GridOperation<PointType, LDouble> {

    private double diffusionRadius;

    public DiffuserOperation_d(double radius) {
        diffusionRadius = radius;
    }

    public LDouble getNewValue(Well<PointType> well, WellGrid<PointType> grid, GridData<PointType, LDouble> oldData) {

        double currentValue = oldData.getData(well).val;

        // calculate the neighbor average
        double neighborAverage = 0;
        List<Well<PointType>> wellsInNeighborhood = grid.getWellsInNeighborhood(well, diffusionRadius);
        for (Well<PointType> neighbor : wellsInNeighborhood) {
            neighborAverage += oldData.getData(neighbor).val;
        }
        neighborAverage /= wellsInNeighborhood.size();

        double result;

        if (neighborAverage > currentValue) {
            // neighbor average is greater than current value
            if (neighborAverage - currentValue < .2) {
                // but difference is small, return alone
                result = currentValue;
            } else {
                // if difference is larger, raise the current value
                result = currentValue + .1 * (neighborAverage - currentValue) + .1;
            }
        } else {
            // neighbor average is less than current value
            if (currentValue - neighborAverage < .2) {
                // if difference is small, lower the current value a little
                result = currentValue - Math.min(.3, .05 * (currentValue - neighborAverage));

                if (currentValue < .3) {
                    // add a little energy
                    result += .1;
                }
            } else {
                // difference is large, lower the current value, has chance to rebound
                result = currentValue - .3;
            }
        }

        return new LDouble(result);
    }
}
