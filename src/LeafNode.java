import java.util.List;

/**
 * Represents a Node in a Kd tree.
 */
public class LeafNode extends AbstractNode {

  protected LeafNode(List<Point2D> points, List<Integer> on, int depth) {
    super(points, on, depth);
  }

  @Override
  public boolean isLeafNode() {
    return true;
  }

  @Override
  public List<Point2D> allPointsWithinCircle(Point2D center, double radius) {
    return getPointsInTheCircle(getPointsIndices(), center, radius);
  }

  @Override
  public Point2D closestPoint(Point2D point) {
    return getClosestPointInNode(point);
  }

  @Override
  public void add(Point2D point) {
    if (on.size() == 0) {
      points.add(point);
      on.add(points.size() - 1);
    }
  }

  @Override
  public String toString() {
    return "LeafNode{"
        + "data=" + on
        + '}';
  }
}
