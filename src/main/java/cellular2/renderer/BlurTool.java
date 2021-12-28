/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cellular2.renderer;

import utils.linear.LDouble;
import utils.linear.grid.Buffer_d;

/**
 *
 * @author Calvin Ashmore
 */
public class BlurTool {

    /**
     * Method assumes a square grid! Scales to newResolution using square scaling.
     * Creates a new buffer.
     * @param buffer
     * @param newResolution
     * @return
     */
    public static Buffer_d scaleBuffer(Buffer_d buffer, int newResolution) {
        int oldResolution = buffer.getXRes();
        Buffer_d newBuffer = new Buffer_d(newResolution, newResolution);
        for (int x = 0; x < newResolution; x++) {
            for (int y = 0; y < newResolution; y++) {
                int xOld = x * oldResolution / newResolution;
                int yOld = y * oldResolution / newResolution;
                LDouble value = buffer.getValue(xOld, yOld);
                newBuffer.setValue(x, y, value);
            }
        }
        return newBuffer;
    }

    public BlurTool(double sharpness) {
        this.sharpness = sharpness;
    }
    private double sharpness;

    public Buffer_d process(Buffer_d gridIn, Buffer_d gridOut, int steps) {

        for (int step = 0; step < steps; step+=2) {
            processHelper(gridIn, gridOut);
            if(step+1<steps)
                processHelper(gridOut, gridIn);
        }
        
        if(steps % 2==0)
            return gridIn;
        else return gridOut;
    }

    private void processHelper(Buffer_d gridIn, Buffer_d gridOut) {

        int resolution = gridIn.getXRes();

        for (int x = 0; x < resolution; x++) {
            for (int y = 0; y < resolution; y++) {
                LDouble value = gridIn.pointAverage(x, y, 4);
                value.val = transform(value.val);
                gridOut.setValue(x, y, value);
            }
        }
    }

    private double transform(double val) {
        // use squashing function: 0-1
        val = 2 * val - 1; // scale from -1 to 1
        val = Math.signum(val) * Math.pow(Math.abs(val), 1.0 / sharpness);

        val = Math.sin((Math.PI / 2) * val);
        val = (1 + val) / 2; // scale back
        return val;
    }
}
