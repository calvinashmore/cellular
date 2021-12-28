/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cellular2.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import utils.pointfield.Point;
import utils.pointfield.Quadtree;

/**
 * Defines a grid of wells.
 * @author Calvin Ashmore
 */
public class WellGrid<PointType extends Point> {

    private Quadtree<PointType> quadtree; // for quick access of points.
    private List<Well<PointType>> wells;
    private Map<Point, Well<PointType>> wellMap; // used for figuring out the quadtree results.
    private GridTopology topology;

    public WellGrid(WellProducer<PointType> producer, GridTopology<PointType> topology) {

        this.topology = topology;

        wellMap = new HashMap<Point, Well<PointType>>();

        // get the data from the well producer
        wells = producer.produceWells();

        // fill in the map with the data
        // also build the list of points for the quadtree
        // also calculate the minimum and maximum values
        // also assigns IDs to each well.
        int id = 0;
        List<PointType> qtPoints = new ArrayList<PointType>();
        double minx = Double.MAX_VALUE;
        double miny = Double.MAX_VALUE;
        double maxx = -Double.MAX_VALUE;
        double maxy = -Double.MAX_VALUE;
        for (Well<PointType> well : wells) {
            well.setId(id++);

            PointType point = well.getPosition();

            wellMap.put(point, well);
            qtPoints.add(point);

            if (point.x < minx) {
                minx = point.x;
            }
            if (point.y < miny) {
                miny = point.y;
            }
            if (point.x > maxx) {
                maxx = point.x;
            }
            if (point.y > maxy) {
                maxy = point.y;
            }
        }

        quadtree = new Quadtree<PointType>();
        quadtree.setup(minx, maxx, miny, maxy);
        quadtree.setData(qtPoints);
    }

    public GridTopology getTopology() {
        return topology;
    }

    public Well<PointType> getWell(Point point) {
        return wellMap.get(point);
    }

    public Quadtree<PointType> getQuadtree() {
        return quadtree;
    }

    /**
     * returns all the wells in this grid
     * @return
     */
    public List<Well<PointType>> getAllWells() {
        return Collections.unmodifiableList(wells);
    }

    /**
     * Returns the wells in a neighborhood of the given well.
     * @param well
     * @param radius
     * @return
     */
    public List<Well<PointType>> getWellsInNeighborhood(Well<PointType> well, double radius) {
        return getWellsInNeighborhood(well.getPosition().x, well.getPosition().y, radius);
    }

    public double getDistance(Well<PointType> well1, Well<PointType> well2) {
        return topology.distanceBetween(well1, well2, this);
    }

    /**
     * Returns the wells in a neighborhood of the given point.
     * @param x
     * @param y
     * @param radius
     * @return
     */
    public List<Well<PointType>> getWellsInNeighborhood(double x, double y, double radius) {
        return topology.getWellsInNeighborhood(x, y, radius, this);
//        List<PointType> contents = quadtree.getContents(x, y, radius);
//        List<Well<PointType>> results = new ArrayList<Well<PointType>>();
//        for (PointType point : contents) {
//            results.add(wellMap.get(point));
//        }
//        return results;
    }
}
