/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cellular2.renderer;

import cellular2.algorithm.GridData;
import cellular2.algorithm.Well;
import cellular2.algorithm.WellGrid;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import utils.linear.LVect2i;
import utils.pointfield.Point;

/**
 * Renders a GridData from a WellGrid
 * @author Calvin Ashmore
 */
abstract public class GridRenderer<PointType extends Point, DataType> {

    private WellGrid<PointType> grid;
    private int resolution = 500;

    public GridRenderer(WellGrid<PointType> grid) {
        this.grid = grid;
    }

    public void setResolution(int resolution) {
        this.resolution = resolution;
    }

    public WellGrid<PointType> getGrid() {
        return grid;
    }

    public int getResolution() {
        return resolution;
    }

    /**
     * Produces an image from the data given.
     * @param data
     * @return
     */
    public BufferedImage render(GridData<PointType, DataType> data) {
        BufferedImage image = new BufferedImage(resolution, resolution, BufferedImage.TYPE_INT_ARGB);

        Graphics2D graphics = image.createGraphics();
        renderBackground(graphics);

        for (Well<PointType> well : grid.getAllWells()) {
            renderWell(graphics, well, data.getData(well));
        }

        image = postFilter(image);

        return image;
    }

    protected LVect2i getCoordinates(Well<PointType> well) {
        double wellX = well.getPosition().x;
        double wellY = well.getPosition().y;

        double maxX = grid.getQuadtree().getMaxX();
        double minX = grid.getQuadtree().getMinX();
        double maxY = grid.getQuadtree().getMaxY();
        double minY = grid.getQuadtree().getMinY();

        double scaledX = (wellX - minX) / (maxX - minX);
        double scaledY = (wellY - minY) / (maxY - minY);
        int intX = (int) (scaledX * resolution);
        int intY = (int) (scaledY * resolution);

        return new LVect2i(intX, intY);
    }

    abstract void renderBackground(Graphics2D g);

    abstract void renderWell(Graphics2D g, Well<PointType> well, DataType data);

    abstract BufferedImage postFilter(BufferedImage image);
}
