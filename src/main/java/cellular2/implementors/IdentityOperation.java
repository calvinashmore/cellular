/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cellular2.implementors;

import cellular2.algorithm.GridData;
import cellular2.algorithm.GridOperation;
import cellular2.algorithm.Well;
import cellular2.algorithm.WellGrid;
import utils.pointfield.Point;

/**
 *
 * @author Calvin Ashmore
 */
public class IdentityOperation<DataType, PointType extends Point> implements GridOperation<PointType, DataType> {

    public DataType getNewValue(Well<PointType> well, WellGrid<PointType> grid, GridData<PointType, DataType> oldData) {
        return oldData.getData(well);
    }
}
