/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cellular2.renderer;

import cellular2.algorithm.WellGrid;
import java.awt.image.BufferedImage;
import utils.linear.Color;
import utils.linear.LDouble;
import utils.linear.grid.Buffer_d;
import utils.linear.grid.ColorGrid;
import utils.pointfield.attractor.APoint2d;

/**
 *
 * @author Calvin Ashmore
 */
public class BlurryRenderer_d extends SimpleRenderer_d {

    private double sharpness = 1.0;

    public BlurryRenderer_d(WellGrid<APoint2d> grid) {
        super(grid);
    }

    @Override
    protected BufferedImage postFilter(BufferedImage image) {
        Buffer_d bufferIn = makeBuffer(image);
        Buffer_d bufferOut = new TorusBuffer_d(image.getWidth(), image.getHeight());
        Buffer_d result = new BlurTool(sharpness).process(bufferIn, bufferOut, 1);
        return flesh(result).makeImage();
    }

    private Buffer_d makeBuffer(BufferedImage image) {
        Buffer_d buffer = new TorusBuffer_d(image.getWidth(), image.getHeight());

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                double value = (double) (image.getRGB(x, y) & 0xff) / 255;
                buffer.setValue(x, y, new LDouble(value));
            }
        }

        return buffer;
    }

    private ColorGrid flesh(Buffer_d data) {

        ColorGrid cg = new ColorGrid(data.getXRes(), data.getYRes());

        for (int x = 0; x < data.getXRes(); x++) {
            for (int y = 0; y < data.getYRes(); y++) {
                double d = 1 - data.getValue(x, y).val;
                cg.setValue(x, y, new Color(d, d, d));
            }
        }

        return cg;
    }

    private class TorusBuffer_d extends Buffer_d {

        public TorusBuffer_d(int xres, int yres) {
            super(xres, yres);
        }

        @Override
        public LDouble getValue(int x, int y) {
            if (x < 0) {
                x += xres;
            }
            if (x >= xres) {
                x -= xres;
            }
            if (y < 0) {
                y += yres;
            }
            if (y >= yres) {
                y -= yres;
            }

            return super.getValue(x, y);
        }

        @Override
        public LDouble pointAverage(int x, int y, int rx, int ry) {
            //return super.pointAverage(x, y, rx, ry);

            int count = 0;
            LDouble avg = new LDouble();
            for (int xt = x - rx; xt <= x + rx; xt++) {
                for (int yt = y - ry; yt <= y + ry; yt++) {

                    avg.addv(getValue(xt, yt));
                    count++;
                }
            }
            avg.multv(1.0 / count);
            return avg;
        }
    }
}
