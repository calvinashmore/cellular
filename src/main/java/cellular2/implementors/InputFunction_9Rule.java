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
public class InputFunction_9Rule implements SimpleIntegratorOperation_d.InputFunction {

    private double v1,  v2,  v3,  v4,  v5,  v6,  v7,  v8,  v9;
    private double base;

    public InputFunction_9Rule(double base, double v1, double v2, double v3, double v4, double v5, double v6, double v7, double v8, double v9) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.v4 = v4;
        this.v5 = v5;
        this.v6 = v6;
        this.v7 = v7;
        this.v8 = v8;
        this.v9 = v9;
        this.base = base;
    }

    public double getContribution(LVect2d position, double value) {

        if (position.x > .25) {
            if (position.y > .25) {
                return value * v1 + base;
            } else if (position.y < -.25) {
                return value * v2 + base;
            } else {
                return value * v3 + base;
            }

        } else if (position.x < -.25) {
            if (position.y > .25) {
                return value * v4 + base;
            } else if (position.y < -.25) {
                return value * v5 + base;
            } else {
                return value * v6 + base;
            }
        } else {
            if (position.y > .25) {
                return value * v7 + base;
            } else if (position.y < -.25) {
                return value * v8 + base;
            } else {
                return value * v9 + base;
            }
        }
    }

    public double getWeight(LVect2d position) {
        return 1;
    }
}
