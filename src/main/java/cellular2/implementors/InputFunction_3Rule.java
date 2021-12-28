/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cellular2.implementors;

import utils.linear.LVect2d;

/**
 *
 * @author Calvin Ashmore
 */
public class InputFunction_3Rule implements SimpleIntegratorOperation_d.InputFunction {

    private double base,  left,  middle,  right;

    public InputFunction_3Rule(double base, double left, double middle, double right) {
        this.base = base;
        this.left = left;
        this.middle = middle;
        this.right = right;
    }

    public double getContribution(LVect2d position, double value) {
        if (position.y <= 0) {
            return 0;
        }

        if (position.x < .25) {
            return base + left * value;
        }

        if (position.x > .25) {
            return base + right * value;
        }

        return base + middle * value;
    }

    public double getWeight(LVect2d position) {
        if (position.y <= 0) {
            return 0;
        }
        return 1;
    }
}
