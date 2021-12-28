/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cellular2.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import utils.pointfield.Point;

/**
 *
 * @author Calvin Ashmore
 */
public class GridData<PointType extends Point, DataType> {

    private Map<Well, DataType> data = new HashMap<Well, DataType>();

    public void setData(Well well, DataType value) {
        data.put(well, value);
    }

    public DataType getData(Well well) {
        return data.get(well);
    }

    public List<DataType> getData(List<Well> wells) {
        List<DataType> returnData = new ArrayList<DataType>();
        for (Well well : wells) {
            returnData.add(data.get(well));
        }
        return returnData;
    }
}
