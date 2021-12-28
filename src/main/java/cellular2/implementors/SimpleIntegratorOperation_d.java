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
import utils.linear.LVect2d;
import utils.pointfield.Point;

/**
 *
 * @author Calvin Ashmore
 */
public class SimpleIntegratorOperation_d<PointType extends Point> implements GridOperation<PointType, LDouble> {

    public interface InputFunction {

        public double getContribution(LVect2d position, double value);

        public double getWeight(LVect2d position);
    }
    private static double changeRate = .5;//0.1;
    private double radius;
    private InputFunction inputFunction;

    public SimpleIntegratorOperation_d(double radius, InputFunction inputFunction) {
        this.radius = radius;
        this.inputFunction = inputFunction;
    }

    public LDouble getNewValue(Well<PointType> well, WellGrid<PointType> grid, GridData<PointType, LDouble> oldData) {

        double currentValue = oldData.getData(well).val;

        List<Well<PointType>> wellsInNeighborhood = grid.getWellsInNeighborhood(well, radius);

        double input = 0;
        double totalWeight = 0;

        for (Well<PointType> well1 : wellsInNeighborhood) {
            LVect2d direction = grid.getTopology().directionTo(well, well1, grid);
            direction.multv(1 / radius);
            input += inputFunction.getContribution(direction, oldData.getData(well1).val);
            totalWeight += inputFunction.getWeight(direction);
        }

        double newValue = currentValue;

        if (totalWeight > 0) {
            newValue = currentValue * (1 - changeRate) + input * changeRate / totalWeight;
        }

        // what happens if we apply a squash?

        //double scale = 10;
        //double squash = 5;
        //double v1 = 2 * newValue * scale - 1 * scale;
        //newValue = .5 * v1 / (squash + Math.abs(v1)) + .5;

        return new LDouble(newValue);
    }
}
