/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cellular2.algorithm;

import utils.pointfield.Point;

/**
 * Defines an operation that takes place over the grid.
 * @author Calvin Ashmore
 */
public interface GridOperation<PointType extends Point, DataType> {

    public DataType getNewValue(Well<PointType> well, WellGrid<PointType> grid, GridData<PointType, DataType> oldData);
}
