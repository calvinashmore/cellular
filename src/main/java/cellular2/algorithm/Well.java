/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cellular2.algorithm;

import utils.linear.Linear;
import utils.pointfield.Point;

/**
 * This is a "well" which contains a chemical or pigment.
 * The type parameter PointType denotes the type of point in space that the well has.
 * For points where there is overlap or some thickness, different types of APoint will want to be used.
 * This specifies the location only of the well, it does not specify the contents.
 * @author Calvin Ashmore
 */
public class Well<PointType extends Point/*, DataType extends Linear<DataType>*/> {

    private PointType position; // position on the grid
    private double radius; // the well's radius in grid units
    private int id;
    //private DataType contents; // the well's contents

    /**
     * @param id
     * @param position
     * @param radius
     */
    public Well(PointType position, double radius) {//, DataType initialContents) {
        //this.id = id;
        this.position = position;
        this.radius = radius;
    }

    int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    //public DataType getContents() {
    //    return contents;
    //}
    public PointType getPosition() {
        return position;
    }

    public double getRadius() {
        return radius;
    }

    //public void setContents(DataType contents) {
    //    this.contents = contents;
    //}
}
