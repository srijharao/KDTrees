import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to convert leaf node to non leaf node for when a new point needs to be added to the
 * tree.
 */
public class Utility {

  /**
   * converts leaf node to non leaf node.
   *
   * @param next  next node
   * @param point point
   * @return non leaf node
   */
  public static Node convertLeafNodeToNonLeafNode(Node next, Point2D point) {
    int level = next.getLevel();
    int index = next.getPointsIndices().get(0);
    List<Point2D> points = next.getAllPoints();
    List<Integer> on = next.getPointsIndices();
    Point2D medianPoint = points.get(index);
    int a;
    int b;
    int c;
    if (level % 2 == 0) {
      a = 1;
      b = 0;
      c = medianPoint.x * -1;
    } else {
      a = 0;
      b = 1;
      c = medianPoint.y * -1;
    }
    int signedDistance = signedDistance(point, a, b, c);
    points.add(point);
    if (signedDistance == 0) {
      on.add(points.size() - 1);
      return new NonLeafNode(new LeafNode(points, new ArrayList<>(), level + 1),
          new LeafNode(points, new ArrayList<>(), level + 1), points, on, a, b, c, level);
    }
    List<Integer> temp = new ArrayList<>();
    temp.add(points.size() - 1);
    Node newLeafNode = new LeafNode(points, temp, level + 1);
    if (signedDistance < 0) {
      return new NonLeafNode(newLeafNode, new LeafNode(points, new ArrayList<>(), level + 1),
          points, on, a, b, c, level);
    } else {
      return new NonLeafNode(new LeafNode(points, new ArrayList<>(), level + 1), newLeafNode,
          points, on, a, b, c, level);
    }
  }

  /**
   * computes signed distance.
   *
   * @param point point to add
   * @param a     x coefficient
   * @param b     y coefficient
   * @param c     c
   * @return
   */
  public static int signedDistance(Point2D point, int a, int b, int c) {
    //find the signed distance of point from line ax+by+c=0
    return a * point.x + b * point.y + c;
  }
}
