/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cellular2.renderer;

import cellular2.algorithm.Well;
import cellular2.algorithm.WellGrid;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import utils.linear.LDouble;
import utils.linear.LVect2i;
import utils.pointfield.attractor.APoint2d;

/**
 *
 * @author Calvin Ashmore
 */
public class SimpleRenderer_d extends GridRenderer<APoint2d, LDouble> {

    public SimpleRenderer_d(WellGrid<APoint2d> grid) {
        super(grid);
    }
    private Color backgroundColor = Color.WHITE;

    @Override
    protected void renderBackground(Graphics2D g) {
        g.setBackground(backgroundColor);
        g.fillRect(0, 0, getResolution(), getResolution());
    }

    @Override
    protected void renderWell(Graphics2D g, Well<APoint2d> well, LDouble data) {

        float value = (float) Math.min(1, Math.max(0, data.val));
        Color fillColor = new Color(value, value, value);
        LVect2i coordinates = getCoordinates(well);

        int radius = (int) (well.getRadius() * getResolution());

        g.setBackground(fillColor);
        g.setColor(fillColor);
        g.fillOval(coordinates.x - radius, coordinates.y - radius, 2 * radius, 2 * radius);
    }

    @Override
    protected BufferedImage postFilter(BufferedImage image) {
        return image;
    }
}
