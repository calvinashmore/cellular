/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cellular2.implementors;

import cellular2.algorithm.GridData;
import cellular2.algorithm.GridOperation;
import cellular2.algorithm.Well;
import cellular2.algorithm.WellGrid;
import utils.linear.LDouble;
import utils.pointfield.Point;

/**
 *
 * @author Calvin Ashmore
 */
public class EntropyOperation_d<PointType extends Point> implements GridOperation<PointType, LDouble> {

    private double force = .8;
    //private double wiggleRoom = .3;

    public LDouble getNewValue(Well<PointType> well, WellGrid<PointType> grid, GridData<PointType, LDouble> oldData) {

        double currentValue = oldData.getData(well).val;

        double deviance = currentValue - .5;

        double adjustment = -deviance * Math.abs(deviance) * force;

        double result = currentValue + adjustment;
        result = Math.max(Math.min(result, 1), 0);
        return new LDouble(result);
    }
}
