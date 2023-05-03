import java.util.List;

/**
 * Represents a non leaf Node in a Kd tree.
 */
public class NonLeafNode extends AbstractNode {

  private int a;
  private int b;
  private int c;
  private Node left;
  private Node right;

  protected NonLeafNode(Node left, Node right, List<Point2D> points, List<Integer> on, int a, int b,
      int c, int depth) {
    super(points, on, depth);
    this.left = left;
    this.right = right;
    this.a = a;
    this.b = b;
    this.c = c;
  }

  @Override
  public boolean isLeafNode() {
    return super.isLeafNode();
  }

  @Override
  public Node getLeftNode() {
    return left;
  }

  @Override
  public Node getRightNode() {
    return right;
  }

  @Override
  public double getSignedDistance(Point2D point) {
    return point.x * a + point.y * b + c;
  }

  @Override
  public double getPerpendicularDistance(Point2D point) {
    return Math.abs(getSignedDistance(point)) / Math.sqrt(a * a + b * b);
  }

  @Override
  public List<Point2D> getPoints() {
    List<Point2D> pointsList = super.getPoints();
    pointsList.addAll(getLeftNode().getPoints());
    pointsList.addAll(getRightNode().getPoints());
    return pointsList;
  }

  @Override
  public void add(Point2D point) {
    boolean isLeftNode = false;
    Node next;
    if (getSignedDistance(point) < 0) {
      next = getLeftNode();
      isLeftNode = true;
    } else if (getSignedDistance(point) > 0) {
      next = getRightNode();
    } else {
      super.add(point);
      return;
    }
    if (next.isLeafNode() && next.getPointsIndices().size() > 0) {
      Node newNode = Utility.convertLeafNodeToNonLeafNode(next, point);
      if (isLeftNode) {
        this.left = newNode;
      } else {
        this.right = newNode;
      }
    } else {
      next.add(point);
    }
  }

  @Override
  public List<Point2D> allPointsWithinCircle(Point2D center, double radius) {
    List<Point2D> pointsWithinCircle;
    double signedDistance = getSignedDistance(center);
    if (signedDistance <= 0) {
      pointsWithinCircle = getLeftNode().allPointsWithinCircle(center, radius);
      if (getPerpendicularDistance(center) < radius) {
        pointsWithinCircle.addAll(getRightNode().allPointsWithinCircle(center, radius));
      }
    } else {
      pointsWithinCircle = getRightNode().allPointsWithinCircle(center, radius);
      if (getPerpendicularDistance(center) < radius) {
        pointsWithinCircle.addAll(getLeftNode().allPointsWithinCircle(center, radius));
      }
    }
    pointsWithinCircle.addAll(getPointsInTheCircle(getPointsIndices(), center, radius));
    return pointsWithinCircle;
  }

  @Override
  public Point2D closestPoint(Point2D point) {
    Point2D p;
    double signedDistance = getSignedDistance(point);
    Point2D center = point;
    double radius = Double.MAX_VALUE;
    if (signedDistance < 0) {
      p = getLeftNode().closestPoint(point);
      if (p != null) {
        radius = point.distance(p);
      }
      if (p == null || getPerpendicularDistance(point) <= radius) {
        Point2D pp = getClosestPointInNode(point);
        if (pp == null) {
          pp = getRightNode().closestPoint(point);
        }
        if (pp != null && point.distance(pp) < radius) {
          p = pp;
          radius = point.distance(p);
        }
      }
      if (getPerpendicularDistance(point) <= radius) {
        List<Point2D> prospectCloserPointsInRightSubTree
            = getRightNode().allPointsWithinCircle(center, radius);
        Point2D pp = getClosestPointsInList(point, prospectCloserPointsInRightSubTree);
        if (pp != null && point.distance(p) > point.distance(pp)) {
          p = pp;
        }
      }
    } else {
      p = getRightNode().closestPoint(point);
      if (p != null) {
        radius = point.distance(p);
      }
      if (p == null || getPerpendicularDistance(point) <= radius) {
        Point2D pp = getClosestPointInNode(point);
        if (pp == null) {
          pp = getLeftNode().closestPoint(point);
        }
        if (pp != null && point.distance(pp) < radius) {
          p = pp;
          radius = point.distance(p);
        }
      }
      if (getPerpendicularDistance(point) <= radius) {
        List<Point2D> prospectCloserPointsInLeftSubTree = getLeftNode().allPointsWithinCircle(
            center, radius);
        Point2D pp = getClosestPointsInList(point, prospectCloserPointsInLeftSubTree);
        if (pp != null && point.distance(p) > point.distance((pp))) {
          p = pp;
        }
      }
    }
    return p;
  }
}
