/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cellular2.algorithm;

import java.util.List;
import utils.pointfield.Point;

/**
 * This is meant to be implemented to create new and interesting types of well grids.
 * @author Calvin Ashmore
 */
public interface WellProducer<PointType extends Point> {

    /**
     * Create all of the wells for a grid.
     * @return
     */
    public List<Well<PointType>> produceWells();
}
