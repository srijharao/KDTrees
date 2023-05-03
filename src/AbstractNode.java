import java.util.ArrayList;
import java.util.List;

/**
 * Abstract Node class implements Node interface and codes getPoints methods. for all points, points
 * in circle and closest points.
 */

public class AbstractNode implements Node {

  protected List<Integer> on;
  protected int depth;

  protected List<Point2D> points;

  protected AbstractNode(List<Point2D> points, List<Integer> data, int depth) {
    this.points = points;
    on = data;
    this.depth = depth;
  }

  public boolean isLeafNode() {
    return false;
  }

  @Override
  public int getLevel() {
    return depth;
  }

  @Override
  public List<Integer> getPointsIndices() {
    return on;
  }

  @Override
  public Node getLeftNode() {
    return null;
  }

  @Override
  public Node getRightNode() {
    return null;
  }

  @Override
  public double getSignedDistance(Point2D point) {
    return Double.MAX_VALUE;
  }

  @Override
  public double getPerpendicularDistance(Point2D point) {
    return Double.MAX_VALUE;
  }

  @Override
  public List<Point2D> getPoints() {
    List<Point2D> pointsInTheNode = new ArrayList<>();
    for (int i = 0; i < on.size(); i++) {
      pointsInTheNode.add(points.get(on.get(i)));
    }
    return pointsInTheNode;
  }

  @Override
  public List<Point2D> allPointsWithinCircle(Point2D center, double radius) {
    return null;
  }

  @Override
  public Point2D closestPoint(Point2D point) {
    return null;
  }

  @Override
  public void add(Point2D point) {
    points.add(point);
    on.add(points.size() - 1);
  }

  @Override
  public List<Point2D> getAllPoints() {
    return points;
  }

  /**
   * method to return the points in circle.
   *
   * @param pointsIndices point indices
   * @param center        of circle
   * @param radius        of circle
   * @return list of points that are in circle
   */

  protected List<Point2D> getPointsInTheCircle(List<Integer> pointsIndices,
      Point2D center, double radius) {
    List<Point2D> pointsInTheCircle = new ArrayList<>();
    for (int i = 0; i < pointsIndices.size(); i++) {
      Point2D currPoint = points.get(pointsIndices.get(i));
      if (currPoint.distance(center) <= radius) {
        pointsInTheCircle.add(currPoint);
      }
    }
    return pointsInTheCircle;
  }

  /**
   * calls getClosestPointsInList with query point and all points in list.
   *
   * @param point query point
   * @return point that is closest to query point
   */
  protected Point2D getClosestPointInNode(Point2D point) {
    return getClosestPointsInList(point, getPoints());
  }

  /**
   * Compute closest points.
   *
   * @param point               query point
   * @param pointsInCurrentNode points in node
   * @return closest point
   */
  protected Point2D getClosestPointsInList(Point2D point, List<Point2D> pointsInCurrentNode) {
    if (pointsInCurrentNode.size() == 0) {
      return null;
    }
    Point2D closestPoint = pointsInCurrentNode.get(0);
    double dist = closestPoint.distance(point);
    for (int i = 0; i < pointsInCurrentNode.size(); i++) {
      double currDist = pointsInCurrentNode.get(i).distance(point);
      if (currDist < dist) {
        dist = currDist;
        closestPoint = pointsInCurrentNode.get(i);
      }
    }
    return closestPoint;
  }
}
