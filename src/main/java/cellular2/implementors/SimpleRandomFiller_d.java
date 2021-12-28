/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cellular2.implementors;

import cellular2.algorithm.DataFiller;
import cellular2.algorithm.Well;
import utils.linear.LDouble;
import utils.pointfield.Point;

/**
 * This fills the data with random LDoubles between 0 and 1.
 * @author Calvin Ashmore
 */
public class SimpleRandomFiller_d<PointType extends Point> implements DataFiller<PointType, LDouble> {

    public LDouble createData(Well<PointType> well) {
        return new LDouble(Math.random());
    }
}
