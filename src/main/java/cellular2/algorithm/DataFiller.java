/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cellular2.algorithm;

import utils.pointfield.Point;

/**
 *
 * @author Calvin Ashmore
 */
public interface DataFiller<PointType extends Point, DataType> {

    /**
     * This should fill all of the wells in the grid data with some initial values
     * @param data
     * @param grid
     */
    public DataType createData(Well<PointType> well);
            //fillData(GridData<PointType, DataType> data, WellGrid<PointType> grid);
}
