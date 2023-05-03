import java.util.List;

/**
 * Node interface declares public method signatures to be implemented for a KD tree Node.
 */

public interface Node {

  /**
   * whether current node is a leaf node.
   *
   * @return true or false
   */
  boolean isLeafNode();

  /**
   * depth of tree.
   *
   * @return depth
   */
  int getLevel();

  /**
   * convert points to indices.
   *
   * @return indices of points
   */
  public List<Integer> getPointsIndices();

  /**
   * get the left node.
   *
   * @return left node
   */
  Node getLeftNode();

  /**
   * get the right node.
   *
   * @return right node
   */

  Node getRightNode();

  /**
   * signed distance to a point.
   *
   * @param point input
   * @return distance
   */
  double getSignedDistance(Point2D point);

  /**
   * returns perpendicular distance.
   *
   * @param point to calculate distance
   * @return distance
   */
  double getPerpendicularDistance(Point2D point);

  /**
   * retrieve list of points in KD tree.
   *
   * @return point list
   */
  List<Point2D> getPoints();

  /**
   * All points that fall within circle radius.
   *
   * @param center of circle
   * @param radius of circle
   * @return point list
   */
  List<Point2D> allPointsWithinCircle(Point2D center, double radius);

  /**
   * closest point to the query point.
   *
   * @param point query point
   * @return point
   */
  Point2D closestPoint(Point2D point);

  /**
   * add a point to tree.
   *
   * @param point point
   */
  void add(Point2D point);

  /**
   * gets all points.
   * @return list of points
   */
  public List<Point2D> getAllPoints();

}
