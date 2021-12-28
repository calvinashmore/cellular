/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cellular2.implementors;

import cellular2.algorithm.Well;
import utils.pointfield.attractor.APoint2d;

/**
 *
 * @author Calvin Ashmore
 */
public class PerturbedWellProducer extends SimpleWellProducer {

    private double perturb;

    public PerturbedWellProducer(int size) {
        super(size);

        perturb = .5 / size;
    }

    @Override
    protected Well<APoint2d> createWell(double x, double y) {

        x += (2 * Math.random() - 1) * perturb;
        y += (2 * Math.random() - 1) * perturb;

        return super.createWell(x, y);
    }
}
