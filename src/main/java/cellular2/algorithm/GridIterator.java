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
public class GridIterator<PointType extends Point, DataType> {

    private WellGrid<PointType> grid;

    public GridIterator(WellGrid<PointType> grid) {
        this.grid = grid;
    }

    public GridData<PointType, DataType> createInitialData(DataFiller<PointType, DataType> filler) {
        GridData<PointType, DataType> data = new GridData<PointType, DataType>();
        for (Well<PointType> well : grid.getAllWells()) {
            data.setData(well, filler.createData(well));
        }
        return data;
    }

    /**
     * dataIn must be filled, but dataOut may be empty.
     * @param dataOut
     * @param dataIn
     */
    public void iterateData(GridOperation<PointType, DataType> op, GridData<PointType, DataType> dataOut, GridData<PointType, DataType> dataIn) {
        for (Well<PointType> well : grid.getAllWells()) {
            DataType newValue = op.getNewValue(well, grid, dataIn);
            dataOut.setData(well, newValue);
        }
    }
}
